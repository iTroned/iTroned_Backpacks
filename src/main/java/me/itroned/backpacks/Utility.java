package me.itroned.backpacks;

import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Objects.Tiers;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Utility {
    public static NamespacedKey createKey(String nameOfKey){
        return new NamespacedKey(Backpacks.getInstance(), nameOfKey);
    }
    public static String createBackpack(@Nullable ItemStack[] items, @Nullable String uuid){
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
        }
        String tier;
        if(items == null){
            tier = Tiers.TIER1;
        }
        else if(items.length == 9){
            tier = Tiers.TIER1;
        }
        else if(items.length == 18){
            tier = Tiers.TIER2;
        }
        else if(items.length == 27){
            tier = Tiers.TIER3;
        }
        else if(items.length == 36){
            tier = Tiers.TIER4;
        }
        else if(items.length == 45){
            tier = Tiers.TIER5;
        }
        //TODO fix inventories that somehow get bugged??
        else{
            tier = Tiers.TIER1;
        }
        Backpacks.getBackpacks().put(uuid, new Backpack(uuid, tier, items));
        return uuid;
    }
    public static void openBackpack(@NotNull Player player, @NotNull String uuid){
        try{
            if(!Backpacks.getBackpacks().containsKey(uuid)){
                Backpacks.getInstance().loadSingleBackpack(uuid);
            }
            Backpack backpack = Backpacks.getBackpacks().get(uuid);
            if(!backpack.openBackpack(player)){
                player.sendMessage(ChatColor.RED + "Backpack is already open!");
            }
        }
        catch (Exception e){

        }
    }
}
