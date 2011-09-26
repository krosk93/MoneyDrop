package me.drakespirit.plugins.moneydrop;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.getspout.spoutapi.SpoutManager;

import com.nijikokun.register.payment.Methods;

public class MDPlayerListener extends PlayerListener {
	
	public static final ConcurrentHashMap<String, Notification> notemap = new ConcurrentHashMap<String, Notification>();
	private MoneyDrop md;
	
	public MDPlayerListener(MoneyDrop moneydrop) {
		md = moneydrop;
	}
	
	@Override
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if(! event.isCancelled()) {
			if(event.getItem().getItemStack().getDurability() == 9001) {
				Methods m = Settings.getMethods();
				if(m.hasMethod()) {
					if(m.getMethod().hasAccount(event.getPlayer().getName())) {
						double a = (event.getItem().getItemStack().getAmount() * Settings.getDropValue());
						a = (double)(((int)(a * 1000))*0.001);
						m.getMethod().getAccount(event.getPlayer().getName()).add(a);
						event.getItem().remove();
						spoutSound(event.getPlayer());
						addNotification(event.getPlayer(), a);
					}
				}
				event.setCancelled(true);
			}
		}
	}
	
	private void spoutSound(Player player) {
		if(Settings.isSpoutEnabled()) {
			if(! Settings.getSpoutSound().equals("")) {
				if(player.getServer().getPluginManager().isPluginEnabled("Spout")) {
					if(SpoutManager.getPlayer(player).isSpoutCraftEnabled()) {
						SpoutManager.getSoundManager().playCustomSoundEffect(md, SpoutManager.getPlayer(player), Settings.getSpoutSound(), false);
					}
				}
			}
		}
		
	}
	
	private void addNotification(Player player, double a) {
		if(Settings.isSpoutNoteEnabled() || Settings.isChatNoteEnabled()) {
			Notification n = notemap.get(player.getName());
			if(n == null) {
				n = new Notification(player, a);
				player.getServer().getScheduler().scheduleAsyncDelayedTask(md, n, 20);
				notemap.put(player.getName(), n);
			}
			else {
				n.addMoney(a);
			}
		}
	}

}
