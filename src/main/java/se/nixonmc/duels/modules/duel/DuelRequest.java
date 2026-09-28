package se.nixonmc.duels.modules.duel;

import java.util.UUID;

public class DuelRequest {

    private final UUID sender;
    private final UUID target;
    private final String kit;
    private final String map;
    private final long createdAt;

    public DuelRequest(
            UUID sender,
            UUID target,
            String kit,
            String map,
            long createdAt
    ) {
        this.sender = sender;
        this.target = target;
        this.kit = kit;
        this.map = map;
        this.createdAt = createdAt;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getTarget() {
        return target;
    }

    public String getKit() {
        return kit;
    }

    public String getMap() {
        return map;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}