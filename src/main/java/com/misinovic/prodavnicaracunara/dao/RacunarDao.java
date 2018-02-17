/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Misinovic
 */
@Stateless
public class RacunarDao implements RacunarDaoLocal {

    private static final Logger log = Logger.getLogger(RacunarDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Override
    public Racunar ucitajRacunar(int id) {
        return em.find(Racunar.class, id);
    }

    @Override
    public List<Racunar> ucitajRacunare() {
        List<Racunar> lista = em.createNamedQuery(Racunar.NamedQuery.findAll).getResultList();
        return lista;
    }

    @Override
    public void zapamtiRacunar(Racunar racunar) {
        log.log(Level.INFO, "zapamtiRacunar");
        em.persist(racunar);
    }

    @Override
    public void izmeniRacunar(Racunar racunar) {
        log.log(Level.INFO, "izmeniRacunar: ", racunar.getId());
        em.merge(racunar);
    }

    @Override
    public void obrisiRacunar(Racunar racunar) {
        log.log(Level.INFO, "obrisiRacunar: ", racunar.getId());
        em.remove(em.merge(racunar));
    }

}
