package ProjectDir.Analitics;

import ProjectDir.AverageDataFactory;
import ProjectDir.DataWorker;
import ProjectDir.FileOperationsFactory;
import ProjectDir.MatchInfo.*;
import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	AverageDataFactory averageDataFactory = new AverageDataFactory();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void analizeMatch(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException, ParseException
	{

		/**Create team file if not exists**/
		fileControlFactory.createTeamFileIfNotExists(team[0].id);
		fileControlFactory.createTeamFileIfNotExists(team[1].id);
		/**Start analizing**/
		/****Aggression**/
		Integer radiantKillAbility = 1;
		String radiantKillAbilityString = analizeRadiantKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
		Integer direKillAbility = 1;
		String direKillAbilityString = analizeDireKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
		//System.out.println("Radiant KillAbility:" + radiantKillAbility);
		//System.out.println("Dire KillAbility:" + direKillAbility);

		/*****Pushing****/
		Integer radiantPushing = analizeRadiantPushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direPushing = analizeDirePushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Pushing:" + radiantPushing);
		//System.out.println("Dire Pushing:" + direPushing);
		if (direPushing < -400 || direPushing > 2500 || radiantPushing < -400 || radiantPushing > 2500)
		{
			System.out.println(match.id);
			System.out.println("Radiant Pushing:" + radiantPushing);
			System.out.println("Dire Pushing:" + direPushing);
			Integer testRadiantPushing = testRadiantPushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			Integer testDirePushing = testDirePushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			System.out.println();
		}
		/**** Vision****/
		Integer radiantVision = analizeRadiantVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direVision = analizeDireVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Vision:" + radiantVision);
		//System.out.println("Dire Vision:" + direVision);
		if (direVision < -1000 || direVision > 1800 || radiantVision < -1000 || radiantVision > 1800)

		{
			System.out.println(match.id);
			System.out.println("Radiant Vision:" + radiantVision);
			System.out.println("Dire Vision:" + direVision);
			Integer testRadiantVision = testRadiantVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			Integer testDireVision = testDireVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			System.out.println();
		}

		/*****Lining****/
		Integer radiantLining = analizeRadiantLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direLining = analizeDireLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Lining:" + radiantLining);
		//System.out.println("Dire Lining:" + direLining);
		if (direLining < -2400 || direLining > 2000 || radiantLining < -2400 || radiantLining > 2000)

		{
			System.out.println(match.id);
			System.out.println("Radiant Lining:" + radiantLining);
			System.out.println("Dire Lining:" + direLining);
			Integer testRadiantLining = testRadiantLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			Integer testDireLining = testDireLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			System.out.println();
		}

		/*****TenKills****/
		String radiantTenKills = analizeRadiantTenKills(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		String direTenKills = analizeDireTenKills(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant TenKills:" + radiantTenKills);
		//System.out.println("Dire TenKills:" + direTenKills);


		/*****Farming****/
		Integer radiantFarming = analizeRadiantFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direFarming = analizeDireFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant Farming:" + radiantFarming);
		//System.out.println("Dire Farming:" + direFarming);
		if (direFarming < 200 || direFarming > 2000 || radiantFarming < 200 || radiantFarming > 2000)
		{
			System.out.println(match.id);
			System.out.println("Radiant Farming:" + radiantFarming);
			System.out.println("Dire Farming:" + direFarming);
			Integer testRadiantFarming = testRadiantFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			Integer testDireFarming = testDireFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
			System.out.println();
		}
		/****FB****/
		String radiantFB = analizeRadiantFB(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		String direFB = analizeDireFB(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant FB:" + radiantFB);
		//System.out.println("Dire FB:" + direFB);

		/**FBRatings**/
		ArrayList<String> radiantFBMarks = new ArrayList<>();
		ArrayList<String> direFBMarks = new ArrayList<>();
		Date matchDate = formatter.parse(match.date);
		String team1Id = team[0].id;
		String team2Id = team[1].id;
		radiantFBMarks = getFBMarks(team1Id, matchDate);
		direFBMarks = getFBMarks(team2Id, matchDate);
		String radiantFBRating = getFBRating(radiantFBMarks);
		String direFBRating = getFBRating(direFBMarks);
		/**F10KRating**/
		ArrayList<String> radiantF10KMarks = new ArrayList<>();
		ArrayList<String> direF10KMarks = new ArrayList<>();
		radiantF10KMarks = getF10KMarks(team1Id, matchDate);
		direF10KMarks = getF10KMarks(team2Id, matchDate);
		String radiantF10KRating = getF10KRating(radiantF10KMarks);
		String direF10KRating = getF10KRating(direF10KMarks);
		/**MatchHardness**/
		String matchHardness=calculateMatchHardness(team[0].kills+"",team[1].kills+"",team[0].totalGold+"",team[1].totalGold+"",match.matchTime+"");
		/**End analizing**/
		/*** Write info to file **/

		///Write that match was analized
		writeRadiantInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, radiantKillAbility, radiantPushing, radiantVision, radiantLining, radiantTenKills, radiantFarming, radiantFB, direKillAbility, direPushing, direVision, direLining, direTenKills, direFarming, direFB, direFBRating, direF10KRating, matchHardness);
		writeDireInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, radiantKillAbility, radiantPushing, radiantVision, radiantLining, radiantTenKills, radiantFarming, radiantFB, direKillAbility, direPushing, direVision, direLining, direTenKills, direFarming, direFB, radiantFBRating, radiantF10KRating,matchHardness);
		writePlayersInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

		System.out.println("Match " + match.id + " was analized.");
	}

	//<editor-fold desc="Parameters">
	public String analizeRadiantKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		Double EGKillPoints = 0.0;
		Double MGKillPoints = 0.0;
		Double EGDefPoints = 0.0;
		Double MGDefPoints = 0.0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second < 900)
			{
				//<editor-fold desc="Usual Kill">
				////Usual kill
				if (killEventArrayList.get(i).dier >= 6)
				{

					EGKillPoints += 15;
				}
				////Usual death
				if (killEventArrayList.get(i).dier <= 5)
				{
					EGKillPoints -= 15;
				}
				//</editor-fold>
				//<editor-fold desc="Kill with multiple assists">
				////Kills KillAssist>2
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier >= 6)
				{
					EGKillPoints += 15;
				}
				////Kills KillAssist>3
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier >= 6)
				{
					EGKillPoints += 5;
				}
				//</editor-fold>
				//<editor-fold desc="Kill under Roshan">
				if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 53)
				{
					for (int j = 0; j < roshanEventArrayList.size(); j++)
					{
						if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
						{
							if (killEventArrayList.get(i).dier >= 6)
							{
								//System.out.println("Radiant Roshan Kill X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
								EGKillPoints += 30;
							}
							if (killEventArrayList.get(i).dier <= 5)
							{
								//System.out.println("Radiant Roshan death X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
								EGKillPoints -= 30;
							}
						}
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill after Glyph">
				if (killEventArrayList.get(i).dier >= 6)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 1)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Radiant kill after glyph X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
								EGDefPoints += 20;
							}
						}
					}
				if (killEventArrayList.get(i).dier <= 5)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 1)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Radiant die after glyph X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
								EGDefPoints -= 20;
							}
						}
					}
				//</editor-fold>

				//<editor-fold desc="Kill in enemy forest">
				//EG kill in enemy forest
				if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill in enemy forest X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints += 30;
					} else
					{
						//System.out.println("Radiant death in enemy forest X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints -= 30;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under  enemy T1">
				//<editor-fold desc="Kill under T1">
				if (killEventArrayList.get(i).dier >= 6)
				{
					if ((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y <= 16 && killEventArrayList.get(i).y >= 6))
					{
						//System.out.println("Radiant enemy T1 kill X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints += 30;
					}
					if ((killEventArrayList.get(i).x >= 51 && killEventArrayList.get(i).x <= 60 && killEventArrayList.get(i).y <= 50 && killEventArrayList.get(i).y >= 38))
					{
						//System.out.println("Radiant enemy T1 kill X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints += 30;
					}
					if ((killEventArrayList.get(i).x >= 81 && killEventArrayList.get(i).x <= 97 && killEventArrayList.get(i).y <= 62 && killEventArrayList.get(i).y >= 45))
					{
						//System.out.println("Radiant enemy T1 kill X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints += 30;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Death under T1">
				if (killEventArrayList.get(i).dier <= 5)
				{
					if ((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y <= 16 && killEventArrayList.get(i).y >= 6))
					{
						//System.out.println("Radiant enemy T1 death X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);
						EGKillPoints -= 30;
					}
					if ((killEventArrayList.get(i).x >= 51 && killEventArrayList.get(i).x <= 60 && killEventArrayList.get(i).y <= 49 && killEventArrayList.get(i).y >= 38))
					{
						//System.out.println("Radiant enemy T1 death X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints -= 30;
					}
					if ((killEventArrayList.get(i).x >= 81 && killEventArrayList.get(i).x <= 97 && killEventArrayList.get(i).y <= 62 && killEventArrayList.get(i).y >= 45))
					{
						//System.out.println("Radiant enemy T1 death X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints -= 30;
					}
				}
				//</editor-fold>
				//</editor-fold>
				//<editor-fold desc="Kill under enemy T2">
				if (((killEventArrayList.get(i).x >= 46 && killEventArrayList.get(i).x <= 58 && killEventArrayList.get(i).y >= 7 && killEventArrayList.get(i).y <= 16) || (killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).x <= 72 && killEventArrayList.get(i).y >= 30 && killEventArrayList.get(i).y <= 39) || (killEventArrayList.get(i).x >= 84 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 36 && killEventArrayList.get(i).y <= 46)))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant enemy T2 kill X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints += 40;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant enemy T2 death X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints -= 40;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under enemy Ancients">
				if (killEventArrayList.get(i).x >= 71 && killEventArrayList.get(i).x <= 77 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 59)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under enemy anci X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints += 20;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant deaths under enemy anci X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGKillPoints -= 20;
					}
				}
				//</editor-fold>

				//<editor-fold desc="Kill under self T1">
				if (((killEventArrayList.get(i).x >= 0 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 35 && killEventArrayList.get(i).y <= 50) || (killEventArrayList.get(i).x >= 36 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 60) || (killEventArrayList.get(i).x >= 72 && killEventArrayList.get(i).x <= 84 && killEventArrayList.get(i).y >= 84 && killEventArrayList.get(i).y <= 98)) && killEventArrayList.get(i).second <= 900)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self T1 X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints += 30;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self T1 X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self T2">
				if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 49 && killEventArrayList.get(i).y <= 65) || (killEventArrayList.get(i).x >= 27 && killEventArrayList.get(i).x <= 34 && killEventArrayList.get(i).y >= 64 && killEventArrayList.get(i).y <= 68) || (killEventArrayList.get(i).x >= 42 && killEventArrayList.get(i).x <= 54 && killEventArrayList.get(i).y >= 85 && killEventArrayList.get(i).y <= 95)) && killEventArrayList.get(i).second <= 1200)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self T2 X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints += 40;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant deaths under self T2 X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints -= 40;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self Ancients">
				if (killEventArrayList.get(i).x >= 30 && killEventArrayList.get(i).x <= 36 && killEventArrayList.get(i).y >= 46 && killEventArrayList.get(i).y <= 52)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self anci X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self anci X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self forest">
				if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self forest X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints += 30;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self forest X:" + killEventArrayList.get(i).x + " Y:" + killEventArrayList.get(i).y + "Second:" + killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
				}
				//</editor-fold>

			} else if (killEventArrayList.get(i).second >= 900 && killEventArrayList.get(i).second < 1800)
			{
				//<editor-fold desc="Usual Kill">
				////Usual kill
				if (killEventArrayList.get(i).dier >= 6)
				{
					MGKillPoints += 15;
				}
				////Usual death
				if (killEventArrayList.get(i).dier <= 5)
				{
					MGKillPoints -= 15;
				}
				//</editor-fold>
				//<editor-fold desc="Kill with multiple assists">
				////Kills KillAssist>2
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier >= 6)
				{
					MGKillPoints += 15;
				}
				////Kills KillAssist>3
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier >= 6)
				{
					MGKillPoints += 5;
				}
				//</editor-fold>
				//<editor-fold desc="Kill under Roshan">
				if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 53)
				{
					for (int j = 0; j < roshanEventArrayList.size(); j++)
					{
						if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
						{
							if (killEventArrayList.get(i).dier >= 6)
							{
								//System.out.println("Radiant kill Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGKillPoints += 40;
							}
							if (killEventArrayList.get(i).dier <= 5)
							{
								//System.out.println("Radiant death Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGKillPoints -= 40;
							}
						}
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill after Glyph">
				if (killEventArrayList.get(i).dier >= 6)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 1)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Radiant kill after glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGDefPoints += 20;
							}
						}
					}
				if (killEventArrayList.get(i).dier <= 5)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 1)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Radiant death after glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGDefPoints -= 20;
							}
						}
					}
				//</editor-fold>

				//<editor-fold desc="Kill in enemy forest">
				//MG kill in enemy forest
				if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 20;
					} else
					{
						//System.out.println("Radiant death in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under enemy T2">
				if (((killEventArrayList.get(i).x >= 46 && killEventArrayList.get(i).x <= 58 && killEventArrayList.get(i).y >= 7 && killEventArrayList.get(i).y <= 16) || (killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).x <= 72 && killEventArrayList.get(i).y >= 30 && killEventArrayList.get(i).y <= 39) || (killEventArrayList.get(i).x >= 84 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 36 && killEventArrayList.get(i).y <= 46)))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under  enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 30;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 30;
					}
				}
				//</editor-fold>
				/**
				 //<editor-fold desc="Kill under enemy HG">
				 if (killEventArrayList.get(i).x >= 63 && killEventArrayList.get(i).x <= 100 && killEventArrayList.get(i).y >= 0 && killEventArrayList.get(i).y <= 36)
				 {
				 if (killEventArrayList.get(i).dier >= 6)
				 {
				 //System.out.println("Radiant kill under enemy HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGKillPoints += 15;
				 }
				 if (killEventArrayList.get(i).dier <= 5)
				 {
				 //System.out.println("Radiant death under enemy HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGKillPoints -= 45;
				 }
				 }
				 //</editor-fold
				 **/
				//<editor-fold desc="Kill under enemy Ancients">
				if (killEventArrayList.get(i).x >= 71 && killEventArrayList.get(i).x <= 77 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 59)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 30;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 30;
					}
				}
				//</editor-fold>

				//<editor-fold desc="Kill under self T2">
				if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 49 && killEventArrayList.get(i).y <= 65) || (killEventArrayList.get(i).x >= 27 && killEventArrayList.get(i).x <= 34 && killEventArrayList.get(i).y >= 64 && killEventArrayList.get(i).y <= 68) || (killEventArrayList.get(i).x >= 42 && killEventArrayList.get(i).x <= 54 && killEventArrayList.get(i).y >= 85 && killEventArrayList.get(i).y <= 95)) && killEventArrayList.get(i).second <= 1300)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints -= 20;
					}
				}
				//</editor-fold>
				/**
				 //<editor-fold desc="Kill under self HG">
				 if (killEventArrayList.get(i).x >= 0 && killEventArrayList.get(i).x <= 31 && killEventArrayList.get(i).y >= 68 && killEventArrayList.get(i).y <= 100)
				 {
				 if (killEventArrayList.get(i).dier >= 6)
				 {
				 //System.out.println("Radiant kill under self HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGDefPoints += 45;
				 }
				 if (killEventArrayList.get(i).dier <= 5)
				 {
				 //System.out.println("Radiant death under self HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGDefPoints -= 15;
				 }
				 }
				 //</editor-fold>
				 **/
				//<editor-fold desc="Kill under self Ancients">
				if (killEventArrayList.get(i).x >= 30 && killEventArrayList.get(i).x <= 36 && killEventArrayList.get(i).y >= 46 && killEventArrayList.get(i).y <= 52)
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self ancients X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self ancients X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self forest">
				if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64))
				{
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Radiant kill under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints += 30;
					}
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Radiant death under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints -= 30;
					}
				}
				//</editor-fold>
			}
		}
		return EGKillPoints + "||" + EGDefPoints + "##" + MGKillPoints + "||" + MGDefPoints;
	}

	public String analizeDireKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		Integer time = 30;

		Double EGKillPoints = 0.0;
		Double MGKillPoints = 0.0;
		Double EGDefPoints = 0.0;
		Double MGDefPoints = 0.0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second < 900)
			{
				//<editor-fold desc="Usual Kill">
				////Usual kill
				if (killEventArrayList.get(i).dier <= 5)
				{
					EGKillPoints += 15;
				}
				////Usual death
				if (killEventArrayList.get(i).dier >= 6)
				{
					EGKillPoints -= 15;
				}
				//</editor-fold>
				//<editor-fold desc="Kill with multiple assists">
				////Kills KillAssist>2
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier <= 5)
				{
					EGKillPoints += 15;
				}
				////Kills KillAssist>3
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier <= 5)
				{
					EGKillPoints += 5;
				}
				//</editor-fold>
				//<editor-fold desc="Kill under Roshan">
				if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 53)
				{
					for (int j = 0; j < roshanEventArrayList.size(); j++)
					{
						if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
						{
							if (killEventArrayList.get(i).dier <= 5)
							{
								//System.out.println("Dire kill under Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								EGKillPoints += 30;
							}
							if (killEventArrayList.get(i).dier >= 6)
							{
								//System.out.println("Dire death under Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								EGKillPoints -= 30;
							}
						}
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill after Glyph">
				if (killEventArrayList.get(i).dier <= 5)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 2)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Dire kill after Glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								EGDefPoints += 20;
							}
						}
					}
				if (killEventArrayList.get(i).dier >= 6)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 2)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Dire death after glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								EGDefPoints -= 20;
							}
						}
					}
				//</editor-fold>

				//<editor-fold desc="Kill in enemy forest">
				//EG kill in enemy forest
				if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints += 30;
					} else
					{
						//System.out.println("Dire death in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints -= 30;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under  enemy T1">

				if ((killEventArrayList.get(i).x >= 0 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 35 && killEventArrayList.get(i).y <= 50) || (killEventArrayList.get(i).x >= 36 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 60) || (killEventArrayList.get(i).x >= 72 && killEventArrayList.get(i).x <= 84 && killEventArrayList.get(i).y >= 84 && killEventArrayList.get(i).y <= 98))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under enemy T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);
						EGKillPoints += 30;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under enemy T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);
						EGKillPoints -= 30;
					}

				}
				//</editor-fold>
				//<editor-fold desc="Kill under enemy T2">
				if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 49 && killEventArrayList.get(i).y <= 65) || (killEventArrayList.get(i).x >= 27 && killEventArrayList.get(i).x <= 34 && killEventArrayList.get(i).y >= 64 && killEventArrayList.get(i).y <= 68) || (killEventArrayList.get(i).x >= 42 && killEventArrayList.get(i).x <= 54 && killEventArrayList.get(i).y >= 85 && killEventArrayList.get(i).y <= 95)))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints += 40;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints -= 40;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under enemy Ancients">
				if (killEventArrayList.get(i).x >= 30 && killEventArrayList.get(i).x <= 36 && killEventArrayList.get(i).y >= 46 && killEventArrayList.get(i).y <= 52)
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints += 20;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGKillPoints -= 20;
					}
				}
				//</editor-fold>

				//<editor-fold desc="Kill under self T1">

				if (killEventArrayList.get(i).dier <= 5)
				{
					if ((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y <= 16 && killEventArrayList.get(i).y >= 6))
					{
						//System.out.println("Dire kill under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);
						EGDefPoints += 30;
					}
					if ((killEventArrayList.get(i).x >= 51 && killEventArrayList.get(i).x <= 60 && killEventArrayList.get(i).y <= 50 && killEventArrayList.get(i).y >= 38))
					{
						//System.out.println("Dire kill under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints += 30;
					}
					if ((killEventArrayList.get(i).x >= 81 && killEventArrayList.get(i).x <= 97 && killEventArrayList.get(i).y <= 62 && killEventArrayList.get(i).y >= 45))
					{
						//System.out.println("Dire kill under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints += 30;
					}
				}
				if (killEventArrayList.get(i).dier >= 6)
				{
					if ((killEventArrayList.get(i).x >= 17 && killEventArrayList.get(i).x <= 45 && killEventArrayList.get(i).y <= 16 && killEventArrayList.get(i).y >= 6))
					{
						//System.out.println("Dire death under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
					if ((killEventArrayList.get(i).x >= 51 && killEventArrayList.get(i).x <= 60 && killEventArrayList.get(i).y <= 50 && killEventArrayList.get(i).y >= 38))
					{
						//System.out.println("Dire death under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
					if ((killEventArrayList.get(i).x >= 81 && killEventArrayList.get(i).x <= 97 && killEventArrayList.get(i).y <= 62 && killEventArrayList.get(i).y >= 45))
					{
						//System.out.println("Dire death under self T1 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self T2">
				if (((killEventArrayList.get(i).x >= 46 && killEventArrayList.get(i).x <= 58 && killEventArrayList.get(i).y >= 7 && killEventArrayList.get(i).y <= 16) || (killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).x <= 72 && killEventArrayList.get(i).y >= 30 && killEventArrayList.get(i).y <= 39) || (killEventArrayList.get(i).x >= 84 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 36 && killEventArrayList.get(i).y <= 46)))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints += 40;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 40;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self Ancients">
				if (killEventArrayList.get(i).x >= 71 && killEventArrayList.get(i).x <= 77 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 59)
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self forest">
				if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints += 30;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						EGDefPoints -= 30;
					}
				}
				//</editor-fold>
			} else if (killEventArrayList.get(i).second >= 900 && killEventArrayList.get(i).second < 1800)
			{
				//<editor-fold desc="Usual Kill">
				////Usual kill
				if (killEventArrayList.get(i).dier <= 5)
				{
					MGKillPoints += 15;
				}
				////Usual death
				if (killEventArrayList.get(i).dier >= 6)
				{
					MGKillPoints -= 15;
				}
				//</editor-fold>
				//<editor-fold desc="Kill with multiple assists">
				////Kills KillAssist>2
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).dier <= 5)
				{
					MGKillPoints += 15;
				}
				////Kills KillAssist>3
				if (killEventArrayList.get(i).killers[0] != 0 && killEventArrayList.get(i).killers[1] != 0 && killEventArrayList.get(i).killers[2] != 0 && killEventArrayList.get(i).killers[3] != 0 && killEventArrayList.get(i).dier <= 5)
				{
					MGKillPoints += 5;
				}
				//</editor-fold>
				//<editor-fold desc="Kill under Roshan">
				if (killEventArrayList.get(i).x <= 79 && killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 53)
				{
					for (int j = 0; j < roshanEventArrayList.size(); j++)
					{
						if (roshanEventArrayList.get(j).second >= killEventArrayList.get(i).second - 60 && roshanEventArrayList.get(j).second <= killEventArrayList.get(i).second + 60)
						{
							if (killEventArrayList.get(i).dier <= 5)
							{
								//System.out.println("Dire kill under Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGKillPoints += 40;
							}
							if (killEventArrayList.get(i).dier >= 6)
							{
								//System.out.println("Dire death under Roshan X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGKillPoints -= 40;
							}
						}
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill after Glyph">
				if (killEventArrayList.get(i).dier <= 5)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 2)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Dire kill after glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGDefPoints += 20;
							}
						}
					}
				if (killEventArrayList.get(i).dier >= 6)
					for (int j = 0; j < glyphEventArrayList.size(); j++)
					{
						if (glyphEventArrayList.get(j).side == 2)
						{
							if (killEventArrayList.get(i).second <= glyphEventArrayList.get(j).second + 15 && killEventArrayList.get(i).second >= glyphEventArrayList.get(j).second)
							{
								//System.out.println("Dire death after glyph X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

								MGDefPoints -= 20;
							}
						}
					}
				//</editor-fold>

				//<editor-fold desc="Kill in enemy forest">
				if ((killEventArrayList.get(i).x >= 37 && killEventArrayList.get(i).x <= 81 && killEventArrayList.get(i).y <= 84 && killEventArrayList.get(i).y >= 65) && !(killEventArrayList.get(i).x >= 59 && killEventArrayList.get(i).x <= 80 && killEventArrayList.get(i).y <= 69 && killEventArrayList.get(i).y >= 64))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 20;
					} else
					{
						//System.out.println("Dire death in enemy forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under enemy T2">
				if (((killEventArrayList.get(i).x >= 4 && killEventArrayList.get(i).x <= 15 && killEventArrayList.get(i).y >= 49 && killEventArrayList.get(i).y <= 65) || (killEventArrayList.get(i).x >= 27 && killEventArrayList.get(i).x <= 34 && killEventArrayList.get(i).y >= 64 && killEventArrayList.get(i).y <= 68) || (killEventArrayList.get(i).x >= 42 && killEventArrayList.get(i).x <= 54 && killEventArrayList.get(i).y >= 85 && killEventArrayList.get(i).y <= 95)))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 30;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under enemy T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 30;
					}
				}
				//</editor-fold>
				/**
				 //<editor-fold desc="Kill under enemy HG">
				 if (killEventArrayList.get(i).x >= 0 && killEventArrayList.get(i).x <= 31 && killEventArrayList.get(i).y >= 68 && killEventArrayList.get(i).y <= 100)
				 {
				 if (killEventArrayList.get(i).dier <= 5)
				 {
				 //System.out.println("Dire kill under enemy HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGKillPoints += 15;
				 }
				 if (killEventArrayList.get(i).dier >= 6)
				 {
				 //System.out.println("Dire death under enemy HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGKillPoints -= 45;
				 }
				 }
				 //</editor-fold
				 **/
				//<editor-fold desc="Kill under enemy Ancients">
				if (killEventArrayList.get(i).x >= 30 && killEventArrayList.get(i).x <= 36 && killEventArrayList.get(i).y >= 46 && killEventArrayList.get(i).y <= 52)
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints += 20;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under enemy anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGKillPoints -= 20;
					}
				}
				//</editor-fold>

				//<editor-fold desc="Kill under self T2">
				if (((killEventArrayList.get(i).x >= 46 && killEventArrayList.get(i).x <= 58 && killEventArrayList.get(i).y >= 7 && killEventArrayList.get(i).y <= 16) || (killEventArrayList.get(i).x >= 62 && killEventArrayList.get(i).x <= 72 && killEventArrayList.get(i).y >= 30 && killEventArrayList.get(i).y <= 39) || (killEventArrayList.get(i).x >= 84 && killEventArrayList.get(i).x <= 99 && killEventArrayList.get(i).y >= 36 && killEventArrayList.get(i).y <= 46)))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints += 30;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self T2 X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints -= 30;
					}
				}
				//</editor-fold>
				/**
				 //<editor-fold desc="Kill under self HG">
				 if (killEventArrayList.get(i).x >= 65 && killEventArrayList.get(i).x <= 100 && killEventArrayList.get(i).y >= 0 && killEventArrayList.get(i).y <= 36)
				 {
				 if (killEventArrayList.get(i).dier <= 5)
				 {
				 //System.out.println("Dire kill under self HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGDefPoints += 45;
				 }
				 if (killEventArrayList.get(i).dier >= 6)
				 {
				 //System.out.println("Dire death under self HG X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

				 MGDefPoints -= 15;
				 }
				 }
				 //</editor-fold>
				 **/
				//<editor-fold desc="Kill under self Ancients">
				if (killEventArrayList.get(i).x >= 71 && killEventArrayList.get(i).x <= 77 && killEventArrayList.get(i).y >= 52 && killEventArrayList.get(i).y <= 59)
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self anc X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);

						MGDefPoints -= 20;
					}
				}
				//</editor-fold>
				//<editor-fold desc="Kill under self forest">
				if ((killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 62 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 16) && !(killEventArrayList.get(i).x >= 21 && killEventArrayList.get(i).x <= 35 && killEventArrayList.get(i).y <= 35 && killEventArrayList.get(i).y >= 29))
				{
					if (killEventArrayList.get(i).dier <= 5)
					{
						//System.out.println("Dire kill under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);
						MGDefPoints += 20;
					}
					if (killEventArrayList.get(i).dier >= 6)
					{
						//System.out.println("Dire death under self forest X:"+killEventArrayList.get(i).x+" Y:"+killEventArrayList.get(i).y+"Second:"+killEventArrayList.get(i).second);
						MGDefPoints -= 20;
					}
				}
				//</editor-fold>
			}
		}
		return EGKillPoints + "||" + EGDefPoints + "##" + MGKillPoints + "||" + MGDefPoints;
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

		LHF5MPoints = (LHF5M - averageDataFactory.avgLHF5M + 10) * 15;
		LHF510MPoints = (LHF510M - averageDataFactory.avgLHF510M + 10) * 15;
		KDF10MPoints = 0.3 * 1000 * KDF10M / 5;
		XPMF10MPoints = (XPMF10M - averageDataFactory.avgXPMF10M) / 1000 * 50;

		//System.out.println("Radiant LHF5MPoints:" + LHF5MPoints);
		//System.out.println("Radiant LHF5-10MPoints:" + LHF510MPoints);
		//System.out.println("Radiant KDF10MPoints:" + KDF10MPoints);
		//System.out.println("Radiant XPMF10MPoints:" + XPMF10MPoints);

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

		LHF5MPoints = (LHF5M - averageDataFactory.avgLHF5M + 10) * 15;
		LHF510MPoints = (LHF510M - averageDataFactory.avgLHF510M + 10) * 15;
		KDF10MPoints = 0.3 * 1000 * KDF10M / 5;
		XPMF10MPoints = (XPMF10M - averageDataFactory.avgXPMF10M) / 1000 * 50;

		//System.out.println("Dire LHF5MPoints:" + LHF5MPoints);
		//System.out.println("Dire LHF5-10MPoints:" + LHF510MPoints);
		//System.out.println("Dire KDF10MPoints:" + KDF10MPoints);
		//System.out.println("Dire XPMF10MPoints:" + XPMF10MPoints);

		return (int) (LHF5MPoints + LHF510MPoints + KDF10MPoints + XPMF10MPoints);
	}


	public Integer analizeRadiantFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryLHPoints = 0.0;
		Double miderLHPoints = 0.0;

		Integer carryLH = 0;
		Integer miderLH = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i].role == 2)
				carryLH = player[i].totalLH;
			if (player[i].role == 1)
				miderLH = player[i].totalLH;
		}

		totalLH += player[0].totalLH + player[1].totalLH + player[2].totalLH + player[3].totalLH + player[4].totalLH;

		carryLHPoints = (0.35 * 1000 * carryLH / match.matchTime) / averageDataFactory.avgCarryLHM;
		miderLHPoints = (0.3 * 1000 * miderLH / match.matchTime) / averageDataFactory.avgMiderLHM;
		totalGPMPoints = 0.15 * 1000 * team[0].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.2 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		if (carryLHPoints == 0)
			carryLHPoints = (0.35 * 1000 * averageDataFactory.avgCarryLHM * 0.75) / averageDataFactory.avgCarryLHM;
		if (miderLHPoints == 0)
			miderLHPoints = (0.3 * 1000 * averageDataFactory.avgMiderLHM * 0.75) / averageDataFactory.avgMiderLHM;

		//System.out.println("Radiant CarryGPMPoints:" + carryLHPoints);
		//System.out.println("Radiant MiderGPMPoints:" + miderLHPoints);
		//System.out.println("Radiant TotalGPMPoints:" + totalGPMPoints);
		//System.out.println("Radiant LHMPoints:" + LHMPoints);

		return (int) (carryLHPoints + miderLHPoints + totalGPMPoints + LHMPoints);
	}

	public Integer analizeDireFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryLHPoints = 0.0;
		Double miderLHPoints = 0.0;

		Integer carryLH = 0;
		Integer miderLH = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i + 5].role == 2)
				carryLH = player[i + 5].totalLH;
			if (player[i + 5].role == 1)
				miderLH = player[i + 5].totalLH;
		}

		totalLH += player[5].totalLH + player[6].totalLH + player[7].totalLH + player[8].totalLH + player[9].totalLH;

		carryLHPoints = (0.35 * 1000 * carryLH / match.matchTime) / averageDataFactory.avgCarryLHM;
		miderLHPoints = (0.3 * 1000 * miderLH / match.matchTime) / averageDataFactory.avgMiderLHM;
		totalGPMPoints = 0.15 * 1000 * team[1].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.2 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		if (carryLHPoints == 0)
			carryLHPoints = (0.35 * 1000 * averageDataFactory.avgCarryLHM * 0.75) / averageDataFactory.avgCarryLHM;
		if (miderLHPoints == 0)
			miderLHPoints = (0.3 * 1000 * averageDataFactory.avgMiderLHM * 0.75) / averageDataFactory.avgMiderLHM;

		//System.out.println("Dire CarryGPMPoints:" + carryLHPoints);
		//System.out.println("Dire MiderGPMPoints:" + miderLHPoints);
		//System.out.println("Dire TotalGPMPoints:" + totalGPMPoints);
		//System.out.println("Dire LHMPoints:" + LHMPoints);

		return (int) (carryLHPoints + miderLHPoints + totalGPMPoints + LHMPoints);
	}

	public Integer analizeRadiantPushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Integer rowPushing = 0;
		Integer splitPushing = 0;
		Double t1SecondsPushing = 0.0;
		Double t2SecondsPushing = 0.0;
		Double t3SecondsPushing = 0.0;
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
							rowPushing += 100;
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
				t1SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 2)
				t2SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 3)
				t3SecondsPushing += 150 - (0.075 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;
		//System.out.println("Radiant SecondPushingPoints:" + secondsPushing);
		//System.out.println("Radiant RowPushingPoints:" + rowPushing);
		//System.out.println("Radiant SplitPushingPoints:" + splitPushing);
		pushing = (int) (secondsPushing + rowPushing + splitPushing);
		return pushing;
	}

	public Integer analizeDirePushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Double t1SecondsPushing = 0.0;
		Double t2SecondsPushing = 0.0;
		Double t3SecondsPushing = 0.0;
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
							rowPushing += 100;
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
				t1SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 2)
				t2SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 3)
				t3SecondsPushing += 150 - (0.075 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;
		//System.out.println("Dire SecondPushingPoints:" + secondsPushing);
		//System.out.println("Dire RowPushingPoints:" + rowPushing);
		//System.out.println("Dire SplitPushingPoints:" + splitPushing);
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
							killUnderWardPoints += 50;
						}
					}
				}
			}
		}
		avgLifeTimeOfWardPoints = (wardLifeTime / wardsPlaced - averageDataFactory.avgWardLifeTime) * 5;
		//System.out.println("Radiant AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		//System.out.println("Radiant WardsDestroyedPoints:" + wardsDestroyedPoints);
		//System.out.println("Radiant KillUnderWard:" + killUnderWardPoints);

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
							killUnderWardPoints += 50;
						}
					}
				}
			}
		}

		avgLifeTimeOfWardPoints = (wardLifeTime / wardsPlaced - averageDataFactory.avgWardLifeTime) * 5;
		//System.out.println("Dire AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		//System.out.println("Dire WardsDestroyedPoints:" + wardsDestroyedPoints);
		//System.out.println("Dire KillUnderWard:" + killUnderWardPoints);

		return (int) (killUnderWardPoints + wardsDestroyedPoints + avgLifeTimeOfWardPoints);
	}


	//</editor-fold>

	public String analizeRadiantFB(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		if (killEventArrayList.get(0).second <= 90)
		{
			if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier >= 6)
				return "perfect";

			if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier <= 5)
				return "horrible";

			if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier <= 5)
				if (killEventArrayList.get(0).second + 4 >= killEventArrayList.get(1).second)
					return "good";
			if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier >= 6)
				if (killEventArrayList.get(0).second + 4 >= killEventArrayList.get(1).second)
					return "good";
		}
		if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(0).second >= 360)
			return "normal";
		if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(0).second >= 360)
			return "bad";

		if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(0).second + 4 < killEventArrayList.get(1).second)
			return "good";
		if ((killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier <= 5) || (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier >= 6))
			if (killEventArrayList.get(0).second + 10 >= killEventArrayList.get(1).second)
			{
				if (killEventArrayList.get(2).dier >= 6 && killEventArrayList.get(1).second + 5 >= killEventArrayList.get(2).second)
					return "good";
				if (killEventArrayList.get(2).dier <= 5 && killEventArrayList.get(1).second + 5 >= killEventArrayList.get(2).second)
					return "bad";
				return "normal";
			}
		return "bad";

	}

	public String analizeDireFB(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		if (killEventArrayList.get(0).second <= 90)
		{
			if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier >= 6)
				return "horrible";

			if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier <= 5)
				return "perfect";

			if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier <= 5)
				if (killEventArrayList.get(0).second + 4 >= killEventArrayList.get(1).second)
					return "good";
			if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier >= 6)
				if (killEventArrayList.get(0).second + 4 >= killEventArrayList.get(1).second)
					return "good";
		}
		if (killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(0).second >= 360)
			return "bad";
		if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(0).second >= 360)
			return "normal";
		if (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(0).second + 4 < killEventArrayList.get(1).second)
			return "good";
		if ((killEventArrayList.get(0).dier >= 6 && killEventArrayList.get(1).dier <= 5) || (killEventArrayList.get(0).dier <= 5 && killEventArrayList.get(1).dier >= 6))
			if (killEventArrayList.get(0).second + 10 >= killEventArrayList.get(1).second)
			{
				if (killEventArrayList.get(2).dier <= 5 && killEventArrayList.get(1).second + 5 >= killEventArrayList.get(2).second)
					return "good";
				if (killEventArrayList.get(2).dier >= 6 && killEventArrayList.get(1).second + 5 >= killEventArrayList.get(2).second)
					return "bad";
				return "normal";
			}
		return "bad";
	}

	public String analizeRadiantTenKills(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer radiantKillsCounter = 0;
		Integer direKillsCounter = 0;
		Boolean needToContinue = false;
		Boolean wasTimeAdmitted = false;
		Integer second = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (direKillsCounter >= 10 || radiantKillsCounter >= 10)
			{
				if (!wasTimeAdmitted)
				{
					second = killEventArrayList.get(i - 1).second;
					wasTimeAdmitted = true;
				}
				if (needToContinue)
					continue;
				if (killEventArrayList.get(i - 1).second + 10 >= killEventArrayList.get(i).second)
				{
					if (killEventArrayList.get(i).dier >= 6)
						radiantKillsCounter++;
					else
						direKillsCounter++;
				}
				needToContinue = true;
				continue;
			}
			if (killEventArrayList.get(i).dier >= 6)
				radiantKillsCounter++;
			else
				direKillsCounter++;
		}
		Integer diff = radiantKillsCounter - direKillsCounter;
		if (diff >= 5 && second <= 900)
			return "perfect";
		else if (diff >= 5)
			return "good";
		if (diff <= -5 && second <= 900)
			return "horrible";
		else if (diff <= -5)
			return "bad";
		if (diff == 3 || diff == 4)
			return "good";
		if (diff == -3 || diff == -4)
			return "bad";

		return "normal";
	}

	public String analizeDireTenKills(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer radiantKillsCounter = 0;
		Integer direKillsCounter = 0;
		Boolean needToContinue = false;
		Boolean wasTimeAdmitted = false;
		Integer second = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (direKillsCounter >= 10 || radiantKillsCounter >= 10)
			{
				if (!wasTimeAdmitted)
				{
					second = killEventArrayList.get(i - 1).second;
					wasTimeAdmitted = true;
				}
				if (needToContinue)
					continue;
				if (killEventArrayList.get(i - 1).second + 10 >= killEventArrayList.get(i).second)
				{
					if (killEventArrayList.get(i).dier >= 6)
						radiantKillsCounter++;
					else
						direKillsCounter++;
				}
				needToContinue = true;
				continue;
			}
			if (killEventArrayList.get(i).dier >= 6)
				radiantKillsCounter++;
			else
				direKillsCounter++;
		}
		Integer diff = direKillsCounter - radiantKillsCounter;
		if (diff >= 5 && second <= 900)
			return "perfect";
		else if (diff >= 5)
			return "good";
		if (diff <= -5 && second <= 900)
			return "horrible";
		else if (diff <= -5)
			return "bad";
		if (diff == 3 || diff == 4)
			return "good";
		if (diff == -3 || diff == -4)
			return "bad";

		return "normal";
	}

	//<editor-fold desc="FB F10K ratings">
	public String getFBRating(ArrayList<String> fbMarks)
	{
		Integer badGames = 0;
		Integer horribleGames = 0;
		Integer normalGames = 0;
		Integer goodGames = 0;
		Integer perfectGames = 0;
		Integer allGames = 0;
		if (fbMarks.size() <= 4)
			return "normal";
		for (int i = 0; i < fbMarks.size(); i++)
		{
			if (fbMarks.get(i).equals("horrible"))
			{
				horribleGames++;
			}
			if (fbMarks.get(i).equals("bad"))
			{
				badGames++;
			}
			if (fbMarks.get(i).equals("normal"))
			{
				normalGames++;
			}
			if (fbMarks.get(i).equals("good"))
			{
				goodGames++;
			}
			if (fbMarks.get(i).equals("perfect"))
			{
				perfectGames++;
			}
			allGames++;
		}
		Double horribleGamesPercent = (double) horribleGames / allGames * 100;
		Double badGamesPercent = (double) badGames / allGames * 100;
		Double normalGamesPercent = (double) normalGames / allGames * 100;
		Double goodGamesPercent = (double) goodGames / allGames * 100;
		Double perfectGamesPercent = (double) perfectGames / allGames * 100;

		if (goodGamesPercent + perfectGamesPercent >= 60 && badGamesPercent + horribleGamesPercent <= 30)
			return "perfect";
		else if (goodGamesPercent + perfectGamesPercent >= 50 && badGamesPercent + horribleGamesPercent <= 35)
			return "nice";
		else if (badGamesPercent + horribleGamesPercent >= 50)
			return "bad";
		else
			return "normal";
	}

	public ArrayList<String> getFBMarks(String teamId, Date date) throws IOException, ParseException
	{
		String teamFile = readFile("files/teams/" + teamId + "/TeamMatches.txt");
		String[] eachMatch = teamFile.split("\n");
		ArrayList<String> FBMarks = new ArrayList<>();
		for (int i = 0; i < eachMatch.length; i++)
		{
			if (eachMatch[i].length() <= 40)
				continue;
			String matchDate = eachMatch[i].split(";")[1];

			Date thisMatchDate = formatter.parse(matchDate);
			if (thisMatchDate.compareTo(date) <= 0 && getDifferenceDays(thisMatchDate, date) <= 20)
			{
				FBMarks.add(eachMatch[i].split("##")[3].split(";")[5]);
			}
		}
		return FBMarks;
	}

	public String getF10KRating(ArrayList<String> F10KMarks)
	{
		Integer badGames = 0;
		Integer horribleGames = 0;
		Integer normalGames = 0;
		Integer goodGames = 0;
		Integer perfectGames = 0;
		Integer allGames = 0;
		if (F10KMarks.size() <= 4)
			return "normal";
		for (int i = 0; i < F10KMarks.size(); i++)
		{
			if (F10KMarks.get(i).equals("horrible"))
			{
				horribleGames++;
			}
			if (F10KMarks.get(i).equals("bad"))
			{
				badGames++;
			}
			if (F10KMarks.get(i).equals("normal"))
			{
				normalGames++;
			}
			if (F10KMarks.get(i).equals("good"))
			{
				goodGames++;
			}
			if (F10KMarks.get(i).equals("perfect"))
			{
				perfectGames++;
			}
			allGames++;
		}
		Double horribleGamesPercent = (double) horribleGames / allGames * 100;
		Double badGamesPercent = (double) badGames / allGames * 100;
		Double normalGamesPercent = (double) normalGames / allGames * 100;
		Double goodGamesPercent = (double) goodGames / allGames * 100;
		Double perfectGamesPercent = (double) perfectGames / allGames * 100;

		if (goodGamesPercent + perfectGamesPercent >= 60 && badGamesPercent + horribleGamesPercent <= 30)
			return "perfect";
		else if (goodGamesPercent + perfectGamesPercent >= 50 && badGamesPercent + horribleGamesPercent <= 35)
			return "nice";
		else if (badGamesPercent + horribleGamesPercent >= 50)
			return "bad";
		else
			return "normal";
	}

	public ArrayList<String> getF10KMarks(String teamId, Date date) throws IOException, ParseException
	{
		String teamFile = readFile("files/teams/" + teamId + "/TeamMatches.txt");
		String[] eachMatch = teamFile.split("\n");
		ArrayList<String> F10KMarks = new ArrayList<>();
		for (int i = 0; i < eachMatch.length; i++)
		{
			if (eachMatch[i].length() <= 40)
				continue;
			String matchDate = eachMatch[i].split(";")[1];

			Date thisMatchDate = formatter.parse(matchDate);
			if (thisMatchDate.compareTo(date) <= 0 && getDifferenceDays(thisMatchDate, date) <= 20)
			{
				F10KMarks.add(eachMatch[i].split("##")[3].split(";")[4]);
			}
		}
		return F10KMarks;
	}
	//</editor-fold>


	//<editor-fold desc="Tests">
	public Integer testRadiantPushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Integer rowPushing = 0;
		Integer splitPushing = 0;
		Double t1SecondsPushing = 0.0;
		Double t2SecondsPushing = 0.0;
		Double t3SecondsPushing = 0.0;
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
							rowPushing += 100;
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
				t1SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 2)
				t2SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 3)
				t3SecondsPushing += 150 - (0.075 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;
		System.out.println("Radiant SecondPushingPoints:" + secondsPushing);
		System.out.println("Radiant RowPushingPoints:" + rowPushing);
		System.out.println("Radiant SplitPushingPoints:" + splitPushing);
		pushing = (int) (secondsPushing + rowPushing + splitPushing);
		return pushing;
	}

	public Integer testDirePushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Double t1SecondsPushing = 0.0;
		Double t2SecondsPushing = 0.0;
		Double t3SecondsPushing = 0.0;
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
							rowPushing += 100;
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
				t1SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 2)
				t2SecondsPushing += 300 - (0.15 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 3)
				t3SecondsPushing += 150 - (0.075 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;
		System.out.println("Dire SecondPushingPoints:" + secondsPushing);
		System.out.println("Dire RowPushingPoints:" + rowPushing);
		System.out.println("Dire SplitPushingPoints:" + splitPushing);
		pushing = (int) (secondsPushing + rowPushing + splitPushing);
		return pushing;
	}

	public Integer testRadiantVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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
							killUnderWardPoints += 50;
						}
					}
				}
			}
		}
		avgLifeTimeOfWardPoints = (wardLifeTime / wardsPlaced - averageDataFactory.avgWardLifeTime) * 5;
		System.out.println("Radiant AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		System.out.println("Radiant WardsDestroyedPoints:" + wardsDestroyedPoints);
		System.out.println("Radiant KillUnderWard:" + killUnderWardPoints);

		return (int) (killUnderWardPoints + wardsDestroyedPoints + avgLifeTimeOfWardPoints);
	}

	public Integer testDireVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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
							killUnderWardPoints += 50;
						}
					}
				}
			}
		}

		avgLifeTimeOfWardPoints = (wardLifeTime / wardsPlaced - averageDataFactory.avgWardLifeTime) * 5;
		System.out.println("Dire AvgLifeTimeWardPoints:" + avgLifeTimeOfWardPoints);
		System.out.println("Dire WardsDestroyedPoints:" + wardsDestroyedPoints);
		System.out.println("Dire KillUnderWard:" + killUnderWardPoints);

		return (int) (killUnderWardPoints + wardsDestroyedPoints + avgLifeTimeOfWardPoints);
	}

	public Integer testRadiantLining(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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

		LHF5MPoints = (LHF5M - averageDataFactory.avgLHF5M + 10) * 15;
		LHF510MPoints = (LHF510M - averageDataFactory.avgLHF510M + 10) * 15;
		KDF10MPoints = 0.3 * 1000 * KDF10M / 5;
		XPMF10MPoints = (XPMF10M - averageDataFactory.avgXPMF10M) / 1000 * 50;

		System.out.println("Radiant LHF5MPoints:" + LHF5MPoints);
		System.out.println("Radiant LHF5-10MPoints:" + LHF510MPoints);
		System.out.println("Radiant KDF10MPoints:" + KDF10MPoints);
		System.out.println("Radiant XPMF10MPoints:" + XPMF10MPoints);

		return (int) (LHF5MPoints + LHF510MPoints + KDF10MPoints + XPMF10MPoints);
	}

	public Integer testDireLining(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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

		LHF5MPoints = (LHF5M - averageDataFactory.avgLHF5M + 10) * 15;
		LHF510MPoints = (LHF510M - averageDataFactory.avgLHF510M + 10) * 15;
		KDF10MPoints = 0.3 * 1000 * KDF10M / 5;
		XPMF10MPoints = (XPMF10M - averageDataFactory.avgXPMF10M) / 1000 * 50;
		System.out.println(XPMF10M);
		System.out.println(averageDataFactory.avgXPMF10M);
		System.out.println("Dire LHF5MPoints:" + LHF5MPoints);
		System.out.println("Dire LHF5-10MPoints:" + LHF510MPoints);
		System.out.println("Dire KDF10MPoints:" + KDF10MPoints);
		System.out.println("Dire XPMF10MPoints:" + XPMF10MPoints);

		return (int) (LHF5MPoints + LHF510MPoints + KDF10MPoints + XPMF10MPoints);
	}

	public Integer testRadiantTenKills(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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
		F10KDifferencePoints = difference * 100;

		System.out.println("Radiant DifferencePoints:" + F10KDifferencePoints);
		System.out.println("Radiant TimePoints:" + F10KTPoints);

		return (int) (F10KDifferencePoints + F10KTPoints);
	}

	public Integer testDireTenKills(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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
		F10KDifferencePoints = difference * 100;

		System.out.println("Dire DifferencePoints:" + F10KDifferencePoints);
		System.out.println("Dire TimePoints:" + F10KTPoints);

		return (int) (F10KDifferencePoints + F10KTPoints);
	}

	public Integer testRadiantFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryLHPoints = 0.0;
		Double miderLHPoints = 0.0;

		Integer carryLH = 0;
		Integer miderLH = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i].role == 2)
				carryLH = player[i].totalLH;
			if (player[i].role == 1)
				miderLH = player[i].totalLH;
		}

		totalLH += player[0].totalLH + player[1].totalLH + player[2].totalLH + player[3].totalLH + player[4].totalLH;

		carryLHPoints = (0.35 * 1000 * carryLH / match.matchTime) / averageDataFactory.avgCarryLHM;
		miderLHPoints = (0.3 * 1000 * miderLH / match.matchTime) / averageDataFactory.avgMiderLHM;
		totalGPMPoints = 0.15 * 1000 * team[0].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.2 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		System.out.println("Radiant CarryGPMPoints:" + carryLHPoints);
		System.out.println("Radiant MiderGPMPoints:" + miderLHPoints);
		System.out.println("Radiant TotalGPMPoints:" + totalGPMPoints);
		System.out.println("Radiant LHMPoints:" + LHMPoints);
		return (int) (carryLHPoints + miderLHPoints + totalGPMPoints + LHMPoints);
	}

	public Integer testDireFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double LHMPoints = 0.0;
		Double totalGPMPoints = 0.0;
		Double carryLHPoints = 0.0;
		Double miderLHPoints = 0.0;

		Integer carryLH = 0;
		Integer miderLH = 0;
		Integer totalLH = 0;
		for (int i = 0; i < 5; i++)
		{
			if (player[i + 5].role == 2)
				carryLH = player[i + 5].totalLH;
			if (player[i + 5].role == 1)
				miderLH = player[i + 5].totalLH;
		}

		totalLH += player[5].totalLH + player[6].totalLH + player[7].totalLH + player[8].totalLH + player[9].totalLH;

		carryLHPoints = (0.35 * 1000 * carryLH / match.matchTime) / averageDataFactory.avgCarryLHM;
		miderLHPoints = (0.3 * 1000 * miderLH / match.matchTime) / averageDataFactory.avgMiderLHM;
		totalGPMPoints = 0.15 * 1000 * team[1].totalGPM / averageDataFactory.avgGPM;
		LHMPoints = (0.2 * 1000 * totalLH / match.matchTime) / averageDataFactory.avgLHM;

		System.out.println("Dire CarryGPMPoints:" + carryLHPoints);
		System.out.println("Dire MiderGPMPoints:" + miderLHPoints);
		System.out.println("Dire TotalGPMPoints:" + totalGPMPoints);
		System.out.println("Dire LHMPoints:" + LHMPoints);
		return (int) (carryLHPoints + miderLHPoints + totalGPMPoints + LHMPoints);
	}
	//</editor-fold>

	public void writeRadiantInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer radiantKillAbility, Integer radiantPushing, Integer radiantWardAbility, Integer radiantLining, String radiantTenKills, Integer radiantFarming, String radiantFB, Integer direKillAbility, Integer direPushing, Integer direWardAbility, Integer direLining, String direTenKills, Integer direFarming, String direFB, String enemyFBRating, String enemyF10KRating, String matchHardness) throws IOException
	{
		String teamString = "";
		/**General Information [0]**/
		teamString += match.id + ";";//[0]
		teamString += match.date + ";";//[1]
		teamString += match.matchTime + ";";//[2]
		teamString += team[0].id + ";";//[3]
		teamString += (match.winRadiant ? "true" : "false") + ";";//[4]
		teamString += "0" + ";";//[5]
		teamString += team[0].EGPoints + ";";
		teamString += team[0].MGPoints + ";";
		teamString += team[0].LGPoints + ";";
		teamString += team[1].name+";";
		teamString += team[1].id;
		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[0].kills + ";";
		teamString += team[0].deaths + ";";
		teamString += team[0].assists;

		teamString += "##";
		/**Players And EPP's [2]**/
		teamString += player[0].playerId + ";";
		teamString += player[0].EPP + "||";
		teamString += player[1].playerId + ";";
		teamString += player[1].EPP + "||";
		teamString += player[2].playerId + ";";
		teamString += player[2].EPP + "||";
		teamString += player[3].playerId + ";";
		teamString += player[3].EPP + "||";
		teamString += player[4].playerId + ";";
		teamString += player[4].EPP;
		teamString += "##";
		/**Parameters [3]**/
		teamString += radiantKillAbility + ";";
		teamString += radiantPushing + ";";
		teamString += radiantWardAbility + ";";
		teamString += radiantLining + ";";
		teamString += radiantTenKills + ";";
		teamString += radiantFB + ";";
		teamString += radiantFarming;
		teamString += "##";
		/**Parameters [4]**/
		teamString += direKillAbility + ";";
		teamString += direPushing + ";";
		teamString += direWardAbility + ";";
		teamString += direLining + ";";
		teamString += direTenKills + ";";
		teamString += direFB + ";";
		teamString += direFarming;
		teamString += "##";
		/**FB Information [5]**/
		teamString += (match.firstBloodRadiant ? "true" : "false") + ";"; //[0]
		teamString += match.FBTime; //[1]
		teamString += "##";
		/**F10K Information [6]**/
		teamString += (match.first10KillsRadiant ? "true" : "false") + ";";
		teamString += match.F10KTime;
		teamString += "##";
		/**FR Information [7]**/
		teamString += (match.firstRoshanRadiant ? "true" : "false") + ";";
		teamString += match.FRoshanTime;
		teamString += "##";
		/**KillEvents [8]**/
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			teamString += killEventArrayList.get(i).dier + ";";
			teamString += killEventArrayList.get(i).second;
			if (i != killEventArrayList.size() - 1)
				teamString += "||";
		}
		teamString += "##";
		/**FBRating [9]**/
		teamString += enemyFBRating;
		teamString += "##";
		/**F10KRating [10]**/
		teamString += enemyF10KRating;
		teamString += "##";
		/**MatchHardness [11]**/
		teamString += matchHardness;
		teamString += "##";
		/**Rating Changes [12]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[0].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";

		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 40)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
			}
		}
		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[0].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[0].id + "/TeamMatches.txt");
		}
		System.out.println("Team " + team[0].id + " file was changed.");
	}

	public void writeDireInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer radiantKillAbility, Integer radiantPushing, Integer radiantWardAbility, Integer radiantLining, String radiantTenKills, Integer radiantFarming, String radiantFB, Integer direKillAbility, Integer direPushing, Integer direWardAbility, Integer direLining, String direTenKills, Integer direFarming, String direFB, String enemyFBRating, String enemyF10KRating,String matchHardness) throws IOException
	{
		String teamString = "";
		/**General Information [0]**/
		teamString += match.id + ";";//[0]
		teamString += match.date + ";";//[1]
		teamString += match.matchTime + ";";//[2]
		teamString += team[1].id + ";";//[3]
		teamString += (match.winRadiant ? "false" : "true") + ";";
		teamString += "1" + ";";//[4]
		teamString += team[1].EGPoints + ";";
		teamString += team[1].MGPoints + ";";
		teamString += team[1].LGPoints + ";";
		teamString += team[0].name+";";
		teamString += team[0].id;
		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[1].kills + ";";
		teamString += team[1].deaths + ";";
		teamString += team[1].assists;

		teamString += "##";
		/**Players And EPP's [2]**/
		teamString += player[5].playerId + ";";
		teamString += player[5].EPP + "||";
		teamString += player[6].playerId + ";";
		teamString += player[6].EPP + "||";
		teamString += player[7].playerId + ";";
		teamString += player[7].EPP + "||";
		teamString += player[8].playerId + ";";
		teamString += player[8].EPP + "||";
		teamString += player[9].playerId + ";";
		teamString += player[9].EPP;
		teamString += "##";
		/**Parameters [3]**/
		teamString += direKillAbility + ";";
		teamString += direPushing + ";";
		teamString += direWardAbility + ";";
		teamString += direLining + ";";
		teamString += direTenKills + ";";
		teamString += direFB + ";";
		teamString += direFarming;
		teamString += "##";
		/**Parameters [4]**/
		teamString += radiantKillAbility + ";";
		teamString += radiantPushing + ";";
		teamString += radiantWardAbility + ";";
		teamString += radiantLining + ";";
		teamString += radiantTenKills + ";";
		teamString += radiantFB + ";";
		teamString += radiantFarming;
		teamString += "##";
		/**FB Information [5]**/
		teamString += (match.firstBloodRadiant ? "false" : "true") + ";";
		teamString += match.FBTime;
		teamString += "##";
		/**F10K Information [6]**/
		teamString += (match.first10KillsRadiant ? "false" : "true") + ";";
		teamString += match.F10KTime;
		teamString += "##";
		/**FR Information [7]**/
		teamString += (match.firstRoshanRadiant ? "false" : "true") + ";";
		teamString += match.FRoshanTime;
		teamString += "##";
		/**KillEvents [8]**/
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			teamString += killEventArrayList.get(i).dier + ";";
			teamString += killEventArrayList.get(i).second;
			if (i != killEventArrayList.size() - 1)
				teamString += "||";
		}
		teamString += "##";
		/**FBRating[9] F10KRating[10]**/
		teamString += enemyFBRating;
		teamString += "##";
		teamString += enemyF10KRating;
		teamString += "##";
		teamString += matchHardness;
		teamString += "##";
		/**Rating Changes [11]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[1].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";
		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 40)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
			}
		}
		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[1].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[1].id + "/TeamMatches.txt");
		}
		System.out.println("Team " + team[1].id + " file was changed.");
	}

	public void writePlayersInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		for (int i = 0; i < 10; i++)
		{
			String playerString = "";
			playerString += match.id + ";";
			if (i <= 4)
				playerString += team[0].id + ";";
			else
				playerString += team[1].id + ";";
			playerString += match.date + ";";
			playerString += player[i].EPP + ";";
			if (i <= 4)
			{
				if (match.winRadiant)
					playerString += "true";
				else
					playerString += "false";
			} else
			{
				if (match.winRadiant)
					playerString += "false";
				else
					playerString += "true";
			}
			fileControlFactory.createPlayerFileIfNotExist(player[i].playerId);
			fileControlFactory.writeToFile(playerString, "files/players/" + player[i].playerId + ".txt");

		}
	}

	String readFile(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally
		{
			br.close();
		}
	}

	public static long getDifferenceDays(Date beforeDate, Date matchDate)
	{
		long diff = matchDate.getTime() - beforeDate.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public String calculateMatchHardness(String team1Kills, String team2Kills, String team1totalGold, String team2totalGold, String duration)
	{
		Double t1K = Double.parseDouble(team1Kills);
		Double t2K = Double.parseDouble(team2Kills);
		Double t1G = Double.parseDouble(team1totalGold);
		Double t2G = Double.parseDouble(team2totalGold);
		Integer matchDuration = Integer.parseInt(duration);

		int durationHardness;
		int killsHardness;
		int goldHardness;

		if ((Math.max(t1K, t2K) / Math.min(t1K, t2K)) >= 2)
			killsHardness = 0;
		else if ((Math.max(t1K, t2K) / Math.min(t1K, t2K)) >= 1.6 && (Math.max(t1K, t2K) / Math.min(t1K, t2K)) < 2)
			killsHardness = 33;
		else if ((Math.max(t1K, t2K) / Math.min(t1K, t2K)) >= 1.3 && (Math.max(t1K, t2K) / Math.min(t1K, t2K)) < 1.6)
			killsHardness = 66;
		else killsHardness = 100;


		if ((Math.max(t1G, t2G) / Math.min(t1G, t2G)) >= 1.45)
			goldHardness = 0;
		else if ((Math.max(t1G, t2G) / Math.min(t1G, t2G)) >= 1.28 && (Math.max(t1G, t2G) / Math.min(t1G, t2G)) < 1.45)
			goldHardness = 33;
		else if ((Math.max(t1G, t2G) / Math.min(t1G, t2G)) > 1.11 && (Math.max(t1G, t2G) / Math.min(t1G, t2G)) < 1.28)
			goldHardness = 66;
		else goldHardness = 100;

		if (matchDuration >= 50)
			durationHardness = 100;
		else if (matchDuration > 36 && matchDuration < 50)
			durationHardness = 66;
		else if (matchDuration <= 36 && matchDuration > 25)
			durationHardness = 33;
		else durationHardness = 0;

		float matchHardness = (durationHardness + goldHardness + killsHardness) / 3;


		if (matchHardness >= 80)
			return "MH";
		else if (matchHardness < 80 && matchHardness >= 50)
			return "H";
		else if (matchHardness < 50 && matchHardness >= 20)
			return "L";
		else
			return "ML";
	}
}

