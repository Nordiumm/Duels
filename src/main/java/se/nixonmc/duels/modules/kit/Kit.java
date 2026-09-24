package se.nixonmc.duels.modules.kit;

import org.bukkit.inventory.ItemStack;

public class Kit {
    private final String id;
    private final String name;
    private final ItemStack[] contents;
    private final ItemStack[] armor;
    private final ItemStack offHand;

    public Kit(
            String id,
            String name,
            ItemStack[] contents,
            ItemStack[] armor,
            ItemStack offHand
    ) {
        this.id = id;
        this.name = name;
        this.contents = contents;
        this.armor = armor;
        this.offHand = offHand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getContents() {
        return contents;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack getOffhand() {
        return offHand;
    }
}