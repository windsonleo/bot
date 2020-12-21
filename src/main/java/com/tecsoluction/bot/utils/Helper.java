package com.tecsoluction.bot.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.commons.math3.util.Precision;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Helper {
	
	public static double[] normalize(double[] vector) {
		double length = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2));
		return new double[]{vector[0] / length, vector[1] / length};
	}

	public static float[] normalize(float[] vector) {
		double length = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2));
		return new float[]{(float) (vector[0] / length), (float) (vector[1] / length)};
	}

	public static int[] normalize(int[] vector, RoundingMode roundingMode) {
		double length = Math.sqrt(Math.pow(vector[0], 2) + Math.pow(vector[1], 2));
//		return switch (roundingMode) {
//			
//		case UP, CEILING, HALF_UP -> new int[]{mirroredCeil(vector[0] / length), mirroredCeil(vector[1] / length)};
//			
//		case DOWN, FLOOR, HALF_DOWN -> new int[]{(int) mirroredFloor(vector[0] / length), (int) mirroredFloor(vector[1] / length)};
//			
//		case HALF_EVEN -> new int[]{(int) Math.round(vector[0] / length), (int) Math.round(vector[1] / length)};
//			
//		default -> throw new IllegalArgumentException();
//		};
	
	return null;
	}

	public static int mirroredCeil(double value) {
		return (int) (value < 0 ? Math.floor(value) : Math.ceil(value));
	}

	public static int mirroredFloor(double value) {
		return (int) (value > 0 ? Math.floor(value) : Math.ceil(value));
	}
	
	
	public static InputStream getImage(String url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.addRequestProperty("User-Agent", "Mozilla/5.0");
		return con.getInputStream();
	}
	
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		return Precision.round(value, places);
	}
	
	
	public static String logger(String source) {
		return source;
	}
	
	public static BufferedImage scaleImage(BufferedImage image, int w, int h) {
		double thumbRatio = (double) w / (double) h;
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		double aspectRatio = (double) imageWidth / (double) imageHeight;

		if (thumbRatio > aspectRatio) {
			h = (int) (w / aspectRatio);
		} else {
			w = (int) (h * aspectRatio);
		}

		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = newImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, w, h, null);

		return newImage;
	}
	
	public static byte[] getBytes(BufferedImage image) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(image, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			logger(" | " + e.getStackTrace()[0]);
			return new byte[0];
		}
	}

	public static byte[] getBytes(BufferedImage image, String encode) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(image, encode, baos);
			return baos.toByteArray();
		} catch (IOException e) {
			logger(" | " + e.getStackTrace()[0]);
			return new byte[0];
		}
	}

	public static byte[] getBytes(BufferedImage image, String encode, float compression) {
		byte[] bytes;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName(encode).next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();
			if (param.canWriteCompressed()) {
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(compression);
			}

			writer.write(null, new IIOImage(image, null, null), param);
			bytes = baos.toByteArray();
		} catch (IOException e) {
			logger(" | " + e.getStackTrace()[0]);
			bytes = new byte[0];
		}

		return bytes;
	}
	
	public static List<Triple<Integer, Integer, BufferedImage>> readGIF(String url) throws IOException {
		List<Triple<Integer, Integer, BufferedImage>> frms = new ArrayList<>();
		ImageReader ir = ImageIO.getImageReadersByFormatName("gif").next();
		ImageInputStream iis = ImageIO.createImageInputStream(getImage(url));
		ir.setInput(iis);

		int w = 0;
		int h = 0;
		int frames = ir.getNumImages(true);
		for (int i = 0; i < frames; i++) {
			BufferedImage image = ir.read(i);
			if (i == 0) {
				w = image.getWidth();
				h = image.getHeight();
			}
			JSONObject metadata = new JSONObject(new Gson().toJson(ir.getImageMetadata(i)));

			BufferedImage master = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			master.getGraphics().drawImage(image, metadata.getInt("imageLeftPosition"), metadata.getInt("imageTopPosition"), null);

			frms.add(Triple.of(metadata.getInt("disposalMethod"), metadata.getInt("delayTime"), master));
		}

		return frms;
	}
}


