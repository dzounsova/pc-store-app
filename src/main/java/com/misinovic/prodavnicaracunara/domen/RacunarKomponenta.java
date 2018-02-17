/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "racunarkomponenta")
@Inheritance(strategy = InheritanceType.JOINED)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findAll, query = "SELECT r FROM RacunarKomponenta r"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findByRacunarKomponentaID, query = "SELECT r FROM RacunarKomponenta r WHERE r.id = :id"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findByNaziv, query = "SELECT r FROM RacunarKomponenta r WHERE r.naziv = :naziv"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findByProdajnaCena, query = "SELECT r FROM RacunarKomponenta r WHERE r.prodajnaCena = :prodajnaCena"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findBySpecifikacija, query = "SELECT r FROM RacunarKomponenta r WHERE r.specifikacija = :specifikacija"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.findByKolicinaNaZalihi, query = "SELECT r FROM RacunarKomponenta r WHERE r.kolicinaNaZalihi = :kolicinaNaZalihi"),
    @NamedQuery(name = RacunarKomponenta.NamedQuery.smanjiKolicinu, query = "UPDATE RacunarKomponenta r SET r.kolicinaNaZalihi = r.kolicinaNaZalihi - :kolicina WHERE r.id = :id")})
public class RacunarKomponenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "racunarKomponentaID")
    private Integer id;
    @Size(max = 250)
    @Column(name = "naziv")
    private String naziv;
    @Column(name = "prodajnaCena")
    private Double prodajnaCena;
    @Size(max = 500)
    @Column(name = "specifikacija")
    private String specifikacija;
    @Min(0)
    @Column(name = "kolicinaNaZalihi")
    private Integer kolicinaNaZalihi;

    public RacunarKomponenta() {
    }

    public RacunarKomponenta(Integer racunarKomponentaID) {
        this.id = racunarKomponentaID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getProdajnaCena() {
        return prodajnaCena;
    }

    public void setProdajnaCena(Double prodajnaCena) {
        this.prodajnaCena = prodajnaCena;
    }

    public String getSpecifikacija() {
        return specifikacija;
    }

    public void setSpecifikacija(String specifikacija) {
        this.specifikacija = specifikacija;
    }

    public Integer getKolicinaNaZalihi() {
        return kolicinaNaZalihi;
    }

    public void setKolicinaNaZalihi(Integer kolicinaNaZalihi) {
        this.kolicinaNaZalihi = kolicinaNaZalihi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RacunarKomponenta)) {
            return false;
        }
        RacunarKomponenta other = (RacunarKomponenta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.RacunarKomponenta[ racunarKomponentaID=" + id + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "RacunarKomponenta.findAll";
        public static final String findByRacunarKomponentaID = "RacunarKomponenta.findByRacunarKomponentaID";
        public static final String findByNaziv = "RacunarKomponenta.findByNaziv";
        public static final String findByProdajnaCena = "RacunarKomponenta.findByProdajnaCena";
        public static final String findBySpecifikacija = "RacunarKomponenta.findBySpecifikacija";
        public static final String findByKolicinaNaZalihi = "RacunarKomponenta.findByKolicinaNaZalihi";
        public static final String smanjiKolicinu = "RacunarKomponenta.smanjiKolicinu";
    }

}
