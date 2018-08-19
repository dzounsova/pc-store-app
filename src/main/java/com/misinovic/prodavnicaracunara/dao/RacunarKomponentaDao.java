/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import com.misinovic.prodavnicaracunara.domen.StavkaRacuna;
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
public class RacunarKomponentaDao implements RacunarKomponentaDaoLocal {

    private static final Logger LOG = Logger.getLogger(RacunarKomponentaDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Resource
    Validator validator;

    @Override
    public RacunarKomponenta ucitajRacunarIliKomponentu(int id) {
        LOG.log(Level.INFO, "ucitajRacunarIliKomponentu: ", id);
        return em.find(RacunarKomponenta.class, id);
    }

    @Override
    public List<RacunarKomponenta> ucitajRacunareIKomponente() {
        List<RacunarKomponenta> lista = em.createNamedQuery(RacunarKomponenta.NamedQuery.findAll).getResultList();
        return lista;
    }

    @Override
    public void smanjiKolicinu(StavkaRacuna stavka) {
        LOG.log(Level.INFO, "smanjiKolicinu: racunar/komponenta = {0}, kolicina = {1}", new Object[]{stavka.getRacunarKomponenta().getId(), stavka.getKolicina()});
        Query q = em.createNamedQuery(RacunarKomponenta.NamedQuery.smanjiKolicinu).setParameter("kolicina", stavka.getKolicina()).setParameter("id", stavka.getRacunarKomponenta().getId());
        q.executeUpdate();
        RacunarKomponenta rk = ucitajRacunarIliKomponentu(stavka.getRacunarKomponenta().getId());
        Set<ConstraintViolation<RacunarKomponenta>> violations = validator.validate(rk);
        if (violations.size() > 0) {
            LOG.log(Level.INFO, "smanjiKolicinu: Constraint violation");
            em.getTransaction().rollback();
        }
    }

}
