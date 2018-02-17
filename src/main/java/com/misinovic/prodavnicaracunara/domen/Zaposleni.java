/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.domen;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misinovic
 */
@Entity
@Table(name = "zaposleni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = Zaposleni.NamedQuery.findAll, query = "SELECT z FROM Zaposleni z"),
    @NamedQuery(name = Zaposleni.NamedQuery.findById, query = "SELECT z FROM Zaposleni z WHERE z.id = :id"),
    @NamedQuery(name = Zaposleni.NamedQuery.findByUsername, query = "SELECT z FROM Zaposleni z WHERE z.username = :username"),
    @NamedQuery(name = Zaposleni.NamedQuery.findByPassword, query = "SELECT z FROM Zaposleni z WHERE z.password = :password"),
    @NamedQuery(name = Zaposleni.NamedQuery.findByIme, query = "SELECT z FROM Zaposleni z WHERE z.ime = :ime"),
    @NamedQuery(name = Zaposleni.NamedQuery.findByPrezime, query = "SELECT z FROM Zaposleni z WHERE z.prezime = :prezime"),
    @NamedQuery(name = Zaposleni.NamedQuery.findByUsernameAndPassword, query = "SELECT z FROM Zaposleni z WHERE z.username = :username AND z.password = :password")})
public class Zaposleni implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "zaposleniID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 15)
    @Column(name = "password")
    private String password;
    @Size(max = 30)
    @Column(name = "ime")
    private String ime;
    @Size(max = 30)
    @Column(name = "prezime")
    private String prezime;
    @Size(max = 500)
    @Column(name = "slika")
    private String slika;
    @OneToMany(mappedBy = "zaposleni")
    private List<Racunar> racunari;
    @OneToMany(mappedBy = "zaposleni")
    private List<Racun> racuni;

    public Zaposleni() {
    }

    public Zaposleni(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @XmlTransient
    public List<Racunar> getRacunari() {
        return racunari;
    }

    public void setRacunari(List<Racunar> racunari) {
        this.racunari = racunari;
    }

    @XmlTransient
    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Zaposleni)) {
            return false;
        }
        Zaposleni other = (Zaposleni) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.misinovic.prodavnicaracunara.entities.Zaposleni[ id=" + id + " ]";
    }

    public static class NamedQuery {

        public static final String findAll = "Zaposleni.findAll";
        public static final String findById = "Zaposleni.findById";
        public static final String findByUsername = "Zaposleni.findByUsername";
        public static final String findByPassword = "Zaposleni.findByPassword";
        public static final String findByIme = "Zaposleni.findByIme";
        public static final String findByPrezime = "Zaposleni.findByPrezime";
        public static final String findByUsernameAndPassword = "Zaposleni.findByUsernameAndPassword";
    }

}
