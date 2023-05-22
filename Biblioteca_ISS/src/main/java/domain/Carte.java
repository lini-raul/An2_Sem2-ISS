package domain;

import java.util.Objects;

public class Carte extends Identifiable{
    private String titlu;
    private String autor;
    private int nrExemplare;

    public Carte(String titlu, String autor, int nrExemplare) {
        this.titlu = titlu;
        this.autor = autor;
        this.nrExemplare = nrExemplare;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getNrExemplare() {
        return nrExemplare;
    }

    public void setNrExemplare(int nrExemplare) {
        this.nrExemplare = nrExemplare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carte carte = (Carte) o;
        return nrExemplare == carte.nrExemplare && Objects.equals(titlu, carte.titlu) && Objects.equals(autor, carte.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titlu, autor, nrExemplare);
    }

    @Override
    public String toString() {
        return "Carte{" +
                "titlu='" + titlu + '\'' +
                ", autor='" + autor + '\'' +
                ", nrExemplare=" + nrExemplare +
                '}';
    }
}
