/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Komponenta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Misinovic
 */
@Stateless
public class KomponentaDao implements KomponentaDaoLocal {

    private static final Logger LOG = Logger.getLogger(KomponentaDao.class.getName());

    @PersistenceContext(unitName = "ProdavnicaRacunaraPU")
    private EntityManager em;

    @Override
    public Komponenta ucitajKomponentu(int id) {
        LOG.log(Level.INFO, "ucitajKomponentu: {0}", id);
        return em.find(Komponenta.class, id);
    }

    /**
     * Ucitaj komponentu sa parametrima koji cine njen business key (tip, proizvodjac, naziv)
     *
     * @param komponenta Objekat komponente sa podesenim business key parametrima
     * @return Ucitana komponenta
     * @throws NoResultException Ukoliko ne postoji komponenta sa zadatim parametrima
     */
    @Override
    public Komponenta ucitajKomponentu(Komponenta komponenta) throws NoResultException {
        LOG.log(Level.INFO, "ucitajKomponentu: {0} - {1} - {2}", new String[]{komponenta.getTip().getNaziv(),
            komponenta.getProizvodjac(), komponenta.getNaziv()});
        Komponenta k = (Komponenta) em.createNamedQuery(Komponenta.NamedQuery.findByComparableAttributes)
                .setParameter("tip", komponenta.getTip())
                .setParameter("proizvodjac", komponenta.getProizvodjac())
                .setParameter("naziv", komponenta.getNaziv())
                .getSingleResult();
        return k;
    }

    @Override
    public List<Komponenta> ucitajKomponente() {
        LOG.log(Level.INFO, "ucitajKomponente");
        List<Komponenta> komponente = em.createNamedQuery(Komponenta.NamedQuery.findAll).getResultList();
        return komponente;
    }

    @Override
    public List<Komponenta> ucitajRaspoloziveKomponente() {
        LOG.log(Level.INFO, "ucitajKomponente");
        List<Komponenta> komponente = em.createNamedQuery(Komponenta.NamedQuery.findByKolicina).getResultList();
        return komponente;
    }

    @Override
    public void zapamtiKomponentu(Komponenta komponenta) {
        LOG.log(Level.INFO, "zapamtiKomponentu");
        em.persist(komponenta);
    }

    @Override
    public void izmeniKomponentu(Komponenta komponenta) {
        LOG.log(Level.INFO, "izmeniKomponentu: {0}", komponenta.getId());
        em.merge(komponenta);
    }

    @Override
    public void obrisiKomponentu(Komponenta komponenta) {
        LOG.log(Level.INFO, "obrisiKomponentu: {0}", komponenta.getId());
        em.remove(em.merge(komponenta));
    }

}
