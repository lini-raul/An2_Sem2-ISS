package repository.database;

import domain.Carte;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class CarteDBRepository implements CarteRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public CarteDBRepository(Properties props) {
        logger.info("Initializing CarteDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public void add(Carte elem) {

    }

    @Override
    public void delete(Carte elem) {

    }

    @Override
    public void update(Carte elem) {
        logger.traceEntry("updating task {}", elem);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("update Carte set titlu=?, autor=?, nrExemplare=?  where id=?")){
            preparedStatement.setString(1,elem.getTitlu());
            preparedStatement.setString(2,elem.getAutor());
            preparedStatement.setInt(3,elem.getNrExemplare());
            preparedStatement.setInt(4,elem.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
        }
        logger.traceExit();

    }

    @Override
    public Carte findById(Integer integer) throws RepositoryException {
        logger.traceEntry("finding after id task {} {}", integer);
        Carte found_carte = null;
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt= con.prepareStatement("select * from Carte where id=?")) {
            preStmt.setInt(1,integer);

            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()!=false){
                    int id = result.getInt("id");
                    String titlu = result.getString("titlu");
                    String autor = result.getString("autor");
                    int nrExemplare = result.getInt("nrExemplare");
                    found_carte = new Carte(titlu,autor,nrExemplare);
                    found_carte.setId(id);

                }
                else throw new RepositoryException("Carte not found by ID!");
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
            //throw new RuntimeException(e);
        }
        logger.traceExit(found_carte);
        return found_carte;
    }

    @Override
    public Iterable<Carte> findAll() {
        logger.traceEntry();
        Connection con = dbUtils.getConnection();
        List<Carte> carti = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("SELECT * from Carte")) {
            try(ResultSet result = preStmt.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String titlu = result.getString("titlu");
                    String autor = result.getString("autor");
                    int nrExemplare = result.getInt("nrExemplare");
                    Carte carte = new Carte(titlu, autor,nrExemplare);
                    carte.setId(id);
                    carti.add(carte);
                }
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+e);
            //throw new RuntimeException(e);
        }
        logger.traceExit();
        return carti;
    }
}
