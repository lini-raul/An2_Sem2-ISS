package service;
import domain.*;
import repository.*;
import utils.observer.Observable;
import utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service implements Observable{
    private AbonatRepository abonatRepo;
    private BibliotecarRepository bibliotecarRepo;
    private CarteRepository carteRepo;
    private ImprumutRepository imprumutRepo;
    private ReviewRepository reviewRepo;
    private ValidatorImprumut validatorImprumut;

    public Service(AbonatRepository abonatRepo, BibliotecarRepository bibliotecarRepo, CarteRepository carteRepo, ImprumutRepository imprumutRepo, ReviewRepository reviewRepo, ValidatorImprumut validatorImprumut) {
        this.abonatRepo = abonatRepo;
        this.bibliotecarRepo = bibliotecarRepo;
        this.carteRepo = carteRepo;
        this.imprumutRepo = imprumutRepo;
        this.reviewRepo = reviewRepo;
        this.validatorImprumut = validatorImprumut;
    }

    public Abonat findAbonatByCredentials(String username, String password) throws RepositoryException {
        Abonat abonat = abonatRepo.findByCredentials(username,password);
        return abonat;
    }
    public Bibliotecar findBibliotecarByCredentials(String username, String password) throws RepositoryException {
        Bibliotecar bibliotecar = bibliotecarRepo.findByCredentials(username, password);
        return bibliotecar;
    }

    public List<Carte> findAllCarti(){
        List<Carte> carti = (List<Carte>)carteRepo.findAll();
        return carti;
    }

    public void addReview(int idAbonat, int idCarte, String descriere) {
        Review review = new Review(idAbonat,idCarte,descriere);
        reviewRepo.add(review);
        notifyObservers();

    }

    public void addImprumut(LocalDate data, int nrExemplare, int idCarte,int idAbonat) throws ValidateException, RepositoryException {
        Imprumut imprumut = new Imprumut(data, StatusImprumut.IMPRUMUTAT,nrExemplare,idCarte, idAbonat);
        validatorImprumut.validate(imprumut);

        Carte carte = carteRepo.findById(idCarte);
        if(carte.getNrExemplare() < nrExemplare)
            throw new RepositoryException("Nu sunt destule exemplare!!");
        else{
            carte.setNrExemplare(carte.getNrExemplare()-nrExemplare);
            carteRepo.update(carte);
            imprumutRepo.add(imprumut);
            notifyObservers();
        }


    }

    public List<Review> findReviewsByCarte(Carte carte){
        List<Review> reviews = reviewRepo.findByCarte(carte);
        return reviews;
    }


    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void remoObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.stream().forEach(x->x.update());
    }
}
