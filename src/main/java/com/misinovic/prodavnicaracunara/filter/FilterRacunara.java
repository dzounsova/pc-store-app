/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.filter;

import com.misinovic.prodavnicaracunara.domen.Racunar;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Misinovic
 */
@Named(value = "filterRacunara")
@ViewScoped
public class FilterRacunara implements Serializable {

    private List<Racunar> filtriraniRacunari;

    public List<Racunar> getFiltriraniRacunari() {
        return filtriraniRacunari;
    }

    public void setFiltriraniRacunari(List<Racunar> filtriraniRacunari) {
        this.filtriraniRacunari = filtriraniRacunari;
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

    public boolean filtrirajPoImenuIPrezimenu(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        boolean contains = false;
        String[] name = value.toString().toLowerCase().trim().split(" ");
        for (String namePart : name) {
            if (namePart.startsWith(filterText.toLowerCase())) {
                contains = true;
            }
        }
        return contains;
    }
}
