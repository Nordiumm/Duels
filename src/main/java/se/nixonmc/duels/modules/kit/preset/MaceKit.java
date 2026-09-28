package se.nixonmc.duels.modules.kit.preset;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.block.ShulkerBox;
import org.bukkit.potion.PotionType;
import se.nixonmc.duels.modules.kit.Kit;

public class MaceKit extends Kit {
    public MaceKit() {
        super(
                "mace",
                "Mace",
                createContents(),
                createArmor(),
                createOffhand(),
                new ItemStack(Material.MACE)
        );
    }

    private static ItemStack[] createContents() {
        ItemStack[] contents = new ItemStack[36];

        contents[0] = new ItemStack(Material.NETHERITE_SWORD);
        contents[0].addEnchantment(Enchantment.SHARPNESS, 5);
        contents[0].addEnchantment(Enchantment.UNBREAKING, 3);

        contents[1] = new ItemStack(Material.NETHERITE_AXE);
        contents[1].addEnchantment(Enchantment.SHARPNESS, 5);
        contents[1].addEnchantment(Enchantment.UNBREAKING, 3);

        contents[2] = new ItemStack(Material.ENDER_PEARL, 16);
        contents[3] = new ItemStack(Material.GOLDEN_APPLE, 64);
        contents[4] = new ItemStack(Material.WIND_CHARGE, 64);
        contents[5] = new ItemStack(Material.ELYTRA);

        contents[6] = new ItemStack(Material.MACE);
        contents[6].addEnchantment(Enchantment.BREACH, 4);
        contents[6].addEnchantment(Enchantment.UNBREAKING, 3);

        contents[7] = new ItemStack(Material.MACE);
        contents[7].addEnchantment(Enchantment.WIND_BURST, 1);
        contents[7].addEnchantment(Enchantment.DENSITY, 5);
        contents[7].addEnchantment(Enchantment.UNBREAKING, 3);

        contents[8] = new ItemStack(Material.SHIELD);
        contents[8].addEnchantment(Enchantment.MENDING, 1);
        contents[8].addEnchantment(Enchantment.UNBREAKING, 3);

        contents[9] = new ItemStack(Material.WIND_CHARGE, 64);

        for (int i = 10; i <= 16; i++) {
            contents[i] = createStrengthPotion();
        }

        contents[17] = createStrengthShulker();

        contents[18] = new ItemStack(Material.ENDER_PEARL, 16);

        for (int i = 19; i <= 25; i++) {
            contents[i] = createSwiftnessPotion();
        }
        contents[26] = new ItemStack(Material.GOLDEN_APPLE, 64);
        contents[27] = new ItemStack(Material.ENDER_PEARL, 16);

        for (int i = 28; i <= 31; i++) {
            contents[i] = createStrengthPotion();
        }

        for (int i = 32; i <= 34; i++) {
            contents[i] = createSwiftnessPotion();
        }
        contents[35] = new ItemStack(Material.TOTEM_OF_UNDYING);

        return contents;
    }

    private static ItemStack createStrengthPotion() {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        meta.setBasePotionType(PotionType.STRONG_STRENGTH);

        potion.setItemMeta(meta);
        return potion;
    }
    private static ItemStack createSwiftnessPotion() {
        ItemStack potion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();

        meta.setBasePotionType(PotionType.STRONG_SWIFTNESS);

        potion.setItemMeta(meta);
        return potion;
    }

    private static ItemStack createStrengthShulker() {
        ItemStack shulker = new ItemStack(Material.SHULKER_BOX);
        BlockStateMeta meta = (BlockStateMeta) shulker.getItemMeta();
        ShulkerBox box = (ShulkerBox) meta.getBlockState();

        for (int i = 0; i < 27; i++) {
            box.getInventory().setItem(i, createStrengthPotion());
        }

        meta.setBlockState(box);
        shulker.setItemMeta(meta);

        return shulker;
    }

    private static ItemStack[] createArmor() {
        ItemStack[] armor = new ItemStack[4];

        armor[3] = new ItemStack(Material.NETHERITE_HELMET);
        armor[2] = new ItemStack(Material.NETHERITE_CHESTPLATE);
        armor[1] = new ItemStack(Material.NETHERITE_LEGGINGS);
        armor[0] = new ItemStack(Material.NETHERITE_BOOTS);

        for (ItemStack item : armor) {
            item.addEnchantment(Enchantment.PROTECTION, 4);
        }
        armor[0].addEnchantment(Enchantment.FEATHER_FALLING, 4);

        return armor;
    }

    private static ItemStack createOffhand() {
        return new ItemStack(Material.TOTEM_OF_UNDYING);
    }
}