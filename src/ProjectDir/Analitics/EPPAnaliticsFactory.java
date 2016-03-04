package ProjectDir.Analitics;

import ProjectDir.MatchInfo.*;
import ProjectDir.AverageDataFactory;
import java.util.ArrayList;

public class EPPAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	StringReader stringReader = new StringReader();

	public void calculatePlayersEPP(AverageDataFactory averageDataFactory,String stringFromFile)
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
		stringReader.fillArraysFromFile(stringFromFile, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

	}
	public void giveEPPToPlayers(String stringFromFile)
	{
		/**Filling arrays**/


	}
}
