package domain;

import java.util.Objects;

public class Review extends Identifiable{
    private int idAbonat;
    private int idCarte;
    private String descriere;

    public Review(int idAbonat, int idCarte, String descriere) {
        this.idAbonat = idAbonat;
        this.idCarte = idCarte;
        this.descriere = descriere;
    }

    public int getIdAbonat() {
        return idAbonat;
    }

    public void setIdAbonat(int idAbonat) {
        this.idAbonat = idAbonat;
    }

    public int getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return idAbonat == review.idAbonat && idCarte == review.idCarte && Objects.equals(descriere, review.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAbonat, idCarte, descriere);
    }

    @Override
    public String toString() {
        return "Review{" +
                "idAbonat=" + idAbonat +
                ", idCarte=" + idCarte +
                ", descriere='" + descriere + '\'' +
                '}';
    }
}
