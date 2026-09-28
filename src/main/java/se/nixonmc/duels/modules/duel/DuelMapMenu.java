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

public class DuelMapMenu {

    private final ArenaManager arenaManager;
    private final Map<Integer, String> mapsBySlot = new HashMap<>();

    public DuelMapMenu(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                "Select a Map"
        );

        mapsBySlot.clear();

        int mapCount = arenaManager.arenaDefinitions.size();

        if (mapCount == 0) {
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
            mapsBySlot.put(slot, arenaId);

            slot++;
        }

        player.openInventory(inventory);
    }

    public String getMap(int slot) {
        return mapsBySlot.get(slot);
    }
}