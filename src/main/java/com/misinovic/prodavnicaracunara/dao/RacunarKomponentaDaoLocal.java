/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import com.misinovic.prodavnicaracunara.domen.StavkaRacuna;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Misinovic
 */
@Local
public interface RacunarKomponentaDaoLocal {

    public RacunarKomponenta ucitajRacunarIliKomponentu(int id);

    public List<RacunarKomponenta> ucitajRacunareIKomponente();
    
    public List<RacunarKomponenta> ucitajRaspoloziveRacunareIKomponente();

    public void smanjiKolicinu(StavkaRacuna stavka);
}
