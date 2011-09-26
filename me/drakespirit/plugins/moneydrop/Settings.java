package me.drakespirit.plugins.moneydrop;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.config.Configuration;

import com.nijikokun.register.payment.md.Methods;

public class Settings {
	
	private static Methods methods = null;
	
	private static boolean spawnerDropsAllowed = true;
	private static ArrayList<Entity> spawnerBlacklist = new ArrayList<Entity>();
	
	private static String preferredEcon;
	private static int materialID;
	private static boolean indieDrop;
	private static double dropValue;
	private static String spoutSound;
	private static boolean spoutNoteEnabled;
	private static String spoutNoteTitle;
	private static String spoutNoteMessage;
	private static boolean chatNoteEnabled;
	private static String chatNote;
	private static int[] playerDrop = new int[10];
	private static boolean[] playerPercent = new boolean[10];
	private static int caveSpiderDropMin;
	private static int caveSpiderDropMax;
	private static double caveSpiderDropFreq;
	private static int chickenDropMin;
	private static int chickenDropMax;
	private static double chickenDropFreq;
	private static int cowDropMin;
	private static int cowDropMax;
	private static double cowDropFreq;
	private static int creeperDropMin;
	private static int creeperDropMax;
	private static double creeperDropFreq;
	private static int endermanDropMin;
	private static int endermanDropMax;
	private static double endermanDropFreq;
	private static int ghastDropMin;
	private static int ghastDropMax;
	private static double ghastDropFreq;
	private static int giantDropMin;
	private static int giantDropMax;
	private static double giantDropFreq;
	private static int humanDropMin;
	private static int humanDropMax;
	private static double humanDropFreq;
	private static int pigDropMin;
	private static int pigDropMax;
	private static double pigDropFreq;
	private static int pigzombieDropMin;
	private static int pigzombieDropMax;
	private static double pigzombieDropFreq;
	private static int sheepDropMin;
	private static int sheepDropMax;
	private static double sheepDropFreq;
	private static int silverfishDropMin;
	private static int silverfishDropMax;
	private static double silverfishDropFreq;
	private static int skeletonDropMin;
	private static int skeletonDropMax;
	private static double skeletonDropFreq;
	private static int slimeDropMin;
	private static int slimeDropMax;
	private static double slimeDropFreq;
	private static int spiderDropMin;
	private static int spiderDropMax;
	private static double spiderDropFreq;
	private static int squidDropMin;
	private static int squidDropMax;
	private static double squidDropFreq;
	private static int wildwolfDropMin;
	private static int wildwolfDropMax;
	private static double wildwolfDropFreq;
	private static int tamedwolfDropMin;
	private static int tamedwolfDropMax;
	private static double tamedwolfDropFreq;
	private static int zombieDropMin;
	private static int zombieDropMax;
	private static double zombieDropFreq;
	private static boolean globalMobDrops;
	private static boolean globalPlayerDrops;
	private static List<String> mobEnabledRegions;
	private static List<String> mobDisabledRegions;
	private static List<String> playerEnabledRegions;
	private static List<String> playerDisabledRegions;
	private static boolean worldGuardEnabled;
	private static boolean spoutEnabled;
	
