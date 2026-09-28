package se.nixonmc.duels.modules.duel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class DuelConfirmMenu {

    private final DuelKitMenu kitMenu;
    private final DuelSystem duelSystem;

    public DuelConfirmMenu(
            DuelKitMenu kitMenu,
            DuelSystem duelSystem
    ) {
        this.kitMenu = kitMenu;
        this.duelSystem = duelSystem;
    }

    public void open(Player player) {
        DuelSetup setup = kitMenu.getSetup(player);

        if (setup == null) {
            return;
        }

        Inventory inventory = Bukkit.createInventory(
                null,
                27,
                "Confirm Duel"
        );

        ItemStack kitItem = setup.getKit().getIcon();
        ItemMeta kitMeta = kitItem.getItemMeta();

        kitMeta.setDisplayName(setup.getKit().getName());

        kitItem.setItemMeta(kitMeta);

        inventory.setItem(11, kitItem);

        ItemStack targetItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta targetMeta = (SkullMeta) targetItem.getItemMeta();

        targetMeta.setOwningPlayer(setup.getTarget());
        targetMeta.setDisplayName(setup.getTarget().getName());

        targetItem.setItemMeta(targetMeta);

        inventory.setItem(13, targetItem);

        ItemStack mapItem = new ItemStack(Material.MAP);
        ItemMeta mapMeta = mapItem.getItemMeta();

        mapMeta.setDisplayName(setup.getMap());

        mapItem.setItemMeta(mapMeta);

        inventory.setItem(15, mapItem);

        ItemStack confirmItem = new ItemStack(Material.LIME_WOOL);
        ItemMeta confirmMeta = confirmItem.getItemMeta();

        confirmMeta.setDisplayName("Confirm");

        confirmItem.setItemMeta(confirmMeta);

        inventory.setItem(10, confirmItem);

        ItemStack cancelItem = new ItemStack(Material.RED_WOOL);
        ItemMeta cancelMeta = cancelItem.getItemMeta();

        cancelMeta.setDisplayName("Cancel");

        cancelItem.setItemMeta(cancelMeta);

        inventory.setItem(16, cancelItem);

        player.openInventory(inventory);
    }
}