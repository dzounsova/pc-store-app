/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Racun;
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
public class RacunDao implements RacunDaoLocal {

    private static final Logger LOG = Logger.getLogger(RacunDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Override
    public List<Racun> ucitajRacune() {
        List<Racun> lista = em.createNamedQuery(Racun.NamedQuery.findAll).getResultList();
        return lista;
    }

    @Override
    public void zapamtiRacun(Racun racun) {
        LOG.log(Level.INFO, "zapamtiRacun");
        em.persist(racun);
    }

    @Override
    public void obrisiRacun(Racun racun) {
        LOG.log(Level.INFO, "obrisiRacun: ", racun.getId());
        em.remove(em.merge(racun));
    }

}