	public static void initConfig(Configuration config) {
		spawnerDropsAllowed = config.getBoolean("Settings.Mobspawner-Drops-Allowed", true);
		materialID = config.getInt("Settings.Dropped-Material-ID", 266);
		dropValue = config.getDouble("Settings.Dropped-Value", 1.0);
		preferredEcon = config.getString("Settings.Preferred-Economy", "");
		if(preferredEcon.equals("")) {
			preferredEcon = null;
		}
		spoutEnabled = config.getBoolean("Settings.Spout.Enabled", true);
		spoutSound = config.getString("Settings.Spout.Pickup-Sound", "");
		spoutNoteEnabled = config.getBoolean("Settings.Spout.Pickup-Achievement-Notification-Enabled", true);
		spoutNoteTitle = config.getString("Settings.Spout.Pickup-Achievement-Notification-Title", "$$$");
		spoutNoteMessage = config.getString("Settings.Spout.Pickup-Achievement-Notification-Message", "Picked up <money>$");
		chatNoteEnabled = config.getBoolean("Settings.Pickup-Chat-Notification-Enabled", true);
		chatNote = config.getString("Settings.Pickup-Chat-Notification-Message", "Picked up <money>$");
		
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Mob-Attack", "0"), 0);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Player-Attack", "0"), 1);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Block-Explosion", "0"), 2);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Block-Contact", "0"), 3);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Drowning", "0"), 4);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Fall", "0"), 5);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Fire", "0"), 6);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Suffocation", "0"), 7);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Suicide", "0"), 8);
		checkPlayerDropAmount(config.getString("Player-Dropped-Amount.Other", "0"), 9);
		
		worldGuardEnabled = config.getBoolean("Settings.WorldGuard.Enabled", true);
		globalMobDrops = config.getBoolean("Settings.WorldGuard.Global-Mob-Drops", true);
		mobEnabledRegions = config.getStringList("Settings.WorldGuard.Mob-Drop-Enabled-Regions", null);
		if(mobEnabledRegions.size() == 0){
			mobEnabledRegions.add("exampleregionname1");
			mobEnabledRegions.add("exampleregionname2");
			config.setProperty("Settings.WorldGuard.Mob-Drop-Enabled-Regions", mobEnabledRegions);
		}
		mobDisabledRegions = config.getStringList("Settings.WorldGuard.Mob-Drop-Disabled-Regions", null);
		if(mobDisabledRegions.size() == 0){
			mobDisabledRegions.add("exampleregionname");
			config.setProperty("Settings.WorldGuard.Mob-Drop-Disabled-Regions", mobDisabledRegions);
		}
		
		globalPlayerDrops = config.getBoolean("Settings.WorldGuard.Global-Player-Drops", true);
		playerEnabledRegions = config.getStringList("Settings.WorldGuard.Player-Drop-Enabled-Regions", null);
		if(playerEnabledRegions.size() == 0){
			playerEnabledRegions.add("exampleregionname");
			config.setProperty("Settings.WorldGuard.Player-Drop-Enabled-Regions", playerEnabledRegions);
		}
		playerDisabledRegions = config.getStringList("Settings.WorldGuard.Player-Drop-Disabled-Regions", null);
		if(playerDisabledRegions.size() == 0){
			playerDisabledRegions.add("exampleregionname");
			config.setProperty("Settings.WorldGuard.Player-Drop-Disabled-Regions", playerDisabledRegions);
		}
		
		caveSpiderDropMin = config.getInt("Mobs.Cave-Spider.Dropped-Minimum", 0);
		caveSpiderDropMax = config.getInt("Mobs.Cave-Spider.Dropped-Maximum", 0);
		caveSpiderDropFreq = config.getDouble("Mobs.Cave-Spider.Dropped-Frequency", 0);
		
		chickenDropMin = config.getInt("Mobs.Chicken.Dropped-Minimum", 0);
		chickenDropMax = config.getInt("Mobs.Chicken.Dropped-Maximum", 0);
		chickenDropFreq = config.getDouble("Mobs.Chicken.Dropped-Frequency", 0);
		
		cowDropMin = config.getInt("Mobs.Cow.Dropped-Minimum", 0);
		cowDropMax = config.getInt("Mobs.Cow.Dropped-Maximum", 0);
		cowDropFreq = config.getDouble("Mobs.Cow.Dropped-Frequency", 0);
		
		creeperDropMin = config.getInt("Mobs.Creeper.Dropped-Minimum", 0);
		creeperDropMax = config.getInt("Mobs.Creeper.Dropped-Maximum", 0);
		creeperDropFreq = config.getDouble("Mobs.Creeper.Dropped-Frequency", 0);
		
		endermanDropMin = config.getInt("Mobs.Enderman.Dropped-Minimum", 0);
		endermanDropMax = config.getInt("Mobs.Enderman.Dropped-Maximum", 0);
		endermanDropFreq = config.getDouble("Mobs.Enderman.Dropped-Frequency", 0);
		
		ghastDropMin = config.getInt("Mobs.Ghast.Dropped-Minimum", 0);
		ghastDropMax = config.getInt("Mobs.Ghast.Dropped-Maximum", 0);
		ghastDropFreq = config.getDouble("Mobs.Ghast.Dropped-Frequency", 0);
		
		giantDropMin = config.getInt("Mobs.Giant.Dropped-Minimum", 0);
		giantDropMax = config.getInt("Mobs.Giant.Dropped-Maximum", 0);
		giantDropFreq = config.getDouble("Mobs.Giant.Dropped-Frequency", 0);
		
		humanDropMin = config.getInt("Mobs.Human.Dropped-Minimum", 0);
		humanDropMax = config.getInt("Mobs.Human.Dropped-Maximum", 0);
		humanDropFreq = config.getDouble("Mobs.Human.Dropped-Frequency", 0);
		
		pigDropMin = config.getInt("Mobs.Pig.Dropped-Minimum", 0);
		pigDropMax = config.getInt("Mobs.Pig.Dropped-Maximum", 0);
		pigDropFreq = config.getDouble("Mobs.Pig.Dropped-Frequency", 0);
		
		pigzombieDropMin = config.getInt("Mobs.PigZombie.Dropped-Minimum", 0);
		pigzombieDropMax = config.getInt("Mobs.PigZombie.Dropped-Maximum", 0);
		pigzombieDropFreq = config.getDouble("Mobs.PigZombie.Dropped-Frequency", 0);
		
		sheepDropMin = config.getInt("Mobs.Sheep.Dropped-Minimum", 0);
		sheepDropMax = config.getInt("Mobs.Sheep.Dropped-Maximum", 0);
		sheepDropFreq = config.getDouble("Mobs.Sheep.Dropped-Frequency", 0);
		
		silverfishDropMin = config.getInt("Mobs.Silverfish.Dropped-Minimum", 0);
		silverfishDropMax = config.getInt("Mobs.Silverfish.Dropped-Maximum", 0);
		silverfishDropFreq = config.getDouble("Mobs.Silverfish.Dropped-Frequency", 0);
		
		skeletonDropMin = config.getInt("Mobs.Skeleton.Dropped-Minimum", 0);
		skeletonDropMax = config.getInt("Mobs.Skeleton.Dropped-Maximum", 0);
		skeletonDropFreq = config.getDouble("Mobs.Skeleton.Dropped-Frequency", 0);
		
		slimeDropMin = config.getInt("Mobs.Slime.Dropped-Minimum", 0);
		slimeDropMax = config.getInt("Mobs.Slime.Dropped-Maximum", 0);
		slimeDropFreq = config.getDouble("Mobs.Slime.Dropped-Frequency", 0);
		
		spiderDropMin = config.getInt("Mobs.Spider.Dropped-Minimum", 0);
		spiderDropMax = config.getInt("Mobs.Spider.Dropped-Maximum", 0);
		spiderDropFreq = config.getDouble("Mobs.Spider.Dropped-Frequency", 0);
		
		squidDropMin = config.getInt("Mobs.Squid.Dropped-Minimum", 0);
		squidDropMax = config.getInt("Mobs.Squid.Dropped-Maximum", 0);
		squidDropFreq = config.getDouble("Mobs.Squid.Dropped-Frequency", 0);
		
		wildwolfDropMin = config.getInt("Mobs.Wild-Wolf.Dropped-Minimum", 0);
		wildwolfDropMax = config.getInt("Mobs.Wild-Wolf.Dropped-Maximum", 0);
		wildwolfDropFreq = config.getDouble("Mobs.Wild-Wolf.Dropped-Frequency", 0);
		
		tamedwolfDropMin = config.getInt("Mobs.Tamed-Wolf.Dropped-Minimum", 0);
		tamedwolfDropMax = config.getInt("Mobs.Tamed-Wolf.Dropped-Maximum", 0);
		tamedwolfDropFreq = config.getDouble("Mobs.Tamed-Wolf.Dropped-Frequency", 0);
		
		zombieDropMin = config.getInt("Mobs.Zombie.Dropped-Minimum", 0);
		zombieDropMax = config.getInt("Mobs.Zombie.Dropped-Maximum", 0);
		zombieDropFreq = config.getDouble("Mobs.Zombie.Dropped-Frequency", 0);
		
		indieDrop = config.getBoolean("Settings.Independent-Drops", false);
		
		config.save();
		
		//soundcheck
		if(! spoutSound.equals("")) {
			if(! (spoutSound.endsWith(".wav") || spoutSound.endsWith(".ogg") || spoutSound.endsWith(".midi"))) {
				System.err.println("[MoneyDrop] Invalid sound file.");
				spoutSound = "";
			}
		}
	}

	public static Methods getMethods() {
		if(methods == null) {
			methods = new Methods();
		}
		return methods;
	}
	
	public static void createMethods(String preferredEconomy) {
		if(methods == null) {
			if(preferredEconomy == null) {
				methods = new Methods();
			}
			else if(preferredEconomy.equals("")) {
				methods = new Methods();
			}
			else {
				methods = new Methods(preferredEconomy);
			}
		}
	}

	public static int getGiantDropMin() {
		return giantDropMin;
	}

	public static void setGiantDropMin(int giantDropMin) {
		Settings.giantDropMin = giantDropMin;
	}

	public static int getGiantDropMax() {
		return giantDropMax;
	}

	public static void setGiantDropMax(int giantDropMax) {
		Settings.giantDropMax = giantDropMax;
	}

	public static double getGiantDropFreq() {
		return giantDropFreq;
	}

	public static void setGiantDropFreq(double giantDropFreq) {
		Settings.giantDropFreq = giantDropFreq;
	}

	public static int getHumanDropMin() {
		return humanDropMin;
	}

	public static void setHumanDropMin(int humanDropMin) {
		Settings.humanDropMin = humanDropMin;
	}

	public static int getHumanDropMax() {
		return humanDropMax;
	}

	public static void setHumanDropMax(int humanDropMax) {
		Settings.humanDropMax = humanDropMax;
	}

	public static double getHumanDropFreq() {
		return humanDropFreq;
	}

	public static void setHumanDropFreq(double humanDropFreq) {
		Settings.humanDropFreq = humanDropFreq;
	}

	public static String getSpoutNoteTitle() {
		return spoutNoteTitle;
	}

	public static void setSpoutNoteTitle(String spoutNoteTitle) {
		Settings.spoutNoteTitle = spoutNoteTitle;
	}

	public static String getSpoutNoteMessage() {
		return spoutNoteMessage;
	}

	public static void setSpoutNoteMessage(String spoutNoteMessage) {
		Settings.spoutNoteMessage = spoutNoteMessage;
	}

	public static String getChatNote() {
		return chatNote;
	}

	public static void setChatNote(String chatNote) {
		Settings.chatNote = chatNote;
	}

	public static boolean isSpawnerDropsAllowed() {
		return spawnerDropsAllowed;
	}

	public static void setSpawnerDropsAllowed(boolean spawnerDropsAllowed) {
		Settings.spawnerDropsAllowed = spawnerDropsAllowed;
	}

	public static ArrayList<Entity> getSpawnerBlacklist() {
		return spawnerBlacklist;
	}

	public static int getCaveSpiderDropMin() {
		return caveSpiderDropMin;
	}

	public static void setCaveSpiderDropMin(int caveSpiderDropMin) {
		Settings.caveSpiderDropMin = caveSpiderDropMin;
	}

	public static int getCaveSpiderDropMax() {
		return caveSpiderDropMax;
	}

	public static void setCaveSpiderDropMax(int caveSpiderDropMax) {
		Settings.caveSpiderDropMax = caveSpiderDropMax;
	}

	public static double getCaveSpiderDropFreq() {
		return caveSpiderDropFreq;
	}

	public static void setCaveSpiderDropFreq(double caveSpiderDropFreq) {
		Settings.caveSpiderDropFreq = caveSpiderDropFreq;
	}

	public static int getEndermanDropMin() {
		return endermanDropMin;
	}

	public static void setEndermanDropMin(int endermanDropMin) {
		Settings.endermanDropMin = endermanDropMin;
	}

	public static int getEndermanDropMax() {
		return endermanDropMax;
	}

	public static void setEndermanDropMax(int endermanDropMax) {
		Settings.endermanDropMax = endermanDropMax;
	}

	public static double getEndermanDropFreq() {
		return endermanDropFreq;
	}

	public static void setEndermanDropFreq(double endermanDropFreq) {
		Settings.endermanDropFreq = endermanDropFreq;
	}

	public static int getSilverfishDropMin() {
		return silverfishDropMin;
	}

	public static void setSilverfishDropMin(int silverfishDropMin) {
		Settings.silverfishDropMin = silverfishDropMin;
	}

	public static int getSilverfishDropMax() {
		return silverfishDropMax;
	}

	public static void setSilverfishDropMax(int silverfishDropMax) {
		Settings.silverfishDropMax = silverfishDropMax;
	}

	public static double getSilverfishDropFreq() {
		return silverfishDropFreq;
	}

	public static void setSilverfishDropFreq(double silverfishDropFreq) {
		Settings.silverfishDropFreq = silverfishDropFreq;
	}

	public static String getPreferredEcon() {
		return preferredEcon;
	}

	public static double getDropValue() {
		return dropValue;
	}

	public static boolean isPlayerPercent(int index) {
		return playerPercent[index];
	}

	public static boolean isGlobalMobDrops() {
		return globalMobDrops;
	}

	public static boolean isGlobalPlayerDrops() {
		return globalPlayerDrops;
	}

	public static List<String> getMobEnabledRegions() {
		return mobEnabledRegions;
	}

	public static List<String> getMobDisabledRegions() {
		return mobDisabledRegions;
	}

	public static List<String> getPlayerEnabledRegions() {
		return playerEnabledRegions;
	}

	public static List<String> getPlayerDisabledRegions() {
		return playerDisabledRegions;
	}

	public static int getMaterialID() {
		return materialID;
	}

	public static String getSpoutSound() {
		return spoutSound;
	}

	public static boolean isSpoutNoteEnabled() {
		return spoutNoteEnabled;
	}

	public static boolean isChatNoteEnabled() {
		return chatNoteEnabled;
	}

	public static boolean isIndieDrop() {
		return indieDrop;
	}

	public static int getPlayerDrop(int index) {
		return playerDrop[index];
	}

	public static int getChickenDropMin() {
		return chickenDropMin;
	}

	public static int getChickenDropMax() {
		return chickenDropMax;
	}

	public static double getChickenDropFreq() {
		return chickenDropFreq;
	}

	public static int getCowDropMin() {
		return cowDropMin;
	}

	public static int getCowDropMax() {
		return cowDropMax;
	}

	public static double getCowDropFreq() {
		return cowDropFreq;
	}

	public static int getCreeperDropMin() {
		return creeperDropMin;
	}

	public static int getCreeperDropMax() {
		return creeperDropMax;
	}

	public static double getCreeperDropFreq() {
		return creeperDropFreq;
	}

	public static int getGhastDropMin() {
		return ghastDropMin;
	}

	public static int getGhastDropMax() {
		return ghastDropMax;
	}

	public static double getGhastDropFreq() {
		return ghastDropFreq;
	}

	public static int getPigDropMin() {
		return pigDropMin;
	}

	public static int getPigDropMax() {
		return pigDropMax;
	}

	public static double getPigDropFreq() {
		return pigDropFreq;
	}

	public static int getPigzombieDropMin() {
		return pigzombieDropMin;
	}

	public static int getPigzombieDropMax() {
		return pigzombieDropMax;
	}

	public static double getPigzombieDropFreq() {
		return pigzombieDropFreq;
	}

	public static int getSheepDropMin() {
		return sheepDropMin;
	}

	public static int getSheepDropMax() {
		return sheepDropMax;
	}

	public static double getSheepDropFreq() {
		return sheepDropFreq;
	}

	public static int getSkeletonDropMin() {
		return skeletonDropMin;
	}

	public static int getSkeletonDropMax() {
		return skeletonDropMax;
	}

	public static double getSkeletonDropFreq() {
		return skeletonDropFreq;
	}

	public static int getSlimeDropMin() {
		return slimeDropMin;
	}

	public static int getSlimeDropMax() {
		return slimeDropMax;
	}

	public static double getSlimeDropFreq() {
		return slimeDropFreq;
	}

	public static int getSpiderDropMin() {
		return spiderDropMin;
	}

	public static int getSpiderDropMax() {
		return spiderDropMax;
	}

	public static double getSpiderDropFreq() {
		return spiderDropFreq;
	}

	public static int getSquidDropMin() {
		return squidDropMin;
	}

	public static int getSquidDropMax() {
		return squidDropMax;
	}

	public static double getSquidDropFreq() {
		return squidDropFreq;
	}

	public static int getWildwolfDropMin() {
		return wildwolfDropMin;
	}

	public static int getWildwolfDropMax() {
		return wildwolfDropMax;
	}

	public static double getWildwolfDropFreq() {
		return wildwolfDropFreq;
	}

	public static int getTamedwolfDropMin() {
		return tamedwolfDropMin;
	}

	public static int getTamedwolfDropMax() {
		return tamedwolfDropMax;
	}

	public static double getTamedwolfDropFreq() {
		return tamedwolfDropFreq;
	}

	public static int getZombieDropMin() {
		return zombieDropMin;
	}

	public static int getZombieDropMax() {
		return zombieDropMax;
	}

	public static double getZombieDropFreq() {
		return zombieDropFreq;
	}
	
	public static boolean isWorldGuardEnabled() {
		return worldGuardEnabled;
	}

	public static boolean isSpoutEnabled() {
		return spoutEnabled;
	}

	public static void setSpoutEnabled(boolean spoutEnabled) {
		Settings.spoutEnabled = spoutEnabled;
	}

	public static void setMethods(Methods methods) {
		Settings.methods = methods;
	}

	public static void setMaterialID(int materialID) {
		Settings.materialID = materialID;
	}

	public static void setIndieDrop(boolean indieDrop) {
		Settings.indieDrop = indieDrop;
	}

	public static void setSpoutSound(String spoutSound) {
		Settings.spoutSound = spoutSound;
	}

	public static void setSpoutNoteEnabled(boolean spoutNoteEnabled) {
		Settings.spoutNoteEnabled = spoutNoteEnabled;
	}

	public static void setChatNoteEnabled(boolean chatNoteEnabled) {
		Settings.chatNoteEnabled = chatNoteEnabled;
	}

	public static void setChickenDropMin(int chickenDropMin) {
		Settings.chickenDropMin = chickenDropMin;
	}

	public static void setChickenDropMax(int chickenDropMax) {
		Settings.chickenDropMax = chickenDropMax;
	}

	public static void setChickenDropFreq(double chickenDropFreq) {
		Settings.chickenDropFreq = chickenDropFreq;
	}

	public static void setCowDropMin(int cowDropMin) {
		Settings.cowDropMin = cowDropMin;
	}

	public static void setCowDropMax(int cowDropMax) {
		Settings.cowDropMax = cowDropMax;
	}

	public static void setCowDropFreq(double cowDropFreq) {
		Settings.cowDropFreq = cowDropFreq;
	}

	public static void setCreeperDropMin(int creeperDropMin) {
		Settings.creeperDropMin = creeperDropMin;
	}

	public static void setCreeperDropMax(int creeperDropMax) {
		Settings.creeperDropMax = creeperDropMax;
	}

	public static void setCreeperDropFreq(double creeperDropFreq) {
		Settings.creeperDropFreq = creeperDropFreq;
	}

	public static void setGhastDropMin(int ghastDropMin) {
		Settings.ghastDropMin = ghastDropMin;
	}

	public static void setGhastDropMax(int ghastDropMax) {
		Settings.ghastDropMax = ghastDropMax;
	}

	public static void setGhastDropFreq(double ghastDropFreq) {
		Settings.ghastDropFreq = ghastDropFreq;
	}

	public static void setPigDropMin(int pigDropMin) {
		Settings.pigDropMin = pigDropMin;
	}

	public static void setPigDropMax(int pigDropMax) {
		Settings.pigDropMax = pigDropMax;
	}

	public static void setPigDropFreq(double pigDropFreq) {
		Settings.pigDropFreq = pigDropFreq;
	}

	public static void setPigzombieDropMin(int pigzombieDropMin) {
		Settings.pigzombieDropMin = pigzombieDropMin;
	}

	public static void setPigzombieDropMax(int pigzombieDropMax) {
		Settings.pigzombieDropMax = pigzombieDropMax;
	}

	public static void setPigzombieDropFreq(double pigzombieDropFreq) {
		Settings.pigzombieDropFreq = pigzombieDropFreq;
	}

	public static void setSheepDropMin(int sheepDropMin) {
		Settings.sheepDropMin = sheepDropMin;
	}

	public static void setSheepDropMax(int sheepDropMax) {
		Settings.sheepDropMax = sheepDropMax;
	}

	public static void setSheepDropFreq(double sheepDropFreq) {
		Settings.sheepDropFreq = sheepDropFreq;
	}

	public static void setSkeletonDropMin(int skeletonDropMin) {
		Settings.skeletonDropMin = skeletonDropMin;
	}

	public static void setSkeletonDropMax(int skeletonDropMax) {
		Settings.skeletonDropMax = skeletonDropMax;
	}

	public static void setSkeletonDropFreq(double skeletonDropFreq) {
		Settings.skeletonDropFreq = skeletonDropFreq;
	}

	public static void setSlimeDropMin(int slimeDropMin) {
		Settings.slimeDropMin = slimeDropMin;
	}

	public static void setSlimeDropMax(int slimeDropMax) {
		Settings.slimeDropMax = slimeDropMax;
	}

	public static void setSlimeDropFreq(double slimeDropFreq) {
		Settings.slimeDropFreq = slimeDropFreq;
	}

	public static void setSpiderDropMin(int spiderDropMin) {
		Settings.spiderDropMin = spiderDropMin;
	}

	public static void setSpiderDropMax(int spiderDropMax) {
		Settings.spiderDropMax = spiderDropMax;
	}

	public static void setSpiderDropFreq(double spiderDropFreq) {
		Settings.spiderDropFreq = spiderDropFreq;
	}

	public static void setSquidDropMin(int squidDropMin) {
		Settings.squidDropMin = squidDropMin;
	}

	public static void setSquidDropMax(int squidDropMax) {
		Settings.squidDropMax = squidDropMax;
	}

	public static void setSquidDropFreq(double squidDropFreq) {
		Settings.squidDropFreq = squidDropFreq;
	}

	public static void setWildwolfDropMin(int wildwolfDropMin) {
		Settings.wildwolfDropMin = wildwolfDropMin;
	}

	public static void setWildwolfDropMax(int wildwolfDropMax) {
		Settings.wildwolfDropMax = wildwolfDropMax;
	}

	public static void setWildwolfDropFreq(double wildwolfDropFreq) {
		Settings.wildwolfDropFreq = wildwolfDropFreq;
	}

	public static void setTamedwolfDropMin(int tamedwolfDropMin) {
		Settings.tamedwolfDropMin = tamedwolfDropMin;
	}

	public static void setTamedwolfDropMax(int tamedwolfDropMax) {
		Settings.tamedwolfDropMax = tamedwolfDropMax;
	}

	public static void setTamedwolfDropFreq(double tamedwolfDropFreq) {
		Settings.tamedwolfDropFreq = tamedwolfDropFreq;
	}

	public static void setZombieDropMin(int zombieDropMin) {
		Settings.zombieDropMin = zombieDropMin;
	}

	public static void setZombieDropMax(int zombieDropMax) {
		Settings.zombieDropMax = zombieDropMax;
	}

	public static void setZombieDropFreq(double zombieDropFreq) {
		Settings.zombieDropFreq = zombieDropFreq;
	}

	public static void setGlobalMobDrops(boolean globalMobDrops) {
		Settings.globalMobDrops = globalMobDrops;
	}

	public static void setGlobalPlayerDrops(boolean globalPlayerDrops) {
		Settings.globalPlayerDrops = globalPlayerDrops;
	}

	public static void setMobEnabledRegions(List<String> mobEnabledRegions) {
		Settings.mobEnabledRegions = mobEnabledRegions;
	}

	public static void setMobDisabledRegions(List<String> mobDisabledRegions) {
		Settings.mobDisabledRegions = mobDisabledRegions;
	}

	public static void setPlayerEnabledRegions(List<String> playerEnabledRegions) {
		Settings.playerEnabledRegions = playerEnabledRegions;
	}

	public static void setPlayerDisabledRegions(List<String> playerDisabledRegions) {
		Settings.playerDisabledRegions = playerDisabledRegions;
	}

	public static void setWorldGuardEnabled(boolean worldGuardEnabled) {
		Settings.worldGuardEnabled = worldGuardEnabled;
	}

	public static int getDeathCauseIndex(EntityDamageEvent event) {
		if(event == null) {
			return 9;
		}
		if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			LivingEntity attacker;
			if(e.getDamager() instanceof Projectile) {
				attacker = ((Projectile) e.getDamager()).getShooter();
			}
			else if(e.getDamager() instanceof LivingEntity) {
				attacker = (LivingEntity) e.getDamager();
			}
			else {
				return 9;
			}
			if(attacker instanceof Player) {
				return 1;
			}
			else {
				return 0;
			}
		}
		else {
			if(event.getCause() == DamageCause.BLOCK_EXPLOSION) {
				return 2;
			}
			else if(event.getCause() == DamageCause.CONTACT) {
				return 3;
			}
			else if(event.getCause() == DamageCause.DROWNING) {
				return 4;
			}
			else if(event.getCause() == DamageCause.FALL) {
				return 5;
			}
			else if(event.getCause() == DamageCause.FIRE || event.getCause() == DamageCause.FIRE_TICK || event.getCause() == DamageCause.LAVA) {
				return 6;
			}
			else if(event.getCause() == DamageCause.SUFFOCATION) {
				return 7;
			}
			else if(event.getCause() == DamageCause.SUICIDE) {
				return 8;
			}
			else {
				return 9;
			}
		}
	}
	
	private static void checkPlayerDropAmount(String amount, int index) {
		if(amount.endsWith("%")) {
			try {
				playerDrop[index] = Integer.parseInt(amount.substring(0, amount.length() - 1));
				playerPercent[index] = true;
				if(playerDrop[index] < 0) {
					playerDrop[index] = Math.abs(playerDrop[index]);
					System.err.println("[MoneyDrop] Negative percentage at Player-Dropped-Amount, converting to " + playerDrop[index] + "%.");
				}
				if(playerDrop[index] > 100) {
					playerDrop[index] = 100;
					System.err.println("[MoneyDrop] Percentage at Player-Dropped-Amount is >100, lowering to 100%.");
				}
			} catch (NumberFormatException e) {
				System.err.println("[MoneyDrop] Invalid number at Player-Dropped-Amount, defaulting to 0.");
				playerDrop[index] = 0;
				playerPercent[index] = false;
			}
		}
		else {
			playerPercent[index] = false;
			try {
				playerDrop[index] = Integer.parseInt(amount);
			} catch (NumberFormatException e) {
				System.err.println("[MoneyDrop] Invalid number at Player-Dropped-Amount, defaulting to 0.");
				playerDrop[index] = 0;
			}
		}
	}
	
}
