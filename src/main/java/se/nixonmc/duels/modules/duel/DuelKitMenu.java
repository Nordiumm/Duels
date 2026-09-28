package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import se.nixonmc.duels.modules.kit.Kit;
import se.nixonmc.duels.modules.kit.KitSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DuelKitMenu {

    private final KitSystem kitSystem;
    private final Map<Integer, Kit> kitsBySlot = new HashMap<>();
    private final Map<UUID, DuelSetup> setups = new HashMap<>();
    private final Set<UUID> transitioning = new HashSet<>();

    public DuelKitMenu(KitSystem kitSystem) {
        this.kitSystem = kitSystem;
    }

    public void open(Player player, Player target) {
        DuelSetup setup = new DuelSetup(player, target);
        setups.put(player.getUniqueId(), setup);

        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                "Select a Kit"
        );

        kitsBySlot.clear();

        int kitCount = kitSystem.getKitManager().getKits().size();

        if (kitCount > 0) {
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
        }

        player.openInventory(inventory);
    }

    public Kit getKit(int slot) {
        return kitsBySlot.get(slot);
    }

    public DuelSetup getSetup(Player player) {
        return setups.get(player.getUniqueId());
    }

    public void removeSetup(Player player) {
        setups.remove(player.getUniqueId());
        transitioning.remove(player.getUniqueId());
    }

    public void markTransition(Player player) {
        transitioning.add(player.getUniqueId());
    }

    public boolean consumeTransition(Player player) {
        return transitioning.remove(player.getUniqueId());
    }
}