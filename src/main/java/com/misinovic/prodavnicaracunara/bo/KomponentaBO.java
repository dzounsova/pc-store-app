/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.KomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.exception.ConstraintViolationException;
import com.misinovic.prodavnicaracunara.exception.NoResultException;
import com.misinovic.prodavnicaracunara.exception.NonUniqueResourceException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Validator;

/**
 *
 * @author Misinovic
 */
@Stateless
public class KomponentaBO {

    @EJB
    KomponentaDaoLocal komponentaDao;

    @Resource
    Validator validator;

    public void izmeniKomponentu(Komponenta komponenta) {
        komponentaDao.izmeniKomponentu(komponenta);
    }

    public void zapamtiKomponentu(Komponenta komponenta) throws NonUniqueResourceException {
        try {
            Komponenta k = komponentaDao.ucitajKomponentu(komponenta);
            throw new NonUniqueResourceException(k);
        } catch (NoResultException nre) {
            komponentaDao.zapamtiKomponentu(komponenta);
        }
    }

    public void obrisiKomponentu(Komponenta komponenta) {
        komponentaDao.obrisiKomponentu(komponenta);
    }

    public List<Komponenta> ucitajKomponente() {
        List<Komponenta> komponente = komponentaDao.ucitajKomponente();
        return komponente;
    }

    public Komponenta ucitajKomponentu(Komponenta komponenta) {
        return komponentaDao.ucitajKomponentu(komponenta.getId());
    }

    private void validirajKomponentu(Komponenta komponenta) throws ConstraintViolationException {
        validator.validate(komponenta).stream().forEach(violation -> {throw new ConstraintViolationException(violation);});
    }

    public void smanjiKolicinu(Komponenta komponenta, int kolicina) throws ConstraintViolationException {
        Komponenta k = ucitajKomponentu(komponenta);
        k.setKolicinaNaZalihi(k.getKolicinaNaZalihi() - kolicina);
        validirajKomponentu(k);
        izmeniKomponentu(k);
    }
}
