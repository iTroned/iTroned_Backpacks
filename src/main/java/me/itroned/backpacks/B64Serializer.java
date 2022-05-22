package me.itroned.backpacks;

import me.itroned.backpacks.Objects.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class B64Serializer{

    public static String inventoryToB64(Inventory inventory){
        try{
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream);

            bukkitStream.writeInt(inventory.getSize());
            for(int i = 0; i < inventory.getSize(); i++){
                bukkitStream.writeObject(inventory.getItem(i));
            }

            bukkitStream.close();

            return Base64Coder.encodeLines(byteStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Something went wrong when saving.", e);
        }
    }

    public static Inventory inventoryFromB64(String data) throws IOException{
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream);
            Inventory inventory = Bukkit.getServer().createInventory(null, bukkitStream.readInt());

            for(int i = 0; i < inventory.getSize(); i++){
                inventory.setItem(i, (ItemStack) bukkitStream.readObject());
            }

            bukkitStream.close();
            return inventory;
        }
        catch (ClassNotFoundException e){
            throw new IOException("Unable to find class.", e);
        }
    }

    public static String itemStackArrayToB64(ItemStack[] items) throws IllegalStateException{
        try{
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream);

            bukkitStream.writeInt(items.length);

            for (ItemStack item : items) {
                bukkitStream.writeObject(item);
            }

            bukkitStream.close();
            return Base64Coder.encodeLines(byteStream.toByteArray());
        }
        catch (Exception e){
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    public static ItemStack[] itemStackArrayFromB64(String data) throws IOException{
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream);
            ItemStack[] items = new ItemStack[bukkitStream.readInt()];

            for(int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) bukkitStream.readObject();
            }

            bukkitStream.close();
            return items;
        }
        catch (ClassNotFoundException e){
            throw new IOException("Unable to decode class type.", e);
        }
    }
    public static String itemStackToB64(ItemStack item) throws IllegalStateException{
        try{
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitStream = new BukkitObjectOutputStream(byteStream);
            bukkitStream.writeObject(item);
            bukkitStream.close();
            return Base64Coder.encodeLines(byteStream.toByteArray());
        }
        catch (Exception e){
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
    public static ItemStack itemStackFromB64(String data) throws IOException{
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream bukkitStream = new BukkitObjectInputStream(byteStream);
            ItemStack item = (ItemStack) bukkitStream.readObject();
            bukkitStream.close();
            return item;
        }
        catch (ClassNotFoundException e){
            throw new IOException("Unable to decode class type.", e);
        }
    }

    public static String[] backpackToB64(Backpack backpack) throws IllegalStateException {
        ItemStack[] allItems = backpack.getInventory().getContents();
        ArrayList<ItemStack> realItems = new ArrayList<>();
        for(int i = 9; i < allItems.length; i++){
            realItems.add(allItems[i]);
        }
        ItemStack[] items = realItems.toArray(new ItemStack[0]);
        String b64Items = itemStackArrayToB64(items);
        String name = backpack.getName();
        String tier = backpack.getTier();
        String uuid = backpack.getUuid();
        String filterItem = backpack.getFilterItem().toString();
        return new String[] {b64Items, name, tier, uuid, filterItem};
    }

    public static Backpack backpackFromB64(String[] data, ItemStack ownerItem) throws IOException {
        ItemStack[] items = itemStackArrayFromB64(data[0]);
        return new Backpack(items, data[1], data[2], data[3], ownerItem, Material.valueOf(data[4]));
    }
}
