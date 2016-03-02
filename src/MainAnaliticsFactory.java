import Analitics.*;
import MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainAnaliticsFactory
{
	AverageAnaliticsFactory averageAnaliticsFactory = new AverageAnaliticsFactory();
	PrimaryAnaliticsFactory primaryAnaliticsFactory = new PrimaryAnaliticsFactory();
	FileControlFactory fileControlFactory = new FileControlFactory();
	HeatMapAnaliticsFactory heatMapAnaliticsFactory = new HeatMapAnaliticsFactory();

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
		/**PrimaryAnaliticsFactory**/
		String matchesFile = fileControlFactory.readFile("files/Matches.txt");
		String[] matches = matchesFile.split("\n");
		/*for (int i = 0; i < matches.length; i++)
		{
			primaryAnaliticsFactory.analizeMatch(matches[i]);
		}*/
		/**AverageAnaliticsFactory**/
		//averageAnaliticsFactory.startAverageAnalitics(matchesFile);
		heatMapAnaliticsFactory.buildHeatMap("1883502");

	}
}
