package utils.observer;

public interface Observable {
    void addObserver(Observer e);
    void remoObserver(Observer e);
    void notifyObservers();

}
