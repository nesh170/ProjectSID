package util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 * The network class is designed to make your life easier to set up a 2 player network game.
 * It uses UDP to sets up the server and client and allows you to send packets across the same wireless
 * network to each of the players. 
 * 
 * This utility is able to send strings witch a maximum size of 15000 bytes across the network.
 * In prioritizing ease of use over efficiency, the program runs a loop over all of Duke Subnet
 * IP addresses to determine the Client and the Server IPaddress and send a IDENTIFIER_WORD to create
 * a connection between them
 * 
 * Due to JavaFX not being thread-safe, run the getString methods using a task object to prevent the threads 
 * from affecting each other
 * 
 * DISCLAIMER: This does leave a open hole in the network so do not use it if you are not under a 
 * Firewall.
 * 
 * @author Le Qi & Sivaneshwaran Loganathan
 *
 */
public class Network {
    private static final int MAX_PACKET_SIZE = 15000;
    private static final String IDENTIFIER_WORD = "SCROLLINGINTHEDEEP";
    private int myPortNumber;
    private DatagramSocket myServerSocket;
    private DatagramSocket myClientSocket;
    private InetAddress clientIPAddress;
    private InetAddress serverIPAddress;


    /**
     * This method sets up the server. It then waits for a respond from a client before
     * completing the set up protocol to allow easy sending of data across the network
     * 
     * @param portNumber is the port you want the server to listen to (any number between 1025 and 65536)
     * It has to be the same with the client port
     * @throws IOException
     */
    public void setUpServer (int portNumber) throws IOException {
        myPortNumber = portNumber;
        myServerSocket = new DatagramSocket(portNumber, InetAddress.getByName("0.0.0.0"));
        myServerSocket.setBroadcast(true);
        while (true) {
            byte[] recvBuf = new byte[15000];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            myServerSocket.receive(packet);
            String message = new String(packet.getData()).trim();
            if (message.equals(IDENTIFIER_WORD)) {
                clientIPAddress = packet.getAddress();
                System.out.println(clientIPAddress); //TODO remove this at production
                byte[] sendData = IDENTIFIER_WORD.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, packet.getAddress(),
                                           packet.getPort());
                myServerSocket.send(sendPacket);
                break;
            }
        }
    }

    /**
     * This method sets up the client side. It ensures a connection with the server by receiving the
     * identifier word. For easibility, it loops through all of Duke Subnet addresses to create a connection
     * @param portNumber is the port you want the client to listen to (any number between 1025 and 65536)
     * It has to be the same with the server port
     * @throws IOException
     */
    public void setUpClient (int portNumber) throws IOException {
        myPortNumber = portNumber;
        myClientSocket = new DatagramSocket();
        myClientSocket.setBroadcast(true);
        byte[] sendData = IDENTIFIER_WORD.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length,
                                   InetAddress.getByName("10.190.37.169"), myPortNumber); //TODO Add the loop here
        myClientSocket.send(sendPacket);
        byte[] recvBuf = new byte[MAX_PACKET_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
        myClientSocket.receive(receivePacket);
        String message = new String(receivePacket.getData()).trim();
        if (message.equals(IDENTIFIER_WORD)) {
            serverIPAddress = receivePacket.getAddress();
            System.out.println("TEST SUCESSS" + serverIPAddress); //TODO remove at prodcutions
        }
    }


    /**
     * Gets the string from the server
     * @return
     * @throws IOException
     */
    public String getStringFromServer () throws IOException {
        return getStringHelper(myClientSocket);
    }


    /**
     * Gets the string from the Client
     * @return
     * @throws IOException
     */
    public String getStringFromClient () throws IOException {
        return getStringHelper(myServerSocket);
    }

    private String getStringHelper (DatagramSocket socket) throws IOException {
        byte[] recvBuf = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(packet);
        return new String(packet.getData()).trim();
    }

    /**
     * Sends the string to the Server
     * @param stringToSend
     * @throws IOException
     */
    public void sendStringToServer (String stringToSend) throws IOException {
        sendStringHelper(myClientSocket, stringToSend, serverIPAddress);
    }
    
    /**
     * Sends the string to the client
     * @param stringToSend
     * @throws IOException
     */
    public void sendStringToClient (String stringToSend) throws IOException {
        sendStringHelper(myServerSocket, stringToSend, clientIPAddress);
    }
    
    private void sendStringHelper (DatagramSocket senderSocket, String toSend, InetAddress receipientAddress) throws IOException {
        byte[] sendData = toSend.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length,
                                   receipientAddress, myPortNumber);
        senderSocket.send(sendPacket);
    }

    /**
     * Closes the client Socket to prevent leaving an open hole in the network
     */
    public void closeClientSocket () {
        myClientSocket.close();
    }

}
