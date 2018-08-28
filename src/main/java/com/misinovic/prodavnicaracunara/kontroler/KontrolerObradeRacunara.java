/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.KomponentaBO;
import com.misinovic.prodavnicaracunara.bo.RacunarBO;
import com.misinovic.prodavnicaracunara.bo.TipKomponenteBO;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import com.misinovic.prodavnicaracunara.domen.Ugradnja;
import com.misinovic.prodavnicaracunara.exception.ConstraintViolationException;
import com.misinovic.prodavnicaracunara.exception.NonUniqueResourceException;
import com.misinovic.prodavnicaracunara.exception.IllegalStateException;
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "kontrolerObradeRacunara")
@ViewScoped
public class KontrolerObradeRacunara implements Serializable {

    private static final Logger LOG = Logger.getLogger(KontrolerObradeRacunara.class.getName());

    @Inject
    KontrolerZaposlenih kontrolerZaposlenih;

    @Inject
    TipKomponenteBO tipKomponenteBO;

    @Inject
    KomponentaBO komponentaBO;

    @Inject
    RacunarBO racunarBO;

    private Racunar racunar;
    private List<TipKomponente> tipovi;
    private List<Komponenta> komponente;
    private Ugradnja ugradnja;
    private Komponenta komponenta;
    private boolean edit;
    private int zalihaRacunara = 0;

    public KontrolerObradeRacunara() {
    }

    @PostConstruct
    public void init() {
        inicijalizujTipove();
        inicijalizujKomponente();
        odrediRezim();
    }

    public void inicijalizujTipove() {
        tipovi = tipKomponenteBO.ucitajTipove();
    }

    public void inicijalizujKomponente() {
        komponente = komponentaBO.ucitajKomponente();
    }

    private void odrediRezim() {
        racunar = (Racunar) FacesUtils.getParameterFromSessionMap("racunar");
        if (racunar != null) {
            edit = true;
            zalihaRacunara = racunar.getKolicinaNaZalihi();
            FacesUtils.removeParameterFromSessionMap("racunar");
        } else {
            edit = false;
            racunar = new Racunar();
            zalihaRacunara = 0;
        }
    }

    public Racunar getRacunar() {
        return racunar;
    }

    public List<TipKomponente> getTipovi() {
        return tipovi;
    }

    public List<Komponenta> getKomponente() {
        return komponente;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public Ugradnja getUgradnja() {
        return ugradnja;
    }

    public void setUgradnja(Ugradnja ugradnja) {
        this.ugradnja = ugradnja;
    }

    public Komponenta getKomponenta() {
        return komponenta;
    }

    public void setKomponenta(Komponenta komponenta) {
        this.komponenta = komponenta;
    }

    public double ukupnaVrednost() {
        return racunar.getUgradnje().stream()
                .map(u -> u.getKomponenta().getProdajnaCena() * u.getKolicina())
                .reduce(0.0, Double::sum);
    }

    public void dodajUgradnju() {
        Ugradnja u = new Ugradnja();
        u.setRacunar(racunar);
        u.setKomponenta(komponenta);
        u.setDatumUgradnje(new Date());
        try {
            racunarBO.dodajUgradnju(u);
        } catch (NonUniqueResourceException nure) {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "komponentaIstogTipaVecPostoji");
        }
    }

    public void ukloniUgradnju() {
        racunar.getUgradnje().remove(ugradnja);
    }

    public void zapamtiRacunar() throws IOException {
        if (edit) {
            try {
                racunarBO.izmeniRacunar(racunar, zalihaRacunara);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunarIzmenjen");
                FacesUtils.redirect("racunari.xhtml");
            } catch (IOException e) {
                LOG.log(Level.SEVERE, e.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "racunarNijeIzmenjen");
                FacesUtils.redirect("racunar.xhtml");
            }
        } else {
            racunar.setZaposleni(kontrolerZaposlenih.getZaposleni());
            racunar.setProdajnaCena(ukupnaVrednost());
            try {
                racunarBO.validirajRacunar(racunar);
                racunarBO.zapamtiRacunar(racunar);
                FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunarZapamcen");
                FacesUtils.redirect("racunar.xhtml");
            } catch (IllegalStateException ise) {
                LOG.log(Level.WARNING, ise.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunarNijeZapamcenFaleKomponente");
            } catch (ConstraintViolationException cve) {
                LOG.log(Level.WARNING, cve.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunarNijeZapamcenNemaZaliha");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, e.getMessage());
                FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "racunarNijeZapamcen");
                FacesUtils.redirect("racunar.xhtml");
            }
        }
    }
}
