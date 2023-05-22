package repository;

import domain.Abonat;

public interface AbonatRepository extends Repository<Abonat, Integer> {
    public Abonat findByCredentials(String username, String password) throws RepositoryException;

}
