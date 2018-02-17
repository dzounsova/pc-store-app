/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.RacunarBO;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Misinovic
 */
@Named
@ViewScoped
public class KontrolerPretrageRacunara implements Serializable {

    @Inject
    private RacunarBO racunarBO;

    private Racunar racunar;
    private List<Racunar> racunari;

    public KontrolerPretrageRacunara() {
    }

    @PostConstruct
    public void init() {
        racunari = racunarBO.ucitajRacunare();
    }

    public Racunar getRacunar() {
        return racunar;
    }

    public void setRacunar(Racunar racunar) {
        this.racunar = racunar;
    }

    public List<Racunar> getRacunari() {
        return racunari;
    }

    public boolean disableButtons() {
        if (racunar == null) {
            return true;
        } else {
            return false;
        }
    }

    public String ucitajFormuZaIzmenuRacunara() {
        if (racunar != null) {
            postaviParametar();
            return "racunar";
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("upozorenje"), bundle.getString("racunarNijeIzabran")));
            return null;
        }
    }

    private void postaviParametar() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("racunar", racunar);
    }

    public void obrisiRacunar() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (racunar != null) {
            try {
                racunarBO.obrisiRacunar(racunar);
                racunari.remove(racunar);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("racunarObrisan")));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("racunarNijeObrisan")));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("upozorenje"), bundle.getString("racunarNijeIzabran")));
        }
    }

}
