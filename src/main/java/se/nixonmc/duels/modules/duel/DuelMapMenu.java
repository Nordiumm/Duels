package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DuelMapMenu {

    private final ArenaManager arenaManager;
    private final Map<UUID, Map<Integer, String>> mapsByPlayer = new HashMap<>();

    public DuelMapMenu(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                "Select a Map"
        );

        Map<Integer, String> playerMaps = new HashMap<>();
        int mapCount = arenaManager.arenaDefinitions.size();

        if (mapCount == 0) {
            mapsByPlayer.remove(player.getUniqueId());
            player.sendMessage("There are no maps available.");
            return;
        }

        int startSlot = 13 - (Math.min(mapCount, 9) / 2);
        int slot = startSlot;

        for (String arenaId : arenaManager.arenaDefinitions.keySet()) {
            if (slot > 17) {
                break;
            }

            ItemStack item = new ItemStack(Material.MAP);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(arenaId);
            item.setItemMeta(meta);

            inventory.setItem(slot, item);
            playerMaps.put(slot, arenaId);
            slot++;
        }

        mapsByPlayer.put(player.getUniqueId(), playerMaps);
        player.openInventory(inventory);
    }

    public String getMap(UUID playerId, int slot) {
        Map<Integer, String> playerMaps = mapsByPlayer.get(playerId);
        return playerMaps == null ? null : playerMaps.get(slot);
    }

    public void removeMaps(UUID playerId) {
        mapsByPlayer.remove(playerId);
    }
}
