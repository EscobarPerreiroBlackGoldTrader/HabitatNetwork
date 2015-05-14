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
public class Car extends BaseAI implements Serializable{

    public Car(Habitat obj){
        super(obj);
    }
    
    @Override
    public String Beep() {
      return "Im a Car";
    }

}
