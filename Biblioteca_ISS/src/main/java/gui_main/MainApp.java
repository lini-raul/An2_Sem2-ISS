package gui_main;

import controller.LoginController;
import domain.ValidatorCarte;
import domain.ValidatorImprumut;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repository.*;
import repository.database.*;
import repository.orm.*;
import service.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {

    Service srv;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties props = new Properties();
        try {
            props.load(new FileReader("db.config"));
        } catch(IOException e){
            System.out.println("Cannot find bd.config " +e);
        }

        //AbonatRepository abonatRepository = new AbonatDBRepository(props);
        //BibliotecarRepository bibliotecarRepository = new BibliotecarDBRepository(props);
        //ReviewRepository reviewRepository = new ReviewDBRepository(props);
        //CarteRepository carteRepository = new CarteDBRepository(props);
        AbonatRepository abonatRepository = new AbonatORMRepository();
        BibliotecarRepository bibliotecarRepository = new BibliotecarORMRepository();
        ReviewRepository reviewRepository = new ReviewORMRepository();
        CarteRepository carteRepository = new CarteORMRepository();

        //couldnt make the ImprumutRepository with ORM
        //ImprumutRepository imprumutRepository = new ImprumutORMRepository();
        ImprumutRepository imprumutRepository = new ImprumutDBRepository(props);

        ValidatorImprumut validatorImprumut = new ValidatorImprumut();
        ValidatorCarte validatorCarte = new ValidatorCarte();
        srv = new Service(abonatRepository,bibliotecarRepository,carteRepository,imprumutRepository,reviewRepository, validatorImprumut, validatorCarte);

        initView(primaryStage);
        primaryStage.show();

    }

    private void initView(Stage primaryStage) throws IOException {

        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("/GUI/login-view.fxml"));

        AnchorPane mainLayout = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setService(srv, primaryStage);

        Scene scene = new Scene(mainLayout, 550, 430);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args){
        launch();
    }
}
