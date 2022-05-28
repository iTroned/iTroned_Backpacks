package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import oshi.util.Util;

import javax.xml.transform.Result;

public class OnBackpackClick implements Listener {
    @EventHandler
    public void onBackpackClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof Backpack)) {
            if(event.getCurrentItem() == null && !event.getAction().equals(InventoryAction.HOTBAR_SWAP)){
                return;
            }
            if (event.getInventory().getHolder() instanceof ShulkerBox) {
                checkBackpackMove(event);
            }
            return;
        }
        if(event.getCurrentItem() == null && !event.getAction().equals(InventoryAction.HOTBAR_SWAP)){
            return;
        }
        Backpack backpack = (Backpack) event.getView().getTopInventory().getHolder();
        if (backpack == null) {
            Bukkit.getLogger().info("Backpack does not exist!");
        }
        Player player = (Player) event.getWhoClicked();
        //Makes sure the item that is moved is not a backpack to make sure a backpack is not moved into a backpack
        checkBackpackMove(event);

        if (event.getSlot() < 9 && event.getClickedInventory().getHolder() instanceof Backpack) {
            event.setCancelled(true);
            int slot = event.getSlot();
            switch (slot) {
                case 0:
                    Utility.renameBackpack(backpack, player);
                    break;
                case 1:
                    backpack.sortInventory(player);
                    break;
                case 8:
                    if (event.getCurrentItem() == null) {
                        break;
                    }
                    backpack.setFilterItem(player.getItemOnCursor(), player);
                    break;
                default:
                    break;
            }

        }
    }

    private void checkBackpackMove(InventoryClickEvent event) {
        if ((event.getCurrentItem() != null) && (event.getCurrentItem().getItemMeta() != null) && (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING))) {
            event.setCancelled(true);
        }
        else if((event.getAction().equals(InventoryAction.HOTBAR_SWAP)) || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)){
            int slot = event.getHotbarButton();
            ItemStack item = event.getView().getBottomInventory().getItem(slot);
            if(item == null){
                return;
            }
            if(item.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
                event.setCancelled(true);
            }
        }
    }
}
