package ProjectDir.Analitics;

import ProjectDir.AverageDataFactory;
import ProjectDir.FileOperationsFactory;
import ProjectDir.MatchInfo.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	AverageDataFactory averageDataFactory = new AverageDataFactory();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	Scanner reader = new Scanner(System.in);

	public void analizeMatch(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException, ParseException
	{
		/**Create team file if not exists**/
		fileControlFactory.createTeamFileIfNotExists(team[0].id);
		fileControlFactory.createTeamFileIfNotExists(team[1].id);
		//createLeagueNameIfNotExists(match.leagueId,match.leagueName);
		/**Start analizing**/
		/****Aggression**/
		Integer radiantKillAbility = 0;//analizeRadiantKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direKillAbility = 0;//analizeDireKillAbility(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/*****Pushing****/
		Integer radiantPushing = 0;//analizeRadiantPushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direPushing = 0;// analizeDirePushing(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/**** Vision****/
		Integer radiantVision = 0;//analizeRadiantVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direVision = 0;//analizeDireVision(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/*****Lining****/
		Integer radiantLining = 0;//analizeRadiantLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direLining = 0;//analizeDireLining(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/*****TenKills****/
		String radiantTenKills = analizeRadiantTenKills(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		String direTenKills = analizeDireTenKills(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//System.out.println("Radiant TenKills:" + radiantTenKills);
		//System.out.println("Dire TenKills:" + direTenKills);
		/*****Farming****/
		Integer radiantFarming = 0;// analizeRadiantFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		Integer direFarming = 0;//analizeDireFarming(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/****FB****/
		String radiantFB = analizeRadiantFB(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		String direFB = analizeDireFB(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		/****AgrDef****/
		//String radiantAgrDef = analizeRadiantAgrDef(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
		//String direAgrDef = analizeDireAgrDef(averageDataFactory, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
		/**MatchHardness**/
		String matchHardness = "H";
		/**KillsDeaths Aggression Coefficient**/
		Double radiantAgrKills = 0.0;//analizeRadiantAggressionKills(killEventArrayList);
		Double radiantAgrDeaths = 0.0;//analizeRadiantAggressionDeaths(killEventArrayList);
		Double direAgrKills = 0.0;//analizeDireAggressionKills(killEventArrayList);
		Double direAgrDeaths = 0.0;//analizeDireAggressionDeaths(killEventArrayList);
		/**LeagueTier**/
		String leagueTier = getLeagueTier(match.leagueId, match.leagueName);
		/**End analizing**/
		/*** Write info to file **/
		///Write that match was analized

		writeRadiantInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, radiantKillAbility, radiantPushing, radiantVision, radiantLining, radiantTenKills, radiantFarming, radiantFB, radiantAgrKills, radiantAgrDeaths, direKillAbility, direPushing, direVision, direLining, direTenKills, direFarming, direFB, direAgrKills, direAgrDeaths, matchHardness, leagueTier);
		writeDireInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, radiantKillAbility, radiantPushing, radiantVision, radiantLining, radiantTenKills, radiantFarming, radiantFB, radiantAgrKills, radiantAgrDeaths, direKillAbility, direPushing, direVision, direLining, direTenKills, direFarming, direFB, direAgrKills, direAgrDeaths, matchHardness, leagueTier);
		writePlayersInfoToFile(team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		System.out.println("Match " + match.id + " was analized.");
	}

	//<editor-fold desc="Parameters">
	public String analizeRadiantAgrDef(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
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

	public String analizeDireAgrDef(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
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
		Integer firstLHF10M = 0;
		Integer firstXPMF10M = 0;
		Integer firstGPMF10M = 0;
		Integer secondLHF10M = 0;
		Integer secondXPMF10M = 0;
		Integer secondGPMF10M = 0;

		for (int i = 0; i < 10; i++)
		{
			firstLHF10M += team[0].perMinuteLastHits[i];
			firstXPMF10M += team[0].minuteXPM[i];
			firstGPMF10M += team[0].minuteGPM[i];
			secondLHF10M += team[1].perMinuteLastHits[i];
			secondXPMF10M += team[1].minuteXPM[i];
			secondGPMF10M += team[1].minuteGPM[i];
		}

		Double totalDiff = 0.0;
		totalDiff += (double) firstLHF10M / secondLHF10M * 100 - 100;
		totalDiff += (double) firstGPMF10M / secondGPMF10M * 100 - 100;
		totalDiff += (double) firstXPMF10M / secondXPMF10M * 100 - 100;

		totalDiff = totalDiff / 300;
		totalDiff += 1;
		totalDiff = totalDiff * 500;
		return totalDiff.intValue();
	}

	public Integer analizeDireLining(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer firstLHF10M = 0;
		Integer firstXPMF10M = 0;
		Integer firstGPMF10M = 0;
		Integer secondLHF10M = 0;
		Integer secondXPMF10M = 0;
		Integer secondGPMF10M = 0;

		for (int i = 0; i < 10; i++)
		{
			firstLHF10M += team[0].perMinuteLastHits[i];
			firstXPMF10M += team[0].minuteXPM[i];
			firstGPMF10M += team[0].minuteGPM[i];
			secondLHF10M += team[1].perMinuteLastHits[i];
			secondXPMF10M += team[1].minuteXPM[i];
			secondGPMF10M += team[1].minuteGPM[i];
		}

		Double totalDiff = 0.0;

		totalDiff += (double) secondLHF10M / firstLHF10M * 100 - 100;
		totalDiff += (double) secondGPMF10M / firstGPMF10M * 100 - 100;
		totalDiff += (double) secondXPMF10M / firstXPMF10M * 100 - 100;

		totalDiff = totalDiff / 300;
		totalDiff += 1;
		totalDiff = totalDiff * 500;
		return totalDiff.intValue();
	}

	public Integer analizeRadiantFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double totalDiff = 0.0;
		Double firstCarryLH = 0.0;
		Double firstMiderLH = 0.0;
		Double firstTotalLH = 0.0;
		Double secondCarryLH = 0.0;
		Double secondMiderLH = 0.0;
		Double secondTotalLH = 0.0;

		for (int i = 0; i < 5; i++)
		{
			if (player[i].role == 2)
				firstCarryLH = (double) player[i].totalLH;
			if (player[i].role == 1)
				firstMiderLH = (double) player[i].totalLH;
		}
		for (int i = 5; i < 10; i++)
		{
			if (player[i].role == 2)
				secondCarryLH = (double) player[i].totalLH;
			if (player[i].role == 1)
				secondMiderLH = (double) player[i].totalLH;
		}

		firstTotalLH += player[0].totalLH + player[1].totalLH + player[2].totalLH + player[3].totalLH + player[4].totalLH;
		secondTotalLH += player[5].totalLH + player[6].totalLH + player[7].totalLH + player[8].totalLH + player[9].totalLH;

		if (firstCarryLH != 0.0 && secondCarryLH != 0.0)
			totalDiff += (firstCarryLH / secondCarryLH * 100 - 100) * 0.3;
		if (firstMiderLH != 0.0 && secondMiderLH != 0.0)
			totalDiff += (firstMiderLH / secondMiderLH * 100 - 100) * 0.15;
		totalDiff += (team[0].totalGPM / team[1].totalGPM * 100 - 100) * 0.25;
		totalDiff += (firstTotalLH / secondTotalLH * 100 - 100) * 0.3;
		totalDiff /= 100;
		totalDiff += 1;
		totalDiff *= 500;

		return totalDiff.intValue();
	}

	public Integer analizeDireFarming(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double totalDiff = 0.0;
		Double firstCarryLH = 0.0;
		Double firstMiderLH = 0.0;
		Double firstTotalLH = 0.0;
		Double secondCarryLH = 0.0;
		Double secondMiderLH = 0.0;
		Double secondTotalLH = 0.0;

		for (int i = 0; i < 5; i++)
		{
			if (player[i].role == 2)
				firstCarryLH = (double) player[i].totalLH;
			if (player[i].role == 1)
				firstMiderLH = (double) player[i].totalLH;
		}
		for (int i = 5; i < 10; i++)
		{
			if (player[i].role == 2)
				secondCarryLH = (double) player[i].totalLH;
			if (player[i].role == 1)
				secondMiderLH = (double) player[i].totalLH;
		}

		firstTotalLH += player[0].totalLH + player[1].totalLH + player[2].totalLH + player[3].totalLH + player[4].totalLH;
		secondTotalLH += player[5].totalLH + player[6].totalLH + player[7].totalLH + player[8].totalLH + player[9].totalLH;

		if (firstCarryLH != 0.0 && secondCarryLH != 0.0)
			totalDiff += (secondCarryLH / firstCarryLH * 100 - 100) * 0.3;
		if (firstMiderLH != 0.0 && secondMiderLH != 0.0)
			totalDiff += (secondMiderLH / firstCarryLH * 100 - 100) * 0.15;
		totalDiff += (team[1].totalGPM / team[0].totalGPM * 100 - 100) * 0.25;
		totalDiff += (secondTotalLH / firstTotalLH * 100 - 100) * 0.3;
		totalDiff /= 100;
		totalDiff += 1;
		totalDiff *= 500;

		return totalDiff.intValue();
	}

	public Integer analizeRadiantPushing(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Integer pushing;
		Double secondsPushing = 0.0;
		Integer splitPushing = 0;
		Double t1SecondsPushing = 0.0;
		Double t2SecondsPushing = 0.0;
		Double t3SecondsPushing = 0.0;
		for (int i = 0; i < towerEventArrayList.size(); i++)
		{
			if (towerEventArrayList.get(i).tierLevel == 4)
				continue;

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
			//if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 1)
			//t1SecondsPushing += 500 - (0.25 * 1000 * towerEventArrayList.get(i).second) / (.secondsT1 / averageDataFactory.T1Counter);
			//if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 2)
			//	t2SecondsPushing += 200 - (0.1 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			//if (towerEventArrayList.get(i).whoDestroy.contains("Radiant") && towerEventArrayList.get(i).tierLevel == 3)
			//t3SecondsPushing += 100 - (0.05 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;
		pushing = (int) (secondsPushing + splitPushing);
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
			///if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 1)
			//	t1SecondsPushing += 500 - (0.25 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT1 / averageDataFactory.T1Counter);
			//if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 2)
			//	t2SecondsPushing += 200 - (0.10 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT2 / averageDataFactory.T2Counter);
			//if (towerEventArrayList.get(i).whoDestroy.contains("Dire") && towerEventArrayList.get(i).tierLevel == 3)
			//	t3SecondsPushing += 100 - (0.05 * 1000 * towerEventArrayList.get(i).second) / (averageDataFactory.secondsT3 / averageDataFactory.T3Counter);

		}
		secondsPushing = t1SecondsPushing + t2SecondsPushing + t3SecondsPushing;

		pushing = (int) (secondsPushing + splitPushing);
		return pushing;
	}

	public Integer analizeRadiantVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double wardsDestroyedPoints = 0.0;//30%
		Double killUnderWardPoints = 0.0;//40%

		//wardsDestroyedPoints = (0.5 * 1000 * team[0].observerWardsDestroyed / match.matchTime) / averageDataFactory.avgSupportWDM;

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
							killUnderWardPoints += 75;
						}
					}
				}
			}
		}

		return (int) (killUnderWardPoints + wardsDestroyedPoints) / match.matchTime * 30;
	}

	public Integer analizeDireVision(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		Double wardsDestroyedPoints = 0.0;
		Double avgLifeTimeOfWardPoints = 0.0;
		Double killUnderWardPoints = 0.0;

		//wardsDestroyedPoints = (0.5 * 1000 * team[1].observerWardsDestroyed / match.matchTime) / averageDataFactory.avgSupportWDM;

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
							killUnderWardPoints += 75;
						}
					}
				}
			}
		}

		return (int) (killUnderWardPoints + wardsDestroyedPoints) / match.matchTime * 30;
	}

	public Integer analizeRadiantKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		return 1;
	}

	public Integer analizeDireKillAbility(AverageDataFactory averageDataFactory, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		return 1;
	}
	//</editor-fold>

	//<editor-fold desc="FB,F10K">
	public Double analizeRadiantAggressionKills(ArrayList<KillEvent> killEventArrayList)
	{
		Integer killsCounter = 0;
		Integer deathsCounter = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second <= 900)
			{
				if (killEventArrayList.get(i).dier >= 6)
					killsCounter++;
				else
					deathsCounter++;
			}
		}
		double coef = (double) killsCounter / 10;
		coef = coef * 100;
		coef = Math.round(coef);
		coef = coef / 100;
		return coef;
	}

	public Double analizeRadiantAggressionDeaths(ArrayList<KillEvent> killEventArrayList)
	{
		Integer killsCounter = 0;
		Integer deathsCounter = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second < 900)
			{
				if (killEventArrayList.get(i).dier >= 6)
					killsCounter++;
				else
					deathsCounter++;
			}
		}
		double coef = (double) deathsCounter / 10;
		coef = coef * 100;
		coef = Math.round(coef);
		coef = coef / 100;
		return coef;
	}

	public Double analizeDireAggressionKills(ArrayList<KillEvent> killEventArrayList)
	{
		Integer killsCounter = 0;
		Integer deathsCounter = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second < 900)
			{
				if (killEventArrayList.get(i).dier <= 5)
					killsCounter++;
				else
					deathsCounter++;
			}
		}
		double coef = (double) killsCounter / 10;
		coef = coef * 100;
		coef = Math.round(coef);
		coef = coef / 100;
		return coef;
	}

	public Double analizeDireAggressionDeaths(ArrayList<KillEvent> killEventArrayList)
	{
		Integer killsCounter = 0;
		Integer deathsCounter = 0;
		for (int i = 0; i < killEventArrayList.size(); i++)
		{
			if (killEventArrayList.get(i).second < 900)
			{
				if (killEventArrayList.get(i).dier <= 5)
					killsCounter++;
				else
					deathsCounter++;
			}
		}
		double coef = (double) deathsCounter / 10;
		coef = coef * 100;
		coef = Math.round(coef);
		coef = coef / 100;
		return coef;
	}

	public String analizeRadiantFB(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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

	public String analizeDireFB(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
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
	//</editor-fold>

	public void writeRadiantInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer radiantKillAbility, Integer radiantPushing, Integer radiantWardAbility, Integer radiantLining, String radiantTenKills, Integer radiantFarming, String radiantFB, Double radiantAgrKills, Double radiantAgrDeaths, Integer direKillAbility, Integer direPushing, Integer direWardAbility, Integer direLining, String direTenKills, Integer direFarming, String direFB, Double direAgrKills, Double direAgrDeaths, String matchHardness, String leagueTier) throws IOException, ParseException
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
		teamString += team[1].name + ";";
		teamString += team[1].id + ";";
		teamString += match.leagueName + ";";
		teamString += match.leagueId + ";";
		teamString += leagueTier + ";";
		teamString += team[0].name;
		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[0].kills + ";";
		teamString += team[0].deaths + ";";
		teamString += team[0].assists;
		teamString += "##";
		/**Players And EPP's [2]**/
		teamString += player[0].playerId + ";";
		teamString += player[0].EPP + ";";
		teamString += player[0].firstLine + ";";
		teamString += player[0].hero + ";";
		teamString += player[0].totalGPM + ";";
		teamString += player[0].totalXPM + ";";
		teamString += player[0].kills + ";";
		teamString += player[0].deaths + ";";
		teamString += player[0].assists + ";";
		teamString += player[0].heroDamage + ";";
		teamString += player[0].heroHeal + ";";
		teamString += player[0].towerDamage + "||";

		teamString += player[1].playerId + ";";
		teamString += player[1].EPP + ";";
		teamString += player[1].firstLine + ";";
		teamString += player[1].hero + ";";
		teamString += player[1].totalGPM + ";";
		teamString += player[1].totalXPM + ";";
		teamString += player[1].kills + ";";
		teamString += player[1].deaths + ";";
		teamString += player[1].assists + ";";
		teamString += player[1].heroDamage + ";";
		teamString += player[1].heroHeal + ";";
		teamString += player[1].towerDamage + "||";

		teamString += player[2].playerId + ";";
		teamString += player[2].EPP + ";";
		teamString += player[2].firstLine + ";";
		teamString += player[2].hero + ";";
		teamString += player[2].totalGPM + ";";
		teamString += player[2].totalXPM + ";";
		teamString += player[2].kills + ";";
		teamString += player[2].deaths + ";";
		teamString += player[2].assists + ";";
		teamString += player[2].heroDamage + ";";
		teamString += player[2].heroHeal + ";";
		teamString += player[2].towerDamage + "||";

		teamString += player[3].playerId + ";";
		teamString += player[3].EPP + ";";
		teamString += player[3].firstLine + ";";
		teamString += player[3].hero + ";";
		teamString += player[3].totalGPM + ";";
		teamString += player[3].totalXPM + ";";
		teamString += player[3].kills + ";";
		teamString += player[3].deaths + ";";
		teamString += player[3].assists + ";";
		teamString += player[3].heroDamage + ";";
		teamString += player[3].heroHeal + ";";
		teamString += player[3].towerDamage + "||";

		teamString += player[4].playerId + ";";
		teamString += player[4].EPP + ";";
		teamString += player[4].firstLine + ";";
		teamString += player[4].hero + ";";
		teamString += player[4].totalGPM + ";";
		teamString += player[4].totalXPM + ";";
		teamString += player[4].kills + ";";
		teamString += player[4].deaths + ";";
		teamString += player[4].assists + ";";
		teamString += player[4].heroDamage + ";";
		teamString += player[4].heroHeal + ";";
		teamString += player[4].towerDamage + "||";
		teamString += player[5].hero + ";";
		teamString += player[6].hero + ";";
		teamString += player[7].hero + ";";
		teamString += player[8].hero + ";";
		teamString += player[9].hero;
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
		teamString += match.F10KTime + ";";
		teamString += radiantAgrKills + ";";
		teamString += radiantAgrDeaths;
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
		/**MatchHardness [9]**/
		teamString += matchHardness;
		teamString += "##";
		/**Rating Changes [10]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[0].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";
		int missedStringNum = 0;
		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 70)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
				missedStringNum = i;
			}
		}
		boolean shouldNotWrite = checkIfShouldNotBeWrited(team[0].id, match.date);

		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[0].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			if (shouldNotWrite && i == missedStringNum)
			{
				System.out.println("I don't write match for team " + team[0].id);
			} else fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[0].id + "/TeamMatches.txt");
		}

	}

	public void writeDireInfoToFile(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, Integer radiantKillAbility, Integer radiantPushing, Integer radiantWardAbility, Integer radiantLining, String radiantTenKills, Integer radiantFarming, String radiantFB, Double radiantAgrKills, Double radiantAgrDeaths, Integer direKillAbility, Integer direPushing, Integer direWardAbility, Integer direLining, String direTenKills, Integer direFarming, String direFB, Double direAgrKills, Double direAgrDeaths, String matchHardness, String leagueTier) throws IOException, ParseException
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
		teamString += team[0].name + ";";
		teamString += team[0].id + ";";
		teamString += match.leagueName + ";";
		teamString += match.leagueId + ";";
		teamString += leagueTier + ";";
		teamString += team[1].name;
		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[1].kills + ";";
		teamString += team[1].deaths + ";";
		teamString += team[1].assists;
		teamString += "##";
		/**Players And EPP's [2]**/
		teamString += player[5].playerId + ";";
		teamString += player[5].EPP + ";";
		teamString += player[5].firstLine + ";";
		teamString += player[5].hero + ";";
		teamString += player[5].totalGPM + ";";
		teamString += player[5].totalXPM + ";";
		teamString += player[5].kills + ";";
		teamString += player[5].deaths + ";";
		teamString += player[5].assists + ";";
		teamString += player[5].heroDamage + ";";
		teamString += player[5].heroHeal + ";";
		teamString += player[5].towerDamage + "||";
		teamString += player[6].playerId + ";";
		teamString += player[6].EPP + ";";
		teamString += player[6].firstLine + ";";
		teamString += player[6].hero + ";";
		teamString += player[6].totalGPM + ";";
		teamString += player[6].totalXPM + ";";
		teamString += player[6].kills + ";";
		teamString += player[6].deaths + ";";
		teamString += player[6].assists + ";";
		teamString += player[6].heroDamage + ";";
		teamString += player[6].heroHeal + ";";
		teamString += player[6].towerDamage + "||";

		teamString += player[7].playerId + ";";
		teamString += player[7].EPP + ";";
		teamString += player[7].firstLine + ";";
		teamString += player[7].hero + ";";
		teamString += player[7].totalGPM + ";";
		teamString += player[7].totalXPM + ";";
		teamString += player[7].kills + ";";
		teamString += player[7].deaths + ";";
		teamString += player[7].assists + ";";
		teamString += player[7].heroDamage + ";";
		teamString += player[7].heroHeal + ";";
		teamString += player[7].towerDamage + "||";

		teamString += player[8].playerId + ";";
		teamString += player[8].EPP + ";";
		teamString += player[8].firstLine + ";";
		teamString += player[8].hero + ";";
		teamString += player[8].totalGPM + ";";
		teamString += player[8].totalXPM + ";";
		teamString += player[8].kills + ";";
		teamString += player[8].deaths + ";";
		teamString += player[8].assists + ";";
		teamString += player[8].heroDamage + ";";
		teamString += player[8].heroHeal + ";";
		teamString += player[8].towerDamage + "||";

		teamString += player[9].playerId + ";";
		teamString += player[9].EPP + ";";
		teamString += player[9].firstLine + ";";
		teamString += player[9].hero + ";";
		teamString += player[9].totalGPM + ";";
		teamString += player[9].totalXPM + ";";
		teamString += player[9].kills + ";";
		teamString += player[9].deaths + ";";
		teamString += player[9].assists + ";";
		teamString += player[9].heroDamage + ";";
		teamString += player[9].heroHeal + ";";
		teamString += player[9].towerDamage + "||";
		teamString += player[0].hero + ";";
		teamString += player[1].hero + ";";
		teamString += player[2].hero + ";";
		teamString += player[3].hero + ";";
		teamString += player[4].hero;
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
		teamString += match.F10KTime + ";";
		teamString += direAgrKills + ";";
		teamString += direAgrDeaths;
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
		/**MatchHardness[9]**/
		teamString += matchHardness;
		teamString += "##";
		/**Rating Changes [10]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[1].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";
		int missedStringNum = 0;
		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 70)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
				missedStringNum = i;
			}
		}
		boolean shouldNotWrite = checkIfShouldNotBeWrited(team[1].id, match.date);
		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[1].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			if (shouldNotWrite && i == missedStringNum)
			{
				System.out.println("I don't write match for team " + team[1].id);
			} else fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[1].id + "/TeamMatches.txt");
		}
	}

	private boolean checkIfShouldNotBeWrited(String id, String date) throws ParseException
	{
		ArrayList<String> depricatedId = new ArrayList<>();
		depricatedId.add("15");//LGD
		depricatedId.add("2581813");//Exec
		depricatedId.add("350190");//Fnatic
		depricatedId.add("46");//Empire
		depricatedId.add("2586976");//OG
		depricatedId.add("1838315");//Secret
		depricatedId.add("1883502");//VP
		depricatedId.add("3");//Comlexity
		depricatedId.add("1375614");//Newbee
		depricatedId.add("726228");//Vg
		depricatedId.add("2108395");//TNC
		depricatedId.add("2767921");//Rave
		depricatedId.add("3210352");//Rave
		depricatedId.add("2776208");//WFG
		depricatedId.add("20");//Tongfu

		boolean exist = false;
		for (int i = 0; i < depricatedId.size(); i++)
		{
			if (id.equals(depricatedId.get(i)))
				exist = true;
		}
		if (exist)
		{
			if (formatter.parse(date).after(formatter.parse("2016-09-10")))
				return false;
			else return true;
		} else
		{
			return false;

		}
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
			playerString += getLeagueTier(match.leagueId, match.leagueName) + ";";
			playerString += player[i].EPP + ";";
			playerString += player[i].firstLine + ";";
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
			if (i <= 4)
				playerString += ";"+team[1].id;
			else
				playerString += ";"+team[0].id;

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

	void writeToFile(String whatToWrite, String fileName)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true))))
		{
			out.println(whatToWrite);
		} catch (IOException e)
		{
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

