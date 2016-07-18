package ProjectDir;

import ProjectDir.Analitics.*;
import ProjectDir.MatchInfo.AnalizedInfo;
import no.geosoft.cc.geometry.Geometry;
import no.geosoft.cc.graphics.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataWorker
{
	public static Integer globalTier = 0;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	Scanner s = new Scanner(System.in);
	HeatMapAnaliticsFactory heatMapAnaliticsFactory = new HeatMapAnaliticsFactory();
	ABCAnalitics abcAnalitics = new ABCAnalitics();
	StandinAnalitics standinAnalitics = new StandinAnalitics();
	RolePointAnalitics roleAnalitics = new RolePointAnalitics();
	LeagueAnalitics leagueAnalitics = new LeagueAnalitics();

	public void analizeFutureMatch(String team1Id, String team2Id) throws IOException, ParseException, InterruptedException
	{
//		heatMapAnaliticsFactory.buildHeatMap(team1Id);
//		heatMapAnaliticsFactory.buildHeatMap(team2Id);
		System.out.println("/----------------Jack--------------------/");
		System.out.println("/-Yo-yo-yo.");
		String team1Matches = fileOperationsFactory.readFile("files/teams/" + team1Id + "/TeamMatches.txt");
		String team2Matches = fileOperationsFactory.readFile("files/teams/" + team2Id + "/TeamMatches.txt");

		analizeTeamInfo(team1Matches, team2Matches);
	}

	public void analizeTeamInfo(String team1String, String team2String) throws ParseException, IOException, InterruptedException
	{
		ArrayList<AnalizedInfo> firstObjects = new ArrayList<>();
		ArrayList<AnalizedInfo> secondObjects = new ArrayList<>();
		fillObject(firstObjects, team1String);
		fillObject(secondObjects, team2String);

		ArrayList<AnalizedInfo> firstObjectsCopy = firstObjects;
		ArrayList<AnalizedInfo> secondObjectsCopy = secondObjects;

		Integer globalChoice = 0;
		Integer tierLevel = 0;
		Integer league = 0;
		while (globalChoice != 9)
		{
			System.out.println("/--- Please, choose .");
			System.out.println("/--1: Tier games.");
			System.out.println("/--2: One league games.");
			System.out.println("/--3: All games.");
			System.out.println("/--9: End program.");
			globalChoice = s.nextInt();
			switch (globalChoice)
			{
				case 1:
				{
					System.out.println("Enter tier level:");
					tierLevel = s.nextInt();
					globalTier = tierLevel;
					firstObjects = separateTierGames(firstObjectsCopy, tierLevel);
					secondObjects = separateTierGames(secondObjectsCopy, tierLevel);
					break;
				}
				case 2:
				{
					System.out.println("Enter league ID:");
					league = s.nextInt();
					globalTier = 0;
					firstObjects = separateLeagueGames(firstObjectsCopy, league);
					secondObjects = separateLeagueGames(secondObjectsCopy, league);
					break;
				}
				case 3:
				{
					globalTier = 0;
					firstObjects = firstObjectsCopy;
					secondObjects = secondObjectsCopy;

					break;
				}
				case 9:
					return;
			}
			if (firstObjects.size() == 0 && secondObjects.size() == 0)
			{
				System.out.println("Both teams have 0 games here.");
				continue;
			}


			//<editor-fold desc="Separators">
			ArrayList<AnalizedInfo> firstTier1Objects = separateTier1Objects(firstObjectsCopy);
			ArrayList<AnalizedInfo> secondTier1Objects = separateTier1Objects(secondObjectsCopy);
			ArrayList<AnalizedInfo> firstTier2Objects = separateTier2Objects(firstObjectsCopy);
			ArrayList<AnalizedInfo> secondTier2Objects = separateTier2Objects(secondObjectsCopy);
			ArrayList<AnalizedInfo> firstTier3Objects = separateTier3Objects(firstObjectsCopy);
			ArrayList<AnalizedInfo> secondTier3Objects = separateTier3Objects(secondObjectsCopy);
			ArrayList<AnalizedInfo> firstTier4Objects = separateTier4Objects(firstObjectsCopy);
			ArrayList<AnalizedInfo> secondTier4Objects = separateTier4Objects(secondObjectsCopy);


			ArrayList<AnalizedInfo> firstMonthObjects = separateMonthObjects(firstObjects);
			ArrayList<AnalizedInfo> firstTenDaysObjects = separateTenDaysObjects(firstObjects);
			ArrayList<AnalizedInfo> firstLastFiveGames = separateLastFiveGames(firstObjects);
			ArrayList<AnalizedInfo> secondMonthObjects = separateMonthObjects(secondObjects);
			ArrayList<AnalizedInfo> secondTenDaysObjects = separateTenDaysObjects(secondObjects);
			ArrayList<AnalizedInfo> secondLastFiveGames = separateLastFiveGames(secondObjects);
			ArrayList<AnalizedInfo> firstLastTenGames = separateLastTenGames(firstObjects);
			ArrayList<AnalizedInfo> firstLastTenOrLessGames = separateLastTenOrLessGames(firstObjects);
			ArrayList<AnalizedInfo> secondLastTenOrLessGames = separateLastTenOrLessGames(secondObjects);
			ArrayList<AnalizedInfo> secondLastTenGames = separateLastTenGames(secondObjects);

			ArrayList<AnalizedInfo> lastTenFirstObjects = separateLastTenGames(firstObjects);
			ArrayList<AnalizedInfo> lastThreeFirstObjects = separateLastThreeGames(firstObjects);
			ArrayList<AnalizedInfo> lastTenSecondObjects = separateLastTenGames(secondObjects);
			ArrayList<AnalizedInfo> lastThreeSecondObjects = separateLastThreeGames(secondObjects);
			ArrayList<AnalizedInfo> matchesHistory = getMeetingsMatches(firstMonthObjects, secondMonthObjects);
			//</editor-fold>

			String firstName = firstObjects.get(0).teamName;
			String secondName = secondObjects.get(0).teamName;

			/**GeneralInfo**/
			Integer firstAvgMonthEnemyRating = getAverageEnemyRating(firstMonthObjects);
			Integer firstAvgTenDaysEnemyRating = getAverageEnemyRating(firstTenDaysObjects);
			Integer firstMonthGamesPlayed = getMonthGamesPlayed(firstMonthObjects);
			Integer secondMonthGamesPlayed = getMonthGamesPlayed(secondMonthObjects);
			Integer secondAvgMonthEnemyRating = getAverageEnemyRating(secondMonthObjects);
			Integer secondAvgTenDaysEnemyRating = getAverageEnemyRating(secondTenDaysObjects);

			//<editor-fold desc="FB">
			/**FB**/
			/****First Team****/
			Integer firstFBMedianTime = getMedianFBTime(firstMonthObjects);
			Integer secondFBMedianTime = getMedianFBTime(secondMonthObjects);


			Integer firstFBForm = getFBForm(firstMonthObjects, firstTenDaysObjects);
			Integer firstAvgFBPercent = getAverageFBPercent(firstMonthObjects);
			Integer firstHorribleGameFBPercent = getHorribleGameFBPercent(firstMonthObjects);
			Integer firstBadGameFBPercent = getBadGameFBPercent(firstMonthObjects);
			Integer firstNormalGameFBPercent = getNormalGameFBPercent(firstMonthObjects);
			Integer firstGoodGameFBPercent = getGoodGameFBPercent(firstMonthObjects);
			Integer firstPerfectGameFBPercent = getPerfectGameFBPercent(firstMonthObjects);

			/****Second Team****/
			Integer secondFBForm = getFBForm(secondMonthObjects, secondTenDaysObjects);
			Integer secondAvgFBPercent = getAverageFBPercent(secondMonthObjects);
			Integer secondHorribleGameFBPercent = getHorribleGameFBPercent(secondMonthObjects);
			Integer secondBadGameFBPercent = getBadGameFBPercent(secondMonthObjects);
			Integer secondNormalGameFBPercent = getNormalGameFBPercent(secondMonthObjects);
			Integer secondGoodGameFBPercent = getGoodGameFBPercent(secondMonthObjects);
			Integer secondPerfectGameFBPercent = getPerfectGameFBPercent(secondMonthObjects);
			/*************************************************/
			//</editor-fold>
			//<editor-fold desc="F10K">
			/**F10K**/
			Integer firstF10KMedianTime = getMedianF10KTime(firstMonthObjects);
			Integer secondF10KMedianTime = getMedianF10KTime(secondMonthObjects);

			Integer firstF10KForm = getF10KForm(firstMonthObjects, firstTenDaysObjects);
			Integer firstAvgF10KPercent = getAverageF10KPercent(firstMonthObjects);
			Integer secondF10KForm = getF10KForm(secondMonthObjects, secondTenDaysObjects);
			Integer secondAvgF10KPercent = getAverageF10KPercent(secondMonthObjects);


			Integer firstHorribleGameF10KPercent = getHorribleGameF10KPercent(firstMonthObjects);
			Integer firstBadGameF10KPercent = getBadGameF10KPercent(firstMonthObjects);
			Integer firstNormalGameF10KPercent = getNormalGameF10KPercent(firstMonthObjects);
			Integer firstGoodGameF10KPercent = getGoodGameF10KPercent(firstMonthObjects);
			Integer firstPerfectGameF10KPercent = getPerfectGameF10KPercent(firstMonthObjects);
			Integer secondHorribleGameF10KPercent = getHorribleGameF10KPercent(secondMonthObjects);
			Integer secondBadGameF10KPercent = getBadGameF10KPercent(secondMonthObjects);
			Integer secondNormalGameF10KPercent = getNormalGameF10KPercent(secondMonthObjects);
			Integer secondGoodGameF10KPercent = getGoodGameF10KPercent(secondMonthObjects);
			Integer secondPerfectGameF10KPercent = getPerfectGameF10KPercent(secondMonthObjects);

			Integer firstPercentWhenFBWasGet = getPercentWhenFBWasGet(firstMonthObjects);
			Integer secondPercentWhenFBWasGet = getPercentWhenFBWasGet(secondMonthObjects);

			Double firstMonthKillsAggressionCoef = getKillsAggressionCoef(firstMonthObjects);
			Double firstTenDaysKillsAggressionCoef = getKillsAggressionCoef(firstTenDaysObjects);
			Double secondMonthKillsAggressionCoef = getKillsAggressionCoef(secondMonthObjects);
			Double secondTenDaysKillsAggressionCoef = getKillsAggressionCoef(secondTenDaysObjects);
			Double firstMonthDeathsAggressionCoef = getDeathsAggressionCoef(firstMonthObjects);
			Double firstTenDaysDeathsAggressionCoef = getDeathsAggressionCoef(firstTenDaysObjects);
			Double secondMonthDeathsAggressionCoef = getDeathsAggressionCoef(secondMonthObjects);
			Double secondTenDaysDeathsAggressionCoef = getDeathsAggressionCoef(secondTenDaysObjects);
			/**************************************************/
			//</editor-fold>
			//<editor-fold desc="Parameters">
			Integer firstStreak = getCurrentStreak(firstLastTenGames);
			Integer secondStreak = getCurrentStreak(secondLastTenGames);

			Integer firstDiff = getLastTenMatchesDiff(firstLastTenGames);
			Integer secondDiff = getLastTenMatchesDiff(secondLastTenGames);

			Integer firstFarming = getMediumFarming(firstMonthObjects);
			Integer secondFarming = getMediumFarming(secondMonthObjects);
			Integer firstPushing = getMediumPushing(firstMonthObjects);
			Integer secondPushing = getMediumPushing(secondMonthObjects);
			Integer firstLining = getMediumLining(firstMonthObjects);
			Integer secondLining = getMediumLining(secondMonthObjects);
			Integer firstVision = getMediumVision(firstMonthObjects);
			Integer secondVision = getMediumVision(secondMonthObjects);

			Integer firstGreatEnemyPercent = getPercentGreatEnemy(firstMonthObjects);
			Integer secondGreatEnemyPercent = getPercentGreatEnemy(secondMonthObjects);
			Integer firstGoodEnemyPercent = getPercentGoodEnemy(firstMonthObjects);
			Integer secondGoodEnemyPercent = getPercentGoodEnemy(secondMonthObjects);
			Integer firstNormalEnemyPercent = getPercentNormalEnemy(firstMonthObjects);
			Integer secondNormalEnemyPercent = getPercentNormalEnemy(secondMonthObjects);
			Integer firstBadEnemyPercent = getPercentBadEnemy(firstMonthObjects);
			Integer secondBadEnemyPercent = getPercentBadEnemy(secondMonthObjects);
			Integer firstGamesNumberGreatEnemy = getGamesNumberGreatEnemy(firstMonthObjects);
			Integer secondGamesNumberGreatEnemy = getGamesNumberGreatEnemy(secondMonthObjects);
			Integer firstGamesNumberGoodEnemy = getGamesNumberGoodEnemy(firstMonthObjects);
			Integer secondGamesNumberGoodEnemy = getGamesNumberGoodEnemy(secondMonthObjects);
			Integer firstGamesNumberNormalEnemy = getGamesNumberNormalEnemy(firstMonthObjects);
			Integer secondGamesNumberNormalEnemy = getGamesNumberNormalEnemy(secondMonthObjects);
			Integer firstGamesNumberBadEnemy = getGamesNumberBadEnemy(firstMonthObjects);
			Integer secondGamesNumberBadEnemy = getGamesNumberBadEnemy(secondMonthObjects);

			Integer firstMonthMedianMatchTime = getMedianMatchTime(firstMonthObjects);
			Integer secondMonthMedianMatchTime = getMedianMatchTime(secondMonthObjects);
			Integer firstMonthMedianKills = getMedianKills(firstMonthObjects);
			Integer secondMonthMedianKills = getMedianKills(secondMonthObjects);
			Integer firstMonthMedianDeaths = getMedianDeaths(firstMonthObjects);
			Integer secondMonthMedianDeaths = getMedianDeaths(secondMonthObjects);
			Integer firstTenMedianMatchTime = getMedianMatchTime(firstTenDaysObjects);
			Integer secondTenMedianMatchTime = getMedianMatchTime(secondTenDaysObjects);
			Integer firstTenMedianKills = getMedianKills(firstTenDaysObjects);
			Integer secondTenMedianKills = getMedianKills(secondTenDaysObjects);
			Integer firstTenMedianDeaths = getMedianDeaths(firstTenDaysObjects);
			Integer secondTenMedianDeaths = getMedianDeaths(secondTenDaysObjects);

			Integer firstMonthPercentKillsOver = getPercentKillsOver(firstMonthObjects);
			Integer firstMonthPercentTimeOver = getPercentTimeOver(firstMonthObjects);
			Integer secondMonthPercentKillsOver = getPercentKillsOver(secondMonthObjects);
			Integer secondMonthPercentTimeOver = getPercentTimeOver(secondMonthObjects);
			Integer firstTenPercentKillsOver = getPercentKillsOver(firstTenDaysObjects);
			Integer firstTenPercentTimeOver = getPercentTimeOver(firstTenDaysObjects);
			Integer secondTenPercentKillsOver = getPercentKillsOver(secondTenDaysObjects);
			Integer secondTenPercentTimeOver = getPercentTimeOver(secondTenDaysObjects);

			Double firstMonthAggressionCoefficient = getAggressionCoefficient(firstMonthMedianKills, firstMonthMedianMatchTime);
			Double secondMonthAggressionCoefficient = getAggressionCoefficient(secondMonthMedianKills, secondMonthMedianMatchTime);
			Double firstTenAggressionCoefficient = getAggressionCoefficient(firstTenMedianKills, firstTenMedianMatchTime);
			Double secondTenAggressionCoefficient = getAggressionCoefficient(secondTenMedianKills, secondTenMedianMatchTime);

			Integer firstEGPoints = getMedianGameStagePoints(firstMonthObjects, 1);
			Integer secondEGPoints = getMedianGameStagePoints(secondMonthObjects, 1);
			Integer firstMGPoints = getMedianGameStagePoints(firstMonthObjects, 2);
			Integer secondMGPoints = getMedianGameStagePoints(secondMonthObjects, 2);
			Integer firstLGPoints = getMedianGameStagePoints(firstMonthObjects, 3);
			Integer secondLGPoints = getMedianGameStagePoints(secondMonthObjects, 3);

			//</editor-fold>
			//<editor-fold desc="Filling InfoObject">

			InfoObject infoFBObject = new InfoObject();
			infoFBObject.team1HorribleGamesPercent = firstHorribleGameFBPercent;
			infoFBObject.team1BadGamesPercent = firstBadGameFBPercent;
			infoFBObject.team1NormalGamesPercent = firstNormalGameFBPercent;
			infoFBObject.team1GoodGamesPercent = firstGoodGameFBPercent;
			infoFBObject.team1PerfectGamesPercent = firstPerfectGameFBPercent;

			infoFBObject.team2HorribleGamesPercent = secondHorribleGameFBPercent;
			infoFBObject.team2BadGamesPercent = secondBadGameFBPercent;
			infoFBObject.team2NormalGamesPercent = secondNormalGameFBPercent;
			infoFBObject.team2GoodGamesPercent = secondGoodGameFBPercent;
			infoFBObject.team2PerfectGamesPercent = secondPerfectGameFBPercent;

			infoFBObject.team1Percent = firstAvgFBPercent;
			infoFBObject.team2Percent = secondAvgFBPercent;
			infoFBObject.team1Name = firstName;
			infoFBObject.team2Name = secondName;
			infoFBObject.team1GamesPlayed = firstMonthGamesPlayed;
			infoFBObject.team2GamesPlayed = secondMonthGamesPlayed;
			infoFBObject.team1AvgEnemyRating = firstAvgMonthEnemyRating;
			infoFBObject.team2AvgEnemyRating = secondAvgMonthEnemyRating;

			infoFBObject.team1AvgSkillEnemyRatingMonth = 0.0;
			infoFBObject.team1AvgSkillEnemyRatingTen = 0.0;
			infoFBObject.team2AvgSkillEnemyRatingMonth = 0.0;
			infoFBObject.team2AvgSkillEnemyRatingTen = 0.0;

			infoFBObject.team1MedianTime = firstFBMedianTime;
			infoFBObject.team2MedianTime = secondFBMedianTime;
			infoFBObject.team1Form = firstFBForm;
			infoFBObject.team2Form = secondFBForm;
			infoFBObject.team1PercentIfFB = 0;
			infoFBObject.team2PercentIfFB = 0;

			InfoObject infoF10KObject = new InfoObject();
			infoF10KObject.team1HorribleGamesPercent = firstHorribleGameF10KPercent;
			infoF10KObject.team1BadGamesPercent = firstBadGameF10KPercent;
			infoF10KObject.team1NormalGamesPercent = firstNormalGameF10KPercent;
			infoF10KObject.team1GoodGamesPercent = firstGoodGameF10KPercent;
			infoF10KObject.team1PerfectGamesPercent = firstPerfectGameF10KPercent;

			infoF10KObject.team2HorribleGamesPercent = secondHorribleGameF10KPercent;
			infoF10KObject.team2BadGamesPercent = secondBadGameF10KPercent;
			infoF10KObject.team2NormalGamesPercent = secondNormalGameF10KPercent;
			infoF10KObject.team2GoodGamesPercent = secondGoodGameF10KPercent;
			infoF10KObject.team2PerfectGamesPercent = secondPerfectGameF10KPercent;

			infoF10KObject.team1Percent = firstAvgF10KPercent;
			infoF10KObject.team2Percent = secondAvgF10KPercent;
			infoF10KObject.team1Name = firstName;
			infoF10KObject.team2Name = secondName;
			infoF10KObject.team1GamesPlayed = firstMonthGamesPlayed;
			infoF10KObject.team2GamesPlayed = secondMonthGamesPlayed;
			infoF10KObject.team1AvgEnemyRating = firstAvgMonthEnemyRating;
			infoF10KObject.team2AvgEnemyRating = secondAvgMonthEnemyRating;

			infoF10KObject.team1AvgSkillEnemyRatingMonth = 0.0;
			infoF10KObject.team1AvgSkillEnemyRatingTen = 0.0;
			infoF10KObject.team2AvgSkillEnemyRatingMonth = 0.0;
			infoF10KObject.team2AvgSkillEnemyRatingTen = 0.0;

			infoF10KObject.team1MedianTime = firstF10KMedianTime;
			infoF10KObject.team2MedianTime = secondF10KMedianTime;
			infoF10KObject.team1Form = firstF10KForm;
			infoF10KObject.team2Form = secondF10KForm;
			infoF10KObject.team1PercentIfFB = firstPercentWhenFBWasGet;
			infoF10KObject.team2PercentIfFB = secondPercentWhenFBWasGet;
			//</editor-fold>

			System.out.println(firstObjectsCopy.get(0).teamName + " Tier1 games played:" + firstTier1Objects.size() + ". Winrate:" + getWinrate(firstTier1Objects) + "%");
			System.out.println(secondObjectsCopy.get(0).teamName + " Tier1 games played:" + secondTier1Objects.size() + ". Winrate:" + getWinrate(secondTier1Objects) + "%");
			System.out.println(firstObjectsCopy.get(0).teamName + " Tier2 games played:" + firstTier2Objects.size() + ". Winrate:" + getWinrate(firstTier2Objects) + "%");
			System.out.println(secondObjectsCopy.get(0).teamName + " Tier2 games played:" + secondTier2Objects.size() + ". Winrate:" + getWinrate(secondTier2Objects) + "%");
			System.out.println(firstObjectsCopy.get(0).teamName + " Tier3 games played:" + firstTier3Objects.size() + ". Winrate:" + getWinrate(firstTier3Objects) + "%");
			System.out.println(secondObjectsCopy.get(0).teamName + "Tier3 games played:" + secondTier3Objects.size() + ". Winrate:" + getWinrate(secondTier3Objects) + "%");
			System.out.println(firstObjectsCopy.get(0).teamName + " Tier4 games played:" + firstTier4Objects.size() + ". Winrate:" + getWinrate(firstTier4Objects) + "%");
			System.out.println(secondObjectsCopy.get(0).teamName + " Tier4 games played:" + secondTier4Objects.size() + ". Winrate:" + getWinrate(secondTier4Objects) + "%");

			Integer choice = 0;
			while (choice != 10)
			{
				System.out.println("/-What do u want to know about future?");
				System.out.println("/--1: FB Statistics.");
				System.out.println("/--2: F10K Statistics.");
				System.out.println("/--3: Win Analitics.");
				System.out.println("/--4: ABC Analitics.");
				System.out.println("/--5: Standins Analitics.");
				System.out.println("/--6: Kills and Time Analitics.");
				System.out.println("/--7: Players role points.");
				System.out.println("/--8: League Analitics.");
				System.out.println("/--9: Output for analiticians.");
				System.out.println("/--10: Exit.");
				choice = s.nextInt();
				switch (choice)
				{
					//<editor-fold desc="FB CASE">
					case 1:
					{
						new Window("FB", infoFBObject);
						Integer secondChoice = 0;
						while (secondChoice != 9)
						{
							System.out.println("/-- FB Statistics.");
							System.out.println("/--1: Last 10 Matches.");
							System.out.println("/--2: Last 10 Days Matches.");
							System.out.println("/--9: Back.");
							secondChoice = s.nextInt();
							switch (secondChoice)
							{
								case 1:
								{
									System.out.println("---" + firstName + " Team Last 10 Games---");
									for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
									{
										System.out.println(firstLastTenOrLessGames.get(i).date + ";" + firstLastTenOrLessGames.get(i).enemyTeamName + ";" + firstLastTenOrLessGames.get(i).isFB + ";" + firstLastTenOrLessGames.get(i).FB + ";" + firstLastTenOrLessGames.get(i).leagueName + ".");
									}
									System.out.println("---" + secondName + " Team Last 10 Games---");
									for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
									{
										System.out.println(secondLastTenOrLessGames.get(i).date + ";" + secondLastTenOrLessGames.get(i).enemyTeamName + ";" + secondLastTenOrLessGames.get(i).isFB + ";" + secondLastTenOrLessGames.get(i).FB + ";" + secondLastTenOrLessGames.get(i).leagueName + ".");
									}
									break;
								}
								case 2:
								{
									System.out.println("---" + firstName + " Team Last 10 days Matches---");
									for (int i = 0; i < firstTenDaysObjects.size(); i++)
									{
										System.out.println(firstTenDaysObjects.get(i).date + ";" + firstTenDaysObjects.get(i).enemyTeamName + ";" + firstTenDaysObjects.get(i).isFB + ";" + firstTenDaysObjects.get(i).FB + ";" + firstTenDaysObjects.get(i).leagueName + ".");
									}
									System.out.println("---" + secondName + " Team Last 10 days Matches---");
									for (int i = 0; i < secondTenDaysObjects.size(); i++)
									{
										System.out.println(secondTenDaysObjects.get(i).date + ";" + secondTenDaysObjects.get(i).enemyTeamName + ";" + secondTenDaysObjects.get(i).isFB + ";" + secondTenDaysObjects.get(i).FB + ";" + secondTenDaysObjects.get(i).leagueName + ".");
									}
									System.out.println("--- Meetings History:");
									for (int i = 0; i < matchesHistory.size(); i++)
									{
										System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).isFB + ";" + matchesHistory.get(i).FB + ";" + matchesHistory.get(i).leagueName);
									}
								}
							}
						}
						break;
					}
					//</editor-fold>
					//<editor-fold desc="F10K CASE">
					case 2:
					{
						new Window("F10K", infoF10KObject);
						Integer secondChoice = 0;
						while (secondChoice != 9)
						{
							System.out.println("/---  F10K Statistics.");
							System.out.println("/--1: Last 10 Matches.");
							System.out.println("/--2: Last 10 Days Matches.");
							System.out.println("/--3: First 10 Minutes Aggression.");
							System.out.println("/--9: Back.");
							secondChoice = s.nextInt();
							switch (secondChoice)
							{
								case 1:
								{
									System.out.println("---" + firstName + " Team Last 10 Games---");
									for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
									{
										System.out.println(firstLastTenOrLessGames.get(i).date + ";" + firstLastTenOrLessGames.get(i).enemyTeamName + ";" + firstLastTenOrLessGames.get(i).isF10K + ";" + firstLastTenOrLessGames.get(i).tenKills + ";" + firstLastTenOrLessGames.get(i).leagueName);
									}
									System.out.println("---" + secondName + " Team Last 10 Games---");
									for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
									{
										System.out.println(secondLastTenOrLessGames.get(i).date + ";" + secondLastTenOrLessGames.get(i).enemyTeamName + ";" + secondLastTenOrLessGames.get(i).isF10K + ";" + secondLastTenOrLessGames.get(i).tenKills + ";" + secondLastTenOrLessGames.get(i).leagueName);
									}
									break;
								}
								case 2:
								{
									System.out.println("---" + firstName + " Team Last 10 days Matches---");
									for (int i = 0; i < firstTenDaysObjects.size(); i++)
									{
										System.out.println(firstTenDaysObjects.get(i).date + ";" + firstTenDaysObjects.get(i).enemyTeamName + ";" + firstTenDaysObjects.get(i).isF10K + ";" + firstTenDaysObjects.get(i).tenKills + ";" + firstTenDaysObjects.get(i).leagueName);
									}
									System.out.println("---" + secondName + " Team Last 10 days Matches---");
									for (int i = 0; i < secondTenDaysObjects.size(); i++)
									{
										System.out.println(secondTenDaysObjects.get(i).date + ";" + secondTenDaysObjects.get(i).enemyTeamName + ";" + secondTenDaysObjects.get(i).isF10K + ";" + secondTenDaysObjects.get(i).tenKills + ";" + secondTenDaysObjects.get(i).leagueName);
									}
									System.out.println("--- Meetings History:");
									for (int i = 0; i < matchesHistory.size(); i++)
									{
										System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).isF10K + ";" + matchesHistory.get(i).tenKills + ";" + matchesHistory.get(i).leagueName);
									}
									break;
								}
								case 3:
								{
									System.out.println("/---First 10 minutes Aggression Coefficient");
									System.out.println(firstMonthObjects.get(0).teamName + " Kills:" + firstMonthKillsAggressionCoef + "/" + firstTenDaysKillsAggressionCoef + " Deaths:" + firstMonthDeathsAggressionCoef + "/" + firstTenDaysDeathsAggressionCoef);
									System.out.println(secondMonthObjects.get(0).teamName + " Kills:" + secondMonthKillsAggressionCoef + "/" + secondTenDaysKillsAggressionCoef + " Deaths:" + secondMonthDeathsAggressionCoef + "/" + secondTenDaysDeathsAggressionCoef);
								}
							}
						}
						break;
					}
					//</editor-fold>
					//<editor-fold desc="WIN CASE">
					case 3:
					{
						Integer secondChoice = 0;
						while (secondChoice != 9)
						{
							System.out.println("/--- Win Analitics.");
							System.out.println("/--1: Global statistics.");
							System.out.println("/--2: Parameters.");
							System.out.println("/--3: Enemy's Statistics.");
							System.out.println("/--4: Last 10 Matches.");
							System.out.println("/--5: Last 10 days Matches.");
							System.out.println("/--6: Game Stages.");
							System.out.println("/--9: Back.");
							secondChoice = s.nextInt();

							switch (secondChoice)
							{
								case 1:
								{
									System.out.println("---Global Statistics---");
									System.out.println("TeamName. First:" + firstName + "\tSecond:" + secondName);
									System.out.println("Month Games Played. First:" + firstMonthGamesPlayed + "\tSecond:" + secondMonthGamesPlayed);
									System.out.println("AvgEnemyRating. First:" + firstAvgMonthEnemyRating + "\tSecond:" + secondAvgMonthEnemyRating);
									System.out.println("Streak. First:" + firstStreak + "\tSecond:" + secondStreak);
									System.out.println("Difference. First:" + firstDiff + "\tSecond:" + secondDiff);
									break;
								}
								case 2:
								{
									System.out.println("---Parameters---");
									System.out.println("Farming. First:" + firstFarming + "\tSecond:" + secondFarming);
									System.out.println("Vision. First:" + firstVision + "\tSecond:" + secondVision);
									System.out.println("Pushing. First:" + firstPushing + "\tSecond:" + secondPushing);
									System.out.println("Lining. First:" + firstLining + "\tSecond:" + secondLining);
									break;
								}
								case 3:
								{
									System.out.println("---Enemy's Statistic's---");
									System.out.println("\t\t\t\tGreat enemy");
									System.out.println("First:" + firstGamesNumberGreatEnemy + " games with " + firstGreatEnemyPercent + "%" + "\tSecond:" + secondGamesNumberGreatEnemy + " games with " + secondGreatEnemyPercent + "%");
									System.out.println("\t\t\t\tGood enemy");
									System.out.println("First:" + firstGamesNumberGoodEnemy + " games with " + firstGoodEnemyPercent + "%" + "\tSecond:" + secondGamesNumberGoodEnemy + " games with " + secondGoodEnemyPercent + "%");
									System.out.println("\t\t\t\tNormal enemy");
									System.out.println("First:" + firstGamesNumberNormalEnemy + " games with " + firstNormalEnemyPercent + "%" + "\tSecond:" + secondGamesNumberNormalEnemy + " games with " + secondNormalEnemyPercent + "%");
									System.out.println("\t\t\t\tBad enemy");
									System.out.println("First:" + firstGamesNumberBadEnemy + " games with " + firstBadEnemyPercent + "%" + "\tSecond:" + secondGamesNumberBadEnemy + " games with " + secondBadEnemyPercent + "%");
									break;
								}
								case 4:
								{
									System.out.println("---" + firstName + " Team Last 10 Games---");
									for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
									{
										System.out.println("Date:" + firstLastTenOrLessGames.get(i).date + ". Opponent:" + firstLastTenOrLessGames.get(i).enemyTeamName + ". Enemy rating:" + firstLastTenOrLessGames.get(i).enemyGlobalRating + ". MatchTime:" + firstLastTenOrLessGames.get(i).matchTime + ". IsWin:" + firstLastTenOrLessGames.get(i).isWin + ". Hardness" + firstLastTenOrLessGames.get(i).matchHardness + ". EG:" + firstLastTenOrLessGames.get(i).EG + ". MG:" + firstLastTenOrLessGames.get(i).MG + ". LG:" + firstLastTenOrLessGames.get(i).LG + ". League:" + firstLastTenOrLessGames.get(i).leagueName);
									}
									System.out.println("---" + secondName + " Team Last 10 Games---");
									for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
									{
										System.out.println("Date:" + secondLastTenOrLessGames.get(i).date + ". Opponent:" + secondLastTenOrLessGames.get(i).enemyTeamName + ". Enemy rating:" + secondLastTenOrLessGames.get(i).enemyGlobalRating + ". MatchTime:" + secondLastTenOrLessGames.get(i).matchTime + ". IsWin:" + secondLastTenOrLessGames.get(i).isWin + ". Hardness" + secondLastTenOrLessGames.get(i).matchHardness + ". EG:" + secondLastTenOrLessGames.get(i).EG + ". MG:" + secondLastTenOrLessGames.get(i).MG + ". LG:" + secondLastTenOrLessGames.get(i).LG + ". League:" + secondLastTenOrLessGames.get(i).leagueName);
									}
									break;
								}
								case 5:
								{
									System.out.println("---" + firstName + " Team Last 10 days Matches---");
									for (int i = 0; i < firstTenDaysObjects.size(); i++)
									{
										System.out.println("Date:" + firstTenDaysObjects.get(i).date + ". Opponent:" + firstTenDaysObjects.get(i).enemyTeamName + ". Enemy rating:" + firstTenDaysObjects.get(i).enemyGlobalRating + ". MatchTime:" + firstTenDaysObjects.get(i).matchTime + ". IsWin:" + firstTenDaysObjects.get(i).isWin + ". HardNess" + firstTenDaysObjects.get(i).matchHardness + ". EG:" + firstTenDaysObjects.get(i).EG + ". MG:" + firstTenDaysObjects.get(i).MG + ". LG:" + firstTenDaysObjects.get(i).LG + ". League:" + firstTenDaysObjects.get(i).leagueName);
									}
									System.out.println("---" + secondName + " Team Last 10 days Matches---");
									for (int i = 0; i < secondTenDaysObjects.size(); i++)
									{
										System.out.println("Date:" + secondTenDaysObjects.get(i).date + ". Opponent:" + secondTenDaysObjects.get(i).enemyTeamName + ". Enemy rating:" + secondTenDaysObjects.get(i).enemyGlobalRating + ". MatchTime:" + secondTenDaysObjects.get(i).matchTime + ". IsWin:" + secondTenDaysObjects.get(i).isWin + ". HardNess" + secondTenDaysObjects.get(i).matchHardness + ". EG:" + secondTenDaysObjects.get(i).EG + ". MG:" + secondTenDaysObjects.get(i).MG + ". LG:" + secondTenDaysObjects.get(i).LG + ". League:" + secondTenDaysObjects.get(i).leagueName);
									}
									System.out.println("---Meetings History---");
									for (int i = 0; i < matchesHistory.size(); i++)
									{
										System.out.println((i + 1) + ". Date:" + matchesHistory.get(i).date + ". Team1:" + matchesHistory.get(i).teamName + ". Team2:" + matchesHistory.get(i).enemyTeamName + ". MatchTime:" + matchesHistory.get(i).matchTime + ". IsWin:" + matchesHistory.get(i).isWin + ". HardNess" + matchesHistory.get(i).matchHardness + ". EG:" + matchesHistory.get(i).EG + ". MG:" + matchesHistory.get(i).MG + ". LG:" + matchesHistory.get(i).LG + ". League:" + matchesHistory.get(i).leagueName);
									}
								}
								case 6:
								{
									System.out.println("---Game Stages---");
									System.out.println(firstObjectsCopy.get(0).teamName + " Stats. Early Game:" + firstEGPoints + ". Middle Game:" + firstMGPoints + ". Late Game:" + firstLGPoints);
									System.out.println(secondObjectsCopy.get(0).teamName + " Stats. Early Game:" + secondEGPoints + ". Middle Game:" + secondMGPoints + ". Late Game:" + secondLGPoints);
								}
							}
						}
						break;
					}
					//</editor-fold>
					//<editor-fold desc="ABC CASE">
					case 4:
					{
						Integer abcChoice = 0;
						while (abcChoice != 9)
						{
							System.out.println("/--- ABC Analitics.");
							System.out.println("/--1: Month ABC.");
							System.out.println("/--2: Ten Days ABC.");
							System.out.println("/--9: Back.");
							abcChoice = s.nextInt();
							switch (abcChoice)
							{
								case 1:
								{
									abcAnalitics.checkForABCMatches(firstMonthObjects, secondMonthObjects);
									break;
								}
								case 2:
								{
									abcAnalitics.checkForABCMatches(firstTenDaysObjects, secondTenDaysObjects);
									break;
								}
								case 9:
								{
									break;
								}
							}
						}
						break;
					}
					//</editor-fold
					//<editor-fold desc="STANDIN CASE">
					case 5:
					{
						System.out.println("---Standin's Analitics---");
						standinAnalitics.lookForStandins(firstMonthObjects, firstTenDaysObjects, secondMonthObjects, secondTenDaysObjects);
						break;
					}
					//</editor-fold>
					//<editor-fold desc="KILLS, TIMES CASE">
					case 6:
					{
						Integer tkChoice = 0;
						while (tkChoice != 9)
						{
							System.out.println("/--- Total Time and Kills Analitics.");
							System.out.println("/--1: Global Statistics.");
							System.out.println("/--2: Last 5 Games.");
							System.out.println("/--9: Back.");
							tkChoice = s.nextInt();
							switch (tkChoice)
							{
								case 1:
								{
									System.out.println("\t\t\t\tGlobal Statistics:");
									System.out.println(firstMonthObjects.get(0).teamName + " avg. match time:" + firstMonthMedianMatchTime + "/" + firstTenMedianMatchTime + ". Kills:" + (firstMonthMedianKills + firstMonthMedianDeaths) + "/" + (firstTenMedianKills + firstTenMedianDeaths));
									System.out.println(secondMonthObjects.get(0).teamName + " avg. match time:" + secondMonthMedianMatchTime + "/" + secondTenMedianMatchTime + ". Kills:" + (secondMonthMedianKills + secondMonthMedianDeaths) + "/" + (secondTenMedianKills + secondTenMedianDeaths));
									System.out.println(firstMonthObjects.get(0).teamName + " time over 40 mins:" + firstMonthPercentTimeOver + "/" + firstTenPercentTimeOver + "%. Kills over 45:" + firstMonthPercentKillsOver + "/" + firstTenPercentKillsOver + "%");
									System.out.println(secondMonthObjects.get(0).teamName + " time over 40 mins:" + secondMonthPercentTimeOver + "/" + secondTenPercentTimeOver + "%. Kills over 45:" + secondMonthPercentKillsOver + "/" + secondTenPercentKillsOver + "%");
									System.out.println(firstMonthObjects.get(0).teamName + " aggression coefficient:" + firstMonthAggressionCoefficient + "/" + secondTenAggressionCoefficient);
									System.out.println(secondMonthObjects.get(0).teamName + " aggression coefficient:" + secondMonthAggressionCoefficient + "/" + secondTenAggressionCoefficient);
									System.out.println("Agression coefficient Sum:" + (firstMonthAggressionCoefficient + secondMonthAggressionCoefficient) + "/" + (firstTenAggressionCoefficient + secondTenAggressionCoefficient));
									break;
								}
								case 2:
								{
									System.out.println("\t\t\t\t" + firstMonthObjects.get(0).teamName + " Last 5 Games:");
									for (int i = 0; i < firstLastFiveGames.size(); i++)
									{
										System.out.println((i + 1) + ":" + firstLastFiveGames.get(i).enemyTeamName + ";" + firstLastFiveGames.get(i).matchTime + ";" + (firstLastFiveGames.get(i).kills + firstLastFiveGames.get(i).deaths));
									}
									System.out.println("\t\t\t\t" + secondMonthObjects.get(0).teamName + " Last 5 Games:");
									for (int i = 0; i < secondLastFiveGames.size(); i++)
									{
										System.out.println((i + 1) + ":" + secondLastFiveGames.get(i).enemyTeamName + ";" + secondLastFiveGames.get(i).matchTime + ";" + (secondLastFiveGames.get(i).kills + secondLastFiveGames.get(i).deaths));
									}
								}
								case 3:
								{
									System.out.println("\t\t\t\tMeetingsHistory:");
									for (int i = 0; i < matchesHistory.size(); i++)
									{
										System.out.println((i + 1) + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).matchTime + ";" + (matchesHistory.get(i).kills + matchesHistory.get(i).deaths));
									}
									break;
								}
							}
						}
						break;
					}
					//</editor-fold>
					//<editor-fold desc="ROLE CASE">
					case 7:
					{
						roleAnalitics.start_work(firstMonthObjects, secondMonthObjects, firstTenDaysObjects, secondTenDaysObjects);
						break;
					}
					//</editor-fold>
					//<editor-fold desc="LEAGUE CASE">
					case 8:
					{
						leagueAnalitics.start_work(firstMonthObjects, firstTenDaysObjects, secondMonthObjects, secondTenDaysObjects);
						break;
					}
					//</editor-fold>
					//<editor-fold desc="OUTPUT">
					case 9:
					{
						String writeString = "";
						//TODO createAndWriteToFile(writeString,firstObjectsCopy.get(0).teamName,secondObjectsCopy.get(0).teamName);

						System.out.println("\t\t\t\tMATCH " + firstObjectsCopy.get(0).teamName + " VS " + secondObjectsCopy.get(0).teamName);
						if (globalChoice == 1)
							System.out.println("Matches: Search by TIER" + tierLevel);
						if (globalChoice == 2)
							System.out.println("Matches: Search by league" + tierLevel);
						else
							System.out.println("Matches : All matches.");


						System.out.println(firstObjectsCopy.get(0).teamName + " Tier1 games played:" + firstTier1Objects.size() + ". Winrate:" + getWinrate(firstTier1Objects) + "%");
						System.out.println(secondObjectsCopy.get(0).teamName + " Tier1 games played:" + secondTier1Objects.size() + ". Winrate:" + getWinrate(secondTier1Objects) + "%");
						System.out.println(firstObjectsCopy.get(0).teamName + " Tier2 games played:" + firstTier2Objects.size() + ". Winrate:" + getWinrate(firstTier2Objects) + "%");
						System.out.println(secondObjectsCopy.get(0).teamName + " Tier2 games played:" + secondTier2Objects.size() + ". Winrate:" + getWinrate(secondTier2Objects) + "%");
						System.out.println(firstObjectsCopy.get(0).teamName + " Tier3 games played:" + firstTier3Objects.size() + ". Winrate:" + getWinrate(firstTier3Objects) + "%");
						System.out.println(secondObjectsCopy.get(0).teamName + "Tier3 games played:" + secondTier3Objects.size() + ". Winrate:" + getWinrate(secondTier3Objects) + "%");
						System.out.println(firstObjectsCopy.get(0).teamName + " Tier4 games played:" + firstTier4Objects.size() + ". Winrate:" + getWinrate(firstTier4Objects) + "%");
						System.out.println(secondObjectsCopy.get(0).teamName + " Tier4 games played:" + secondTier4Objects.size() + ". Winrate:" + getWinrate(secondTier4Objects) + "%");

						//roleAnalitics.start_work(firstMonthObjects, secondMonthObjects);

						System.out.println("\t\t\t\t---Global Statistics---");
						System.out.println("TeamName. First:" + firstName + "\tSecond:" + secondName);
						System.out.println("Month Games Played. First:" + firstMonthGamesPlayed + "\tSecond:" + secondMonthGamesPlayed);
						System.out.println("AvgEnemyRating. First:" + firstAvgMonthEnemyRating + "\tSecond:" + secondAvgMonthEnemyRating);
						System.out.println("Streak. First:" + firstStreak + "\tSecond:" + secondStreak);
						System.out.println("Difference. First:" + firstDiff + "\tSecond:" + secondDiff);
						System.out.println("\t\t\t\t---Parameters---");
						System.out.println("Farming. First:" + firstFarming + "\tSecond:" + secondFarming);
						System.out.println("Vision. First:" + firstVision + "\tSecond:" + secondVision);
						System.out.println("Pushing. First:" + firstPushing + "\tSecond:" + secondPushing);
						System.out.println("Lining. First:" + firstLining + "\tSecond:" + secondLining);
						System.out.println("\t\t\t\t---Enemy's Statistic's---");
						System.out.println("\t\tGreat enemy");
						System.out.println("First:" + firstGamesNumberGreatEnemy + " games with " + firstGreatEnemyPercent + "%" + "\tSecond:" + secondGamesNumberGreatEnemy + " games with " + secondGreatEnemyPercent + "%");
						System.out.println("\t\tGood enemy");
						System.out.println("First:" + firstGamesNumberGoodEnemy + " games with " + firstGoodEnemyPercent + "%" + "\tSecond:" + secondGamesNumberGoodEnemy + " games with " + secondGoodEnemyPercent + "%");
						System.out.println("\t\tNormal enemy");
						System.out.println("First:" + firstGamesNumberNormalEnemy + " games with " + firstNormalEnemyPercent + "%" + "\tSecond:" + secondGamesNumberNormalEnemy + " games with " + secondNormalEnemyPercent + "%");
						System.out.println("\t\tBad enemy");
						System.out.println("First:" + firstGamesNumberBadEnemy + " games with " + firstBadEnemyPercent + "%" + "\tSecond:" + secondGamesNumberBadEnemy + " games with " + secondBadEnemyPercent + "%");
						System.out.println("\t\t\t\t---" + firstName + " Team Last 10 Games---");
						for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
						{
							System.out.println("Date:" + firstLastTenOrLessGames.get(i).date + ". Opponent:" + firstLastTenOrLessGames.get(i).enemyTeamName + ". Enemy rating:" + firstLastTenOrLessGames.get(i).enemyGlobalRating + ". MatchTime:" + firstLastTenOrLessGames.get(i).matchTime + ". IsWin:" + firstLastTenOrLessGames.get(i).isWin + ". Hardness" + firstLastTenOrLessGames.get(i).matchHardness + ". EG:" + firstLastTenOrLessGames.get(i).EG + ". MG:" + firstLastTenOrLessGames.get(i).MG + ". LG:" + firstLastTenOrLessGames.get(i).LG + ". League:" + firstLastTenOrLessGames.get(i).leagueName);
						}
						System.out.println("\t\t\t\t---" + secondName + " Team Last 10 Games---");
						for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
						{
							System.out.println("Date:" + secondLastTenOrLessGames.get(i).date + ". Opponent:" + secondLastTenOrLessGames.get(i).enemyTeamName + ". Enemy rating:" + secondLastTenOrLessGames.get(i).enemyGlobalRating + ". MatchTime:" + secondLastTenOrLessGames.get(i).matchTime + ". IsWin:" + secondLastTenOrLessGames.get(i).isWin + ". Hardness" + secondLastTenOrLessGames.get(i).matchHardness + ". EG:" + secondLastTenOrLessGames.get(i).EG + ". MG:" + secondLastTenOrLessGames.get(i).MG + ". LG:" + secondLastTenOrLessGames.get(i).LG + ". League:" + secondLastTenOrLessGames.get(i).leagueName);
						}
						System.out.println("\t\t\t\t---Meetings History---");
						for (int i = 0; i < matchesHistory.size(); i++)
						{
							System.out.println((i + 1) + ". Date:" + matchesHistory.get(i).date + ". Team1:" + matchesHistory.get(i).teamName + ". Team2:" + matchesHistory.get(i).enemyTeamName + ". MatchTime:" + matchesHistory.get(i).matchTime + ". IsWin:" + matchesHistory.get(i).isWin + ". HardNess" + matchesHistory.get(i).matchHardness + ". EG:" + matchesHistory.get(i).EG + ". MG:" + matchesHistory.get(i).MG + ". LG:" + matchesHistory.get(i).LG + ". League:" + matchesHistory.get(i).leagueName);
						}
						System.out.println("\t\t\t\t---ABC Analitics---");
						abcAnalitics.checkForABCMatches(firstTenDaysObjects, secondTenDaysObjects);
						roleAnalitics.start_work(firstMonthObjects, secondMonthObjects, firstTenDaysObjects, secondTenDaysObjects);
						leagueAnalitics.start_work(firstMonthObjects, firstTenDaysObjects, secondMonthObjects, secondTenDaysObjects);

						break;
					}
					//</editor-fold>
					default:
						break;
				}
			}
		}
	}

	public void fillObject(ArrayList<AnalizedInfo> objects, String allMatches) throws IOException
	{
		String[] eachMatch = allMatches.split("\n");
		for (int i = 0; i < eachMatch.length; i++)
		{
			AnalizedInfo object = new AnalizedInfo();

			String matchGeneralInfo = eachMatch[i].split("##")[0];
			String KDA = eachMatch[i].split("##")[1];
			String playerEPPs = eachMatch[i].split("##")[2];
			String parameters = eachMatch[i].split("##")[3];
			String enemyParameters = eachMatch[i].split("##")[4];
			String FBInfo = eachMatch[i].split("##")[5];
			String F10KInfo = eachMatch[i].split("##")[6];
			String FRInfo = eachMatch[i].split("##")[7];
			String killEventsInfo = eachMatch[i].split("##")[8];
			String matchHardness = eachMatch[i].split("##")[9];
			String ratingChanges = eachMatch[i].split("##")[10];

			object.id = matchGeneralInfo.split(";")[0];
			object.date = matchGeneralInfo.split(";")[1];
			object.matchTime = matchGeneralInfo.split(";")[2];
			object.teamId = matchGeneralInfo.split(";")[3];
			object.teamName = getTeamNameById(object.teamId);
			object.isWin = Boolean.valueOf(matchGeneralInfo.split(";")[4]);
			object.side = Integer.parseInt(matchGeneralInfo.split(";")[5]);
			object.EG = Integer.parseInt(matchGeneralInfo.split(";")[6]);
			object.MG = Integer.parseInt(matchGeneralInfo.split(";")[7]);
			object.LG = Integer.parseInt(matchGeneralInfo.split(";")[8]);
			object.enemyTeamName = matchGeneralInfo.split(";")[9];
			object.enemyTeamId = matchGeneralInfo.split(";")[10];
			object.leagueName = matchGeneralInfo.split(";")[11];
			object.leagueId = Integer.parseInt(matchGeneralInfo.split(";")[12]);
			object.tier = Integer.parseInt(matchGeneralInfo.split(";")[13]);

			object.kills = Integer.parseInt(KDA.split(";")[0]);
			object.deaths = Integer.parseInt(KDA.split(";")[1]);
			object.assists = Integer.parseInt(KDA.split(";")[2]);

			String[] eachEPPs = playerEPPs.split("\\|\\|");
			object.playerId[0] = eachEPPs[0].split(";")[0];
			object.playerEPP[0] = Integer.parseInt(eachEPPs[0].split(";")[1]);
			object.playerRole[0] = Integer.parseInt(eachEPPs[0].split(";")[2]);
			object.playerId[1] = eachEPPs[1].split(";")[0];
			object.playerEPP[1] = Integer.parseInt(eachEPPs[1].split(";")[1]);
			object.playerRole[1] = Integer.parseInt(eachEPPs[1].split(";")[2]);
			object.playerId[2] = eachEPPs[2].split(";")[0];
			object.playerEPP[2] = Integer.parseInt(eachEPPs[2].split(";")[1]);
			object.playerRole[2] = Integer.parseInt(eachEPPs[2].split(";")[2]);
			object.playerId[3] = eachEPPs[3].split(";")[0];
			object.playerEPP[3] = Integer.parseInt(eachEPPs[3].split(";")[1]);
			object.playerRole[3] = Integer.parseInt(eachEPPs[3].split(";")[2]);
			object.playerId[4] = eachEPPs[4].split(";")[0];
			object.playerEPP[4] = Integer.parseInt(eachEPPs[4].split(";")[1]);
			object.playerRole[4] = Integer.parseInt(eachEPPs[4].split(";")[2]);

			object.killAbility = Integer.parseInt(parameters.split(";")[0]);
			object.pushing = Integer.parseInt(parameters.split(";")[1]);
			object.vision = Integer.parseInt(parameters.split(";")[2]);
			object.lining = Integer.parseInt(parameters.split(";")[3]);
			object.tenKills = parameters.split(";")[4];
			object.FB = parameters.split(";")[5];
			object.farming = Integer.parseInt(parameters.split(";")[6]);

			object.isFB = Boolean.valueOf(FBInfo.split(";")[0]);
			object.FBTime = Integer.parseInt(FBInfo.split(";")[1]);

			object.isF10K = Boolean.valueOf(F10KInfo.split(";")[0]);
			object.F10KTime = Integer.parseInt(F10KInfo.split(";")[1]);
			object.tenMinutesKillsCoef = Double.parseDouble(F10KInfo.split(";")[2]);
			object.tenMinutesDeathsCoef = Double.parseDouble(F10KInfo.split(";")[3]);

			object.isFR = Boolean.valueOf(FRInfo.split(";")[0]);
			object.FRTime = Integer.parseInt(FRInfo.split(";")[1]);

			object.mineGlobalRating = Integer.parseInt(ratingChanges.split(";")[1]);
			object.enemyGlobalRating = Integer.parseInt(ratingChanges.split(";")[2]);
			object.mineTierRating = Integer.parseInt(ratingChanges.split(";")[3]);
			object.enemyTierRating = Integer.parseInt(ratingChanges.split(";")[4]);

			object.matchHardness = matchHardness;

			objects.add(object);
		}
	}

	//<editor-fold desc="Enemy's Statistics">
	public Integer getPercentGreatEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		Integer winGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 1300)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 1300)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			}
		}
		Double percent = 0.0;
		if (allGames != 0)
		{
			percent = (double) winGames / allGames * 100;
			return percent.intValue();
		} else
			return 9999;
	}

	public Integer getPercentGoodEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		Integer winGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 1100 && objects.get(i).enemyTierRating < 1300)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 1100 && objects.get(i).enemyGlobalRating < 1300)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			}
		}
		Double percent = 0.0;
		if (allGames != 0)
		{
			percent = (double) winGames / allGames * 100;
			return percent.intValue();
		} else
			return 9999;
	}

	public Integer getPercentNormalEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		Integer winGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 900 && objects.get(i).enemyTierRating < 1100)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 900 && objects.get(i).enemyGlobalRating < 1100)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			}
		}
		Double percent = 0.0;
		if (allGames != 0)
		{
			percent = (double) winGames / allGames * 100;
			return percent.intValue();
		} else
			return 9999;
	}

	public Integer getPercentBadEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		Integer winGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating < 900)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating < 900)
				{
					allGames++;
					if (objects.get(i).isWin)
						winGames++;
				}
			}
		}
		Double percent = 0.0;
		if (allGames != 0)
		{
			percent = (double) winGames / allGames * 100;
			return percent.intValue();
		} else
			return 9999;
	}

	public Integer getGamesNumberGreatEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 1300)
				{
					allGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 1300)
				{
					allGames++;
				}
			}
		}
		return allGames;
	}

	public Integer getGamesNumberGoodEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 1100 && objects.get(i).enemyTierRating < 1300)
				{
					allGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 1100 && objects.get(i).enemyGlobalRating < 1300)
				{
					allGames++;
				}
			}
		}
		return allGames;
	}

	public Integer getGamesNumberNormalEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating >= 900 && objects.get(i).enemyTierRating < 1100)
				{
					allGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating >= 900 && objects.get(i).enemyGlobalRating < 1100)
				{
					allGames++;
				}
			}
		}
		return allGames;
	}

	public Integer getGamesNumberBadEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier != 0)
			{
				if (objects.get(i).enemyTierRating < 900)
				{
					allGames++;
				}
			} else
			{
				if (objects.get(i).enemyGlobalRating < 900)
				{
					allGames++;
				}
			}
		}
		return allGames;
	}
	//</editor-fold>

	//<editor-fold desc="FB and F10K">
	//<editor-fold desc="FB">
	public Integer getMedianFBTime(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> FBTimeArray = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isFB == true)
			{
				FBTimeArray.add(objects.get(i).FBTime);
			}
		}
		return getMedianFromArray(FBTimeArray);
	}

	public Integer getAverageFBPercent(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isFB == true)
				points++;
			counter++;
		}
		points = points / counter * 100;
		return points.intValue();
	}

	public Integer getFBForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> TenDaysObjects) throws ParseException
	{

		if (TenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			Integer horribleGamesMonth = getHorribleGameFBPercent(monthObjects);
			Integer badGamesMonth = getBadGameFBPercent(monthObjects);
			Integer normalGamesMonth = getNormalGameFBPercent(monthObjects);
			Integer goodGamesMonth = getGoodGameFBPercent(monthObjects);
			Integer perfectGamesMonth = getPerfectGameFBPercent(monthObjects);
			Integer horribleGamesTenDays = getHorribleGameFBPercent(TenDaysObjects);
			Integer badGamesTenDays = getBadGameFBPercent(TenDaysObjects);
			Integer normalGamesTenDays = getNormalGameFBPercent(TenDaysObjects);
			Integer goodGamesTenDays = getGoodGameFBPercent(TenDaysObjects);
			Integer perfectGamesTenDays = getPerfectGameFBPercent(TenDaysObjects);
			Integer form = perfectGamesTenDays * 5 + 4 * goodGamesTenDays + 3 * normalGamesTenDays + 2 * badGamesTenDays + 1 * horribleGamesTenDays - 5 * perfectGamesMonth - 4 * goodGamesMonth - 3 * normalGamesMonth - 2 * badGamesMonth - 1 * horribleGamesMonth;
			return form;
		}
	}

	public Integer getHorribleGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB.equals("horrible"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getBadGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB.equals("bad"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getNormalGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB.equals("normal"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getGoodGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB.equals("good"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getPerfectGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB.equals("perfect"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}
	//</editor-fold>

	//<editor-fold desc="F10K">

	public Integer getPercentWhenFBWasGet(ArrayList<AnalizedInfo> objects)
	{
		Integer fbGets = 0;
		Integer f10kGets = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isFB == true)
			{
				fbGets++;
				if (objects.get(i).isF10K == true)
				{
					f10kGets++;
				}
			}
		}
		Double temp = (double) f10kGets / fbGets * 100;
		return temp.intValue();
	}

	public Integer getMedianF10KTime(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> F10KTimeArray = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isF10K == true)
			{
				F10KTimeArray.add(objects.get(i).F10KTime);
			}
		}
		return getMedianFromArray(F10KTimeArray);
	}

	public Integer getAverageF10KPercent(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				if (objects.get(i).isF10K == true)
					points++;
				counter++;
			}
		}
		points = points / counter * 100;
		return points.intValue();
	}

	public Integer getF10KForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> TenDaysObjects) throws ParseException
	{
		if (TenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			Integer horribleGamesMonth = getHorribleGameF10KPercent(monthObjects);
			Integer badGamesMonth = getBadGameF10KPercent(monthObjects);
			Integer normalGamesMonth = getNormalGameF10KPercent(monthObjects);
			Integer goodGamesMonth = getGoodGameF10KPercent(monthObjects);
			Integer perfectGamesMonth = getPerfectGameF10KPercent(monthObjects);
			Integer horribleGamesTenDays = getHorribleGameF10KPercent(monthObjects);
			Integer badGamesTenDays = getBadGameF10KPercent(TenDaysObjects);
			Integer normalGamesTenDays = getNormalGameF10KPercent(TenDaysObjects);
			Integer goodGamesTenDays = getGoodGameF10KPercent(TenDaysObjects);
			Integer perfectGamesTenDays = getPerfectGameF10KPercent(TenDaysObjects);
			Integer form = 5 * perfectGamesTenDays + 4 * goodGamesTenDays + 3 * normalGamesTenDays + 2 * badGamesTenDays + horribleGamesTenDays - 5 * perfectGamesMonth - 4 * goodGamesMonth - 3 * normalGamesMonth - 2 * badGamesMonth - horribleGamesMonth;
			return form;
		}
	}

	public Integer getHorribleGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills.equals("horrible"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getBadGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills.equals("bad"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getNormalGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills.equals("normal"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getGoodGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills.equals("good"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	public Integer getPerfectGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills.equals("perfect"))
			{
				thisCounter++;
			}
			counter++;
		}
		Double percent = (double) thisCounter / counter * 100;
		return (int) Math.round(percent);
	}

	private Double getDeathsAggressionCoef(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Double> deathsCoef = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			deathsCoef.add(objects.get(i).tenMinutesDeathsCoef);
		}
		return getDoubleMedianFromArray(deathsCoef);
	}

	private Double getKillsAggressionCoef(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Double> killsCoef = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			killsCoef.add(objects.get(i).tenMinutesKillsCoef);
		}
		return getDoubleMedianFromArray(killsCoef);
	}
	//</editor-fold>
	//</editor-fold>

	//<editor-fold desc="WinData">
	private Integer getMedianGameStagePoints(ArrayList<AnalizedInfo> objects, int stage)
	{
		ArrayList<Integer> array = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			if (stage == 1)
			{
				if (objects.get(i).EG != 9999)
					array.add(objects.get(i).EG);
			}
			if (stage == 2)
			{
				if (objects.get(i).MG != 9999)
					array.add(objects.get(i).MG);
			}
			if (stage == 3)
			{
				if (objects.get(i).LG != 9999)
					array.add(objects.get(i).LG);
			}
		}
		return getMedianFromArray(array);
	}

	private String getWinrate(ArrayList<AnalizedInfo> objects)
	{
		Integer winGames = 0;
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isWin)
				winGames++;
			allGames++;
		}
		double winrate = (double) winGames / allGames * 10000;
		winrate = Math.round(winrate);
		winrate = winrate / 100;
		return winrate + "";

	}

	private Double getAggressionCoefficient(Integer medianKills, Integer medianMatchTime)
	{
		Double coef = (double) medianKills / medianMatchTime * 100;
		Integer some = coef.intValue();
		coef = (double) some / 100;
		return coef;
	}

	public Integer getCurrentStreak(ArrayList<AnalizedInfo> objects)
	{
		Integer streak = 0;
		Boolean firstWin = false;
		Boolean firstLose = false;
		Boolean checked = false;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).isWin && !checked)
			{
				firstWin = true;
				checked = true;
			}
			if (!objects.get(i).isWin && !checked)
			{
				firstLose = true;
				checked = true;
			}
			if (firstWin && checked)
			{
				if (objects.get(i).isWin)
					streak++;
				else
					return streak;
			}
			if (firstLose && checked)
			{
				if (objects.get(i).isWin == false)
					streak--;
				else
					return streak;
			}
		}
		return streak;
	}

	public Integer getLastTenMatchesDiff(ArrayList<AnalizedInfo> lastTenMatches)
	{
		if (lastTenMatches.size() != 10)
			return 0;
		Integer diff = 0;
		for (int i = 0; i < lastTenMatches.size(); i++)
		{
			if (lastTenMatches.get(i).isWin == true)
				diff++;
			else
				diff--;
		}
		return diff;
	}

	public Integer getMediumFarming(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).farming);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMediumPushing(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).pushing);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMediumLining(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).lining);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMediumVision(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).vision);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMedianMatchTime(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(Integer.parseInt(objects.get(i).matchTime));
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMedianKills(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).kills);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getMedianDeaths(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> array = new ArrayList<>();
		Integer median = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).deaths);
		}
		median = getMedianFromArray(array);
		return median;
	}

	public Integer getPercentKillsOver(ArrayList<AnalizedInfo> objects)
	{
		int allGames = 0;
		int yesGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).kills + objects.get(i).deaths >= 45)
			{
				yesGames++;
			}
			allGames++;
		}
		Double percent = (double) yesGames / allGames * 100;

		return percent.intValue();
	}

	public Integer getPercentTimeOver(ArrayList<AnalizedInfo> objects)
	{
		int allGames = 0;
		int yesGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (Integer.parseInt(objects.get(i).matchTime) >= 40)
			{
				yesGames++;
			}
			allGames++;
		}
		Double percent = (double) yesGames / allGames * 100;
		return percent.intValue();
	}
	//</editor-fold>

	//<editor-fold desc="GeneralInfo">
	public Integer getAverageEnemyRating(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		ArrayList<Integer> ratingArray = new ArrayList<>();
		if (objects.size() < 4)
			return 9999;
		for (int i = 0; i < objects.size(); i++)
		{
			if (globalTier == 0)
				ratingArray.add(objects.get(i).enemyGlobalRating);
			else if (objects.get(i).tier == globalTier)
				ratingArray.add(objects.get(i).enemyTierRating);
		}
		Collections.sort(ratingArray);
		if (ratingArray.size() % 2 == 0)
		{
			Double value = (double) (ratingArray.get(ratingArray.size() / 2 - 1) + ratingArray.get(ratingArray.size() / 2)) / 2;
			return value.intValue();
		} else
		{
			return ratingArray.get(ratingArray.size() / 2);
		}
	}

	public Integer getMonthGamesPlayed(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				counter++;
			}
		}
		return counter;
	}

	//</editor-fold>

	//<editor-fold desc="WorkerFunctions">
	public static long getDifferenceDays(Date d1)
	{
		Date d2 = new Date();
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}

	public String getTeamNameById(String id) throws IOException
	{
		String matchFile = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] eachMatch = matchFile.split("\n");
		for (int i = 0; i < eachMatch.length; i++)
		{
			String teamId = eachMatch[i].split(";")[0];
			String teamName = eachMatch[i].split(";")[1];
			if (teamId.equals(id))
				return teamName;
		}
		return "Default";
	}

	public Integer getMedianFromArray(ArrayList<Integer> array)
	{
		Collections.sort(array);
		if (array.size() <= 4)
			return 9999;
		if (array.size() % 2 == 0)
		{
			return (int) (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		} else
		{
			return (int) array.get(array.size() / 2);
		}
	}

	public Double getDoubleMedianFromArray(ArrayList<Double> array)
	{
		Collections.sort(array);
		if (array.size() <= 4)
			return 0.0;
		if (array.size() % 2 == 0)
		{
			return (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		} else
		{
			return array.get(array.size() / 2);
		}
	}
	//</editor-fold>

	//<editor-fold desc="Object separators">
	public ArrayList<AnalizedInfo> separateTierGames(ArrayList<AnalizedInfo> array, Integer tier)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).tier == tier)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLeagueGames(ArrayList<AnalizedInfo> array, Integer league)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).leagueId == league)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLastThreeGames(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		if (array.size() < 3)
			return newArray;
		for (int i = array.size() - 1; i > array.size() - 4; i--)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 20)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLastTenGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		if (array.size() >= 10)
		{
			for (int i = array.size() - 1; i > array.size() - 11; i--)
			{
				newArray.add(array.get(i));
			}
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLastTenOrLessGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		if (array.size() >= 10)
		{
			for (int i = array.size() - 1; i > array.size() - 11; i--)
			{
				newArray.add(array.get(i));
			}
		} else
		{
			for (int i = array.size() - 1; i > array.size() - (array.size() + 1); i--)
			{
				newArray.add(array.get(i));
			}
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLastFiveGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		if (array.size() >= 5)
		{
			for (int i = array.size() - 1; i > array.size() - 6; i--)
			{
				newArray.add(array.get(i));
			}
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateTenDaysObjects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 10)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateMonthObjects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 30)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> getMeetingsMatches(ArrayList<AnalizedInfo> firstArray, ArrayList<AnalizedInfo> secondArray)
	{
		String team1Id = firstArray.get(0).teamId;
		String team2Id = secondArray.get(0).teamId;
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		for (int i = 0; i < firstArray.size(); i++)
		{
			if (firstArray.get(i).enemyTeamId.equals(team2Id))
				newArray.add(firstArray.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateTier1Objects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).tier == 1)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateTier2Objects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).tier == 2)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateTier3Objects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).tier == 3)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateTier4Objects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).tier == 4)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	//</editor-fold>
}

