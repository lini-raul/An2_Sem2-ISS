package repository.database;

import domain.Carte;
import domain.Imprumut;
import domain.StatusImprumut;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.ImprumutRepository;
import utils.JdbcUtils;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
        String sql = "UPDATE Imprumut SET data=?, status=?, nrExemplare=?, idCarte=?, idAbonat=? WHERE id=?";
        logger.traceEntry("updating task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement(sql)) {
            preStmt.setString(1,elem.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            preStmt.setString(2, elem.getStatus().toString());
            preStmt.setInt(3, elem.getNrExemplare());
            preStmt.setInt(4, elem.getIdCarte());
            preStmt.setInt(5,elem.getIdAbonat());
            preStmt.setInt(6, elem.getId());

            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB " + e);
            throw new RuntimeException(e);
        } ;
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

        Connection con = dbUtils.getConnection();
        List<Imprumut> imprumuturi = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT * from Imprumut")) {
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");

                    LocalDate data = LocalDate.parse(result.getString("data"),formatter);
                    StatusImprumut status = StatusImprumut.valueOf(result.getString("status"));
                    int nrExemplare = result.getInt("nrExemplare");
                    int idCarte = result.getInt("idCarte");
                    int idAbonat=  result.getInt("idAbonat");

                    Imprumut imprumut = new Imprumut(data,status,nrExemplare, idCarte, idAbonat);
                    imprumut.setId(id);
                    imprumuturi.add(imprumut);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
            //throw new RuntimeException(e);
        }
        logger.traceExit();
        return imprumuturi;
    }
}
