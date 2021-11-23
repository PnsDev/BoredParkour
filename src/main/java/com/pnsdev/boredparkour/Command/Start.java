package com.pnsdev.boredparkour.Command;

import com.pnsdev.boredparkour.Manager.PKManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Start implements CommandExecutor {

    private PKManager manager;

    public Start(PKManager manager) { this.manager = manager; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        manager.startSession((Player) sender);
        return false;
    }
}
