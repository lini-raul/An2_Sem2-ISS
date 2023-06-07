package controller;

import domain.Abonat;
import domain.Bibliotecar;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import repository.RepositoryException;
import service.Service;

import java.io.IOException;

public class LoginController {

    private Service service;
    Stage dialogStage;

    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldPassword;
    @FXML
    Button buttonLoginAbonat;
    @FXML
    Button buttonLoginBibliotecar;

    public void setService(Service service, Stage stage){
        this.service = service;
        this.dialogStage = stage;
    }


    public void handleLoginAbonat(){
        String username= textFieldUsername.getText();
        String password = textFieldPassword.getText();
            try{
                Abonat abonat = service.findAbonatByCredentials(username,password);

                Stage stage = new Stage();
                stage.setTitle("Biblioteca");
                FXMLLoader bibliotecaUiLoader = new FXMLLoader();
                bibliotecaUiLoader.setLocation(getClass().getResource("/GUI/user-main-view.fxml"));

                AnchorPane bibliotecaUiLayout = bibliotecaUiLoader.load();

                MainAbonatController mainAbonatController = bibliotecaUiLoader.getController();
                mainAbonatController.setService(service,stage,abonat);

                stage.setScene((new Scene(bibliotecaUiLayout)));
                stage.setWidth(1000);
                stage.show();
                this.dialogStage.hide();

            } catch (RepositoryException e) {
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
            } catch(IOException e){
                throw new RuntimeException(e);
            }


    }
    public void handleLoginBibliotecar(){
        String username= textFieldUsername.getText();
        String password = textFieldPassword.getText();

        try{
            Bibliotecar bibliotecar = service.findBibliotecarByCredentials(username,password);


            Stage stage = new Stage();
            stage.setTitle("Biblioteca");
            FXMLLoader bibliotecaUiLoader = new FXMLLoader();
            bibliotecaUiLoader.setLocation(getClass().getResource("/GUI/admin-main-view.fxml"));

            AnchorPane bibliotecaUiLayout = bibliotecaUiLoader.load();

            MainBibliotecarController mainBibliotecarController = bibliotecaUiLoader.getController();
            mainBibliotecarController.setService(service,stage,bibliotecar);

            stage.setScene((new Scene(bibliotecaUiLayout)));
            stage.setWidth(1000);
            stage.show();

        } catch (RepositoryException e) {
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
