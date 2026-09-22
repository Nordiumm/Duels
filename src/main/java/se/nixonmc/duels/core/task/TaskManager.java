package se.nixonmc.duels.core.task;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public final class TaskManager {

    private final JavaPlugin plugin;
    private final Set<BukkitTask> tasks = new HashSet<>();

    public TaskManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public BukkitTask run(Runnable runnable) {
        BukkitTask task = Bukkit.getScheduler()
                .runTask(plugin, runnable);

        tasks.add(task);

        return task;
    }

    public BukkitTask runLater(Runnable runnable, long delay) {
        BukkitTask task = Bukkit.getScheduler()
                .runTaskLater(plugin, runnable, delay);

        tasks.add(task);

        return task;
    }

    public BukkitTask runTimer(
            Runnable runnable,
            long delay,
            long period
    ) {
        BukkitTask task = Bukkit.getScheduler()
                .runTaskTimer(plugin, runnable, delay, period);

        tasks.add(task);

        return task;
    }

    public void cancel(BukkitTask task) {
        if (task == null) {
            return;
        }

        task.cancel();
        tasks.remove(task);
    }

    public void cancelAll() {
        for (BukkitTask task : tasks) {
            task.cancel();
        }

        tasks.clear();
    }
}