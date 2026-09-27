package se.nixonmc.duels.modules.kit.preset;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.modules.kit.Kit;

public class SwordKit extends Kit {
    public SwordKit() {
        super(
                "sword",
                "Sword",
                createContents(),
                createArmor(),
                null
        );
    }
    private static ItemStack[] createContents() {
        ItemStack[] contents = new ItemStack[36];
        contents[0] = new ItemStack(Material.DIAMOND_SWORD);
        contents[0].addEnchantment(Enchantment.UNBREAKING, 3);
        return contents;
    }
    private static ItemStack[] createArmor() {
        ItemStack[] armor = new ItemStack[4];
        armor[3] = new ItemStack(Material.DIAMOND_HELMET);
        armor[2] = new ItemStack(Material.DIAMOND_CHESTPLATE);
        armor[1] = new ItemStack(Material.DIAMOND_LEGGINGS);
        armor[0] = new ItemStack(Material.DIAMOND_BOOTS);

        for (ItemStack item : armor) {
            item.addEnchantment(Enchantment.UNBREAKING, 3);
            item.addEnchantment(Enchantment.PROTECTION, 4);
        }
        return armor;
    }
}
