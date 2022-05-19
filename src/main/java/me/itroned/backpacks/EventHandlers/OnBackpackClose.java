package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Objects.Tiers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.io.IOException;

public class OnBackpackClose implements Listener {
    @EventHandler
    public void onGUIClose(InventoryCloseEvent event) throws IOException {
        if(!(event.getInventory().getHolder() instanceof Backpack)) {
            return;
        }
        Backpack backpack = (Backpack) event.getInventory().getHolder();
        if(!(event.getPlayer() instanceof Player)){
            return;
        }
        Player player = (Player) event.getPlayer();
        backpack.removeOpened(player);
        backpack.saveBackpack();
    }
}
