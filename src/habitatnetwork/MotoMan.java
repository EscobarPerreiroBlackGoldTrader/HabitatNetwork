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
public class MotoMan extends Thread{
    private final Habitat mother;
    private CopyOnWriteArrayList<BaseAI> lst;
    private int p_h;
    private boolean freezeUP;
    boolean going = true;
    
    public MotoMan(Habitat mother){
        this.mother = mother;
        this.lst = mother.lst;
        //this.p_h = mother.getHeight();
    }

    private void setLst(CopyOnWriteArrayList<BaseAI> lst) {
        this.lst = lst;
    }
    
    @Override
    public void run(){
        while(going){
            
            //------------------------
            if(mother.isPausedMoto()){
                    freezeUP = true;
                   try {
                       freeze();
                   } catch (InterruptedException ex) {
                       System.out.println(" ошмар - InterruptedException при попытке заморозить поток мотоциклов (не в цикле просмотра листа)");
                       Logger.getLogger(MotoMan.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
            
            //------------------------
            
            setLst(mother.lst);//иначе обновить ссылку на lst не получаетс€ 
                               //необходимо обновл€ть ссылку на лист, 
                               //иначе после загрузки листа будет указывать 
                               //на старый лист и не увидит загруженного
            BaseAI a = null;
            int y,speed; 
            p_h = mother.getHeight();
            
            //**************************************************************
            for(Iterator<BaseAI> it = lst.iterator();it.hasNext();){
               
               if(mother.isPausedMoto()){
                    freezeUP = true;
                   try {
                       freeze();
                   } catch (InterruptedException ex) {
                       System.out.println(" ошмар - InterruptedException при попытке заморозить поток мотоциклов");
                       Logger.getLogger(MotoMan.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
                
               a = it.next();
               if(a instanceof Moto){
                   //System.out.println("нашЄл мотоцикл, начинаю двигать");
                   y = a.getY();
                   //speed = a.getSpeed();
                   
                   if(y < p_h)a.mooveY(); 
                   if(y >= p_h) a.setY(0);
               }
            
            }
            //***************************************************************
            
            try {
                    TimeUnit.MILLISECONDS.sleep(mother.CLOCK_RATE);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
                }
                Thread.yield();
            mother.repaint();
            //System.out.println("перерисовываю из MotoMan");
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
