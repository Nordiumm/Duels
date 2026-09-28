package se.nixonmc.duels.modules.duel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import se.nixonmc.duels.modules.kit.Kit;

public class DuelKitMenuListener implements Listener {

    private final DuelKitMenu kitMenu;
    private final DuelMapMenu mapMenu;

    public DuelKitMenuListener(
            DuelKitMenu kitMenu,
            DuelMapMenu mapMenu
    ) {
        this.kitMenu = kitMenu;
        this.mapMenu = mapMenu;
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

        Kit kit = kitMenu.getKit(event.getRawSlot());

        if (event.getRawSlot() < 0
                || event.getRawSlot() >= event.getView().getTopInventory().getSize()
                || kit == null) {
            return;
        }

        DuelSetup setup = kitMenu.getSetup(player);

        if (setup == null) {
            return;
        }

        setup.setKit(kit);

        kitMenu.markTransition(player);
        mapMenu.open(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!event.getView().getTitle().equals("Select a Kit")) {
            return;
        }

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (kitMenu.consumeTransition(player)) {
            return;
        }

        kitMenu.removeSetup(player);
    }
}