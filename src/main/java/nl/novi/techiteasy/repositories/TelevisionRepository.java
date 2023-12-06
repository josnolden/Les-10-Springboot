package nl.novi.techiteasy.repositories;

import nl.novi.techiteasy.models.Television;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelevisionRepository extends JpaRepository<Television, Long> {
    Optional<Television> findById(Long id);
}
