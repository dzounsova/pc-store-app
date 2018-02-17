/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.filter;

import com.misinovic.prodavnicaracunara.domen.Racun;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Misinovic
 */
@Named(value = "filterRacuna")
@ViewScoped
public class FilterRacuna implements Serializable {

    private List<Racun> filtriraniRacuni;

    public List<Racun> getFiltriraniRacuni() {
        return filtriraniRacuni;
    }

    public void setFiltriraniRacuni(List<Racun> filtriraniRacuni) {
        this.filtriraniRacuni = filtriraniRacuni;
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

    public boolean filtrirajPoDatumu(Object value, Object filter, Locale locale) {
        try {
            Calendar cal = Calendar.getInstance();
            Date filterDate = (Date) filter;
            Date valueDate = (Date) value;
            StringBuilder filterDateString = generateStringFromDate(cal, filterDate);
            StringBuilder valueDateString = generateStringFromDate(cal, valueDate);
            return valueDateString.toString().equals(filterDateString.toString());
        } catch (Exception e) {
            return false;
        }
    }

    private StringBuilder generateStringFromDate(Calendar cal, Date date) {
        cal.setTime(date);
        StringBuilder builder = new StringBuilder();
        builder.append(cal.get(Calendar.YEAR));
        builder.append("-");
        builder.append(cal.get(Calendar.MONTH));
        builder.append("-");
        builder.append(cal.get(Calendar.DAY_OF_MONTH));
        return builder;
    }

}
