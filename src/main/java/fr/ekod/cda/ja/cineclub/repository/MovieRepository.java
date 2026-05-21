package fr.ekod.cda.ja.cineclub.repository;

import fr.ekod.cda.ja.cineclub.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByDirectorId(Integer directorId);

    List<Movie> findByGenreIgnoreCase(String genre);
}
