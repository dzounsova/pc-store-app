/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "racunar")
@PrimaryKeyJoinColumn(name = "racunarID", referencedColumnName = "racunarKomponentaID")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Racunar.NamedQuery.findAll, query = "SELECT r FROM Racunar r"),
    @NamedQuery(name = Racunar.NamedQuery.findById, query = "SELECT r FROM Racunar r WHERE r.id = :id")})
public class Racunar extends RacunarKomponenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "zaposleniID", referencedColumnName = "zaposleniID")
    @ManyToOne(fetch = FetchType.EAGER)
    private Zaposleni zaposleni;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "ugradnjaPK.racunar", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Ugradnja> ugradnje = new ArrayList<>();

    public Racunar() {
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public List<Ugradnja> getUgradnje() {
        return ugradnje;
    }

    public void setUgradnje(List<Ugradnja> ugradnje) {
        this.ugradnje = ugradnje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Racunar)) {
            return false;
        }
        Racunar other = (Racunar) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.Racunar[ racunarID=" + getId() + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "Racunar.findAll";
        public static final String findById = "Racunar.findById";
    }

}
