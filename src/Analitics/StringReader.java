package Analitics;

import MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class StringReader
{
	FileControlFactory fileControlFactory = new FileControlFactory();

	//Give me String and we will return smthg
	public String getMatchInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[0];
	}

	public String getTeamsInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[1];
	}

	public String getPlayersInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[2];
	}

	public String getKillEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[3];
	}

	public String getWardEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[4];
	}

	public String getGlyphEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[5];
	}

	public String getBuyBackEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[6];
	}

	public String getTowerEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[7];
	}

	public String getTeamMatches(String id) throws IOException
	{
		String allMatches = fileControlFactory.readFile("files/Matches.txt");
		String[] oneMatch = allMatches.split("\n");
		String teamMatches = "";
		Integer counter = 0;
		for (int i = 0; i < oneMatch.length; i++)
		{
			String teamInfo = getTeamsInfo(oneMatch[i]);
			String[] oneTeamInfo = teamInfo.split("\\|\\|");
			String[] firstTeamSplitter = oneTeamInfo[0].split(";");
			String[] secondTeamSplitter = oneTeamInfo[1].split(";");
			if (firstTeamSplitter[0].equals(id) || secondTeamSplitter[0].equals(id))
			{
				if (counter != 0)
					teamMatches += "\n";
				teamMatches += oneMatch[i];
				counter++;
			}
		}
		System.out.println(teamMatches);
		return teamMatches;
	}

	public void fillArraysFromFile(String matchString, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList)
	{
		String matchInfo = getMatchInfo(matchString);
		String teamInfo = getTeamsInfo(matchString);
		String playerInfo = getPlayersInfo(matchString);
		String killEventsInfo = getKillEvents(matchString);
		String buyBackEventsInfo = getBuyBackEvents(matchString);
		String wardEventsInfo = getWardEvents(matchString);
		String towerEventsInfo = getTowerEvents(matchString);
		String glyphEventsInfo = getGlyphEvents(matchString);

		//<editor-fold desc="MATCH">
		//Match
		String[] matchInfoArray = matchInfo.split(";");
		match.id = matchInfoArray[0];
		match.date = matchInfoArray[1];
		match.matchTime = Integer.parseInt(matchInfoArray[2]);
		match.team1Id = matchInfoArray[3];
		match.team2Id = matchInfoArray[4];
		match.leagueId = Integer.parseInt(matchInfoArray[5]);
		match.leagueName = matchInfoArray[6];
		match.winRadiant = Boolean.parseBoolean(matchInfoArray[7]);
		match.firstBloodRadiant = Boolean.parseBoolean(matchInfoArray[8]);
		match.first10KillsRadiant = Boolean.parseBoolean(matchInfoArray[9]);
		match.firstRoshanRadiant = Boolean.parseBoolean(matchInfoArray[10]);
		match.FBTime = Integer.parseInt(matchInfoArray[11]);
		match.F10KTime = Integer.parseInt(matchInfoArray[12]);
		match.FRoshanTime = Integer.parseInt(matchInfoArray[13]);
		//</editor-fold>

		//<editor-fold desc="TEAMS">
		//Teams
		String[] teamInfoArray = teamInfo.split("\\|\\|");
		String[] teamInfo1GPMXPMLH = teamInfoArray[0].split("\\*\\*");
		String[] teamInfo2GPMXPMLH = teamInfoArray[1].split("\\*\\*");
		String[] teamInfo1Array = teamInfo1GPMXPMLH[0].split(";");
		String[] teamInfo2Array = teamInfo2GPMXPMLH[0].split(";");

		team[0].id = teamInfo1Array[0];
		team[0].name = teamInfo1Array[1];
		team[0].rating = Integer.parseInt(teamInfo1Array[2]);
		team[0].kills = Integer.parseInt(teamInfo1Array[3]);
		team[0].deaths = Integer.parseInt(teamInfo1Array[4]);
		team[0].assists = Integer.parseInt(teamInfo1Array[5]);
		team[0].partisipation = Integer.parseInt(teamInfo1Array[6]);
		team[0].heroHeal = Integer.parseInt(teamInfo1Array[7]);
		team[0].heroDamage = Integer.parseInt(teamInfo1Array[8]);
		team[0].towerDamage = Integer.parseInt(teamInfo1Array[9]);
		team[0].totalGPM = Integer.parseInt(teamInfo1Array[10]);
		team[0].totalXPM = Integer.parseInt(teamInfo1Array[11]);
		team[0].totalLH = Integer.parseInt(teamInfo1Array[12]);
		team[0].totalDenies = Integer.parseInt(teamInfo1Array[13]);
		team[0].towersDestroyed = Integer.parseInt(teamInfo1Array[14]);
		team[0].towersDenied = Integer.parseInt(teamInfo1Array[15]);
		team[0].roshanKills = Integer.parseInt(teamInfo1Array[16]);
		team[0].goldForKills = Integer.parseInt(teamInfo1Array[17]);
		team[0].goldFed = Integer.parseInt(teamInfo1Array[18]);
		team[0].goldLost = Integer.parseInt(teamInfo1Array[19]);
		team[0].totalGold = Integer.parseInt(teamInfo1Array[20]);
		team[0].observerWardsPlaced = Integer.parseInt(teamInfo1Array[21]);
		team[0].observerWardsDestroyed = Integer.parseInt(teamInfo1Array[22]);
		team[0].sentryWardsPlaced = Integer.parseInt(teamInfo1Array[23]);
		team[0].sentryWardsDestroyed = Integer.parseInt(teamInfo1Array[24]);
		team[0].dustHits = Integer.parseInt(teamInfo1Array[25]);
		team[0].dustAccuracy = Integer.parseInt(teamInfo1Array[26]);
		team[0].smokeHits = Integer.parseInt(teamInfo1Array[27]);
		team[0].smokeTotalHeroes = Integer.parseInt(teamInfo1Array[28]);
		team[0].gemsBought = Integer.parseInt(teamInfo1Array[29]);
		team[0].gemsDropped = Integer.parseInt(teamInfo1Array[30]);
		team[0].gemTimeCarried = Integer.parseInt(teamInfo1Array[31]);
		//GPM Array
		String[] GPMInfo1Array = teamInfo1GPMXPMLH[1].split(";");
		for (int i = 0; i < Integer.parseInt(GPMInfo1Array[0]); i++)
		{
			team[0].minuteGPM[i] = Integer.parseInt(GPMInfo1Array[i + 1]);
		}
		//XPM Array
		String[] XPMInfo1Array = teamInfo1GPMXPMLH[2].split(";");
		for (int i = 0; i < Integer.parseInt(XPMInfo1Array[0]); i++)
		{
			team[0].minuteXPM[i] = Integer.parseInt(XPMInfo1Array[i + 1]);
		}
		//LH Array
		String[] LHInfo1Array = teamInfo1GPMXPMLH[3].split(";");
		for (int i = 0; i < Integer.parseInt(LHInfo1Array[0]); i++)
		{
			team[0].perMinuteLastHits[i] = Integer.parseInt(LHInfo1Array[i + 1]);
		}

		team[1].id = teamInfo2Array[0];
		team[1].name = teamInfo2Array[1];
		team[1].rating = Integer.parseInt(teamInfo2Array[2]);
		team[1].kills = Integer.parseInt(teamInfo2Array[3]);
		team[1].deaths = Integer.parseInt(teamInfo2Array[4]);
		team[1].assists = Integer.parseInt(teamInfo2Array[5]);
		team[1].partisipation = Integer.parseInt(teamInfo2Array[6]);
		team[1].heroHeal = Integer.parseInt(teamInfo2Array[7]);
		team[1].heroDamage = Integer.parseInt(teamInfo2Array[8]);
		team[1].towerDamage = Integer.parseInt(teamInfo2Array[9]);
		team[1].totalGPM = Integer.parseInt(teamInfo2Array[10]);
		team[1].totalXPM = Integer.parseInt(teamInfo2Array[11]);
		team[1].totalLH = Integer.parseInt(teamInfo2Array[12]);
		team[1].totalDenies = Integer.parseInt(teamInfo2Array[13]);
		team[1].towersDestroyed = Integer.parseInt(teamInfo2Array[14]);
		team[1].towersDenied = Integer.parseInt(teamInfo2Array[15]);
		team[1].roshanKills = Integer.parseInt(teamInfo2Array[16]);
		team[1].goldForKills = Integer.parseInt(teamInfo2Array[17]);
		team[1].goldFed = Integer.parseInt(teamInfo2Array[18]);
		team[1].goldLost = Integer.parseInt(teamInfo2Array[19]);
		team[1].totalGold = Integer.parseInt(teamInfo2Array[20]);
		team[1].observerWardsPlaced = Integer.parseInt(teamInfo2Array[21]);
		team[1].observerWardsDestroyed = Integer.parseInt(teamInfo2Array[22]);
		team[1].sentryWardsPlaced = Integer.parseInt(teamInfo2Array[23]);
		team[1].sentryWardsDestroyed = Integer.parseInt(teamInfo2Array[24]);
		team[1].dustHits = Integer.parseInt(teamInfo2Array[25]);
		team[1].dustAccuracy = Integer.parseInt(teamInfo2Array[26]);
		team[1].smokeHits = Integer.parseInt(teamInfo2Array[27]);
		team[1].smokeTotalHeroes = Integer.parseInt(teamInfo2Array[28]);
		team[1].gemsBought = Integer.parseInt(teamInfo2Array[29]);
		team[1].gemsDropped = Integer.parseInt(teamInfo2Array[30]);
		team[1].gemTimeCarried = Integer.parseInt(teamInfo2Array[31]);
		//GPM Array
		String[] GPMInfo2Array = teamInfo2GPMXPMLH[1].split(";");
		for (int i = 0; i < Integer.parseInt(GPMInfo2Array[0]); i++)
		{
			team[1].minuteGPM[i] = Integer.parseInt(GPMInfo2Array[i + 1]);
		}
		//XPM Array
		String[] XPMInfo2Array = teamInfo2GPMXPMLH[1].split(";");
		for (int i = 0; i < Integer.parseInt(XPMInfo2Array[0]); i++)
		{
			team[1].minuteXPM[i] = Integer.parseInt(XPMInfo2Array[i + 1]);
		}
		//LH Array
		String[] LHInfo2Array = teamInfo2GPMXPMLH[1].split(";");
		for (int i = 0; i < Integer.parseInt(LHInfo2Array[0]); i++)
		{
			team[1].perMinuteLastHits[i] = Integer.parseInt(LHInfo2Array[i + 1]);
		}
		//</editor-fold>

		//<editor-fold desc="PLAYERS">
		//Players
		String[] onePlayerInfo = playerInfo.split("\\|\\|");
		for (int i = 0; i < 10; i++)
		{
			String[] playerInfoItemsGPMXPMLH = onePlayerInfo[i].split("\\*\\*");
			String[] mainInfoOnePlayer = playerInfoItemsGPMXPMLH[0].split(";");

			player[i].playerId = mainInfoOnePlayer[0];
			player[i].hero = mainInfoOnePlayer[1];
			player[i].role = Integer.parseInt(mainInfoOnePlayer[2]);
			player[i].level = Integer.parseInt(mainInfoOnePlayer[3]);
			player[i].kills = Integer.parseInt(mainInfoOnePlayer[4]);
			player[i].deaths = Integer.parseInt(mainInfoOnePlayer[5]);
			player[i].assists = Integer.parseInt(mainInfoOnePlayer[6]);
			player[i].partisipation = Integer.parseInt(mainInfoOnePlayer[7]);
			player[i].heroHeal = Integer.parseInt(mainInfoOnePlayer[8]);
			player[i].heroDamage = Integer.parseInt(mainInfoOnePlayer[9]);
			player[i].towerDamage = Integer.parseInt(mainInfoOnePlayer[10]);
			player[i].totalGPM = Integer.parseInt(mainInfoOnePlayer[11]);
			player[i].totalXPM = Integer.parseInt(mainInfoOnePlayer[12]);
			player[i].totalLH = Integer.parseInt(mainInfoOnePlayer[13]);
			player[i].towersDestroyed = Integer.parseInt(mainInfoOnePlayer[14]);
			player[i].towersDenied = Integer.parseInt(mainInfoOnePlayer[15]);
			player[i].roshanKills = Integer.parseInt(mainInfoOnePlayer[16]);
			player[i].goldForKills = Integer.parseInt(mainInfoOnePlayer[17]);
			player[i].goldFed = Integer.parseInt(mainInfoOnePlayer[18]);
			player[i].goldLost = Integer.parseInt(mainInfoOnePlayer[19]);
			player[i].totalGold = Integer.parseInt(mainInfoOnePlayer[20]);
			player[i].observerWardsPlaced = Integer.parseInt(mainInfoOnePlayer[21]);
			player[i].observerWardsDestroyed = Integer.parseInt(mainInfoOnePlayer[22]);
			player[i].sentryWardsPlaced = Integer.parseInt(mainInfoOnePlayer[23]);
			player[i].sentryWardsDestroyed = Integer.parseInt(mainInfoOnePlayer[24]);
			player[i].dustHits = Integer.parseInt(mainInfoOnePlayer[25]);
			player[i].dustAccuracy = Integer.parseInt(mainInfoOnePlayer[26]);
			player[i].smokeHits = Integer.parseInt(mainInfoOnePlayer[27]);
			player[i].smokeTotalHeroes = Integer.parseInt(mainInfoOnePlayer[28]);
			player[i].gemTimeCarried = Integer.parseInt(mainInfoOnePlayer[29]);
			//Items
			String[] ItemsInfoOnePlayer = playerInfoItemsGPMXPMLH[1].split(";");
			for (int j = 0; j < 6; j++)
			{
				player[i].item[j] = ItemsInfoOnePlayer[j];
			}
			//GPM
			String[] GPMInfoOnePlayer = playerInfoItemsGPMXPMLH[2].split(";");
			for (int j = 0; j < Integer.parseInt(GPMInfoOnePlayer[0]); j++)
			{
				player[i].minuteGPM[j] = Integer.parseInt(GPMInfoOnePlayer[j + 1]);
			}
			//XPM
			String[] XPMInfoOnePlayer = playerInfoItemsGPMXPMLH[3].split(";");
			for (int j = 0; j < Integer.parseInt(XPMInfoOnePlayer[0]); j++)
			{
				player[i].minuteXPM[j] = Integer.parseInt(XPMInfoOnePlayer[j + 1]);
			}
			//LH
			String[] LHInfoOnePlayer = playerInfoItemsGPMXPMLH[4].split(";");
			for (int j = 0; j < Integer.parseInt(LHInfoOnePlayer[0]); j++)
			{
				player[i].perMinuteLastHits[j] = Integer.parseInt(LHInfoOnePlayer[j + 1]);
			}
		}
		//</editor-fold>

		//<editor-fold desc="KillEvents">
		String[] oneKillEvent = killEventsInfo.split("\\*\\*");
		for (int i = 0; i < Integer.parseInt(oneKillEvent[0]); i++)
		{
			KillEvent killik = new KillEvent();
			String[] oneKillEventInfo = oneKillEvent[i + 1].split(";");
			killik.x = Float.parseFloat(oneKillEventInfo[0]);
			killik.y = Float.parseFloat(oneKillEventInfo[1]);
			killik.second = Integer.parseInt(oneKillEventInfo[2]);
			killik.dier = Integer.parseInt(oneKillEventInfo[3]);
			killik.killers[0] = Integer.parseInt(oneKillEventInfo[4]);
			killik.killers[1] = Integer.parseInt(oneKillEventInfo[5]);
			killik.killers[2] = Integer.parseInt(oneKillEventInfo[6]);
			killik.killers[3] = Integer.parseInt(oneKillEventInfo[7]);
			killik.killers[4] = Integer.parseInt(oneKillEventInfo[8]);
			killEventArrayList.add(killik);
		}
		//</editor-fold>

		//<editor-fold desc="BuyBackEvents">
		String[] oneBuyBackEvent = buyBackEventsInfo.split("\\*\\*");
		for (int i = 0; i < Integer.parseInt(oneBuyBackEvent[0]); i++)
		{
			BuyBackEvent buybackik = new BuyBackEvent();
			String[] oneBuyBackInfo = oneBuyBackEvent[i + 1].split(";");
			buybackik.whoBoughtBack = oneBuyBackInfo[0];
			buybackik.second = Integer.parseInt(oneBuyBackInfo[1]);
			buybackik.goldCost = Integer.parseInt(oneBuyBackInfo[2]);
			buyBackEventArrayList.add(buybackik);
		}
		//</editor-fold>

		//<editor-fold desc="GlyphEvents">
		String[] oneGlyphEvent = glyphEventsInfo.split("\\*\\*");
		for (int i = 0; i < Integer.parseInt(oneGlyphEvent[0]); i++)
		{
			GlyphEvent glyphik = new GlyphEvent();
			String[] oneGlyphInfo = oneGlyphEvent[i + 1].split(";");
			glyphik.side = Integer.parseInt(oneGlyphInfo[0]);
			glyphik.second = Integer.parseInt(oneGlyphInfo[1]);
			glyphEventArrayList.add(glyphik);
		}
		//</editor-fold>

		//<editor-fold desc="TowerEvents">
		String[] oneTowerEvent = towerEventsInfo.split("\\*\\*");
		for (int i = 0; i < Integer.parseInt(oneTowerEvent[0]); i++)
		{
			TowerEvent towerik = new TowerEvent();
			String[] oneTowerInfo = oneTowerEvent[i + 1].split(";");
			towerik.whoDestroy = oneTowerInfo[0];
			towerik.second = Integer.parseInt(oneTowerInfo[1]);
			towerik.tierLevel = Integer.parseInt(oneTowerInfo[2]);
			towerEventArrayList.add(towerik);
		}
		//</editor-fold>

		//<editor-fold desc="WardEvents">
		String[] oneWardEvent = wardEventsInfo.split("\\*\\*");
		for (int i = 0; i < Integer.parseInt(oneWardEvent[0]); i++)
		{
			WardEvent wardik = new WardEvent();
			String[] oneWardInfo = oneWardEvent[i + 1].split(";");
			wardik.x = Float.parseFloat(oneWardInfo[0]);
			wardik.y = Float.parseFloat(oneWardInfo[1]);
			wardik.second = Integer.parseInt(oneWardInfo[2]);
			wardik.lifeTime = Integer.parseInt(oneWardInfo[3]);
			wardEventArrayList.add(wardik);
		}
		//</editor-fold>

	}
}
