/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.RacunBO;
import com.misinovic.prodavnicaracunara.bo.RacunarKomponentaBO;
import com.misinovic.prodavnicaracunara.domen.Racun;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
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
@Named(value = "kontrolerPretrageRacuna")
@ViewScoped
public class KontrolerPretrageRacuna implements Serializable {

    @Inject
    RacunarKomponentaBO racunarKomponentaBO;

    @Inject
    RacunBO racunBO;

    private List<RacunarKomponenta> racunariIKomponente;
    private List<Racun> racuni;
    private Racun racun;

    public KontrolerPretrageRacuna() {
    }

    @PostConstruct
    public void init() {
        racuni = racunBO.ucitajRacune();
    }

    public List<RacunarKomponenta> getRacunariIKomponente() {
        return racunariIKomponente;
    }

    public void setRacunariIKomponente(List<RacunarKomponenta> racunariIKomponente) {
        this.racunariIKomponente = racunariIKomponente;
    }

    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public boolean disableButtons() {
        if (racun == null) {
            return true;
        } else {
            return false;
        }
    }

    public void obrisiRacun() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (racun != null) {
            try {
                racunBO.obrisiRacun(racun);
                racuni.remove(racun);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("racunObrisan")));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("racunNijeObrisan")));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, bundle.getString("upozorenje"), bundle.getString("racunNijeIzabran")));
        }
    }

}
