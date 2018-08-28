/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.exception;

import javax.ejb.ApplicationException;
import javax.validation.ConstraintViolation;

/**
 *
 * @author Misinovic
 */
@ApplicationException(rollback = true)
public class ConstraintViolationException extends RuntimeException {

    public static final String MESSAGE_TEMPLATE = "Constraint violation on entity: %s, property: %s, rule: %s";

    public ConstraintViolationException(ConstraintViolation constraintViolation) {
        super(String.format(MESSAGE_TEMPLATE, constraintViolation.getRootBean().getClass().getSimpleName(), 
                constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
    }

}
