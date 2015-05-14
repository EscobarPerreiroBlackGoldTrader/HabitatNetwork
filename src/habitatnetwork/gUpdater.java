/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iUser
 *  ласс созданный специально чтобы выполн€€сь в отдельном потоке 
 * обновл€ть экран программы (вызывать метод repaint() с периодичностью 
 * хран€щейс€ в константе CLOCK_RATE котора€ определена в Habitat)
 */
public class gUpdater implements Runnable/*extends TimerTask*/{
    Habitat mother;
    
    gUpdater(Habitat  maintread){
        this.mother = maintread;
    }
    
    @Override
    public void run(){
        while(true){
            mother.repaint();
            //System.out.println("перерисовал");
            try {
                TimeUnit.MILLISECONDS.sleep(mother.CLOCK_RATE);
            } catch (InterruptedException ex) {
                Logger.getLogger(gUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread.yield();
            //++mainT.counter;
        }
    }
    
}
