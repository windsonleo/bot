package com.tecsoluction.bot.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Convite extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		
		String url = "https://discord.com/oauth2/authorize?client_id=%s&scope=bot";
		
		if(event.getMessage().getContentRaw().equalsIgnoreCase("!convidar")) {
			
						
			event.getChannel().sendMessage(String.format(url,event.getJDA().getSelfUser().getId())).queue();
			
		
		}
	}

}
