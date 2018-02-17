/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.KomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.dao.RacunarDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.domen.Ugradnja;
import java.util.Arrays;
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
public class RacunarBO {

    @Inject
    RacunarDaoLocal racunarDao;

    @Inject
    KomponentaDaoLocal komponentaDao;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void izmeniRacunar(Racunar racunar, int zalihaRacunara) {
        int izmenjeni = racunar.getKolicinaNaZalihi();
        if (zalihaRacunara < izmenjeni) {
            int razlika = izmenjeni - zalihaRacunara;
            racunar.setKolicinaNaZalihi(razlika);
            for (Ugradnja ugradnja : racunar.getUgradnje()) {
                komponentaDao.smanjiKolicinu(ugradnja);
            }
        }
        racunar.setKolicinaNaZalihi(izmenjeni);
        racunarDao.izmeniRacunar(racunar);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void zapamtiRacunar(Racunar racunar) {
        racunarDao.zapamtiRacunar(racunar);
        for (Ugradnja ugradnja : racunar.getUgradnje()) {
            komponentaDao.smanjiKolicinu(ugradnja);
        }
    }

    public void obrisiRacunar(Racunar racunar) {
        racunarDao.obrisiRacunar(racunar);
    }

    public List<Racunar> ucitajRacunare() {
        List<Racunar> racunari = racunarDao.ucitajRacunare();
        return racunari;
    }

    public void dodajUgradnju(Ugradnja ugradnja) throws Exception {
        boolean contains = false;
        if (!ugradnja.getRacunar().getUgradnje().isEmpty()) {
            for (Ugradnja u : ugradnja.getRacunar().getUgradnje()) {
                if (u.getKomponenta().getTip().equals(ugradnja.getKomponenta().getTip())) {
                    if (jedinstvenaKomponenta(ugradnja.getKomponenta())) {
                        throw new Exception("Komponenta je vec ugraÄ‘ena u raÄŤunar");
                    } else {
                        if (u.getKomponenta().equals(ugradnja.getKomponenta())) {
                            int kolicina = u.getKolicina();
                            int temp = ugradnja.getKolicina();
                            kolicina += temp;
                            u.setKolicina(kolicina);
                            contains = true;
                            break;
                        }
                    }
                }
            }
            if (!contains) {
                ugradnja.getRacunar().getUgradnje().add(ugradnja);
            }
        } else {
            ugradnja.getRacunar().getUgradnje().add(ugradnja);
        }

    }

    public boolean jedinstvenaKomponenta(Komponenta komponenta) {
        String[] jedinstveneKomponente = {"MatiÄŤna ploÄŤa", "Procesor", "Hladnjak", "Operativni sistem", "KuÄ‡iĹˇte", "Napajanje", "ZvuÄŤna karta", "Tastatura", "MiĹˇ", "GrafiÄŤka karta"};
        if (Arrays.asList(jedinstveneKomponente).contains(komponenta.getTip().getNaziv())) {
            return true;
        } else {
            return false;
        }
    }

}
