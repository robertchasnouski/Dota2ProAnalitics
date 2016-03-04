import Analitics.FileControlFactory;
import MatchInfo.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriterReaderFactory
{

	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	void writeMatchTestInfoToFile(Player[] players, Team[] teams, Match match, ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent)
	{
		//Match
		writeToFile("MATCH INFO:", false);
		writeToFile("Match ID:" + match.id, true);
		if (match.id.length() != 10)
			System.out.println("MatchID Error");
		writeToFile("Match date:" + match.date, true);
		if (match.date.length() != 10)
			System.out.println("MatchDate Error");
		writeToFile("Match minutes:" + Integer.toString(match.matchTime), true);
		if (match.matchTime < 10 || match.matchTime > 150)
			System.out.println("MatchTime Error");
		writeToFile("Team1Id:" + match.team1Id, true);
		writeToFile("Team2Id:" + match.team2Id, true);
		writeToFile("LeagueId:" + Integer.toString(match.leagueId), true);
		writeToFile("LeagueName:" + match.leagueName, true);
		writeToFile("WinRadiant?:" + Boolean.toString(match.winRadiant), true);
		writeToFile("FBRadiant?:" + Boolean.toString(match.firstBloodRadiant), true);
		writeToFile("F10KRadiant?:" + Boolean.toString(match.first10KillsRadiant), true);
		writeToFile("FRRadiant?:" + Boolean.toString(match.firstRoshanRadiant), true);
		writeToFile("FBTime:" + Integer.toString(match.FBTime), true);
		if (match.FBTime > match.matchTime * 60 || match.FBTime < -90)
			System.out.println("FBTime error");
		writeToFile("F10KTime:" + Integer.toString(match.F10KTime), true);
		if (match.F10KTime > match.matchTime * 60 || match.F10KTime < -90)
			System.out.println("F10KTime error");
		writeToFile("FRTIme:" + Integer.toString(match.FRoshanTime), false);
		if (match.FRoshanTime > match.matchTime * 60 || match.FRoshanTime < -90)
			if (match.FRoshanTime != 9999)
				System.out.println("FRTime error");
		writeToFile("##", false);
		//Team
		writeToFile("TEAMS INFO:", false);
		for (int i = 0; i < 2; i++)
		{
			writeToFile("TeamId:" + teams[i].id, true);
			writeToFile("Name:" + teams[i].name, true);
			writeToFile("Rating:" + Integer.toString(teams[i].rating), true);
			writeToFile("KDA:" + Integer.toString(teams[i].kills), true);
			if (teams[i].kills < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(teams[i].deaths), true);
			if (teams[i].deaths < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(teams[i].assists), true);
			if (teams[i].assists < 0)
				System.out.println("KDA error");
			writeToFile("Partisipartion:" + Integer.toString(teams[i].partisipation), true);
			if (teams[i].partisipation < 0 || teams[i].partisipation > 100)
				System.out.println("Partisipation error");
			writeToFile("HH:" + Integer.toString(teams[i].heroHeal), true);
			if (teams[i].heroHeal < 0 || teams[i].heroHeal > 300000)
				System.out.println("HeroHeal error");
			writeToFile("HD:" + Integer.toString(teams[i].heroDamage), true);
			if (teams[i].heroDamage < 0 || teams[i].heroDamage > 300000)
				System.out.println("HeroDamage error");
			writeToFile("TD:" + Integer.toString(teams[i].towerDamage), true);
			if (teams[i].towerDamage < 0 || teams[i].towerDamage > 300000)
				System.out.println("TowerDamage error");
			writeToFile("totalGPM:" + Integer.toString(teams[i].totalGPM), true);
			if (teams[i].totalGPM < -5000 || teams[i].totalGPM > 20000)
				System.out.println("TotalGPM error");
			writeToFile("totalXPM:" + Integer.toString(teams[i].totalXPM), true);
			if (teams[i].totalXPM < 0 || teams[i].totalXPM > 20000)
				System.out.println("TotalXPM error");
			writeToFile("totalLH:" + Integer.toString(teams[i].totalLH), true);
			if (teams[i].totalLH < 0 || teams[i].totalLH > 15000)
				System.out.println("TotalLH error");
			writeToFile("totalDenies:" + Integer.toString(teams[i].totalDenies), true);
			if (teams[i].totalDenies < 0 || teams[i].totalDenies > 1000)
				System.out.println("TotalDenies error");
			writeToFile("towersDestroyed:" + Integer.toString(teams[i].towersDestroyed), true);
			if (teams[i].towersDestroyed < 0 || teams[i].towersDestroyed > 12)
				System.out.println("TowersDestroyed error");
			writeToFile("towersDenied:" + Integer.toString(teams[i].towersDenied), true);
			if (teams[i].towersDenied < 0 || teams[i].towersDenied > 12)
				System.out.println("TowersDenied error");
			writeToFile("RoshanKills:" + Integer.toString(teams[i].roshanKills), true);
			if (teams[i].roshanKills < 0 || teams[i].roshanKills > 10)
				System.out.println("RoshanKills error");
			writeToFile("GfK:" + Integer.toString(teams[i].goldForKills), true);
			if (teams[i].goldForKills < 0 || teams[i].goldForKills > 150000)
				System.out.println("GoldForKills error");
			writeToFile("GF:" + Integer.toString(teams[i].goldFed), true);
			if (teams[i].goldFed < 0 || teams[i].goldFed > 150000)
				System.out.println("GoldFed error");
			writeToFile("GL:" + Integer.toString(teams[i].goldLost), true);
			if (teams[i].goldLost < 0 || teams[i].goldLost > 150000)
				System.out.println("GoldLost error");
			writeToFile("totalGold:" + Integer.toString(teams[i].totalGold), true);
			if (teams[i].totalGold < 0 || teams[i].totalGold > 300000)
				System.out.println("TotalGold error");
			writeToFile("OWP:" + Integer.toString(teams[i].observerWardsPlaced), true);
			if (teams[i].observerWardsPlaced < 0 || teams[i].observerWardsPlaced > 50)
				System.out.println("ObserwerWardsPlaced error");
			writeToFile("OWD:" + Integer.toString(teams[i].observerWardsDestroyed), true);
			if (teams[i].observerWardsDestroyed < 0 || teams[i].observerWardsDestroyed > 50)
				System.out.println("ObserwerWardsDestroyed error");
			writeToFile("SWP:" + Integer.toString(teams[i].sentryWardsPlaced), true);
			if (teams[i].sentryWardsPlaced < 0 || teams[i].sentryWardsPlaced > 50)
				System.out.println("SentryWardsPlaced error");
			writeToFile("SWD:" + Integer.toString(teams[i].sentryWardsDestroyed), true);
			if (teams[i].sentryWardsDestroyed < 0 || teams[i].sentryWardsDestroyed > 50)
				System.out.println("SentryWardsDestroyed error");
			writeToFile("DustHits:" + Integer.toString(teams[i].dustHits), true);
			if (teams[i].dustHits < 0 || teams[i].dustHits > 50)
				System.out.println("DustHits error");
			writeToFile("DustAccuracy:" + Integer.toString(teams[i].dustAccuracy), true);
			if (teams[i].dustAccuracy < 0 || teams[i].dustAccuracy > 100)
				System.out.println("DustAccuracy error");
			writeToFile("SmokeHits:" + Integer.toString(teams[i].smokeHits), true);
			if (teams[i].smokeHits < 0 || teams[i].smokeHits > 20)
				System.out.println("SmokeHits error");
			writeToFile("SmokeTotalHeroes:" + Integer.toString(teams[i].smokeTotalHeroes), true);
			if (teams[i].smokeTotalHeroes < 0 || teams[i].smokeTotalHeroes > 100)
				System.out.println("SmokeTotalHeroes error");
			writeToFile("GemsBought:" + Integer.toString(teams[i].gemsBought), true);
			if (teams[i].gemsBought < 0 || teams[i].gemsBought > 10)
				System.out.println("GemsBought error");
			writeToFile("GemsDropped:" + Integer.toString(teams[i].gemsDropped), true);
			if (teams[i].gemsDropped < 0 || teams[i].gemsDropped > 20)
				System.out.println("GemsDropped error");
			writeToFile("GemTimeCarried:" + Integer.toString(teams[i].gemTimeCarried), false);
			if (teams[i].gemTimeCarried < 0 || teams[i].gemTimeCarried > 10000)
				System.out.println("GemTimeCarried error");
			//GPM
			writeToFile("**", false);
			writeToFile("UniversalX GPM ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), false);
				if (teams[i].minuteGPM[j] < -20000 || teams[i].minuteGPM[j] > 20000)
					System.out.println("minuteGPM error");
			}
			//XPM
			writeToFile("**", false);
			writeToFile("UniversalX XPM ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteXPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteXPM[j]), false);
				if (teams[i].minuteXPM[j] < 0 || teams[i].minuteXPM[j] > 20000)
					System.out.println("minuteXPM error");
			}
			//LH
			writeToFile("**", false);
			writeToFile("UniversalX LH ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), true);
				else
					writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), false);
				if (teams[i].perMinuteLastHits[j] < 0 || teams[i].perMinuteLastHits[j] > 100)
					System.out.println("perMinuteLastHits error");
			}
			if (i == 0)
				writeToFile("||", false);
		}
		writeToFile("##", false);
		writeToFile("PLAYERS INFO", false);
		//Player
		for (int i = 0; i < 10; i++)
		{
			writeToFile("ID:" + players[i].playerId, true);
			writeToFile("Hero:" + players[i].hero, true);
			writeToFile("Role:" + Integer.toString(players[i].role), true);
			writeToFile("Level:" + Integer.toString(players[i].level), true);
			writeToFile("KDA:" + Integer.toString(players[i].kills), true);
			if (players[i].kills < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(players[i].deaths), true);
			if (players[i].deaths < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(players[i].assists), true);
			if (players[i].assists < 0)
				System.out.println("KDA error");
			writeToFile("Partisipation:" + Integer.toString(players[i].partisipation), true);
			if (players[i].partisipation < 0 || players[i].partisipation > 100)
				System.out.println("Partisipation error");
			writeToFile("HH:" + Integer.toString(players[i].heroHeal), true);
			if (players[i].heroHeal < 0 || players[i].heroHeal > 100000)
				System.out.println("HeroHeal error");
			writeToFile("HD:" + Integer.toString(players[i].heroDamage), true);
			if (players[i].heroDamage < 0 || players[i].heroDamage > 100000)
				System.out.println("HeroDamage error");
			writeToFile("TD:" + Integer.toString(players[i].towerDamage), true);
			if (players[i].towerDamage < 0 || players[i].towerDamage > 100000)
				System.out.println("TowerDamage error");
			writeToFile("totalGPM:" + Integer.toString(players[i].totalGPM), true);
			if (players[i].totalGPM < 50 || players[i].totalGPM > 10000)
				System.out.println("TotalGPM error");
			writeToFile("totalXPM:" + Integer.toString(players[i].totalXPM), true);
			if (players[i].totalXPM < 0 || players[i].totalXPM > 10000)
				System.out.println("TotalXPM error");
			writeToFile("totalLH:" + Integer.toString(players[i].totalLH), true);
			if (players[i].totalLH < 0 || players[i].totalLH > 15000)
				System.out.println("TotalLH error");
			writeToFile("towersDestroyed:" + Integer.toString(players[i].towersDestroyed), true);
			if (players[i].towersDestroyed < 0 || players[i].towersDestroyed > 12)
				System.out.println("TowersDestroyed error");
			writeToFile("towersDenied:" + Integer.toString(players[i].towersDenied), true);
			if (players[i].towersDenied < 0 || players[i].towersDenied > 12)
				System.out.println("TowersDenied error");
			writeToFile("roshanKills:" + Integer.toString(players[i].roshanKills), true);
			if (players[i].roshanKills < 0 || players[i].roshanKills > 10)
				System.out.println("RoshanKills error");
			writeToFile("GfK:" + Integer.toString(players[i].goldForKills), true);
			if (players[i].goldForKills < 0 || players[i].goldForKills > 100000)
				System.out.println("GoldForKills error");
			writeToFile("GF:" + Integer.toString(players[i].goldFed), true);
			if (players[i].goldFed < 0 || players[i].goldFed > 100000)
				System.out.println("GoldFed error");
			writeToFile("GL:" + Integer.toString(players[i].goldLost), true);
			if (players[i].goldLost < 0 || players[i].goldLost > 100000)
				System.out.println("GoldLost error");
			writeToFile("TotalGold:" + Integer.toString(players[i].totalGold), true);
			if (players[i].totalGold < 0 || players[i].totalGold > 100000)
				System.out.println("TotalGold error");
			writeToFile("OWP:" + Integer.toString(players[i].observerWardsPlaced), true);
			if (players[i].observerWardsPlaced < 0 || players[i].observerWardsPlaced > 50)
				System.out.println("ObserwerWardsPlaced error");
			writeToFile("OWD:" + Integer.toString(players[i].observerWardsDestroyed), true);
			if (players[i].observerWardsDestroyed < 0 || players[i].observerWardsDestroyed > 50)
				System.out.println("ObserwerWardsDestroyed error");
			writeToFile("SWP:" + Integer.toString(players[i].sentryWardsPlaced), true);
			if (players[i].sentryWardsPlaced < 0 || players[i].sentryWardsPlaced > 50)
				System.out.println("SentryWardsPlaced error");
			writeToFile("SWD:" + Integer.toString(players[i].sentryWardsDestroyed), true);
			if (players[i].sentryWardsDestroyed < 0 || players[i].sentryWardsDestroyed > 50)
				System.out.println("SentryWardsDestroyed error");
			writeToFile("DustHits:" + Integer.toString(players[i].dustHits), true);
			if (players[i].dustHits < 0 || players[i].dustHits > 50)
				System.out.println("DustHits error");
			writeToFile("DustAccuracy:" + Integer.toString(players[i].dustAccuracy), true);
			if (players[i].dustAccuracy < 0 || players[i].dustAccuracy > 100)
				System.out.println("DustAccuracy error");
			writeToFile("SmokeHits:" + Integer.toString(players[i].smokeHits), true);
			if (players[i].smokeHits < 0 || players[i].smokeHits > 20)
				System.out.println("SmokeHits error");
			writeToFile("SmokeTotalHeroes:" + Integer.toString(players[i].smokeTotalHeroes), true);
			if (players[i].smokeTotalHeroes < 0 || players[i].smokeTotalHeroes > 100)
				System.out.println("SmokeTotalHeroes error");
			writeToFile("GemTimeCarried:" + Integer.toString(players[i].gemTimeCarried), false);
			if (players[i].gemTimeCarried < 0 || players[i].gemTimeCarried > 10000)
				System.out.println("GemTimeCarried error");
			//Items
			writeToFile("**", false);
			writeToFile("ITEMS:", false);
			for (int j = 0; j < 6; j++)
			{
				if (j != 5)
					writeToFile(players[i].item[j], true);
				else
					writeToFile(players[i].item[j], false);
			}
			//GPM
			writeToFile("**", false);
			writeToFile("UniversalX GPM ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(players[i].minuteGPM[j]), false);
			}
			//XPM
			writeToFile("**", false);
			writeToFile("UniversalX XPM ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].minuteXPM[j]), true);
				else
					writeToFile(Integer.toString(players[i].minuteXPM[j]), false);
			}
			//LH
			writeToFile("**", false);
			writeToFile("UniversalX LH ARRAY:" + Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), true);
				else
					writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), false);
			}
			if (i != 9)
				writeToFile("||", false);
		}
		//KillEvents
		writeToFile("##", false);
		writeToFile("UniversalX KILLEVENTS:" + Integer.toString(killEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < killEvent.size(); i++)
		{
			writeToFile("X:" + Float.toString(killEvent.get(i).x), true);
			if (killEvent.get(i).x < 0 || killEvent.get(i).x > 100)
				System.out.println("KillEvents X error");
			writeToFile("Y:" + Float.toString(killEvent.get(i).y), true);
			if (killEvent.get(i).y < 0 || killEvent.get(i).y > 100)
				System.out.println("KillEvents Y error");
			writeToFile("Second:" + Integer.toString(killEvent.get(i).second), true);
			if (killEvent.get(i).second < -90 || killEvent.get(i).second > match.matchTime * 60 + 60)
				System.out.println("KillEvents Second error");
			writeToFile("Dier:" + Integer.toString(killEvent.get(i).dier), true);
			if (killEvent.get(i).dier < 0 || killEvent.get(i).dier > 10)
				System.out.println("KillEvents Dier error");
			writeToFile("Killers:", false);
			for (int j = 0; j < 5; j++)
			{
				if (killEvent.get(i).killers[j].equals("null"))
					killEvent.get(i).killers[j] = 0;
				if (j != 4)
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), true);
				else
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), false);
				if (killEvent.get(i).killers[j] < 0 || killEvent.get(i).killers[j] > 10)
					System.out.println("KillEvents Dier error");
			}

			if (i != killEvent.size() - 1)
				writeToFile("**", false);
		}
		//WardEvent
		writeToFile("##", false);
		writeToFile("UniversalX WARDEVENTS:" + Integer.toString(wardEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < wardEvent.size(); i++)
		{
			writeToFile("X:" + Float.toString(wardEvent.get(i).x), true);
			if (wardEvent.get(i).x < 0 || wardEvent.get(i).x > 100)
				System.out.println("WardEvent X error");
			writeToFile("Y:" + Float.toString(wardEvent.get(i).y), true);
			if (wardEvent.get(i).y < 0 || wardEvent.get(i).y > 100)
				System.out.println("WardEvent Y error");
			writeToFile("Second:" + Integer.toString(wardEvent.get(i).second), true);
			if (wardEvent.get(i).second < -90 || wardEvent.get(i).second > match.matchTime * 60 + 60)
				System.out.println("WardEvent second error");
			writeToFile("LifeTime:" + Integer.toString(wardEvent.get(i).lifeTime), false);
			if (wardEvent.get(i).lifeTime < 0 || wardEvent.get(i).lifeTime > 420)
				System.out.println("WardEvent LifeTime error");
			if (i != wardEvent.size() - 1)
				writeToFile("**", false);
		}
		//GlyphEvent
		writeToFile("##", false);
		writeToFile("UniversalX GLYPHEVENTS:" + Integer.toString(glyphEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < glyphEvent.size(); i++)
		{
			writeToFile("Side:" + Integer.toString(glyphEvent.get(i).side), true);
			if (glyphEvent.get(i).side < 1 || glyphEvent.get(i).side > 2)
				System.out.println("GlyphEvent Side error");
			writeToFile("Second:" + Integer.toString(glyphEvent.get(i).second), false);
			if (glyphEvent.get(i).second < -90 || glyphEvent.get(i).second > match.matchTime * 60 + 60)
				System.out.println("GlyphEvent Second error");
			if (i != glyphEvent.size() - 1)
				writeToFile("**", false);
		}
		//BuyBackEvent
		writeToFile("##", false);
		writeToFile("UniversalX BUYBACKEVENTS:" + Integer.toString(buyBackEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < buyBackEvent.size(); i++)
		{
			writeToFile("Who:" + buyBackEvent.get(i).whoBoughtBack, true);
			writeToFile("Second:" + Integer.toString(buyBackEvent.get(i).second), true);
			if (buyBackEvent.get(i).second < -90 || buyBackEvent.get(i).second > match.matchTime * 60 + 60)
				System.out.println("BuyBackEvent Second error");
			writeToFile("GoldCost:" + Integer.toString(buyBackEvent.get(i).goldCost), false);
			if (buyBackEvent.get(i).goldCost < 0 || buyBackEvent.get(i).goldCost > 4000)
				System.out.println("BuyBackEvent goldCost error");
			if (i != buyBackEvent.size() - 1)
				writeToFile("**", false);
		}
		//TowerEvent
		writeToFile("##", false);
		writeToFile("UniversalX TOWEREVENTS:" + Integer.toString(towerEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < towerEvent.size(); i++)
		{
			writeToFile("WhoDestroy:" + towerEvent.get(i).whoDestroy, true);
			writeToFile("Second:" + Integer.toString(towerEvent.get(i).second), true);
			if (towerEvent.get(i).second < -90 || towerEvent.get(i).second > match.matchTime * 60 + 60)
				System.out.println("TowerEvent second error");
			writeToFile("TierLevel:" + Integer.toString(towerEvent.get(i).tierLevel), false);
			if (towerEvent.get(i).tierLevel < 1 || towerEvent.get(i).tierLevel > 4)
				System.out.println("TowerEvent tierLevel error");
			if (i != towerEvent.size() - 1)
				writeToFile("**", false);
		}
		writeToFile("\n", false);
	}

	Boolean writeMatchInfoToFile(Player[] players, Team[] teams, Match match, ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent) throws IOException
	{
		boolean error = false;
		//Match
		writeToFile(match.id, true);
		if (match.id.length() != 10)
		{
			System.out.println("MatchID Error");
			error = true;
		}
		writeToFile(match.date, true);
		if (match.date.length() != 10)
		{
			error = true;
			System.out.println("MatchDate Error");
		}

		writeToFile(Integer.toString(match.matchTime), true);
		if (match.matchTime < 10 || match.matchTime > 150)
		{
			error = true;
			System.out.println("MatchTime Error");
		}
		writeToFile(match.team1Id, true);
		writeToFile(match.team2Id, true);
		writeToFile(Integer.toString(match.leagueId), true);
		writeToFile(match.leagueName, true);
		writeToFile(Boolean.toString(match.winRadiant), true);
		writeToFile(Boolean.toString(match.firstBloodRadiant), true);
		writeToFile(Boolean.toString(match.first10KillsRadiant), true);
		writeToFile(Boolean.toString(match.firstRoshanRadiant), true);
		writeToFile(Integer.toString(match.FBTime), true);
		if ((match.FBTime > match.matchTime * 60 || match.FBTime < -90) && match.FBTime != 9999)
		{
			error = true;
			System.out.println("FBTime error");
		}
		writeToFile(Integer.toString(match.F10KTime), true);
		if ((match.F10KTime > match.matchTime * 60 || match.F10KTime < -90) && match.F10KTime != 9999)
		{
			error = true;
			System.out.println("F10KTime error");
		}

		writeToFile(Integer.toString(match.FRoshanTime),

				false);
		if ((match.FRoshanTime > match.matchTime * 60 || match.FRoshanTime < -90) && match.FRoshanTime != 9999)
		{
			error = true;
			System.out.println("FRTime error");
		}

		writeToFile("##", false);
		//Team
		for (int i = 0; i < 2; i++)
		{
			writeToFile(teams[i].id, true);
			writeToFile(teams[i].name, true);
			writeToFile("1000", true);
			writeToFile(Integer.toString(teams[i].kills), true);
			if (teams[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(teams[i].deaths), true);
			if (teams[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(teams[i].assists), true);
			if (teams[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(teams[i].partisipation), true);
			if (teams[i].partisipation < 0 || teams[i].partisipation > 200)
			{
				error = true;
				System.out.println("Partisipation error");
			}
			writeToFile(Integer.toString(teams[i].heroHeal), true);
			if (teams[i].heroHeal < 0 || teams[i].heroHeal > 100000)
			{
				error = true;
				System.out.println("HeroHeal error");
			}
			writeToFile(Integer.toString(teams[i].heroDamage), true);
			if (teams[i].heroDamage < 0 || teams[i].heroDamage > 250000)
			{
				error = true;
				System.out.println("HeroDamage error");
			}
			writeToFile(Integer.toString(teams[i].towerDamage), true);
			if (teams[i].towerDamage < 0 || teams[i].towerDamage > 100000)
			{
				error = true;
				System.out.println("TowerDamage error");
			}
			writeToFile(Integer.toString(teams[i].totalGPM), true);
			if (teams[i].totalGPM < 50 || teams[i].totalGPM > 12000)
			{
				error = true;
				System.out.println("TotalGPM error");
			}
			writeToFile(Integer.toString(teams[i].totalXPM), true);
			if (teams[i].totalXPM < 0 || teams[i].totalXPM > 10000)
			{
				error = true;
				System.out.println("TotalXPM error");
			}
			writeToFile(Integer.toString(teams[i].totalLH), true);
			if (teams[i].totalLH < 0 || teams[i].totalLH > 15000)
			{
				error = true;
				System.out.println("TotalLH error");
			}
			writeToFile(Integer.toString(teams[i].totalDenies), true);
			if (teams[i].totalDenies < 0 || teams[i].totalDenies > 2000)
			{
				error = true;
				System.out.println("TotalDenies error");
			}
			writeToFile(Integer.toString(teams[i].towersDestroyed), true);
			if (teams[i].towersDestroyed < 0 || teams[i].towersDestroyed > 12)
			{
				error = true;
				System.out.println("TowersDestroyed error");
			}
			writeToFile(Integer.toString(teams[i].towersDenied), true);
			if (teams[i].towersDenied < 0 || teams[i].towersDenied > 12)
			{
				error = true;
				System.out.println("TowersDenied error");
			}
			writeToFile(Integer.toString(teams[i].roshanKills), true);
			if (teams[i].roshanKills < 0 || teams[i].roshanKills > 10)
			{
				error = true;
				System.out.println("RoshanKills error");
			}
			writeToFile(Integer.toString(teams[i].goldForKills), true);
			if (teams[i].goldForKills < 0 || teams[i].goldForKills > 100000)
			{
				error = true;
				System.out.println("GoldForKills error");
			}
			writeToFile(Integer.toString(teams[i].goldFed), true);
			if (teams[i].goldFed < 0 || teams[i].goldFed > 100000)
			{
				error = true;
				System.out.println("GoldFed error");
			}
			writeToFile(Integer.toString(teams[i].goldLost), true);
			if (teams[i].goldLost < 0 || teams[i].goldLost > 100000)
			{
				error = true;
				System.out.println("GoldLost error");
			}
			writeToFile(Integer.toString(teams[i].totalGold), true);
			if (teams[i].totalGold < 0 || teams[i].totalGold > 250000)
			{
				error = true;
				System.out.println("TotalGold error");
			}
			writeToFile(Integer.toString(teams[i].observerWardsPlaced), true);
			if (teams[i].observerWardsPlaced < 0 || teams[i].observerWardsPlaced > 60)
			{
				error = true;
				System.out.println("ObserwerWardsPlaced error");
			}
			writeToFile(Integer.toString(teams[i].observerWardsDestroyed), true);
			if (teams[i].observerWardsDestroyed < 0 || teams[i].observerWardsDestroyed > 50)
			{
				error = true;
				System.out.println("ObserwerWardsDestroyed error");
			}
			writeToFile(Integer.toString(teams[i].sentryWardsPlaced), true);
			if (teams[i].sentryWardsPlaced < 0 || teams[i].sentryWardsPlaced > 70)
			{
				error = true;
				System.out.println("SentryWardsPlaced error");
			}
			writeToFile(Integer.toString(teams[i].sentryWardsDestroyed), true);
			if (teams[i].sentryWardsDestroyed < 0 || teams[i].sentryWardsDestroyed > 50)
			{
				error = true;
				System.out.println("SentryWardsDestroyed error");
			}
			writeToFile(Integer.toString(teams[i].dustHits), true);
			if (teams[i].dustHits < 0 || teams[i].dustHits > 50)
			{
				error = true;
				System.out.println("DustHits error");
			}
			writeToFile(Integer.toString(teams[i].dustAccuracy), true);
			if (teams[i].dustAccuracy < 0 || teams[i].dustAccuracy > 100)
			{
				error = true;
				System.out.println("DustAccuracy error");
			}
			writeToFile(Integer.toString(teams[i].smokeHits), true);
			if (teams[i].smokeHits < 0 || teams[i].smokeHits > 20)
			{
				error = true;
				System.out.println("SmokeHits error");
			}
			writeToFile(Integer.toString(teams[i].smokeTotalHeroes), true);
			if (teams[i].smokeTotalHeroes < 0 || teams[i].smokeTotalHeroes > 100)
			{
				error = true;
				System.out.println("SmokeTotalHeroes error");
			}
			writeToFile(Integer.toString(teams[i].gemsBought), true);
			if (teams[i].gemsBought < 0 || teams[i].gemsBought > 10)
			{
				error = true;
				System.out.println("GemsBought error");
			}
			writeToFile(Integer.toString(teams[i].gemsDropped), true);
			if (teams[i].gemsDropped < 0 || teams[i].gemsDropped > 20)
			{
				error = true;
				System.out.println("GemsDropped error");
			}
			writeToFile(Integer.toString(teams[i].gemTimeCarried), false);
			if (teams[i].gemTimeCarried < 0 || teams[i].gemTimeCarried > 3600)
			{
				error = true;
				System.out.println("GemTimeCarried error");
			}
			//GPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), false);
				if (teams[i].minuteGPM[j] < -20000 || teams[i].minuteGPM[j] > 20000)
				{

					error = true;
					System.out.println("minuteGPM  Team error");
				}
			}
			//XPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteXPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteXPM[j]), false);
				if (teams[i].minuteXPM[j] < 0 || teams[i].minuteXPM[j] > 20000)
				{
					error = true;
					System.out.println("minuteXPM error");
				}
			}
			//LH
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), true);
				else
					writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), false);
				if (teams[i].perMinuteLastHits[j] < 0 || teams[i].perMinuteLastHits[j] > 100)
				{
					error = true;
					System.out.println("perMinuteLastHits error");
				}
			}
			if (i == 0)
				writeToFile("||", false);
		}

		writeToFile("##", false);
		//Player
		for (
				int i = 0;
				i < 10; i++)

		{
			writeToFile(players[i].playerId, true);
			writeToFile(players[i].hero, true);
			writeToFile(Integer.toString(players[i].role), true);
			writeToFile(Integer.toString(players[i].level), true);
			writeToFile(Integer.toString(players[i].kills), true);
			if (players[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(players[i].deaths), true);
			if (players[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(players[i].assists), true);
			if (players[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeToFile(Integer.toString(players[i].partisipation), true);
			if (players[i].partisipation < 0 || players[i].partisipation > 200)
			{
				error = true;
				System.out.println("Partisipation error");
			}
			writeToFile(Integer.toString(players[i].heroHeal), true);
			if (players[i].heroHeal < 0 || players[i].heroHeal > 100000)
			{
				error = true;
				System.out.println("HeroHeal error");
			}
			writeToFile(Integer.toString(players[i].heroDamage), true);
			if (players[i].heroDamage < 0 || players[i].heroDamage > 250000)
			{
				error = true;
				System.out.println("HeroDamage error");
			}
			writeToFile(Integer.toString(players[i].towerDamage), true);
			if (players[i].towerDamage < 0 || players[i].towerDamage > 100000)
			{
				error = true;
				System.out.println("TowerDamage error");
			}
			writeToFile(Integer.toString(players[i].totalGPM), true);
			if (players[i].totalGPM < 50 || players[i].totalGPM > 10000)
			{
				error = true;
				System.out.println("TotalGPM error");
			}
			writeToFile(Integer.toString(players[i].totalXPM), true);
			if (players[i].totalXPM < 0 || players[i].totalXPM > 10000)
			{
				error = true;
				System.out.println("TotalXPM error");
			}
			writeToFile(Integer.toString(players[i].totalLH), true);
			if (players[i].totalLH < 0 || players[i].totalLH > 15000)
			{
				error = true;
				System.out.println("TotalLH error");
			}
			writeToFile(Integer.toString(players[i].towersDestroyed), true);
			if (players[i].towersDestroyed < 0 || players[i].towersDestroyed > 12)
			{
				error = true;
				System.out.println("TowersDestroyed error");
			}
			writeToFile(Integer.toString(players[i].towersDenied), true);
			if (players[i].towersDenied < 0 || players[i].towersDenied > 12)
			{
				error = true;
				System.out.println("TowersDenied error");
			}
			writeToFile(Integer.toString(players[i].roshanKills), true);
			if (players[i].roshanKills < 0 || players[i].roshanKills > 10)
			{
				error = true;
				System.out.println("RoshanKills error");
			}
			writeToFile(Integer.toString(players[i].goldForKills), true);
			if (players[i].goldForKills < 0 || players[i].goldForKills > 100000)
			{
				error = true;
				System.out.println("GoldForKills error");
			}
			writeToFile(Integer.toString(players[i].goldFed), true);
			if (players[i].goldFed < 0 || players[i].goldFed > 100000)
			{
				error = true;
				System.out.println("GoldFed error");
			}
			writeToFile(Integer.toString(players[i].goldLost), true);
			if (players[i].goldLost < 0 || players[i].goldLost > 100000)
			{
				error = true;
				System.out.println("GoldLost error");
			}
			writeToFile(Integer.toString(players[i].totalGold), true);
			if (players[i].totalGold < 0 || players[i].totalGold > 100000)
			{
				error = true;
				System.out.println("TotalGold error");
			}
			writeToFile(Integer.toString(players[i].observerWardsPlaced), true);
			if (players[i].observerWardsPlaced < 0 || players[i].observerWardsPlaced > 50)
			{
				error = true;
				System.out.println("ObserwerWardsPlaced error");
			}
			writeToFile(Integer.toString(players[i].observerWardsDestroyed), true);
			if (players[i].observerWardsDestroyed < 0 || players[i].observerWardsDestroyed > 50)
			{
				error = true;
				System.out.println("ObserwerWardsDestroyed error");
			}
			writeToFile(Integer.toString(players[i].sentryWardsPlaced), true);
			if (players[i].sentryWardsPlaced < 0 || players[i].sentryWardsPlaced > 50)
			{
				error = true;
				System.out.println("SentryWardsPlaced error");
			}
			writeToFile(Integer.toString(players[i].sentryWardsDestroyed), true);
			if (players[i].sentryWardsDestroyed < 0 || players[i].sentryWardsDestroyed > 50)
			{
				error = true;
				System.out.println("SentryWardsDestroyed error");
			}
			writeToFile(Integer.toString(players[i].dustHits), true);
			if (players[i].dustHits < 0 || players[i].dustHits > 50)
			{
				error = true;
				System.out.println("DustHits error");
			}
			writeToFile(Integer.toString(players[i].dustAccuracy), true);
			if (players[i].dustAccuracy < 0 || players[i].dustAccuracy > 100)
			{
				error = true;
				System.out.println("DustAccuracy error");
			}
			writeToFile(Integer.toString(players[i].smokeHits), true);
			if (players[i].smokeHits < 0 || players[i].smokeHits > 20)
			{
				error = true;
				System.out.println("SmokeHits error");
			}
			writeToFile(Integer.toString(players[i].smokeTotalHeroes), true);
			if (players[i].smokeTotalHeroes < 0 || players[i].smokeTotalHeroes > 100)
			{
				error = true;
				System.out.println("SmokeTotalHeroes error");
			}
			writeToFile(Integer.toString(players[i].gemTimeCarried), false);
			if (players[i].gemTimeCarried < 0 || players[i].gemTimeCarried > 5000)
			{
				error = true;
				System.out.println("GemTimeCarried error");
			}
			//Items
			writeToFile("**", false);
			for (int j = 0; j < 6; j++)
			{
				if (j != 5)
					writeToFile(players[i].item[j], true);
				else
					writeToFile(players[i].item[j], false);
			}
			//GPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(players[i].minuteGPM[j]), false);
				if (players[i].minuteGPM[j] < -5000 || players[i].minuteGPM[j] > 5000)
				{
					System.out.println(players[i].minuteGPM[j]);
					System.out.println(i);
					System.out.println(j);
					error = true;
					System.out.println("MinuteGPM Player error");
				}
			}
			//XPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].minuteXPM[j]), true);
				else
					writeToFile(Integer.toString(players[i].minuteXPM[j]), false);
				if (players[i].minuteXPM[j] < -100 || players[i].minuteXPM[j] > 6000)
				{
					error = true;
					System.out.println("MinuteXPM error");
				}
			}
			//LH
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), true);
				else
					writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), false);
				if (players[i].perMinuteLastHits[j] < 0 || players[i].perMinuteLastHits[j] > 100)
				{
					error = true;
					System.out.println("MinuteLH error");
				}
			}
			if (i != 9)
				writeToFile("||", false);
		}

		//KillEvents
		writeToFile("##", false);

		writeToFile(Integer.toString(killEvent.size()), false);
		if (killEvent.size() < 10)
		{
			error = true;
			System.out.println("Only 10 kills? Error!");
		}
		writeToFile("**", false);

		for (int i = 0; i < killEvent.size(); i++)
		{
			writeToFile(Float.toString(killEvent.get(i).x), true);
			if (killEvent.get(i).x < 0.0f || killEvent.get(i).x > 100.0f)
			{
				error = true;
				System.out.println("Kill X error");
			}
			writeToFile(Float.toString(killEvent.get(i).y), true);
			if (killEvent.get(i).y < 0.0f || killEvent.get(i).y > 100.0f)
			{
				error = true;
				System.out.println("Kill Y error");
			}
			writeToFile(Integer.toString(killEvent.get(i).second), true);
			if (killEvent.get(i).second < -90 || killEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Kill second error");
			}
			writeToFile(Integer.toString(killEvent.get(i).dier), true);
			if (killEvent.get(i).dier < 0 || killEvent.get(i).dier > 10)
			{
				error = true;
				System.out.println("Kill Dier error");
			}
			for (int j = 0; j < 5; j++)
			{
				if (killEvent.get(i).killers[j].equals("null"))
					killEvent.get(i).killers[j] = 0;
				if (j != 4)
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), true);
				else
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), false);
				if (killEvent.get(i).killers[j] < 0 || killEvent.get(i).killers[j] > 10)
				{
					error = true;
					System.out.println("Kill Killers error");
				}
			}

			if (i != killEvent.size() - 1)
				writeToFile("**", false);
		}

		//WardEvent
		writeToFile("##", false);

		writeToFile(Integer.toString(wardEvent.size()), false);
		if (wardEvent.size() < 5)
		{
			error = true;
			System.out.println("Only 5 wards? Error!");
		}
		writeToFile("**", false);

		for (int i = 0; i < wardEvent.size(); i++)
		{
			writeToFile(Float.toString(wardEvent.get(i).x), true);
			if (wardEvent.get(i).x < 0.0f || wardEvent.get(i).x > 100.0f)
			{
				error = true;
				System.out.println("Ward X error");
			}
			writeToFile(Float.toString(wardEvent.get(i).y), true);
			if (wardEvent.get(i).y < 0.0f || wardEvent.get(i).y > 100.0f)
			{
				error = true;
				System.out.println("Ward Y error");
			}
			writeToFile(Integer.toString(wardEvent.get(i).second), true);
			if (wardEvent.get(i).second < -90 || wardEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Ward Second error");
			}
			writeToFile(Integer.toString(wardEvent.get(i).lifeTime), false);
			if (wardEvent.get(i).lifeTime < 0 || wardEvent.get(i).lifeTime > 420)
			{
				error = true;
				System.out.println("Ward Lifetime error");
			}
			if (i != wardEvent.size() - 1)
				writeToFile("**", false);
		}

		//GlyphEvent
		writeToFile("##", false);

		writeToFile(Integer.toString(glyphEvent.size()), false);
		if (glyphEvent.size() < 1)
		{
			error = true;
			System.out.println("Only 1 glyph? Error!");
		}
		writeToFile("**", false);

		for (int i = 0; i < glyphEvent.size(); i++)
		{
			writeToFile(Integer.toString(glyphEvent.get(i).side), true);
			if (glyphEvent.get(i).side < 0 || glyphEvent.get(i).side > 2)
			{
				error = true;
				System.out.println("Glyph side error");
			}
			writeToFile(Integer.toString(glyphEvent.get(i).second), false);
			if (glyphEvent.get(i).second < -90 || glyphEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Ward X error");
			}
			if (i != glyphEvent.size() - 1)
				writeToFile("**", false);
		}

		//BuyBackEvent
		writeToFile("##", false);

		writeToFile(Integer.toString(buyBackEvent.size()), false);

		writeToFile("**", false);

		for (int i = 0; i < buyBackEvent.size(); i++)
		{
			writeToFile(buyBackEvent.get(i).whoBoughtBack, true);
			writeToFile(Integer.toString(buyBackEvent.get(i).second), true);
			if (buyBackEvent.get(i).second < -90 || buyBackEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("BuyBackEvent second error");
			}
			writeToFile(Integer.toString(buyBackEvent.get(i).goldCost), false);
			if (i != buyBackEvent.size() - 1)
				writeToFile("**", false);
		}

		//TowerEvent
		writeToFile("##", false);

		writeToFile(Integer.toString(towerEvent.size()), false);
		if (towerEvent.size() < 3)
		{
			System.out.println("Only 3 towers? Error!");
			error = true;
		}
		writeToFile("**", false);

		for (int i = 0; i < towerEvent.size(); i++)
		{
			writeToFile(towerEvent.get(i).whoDestroy, true);
			writeToFile(Integer.toString(towerEvent.get(i).second), true);
			if (towerEvent.get(i).second < -90 || towerEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Tower second error");
			}
			writeToFile(Integer.toString(towerEvent.get(i).tierLevel), false);
			if (towerEvent.get(i).tierLevel < 1 || towerEvent.get(i).tierLevel > 4)
			{
				error = true;
				System.out.println("Tower level error");
			}
			if (i != towerEvent.size() - 1)
				writeToFile("**", false);
		}

		if (error)
		{
			String newString = "";
			String originalFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
			String[] lineByLine = originalFile.split("\n");
			for (int i = 0; i < lineByLine.length - 1; i++)
			{
				newString += lineByLine[i] + "\n";
			}
			fileOperationsFactory.cleanAndWriteToFile(newString, "files/TemporaryMatches.txt");

		} else writeToFile("\n", false);
		if (error)
			return false;
		else return true;

	}

	void makeZeros(Team[] teams, Player[] players, Match match)
	{
		for (int i = 0; i < teams.length; i++)
		{
			teams[i].teamZeros();
		}
		for (int i = 0; i < players.length; i++)
		{
			players[i].playerZeros();
		}
		match.matchZeros();
	}

	void writeToFile(String whatToWrite, Boolean giveSeparator)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("files/TemporaryMatches.txt", true))))
		{
			if (giveSeparator)
				out.print(whatToWrite + ";");
			else
				out.print(whatToWrite);
		} catch (IOException e)
		{
		}

	}

	public void cleanArrayLists(ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent)
	{
		wardEvent.clear();
		towerEvent.clear();
		killEvent.clear();
		glyphEvent.clear();
		buyBackEvent.clear();
	}

	public WriterReaderFactory()
	{

	}

}
