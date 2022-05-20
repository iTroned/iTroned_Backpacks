package me.itroned.backpacks.Objects;

import me.itroned.backpacks.Backpacks;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
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

    public Backpack(@NotNull String uuid, String tier, @Nullable ItemStack[] items){
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
        updateName();
        makeInventory();
        if(items == null){
            return;
        }
        if(size >= items.length){
            setContents(items);
        }
        //TODO Make a fix here if size has been downsized. Should drop the excess items on player location
        else{

        }


    }
    private void makeInventory(){
        inventory = Bukkit.createInventory(this, size, name);
    }

    public void setContents(ItemStack... items){
        for(int i = 0; i < items.length; i++){
            inventory.setItem(i, items[i]);
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
        ItemStack[] contents = getInventory().getContents();
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
        updateName();
        makeInventory();
        setContents(contents);
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
