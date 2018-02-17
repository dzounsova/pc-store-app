/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "stavkaracuna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = StavkaRacuna.NamedQuery.findAll, query = "SELECT s FROM StavkaRacuna s"),
    @NamedQuery(name = StavkaRacuna.NamedQuery.findByRacun, query = "SELECT s FROM StavkaRacuna s WHERE s.stavkaRacunaPK.racun.id = :id"),
    @NamedQuery(name = StavkaRacuna.NamedQuery.findByRedniBroj, query = "SELECT s FROM StavkaRacuna s WHERE s.stavkaRacunaPK.redniBroj = :redniBroj"),
    @NamedQuery(name = StavkaRacuna.NamedQuery.findByKolicina, query = "SELECT s FROM StavkaRacuna s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = StavkaRacuna.NamedQuery.findByProdajnaCena, query = "SELECT s FROM StavkaRacuna s WHERE s.prodajnaCena = :prodajnaCena"),
    @NamedQuery(name = StavkaRacuna.NamedQuery.findByUkupnaVrednost, query = "SELECT s FROM StavkaRacuna s WHERE s.ukupnaVrednost = :ukupnaVrednost")})
@AssociationOverride(name = "stavkaRacunaPK.racun", joinColumns = @JoinColumn(name = "racunID"))
public class StavkaRacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected StavkaRacunaPK stavkaRacunaPK = new StavkaRacunaPK();
    @Column(name = "kolicina")
    private Integer kolicina;
    @Column(name = "prodajnaCena")
    private Double prodajnaCena;
    @Column(name = "ukupnaVrednost")
    private Double ukupnaVrednost;

    @Transient
    private Racun racun;
    @JoinColumn(name = "racunarKomponentaID", referencedColumnName = "racunarKomponentaID")
    @ManyToOne
    private RacunarKomponenta racunarKomponenta;

    public StavkaRacuna() {
    }

    public StavkaRacuna(StavkaRacunaPK stavkaRacunaPK) {
        this.stavkaRacunaPK = stavkaRacunaPK;
    }

    public StavkaRacunaPK getStavkaRacunaPK() {
        return stavkaRacunaPK;
    }

    public void setStavkaRacunaPK(StavkaRacunaPK stavkaRacunaPK) {
        this.stavkaRacunaPK = stavkaRacunaPK;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Double getProdajnaCena() {
        return prodajnaCena;
    }

    public void setProdajnaCena(Double prodajnaCena) {
        this.prodajnaCena = prodajnaCena;
    }

    public Double getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(Double ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }

    public Racun getRacun() {
        return getStavkaRacunaPK().getRacun();
    }

    public void setRacun(Racun racun) {
        getStavkaRacunaPK().setRacun(racun);
    }

    public int getRedniBroj() {
        return getStavkaRacunaPK().getRedniBroj();
    }

    public void setRedniBroj(int redniBroj) {
        getStavkaRacunaPK().setRedniBroj(redniBroj);
    }

    public RacunarKomponenta getRacunarKomponenta() {
        return racunarKomponenta;
    }

    public void setRacunarKomponenta(RacunarKomponenta racunarKomponenta) {
        this.racunarKomponenta = racunarKomponenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stavkaRacunaPK != null ? stavkaRacunaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StavkaRacuna)) {
            return false;
        }
        StavkaRacuna other = (StavkaRacuna) object;
        if ((this.stavkaRacunaPK == null && other.stavkaRacunaPK != null) || (this.stavkaRacunaPK != null && !this.stavkaRacunaPK.equals(other.stavkaRacunaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.StavkaRacuna[ stavkaRacunaPK=" + stavkaRacunaPK + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "StavkaRacuna.findAll";
        public static final String findByRacun = "StavkaRacuna.findByRacun";
        public static final String findByRedniBroj = "StavkaRacuna.findByRedniBroj";
        public static final String findByKolicina = "StavkaRacuna.findByKolicina";
        public static final String findByProdajnaCena = "StavkaRacuna.findByProdajnaCena";
        public static final String findByUkupnaVrednost = "StavkaRacuna.findByUkupnaVrednost";
    }

}
