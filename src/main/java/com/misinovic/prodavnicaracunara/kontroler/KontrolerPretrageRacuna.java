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
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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

    private static final Logger LOG = Logger.getLogger(KontrolerPretrageRacuna.class.getName());

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
        return racun == null;
    }

    public void obrisiRacun() {
        if (racun != null) {
            try {
                racunBO.obrisiRacun(racun);
                // Brisanje racuna iz liste racuna kako bi imali azurne podatke bez dodatnog request-a
                racuni.remove(racun);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunObrisan");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "racunNijeObrisan");
            }
        } else {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunNijeIzabran");
        }
    }

}
