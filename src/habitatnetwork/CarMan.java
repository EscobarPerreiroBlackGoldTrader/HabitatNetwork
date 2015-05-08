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
     *  ширина главного окна
     */
    private int p_w; 
    
    /**
     *  переменная необходима для переключения в состояния замри/отомр
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
                    System.out.println("Кошмар - InterruptedException при попытке заморозить поток машин (не в цикле просмотра листа)");
                    Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //-------------------------------------
            
            setLst(mother.lst);//иначе обновить ссылку на lst не получается 
                               //необходимо обновлять ссылку на лист, 
                               //иначе после загрузки листа будет указывать 
                               //на старый лист и не увидит загруженного
            BaseAI a;
            int x,speed; 
            p_w = mother.getWidth();
            
            //**************************************************************
            for(Iterator<BaseAI> it = lst.iterator();it.hasNext();){
                //System.out.println("начинаю искать машину");
                if(mother.isPausedCar()){
                    freezeUP = true;
                    try {
                        freeze();
                    } catch (InterruptedException ex) {
                        System.out.println("Кошмар - InterruptedException при попытке заморозить поток машин");
                        Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
               a = it.next();
               if(a instanceof Car){
                   //System.out.println("нашёл машину, начинаю двигать");
                   x = a.getX();
                   if(x < p_w){
                       a.mooveX();
                       //System.out.println("сдвинул машину");
                   }
                   if(x >= p_w)a.setX(0); 
               }
               //System.out.println("закончил одну итерацию поиска машин");
                    
            }// end of for
            //**************************************************************
            
            try {//ляг поспи
                    TimeUnit.MILLISECONDS.sleep(mother.CLOCK_RATE);
            } catch (InterruptedException ex) {
                    System.out.println("не дают поспать перед поиском следующей партии машин");
                    Logger.getLogger(CarMan.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread.yield();
            mother.repaint();
            //System.out.println("перерисовываю из CarMan");
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
