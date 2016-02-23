package Analitics;


import MatchInfo.KillEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HeatMapAnaliticsFactory
{
	StringReader stringReader = new StringReader();

	public static BufferedImage joinVertical(BufferedImage i1, BufferedImage i2, int mergeWidth)
	{
		if (i1.getWidth() != i2.getWidth())
			throw new IllegalArgumentException("Images i1 and i2 are not the same width");

		BufferedImage imgClone = new BufferedImage(i2.getWidth(), mergeWidth, BufferedImage.TYPE_INT_ARGB);
		Graphics2D cloneG = imgClone.createGraphics();
		cloneG.drawImage(i2, 0, 0, null);
		cloneG.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 0.5f));
		cloneG.drawImage(i2, 0, 0, null);

		BufferedImage result = new BufferedImage(i1.getWidth(),
				i1.getHeight() + i2.getHeight() - mergeWidth, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = result.createGraphics();
		g.drawImage(i1, 0, 0, null);
		g.drawImage(i2.getSubimage(0, mergeWidth, i2.getWidth(),
				i2.getHeight() - mergeWidth), 0, i1.getHeight(), null);
		g.drawImage(imgClone, 0, i1.getHeight() - mergeWidth, null);

		return result;
	}

	public void buildHeatMap(String id) throws IOException
	{
		String teamMatches=stringReader.getTeamMatches(id);
		String []oneTeamMatch=teamMatches.split("\n");
		ArrayList<KillEvent> radiantKillArrayList=new ArrayList<KillEvent>();
		ArrayList<KillEvent> direKillArrayList=new ArrayList<KillEvent>();
		for (int i = 0; i <oneTeamMatch.length; i++)
		{

		}

		BufferedImage img1 = ImageIO.read(new File("files/heatmap.png"));
		BufferedImage img2 = ImageIO.read(new File("files/originalmap.png"));

		int mergeWidth = 779;
		BufferedImage merge = joinVertical(img2, img1, mergeWidth);

		boolean success = ImageIO.write(merge, "png", new File("files/joinedmap.png"));
		System.out.println("saved success? " + success);
	}


}
