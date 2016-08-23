package ProjectDir;

import java.io.IOException;
import java.util.ArrayList;

public class HeroesPointsWorker
{

	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void start_work() throws IOException
	{
		ArrayList<String> namesPool = new ArrayList<>();
		String file = fileOperationsFactory.readFile("files/Matches.txt");
		String[] eachMatch = file.split("\n");
		for (int i = 0; i < eachMatch.length; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				String heroName = eachMatch[i].split("##")[2].split("\\|\\|")[j].split(";")[1];
				if (!namesPool.contains(heroName))
					namesPool.add(heroName);
			}
		}
		ArrayList<MatchEvent> eventsArray = new ArrayList<>();
		eventsArray = fillEvents(file);
		ArrayList<HeroStat> heroStats = new ArrayList<>();
		for (int i = 0; i < namesPool.size(); i++)
		{
			HeroStat oneStat = new HeroStat();
			Integer allGames = 0;
			Integer FBGet = 0;
			Integer F10KGet = 0;
			oneStat.heroName = namesPool.get(i);
			for (int j = 0; j < eventsArray.size(); j++)
			{
				Integer playerNumber = 0;
				Boolean exist = false;
				for (int k = 0; k < 10; k++)
				{
					if (oneStat.heroName.equals(eventsArray.get(j).playerNames[k]))
					{
						playerNumber = k;
						exist = true;
					}
				}
				if (exist)
				{
					//Radiant
					if (playerNumber < 5)
					{
						if (eventsArray.get(j).radiantFB)
							FBGet++;
						if (eventsArray.get(j).radiantF10K)
							F10KGet++;
					}
					//Dire
					else
					{
						if (!eventsArray.get(j).radiantFB)
							FBGet++;
						if (!eventsArray.get(j).radiantF10K)
							F10KGet++;
					}
					allGames++;
				}
			}
			Double fb = (double) FBGet / allGames * 100;
			Double f10k = (double) F10KGet / allGames * 100;
			oneStat.FBPercent = fb.intValue();
			oneStat.F10KPercent = f10k.intValue();
			heroStats.add(oneStat);
		}
		for (int i = 0; i < heroStats.size(); i++)
		{
			System.out.println(heroStats.get(i).heroName + " FB:" + heroStats.get(i).FBPercent + "% .F10K:" + heroStats.get(i).F10KPercent + "%");
		}
	}

	private ArrayList<MatchEvent> fillEvents(String file)
	{
		ArrayList<MatchEvent> events = new ArrayList<>();
		String[] eachLine = file.split("\n");
		for (int i = 0; i < eachLine.length; i++)
		{
			MatchEvent event = new MatchEvent();
			event.matchId = eachLine[i].split(";")[0];
			event.radiantFB = Boolean.parseBoolean(eachLine[i].split(";")[8]);
			event.radiantF10K = Boolean.parseBoolean(eachLine[i].split(";")[9]);
			for (int j = 0; j < 10; j++)
			{
				event.playerNames[j] = eachLine[i].split("##")[2].split("\\|\\|")[j].split(";")[1];
			}
			events.add(event);
		}
		return events;
	}

	public class HeroStat
	{
		public String heroName;
		public Integer FBPercent;
		public Integer F10KPercent;
	}

	public class MatchEvent
	{
		public String matchId;
		public String[] playerNames = new String[10];
		public Boolean radiantFB;
		public Boolean radiantF10K;
	}
}

