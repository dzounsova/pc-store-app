/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Misinovic
 */
@ApplicationException
public class IllegalStateException extends RuntimeException{

    public IllegalStateException() {
        super();
    }
    
    public IllegalStateException(String message) {
        super(message);
    }
    
}
