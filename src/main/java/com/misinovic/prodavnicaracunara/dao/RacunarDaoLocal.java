/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Racunar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Misinovic
 */
@Local
public interface RacunarDaoLocal {

    public Racunar ucitajRacunar(int id);

    public List<Racunar> ucitajRacunare();

    public void zapamtiRacunar(Racunar racunar);

    public void izmeniRacunar(Racunar racunar);

    public void obrisiRacunar(Racunar racunar);

}
