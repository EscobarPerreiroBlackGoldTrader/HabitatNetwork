/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 */
public class CarMan extends Thread{
    private final Habitat mother;
    private CopyOnWriteArrayList<BaseAI> lst;
    /**
     *  ������ �������� ����
     */
    private int p_w; 
    
    /**
     *  ���������� ���������� ��� ������������ � ��������� �����/�����
     */
    boolean freezeUP; 
    
    boolean going = true;
    
    
    public CarMan(Habitat mother){
        this.mother = mother;
        this.lst = mother.lst;
        //this.p_w = mother.getWidth();
    }

    private void setLst(CopyOnWriteArrayList<BaseAI> lst) {
        this.lst = lst;
    }
    
    @Override
    public void run(){
        while(going){
            
            //-------------------------------------
            if(mother.isPausedCar()){
                freezeUP = true;
                try {
                    freeze();
                } catch (InterruptedException ex) {
                    System.out.println("������ - InterruptedException ��� ������� ���������� ����� ����� (�� � ����� ��������� �����)");
                    Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //-------------------------------------
            
            setLst(mother.lst);//����� �������� ������ �� lst �� ���������� 
                               //���������� ��������� ������ �� ����, 
                               //����� ����� �������� ����� ����� ��������� 
                               //�� ������ ���� � �� ������ ������������
            BaseAI a;
            int x,speed; 
            p_w = mother.getWidth();
            
            //**************************************************************
            for(Iterator<BaseAI> it = lst.iterator();it.hasNext();){
                //System.out.println("������� ������ ������");
                if(mother.isPausedCar()){
                    freezeUP = true;
                    try {
                        freeze();
                    } catch (InterruptedException ex) {
                        System.out.println("������ - InterruptedException ��� ������� ���������� ����� �����");
                        Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
               a = it.next();
               if(a instanceof Car){
                   //System.out.println("����� ������, ������� �������");
                   x = a.getX();
                   if(x < p_w){
                       a.mooveX();
                       //System.out.println("������� ������");
                   }
                   if(x >= p_w)a.setX(0); 
               }
               //System.out.println("�������� ���� �������� ������ �����");
                    
            }// end of for
            //**************************************************************
            
            try {//��� �����
                    TimeUnit.MILLISECONDS.sleep(mother.CLOCK_RATE);
            } catch (InterruptedException ex) {
                    System.out.println("�� ���� ������� ����� ������� ��������� ������ �����");
                    Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread.yield();
            mother.repaint();
            //System.out.println("������������� �� CarMan");
        }
    }
    
    private synchronized void freeze() throws InterruptedException{
       while(freezeUP)wait();
    }
    
    public synchronized void unfreeze(){
        freezeUP = false;
        notify();
    }
    
    public void workDone(){
        this.going = false;
    }
}
