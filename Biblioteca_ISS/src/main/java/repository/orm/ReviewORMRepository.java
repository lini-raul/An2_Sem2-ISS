package repository.orm;

import domain.Bibliotecar;
import domain.Carte;
import domain.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.ReviewRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReviewORMRepository implements ReviewRepository {
    private static final Logger logger = LogManager.getLogger();
    private static SessionFactory sessionFactory;
    public ReviewORMRepository() {
        logger.info("Initializing ReviewORMRepository  ");
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
    public void add(Review elem) {
        logger.traceEntry("saving task {}", elem);


        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();
        }

        logger.traceExit();

    }

    @Override
    public void delete(Review elem) {

    }

    @Override
    public void update(Review elem) {

    }

    @Override
    public Review findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Review> findAll() {
        return null;
    }

    @Override
    public List<Review> findByCarte(Carte carte) {
        logger.traceEntry();

        List<Review> found_reviews = new ArrayList<>();

        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            found_reviews = session.createQuery("FROM Review WHERE idCarte = :idCarte", Review.class)
                    .setParameter("idCarte", carte.getId())
                    .list();

            session.getTransaction().commit();
        }

        logger.traceExit(found_reviews);
        return found_reviews;




    }
}
