package me.hsgamer.topperstale.manager;

import me.hsgamer.hscore.builder.FunctionalMassBuilder;
import me.hsgamer.topper.value.core.ValueProvider;
import me.hsgamer.topperstale.ToppersTale;

import java.util.*;

public class ValueProviderManager extends FunctionalMassBuilder<Map<String, Object>, ValueProvider<UUID, Double>> {
    public ValueProviderManager(ToppersTale plugin) {

    }

    @Override
    protected String getType(Map<String, Object> map) {
        return Objects.toString(map.get("type"), "");
    }
}
