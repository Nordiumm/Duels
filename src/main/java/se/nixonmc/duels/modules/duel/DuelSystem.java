package se.nixonmc.duels.modules.duel;

import java.util.UUID;

public class DuelSystem {
    private final DuelManager duelManager;

    public DuelSystem(DuelManager duelManager) {
        this.duelManager = duelManager;
    }

    public Duel createDuel() {
        return duelManager.createDuel();
    }
    public boolean addPlayerToDuel(Duel duel, UUID playerId) {
        return duelManager.addPlayerToDuel(duel, playerId);
    }
    public boolean removePlayerFromDuel(Duel duel, UUID playerId) {
        return duelManager.removePlayerFromDuel(duel, playerId);
    }
    public void startDuel(Duel duel) {
        duel.setState(DuelState.IN_PROGRESS);
    }
    public void finishDuel(Duel duel) {
        duel.setState(DuelState.FINISHED);
    }
    public void removeDuel(Duel duel) {
        duelManager.removeDuel(duel);
    }

}
