/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.RacunarKomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

/**
 *
 * @author Misinovic
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RacunarKomponentaBO {

    @Inject
    RacunarKomponentaDaoLocal racunarKomponentaDao;

    public List<RacunarKomponenta> ucitajRaspoloziveRacunareIKomponente() {
        List<RacunarKomponenta> racunariIKomponente = racunarKomponentaDao.ucitajRaspoloziveRacunareIKomponente();
        return racunariIKomponente;
    }

}
