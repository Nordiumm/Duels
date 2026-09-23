package se.nixonmc.duels.modules.kit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KitManager {
    private final Map<String, Kit> kits;

    public KitManager() {
        kits = new HashMap<>();
    }
    public void addKit(Kit kit) {
        kits.put(kit.getId(), kit);
    }

    public void removeKit(Kit kit) {
        kits.remove(kit.getId());
    }

    public Kit getKit(String id) {
        return kits.get(id);
    }

    public Map<String, Kit> getKits() {
        return Collections.unmodifiableMap(kits);
    }
}
