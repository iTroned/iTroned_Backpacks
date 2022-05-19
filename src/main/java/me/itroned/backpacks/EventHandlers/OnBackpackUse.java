package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class OnBackpackUse implements Listener {
    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        ItemStack itemUsed = event.getItem();
        if (itemUsed == null) {
            return;
        }
        Player player = event.getPlayer();
        if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack1"), PersistentDataType.STRING) || itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack2"), PersistentDataType.STRING) || itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack3"), PersistentDataType.STRING))) {
            String uuid = itemUsed.getItemMeta().getPersistentDataContainer().get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
            Utility.openBackpack(player, uuid);
        }

    }
}
