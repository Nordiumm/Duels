package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DuelEndListener implements Listener {

    private final DuelsCore core;
    private final DuelSystem duelSystem;
    private final ArenaManager arenaManager;

    private final Map<UUID, Duel> pendingRespawns = new HashMap<>();

    public DuelEndListener(
            DuelsCore core,
            DuelSystem duelSystem,
            ArenaManager arenaManager
    ) {
        this.core = core;
        this.duelSystem = duelSystem;
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();

        Duel duel = duelSystem.getDuel(deadPlayer.getUniqueId());

        if (duel == null || duel.getState() != DuelState.IN_PROGRESS) {
            return;
        }

        event.getDrops().clear();
        event.setDroppedExp(0);

        UUID deadPlayerId = deadPlayer.getUniqueId();
        Location returnLocation = getReturnLocation(duel.getArena().getWorld());

        if (returnLocation == null) {
            core.getPlugin().getLogger().severe(
                    "[Duels] No lobby or fallback world is available. The duel will still be finished."
            );
        }

        // Skip the death screen for this duel world.
        deadPlayer.getWorld().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

        // The dead player will be sent to the lobby by PlayerRespawnEvent.
        pendingRespawns.put(deadPlayerId, duel);

        for (UUID playerId : duel.getPlayers()) {
            if (playerId.equals(deadPlayerId)) {
                continue;
            }

            Player winner = Bukkit.getPlayer(playerId);

            if (winner != null && winner.isOnline()) {
                winner.sendMessage("You won the duel!");
                if (returnLocation != null && !winner.teleport(returnLocation)) {
                    core.getPlugin().getLogger().warning(
                            "[Duels] Could not teleport the winner to the return location."
                    );
                }
            }
        }

        deadPlayer.sendMessage("You lost the duel!");

        // Finish and unregister the duel once, when the death is confirmed.
        duelSystem.finishDuel(duel);
        duelSystem.removeDuel(duel);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        Duel duel = pendingRespawns.get(playerId);

        if (duel == null) {
            return;
        }

        Location returnLocation = getReturnLocation(duel.getArena().getWorld());

        if (returnLocation != null) {
            event.setRespawnLocation(returnLocation);
        } else {
            core.getPlugin().getLogger().severe(
                    "[Duels] No safe return location is available for this respawn."
            );
        }

        Bukkit.getScheduler().runTask(core.getPlugin(), () -> {
            Player player = Bukkit.getPlayer(playerId);

            if (player == null || !player.isOnline()) {
                cleanupArena(duel);
                return;
            }

            if (player.getWorld().equals(duel.getArena().getWorld())) {
                Location fallback = getReturnLocation(duel.getArena().getWorld());
                if (fallback == null || !player.teleport(fallback)) {
                    core.getPlugin().getLogger().severe(
                            "[Duels] Could not move the respawned player out of the arena; arena cleanup was postponed."
                    );
                    return;
                }
            }

            cleanupArena(duel);
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID playerId = event.getPlayer().getUniqueId();
        duelSystem.cancelRequestsForPlayer(playerId);

        Duel duel = duelSystem.getDuel(playerId);
        if (duel == null) {
            Duel pendingDuel = pendingRespawns.remove(playerId);
            if (pendingDuel != null) {
                cleanupArena(pendingDuel);
            }
            return;
        }

        if (duel.getState() != DuelState.IN_PROGRESS) {
            return;
        }

        Location lobby = getLobbyLocation();
        for (UUID opponentId : duel.getPlayers()) {
            if (opponentId.equals(playerId)) continue;

            Player opponent = Bukkit.getPlayer(opponentId);
            if (opponent != null && opponent.isOnline()) {
                opponent.sendMessage("Your opponent left the duel. You won!");
                if (lobby != null) {
                    opponent.teleport(lobby);
                }
            }
        }

        duelSystem.finishDuel(duel);
        duelSystem.removeDuel(duel);
        cleanupArena(duel);
    }

    private void cleanupArena(Duel duel) {
        pendingRespawns.values().removeIf(pending -> pending == duel);
        duel.getArena().SetPlayers(null, null);
        arenaManager.removeArena(duel.getArena());
    }

    private Location getReturnLocation(World arenaWorld) {
        Location lobby = getLobbyLocation();
        if (lobby != null && !lobby.getWorld().equals(arenaWorld)) {
            return lobby;
        }

        for (World world : Bukkit.getWorlds()) {
            if (!world.equals(arenaWorld)) {
                return world.getSpawnLocation();
            }
        }

        return null;
    }

    private Location getLobbyLocation() {
        FileConfiguration config = core.getConfigManager().get("config.yml");

        String worldName = config.getString("lobby.world", "world");
        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            core.getPlugin().getLogger().severe(
                    "Lobby world '" + worldName + "' is not loaded!"
            );
            return null;
        }

        return new Location(
                world,
                config.getDouble("lobby.x", 0.5),
                config.getDouble("lobby.y", 80.0),
                config.getDouble("lobby.z", 0.5),
                (float) config.getDouble("lobby.yaw", 0.0),
                (float) config.getDouble("lobby.pitch", 0.0)
        );
    }
}