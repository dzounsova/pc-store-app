/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.dao.ZaposleniDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Zaposleni;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Misinovic
 */
@Named(value = "kontrolerZaposlenih")
@SessionScoped
public class KontrolerZaposlenih implements Serializable {

    private String username;
    private String password;
    private Zaposleni zaposleni;
    private boolean logoutButtonRendered;

    @Inject
    ZaposleniDaoLocal zaposleniDao;

    public KontrolerZaposlenih() {
        logoutButtonRendered = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public boolean isLogoutButtonRendered() {
        return logoutButtonRendered;
    }

    public String ucitajZaposlenog() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        zaposleni = zaposleniDao.ucitajZaposlenog(username, password);
        if (zaposleni != null) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("zaposleni", zaposleni);
            return "template";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("zaposleniNePostoji")));
            return "login";
        }
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        return "login";
    }

    public void userClicked() {
        if (isLogoutButtonRendered()) {
            logoutButtonRendered = false;
        } else {
            logoutButtonRendered = true;
        }
    }
}
