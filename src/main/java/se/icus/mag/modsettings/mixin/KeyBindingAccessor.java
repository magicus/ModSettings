/*
 * Copyright © Magnus Ihse Bursie 2025.
 * This file is released under the MIT License. See LICENSE for full license details.
 */
package se.icus.mag.modsettings.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @Accessor
    InputUtil.Key getBoundKey();
}
