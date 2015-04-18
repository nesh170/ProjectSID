package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javafx.scene.input.KeyCode;


/**
 * This is the network class, it has methods to set up a server and to also set up a client network
 * which allows the user to send keycodes across
 * It uses Java Implementation of sockets.
 * 
 * To use this class, The user who wants to instantiate the chat should call the setUpServer and
 * then procceed to call setUpClient
 * to connect to the server. The game who wants to connect to the other game should get up the
 * client by passing in the host name which was returned
 * in the setUpServer method and the portNumber. Messages or KeyCode can be sent across the server
 * based on send and get Methods below.
 * 
 * @author Sivaneshwaran Loganathan & Le Qi
 *
 */
public class Network {
    private static final int MAX_PACKET_SIZE = 15000;
    private static final String TEST_WORD = "SCROLLINGINTHEDEEP";
    private int myPortNumber;
    private DatagramSocket myServerSocket;
    private DatagramSocket myClientSocket;
    private InetAddress clientIPAddress;
    private InetAddress serverIPAddress;


    public void setUpServer (int portNumber) throws IOException {
        myPortNumber = portNumber;
        myServerSocket = new DatagramSocket(portNumber, InetAddress.getByName("0.0.0.0"));
        myServerSocket.setBroadcast(true);
        while (true) {
            byte[] recvBuf = new byte[15000];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            myServerSocket.receive(packet);
            String message = new String(packet.getData()).trim();
            if (message.equals(TEST_WORD)) {
                clientIPAddress = packet.getAddress();
                System.out.println(clientIPAddress); //TODO remove this at production
                byte[] sendData = TEST_WORD.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, packet.getAddress(),
                                           packet.getPort());
                myServerSocket.send(sendPacket);
                break;
            }
        }
    }

    
    public void setUpClient (int portNumber) throws IOException {
        myPortNumber = portNumber;
        myClientSocket = new DatagramSocket();
        myClientSocket.setBroadcast(true);
        byte[] sendData = TEST_WORD.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length,
                                   InetAddress.getByName("10.190.37.169"), myPortNumber); //TODO Add the loop here
        myClientSocket.send(sendPacket);
        byte[] recvBuf = new byte[MAX_PACKET_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
        myClientSocket.receive(receivePacket);
        String message = new String(receivePacket.getData()).trim();
        if (message.equals(TEST_WORD)) {
            serverIPAddress = receivePacket.getAddress();
            System.out.println("TEST SUCESSS" + serverIPAddress); //TODO remove at prodcutions
        }
    }


    public String getStringFromServer () throws IOException {
        return getStringHelper(myClientSocket);
    }


    public String getStringFromClient () throws IOException {
        return getStringHelper(myServerSocket);
    }

    private String getStringHelper (DatagramSocket socket) throws IOException {
        byte[] recvBuf = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(packet);
        return new String(packet.getData()).trim();
    }

    public void sendStringToServer (String stringToSend) throws IOException {
        sendStringHelper(myClientSocket, stringToSend, serverIPAddress);
    }
    
    public void sendStringToClient (String stringToSend) throws IOException {
        sendStringHelper(myServerSocket, stringToSend, clientIPAddress);
    }
    
    private void sendStringHelper (DatagramSocket senderSocket, String toSend, InetAddress receipientAddress) throws IOException {
        byte[] sendData = toSend.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length,
                                   receipientAddress, myPortNumber); //TODO Add the loop here
        senderSocket.send(sendPacket);
    }

    public void closeClientSocket () {
        myClientSocket.close();
    }

}
