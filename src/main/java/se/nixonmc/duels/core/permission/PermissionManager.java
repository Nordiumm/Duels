package se.nixonmc.duels.core.permission;

import org.bukkit.command.CommandSender;

public final class PermissionManager {

    public boolean has(
            CommandSender sender,
            String permission
    ) {
        return sender.hasPermission(permission);
    }
}