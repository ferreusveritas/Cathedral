package com.ferreusveritas.cathedral.util;

import com.ferreusveritas.cathedral.ModConstants;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * @author Harley O'Connor
 */
public final class InterModCommsUtils {

    public static void addChiselVariation(String group, Block block, int meta) {
        addChiselVariation(group, String.valueOf(block.getRegistryName()), meta);
    }

    public static void addChiselVariation(String group, String block, int meta) {
        FMLInterModComms.sendMessage(
                ModConstants.CHISEL_ID,
                "add_variation",
                new NBTTagBuilder()
                        .withString("group", group)
                        .withString("block", block)
                        .withInt("meta", meta)
                        .build()
        );
    }

}
