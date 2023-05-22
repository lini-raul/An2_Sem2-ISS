package repository;

import domain.Carte;
import domain.Review;

import java.util.List;

public interface ReviewRepository extends Repository<Review, Integer>{
    List<Review> findByCarte(Carte carte);
}
