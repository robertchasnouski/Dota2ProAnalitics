package ProjectDir;

import ProjectDir.Analitics.StringReader;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;

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

	public Double avgCarryKM = 0.0;
	public Double avgCarryDM = 0.0;
	public Double avgCarryAM = 0.0;
	public Double avgCarryGPM = 0.0;
	public Double avgCarryHDM = 0.0;
	public Double avgCarryTDM = 0.0;
	public Double avgCarryGfKGFDifference = 0.0;
	public Double avgCarryCreepsF15M = 0.0;

	public Double avgSupportKM = 0.0;
	public Double avgSupportDM = 0.0;
	public Double avgSupportAM = 0.0;
	public Double avgSupportWLT;
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

	public Integer allKills=0;
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	StringReader stringReader = new StringReader();

	void getAverageData() throws IOException
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

			allKills+=Integer.parseInt(team1MatchInfo[3]);

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
					avgSupportPartisipate += Integer.parseInt(onePlayerInfo[7]);
					avgSupportWDM += Integer.parseInt(onePlayerInfo[23]) / matchTime;
					//TODO: WardLifeTime
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

		avgCarryKM = avgCarryKM / carryCounter;
		avgCarryDM = avgCarryDM / carryCounter;
		avgCarryAM = avgCarryAM / carryCounter;
		avgCarryTDM = avgCarryTDM / carryCounter;
		avgCarryHDM = avgCarryHDM / carryCounter;
		avgCarryGPM = avgCarryGPM / carryCounter;
		avgCarryGfKGFDifference = avgCarryGfKGFDifference / carryCounter;
		avgCarryCreepsF15M = avgCarryCreepsF15M / carryCounter;

		avgSupportKM = avgSupportKM / supportCounter;
		avgSupportDM = avgSupportDM / supportCounter;
		avgSupportAM = avgSupportAM / supportCounter;
		avgSupportHHM = avgSupportHHM / supportCounter;
		avgSupportHDM = avgSupportHDM / supportCounter;
		avgSupportGPM = avgSupportGPM / supportCounter;
		avgSupportPartisipate = avgSupportPartisipate / supportCounter;
		avgSupportWDM = avgSupportWDM / supportCounter;

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

		System.out.println("Avg. Kills:"+allKills/matchesCount);
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
}
