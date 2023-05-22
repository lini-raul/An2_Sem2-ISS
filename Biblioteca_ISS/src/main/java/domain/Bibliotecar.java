package domain;

import java.util.Objects;

public class Bibliotecar extends Identifiable{
    private String username;
    private String password;
    private String nume;

    public Bibliotecar(String username, String password, String nume) {
        this.username = username;
        this.password = password;
        this.nume = nume;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bibliotecar that = (Bibliotecar) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Bibliotecar{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nume='" + nume + '\'' +
                '}';
    }
}
