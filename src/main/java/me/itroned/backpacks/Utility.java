package me.itroned.backpacks;

import com.google.common.base.Converter;
import me.itroned.backpacks.Objects.Backpack;
import me.itroned.backpacks.Objects.Tiers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Comparator;
import java.util.UUID;

public class Utility {
    public static NamespacedKey createKey(String nameOfKey){
        return new NamespacedKey(Backpacks.getInstance(), nameOfKey);
    }
    public static void loadBackpack(String[] serialized, ItemStack ownerItem) throws IOException {
        Backpack backpack = B64Serializer.backpackFromB64(serialized, ownerItem);
        Backpacks.getBackpacks().put(backpack.getUuid(), backpack);
    }
    public static Backpack createBackpack(@Nullable String uuid, @NotNull String name, ItemStack ownerItem){
        if(uuid == null){
            uuid = UUID.randomUUID().toString();
        }
        Backpack backpack = new Backpack(uuid, name, ownerItem);
        Backpacks.getBackpacks().put(uuid, backpack);
        return backpack;
    }
    public static void openBackpack(@NotNull Player player, @NotNull String uuid, ItemStack ownerItem){
        try{
            if(!Backpacks.getBackpacks().containsKey(uuid)){
                //System.out.println("Backpack not loaded");
                Backpacks.getInstance().loadSingleBackpack(uuid, ownerItem);
            }
            Backpack backpack = Backpacks.getBackpacks().get(uuid);
            if(!backpack.openBackpack(player)){
                player.sendMessage(ChatColor.RED + "Backpack is already open!");
            }
        }
        catch (Exception e){

        }
    }
    public static ItemStack getPlaceholderItem(){
        ItemStack placeholderItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        //TODO make a better fix to make sure the placeholderitems cannot be removed!!
        ItemMeta meta = placeholderItem.getItemMeta();
        meta.setDisplayName("§c§lToolbar Placeholder");
        meta.getPersistentDataContainer().set(Utility.createKey("odaso"), PersistentDataType.STRING, "sds");
        placeholderItem.setItemMeta(meta);
        return placeholderItem;
    }
    public static ItemStack getRenameBackpackItem(){
        ItemStack renameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta meta = renameTag.getItemMeta();
        meta.setDisplayName("§e§lRename Backpack");
        meta.getPersistentDataContainer().set(Utility.createKey("odaso"), PersistentDataType.STRING, "sds");
        renameTag.setItemMeta(meta);
        return renameTag;
    }
    public static ItemStack getSortBackpackItem(){
        ItemStack renameTag = new ItemStack(Material.HOPPER);
        ItemMeta meta = renameTag.getItemMeta();
        meta.setDisplayName("§a§lSort Backpack");
        meta.getPersistentDataContainer().set(Utility.createKey("odaso"), PersistentDataType.STRING, "sds");
        renameTag.setItemMeta(meta);
        return renameTag;
    }


    public static void renameBackpack(Backpack backpack, Player player){
        if(Backpacks.getPlayerInConversation().containsKey(player)){
            return;
        }
        Backpacks.getPlayerInConversation().put(player, "true");
        player.closeInventory();

        ConversationFactory factory = new ConversationFactory(Backpacks.getInstance());
        Prompt prompt = new StringPrompt() {
            @NotNull
            @Override
            public String getPromptText(@NotNull ConversationContext context) {
                return "§e§lWrite a new name here";
            }

            @Nullable
            @Override
            public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input) {
                if(input == null){
                    return null;
                }
                backpack.updateName(input.replace("|", "§"));
                Backpacks.getPlayerInConversation().remove(player);
                return null;
            }
        };
        factory.withFirstPrompt(prompt);
        Conversation conversation = factory.buildConversation(player);
        conversation.begin();
    }



}
