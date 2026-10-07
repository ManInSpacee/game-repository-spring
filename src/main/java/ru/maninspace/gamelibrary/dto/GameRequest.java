package ru.maninspace.gamelibrary.dto;

import jakarta.validation.constraints.*;
import ru.maninspace.gamelibrary.entity.GameStatus;
import ru.maninspace.gamelibrary.entity.Genre;

import java.time.LocalDate;

public record GameRequest(
        @NotBlank @Size(max = 50) String title,
        @NotNull Genre genre,
        @PastOrPresent @NotNull LocalDate releaseDate,
        @PastOrPresent LocalDate completedAt,
        GameStatus status,
        @Min(1) @Max(10) Integer rating
) {

    public GameRequest {
        if (status == null) {
            status = GameStatus.NOT_STARTED;
        }
    }
}
