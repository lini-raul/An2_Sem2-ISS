package controller;

import domain.Abonat;
import domain.Carte;
import domain.Review;
import domain.ValidateException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.RepositoryException;
import service.Service;
import utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainAbonatController  implements Observer {
    
    
    private Service service;
    private Stage dialogStage;
    private Abonat abonat;
    private ObservableList<Carte> carti = FXCollections.observableArrayList();
    private ObservableList<Review> reviews = FXCollections.observableArrayList();

    @FXML
    TableView<Carte> tableViewCarti;
    @FXML
    TableColumn<Carte, String> tableColumnTitlu;
    @FXML
    TableColumn<Carte, String> tableColumnAutor;
    @FXML
    TableColumn<Carte, Integer> tableColumnNrExemplare;
    @FXML
    TableView<Review> tableViewReviews;
    @FXML
    TableColumn<Review, String> tableColumnReview;
    @FXML
    TableColumn<Review, String> tableColumnAbonat;
    @FXML
    TextArea textAreaReview;
    @FXML
    Button buttonAddReview;
    @FXML
    TextField textFieldNrExemplare;
    @FXML
    Button buttonAddImprumut;


    public void setService(Service service, Stage stage,Abonat abonat){
        this.service = service;
        this.dialogStage = stage;
        this.abonat = abonat;
        service.addObserver(this);

        initialiseBibliotecaController();
    }

    private void initialiseBibliotecaController() {
        List<Carte> c = new ArrayList<>();
        service.findAllCarti().forEach(c::add);

        carti = FXCollections.observableArrayList(c);

        tableViewCarti.getItems().clear();
        tableViewCarti.setItems(carti);

        tableColumnTitlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnNrExemplare.setCellValueFactory(new PropertyValueFactory<>("nrExemplare"));


        tableViewCarti.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(tableViewCarti.getSelectionModel().getSelectedItem() != null)
                {
                    loadReviewsOnScreen();
                }
            }
        });
    }

    private void loadReviewsOnScreen() {
        Carte carte = tableViewCarti.getSelectionModel().getSelectedItem();
        List<Review> r  = service.findReviewsByCarte(carte);
        tableViewReviews.getItems().clear();
        reviews = FXCollections.observableArrayList(r);

        //
        tableViewReviews.setItems(reviews);

        tableColumnReview.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        tableColumnAbonat.setCellValueFactory(new PropertyValueFactory<>("idAbonat"));


    }

    public void handleAddReview(){
        if(tableViewCarti.getSelectionModel().getSelectedItem()!=null){

            if(!textAreaReview.getText().isEmpty()){
                String descriere = textAreaReview.getText();
                int idAbonat = abonat.getId();
                Carte carte = tableViewCarti.getSelectionModel().getSelectedItem();
                int idCarte = carte.getId();
                service.addReview(idAbonat,idCarte,descriere);


            }
            else{
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, "Please insert the review text!");
            }




        }
        else{
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, "No book selected!!");
        }

    }

    public void handleAddImprumut(){
        if(tableViewCarti.getSelectionModel().getSelectedItem()!=null){
            try{
                LocalDate data = LocalDate.now();
                int nrExemplare = Integer.parseInt(textFieldNrExemplare.getText()) ;
                Carte c = tableViewCarti.getSelectionModel().getSelectedItem();
                int idCarte = c.getId();
                int idAbonat = abonat.getId();
                service.addImprumut(data,nrExemplare,idCarte,idAbonat);
            } catch (ValidateException e) {
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
            } catch (RepositoryException e) {
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, e.getMessage());
            } catch (NumberFormatException e){
                MessageAlert messageAlert = null;
                messageAlert.showErrorMessage(this.dialogStage, "Dati un numar valid de exemplare!");
            }

        }
        else{
            MessageAlert messageAlert = null;
            messageAlert.showErrorMessage(this.dialogStage, "Va rugam selectati o carte!!");

        }
    }

    @Override
    public void update() {
        loadReviewsOnScreen();
        initialiseBibliotecaController();

    }
}
