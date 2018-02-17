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
@Named(value = "kontrolerPretrageKomponenti")
@ViewScoped
public class KontrolerPretrageKomponenti implements Serializable {

    @Inject
    private TipKomponenteBO tipKomponenteBO;

    @Inject
    private KomponentaBO komponentaBO;

    private Komponenta komponenta;
    private List<Komponenta> komponente;
    private List<TipKomponente> tipovi;

    public KontrolerPretrageKomponenti() {
    }

    @PostConstruct
    public void init() {
        tipovi = tipKomponenteBO.ucitajTipove();
        komponente = komponentaBO.ucitajKomponente();
    }

    public Komponenta getKomponenta() {
        return komponenta;
    }

    public void setKomponenta(Komponenta komponenta) {
        this.komponenta = komponenta;
    }

    public List<Komponenta> getKomponente() {
        return komponente;
    }

    public List<TipKomponente> getTipovi() {
        return tipovi;
    }

    public boolean disableButtons() {
        if (komponenta == null) {
            return true;
        } else {
            return false;
        }
    }

    public String ucitajFormuZaIzmenuKomponente() {
        if (komponenta != null) {
            postaviParametar();
            return "komponenta";
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("upozorenje"), bundle.getString("komponentaNijeIzabrana")));
            return null;
        }
    }

    public void postaviParametar() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("komponenta", komponenta);
    }

    public void obrisiKomponentu() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (komponenta != null) {
            try {
                komponentaBO.obrisiKomponentu(komponenta);
                komponente.remove(komponenta);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("komponentaObrisana")));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("komponentaNijeObrisana")));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("upozorenje"), bundle.getString("komponentaNijeIzabrana")));
        }
    }

}
