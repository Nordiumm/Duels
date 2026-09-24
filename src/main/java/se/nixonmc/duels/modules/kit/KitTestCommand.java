package se.nixonmc.duels.modules.kit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitTestCommand implements CommandExecutor {

    private final KitSystem kitSystem;

    public KitTestCommand(KitSystem kitSystem) {
        this.kitSystem = kitSystem;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length == 0) {
            return true;
        }

        Kit kit = kitSystem.getKitManager().getKit(args[0]);

        if (kit == null) {
            return true;
        }

        kitSystem.applyKit(player, kit);

        return true;
    }
}