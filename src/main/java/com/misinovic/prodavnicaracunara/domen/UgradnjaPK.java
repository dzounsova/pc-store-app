/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Misinovic
 */
@Embeddable
public class UgradnjaPK implements Serializable {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Racunar racunar;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Komponenta komponenta;

    public UgradnjaPK() {
    }

    public Racunar getRacunar() {
        return racunar;
    }

    public void setRacunar(Racunar racunar) {
        this.racunar = racunar;
    }

    public Komponenta getKomponenta() {
        return komponenta;
    }

    public void setKomponenta(Komponenta komponenta) {
        this.komponenta = komponenta;
    }

    @Override
    public int hashCode() {
        int hash;
        hash = (racunar != null ? racunar.hashCode() : 0);
        hash = 31 * hash + (komponenta != null ? komponenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UgradnjaPK)) {
            return false;
        }
        UgradnjaPK other = (UgradnjaPK) object;
        if (!this.racunar.equals(other.racunar)) {
            return false;
        }
        if (!this.komponenta.equals(other.komponenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.UgradnjaPK[ racunar=" + racunar + ", komponenta=" + komponenta + " ]";
    }

}
