package sample;

import java.util.Date;

public class Animal {

    private String meno;
    private Date datum_narodenia;
    private String stav;
    private String zdravotna_karta;
    private String  trieda;
    private String rad;
    private String celad;

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public Date getDatum_narodenia() {
        return datum_narodenia;
    }

    public void setDatum_narodenia(Date datum_narodenia) {
        this.datum_narodenia = datum_narodenia;
    }

    public String getStav() {
        return stav;
    }

    public void setStav(String stav) {
        this.stav = stav;
    }

    public String getZdravotna_karta() {
        return zdravotna_karta;
    }

    public void setZdravotna_karta(String zdravotna_karta) {
        this.zdravotna_karta = zdravotna_karta;
    }

    public String getTrieda() {
        return trieda;
    }

    public void setTrieda(String trieda) {
        this.trieda = trieda;
    }

    public String getRad() {
        return rad;
    }

    public void setRad(String rad) {
        this.rad = rad;
    }

    public String getCelad() {
        return celad;
    }

    public void setCelad(String celad) {
        this.celad = celad;
    }
}
