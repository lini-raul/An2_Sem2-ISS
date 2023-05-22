package domain;

import java.util.Objects;

public class Abonat extends Identifiable{
    private String username;
    private String password;
    private String nume;
    private String CNP;
    private String adresa;
    private String telefon;

    public Abonat(String username, String password, String nume, String CNP, String adresa, String telefon) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.CNP = CNP;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Abonat abonat = (Abonat) o;
        return Objects.equals(username, abonat.username) && Objects.equals(password, abonat.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Abonat{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nume='" + nume + '\'' +
                ", CNP='" + CNP + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
