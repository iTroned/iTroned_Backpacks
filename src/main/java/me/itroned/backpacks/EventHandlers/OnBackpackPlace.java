package me.itroned.backpacks.EventHandlers;


import me.itroned.backpacks.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

//Makes sure the backpack does not get placed
public class OnBackpackPlace implements Listener {
    @EventHandler
    public void onItemPlace(BlockPlaceEvent event){
        if(event.getItemInHand().getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
            event.setCancelled(true);
        }
    }
}
