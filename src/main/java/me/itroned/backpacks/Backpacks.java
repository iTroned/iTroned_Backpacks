package me.itroned.backpacks;

import me.itroned.backpacks.EventHandlers.CraftingEvents;
import me.itroned.backpacks.EventHandlers.OnBackpackClose;
import me.itroned.backpacks.EventHandlers.OnBackpackUse;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Objects.Tiers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Backpacks extends JavaPlugin implements Listener {
    private static Backpacks instance;
    //Saves the backpacks during runtime
    private static Map<String, Backpack> backpackMap = new HashMap<String, Backpack>();

    private static File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        instance = this;
        createCustomConfig();

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new OnBackpackUse(), this);
        getServer().getPluginManager().registerEvents(new OnBackpackClose(), this);
        getServer().getPluginManager().registerEvents(new CraftingEvents(), this);
        getServer().getPluginCommand("backpack").setExecutor(new BackpackCommandExecutor());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier1());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier2());
        Bukkit.addRecipe(BackpackRecipes.getRecipeTier3());


        if(instance.getCustomConfig().contains("backpacks")){
            loadBackpacks();
        }
    }

    @Override
    public void onDisable() {
        if(!backpackMap.isEmpty()){
            //System.out.println("Saving backpacks");
            try {
                saveBackpacks();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "backpacks.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("backpacks.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Backpacks getInstance() {
        return instance;
    }
    public static Map<String, Backpack> getBackpacks(){
        return backpackMap;
    }
    public static void saveBackpacks() throws IOException {
        backpackMap.forEach((key, backpack) -> {
            ItemStack[] items = backpack.getInventory().getContents();
            instance.getCustomConfig().set("backpacks." + key, items);
        });
        instance.getCustomConfig().save(customConfigFile);
    }
    public void saveSingleBackpack(String uuid) throws IOException {
        ItemStack[] items = backpackMap.get(uuid).getInventory().getContents();
        instance.getCustomConfig().set("backpacks." + uuid, items);
        instance.getCustomConfig().save(customConfigFile);
    }
    private void loadBackpacks(){
        instance.getCustomConfig().getConfigurationSection("backpacks").getKeys(false).forEach(key ->{
            ItemStack[] items = ((List<ItemStack>) this.getCustomConfig().get("backpacks." + key)).toArray(new ItemStack[0]);
            Utility.createBackpack(items, key);
        });
    }

}
