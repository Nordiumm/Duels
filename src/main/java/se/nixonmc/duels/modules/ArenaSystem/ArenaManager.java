package se.nixonmc.duels.modules.ArenaSystem;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import org.mvplugins.multiverse.core.MultiverseCoreApi;

import se.nixonmc.duels.core.DuelsCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import java.util.HashMap;


public class ArenaManager {

    private final DuelsCore core;
    private final MultiverseCoreApi multiverseCore = MultiverseCoreApi.get();;

    public final Map<String, Arena> arenas = new HashMap<>();
    private final List<Arena> occupiedArenas = new ArrayList<>();

    public ArenaManager(
            DuelsCore core
    ) {
        this.core = core;
        Loader l = new Loader(core, this);
        l.addArenasFromConfig();
    }


    public Arena getAvailableArena() {
        for (Arena arena : arenas.values()) {
            if (arena.isAvailable()) {
                return arena;
            }
        }

        return null;
    }


    public void removeArena(Arena arena) {

        arenas.values().remove(arena);
        occupiedArenas.remove(arena);

    }
}
