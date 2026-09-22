package se.nixonmc.duels.core.player;

import java.util.UUID;

public final class PlayerContext {

    private final UUID uniqueId;

    public PlayerContext(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }
}