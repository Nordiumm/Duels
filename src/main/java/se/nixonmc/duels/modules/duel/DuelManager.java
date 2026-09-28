package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.modules.ArenaSystem.Arena;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DuelManager {
    private final Set<Duel> duels = new HashSet<>();

    public void addDuel(Duel duel) {
        duels.add(duel);
    }
    public void removeDuel(Duel duel) {
        duels.remove(duel);
    }
    public Set<Duel> getDuels() {
        return Collections.unmodifiableSet(duels);
    }
    public Duel findDuel(UUID playerId) {
        for (Duel duel : duels) {
            if (duel.getPlayers().contains(playerId)) {
                return duel;
            }
        }
        return null;
    }
    public Duel createDuel(Arena arena) {
        UUID duelId = UUID.randomUUID();
        Duel duel = new Duel(duelId, DuelState.STARTING, arena);
        addDuel(duel);
        return duel;
    }
    public boolean isInDuel(UUID playerId) {
        return findDuel(playerId) != null;
    }
    public boolean addPlayerToDuel(Duel duel, UUID playerId) {
        if (isInDuel(playerId)) {
            return false;
        }
        duel.addPlayer(playerId);
        return true;
    }
    public boolean removePlayerFromDuel(Duel duel, UUID playerId) {
        if (!duel.getPlayers().contains(playerId)) {
            return false;
        }
        duel.removePlayer(playerId);
        return true;
    }
}
