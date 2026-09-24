package se.nixonmc.duels.modules.ArenaSystem;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import org.mvplugins.multiverse.core.MultiverseCoreApi;

import org.mvplugins.multiverse.core.world.LoadedMultiverseWorld;
import org.mvplugins.multiverse.core.world.MultiverseWorld;
import org.mvplugins.multiverse.core.world.options.CloneWorldOptions;
import org.mvplugins.multiverse.core.world.options.DeleteWorldOptions;
import org.mvplugins.multiverse.core.world.options.UnloadWorldOptions;
import se.nixonmc.duels.core.DuelsCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import java.util.HashMap;


public class ArenaManager {

    private final DuelsCore core;
    private final MultiverseCoreApi multiverseCore = MultiverseCoreApi.get();;
    public final Map<String, Arena> arenaDefinitions = new HashMap<>();



    public final Map<String, Arena> arenas = new HashMap<>();
    private final List<Arena> occupiedArenas = new ArrayList<>();

    public ArenaManager(
            DuelsCore core
    ) {
        this.core = core;
        Loader l = new Loader(core, this);
        l.addArenasFromConfig();
    }

    public void ReturnWorldToCycle(Arena arena) {
            MultiverseWorld mvWorld = multiverseCore.getWorldManager()
                .getWorld(arena.getWorld())
                .getOrElseThrow(() -> new IllegalStateException(
                        "Template world is not managed by Multiverse"
                ));


        var result = multiverseCore.getWorldManager().unloadWorld(
                UnloadWorldOptions
                        .world((LoadedMultiverseWorld) mvWorld)
                        .saveBukkitWorld(false)
        );
        if (result.isSuccess()) {
            System.out.println("Arena world deleted: " + mvWorld.getName());
        } else {
            System.out.println("Failed to delete arena world: " + result);
        }


        occupiedArenas.remove(arena);
        arenas.put(arena.getWorld().getName(), arena);

    }

    public void DeleteArena(Arena arena){
        MultiverseWorld mvWorld = multiverseCore.getWorldManager()
                .getWorld(arena.getWorld())
                .getOrElseThrow(() -> new IllegalStateException(
                        "Template world is not managed by Multiverse"
                ));


        var result = multiverseCore.getWorldManager().unloadWorld(
                UnloadWorldOptions
                        .world((LoadedMultiverseWorld) mvWorld)
                        .saveBukkitWorld(false)
        );

        var result_ = multiverseCore.getWorldManager().deleteWorld(
                DeleteWorldOptions.world(mvWorld)
        );

        if (result_.isSuccess()) {
            System.out.println("Arena world deleted: " + mvWorld.getName());
        } else {
            System.out.println("Failed to delete arena world: " + result);
        }

        occupiedArenas.remove(arena);
    }

    public Arena getAvailableArena() {
        for (Arena arena : arenas.values()) {
            if (arena.isAvailable()) {
                return arena;
            }
        }

        Arena template = arenaDefinitions.values().iterator().next();
        World templateWorld = template.getWorld();

        MultiverseWorld mvWorld = multiverseCore.getWorldManager()
                .getWorld(templateWorld)
                .getOrElseThrow(() -> new IllegalStateException(
                        "Template world is not managed by Multiverse"
                ));

        String newWorldName = templateWorld.getName() + (occupiedArenas.size() + 1);

        var result = multiverseCore.getWorldManager().cloneWorld(
                CloneWorldOptions.fromTo(mvWorld, newWorldName)
        );

        Bukkit.getWorld(result.get().getName()).setAutoSave(false);



        Arena a = new Arena(Bukkit.getWorld(result.get().getName()), template.getPlayer1Spawn(), template.getPlayer2Spawn());

        return a;
    }


    public void removeArena(Arena arena) {

        arenas.values().remove(arena);
        occupiedArenas.remove(arena);

    }
}
