package se.nixonmc.duels.core.module;

import se.nixonmc.duels.core.DuelsCore;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModuleManager {

    private final DuelsCore core;
    private final Map<String, DuelsModule> modules = new LinkedHashMap<>();

    public ModuleManager(DuelsCore core) {
        this.core = core;
    }

    public void register(DuelsModule module) {
        String name = module.getName().toLowerCase();

        if (modules.containsKey(name)) {
            throw new IllegalStateException(
                    "Module '" + name + "' is already registered."
            );
        }

        modules.put(name, module);
    }

    public void enableModules() {
        for (DuelsModule module : modules.values()) {
            try {
                module.enable();

                core.getPlugin().getLogger().info(
                        "Enabled module: " + module.getName()
                );
            } catch (Exception exception) {
                core.getPlugin().getLogger().severe(
                        "Failed to enable module: " + module.getName()
                );

                exception.printStackTrace();
            }
        }
    }

    public void disableModules() {
        for (DuelsModule module : modules.values()) {
            try {
                module.disable();

                core.getPlugin().getLogger().info(
                        "Disabled module: " + module.getName()
                );
            } catch (Exception exception) {
                core.getPlugin().getLogger().severe(
                        "Failed to disable module: " + module.getName()
                );

                exception.printStackTrace();
            }
        }
    }

    public DuelsModule get(String name) {
        return modules.get(name.toLowerCase());
    }

    public boolean isRegistered(String name) {
        return modules.containsKey(name.toLowerCase());
    }

    public Collection<DuelsModule> getModules() {
        return modules.values();
    }
}