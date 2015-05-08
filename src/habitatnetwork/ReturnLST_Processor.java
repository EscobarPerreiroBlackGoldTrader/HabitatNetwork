/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
class ReturnLST_Processor extends Thread{
    
    private Socket conn;
    private Habitat habitat;
    
    ObjectOutputStream oos;
    ObjectInputStream ois;

    ReturnLST_Processor(Socket conn, Habitat habitat) {
        
        this.conn = conn;
        this.habitat = habitat;
    }
    
    @Override
    public void run(){
        try {
            System.out.println("ReturnLST_Processor::run() ������� ������� ������");
            oos = new ObjectOutputStream(conn.getOutputStream());
            System.out.println("ReturnLST_Processor::run() ������ oos: " + oos);
            ois = new ObjectInputStream(conn.getInputStream());
            System.out.println("ReturnLST_Processor::run() ������ ois: " + ois);
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ///  ���������� ����� ///
        System.out.println("ReturnLST_Processor::run()  �������� �����");
        CopyOnWriteArrayList<BaseAI> income_lst = null;
        CopyOnWriteArrayList<BaseAI> lst = habitat.get_lst_copy();
        System.out.println("ReturnLST_Processor::run()  ����� ������");
        try {
            //�������� lst �� ����������
            income_lst = (CopyOnWriteArrayList<BaseAI>)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            oos.writeObject(lst);// �������� ����� (����������)�������� �����
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        if(income_lst != null)habitat.update_lst(income_lst);
        
        
        try {
            sleep(1000);//������� �������
        } catch (InterruptedException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ois.close();
            oos.close();
            conn.close();
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// �����
    
    
}
