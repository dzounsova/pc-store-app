/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.KomponentaBO;
import com.misinovic.prodavnicaracunara.bo.TipKomponenteBO;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Misinovic
 */
@Named(value = "kontrolerObradeKomponenti")
@ViewScoped
public class KontrolerObradeKomponenti implements Serializable {

    @Inject
    TipKomponenteBO tipKomponenteBO;

    @Inject
    KomponentaBO komponentaBO;

    private Komponenta komponenta;
    private List<TipKomponente> tipovi;
    private boolean edit;

    public KontrolerObradeKomponenti() {
    }

    @PostConstruct
    public void init() {
        inicijalizujTipove();
        inicijalizujKomponentu();
    }

    public void inicijalizujTipove() {
        tipovi = tipKomponenteBO.ucitajTipove();
    }

    public void inicijalizujKomponentu() {
        preuzmiParametar();
        if (komponenta != null) {
            edit = true;
            ukloniParametar();
        } else {
            edit = false;
            komponenta = new Komponenta();
        }
    }

    public void preuzmiParametar() {
        komponenta = (Komponenta) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("komponenta");
    }

    public void ukloniParametar() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove("komponenta");
    }

    public void ucitajPocetnuPoruku() {
        if (edit && komponenta != null) {
            ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("komponentaPronadjena")));
        }
    }

    public Komponenta getKomponenta() {
        return komponenta;
    }

    public List<TipKomponente> getTipovi() {
        return tipovi;
    }

    public boolean isEdit() {
        return edit;
    }

    public String zapamtiKomponentu() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (edit) {
            try {
                komponentaBO.izmeniKomponentu(komponenta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("komponentaIzmenjena")));
                return "komponente";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("komponentaNijeIzmenjena")));
                return "komponenta";
            }
        } else {
            try {
                komponentaBO.zapamtiKomponentu(komponenta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("komponentaZapamcena")));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("komponentaNijeZapamcena")));
            }
            return "komponenta";
        }
    }
}
