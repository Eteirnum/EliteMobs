package com.magmaguy.elitemobs.config.customitems.premade;

import com.magmaguy.elitemobs.config.customitems.CustomItemsConfigFields;
import com.magmaguy.elitemobs.items.customitems.CustomItem;
import org.bukkit.Material;

import java.util.Arrays;

public class ChallengersHelmetConfig extends CustomItemsConfigFields {
    public ChallengersHelmetConfig(){
        super("challengers_helmet", true, Material.DIAMOND_HELMET, "&cChallenger's Helmet", Arrays.asList("&2Awarded to those who challenge the", "&2Wood League Arena!"));
        setEnchantments(Arrays.asList("PROTECTION_ENVIRONMENTAL,5", "PROTECTION_EXPLOSIONS,4", "PROTECTION_PROJECTILE,4", "MENDING,1", "DURABILITY,5"));
        setItemType(CustomItem.ItemType.UNIQUE);
        setLevel(40);
    }
}
