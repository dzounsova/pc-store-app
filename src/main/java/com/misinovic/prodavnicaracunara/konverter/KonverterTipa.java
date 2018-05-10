/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.konverter;

import com.misinovic.prodavnicaracunara.domen.TipKomponente;
import com.misinovic.prodavnicaracunara.kontroler.KontrolerObradeKomponenti;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Misinovic
 */
@Named
@ApplicationScoped
public class KonverterTipa implements Converter {

    @Inject
    KontrolerObradeKomponenti kontrolerObradeKomponenti;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string != null && !string.isEmpty()) {
            List<TipKomponente> tipovi = kontrolerObradeKomponenti.getTipovi();
            for (TipKomponente tip : tipovi) {
                if (tip.getId() == Integer.parseInt(string)) {
                    return tip;
                }
            }
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
