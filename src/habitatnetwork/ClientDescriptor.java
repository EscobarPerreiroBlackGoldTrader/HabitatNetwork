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
public class ClientDescriptor implements Serializable, Comparable { //класс для обмена с сервером

        private final String master_id;
        private final String master_name;
        private final int wellcome_listen_port;
        
        public ClientDescriptor(String id,String name,int wellcome_listen_port) {
            this.master_id = id;
            this.master_name = name;
            this.wellcome_listen_port = wellcome_listen_port;
        }

        public String getMaster_id() {
            return master_id;
        }

        public String getMaster_name() {
            return master_name;
        } 

        public int getWellcome_listen_port() {
            return wellcome_listen_port;
        }

        
        
    @Override
    public int compareTo(Object o) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        if( Long.parseLong(this.master_id) == Long.parseLong(((ClientDescriptor)o).getMaster_id()) ){
            return 0;
        }else if(Long.parseLong(this.master_id) > Long.parseLong(((ClientDescriptor)o).getMaster_id())){
            return 1;
        }else{
            return -1;
        }
    }
        
    }
    
