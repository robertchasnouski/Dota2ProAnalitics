package ProjectDir;

import ProjectDir.Analitics.StringReader;
import ProjectDir.MatchInfo.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.util.ArrayList;

public class AverageDataFactory
{
	public Double avgMiderKM = 0.0;
	public Double avgMiderDM = 0.0;
	public Double avgMiderAM = 0.0;
	public Double avgMiderCreepsF10M = 0.0;
	public Double avgMiderHDM = 0.0;
	public Double avgMiderTDM = 0.0;
	public Double avgMiderGPM = 0.0;
	public Double avgMiderGfKGFDifference = 0.0;
	public Double avgMiderLHM = 0.0;


	public Double avgCarryKM = 0.0;
	public Double avgCarryDM = 0.0;
	public Double avgCarryAM = 0.0;
	public Double avgCarryGPM = 0.0;
	public Double avgCarryHDM = 0.0;
	public Double avgCarryTDM = 0.0;
	public Double avgCarryGfKGFDifference = 0.0;
	public Double avgCarryCreepsF15M = 0.0;
	public Double avgCarryLHM = 0.0;

	public Double avgSupportKM = 0.0;
	public Double avgSupportDM = 0.0;
	public Double avgSupportAM = 0.0;
	public Double avgSupportWLT;
	public Double avgSupportXPM=0.0;
	public Double avgSupportWDM = 0.0;
	public Double avgSupportPartisipate = 0.0;
	public Double avgSupportGPM = 0.0;
	public Double avgSupportHHM = 0.0;
	public Double avgSupportHDM = 0.0;

	public Double avgHardlinerKM = 0.0;
	public Double avgHardlinerDM = 0.0;
	public Double avgHardlinerAM = 0.0;
	public Double avgHardlinerDF10M = 0.0;
	public Double avgHardlinerXPMF10M = 0.0;
	public Double avgHardlinerHDM = 0.0;
	public Double avgHardlinerGPM = 0.0;
	public Double avgHardlinerPartisipate = 0.0;

	public Double avgJunglerKM = 0.0;
	public Double avgJunglerDM = 0.0;
	public Double avgJunglerAM = 0.0;
	public Double avgJunglerHDM = 0.0;
	public Double avgJunglerDF10M = 0.0;
	public Double avgJunglerGPM = 0.0;
	public Double avgJunglerPartisipate = 0.0;
	public Double avgJunglerTDM = 0.0;

	public Integer miderCounter = 0;
	public Integer carryCounter = 0;
	public Integer supportCounter = 0;
	public Integer hardlinerCounter = 0;
	public Integer junglerCounter = 0;


	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	StringReader stringReader = new StringReader();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();

