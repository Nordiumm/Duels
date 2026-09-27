package se.nixonmc.duels.modules.kit.preset;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import se.nixonmc.duels.modules.kit.Kit;

public class AxeKit extends Kit {
    public AxeKit() {
        super(
                "axe",
                "Axe",
                createContents(),
                createArmor(),
                createOffhand(),
                new ItemStack(Material.DIAMOND_AXE)
        );
    }
    private static ItemStack[] createContents() {
        ItemStack[] contents = new ItemStack[36];
        contents[0] = new ItemStack(Material.DIAMOND_SWORD);
        contents[1] = new ItemStack(Material.DIAMOND_AXE);
        contents[2] = new ItemStack(Material.BOW);
        contents[3] = new ItemStack(Material.CROSSBOW);
        contents[8] = new ItemStack(Material.ARROW, 6);
        return contents;
    }
    private static ItemStack[] createArmor() {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = new ItemStack(Material.DIAMOND_HELMET);
        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armor[0] = new ItemStack(Material.DIAMOND_BOOTS);
        return armor;
    }
    private static ItemStack createOffhand() {
        return new ItemStack(Material.SHIELD);
    }
}
