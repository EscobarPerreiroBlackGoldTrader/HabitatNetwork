/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class ConsListner extends Thread {
    
    Habitat mother;
    private PipedReader pr_income_form_console;

    public ConsListner(Habitat mother) {
        this.mother = mother;
    }

    public void initPW(PipedWriter pw){
        
        try {
            this.pr_income_form_console = new PipedReader(pw);
        } catch (IOException ex) {
            Logger.getLogger(jMyTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private char[] cbuf = new char[80];
//    
//    private DataInputStream dis = new DataInputStream(null);
    
    @Override
    public void run(){
        int i =0;
        while(true){
            try {
                 
                //pr_income_form_console.read(cbuf);
                //String read = chBuff.toString();
                //String read = Arrays.toString(cbuf);
                
                
                
                int val = pr_income_form_console.read();// получаем ASCI код и преобразовываем 
                                                        // в цифру
                char ch = (char)val;
                System.out.println("ConsListener прочитал из консоли [CHAR]: " + /*read*/ch);
                System.out.println("ConsListener прочитал из консоли [INT]: " + /*read*/val);
                
            
                if(ch >= '0' && ch <='9'){
                    cbuf[i] = ch;
                    ++i;
                }
                
                if(ch == '\n'){
                    System.out.println("нашёл 1-й конец строки");
                    int val_next = pr_income_form_console.read();
                    if(val_next == '\n'){
                        System.out.println("нашёл 2-й конец строки");
                        String s = new String(cbuf);
                        s = s.substring(0, i);
                        System.out.println( "Отпарсенный int = " + Integer.parseInt(s));
                        mother.ReduceMotoCountBy(/*Character.getNumericValue(ch)*/Integer.parseInt(s));
                        Arrays.fill(cbuf, (char)0);
                        i = 0;
                    }
                    
                }
//                
//                if( ((ch < '0' || ch > '9') && i>0) ){
//                    
//                    String s = new String(cbuf);
//                    Arrays.fill(cbuf, (char)0);
//                       i = 0;
//                    System.out.println("Преобразовалось в INT: " + Integer.parseInt(s));
//                    mother.ReduceMotoCountBy(Integer.parseInt(s)
//                    /*Character.getNumericValue(ch)*/
//                    );
//                }
                
                
                
                //mother.write_to_console(null);
            } catch (IOException ex) {
                Logger.getLogger(ConsListner.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