class InfoObject
{
	String team1Name;
	String team2Name;
	Integer team1HorribleGamesPercent;
	Integer team2HorribleGamesPercent;
	Integer team1BadGamesPercent;
	Integer team2BadGamesPercent;
	Integer team1NormalGamesPercent;
	Integer team2NormalGamesPercent;
	Integer team1GoodGamesPercent;
	Integer team2GoodGamesPercent;
	Integer team1PerfectGamesPercent;
	Integer team2PerfectGamesPercent;
	Integer team1Form;
	Integer team2Form;
	Integer team1AvgEnemyRating;
	Integer team2AvgEnemyRating;
	Double team1AvgSkillEnemyRatingMonth;
	Double team2AvgSkillEnemyRatingMonth;
	Double team1AvgSkillEnemyRatingTen;
	Double team2AvgSkillEnemyRatingTen;
	Integer team1MedianTime;
	Integer team2MedianTime;
	Integer team1GamesPlayed;
	Integer team2GamesPlayed;
	Integer team1Percent;
	Integer team2Percent;
	Integer team1PercentIfFB;
	Integer team2PercentIfFB;
}

class Window extends JFrame
{
	GSegment team1NameLabel = new GSegment();
	GSegment team2NameLabel = new GSegment();
	GSegment team1Percent = new GSegment();
	GSegment team2Percent = new GSegment();
	GSegment team1GamesPlayed = new GSegment();
	GSegment team2GamesPlayed = new GSegment();
	GSegment team1AvgEnemyRating = new GSegment();
	GSegment team2AvgEnemyRating = new GSegment();
	GSegment team1AvgSkillEnemyRatingMonth = new GSegment();
	GSegment team2AvgSkillEnemyRatingMonth = new GSegment();
	GSegment team1AvgSkillEnemyRatingTen = new GSegment();
	GSegment team2AvgSkillEnemyRatingTen = new GSegment();
	GSegment team1MedianTime = new GSegment();
	GSegment team2MedianTime = new GSegment();
	GSegment team1Form = new GSegment();
	GSegment team2Form = new GSegment();
	GSegment team1PercentIfFB = new GSegment();
	GSegment team2PercentIfFB = new GSegment();

