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
 * ����� ��������� ���������� ����� ���������� � ��������� ������ 
 * ��������� ����� ��������� (�������� ����� repaint() � �������������� 
 * ���������� � ��������� CLOCK_RATE ������� ���������� � Habitat)
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
            //System.out.println("�����������");
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
