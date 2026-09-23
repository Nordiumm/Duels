package se.nixonmc.duels.modules.duel;

import se.nixonmc.duels.modules.kit.KitSystem;

import java.util.UUID;

public class DuelSystem {
    private final DuelManager duelManager;
    private final DuelRequestManager requestManager;
    private final KitSystem kitSystem;

    public DuelSystem(
            DuelManager duelManager,
            DuelRequestManager requestManager,
            KitSystem kitSystem
    ) {
        this.duelManager = duelManager;
        this.requestManager = requestManager;
        this.kitSystem = kitSystem;
    }

    public Duel createDuel() {
        return duelManager.createDuel();
    }
    public Duel acceptRequest(UUID target) {
        DuelRequest request = requestManager.getRequest(target);

        if (request == null) {
            return null;
        }

        Duel duel = createDuel();

        addPlayerToDuel(duel, request.getSender());
        addPlayerToDuel(duel, request.getTarget());

        requestManager.cancelRequestsFromSender(request.getSender());
        requestManager.cancelRequestsFromSender(request.getTarget());

        return duel;
    }

    public boolean denyRequest(UUID target) {
        return requestManager.denyRequest(target);
    }
    public DuelRequest createRequest(UUID sender, UUID target) {
        if (duelManager.isInDuel(sender)) {
            return null;
        }

        if (duelManager.isInDuel(target)) {
            return null;
        }

        return requestManager.createRequest(sender, target);
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
