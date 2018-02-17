/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Ugradnja;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

/**
 *
 * @author Misinovic
 */
@Stateless
public class KomponentaDao implements KomponentaDaoLocal {

    private static final Logger log = Logger.getLogger(KomponentaDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Resource
    Validator validator;

    @Override
    public Komponenta ucitajKomponentu(int id) {
        log.log(Level.INFO, "ucitajKomponentu: ", id);
        return em.find(Komponenta.class, id);
    }

    @Override
    public List<Komponenta> ucitajKomponente() {
        List<Komponenta> lista = em.createNamedQuery(Komponenta.NamedQuery.findAll).getResultList();
        return lista;
    }

    @Override
    public void zapamtiKomponentu(Komponenta komponenta) {
        log.log(Level.INFO, "zapamtiKomponentu");
        em.persist(komponenta);
    }

    @Override
    public void izmeniKomponentu(Komponenta komponenta) {
        log.log(Level.INFO, "izmeniKomponentu: ", komponenta.getId());
        em.merge(komponenta);
    }

    @Override
    public void obrisiKomponentu(Komponenta komponenta) {
        log.log(Level.INFO, "obrisiKomponentu: ", komponenta.getId());
        em.remove(em.merge(komponenta));
    }

    @Override
    public void smanjiKolicinu(Ugradnja ugradnja) {
        log.log(Level.INFO, "smanjiKolicinu: komponenta = {0}, kolicina = {1}", new Object[]{ugradnja.getKomponenta().getId(), ugradnja.getKolicina() * ugradnja.getRacunar().getKolicinaNaZalihi()});
        Query q = em.createNamedQuery(Komponenta.NamedQuery.smanjiKolicinu).setParameter("kolicina", ugradnja.getKolicina() * ugradnja.getRacunar().getKolicinaNaZalihi()).setParameter("id", ugradnja.getKomponenta().getId());
        q.executeUpdate();
        Komponenta k = ucitajKomponentu(ugradnja.getKomponenta().getId());
        Set<ConstraintViolation<Komponenta>> violations = validator.validate(k);
        if (violations.size() > 0) {
            log.log(Level.INFO, "smanjiKolicinu: Constraint violation");
            em.getTransaction().rollback();
        }
    }

}
