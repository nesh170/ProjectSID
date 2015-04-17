package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.scene.input.KeyCode;

/**
 * This is the network class, it has methods to set up a server and to also set up a client network which allows the user to send keycodes across
 * It uses Java Implementation of sockets.
 * 
 * To use this class, The user who wants to instantiate the chat should call the setUpServer and then procceed to call setUpClient
 * to connect to the server. The game who wants to connect to the other game should get up the client by passing in the host name which was returned
 * in the setUpServer method and the portNumber. Messages or KeyCode can be sent across the server based on send and get Methods below.
 * 
 * @author Sivaneshwaran Loganathan & Le Qi
 *
 */
public class Network {

    private int myPortNumber; 
    private ServerSocket serverSocket; // TODO might want to delete this line
    private Socket myClientSocket;
    private PrintWriter myInput;
    private BufferedReader myOutput;


    /**
     * This method sets up a ServerSocket
     * @return host name of the server which is needed when a client is associated to it
     */
    public String setUpServer (int portNumber) {
        myPortNumber=portNumber;
        try {
            serverSocket = new ServerSocket(myPortNumber);
        }
        catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port "
                               + myPortNumber + " or listening for a connection");
            return "";
        }
        return serverSocket.getInetAddress().getHostName();
    }

    /**
     * This method sets up the client side. It also creates a printWriter to accept the data from the server.
     * It creates a bufferedReader to allow the user to send the data to the server
     * @param host is the host name of the server
     * @param port is the port to connect the server too
     */
    public void setUpClient (String host, int port) {
        try {
            myClientSocket = new Socket(host, port);
            myInput = new PrintWriter(myClientSocket.getOutputStream(), true);
            myOutput = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream()));
        }
        catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port "
                               + port + " or listening for a connection");
        }

    }

    /**
     * Sends the KeyCode to the server. 
     * @param key is the KeyCode
     */
    public void sendKeyCode (KeyCode key) {
        myInput.println(key.getName());
    }

    /**
     * Gets the keyCode from the server. 
     * @return keyCode
     */
    public KeyCode getKeyCode () {
        String keyString = "";
        try {
            keyString = myOutput.readLine();
        }
        catch (IOException e) {
            System.out.println("Error no Keys found");
        }
        return KeyCode.getKeyCode(keyString);
    }
    
    /**
     * Gets the output from the server and returns in
     * @return
     * @throws IOException
     */
    public String getStringFromServer() throws IOException{
        return myOutput.readLine();
    }
    
    /**
     * Sends the string to the server to be sent to the clients
     * @param stringToSend
     */
    public void sendStringToServer(String stringToSend){
        myInput.println(stringToSend);
    }
    
    
    
}