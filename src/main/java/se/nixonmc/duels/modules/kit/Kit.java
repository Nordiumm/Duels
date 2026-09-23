package se.nixonmc.duels.modules.kit;

public class Kit {
    private final String id;
    private final String name;

    public Kit(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
