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
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "kontrolerObradeRacunara")
@ViewScoped
public class KontrolerObradeRacunara implements Serializable {

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
    private int kolicina = 1;
    private int zalihaRacunara = 0;

    public KontrolerObradeRacunara() {
    }

    @PostConstruct
    public void init() {
        tipovi = tipKomponenteBO.ucitajTipove();
        komponente = komponentaBO.ucitajKomponente();
        inicijalizujRacunar();
    }

    public void inicijalizujRacunar() {
        preuzmiParametar();
        if (racunar != null) {
            edit = true;
            zalihaRacunara = racunar.getKolicinaNaZalihi();
            ukloniParametar();
        } else {
            edit = false;
            racunar = new Racunar();
            zalihaRacunara = 0;
        }
    }

    public void preuzmiParametar() {
        racunar = (Racunar) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("racunar");
    }

    public void ukloniParametar() {
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove("racunar");
    }

    public void ucitajPocetnuPoruku() {
        if (edit && racunar != null) {
            ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("racunarPronadjen")));
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

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public boolean disableKolicina() {
        if (komponenta != null) {
            return racunarBO.jedinstvenaKomponenta(komponenta);
        }
        return false;
    }

    public double ukupnaVrednost() {
        double vrednost = 0.00;
        for (Ugradnja u : racunar.getUgradnje()) {
            vrednost += (u.getKomponenta().getProdajnaCena() * u.getKolicina());
        }
        return vrednost;
    }

    public void dodajUgradnju() {
        Ugradnja ugradnja = new Ugradnja();
        ugradnja.setRacunar(racunar);
        ugradnja.setKomponenta(komponenta);
        ugradnja.setKolicina(kolicina);
        ugradnja.setDatumUgradnje(new Date());
        try {
            racunarBO.dodajUgradnju(ugradnja);
        } catch (Exception ex) {
            ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), ex.getMessage()));
        }
    }

    public void ukloniUgradnju() {
        racunar.getUgradnje().remove(ugradnja);
    }

    public String zapamtiRacunar() {
        ResourceBundle bundle = ResourceBundle.getBundle("message", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (edit) {
            try {
                racunarBO.izmeniRacunar(racunar, zalihaRacunara);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("racunarIzmenjen")));
                return "racunari";
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("racunarNijeIzmenjen")));
                return "racunar";
            }
        } else {
            racunar.setZaposleni(kontrolerZaposlenih.getZaposleni());
            racunar.setProdajnaCena(ukupnaVrednost());
            try {
                racunarBO.zapamtiRacunar(racunar);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("info"), bundle.getString("racunarZapamcen")));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("greska"), bundle.getString("racunarNijeZapamcen")));
            }
            return "racunar";
        }
    }
}
