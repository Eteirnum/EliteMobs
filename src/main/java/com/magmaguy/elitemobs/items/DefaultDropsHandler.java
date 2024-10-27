package com.magmaguy.elitemobs.items;

import com.eteirnum.core.EteirnumCore;
import com.eteirnum.core.player.attributes.PlayerAttributes;
import com.magmaguy.elitemobs.api.EliteMobDeathEvent;
import com.magmaguy.elitemobs.config.ItemSettingsConfig;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MagmaGuy on 04/06/2017.
 */
public class DefaultDropsHandler implements Listener {

    private final Random random = new Random();
    private final List<ItemStack> wornItems = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EliteMobDeathEvent event) {

        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!event.getEliteEntity().isVanillaLoot()) return;
        if (event.getEntityDeathEvent() == null) return;

        List<ItemStack> droppedItems = event.getEntityDeathEvent().getDrops();
        int mobLevel = event.getEliteEntity().getLevel();

        if (mobLevel > ItemSettingsConfig.getMaxLevelForDefaultLootMultiplier())
            mobLevel = ItemSettingsConfig.getMaxLevelForDefaultLootMultiplier();

        inventoryItemsConstructor((LivingEntity) event.getEntity());

        if (ItemSettingsConfig.getDefaultLootMultiplier() != 0) {
            for (ItemStack itemStack : droppedItems) {

                if (itemStack == null) continue;
                if (itemStack.getType().equals(Material.AIR)) continue;
                boolean itemIsWorn = false;

                for (ItemStack wornItem : wornItems)
                    if (wornItem.isSimilar(itemStack))
                        itemIsWorn = true;

                if (!itemIsWorn)
                    for (int i = 0; i < mobLevel * 0.1 * ItemSettingsConfig.getDefaultLootMultiplier(); i++)
                        event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(), itemStack);

            }
        }

        event.getEntityDeathEvent().setDroppedExp(0);

        Entity causingEntity = event.getEntityDeathEvent().getDamageSource().getCausingEntity();
        if (causingEntity == null) return;
        if (!(causingEntity instanceof Player causingPlayer)) return;
        PlayerAttributes attributes = EteirnumCore.instance.getPlayerAttributesManager().get(causingPlayer.getUniqueId());

        // FIXME
        //  Rebalance the exp gained
        int rand = random.nextInt(5) + 1;
        double level = event.getEliteEntity().getLevel() * ItemSettingsConfig.getDefaultExperienceMultiplier();

        attributes.addExp((int) (rand * level));

    }


    private List<ItemStack> inventoryItemsConstructor(LivingEntity entity) {

        EntityEquipment equipment = entity.getEquipment();

        if (equipment.getItemInMainHand() != null && !equipment.getItemInMainHand().getType().equals(Material.AIR))
            wornItems.add(equipment.getItemInMainHand());

        if (equipment.getHelmet() != null)
            wornItems.add(equipment.getHelmet());

        if (equipment.getChestplate() != null)
            wornItems.add(equipment.getChestplate());

        if (equipment.getLeggings() != null)
            wornItems.add(equipment.getLeggings());

        if (equipment.getBoots() != null)
            wornItems.add(equipment.getBoots());

        return wornItems;

    }

}
