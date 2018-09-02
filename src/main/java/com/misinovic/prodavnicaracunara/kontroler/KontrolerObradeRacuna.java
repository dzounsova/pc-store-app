/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.kontroler;

import com.misinovic.prodavnicaracunara.bo.RacunBO;
import com.misinovic.prodavnicaracunara.bo.RacunarKomponentaBO;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Racun;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import com.misinovic.prodavnicaracunara.domen.StavkaRacuna;
import com.misinovic.prodavnicaracunara.exception.ConstraintViolationException;
import com.misinovic.prodavnicaracunara.exception.IllegalStateException;
import com.misinovic.prodavnicaracunara.utils.FacesUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Named(value = "kontrolerObradeRacuna")
@ViewScoped
public class KontrolerObradeRacuna implements Serializable {

    private static final Logger LOG = Logger.getLogger(KontrolerObradeRacuna.class.getName());

    @Inject
    KontrolerZaposlenih kontrolerZaposlenih;

    @Inject
    RacunarKomponentaBO racunarKomponentaBO;

    @Inject
    RacunBO racunBO;

    /**
     * Dummy vrednost koja predstavlja tip komponente - racunar
     */
    private static final String TIP_RACUNAR = "Računar";

    private List<RacunarKomponenta> racunariIkomponente;
    private Racun racun;
    private StavkaRacuna stavkaRacuna;
    private RacunarKomponenta racunarKomponenta;
    private int kolicina = 1;

    public KontrolerObradeRacuna() {
    }

    @PostConstruct
    public void init() {
        racunariIkomponente = racunarKomponentaBO.ucitajRaspoloziveRacunareIKomponente();
        racun = new Racun();
    }

    public List<RacunarKomponenta> getRacunariIkomponente() {
        return racunariIkomponente;
    }

    public void setRacunariIkomponente(List<RacunarKomponenta> racunariIkomponente) {
        this.racunariIkomponente = racunariIkomponente;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public StavkaRacuna getStavkaRacuna() {
        return stavkaRacuna;
    }

    public void setStavkaRacuna(StavkaRacuna stavkaRacuna) {
        this.stavkaRacuna = stavkaRacuna;
    }

    public RacunarKomponenta getRacunarKomponenta() {
        return racunarKomponenta;
    }

    public void setRacunarKomponenta(RacunarKomponenta racunarKomponenta) {
        this.racunarKomponenta = racunarKomponenta;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String vratiTip(RacunarKomponenta racunarKomponenta) {
        if (isRacunar(racunarKomponenta)) {
            return TIP_RACUNAR;
        } else {
            Komponenta komponenta = (Komponenta) racunarKomponenta;
            return komponenta.getTip().getNaziv();
        }
    }

    public boolean isRacunar(RacunarKomponenta racunarKomponenta) {
        return racunarKomponenta instanceof Racunar;
    }

    public double ukupnaVrednost() {
        return racun.getStavkeRacuna().stream()
                .map(StavkaRacuna::getUkupnaVrednost)
                .reduce(0.0, Double::sum);
    }

    private void proveriKolicinu() throws IllegalStateException {
        StavkaRacuna istaStavka = racun.getStavkeRacuna().stream()
                .filter(s -> s.getRacunarKomponenta().equals(racunarKomponenta))
                .findFirst().orElse(null);

        int dodataKolicina = istaStavka == null ? 0 : istaStavka.getKolicina();

        if (dodataKolicina + kolicina > racunarKomponenta.getKolicinaNaZalihi()) {
            throw new IllegalStateException();
        }
    }

    public void dodajStavku() {
        StavkaRacuna stavka = new StavkaRacuna();
        stavka.setRacun(racun);
        stavka.setRacunarKomponenta(racunarKomponenta);
        stavka.setKolicina(kolicina);
        stavka.setProdajnaCena(racunarKomponenta.getProdajnaCena());
        try {
            proveriKolicinu();
            racunBO.dodajStavku(stavka);
        } catch (IllegalStateException ise) {
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "stavkaNijeDodata");
        }

    }

    public void ukloniStavku() {
        racun.getStavkeRacuna().remove(stavkaRacuna);
    }

    private void postaviRedneBrojeve() {
        racun.getStavkeRacuna().forEach(s -> s.setRedniBroj(1 + racun.getStavkeRacuna().indexOf(s)));
    }

    public void zapamtiRacun() throws IOException {
        postaviRedneBrojeve();
        racun.setZaposleni(kontrolerZaposlenih.getZaposleni());
        racun.setUkupnaVrednost(ukupnaVrednost());
        racun.setDatum(new Date());
        try {
            racunBO.zapamtiRacun(racun);
            FacesUtils.addMessage(FacesMessage.SEVERITY_INFO, "info", "racunZapamcen");
            FacesUtils.redirect("racun.xhtml");
        } catch (ConstraintViolationException cve) {
            LOG.log(Level.WARNING, cve.getMessage());
            FacesUtils.addMessage(FacesMessage.SEVERITY_WARN, "upozorenje", "racunNijeZapamcenNemaZaliha");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
            FacesUtils.addMessage(FacesMessage.SEVERITY_ERROR, "greska", "racunNijeZapamcen");
            FacesUtils.redirect("racun.xhtml");
        }
    }

}
