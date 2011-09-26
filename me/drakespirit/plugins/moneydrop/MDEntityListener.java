package me.drakespirit.plugins.moneydrop;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import com.nijikokun.register.payment.md.Methods;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class MDEntityListener extends EntityListener {
	
	private WorldGuardPlugin wg;
	
	public MDEntityListener(PluginManager pm) {
		wg = (WorldGuardPlugin) pm.getPlugin("WorldGuard");
	}

	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		if(! Settings.isSpawnerDropsAllowed()) {
			if(Settings.getSpawnerBlacklist().contains(event.getEntity())) {
				Settings.getSpawnerBlacklist().remove(event.getEntity());
				return;
			}
		}
		if(event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if(Settings.isWorldGuardEnabled()) {
				boolean enable = Settings.isGlobalPlayerDrops();
				if(wg != null) {
					Vector pt = BukkitUtil.toVector(p.getLocation());			 
					RegionManager regionManager = wg.getRegionManager(p.getWorld());
					if(regionManager != null) {
						Iterator<ProtectedRegion> i = regionManager.getApplicableRegions(pt).iterator();
						int size = 0;
						while (i.hasNext()) {
							ProtectedRegion r = i.next();
							if(Settings.getPlayerDisabledRegions().contains(r.getId())) {
								if(r.volume() < size || size == 0) {
									enable = false;
									size = r.volume();
								}
							}
							else if(Settings.getPlayerEnabledRegions().contains(r.getId())) {
								if(r.volume() < size || size == 0) {
									enable = true;
									size = r.volume();
								}
							}
						}
					}
				}
				if(! enable) {
					return;
				}
			}
			int index = Settings.getDeathCauseIndex(p.getLastDamageCause());
			int dropAmount = Settings.getPlayerDrop(index);
			if(dropAmount == 0) {
				return;
			}
			Methods m = Settings.getMethods();
			if(m.hasMethod()) {
				if(m.getMethod().hasAccount(p.getName())) {
					double amount = 0;
					double balance = m.getMethod().getAccount(p.getName()).balance();
					if(dropAmount > 0) {
						if(Settings.isPlayerPercent(index)) {
							amount = (balance * dropAmount * 0.01);
						}
						else {
							double a = (dropAmount * Settings.getDropValue());
							if(a > balance) {
								amount = a - balance;
							}
							else {
								amount = a;
							}
						}
						m.getMethod().getAccount(p.getName()).subtract(amount);
					}
					else {
						double a = Math.abs(dropAmount) * Settings.getDropValue();
						if(balance > a) {
							m.getMethod().getAccount(p.getName()).set(a);
							amount = balance - a;
						}
					}
					if(Settings.isIndieDrop()) {
						dropIndieMonnies(p.getLocation(), (int)(amount / Settings.getDropValue()));
					}
					else {
						dropListMonnies(event.getDrops(), (int)(amount / Settings.getDropValue()));
					}
				}
			}
		}else{
			if (event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event.getEntity().getLastDamageCause();
				LivingEntity attacker;
				if(e.getDamager() instanceof Projectile) {
					attacker = ((Projectile) e.getDamager()).getShooter();
				}
				else if(e.getDamager() instanceof LivingEntity) {
					attacker = (LivingEntity) e.getDamager();
				}
				else {
					return;
				}
				if(attacker instanceof Player) {
					if(event.getEntity() instanceof CaveSpider) {
						if(Math.random() < Settings.getCaveSpiderDropFreq()) {
							mobDrop(event, Settings.getCaveSpiderDropMin(), Settings.getCaveSpiderDropMax());
						}
					}
					else if(event.getEntity() instanceof Chicken) {
						if(Math.random() < Settings.getChickenDropFreq()) {
							mobDrop(event, Settings.getChickenDropMin(), Settings.getChickenDropMax());
						}
					}
					else if(event.getEntity() instanceof Cow) {
						if(Math.random() < Settings.getCowDropFreq()) {
							mobDrop(event, Settings.getCowDropMin(), Settings.getCowDropMax());
						}
					}
					else if(event.getEntity() instanceof Creeper) {
						if(Math.random() < Settings.getCreeperDropFreq()) {
							mobDrop(event, Settings.getCreeperDropMin(), Settings.getCreeperDropMax());
						}
					}
					else if(event.getEntity() instanceof Enderman) {
						if(Math.random() < Settings.getEndermanDropFreq()) {
							mobDrop(event, Settings.getEndermanDropMin(), Settings.getEndermanDropMax());
						}
					}
					else if(event.getEntity() instanceof Ghast) {
						if(Math.random() < Settings.getGhastDropFreq()) {
							mobDrop(event, Settings.getGhastDropMin(), Settings.getGhastDropMax());
						}
					}
					else if(event.getEntity() instanceof Giant) {
						if(Math.random() < Settings.getGiantDropFreq()) {
							mobDrop(event, Settings.getGiantDropMin(), Settings.getGiantDropMax());
						}
					}
					else if(event.getEntity() instanceof HumanEntity) {
						if(Math.random() < Settings.getHumanDropFreq()) {
							mobDrop(event, Settings.getHumanDropMin(), Settings.getHumanDropMax());
						}
					}
					else if(event.getEntity() instanceof Pig) {
						if(Math.random() < Settings.getPigDropFreq()) {
							mobDrop(event, Settings.getPigDropMin(), Settings.getPigDropMax());
						}
					}
					else if(event.getEntity() instanceof PigZombie) {
						if(Math.random() < Settings.getPigzombieDropFreq()) {
							mobDrop(event, Settings.getPigzombieDropMin(), Settings.getPigzombieDropMax());
						}
					}
					else if(event.getEntity() instanceof Sheep) {
						if(Math.random() < Settings.getSheepDropFreq()) {
							mobDrop(event, Settings.getSheepDropMin(), Settings.getSheepDropMax());
						}
					}
					else if(event.getEntity() instanceof Skeleton) {
						if(Math.random() < Settings.getSkeletonDropFreq()) {
							mobDrop(event, Settings.getSkeletonDropMin(), Settings.getSkeletonDropMax());
						}
					}
					else if(event.getEntity() instanceof Slime) {
						if(Math.random() < Settings.getSlimeDropFreq()) {
							mobDrop(event, Settings.getSlimeDropMin(), Settings.getSlimeDropMax());
						}
					}
					else if(event.getEntity() instanceof Spider) {
						if(Math.random() < Settings.getSpiderDropFreq()) {
							mobDrop(event, Settings.getSpiderDropMin(), Settings.getSpiderDropMax());
						}
					}
					else if(event.getEntity() instanceof Squid) {
						if(Math.random() < Settings.getSquidDropFreq()) {
							mobDrop(event, Settings.getSquidDropMin(), Settings.getSquidDropMax());
						}
					}
					else if(event.getEntity() instanceof Wolf) {
						Wolf w = (Wolf) event.getEntity();
						if(w.isTamed()) {
							if(Math.random() < Settings.getTamedwolfDropFreq()) {
								mobDrop(event, Settings.getTamedwolfDropMin(), Settings.getTamedwolfDropMax());
							}
						}
						else {
							if(Math.random() < Settings.getWildwolfDropFreq()) {
								mobDrop(event, Settings.getWildwolfDropMin(), Settings.getWildwolfDropMax());
							}
						}
					}
					else if(event.getEntity() instanceof Zombie) {
						if(Math.random() < Settings.getZombieDropFreq()) {
							mobDrop(event, Settings.getZombieDropMin(), Settings.getZombieDropMax());
						}
					}
				}
			}
		}
	}
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(! Settings.isSpawnerDropsAllowed()) {
			if(event.getSpawnReason() == SpawnReason.SPAWNER) {
				Settings.getSpawnerBlacklist().add(event.getEntity());
			}
		}
	}

	private void mobDrop(EntityDeathEvent event, int min, int max) {
		if(Settings.isWorldGuardEnabled()) {
			boolean enable = Settings.isGlobalMobDrops();
			if(wg != null) {
				try {
					Vector pt = BukkitUtil.toVector(event.getEntity().getLocation());			 
					RegionManager regionManager = wg.getRegionManager(event.getEntity().getWorld());
					if(regionManager != null) {
						Iterator<ProtectedRegion> i = regionManager.getApplicableRegions(pt).iterator();
						int size = 0;
						while (i.hasNext()) {
							ProtectedRegion r = i.next();
							if(Settings.getMobDisabledRegions().contains(r.getId())) {
								if(r.volume() < size || size == 0) {
									enable = false;
									size = r.volume();
								}
							}
							else if(Settings.getMobEnabledRegions().contains(r.getId())) {
								if(r.volume() < size || size == 0) {
									enable = true;
									size = r.volume();
								}
							}
						}
					}
				}
				catch (NoSuchMethodError e) {
					System.err.println("[MoneyDrop] You might be using an outdated version of WorldGuard that MoneyDrop doesn't support. Try updating WorldGuard to the most recent version. WorldGuard support will be disabled untill the next restart.");
					Settings.setWorldGuardEnabled(false);
					e.printStackTrace();
				}
			}
			if(! enable) {
				return;
			}
		}
		if(max < min) {
			max = min;
		}
		int amount =  min + ((int) (Math.random() * ((max - min) + 1)));
		
		if(Settings.isIndieDrop()) {
			dropIndieMonnies(event.getEntity().getLocation(), amount);
		}
		else {
			dropListMonnies(event.getDrops(), amount);
		}
	}
	
	private void dropIndieMonnies(Location loc, int amount) {
		if(amount <= 0) {
			return;
		}
		Material mat = Material.getMaterial(Settings.getMaterialID());
		if(mat == null) {
			System.err.println("[MoneyDrop] Material not found, please check your Dropped-Material-ID setting.");
			return;
		}
		int stack = mat.getMaxStackSize();
		int loops = amount / stack;
		if(amount % stack != 0) {
			loops++;
		}
		for (int i = 0; i < loops; i++) {
			if(i == (loops - 1)) {
				loc.getWorld().dropItemNaturally(loc, new ItemStack(Settings.getMaterialID(), amount, (short) 9001));
			}
			else {
				loc.getWorld().dropItemNaturally(loc, new ItemStack(Settings.getMaterialID(), stack, (short) 9001));
				amount -= stack;
			}
		}
	}
	
	private void dropListMonnies(List<ItemStack> list, int amount) {
		if(amount <= 0) {
			return;
		}
		Material mat = Material.getMaterial(Settings.getMaterialID());
		if(mat == null) {
			System.err.println("[MoneyDrop] Material not found, please check your Dropped-Material-ID setting.");
			return;
		}
		int stack = mat.getMaxStackSize();
		int loops = amount / stack;
		if(amount % stack != 0) {
			loops++;
		}
		
		for (int i = 0; i < loops; i++) {
			if(i == (loops - 1)) {
				list.add(new ItemStack(Settings.getMaterialID(), amount, (short) 9001));
			}
			else {
				list.add(new ItemStack(Settings.getMaterialID(), stack, (short) 9001));
				amount -= stack;
			}
		}
	}

}
