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
    public void onBackpackClick(InventoryClickEvent event){
        if(!(event.getInventory().getHolder() instanceof Backpack)){
            if(event.getInventory().getHolder() instanceof ShulkerBox){
                if(event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
                    event.setCancelled(true);
                }
            }
            return;
        }
        if(event.getCurrentItem() == null){
            return;
        }
        Backpack backpack = (Backpack) event.getView().getTopInventory().getHolder();
        if(backpack == null){
            Bukkit.getLogger().info("Backpack does not exist!");
        }
        Player player = (Player) event.getWhoClicked();
        //Backpacks.getInstance().getLogger().info("Backpack clicked");
        if(event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
            event.setCancelled(true);
        }
        //System.out.println(event.getClick().isKeyboardClick());
        /*if(event.getAction().equals(InventoryAction.HOTBAR_SWAP)){

        }
        System.out.println(event.getAction());*/
        /*int button = event.getHotbarButton();
        System.out.println(button);
        ItemStack itemMoved = event.getView().getBottomInventory().getItem(event.getHotbarButton());
        System.out.println(event.getClick());*/
        /*if(itemMoved != null && itemMoved.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack"), PersistentDataType.STRING)){
            event.setCancelled(true);
        }*/
        //TODO check if you can abuse shulker boxes
        /*if(event.getCurrentItem().getType().equals(Material.SHULKER_BOX)){
            event.setCancelled(true);
        }*/
        if(event.getSlot() < 9 && event.getClickedInventory().getHolder() instanceof Backpack){
            event.setCancelled(true);
            int slot = event.getSlot();
            switch (slot){
                case 0:
                    Utility.renameBackpack(backpack, player);
                    break;
                case 1:
                    backpack.sortInventory(player);
                    break;
                case 8:
                    if(event.getCurrentItem() == null){
                        break;
                    }
                    backpack.setFilterItem(player.getItemOnCursor(), player);
                    break;
                default:
                    break;
            }

        }
    }
    /*@EventHandler
    public void onInventoryAction(InventoryInteractEvent event){

    }*/
}
