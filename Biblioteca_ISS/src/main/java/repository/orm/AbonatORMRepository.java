package repository.orm;

import domain.Abonat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.AbonatRepository;
import repository.RepositoryException;
import utils.JdbcUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class AbonatORMRepository implements AbonatRepository {

    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger();

    public AbonatORMRepository() {
        logger.info("Initializing AbonatORMRepository");
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
    public void add(Abonat elem) {

    }

    @Override
    public void delete(Abonat elem) {

    }

    @Override
    public void update(Abonat elem) {

    }

    @Override
    public Abonat findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Abonat> findAll() {
        return null;
    }

    @Override
    public Abonat findByCredentials(String username, String password) throws RepositoryException {
        logger.traceEntry("finding task {} {}", username,password);
        Abonat found_abonat = null;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            List<Abonat> abonati = session.createQuery("FROM Abonat ", Abonat.class).list();
            for (Abonat abonat : abonati) {
                if(abonat.getUsername().equals(username) && abonat.getPassword().equals(password))
                    found_abonat = new Abonat(abonat.getUsername(),abonat.getPassword(), abonat.getNume(), abonat.getCNP(), abonat.getAdresa(), abonat.getTelefon());
                    found_abonat.setId(abonat.getId());
            }
            session.getTransaction().commit();
        }
        return found_abonat;

    }
}

