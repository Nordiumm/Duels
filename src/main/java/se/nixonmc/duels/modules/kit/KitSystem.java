package se.nixonmc.duels.modules.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitSystem {

    private final KitManager kitManager;

    public KitSystem() {
        kitManager = new KitManager();
        registerKits();
    }

    private void registerKits() {
        ItemStack[] swordContents = new ItemStack[36];

        swordContents[0] = new ItemStack(Material.DIAMOND_SWORD);

        ItemStack[] swordArmor = new ItemStack[4];

        swordArmor[0] = new ItemStack(Material.DIAMOND_BOOTS);
        swordArmor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        swordArmor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        swordArmor[3] = new ItemStack(Material.DIAMOND_HELMET);

        kitManager.addKit(new Kit("sword", "Sword", swordContents, swordArmor, null));
        kitManager.addKit(new Kit("axe", "Axe", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("mace", "Mace", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("uhc", "UHC", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("netherite-pot", "Netherite Pot", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("pot", "Pot", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("smp", "SMP", new ItemStack[36], new ItemStack[4], null));
        kitManager.addKit(new Kit("vanilla", "Vanilla", new ItemStack[36], new ItemStack[4], null));
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