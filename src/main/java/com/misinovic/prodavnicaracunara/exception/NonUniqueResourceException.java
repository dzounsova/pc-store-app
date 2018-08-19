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
public class NonUniqueResourceException extends Exception {

    public static final String MESSAGE_TEMPLATE = "Resource of type %s with the same business key, already exists.";

    public NonUniqueResourceException(Object resource) {
        super(String.format(MESSAGE_TEMPLATE, resource.getClass().getName()));
    }

}
