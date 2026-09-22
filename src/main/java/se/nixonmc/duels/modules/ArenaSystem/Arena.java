package se.nixonmc.duels.modules.ArenaSystem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import se.nixonmc.duels.core.DuelsCore;

public class Arena {

    private final World world;

    private final Location player1Spawn;
    private final Location player2Spawn;

    private Player player1;
    private Player player2;

    public Arena(
            World world,
            Location player1Spawn,
            Location player2Spawn
    ) {
        this.world = world;
        this.player1Spawn = player1Spawn;
        this.player2Spawn = player2Spawn;
    }

    public World getWorld() {
        return world;
    }

    public Location getPlayer1Spawn() {
        return player1Spawn;
    }

    public Location getPlayer2Spawn() {
        return player2Spawn;
    }

    public void SetPlayers(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }
}
