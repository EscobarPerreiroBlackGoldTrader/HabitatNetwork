/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author iUser
 */
public class jMyTextArea extends JTextArea implements Runnable{
    private PipedReader pr;

    //-- для обратной связи --
    private PipedWriter out_pw = new PipedWriter();

    public PipedWriter getWrite_Stream() {
        return out_pw;
    }
    //------------------------
    
    public jMyTextArea(){} 
    
    public jMyTextArea(PipedWriter pw) {
        try {
            this.pr = new PipedReader(pw);
        } catch (IOException ex) {
            Logger.getLogger(jMyTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initPW(PipedWriter pw){
        try {
            this.pr = new PipedReader(pw);
        } catch (IOException ex) {
            Logger.getLogger(jMyTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public PipedReader getReead_Stream() {
        return pr;
    }
    
    public void write_to_ConsListener(String s){
        try {
            out_pw.write(/*(s.toCharArray())*/ s);
        } catch (IOException ex) {
            Logger.getLogger(jMyTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //private DataInputStream dis = new DataInputStream(pr);
    
    @Override
    public void run() {
        while(true){
            try {
                char ch =  (char)pr.read();
                
                System.out.println("jMyTextArea Reeding ... " + ch);
                
                this.append(String.valueOf(ch));
                
                //this.setText();
            } catch (IOException ex) {
                Logger.getLogger(jMyTextArea.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("jMyTextArea Jobs finishing...");
            }
        }
    }
    
}
    
