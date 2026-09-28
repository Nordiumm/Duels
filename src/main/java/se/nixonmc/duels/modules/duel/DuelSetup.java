package se.nixonmc.duels.modules.duel;

import org.bukkit.entity.Player;
import se.nixonmc.duels.modules.kit.Kit;

public class DuelSetup {

    private final Player player;
    private final Player target;
    private Kit kit;

    public DuelSetup(Player player, Player target) {
        this.player = player;
        this.target = target;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getTarget() {
        return target;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }
}