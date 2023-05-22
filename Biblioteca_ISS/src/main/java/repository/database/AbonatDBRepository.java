package repository.database;

import domain.Abonat;
import repository.AbonatRepository;
import repository.RepositoryException;
import utils.JdbcUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class AbonatDBRepository implements AbonatRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public AbonatDBRepository(Properties props) {
        logger.info("Initializing AbonatDBRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
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
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("select * from Abonat where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,password);
            try(ResultSet result= preStmt.executeQuery()) {
                if(result.next()!=false){
                    int id = result.getInt("id");
                    String found_username = result.getString("username");
                    String found_password = result.getString("password");
                    String found_nume = result.getString("nume");
                    String found_CNP = result.getString("CNP");
                    String found_adresa = result.getString("adresa");
                    String found_telefon = result.getString("telefon");
                    found_abonat = new Abonat(found_username,found_password,found_nume, found_CNP,found_adresa,found_telefon);
                    found_abonat.setId(id);
                }
                else throw new RepositoryException("Abonat not found!");
            }

        } catch (SQLException e) {
            logger.error(e);
            System.err.println("Error DB "+ e);
            //throw new RuntimeException(e);
        }
        logger.traceExit(found_abonat);
        return found_abonat;

    }
}
