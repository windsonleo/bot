package com.tecsoluction.bot;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Instagram extends ListenerAdapter {
	
	final TextChannel tchannel = null;

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase("!insta")) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle(":x: Especife uma Membro pra Procurar");
				usage.setDescription("Uso: !insta # `"  + "Nome No Insta [# Nome no Insta]`");
				event.getChannel().sendMessage(usage.build()).queue();
			}
			else {
				try {
				
				
					// Success
					
					final String usn = args[1];

			        WebUtils.ins.getJSONObject("http://sushisenpai.herokuapp.com/" + usn).async((json) -> {
			            if (!json.get("success").asBoolean()) {
			            	event.getChannel().sendMessage(json.get("error").get("message").asText()).queue();
			                return;
			            }

			            final JsonNode user = json.get("user");
			            final String username = user.get("username").asText();
			            final String pfp = user.get("profile_pic_url").asText();
			            final String biography = user.get("biography").asText();
			            final boolean isPrivate = user.get("is_private").asBoolean();
			            final int following = user.get("following").get("count").asInt();
			            final int followers = user.get("followers").get("count").asInt();
			            final int uploads = user.get("uploads").get("count").asInt();

			            final EmbedBuilder embed = EmbedUtils.defaultEmbed()
			                    .setTitle("Instagram info of " + username, "https://www.instagram.com/" + username)
			                    .setThumbnail(pfp)
			                    .setDescription(String.format(
			                            "**Private account:** %s\n**Bio:** %s\n**Following:** %s\n**Followers:** %s\n**Uploads:** %s",
			                            toEmote(isPrivate),
			                            biography,
			                            following,
			                            followers,
			                            uploads
			                    ))
			                    .setImage(getLatestImage(json.get("images")));

			            event.getChannel().sendMessage(embed.build()).queue();
			        });
					
					

				}
				catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Muitas Nomes Selecionadoss");
						error.setDescription(" 1 contas podem ser procuradas por vez.");
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
	
	
    private String getLatestImage(JsonNode json) {
        if (!json.isArray()) {
            return null;
        }

        if (json.size() == 0) {
            return null;
        }

        return json.get(0).get("url").asText();
    }

    private String toEmote(boolean bool) {
        return bool ? "<:sliderRight:582718257598038017>" : "<:sliderLeft:582718257866473472>";
    }

}
