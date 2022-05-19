package me.itroned.backpacks;

import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Objects.Tiers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Backpacks extends JavaPlugin implements Listener {
    private static Backpacks instance;
    //Saves the backpacks during runtime
    private static Map<String, Backpack> backpackMap = new HashMap<String, Backpack>();


    @Override
    public void onEnable() {
        instance = this;
        saveResource("config.yml", false);
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginCommand("backpack").setExecutor(new BackpackCommandExecutor());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier1());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier2());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier3());

        if(instance.getConfig().contains("backpacks")){
            loadBackpacks();
        }
    }

    @Override
    public void onDisable() {
        if(!backpackMap.isEmpty()){
            //System.out.println("Saving backpacks");
            saveBackpacks();
        }
    }


    public static Backpacks getInstance() {
        return instance;
    }
    public static Map<String, Backpack> getBackpacks(){
        return backpackMap;
    }
    public static void saveBackpacks(){
        backpackMap.forEach((key, backpack) -> {
            ItemStack[] items = backpack.getInventory().getContents();
            instance.getConfig().set("backpacks." + key, items);
        });
        instance.saveConfig();
    }
    private void loadBackpacks(){
        instance.getConfig().getConfigurationSection("backpacks").getKeys(false).forEach(key ->{
            ItemStack[] items = ((List<ItemStack>) this.getConfig().get("backpacks." + key)).toArray(new ItemStack[0]);
            Utility.createBackpack(items, key);
        });
    }

    @EventHandler
    public void onItemClick(PlayerInteractEvent event) {
        ItemStack itemUsed = event.getItem();
        if (itemUsed == null) {
            return;
        }
        Player player = event.getPlayer();
        if ((event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && (itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack1"), PersistentDataType.STRING) || itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack2"), PersistentDataType.STRING) || itemUsed.getItemMeta().getPersistentDataContainer().has(Utility.createKey("backpack3"), PersistentDataType.STRING))) {
            String uuid = itemUsed.getItemMeta().getPersistentDataContainer().get(Utility.createKey("uuid"), PersistentDataType.STRING);
            Utility.openBackpack(player, uuid);
        }

    }
    @EventHandler
    public void onGUIClose(InventoryCloseEvent event){
        if(event.getView().getTitle().contains(Tiers.BASE)){
            saveBackpacks();
        }
    }
    @EventHandler
    public void onCraft(CraftItemEvent event){
        //System.out.println("Crafting");
        ItemStack item = event.getCurrentItem();
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.has(Utility.createKey("backpack1"), PersistentDataType.STRING)){
            String uuid = Utility.createBackpack(null, null);
            container.set(Utility.createKey("uuid"), PersistentDataType.STRING, uuid);
            item.setItemMeta(meta);
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
                String uuid = oldContainer.get(Utility.createKey("uuid"), PersistentDataType.STRING);
                //System.out.println(uuid);
                container.set(Utility.createKey("uuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                backpackMap.get(uuid).upgrade();
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
                String uuid = oldContainer.get(Utility.createKey("uuid"), PersistentDataType.STRING);
                //System.out.println(uuid);
                container.set(Utility.createKey("uuid"), PersistentDataType.STRING, uuid);
                item.setItemMeta(meta);
                backpackMap.get(uuid).upgrade();
            }
        }
    }
}
