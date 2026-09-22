package se.nixonmc.duels.modules.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import se.nixonmc.duels.core.DuelsCore;
import se.nixonmc.duels.core.module.DuelsModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ExampleModule implements DuelsModule {

    private final DuelsCore core;

    private final Map<UUID, ExampleDuel> duels = new HashMap<>();
    private final Map<UUID, ExampleDuel> playerDuels = new HashMap<>();


    public ExampleModule(DuelsCore core) {
        this.core = core;
    }

    @Override
    public String getName() {
        return "example";
    }

    @Override
    public void enable() {
        core.getPlugin().getLogger().info(
                "Example module enabled!"
        );
    }

    @Override
    public void disable() {
        duels.clear();
        playerDuels.clear();

        core.getPlugin().getLogger().info(
                "Example module disabled!"
        );
    }

    public ExampleDuel createDuel(
            Player playerOne,
            Player playerTwo
    ) {
        ExampleDuel duel = new ExampleDuel(
                playerOne.getUniqueId(),
                playerTwo.getUniqueId()
        );

        duels.put(duel.getId(), duel);

        playerDuels.put(playerOne.getUniqueId(), duel);
        playerDuels.put(playerTwo.getUniqueId(), duel);

        return duel;
    }

    public void startDuel(ExampleDuel duel) {
        duel.setStarted(true);

        Player playerOne = Bukkit.getPlayer(duel.getPlayerOne());
        Player playerTwo = Bukkit.getPlayer(duel.getPlayerTwo());

        if (playerOne != null) {
            core.getMessageManager().send(
                    playerOne,
                    "duel.started"
            );
        }

        if (playerTwo != null) {
            core.getMessageManager().send(
                    playerTwo,
                    "duel.started"
            );
        }
    }

    public void endDuel(ExampleDuel duel) {
        duels.remove(duel.getId());

        playerDuels.remove(duel.getPlayerOne());
        playerDuels.remove(duel.getPlayerTwo());

        duel.setStarted(false);
    }

    public ExampleDuel getDuel(UUID playerId) {
        return playerDuels.get(playerId);
    }

    public boolean isInDuel(UUID playerId) {
        return playerDuels.containsKey(playerId);
    }
}