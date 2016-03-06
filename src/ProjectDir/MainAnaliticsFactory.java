package ProjectDir;

import ProjectDir.Analitics.*;
import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainAnaliticsFactory
{
	AverageAnaliticsFactory averageAnaliticsFactory = new AverageAnaliticsFactory();
	PrimaryAnaliticsFactory primaryAnaliticsFactory = new PrimaryAnaliticsFactory();
	HeatMapAnaliticsFactory heatMapAnaliticsFactory = new HeatMapAnaliticsFactory();
	EPPAnaliticsFactory eppAnaliticsFactory = new EPPAnaliticsFactory();
	GameStageAnalitics gameStageAnalitics=new GameStageAnalitics();

	AverageDataFactory averageDataFactory = new AverageDataFactory();
	FileControlFactory fileControlFactory = new FileControlFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	StringReader stringReader = new StringReader();

	public void startWork() throws IOException
	{
		ArrayList<KillEvent> killEventArrayList = new ArrayList<KillEvent>();
		ArrayList<BuyBackEvent> buyBackEventArrayList = new ArrayList<BuyBackEvent>();
		ArrayList<GlyphEvent> glyphEventArrayList = new ArrayList<GlyphEvent>();
		ArrayList<TowerEvent> towerEventArrayList = new ArrayList<TowerEvent>();
		ArrayList<WardEvent> wardEventArrayList = new ArrayList<WardEvent>();
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
		/**ProjectDir.AverageDataFactory**/
		averageDataFactory.getAverageData();
		/**PrimaryAnaliticsFactory and EPPFactory**/
		String matchesFile = fileControlFactory.readFile("files/Matches.txt");
		String[] matches = matchesFile.split("\n");
		for (int i = 1; i < 3/**matches.length*/; i++)
		{
			System.out.println(matches[i]);
			stringReader.fillArraysFromFile(matches[i], team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

			gameStageAnalitics.getEGPoints(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			gameStageAnalitics.getMGPoints(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			gameStageAnalitics.getLGPoints(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			eppAnaliticsFactory.calculatePlayersEPP(averageDataFactory, matches[i], team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

			//System.out.println("Team Radiant   EGPoints:"+team[0].EGPoints+" MGPoints:"+team[0].MGPoints+" LGPoints:"+team[0].LGPoints);
			//System.out.println("Team Dire   EGPoints:"+team[1].EGPoints+" MGPoints:"+team[1].MGPoints+" LGPoints:"+team[1].LGPoints);
			for (int j = 0; j < 10; j++)
			{
				System.out.println("Hero:" + player[j].hero + " Role:" + player[j].role + " EPP:" + player[j].EPP);
			}

			primaryAnaliticsFactory.analizeMatch(matches[i], team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

			writerReaderFactory.makeZeros(team, player, match);
			writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);

		}
		/**AverageAnaliticsFactory**/
		//averageAnaliticsFactory.startAverageAnalitics(matchesFile);
		//heatMapAnaliticsFactory.buildHeatMap("1883502");

	}
}
