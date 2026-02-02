package me.hsgamer.topperstale.template;

import me.hsgamer.topper.agent.update.UpdateAgent;
import me.hsgamer.topper.template.topplayernumber.holder.NumberTopHolder;
import me.hsgamer.topper.template.topplayernumber.holder.display.ValueDisplay;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HyTopHolderSettings implements NumberTopHolder.Settings {
    private final Map<String, Object> map;
    private final HyValueDisplaySettings valueDisplaySettings;

    public HyTopHolderSettings(Map<String, Object> map) {
        this.map = map;
        this.valueDisplaySettings = new HyValueDisplaySettings(map);
    }

    @Override
    public Double defaultValue() {
        return Optional.ofNullable(map.get("default-value"))
                .map(Object::toString)
                .map(s -> {
                    try {
                        return Double.parseDouble(s);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .orElse(null);
    }

    @Override
    public ValueDisplay.Settings displaySettings() {
        return valueDisplaySettings;
    }

    @Override
    public boolean showErrors() {
        return Optional.ofNullable(map.get("show-errors"))
                .map(Object::toString)
                .map(String::toLowerCase)
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    @Override
    public boolean resetOnError() {
        return Optional.ofNullable(map.get("reset-on-error"))
                .map(Object::toString)
                .map(String::toLowerCase)
                .map(Boolean::parseBoolean)
                .orElse(true);
    }

    @Override
    public boolean reverse() {
        return Optional.ofNullable(map.get("reverse"))
                .map(String::valueOf)
                .map(Boolean::parseBoolean)
                .orElse(false);
    }

    @Override
    public UpdateAgent.FilterResult filter(UUID uuid) {
        // TODO: Filter based on Permissions
        return UpdateAgent.FilterResult.CONTINUE;
    }

    @Override
    public Map<String, Object> valueProvider() {
        return map;
    }

    public Map<String, Object> map() {
        return map;
    }
}
