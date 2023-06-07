package repository.orm;

import domain.Carte;
import domain.Imprumut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.CarteRepository;
import repository.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarteORMRepository implements CarteRepository {

    private static SessionFactory sessionFactory;
    private static final Logger logger = LogManager.getLogger();

    public CarteORMRepository() {
        logger.info("Initializing CarteORMRepository ");
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
    public void add(Carte elem) {
        logger.traceEntry("saving task {}", elem);
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();
        }
        logger.traceExit();

    }

    @Override
    public void delete(Carte elem) {
        logger.traceEntry("deleting task {}", elem);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.delete(elem);

            session.getTransaction().commit();
        }
        logger.traceExit();


    }

    @Override
    public void update(Carte elem) {
        logger.traceEntry("updating task {}", elem);
        Carte elemToUpdate;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.update(elem);
            session.getTransaction().commit();
        }
        logger.traceExit();

    }

    @Override
    public Carte findById(Integer integer) throws RepositoryException {
        logger.traceEntry("finding after id task {} {}", integer);
        Carte found_carte = null;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            found_carte = session.get(Carte.class,integer);

            session.getTransaction().commit();
        }
        logger.traceExit(found_carte);
        return found_carte;
    }

    @Override
    public Iterable<Carte> findAll() {
        logger.traceEntry();

        List<Carte> carti;

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            carti = session.createQuery("FROM Carte", Carte.class)
                    .list();

            session.getTransaction().commit();
        }
        logger.traceExit();
        return carti;
    }
}
