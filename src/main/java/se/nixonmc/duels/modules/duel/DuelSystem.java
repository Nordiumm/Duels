package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import se.nixonmc.duels.modules.ArenaSystem.Arena;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;
import se.nixonmc.duels.modules.kit.Kit;
import se.nixonmc.duels.modules.kit.KitSystem;

import java.util.UUID;

public class DuelSystem {

    private final DuelManager duelManager;
    private final DuelRequestManager requestManager;
    private final KitSystem kitSystem;
    private final ArenaManager arenaManager;

    public DuelSystem(
            DuelManager duelManager,
            DuelRequestManager requestManager,
            KitSystem kitSystem,
            ArenaManager arenaManager
    ) {
        this.duelManager = duelManager;
        this.requestManager = requestManager;
        this.kitSystem = kitSystem;
        this.arenaManager = arenaManager;
    }

    public Duel createDuel(Arena arena) {
        return duelManager.createDuel(arena);
    }

    public Duel getDuel(UUID playerId) {
        return duelManager.findDuel(playerId);
    }

    public Duel acceptRequest(UUID target) {
        DuelRequest request = requestManager.getRequest(target);

        if (request == null) {
            return null;
        }

        Player sender = Bukkit.getPlayer(request.getSender());
        Player receiver = Bukkit.getPlayer(request.getTarget());

        if (sender == null || receiver == null) {
            return null;
        }

        if (duelManager.isInDuel(sender.getUniqueId())
                || duelManager.isInDuel(receiver.getUniqueId())) {
            return null;
        }

        Kit kit = getKit(request.getKit());

        if (kit == null) {
            return null;
        }

        Arena arena = arenaManager.getAvailableArena(request.getMap());

        if (arena == null) {
            return null;
        }

        arena.SetPlayers(sender, receiver);

        Duel duel = createDuel(arena);

        addPlayerToDuel(duel, sender.getUniqueId());
        addPlayerToDuel(duel, receiver.getUniqueId());

        Location senderSpawn = arena.getPlayer1Spawn().clone();
        senderSpawn.setWorld(arena.getWorld());

        Location receiverSpawn = arena.getPlayer2Spawn().clone();
        receiverSpawn.setWorld(arena.getWorld());

        sender.teleport(senderSpawn);
        receiver.teleport(receiverSpawn);

        kitSystem.applyKit(sender, kit);
        kitSystem.applyKit(receiver, kit);

        startDuel(duel);

        requestManager.cancelRequestsFromSender(request.getSender());
        requestManager.cancelRequestsFromSender(request.getTarget());

        return duel;
    }

    public Kit getKit(String id) {
        return kitSystem.getKitManager().getKit(id);
    }

    public boolean denyRequest(UUID target) {
        return requestManager.denyRequest(target);
    }

    public DuelRequest createRequest(
            UUID sender,
            UUID target,
            String kit,
            String map
    ) {
        if (duelManager.isInDuel(sender)) {
            return null;
        }

        if (duelManager.isInDuel(target)) {
            return null;
        }

        return requestManager.createRequest(
                sender,
                target,
                kit,
                map
        );
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