package me.itroned.backpacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.UUID;

public class BackpackRecipes {
    public static ShapedRecipe getRecipeTier1(){
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§l§aBackpack Tier 1");
        meta.setLore(Collections.singletonList(ChatColor.RED + "Small pouch"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack1"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier1");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.DIAMOND);
        recipe.setIngredient('T', Material.NETHERITE_INGOT);
        recipe.setIngredient('X', Material.NETHER_STAR);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier2(){
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§l§9Backpack Tier 2");
        meta.setLore(Collections.singletonList(ChatColor.RED + "More like a bag"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack2"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier2");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.DIAMOND_BLOCK);
        recipe.setIngredient('T', Material.NETHERITE_INGOT);
        recipe.setIngredient('X', Material.LEATHER);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier3(){
        ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§l§6Backpack Tier 3");
        meta.setLore(Collections.singletonList(ChatColor.RED + "Bottomless"));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack3"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier3");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.PLAYER_HEAD);
        recipe.setIngredient('T', Material.NETHERITE_INGOT);
        recipe.setIngredient('X', Material.LEATHER);

        return recipe;
    }
}
