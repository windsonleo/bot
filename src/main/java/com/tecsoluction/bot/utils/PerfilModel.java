package com.tecsoluction.bot.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.tuple.Triple;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class PerfilModel {
	
	public static Font FONT;
	public static final int WIDTH = 944;
	public static final int HEIGTH = 600;

	static {
		try {
			FONT = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(PerfilModel.class.getClassLoader().getResourceAsStream("font/AovelSansRounded-rdDL.ttf")));
		} catch ( IOException | FontFormatException e) {
			Helper.logger(PerfilModel.class.getSimpleName() + " | " + ((Throwable) e).getStackTrace()[0]);
		}
	}

	public static BufferedImage makeProfile(net.dv8tion.jda.api.entities.Member m, Guild g) throws IOException {
		BufferedImage avatar;
//		Member mb = MemberDAO.getMemberById(m.getUser().getId() + g.getId());
//		Account acc = AccountDAO.getAccount(m.getId());

		try {
			avatar = Helper.scaleImage(ImageIO.read(Helper.getImage(m.getUser().getEffectiveAvatarUrl())), 200, 200);
	
			//avatar = Helper.scaleImage(ImageIO.read(Helper.getImage("https://j.gifs.com/32BNX9.gif")), 200, 200);
			//https://steamuserimages-a.akamaihd.net/ugc/832513454106893119/AAFB71967750243C39EA57E456B7CF7FD16218C7/ 
		} catch (NullPointerException | IOException e) {
			avatar = Helper.scaleImage(ImageIO.read(Helper.getImage("https://institutogoldenprana.com.br/wp-content/uploads/2015/08/no-avatar-25359d55aa3c93ab3466622fd2ce712d1.jpg")), 200, 200);
		}

		BufferedImage bi = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setBackground(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int xOffset = 0;
		int yOffset = 0;

//		Color main = null;
//		if (!mb.getProfileColor().isBlank()) {
//			try {
//				main = Color.decode(mb.getProfileColor());
//			} catch (NumberFormatException ignore) {
//			}
//		}




		g2d.setFont(FONT.deriveFont(Font.PLAIN, 50));
		g2d.setColor(Color.WHITE);
	//	printCenteredString("LEVEL", 196, 52, 440, g2d);
		String name = m.getEffectiveName();
		String nick = m.getNickname();
		String x = m.getTimeJoined().toString();
		
		if (g2d.getFontMetrics().stringWidth(m.getEffectiveName()) >= 678)
			name = m.getEffectiveName().substring(0, 21).concat("...");
		drawOutlinedText("Nome:" + name, 270, 302, g2d);

		drawOutlinedText("Apelido:" + nick, 270, 352, g2d);
		
		drawOutlinedText("Entrou em :" + x, 270, 402, g2d);
		




		g2d.setClip(new Ellipse2D.Float(50, 200, avatar.getWidth(), avatar.getHeight()));
		g2d.fillOval(50, 200, avatar.getWidth(), avatar.getHeight());
		g2d.drawImage(avatar, null, 50, 200);

		g2d.setClip(null);

		g2d.dispose();

		return Helper.scaleImage(clipRoundEdges(bi), 400, 254);
	}

	public static BufferedImage clipRoundEdges(BufferedImage image) {
		BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();

		g2d.setClip(new RoundRectangle2D.Float(0, 0, bi.getWidth(), bi.getHeight(), bi.getWidth() * 0.1f, bi.getWidth() * 0.1f));
		g2d.drawImage(image, null, 0, 0);
		g2d.dispose();

		return bi;
	}



	public static void printCenteredString(String s, int width, int XPos, int YPos, Graphics2D g2d) {
		int stringLen = g2d.getFontMetrics().stringWidth(s);
		int start = width / 2 - stringLen / 2;
		drawOutlinedText(s, start + XPos, YPos, g2d);
	}

	public static void drawOutlinedText(String s, int x, int y, Graphics2D g2d) {
		AffineTransform transform = g2d.getTransform();
		transform.translate(x, y);
		g2d.transform(transform);
		makeOutline(s, g2d);
		transform.translate(-x, -y);
		g2d.setTransform(transform);
	}

	private static void makeOutline(String s, Graphics2D g2d) {
		Color c = g2d.getColor();
		s = s.isEmpty() ? "Não definido" : s;
		g2d.setColor(g2d.getBackground());
		FontRenderContext frc = g2d.getFontRenderContext();
		TextLayout tl = new TextLayout(s, g2d.getFont(), frc);
		Shape shape = tl.getOutline(null);
		g2d.setStroke(new BasicStroke(4));
		g2d.draw(shape);
		g2d.setColor(c);
		g2d.fill(shape);
	}

	public static void drawStringMultiLineNO(Graphics2D g, String text, int lineWidth, int x, int y) {
		FontMetrics m = g.getFontMetrics();
		if (m.stringWidth(text) < lineWidth) {
			g.drawString(text, x, y);
		} else {
			String[] words = text.split(" ");
			StringBuilder currentLine = new StringBuilder(words[0]);
			for (int i = 1; i < words.length; i++) {
				if (m.stringWidth(currentLine + words[i]) < lineWidth) {
					currentLine.append(" ").append(words[i]);
				} else {
					String s = currentLine.toString();
					g.drawString(s, x, y);
					y += m.getHeight();
					currentLine = new StringBuilder(words[i]);
				}
			}
			if (currentLine.toString().trim().length() > 0) {
				g.drawString(currentLine.toString(), x, y);
			}
		}
	}

	public static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
		FontMetrics m = g.getFontMetrics();
		if (m.stringWidth(text) < lineWidth) {
			drawOutlinedText(text, x, y, g);
		} else {
			String[] words = text.split(" ");
			StringBuilder currentLine = new StringBuilder(words[0]);
			for (int i = 1; i < words.length; i++) {
				if (m.stringWidth(currentLine + words[i]) < lineWidth) {
					currentLine.append(" ").append(words[i]);
				} else {
					String s = currentLine.toString();
					drawOutlinedText(s, x, y, g);
					y += m.getHeight();
					currentLine = new StringBuilder(words[i]);
				}
			}
			if (currentLine.toString().trim().length() > 0) {
				drawOutlinedText(currentLine.toString(), x, y, g);
			}
		}
	}

	public static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) {
		g2d.translate((float) x, (float) y);
		g2d.rotate(Math.toRadians(angle));
		makeOutline(text, g2d);
		g2d.rotate(-Math.toRadians(angle));
		g2d.translate(-(float) x, -(float) y);
	}

	private static void drawPrestigeDetails(Graphics2D g2d, BufferedImage avatar, Color main, int lvl) {
		int polyOffset = 30;
		if (lvl >= 125) {
			g2d.setColor(Color.black);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getWidth() + 24) / 2,
					38 + (avatar.getWidth() + 24) / 2 + 50,
					38 + (avatar.getWidth() + 24) + polyOffset + 4,
					38 + (avatar.getWidth() + 24) / 2 + 50,
					38 + (avatar.getWidth() + 24) / 2,
					38 + (avatar.getWidth() + 24) / 2 - 50,
					38 - polyOffset - 4,
					38 + (avatar.getWidth() + 24) / 2 - 50,
			}, new int[]{
					188 - polyOffset - 4,
					188 + (avatar.getHeight() + 24) / 2 - 50,
					188 + (avatar.getHeight() + 24) / 2,
					188 + (avatar.getHeight() + 24) / 2 + 50,
					188 + (avatar.getHeight() + 24) + polyOffset + 4,
					188 + (avatar.getHeight() + 24) / 2 + 50,
					188 + (avatar.getHeight() + 24) / 2,
					188 + (avatar.getHeight() + 24) / 2 - 50,
			}, 8);
			g2d.setColor(main);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getWidth() + 24) / 2,
					38 + (avatar.getWidth() + 24) / 2 + 46,
					38 + (avatar.getWidth() + 24) + polyOffset - 8,
					38 + (avatar.getWidth() + 24) / 2 + 46,
					38 + (avatar.getWidth() + 24) / 2,
					38 + (avatar.getWidth() + 24) / 2 - 46,
					38 - polyOffset + 8,
					38 + (avatar.getWidth() + 24) / 2 - 46,
			}, new int[]{
					188 - polyOffset + 8,
					188 + (avatar.getHeight() + 24) / 2 - 46,
					188 + (avatar.getHeight() + 24) / 2,
					188 + (avatar.getHeight() + 24) / 2 + 46,
					188 + (avatar.getHeight() + 24) + polyOffset - 8,
					188 + (avatar.getHeight() + 24) / 2 + 46,
					188 + (avatar.getHeight() + 24) / 2,
					188 + (avatar.getHeight() + 24) / 2 - 46,
			}, 8);
		}

		if (lvl >= 245) {
			g2d.setColor(Color.black);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getHeight() + 24) / 2 - polyOffset * 2,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset * 2
			}, new int[]{
					188 - 4,
					188 - polyOffset * 2 - 4,
					0,
					0
			}, 4);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getHeight() + 24) / 2 + polyOffset * 2,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset * 2
			}, new int[]{
					188 - 4,
					188 - polyOffset * 2 - 4,
					0,
					0
			}, 4);
			g2d.setColor(main);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getHeight() + 24) / 2 - polyOffset * 2 + 4,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset - 4,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset - 4,
					38 + (avatar.getHeight() + 24) / 2 - polyOffset * 2 + 4
			}, new int[]{
					180 - 16,
					180 - polyOffset * 2,
					0,
					0
			}, 4);
			g2d.fillPolygon(new int[]{
					38 + (avatar.getHeight() + 24) / 2 + polyOffset * 2 - 4,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset + 4,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset + 4,
					38 + (avatar.getHeight() + 24) / 2 + polyOffset * 2 - 4
			}, new int[]{
					180 - 16,
					180 - polyOffset * 2,
					0,
					0
			}, 4);
		}
	}
	
	public static BufferedImage makeImageGif(net.dv8tion.jda.api.entities.Member m, Guild g) throws IOException {
		BufferedImage avatar;
//		Member mb = MemberDAO.getMemberById(m.getUser().getId() + g.getId());
//		Account acc = AccountDAO.getAccount(m.getId());

		try {
		//	avatar = Helper.scaleImage(ImageIO.read(Helper.getImage(m.getUser().getEffectiveAvatarUrl())), 200, 200);
	
			avatar = Helper.scaleImage(ImageIO.read(Helper.getImage("https://j.gifs.com/32BNX9.gif")), 200, 200);
			//https://steamuserimages-a.akamaihd.net/ugc/832513454106893119/AAFB71967750243C39EA57E456B7CF7FD16218C7/ 
		} catch (NullPointerException | IOException e) {
			avatar = Helper.scaleImage(ImageIO.read(Helper.getImage("https://institutogoldenprana.com.br/wp-content/uploads/2015/08/no-avatar-25359d55aa3c93ab3466622fd2ce712d1.jpg")), 200, 200);
		}

		BufferedImage bi = new BufferedImage(WIDTH, HEIGTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setBackground(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int xOffset = 0;
		int yOffset = 0;

//		Color main = null;
//		if (!mb.getProfileColor().isBlank()) {
//			try {
//				main = Color.decode(mb.getProfileColor());
//			} catch (NumberFormatException ignore) {
//			}
//		}




//		g2d.setFont(FONT.deriveFont(Font.PLAIN, 50));
//		g2d.setColor(Color.WHITE);
//	//	printCenteredString("LEVEL", 196, 52, 440, g2d);
//		String name = m.getEffectiveName();
//		String nick = m.getNickname();
//		String x = m.getTimeJoined().toString();
//		
//		if (g2d.getFontMetrics().stringWidth(m.getEffectiveName()) >= 678)
//			name = m.getEffectiveName().substring(0, 21).concat("...");
//		drawOutlinedText("Nome:" + name, 270, 322, g2d);
//
//		drawOutlinedText("Apelido:" + nick, 270, 372, g2d);
//		
//		drawOutlinedText("Entrou em :" + x, 270, 422, g2d);
		




//		g2d.setClip(new Ellipse2D.Float(50, 200, avatar.getWidth(), avatar.getHeight()));
//		g2d.fillOval(50, 200, avatar.getWidth(), avatar.getHeight());
//		g2d.drawImage(avatar, null, 50, 200);

		g2d.setClip(null);

		g2d.dispose();

		return Helper.scaleImage(clipRoundEdges(bi), 400, 254);
	}
	
	public static File applyAnimatedBackground(Member mb, BufferedImage overlay,String url) throws IOException {
		File out = File.createTempFile("profile_", ".gif");
		try (ImageOutputStream ios = new FileImageOutputStream(out)) {
			List<Triple<Integer, Integer, BufferedImage>> frames = Helper.readGIF(url);
			List<Triple<Integer, Integer, BufferedImage>> toDraw = new ArrayList<>();
			AtomicInteger xOffset = new AtomicInteger();
			AtomicInteger yOffset = new AtomicInteger();

			frames.forEach(p -> {
				BufferedImage canvas = new BufferedImage(overlay.getWidth(), overlay.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = canvas.createGraphics();

				if (p.getRight().getWidth() > canvas.getWidth())
					xOffset.set(-(p.getRight().getWidth() - canvas.getWidth()) / 2);
				if (p.getRight().getHeight() > canvas.getHeight())
					yOffset.set(-(p.getRight().getHeight() - canvas.getHeight()) / 2);

				g2d.drawImage(p.getRight(), xOffset.get(), yOffset.get(), null);
				g2d.drawImage(overlay, 0, 0, null);

				g2d.dispose();
				toDraw.add(Triple.of(p.getLeft(), p.getMiddle(), clipRoundEdges(canvas)));
			});

			GifSequenceWriter writer = new GifSequenceWriter(ios, BufferedImage.TYPE_INT_ARGB);
			toDraw.forEach(p -> {
				try {
					writer.writeToSequence(p.getRight(), p.getLeft(), p.getMiddle(), true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			writer.close();
		}

		return out;
	}

}
