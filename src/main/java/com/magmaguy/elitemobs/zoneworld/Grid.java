package com.magmaguy.elitemobs.zoneworld;

import com.magmaguy.elitemobs.config.MobCombatSettingsConfig;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

    private static HashMap<EliteChunk, Integer> chunkMap = new HashMap<>();

    public static HashMap<EliteChunk, Integer> getChunkMap() {
        return chunkMap;
    }

    private static void registerChunk(EliteChunk eliteChunk, int tier) {
        chunkMap.put(eliteChunk, tier);
    }

    public static void initializeGrid() {
        for (int x = -100; x < 101; x++)
            for (int z = -100; z < 101; z++) {
                EliteChunk eliteChunk = new EliteChunk(x, z);
                int value = (Math.abs(x) + Math.abs(z)) / 2;
                int tier = 0;
                if (value > 1) {
                    tier = ThreadLocalRandom.current().nextInt(value) + value;
                    if (ThreadLocalRandom.current().nextInt() < 0.05)
                        tier = ThreadLocalRandom.current().nextInt(value);
                    if (ThreadLocalRandom.current().nextInt() < 0.05)
                        tier = ThreadLocalRandom.current().nextInt(value * 2) + value;
                }
                tier = tier > MobCombatSettingsConfig.naturalElitemobLevelCap / MobCombatSettingsConfig.perTierLevelIncrease ?
                        MobCombatSettingsConfig.naturalElitemobLevelCap / MobCombatSettingsConfig.perTierLevelIncrease : tier;
                registerChunk(eliteChunk, tier);
            }
    }

    public static double getMobTierFromLocation(Location location) {
        return ThreadLocalRandom.current().nextDouble() + chunkMap.get(getEliteChunk(location));
    }

    public static EliteChunk getEliteChunk(Location location) {
        Location customSpawnLocation = location.getWorld().getSpawnLocation().clone();
        //Get the 0,0 chunk location
        customSpawnLocation = customSpawnLocation.subtract(new Vector(EliteChunk.getGridSize() / 2, 0, EliteChunk.getGridSize() / 2));
        //Assuming the chunks increment in 100
        int chunkX = (int) Math.floor((location.getX() - customSpawnLocation.getX()) / EliteChunk.getGridSize());
        int chunkZ = (int) Math.floor((location.getZ() - customSpawnLocation.getZ()) / EliteChunk.getGridSize());

        return EliteChunk.getEliteChunk(chunkX, chunkZ);
    }

}
