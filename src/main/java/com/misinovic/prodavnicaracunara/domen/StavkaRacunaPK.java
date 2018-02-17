/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Misinovic
 */
@Embeddable
public class StavkaRacunaPK implements Serializable {

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Racun racun;
    @Basic(optional = false)
    @NotNull
    @Column(name = "redniBroj")
    private int redniBroj;

    public StavkaRacunaPK() {
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    @Override
    public int hashCode() {
        int hash;
        hash = (racun != null ? racun.hashCode() : 0);
        hash = 31 * hash + redniBroj;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StavkaRacunaPK)) {
            return false;
        }
        StavkaRacunaPK other = (StavkaRacunaPK) obj;
        if (this.racun != other.racun) {
            return false;
        }
        if (this.redniBroj != other.redniBroj) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.StavkaRacunaPK[ racun=" + racun + ", redniBroj=" + redniBroj + " ]";
    }

}
