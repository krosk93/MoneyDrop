package me.drakespirit.plugins.moneydrop;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyDrop extends JavaPlugin {
	
	private MDEntityListener el;
	private MDPlayerListener pl;
	private MDServerListener sl;
	

	@Override
	public void onDisable() {
		System.out.println("MoneyDrop has shut down.");
	}

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		el = new MDEntityListener(pm);
		pl = new MDPlayerListener(this);
		
		pm.registerEvent(Type.PLAYER_PICKUP_ITEM, pl, Priority.Normal, this);
		pm.registerEvent(Type.ENTITY_DEATH, el, Priority.Normal, this);
		
		Settings.initConfig(getConfiguration());
		Settings.createMethods(Settings.getPreferredEcon());
		
		sl = new MDServerListener();
		pm.registerEvent(Type.PLUGIN_DISABLE, sl, Priority.Monitor, this);
		pm.registerEvent(Type.PLUGIN_ENABLE, sl, Priority.Monitor, this);
		
		System.out.println("MoneyDrop is up and running!");
	}
	
	public Item[] dropMoney(Location location, int amount) {
		if(amount <= 0) {
			return null;
		}
		int stack = Material.getMaterial(getConfiguration().getInt("Dropped Material ID", 266)).getMaxStackSize();
		int loops = amount / stack;
		if(amount % stack != 0) {
			loops++;
		}
		Item[] items = new Item[loops];
		for (int i = 0; i < loops; i++) {
			if(i == (loops - 1)) {
				items[i] = location.getWorld().dropItemNaturally(location, new ItemStack(Settings.getMaterialID(), amount, (short) 9001));
			}
			else {
				items[i] = location.getWorld().dropItemNaturally(location, new ItemStack(Settings.getMaterialID(), stack, (short) 9001));
				amount -= stack;
			}
		}
		return items;
	}

}
