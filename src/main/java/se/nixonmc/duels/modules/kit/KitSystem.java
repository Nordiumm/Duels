package se.nixonmc.duels.modules.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.nixonmc.duels.modules.kit.preset.AxeKit;
import se.nixonmc.duels.modules.kit.preset.MaceKit;
import se.nixonmc.duels.modules.kit.preset.SwordKit;

public class KitSystem {

    private final KitManager kitManager;

    public KitSystem() {
        kitManager = new KitManager();
        registerKits();
    }

    private void registerKits() {
        kitManager.addKit(new SwordKit());
        kitManager.addKit(new AxeKit());
        kitManager.addKit(new MaceKit());
    }

    public KitManager getKitManager() {
        return kitManager;
    }
    public void applyKit(Player player, Kit kit) {
        player.getInventory().clear();
        player.getInventory().setContents(kit.getContents());
        player.getInventory().setArmorContents(kit.getArmor());
        player.getInventory().setItemInOffHand(kit.getOffhand());
    }
}