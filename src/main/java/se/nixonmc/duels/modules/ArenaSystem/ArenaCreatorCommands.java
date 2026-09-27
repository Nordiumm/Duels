package se.nixonmc.duels.modules.ArenaSystem;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArenaCreatorCommands implements CommandExecutor {

    private final ArenaManager arenaManager;
    private final DuelsCore core;
    private final ConfigManager configManager;

    private final Map<UUID, ArenaCreation> creations = new HashMap<>();

    public ArenaCreatorCommands(
            ArenaManager manager,
            DuelsCore core
    ) {
        this.arenaManager = manager;
        this.core = core;
        this.configManager = core.getConfigManager();
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
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "create", "createarena" -> {

                if (args.length < 2) {
                    player.sendMessage(
                            "§cUsage: /arena create <name>"
                    );
                    return true;
                }

                if (creations.containsKey(player.getUniqueId())) {
                    player.sendMessage(
                            "§cYou are already creating an arena."
                    );
                    return true;
                }

                String name = args[1];

                if (arenaManager.arenaDefinitions.containsKey(name)) {
                    player.sendMessage(
                            "§cAn arena with that name already exists."
                    );
                    return true;
                }

                creations.put(
                        player.getUniqueId(),
                        new ArenaCreation(
                                name,
                                player.getWorld()
                        )
                );

                player.sendMessage(
                        "§aStarted creating arena §f" + name + "§a."
                );

                player.sendMessage(
                        "§7Stand at the first spawn and use:"
                );

                player.sendMessage(
                        "§f/arena setspawn1"
                );

                player.sendMessage(
                        "§7Then stand at the second spawn and use:"
                );

                player.sendMessage(
                        "§f/arena setspawn2"
                );

                player.sendMessage(
                        "§7Finally use §f/arena save §7to save it."
                );

                return true;
            }

            case "setspawn1" -> {

                ArenaCreation creation =
                        creations.get(player.getUniqueId());

                if (creation == null) {
                    player.sendMessage(
                            "§cYou are not currently creating an arena."
                    );
                    return true;
                }

                if (!player.getWorld().equals(creation.world)) {
                    player.sendMessage(
                            "§cYou must set the spawn in the arena world."
                    );
                    return true;
                }

                creation.player1Spawn =
                        player.getLocation().clone();

                player.sendMessage(
                        "§aPlayer 1 spawn has been set."
                );

                return true;
            }

            case "setspawn2" -> {

                ArenaCreation creation =
                        creations.get(player.getUniqueId());

                if (creation == null) {
                    player.sendMessage(
                            "§cYou are not currently creating an arena."
                    );
                    return true;
                }

                if (!player.getWorld().equals(creation.world)) {
                    player.sendMessage(
                            "§cYou must set the spawn in the arena world."
                    );
                    return true;
                }

                creation.player2Spawn =
                        player.getLocation().clone();

                player.sendMessage(
                        "§aPlayer 2 spawn has been set."
                );

                return true;
            }

            case "save" -> {

                ArenaCreation creation =
                        creations.get(player.getUniqueId());

                if (creation == null) {
                    player.sendMessage(
                            "§cYou are not currently creating an arena."
                    );
                    return true;
                }

                if (creation.player1Spawn == null) {
                    player.sendMessage(
                            "§cYou haven't set Player 1's spawn."
                    );
                    return true;
                }

                if (creation.player2Spawn == null) {
                    player.sendMessage(
                            "§cYou haven't set Player 2's spawn."
                    );
                    return true;
                }

                Arena arena = new Arena(
                        creation.world,
                        creation.player1Spawn,
                        creation.player2Spawn
                );

                /*
                 * Add the definition to ArenaManager.
                 */
                arenaManager.arenaDefinitions.put(
                        creation.name,
                        arena
                );

                /*
                 * Save the definition to arenas.yml.
                 */
                saveArena(
                        creation.name,
                        arena
                );

                creations.remove(player.getUniqueId());

                player.sendMessage(
                        "§aArena §f" +
                                creation.name +
                                " §ahas been created and saved."
                );

                return true;
            }

            case "cancel" -> {

                ArenaCreation creation =
                        creations.remove(player.getUniqueId());

                if (creation == null) {
                    player.sendMessage(
                            "§cYou are not currently creating an arena."
                    );
                    return true;
                }

                player.sendMessage(
                        "§cArena creation cancelled."
                );

                return true;
            }

            default -> {
                sendHelp(player);
                return true;
            }
        }
    }

    private void saveArena(
            String name,
            Arena arena
    ) {

        FileConfiguration config =
                configManager.get("arenas.yml");

        if (config == null) {
            throw new IllegalStateException(
                    "arenas.yml has not been loaded."
            );
        }

        String path = "arenas." + name;

        config.set(
                path + ".world",
                arena.getWorld().getName()
        );

        saveLocation(
                config,
                path + ".player1",
                arena.getPlayer1Spawn()
        );

        saveLocation(
                config,
                path + ".player2",
                arena.getPlayer2Spawn()
        );

        configManager.save("arenas.yml");
    }

    private void saveLocation(
            FileConfiguration config,
            String path,
            Location location
    ) {

        config.set(
                path + ".world",
                location.getWorld().getName()
        );

        config.set(
                path + ".x",
                location.getX()
        );

        config.set(
                path + ".y",
                location.getY()
        );

        config.set(
                path + ".z",
                location.getZ()
        );

        config.set(
                path + ".yaw",
                location.getYaw()
        );

        config.set(
                path + ".pitch",
                location.getPitch()
        );
    }

    private void sendHelp(Player player) {

        player.sendMessage("§6§lArena Commands");
        player.sendMessage(
                "§e/arena create <name> §7- Create an arena definition"
        );
        player.sendMessage(
                "§e/arena setspawn1 §7- Set Player 1's spawn"
        );
        player.sendMessage(
                "§e/arena setspawn2 §7- Set Player 2's spawn"
        );
        player.sendMessage(
                "§e/arena save §7- Save the arena"
        );
        player.sendMessage(
                "§e/arena cancel §7- Cancel creation"
        );
    }
    private static class ArenaCreation {

        private final String name;
        private final World world;

        private Location player1Spawn;
        private Location player2Spawn;

        private ArenaCreation(
                String name,
                World world
        ) {
            this.name = name;
            this.world = world;
        }
    }
}