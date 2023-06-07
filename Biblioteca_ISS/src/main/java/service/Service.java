package service;
import domain.*;
import repository.*;
import utils.observer.Observable;
import utils.observer.Observer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Service implements Observable{
    private AbonatRepository abonatRepo;
    private BibliotecarRepository bibliotecarRepo;
    private CarteRepository carteRepo;
    private ImprumutRepository imprumutRepo;
    private ReviewRepository reviewRepo;
    private ValidatorImprumut validatorImprumut;

    private ValidatorCarte validatorCarte;

    public Service(AbonatRepository abonatRepo, BibliotecarRepository bibliotecarRepo, CarteRepository carteRepo, ImprumutRepository imprumutRepo, ReviewRepository reviewRepo, ValidatorImprumut validatorImprumut, ValidatorCarte validatorCarte) {
        this.abonatRepo = abonatRepo;
        this.bibliotecarRepo = bibliotecarRepo;
        this.carteRepo = carteRepo;
        this.imprumutRepo = imprumutRepo;
        this.reviewRepo = reviewRepo;
        this.validatorImprumut = validatorImprumut;
        this.validatorCarte = validatorCarte;
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

    public List<Imprumut> findAllImprumuturi(){
        List<Imprumut> imprumuturi = (List<Imprumut>) imprumutRepo.findAll();
        return imprumuturi;
    }

    public List<Carte> filterCartiByTitlu(String titlu){
        List<Carte> cartiFiltered = findAllCarti().stream().filter(c->c.getTitlu().contains(titlu)).collect(Collectors.toList());
        return cartiFiltered;
    }

    public List<Carte> filterCartiByAutor(String autor){
        List<Carte> cartiFiltered = findAllCarti().stream().filter(c->c.getAutor().contains(autor)).collect(Collectors.toList());
        return cartiFiltered;
    }


    public void addReview(int idAbonat, int idCarte, String descriere) {
        Review review = new Review(idAbonat,idCarte,descriere);
        reviewRepo.add(review);
        notifyObservers();

    }

    public void addCarte(String titlu,String autor, int nrExemplare) throws ValidateException {
        Carte carte = new Carte(titlu, autor, nrExemplare);
        validatorCarte.validate(carte);
        carteRepo.add(carte);
        notifyObservers();
    }

    public void deleteCarte(Carte carte){
        carteRepo.delete(carte);
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

    public void returneazaImrumut(Imprumut imprumut) throws RepositoryException {
        imprumut.setStatus(StatusImprumut.PREDAT);
        imprumutRepo.update(imprumut);

        Carte carte = carteRepo.findById(imprumut.getIdCarte());
        carte.setNrExemplare(carte.getNrExemplare() + imprumut.getNrExemplare());

        carteRepo.update(carte);
        notifyObservers();

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
