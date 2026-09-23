package se.nixonmc.duels.modules.duel;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import se.nixonmc.duels.core.DuelsCore;

public class DuelCommand implements CommandExecutor {

    private final DuelsCore core;
    private final DuelSystem duelSystem;

    public DuelCommand(DuelsCore core, DuelSystem duelSystem) {
        this.core = core;
        this.duelSystem = duelSystem;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (!(sender instanceof Player player)) {
            core.getMessageManager().send(
                    sender,
                    "general.player-only"
            );
            return true;
        }

        if (args.length == 0) {
            core.getMessageManager().send(
                    player,
                    "duel.usage"
            );
            return true;
        }

        if (args[0].equalsIgnoreCase("accept")) {
            Duel duel = duelSystem.acceptRequest(player.getUniqueId());

            if (duel == null) {
                core.getMessageManager().send(
                        player,
                        "duel.no-request"
                );
                return true;
            }

            core.getMessageManager().send(
                    player,
                    "duel.request-accepted"
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("deny")) {
            boolean denied = duelSystem.denyRequest(player.getUniqueId());

            if (!denied) {
                core.getMessageManager().send(
                        player,
                        "duel.no-request"
                );
                return true;
            }

            core.getMessageManager().send(
                    player,
                    "duel.request-denied"
            );

            return true;
        }

        return true;
    }
}