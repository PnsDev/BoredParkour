package com.pnsdev.boredparkour;

import com.pnsdev.boredparkour.Command.Start;
import com.pnsdev.boredparkour.Manager.PKManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BoredParkour extends JavaPlugin {

    PKManager manager;

    @Override
    public void onEnable() { // Plugin startup logic
        manager = new PKManager(this);

        Bukkit.getServer().getPluginCommand("start").setExecutor(new Start(manager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
