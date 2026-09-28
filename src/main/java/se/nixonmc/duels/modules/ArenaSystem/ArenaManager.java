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

import javax.print.attribute.ResolutionSyntax;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




import java.util.HashMap;


public class ArenaManager {

    private final DuelsCore core;
    private final MultiverseCoreApi multiverseCore = MultiverseCoreApi.get();
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

    public Arena getAvailableArena(String arenaName) {
        // First, look for an existing available arena
        for (Arena arena : arenas.values()) {
            if (arena.getArenaID().equals(arenaName) && arena.isAvailable()) {
                occupiedArenas.add(arena);
                return arena;
            }
        }

        // Make sure we have at least one arena template
        if (arenaDefinitions.isEmpty()) {
            Bukkit.getLogger().severe("[Duels] No arena definitions are configured.");
            return null;
        }

        // Get the template arena
        Arena template = arenaDefinitions.values().iterator().next();
        World templateWorld = template.getWorld();

        if (templateWorld == null) {
            Bukkit.getLogger().severe("[Duels] Template arena has no world.");
            return null;
        }

        // Get the template world from Multiverse
        MultiverseWorld mvWorld;

        try {
            mvWorld = multiverseCore.getWorldManager()
                    .getWorld(templateWorld)
                    .getOrElseThrow(() -> new IllegalStateException(
                            "Template world '" + templateWorld.getName()
                                    + "' is not managed by Multiverse"
                    ));
        } catch (Exception e) {
            Bukkit.getLogger().severe(
                    "[Duels] Could not find template world in Multiverse: "
                            + templateWorld.getName()
            );
            e.printStackTrace();
            return null;
        }

        // Generate a new world name
        String newWorldName = templateWorld.getName()
                + (occupiedArenas.size() + arenas.size() + 1);

        // Clone the template world
        var result = multiverseCore.getWorldManager().cloneWorld(
                CloneWorldOptions.fromTo(mvWorld, newWorldName)
        );

        // IMPORTANT: cloneWorld() can return a failed Attempt.
        if (result.isFailure()) {
            Bukkit.getLogger().severe(
                    "[Duels] Failed to clone arena world!"
            );
            Bukkit.getLogger().severe(
                    "[Duels] Template: " + templateWorld.getName()
            );
            Bukkit.getLogger().severe(
                    "[Duels] Target: " + newWorldName
            );
            Bukkit.getLogger().severe(
                    "[Duels] Result: " + result
            );

            return null;
        }

        // Only call get() after checking for failure
        MultiverseWorld clonedWorld = result.get();

        if (clonedWorld == null) {
            Bukkit.getLogger().severe(
                    "[Duels] Multiverse returned a null cloned world."
            );
            return null;
        }

        // Get the Bukkit world
        World world = clonedWorld.getRespawnWorld();

        if (world == null) {
            Bukkit.getLogger().severe(
                    "[Duels] Cloned world has no respawn world: "
                            + newWorldName
            );
            return null;
        }

        // Disable autosaving for temporary duel worlds
        world.setAutoSave(false);

        // Create the arena
        Arena a = new Arena(
                world,
                template.getPlayer1Spawn(),
                template.getPlayer2Spawn(),
                newWorldName
        );

        occupiedArenas.add(a);

        Bukkit.getLogger().info(
                "[Duels] Created arena world: " + newWorldName
        );

        return a;
    }





    public void removeArena(Arena arena) {

        arenas.values().remove(arena);
        occupiedArenas.remove(arena);
        DeleteArena(arena);
    }
}
