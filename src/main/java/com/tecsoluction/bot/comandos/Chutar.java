package com.tecsoluction.bot.comandos;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Chutar extends ListenerAdapter {
	
	final TextChannel tchannel = null;

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		List<String> list = Arrays.asList(args);
		
		if (args[0].equalsIgnoreCase("!chutar")) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle(":x: Especife uma Membro pra Chutar");
				usage.setDescription("Uso: !chutar @ `"  + "Nome Membro [# Membro]`");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else {
				try {
				
				
					// Success
					
					final Member member = event.getMember();
					
					final Member target = 	event.getMessage().getMentionedMembers().get(0);

					if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)) {
						event.getChannel().sendMessage("Voce não tem permissão pra chutar esse membro").queue();
					    return;
					}

					final Member selfMember = event.getGuild().getSelfMember();

					if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)) {
						event.getChannel().sendMessage("Vc perdeu a permissão pra chutar esse membro").queue();
					    return;
					}

					final String reason = String.join(" ", list.subList(1, list.size()));

					event.getChannel().getGuild()
					        .kick(target, reason)
					        .reason(reason)
					        .queue(
					                (__) -> 	event.getChannel().sendMessage("Chutado com Sucesso").queue(),
					                (error) -> 	event.getChannel().sendMessageFormat("não pode chutar %s", error.getMessage()).queue()
					        );
					
					

				}
				catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Muitas Nomes Selecionadoss");
						error.setDescription(" 1 Membro podem ser chutado por vez.");
						event.getChannel().sendMessage(error.build()).queue();
					}
					else {
						// Messages too old
//						EmbedBuilder error = new EmbedBuilder();
//						error.setColor(0xff3923);
//						error.setTitle("🔴 Mensagens Selecionadas Superior a duas semanas");
//						error.setDescription("Mensagens com mais de duas semanas nao podem ser deletadas.");
//						event.getChannel().sendMessage(error.build()).queue();
					}
				}
			}
		}
		
		
		
	}
	



}