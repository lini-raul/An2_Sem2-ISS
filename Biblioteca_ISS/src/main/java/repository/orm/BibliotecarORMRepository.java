package repository.orm;

import domain.Abonat;
import domain.Bibliotecar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.BibliotecarRepository;
import repository.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class BibliotecarORMRepository implements BibliotecarRepository {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    public BibliotecarORMRepository() {
        logger.info("Initializing BibliotecarORMRepository");
        initialize();
    }
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
    static void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    @Override
    public void add(Bibliotecar elem) {

    }

    @Override
    public void delete(Bibliotecar elem) {

    }

    @Override
    public void update(Bibliotecar elem) {

    }

    @Override
    public Bibliotecar findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Bibliotecar> findAll() {
        return null;
    }

    @Override
    public Bibliotecar findByCredentials(String username, String password) throws RepositoryException {
        logger.traceEntry("finding task {} {}", username,password);
        Bibliotecar found_bibliotecar = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Bibliotecar> bibliotecari = session.createQuery("FROM Bibliotecar ", Bibliotecar.class).list();
            for (Bibliotecar bibliotecar : bibliotecari) {
                if(bibliotecar.getUsername().equals(username) && bibliotecar.getPassword().equals(password))
                    found_bibliotecar = new Bibliotecar(bibliotecar.getUsername(), bibliotecar.getPassword(), bibliotecar.getNume());
            }
            session.getTransaction().commit();
        }
        return found_bibliotecar;
    }
}

