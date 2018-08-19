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
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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

    private static final Logger LOG = Logger.getLogger(KontrolerPretrageKomponenti.class.getName());
    
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

    // Ukoliko komponenta nije izabrana onemoguci izmenu, brisanje i detalje.
    public boolean disableButtons() {
        return komponenta == null;
    }

    public void ucitajFormuZaIzmenuKomponente() throws IOException {
        if (komponenta != null) {
            FacesUtils.putParameterIntoSessionMap("komponenta", komponenta);
            FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info",
                    "komponentaPronadjena");
            FacesUtils.redirect("komponenta.xhtml");
        } else {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje",
                    "komponentaNijeIzabrana");
        }
    }

    public void obrisiKomponentu() {
        if (komponenta != null) {
            try {
                komponentaBO.obrisiKomponentu(komponenta);
                //brisanje komponente iz liste komponenti kako bi imali azurne podatke bez dodatnog request-a
                komponente.remove(komponenta);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "komponentaObrisana");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "komponentaNijeObrisana");
            }
        } else {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "komponentaNijeIzabrana");
        }
    }

}
