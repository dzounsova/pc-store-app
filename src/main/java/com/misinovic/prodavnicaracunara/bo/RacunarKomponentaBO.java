/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.RacunarKomponentaDaoLocal;
import com.misinovic.prodavnicaracunara.domen.RacunarKomponenta;
import com.misinovic.prodavnicaracunara.exception.ConstraintViolationException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.validation.Validator;

/**
 *
 * @author Misinovic
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RacunarKomponentaBO {
    
    @Inject
    RacunarKomponentaDaoLocal racunarKomponentaDao;
    
    @Resource
    Validator validator;
    
    public List<RacunarKomponenta> ucitajRaspoloziveRacunareIKomponente() {
        List<RacunarKomponenta> racunariIKomponente = racunarKomponentaDao.ucitajRaspoloziveRacunareIKomponente();
        return racunariIKomponente;
    }
    
    public RacunarKomponenta ucitajRacunarKomponentu(RacunarKomponenta racunarKomponenta) {
        return racunarKomponentaDao.ucitajRacunarIliKomponentu(racunarKomponenta.getId());
    }
    
    public void izmeniRacunarKomponentu(RacunarKomponenta racunarKomponenta) {
        racunarKomponentaDao.izmeniKomponentu(racunarKomponenta);
    }
    
    private void validirajRacunarKomponentu(RacunarKomponenta racunarKomponenta) throws ConstraintViolationException {
        validator.validate(racunarKomponenta).stream()
                .findFirst()
                .ifPresent(violation -> {
                    throw new ConstraintViolationException(violation);
                });
    }
    
    public void smanjiKolicinu(RacunarKomponenta racunarKomponenta, int kolicina) throws ConstraintViolationException {
        RacunarKomponenta rk = ucitajRacunarKomponentu(racunarKomponenta);
        rk.setKolicinaNaZalihi(rk.getKolicinaNaZalihi() - kolicina);
        validirajRacunarKomponentu(rk);
        izmeniRacunarKomponentu(rk);
    }
}
