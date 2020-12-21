package com.tecsoluction.bot.comandos;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDAInfo;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Server extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		
		
		if(event.getMessage().getContentRaw().equalsIgnoreCase("!server")) {
			
						
		//	event.getChannel().sendMessage("informações do bot").queue();
			
			ContruirJanela(event);
			
		
		}
	}
	
	public void ContruirJanela(MessageReceivedEvent event) {
		
		 EmbedBuilder builder = new EmbedBuilder();
	        builder.setColor(event.isFromType(ChannelType.TEXT) ? event.getGuild().getSelfMember().getColor() : Color.ORANGE);
	        builder.setAuthor("sOBRE " + event.getAuthor().getName() + "!", null, event.getMember().getUser().getAvatarUrl());

	      
	        String author = event.getJDA().getUserById(event.getMember().getGuild().getOwnerId())==null ? "<@" + event.getMember().getGuild().getOwnerId()+">" 
	                : event.getJDA().getUserById(event.getMember().getGuild().getOwnerId()).getName();
	        StringBuilder descr = new StringBuilder().append("Hello! I am **").append(event.getMember().getNickname()).append("**, ")
	                .append(JDAInfo.VERSION).append(")\nType `").append(event.getChannel().getName()).append(event.getGuild().getOwnerId());
	        builder.setDescription(descr);
	        if (event.getJDA().getShardInfo() == null)
	        {
	            builder.addField("Stats", event.getJDA().getGuilds().size() + " servers\n1 shard", true);
	            builder.addField("Users", event.getJDA().getUsers().size() + " unique\n" + event.getJDA().getGuilds().stream().mapToInt(g -> g.getMembers().size()).sum() + " total", true);
	            builder.addField("Channels", event.getJDA().getTextChannels().size() + " Text\n" + event.getJDA().getVoiceChannels().size() + " Voice", true);
	        }
	        else
	        {
	            builder.addField("Stats", (event.getGuild().getMembers()) + " Servers\nShard " + (event.getJDA().getShardInfo().getShardId() + 1) 
	                    + "/" + event.getJDA().getShardInfo().getShardTotal(), true);
	            builder.addField("This shard", event.getJDA().getUsers().size() + " Users\n" + event.getJDA().getGuilds().size() + " Servers", true);
	            builder.addField("", event.getJDA().getTextChannels().size() + " Text Channels\n" + event.getJDA().getVoiceChannels().size() + " Voice Channels", true);
	        }
	        builder.setFooter("Last restart", null);
	        builder.setTimestamp(event.getMember().getTimeCreated());
	        
	        event.getChannel().sendMessage(builder.build()).queue();
	       // event.reply(builder.build());
		
		
	}
}