	void getAveragePlayerData() throws IOException
	{
		String allMatchesFile = fileOperationsFactory.readFile("files/Matches.txt");
		String[] eachMatch = allMatchesFile.split("\n");
		Integer matchesCount = 50;
		if (eachMatch.length < 50)
			matchesCount = eachMatch.length;
		for (int i = eachMatch.length - 1; i > eachMatch.length - matchesCount - 1; i--)
		{
			String generalMatchString = stringReader.getMatchInfo(eachMatch[i]);
			String teamsMatchString = stringReader.getTeamsInfo(eachMatch[i]);
			String playersMatchString = stringReader.getPlayersInfo(eachMatch[i]);

			//System.out.println("Match ID:" + generalMatchString.split(";")[0]);
			Double matchTime = (double) Integer.parseInt(generalMatchString.split(";")[2]);

			String[] team1MatchInfo = teamsMatchString.split("\\|\\|")[0].split(";");
			String[] team2MatchInfo = teamsMatchString.split("\\|\\|")[1].split(";");

			String[] onePlayerString = playersMatchString.split("\\|\\|");

			//System.out.println("Match time:" + matchTime);


			for (int j = 0; j < 10; j++)
			{
				String[] onePlayerInfo = onePlayerString[j].split(";");
				String[] infoGPMXPMLH = onePlayerString[j].split("\\*\\*");
				String[] GPMArray = infoGPMXPMLH[2].split(";");
				String[] XPMArray = infoGPMXPMLH[3].split(";");
				String[] LHArray = infoGPMXPMLH[4].split(";");
				String killsString = stringReader.getKillEvents(eachMatch[i]);
				String[] eachKill = killsString.split("\\*\\*");

				//Mider
				if (onePlayerInfo[2].equals("1"))
				{
					miderCounter++;
					avgMiderKM += Integer.parseInt(onePlayerInfo[4]) / matchTime;
					avgMiderDM += Integer.parseInt(onePlayerInfo[5]) / matchTime;
					avgMiderAM += Integer.parseInt(onePlayerInfo[6]) / matchTime;
					avgMiderGPM += Integer.parseInt(onePlayerInfo[11]);
					avgMiderHDM += Integer.parseInt(onePlayerInfo[9]) / matchTime;
					avgMiderTDM += Integer.parseInt(onePlayerInfo[10]) / matchTime;
					avgMiderGfKGFDifference += Integer.parseInt(onePlayerInfo[17]) - Integer.parseInt(onePlayerInfo[19]);
					avgMiderCreepsF10M += Integer.parseInt(LHArray[1]) + Integer.parseInt(LHArray[2]) + Integer.parseInt(LHArray[3]) + Integer.parseInt(LHArray[4]) + Integer.parseInt(LHArray[5]) + Integer.parseInt(LHArray[6]) + Integer.parseInt(LHArray[7]) + Integer.parseInt(LHArray[8]) + Integer.parseInt(LHArray[9]) + Integer.parseInt(LHArray[10]);
					avgMiderLHM += Integer.parseInt(onePlayerInfo[13]) / matchTime;
				}
				//Carry
				if (onePlayerInfo[2].equals("2"))
				{
					carryCounter++;
					avgCarryKM += Integer.parseInt(onePlayerInfo[4]) / matchTime;
					avgCarryDM += Integer.parseInt(onePlayerInfo[5]) / matchTime;
					avgCarryAM += Integer.parseInt(onePlayerInfo[6]) / matchTime;
					avgCarryGfKGFDifference += Integer.parseInt(onePlayerInfo[17]) - Integer.parseInt(onePlayerInfo[19]);
					avgCarryGPM += Integer.parseInt(onePlayerInfo[11]);
					avgCarryHDM += Integer.parseInt(onePlayerInfo[9]) / matchTime;
					avgCarryTDM += Integer.parseInt(onePlayerInfo[10]) / matchTime;
					avgCarryLHM += Integer.parseInt(onePlayerInfo[13]) / matchTime;
					if (matchTime <= 15)
					{
						for (int k = 1; k < matchTime; k++)
						{
							avgCarryCreepsF15M += Integer.parseInt(LHArray[k]);
						}
					} else
					{
						for (int k = 1; k < 16; k++)
						{
							avgCarryCreepsF15M += Integer.parseInt(LHArray[k]);
						}
					}
				}
				//Support
				if (onePlayerInfo[2].equals("3"))
				{
					supportCounter++;
					avgSupportKM += Integer.parseInt(onePlayerInfo[4]) / matchTime;
					avgSupportDM += Integer.parseInt(onePlayerInfo[5]) / matchTime;
					avgSupportAM += Integer.parseInt(onePlayerInfo[6]) / matchTime;
					avgSupportGPM += Integer.parseInt(onePlayerInfo[11]);
					avgSupportXPM += Integer.parseInt(onePlayerInfo[12]);
					avgSupportPartisipate += Integer.parseInt(onePlayerInfo[7]);
					avgSupportWDM += Integer.parseInt(onePlayerInfo[23]) / matchTime;
					avgSupportHHM += Integer.parseInt(onePlayerInfo[8]) / matchTime;
					avgSupportHDM += Integer.parseInt(onePlayerInfo[9]) / matchTime;
				}
				//HardLiner
				if (onePlayerInfo[2].equals("4"))
				{
					hardlinerCounter++;
					avgHardlinerKM += Integer.parseInt(onePlayerInfo[4]) / matchTime;
					avgHardlinerDM += Integer.parseInt(onePlayerInfo[5]) / matchTime;
					avgHardlinerAM += Integer.parseInt(onePlayerInfo[6]) / matchTime;
					avgHardlinerGPM += Integer.parseInt(onePlayerInfo[11]);
					avgHardlinerPartisipate += Integer.parseInt(onePlayerInfo[7]);
					avgHardlinerHDM += Integer.parseInt(onePlayerInfo[9]) / matchTime;
					for (int k = 0; k < Integer.parseInt(eachKill[0]); k++)
					{
						String[] killInfo = eachKill[k + 1].split(";");
						int dier = j + 1;
						if (Integer.parseInt(killInfo[2]) < 600 && killInfo[3].equals(dier + ""))
						{
							avgHardlinerDF10M += 1;
						}
					}
					avgHardlinerXPMF10M += (double) Integer.parseInt(XPMArray[1]) + Integer.parseInt(XPMArray[2]) + Integer.parseInt(XPMArray[3]) + Integer.parseInt(XPMArray[4]) + Integer.parseInt(XPMArray[5]) + Integer.parseInt(XPMArray[6]) + Integer.parseInt(XPMArray[7]) + Integer.parseInt(XPMArray[8]) + Integer.parseInt(XPMArray[9]) + Integer.parseInt(XPMArray[10]);
				}
				//Jungler
				if (onePlayerInfo[2].equals("5"))
				{
					junglerCounter++;
					avgJunglerKM += Integer.parseInt(onePlayerInfo[4]) / matchTime;
					avgJunglerDM += Integer.parseInt(onePlayerInfo[5]) / matchTime;
					avgJunglerAM += Integer.parseInt(onePlayerInfo[6]) / matchTime;
					avgJunglerGPM += Integer.parseInt(onePlayerInfo[11]);
					avgJunglerHDM += Integer.parseInt(onePlayerInfo[9]) / matchTime;
					avgJunglerPartisipate += Integer.parseInt(onePlayerInfo[7]);
					avgJunglerTDM += Integer.parseInt(onePlayerInfo[10]) / matchTime;
					for (int k = 0; k < Integer.parseInt(eachKill[0]); k++)
					{
						String[] killInfo = eachKill[k + 1].split(";");
						int dier = j + 1;
						if (Integer.parseInt(killInfo[2]) < 600 && killInfo[3].equals(dier + ""))
						{
							avgJunglerDF10M += 1;
						}
					}
				}
			}

		}


		avgMiderKM = avgMiderKM / miderCounter;
		avgMiderDM = avgMiderDM / miderCounter;
		avgMiderAM = avgMiderAM / miderCounter;
		avgMiderTDM = avgMiderTDM / miderCounter;
		avgMiderHDM = avgMiderHDM / miderCounter;
		avgMiderGPM = avgMiderGPM / miderCounter;
		avgMiderGfKGFDifference = avgMiderGfKGFDifference / miderCounter;
		avgMiderCreepsF10M = avgMiderCreepsF10M / miderCounter;
		avgMiderLHM = avgMiderLHM / miderCounter;

		avgCarryKM = avgCarryKM / carryCounter;
		avgCarryDM = avgCarryDM / carryCounter;
		avgCarryAM = avgCarryAM / carryCounter;
		avgCarryTDM = avgCarryTDM / carryCounter;
		avgCarryHDM = avgCarryHDM / carryCounter;
		avgCarryGPM = avgCarryGPM / carryCounter;
		avgCarryGfKGFDifference = avgCarryGfKGFDifference / carryCounter;
		avgCarryCreepsF15M = avgCarryCreepsF15M / carryCounter;
		avgCarryLHM = avgCarryLHM / carryCounter;

		avgSupportKM = avgSupportKM / supportCounter;
		avgSupportDM = avgSupportDM / supportCounter;
		avgSupportAM = avgSupportAM / supportCounter;
		avgSupportHHM = avgSupportHHM / supportCounter;
		avgSupportHDM = avgSupportHDM / supportCounter;
		avgSupportGPM = avgSupportGPM / supportCounter;
		avgSupportPartisipate = avgSupportPartisipate / supportCounter;
		avgSupportWDM = avgSupportWDM / supportCounter;
		avgSupportXPM=avgSupportXPM/supportCounter;

		avgHardlinerKM = avgHardlinerKM / hardlinerCounter;
		avgHardlinerDM = avgHardlinerDM / hardlinerCounter;
		avgHardlinerAM = avgHardlinerAM / hardlinerCounter;
		avgHardlinerXPMF10M = avgHardlinerXPMF10M / hardlinerCounter;
		avgHardlinerPartisipate = avgHardlinerPartisipate / hardlinerCounter;
		avgHardlinerGPM = avgHardlinerGPM / hardlinerCounter;
		avgHardlinerDF10M = avgHardlinerDF10M / hardlinerCounter;
		avgHardlinerHDM = avgHardlinerHDM / hardlinerCounter;

		avgJunglerKM = avgJunglerKM / junglerCounter;
		avgJunglerDM = avgJunglerDM / junglerCounter;
		avgJunglerAM = avgJunglerAM / junglerCounter;
		avgJunglerHDM = avgJunglerHDM / junglerCounter;
		avgJunglerDF10M = avgJunglerDF10M / junglerCounter;
		avgJunglerGPM = avgJunglerGPM / junglerCounter;
		avgJunglerTDM = avgJunglerTDM / junglerCounter;
		avgJunglerPartisipate = avgJunglerPartisipate / junglerCounter;


		/*System.out.println("MIDER Avg. Values:");
		System.out.println("KillsPerMinute:" + avgMiderKM);
		System.out.println("DeathsPerMinute:" + avgMiderDM);
		System.out.println("AssistsPerMinute:" + avgMiderAM);
		System.out.println("TowerDamagePerMinute:" + avgMiderTDM);
		System.out.println("HeroDamagePerMinute:" + avgMiderHDM);
		System.out.println("GPM:" + avgMiderGPM);
		System.out.println("GfK-GL:" + avgMiderGfKGFDifference);
		System.out.println("CreepsF10M:" + avgMiderCreepsF10M);
		System.out.println("CARRY Avg. Values:");
		System.out.println("KillsPerMinute:" + avgCarryKM);
		System.out.println("DeathsPerMinute:" + avgCarryDM);
		System.out.println("AssistsPerMinute:" + avgCarryAM);
		System.out.println("TowerDamagePerMinute:" + avgCarryTDM);
		System.out.println("HeroDamagePerMinute:" + avgCarryHDM);
		System.out.println("GPM:" + avgCarryGPM);
		System.out.println("GfK-GL:" + avgCarryGfKGFDifference);
		System.out.println("CreepsF15M:" + avgCarryCreepsF15M);
		System.out.println("SUPPORT Avg. Values:");
		System.out.println("KillsPerMinute:" + avgSupportKM);
		System.out.println("DeathsPerMinute:" + avgSupportDM);
		System.out.println("AssistsPerMinute:" + avgSupportAM);
		System.out.println("HeroHealPerMinute:" + avgSupportHHM);
		System.out.println("HeroDamagePerMinute:" + avgSupportHDM);
		System.out.println("GPM:" + avgSupportGPM);
		System.out.println("Partisipate:" + avgSupportPartisipate);
		System.out.println("WardDestroyMinute:" + avgSupportWDM);
		System.out.println("HARDLINER Avg. Values:");
		System.out.println("KillsPerMinute:" + avgHardlinerKM);
		System.out.println("DeathsPerMinute:" + avgHardlinerDM);
		System.out.println("AssistsPerMinute:" + avgHardlinerAM);
		System.out.println("XPMF10M:" + avgHardlinerXPMF10M);
		System.out.println("Partisipate:" + avgHardlinerPartisipate);
		System.out.println("GPM:" + avgHardlinerGPM);
		System.out.println("DF10M:" + avgHardlinerDF10M);
		System.out.println("HDM:" + avgHardlinerHDM);
		System.out.println("JUNGLER Avg. Values:");
		System.out.println("KillsPerMinute:" + avgJunglerKM);
		System.out.println("DeathsPerMinute:" + avgJunglerDM);
		System.out.println("AssistsPerMinute:" + avgJunglerAM);
		System.out.println("HeroDamageMinute:" + avgJunglerHDM);
		System.out.println("DF10M:" + avgJunglerDF10M);
		System.out.println("GPM:" + avgJunglerGPM);
		System.out.println("Partisipation:" + avgJunglerPartisipate);*/
	}


