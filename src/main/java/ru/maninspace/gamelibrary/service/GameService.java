package ru.maninspace.gamelibrary.service;

import org.springframework.stereotype.Service;
import ru.maninspace.gamelibrary.dto.GameRequest;
import ru.maninspace.gamelibrary.entity.Game;
import ru.maninspace.gamelibrary.entity.GameStatus;
import ru.maninspace.gamelibrary.exception.GameAlreadyExistsException;
import ru.maninspace.gamelibrary.exception.GameNotFoundException;
import ru.maninspace.gamelibrary.exception.InvalidGameDataException;
import ru.maninspace.gamelibrary.repository.GameRepository;

import java.util.List;


@Service
public class GameService {

    private final GameRepository gameRepo;

    public GameService(GameRepository gameRepo) {
        this.gameRepo = gameRepo;
    }

    private void applyRequest(GameRequest gameRequest, Game game) {
        game.setTitle(gameRequest.title());
        game.setGenre(gameRequest.genre());
        game.setReleaseDate(gameRequest.releaseDate());
        game.setCompletedAt(gameRequest.completedAt());
        game.setStatus(gameRequest.status());
        game.setRating(gameRequest.rating());
    }


    private void validateCompletion(Game game) {
        if (game.getCompletedAt() != null && game.getCompletedAt().isBefore(game.getReleaseDate())) {
            throw new InvalidGameDataException("Дата прохождения не может быть раньше выхода игры");
        }
        if (game.getStatus() != GameStatus.COMPLETED && game.getCompletedAt() != null) {
            throw new InvalidGameDataException("Нельзя указать дату прохождения без завершения игры");
        }
    }

    public List<Game> findAll() {
        return gameRepo.findAll();
    }

    public Game findById(Long gameId) {
        return gameRepo.findById(gameId).orElseThrow(() -> new GameNotFoundException("Игра с id " + gameId + " не найдена"));
    }

    public Game create(GameRequest request) {
        Game game = new Game();
        applyRequest(request, game);
        validateCompletion(game);
        if (gameRepo.existsByTitle(game.getTitle())) {
            throw new GameAlreadyExistsException("Игра с таким названием уже существует");
        }
        return gameRepo.save(game);
    }

    public Game update(Long gameId, GameRequest updatedGame) {
        Game game = findById(gameId);
        applyRequest(updatedGame, game);
        validateCompletion(game);
        if (gameRepo.existsByTitleAndIdNot(game.getTitle(), gameId)) {
            throw new GameAlreadyExistsException("Игра с таким названием уже существует");
        }
        return gameRepo.save(game);

    }

    public void delete(Long gameId) {
        Game game = findById(gameId);
        gameRepo.delete(game);
    }

}
