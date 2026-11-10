package se.nixonmc.duels.modules.duel;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DuelRequestManager {

    private static final long REQUEST_EXPIRATION_MILLIS = 60_000L;
    private final Set<DuelRequest> requests = new HashSet<>();

    public void addRequest(DuelRequest request) {
        purgeExpiredRequests();
        requests.add(request);
    }

    public void removeRequest(DuelRequest request) {
        requests.remove(request);
    }

    public DuelRequest getRequest(UUID playerId) {
        purgeExpiredRequests();

        for (DuelRequest request : requests) {
            if (request.getTarget().equals(playerId)) {
                return request;
            }
        }

        return null;
    }

    public DuelRequest createRequest(UUID sender, UUID target, String kit, String map) {
        purgeExpiredRequests();

        if (sender.equals(target)
                || hasRequestWithoutExpiryCheck(sender, target)
                || hasIncomingRequestWithoutExpiryCheck(target)) {
            return null;
        }

        DuelRequest request = new DuelRequest(
                sender, target, kit, map, System.currentTimeMillis()
        );
        addRequest(request);
        return request;
    }

    public boolean denyRequest(UUID target) {
        DuelRequest request = getRequest(target);
        if (request == null) {
            return false;
        }

        removeRequest(request);
        return true;
    }

    public void cancelRequestsFromSender(UUID sender) {
        purgeExpiredRequests();
        requests.removeIf(request -> request.getSender().equals(sender));
    }

    public void cancelRequestsForPlayer(UUID playerId) {
        purgeExpiredRequests();
        requests.removeIf(request ->
                request.getSender().equals(playerId)
                        || request.getTarget().equals(playerId)
        );
    }

    public boolean hasRequest(UUID sender, UUID target) {
        purgeExpiredRequests();
        return hasRequestWithoutExpiryCheck(sender, target);
    }

    public boolean hasIncomingRequest(UUID target) {
        purgeExpiredRequests();
        return hasIncomingRequestWithoutExpiryCheck(target);
    }

    private boolean hasIncomingRequestWithoutExpiryCheck(UUID target) {
        for (DuelRequest request : requests) {
            if (request.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRequestWithoutExpiryCheck(UUID sender, UUID target) {
        for (DuelRequest request : requests) {
            if (request.getSender().equals(sender)
                    && request.getTarget().equals(target)) {
                return true;
            }
        }
        return false;
    }

    private void purgeExpiredRequests() {
        long now = System.currentTimeMillis();
        requests.removeIf(request ->
                now - request.getCreatedAt() >= REQUEST_EXPIRATION_MILLIS
        );
    }
}
