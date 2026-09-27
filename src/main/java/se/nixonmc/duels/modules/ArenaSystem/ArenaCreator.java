package se.nixonmc.duels.modules.ArenaSystem;

import se.nixonmc.duels.core.DuelsCore;

public class ArenaCreator {
    ArenaManager arenaManager;
    DuelsCore core;
    ArenaCreatorCommands commandsExecutor;


    public ArenaCreator(ArenaManager manager, DuelsCore core){
        this.arenaManager = manager;
        this.core = core;


        commandsExecutor = new ArenaCreatorCommands(arenaManager, core);
    }



}
