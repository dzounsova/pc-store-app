/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.bo;

import com.misinovic.prodavnicaracunara.dao.TipKomponenteDaoLocal;
import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Misinovic
 */
@Stateless
public class TipKomponenteBO {

    @Inject
    TipKomponenteDaoLocal tipKomponenteDao;

    public List<TipKomponente> ucitajTipove() {
        return tipKomponenteDao.ucitajTipove();
    }
}
