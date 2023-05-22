package controller;

import domain.Abonat;
import domain.Bibliotecar;
import javafx.stage.Stage;
import service.Service;
import utils.observer.Observer;

public class MainBibliotecarController implements Observer {

    private Service service;
    private Stage dialogStage;
    private Bibliotecar bibliotecar;

    public void setService(Service service, Stage stage,Bibliotecar bibliotecar){
        this.service = service;
        this.dialogStage = stage;
        this.bibliotecar = bibliotecar;
        service.addObserver(this);

        initialiseBibliotecaController();
    }

    private void initialiseBibliotecaController() {
    }


    @Override
    public void update() {

    }
}
