package me.hsgamer.topperstale.manager;

import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.task.TaskRegistration;
import me.hsgamer.topper.agent.core.Agent;
import me.hsgamer.topperstale.ToppersTale;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    private final ToppersTale plugin;

    public TaskManager(ToppersTale plugin) {
        this.plugin = plugin;
    }

    public Agent createTaskAgent(Runnable runnable, long delayMillis, boolean async) {
        long finalDelayMillis = delayMillis <= 0 ? 1000 : delayMillis;
        return new Agent() {
            private TaskRegistration taskRegistration;

            @Override
            public void start() {
                ScheduledFuture<Void> scheduledFuture = (async ? scheduler : HytaleServer.SCHEDULED_EXECUTOR).schedule(() -> {
                    runnable.run();
                    return null;
                }, finalDelayMillis, TimeUnit.MILLISECONDS);
                taskRegistration = plugin.getTaskRegistry().registerTask(scheduledFuture);
            }

            @Override
            public void stop() {
                if (taskRegistration != null) {
                    taskRegistration.unregister();
                }
            }
        };
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