	public Double allKills = 0.0;
	public Integer allKillsCounter = 0;
	public Double smokeHits = 0.0;
	public Integer smokeHitsCounter = 0;
	public Double smokeTotalHeroes = 0.0;
	public Integer smokeTotalHeroesCounter = 0;
	public Double goldForKills = 0.0;
	public Integer goldForKillsCounter = 0;

	public Integer secondsT1 = 0;
	public Integer T1Counter = 0;

	public Integer secondsT2 = 0;
	public Integer T2Counter = 0;

	public Integer secondsT3 = 0;
	public Integer T3Counter = 0;

	public Double avgWardLifeTime = 0.0;
	public Integer wardsPlaced = 0;
	public Integer wardLifeTime = 0;


	public Double avgLHF5M = 0.0;
	public Double avgLHF510M = 0.0;
	public Double avgXPMF10M = 0.0;
	public Double avgGPMF10M = 0.0;
	public Double avgF10KTime = 0.0;
	public Double avgLHM = 0.0;
	public Double avgGPM = 0.0;
	public Double avgGF = 0.0;

	public void getAverageMatchData() throws IOException
	{
		ArrayList<KillEvent> killEventArrayList = new ArrayList<KillEvent>();
		ArrayList<BuyBackEvent> buyBackEventArrayList = new ArrayList<BuyBackEvent>();
		ArrayList<GlyphEvent> glyphEventArrayList = new ArrayList<GlyphEvent>();
		ArrayList<TowerEvent> towerEventArrayList = new ArrayList<TowerEvent>();
		ArrayList<WardEvent> wardEventArrayList = new ArrayList<WardEvent>();
		ArrayList<RoshanEvent> roshanEventArrayList = new ArrayList<RoshanEvent>();
		Match match = new Match();
		Player[] player = new Player[10];
		Team[] team = new Team[2];

		for (int i = 0; i < 10; i++)
		{
			player[i] = new Player();
		}
		for (int i = 0; i < 2; i++)
		{
			team[i] = new Team();
		}

		String allMatchesFile = fileOperationsFactory.readFile("files/Matches.txt");
		String[] eachMatch = allMatchesFile.split("\n");
		Integer matchesCount = 50;

		if (eachMatch.length < 50)
			matchesCount = eachMatch.length;
		Integer LHF5M = 0;
		Integer LHF5MCounter = 0;
		Integer LHF510M = 0;
		Integer LHF510MCounter = 0;
		Integer XPMCounter = 0;
		Integer GPMCounter = 0;
		Integer XPMF10M = 0;
		Integer GPMF10M = 0;
		Integer F10KTime = 0;
		Integer F10KCounter = 0;
		Integer minutesCounter = 0;
		Integer totalLH = 0;
		Integer totalGPMCounter = 0;
		Integer totalGPM = 0;
		Integer totalGF = 0;
		Integer totalGFCounter = 0;
		for (int i = eachMatch.length - 1; i > eachMatch.length - matchesCount - 1; i--)
		{
			stringReader.fillArraysFromFile(eachMatch[i], team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);

			totalGPM += team[0].totalGPM + team[1].totalGPM;
			totalGPMCounter += 2;

			for (int j = 0; j < 5; j++)
			{
				LHF5M += team[0].perMinuteLastHits[j];
				LHF5M += team[1].perMinuteLastHits[j];
				LHF510M += team[0].perMinuteLastHits[j + 5];
				LHF510M += team[1].perMinuteLastHits[j + 5];
			}
			for (int j = 0; j < 10; j++)
			{
				XPMF10M += team[0].minuteXPM[j];
				XPMF10M += team[1].minuteXPM[j];

				GPMF10M += team[0].minuteGPM[j];
				GPMF10M += team[1].minuteGPM[j];
			}
			for (int j = 0; j < match.matchTime; j++)
			{
				totalLH += team[0].perMinuteLastHits[j];
				totalLH += team[1].perMinuteLastHits[j];
			}
			minutesCounter += match.matchTime * 2;
			XPMCounter += 2;
			GPMCounter += 2;
			LHF5MCounter += 2;
			LHF510MCounter += 2;
			allKills += (double) (team[0].kills + team[1].kills) / match.matchTime;
			allKillsCounter = allKillsCounter + 2;

			smokeHits += (double) (team[0].smokeHits + team[1].smokeHits) / match.matchTime;
			smokeHitsCounter = smokeHitsCounter + 2;

			smokeTotalHeroes += (double) (team[0].smokeTotalHeroes + team[1].smokeTotalHeroes) / match.matchTime;
			smokeTotalHeroesCounter = smokeTotalHeroesCounter + 2;

			goldForKills += (team[0].goldForKills + team[1].goldForKills) / match.matchTime;
			goldForKillsCounter = goldForKillsCounter + 2;

			for (int j = 0; j < towerEventArrayList.size(); j++)
			{
				if (towerEventArrayList.get(j).tierLevel == 1)
				{
					secondsT1 += towerEventArrayList.get(j).second;
					T1Counter++;
				}
				if (towerEventArrayList.get(j).tierLevel == 2)
				{
					secondsT2 += towerEventArrayList.get(j).second;
					T2Counter++;
				}
				if (towerEventArrayList.get(j).tierLevel == 3)
				{
					secondsT3 += towerEventArrayList.get(j).second;
					T3Counter++;
				}
			}
			for (int j = 0; j < wardEventArrayList.size(); j++)
			{
				wardsPlaced++;
				wardLifeTime += wardEventArrayList.get(j).lifeTime;
			}
			Integer radKills = 0;
			Integer direKills = 0;
			Integer currTime = 0;
			for (int j = 0; j < killEventArrayList.size(); j++)
			{
				if (radKills >= 10 || direKills >= 10)
					continue;
				if (killEventArrayList.get(j).dier >= 6)
					radKills++;
				else
					direKills++;
				currTime = killEventArrayList.get(j).second;
				if (radKills == 10 || direKills == 10)
				{
					F10KTime += currTime;
					F10KCounter++;
				}

			}
			totalGF += team[0].goldFed + team[1].goldFed;
			totalGFCounter += 2;
		}
		avgGF = (double) totalGF / totalGFCounter;
		avgGPM = (double) totalGPM / totalGPMCounter;
		avgLHM = (double) totalLH / minutesCounter;
		avgF10KTime = (double) F10KTime / F10KCounter;
		avgXPMF10M = (double) XPMF10M / XPMCounter;
		avgGPMF10M = (double) GPMF10M / GPMCounter;
		avgLHF5M = (double) LHF5M / LHF5MCounter;
		avgLHF510M = (double) LHF510M / LHF510MCounter;
		avgWardLifeTime = (double) wardLifeTime / wardsPlaced;
		writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList, roshanEventArrayList);
		writerReaderFactory.makeZeros(team, player, match);
	}
}
