package ProjectDir.Analitics;

import ProjectDir.MatchInfo.*;

import java.util.ArrayList;

public class GameStageAnalitics
{
	public void getEGPoints(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList)
	{
		if (match.matchTime < 15)
		{
			team[0].EGPoints = 9999;
			team[1].EGPoints = 9999;
			return;
		}
		Integer killsRadiantCounter = 0;
		Integer totalRadiantGPM = 0;
		Integer totalRadiantLH = 0;
		Integer towerRadiantPoints = 0;
		Integer roshanRadiantPoints = 0;

		Integer killsDireCounter = 0;
		Integer totalDireGPM = 0;
		Integer totalDireLH = 0;
		Integer towerDirePoints = 0;
		Integer roshanDirePoints = 0;


		//<editor-fold desc="Radiant EG">
		//AVGAvg10
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second <= 900 && killEventArrayList.get(i).dier >= 6)
			{
				killsRadiantCounter++;
			}
		}
		//AVG22400
		for (int i = 0; i < 15; i++)
		{
			totalRadiantGPM += team[0].minuteGPM[i];
		}
		//AVG235
		for (int i = 0; i < 15; i++)
		{
			totalRadiantLH += team[0].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Radiant") && towerEventArrayList.get(i).second <= 900)
				towerRadiantPoints = +100;
		}
		if (match.firstRoshanRadiant && match.FRoshanTime <= 900)
			roshanRadiantPoints = 100;
		//</editor-fold>
		//<editor-fold desc="Dire EG">
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second <= 900 && killEventArrayList.get(i).dier <= 5)
			{
				killsDireCounter++;
			}
		}
		for (int i = 0; i < 15; i++)
		{
			totalDireGPM += team[1].minuteGPM[i];
		}
		for (int i = 0; i < 15; i++)
		{
			totalDireGPM += team[1].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Dire") && towerEventArrayList.get(i).second <= 900)
				towerDirePoints = +100;
		}
		if (!match.firstRoshanRadiant && match.FRoshanTime <= 900)
			roshanDirePoints = 100;
		//</editor-fold>

		//Calculate
		Double radiantPoints = ((0.33 * 1000 * killsRadiantCounter / 10) + (0.33 * 1000 * totalRadiantGPM / 22000) + (0.33 * 1000 * totalRadiantLH / 235))  + towerRadiantPoints + roshanRadiantPoints;
		Double direPoints = ((0.33 * 1000 * killsDireCounter / 10) + (0.33 * 1000 * totalDireGPM / 22000) + (0.33 * 1000 * totalDireLH / 235))  + towerDirePoints + roshanDirePoints;
		team[0].EGPoints = radiantPoints.intValue();
		team[1].EGPoints = direPoints.intValue();
	}

	public void getMGPoints(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList)
	{
		if (match.matchTime < 30)
		{
			team[0].MGPoints = 9999;
			team[1].MGPoints = 9999;
			return;
		}
		Integer killsRadiantCounter = 0;
		Integer totalRadiantGPM = 0;
		Integer totalRadiantLH = 0;
		Integer towerRadiantPoints = 0;
		Integer roshanRadiantPoints = 0;

		Integer killsDireCounter = 0;
		Integer totalDireGPM = 0;
		Integer totalDireLH = 0;
		Integer towerDirePoints = 0;
		Integer roshanDirePoints = 0;

		//<editor-fold desc="Radiant MG">
		//AVG10
		for (int i = 0; i < killEventArrayList.size(); i++)
		{

			if (killEventArrayList.get(i).second > 900 && killEventArrayList.get(i).second <= 1800 && killEventArrayList.get(i).dier >= 6)
			{
				killsRadiantCounter++;
			}
		}
		//AVG28500
		for (int i = 15; i < 30; i++)
		{
			totalRadiantGPM += team[0].minuteGPM[i];
		}
		//AVG338
		for (int i = 15; i < 30; i++)
		{
			totalRadiantLH += team[0].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Radiant") && towerEventArrayList.get(i).second <= 1800 && towerEventArrayList.get(i).second > 900)
				towerRadiantPoints = +100;
		}
		if (match.firstRoshanRadiant && match.FRoshanTime > 900 && match.FRoshanTime <= 1800)
			roshanRadiantPoints = 100;
		//</editor-fold>
		//<editor-fold desc="Dire MG">
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second > 900 && killEventArrayList.get(i).second <= 1800 && killEventArrayList.get(i).dier <= 5)
			{
				killsDireCounter++;
			}
		}
		for (int i = 15; i < 30; i++)
		{
			totalDireGPM += team[1].minuteGPM[i];
		}
		for (int i = 15; i < 30; i++)
		{
			totalDireLH += team[1].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Dire") && towerEventArrayList.get(i).second <= 1800 && towerEventArrayList.get(i).second >900)
				towerDirePoints = +100;
		}
		if (!match.firstRoshanRadiant && match.FRoshanTime > 900 && match.FRoshanTime <= 1800)
			roshanDirePoints = 100;
		//</editor-fold>

		Double radiantPoints = ((0.33 * 1000 * killsRadiantCounter / 10) + (0.33 * 1000 * totalRadiantGPM / 28500) + (0.33 * 1000 * totalRadiantLH / 330)) + towerRadiantPoints + roshanRadiantPoints;
		Double direPoints = ((0.33 * 1000 * killsDireCounter / 10) + (0.33 * 1000 * totalDireGPM / 28500) + (0.33 * 1000 * totalDireLH / 330))  + towerDirePoints + roshanDirePoints;

		team[0].MGPoints = radiantPoints.intValue();
		team[1].MGPoints = direPoints.intValue();
	}

	public void getLGPoints(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList)
	{
		if (match.matchTime < 45)
		{
			team[0].LGPoints = 9999;
			team[1].LGPoints = 9999;
			return;
		}
		Integer killsRadiantCounter = 0;
		Integer totalRadiantGPM = 0;
		Integer totalRadiantLH = 0;
		Integer towerRadiantPoints = 0;
		Integer roshanRadiantPoints = 0;

		Integer killsDireCounter = 0;
		Integer totalDireGPM = 0;
		Integer totalDireLH = 0;
		Integer towerDirePoints = 0;
		Integer roshanDirePoints = 0;

		//<editor-fold desc="Radiant LG">
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second > 1800 && killEventArrayList.get(i).second <= 2700 && killEventArrayList.get(i).dier >= 6)
			{
				killsRadiantCounter++;
			}
		}
		for (int i = 30; i < 45; i++)
		{
			totalRadiantGPM += team[0].minuteGPM[i];
		}
		for (int i = 30; i < 45; i++)
		{
			totalRadiantLH += team[0].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Radiant") && towerEventArrayList.get(i).second <= 2700 && towerEventArrayList.get(i).second > 1800)
				towerRadiantPoints = +100;
		}
		if (match.firstRoshanRadiant && match.FRoshanTime > 1800 && match.FRoshanTime <= 2700)
			roshanRadiantPoints = 100;
		//</editor-fold>
		//<editor-fold desc="Dire LG">
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second > 1800 && killEventArrayList.get(i).second <= 2700 && killEventArrayList.get(i).dier <= 5)
			{
				killsDireCounter++;
			}
		}
		for (int i = 30; i < 45; i++)
		{
			totalDireGPM += team[1].minuteGPM[i];
		}
		for (int i = 30; i < 45; i++)
		{
			totalDireLH += team[1].perMinuteLastHits[i];
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).whoDestroy.equals("Dire") && towerEventArrayList.get(i).second <= 2700 && towerEventArrayList.get(i).second > 1800)
				towerDirePoints = +100;
		}
		if (!match.firstRoshanRadiant && match.FRoshanTime > 1800 && match.FRoshanTime <= 2700)
			roshanDirePoints = 100;
		//</editor-fold>

		Double radiantPoints = (0.33 * 1000 * killsRadiantCounter / 14) + (0.33 * 1000 * totalRadiantGPM / 33000) + (0.33 * 1000 * totalRadiantLH / 355)+ towerRadiantPoints + roshanRadiantPoints;
		Double direPoints = (0.33 * 1000 * killsDireCounter / 14) + (0.33 * 1000 * totalDireGPM / 33000) + (0.33 * 1000 * totalDireLH / 355)+ towerDirePoints + roshanDirePoints;
		team[0].LGPoints = radiantPoints.intValue();
		team[1].LGPoints = direPoints.intValue();
	}
}