package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Imprumut extends Identifiable{
    private LocalDate data;
    private StatusImprumut status;
    private int nrExemplare;
    private int idCarte;
    private int idAbonat;

    public Imprumut(LocalDate data, StatusImprumut status, int nrExemplare, int idCarte, int idAbonat) {
        this.data = data;
        this.status = status;
        this.nrExemplare = nrExemplare;
        this.idCarte=idCarte;
        this.idAbonat = idAbonat;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public StatusImprumut getStatus() {
        return status;
    }

    public void setStatus(StatusImprumut status) {
        this.status = status;
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
        Imprumut imprumut = (Imprumut) o;
        return nrExemplare == imprumut.nrExemplare && Objects.equals(data, imprumut.data) && status == imprumut.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, status, nrExemplare);
    }

    @Override
    public String toString() {
        return "Imprumut{" +
                "data=" + data +
                ", status=" + status +
                ", nrExemplare=" + nrExemplare +
                '}';
    }
}
