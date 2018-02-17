/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.dao;

import com.misinovic.prodavnicaracunara.domen.Zaposleni;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Misinovic
 */
@Local
public interface ZaposleniDaoLocal {

    Zaposleni ucitajZaposlenog(Integer id);

    Zaposleni ucitajZaposlenog(String username, String password);

}
