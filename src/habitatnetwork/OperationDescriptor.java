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
public class OperationDescriptor implements Serializable{
    
    public static enum op {HANDSHAKE,EXCHANGE,GET_FRIENDS_LIST,BYE};
    private final op opcode;

    public OperationDescriptor(op opcode) {
        this.opcode = opcode;
    }

    public op getOpcode() {
        return opcode;
    }
    
}
