/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "ugradnja")
@NamedQueries({
    @NamedQuery(name = Ugradnja.NamedQuery.findAll, query = "SELECT u FROM Ugradnja u"),
    @NamedQuery(name = Ugradnja.NamedQuery.findByRacunar, query = "SELECT u FROM Ugradnja u WHERE u.ugradnjaPK.racunar.id = :id"),
    @NamedQuery(name = Ugradnja.NamedQuery.findByKomponenta, query = "SELECT u FROM Ugradnja u WHERE u.ugradnjaPK.komponenta.id = :id"),
    @NamedQuery(name = Ugradnja.NamedQuery.findByDatumUgradnje, query = "SELECT u FROM Ugradnja u WHERE u.datumUgradnje = :datumUgradnje")})
@AssociationOverrides({
    @AssociationOverride(name = "ugradnjaPK.racunar",
            joinColumns = @JoinColumn(name = "racunarID")),
    @AssociationOverride(name = "ugradnjaPK.komponenta",
            joinColumns = @JoinColumn(name = "komponentaID"))})
public class Ugradnja implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected UgradnjaPK ugradnjaPK = new UgradnjaPK();
    @Column(name = "datumUgradnje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumUgradnje;
    @Column(name = "kolicina")
    private Integer kolicina = 1;
    @Transient
    private Racunar racunar;
    @Transient
    private Komponenta komponenta;

    public Ugradnja() {
    }

    public Ugradnja(UgradnjaPK ugradnjaPK) {
        this.ugradnjaPK = ugradnjaPK;
    }

    public UgradnjaPK getUgradnjaPK() {
        return ugradnjaPK;
    }

    public void setUgradnjaPK(UgradnjaPK ugradnjaPK) {
        this.ugradnjaPK = ugradnjaPK;
    }

    public Date getDatumUgradnje() {
        return datumUgradnje;
    }

    public void setDatumUgradnje(Date datumUgradnje) {
        this.datumUgradnje = datumUgradnje;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Racunar getRacunar() {
        return getUgradnjaPK().getRacunar();
    }

    public void setRacunar(Racunar racunar) {
        getUgradnjaPK().setRacunar(racunar);
    }

    public Komponenta getKomponenta() {
        return getUgradnjaPK().getKomponenta();
    }

    public void setKomponenta(Komponenta komponenta) {
        getUgradnjaPK().setKomponenta(komponenta);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ugradnjaPK != null ? ugradnjaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ugradnja)) {
            return false;
        }
        Ugradnja other = (Ugradnja) object;
        if ((this.ugradnjaPK == null && other.ugradnjaPK != null) || (this.ugradnjaPK != null && !this.ugradnjaPK.equals(other.ugradnjaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.Ugradnja[ ugradnjaPK=" + ugradnjaPK + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "Ugradnja.findAll";
        public static final String findByRacunar = "Ugradnja.findByRacunar";
        public static final String findByKomponenta = "Ugradnja.findByKomponenta";
        public static final String findByDatumUgradnje = "Ugradnja.findByDatumUgradnje";
    }

}
