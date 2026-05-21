package fr.ekod.cda.ja.cineclub.repository;

import fr.ekod.cda.ja.cineclub.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
