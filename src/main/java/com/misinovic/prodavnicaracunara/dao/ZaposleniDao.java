/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Zaposleni;
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
public class ZaposleniDao implements ZaposleniDaoLocal {

    private static final Logger LOG = Logger.getLogger(ZaposleniDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Override
    public Zaposleni ucitajZaposlenog(Integer id) {
        LOG.log(Level.INFO, "ucitajZaposlenog: ", id);
        return em.find(Zaposleni.class, id);
    }

    @Override
    public Zaposleni ucitajZaposlenog(String username, String password) {
        List<Zaposleni> list = em.createNamedQuery(Zaposleni.NamedQuery.findByUsernameAndPassword, Zaposleni.class).setParameter("username", username).setParameter("password", password).getResultList();
        if (list.isEmpty() || list.size() > 1) {
            return null;
        }
        return list.get(0);
    }

}
