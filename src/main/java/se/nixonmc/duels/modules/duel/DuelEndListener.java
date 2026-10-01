package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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

        for (UUID playerId : duel.getPlayers()) {
            if (playerId.equals(deadPlayerId)) {
                continue;
            }

            Player winner = Bukkit.getPlayer(playerId);

            if (winner != null && winner.isOnline()) {
                winner.sendMessage("You won the duel!");

                Location lobby = getLobbyLocation();

                if (lobby != null) {
                    winner.teleport(lobby);
                }
            }
        }

        deadPlayer.sendMessage("You lost the duel!");

        Location lobby = getLobbyLocation();

        if (lobby != null) {
            pendingRespawns.put(deadPlayerId, duel);
        }

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

        Location lobby = getLobbyLocation();

        if (lobby == null) {
            return;
        }

        event.setRespawnLocation(lobby);

        Bukkit.getScheduler().runTask(core.getPlugin(), () -> {
            Player player = Bukkit.getPlayer(playerId);

            if (player == null || player.getWorld().equals(duel.getArena().getWorld())) {
                return;
            }

            pendingRespawns.remove(playerId);

            duel.getArena().SetPlayers(null, null);
            arenaManager.removeArena(duel.getArena());
        });
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