package com.tecsoluction.bot.comandos;

import java.time.temporal.ChronoUnit;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		
		if(event.getMessage().getContentRaw().equalsIgnoreCase("!ping")) {
			
			
			
            long ping = event.getMessage().getTimeCreated().until(event.getAuthor().getTimeCreated(), ChronoUnit.MILLIS);
			
			event.getChannel().sendMessage("Ping: " + ping  + "ms | Websocket: " + event.getJDA().getGatewayPing() + "ms").queue();
			
		
		}
		
		
		
	}

}
