import Analitics.*;
import MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainAnaliticsFactory
{
	AverageAnaliticsFactory averageAnaliticsFactory = new AverageAnaliticsFactory();
	PrimaryAnaliticsFactory primaryAnaliticsFactory = new PrimaryAnaliticsFactory();


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
		//fileControlFactory.createTeamFileIfNotExists("4");
		//TODO:

		primaryAnaliticsFactory.analizeMatches();


		//TODO:
	}
}
