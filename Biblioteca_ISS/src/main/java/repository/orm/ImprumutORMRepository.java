package repository.orm;

import domain.Imprumut;
import domain.Review;
import domain.StatusImprumut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ImprumutRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImprumutORMRepository implements ImprumutRepository {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;

    public ImprumutORMRepository() {
        logger.info("Initializing ImprumutORMRepository");
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
    public void add(Imprumut elem) {
        logger.traceEntry("saving task {}", elem);
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();
        }
        logger.traceExit();

    }

    @Override
    public void delete(Imprumut elem) {

    }

    @Override
    public void update(Imprumut elem) {

        logger.traceEntry("updating task {}", elem);

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Imprumut elemToUpdate = session.get(Imprumut.class, elem.getId());
            elemToUpdate = elem;
            session.update(elemToUpdate);

            session.getTransaction().commit();
        }

        logger.traceExit();

    }

    @Override
    public Imprumut findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Imprumut> findAll() {

        logger.traceEntry();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Imprumut> found_imprumuturi = new ArrayList<>();

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            found_imprumuturi = session.createQuery("FROM Imprumut", Imprumut.class)
                    .list();

            session.getTransaction().commit();
        }
        logger.traceExit();
        return found_imprumuturi;

    }
}
