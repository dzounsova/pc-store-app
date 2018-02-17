/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "komponenta")
@PrimaryKeyJoinColumn(name = "komponentaID", referencedColumnName = "racunarKomponentaID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Komponenta.NamedQuery.findAll, query = "SELECT k FROM Komponenta k"),
    @NamedQuery(name = Komponenta.NamedQuery.findById, query = "SELECT k FROM Komponenta k WHERE k.id = :id"),
    @NamedQuery(name = Komponenta.NamedQuery.findByProizvodjac, query = "SELECT k FROM Komponenta k WHERE k.proizvodjac = :proizvodjac"),
    @NamedQuery(name = Komponenta.NamedQuery.smanjiKolicinu, query = "UPDATE Komponenta k SET k.kolicinaNaZalihi = k.kolicinaNaZalihi - :kolicina WHERE k.id = :id")})
public class Komponenta extends RacunarKomponenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 30)
    @Column(name = "proizvodjac")
    private String proizvodjac;
    @JoinColumn(name = "tipKomponenteID", referencedColumnName = "tipKomponenteID")
    @ManyToOne(fetch = FetchType.EAGER)
    private TipKomponente tip;

    public Komponenta() {
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public TipKomponente getTip() {
        return tip;
    }

    public void setTip(TipKomponente tip) {
        this.tip = tip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Komponenta)) {
            return false;
        }
        Komponenta other = (Komponenta) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.Komponenta[ komponentaID=" + getId() + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "Komponenta.findAll";
        public static final String findById = "Komponenta.findById";
        public static final String findByProizvodjac = "Komponenta.findByProizvodjac";
        public static final String smanjiKolicinu = "Komponenta.smanjiKolicinu";
    }
}
