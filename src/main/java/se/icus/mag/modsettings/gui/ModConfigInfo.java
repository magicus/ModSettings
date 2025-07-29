/*
 * Copyright © Magnus Ihse Bursie 2025.
 * This file is released under the MIT License. See LICENSE for full license details.
 */
package se.icus.mag.modsettings.gui;

import net.minecraft.client.gui.screen.Screen;

public record ModConfigInfo(String modId, String modName, Screen configScreen) {}
