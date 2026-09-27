package se.nixonmc.duels.modules.duel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import se.nixonmc.duels.modules.kit.Kit;

public class DuelKitMenuListener implements Listener {

    private final DuelKitMenu kitMenu;

    public DuelKitMenuListener(DuelKitMenu kitMenu) {
        this.kitMenu = kitMenu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Select a Kit")) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        Kit kit = kitMenu.getKit(event.getSlot());

        if (kit == null) {
            return;
        }

        player.sendMessage("You selected: " + kit.getName());
    }
}