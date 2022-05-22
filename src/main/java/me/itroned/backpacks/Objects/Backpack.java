package me.itroned.backpacks.Objects;

import me.itroned.backpacks.B64Serializer;
import me.itroned.backpacks.Backpacks;
import me.itroned.backpacks.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Backpack implements InventoryHolder, Serializable {
    private Inventory inventory;
    private Backpack instance;
    private String name;
    private String tier;
    private final String uuid;
    private int size;
    private ItemStack ownerItem;
    private Material filterItem;

    //Makes sure only one player can open it at a time
    private final Map<Player, Boolean> openedBy = new ConcurrentHashMap<>();
    private final ItemStack placeholderItem = Utility.getPlaceholderItem();

    //The one used to create a new backpack item
    public Backpack(@NotNull String uuid, @NotNull String name, ItemStack ownerItem){
        this.uuid = uuid;
        this.ownerItem = ownerItem;
        this.tier = Tiers.TIER1;
        this.name = "§a§lBackpack §8§l- " + name;
        this.size = 18;
        makeInventory();
        updateName(this.name);
        setFilterItem(new ItemStack(Material.COBBLESTONE), null);
        setContents(null);
    }
    public Backpack(ItemStack[] items, String name, String tier, String uuid, ItemStack ownerItem, Material filterItem){
        this.name = name;
        this.tier = tier;
        this.uuid = uuid;
        this.ownerItem = ownerItem;
        instance = this;
        switch (tier) {
            case Tiers.TIER1:
                this.size = 9;
                break;
            case Tiers.TIER2:
                this.size = 18;
                break;
            case Tiers.TIER3:
                this.size = 27;
                break;
            case Tiers.TIER4:
                this.size = 36;
                break;
            case Tiers.TIER5:
                this.size = 45;
                break;
            default:
                this.size = 0;
                break;
        }
        if(size != 0){
            size += 9;
        }
        makeInventory();
        updateName(this.name);
        setContents(items);
        setFilterItem(new ItemStack(filterItem), null);
    }
    public String[] serializeBackpack(){
        return B64Serializer.backpackToB64(this);
    }
    private void makeInventory(){
        inventory = Bukkit.createInventory(this, size, name);
    }

    public void setContents(@Nullable ItemStack... items){
        inventory.setItem(0, Utility.getRenameBackpackItem());
        inventory.setItem(1, Utility.getSortBackpackItem());
        for(int i = 2; i < 8; i++){
            inventory.setItem(i, placeholderItem);
        }
        if(items != null){
            for(int i = 0; i < items.length; i++){
                inventory.setItem(i + 9, items[i]);
            }
        }

    }

    public void updateName(String name){
        this.name = name;
        ItemMeta meta = ownerItem.getItemMeta();
        if(meta != null){
            meta.setDisplayName(name);
            ownerItem.setItemMeta(meta);
        }
        ItemStack[] items = getRealItems();
        makeInventory();
        setContents(items);
        if(filterItem != null){
            setFilterItem(new ItemStack(filterItem), null);
        }
        else{
            setFilterItem(new ItemStack(Material.COBBLESTONE), null);
        }

    }
    //Creates a new backpack with a new size
    public boolean upgrade(){
        if(tier.equals(Tiers.TIER5)){
            return false;
        }
        ItemStack[] items = getRealItems();
        if(tier.equals(Tiers.TIER1)){
            tier = Tiers.TIER2;
            size = 18;
        }
        else if(tier.equals(Tiers.TIER2)){
            tier = Tiers.TIER3;
            size = 27;
        }
        else if(tier.equals(Tiers.TIER3)){
            tier = Tiers.TIER4;
            size = 36;
        }
        else if(tier.equals(Tiers.TIER4)){
            tier = Tiers.TIER5;
            size = 45;
        }
        if(size != 0){
            size += 9;
        }
        makeInventory();
        setContents(items);
        updateName(name);
        return true;
    }

    private ItemStack[] getRealItems() {
        ItemStack[] allItems = getInventory().getContents();
        ArrayList<ItemStack> realItems = new ArrayList<>();
        for(int i = 9; i < allItems.length; i++){
            realItems.add(allItems[i]);
        }
        return realItems.toArray(new ItemStack[0]);
    }
    public void refreshBackpack(Player player){
        player.closeInventory();
        player.openInventory(inventory);
    }

    public boolean openBackpack(Player player){
        if(!openedBy.isEmpty()) {
            return false;
        }
        openedBy.put(player, true);
        player.openInventory(inventory);

        return true;
    }
    public void sortInventory(Player player){
        ItemStack[] items = getRealItems();
        ArrayList<ItemStack> itemList = new ArrayList<>();
        Collections.addAll(itemList, items);

        itemList.sort(Backpacks.getInstance().getComparator());
        player.closeInventory();
        makeInventory();
        setContents(null);
        setFilterItem(new ItemStack(filterItem), null);
        for(ItemStack item : itemList){
            if(item == null){
                continue;
            }
            inventory.addItem(item);
        }
        openBackpack(player);
    }
    public void setFilterItem(@NotNull ItemStack item, @Nullable Player player){
        if(item.getType().equals(Material.AIR)){
            return;
        }
        ItemStack newItem = new ItemStack(item.getType());
        ItemMeta meta = newItem.getItemMeta();
        if(meta != null){
            meta.setDisplayName("§6§lCurrently Picking Up");
            meta.getPersistentDataContainer().set(Utility.createKey("odaso"), PersistentDataType.STRING, "sds");
            newItem.setItemMeta(meta);
            filterItem = item.getType();
            setSlot(8, newItem, player);
        }
    }
    public void setSlot(int index, @NotNull ItemStack item, @Nullable Player player){
        if(index > 8 || index < 0){
            return;
        }
        if(player == null){
            inventory.setItem(index, item);
            return;
        }
        player.closeInventory();
        inventory.setItem(index, item);
        openBackpack(player);
    }
    public void setOwnerItem(ItemStack item){
        ownerItem = item;
    }
    public void removeOpened(Player player){
        openedBy.remove(player);
    }
    public void saveBackpack() throws IOException {
        Backpacks.getInstance().saveSingleBackpack(uuid);
    }
    public Backpack getInstance(){
        return instance;
    }
    public boolean isOpen(){
        return !openedBy.isEmpty();
    }
    public int getSize(){
        return size;
    }
    public String getUuid(){
        return uuid;
    }
    public String getTier(){
        return tier;
    }
    public String getName(){
        return name;
    }
    public Material getFilterItem(){
        return filterItem;
    }

    @Override
    public @NotNull Inventory getInventory(){
        return inventory;
    }
}
