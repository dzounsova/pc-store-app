/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Racun.NamedQuery.findAll, query = "SELECT r FROM Racun r"),
    @NamedQuery(name = Racun.NamedQuery.findById, query = "SELECT r FROM Racun r WHERE r.id = :id"),
    @NamedQuery(name = Racun.NamedQuery.findByUkupnaVrednost, query = "SELECT r FROM Racun r WHERE r.ukupnaVrednost = :ukupnaVrednost"),
    @NamedQuery(name = Racun.NamedQuery.findByDatum, query = "SELECT r FROM Racun r WHERE r.datum = :datum")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "racunID")
    private Integer id;
    @Column(name = "ukupnaVrednost")
    private Double ukupnaVrednost;
    @Column(name = "datum")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "stavkaRacunaPK.racun", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<StavkaRacuna> stavkeRacuna = new ArrayList<>();
    @JoinColumn(name = "zaposleniID", referencedColumnName = "zaposleniID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Zaposleni zaposleni;

    public Racun() {
    }

    public Racun(Integer racunID) {
        this.id = racunID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getUkupnaVrednost() {
        return ukupnaVrednost;
    }

    public void setUkupnaVrednost(Double ukupnaVrednost) {
        this.ukupnaVrednost = ukupnaVrednost;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @XmlTransient
    public List<StavkaRacuna> getStavkeRacuna() {
        return stavkeRacuna;
    }

    public void setStavkeRacuna(List<StavkaRacuna> stavkeRacuna) {
        this.stavkeRacuna = stavkeRacuna;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.Racun[ id=" + id + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "Racun.findAll";
        public static final String findById = "Racun.findById";
        public static final String findByUkupnaVrednost = "Racun.findByUkupnaVrednost";
        public static final String findByDatum = "Racun.findByDatum";
    }

}
