/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.konverter;

import com.misinovic.prodavnicaracunara.dao.TipKomponenteDaoLocal;
import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Misinovic
 */
@FacesConverter(value = "konverterTipa")
public class KonverterTipa implements Converter{
    
    @Inject
    TipKomponenteDaoLocal tipKomponenteDao;
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            TipKomponente tip = tipKomponenteDao.ucitajTip(Integer.parseInt(string));
            return tip;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && (o instanceof TipKomponente)) {
            TipKomponente tip = (TipKomponente) o;
            return tip.getId().toString();
        }
        return "";
    }
}
