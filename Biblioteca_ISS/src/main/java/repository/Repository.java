package repository;

public interface Repository <E,ID>{
    void add(E elem);
    void delete(E elem);
    void update(E elem);
    E findById(ID id) throws RepositoryException;
    Iterable<E> findAll();
}
