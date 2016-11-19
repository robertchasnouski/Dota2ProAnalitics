package ProjectDir;

import ProjectDir.Analitics.StringReader;
import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class HeroesAverageStatsWorker
{
	StringReader stringReader = new StringReader();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void updateStats() throws IOException
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

		String allMatches = fileOperationsFactory.readFile("files/Matches.txt");
		String[] eachMatch = allMatches.split("\n");
		ArrayList<String> heroesArray = getHeroesNamesArray();

		for (int i = 0; i < heroesArray.size(); i++)
		{
			System.out.println("Calculating stat for " + heroesArray.get(i) + ".");
			String writeString = "";
			Integer medianGPM = 0;
			Integer medianXPM = 0;
			Integer medianHeroHeal = 0;
			Integer medianHeroDamage = 0;
			Integer medianTowerDamage = 0;
			ArrayList<Integer> arrayGPM = new ArrayList<>();
			ArrayList<Integer> arrayXPM = new ArrayList<>();
			ArrayList<Integer> arrayHeroHeal = new ArrayList<>();
			ArrayList<Integer> arrayHeroDamage = new ArrayList<>();
			ArrayList<Integer> arrayTowerDamage = new ArrayList<>();

			for (int j = 0; j < eachMatch.length; j++)
			{
				stringReader.fillArraysFromFile(eachMatch[j], team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
				for (int k = 0; k < 10; k++)
				{
					if (heroesArray.get(i).equals(parseHeroName(player[k].hero)))
					{
						arrayGPM.add(player[k].totalGPM);
						arrayXPM.add(player[k].totalXPM);
						arrayHeroHeal.add(player[k].heroHeal / match.matchTime);
						arrayHeroDamage.add(player[k].heroDamage / match.matchTime);
						arrayTowerDamage.add(player[k].towerDamage);
					}
				}
			}
			medianGPM = getMedianFromArray(arrayGPM);
			medianXPM = getMedianFromArray(arrayXPM);
			medianHeroDamage = getMedianFromArray(arrayHeroDamage);
			medianHeroHeal = getMedianFromArray(arrayHeroHeal);
			medianTowerDamage = getMedianFromArray(arrayTowerDamage);
			writeString += medianGPM + ";" + medianXPM + ";" + medianHeroDamage + ";" + medianHeroHeal + ";" + medianTowerDamage;
			fileOperationsFactory.cleanAndWriteToFile(writeString, "files/heroes/" + heroesArray.get(i) + ".txt");
			killEventArrayList.clear();
		}

	}

	public Integer getMedianFromArray(ArrayList<Integer> array)
	{
		Collections.sort(array);
		if (array.size() < 2)
			return 0;
		if (array.size() % 2 == 0)
		{
			return (int) (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		} else
		{
			return (int) array.get(array.size() / 2);
		}
	}

	public ArrayList<String> getHeroesNamesArray() throws IOException
	{
		String file = fileOperationsFactory.readFile("files/HeroesNumbers.txt");
		ArrayList<String> array = new ArrayList<>();
		String[] eachLine = file.split("\n");
		for (int i = 0; i < eachLine.length; i++)
		{
			array.add(eachLine[i].split(";")[1]);
		}
		return array;
	}

	public String parseHeroName(String heroName)
	{
		heroName = heroName.replaceAll(" ", "");
		heroName = heroName.replaceAll("-", "");
		heroName = heroName.replaceAll("'", "");
		return heroName;
	}

	private static String replaceLast(String text, String regex, String replacement)
	{
		return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
	}

}
