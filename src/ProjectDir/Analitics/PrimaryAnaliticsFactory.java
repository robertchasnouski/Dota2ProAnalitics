package ProjectDir.Analitics;

import ProjectDir.AverageDataFactory;
import ProjectDir.MatchInfo.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.IOException;
import java.util.ArrayList;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	AverageDataFactory averageDataFactory = new AverageDataFactory();


	public void analizeMatch(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		/**Create team file if not exists**/
		fileControlFactory.createTeamFileIfNotExists(team[0].id);
		fileControlFactory.createTeamFileIfNotExists(team[1].id);
		/**Start analizing**/
		/****Aggression**/
		//Integer radiantAggression = analizeRadiantAggression(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList,roshanEventArrayList);
		//Integer direAggression = analizeDireAggression(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList,roshanEventArrayList);
		//System.out.println("Radiant Aggression:" + radiantAggression);
		//System.out.println("Dire aggression:" + direAggression);
		/****Pushing****/
		//Integer radiantPushing = analizeRadiantPushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//Integer direPushing = analizeDirePushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Pushing:" + radiantPushing);
		//System.out.println("Dire Pushing:" + direPushing);
		/****Wardability****/
		//Integer radiantVision = analizeRadiantVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//Integer direVision = analizeDireVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Vision:" + radiantVision);
		//System.out.println("Dire Vision:" + direVision);
		/****Defence****/
		//Integer radiantDefence = analizeRadiantDefence(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//Integer direDefence = analizeDireDefence(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant defence:" + radiantDefence);
		//System.out.println("Dire defence:" + direDefence);
		/****Lining****/
		//Integer radiantLining = analizeRadiantLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//Integer direLining = analizeDireLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant lining:" + radiantLining);
		//System.out.println("Dire lining:" + direLining);
		/****KillAbility****/
		//Integer radiantKillAbility = analizeRadiantKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//Integer direKillAbility = analizeDireKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant KillAbility:" + radiantKillAbility);
		//System.out.println("Dire KillAbility:" + direKillAbility);
		/****Farming****/
		Integer radiantFarming = analizeRadiantFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direFarming = analizeDireFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		System.out.println("Radiant Farming:" + radiantFarming);
		System.out.println("Dire Farming:" + direFarming);
		/**End analizing**/
		/**Write info to file**/


	}

	public Integer analizeRadiantAggression(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		Double aggression = 0.0;
		Double GfK = 0.0;
		Double killPoints = 0.0;
		Double smokePoints = 0.0;

		//GfK
		GfK = (0.30 * team[0].goldForKills * 1000 / match.matchTime) / (averageDataFactory.goldForKills / averageDataFactory.goldForKillsCounter);
		//Smoke
		smokePoints = (0.07 * team[0].smokeHits * 1000 / match.matchTime) / (averageDataFactory.smokeHits / averageDataFactory.smokeHitsCounter);
		//KillPoints
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 59)
			{
				for (int j = 0; j < roshanEventArrayList.size(); j++)
				{
					if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
					{
						if (killEventArrayList.get(i).dier >= 6)
						{
							//System.out.println("Radiant kill in Roshan Pit.");
							//System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killPoints += 40;
						}
						if (killEventArrayList.get(i).dier <= 5)
						{
							//System.out.println("Radiant death in Roshan Pit.");
							//System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

							killPoints -= 30;
						}
					}
				}
			}
			//Usual kill
			if (killEventArrayList.get(i).dier >= 6)
			{
				killPoints += 20;
			}
			//If KillAssist>1
			if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier >= 6)
			{
				//System.out.println("Radiant kill with high assists");
				//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
				killPoints += 15;
			}
			//If KillAssist>1
			if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier >= 6)
			{
				//System.out.println("Radiant kill with high assists");
				//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
				killPoints += 5;
			}
			//EG kill in jungle
			if (killEventArrayList.get(i).dier >= 6 && killEventArrayList.get(i).second <= 900)
			{
				if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29))
				{
					//System.out.println("Radiant Kill in jungle");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
			}
			//EG kill after T1
			if (killEventArrayList.get(i).dier >= 6 && killEventArrayList.get(i).second <= 700)
			{
				if ((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y <= 16 && killEventArrayList.get(i).y >= 6))
				{
					//System.out.println("Radiant Kill near top tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
				if ((killEventArrayList.get(i).x >= 48 && killEventArrayList.get(i).x <= 60 && killEventArrayList.get(i).y <= 50 && killEventArrayList.get(i).y >= 38))
				{
					//System.out.println("Radiant Kill near middle tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
				if ((killEventArrayList.get(i).x >= 81 && killEventArrayList.get(i).x <= 97 && killEventArrayList.get(i).y <= 60 && killEventArrayList.get(i).y >= 45))
				{
					//System.out.println("Radiant Kill near bot tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
			}
			//Kill+tower
			if (killEventArrayList.get(i).dier >= 6)
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).whoDestroy.equals("Radiant") && towerEventArrayList.get(j).second < killEventArrayList.get(i).second + 60 && towerEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60)
					{
						//System.out.println("Radiant Kill+tower");
						//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
						killPoints += 10;
					}
				}
			}
		}
		killPoints = killPoints / match.matchTime * 25;
		System.out.println("Radiant KillPoints:" + killPoints);
		System.out.println("Radiant GfKPoints:" + GfK);
		System.out.println("Radiant SmokePoints:" + smokePoints);
		aggression = killPoints + GfK + smokePoints;
		return aggression.intValue();
	}

	public Integer analizeDireAggression(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		Double aggression = 0.0;
		Double GfK = 0.0;
		Double killPoints = 0.0;
		Double smokePoints = 0.0;
		//KillPoints
		//GfK
		GfK = (0.30 * team[1].goldForKills * 1000 / match.matchTime) / (averageDataFactory.goldForKills / averageDataFactory.goldForKillsCounter);
		//Smoke
		smokePoints = (0.07 * team[1].smokeHits * 1000 / match.matchTime) / (averageDataFactory.smokeHits / averageDataFactory.smokeHitsCounter);
		//KillPoints
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 59)
			{
				for (int j = 0; j < roshanEventArrayList.size(); j++)
				{
					if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
					{
						if (killEventArrayList.get(i).dier <= 5)
						{
							//System.out.println("Dire kill in Roshan Pit.");
							//System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killPoints += 40;
						}
						if (killEventArrayList.get(i).dier >= 6)
						{
							//System.out.println("Dire death in Roshan Pit.");
							//System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

							killPoints -= 30;
						}
					}
				}
			}
			//Usual kill
			if (killEventArrayList.get(i).dier <= 5)
			{
				killPoints += 20;
			}
			//If KillAssist>1
			if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier <= 5)
			{
				//System.out.println("Dire kill with high assists");
				//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
				killPoints += 15;
			}
			//If KillAssist>2
			if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier <= 5)
			{
				//System.out.println("Dire kill with very high assists");
				//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
				killPoints += 5;
			}
			//EG kill in jungle
			if (killEventArrayList.get(i).dier <= 5 && killEventArrayList.get(i).second <= 900)
			{
				if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64))
				{
					//System.out.println("Dire Kill in jungle");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
			}
			//EG kill after T1
			if (killEventArrayList.get(i).dier <= 5 && killEventArrayList.get(i).second <= 700)
			{
				if ((killEventArrayList.get(i).x >= 2 && killEventArrayList.get(i).x <= 18 && killEventArrayList.get(i).y <= 58 && killEventArrayList.get(i).y >= 39))
				{
					//System.out.println("Dire Kill near top tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
				if ((killEventArrayList.get(i).x >= 39 && killEventArrayList.get(i).x <= 44 && killEventArrayList.get(i).y <= 62 && killEventArrayList.get(i).y >= 54))
				{
					//System.out.println("Dire Kill near middle tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
				if ((killEventArrayList.get(i).x >= 52 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 99 && killEventArrayList.get(i).y >= 87))
				{
					//System.out.println("Dire Kill near bot tower");
					//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
					killPoints += 35;
				}
			}
			//Kill+tower
			if (killEventArrayList.get(i).dier <= 5)
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).whoDestroy.equals("Dire") && towerEventArrayList.get(j).second < killEventArrayList.get(i).second + 50 && towerEventArrayList.get(j).second >= killEventArrayList.get(i).second - 50)
					{
						//System.out.println("Dire Kill+tower");
						//System.out.println("X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+" Second:"+killEventArrayList.get(i).second);
						killPoints += 10;
					}
				}
			}
		}
		killPoints = killPoints / match.matchTime * 25;
		System.out.println("Dire KillPoints:" + killPoints);
		System.out.println("Dire GfKPoints:" + GfK);
		System.out.println("Dire SmokePoints:" + smokePoints);
		aggression = killPoints + GfK + smokePoints;
		return aggression.intValue();
	}

	public Integer analizeRadiantPushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Integer rowPushing = 0;
		Integer splitPushing = 0;
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).tierLevel == 4)
				continue;
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant"))
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).whoDestroy.contains("Radiant"))
					{
						if (i == j)
							continue;
						if (towerEventArrayList.get(j).second <= towerEventArrayList.get(i).second + 90 && towerEventArrayList.get(j).second > towerEventArrayList.get(i).second)
							rowPushing += 50;
					}
				}
			}
			if (towerEventArrayList.get(i).whoDestroy.equals("Radiant"))
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).equals("Dire"))
					{
						if (towerEventArrayList.get(j).second <= towerEventArrayList.get(i).second + 20 && towerEventArrayList.get(j).second > towerEventArrayList.get(i).second - 2)
							splitPushing += 50;
					}
				}
			}
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 1)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 2)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 3)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		System.out.println("Radiant SecondPushingPoints:" + secondsPushing);
		System.out.println("Radiant RowPushingPoints:" + rowPushing);
		System.out.println("Radiant SplitPushingPoints:" + splitPushing);
		pushing = (int) (secondsPushing + rowPushing + splitPushing);
		return pushing;
	}

	public Integer analizeDirePushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Integer rowPushing = 0;
		Integer splitPushing = 0;
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).tierLevel == 4)
				continue;
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire"))
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).whoDestroy.contains("Dire"))
					{
						if (i == j)
							continue;
						if (towerEventArrayList.get(j).second <= towerEventArrayList.get(i).second + 90 && towerEventArrayList.get(j).second > towerEventArrayList.get(i).second)
							rowPushing += 50;
					}
				}
			}
			if (towerEventArrayList.get(i).whoDestroy.equals("Dire"))
			{
				for (int j = 0; j < towerEventArrayList.size(); j++)
				{
					if (towerEventArrayList.get(j).equals("Radiant"))
					{
						if (towerEventArrayList.get(j).second <= towerEventArrayList.get(i).second + 20 && towerEventArrayList.get(j).second > towerEventArrayList.get(i).second - 2)
							splitPushing += 50;
					}
				}
			}
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 1)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 2)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 3)
				secondsPushing += (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		System.out.println("Dire SecondPushingPoints:" + secondsPushing);
		System.out.println("Dire RowPushingPoints:" + rowPushing);
		System.out.println("Dire SplitPushingPoints:" + splitPushing);
		pushing = (int) (secondsPushing + rowPushing + splitPushing);
		return pushing;
	}

	public Integer analizeRadiantVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double wardsDestroyedPoints = 0.0;//30%
		Double avgLifeTimeOfWardPoints = 0.0;//30%
		Double killUnderWardPoints = 0.0;//40%

		wardsDestroyedPoints = (0.3 * 1000 * team[0].observerWardsDestroyed / match.matchTime) / averageDataFactory.avgSupportWDM;

		Integer wardsPlaced = 0;
		Integer wardLifeTime = 0;
		for (int i = 0; i < wardEventArrayList.size(); i++)
		{
			if (wardEventArrayList.get(i).side == 1)
			{
				wardsPlaced++;
				wardLifeTime += wardEventArrayList.get(i).lifeTime;

				for (int j = 0; j < killEventArrayList.size(); j++)
				{
					if (killEventArrayList.get(j).second >= wardEventArrayList.get(i).second && killEventArrayList.get(j).second <= wardEventArrayList.get(i).second + wardEventArrayList.get(i).lifeTime && killEventArrayList.get(j).dier >= 6)
					{
						if (killEventArrayList.get(j).x >= wardEventArrayList.get(i).x - 8 && killEventArrayList.get(j).x <= wardEventArrayList.get(i).x + 8 && killEventArrayList.get(j).y >= wardEventArrayList.get(i).y - 8 && killEventArrayList.get(j).y <= wardEventArrayList.get(i).y + 8)
						{
							killUnderWardPoints += 100;
						}
					}
				}
			}

		}
		avgLifeTimeOfWardPoints = (0.3 * 1000 * wardLifeTime / wardsPlaced) / averageDataFactory.avgWardLifeTime;
		System.out.println("Radiant AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		System.out.println("Radiant WardsDestroyedPoints:" + wardsDestroyedPoints);
		System.out.println("Radiant KillUnderWard:" + killUnderWardPoints);

		return (int) (killUnderWardPoints + wardsDestroyedPoints + avgLifeTimeOfWardPoints);
	}

	public Integer analizeDireVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double wardsDestroyedPoints = 0.0;
		Double avgLifeTimeOfWardPoints = 0.0;
		Double killUnderWardPoints = 0.0;

		wardsDestroyedPoints = (0.3 * 1000 * team[1].observerWardsDestroyed / match.matchTime) / averageDataFactory.avgSupportWDM;

		Integer wardsPlaced = 0;
		Integer wardLifeTime = 0;
		for (int i = 0; i < wardEventArrayList.size(); i++)
		{
			if (wardEventArrayList.get(i).side == 2)
			{
				wardsPlaced++;
				wardLifeTime += wardEventArrayList.get(i).lifeTime;

				for (int j = 0; j < killEventArrayList.size(); j++)
				{
					if (killEventArrayList.get(j).second >= wardEventArrayList.get(i).second && killEventArrayList.get(j).second <= wardEventArrayList.get(i).second + wardEventArrayList.get(i).lifeTime && killEventArrayList.get(j).dier <= 5)
					{
						if (killEventArrayList.get(j).x >= wardEventArrayList.get(i).x - 8 && killEventArrayList.get(j).x <= wardEventArrayList.get(i).x + 8 && killEventArrayList.get(j).y >= wardEventArrayList.get(i).y - 8 && killEventArrayList.get(j).y <= wardEventArrayList.get(i).y + 8)
						{
							killUnderWardPoints += 100;
						}
					}
				}
			}

		}
		avgLifeTimeOfWardPoints = (0.3 * 1000 * wardLifeTime / wardsPlaced) / averageDataFactory.avgWardLifeTime;
		System.out.println("Dire AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		System.out.println("Dire WardsDestroyedPoints:" + wardsDestroyedPoints);
		System.out.println("Dire KillUnderWard:" + killUnderWardPoints);

		return (int) (killUnderWardPoints + wardsDestroyedPoints + avgLifeTimeOfWardPoints);
	}

	public Integer analizeRadiantDefence(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		if (match.winRadiant == true)
			return 9999;

		Integer killAfterGliffPoints = 0;
		Integer killOnSelfAncients = 0;
		Integer killInSelfForest = 0;
		Integer killOnSelfHG = 0;
		Integer killUnderSelfTower = 0;
		Integer denied = 0;
		/**Kills**/
		////Kills after gliff
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).dier >= 6)
				for (int j = 0; j < glyphEventArrayList.size(); j++)
				{
					if (glyphEventArrayList.get(j).side == 1)
					{
						if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 20 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
						{
							System.out.println("Radiant kill after gliff");
							System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killAfterGliffPoints += 120;
						}

					}
				}
			if (killEventArrayList.get(i).dier <= 5)
				for (int j = 0; j < glyphEventArrayList.size(); j++)
				{
					if (glyphEventArrayList.get(j).side == 1)
					{
						if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 20 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
						{
							System.out.println("Radiant death after gliff");
							System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killAfterGliffPoints -= 40;
						}

					}
				}
		}
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			//Kill under self towers. Under T1 before 600 sec, Under T2 before 1000 sec
			////T1
			if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 35 && killEventArrayList.get(i).y <= 45) || (killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 46 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 60) || (killEventArrayList.get(i).x >= 76 && killEventArrayList.get(i).x <= 84 && killEventArrayList.get(i).y >= 84 && killEventArrayList.get(i).y <= 95)) && killEventArrayList.get(i).second <= 600)
			{
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Radiant kill under T1");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
					killUnderSelfTower += 150;
				}
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Radiant death under T1");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
					killUnderSelfTower -= 150;
				}
			}
			////T2
			if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 49 && killEventArrayList.get(i).y <= 65) || (killEventArrayList.get(i).x >= 27 && killEventArrayList.get(i).x <= 34 && killEventArrayList.get(i).y >= 64 && killEventArrayList.get(i).y <= 68) || (killEventArrayList.get(i).x >= 42 && killEventArrayList.get(i).x <= 54 && killEventArrayList.get(i).y >= 85 && killEventArrayList.get(i).y <= 95)) && killEventArrayList.get(i).second <= 1000)
			{
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Radiant kill under T2");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower += 150;
				}
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Radiant death under T2");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower -= 150;
				}
			}
			//HG
			if (killEventArrayList.get(i).x >= 0 && killEventArrayList.get(i).x <= 27 && killEventArrayList.get(i).y >= 68 && killEventArrayList.get(i).y <= 100)
			{
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Radiant kill under HG");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower += 200;
				}
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Radiant death under HG");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower -= 50;
				}
			}

			//Kill on self ancients
			if (killEventArrayList.get(i).x >= 30 && killEventArrayList.get(i).x <= 36 && killEventArrayList.get(i).y >= 46 && killEventArrayList.get(i).y <= 52)
			{
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Radiant kill under ancients");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfAncients += 100;
				}
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Radiant deaths under ancients");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfAncients -= 100;
				}
			}
			//Kill in forest (EG)
			if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64) && killEventArrayList.get(i).second <= 1000)
			{
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Radiant kill in self forest");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killInSelfForest += 100;
				}
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Radiant death in self forest");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killInSelfForest -= 100;
				}
			}
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).tierLevel == 4)
				continue;
			if (towerEventArrayList.get(i).whoDestroy.contains("Denied"))
				denied += 80;
		}


		System.out.println("Radiant KillInSelfForest:" + killInSelfForest);
		System.out.println("Radiant KillAfterGliff:" + killAfterGliffPoints);
		System.out.println("Radiant KillOnAncients:" + killOnSelfAncients);
		System.out.println("Radiant KillUnderSelfTowers:" + killUnderSelfTower);
		System.out.println("Radiant Denied:" + denied);
		return killInSelfForest + killAfterGliffPoints + killOnSelfAncients + killUnderSelfTower + denied;

	}

	public Integer analizeDireDefence(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		if (match.winRadiant == false)
			return 9999;

		Integer killAfterGliffPoints = 0;
		Integer killOnSelfAncients = 0;
		Integer killInSelfForest = 0;
		Integer killOnSelfHG = 0;
		Integer killUnderSelfTower = 0;
		Integer denied = 0;
		/**Kills**/
		////Kills after gliff
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).dier <= 5)
				for (int j = 0; j < glyphEventArrayList.size(); j++)
				{
					if (glyphEventArrayList.get(j).side == 2)
					{
						if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 20 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
						{
							System.out.println("Dire kill after gliff");
							System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killAfterGliffPoints += 120;
						}
					}
				}
			if (killEventArrayList.get(i).dier >= 6)
				for (int j = 0; j < glyphEventArrayList.size(); j++)
				{
					if (glyphEventArrayList.get(j).side == 2)
					{
						if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 20 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
						{
							System.out.println("Dire death after gliff");
							System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
							killAfterGliffPoints -= 40;
						}
					}
				}
		}
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			//Kill under self towers. Under T1 before 600 sec, Under T2 before 1000 sec
			////T1
			if (((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 39 && killEventArrayList.get(i).y >= 3 && killEventArrayList.get(i).y <= 14) || (killEventArrayList.get(i).x >= 50 && killEventArrayList.get(i).x <= 61 && killEventArrayList.get(i).y >= 38 && killEventArrayList.get(i).y <= 48) || (killEventArrayList.get(i).x >= 83 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 43 && killEventArrayList.get(i).y <= 61)) && killEventArrayList.get(i).second <= 600)
			{
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Dire kill under T1");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
					killUnderSelfTower += 150;
				}
				if (killEventArrayList.get(i).dier >= 5)
				{
					System.out.println("Dire death under T1");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);
					killUnderSelfTower -= 150;
				}
			}
			////T2
			if (((killEventArrayList.get(i).x >= 46 && killEventArrayList.get(i).x <= 58 && killEventArrayList.get(i).y >= 7 && killEventArrayList.get(i).y <= 16) || (killEventArrayList.get(i).x >= 63 && killEventArrayList.get(i).x <= 72 && killEventArrayList.get(i).y >= 30 && killEventArrayList.get(i).y <= 38) || (killEventArrayList.get(i).x >= 84 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 36 && killEventArrayList.get(i).y <= 46)) && killEventArrayList.get(i).second <= 1000)
			{
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Dire kill under T2");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower += 150;
				}
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Dire death under T2");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killUnderSelfTower -= 150;
				}
			}
			//HG
			if (killEventArrayList.get(i).x >= 67 && killEventArrayList.get(i).x <= 100 && killEventArrayList.get(i).y >= 0 && killEventArrayList.get(i).y <= 34)
			{
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Dire kill under HG");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfHG += 200;
				}
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Dire death under HG");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfHG -= 50;
				}
			}

			//Kill on self ancients
			if (killEventArrayList.get(i).x >= 71 && killEventArrayList.get(i).x <= 77 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 59)
			{
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Dire kill under ancients");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfAncients += 100;
				}
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Dire deaths under ancients");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killOnSelfAncients -= 100;
				}
			}
			//Kill in forest (EG)
			if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29) && killEventArrayList.get(i).second < 900)
			{
				if (killEventArrayList.get(i).dier <= 5)
				{
					System.out.println("Dire kill in self forest");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killInSelfForest += 100;
				}
				if (killEventArrayList.get(i).dier >= 6)
				{
					System.out.println("Dire death in self forest");
					System.out.println("X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + " Second:" + killEventArrayList.get(i).second);

					killInSelfForest -= 100;
				}
			}
		}
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).tierLevel == 4)
				continue;
			if (towerEventArrayList.get(i).whoDestroy.contains("Denied"))
				denied += 80;
		}

		System.out.println("Dire KillInSelfForest:" + killInSelfForest);
		System.out.println("Dire KillAfterGliff:" + killAfterGliffPoints);
		System.out.println("Dire KillOnAncients:" + killOnSelfAncients);
		System.out.println("Dire KillUnderSelfTowers:" + killUnderSelfTower);
		System.out.println("Dire Denied:" + denied);
		return killInSelfForest + killAfterGliffPoints + killOnSelfAncients + killUnderSelfTower + killOnSelfHG + denied;
	}

	public Integer analizeRadiantLining(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer lining = 0;
		Double LHF5MPoints = 0.0;
		Double LHF510MPoints = 0.0;
		Double XPMF10MPoints = 0.0;
		Double KDF10MPoints = 0.0;

		Integer KF10M = 0;
		Integer DF10M = 0;
		Integer LHF5M = 0;
		Integer LHF510M = 0;
		Integer XPMF10M = 0;
		Integer KDF10M = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).dier >= 6 && killEventArrayList.get(i).second <= 600)
			{
				KF10M++;
			}
			if (killEventArrayList.get(i).dier <= 5 && killEventArrayList.get(i).second <= 600)
			{
				DF10M++;
			}
		}

		for (int i = 0; i < 5; i++)
		{
			LHF5M += team[0].perMinuteLastHits[i];
			LHF510M += team[0].perMinuteLastHits[i + 5];
		}
		for (int i = 0; i < 10; i++)
		{
			XPMF10M += team[0].minuteXPM[i];
		}

		KDF10M = KF10M - DF10M;

		LHF5MPoints = 0.25 * 1000 * LHF5M / averageDataFactory.avgLHF5M;
		LHF510MPoints = 0.2 * 1000 * LHF510M / averageDataFactory.avgLHF510M;
		KDF10MPoints = 0.35 * 1000 * KDF10M / 2.5;
		XPMF10MPoints = 0.2 * 1000 * XPMF10M / averageDataFactory.avgXPMF10M;

		System.out.println("Radiant LHF5MPoints:" + LHF5MPoints);
		System.out.println("Radiant LHF5-10MPoints:" + LHF510MPoints);
		System.out.println("Radiant KDF10MPoints:" + KDF10MPoints);
		System.out.println("Radiant XPMF10MPoints:" + XPMF10MPoints);

		return (int) (LHF5MPoints + LHF510MPoints + KDF10MPoints + XPMF10MPoints);
	}

	public Integer analizeDireLining(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer lining = 0;
		Double LHF5MPoints = 0.0;
		Double LHF510MPoints = 0.0;
		Double XPMF10MPoints = 0.0;
		Double KDF10MPoints = 0.0;

		Integer KF10M = 0;
		Integer DF10M = 0;
		Integer LHF5M = 0;
		Integer LHF510M = 0;
		Integer XPMF10M = 0;
		Integer KDF10M = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).dier <= 5 && killEventArrayList.get(i).second <= 600)
			{
				KF10M++;
			}
			if (killEventArrayList.get(i).dier >= 6 && killEventArrayList.get(i).second <= 600)
			{
				DF10M++;
			}
		}

		for (int i = 0; i < 5; i++)
		{
			LHF5M += team[1].perMinuteLastHits[i];
			LHF510M += team[1].perMinuteLastHits[i + 5];
		}
		for (int i = 0; i < 10; i++)
		{
			XPMF10M += team[1].minuteXPM[i];
		}

		KDF10M = KF10M - DF10M;

		LHF5MPoints = 0.25 * 1000 * LHF5M / averageDataFactory.avgLHF5M;
		LHF510MPoints = 0.2 * 1000 * LHF510M / averageDataFactory.avgLHF510M;
		KDF10MPoints = 0.35 * 1000 * KDF10M / 2.5;
		XPMF10MPoints = 0.2 * 1000 * XPMF10M / averageDataFactory.avgXPMF10M;

		System.out.println("Radiant LHF5MPoints:" + LHF5MPoints);
		System.out.println("Radiant LHF5-10MPoints:" + LHF510MPoints);
		System.out.println("Radiant KDF10MPoints:" + KDF10MPoints);
		System.out.println("Radiant XPMF10MPoints:" + XPMF10MPoints);

		return (int) (LHF5MPoints + LHF510MPoints + KDF10MPoints + XPMF10MPoints);
	}

	public Integer analizeRadiantKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer killRadiantCounter = 0;
		Integer killDireCounter = 0;
		Integer F10KTime = 0;
		Integer difference = 0;


		Double F10KTPoints = 0.0;
		Integer F10KDifferencePoints = 0;

		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killDireCounter == 10 || killRadiantCounter == 10)
				continue;
			if (killEventArrayList.get(i).dier >= 6)
				killRadiantCounter++;
			else
				killDireCounter++;
			F10KTime = killEventArrayList.get(i).second;
		}
		if (killDireCounter > killRadiantCounter)
		{
			F10KTPoints = 1200 - 600 * F10KTime / averageDataFactory.avgF10KTime;
			if (F10KTPoints < 0)
				F10KTPoints = F10KTPoints * 10 / killRadiantCounter;
			else
				F10KTPoints = F10KTPoints * killRadiantCounter / 10;
		} else
			F10KTPoints = 1200 - 600 * F10KTime / averageDataFactory.avgF10KTime;


		difference = killRadiantCounter - killDireCounter;
		F10KDifferencePoints = difference * 200;
		//System.out.println("Radiant DifferencePoints:"+F10KDifferencePoints);
		//System.out.println("Radiant TimePoints:"+F10KTPoints);

		return (int) (F10KDifferencePoints + F10KTPoints);
	}

	public Integer analizeDireKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer killRadiantCounter = 0;
		Integer killDireCounter = 0;
		Integer F10KTime = 0;
		Integer difference = 0;


		Double F10KTPoints = 0.0;
		Integer F10KDifferencePoints = 0;

		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killDireCounter == 10 || killRadiantCounter == 10)
				continue;
			if (killEventArrayList.get(i).dier >= 6)
				killRadiantCounter++;
			else
				killDireCounter++;
			F10KTime = killEventArrayList.get(i).second;
		}
		if (killDireCounter < killRadiantCounter)
		{
			F10KTPoints = 1200 - 600 * F10KTime / averageDataFactory.avgF10KTime;
			if (F10KTPoints < 0)
				F10KTPoints = F10KTPoints * 10 / killDireCounter;
			else
				F10KTPoints = F10KTPoints * killDireCounter / 10;
		} else
			F10KTPoints = 1200 - 600 * F10KTime / averageDataFactory.avgF10KTime;


		difference = killDireCounter - killRadiantCounter;
		F10KDifferencePoints = difference * 200;
		//	System.out.println("Dire DifferencePoints:"+F10KDifferencePoints);
		//System.out.println("Dire TimePoints:"+F10KTPoints);

		return (int) (F10KDifferencePoints + F10KTPoints);
	}

	public Integer analizeRadiantFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryGPMPoints = 0.0;
		Double miderGPMPoints = 0.0;

		Integer carryGPM = 0;
		Integer miderGPM = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i].role == 2)
				carryGPM = player[i].totalGPM;
			if (player[i].role == 1)
				miderGPM = player[i].totalGPM;
		}

		totalLH += player[0].totalLH + player[1].totalLH + player[2].totalLH + player[3].totalLH + player[4].totalLH;

		carryGPMPoints = 0.25 * 1000 * carryGPM / averageDataFactory.avgCarryGPM;
		miderGPMPoints = 0.25 * 1000 * miderGPM / averageDataFactory.avgMiderGPM;
		totalGPMPoints = 0.25 * 1000 * team[0].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.25 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		System.out.println("Radiant CarryGPMPoints:" + carryGPMPoints);
		System.out.println("Radiant MiderGPMPoints:" + miderGPMPoints);
		System.out.println("Radiant TotalGPMPoints:" + totalGPMPoints);
		System.out.println("Radiant LHMPoints:" + LHMPoints);

		return (int) (carryGPMPoints + miderGPMPoints + totalGPMPoints + LHMPoints);
	}

	public Integer analizeDireFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryGPMPoints = 0.0;
		Double miderGPMPoints = 0.0;

		Integer carryGPM = 0;
		Integer miderGPM = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i + 5].role == 2)
				carryGPM = player[i + 5].totalGPM;
			if (player[i + 5].role == 1)
				miderGPM = player[i + 5].totalGPM;
		}

		totalLH += player[5].totalLH + player[6].totalLH + player[7].totalLH + player[8].totalLH + player[9].totalLH;

		carryGPMPoints = 0.25 * 1000 * carryGPM / averageDataFactory.avgCarryGPM;
		miderGPMPoints = 0.25 * 1000 * miderGPM / averageDataFactory.avgMiderGPM;
		totalGPMPoints = 0.25 * 1000 * team[1].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.25 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		System.out.println("Dire CarryGPMPoints:" + carryGPMPoints);
		System.out.println("Dire MiderGPMPoints:" + miderGPMPoints);
		System.out.println("Dire TotalGPMPoints:" + totalGPMPoints);
		System.out.println("Dire LHMPoints:" + LHMPoints);

		return (int) (carryGPMPoints + miderGPMPoints + totalGPMPoints + LHMPoints);
	}

	public void writeRadiantInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer aggression, Integer pushing, Integer wardAbility, Integer defence, Integer lining, Integer killAbility, Integer farming) throws IOException
	{
		String writingString = "";
		/**General Information [0]**/
		writingString += match.id + ";";
		writingString += match.date + ";";
		writingString += match.matchTime + ";";
		writingString += team[0].id + ";";
		writingString += team[1].id + ";";

		writingString += "##";
		/**TeamInfo [1]**/
		writingString += team[0].kills + ";";
		writingString += team[0].deaths + ";";
		writingString += team[0].assists + ";";

		writingString += "##";
		/**Players And EPP's [2]**/
		writingString += player[0].playerId + ";";
		writingString += player[0].EPP + "||";
		writingString += player[1].playerId + ";";
		writingString += player[1].EPP + "||";
		writingString += player[2].playerId + ";";
		writingString += player[2].EPP + "||";
		writingString += player[3].playerId + ";";
		writingString += player[3].EPP + "||";
		writingString += player[4].playerId + ";";
		writingString += player[4].EPP;
		writingString += "##";
		/**Parameters [3]**/
		writingString += aggression + ";";
		writingString += pushing + ";";
		writingString += wardAbility + ";";
		writingString += defence + ";";
		writingString += lining + ";";
		writingString += killAbility + ";";
		writingString += farming;
		writingString += "##";
		/**FB Information [4]**/
		writingString += match.firstBloodRadiant + ";";
		writingString += match.FBTime;
		writingString += "##";
		/**F10K Information [5]**/
		writingString += match.first10KillsRadiant + ";";
		writingString += match.F10KTime;
		writingString += "##";
		/**FR Information [6]**/
		writingString += match.firstRoshanRadiant + ";";
		writingString += match.FRoshanTime;
		writingString += "##";
		/**KillEvents [7]**/
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			writingString += killEventArrayList.get(i).dier + ";";
			writingString += killEventArrayList.get(i).second;
			if (i != killEventArrayList.size() - 1)
				writingString += "||";
		}
		writingString += "##";
		/**Rating Changes [8]**/

	}

	public void writeDireInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer aggression, Integer pushing, Integer wardAbility, Integer defence, Integer lining, Integer killAbility, Integer farming) throws IOException
	{
		String writingString = "";
		/**General Information [0]**/
		writingString += match.id + ";";
		writingString += match.date + ";";
		writingString += match.matchTime + ";";
		writingString += team[0].id + ";";
		writingString += team[1].id + ";";

		writingString += "##";
		/**TeamInfo [1]**/
		writingString += team[1].kills + ";";
		writingString += team[1].deaths + ";";
		writingString += team[1].assists + ";";

		writingString += "##";
		/**Players And EPP's [2]**/
		writingString += player[5].playerId + ";";
		writingString += player[5].EPP + "||";
		writingString += player[6].playerId + ";";
		writingString += player[6].EPP + "||";
		writingString += player[7].playerId + ";";
		writingString += player[7].EPP + "||";
		writingString += player[8].playerId + ";";
		writingString += player[8].EPP + "||";
		writingString += player[9].playerId + ";";
		writingString += player[9].EPP;
		writingString += "##";
		/**Parameters [3]**/
		writingString += aggression + ";";
		writingString += pushing + ";";
		writingString += wardAbility + ";";
		writingString += defence + ";";
		writingString += lining + ";";
		writingString += killAbility + ";";
		writingString += farming;
		writingString += "##";
		/**FB Information [4]**/
		writingString += match.firstBloodRadiant + ";";
		writingString += match.FBTime;
		writingString += "##";
		/**F10K Information [5]**/
		writingString += match.first10KillsRadiant + ";";
		writingString += match.F10KTime;
		writingString += "##";
		/**FR Information [6]**/
		writingString += match.firstRoshanRadiant + ";";
		writingString += match.FRoshanTime;
		writingString += "##";
		/**KillEvents [7]**/
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			writingString += killEventArrayList.get(i).dier + ";";
			writingString += killEventArrayList.get(i).second;
			if (i != killEventArrayList.size() - 1)
				writingString += "||";
		}
		writingString += "##";
		/**Rating Changes [8]**/
	}
}

