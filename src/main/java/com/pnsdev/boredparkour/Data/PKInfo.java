package com.pnsdev.boredparkour.Data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

@Getter
@Setter
public class PKInfo {
    private int streak = -1;
    private Block nextBlock;
    private Block currentBlock;
    private Location startingLoc;
    private Material material;
    private final boolean randomMaterial = false;

    public PKInfo(Location startingLoc, Block currentBlock, Block nextBlock, Material material) {
        this.currentBlock = currentBlock;
        this.nextBlock = nextBlock;
        this.startingLoc = startingLoc;
        this.material = material;
    }
}
