package me.itroned.backpacks;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

public class BackpackCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player) sender;
        if(player.hasPermission("ibackpacks.admin")){
            ItemStack icon = new ItemStack(Material.LEATHER);
            String uuid = Utility.createBackpack(null, null);
            ItemMeta meta = icon.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Backpack");
            meta.setLore(Collections.singletonList(ChatColor.RED + "Is backpack"));
            //Sets the new metadata tag
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(Utility.createKey("backpack"), PersistentDataType.STRING, "true");
            //System.out.println(uuid);
            container.set(Utility.createKey("backpackuuid"), PersistentDataType.STRING, uuid);

            icon.setItemMeta(meta);
            player.getInventory().addItem(icon);
            return true;
        }
        return false;
    }
}
