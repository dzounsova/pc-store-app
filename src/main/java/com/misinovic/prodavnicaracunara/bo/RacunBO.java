/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.RacunDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Racun;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
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
    RacunarKomponentaBO racunarKomponentaBO;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void zapamtiRacun(Racun racun) {
        racunDao.zapamtiRacun(racun);
        racun.getStavkeRacuna().forEach((s) -> {
            racunarKomponentaBO.smanjiKolicinu(s.getRacunarKomponenta(), s.getKolicina());
        });
    }

    public void obrisiRacun(Racun racun) {
        racunDao.obrisiRacun(racun);
    }

    public List<Racun> ucitajRacune() {
        List<Racun> racuni = racunDao.ucitajRacune();
        return racuni;
    }

    public void dodajStavku(StavkaRacuna stavka) {
        List<StavkaRacuna> postojeceStavke = stavka.getRacun().getStavkeRacuna();
        final double vrednostStavke = stavka.getKolicina() * stavka.getProdajnaCena();
        final StavkaRacuna istaStavka = postojiStavka(postojeceStavke, stavka.getRacunarKomponenta());

        if (istaStavka != null) {
            istaStavka.setKolicina(stavka.getKolicina() + istaStavka.getKolicina());
            istaStavka.setUkupnaVrednost(vrednostStavke + istaStavka.getUkupnaVrednost());
        } else {
            stavka.setUkupnaVrednost(vrednostStavke);
            postojeceStavke.add(stavka);
        }
    }

    private StavkaRacuna postojiStavka(List<StavkaRacuna> postojeceStavke, RacunarKomponenta racunarKomponenta) {
        return postojeceStavke.stream()
                .filter(s -> racunarKomponenta.equals(s.getRacunarKomponenta()))
                .findFirst().orElse(null);
    }

}
