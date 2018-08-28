/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Ugradnja;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.NoResultException;

/**
 *
 * @author Misinovic
 */
@Local
public interface KomponentaDaoLocal {

    public Komponenta ucitajKomponentu(int id);

    public Komponenta ucitajKomponentu(Komponenta komponenta) throws NoResultException;

    public List<Komponenta> ucitajKomponente();

    public void zapamtiKomponentu(Komponenta komponenta);

    public void izmeniKomponentu(Komponenta komponenta);

    public void obrisiKomponentu(Komponenta komponenta);

}
