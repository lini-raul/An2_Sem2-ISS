package repository.database;

import domain.Abonat;
import domain.Bibliotecar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.BibliotecarRepository;
import repository.RepositoryException;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class BibliotecarDBRepository implements BibliotecarRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public BibliotecarDBRepository(Properties props) {
        logger.info("Initializing BibliotecarDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
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
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Bibliotecar where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,password);
            try(ResultSet result= preStmt.executeQuery()) {
                if(result.next()!=false){
                    int id = result.getInt("id");
                    String found_username = result.getString("username");
                    String found_password = result.getString("password");
                    String found_nume = result.getString("nume");
                    found_bibliotecar = new Bibliotecar(found_username,found_password,found_nume);
                    found_bibliotecar.setId(id);
                }
                else throw new RepositoryException("Bibliotecar not found!");
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+ e);
            //throw new RuntimeException(e);
        }
        logger.traceExit(found_bibliotecar);
        return found_bibliotecar;
    }
}
