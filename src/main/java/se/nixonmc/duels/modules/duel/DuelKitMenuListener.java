package se.nixonmc.duels.modules.duel;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.modules.kit.Kit;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DuelKitMenuListener implements Listener {

    private final DuelsCore core;
    private final DuelKitMenu kitMenu;
    private final DuelMapMenu mapMenu;
    private final DuelConfirmMenu confirmMenu;
    private final DuelSystem duelSystem;

    private final Set<UUID> transitioning = new HashSet<>();

    public DuelKitMenuListener(
            DuelsCore core,
            DuelKitMenu kitMenu,
            DuelMapMenu mapMenu,
            DuelConfirmMenu confirmMenu,
            DuelSystem duelSystem
    ) {
        this.core = core;
        this.kitMenu = kitMenu;
        this.mapMenu = mapMenu;
        this.confirmMenu = confirmMenu;
        this.duelSystem = duelSystem;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getView().getTitle().equals("Confirm Duel")) {
            event.setCancelled(true);

            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }

            if (event.getRawSlot() == 10) {
                DuelSetup setup = kitMenu.getSetup(player);

                if (setup == null) {
                    return;
                }

                DuelRequest request = duelSystem.createRequest(
                        player.getUniqueId(),
                        setup.getTarget().getUniqueId(),
                        setup.getKit().getId(),
                        setup.getMap()
                );

                if (request == null) {
                    return;
                }

                core.getMessageManager().send(
                        player,
                        "duel.request-sent",
                        "<player>",
                        setup.getTarget().getName()
                );

                core.getMessageManager().send(
                        setup.getTarget(),
                        "duel.request-received",
                        "<player>",
                        player.getName(),
                        "<kit>",
                        setup.getKit().getName(),
                        "<map>",
                        setup.getMap()
                );

                kitMenu.removeSetup(player);
                player.closeInventory();

                return;
            }

            if (event.getRawSlot() == 16) {
                kitMenu.removeSetup(player);
                player.closeInventory();
            }

            return;
        }

        if (event.getView().getTitle().equals("Select a Map")) {
            event.setCancelled(true);

            if (!(event.getWhoClicked() instanceof Player player)) {
                return;
            }

            if (event.getRawSlot() < 0
                    || event.getRawSlot() >= event.getView().getTopInventory().getSize()) {
                return;
            }

            String map = mapMenu.getMap(event.getRawSlot());

            if (map == null) {
                return;
            }

            DuelSetup setup = kitMenu.getSetup(player);

            if (setup == null) {
                return;
            }

            setup.setMap(map);

            transitioning.add(player.getUniqueId());
            confirmMenu.open(player);

            return;
        }

        if (!event.getView().getTitle().equals("Select a Kit")) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getRawSlot() < 0
                || event.getRawSlot() >= event.getView().getTopInventory().getSize()) {
            return;
        }

        Kit kit = kitMenu.getKit(event.getRawSlot());

        if (kit == null) {
            return;
        }

        DuelSetup setup = kitMenu.getSetup(player);

        if (setup == null) {
            return;
        }

        setup.setKit(kit);

        transitioning.add(player.getUniqueId());
        mapMenu.open(player);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }

        if (event.getView().getTitle().equals("Select a Kit")) {
            if (transitioning.remove(player.getUniqueId())) {
                return;
            }

            kitMenu.removeSetup(player);
            return;
        }

        if (event.getView().getTitle().equals("Select a Map")) {
            if (transitioning.remove(player.getUniqueId())) {
                return;
            }

            kitMenu.removeSetup(player);
            return;
        }

        if (event.getView().getTitle().equals("Confirm Duel")) {
            kitMenu.removeSetup(player);
        }
    }
}