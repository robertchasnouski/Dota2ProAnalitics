package ProjectDir.Analitics;

import ProjectDir.AverageDataFactory;
import ProjectDir.MatchInfo.*;

import java.util.ArrayList;

public class EPPAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	StringReader stringReader = new StringReader();

	public void calculatePlayersEPP(AverageDataFactory averageDataFactory, String stringFromFile, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList)
	{

		for (int i = 0; i < 10; i++)
		{
			Integer DF10M = 0;
			Integer LH15M = 0;
			Integer XPMF10M = player[i].minuteXPM[0] + player[i].minuteXPM[1] + player[i].minuteXPM[2] + player[i].minuteXPM[3] + player[i].minuteXPM[4] + player[i].minuteXPM[5] + player[i].minuteXPM[6] + player[i].minuteXPM[7] + player[i].minuteXPM[8] + player[i].minuteXPM[9];
			Integer LH10M = player[i].perMinuteLastHits[0] + player[i].perMinuteLastHits[1] + player[i].perMinuteLastHits[2] + player[i].perMinuteLastHits[3] + player[i].perMinuteLastHits[4] + player[i].perMinuteLastHits[5] + player[i].perMinuteLastHits[6] + player[i].perMinuteLastHits[7] + player[i].perMinuteLastHits[8] + player[i].perMinuteLastHits[9];
			if (match.matchTime > 15)
				LH15M = player[i].perMinuteLastHits[0] + player[i].perMinuteLastHits[1] + player[i].perMinuteLastHits[2] + player[i].perMinuteLastHits[3] + player[i].perMinuteLastHits[4] + player[i].perMinuteLastHits[5] + player[i].perMinuteLastHits[6] + player[i].perMinuteLastHits[7] + player[i].perMinuteLastHits[8] + player[i].perMinuteLastHits[9] + player[i].perMinuteLastHits[10] + player[i].perMinuteLastHits[11] + player[i].perMinuteLastHits[12] + player[i].perMinuteLastHits[13] + player[i].perMinuteLastHits[14];
			else LH15M = 85;
			//Mider
			if (player[i].role == 1)
			{
				//HDM
				player[i].EPP += (int) ((0.55 * 1000 * player[i].heroDamage / match.matchTime) / averageDataFactory.avgMiderHDM);
				//GPM
				player[i].EPP += (int) ((0.55 * 1000 * player[i].totalGPM) / averageDataFactory.avgMiderGPM);
				//GfK-GF
				player[i].EPP += (int) ((0.65 * 1000 * (player[i].goldForKills - player[i].goldFed) / match.matchTime) / averageDataFactory.avgMiderGfKGFDifference);
				//LHF10M
				player[i].EPP += (int) ((0.25 * 1000 * LH10M) / averageDataFactory.avgMiderCreepsF10M);
			}
			//Carry
			else if (player[i].role == 2)
			{
				//GPM
				player[i].EPP += (int) ((0.55 * 1000 * player[i].totalGPM) / averageDataFactory.avgCarryGPM);
				//HDM
				player[i].EPP += (int) ((0.50 * 1000 * player[i].heroDamage / match.matchTime) / averageDataFactory.avgCarryHDM);
				//GfK-GF
				player[i].EPP += (int) ((0.65 * 1000 * (player[i].goldForKills - player[i].goldFed)) / averageDataFactory.avgCarryGfKGFDifference);
				//LHF15M
				player[i].EPP += (int) ((0.30 * 1000 * LH15M) / averageDataFactory.avgCarryCreepsF15M);
			}
			//Support
			else if (player[i].role == 3)
			{
				//GWD
				player[i].EPP += (int) ((0.2 * 1000 * player[i].observerWardsDestroyed / match.matchTime) / averageDataFactory.avgSupportWDM);
				//HDM
				player[i].EPP += (int) ((0.4 * 1000 * player[i].heroDamage / match.matchTime) / averageDataFactory.avgSupportHDM);
				//XPM
				player[i].EPP += (int) ((0.65 * 1000 * player[i].totalXPM) / averageDataFactory.avgSupportXPM);
				//GPM
				player[i].EPP += (int) ((0.5 * 1000 * player[i].totalGPM) / averageDataFactory.avgSupportGPM);
				//HHM
				player[i].EPP += (int) ((0.25 * 1000 * player[i].heroHeal / match.matchTime) / 200);
			}
			//HardLiner
			else if (player[i].role == 4)
			{
				//HDM
				player[i].EPP += (int) ((0.7 * 1000 * player[i].heroDamage / match.matchTime) / averageDataFactory.avgHardlinerHDM);
				//GPM
				player[i].EPP += (int) ((0.8 * 1000 * player[i].totalGPM) / averageDataFactory.avgHardlinerGPM);
				//XPMF10M
				player[i].EPP += (int) ((0.5 * 1000 * XPMF10M) / averageDataFactory.avgHardlinerXPMF10M);
			}
			//Jungler
			else if (player[i].role == 5)
			{
				//HDM
				player[i].EPP += (int) ((0.3 * 1000 * player[i].heroDamage / match.matchTime) / averageDataFactory.avgJunglerHDM);
				//GPM
				player[i].EPP += (int) ((0.3 * 1000 * player[i].totalGPM) / averageDataFactory.avgJunglerGPM);
				//HDM
				player[i].EPP += (int) ((0.3 * 1000 * player[i].partisipation) / averageDataFactory.avgJunglerPartisipate);
				//TDM
				player[i].EPP += (int) ((0.1 * 1000 * player[i].towerDamage / match.matchTime) / averageDataFactory.avgJunglerTDM);
			}
		}
	}

}
