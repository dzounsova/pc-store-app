/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.utils;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Misinovic
 */
public class FacesUtils {

    // Dodavanje poruke u growl PF komponentu
    public static void addMessage(FacesMessage.Severity status, String header, String message) {
        final FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle("message", context.getViewRoot().getLocale());
        context.addMessage(null, new FacesMessage(status, bundle.getString(header), bundle.getString(message)));
    }

    // Redirekcija sa zadrzavanjem poruka
    public static void redirect(String resource) throws IOException {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.getFlash().setKeepMessages(true);
        externalContext.redirect(externalContext.getRequestContextPath() + "/faces/" + resource);
    }

    // Postavljanje u SessionMap.
    // Razlog: Parametar nece "preziveti" redirekciju
    public static void putParameterIntoSessionMap(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static Object getParameterFromSessionMap(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static void removeParameterFromSessionMap(String key) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(key);
    }
}
