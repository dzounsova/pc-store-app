/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Misinovic
 */
@Local
public interface TipKomponenteDaoLocal {

    public List<TipKomponente> ucitajTipove();

    public TipKomponente ucitajTip(int id);
}
