/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class SettingsMan {
    private String filename ,path;
    private File f;
    private FileReader fr;
    private FileWriter fw;
//    private FileInputStream fis;
//    private FileOutputStream fos;
//==============================================================================
    public SettingsMan(String filename, String path) {
        this.filename = filename;
        this.path = path;

        f = new File(path+filename);
        
        try {
            if(f.createNewFile()){ // ���� ����� �� ���� �� ���������� � �� ��������
                System.out.println("��� ���������� �����: " + f.getName());
            }
            
            try { 
//                
                if( !(f.length() > 0) ) // ���� ���� ������ ������ � ������, ������� �����������
                    fw = new FileWriter(f);
                //fw = new FileWriter(f, true); //��������� append ����� ������ ������ ��� ������
            } catch (IOException ex) {
                System.out.println("������ ��� ������� ������� FileWriter ��� ����� " + f);
                Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                fr = new FileReader(f);
            } catch (IOException ex) {
                System.out.println("������ ��� ������� ������� FileReader ��� ����� " + f);
                Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        } catch (IOException ex) {
            System.out.println("IOException - ��� ������� ������� ���� �� ����: " + path);
            Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
//==============================================================================    
    public SettingsMan(){// ����������� �� ���������
        this("settings.cfg", "");//����� ��� ����� �������� � ���� � ����
    }
//==============================================================================    
    public boolean settings_save(/*MainJFrame mf*/String[] arrset){
        try {
            System.out.println("������������ ������ arrset: ");
            
            if(fw == null)fw = new FileWriter(f);
            
            for(String s: arrset){
                if(s != null)fw.write(s);
                //fw.write(System.lineSeparator()); // "\n" - ����� �� ��������
            }
            
        } catch (IOException ex) {
            System.out.println("������ ��� ������� ���������� ��������!!! ");
            Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
                
//        for(Component c: cmp){
//            System.out.println("������������ ������ Components: " + c);
//            if(c instanceof JComboBox){
//                String name = ((JComboBox)c).getName();
//                Object o = ((JComboBox)c).getSelectedItem();
//                String s = name + " " +(String)o;
//                
//                try {
//                    fw.write(s);
//                } catch (IOException ex) {
//                    System.out.println("������ ��� ������� ���������� ��������: " + s);
//                    Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            
//            if(c instanceof JRadioButton){
//                if(((JRadioButton)c).isSelected()){
//                    String name = ((JRadioButton)c).getName();
//                    String s = name + " " + 1;
//                    try {
//                        fw.write(s);
//                    } catch (IOException ex) {
//                        System.out.println("������ ��� ������� ���������� ��������: " + s);
//                        Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//            
//        }
        
        try {
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.println("������ ��� ������� fw.close() ");
            Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

//==============================================================================
    
    
    public String[]  settings_load(){
//        CharBuffer buf = CharBuffer.allocate(
//                /*ASCIIReader.DEFAULT_BUFFER_SIZE*/
//                UTF8Reader.DEFAULT_BUFFER_SIZE);
        char[] deep = new char[UTF8Reader.DEFAULT_BUFFER_SIZE]; 
        
        
        try {
            //fr.re
            System.out.println("���� � �����: " + f.getCanonicalPath());
            
            //----------------��������� ��������--------------------------------
            if(!f.canRead())System.out.println("�� ���� ������ �� �����");
//            if(buf.isReadOnly()) System.out.println("����� ������ ��� ������");
            //------------------------------------------------------------------
            
            char c=0;
            int i=0;
            do{
                c = (char)fr.read();
                if(c == /*-1*/'\uffff')
                    break;//
               //buf =  buf.append(c);
                deep[i] = c;
                ++i;
            }while(true);
//            fr.read(buf);
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(SettingsMan.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        int lines = 0;
//        for(int i=0;i<buf.length();++i){
//            if(buf.get(i) == '\n')++lines;
//        }
        
        
        
        
//        for(int i=0;i<buf.length();++i){
//            if(buf.get(i) != '\n'){
//                
//            }
//            
//        }
        
        //String sb = buf.toString();
        
        String sb = String.copyValueOf(deep);
        sb = sb.trim();
        StringTokenizer tok = new StringTokenizer(sb, "\n");
        int ct = tok.countTokens();
        String[] res = new String[ct];
        //res = oper.toArray(buf);
       for(int i=0;i<ct;++i) {
           res[i] = tok.nextToken();
       }
        
       System.out.println("----- ������� ����� ����������� ����� �������� -----");
        for(String s:res){
            System.out.println(s);
        }
       System.out.println("-----------------------------------------");
        return res;
    }
    
}
//==============================================================================
