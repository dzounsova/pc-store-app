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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "tipkomponente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = TipKomponente.NamedQuery.findAll, query = "SELECT t FROM TipKomponente t"),
    @NamedQuery(name = TipKomponente.NamedQuery.findById, query = "SELECT t FROM TipKomponente t WHERE t.id = :id"),
    @NamedQuery(name = TipKomponente.NamedQuery.findByNaziv, query = "SELECT t FROM TipKomponente t WHERE t.naziv = :naziv")})
public class TipKomponente implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tipKomponenteID")
    private Integer id;
    @Size(max = 30)
    @Column(name = "naziv")
    private String naziv;

    public TipKomponente() {
    }

    public TipKomponente(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof TipKomponente)) {
            return false;
        }
        TipKomponente other = (TipKomponente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.TipKomponente[ id=" + id + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "TipKomponente.findAll";
        public static final String findById = "TipKomponente.findById";
        public static final String findByNaziv = "TipKomponente.findByNaziv";
    }

}
