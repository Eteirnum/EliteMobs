package com.magmaguy.elitemobs.instanced.dungeons;

import com.magmaguy.elitemobs.api.EliteMobDeathEvent;
import com.magmaguy.elitemobs.mobconstructor.custombosses.CustomBossEntity;
import com.magmaguy.elitemobs.utils.WarningMessage;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class DungeonKillTargetObjective extends DungeonObjective {
    private static List<DungeonKillTargetObjective> dungeonKillTargetObjectiveList = new ArrayList<>();
    @Getter
    private String bossFilename;
    @Getter
    private int targetAmount = 1;
    @Getter
    private int currentAmount = 0;


    public DungeonKillTargetObjective(String objectiveString) {
        super(objectiveString);
        String[] strings = objectiveString.split(":");
        for (String seperatedByColon : strings) {
            String[] separatedByEquals = seperatedByColon.split("=");
            if (separatedByEquals[0].equalsIgnoreCase("filename")) {
                this.bossFilename = separatedByEquals[1];
            } else if (separatedByEquals[0].equalsIgnoreCase("amount")) {
                try {
                    this.targetAmount = Integer.parseInt(separatedByEquals[1]);
                } catch (Exception ex) {
                    new WarningMessage("Value " + separatedByEquals[1] + " is not a valid integer amount!");
                }
            } else {
                new WarningMessage("Invalid entry for objective string! " + objectiveString + " could not be parsed correctly.");
            }
        }
    }

    @Override
    protected void initializeObjective(DungeonInstance dungeonInstance) {
        super.initializeObjective(dungeonInstance);
        dungeonKillTargetObjectiveList.add(this);
    }

    public void incrementKills() {
        currentAmount++;
        if (currentAmount >= targetAmount) {
            super.completed = true;
            dungeonInstance.checkCompletionStatus();
        }
    }

    public static class DungeonKilLTargetObjectiveListener implements Listener {
        @EventHandler
        public void onEliteDeath(EliteMobDeathEvent event) {
            if (!(event.getEliteEntity() instanceof CustomBossEntity)) return;
            CustomBossEntity customBossEntity = (CustomBossEntity) event.getEliteEntity();
            for (DungeonKillTargetObjective dungeonKillTargetObjective : dungeonKillTargetObjectiveList)
                if (customBossEntity.getCustomBossesConfigFields().getFilename().equals(dungeonKillTargetObjective.getBossFilename()))
                    dungeonKillTargetObjective.incrementKills();
        }
    }
}