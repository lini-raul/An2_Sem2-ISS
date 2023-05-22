package repository.database;

import domain.Imprumut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ImprumutRepository;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ImprumutDBRepository implements ImprumutRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ImprumutDBRepository(Properties props) {
        logger.info("Initializing ImprumutDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Imprumut elem) {
        logger.traceEntry("saving task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("INSERT INTO Imprumut (data, status, nrExemplare, idCarte,idAbonat) values (?,?,?,?,?)")) {
            preStmt.setString(1,elem.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            preStmt.setString(2, elem.getStatus().toString());
            preStmt.setInt(3, elem.getNrExemplare());
            preStmt.setInt(4, elem.getIdCarte());
            preStmt.setInt(5,elem.getIdAbonat());

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
    public void delete(Imprumut elem) {

    }

    @Override
    public void update(Imprumut elem) {

    }

    @Override
    public Imprumut findById(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Imprumut> findAll() {
        return null;
    }
}
