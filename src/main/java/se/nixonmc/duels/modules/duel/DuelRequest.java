package se.nixonmc.duels.modules.duel;

import java.util.UUID;

public class DuelRequest {
    private final UUID sender;
    private final UUID target;
    private final long createdAt;

    public DuelRequest(UUID sender, UUID target, long createdAt) {
        this.sender = sender;
        this.target = target;
        this.createdAt = createdAt;
    }

    public UUID getSender() {
        return sender;
    }
    public UUID getTarget() {
        return target;
    }
    public long getCreatedAt() {
        return createdAt;
    }
}
