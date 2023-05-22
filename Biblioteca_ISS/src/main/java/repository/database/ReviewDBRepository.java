package repository.database;

import com.sun.prism.shader.DrawPgram_LinearGradient_REPEAT_AlphaTest_Loader;
import domain.Carte;
import domain.Review;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ReviewRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ReviewDBRepository implements ReviewRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ReviewDBRepository(Properties props) {
        logger.info("Initializing ReviewDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }


    @Override
    public void add(Review elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("INSERT INTO Review (idAbonat, idCarte, descriere) values (?,?,?)")) {
            preStmt.setInt(1,elem.getIdAbonat());
            preStmt.setInt(2, elem.getIdCarte());
            preStmt.setString(3, elem.getDescriere());

            int result = preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
            throw new RuntimeException(e);
        } ;
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
        Connection con = dbUtils.getConnection();
        List<Review> reviews = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement("select * from Review where idCarte = ?")) {
            preparedStatement.setInt(1, carte.getId());
            try (ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idAbonat = result.getInt("idAbonat");
                    int idCarte = result.getInt("idCarte");
                    String descriere = result.getString("descriere");
                    Review review = new Review(idAbonat, idCarte, descriere);
                    review.setId(id);
                    reviews.add(review);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.print("Error DB " + e);
            //throw new RuntimeException(e);
        }

        logger.traceExit(reviews);
        return reviews;

    }
}
