package Analitics;

import MatchInfo.*;

import java.util.ArrayList;

public class EPPAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	StringReader stringReader = new StringReader();

	public void giveEPPToPlayers(String stringFromFile)
	{
		/**Filling arrays**/
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

		for (int i = 0; i < player.length; i++)
		{
			/**1-Mider. 2-Carry. 3-Support. 4-Hardliner. 5-Jungler.**/
			//<editor-fold desc="MIDER">
			if (player[i].role == 1)
			{
				//EG creeps(High priority)
				//LH accuracy(<5%)
				//HeroDamage(HighPriority)
				//KDA
				//TowerDamage
			}
			//</editor-fold>
			//<editor-fold desc="CARRY">
			if (player[i].role == 2)
			{
				//LH Accuracy
				//KDA
				//HeroDamage

			}
			//</editor-fold>
			//<editor-fold desc="SUPPORT">
			if (player[i].role == 3)
			{

			}
			//</editor-fold>
			//<editor-fold desc="HARDLINER">
			if (player[i].role == 4)
			{

			}
			//</editor-fold>
			//<editor-fold desc="JUNGLER">
			if (player[i].role == 5)
			{

			}
			//</editor-fold>
		}
	}
}
