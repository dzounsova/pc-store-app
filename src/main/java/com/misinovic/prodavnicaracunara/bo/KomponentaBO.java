/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.KomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Misinovic
 */
@Stateless
public class KomponentaBO {

    @EJB
    KomponentaDaoLocal komponentaDao;

    public void izmeniKomponentu(Komponenta komponenta) {
        komponentaDao.izmeniKomponentu(komponenta);
    }

    public void zapamtiKomponentu(Komponenta komponenta) {
        komponentaDao.zapamtiKomponentu(komponenta);
    }

    public void obrisiKomponentu(Komponenta komponenta) {
        komponentaDao.obrisiKomponentu(komponenta);
    }

    public List<Komponenta> ucitajKomponente() {
        List<Komponenta> komponente = komponentaDao.ucitajKomponente();
        return komponente;
    }

}
