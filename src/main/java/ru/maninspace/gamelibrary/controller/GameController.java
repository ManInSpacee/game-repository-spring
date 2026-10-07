package ru.maninspace.gamelibrary.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.maninspace.gamelibrary.dto.GameRequest;
import ru.maninspace.gamelibrary.entity.Game;
import ru.maninspace.gamelibrary.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/games")
class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public List<Game> findAll() {
        return gameService.findAll();
    }

    @GetMapping("/{id}")
    public Game findById(@PathVariable Long id) {
        return gameService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Game create(@Valid @RequestBody GameRequest request) {
        return gameService.create(request);
    }

    @PutMapping("/{id}")
    public Game update(@PathVariable Long id, @Valid @RequestBody GameRequest request) {
        return gameService.update(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gameService.delete(id);
    }

}
