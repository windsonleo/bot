package com.tecsoluction.bot.comandos;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Apagar extends ListenerAdapter {
	
	

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase("!apagar")) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle(":x: Especife uma Quantidade pra deletar");
				usage.setDescription("Uso: !Apagar # `"  + "Quantidade de Mensagens [# qtd de mensagens]`");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else {
				try {
					List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
					event.getChannel().deleteMessages(messages).queue();
				
					// Success
					EmbedBuilder success = new EmbedBuilder();
					success.setColor(0x22ff2a);
					success.setTitle("✅ Mensagens Deletada com Sucesso" + args[1] + " Mensagens.");
					event.getChannel().sendMessage(success.build()).queue();
				}
				catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Muitas mensagens Selecionadas");
						error.setDescription("Entre 1-100 Mensagens podem ser deletadas por vez.");
						event.getChannel().sendMessage(error.build()).queue();
					}
					else {
						// Messages too old
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Mensagens Selecionadas Superior a duas semanas");
						error.setDescription("Mensagens com mais de duas semanas nao podem ser deletadas.");
						event.getChannel().sendMessage(error.build()).queue();
					}
				}
			}
		}
		
		
		
	}

}
