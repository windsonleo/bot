package com.tecsoluction.bot.comandos;

import java.io.IOException;

import com.tecsoluction.bot.utils.Helper;
import com.tecsoluction.bot.utils.PerfilModel;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Perfil extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		
		
		if(event.getMessage().getContentRaw().equalsIgnoreCase("!perfil")) {
			
						
		//	event.getChannel().sendMessage("informações do membro").queue();
			
			User autor = event.getAuthor();
			
			Member member = event.getMember();
			
			Message msg = event.getMessage();
			
			MessageChannel msgchanel = event.getChannel();
					
			Guild guild = event.getGuild();
			
			execute(autor,member,msg,msgchanel,guild);
			
		
		}
	}
	
	public void execute(User author, Member member, Message message, MessageChannel channel, Guild guild) {
		

		channel.sendMessage("execute perfil").queue(m -> {
			try {

					channel.sendMessage(author.getAsMention()).addFile(Helper.getBytes(PerfilModel.makeProfile(member, guild), "png"), "perfil.png").queue(s -> m.delete().queue());
			} catch (IOException e) {
				m.editMessage("erro ao formatar 9mages").queue();
				Helper.logger(" | " + e.getStackTrace()[0]);
			} catch (InsufficientPermissionException e) {
				m.editMessage("insuficiente previlegios").queue();
			}
		});
	}
}