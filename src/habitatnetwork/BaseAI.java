/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package habitatnetwork;

import java.io.Serializable;

/**
 *
 * @author iUser
 */
public abstract class BaseAI implements IBehaviour/*, Runnable*/, Serializable {
   private int x;
   private int y; 
    
    //boolean paused = false;
    /**
     * ��������� �� ���������� ������������.
     * ���������, ������� ������ �� ����� �������� � NotSerializableException
     * ��� ������� ������������ lst
     * ������� ��������� ���������!
     */
    
    /*private*/ int speed;
    
    //boolean going = true;
    /**
     *��������� �� ���������� ������������
     */
    
    transient Habitat parent; 
    
    public BaseAI(Habitat parrentObj){ // �����������
        this.speed = 1;
        this.parent = parrentObj;
    }
    
    public BaseAI(Habitat parrentObj, int speed){
        this.speed = speed;
        this.parent = parrentObj;
    }
    
    /*public void setSpeed(int speed){this.speed = speed;}
    public int getSpeed(){return this.speed;}*/
    
//    @Override
//    public abstract void run(); // �������������� ��� ���������� ������
    
    
    public int mooveX(int speed){
        this.x += speed;
        return x;
    }
    
    public int mooveX(){
        this.x += this.speed;
        return x;
    }
    
    public int mooveY(int speed){
        this.y += speed;
        return y;
    }
    
    public int mooveY(){
        this.y += this.speed;
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
    
//    public synchronized void setPaused() 
//            throws InterruptedException{
//        paused = true;
//        /*while(paused)*/ Thread.sleep(2000);
//    }
// 
////==============================================================================
////����� ��� ����������� ��� �������� ������� ����� ������������� � �������� � �����
//private void writeObject(ObjectOutputStream oos) throws IOException {
//    
//    //oos.writeObject(lst);
//    System.out.println("�������� ������, ������������, ����� x");
//    oos.write(x);
//    System.out.println("����� y");
//    oos.write(y);
//    
//}
//
//private void readObject(ObjectInputStream ois) throws IOException, FileNotFoundException {
//    System.out.println("�������� ������, ������������, ������ x");
//    x = (int)ois.readInt();
//    System.out.println("������ y");
//    y = (int)ois.readInt();  
//
//}
////==============================================================================
//  
}
