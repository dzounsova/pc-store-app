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
import com.misinovic.prodavnicaracunara.exception.NonUniqueResourceException;
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
        odrediRezim();
    }

    public void inicijalizujTipove() {
        tipovi = tipKomponenteBO.ucitajTipove();
    }

    // Stranica moze imati dva rezima: unos ili izmenu komponente
    public void odrediRezim() {
        komponenta = (Komponenta) FacesUtils.getParameterFromSessionMap("komponenta");
        if (komponenta != null) {
            edit = true;
            FacesUtils.removeParameterFromSessionMap("komponenta");
        } else {
            edit = false;
            komponenta = new Komponenta();
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

    public void zapamtiKomponentu() throws IOException {
        if (edit) {
            try {
                komponentaBO.izmeniKomponentu(komponenta);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "komponentaIzmenjena");
                FacesUtils.redirect("komponente.xhtml");
            } catch (IOException e) {
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "komponentaNijeIzmenjena");
                FacesUtils.redirect("komponenta.xhtml");
            }
        } else {
            try {
                komponentaBO.zapamtiKomponentu(komponenta);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "komponentaZapamcena");
            } catch (NonUniqueResourceException nure) {
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "komponentaVecPostoji");
            } catch (Exception e) {
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "komponentaNijeZapamcena");
            }
            FacesUtils.redirect("komponenta.xhtml");
        }
    }
}
