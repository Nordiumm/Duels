package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.modules.ArenaSystem.Arena;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class Duel {

    private final UUID id;
    private final Arena arena;
    private final Set<UUID> players = new HashSet<>();
    private DuelState state;

    public Duel(UUID id, DuelState state, Arena arena) {
        this.id = id;
        this.state = state;
        this.arena = arena;
    }

    public UUID getId() {
        return id;
    }

    public Arena getArena() {
        return arena;
    }

    public Set<UUID> getPlayers() {
        return players;
    }

    public DuelState getState() {
        return state;
    }

    public void setState(DuelState state) {
        this.state = state;
    }

    public void addPlayer(UUID uuid) {
        players.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }
}