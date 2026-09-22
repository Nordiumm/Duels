package se.nixonmc.duels.core.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import se.nixonmc.duels.core.DuelsCore;

public final class MessageManager {

    private final DuelsCore core;
    private final MiniMessage miniMessage;

    public MessageManager(DuelsCore core) {
        this.core = core;
        this.miniMessage = MiniMessage.miniMessage();
    }

    public void load() {
        // Configuration is loaded by ConfigManager.
    }

    public Component get(String path) {
        String message = core.getConfigManager()
                .get("messages.yml")
                .getString(path, "<red>Missing message: " + path);

        return miniMessage.deserialize(message);
    }

    public void send(CommandSender sender, String path) {
        sender.sendMessage(get(path));
    }

    public void send(
            CommandSender sender,
            String path,
            String placeholder,
            String replacement
    ) {
        String message = core.getConfigManager()
                .get("messages.yml")
                .getString(path, "<red>Missing message: " + path);

        message = message.replace(
                placeholder,
                replacement
        );

        sender.sendMessage(miniMessage.deserialize(message));
    }
}