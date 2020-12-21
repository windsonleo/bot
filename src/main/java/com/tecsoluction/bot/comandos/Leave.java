package com.tecsoluction.bot.comandos;

import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Leave extends ListenerAdapter {
	
	String[] messages = {
			"[member] Nos Deixou, a Festa Acabou!.",
			"A casa Caiu,[member] Foi Embora! ",
			"R.I.P [member] , Até Outro Dia! "
	};
	
	
	public String user;
	
	public String userant;
	
//	@SuppressWarnings("deprecation")
//	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
//		Random rand = new Random();
//		int number = rand.nextInt(messages.length);
//		
//		EmbedBuilder join = new EmbedBuilder();
//		join.setColor(0xf48342);
//		join.setDescription(messages[number].replace("[member]", event.getMember().getAsMention()));
//	
//		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();
//		
//	}
	
	

	
	@Override
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		Random rand = new Random();
		int number = rand.nextInt(messages.length);
		
	//	boolean valido = verificarUsuario(event.getMember().getAsMention());
		
//		if(event.getMember().getAsMention() != null) {
//			
//			user = event.getMember().getAsMention();
//			userant = user;
//			
//		}else {
//			
//			user = userant;
//			
//		}
		
		user = event.getUser().getAsMention();
	
		EmbedBuilder join = new EmbedBuilder();
		join.setColor(0xf48342);
		join.setDescription(messages[number].replace("[member]", user));
	
		event.getGuild().getDefaultChannel().sendMessage(join.build()).queue();	
	}

private boolean verificarUsuario(String asMention) {

	boolean valid ;

	if(asMention!= null) {
		
		valid =true;
		
	}else {
		
		valid = false;
		
	}
	
	
	return valid;
}


}