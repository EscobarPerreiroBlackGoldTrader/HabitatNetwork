/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class ServerPart extends Thread{
    
    private int wellcome_listen_port;
    private ServerSocket wellcome;
    private Habitat habitat;
    
    ServerPart(/*int wellcome_listen_port,*/Habitat habitat) {
//        this.port = wellcome_listen_port;
        this.habitat = habitat;
        try {
            wellcome = new ServerSocket(/*port*/0);
            System.out.println("ServerPart:: wellcome создан: " + wellcome);
            this.wellcome_listen_port = wellcome.getLocalPort();
            System.out.println("ServerPart:: wellcome_listen_port is: " + wellcome_listen_port);
        } catch (IOException ex) {
            System.out.println("неудалось создать прослушивающий сокет для обмена lst");
            Logger.getLogger(ServerPart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run(){
        while(true){
            try {
                System.out.println("ServerPart::run() начинаю слушать порт " + wellcome_listen_port);
                Socket connSocket = wellcome.accept();
                System.out.println("ServerPart::run() connSocket создан: " + connSocket);
                ReturnLST_Processor p = new ReturnLST_Processor(connSocket,habitat);
                p.start();
                System.out.println("ServerPart::run() ReturnLST_Processor создан и запущен: " + p);
            } catch (IOException ex) {
                Logger.getLogger(ServerPart.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        }
    }

    synchronized int getWellcomePort() {
        return wellcome_listen_port;
    }
}
