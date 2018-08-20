/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.RacunarBO;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.IOException;
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
@Named
@ViewScoped
public class KontrolerPretrageRacunara implements Serializable {

    private static final Logger LOG = Logger.getLogger(KontrolerPretrageRacunara.class.getName());

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

    /**
     * Onemoguci izmenu, brisanje i detalje.
     *
     * @return true ukoliko racunar nije izabran
     */
    public boolean disableButtons() {
        return racunar == null;
    }

    public void ucitajFormuZaIzmenuRacunara() throws IOException {
        if (racunar != null) {
            FacesUtils.putParameterIntoSessionMap("racunar", racunar);
            FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunarPronadjen");
            FacesUtils.redirect("racunar.xhtml");
        } else {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunarNijeIzabran");
        }
    }

    public void obrisiRacunar() {
        if (racunar != null) {
            try {
                racunarBO.obrisiRacunar(racunar);
                // Brisanje racunara iz liste racunara kako bi imali azurne podatke bez dodatnog request-a
                racunari.remove(racunar);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunarObrisan");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "racunarNijeObrisan");
            }
        } else {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunarNijeIzabran");
        }
    }

}
