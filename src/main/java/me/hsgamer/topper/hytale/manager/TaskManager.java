package me.hsgamer.topper.hytale.manager;

import com.hypixel.hytale.server.core.HytaleServer;
import me.hsgamer.topper.agent.core.Agent;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    private final ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);

    public Agent createTaskAgent(Runnable runnable, long delayMillis, boolean async) {
        long finalDelayMillis = delayMillis <= 0 ? 1000 : delayMillis;
        return new Agent() {
            private ScheduledFuture<?> scheduledFuture;

            @Override
            public void start() {
                scheduledFuture = (async ? scheduler : HytaleServer.SCHEDULED_EXECUTOR).scheduleAtFixedRate(runnable, finalDelayMillis, finalDelayMillis, TimeUnit.MILLISECONDS);
            }

            @Override
            public void stop() {
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(true);
                }
            }
        };
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
