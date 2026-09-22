package se.nixonmc.duels.core.player;

import se.nixonmc.duels.core.DuelsCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerManager {

    private final DuelsCore core;

    private final Map<UUID, PlayerContext> players = new HashMap<>();

    public PlayerManager(DuelsCore core) {
        this.core = core;
    }

    public PlayerContext get(UUID uniqueId) {
        return players.computeIfAbsent(
                uniqueId,
                PlayerContext::new
        );
    }

    public boolean has(UUID uniqueId) {
        return players.containsKey(uniqueId);
    }

    public void remove(UUID uniqueId) {
        players.remove(uniqueId);
    }

    public void clear() {
        players.clear();
    }
}