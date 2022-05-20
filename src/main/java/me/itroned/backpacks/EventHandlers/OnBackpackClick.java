package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Utility;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class OnBackpackClick implements Listener {
    @EventHandler
    public void onBackpackClick(InventoryClickEvent event){
        if(!(event.getInventory().getHolder() instanceof Backpack)){
            return;
        }
        if(event.getCurrentItem() == null){
            return;
        }
        //Backpacks.getInstance().getLogger().info("Backpack clicked");
        if(event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
            event.setCancelled(true);
        }
        if(event.getCurrentItem().getType().equals(Material.SHULKER_BOX)){
            event.setCancelled(true);
        }
        if(event.getSlot() < 9 && event.getClickedInventory().getHolder() instanceof Backpack){
            event.setCancelled(true);
        }
    }
}
