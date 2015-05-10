/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class NetworkClient {
    private MainJFrame mother;
    
    //------------------ ����������� ����� ��� �������� lst �� �������� ���������� -----
    private int wellcome_listen_port; //= 21288; -��������� ������������ ����� �����
    //private ServerSocket inbound_wellcome_listener;
    private ServerPart sp;
    //----------------------------------------------------------------------------------

    private String otvetServera;
    private TreeMap<String,String> clients;
    private ArrayList<ClientDescriptor> friendsList;
    //---------------------
    private final ClientDescriptor cld;
    private final String id,name;
    private final Habitat habitat;
    private final int port;
    
    //---------------------
    
   // private BufferedReader /*inFromUser,*/ inFromServer;
    
    private Socket clientSocket;
    
    //private DataOutputStream outToServer;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    //*********************************************
    private Updater friendsListUpdater; //��������� ������ �������� � �������, � �������� ����������
    private Timer timer_FLU;
    private long period; // ������� ���������� �����
    private boolean timerStarted = false;

    

    
    //**************************************************************************
    private class Updater extends TimerTask {

        private NetworkClient mother;

        public Updater(NetworkClient mother) {
            this.mother = mother;
        }

        
        @Override
        public void run() {
            System.out.println("������� �������� downloadFriendsList()");
            mother.downloadFriendsList();
        }
        
    }
    //**************************************************************************
    private synchronized void downloadFriendsList(){
        
        try {
            oos.writeObject(new OperationDescriptor(OperationDescriptor.op.GET_FRIENDS_LIST));
            System.out.println("��������� GET_FRIENDS_LIST ���������");
            oos.flush();
            
            ArrayList<ClientDescriptor> friends_list = (ArrayList<ClientDescriptor>)ois.readObject();
            System.out.println("friends_list �������");
            
            mother.renewFriendsList(friends_list, cld);//���������, �� �� ������� ������
            
            
        }catch (IOException ex) {
            System.out.println("IOException - ���������� ����� �����������");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException - ��� ������ ������ �� ������� (���������)");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void InitTimer() {
        if(timerStarted)return;
        timer_FLU.schedule(friendsListUpdater,0, period); //period �� ��������� 5 ���.
        //timer_FLU.
        timerStarted = true;
    }
//    private void setPeriodRenewFriendsList(){
//        
//    }
    
    public void setPeriod(int period) {
        this.period = period;
    }
    //**************************************************************************
    // ����������� ������ ���� public, ����� HabitatServer ��� �� �����
//    public class ClientDescriptor1 implements Serializable{ //����� ��� ������ � ��������
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
    
    
    
    public NetworkClient(int port,String hostname,InputStream in, String name,Habitat habitat,MainJFrame mother) throws IOException {
        this.mother = mother;
        this.habitat = habitat;
        this.port = port;
        this.name = name;
        this.id = String.valueOf((new Random()).nextLong());
        /*this.cld = new NetworkClient.ClientDescriptor1(id, this.name);*/
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
        
        this.sp = new ServerPart(/*wellcome_listen_port,*/habitat); //������� ���� �� ������� �������� ������� ������ lst
        this.sp.start(); //???
        this.wellcome_listen_port = this.sp.getWellcomePort(); //������ �� ����� ����� ���������� ���������������(����������� exchange)
        this.cld = new ClientDescriptor(id, name,this.wellcome_listen_port);
        
        //timer
        timer_FLU = new Timer();
        friendsListUpdater = new Updater(this);
        this.period = 5000;
        
    }
    
//    public NetworkClient() throws IOException{
//        this(21285,"localhost",System.in, "Lunohod" + (new Random()).nextInt(10000),null);
//    }
    
    
//    public void check_svyaz() throws IOException{
//        if(clientSocket.isClosed()){
//            //clientSocket.connect(null);
//            clientSocket = new Socket(InetAddress.getLocalHost(), port);
//        }
//        
//        
//        outToServer = new DataOutputStream(clientSocket.getOutputStream());
//        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        //oos = new ObjectOutputStream(clientSocket.getOutputStream());// ��� ������������
//        
//        //--- 1 send ---
//        outToServer.writeBytes(id + '\n'); // + '\n' - ���������� 
//        outToServer.flush();
//        outToServer.writeBytes(name + '\n');
//        outToServer.flush();
//        //oos.writeObject(cld); // ������� ���������
//        //--------------
//        
//        //--- 2 recive --
//        try {
//            ois = new ObjectInputStream(clientSocket.getInputStream());
//            
//            clients = (TreeMap<String,String>)ois.readObject();
//        } catch (ClassNotFoundException ex) {
//            System.out.println("---===������ ��� ��������� ����===---");
//            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        for( Entry<String, String> entry : clients.entrySet() ){
//            System.out.println("������ � �������: " + entry.getKey() + " " + entry.getValue() );
//        }
//        //---------------
//        
//        //--- 3 recive ----
//        otvetServera = inFromServer.readLine();
//        System.out.println("����� �������" + otvetServera);
//        
//        //-----------------
//        
//        clientSocket.close();
//    }
//    
    
    
    public ClientDescriptor getCld(){
        return cld;
    }

    public void establishConnect() throws IOException {
        if(clientSocket == null)
            clientSocket = new Socket(InetAddress.getLocalHost(), port);
        
        if(oos == null)
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
        System.out.println("NetworkClien:: t������� ������ OperationDescriptor.op.HANDSHAKE\n");
        oos.writeObject(new OperationDescriptor(OperationDescriptor.op.HANDSHAKE)); //��������������
        oos.writeObject(cld); // �������� ���� ��������� ������� (�������������-���-����_exchange)
        oos.flush();
        
        if(ois == null)
            ois = new ObjectInputStream(clientSocket.getInputStream());
        try {
            
            if(friendsList != null)friendsList = null;
            
            System.out.println("friendslist = " + 
                    (friendsList = (ArrayList<ClientDescriptor>)ois.readObject()) );
            
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException ��� ������� ������� friendsList");
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ois.close(); oos.close(); clientSocket.close();
        InitTimer();
    }

    public ArrayList<ClientDescriptor> getFriendsList() {
        return friendsList;
    }
    
    private ClientDescriptor check_in_friends(String s){
        
        //String id = s.substring(s.indexOf(/*"client#"*/this.name),s.length());
        String id = s.substring(this.name.length(),s.length());
        System.out.println("id is " + id);
        
        for(Iterator<ClientDescriptor> it = friendsList.iterator();it.hasNext();){
            ClientDescriptor next = it.next();
            if(next.getMaster_id().equals(id))return next;
        }
        return null;
    }    

    public void exchangeData(String sVal) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        ClientDescriptor cld_tmp = check_in_friends(sVal);
        
        if(cld_tmp != null){
            System.out.println("-------\nreturned cld id " + cld_tmp.getMaster_id());
            
//            try {
//                clientSocket = new Socket(InetAddress.getLocalHost(), port);
//                
//            } catch (UnknownHostException ex) {
//                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            try {
//                oos = new ObjectOutputStream(clientSocket.getOutputStream());
//            } catch (IOException ex) {
//                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
            try {// ������������� ������, ��� ����� ������������ � ������ ��������
                oos.writeObject( new OperationDescriptor(OperationDescriptor.op.EXCHANGE));
                oos.flush();
            } catch (IOException ex) {
                System.out.println("Exchange �� ������");
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try {
                oos.writeObject(cld_tmp);//�������� ���������� ���� � ��� ����� ������������� �����
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try{
                boolean result = ois.readBoolean();//������� ���������� �������� (������ ��������� ������� ���������� � ����������� ��������)
                System.out.println("������� ��������� result: " + result);
                if(result){
                    //�������� ����� ����������
                    oos.writeObject(/*habitat.lst*/habitat.get_lst_copy());
                    oos.flush();
                    System.out.println("���� ���������� ���������");
                    //���� ����� ����������
                    CopyOnWriteArrayList<BaseAI> resp_lst =  (CopyOnWriteArrayList<BaseAI>)ois.readObject();
                    System.out.println("���� ���������� ������");
                    habitat.update_lst(resp_lst);
                    System.out.println("����������� ���� �������");
                }else{
                    System.out.println("����� ��� ������ �����������");
                }
            }catch(IOException e){
                System.out.println("yedjn "+ e);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
        }
    }

//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize(); //To change body of generated methods, choose Tools | Templates.
//        System.out.println("finalize");
//        disconnectProperly();
//    }

    private void disconnectProperly() {
        try {
            //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            oos.writeObject(new OperationDescriptor(OperationDescriptor.op.BYE));
            oos.writeObject(cld);
            oos.flush();
            System.out.println("�������� �����");
            oos.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
