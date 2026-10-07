package ru.maninspace.gamelibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maninspace.gamelibrary.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByTitle(String title);

    Game findByTitle(String title);
    boolean existsByTitleAndIdNot(String title, Long id);
}
