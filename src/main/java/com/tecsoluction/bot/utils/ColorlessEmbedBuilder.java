package com.tecsoluction.bot.utils;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class ColorlessEmbedBuilder extends EmbedBuilder {
	
	
	@NotNull
	@Override
	public MessageEmbed build() {
		super.setColor(Color.decode("#2f3136"));
		return super.build();
	}

}
