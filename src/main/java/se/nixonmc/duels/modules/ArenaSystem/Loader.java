package se.nixonmc.duels.modules.ArenaSystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import se.nixonmc.duels.core.DuelsCore;

public class Loader {
    DuelsCore core;
    ArenaManager manager;

    public Loader(DuelsCore core, ArenaManager manager){
        this.core = core;
        this.manager = manager;
    }


    public void addArenasFromConfig() {

        ConfigurationSection arenasSection = core.getConfigManager()
                .get("arenas.yml")
                .getConfigurationSection("Arenas");

        if (arenasSection == null) {
            Bukkit.getLogger().warning("No Arenas section found in arenas.yml!"
            );
            return;
        }

        for (String arenaId : arenasSection.getKeys(false)) {

            ConfigurationSection arenaSection =
                    arenasSection.getConfigurationSection(arenaId);

            if (arenaSection == null) {
                continue;
            }

            String worldName = arenaSection.getString("world");

            ConfigurationSection player1Section =
                    arenaSection.getConfigurationSection("player1");

            ConfigurationSection player2Section =
                    arenaSection.getConfigurationSection("player2");

            if (worldName == null ||
                    player1Section == null ||
                    player2Section == null) {

                Bukkit.getLogger().warning("Invalid configuration for arena: " + arenaId
                );

                continue;
            }

            // Hämta världen från Multiverse
            World world =
                    Bukkit.getWorld(worldName);

            if (world == null) {
                Bukkit.getLogger().warning("World '" + worldName + "' for arena '" + arenaId + "' was not found!");

                continue;
            }


            Location player1Location =
                    getLocation(player1Section, world);

            Location player2Location =
                    getLocation(player2Section, world);

            Arena arena = new Arena(
                    world,
                    player1Location,
                    player2Location
            );

            manager.arenas.put(arenaId, arena);

            Bukkit.getLogger().info("Loaded arena: " + arenaId
            );
        }
    }

    private Location getLocation(
            ConfigurationSection section,
            World world
    ) {

        double x = section.getDouble("x");
        double y = section.getDouble("y");
        double z = section.getDouble("z");

        float yaw = (float) section.getDouble("yaw");
        float pitch = (float) section.getDouble("pitch");

        return new Location(
                world,
                x,
                y,
                z,
                yaw,
                pitch
        );
    }
}
