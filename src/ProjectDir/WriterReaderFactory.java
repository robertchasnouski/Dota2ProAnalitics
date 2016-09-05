package ProjectDir;

import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class WriterReaderFactory
{

	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	Boolean writeMatchInfoToFile(Player[] players, Team[] teams, Match match, ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent, ArrayList<RoshanEvent> roshanEvent) throws IOException
	{
		System.out.print("Don't terminate program. Wait...");
		boolean error = false;
		//Match
		String writeString = "";
		writeString += match.id + ";";
		//writeToFile(match.id, true);
		if (match.id.length() != 10)
		{
			System.out.println("MatchID Error");
			error = true;
		}
		writeString += match.date + ";";
		//writeToFile(match.date, true);
		if (match.date.length() != 10)
		{
			error = true;
			System.out.println("MatchDate Error");
		}
		writeString += Integer.toString(match.matchTime) + ";";
		//writeToFile(Integer.toString(match.matchTime), true);
		if (match.matchTime < 10 || match.matchTime > 150)
		{
			error = true;
			System.out.println("MatchTime Error");
		}
		writeString += match.team1Id + ";";
		writeString += match.team2Id + ";";
		writeString += Integer.toString(match.leagueId) + ";";
		writeString += match.leagueName + ";";
		writeString += Boolean.toString(match.winRadiant) + ";";
		writeString += Boolean.toString(match.firstBloodRadiant) + ";";
		writeString += Boolean.toString(match.first10KillsRadiant) + ";";
		writeString += Boolean.toString(match.firstRoshanRadiant) + ";";
		writeString += Integer.toString(match.FBTime) + ";";
		/*
		writeToFile(match.team1Id, true);
		writeToFile(match.team2Id, true);
		writeToFile(Integer.toString(match.leagueId), true);
		writeToFile(match.leagueName, true);
		writeToFile(Boolean.toString(match.winRadiant), true);
		writeToFile(Boolean.toString(match.firstBloodRadiant), true);
		writeToFile(Boolean.toString(match.first10KillsRadiant), true);
		writeToFile(Boolean.toString(match.firstRoshanRadiant), true);
		writeToFile(Integer.toString(match.FBTime), true);
		*/
		if ((match.FBTime > match.matchTime * 60 || match.FBTime < -90) && match.FBTime != 9999)
		{
			error = true;
			System.out.println("FBTime error");
		}
		writeString += Integer.toString(match.F10KTime) + ";";
		//writeToFile(Integer.toString(match.F10KTime), true);
		if ((match.F10KTime > match.matchTime * 60 || match.F10KTime < -90) && match.F10KTime != 9999)
		{
			error = true;
			System.out.println("F10KTime error");
		}
		writeString += Integer.toString(match.FRoshanTime);
		//writeToFile(Integer.toString(match.FRoshanTime), false);
		if ((match.FRoshanTime > match.matchTime * 60 || match.FRoshanTime < -90) && match.FRoshanTime != 9999)
		{
			System.out.println("FRTime error");
		}
		writeString += "##";
		//writeToFile("##", false);
		//Team
		for (int i = 0; i < 2; i++)
		{
			writeString += teams[i].id + ";";
			//writeToFile(teams[i].id, true);
			teams[i].name = teams[i].name.replaceAll(";", "");
			writeString += teams[i].name + ";";
			writeString += "1000" + ";";
			writeString += Integer.toString(teams[i].kills) + ";";
			//writeToFile(teams[i].name, true);
			//writeToFile("1000", true);
			//writeToFile(Integer.toString(teams[i].kills), true);
			if (teams[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(teams[i].deaths) + ";";
			//writeToFile(Integer.toString(teams[i].deaths), true);
			if (teams[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(teams[i].assists) + ";";
			//writeToFile(Integer.toString(teams[i].assists), true);
			if (teams[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			if (teams[i].partisipation < 0 || teams[i].partisipation > 100)
			{

				teams[i].partisipation = 100;
				System.out.print("Partisipation error but fixed");
			}
			writeString += Integer.toString(teams[i].partisipation) + ";";
			//writeToFile(Integer.toString(teams[i].partisipation), true);

			writeString += Integer.toString(teams[i].heroHeal) + ";";
			//writeToFile(Integer.toString(teams[i].heroHeal), true);
			if (teams[i].heroHeal < 0 || teams[i].heroHeal > 100000)
			{
				error = true;
				System.out.println("HeroHeal error");
			}
			writeString += Integer.toString(teams[i].heroDamage) + ";";
			//writeToFile(Integer.toString(teams[i].heroDamage), true);
			if (teams[i].heroDamage < 0 || teams[i].heroDamage > 250000)
			{
				error = true;
				System.out.println("HeroDamage error");
			}
			writeString += Integer.toString(teams[i].towerDamage) + ";";
			//writeToFile(Integer.toString(teams[i].towerDamage), true);
			if (teams[i].towerDamage < 0 || teams[i].towerDamage > 100000)
			{
				error = true;
				System.out.println("TowerDamage error");
			}
			writeString += Integer.toString(teams[i].totalGPM) + ";";
			//writeToFile(Integer.toString(teams[i].totalGPM), true);
			if (teams[i].totalGPM < 50 || teams[i].totalGPM > 12000)
			{
				error = true;
				System.out.println("TotalGPM team error");
			}
			writeString += Integer.toString(teams[i].totalXPM) + ";";
			//writeToFile(Integer.toString(teams[i].totalXPM), true);
			if (teams[i].totalXPM < 0 || teams[i].totalXPM > 10000)
			{
				error = true;
				System.out.println("TotalXPM error");
			}
			writeString += Integer.toString(teams[i].totalLH) + ";";
			//writeToFile(Integer.toString(teams[i].totalLH), true);
			if (teams[i].totalLH < 0 || teams[i].totalLH > 15000)
			{
				error = true;
				System.out.println("TotalLH error");
			}
			writeString += Integer.toString(teams[i].totalDenies) + ";";
			//writeToFile(Integer.toString(teams[i].totalDenies), true);
			if (teams[i].totalDenies < 0 || teams[i].totalDenies > 2000)
			{
				error = true;
				System.out.println("TotalDenies error");
			}
			writeString += Integer.toString(teams[i].towersDestroyed) + ";";
			//writeToFile(Integer.toString(teams[i].towersDestroyed), true);
			if (teams[i].towersDestroyed < 0 || teams[i].towersDestroyed > 12)
			{
				error = true;
				System.out.println("TowersDestroyed error");
			}
			writeString += Integer.toString(teams[i].towersDenied) + ";";
			//writeToFile(Integer.toString(teams[i].towersDenied), true);
			if (teams[i].towersDenied < 0 || teams[i].towersDenied > 12)
			{
				error = true;
				System.out.println("TowersDenied error");
			}
			writeString += Integer.toString(teams[i].roshanKills) + ";";
			//writeToFile(Integer.toString(teams[i].roshanKills), true);
			if (teams[i].roshanKills < 0 || teams[i].roshanKills > 10)
			{
				error = true;
				System.out.println("RoshanKills error");
			}
			writeString += Integer.toString(teams[i].goldForKills) + ";";
			//writeToFile(Integer.toString(teams[i].goldForKills), true);
			if (teams[i].goldForKills < 0 || teams[i].goldForKills > 100000)
			{
				error = true;
				System.out.println("GoldForKills error");
			}
			writeString += Integer.toString(teams[i].goldFed) + ";";
			//writeToFile(Integer.toString(teams[i].goldFed), true);
			if (teams[i].goldFed < 0 || teams[i].goldFed > 100000)
			{
				error = true;
				System.out.println("GoldFed error");
			}
			writeString += Integer.toString(teams[i].goldLost) + ";";
			//writeToFile(Integer.toString(teams[i].goldLost), true);
			if (teams[i].goldLost < 0 || teams[i].goldLost > 100000)
			{
				error = true;
				System.out.println("GoldLost error");
			}
			writeString += Integer.toString(teams[i].totalGold) + ";";
			//writeToFile(Integer.toString(teams[i].totalGold), true);
			if (teams[i].totalGold < 0 || teams[i].totalGold > 250000)
			{
				error = true;
				System.out.println("TotalGold error");
			}
			writeString += Integer.toString(teams[i].observerWardsPlaced) + ";";
			//writeToFile(Integer.toString(teams[i].observerWardsPlaced), true);
			if (teams[i].observerWardsPlaced < 0 || teams[i].observerWardsPlaced > 60)
			{
				error = true;
				System.out.println("ObserwerWardsPlaced error");
			}
			writeString += Integer.toString(teams[i].observerWardsDestroyed) + ";";
			//writeToFile(Integer.toString(teams[i].observerWardsDestroyed), true);
			if (teams[i].observerWardsDestroyed < 0 || teams[i].observerWardsDestroyed > 50)
			{
				error = true;
				System.out.println("ObserwerWardsDestroyed error");
			}
			writeString += Integer.toString(teams[i].sentryWardsPlaced) + ";";
			//writeToFile(Integer.toString(teams[i].sentryWardsPlaced), true);
			writeString += Integer.toString(teams[i].sentryWardsDestroyed) + ";";
			//writeToFile(Integer.toString(teams[i].sentryWardsDestroyed), true);
			writeString += Integer.toString(teams[i].dustHits) + ";";
			//writeToFile(Integer.toString(teams[i].dustHits), true);
			if (teams[i].dustHits < 0 || teams[i].dustHits > 50)
			{
				error = true;
				System.out.println("DustHits error");
			}
			writeString += Integer.toString(teams[i].dustAccuracy) + ";";
			//writeToFile(Integer.toString(teams[i].dustAccuracy), true);
			if (teams[i].dustAccuracy < 0 || teams[i].dustAccuracy > 100)
			{
				error = true;
				System.out.println("DustAccuracy error");
			}
			writeString += Integer.toString(teams[i].smokeHits) + ";";
			//writeToFile(Integer.toString(teams[i].smokeHits), true);
			if (teams[i].smokeHits < 0 || teams[i].smokeHits > 20)
			{
				error = true;
				System.out.println("SmokeHits error");
			}
			writeString += Integer.toString(teams[i].smokeTotalHeroes) + ";";
			//writeToFile(Integer.toString(teams[i].smokeTotalHeroes), true);
			if (teams[i].smokeTotalHeroes < 0 || teams[i].smokeTotalHeroes > 100)
			{
				error = true;
				System.out.println("SmokeTotalHeroes error");
			}
			writeString += Integer.toString(teams[i].gemsBought) + ";";
			//writeToFile(Integer.toString(teams[i].gemsBought), true);
			if (teams[i].gemsBought < 0 || teams[i].gemsBought > 10)
			{
				error = true;
				System.out.println("GemsBought error");
			}
			writeString += Integer.toString(teams[i].gemsDropped) + ";";
			//writeToFile(Integer.toString(teams[i].gemsDropped), true);
			if (teams[i].gemsDropped < 0 || teams[i].gemsDropped > 20)
			{
				error = true;
				System.out.println("GemsDropped error");
			}
			writeString += Integer.toString(teams[i].gemTimeCarried);
			//writeToFile(Integer.toString(teams[i].gemTimeCarried), false);
			if (teams[i].gemTimeCarried < 0 || teams[i].gemTimeCarried > 3600)
			{
				error = true;
				System.out.println("GemTimeCarried error");
			}

			//GPM
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(teams[i].minuteGPM[j]) + ";";
					//writeToFile(Integer.toString(teams[i].minuteGPM[j]), true);
				} else
				{
					writeString += Integer.toString(teams[i].minuteGPM[j]);
					//writeToFile(Integer.toString(teams[i].minuteGPM[j]), false);
				}
				if (teams[i].minuteGPM[j] < -20000 || teams[i].minuteGPM[j] > 20000)
				{

					error = true;
					System.out.println("minuteGPM  Team error");
				}
			}
			//XPM
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(teams[i].minuteXPM[j]) + ";";
					//writeToFile(Integer.toString(teams[i].minuteXPM[j]), true);
				} else
				{
					writeString += Integer.toString(teams[i].minuteXPM[j]);
					//writeToFile(Integer.toString(teams[i].minuteXPM[j]), false);
				}
				if (teams[i].minuteXPM[j] < 0 || teams[i].minuteXPM[j] > 20000)
				{
					error = true;
					System.out.println("minuteXPM error");
				}
			}
			//LH
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(teams[i].perMinuteLastHits[j]) + ";";
					//	writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), true);
				} else
				{
					writeString += Integer.toString(teams[i].perMinuteLastHits[j]);
					//writeToFile(Integer.toString(teams[i].perMinuteLastHits[j]), false);
				}
				if (teams[i].perMinuteLastHits[j] < 0 || teams[i].perMinuteLastHits[j] > 100)
				{
					error = true;
					System.out.println("perMinuteLastHits error");
				}
			}
			if (i == 0)
			{
				writeString += "||";
				//writeToFile("||", false);
			}
		}
		writeString += "##";
		//writeToFile("##", false);
		//Player
		for (int i = 0; i < 10; i++)
		{
			writeString += players[i].playerId + ";";
			//writeToFile(players[i].playerId, true);
			writeString += players[i].hero + ";";
			//writeToFile(players[i].hero, true);
			writeString += Integer.toString(players[i].role) + ";";
			//writeToFile(Integer.toString(players[i].role), true);
			writeString += Integer.toString(players[i].level) + ";";
			//writeToFile(Integer.toString(players[i].level), true);
			writeString += Integer.toString(players[i].kills) + ";";
			//writeToFile(Integer.toString(players[i].kills), true);
			if (players[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(players[i].deaths) + ";";
			//writeToFile(Integer.toString(players[i].deaths), true);
			if (players[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(players[i].assists) + ";";
			//writeToFile(Integer.toString(players[i].assists), true);
			if (players[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			if (players[i].partisipation < 0 || players[i].partisipation > 100)
			{
				players[i].partisipation = 100;
				System.out.print("Partisipation error but fixed");
			}
			writeString += Integer.toString(players[i].partisipation) + ";";
			//writeToFile(Integer.toString(players[i].partisipation), true);

			writeString += Integer.toString(players[i].heroHeal) + ";";
			//writeToFile(Integer.toString(players[i].heroHeal), true);
			if (players[i].heroHeal < 0 || players[i].heroHeal > 100000)
			{
				error = true;
				System.out.println("HeroHeal error");
			}
			writeString += Integer.toString(players[i].heroDamage) + ";";
			//writeToFile(Integer.toString(players[i].heroDamage), true);
			if (players[i].heroDamage < 0 || players[i].heroDamage > 250000)
			{
				error = true;
				System.out.println("HeroDamage error");
			}
			writeString += Integer.toString(players[i].towerDamage) + ";";
			//writeToFile(Integer.toString(players[i].towerDamage), true);
			if (players[i].towerDamage < 0 || players[i].towerDamage > 100000)
			{
				error = true;
				System.out.println("TowerDamage error");
			}
			writeString += Integer.toString(players[i].totalGPM) + ";";
			//writeToFile(Integer.toString(players[i].totalGPM), true);
			if (players[i].totalGPM < 50 || players[i].totalGPM > 10000)
			{
				error = true;
				System.out.println("TotalGPM player error");
			}
			writeString += Integer.toString(players[i].totalXPM) + ";";
			//writeToFile(Integer.toString(players[i].totalXPM), true);
			if (players[i].totalXPM < 0 || players[i].totalXPM > 10000)
			{
				error = true;
				System.out.println("TotalXPM error");
			}
			writeString += Integer.toString(players[i].totalLH) + ";";
			//writeToFile(Integer.toString(players[i].totalLH), true);
			if (players[i].totalLH < 0 || players[i].totalLH > 15000)
			{
				error = true;
				System.out.println("TotalLH error");
			}
			writeString += Integer.toString(players[i].towersDestroyed) + ";";
			//writeToFile(Integer.toString(players[i].towersDestroyed), true);
			if (players[i].towersDestroyed < 0 || players[i].towersDestroyed > 12)
			{
				error = true;
				System.out.println("TowersDestroyed error");
			}
			writeString += Integer.toString(players[i].towersDenied) + ";";
			//writeToFile(Integer.toString(players[i].towersDenied), true);
			if (players[i].towersDenied < 0 || players[i].towersDenied > 12)
			{
				error = true;
				System.out.println("TowersDenied error");
			}
			writeString += Integer.toString(players[i].roshanKills) + ";";
			//writeToFile(Integer.toString(players[i].roshanKills), true);
			if (players[i].roshanKills < 0 || players[i].roshanKills > 10)
			{
				error = true;
				System.out.println("RoshanKills error");
			}
			writeString += Integer.toString(players[i].goldForKills) + ";";
			//writeToFile(Integer.toString(players[i].goldForKills), true);
			if (players[i].goldForKills < 0 || players[i].goldForKills > 100000)
			{
				error = true;
				System.out.println("GoldForKills error");
			}
			writeString += Integer.toString(players[i].goldFed) + ";";
			//writeToFile(Integer.toString(players[i].goldFed), true);
			if (players[i].goldFed < 0 || players[i].goldFed > 100000)
			{
				error = true;
				System.out.println("GoldFed error");
			}
			writeString += Integer.toString(players[i].goldLost) + ";";
			//writeToFile(Integer.toString(players[i].goldLost), true);
			if (players[i].goldLost < 0 || players[i].goldLost > 100000)
			{
				error = true;
				System.out.println("GoldLost error");
			}
			writeString += Integer.toString(players[i].totalGold) + ";";
			//writeToFile(Integer.toString(players[i].totalGold), true);
			if (players[i].totalGold < 0 || players[i].totalGold > 100000)
			{
				error = true;
				System.out.println("TotalGold error");
			}
			writeString += Integer.toString(players[i].observerWardsPlaced) + ";";
			//writeToFile(Integer.toString(players[i].observerWardsPlaced), true);
			if (players[i].observerWardsPlaced < 0 || players[i].observerWardsPlaced > 50)
			{
				error = true;
				System.out.println("ObserwerWardsPlaced error");
			}
			writeString += Integer.toString(players[i].observerWardsDestroyed) + ";";
			//writeToFile(Integer.toString(players[i].observerWardsDestroyed), true);
			if (players[i].observerWardsDestroyed < 0 || players[i].observerWardsDestroyed > 50)
			{
				error = true;
				System.out.println("ObserwerWardsDestroyed error");
			}
			writeString += Integer.toString(players[i].sentryWardsPlaced) + ";";
			//writeToFile(Integer.toString(players[i].sentryWardsPlaced), true);
			if (players[i].sentryWardsPlaced < 0 || players[i].sentryWardsPlaced > 50)
			{
				error = true;
				System.out.println("SentryWardsPlaced error");
			}
			writeString += Integer.toString(players[i].sentryWardsDestroyed) + ";";
			//writeToFile(Integer.toString(players[i].sentryWardsDestroyed), true);
			if (players[i].sentryWardsDestroyed < 0 || players[i].sentryWardsDestroyed > 50)
			{
				error = true;
				System.out.println("SentryWardsDestroyed error");
			}
			writeString += Integer.toString(players[i].dustHits) + ";";
			//writeToFile(Integer.toString(players[i].dustHits), true);
			if (players[i].dustHits < 0 || players[i].dustHits > 50)
			{
				error = true;
				System.out.println("DustHits error");
			}
			writeString += Integer.toString(players[i].dustAccuracy) + ";";
			//writeToFile(Integer.toString(players[i].dustAccuracy), true);
			if (players[i].dustAccuracy < 0 || players[i].dustAccuracy > 100)
			{
				error = true;
				System.out.println("DustAccuracy error");
			}
			writeString += Integer.toString(players[i].smokeHits) + ";";
			//writeToFile(Integer.toString(players[i].smokeHits), true);
			if (players[i].smokeHits < 0 || players[i].smokeHits > 20)
			{
				error = true;
				System.out.println("SmokeHits error");
			}
			writeString += Integer.toString(players[i].smokeTotalHeroes) + ";";
			//writeToFile(Integer.toString(players[i].smokeTotalHeroes), true);
			if (players[i].smokeTotalHeroes < 0 || players[i].smokeTotalHeroes > 100)
			{
				error = true;
				System.out.println("SmokeTotalHeroes error");
			}
			writeString += Integer.toString(players[i].gemTimeCarried);
			//writeToFile(Integer.toString(players[i].gemTimeCarried), false);
			if (players[i].gemTimeCarried < 0 || players[i].gemTimeCarried > 5000)
			{
				error = true;
				System.out.println("GemTimeCarried error");
			}

			//Items
			writeString += "**";
			//writeToFile("**", false);
			for (int j = 0; j < 6; j++)
			{
				if (j != 5)
				{
					writeString += players[i].item[j] + ";";
					//	writeToFile(players[i].item[j], true);
				} else
				{
					writeString += players[i].item[j];
					//	writeToFile(players[i].item[j], false);
				}
			}
			//GPM
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (players[i].minuteGPM[j] < -5000 || players[i].minuteGPM[j] > 5000)
				{
					players[i].minuteGPM[j] = 800;
					System.out.print("MinuteGPM Player error but fixed");
				}
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(players[i].minuteGPM[j]) + ";";
					//writeToFile(Integer.toString(players[i].minuteGPM[j]), true);
				} else
				{
					writeString += Integer.toString(players[i].minuteGPM[j]);
					//writeToFile(Integer.toString(players[i].minuteGPM[j]), false);
				}
			}
			//XPM
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (players[i].minuteXPM[j] < -100 || players[i].minuteXPM[j] > 10000)
				{
					players[i].minuteXPM[j] = 800;
					System.out.print("MinuteXPM Player error but fixed");
				}
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(players[i].minuteXPM[j]) + ";";
					//writeToFile(Integer.toString(players[i].minuteXPM[j]), true);
				} else
				{
					writeString += Integer.toString(players[i].minuteXPM[j]);
					//writeToFile(Integer.toString(players[i].minuteXPM[j]), false);
				}

			}
			//LH
			writeString += "**";
			//writeToFile("**", false);
			writeString += Integer.toString(match.universalX) + ";";
			//writeToFile(Integer.toString(match.universalX), true);
			for (int j = 0; j < match.universalX; j++)
			{
				if (j != match.universalX - 1)
				{
					writeString += Integer.toString(players[i].perMinuteLastHits[j]) + ";";
					//writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), true);
				} else
				{
					writeString += Integer.toString(players[i].perMinuteLastHits[j]);
					//writeToFile(Integer.toString(players[i].perMinuteLastHits[j]), false);
				}
				if (players[i].perMinuteLastHits[j] < 0 || players[i].perMinuteLastHits[j] > 100)
				{
					error = true;
					System.out.println("MinuteLH error");
				}
			}
			if (i != 9)
			{
				writeString += "||";
				//writeToFile("||", false);
			}
		}

		//KillEvents
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(killEvent.size());
		//writeToFile(Integer.toString(killEvent.size()), false);
		if (killEvent.size() < 10)
		{
			error = true;
			System.out.println("Only 10 kills? Error!");
		}
		writeString += "**";
		//writeToFile("**", false);

		for (int i = 0; i < killEvent.size(); i++)
		{
			writeString += Float.toString(killEvent.get(i).x) + ";";
			//writeToFile(Float.toString(killEvent.get(i).x), true);
			if (killEvent.get(i).x < 0.0f || killEvent.get(i).x > 100.0f)
			{
				error = true;
				System.out.println("Kill X error");
			}
			writeString += Float.toString(killEvent.get(i).y) + ";";
			//writeToFile(Float.toString(killEvent.get(i).y), true);
			if (killEvent.get(i).y < 0.0f || killEvent.get(i).y > 100.0f)
			{
				error = true;
				System.out.println("Kill Y error");
			}
			writeString += Integer.toString(killEvent.get(i).second) + ";";
			//writeToFile(Integer.toString(killEvent.get(i).second), true);
			if (killEvent.get(i).second < -90 || killEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Kill second error");
			}
			writeString += Integer.toString(killEvent.get(i).dier) + ";";
			//writeToFile(Integer.toString(killEvent.get(i).dier), true);
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
				{
					writeString += Integer.toString(killEvent.get(i).killers[j]) + ";";

					//writeToFile(Integer.toString(killEvent.get(i).killers[j]), true);
				} else
				{
					writeString += Integer.toString(killEvent.get(i).killers[j]);
					//writeToFile(Integer.toString(killEvent.get(i).killers[j]), false);
				}
				if (killEvent.get(i).killers[j] < 0 || killEvent.get(i).killers[j] > 10)
				{
					error = true;
					System.out.println("Kill Killers error");
				}
			}

			if (i != killEvent.size() - 1)
			{
				//writeToFile("**", false);
				writeString += "**";
			}
		}

		//WardEvent
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(wardEvent.size());
		//writeToFile(Integer.toString(wardEvent.size()), false);
		if (wardEvent.size() < 5)
		{
			error = true;
			System.out.println("Only 5 wards? Error!");
		}
		writeString += "**";
		//writeToFile("**", false);

		for (int i = 0; i < wardEvent.size(); i++)
		{
			writeString += Float.toString(wardEvent.get(i).x) + ";";
			//writeToFile(Float.toString(wardEvent.get(i).x), true);
			if (wardEvent.get(i).x < 0.0f || wardEvent.get(i).x > 100.0f)
			{
				error = true;
				System.out.println("Ward X error");
			}
			writeString += Float.toString(wardEvent.get(i).y) + ";";
			//writeToFile(Float.toString(wardEvent.get(i).y), true);
			if (wardEvent.get(i).y < 0.0f || wardEvent.get(i).y > 100.0f)
			{
				error = true;
				System.out.println("Ward Y error");
			}
			writeString += Integer.toString(wardEvent.get(i).second) + ";";
			//writeToFile(Integer.toString(wardEvent.get(i).second), true);
			if (wardEvent.get(i).second < -90 || wardEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Ward Second error");
			}
			writeString += Integer.toString(wardEvent.get(i).lifeTime) + ";";
			//writeToFile(Integer.toString(wardEvent.get(i).lifeTime), true);

			if (wardEvent.get(i).lifeTime < 0 || wardEvent.get(i).lifeTime > 420)
			{
				error = true;
				System.out.println("Ward Lifetime error");
			}
			writeString += Integer.toString(wardEvent.get(i).side);
			//writeToFile(Integer.toString(wardEvent.get(i).side), false);

			if (i != wardEvent.size() - 1)
			{
				//writeToFile("**", false);
				writeString += "**";
			}
		}

		//GlyphEvent
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(glyphEvent.size());
		//writeToFile(Integer.toString(glyphEvent.size()), false);
		writeString += "**";
		//writeToFile("**", false);

		for (int i = 0; i < glyphEvent.size(); i++)
		{
			writeString += Integer.toString(glyphEvent.get(i).side) + ";";
			//writeToFile(Integer.toString(glyphEvent.get(i).side), true);
			if (glyphEvent.get(i).side < 0 || glyphEvent.get(i).side > 2)
			{
				error = true;
				System.out.println("Glyph side error");
			}
			writeString += Integer.toString(glyphEvent.get(i).second);
			//writeToFile(Integer.toString(glyphEvent.get(i).second), false);
			if (glyphEvent.get(i).second < -90 || glyphEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Glyph second error");
			}
			if (i != glyphEvent.size() - 1)
			{
				writeString += "**";
				//writeToFile("**", false);
			}
		}

		//BuyBackEvent
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(buyBackEvent.size());
		//writeToFile(Integer.toString(buyBackEvent.size()), false);
		writeString += "**";
		//writeToFile("**", false);

		for (int i = 0; i < buyBackEvent.size(); i++)
		{
			writeString += buyBackEvent.get(i).whoBoughtBack + ";";
			//writeToFile(buyBackEvent.get(i).whoBoughtBack, true);
			writeString += Integer.toString(buyBackEvent.get(i).second) + ";";
			//writeToFile(Integer.toString(buyBackEvent.get(i).second), true);
			if (buyBackEvent.get(i).second < -90 || buyBackEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("BuyBackEvent second error");
			}
			writeString += Integer.toString(buyBackEvent.get(i).goldCost);
			//writeToFile(Integer.toString(buyBackEvent.get(i).goldCost), false);
			if (i != buyBackEvent.size() - 1)
			{
				//writeToFile("**", false);
				writeString += "**";
			}
		}

		//TowerEvent
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(towerEvent.size());
		//writeToFile(Integer.toString(towerEvent.size()), false);
		writeString += "**";
		//writeToFile("**", false);
		for (int i = 0; i < towerEvent.size(); i++)
		{
			writeString += towerEvent.get(i).whoDestroy + ";";
			//writeToFile(towerEvent.get(i).whoDestroy, true);
			writeString += Integer.toString(towerEvent.get(i).second) + ";";
			//writeToFile(Integer.toString(towerEvent.get(i).second), true);
			if (towerEvent.get(i).second < -90 || towerEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Tower second error");
			}
			writeString += Integer.toString(towerEvent.get(i).tierLevel) + ";";
			//writeToFile(Integer.toString(towerEvent.get(i).tierLevel), true);
			if (towerEvent.get(i).tierLevel < 1 || towerEvent.get(i).tierLevel > 4)
			{
				error = true;
				System.out.println("Tower level error");
			}
			writeString += towerEvent.get(i).line;
			//writeToFile(towerEvent.get(i).line, false);
			if (i != towerEvent.size() - 1)
			{
				writeString += "**";
				//writeToFile("**", false);
			}
		}
		writeString += "##";
		//writeToFile("##", false);
		writeString += Integer.toString(roshanEvent.size());
		//writeToFile(Integer.toString(roshanEvent.size()), false);
		writeString += "**";
		//writeToFile("**", false);
		for (int i = 0; i < roshanEvent.size(); i++)
		{
			writeString += roshanEvent.get(i).whoKill + ";";
			//	writeToFile(roshanEvent.get(i).whoKill, true);
			writeString += Integer.toString(roshanEvent.get(i).second);
			//writeToFile(Integer.toString(roshanEvent.get(i).second), false);
			if (roshanEvent.get(i).second < -90 || roshanEvent.get(i).second > (match.matchTime + 2) * 60)
			{
				error = true;
				System.out.println("Roshan second error");
			}
			if (i != roshanEvent.size() - 1)
			{
				writeString += "**";
				//writeToFile("**", false);
			}
		}


		if (error)
		{
			fileOperationsFactory.writeToFile("Match " + match.id + " error.", "files/Log.txt");
			/*String newString = "";
			String originalFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
			String[] lineByLine = originalFile.split("\n");
			for (int i = 0; i < lineByLine.length - 1; i++)
			{
				newString += lineByLine[i] + "\n";
			}
			fileOperationsFactory.cleanAndWriteToFile(newString, "files/TemporaryMatches.txt");*/
		} else
		{
			fileOperationsFactory.writeToFile(writeString, "files/TemporaryMatches.txt");
			//writeString += "\n";
			//writeToFile("\n", false);
		}

		if (error)
		{
			return false;
		} else
		{
			System.out.print("Successfully ended.\n");
			return true;
		}

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

	/*void writeToFile(String whatToWrite, Boolean giveSeparator)
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

	}*/

	public void cleanArrayLists(ArrayList<WardEvent> wardEvent, ArrayList<TowerEvent> towerEvent, ArrayList<KillEvent> killEvent, ArrayList<GlyphEvent> glyphEvent, ArrayList<BuyBackEvent> buyBackEvent, ArrayList<RoshanEvent> roshanEvent)
	{
		wardEvent.clear();
		towerEvent.clear();
		killEvent.clear();
		glyphEvent.clear();
		buyBackEvent.clear();
		roshanEvent.clear();
	}

	public WriterReaderFactory()
	{

	}

}
