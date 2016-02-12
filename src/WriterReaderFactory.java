import MatchInfo.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriterReaderFactory
{
	void writeInfoFromPlayers(Player[] players)
	{

	}

	void writeInfoFromTeams(Team[] teams)
	{

	}

	void writeInfoFromMatch(Match match)
	{

	}

	void writeEvents(WardEvent wardEvent, TowerEvent towerEvent, KillEvent killEvent, GlyphEvent glyphEvent, BuyBackEvent buyBackEvent)
	{

	}

	void writeMatchInfoToFile(Player[] players, Team[] teams, Match match, ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent)
	{
		//Match
		writeToFile(match.id, true);
		writeToFile(match.date, true);
		writeToFile(Integer.toString(match.matchTime), true);
		writeToFile(match.team1Id, true);
		writeToFile(match.team2Id, true);
		writeToFile(Integer.toString(match.leagueId), true);
		writeToFile(match.leagueName, true);
		writeToFile(Boolean.toString(match.winRadiant), true);
		writeToFile(Boolean.toString(match.firstBloodRadiant), true);
		writeToFile(Boolean.toString(match.first10KillsRadiant), true);
		writeToFile(Boolean.toString(match.firstRoshanRadiant), true);
		writeToFile(Integer.toString(match.FBTime), true);
		writeToFile(Integer.toString(match.F10KTime), true);
		writeToFile(Integer.toString(match.FRoshanTime), false);
		writeToFile("##", false);
		//Team
		for (int i = 0; i < 2; i++)
		{
			writeToFile(teams[i].id, true);
			writeToFile(teams[i].name, true);
			//TODO  writeToFile(Integer.toString(teams[i].rating),true);
			writeToFile(Integer.toString(teams[i].kills), true);
			writeToFile(Integer.toString(teams[i].deaths), true);
			writeToFile(Integer.toString(teams[i].assists), true);
			writeToFile(Integer.toString(teams[i].partisipation), true);
			writeToFile(Integer.toString(teams[i].heroHeal), true);
			writeToFile(Integer.toString(teams[i].heroDamage), true);
			writeToFile(Integer.toString(teams[i].towerDamage), true);
			writeToFile(Integer.toString(teams[i].totalGPM), true);
			writeToFile(Integer.toString(teams[i].totalXPM), true);
			writeToFile(Integer.toString(teams[i].totalLH), true);
			writeToFile(Integer.toString(teams[i].totalDenies), true);
			writeToFile(Integer.toString(teams[i].towersDestroyed), true);
			writeToFile(Integer.toString(teams[i].towersDenied), true);
			writeToFile(Integer.toString(teams[i].roshanKills), true);
			writeToFile(Integer.toString(teams[i].goldForKills), true);
			writeToFile(Integer.toString(teams[i].goldFed), true);
			writeToFile(Integer.toString(teams[i].goldLost), true);
			writeToFile(Integer.toString(teams[i].totalGold), true);
			writeToFile(Integer.toString(teams[i].observerWardsPlaced), true);
			writeToFile(Integer.toString(teams[i].observerWardsDestroyed), true);
			writeToFile(Integer.toString(teams[i].sentryWardsPlaced), true);
			writeToFile(Integer.toString(teams[i].sentryWardsDestroyed), true);
			writeToFile(Integer.toString(teams[i].dustHits), true);
			writeToFile(Integer.toString(teams[i].dustAccuracy), true);
			writeToFile(Integer.toString(teams[i].smokeHits), true);
			writeToFile(Integer.toString(teams[i].smokeTotalHeroes), true);
			writeToFile(Integer.toString(teams[i].gemsBought), true);
			writeToFile(Integer.toString(teams[i].gemsDropped), true);
			writeToFile(Integer.toString(teams[i].gemTimeCarried), false);
			//GPM
			writeToFile("**", false);
			writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), true);
				else
					writeToFile(Integer.toString(teams[i].minuteGPM[j]), false);
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
			//TODO writeToFile(Integer.toString(players[i].level),true);
			writeToFile(Integer.toString(players[i].kills), true);
			writeToFile(Integer.toString(players[i].deaths), true);
			writeToFile(Integer.toString(players[i].assists), true);
			writeToFile(Integer.toString(players[i].partisipation), true);
			writeToFile(Integer.toString(players[i].heroHeal), true);
			writeToFile(Integer.toString(players[i].heroDamage), true);
			writeToFile(Integer.toString(players[i].towerDamage), true);
			writeToFile(Integer.toString(players[i].totalGPM), true);
			writeToFile(Integer.toString(players[i].totalXPM), true);
			writeToFile(Integer.toString(players[i].totalLH), true);
			writeToFile(Integer.toString(players[i].towersDestroyed), true);
			writeToFile(Integer.toString(players[i].towersDenied), true);
			writeToFile(Integer.toString(players[i].roshanKills), true);
			writeToFile(Integer.toString(players[i].goldForKills), true);
			writeToFile(Integer.toString(players[i].goldFed), true);
			writeToFile(Integer.toString(players[i].goldLost), true);
			writeToFile(Integer.toString(players[i].totalGold), true);
			writeToFile(Integer.toString(players[i].observerWardsPlaced), true);
			writeToFile(Integer.toString(players[i].observerWardsDestroyed), true);
			writeToFile(Integer.toString(players[i].sentryWardsPlaced), true);
			writeToFile(Integer.toString(players[i].sentryWardsDestroyed), true);
			writeToFile(Integer.toString(players[i].dustHits), true);
			writeToFile(Integer.toString(players[i].dustAccuracy), true);
			writeToFile(Integer.toString(players[i].smokeHits), true);
			writeToFile(Integer.toString(players[i].smokeTotalHeroes), true);
			writeToFile(Integer.toString(players[i].gemTimeCarried), false);
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
		for (int i = 0; i < glyphEvent.size(); i++)
		{
			writeToFile(Integer.toString(glyphEvent.get(i).side), true);
			writeToFile(Integer.toString(glyphEvent.get(i).second), false);
			if (i != glyphEvent.size() - 1)
				writeToFile("**", false);
		}
		//BuyBackEvent
		writeToFile("##", false);
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


	public WriterReaderFactory()
	{

	}
}
