/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.filter;

import com.misinovic.prodavnicaracunara.domen.Komponenta;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Misinovic
 */
@Named(value = "filterKomponenti")
@ViewScoped
public class FilterKomponenti implements Serializable {

    private List<Komponenta> filtriraneKomponente;

    public List<Komponenta> getFiltriraneKomponente() {
        return filtriraneKomponente;
    }

    public void setFiltriraneKomponente(List<Komponenta> filtriraneKomponente) {
        this.filtriraneKomponente = filtriraneKomponente;
    }

    public boolean filtrirajPoCeni(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        Double cena = (Double) value;
        Double unesenaCena = Double.parseDouble(filterText);
        return (cena >= (unesenaCena - 1000)) && (cena <= (unesenaCena + 1000));
    }
    
}
