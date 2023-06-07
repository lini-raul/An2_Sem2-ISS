package controller;

import domain.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class MainBibliotecarController implements Observer {

    private Service service;
    private Stage dialogStage;
    private Bibliotecar bibliotecar;

    private ObservableList<Carte> carti = FXCollections.observableArrayList();
    private ObservableList<Imprumut> imprumuturi  = FXCollections.observableArrayList();

    @FXML
    TableView<Imprumut> tableViewImprumuturi;
    @FXML
    TableColumn<Imprumut,Integer> tableColumnIDImprumut;
    @FXML
    TableColumn<Imprumut,String> tableColumnIDAbonat;
    @FXML
    TableColumn<Imprumut, String> tableColumnDataImprumut;
    @FXML
    TableColumn<Imprumut,String> tableColumnStatusImprumut;
    @FXML
    Button buttonRestituieImprumut;
    @FXML
    TextField textFieldTitlu;
    @FXML
    TextField textFieldAutor;
    @FXML
    TextField textFieldNrExemplare;
    @FXML
    Button buttonAdaugaCarte;
    @FXML
    TableView<Carte> tableViewCarti;
    @FXML
    TableColumn<Carte, String> tableColumnTitlu;
    @FXML
    TableColumn<Carte, String> tableColumnAutor;
    @FXML
    TableColumn<Carte, Integer> tableColumnNrExemplare;
    @FXML
    Button buttonStergeCarte;



    public void setService(Service service, Stage stage,Bibliotecar bibliotecar){
        this.service = service;
        this.dialogStage = stage;
        this.bibliotecar = bibliotecar;
        service.addObserver(this);

        initialiseBibliotecarController();
    }

    private void initialiseBibliotecarController() {
        List<Imprumut> impr = new ArrayList<>();
        service.findAllImprumuturi().forEach(impr::add);

        imprumuturi = FXCollections.observableArrayList(impr);

        tableViewImprumuturi.getItems().clear();
        tableViewImprumuturi.setItems(imprumuturi);

        tableColumnIDImprumut.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnIDAbonat.setCellValueFactory(new PropertyValueFactory<>("idAbonat"));
        tableColumnDataImprumut.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnStatusImprumut.setCellValueFactory(new PropertyValueFactory<>("status"));



        List<Carte> c = new ArrayList<>();
        service.findAllCarti().forEach(c::add);

        carti = FXCollections.observableArrayList(c);

        tableViewCarti.getItems().clear();
        tableViewCarti.setItems(carti);

        tableColumnTitlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnNrExemplare.setCellValueFactory(new PropertyValueFactory<>("nrExemplare"));


    }

    public void handleRestituieImprumut(){
        if(tableViewImprumuturi.getSelectionModel().getSelectedItem()!=null){
            Imprumut imprumut = tableViewImprumuturi.getSelectionModel().getSelectedItem();
            if(imprumut.getStatus().equals(StatusImprumut.IMPRUMUTAT)){
                try {
                    service.returneazaImrumut(imprumut);

                    MessageAlert messageAlert = null;
                    messageAlert.showMessage(this.dialogStage, "Imprumut returnat cu succes!");
                } catch (RepositoryException e) {
                    MessageAlert messageAlert = null;
                    messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
                }
            }
            else{
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, "Imprumutul a fost deja returnat!\nSelectati un imprumut nepredat!");
            }

        }
        else{
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, "Niciun imprumut selectat!!");
        }
    }

    public void handleAdaugaCarte(){
        try{
            String titlu = textFieldTitlu.getText();
            String autor = textFieldAutor.getText();
            int nrExemplare = Integer.parseInt(textFieldNrExemplare.getText());

            service.addCarte(titlu, autor, nrExemplare);
            MessageAlert messageAlert = null;
            messageAlert.showMessage(this.dialogStage, "Carte adaugata cu succes!");
        }
        catch (NumberFormatException e) {
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, "Dati un numar valid de exemplare!");
        } catch (ValidateException e) {
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
        }
    }

    public void handleStergeCarte(){
        if(tableViewCarti.getSelectionModel().getSelectedItem()!=null){
            Carte c = tableViewCarti.getSelectionModel().getSelectedItem();
            service.deleteCarte(c);
            MessageAlert messageAlert = null;
            messageAlert.showMessage(this.dialogStage, "Carte stearsa cu succes!");

        }
        else {
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, "Nicio carte selectata!!");
        }

    }

    @Override
    public void update() {
        initialiseBibliotecarController();
    }
}
