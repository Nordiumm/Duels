package se.nixonmc.duels.core;

import org.bukkit.plugin.java.JavaPlugin;
import se.nixonmc.duels.core.config.ConfigManager;
import se.nixonmc.duels.core.event.EventManager;
import se.nixonmc.duels.core.message.MessageManager;
import se.nixonmc.duels.core.module.ModuleManager;
import se.nixonmc.duels.core.permission.PermissionManager;
import se.nixonmc.duels.core.player.PlayerManager;
import se.nixonmc.duels.core.storage.StorageManager;
import se.nixonmc.duels.core.task.TaskManager;
import se.nixonmc.duels.modules.ArenaSystem.ArenaManager;
import se.nixonmc.duels.modules.duel.DuelModule;
import se.nixonmc.duels.modules.example.ExampleModule;

public final class DuelsCore {

    private final JavaPlugin plugin;

    private final ModuleManager moduleManager;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private final TaskManager taskManager;
    private final PlayerManager playerManager;
    private final EventManager eventManager;
    private final PermissionManager permissionManager;
    private final StorageManager storageManager;

    public DuelsCore(JavaPlugin plugin) {
        this.plugin = plugin;

        this.configManager = new ConfigManager(plugin);
        this.messageManager = new MessageManager(this);
        this.taskManager = new TaskManager(plugin);
        this.playerManager = new PlayerManager(this);
        this.eventManager = new EventManager();
        this.permissionManager = new PermissionManager();
        this.storageManager = new StorageManager(this);
        this.moduleManager = new ModuleManager(this);

        moduleManager.register(new ExampleModule(this));
        moduleManager.register(new DuelModule(this));
    }

    public void enable() {
        configManager.load();
        messageManager.load();
        storageManager.enable();

        moduleManager.enableModules();
    }

    public void disable() {
        moduleManager.disableModules();

        playerManager.clear();
        taskManager.cancelAll();

        storageManager.disable();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}