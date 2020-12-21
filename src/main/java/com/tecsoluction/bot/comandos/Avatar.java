package com.tecsoluction.bot.comandos;

import java.io.IOException;

import com.tecsoluction.bot.utils.Helper;
import com.tecsoluction.bot.utils.PerfilModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Avatar extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+");

		
		if (args[0].equalsIgnoreCase("!avatar")) {
			if (args.length < 2) {
				// Usage
				User autor = event.getAuthor();
				
				Member member = event.getMember();
				
				Message msg = event.getMessage();
				
				MessageChannel msgchanel = event.getChannel();
						
				Guild guild = event.getGuild();
				
				execute(autor,member,msg,msgchanel,guild);
			}	else {
				try {

				
					// Success
					
					User autor = event.getAuthor();
					
					String userstring = event.getMessage().getMember().getAsMention();
					
					User user = event.getJDA().getUserById(userstring);

					
					Member member = event.getGuild().getMember(user);
					
					Message msg = event.getMessage();
					
					MessageChannel msgchanel = event.getChannel();
							
					Guild guild = event.getGuild();
					
					execute(autor,member,msg,msgchanel,guild);

					

				}
				catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Muitas Membro Selecionadas");
						error.setDescription("Somente 1 Membro podem ser avatar por vez.");
						event.getChannel().sendMessage(error.build()).queue();
					}
					else {
						// Messages too old
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Membro Selecionados Superior a um");
						error.setDescription("Comando com mais de um Membro nao podem ser executado.");
						event.getChannel().sendMessage(error.build()).queue();
					}
				}
			}
		}
	}
	
	public void execute(User author, Member member, Message message, MessageChannel channel, Guild guild) {
		

		channel.sendMessage("execute avatar").queue(m -> {
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