package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Utility;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class OnItemPickup implements Listener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        if(!(event.getEntity() instanceof Player)){
            return;
        }
        boolean hasBackpack = false;
        Player player = (Player) event.getEntity();
        Inventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();
        World world = player.getWorld();
        ArrayList<ItemStack> backpacks = new ArrayList<>();
        for(ItemStack invItem : contents){
            if(invItem == null){
                continue;
            }
            if(invItem.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
                hasBackpack = true;
                backpacks.add(invItem);
            }
        }
        if(!hasBackpack){
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        for(ItemStack bpItem : backpacks){
            String uuid = bpItem.getItemMeta().getPersistentDataContainer().get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
            if(!(Backpacks.getBackpacks().containsKey(uuid))){
                continue;
            }
            Backpack backpack = Backpacks.getBackpacks().get(uuid);
            AtomicBoolean dropSound = new AtomicBoolean(true);
            if(item.getType().equals(backpack.getFilterItem())){
                HashMap <Integer, ItemStack> excessItems = backpack.getInventory().addItem(item);
                if(!excessItems.isEmpty()){
                    excessItems.forEach((key, itemToDrop) ->{
                        //TODO fix this where the amount is wrong when player inventory is full as well as backpack. When it picks up a stack of 64 but you only have room for 1 the value of itemToDrop is 1 instead of 64??
                        //System.out.println(itemToDrop.getAmount());
                        HashMap <Integer, ItemStack> excessItems2 = player.getInventory().addItem(itemToDrop);
                        //System.out.println(excessItems2.size());
                        if(!excessItems2.isEmpty()){
                            excessItems2.forEach((key2, itemToDrop2) ->{
                                //System.out.println(itemToDrop2.getAmount());
                                world.dropItemNaturally(player.getLocation(), itemToDrop2);
                            });
                        }
                    });
                }
                if(dropSound.get()){
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, .5F, 1.0F);
                }
                event.setCancelled(true);
                event.getItem().remove();
            }
        }


    }
}
