import MatchInfo.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriterReaderFactory
{
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
			if (teams[i].heroHeal < 0 || teams[i].heroHeal > 100000)
				System.out.println("HeroHeal error");
			writeToFile("HD:" + Integer.toString(teams[i].heroDamage), true);
			if (teams[i].heroDamage < 0 || teams[i].heroDamage > 100000)
				System.out.println("HeroDamage error");
			writeToFile("TD:" + Integer.toString(teams[i].towerDamage), true);
			if (teams[i].towerDamage < 0 || teams[i].towerDamage > 100000)
				System.out.println("TowerDamage error");
			writeToFile("totalGPM:" + Integer.toString(teams[i].totalGPM), true);
			if (teams[i].totalGPM < 50 || teams[i].totalGPM > 10000)
				System.out.println("TotalGPM error");
			writeToFile("totalXPM:" + Integer.toString(teams[i].totalXPM), true);
			if (teams[i].totalXPM < 0 || teams[i].totalXPM > 10000)
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
			if (teams[i].goldForKills < 0 || teams[i].goldForKills > 100000)
				System.out.println("GoldForKills error");
			writeToFile("GF:" + Integer.toString(teams[i].goldFed), true);
			if (teams[i].goldFed < 0 || teams[i].goldFed > 100000)
				System.out.println("GoldFed error");
			writeToFile("GL:" + Integer.toString(teams[i].goldLost), true);
			if (teams[i].goldLost < 0 || teams[i].goldLost > 100000)
				System.out.println("GoldLost error");
			writeToFile("totalGold:" + Integer.toString(teams[i].totalGold), true);
			if (teams[i].totalGold < 0 || teams[i].totalGold > 100000)
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
				if (teams[i].minuteGPM[j] < -2000 || teams[i].minuteGPM[j] > 10000)
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
				if (teams[i].minuteXPM[j] < 0 || teams[i].minuteXPM[j] > 10000)
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
			writeToFile("KDA:" + Integer.toString(players[i].totalGold), true);
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
			writeToFile("Y:" + Float.toString(killEvent.get(i).y), true);
			writeToFile("Second:" + Integer.toString(killEvent.get(i).second), true);
			writeToFile("Dier:" + Integer.toString(killEvent.get(i).dier), true);
			writeToFile("Killers:", false);
			for (int j = 0; j < 5; j++)
			{
				if (j != 4)
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), true);
				else
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), false);
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
			writeToFile("Y:" + Float.toString(wardEvent.get(i).y), true);
			writeToFile("Second:" + Integer.toString(wardEvent.get(i).second), true);
			writeToFile("LifeTime:" + Integer.toString(wardEvent.get(i).lifeTime), false);
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
			writeToFile("Second:" + Integer.toString(glyphEvent.get(i).second), false);
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
			writeToFile("GoldCost:" + Integer.toString(buyBackEvent.get(i).goldCost), false);
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
			writeToFile("TierLevel:" + Integer.toString(towerEvent.get(i).tierLevel), false);
			if (i != towerEvent.size() - 1)
				writeToFile("**", false);
		}
		writeToFile("\n", false);
	}

	void writeMatchInfoToFile(Player[] players, Team[] teams, Match match, ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent)
	{
		//Match
		writeToFile(match.id, true);
		if (match.id.length() != 10)
			System.out.println("MatchID Error");
		writeToFile(match.date, true);
		if (match.date.length() != 10)
			System.out.println("MatchDate Error");
		writeToFile(Integer.toString(match.matchTime), true);
		if (match.matchTime < 10 || match.matchTime > 150)
			System.out.println("MatchTime Error");
		writeToFile(match.team1Id, true);
		writeToFile(match.team2Id, true);
		writeToFile(Integer.toString(match.leagueId), true);
		writeToFile(match.leagueName, true);
		writeToFile(Boolean.toString(match.winRadiant), true);
		writeToFile(Boolean.toString(match.firstBloodRadiant), true);
		writeToFile(Boolean.toString(match.first10KillsRadiant), true);
		writeToFile(Boolean.toString(match.firstRoshanRadiant), true);
		writeToFile(Integer.toString(match.FBTime), true);
		if (match.FBTime > match.matchTime * 60 || match.FBTime < -90)
			System.out.println("FBTime error");
		writeToFile(Integer.toString(match.F10KTime), true);
		if (match.F10KTime > match.matchTime * 60 || match.F10KTime < -90)
			System.out.println("F10KTime error");
		writeToFile(Integer.toString(match.FRoshanTime), false);
		if (match.FRoshanTime > match.matchTime * 60 || match.FRoshanTime < -90)
			System.out.println("FRTime error");
		writeToFile("##", false);
		//Team
		for (int i = 0; i < 2; i++)
		{
			writeToFile(teams[i].id, true);
			writeToFile(teams[i].name, true);
			writeToFile(Integer.toString(teams[i].rating), true);
			writeToFile(Integer.toString(teams[i].kills), true);
			if (teams[i].kills < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(teams[i].deaths), true);
			if (teams[i].deaths < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(teams[i].assists), true);
			if (teams[i].assists < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(teams[i].partisipation), true);
			if (teams[i].partisipation < 0 || teams[i].partisipation > 100)
				System.out.println("Partisipation error");
			writeToFile(Integer.toString(teams[i].heroHeal), true);
			if (teams[i].heroHeal < 0 || teams[i].heroHeal > 100000)
				System.out.println("HeroHeal error");
			writeToFile(Integer.toString(teams[i].heroDamage), true);
			if (teams[i].heroDamage < 0 || teams[i].heroDamage > 100000)
				System.out.println("HeroDamage error");
			writeToFile(Integer.toString(teams[i].towerDamage), true);
			if (teams[i].towerDamage < 0 || teams[i].towerDamage > 100000)
				System.out.println("TowerDamage error");
			writeToFile(Integer.toString(teams[i].totalGPM), true);
			if (teams[i].totalGPM < 50 || teams[i].totalGPM > 10000)
				System.out.println("TotalGPM error");
			writeToFile(Integer.toString(teams[i].totalXPM), true);
			if (teams[i].totalXPM < 0 || teams[i].totalXPM > 10000)
				System.out.println("TotalXPM error");
			writeToFile(Integer.toString(teams[i].totalLH), true);
			if (teams[i].totalLH < 0 || teams[i].totalLH > 15000)
				System.out.println("TotalLH error");
			writeToFile(Integer.toString(teams[i].totalDenies), true);
			if (teams[i].totalDenies < 0 || teams[i].totalDenies > 1000)
				System.out.println("TotalDenies error");
			writeToFile(Integer.toString(teams[i].towersDestroyed), true);
			if (teams[i].towersDestroyed < 0 || teams[i].towersDestroyed > 12)
				System.out.println("TowersDestroyed error");
			writeToFile(Integer.toString(teams[i].towersDenied), true);
			if (teams[i].towersDenied < 0 || teams[i].towersDenied > 12)
				System.out.println("TowersDenied error");
			writeToFile(Integer.toString(teams[i].roshanKills), true);
			if (teams[i].roshanKills < 0 || teams[i].roshanKills > 10)
				System.out.println("RoshanKills error");
			writeToFile(Integer.toString(teams[i].goldForKills), true);
			if (teams[i].goldForKills < 0 || teams[i].goldForKills > 100000)
				System.out.println("GoldForKills error");
			writeToFile(Integer.toString(teams[i].goldFed), true);
			if (teams[i].goldFed < 0 || teams[i].goldFed > 100000)
				System.out.println("GoldFed error");
			writeToFile(Integer.toString(teams[i].goldLost), true);
			if (teams[i].goldLost < 0 || teams[i].goldLost > 100000)
				System.out.println("GoldLost error");
			writeToFile(Integer.toString(teams[i].totalGold), true);
			if (teams[i].totalGold < 0 || teams[i].totalGold > 200000)
				System.out.println("TotalGold error");
			writeToFile(Integer.toString(teams[i].observerWardsPlaced), true);
			if (teams[i].observerWardsPlaced < 0 || teams[i].observerWardsPlaced > 50)
				System.out.println("ObserwerWardsPlaced error");
			writeToFile(Integer.toString(teams[i].observerWardsDestroyed), true);
			if (teams[i].observerWardsDestroyed < 0 || teams[i].observerWardsDestroyed > 50)
				System.out.println("ObserwerWardsDestroyed error");
			writeToFile(Integer.toString(teams[i].sentryWardsPlaced), true);
			if (teams[i].sentryWardsPlaced < 0 || teams[i].sentryWardsPlaced > 50)
				System.out.println("SentryWardsPlaced error");
			writeToFile(Integer.toString(teams[i].sentryWardsDestroyed), true);
			if (teams[i].sentryWardsDestroyed < 0 || teams[i].sentryWardsDestroyed > 50)
				System.out.println("SentryWardsDestroyed error");
			writeToFile(Integer.toString(teams[i].dustHits), true);
			if (teams[i].dustHits < 0 || teams[i].dustHits > 50)
				System.out.println("DustHits error");
			writeToFile(Integer.toString(teams[i].dustAccuracy), true);
			if (teams[i].dustAccuracy < 0 || teams[i].dustAccuracy > 100)
				System.out.println("DustAccuracy error");
			writeToFile(Integer.toString(teams[i].smokeHits), true);
			if (teams[i].smokeHits < 0 || teams[i].smokeHits > 20)
				System.out.println("SmokeHits error");
			writeToFile(Integer.toString(teams[i].smokeTotalHeroes), true);
			if (teams[i].smokeTotalHeroes < 0 || teams[i].smokeTotalHeroes > 100)
				System.out.println("SmokeTotalHeroes error");
			writeToFile(Integer.toString(teams[i].gemsBought), true);
			if (teams[i].gemsBought < 0 || teams[i].gemsBought > 10)
				System.out.println("GemsBought error");
			writeToFile(Integer.toString(teams[i].gemsDropped), true);
			if (teams[i].gemsDropped < 0 || teams[i].gemsDropped > 20)
				System.out.println("GemsDropped error");
			writeToFile(Integer.toString(teams[i].gemTimeCarried), false);
			if (teams[i].gemTimeCarried < 0 || teams[i].gemTimeCarried > 10000)
				System.out.println("GemTimeCarried error");
			//GPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), false);
				if (teams[i].minuteGPM[j] < -2000 || teams[i].minuteGPM[j] > 10000)
					System.out.println("minuteGPM error");
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
				if (teams[i].minuteXPM[j] < 0 || teams[i].minuteXPM[j] > 10000)
					System.out.println("minuteXPM error");
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
					System.out.println("perMinuteLastHits error");
			}
			if (i == 0)
				writeToFile("||", false);
		}
		writeToFile("##", false);
		//Player
		for (int i = 0; i < 10; i++)
		{
			writeToFile(players[i].playerId, true);
			writeToFile(players[i].hero, true);
			writeToFile(Integer.toString(players[i].role), true);
			writeToFile(Integer.toString(players[i].level), true);
			writeToFile(Integer.toString(players[i].kills), true);
			if (players[i].kills < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(players[i].deaths), true);
			if (players[i].deaths < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(players[i].assists), true);
			if (players[i].assists < 0)
				System.out.println("KDA error");
			writeToFile(Integer.toString(players[i].partisipation), true);
			if (players[i].partisipation < 0 || players[i].partisipation > 100)
				System.out.println("Partisipation error");
			writeToFile(Integer.toString(players[i].heroHeal), true);
			if (players[i].heroHeal < 0 || players[i].heroHeal > 100000)
				System.out.println("HeroHeal error");
			writeToFile(Integer.toString(players[i].heroDamage), true);
			if (players[i].heroDamage < 0 || players[i].heroDamage > 100000)
				System.out.println("HeroDamage error");
			writeToFile(Integer.toString(players[i].towerDamage), true);
			if (players[i].towerDamage < 0 || players[i].towerDamage > 100000)
				System.out.println("TowerDamage error");
			writeToFile(Integer.toString(players[i].totalGPM), true);
			if (players[i].totalGPM < 50 || players[i].totalGPM > 10000)
				System.out.println("TotalGPM error");
			writeToFile(Integer.toString(players[i].totalXPM), true);
			if (players[i].totalXPM < 0 || players[i].totalXPM > 10000)
				System.out.println("TotalXPM error");
			writeToFile(Integer.toString(players[i].totalLH), true);
			if (players[i].totalLH < 0 || players[i].totalLH > 15000)
				System.out.println("TotalLH error");
			writeToFile(Integer.toString(players[i].towersDestroyed), true);
			if (players[i].towersDestroyed < 0 || players[i].towersDestroyed > 12)
				System.out.println("TowersDestroyed error");
			writeToFile(Integer.toString(players[i].towersDenied), true);
			if (players[i].towersDenied < 0 || players[i].towersDenied > 12)
				System.out.println("TowersDenied error");
			writeToFile(Integer.toString(players[i].roshanKills), true);
			if (players[i].roshanKills < 0 || players[i].roshanKills > 10)
				System.out.println("RoshanKills error");
			writeToFile(Integer.toString(players[i].goldForKills), true);
			if (players[i].goldForKills < 0 || players[i].goldForKills > 100000)
				System.out.println("GoldForKills error");
			writeToFile(Integer.toString(players[i].goldFed), true);
			if (players[i].goldFed < 0 || players[i].goldFed > 100000)
				System.out.println("GoldFed error");
			writeToFile(Integer.toString(players[i].goldLost), true);
			if (players[i].goldLost < 0 || players[i].goldLost > 100000)
				System.out.println("GoldLost error");
			writeToFile(Integer.toString(players[i].totalGold), true);
			if (players[i].totalGold < 0 || players[i].totalGold > 100000)
				System.out.println("TotalGold error");
			writeToFile(Integer.toString(players[i].observerWardsPlaced), true);
			if (players[i].observerWardsPlaced < 0 || players[i].observerWardsPlaced > 50)
				System.out.println("ObserwerWardsPlaced error");
			writeToFile(Integer.toString(players[i].observerWardsDestroyed), true);
			if (players[i].observerWardsDestroyed < 0 || players[i].observerWardsDestroyed > 50)
				System.out.println("ObserwerWardsDestroyed error");
			writeToFile(Integer.toString(players[i].sentryWardsPlaced), true);
			if (players[i].sentryWardsPlaced < 0 || players[i].sentryWardsPlaced > 50)
				System.out.println("SentryWardsPlaced error");
			writeToFile(Integer.toString(players[i].sentryWardsDestroyed), true);
			if (players[i].sentryWardsDestroyed < 0 || players[i].sentryWardsDestroyed > 50)
				System.out.println("SentryWardsDestroyed error");
			writeToFile(Integer.toString(players[i].dustHits), true);
			if (players[i].dustHits < 0 || players[i].dustHits > 50)
				System.out.println("DustHits error");
			writeToFile(Integer.toString(players[i].dustAccuracy), true);
			if (players[i].dustAccuracy < 0 || players[i].dustAccuracy > 100)
				System.out.println("DustAccuracy error");
			writeToFile(Integer.toString(players[i].smokeHits), true);
			if (players[i].smokeHits < 0 || players[i].smokeHits > 20)
				System.out.println("SmokeHits error");
			writeToFile(Integer.toString(players[i].smokeTotalHeroes), true);
			if (players[i].smokeTotalHeroes < 0 || players[i].smokeTotalHeroes > 100)
				System.out.println("SmokeTotalHeroes error");
			writeToFile(Integer.toString(players[i].gemTimeCarried), false);
			if (players[i].gemTimeCarried < 0 || players[i].gemTimeCarried > 10000)
				System.out.println("GemTimeCarried error");
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
			}
			if (i != 9)
				writeToFile("||", false);
		}
		//KillEvents
		writeToFile("##", false);
		writeToFile(Integer.toString(killEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < killEvent.size(); i++)
		{
			writeToFile(Float.toString(killEvent.get(i).x), true);
			writeToFile(Float.toString(killEvent.get(i).y), true);
			writeToFile(Integer.toString(killEvent.get(i).second), true);
			writeToFile(Integer.toString(killEvent.get(i).dier), true);
			for (int j = 0; j < 5; j++)
			{
				if (j != 4)
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), true);
				else
					writeToFile(Integer.toString(killEvent.get(i).killers[j]), false);
			}

			if (i != killEvent.size() - 1)
				writeToFile("**", false);
		}
		//WardEvent
		writeToFile("##", false);
		writeToFile(Integer.toString(wardEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < wardEvent.size(); i++)
		{
			writeToFile(Float.toString(wardEvent.get(i).x), true);
			writeToFile(Float.toString(wardEvent.get(i).y), true);
			writeToFile(Integer.toString(wardEvent.get(i).second), true);
			writeToFile(Integer.toString(wardEvent.get(i).lifeTime), false);
			if (i != wardEvent.size() - 1)
				writeToFile("**", false);
		}
		//GlyphEvent
		writeToFile("##", false);
		writeToFile(Integer.toString(glyphEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < glyphEvent.size(); i++)
		{
			writeToFile(Integer.toString(glyphEvent.get(i).side), true);
			writeToFile(Integer.toString(glyphEvent.get(i).second), false);
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
			writeToFile(Integer.toString(buyBackEvent.get(i).goldCost), false);
			if (i != buyBackEvent.size() - 1)
				writeToFile("**", false);
		}
		//TowerEvent
		writeToFile("##", false);
		writeToFile(Integer.toString(towerEvent.size()), false);
		writeToFile("**", false);
		for (int i = 0; i < towerEvent.size(); i++)
		{
			writeToFile(towerEvent.get(i).whoDestroy, true);
			writeToFile(Integer.toString(towerEvent.get(i).second), true);
			writeToFile(Integer.toString(towerEvent.get(i).tierLevel), false);
			if (i != towerEvent.size() - 1)
				writeToFile("**", false);
		}
		writeToFile("\n", false);
	}

	void makeZeros(Team [] teams,Player[] players,Match match)
	{
		for (int i = 0; i <teams.length ; i++)
		{
			teams[i].teamZeros();
		}
		for (int i = 0; i <players.length ; i++)
		{
			players[i].playerZeros();
		}
		match.matchZeros();
	}
	void writeToFile(String whatToWrite, Boolean giveSeparator)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("files/Matches.txt", true))))
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
