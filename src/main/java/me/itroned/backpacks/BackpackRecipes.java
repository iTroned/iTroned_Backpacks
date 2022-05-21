package me.itroned.backpacks;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.UUID;

public class BackpackRecipes {
    public static ShapedRecipe getRecipeTier1(){
        ItemStack item = getBackpackHead();
        //ItemStack item = new ItemStack(Material.LEATHER);
        ItemMeta meta = item.getItemMeta();

        //meta.addEnchant(Enchantment.DURABILITY, 1, true);
        //meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setDisplayName("§a§lBackpack Tier 1");
        meta.setLore(Collections.singletonList("§aBackpack Tier 1"));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
        container.set(Utility.createKey("backpack1"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier1");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.LEATHER);
        recipe.setIngredient('T', Material.IRON_INGOT);
        recipe.setIngredient('X', Material.SHULKER_BOX);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier2(){
        ItemStack item = getBackpackHead();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§b§lBackpack Tier 2");
        meta.setLore(Collections.singletonList("§bBackpack Tier 2"));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
        container.set(Utility.createKey("backpack2"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier2");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.LEATHER);
        recipe.setIngredient('T', Material.GOLD_INGOT);
        recipe.setIngredient('X', Material.PLAYER_HEAD);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier3(){
        ItemStack item = getBackpackHead();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§d§lBackpack Tier 3");
        meta.setLore(Collections.singletonList("§dBackpack Tier 3"));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
        container.set(Utility.createKey("backpack3"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier3");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.LEATHER);
        recipe.setIngredient('T', Material.DIAMOND);
        recipe.setIngredient('X', Material.PLAYER_HEAD);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier4(){
        ItemStack item = getBackpackHead();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§c§lBackpack Tier 4");
        meta.setLore(Collections.singletonList("§cBackpack Tier 4"));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
        container.set(Utility.createKey("backpack4"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier4");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.LEATHER);
        recipe.setIngredient('T', Material.EMERALD);
        recipe.setIngredient('X', Material.PLAYER_HEAD);

        return recipe;
    }
    public static ShapedRecipe getRecipeTier5(){
        ItemStack item = getBackpackHead();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§e§lBackpack Tier 5");
        meta.setLore(Collections.singletonList("§eBackpack Tier 5"));
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
        container.set(Utility.createKey("backpack5"), PersistentDataType.STRING, "true");

        NamespacedKey key = Utility.createKey("backpacktier5");

        item.setItemMeta(meta);
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("TET", "EXE", "TET");
        recipe.setIngredient('E', Material.LEATHER);
        recipe.setIngredient('T', Material.NETHERITE_INGOT);
        recipe.setIngredient('X', Material.PLAYER_HEAD);

        return recipe;
    }
    private static ItemStack getBackpackHead(){
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "40b1b53674918391a07a9d00582c058f9280bc526a716c796ee5eab4be10a760"));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {

        }
        item.setItemMeta(meta);
        return item;
    }
}
