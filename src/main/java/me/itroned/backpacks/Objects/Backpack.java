package me.itroned.backpacks.Objects;

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
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Backpack implements InventoryHolder {
    private Inventory inventory;
    private String name;
    private String tier;
    private final String uuid;
    private int size;
    private Backpack instance;
    //Makes sure only one player can open it at a time
    private final Map<Player, Boolean> openedBy = new ConcurrentHashMap<>();

    private final ItemStack placeholderItem;

    public Backpack(@NotNull String uuid, String tier, @Nullable ItemStack[] items){
        placeholderItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        //TODO make a better fix to make sure the placeholderitems cannot be removed!!
        ItemMeta meta = placeholderItem.getItemMeta();
        meta.getPersistentDataContainer().set(Utility.createKey("odaso"), PersistentDataType.STRING, "sds");
        placeholderItem.setItemMeta(meta);
        this.uuid = uuid;
        this.tier = tier;
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
        updateName();
        makeInventory();
        if(items == null){
            setContents(null);
            return;
        }
        if(!(size < items.length)){
            setContents(items);
        }
        //TODO Make a fix here if size has been downsized. Should drop the excess items on player location
        else{

        }


    }
    private void makeInventory(){
        inventory = Bukkit.createInventory(this, size, name);
    }

    public void setContents(@Nullable ItemStack... items){
        for(int i = 0; i < 9; i++){
            inventory.setItem(i, placeholderItem);
        }
        if(items != null){
            for(int i = 0; i < items.length; i++){
                inventory.setItem(i + 9, items[i]);
            }
        }
    }

    public void updateName(){
        this.name = tier;
    }
    //Creates a new backpack with a new size
    public boolean upgrade(){
        if(tier.equals(Tiers.TIER5)){
            return false;
        }
        ItemStack[] allItems = getInventory().getContents();
        ArrayList<ItemStack> realItems = new ArrayList<>();
        for(int i = 9; i < allItems.length; i++){
            realItems.add(allItems[i]);
        }
        ItemStack[] items = realItems.toArray(new ItemStack[0]);
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
        updateName();
        makeInventory();
        setContents(items);
        return true;
    }
    public boolean openBackpack(Player player){
        if(!openedBy.isEmpty()) {
            return false;
        }
        openedBy.put(player, true);
        player.openInventory(inventory);

        return true;
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

    @Override
    public @NotNull Inventory getInventory(){
        return inventory;
    }
}
