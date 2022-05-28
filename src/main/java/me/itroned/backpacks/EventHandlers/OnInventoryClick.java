package me.itroned.backpacks.EventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventoryClick implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Bukkit.getLogger().info(event.getClick().toString());
        Bukkit.getLogger().info(event.getAction().toString());
        Bukkit.getLogger().info(Integer.toString(event.getHotbarButton()));
    }
}
