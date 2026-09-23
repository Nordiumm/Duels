package se.nixonmc.duels.modules.kit;

public class KitSystem {

    private final KitManager kitManager;

    public KitSystem() {
        kitManager = new KitManager();
        registerKits();
    }

    private void registerKits() {
        kitManager.addKit(new Kit("sword", "Sword"));
        kitManager.addKit(new Kit("axe", "Axe"));
        kitManager.addKit(new Kit("mace", "Mace"));
        kitManager.addKit(new Kit("uhc", "UHC"));
        kitManager.addKit(new Kit("netherite-pot", "Netherite Pot"));
        kitManager.addKit(new Kit("pot", "Pot"));
        kitManager.addKit(new Kit("smp", "SMP"));
        kitManager.addKit(new Kit("vanilla", "Vanilla"));
    }

    public KitManager getKitManager() {
        return kitManager;
    }
}