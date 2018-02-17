/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.RacunDaoLocal;
import com.misinovic.prodavnicaracunara.dao.RacunarKomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Racun;
import com.misinovic.prodavnicaracunara.domen.StavkaRacuna;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/**
 *
 * @author Misinovic
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RacunBO {

    @Inject
    RacunDaoLocal racunDao;

    @Inject
    RacunarKomponentaDaoLocal racunarKomponentaDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void zapamtiRacun(Racun racun) {
        racunDao.zapamtiRacun(racun);
        for (StavkaRacuna s : racun.getStavkeRacuna()) {
            racunarKomponentaDao.smanjiKolicinu(s);
        }
    }

    public void obrisiRacun(Racun racun) {
        racunDao.obrisiRacun(racun);
    }

    public List<Racun> ucitajRacune() {
        List<Racun> racuni = racunDao.ucitajRacune();
        return racuni;
    }

    public void dodajStavku(StavkaRacuna stavka) {
        boolean contains = false;
        double vrednostStavke = stavka.getKolicina() * stavka.getProdajnaCena();
        for (StavkaRacuna s : stavka.getRacun().getStavkeRacuna()) {
            if (s.getRacunarKomponenta().equals(stavka.getRacunarKomponenta())) {
                int kolicina = s.getKolicina();
                double vrednost = s.getUkupnaVrednost();
                s.setKolicina(kolicina + stavka.getKolicina());
                s.setUkupnaVrednost(vrednost + vrednostStavke);
                contains = true;
                break;
            }
        }
        if (!contains) {
            int redniBroj = stavka.getRacun().getStavkeRacuna().size();
            stavka.setRedniBroj(++redniBroj);
            stavka.setUkupnaVrednost(vrednostStavke);
            stavka.getRacun().getStavkeRacuna().add(stavka);
        }
    }

}
