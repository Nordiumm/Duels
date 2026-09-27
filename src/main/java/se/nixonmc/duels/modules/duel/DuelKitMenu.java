package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import se.nixonmc.duels.modules.kit.Kit;
import se.nixonmc.duels.modules.kit.KitSystem;

import java.util.HashMap;
import java.util.Map;

public class DuelKitMenu {

    private final KitSystem kitSystem;
    private final Map<Integer, Kit> kitsBySlot = new HashMap<>();

    public DuelKitMenu(KitSystem kitSystem) {
        this.kitSystem = kitSystem;
    }

    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                "Select a Kit"
        );

        kitsBySlot.clear();

        int kitCount = kitSystem.getKitManager().getKits().size();

        if (kitCount == 0) {
            player.openInventory(inventory);
            return;
        }

        int startSlot = 13 - (kitCount / 2);

        int slot = startSlot;

        for (Kit kit : kitSystem.getKitManager().getKits().values()) {
            if (slot > 17) {
                break;
            }

            inventory.setItem(slot, kit.getIcon());
            kitsBySlot.put(slot, kit);

            slot++;
        }

        player.openInventory(inventory);
    }

    public Kit getKit(int slot) {
        return kitsBySlot.get(slot);
    }
}