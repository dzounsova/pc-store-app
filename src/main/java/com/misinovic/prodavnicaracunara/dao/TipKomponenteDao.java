/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.TipKomponente;
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
public class TipKomponenteDao implements TipKomponenteDaoLocal {

    private static final Logger log = Logger.getLogger(TipKomponenteDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Override
    public List<TipKomponente> ucitajTipove() {
        return em.createNamedQuery(TipKomponente.NamedQuery.findAll).getResultList();
    }

    @Override
    public TipKomponente ucitajTip(int id) {
        log.log(Level.INFO, "ucitajTip: ", id);
        return em.find(TipKomponente.class, id);
    }
}
