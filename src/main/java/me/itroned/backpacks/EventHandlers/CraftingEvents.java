package me.itroned.backpacks.EventHandlers;

import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;

public class CraftingEvents implements Listener {
    @EventHandler
    public void onCraft(CraftItemEvent event) throws IOException {
        //System.out.println("Crafting");
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(Utility.createKey("backpack1"), PersistentDataType.STRING)){
            Backpack backpack = Utility.createBackpack(null, event.getWhoClicked().getName(), item);
            container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, backpack.getUuid());
            item.setItemMeta(meta);
            //Backpacks.getInstance().unloadSingleBackpack(backpack.getUuid());
        }
        if(container.has(Utility.createKey("backpack2"), PersistentDataType.STRING)){
            ItemStack oldBackpack = event.getClickedInventory().getItem(5);
            ItemMeta oldMeta = oldBackpack.getItemMeta();
            PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
            if(!oldContainer.has(Utility.createKey("backpack1"), PersistentDataType.STRING)){
                event.getWhoClicked().sendMessage("§l§eThat was not a TIER 1 backpack used!");
                event.setCancelled(true);
            }
            else{
                String uuid = oldContainer.get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
                container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                Backpacks.getBackpacks().get(uuid).upgrade();
                //Backpacks.getInstance().unloadSingleBackpack(uuid);
            }
        }
        if(container.has(Utility.createKey("backpack3"), PersistentDataType.STRING)){
            ItemStack oldBackpack = event.getClickedInventory().getItem(5);
            ItemMeta oldMeta = oldBackpack.getItemMeta();
            PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
            if(!oldContainer.has(Utility.createKey("backpack2"), PersistentDataType.STRING)){
                event.getWhoClicked().sendMessage("§l§eThat was not a TIER 2 backpack used!");
                event.setCancelled(true);
            }
            else{
                String uuid = oldContainer.get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
                //System.out.println(uuid);
                container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                Backpacks.getBackpacks().get(uuid).upgrade();
                //Backpacks.getInstance().unloadSingleBackpack(uuid);
            }
        }
        if(container.has(Utility.createKey("backpack4"), PersistentDataType.STRING)){
            ItemStack oldBackpack = event.getClickedInventory().getItem(5);
            ItemMeta oldMeta = oldBackpack.getItemMeta();
            PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
            if(!oldContainer.has(Utility.createKey("backpack3"), PersistentDataType.STRING)){
                event.getWhoClicked().sendMessage("§l§eThat was not a TIER 3 backpack used!");
                event.setCancelled(true);
            }
            else{
                String uuid = oldContainer.get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
                //System.out.println(uuid);
                container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                Backpacks.getBackpacks().get(uuid).upgrade();
                //Backpacks.getInstance().unloadSingleBackpack(uuid);
            }
        }
        if(container.has(Utility.createKey("backpack5"), PersistentDataType.STRING)){
            ItemStack oldBackpack = event.getClickedInventory().getItem(5);
            ItemMeta oldMeta = oldBackpack.getItemMeta();
            PersistentDataContainer oldContainer = oldMeta.getPersistentDataContainer();
            if(!oldContainer.has(Utility.createKey("backpack4"), PersistentDataType.STRING)){
                event.getWhoClicked().sendMessage("§l§eThat was not a TIER 4 backpack used!");
                event.setCancelled(true);
            }
            else{
                String uuid = oldContainer.get(Utility.createKey("backpackuuid"), PersistentDataType.STRING);
                //System.out.println(uuid);
                container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                Backpacks.getBackpacks().get(uuid).upgrade();
                //Backpacks.getInstance().unloadSingleBackpack(uuid);
            }
        }
    }
    @EventHandler
    public void onSelectCrafting(PrepareItemCraftEvent event){
        try{
            ItemStack itemToCraft = event.getInventory().getResult();
            if(itemToCraft.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack2"), PersistentDataType.STRING)){
                if(!(event.getView().getTopInventory().getItem(5).getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack1"), PersistentDataType.STRING))){
                    event.getInventory().setResult(null);
                }

            }
            else if(itemToCraft.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack3"), PersistentDataType.STRING)){
                if(!(event.getView().getTopInventory().getItem(5).getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack2"), PersistentDataType.STRING))){
                    event.getInventory().setResult(null);
                }
            }
            else if(itemToCraft.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack4"), PersistentDataType.STRING)){
                if(!(event.getView().getTopInventory().getItem(5).getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack3"), PersistentDataType.STRING))){
                    event.getInventory().setResult(null);
                }
            }
            else if(itemToCraft.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack5"), PersistentDataType.STRING)){
                if(!(event.getView().getTopInventory().getItem(5).getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack4"), PersistentDataType.STRING))){
                    event.getInventory().setResult(null);
                }
            }
        }
        catch (Exception e){

        }

    }
}
