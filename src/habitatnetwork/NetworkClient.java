/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
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
    private ArrayList<ClientDescriptor> friendsList;
    //---------------------
    private ClientDescriptor cld;
    private String id,name;
    
    //---------------------
    
    private BufferedReader /*inFromUser,*/ inFromServer;
    
    private Socket clientSocket;
    
    private DataOutputStream outToServer;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    // обязательно должен быть public, иначе HabitatServer его не видит
//    public class ClientDescriptor1 implements Serializable{ //класс для обмена с сервером
//
//        private String master_id,master_name;
//        
//        public ClientDescriptor1(String id,String name) {
//            this.master_id = id;
//            this.master_name = name;
//        }
//
//        public String getMaster_id() {
//            return master_id;
//        }
//
//        public String getMaster_name() {
//            return master_name;
//        } 
//
//        public void setMaster_id(String master_id) {
//            this.master_id = master_id;
//        }
//
//        public void setMaster_name(String master_name) {
//            this.master_name = master_name;
//        }
//        
//    }
//    
    
    
    
    public NetworkClient(int port,String hostname,InputStream in, String name) throws IOException {
        this.port = port;
        //this.hostname = hostname;
        this.name = name;
        this.id = "client#" + (new Random()).nextLong();
        /*this.cld = new NetworkClient.ClientDescriptor1(id, this.name);*/
        this.cld = new ClientDescriptor(id, name);
        //this.inFromUser = new BufferedReader(new InputStreamReader(/*System.in*/in));
        //in.read(id)
        //this.packet = inFromUser.readLine();
        
//        //соединение с сервером
//        this.clientSocket = new Socket(InetAddress.getLocalHost(), port);//Socket(this.hostname,this.port);
//        
//        this.inFromServer = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
//        
//        //this.outToServer.writeBytes(packet + '\n');
//        
//        this.otvetServera = this.inFromServer.readLine();
//        
//        System.out.println("Ответ сервера" + otvetServera);
//        clientSocket.close();
        
        //соединение с сервером
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
        //oos = new ObjectOutputStream(clientSocket.getOutputStream());// для сериализации
        
        //--- 1 send ---
        outToServer.writeBytes(id + '\n'); // + '\n' - обязателно 
        outToServer.flush();
        outToServer.writeBytes(name + '\n');
        outToServer.flush();
        //oos.writeObject(cld); // послать описатель
        //--------------
        
        //--- 2 recive --
        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            
            clients = (TreeMap<String,String>)ois.readObject();
        } catch (ClassNotFoundException ex) {
            System.out.println("---===Ошибка при получении Мапа===---");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for( Entry<String, String> entry : clients.entrySet() ){
            System.out.println("Список с сервера: " + entry.getKey() + " " + entry.getValue() );
        }
        //---------------
        
        //--- 3 recive ----
        otvetServera = inFromServer.readLine();
        System.out.println("Ответ сервера" + otvetServera);
        
        //-----------------
        
        clientSocket.close();
    }
    
    
    public void test() throws IOException{
        
        if(clientSocket == null)
            clientSocket = new Socket(InetAddress.getLocalHost(), port);
        
        if(oos == null)
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
        System.out.println("посылаю объект\n");
        oos.writeObject(cld);
        oos.flush();
        //clientSocket.close(); // здесь закрывать нельзя
        
        if(ois == null)
            ois = new ObjectInputStream(clientSocket.getInputStream());
        try {
            
            System.out.println("friendslist = " + 
                    (friendsList = (ArrayList<ClientDescriptor>)ois.readObject()) );
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException при попытке принять friendsList");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ArrayList<ClientDescriptor> getFriendsList() {
        return friendsList;
    }
    
    
}
