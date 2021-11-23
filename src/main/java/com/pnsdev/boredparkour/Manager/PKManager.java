package com.pnsdev.boredparkour.Manager;

import com.pnsdev.boredparkour.BoredParkour;
import com.pnsdev.boredparkour.Data.PKInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.util.*;

public class PKManager {
    private final HashMap<Player, PKInfo> activeSessions = new HashMap<>();
    private final BoredParkour core;

    public PKManager(BoredParkour core) {
        this.core = core;
        Bukkit.getScheduler().runTaskTimer(core, () -> {
            Set<Map.Entry<Player, PKInfo>> setOfEntries = activeSessions.entrySet();
            Iterator<Map.Entry<Player, PKInfo>> iterator = setOfEntries.iterator();

            while (iterator.hasNext()) {
                Map.Entry<Player, PKInfo> entry = iterator.next();
                Player player = entry.getKey();
                PKInfo info = entry.getValue();
                if (player.getLocation().subtract(0, 1, 0).getBlock().equals(info.getNextBlock())) {
                    info.setStreak(info.getStreak() + 1);

                    info.getCurrentBlock().setType(Material.AIR);

                    // Delete the block
                    FallingBlock fallingBlock = player.getWorld().spawnFallingBlock(info.getCurrentBlock().getLocation().add(0.5, 0, 0.5), info.getMaterial().createBlockData());
                    Bukkit.getScheduler().runTaskLater(core, fallingBlock::remove, 20);

                    info.setCurrentBlock(info.getNextBlock());

                    Block newBlock = findGoodLocation(player);
                    info.setNextBlock(newBlock);
                    newBlock.setType(info.getMaterial());
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                } else if (player.getLocation().getY() < info.getCurrentBlock().getY() - 5) {
                    info.getCurrentBlock().setType(Material.AIR);
                    info.getNextBlock().setType(Material.AIR);
                    player.teleport(info.getStartingLoc());
                    player.sendMessage("§cYou lost the game!");
                    player.sendMessage("§cYour streak was " + info.getStreak());
                    iterator.remove();
                }
            }
        }, 0, 5);
    }

    public PKInfo getSession(Player player) {
        return activeSessions.get(player);
    }

    public void startSession(Player player) {
        Location location = player.getLocation();
        Block block = player.getLocation().add(0, 20, 0).getBlock();
        block.setType(Material.COAL_BLOCK);
        player.teleport(block.getLocation().add(0, 1, 0));
        Bukkit.getScheduler().runTaskLater(core, () -> {
            activeSessions.put(player, new PKInfo(location, block.getLocation().subtract(0, 1, 0).getBlock(), block, Material.SEA_LANTERN));
        }, 20);
    }

    private Block findGoodLocation(Player player) {
        Location location = player.getLocation().subtract(0, 1, 0).getBlock().getLocation().add(0.5, 0, 0.5);
        while (true) {
            Location newLocation = location.clone().add(
                    new Random().nextInt(9) - 4,
                    new Random().nextInt(3) - 1,
                    new Random().nextInt(9) - 4
            ).add(0.5, 0, 0.5);
            System.out.println("----------------");
            System.out.println(newLocation.distance(location));
            if (newLocation.distance(location) < 2 || newLocation.distance(location) > 4) continue;
            return newLocation.getBlock();
        }
    }

    private Material getRandomMaterial() {
        while (true) {
            Material material = Material.values()[new Random().nextInt(Material.values().length)];
            if (material.isSolid() && material.isBlock()) return material;
        }
    }
}
