package me.neznamy.tab.platforms.bukkit.nms.storage.packet;

import lombok.SneakyThrows;
import me.neznamy.tab.platforms.bukkit.nms.storage.nms.NMSStorage;
import me.neznamy.tab.shared.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class PacketPlayOutScoreboardDisplayObjectiveStorage {

    public static Class<?> CLASS;
    public static Constructor<?> CONSTRUCTOR;
    public static Field POSITION;
    public static Field OBJECTIVE_NAME;

    public static void load() throws NoSuchMethodException {
        CONSTRUCTOR = CLASS.getConstructor(int.class, PacketPlayOutScoreboardObjectiveStorage.ScoreboardObjective);
        POSITION = ReflectionUtils.getOnlyField(CLASS, int.class);
        OBJECTIVE_NAME = ReflectionUtils.getOnlyField(CLASS, String.class);
    }

    @SneakyThrows
    public static Object build(int slot, String objective) {
        return CONSTRUCTOR.newInstance(slot, NMSStorage.getInstance().newScoreboardObjective(objective));
    }
}
