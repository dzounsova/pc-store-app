/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.RacunarDaoLocal;
import com.misinovic.prodavnicaracunara.domen.Komponenta;
import com.misinovic.prodavnicaracunara.domen.Racunar;
import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import com.misinovic.prodavnicaracunara.domen.Ugradnja;
import com.misinovic.prodavnicaracunara.exception.NonUniqueResourceException;
import com.misinovic.prodavnicaracunara.exception.IllegalStateException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

    /**
     * Skup jedinstvenih tipova komponenti. Ukoliko je tip jedinstven, racunar moze imati najvise jednu komponentu tog
     * tipa. Vrednosti mapirane sa tipkomponente.tipKomponenteID slogom.
     */
    private final Integer[] jedinstveniTipoviKomponente = new Integer[]{1, 2, 6, 7, 8, 9, 10, 11, 12, 14};

    private final Integer[] obavezniTipoviKomponenti = new Integer[]{1, 2, 3, 4, 6, 8, 9, 10, 14};

    @Inject
    RacunarDaoLocal racunarDao;

    @Inject
    KomponentaBO komponentaBO;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void izmeniRacunar(Racunar racunar, int zalihaRacunara) {
        int izmenjeni = racunar.getKolicinaNaZalihi();
        if (zalihaRacunara < izmenjeni) {
            int razlika = izmenjeni - zalihaRacunara;
            racunar.getUgradnje().forEach((ugradnja) -> {
                Komponenta k = ugradnja.getKomponenta();
                int kolicina = ugradnja.getKolicina() * razlika;
                komponentaBO.smanjiKolicinu(k, kolicina);
            });
        }
        racunarDao.izmeniRacunar(racunar);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void zapamtiRacunar(Racunar racunar) {
        racunarDao.zapamtiRacunar(racunar);
        racunar.getUgradnje().forEach((ugradnja) -> {
            Komponenta k = ugradnja.getKomponenta();
            int kolicina = ugradnja.getKolicina() * racunar.getKolicinaNaZalihi();
            komponentaBO.smanjiKolicinu(k, kolicina);
        });
    }

    public void obrisiRacunar(Racunar racunar) {
        racunarDao.obrisiRacunar(racunar);
    }

    public List<Racunar> ucitajRacunare() {
        List<Racunar> racunari = racunarDao.ucitajRacunare();
        return racunari;
    }

    /**
     * Moguci use case-evi pri dodavanju komponente u racunar: 
     * 1. Komponenta je jedinstvenog tipa i vec postoji ugradjena komponenta istog tipa - NE DODAJE SE 
     * 2. Komponenta nije jedinstvenog tipa i vec je ugradjena u racunar - POVECAVA SE KOLICINA 
     * 3. Komponenta je ili jedinstvenog tipa ali komponenta istog tipa nije vec ugradjena ili nije jedinstvenog tipa
     *    i nije ugradjena - DODAJE SE
     * @param ugradnja
     * @throws NonUniqueResourceException Vec postoji ugradjena komponenta istog tipa
     */
    public void dodajUgradnju(Ugradnja ugradnja) throws NonUniqueResourceException {
        List<Ugradnja> postojeceUgradnje = ugradnja.getRacunar().getUgradnje();
        final TipKomponente tipNoveKomponente = ugradnja.getKomponenta().getTip();

        final boolean postojiTipKomponente = postojiTipKomponente(postojeceUgradnje, tipNoveKomponente);
        final Ugradnja istaKomponenta = postojiKomponenta(postojeceUgradnje, ugradnja.getKomponenta());

        if (jedinstveniTipKomponente(tipNoveKomponente) && postojiTipKomponente) {
            throw new NonUniqueResourceException(ugradnja.getKomponenta());
        } else if (!jedinstveniTipKomponente(tipNoveKomponente) && istaKomponenta != null) {
            istaKomponenta.setKolicina(istaKomponenta.getKolicina() + 1);
        } else {
            postojeceUgradnje.add(ugradnja);
        }
    }

    /**
     * Utvrdjuje da li je tip jedan od jedinstvenih tipova komponenti. Tip je jedinstven ukoliko se komponenta tog tipa
     * moze ugraditi samo jednom u racunar.
     *
     * @param tip
     * @return
     */
    private boolean jedinstveniTipKomponente(TipKomponente tip) {
        return Arrays.asList(jedinstveniTipoviKomponente).stream().anyMatch(tip.getId()::equals);
    }

    /**
     * Pretraga da li je u racunar ugradjena ista komponenta
     *
     * @param ugradnje Ugradjene komponente
     * @param ugradnja Nova komponenta
     * @return
     */
    private Ugradnja postojiKomponenta(List<Ugradnja> ugradnje, Komponenta komponenta) {
        return ugradnje.stream()
                .filter(p -> p.getKomponenta().getTip().equals(komponenta.getTip()))
                .filter(p -> p.getKomponenta().equals(komponenta))
                .findFirst().orElse(null);
    }

    /**
     * Pretraga da li je u racunar ugradjena komponenta istog tipa
     *
     * @param ugradnje Ugradjene komponente
     * @param tip Tip komponente koju zelimo da ugradimo
     * @return
     */
    private boolean postojiTipKomponente(List<Ugradnja> ugradnje, TipKomponente tip) {
        return ugradnje.stream().anyMatch(u -> u.getKomponenta().getTip().equals(tip));
    }

    /**
     * Provera da li racunar ima sve neophodne komponente
     *
     * @param racunar
     */
    public void validirajRacunar(Racunar racunar) throws IllegalStateException {
        List<Integer> ugradjeniTipovi = racunar.getUgradnje().stream()
                .map(u -> u.getKomponenta().getTip().getId())
                .collect(Collectors.toList());

        Arrays.asList(obavezniTipoviKomponenti).stream()
                .filter(t -> !ugradjeniTipovi.contains(t))
                .findFirst()
                .ifPresent(mismatch -> {
                    throw new IllegalStateException("Business constraint violation on entity: "
                            + racunar.getClass().getSimpleName() + ", property: ugradnje, "
                            + "rule: must have all required component types including type: " + mismatch);
                });
    }

}
