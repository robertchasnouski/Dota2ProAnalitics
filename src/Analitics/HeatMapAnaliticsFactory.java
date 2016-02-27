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
		String teamRadiantMatches = stringReader.getTeamRadiantMatches(id);
		String teamDireMatches = stringReader.getTeamDireMatches(id);

		if (teamDireMatches.length() == 0 && teamRadiantMatches.length() == 0)
			return;

		String[] oneTeamDireMatch = teamDireMatches.split("\n");
		String[] oneTeamRadiantMatch = teamRadiantMatches.split("\n");

		ArrayList<KillEvent> radiantKillArrayList = new ArrayList<KillEvent>();
		ArrayList<KillEvent> direKillArrayList = new ArrayList<KillEvent>();

		for (int i = 0; i < oneTeamRadiantMatch.length; i++)
		{
			String killEvents = stringReader.getKillEvents(oneTeamRadiantMatch[i]);
			String[] eachKillEvent = killEvents.split("\\*\\*");
			String[] killEventInfo = eachKillEvent[1].split(";");
			KillEvent killik = new KillEvent();
			killik.x = Float.parseFloat(killEventInfo[0]);
			killik.y = Float.parseFloat(killEventInfo[1]);
			killik.second = Integer.parseInt(killEventInfo[2]);
			killik.dier = Integer.parseInt(killEventInfo[3]);
			killik.killers[0] = Integer.parseInt(killEventInfo[4]);
			killik.killers[1] = Integer.parseInt(killEventInfo[5]);
			killik.killers[2] = Integer.parseInt(killEventInfo[6]);
			killik.killers[3] = Integer.parseInt(killEventInfo[7]);
			killik.killers[4] = Integer.parseInt(killEventInfo[8]);
			radiantKillArrayList.add(killik);
		}

		for (int i = 0; i < oneTeamDireMatch.length; i++)
		{
			String killEvents = stringReader.getKillEvents(oneTeamDireMatch[i]);
			String[] eachKillEvent = killEvents.split("\\*\\*");
			String[] killEventInfo = eachKillEvent[1].split(";");
			KillEvent killik = new KillEvent();
			killik.x = Float.parseFloat(killEventInfo[0]);
			killik.y = Float.parseFloat(killEventInfo[1]);
			killik.second = Integer.parseInt(killEventInfo[2]);
			killik.dier = Integer.parseInt(killEventInfo[3]);
			killik.killers[0] = Integer.parseInt(killEventInfo[4]);
			killik.killers[1] = Integer.parseInt(killEventInfo[5]);
			killik.killers[2] = Integer.parseInt(killEventInfo[6]);
			killik.killers[3] = Integer.parseInt(killEventInfo[7]);
			killik.killers[4] = Integer.parseInt(killEventInfo[8]);
			direKillArrayList.add(killik);
		}
		ArrayList<KillEvent> radiantKills = new ArrayList<KillEvent>();
		ArrayList<KillEvent> direKills = new ArrayList<KillEvent>();
		ArrayList<KillEvent> radiantDeaths = new ArrayList<KillEvent>();
		ArrayList<KillEvent> direDeaths = new ArrayList<KillEvent>();

		for (int i = 0; i < radiantKillArrayList.size(); i++)
		{
			if (radiantKillArrayList.get(i).dier >= 6)
				radiantKills.add(radiantKillArrayList.get(i));
			else if (radiantKillArrayList.get(i).dier <= 5)
				radiantDeaths.add(radiantKillArrayList.get(i));
		}
		for (int i = 0; i < direKillArrayList.size(); i++)
		{
			if (direKillArrayList.get(i).dier <= 5)
				direKills.add(direKillArrayList.get(i));
			else if (direKillArrayList.get(i).dier >= 6)
				direDeaths.add(direKillArrayList.get(i));
		}

		BufferedImage image = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);

		BufferedImage radiantKillsImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		BufferedImage radiantDeathsImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		BufferedImage direKillsImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		BufferedImage direDeathsImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);

		Integer[][] radiantKillsColorArray = new Integer[800][800];
		Integer[][] radiantDeathsColorArray = new Integer[800][800];
		Integer[][] direKillsColorArray = new Integer[800][800];
		Integer[][] direDeathsColorArray = new Integer[800][800];

		for (int i = 0; i < 800; i++)
		{
			for (int j = 0; j < 800; j++)
			{
				radiantKillsColorArray[i][j] = 0;
				radiantDeathsColorArray[i][j] = 0;
				direKillsColorArray[i][j] = 0;
				direDeathsColorArray[i][j] = 0;
			}
		}
		for (int i = 0; i < radiantKills.size(); i++)
		{
			Integer xKillCenter = (int) radiantKills.get(i).x * 8;
			Integer yKillCenter = (int) radiantKills.get(i).y * 8;
			fillColorArray(xKillCenter, yKillCenter, radiantKillsColorArray);
		}
		for (int i = 0; i < radiantDeaths.size(); i++)
		{
			Integer xDeathCenter = (int) radiantDeaths.get(i).x * 8;
			Integer yDeathCenter = (int) radiantDeaths.get(i).y * 8;
			fillColorArray(xDeathCenter, yDeathCenter, radiantDeathsColorArray);
		}
		for (int i = 0; i < direKills.size(); i++)
		{
			Integer xKillsCenter = (int) direKills.get(i).x * 8;
			Integer yKillsCenter = (int) direKills.get(i).y * 8;
			fillColorArray(xKillsCenter, yKillsCenter, direKillsColorArray);
		}
		for (int i = 0; i < direDeaths.size(); i++)
		{
			Integer xDeathCenter = (int) direDeaths.get(i).x * 8;
			Integer yDeathCenter = (int) direDeaths.get(i).y * 8;
			fillColorArray(xDeathCenter, yDeathCenter, direDeathsColorArray);
		}
		for (int i = 1; i < 800; i++)
		{
			for (int j = 1; j < 800; j++)
			{
				if (radiantDeathsColorArray[i][j] != 0)
					radiantDeathsImage.setRGB(i, j, Color.red.getRGB());
				if (radiantKillsColorArray[i][j] != 0)
					radiantKillsImage.setRGB(i, j, Color.red.getRGB());
				if (direDeathsColorArray[i][j] != 0)
					direDeathsImage.setRGB(i, j, Color.red.getRGB());
				if (direKillsColorArray[i][j] != 0)
					direKillsImage.setRGB(i, j, Color.red.getRGB());
			}
		}

		BufferedImage originalMap = ImageIO.read(new File("files/OriginalMap.png"));

		int mergeWidth = 779;
		BufferedImage radiantDeathHeatMap = joinVertical(originalMap, radiantDeathsImage, mergeWidth);
		BufferedImage radiantKillsHeatMap = joinVertical(originalMap, radiantKillsImage, mergeWidth);
		BufferedImage direDeathHeatMap = joinVertical(originalMap, direDeathsImage, mergeWidth);
		BufferedImage direKillsHeatMap = joinVertical(originalMap, direKillsImage, mergeWidth);

		ImageIO.write(radiantDeathHeatMap, "png", new File("files/teams/" + id + "/RadiantDeathsHeatMap.png"));
		ImageIO.write(radiantKillsHeatMap, "png", new File("files/teams/" + id + "/RadiantKillsHeatMap.png"));
		ImageIO.write(direDeathHeatMap, "png", new File("files/teams/" + id + "/DireDeathsHeatMap.png"));
		ImageIO.write(direKillsHeatMap, "png", new File("files/teams/" + id + "/DireKillsHeatMap.png"));

	}

	public void fillColorArray(Integer xCenter, Integer yCenter, Integer[][] array)
	{
		/*if ((xCenter <= 25 && yCenter <= 25) || (xCenter >= 775 && yCenter >= 775) || (xCenter >= 775 && yCenter <= 25) || (xCenter <= 25 && yCenter >= 775))
		{
			System.out.println("3 case");
			for (int i = 0; i < 25 ; i++)
			{
				for (int j = 0; j < 25 ; j++)
				{

				}
			}
		} else if (xCenter > 25 && xCenter < 775 && yCenter > 25 && yCenter < 775)
		{
			System.out.println("1 case");
		} else
		{
			System.out.println("2 case");
		}*/

		for (int i = -25; i < 25; i++)
		{
			for (int j = -25; j < 25; j++)
			{
				if (xCenter + i >= 0 && xCenter + i < 800 && yCenter + i >= 0 && yCenter + i < 800)
					array[xCenter + i][yCenter + j] += 1;
			}
		}
	}
}