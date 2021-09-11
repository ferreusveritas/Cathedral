package com.ferreusveritas.cathedral.util;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Convenience class for building and populating {@linkplain NBTTagCompound compound NBT tags}.
 *
 * @author Harley O'Connor
 */
public final class NBTTagBuilder {

    private final NBTTagCompound compound = new NBTTagCompound();

    public NBTTagBuilder withString(String key, String value) {
        this.compound.setString(key, value);
        return this;
    }

    public NBTTagBuilder withInt(String key, int value) {
        this.compound.setInteger(key, value);
        return this;
    }

    public NBTTagCompound build() {
        return this.compound;
    }

}
