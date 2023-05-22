package repository;

import domain.Abonat;
import domain.Bibliotecar;

public interface BibliotecarRepository extends Repository<Bibliotecar, Integer>{

    public Bibliotecar findByCredentials(String username, String password) throws RepositoryException;
}
