package se.nixonmc.duels.core.storage;

import se.nixonmc.duels.core.DuelsCore;

public final class StorageManager {

    private final DuelsCore core;

    public StorageManager(DuelsCore core) {
        this.core = core;
    }

    public void enable() {
        // Database/storage initialization will go here.
    }

    public void disable() {
        // Database/storage shutdown will go here.
    }
}