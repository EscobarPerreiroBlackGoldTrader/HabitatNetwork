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
            System.out.println("ReturnLST_Processor::run() пытаюсь создать потоки");
            oos = new ObjectOutputStream(conn.getOutputStream());
            System.out.println("ReturnLST_Processor::run() создан oos: " + oos);
            ois = new ObjectInputStream(conn.getInputStream());
            System.out.println("ReturnLST_Processor::run() создан ois: " + ois);
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ///  начинается обмен ///
        System.out.println("ReturnLST_Processor::run()  формирую листы");
        CopyOnWriteArrayList<BaseAI> income_lst = null;
        CopyOnWriteArrayList<BaseAI> lst = habitat.get_lst_copy();
        System.out.println("ReturnLST_Processor::run()  листы готовы");
        try {
            //получаем lst от инициатора
            income_lst = (CopyOnWriteArrayList<BaseAI>)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            oos.writeObject(lst);// отправка листа (респондера)конечной точки
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ReturnLST_Processor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        if(income_lst != null)habitat.update_lst(income_lst);
        
        
        try {
            sleep(1000);//подождём немного
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
    }// копец
    
    
}
