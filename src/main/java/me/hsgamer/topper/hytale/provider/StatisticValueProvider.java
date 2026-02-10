package me.hsgamer.topper.hytale.provider;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import me.hsgamer.topper.value.core.ValueProvider;
import me.hsgamer.topper.value.core.ValueWrapper;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class StatisticValueProvider implements ValueProvider<UUID, Double> {
    private final String statistic;

    public StatisticValueProvider(Map<String, Object> map) {
        this.statistic = Optional.ofNullable(map.get("statistic"))
                .map(Objects::toString)
                .orElse(null);
    }

    private @Nonnull ValueWrapper<Double> apply(@Nonnull UUID uuid) {
        if (statistic == null) return ValueWrapper.notHandled();
        return Optional.ofNullable(Universe.get().getPlayer(uuid))
                .map(PlayerRef::getHolder)
                .map(holder -> holder.getComponent(EntityStatMap.getComponentType()))
                .map(statMap -> statMap.get(EntityStatType.getAssetMap().getIndex(statistic)))
                .map(EntityStatValue::get)
                .map(Float::doubleValue)
                .map(ValueWrapper::handled)
                .orElseGet(() -> ValueWrapper.handled(null));
    }

    @Override
    public void accept(UUID uuid, Consumer<ValueWrapper<Double>> callback) {
        callback.accept(apply(uuid));
    }
}
