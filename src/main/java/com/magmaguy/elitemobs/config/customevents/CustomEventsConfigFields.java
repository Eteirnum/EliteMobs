package com.magmaguy.elitemobs.config.customevents;

import com.magmaguy.elitemobs.config.CustomConfigFields;
import com.magmaguy.elitemobs.events.CustomEvent;
import com.magmaguy.magmacore.util.Logger;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CustomEventsConfigFields extends CustomConfigFields {

    @Getter
    @Setter
    private CustomEvent.EventType eventType = CustomEvent.EventType.DEFAULT;
    @Getter
    @Setter
    private List<String> bossFilenames = new ArrayList<>();
    @Getter
    @Setter
    private String startMessage;
    @Getter
    @Setter
    private String endMessage;
    @Getter
    @Setter
    private List<String> eventStartCommands = new ArrayList<>();
    @Getter
    @Setter
    private List<String> eventEndCommands = new ArrayList<>();
    @Getter
    @Setter
    private int announcementPriority = 0;
    @Getter
    @Setter
    private double chance = 0;
    @Getter
    @Setter
    private List<Material> breakableMaterials = new ArrayList<>();
    @Getter
    @Setter
    private double localCooldown = 0;
    @Getter
    @Setter
    private double globalCooldown = 0;
    @Getter
    @Setter
    private double weight = 0;
    @Getter
    @Setter
    private double eventDuration = 0;
    @Getter
    @Setter
    private int eventEndTime = -1;
    @Getter
    @Setter
    private boolean endEventWithBossDeath = true;
    @Getter
    @Setter
    private String spawnType = "";
    @Getter
    @Setter
    private int minimumPlayerCount = 1;

    public CustomEventsConfigFields(String filename, boolean isEnabled) {
        super(filename, isEnabled);
    }

    @Override
    public void processConfigFields() {
        this.isEnabled = processBoolean("isEnabled", isEnabled, true, true);
        this.eventType = processEnum("eventType", eventType, CustomEvent.EventType.DEFAULT, CustomEvent.EventType.class, true);
        if (eventType == CustomEvent.EventType.DEFAULT) {
            Logger.warn("Failed to determine a valid event type for " + filename + " ! This event will not be registered.");
            return;
        }
        this.bossFilenames = processStringList("bossFilenames", bossFilenames, new ArrayList<>(), true);
        if (bossFilenames == null) return;
        this.startMessage = translatable(filename, "startMessage", processString("startMessage", startMessage, null, false));
        this.endMessage = translatable(filename, "endMessage", processString("endMessage", endMessage, null, false));
        this.eventStartCommands = processStringList("eventStartCommands", eventStartCommands, new ArrayList<>(), false);
        this.eventEndCommands = processStringList("eventEndCommands", eventEndCommands, new ArrayList<>(), false);
        this.announcementPriority = processInt("announcementPriority", announcementPriority, 0, false);
        this.chance = processDouble("chance", chance, 0, false);
        this.breakableMaterials = processEnumList("breakableMaterials", breakableMaterials, new ArrayList<>(), Material.class, false);
        this.localCooldown = processDouble("localCooldown", localCooldown, 0, false);
        this.globalCooldown = processDouble("globalCooldown", globalCooldown, 0, false);
        this.weight = processDouble("weight", weight, 0, true);
        this.eventDuration = processDouble("eventDuration", eventDuration, 0, false);
        this.eventEndTime = processInt("eventEndTime", eventEndTime, -1, false);
        this.endEventWithBossDeath = processBoolean("endEventWithBossDeath", endEventWithBossDeath, true, false);
        this.spawnType = processString("spawnType", spawnType, "", false);
        this.minimumPlayerCount = processInt("minimumPlayerCount", minimumPlayerCount, 1, false);
    }

    // FIXME
    //  This is an attempt to replicate what the method does from its name and context clues
    public @NotNull CompletableFuture<Void> setEnabledAndSave(boolean enable) {
        return CompletableFuture.runAsync(() -> {
            this.setEnabled(enable);
            this.getFileConfiguration().set("isEnabled", enable);
            try {
                this.getFileConfiguration().save(this.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

}
