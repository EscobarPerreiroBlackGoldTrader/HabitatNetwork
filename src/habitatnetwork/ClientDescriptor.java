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
public class ClientDescriptor implements Serializable{ //класс для обмена с сервером

        private String master_id,master_name;
        
        public ClientDescriptor(String id,String name) {
            this.master_id = id;
            this.master_name = name;
        }

        public String getMaster_id() {
            return master_id;
        }

        public String getMaster_name() {
            return master_name;
        } 

        public void setMaster_id(String master_id) {
            this.master_id = master_id;
        }

        public void setMaster_name(String master_name) {
            this.master_name = master_name;
        }
        
    }
    
