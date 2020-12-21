package com.tecsoluction.bot.comandos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.tecsoluction.bot.utils.Helper;
import com.tecsoluction.bot.utils.PerfilModel;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Matar extends ListenerAdapter {
	
	

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase("!matar")) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle(":x: Especife um Membro pra Assassinar");
				usage.setDescription("Uso: !matar @ `"  + "Membro [@ digite um Mmebro]`");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else {
				try {

				
					// Success
					
					User autor = event.getAuthor();
					
					Member member = event.getMember();
					
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
						error.setDescription("Somente 1 Membro podem ser Morto por vez.");
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
		
		
		String[] urls =  { "https://j.gifs.com/32BNX9.gif", "https://i.makeagif.com/media/4-10-2017/-ILD9b.gif", "https://thumbs.gfycat.com/CleverIllustriousBarnowl-size_restricted.gif", "https://blerg.com.au/wp-content/uploads/2015/01/mkx.gif","https://static.wikia.nocookie.net/powerlisting/images/a/ae/Liu-kang-mortal-kombat-animated-gif-8f9db.gif", "https://25.media.tumblr.com/0ff2bc4e0114ded1c03b98df7f4bb279/tumblr_mj6l4nBwEb1s2b58zo1_500.gif", "https://steamuserimages-a.akamaihd.net/ugc/867365298775219507/F85CA5CC56498062FBD4662A9F899FA84C6A5CB3/", "https://64.media.tumblr.com/60e80f6e2c4c8b6c9343006064347eee/tumblr_npg31lUeiO1qb5qxmo1_500.gifv" };
	      Random rand = new Random();
	      int res = rand.nextInt(urls.length);
	      
	      System.out.println("Displaying a random string = " + urls[res]);

		channel.sendMessage("execute matar").queue(m -> {
			try {
				
				File file = PerfilModel.applyAnimatedBackground(member, PerfilModel.makeImageGif(member, guild),urls[res]);

					channel.sendMessage(author.getAsMention()).addFile(file, "perfil.gif").queue(s -> m.delete().queue());
			} catch (IOException e) {
				m.editMessage("erro ao formatar 9mages").queue();
				Helper.logger(" | " + e.getStackTrace()[0]);
			} catch (InsufficientPermissionException e) {
				m.editMessage("insuficiente previlegios").queue();
			}
		});
	}

}
