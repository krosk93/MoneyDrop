package me.drakespirit.plugins.moneydrop;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class MDServerListener extends ServerListener {

	@Override
	public void onPluginDisable(PluginDisableEvent event) {
		if (Settings.getMethods() != null && Settings.getMethods().hasMethod()) {
            if(Settings.getMethods().checkDisabled(event.getPlugin())) {
            	System.out.println("[MoneyDrop] Economy disabled. Money pickup disabled.");
            }
        }
	}

	@Override
	public void onPluginEnable(PluginEnableEvent event) {
		if (! Settings.getMethods().hasMethod()) {
            if(Settings.getMethods().setMethod(event.getPlugin())) {
                System.out.println("[MoneyDrop] Economy found. Money pickup enabled.");
            }
        }
	}

}
