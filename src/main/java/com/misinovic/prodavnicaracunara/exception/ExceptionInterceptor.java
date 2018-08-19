/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.exception;

import java.io.Serializable;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Misinovic
 */
@Interceptor
public class ExceptionInterceptor implements Serializable {

    @AroundInvoke
    public Object handle(InvocationContext context) throws Exception {
        try {
            return context.proceed();
        } catch (javax.persistence.NoResultException e) {
            throw new NoResultException(e);
        }
    }

}
