package se.nixonmc.duels.modules.example;

import java.util.UUID;

public final class ExampleDuel {

    private final UUID id;
    private final UUID playerOne;
    private final UUID playerTwo;

    private boolean started;

    public ExampleDuel(UUID playerOne, UUID playerTwo) {
        this.id = UUID.randomUUID();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerOne() {
        return playerOne;
    }

    public UUID getPlayerTwo() {
        return playerTwo;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}