package com.tecsoluction.bot.utils;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.collections4.map.HashedMap;

public class GifSequenceWriter {
	
	private final ImageWriter writer;
	private final ImageWriteParam params;
	private final IIOMetadata metadata;
//	private static final Map<Integer, String> disposeMethod = Map.of(
//			0, "none",
//			1, "doNotDispose",
//			2, "restoreToBackgroundColor",
//			3, "restoreToPrevious"
//	);
	
	private static final Map<Integer, String> disposeMethod;
    static {
        Map<Integer, String> aMap = new HashedMap<Integer, String>();
       
        aMap.put(0, "none");
        aMap.put(1, "doNotDispose");
        aMap.put(2, "restoreToBackgroundColor");
        aMap.put(3, "restoreToPrevious");
        
        disposeMethod = Collections.unmodifiableMap(aMap);
    }

	public GifSequenceWriter(ImageOutputStream out, int imageType) throws IOException {
		writer = ImageIO.getImageWritersBySuffix("gif").next();
		params = writer.getDefaultWriteParam();

		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
		metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

		writer.setOutput(out);
		writer.prepareWriteSequence(null);
	}

	private void configureRootMetadata(int dispose, int delay, boolean loop) throws IIOInvalidTreeException {


		String metaFormatName = metadata.getNativeMetadataFormatName();
		IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

		IIOMetadataNode gce = getNode(root, "GraphicControlExtension");
		gce.setAttribute("disposalMethod", disposeMethod.get(dispose));
		gce.setAttribute("userInputFlag", "FALSE");
		gce.setAttribute("transparentColorFlag", "FALSE");
		gce.setAttribute("userDelay", "FALSE");
		gce.setAttribute("delayTime", String.valueOf(delay));
		gce.setAttribute("transparentColorIndex", "0");

		IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		int loopContinuously = loop ? 0 : 1;
		child.setUserObject(new byte[]{0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
		appExtensionsNode.appendChild(child);
		metadata.setFromTree(metaFormatName, root);
	}

	private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
				return (IIOMetadataNode) rootNode.item(i);
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return (node);
	}

	public void writeToSequence(RenderedImage img, int dispose, int delay, boolean loop) throws IOException {
		configureRootMetadata(dispose, delay, loop);
		writer.writeToSequence(new IIOImage(img, null, this.metadata), params);
	}

	public void close() throws IOException {
		writer.endWriteSequence();
	}

}
