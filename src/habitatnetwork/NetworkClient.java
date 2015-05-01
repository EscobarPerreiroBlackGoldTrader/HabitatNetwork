/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class NetworkClient {
    
    private int port;
    //private String hostname;
    //private String packet;
    private String otvetServera;
    private TreeMap<String,String> clients;
    //---------------------
    private ClientDescriptor cld;
    private String id,name;
    
    //---------------------
    
    private BufferedReader /*inFromUser,*/ inFromServer;
    
    private Socket clientSocket;
    
    private DataOutputStream outToServer;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    // ����������� ������ ���� public, ����� HabitatServer ��� �� �����
    public class ClientDescriptor implements Serializable{ //����� ��� ������ � ��������

        private final String master_id,master_name;
        
        public ClientDescriptor(String id,String name) {
            this.master_id = id;
            this.master_name = name;
        }

        public String getMaster_id() {
            return master_id;
        }

        public String getMaster_name() {
            return master_name;
        } 
    }
    
    
    
    
    public NetworkClient(int port,String hostname,InputStream in, String name) throws IOException {
        this.port = port;
        //this.hostname = hostname;
        this.name = name;
        this.id = "client#" + (new Random()).nextLong();
        this.cld = new NetworkClient.ClientDescriptor(id, this.name);
        //this.inFromUser = new BufferedReader(new InputStreamReader(/*System.in*/in));
        //in.read(id)
        //this.packet = inFromUser.readLine();
        
//        //���������� � ��������
//        this.clientSocket = new Socket(InetAddress.getLocalHost(), port);//Socket(this.hostname,this.port);
//        
//        this.inFromServer = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
//        
//        //this.outToServer.writeBytes(packet + '\n');
//        
//        this.otvetServera = this.inFromServer.readLine();
//        
//        System.out.println("����� �������" + otvetServera);
//        clientSocket.close();
        
        //���������� � ��������
        this.clientSocket = new Socket(InetAddress.getLocalHost(), port);//Socket(this.hostname,this.port);
        //this.outToServer = new DataOutputStream(this.clientSocket.getOutputStream());
        
    }
    
    public NetworkClient() throws IOException{
        this(21285,"localhost",System.in, "Lunohod1");
    }
    
    
    public void check_svyaz() throws IOException{
        if(clientSocket.isClosed()){
            //clientSocket.connect(null);
            clientSocket = new Socket(InetAddress.getLocalHost(), port);
        }
        
        
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //oos = new ObjectOutputStream(clientSocket.getOutputStream());// ��� ������������
        
        //--- 1 send ---
        outToServer.writeBytes(id + '\n'); // + '\n' - ���������� 
        outToServer.flush();
        outToServer.writeBytes(name + '\n');
        outToServer.flush();
        //oos.writeObject(cld); // ������� ���������
        //--------------
        
        //--- 2 recive --
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            
            clients = (TreeMap<String,String>)ois.readObject();
        } catch (ClassNotFoundException ex) {
            System.out.println("---===������ ��� ��������� ����===---");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for( Entry<String, String> entry : clients.entrySet() ){
            System.out.println("������ � �������: " + entry.getKey() + " " + entry.getValue() );
        }
        //---------------
        
        //--- 3 recive ----
        otvetServera = inFromServer.readLine();
        System.out.println("����� �������" + otvetServera);
        
        //-----------------
        
        clientSocket.close();
    }
    
    
}