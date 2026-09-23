package se.nixonmc.duels.modules.duel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DuelRequestManager {
    private final Set<DuelRequest> requests = new HashSet<>();

    public void addRequest(DuelRequest request) {
        requests.add(request);
    }
    public void removeRequest(DuelRequest request) {
        requests.remove(request);
    }
    public DuelRequest getRequest(UUID playerId) {
        for (DuelRequest request : requests) {
            if (request.getTarget().equals(playerId)) {
                return request;
            }
        }
        return null;
    }
    public DuelRequest createRequest(UUID sender, UUID target) {
        if (hasRequest(sender, target)) {
            return null;
        }

        DuelRequest request = new DuelRequest(
                sender,
                target,
                System.currentTimeMillis()
        );

        addRequest(request);

        return request;
    }
    public boolean denyRequest(UUID target) {
        for (DuelRequest request : requests) {
            if (request.getTarget().equals(target)) {
                removeRequest(request);
                return true;
            }
        }
        return false;
    }
    public void cancelRequestsFromSender(UUID sender) {
        requests.removeIf(request ->
                request.getSender().equals(sender)
        );
    }
    public boolean hasRequest(UUID sender, UUID target) {
        for (DuelRequest request : requests) {
            if (request.getSender().equals(sender)
                    && request.getTarget().equals(target)) {
                return true;
            }
        }

        return false;
    }
}
