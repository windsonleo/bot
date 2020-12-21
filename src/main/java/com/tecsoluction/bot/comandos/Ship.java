package com.tecsoluction.bot.comandos;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.tecsoluction.bot.utils.ColorlessEmbedBuilder;
import com.tecsoluction.bot.utils.Helper;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ship extends ListenerAdapter {
	
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase("!ship")) {
			if (args.length < 2) {
				// Usage
				EmbedBuilder usage = new EmbedBuilder();
				usage.setColor(0xff3923);
				usage.setTitle(":x: Especifique dois membros");
				usage.setDescription("Uso: !ship @membro @membro `"  + "mmebros [# membros]`");
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
					
					execute(autor, member, msg, msgchanel, guild);

				}
				catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
						// Too many messages
						EmbedBuilder error = new EmbedBuilder();
						error.setColor(0xff3923);
						error.setTitle("🔴 Muitas mmebros shipados");
						error.setDescription("2 membros podem ser shipados por vez.");
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
	
	
	
	
	
	public void execute(User author, Member member, Message message, MessageChannel channel, Guild guild) {
		if (message.getMentionedUsers().size() < 2  ) {
			channel.sendMessage(":x: precisa mencionar dois membros").queue();
			return;
		}

		try {
			StringBuilder sb = new StringBuilder();
			String[] meter = {"-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-"};
			String doneMeter;
			BufferedImage bi = new BufferedImage(257, 128, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = bi.createGraphics();
			float love = 100 * new Random(message.getMentionedUsers().get(0).getIdLong() + message.getMentionedUsers().get(1).getIdLong()).nextFloat();

			for (int i = 0; i < Math.round(love / 5); i++) {
				meter[i] = "▉";
			}

			doneMeter = Arrays.toString(meter).replace(",", "").replace(" ", "");

			g2d.drawImage(ImageIO.read(Helper.getImage(message.getMentionedUsers().get(0).getEffectiveAvatarUrl())), null, 0, 0);
			g2d.drawImage(ImageIO.read(Helper.getImage(message.getMentionedUsers().get(1).getEffectiveAvatarUrl())), null, 129, 0);

			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(clipRoundEdges(bi), "png", baos);

			String n1 = message.getMentionedUsers().get(0).getName();
			String n2 = message.getMentionedUsers().get(1).getName();

			sb.append(":heartpulse: ***Nível de love entre ").append(message.getMentionedUsers().get(0).getName()).append(" e ").append(message.getMentionedUsers().get(1).getName()).append(":***");
			sb.append("\n\nNome de casal: `").append(n1, 0, n1.length() / 2 + (n1.length() % 2)).append(n2.substring(n2.length() / 2 - (n1.length() % 2))).append("`");
			if (love <= 30)
				sb.append("\n\nBem, esse casal jamais daria certo, hora de passar pra frente!\n**").append(Helper.round(love, 1)).append("%** `").append(doneMeter).append("`");
			else if (love <= 50)
				sb.append("\n\nPode ate dar certo esse casal, mas vai precisar insistir!\n**").append(Helper.round(love, 1)).append("%** `").append(doneMeter).append("`");
			else if (love <= 70)
				sb.append("\n\nOpa, ou eles já se conhecem, ou o destino sorriu pra eles!\n**").append(Helper.round(love, 1)).append("%** `").append(doneMeter).append("`");
			else
				sb.append("\n\nImpossível casal mais perfeito que esse, tem que casar JÁ!!\n**").append(Helper.round(love, 1)).append("%** `").append(doneMeter).append("`");

			EmbedBuilder eb = new ColorlessEmbedBuilder();
			
			
//			InputStream file = new URL("https://uploads.spiritfanfiction.com/fanfics/historias/201708/uma-outra-historia-starco-10032850-170820172127.png\r\n" + 
//					"").openStream();
		//	https://uploads.spiritfanfiction.com/fanfics/historias/201708/uma-outra-historia-starco-10032850-170820172127.png

			
			eb.setImage("attachment://uma-outra-historia-starco-10032850-170820172127.png");

			MessageBuilder mb = new MessageBuilder();
			mb.append(sb.toString());
			mb.setEmbed(eb.build());

			channel.sendMessage(mb.build()).addFile(baos.toByteArray(), "uma-outra-historia-starco-10032850-170820172127.png").queue();
			
			System.out.println("passou ship");
			
		} catch (IOException e) {
			
			System.out.println(this.getClass().getSimpleName() + e.getStackTrace()[0]);
			channel.sendMessage(e.getMessage()).queue();
		
		}
	}
	
	
	private static BufferedImage clipRoundEdges(BufferedImage image) {
		BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setClip(new RoundRectangle2D.Float(0, 0, bi.getWidth(), bi.getHeight(), 20, 20));
		g2d.drawImage(image, null, 0, 0);
		g2d.dispose();

		return bi;
	}
	
}