	public Window(String what, InfoObject obj)
	{
		super(what + " Statistics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the graphic canvas
		GWindow window = new GWindow();
		getContentPane().add(window.getCanvas());
		//Label

		// Create scane with default viewport and world extent settings
		GScene scene = new GScene(window);
		//PieChart
		GStyle pieStyle = new GStyle();
		pieStyle.setFont(new Font("Dialog", Font.BOLD, 18));
		pieStyle.setLineStyle(GStyle.LINESTYLE_INVISIBLE);
		pieStyle.setForegroundColor(new Color(255, 255, 255));

		PieChart team1PieChart = new PieChart(250, 300, 100);
		team1PieChart.setStyle(pieStyle);
		team1PieChart.addSector((double) obj.team1HorribleGamesPercent / 100, "Horrible", getStyle(255, 0, 0));
		team1PieChart.addSector((double) obj.team1BadGamesPercent / 100, "Bad", getStyle(255, 128, 0));
		team1PieChart.addSector((double) obj.team1NormalGamesPercent / 100, "Normal", getStyle(255, 255, 0));
		team1PieChart.addSector((double) obj.team1GoodGamesPercent / 100, "Good", getStyle(178, 255, 102));
		team1PieChart.addSector((double) obj.team1PerfectGamesPercent / 100, "Perfect", getStyle(0, 255, 0));

		PieChart team2PieChart = new PieChart(750, 300, 100);
		team2PieChart.setStyle(pieStyle);
		team2PieChart.addSector((double) obj.team2HorribleGamesPercent / 100, "Horrible", getStyle(255, 0, 0));
		team2PieChart.addSector((double) obj.team2BadGamesPercent / 100, "Bad", getStyle(255, 128, 0));
		team2PieChart.addSector((double) obj.team2NormalGamesPercent / 100, "Normal", getStyle(255, 255, 0));
		team2PieChart.addSector((double) obj.team2GoodGamesPercent / 100, "Good", getStyle(178, 255, 102));
		team2PieChart.addSector((double) obj.team2PerfectGamesPercent / 100, "Perfect", getStyle(0, 255, 0));


		GStyle label = new GStyle();
		label.setFont(new Font("Dialog", Font.PLAIN, 20));
		label.setLineStyle(GStyle.LINESTYLE_INVISIBLE);
		label.setForegroundColor(new Color(0, 0, 0));

		team1NameLabel.setStyle(label);
		team2NameLabel.setStyle(label);

		team1Percent.setStyle(label);
		team2Percent.setStyle(label);
		team1GamesPlayed.setStyle(label);
		team2GamesPlayed.setStyle(label);
		team1AvgEnemyRating.setStyle(label);
		team2AvgEnemyRating.setStyle(label);
		team1AvgSkillEnemyRatingMonth.setStyle(label);
		team2AvgSkillEnemyRatingMonth.setStyle(label);
		team1AvgSkillEnemyRatingTen.setStyle(label);
		team2AvgSkillEnemyRatingTen.setStyle(label);
		team1MedianTime.setStyle(label);
		team2MedianTime.setStyle(label);
		team1Form.setStyle(label);
		team2Form.setStyle(label);
		team1PercentIfFB.setStyle(label);
		team2PercentIfFB.setStyle(label);

		team1NameLabel.addText(new GText("Name:" + obj.team1Name + "", GPosition.MIDDLE));
		team2NameLabel.addText(new GText("Name:" + obj.team2Name + "", GPosition.MIDDLE));
		team1Percent.addText(new GText("Percent:" + obj.team1Percent + "%", GPosition.MIDDLE));
		team2Percent.addText(new GText("Percent:" + obj.team2Percent + "%", GPosition.MIDDLE));
		team1GamesPlayed.addText(new GText("Games:" + obj.team1GamesPlayed, GPosition.MIDDLE));
		team2GamesPlayed.addText(new GText("Games:" + obj.team2GamesPlayed, GPosition.MIDDLE));
		team1AvgEnemyRating.addText(new GText("AvgEnemyRating:" + obj.team1AvgEnemyRating, GPosition.MIDDLE));
		team2AvgEnemyRating.addText(new GText("AvgEnemyRating:" + obj.team2AvgEnemyRating, GPosition.MIDDLE));
		team1AvgSkillEnemyRatingMonth.addText(new GText("AvgSkillMonth:" + obj.team1AvgSkillEnemyRatingMonth, GPosition.MIDDLE));
		team2AvgSkillEnemyRatingMonth.addText(new GText("AvgSkillMonth:" + obj.team2AvgSkillEnemyRatingMonth, GPosition.MIDDLE));
		team1AvgSkillEnemyRatingTen.addText(new GText("AvgSkillTenDays:" + obj.team1AvgSkillEnemyRatingTen, GPosition.MIDDLE));
		team2AvgSkillEnemyRatingTen.addText(new GText("AvgSkillTenDays:" + obj.team2AvgSkillEnemyRatingTen, GPosition.MIDDLE));
		team1MedianTime.addText(new GText("MedianTime:" + obj.team1MedianTime, GPosition.MIDDLE));
		team2MedianTime.addText(new GText("MedianTime:" + obj.team2MedianTime, GPosition.MIDDLE));
		team1Form.addText(new GText("Form:" + obj.team1Form + " points", GPosition.MIDDLE));
		team2Form.addText(new GText("Form:" + obj.team2Form + " points", GPosition.MIDDLE));
		team1PercentIfFB.addText(new GText("Percent If FB:" + obj.team1PercentIfFB + "%", GPosition.MIDDLE));
		team2PercentIfFB.addText(new GText("Percent If FB:" + obj.team2PercentIfFB + "%", GPosition.MIDDLE));
		scene.addSegment(team1NameLabel);
		scene.addSegment(team2NameLabel);
		scene.addSegment(team1Percent);
		scene.addSegment(team2Percent);
		scene.addSegment(team1GamesPlayed);
		scene.addSegment(team2GamesPlayed);
		scene.addSegment(team1AvgEnemyRating);
		scene.addSegment(team2AvgEnemyRating);
		scene.addSegment(team1AvgSkillEnemyRatingMonth);
		scene.addSegment(team2AvgSkillEnemyRatingMonth);
		scene.addSegment(team1AvgSkillEnemyRatingTen);
		scene.addSegment(team2AvgSkillEnemyRatingTen);
		scene.addSegment(team1MedianTime);
		scene.addSegment(team2MedianTime);
		scene.addSegment(team1Form);
		scene.addSegment(team2Form);
		scene.addSegment(team1PercentIfFB);
		scene.addSegment(team2PercentIfFB);
		scene.add(team1PieChart);
		scene.add(team2PieChart);
		pack();
		setSize(new Dimension(1000, 700));
		setVisible(true);

	}

	private GStyle getStyle(int r, int g, int b)
	{
		GStyle style = new GStyle();
		style.setBackgroundColor(new Color(r, g, b));
		return style;
	}

	private class PieChart extends GObject
	{
		private int x0_, y0_;
		private int radius_;
		private Collection sectors_;


		PieChart(int x0, int y0, int radius)
		{
			x0_ = x0;
			y0_ = y0;
			radius_ = radius;
			sectors_ = new ArrayList();
		}


		void addSector(double fraction, String text, GStyle style)
		{
			sectors_.add(new Sector(fraction, text, style));
		}


		public void draw()
		{
			team1NameLabel.setGeometry(200, 100, 300, 120);
			team2NameLabel.setGeometry(700, 100, 800, 120);
			team1Percent.setGeometry(220, 140, 280, 160);
			team2Percent.setGeometry(720, 140, 780, 160);
			team1GamesPlayed.setGeometry(10, 10, 80, 50);
			team2GamesPlayed.setGeometry(900, 10, 970, 50);
			team1AvgEnemyRating.setGeometry(150, 420, 350, 440);
			team2AvgEnemyRating.setGeometry(650, 420, 850, 440);
			team1AvgSkillEnemyRatingMonth.setGeometry(150, 450, 350, 470);
			team2AvgSkillEnemyRatingMonth.setGeometry(650, 450, 850, 470);
			team1AvgSkillEnemyRatingTen.setGeometry(150, 480, 350, 500);
			team2AvgSkillEnemyRatingTen.setGeometry(650, 480, 850, 500);
			team1MedianTime.setGeometry(150, 510, 350, 530);
			team2MedianTime.setGeometry(650, 510, 850, 530);
			team1Form.setGeometry(150, 540, 350, 560);
			team2Form.setGeometry(650, 540, 850, 560);
			team1PercentIfFB.setGeometry(150, 570, 350, 590);
			team2PercentIfFB.setGeometry(650, 570, 850, 590);
			removeSegments();

			// Loop through the sectors and draw the graphics for each
			double angle0 = 0.0;
			for (Iterator i = sectors_.iterator(); i.hasNext(); )
			{
				Sector sector = (Sector) i.next();

				//
				// Geometry for the sector itself
				//
				GSegment segment = new GSegment();
				addSegment(segment);
				segment.setStyle(sector.style);

				double angle1 = angle0 + sector.fraction * Math.PI * 2.0;

				int[] sectorGeometry = Geometry.createSector(x0_, y0_, radius_,
						angle0, angle1);

				segment.setGeometry(sectorGeometry);
				angle0 = angle1;

				//
				// Add annotation. Create an invisible line from the sector center
				// thorugh the center of the arc and out and associated annotation
				// with this line.
				//
				double[] p0 = new double[3];
				double[] p1 = new double[3];

				int nPoints = sectorGeometry.length / 2;
				int pointNo = (nPoints - 2) / 2;

				p1[0] = sectorGeometry[pointNo * 2 + 0];
				p1[1] = sectorGeometry[pointNo * 2 + 1];
				p1[2] = 0.0;

				double[] sectorCenter = Geometry.computePointOnLine(x0_, y0_,
						p1[0], p1[1],
						0.5);
				p0[0] = sectorCenter[0];
				p0[1] = sectorCenter[1];
				p0[2] = 0.0;

				// Ensure line extends far out of the sector
				Geometry.extendLine(p0, p1, 1000.0);

				GSegment annotationLine = new GSegment();
				addSegment(annotationLine);

				annotationLine.setGeometry((int) p0[0], (int) p0[1],
						(int) p1[0], (int) p1[1]);

				// Add the percentage text
				int percent = (int) Math.round(sector.fraction * 100.0);
				GText text = new GText(percent + "%", GPosition.FIRST);
				if (percent != 0)
					annotationLine.addText(text);

			}
		}
	}

	private class Sector
	{
		public double fraction;
		public String label;
		public GStyle style;

		public Sector(double fraction, String label, GStyle style)
		{
			this.fraction = fraction;
			this.label = label;
			this.style = style;
		}
	}

}


