package ProjectDir;

import ProjectDir.Analitics.ABCAnalitics;
import ProjectDir.Analitics.HeatMapAnaliticsFactory;
import ProjectDir.Analitics.StandinAnalitics;
import ProjectDir.MatchInfo.AnalizedInfo;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_COLOR_BURNPeer;
import no.geosoft.cc.geometry.Geometry;
import no.geosoft.cc.graphics.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class DataWorker
{

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	Scanner s = new Scanner(System.in);
	HeatMapAnaliticsFactory heatMapAnaliticsFactory = new HeatMapAnaliticsFactory();
	ABCAnalitics abcAnalitics = new ABCAnalitics();
	StandinAnalitics standinAnalitics = new StandinAnalitics();

	public void analizeFutureMatch(String team1Id, String team2Id) throws IOException, ParseException
	{
//		heatMapAnaliticsFactory.buildHeatMap(team1Id);
//		heatMapAnaliticsFactory.buildHeatMap(team2Id);
		System.out.println("/----------------Jack--------------------/");
		System.out.println("/-Yo-yo-yo.");
		String team1Matches = fileOperationsFactory.readFile("files/teams/" + team1Id + "/TeamMatches.txt");
		String team2Matches = fileOperationsFactory.readFile("files/teams/" + team2Id + "/TeamMatches.txt");

		analizeTeamInfo(team1Matches, team2Matches);
	}

	public void analizeTeamInfo(String team1String, String team2String) throws ParseException, IOException
	{
		ArrayList<AnalizedInfo> firstObjects = new ArrayList<AnalizedInfo>();
		ArrayList<AnalizedInfo> secondObjects = new ArrayList<AnalizedInfo>();
		fillObject(firstObjects, team1String);
		fillObject(secondObjects, team2String);

		//<editor-fold desc="Separators">
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

		ArrayList<AnalizedInfo> firstFBBadGames = separateEnemyFBBadGames(firstMonthObjects);
		ArrayList<AnalizedInfo> firstFBNormalGames = separateEnemyFBNormalGames(firstMonthObjects);
		ArrayList<AnalizedInfo> firstFBNiceGames = separateEnemyFBNiceGames(firstMonthObjects);
		ArrayList<AnalizedInfo> secondFBBadGames = separateEnemyFBBadGames(secondMonthObjects);
		ArrayList<AnalizedInfo> secondFBNormalGames = separateEnemyFBNormalGames(secondMonthObjects);
		ArrayList<AnalizedInfo> secondFBNiceGames = separateEnemyFBNiceGames(secondMonthObjects);

		ArrayList<AnalizedInfo> firstF10KBadGames = separateEnemyF10KBadGames(firstMonthObjects);
		ArrayList<AnalizedInfo> firstF10KNormalGames = separateEnemyF10KNormalGames(firstMonthObjects);
		ArrayList<AnalizedInfo> firstF10KNiceGames = separateEnemyF10KNiceGames(firstMonthObjects);
		ArrayList<AnalizedInfo> secondF10KBadGames = separateEnemyF10KBadGames(secondMonthObjects);
		ArrayList<AnalizedInfo> secondF10KNormalGames = separateEnemyF10KNormalGames(secondMonthObjects);
		ArrayList<AnalizedInfo> secondF10KNiceGames = separateEnemyF10KNiceGames(secondMonthObjects);

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
		Double firstEnemyFBRatingMonth = getAverageEnemyFBRating(firstMonthObjects);
		Double secondEnemyFBRatingMonth = getAverageEnemyFBRating(secondMonthObjects);
		Double firstEnemyFBRatingTen = getAverageEnemyFBRating(firstTenDaysObjects);
		Double secondEnemyFBRatingTen = getAverageEnemyFBRating(secondTenDaysObjects);

		Integer firstFBForm = getFBForm(firstMonthObjects, firstTenDaysObjects);
		Integer firstAvgFBPercent = getAverageFBPercent(firstMonthObjects);
		Integer firstHorribleGameFBPercent = getHorribleGameFBPercent(firstMonthObjects);
		Integer firstBadGameFBPercent = getBadGameFBPercent(firstMonthObjects);
		Integer firstNormalGameFBPercent = getNormalGameFBPercent(firstMonthObjects);
		Integer firstGoodGameFBPercent = getGoodGameFBPercent(firstMonthObjects);
		Integer firstPerfectGameFBPercent = getPerfectGameFBPercent(firstMonthObjects);

		Integer firstHorribleGameFBPercentNiceEnemy = getHorribleGameFBPercent(firstFBNiceGames);
		Integer firstBadGameFBPercentNiceEnemy = getBadGameFBPercent(firstFBNiceGames);
		Integer firstNormalGameFBPercentNiceEnemy = getNormalGameFBPercent(firstFBNiceGames);
		Integer firstGoodGameFBPercentNiceEnemy = getGoodGameFBPercent(firstFBNiceGames);
		Integer firstPerfectGameFBPercentNiceEnemy = getPerfectGameFBPercent(firstFBNiceGames);
		Integer secondHorribleGameFBPercentNiceEnemy = getHorribleGameFBPercent(secondFBNiceGames);
		Integer secondBadGameFBPercentNiceEnemy = getBadGameFBPercent(secondFBNiceGames);
		Integer secondNormalGameFBPercentNiceEnemy = getNormalGameFBPercent(secondFBNiceGames);
		Integer secondGoodGameFBPercentNiceEnemy = getGoodGameFBPercent(secondFBNiceGames);
		Integer secondPerfectGameFBPercentNiceEnemy = getPerfectGameFBPercent(secondFBNiceGames);

		Integer firstHorribleGameFBPercentNormalEnemy = getHorribleGameFBPercent(firstFBNormalGames);
		Integer firstBadGameFBPercentNormalEnemy = getBadGameFBPercent(firstFBNormalGames);
		Integer firstNormalGameFBPercentNormalEnemy = getNormalGameFBPercent(firstFBNormalGames);
		Integer firstGoodGameFBPercentNormalEnemy = getGoodGameFBPercent(firstFBNormalGames);
		Integer firstPerfectGameFBPercentNormalEnemy = getPerfectGameFBPercent(firstFBNormalGames);
		Integer secondHorribleGameFBPercentNormalEnemy = getHorribleGameFBPercent(secondFBNormalGames);
		Integer secondBadGameFBPercentNormalEnemy = getBadGameFBPercent(secondFBNormalGames);
		Integer secondNormalGameFBPercentNormalEnemy = getNormalGameFBPercent(secondFBNormalGames);
		Integer secondGoodGameFBPercentNormalEnemy = getGoodGameFBPercent(secondFBNormalGames);
		Integer secondPerfectGameFBPercentNormalEnemy = getPerfectGameFBPercent(secondFBNormalGames);

		Integer firstHorribleGameFBPercentBadEnemy = getHorribleGameFBPercent(firstFBBadGames);
		Integer firstBadGameFBPercentBadEnemy = getBadGameFBPercent(firstFBBadGames);
		Integer firstNormalGameFBPercentBadEnemy = getNormalGameFBPercent(firstFBBadGames);
		Integer firstGoodGameFBPercentBadEnemy = getGoodGameFBPercent(firstFBBadGames);
		Integer firstPerfectGameFBPercentBadEnemy = getPerfectGameFBPercent(firstFBBadGames);
		Integer secondHorribleGameFBPercentBadEnemy = getHorribleGameFBPercent(secondFBBadGames);
		Integer secondBadGameFBPercentBadEnemy = getBadGameFBPercent(secondFBBadGames);
		Integer secondNormalGameFBPercentBadEnemy = getNormalGameFBPercent(secondFBBadGames);
		Integer secondGoodGameFBPercentBadEnemy = getGoodGameFBPercent(secondFBBadGames);
		Integer secondPerfectGameFBPercentBadEnemy = getPerfectGameFBPercent(secondFBBadGames);


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
		Double firstEnemyF10KRatingMonth = getAverageEnemyF10KRating(firstMonthObjects);
		Double secondEnemyF10KRatingMonth = getAverageEnemyF10KRating(secondMonthObjects);
		Double firstEnemyF10KRatingTen = getAverageEnemyF10KRating(firstTenDaysObjects);
		Double secondEnemyF10KRatingTen = getAverageEnemyF10KRating(secondTenDaysObjects);

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


		Integer firstHorribleGameF10KPercentNiceEnemy = getHorribleGameF10KPercent(firstF10KNiceGames);
		Integer firstBadGameF10KPercentNiceEnemy = getBadGameF10KPercent(firstF10KNiceGames);
		Integer firstNormalGameF10KPercentNiceEnemy = getNormalGameF10KPercent(firstF10KNiceGames);
		Integer firstGoodGameF10KPercentNiceEnemy = getGoodGameF10KPercent(firstF10KNiceGames);
		Integer firstPerfectGameF10KPercentNiceEnemy = getPerfectGameF10KPercent(firstF10KNiceGames);
		Integer secondHorribleGameF10KPercentNiceEnemy = getHorribleGameF10KPercent(secondF10KNiceGames);
		Integer secondBadGameF10KPercentNiceEnemy = getBadGameF10KPercent(secondF10KNiceGames);
		Integer secondNormalGameF10KPercentNiceEnemy = getNormalGameF10KPercent(secondF10KNiceGames);
		Integer secondGoodGameF10KPercentNiceEnemy = getGoodGameF10KPercent(secondF10KNiceGames);
		Integer secondPerfectGameF10KPercentNiceEnemy = getPerfectGameF10KPercent(secondF10KNiceGames);


		Integer firstHorribleGameF10KPercentNormalEnemy = getHorribleGameF10KPercent(firstF10KNormalGames);
		Integer firstBadGameF10KPercentNormalEnemy = getBadGameF10KPercent(firstF10KNormalGames);
		Integer firstNormalGameF10KPercentNormalEnemy = getNormalGameF10KPercent(firstF10KNormalGames);
		Integer firstGoodGameF10KPercentNormalEnemy = getGoodGameF10KPercent(firstF10KNormalGames);
		Integer firstPerfectGameF10KPercentNormalEnemy = getPerfectGameF10KPercent(firstF10KNormalGames);
		Integer secondHorribleGameF10KPercentNormalEnemy = getHorribleGameF10KPercent(secondF10KNormalGames);
		Integer secondBadGameF10KPercentNormalEnemy = getBadGameF10KPercent(secondF10KNormalGames);
		Integer secondNormalGameF10KPercentNormalEnemy = getNormalGameF10KPercent(secondF10KNormalGames);
		Integer secondGoodGameF10KPercentNormalEnemy = getGoodGameF10KPercent(secondF10KNormalGames);
		Integer secondPerfectGameF10KPercentNormalEnemy = getPerfectGameF10KPercent(secondF10KNormalGames);

		Integer firstHorribleGameF10KPercentBadEnemy = getHorribleGameF10KPercent(firstF10KBadGames);
		Integer firstBadGameF10KPercentBadEnemy = getBadGameF10KPercent(firstF10KBadGames);
		Integer firstNormalGameF10KPercentBadEnemy = getNormalGameF10KPercent(firstF10KBadGames);
		Integer firstGoodGameF10KPercentBadEnemy = getGoodGameF10KPercent(firstF10KBadGames);
		Integer firstPerfectGameF10KPercentBadEnemy = getPerfectGameF10KPercent(firstF10KBadGames);
		Integer secondHorribleGameF10KPercentBadEnemy = getHorribleGameF10KPercent(secondF10KBadGames);
		Integer secondBadGameF10KPercentBadEnemy = getBadGameF10KPercent(secondF10KBadGames);
		Integer secondNormalGameF10KPercentBadEnemy = getNormalGameF10KPercent(secondF10KBadGames);
		Integer secondGoodGameF10KPercentBadEnemy = getGoodGameF10KPercent(secondF10KBadGames);
		Integer secondPerfectGameF10KPercentBadEnemy = getPerfectGameF10KPercent(secondF10KBadGames);

		Integer firstPercentWhenFBWasGet = getPercentWhenFBWasGet(firstMonthObjects);
		Integer secondPercentWhenFBWasGet = getPercentWhenFBWasGet(secondMonthObjects);
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
		Integer firstMedianMatchTime = getMedianMatchTime(firstMonthObjects);
		Integer secondMedianMatchTime = getMedianMatchTime(secondMonthObjects);
		Integer firstMedianKills = getMedianKills(firstMonthObjects);
		Integer secondMedianKills = getMedianKills(secondMonthObjects);
		Integer firstMedianDeaths = getMedianDeaths(firstMonthObjects);
		Integer secondMedianDeaths = getMedianDeaths(secondMonthObjects);
		Integer firstPercentKillsOver = getPercentKillsOver(firstMonthObjects);
		Integer firstPercentTimeOver = getPercentTimeOver(firstMonthObjects);
		Integer secondPercentKillsOver = getPercentKillsOver(secondMonthObjects);
		Integer secondPercentTimeOver = getPercentTimeOver(secondMonthObjects);
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
		infoFBObject.team1AvgSkillEnemyRatingMonth = firstEnemyFBRatingMonth;
		infoFBObject.team2AvgSkillEnemyRatingMonth = secondEnemyFBRatingMonth;
		infoFBObject.team1AvgSkillEnemyRatingTen = firstEnemyFBRatingTen;
		infoFBObject.team2AvgSkillEnemyRatingTen = secondEnemyFBRatingTen;
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
		infoF10KObject.team1AvgSkillEnemyRatingMonth = firstEnemyF10KRatingMonth;
		infoF10KObject.team2AvgSkillEnemyRatingMonth = secondEnemyF10KRatingMonth;
		infoF10KObject.team1AvgSkillEnemyRatingTen = firstEnemyF10KRatingTen;
		infoF10KObject.team2AvgSkillEnemyRatingTen = secondEnemyF10KRatingTen;
		infoF10KObject.team1MedianTime = firstF10KMedianTime;
		infoF10KObject.team2MedianTime = secondF10KMedianTime;
		infoF10KObject.team1Form = firstF10KForm;
		infoF10KObject.team2Form = secondF10KForm;
		infoF10KObject.team1PercentIfFB = firstPercentWhenFBWasGet;
		infoF10KObject.team2PercentIfFB = secondPercentWhenFBWasGet;
		//</editor-fold>

		Integer choice = 0;
		while (choice != 9)
		{
			System.out.println("/-What do u want to know about future?");
			System.out.println("/--1: FB Statistics.");
			System.out.println("/--2: F10K Statistics.");
			System.out.println("/--3: Win Analitics.");
			System.out.println("/--4: ABC Analitics.");
			System.out.println("/--5: Standins Analitics");
			System.out.println("/--6: Kills and Time Analitics");
			System.out.println("/--9: Exit.");
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
						System.out.println("/--1: NiceEnemy Statistics.");
						System.out.println("/--2: NormalEnemy Statistics.");
						System.out.println("/--3: BadEnemy Statistics.");
						System.out.println("/--4: Last 10 Matches.");
						System.out.println("/--5: Last 10 Days Matches.");
						System.out.println("/--9: Back.");
						secondChoice = s.nextInt();
						switch (secondChoice)
						{
							case 1:
							{
								System.out.println("---FB Nice Enemy Statistics---");
								System.out.println("Games Played:" + firstFBNiceGames.size() + "\tSecond Games Played:" + secondFBNiceGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentNiceEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentNiceEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentNiceEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentNiceEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentNiceEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentNiceEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentNiceEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentNiceEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentNiceEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentNiceEnemy);
								break;
							}
							case 2:
							{
								System.out.println("---FB Normal Enemy Statistics---");
								System.out.println("Games Played:" + firstFBNormalGames.size() + "\tSecond Games Played:" + secondFBNormalGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentNormalEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentNormalEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentNormalEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentNormalEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentNormalEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentNormalEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentNormalEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentNormalEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentNormalEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentNormalEnemy);
								break;
							}
							case 3:
							{
								System.out.println("---FB Bad Enemy Statistics---");
								System.out.println("First Games Played:" + firstFBBadGames.size() + "\tSecond Games Played:" + secondFBBadGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentBadEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentBadEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentBadEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentBadEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentBadEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentBadEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentBadEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentBadEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentBadEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentBadEnemy);
								break;
							}
							case 4:
							{
								System.out.println("---" + firstName + " Team Last 10 Games---");
								for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
								{
									System.out.println(firstLastTenOrLessGames.get(i).date + ";" + firstLastTenOrLessGames.get(i).enemyTeamName + ";" + firstLastTenOrLessGames.get(i).enemyFBRating + ";" + firstLastTenOrLessGames.get(i).isFB + ";" + firstLastTenOrLessGames.get(i).FB + ".");
								}
								System.out.println("---" + secondName + " Team Last 10 Games---");
								for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
								{
									System.out.println(secondLastTenOrLessGames.get(i).date + ";" + secondLastTenOrLessGames.get(i).enemyTeamName + ";" + secondLastTenOrLessGames.get(i).enemyFBRating + ";" + secondLastTenOrLessGames.get(i).isFB + ";" + secondLastTenOrLessGames.get(i).FB + ".");
								}
								break;
							}
							case 5:
							{
								System.out.println("---" + firstName + " Team Last 10 days Matches---");
								for (int i = 0; i < firstTenDaysObjects.size(); i++)
								{
									System.out.println(firstTenDaysObjects.get(i).date + ";" + firstTenDaysObjects.get(i).enemyTeamName + ";" + firstTenDaysObjects.get(i).enemyFBRating + ";" + firstTenDaysObjects.get(i).isFB + ";" + firstTenDaysObjects.get(i).FB + ".");
								}
								System.out.println("---" + secondName + " Team Last 10 days Matches---");
								for (int i = 0; i < secondTenDaysObjects.size(); i++)
								{
									System.out.println(secondTenDaysObjects.get(i).date + ";" + secondTenDaysObjects.get(i).enemyTeamName + ";" + secondTenDaysObjects.get(i).enemyFBRating + ";" + secondTenDaysObjects.get(i).isFB + ";" + secondTenDaysObjects.get(i).FB + ".");
								}
								System.out.println("--- Meetings History:");
								for (int i = 0; i < matchesHistory.size(); i++)
								{
									System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).isFB + ";" + matchesHistory.get(i).FB);
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
						System.out.println("/--1: NiceEnemy Statistics.");
						System.out.println("/--2: NormalEnemy Statistics.");
						System.out.println("/--3: BadEnemy Statistics.");
						System.out.println("/--4: Last 10 Matches.");
						System.out.println("/--5: Last 10 Days Matches.");
						System.out.println("/--9: Back.");
						secondChoice = s.nextInt();
						switch (secondChoice)
						{
							case 1:
							{
								System.out.println("---F10K Nice Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KNiceGames.size() + "\tSecond Games Played:" + secondF10KNiceGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentNiceEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentNiceEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentNiceEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentNiceEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentNiceEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentNiceEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentNiceEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentNiceEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentNiceEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentNiceEnemy);
								break;
							}
							case 2:
							{
								System.out.println("---F10K Normal Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KNormalGames.size() + "\tSecond Games Played:" + secondF10KNormalGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentNormalEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentNormalEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentNormalEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentNormalEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentNormalEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentNormalEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentNormalEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentNormalEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentNormalEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentNormalEnemy);
								break;
							}
							case 3:
							{
								System.out.println("---F10K Bad Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KBadGames.size() + "\tSecond Games Played:" + secondF10KBadGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentBadEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentBadEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentBadEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentBadEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentBadEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentBadEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentBadEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentBadEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentBadEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentBadEnemy);
								break;
							}
							case 4:
							{
								System.out.println("---" + firstName + " Team Last 10 Games---");
								for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
								{
									System.out.println(firstLastTenOrLessGames.get(i).date + ";" + firstLastTenOrLessGames.get(i).enemyTeamName + ";" + firstLastTenOrLessGames.get(i).enemyF10KRating + ";" + firstLastTenOrLessGames.get(i).isF10K + ";" + firstLastTenOrLessGames.get(i).tenKills);
								}
								System.out.println("---" + secondName + " Team Last 10 Games---");
								for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
								{
									System.out.println(secondLastTenOrLessGames.get(i).date + ";" + secondLastTenOrLessGames.get(i).enemyTeamName + ";" + secondLastTenOrLessGames.get(i).enemyF10KRating + ";" + secondLastTenOrLessGames.get(i).isF10K + ";" + secondLastTenOrLessGames.get(i).tenKills);
								}
								break;
							}
							case 5:
							{
								System.out.println("---" + firstName + " Team Last 10 days Matches---");
								for (int i = 0; i < firstTenDaysObjects.size(); i++)
								{
									System.out.println(firstTenDaysObjects.get(i).date + ";" + firstTenDaysObjects.get(i).enemyTeamName + ";" + firstTenDaysObjects.get(i).enemyF10KRating + ";" + firstTenDaysObjects.get(i).isF10K + ";" + firstTenDaysObjects.get(i).tenKills);
								}
								System.out.println("---" + secondName + " Team Last 10 days Matches---");
								for (int i = 0; i < secondTenDaysObjects.size(); i++)
								{
									System.out.println(secondTenDaysObjects.get(i).date + ";" + secondTenDaysObjects.get(i).enemyTeamName + ";" + secondTenDaysObjects.get(i).enemyF10KRating + ";" + secondTenDaysObjects.get(i).isF10K + ";" + secondTenDaysObjects.get(i).tenKills);
								}
								System.out.println("--- Meetings History:");
								for (int i = 0; i < matchesHistory.size(); i++)
								{
									System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).isF10K + ";" + matchesHistory.get(i).tenKills);
								}
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
								System.out.println("Great Enemy. First:" + firstGreatEnemyPercent + "\tSecond:" + secondGreatEnemyPercent);
								System.out.println("Good Enemy. First:" + firstGoodEnemyPercent + "\tSecond:" + secondGoodEnemyPercent);
								System.out.println("Normal Enemy. First:" + firstNormalEnemyPercent + "\tSecond:" + secondNormalEnemyPercent);
								System.out.println("Bad Enemy. First:" + firstBadEnemyPercent + "\tSecond:" + secondBadEnemyPercent);
								break;
							}
							case 4:
							{
								System.out.println("---" + firstName + " Team Last 10 Games---");
								for (int i = 0; i < firstLastTenOrLessGames.size(); i++)
								{
									System.out.println(firstLastTenOrLessGames.get(i).date + ";" + firstLastTenOrLessGames.get(i).enemyTeamName + ";" + firstLastTenOrLessGames.get(i).enemyRating + ";" + firstLastTenOrLessGames.get(i).matchTime + ";" + firstLastTenOrLessGames.get(i).isWin + ";" + firstLastTenOrLessGames.get(i).matchHardness + ";" + firstLastTenOrLessGames.get(i).EG + ";" + firstLastTenOrLessGames.get(i).MG + ";" + firstLastTenOrLessGames.get(i).LG);
								}
								System.out.println("---" + secondName + " Team Last 10 Games---");
								for (int i = 0; i < secondLastTenOrLessGames.size(); i++)
								{
									System.out.println(secondLastTenOrLessGames.get(i).date + ";" + secondLastTenOrLessGames.get(i).enemyTeamName + ";" + secondLastTenOrLessGames.get(i).enemyRating + ";" + secondLastTenOrLessGames.get(i).matchTime + ";" + secondLastTenOrLessGames.get(i).isWin + ";" + secondLastTenOrLessGames.get(i).matchHardness + ";" + secondLastTenOrLessGames.get(i).EG + ";" + secondLastTenOrLessGames.get(i).MG + ";" + secondLastTenOrLessGames.get(i).LG);
								}
								break;
							}
							case 5:
							{
								System.out.println("---" + firstName + " Team Last 10 days Matches---");
								for (int i = 0; i < firstTenDaysObjects.size(); i++)
								{
									System.out.println(firstTenDaysObjects.get(i).date + ";" + firstTenDaysObjects.get(i).enemyTeamName + ";" + firstTenDaysObjects.get(i).enemyRating + ";" + firstTenDaysObjects.get(i).matchTime + ";" + firstTenDaysObjects.get(i).isWin + ";" + firstTenDaysObjects.get(i).matchHardness + ";" + firstTenDaysObjects.get(i).EG + ";" + firstTenDaysObjects.get(i).MG + ";" + firstTenDaysObjects.get(i).LG);
								}
								System.out.println("---" + secondName + " Team Last 10 days Matches---");
								for (int i = 0; i < secondTenDaysObjects.size(); i++)
								{
									System.out.println(secondTenDaysObjects.get(i).date + ";" + secondTenDaysObjects.get(i).enemyTeamName + ";" + secondTenDaysObjects.get(i).enemyRating + ";" + secondTenDaysObjects.get(i).matchTime + ";" + secondTenDaysObjects.get(i).isWin + ";" + secondTenDaysObjects.get(i).matchHardness + ";" + secondTenDaysObjects.get(i).EG + ";" + secondTenDaysObjects.get(i).MG + ";" + secondTenDaysObjects.get(i).LG);
								}
								System.out.println("---Meetings History---");
								for (int i = 0; i < matchesHistory.size(); i++)
								{
									System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).matchTime + ";" + matchesHistory.get(i).isWin + ";" + matchesHistory.get(i).matchHardness + ";" + matchesHistory.get(i).EG + ";" + matchesHistory.get(i).MG + ";" + matchesHistory.get(i).LG);
								}
							}
						}
					}
					break;
				}
				//</editor-fold>
				//<editor-fold desc="ABC CASE">
				case 4:
				{
					System.out.println("---ABC Analitics---");
					abcAnalitics.checkForABCMatches(firstMonthObjects, secondMonthObjects);
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

					System.out.println(firstMonthObjects.get(0).teamName + " avg. match time:" + firstMedianMatchTime + ". Kills:" + (firstMedianKills + firstMedianDeaths));
					System.out.println(secondMonthObjects.get(0).teamName + " avg. match time:" + secondMedianMatchTime + ". Kills:" + (secondMedianKills + secondMedianDeaths));
					System.out.println(firstMonthObjects.get(0).teamName + " time over 40 mins:" + firstPercentTimeOver + "%. Kills over 45:" + firstPercentKillsOver + "%");
					System.out.println(secondMonthObjects.get(0).teamName + " time over 40 mins:" + secondPercentTimeOver + "%. Kills over 45:" + secondPercentKillsOver + "%");
					System.out.println("---Meetings History---");
					for (int i = 0; i < matchesHistory.size(); i++)
					{
						System.out.println(i + ":" + matchesHistory.get(i).date + ";" + matchesHistory.get(i).teamName + ";" + matchesHistory.get(i).enemyTeamName + ";" + matchesHistory.get(i).matchTime + ";" + (matchesHistory.get(i).kills + matchesHistory.get(i).deaths));
					}

					break;
				}
				//</editor-fold>
				default:
					break;
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
			String enemyFBRating = eachMatch[i].split("##")[9];
			String enemyF10KRating = eachMatch[i].split("##")[10];
			String matchHardness = eachMatch[i].split("##")[11];
			String ratingChanges = eachMatch[i].split("##")[12];

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

			object.kills = Integer.parseInt(KDA.split(";")[0]);
			object.deaths = Integer.parseInt(KDA.split(";")[1]);
			object.assists = Integer.parseInt(KDA.split(";")[2]);

			String[] eachEPPs = playerEPPs.split("\\|\\|");
			object.playerId[0] = eachEPPs[0].split(";")[0];
			object.playerEPP[0] = Integer.parseInt(eachEPPs[0].split(";")[1]);
			object.playerId[1] = eachEPPs[1].split(";")[0];
			object.playerEPP[1] = Integer.parseInt(eachEPPs[1].split(";")[1]);
			object.playerId[2] = eachEPPs[2].split(";")[0];
			object.playerEPP[2] = Integer.parseInt(eachEPPs[2].split(";")[1]);
			object.playerId[3] = eachEPPs[3].split(";")[0];
			object.playerEPP[3] = Integer.parseInt(eachEPPs[3].split(";")[1]);
			object.playerId[4] = eachEPPs[4].split(";")[0];
			object.playerEPP[4] = Integer.parseInt(eachEPPs[4].split(";")[1]);

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

			object.isFR = Boolean.valueOf(FRInfo.split(";")[0]);
			object.FRTime = Integer.parseInt(FRInfo.split(";")[1]);

			object.mineRating = Integer.parseInt(ratingChanges.split(";")[1]);
			object.enemyRating = Integer.parseInt(ratingChanges.split(";")[2]);
			object.enemyFBRating = enemyFBRating;
			object.enemyF10KRating = enemyF10KRating;

			objects.add(object);
		}
	}

	public void showMatchInfo(AnalizedInfo object)
	{
		System.out.println("Id:" + object.id + " Date:" + object.date + " MineRating:" + object.mineRating + " EnemyRating:" + object.enemyRating);
		System.out.println("EG:" + object.EG + " MG:" + object.MG + " LG:" + object.LG);
		System.out.println("MatchTime:" + object.matchTime + " TeadId:" + object.teamId);
		System.out.println("Kills:" + object.kills + " Deaths:" + object.deaths + " Assists:" + object.assists);
		System.out.println("FRTime:" + object.FRTime + " FBTime:" + object.FBTime + " F10KTime:" + object.F10KTime);
		System.out.println("Farming:" + object.farming + " KillAbility:" + object.killAbility + " Lining:" + object.lining + " Vision:" + object.vision);
		System.out.println("FB:" + object.FB + " TenKills:" + object.tenKills + " Pushing:" + object.pushing);
		System.out.println("IsF10K:" + object.isF10K + " IsFR:" + object.isFR + " IsWin:" + object.isWin + " IsFB:" + object.isFB);
		System.out.println("Player1Id:" + object.playerId[0] + " Player2Id:" + object.playerId[1] + " Player3Id:" + object.playerId[2] + " Player4Id:" + object.playerId[3] + " Player5Id:" + object.playerId[4]);
		System.out.println("Player1EPP:" + object.playerEPP[0] + " Player2EPP:" + object.playerEPP[1] + " Player3EPP:" + object.playerEPP[2] + " Player4EPP:" + object.playerEPP[3] + " Player5EPP:" + object.playerEPP[4]);
	}

	//<editor-fold desc="FB and F10K">
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

	public Double getAverageEnemyFBRating(ArrayList<AnalizedInfo> objects)
	{
		Double avgRating = 0.0;
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).enemyFBRating.equals("bad"))
				avgRating += 1.0;
			if (objects.get(i).enemyFBRating.equals("normal"))
				avgRating += 2.0;
			if (objects.get(i).enemyFBRating.equals("nice"))
				avgRating += 3.0;
			if (objects.get(i).enemyFBRating.equals("perfect"))
				avgRating += 4.0;
			counter++;
		}
		avgRating = avgRating / counter;
		return Math.round(avgRating * 100.0) / 100.0;
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

	public Double getAverageEnemyF10KRating(ArrayList<AnalizedInfo> objects)
	{
		Double avgRating = 0.0;
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).enemyF10KRating.equals("bad"))
				avgRating += 1.0;
			if (objects.get(i).enemyF10KRating.equals("normal"))
				avgRating += 2.0;
			if (objects.get(i).enemyF10KRating.equals("nice"))
				avgRating += 3.0;
			if (objects.get(i).enemyF10KRating.equals("perfect"))
				avgRating += 4.0;
			counter++;
		}
		avgRating = avgRating / counter;
		return Math.round(avgRating * 100.0) / 100.0;
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
	//</editor-fold>
	//</editor-fold>

	//<editor-fold desc="WinData">
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

	public Integer getPercentGreatEnemy(ArrayList<AnalizedInfo> objects)
	{
		Integer allGames = 0;
		Integer winGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).enemyRating >= 1500)
			{
				allGames++;
				if (objects.get(i).isWin)
					winGames++;
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
			if (objects.get(i).enemyRating >= 1250 && objects.get(i).enemyRating < 1500)
			{
				allGames++;
				if (objects.get(i).isWin)
					winGames++;
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
			if (objects.get(i).enemyRating >= 1000 && objects.get(i).enemyRating < 1250)
			{
				allGames++;
				if (objects.get(i).isWin)
					winGames++;
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
			if (objects.get(i).enemyRating < 1000)
			{
				allGames++;
				if (objects.get(i).isWin)
					winGames++;
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
			ratingArray.add(objects.get(i).enemyRating);
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

	ArrayList<Integer> extremumElimination(ArrayList<Integer> inputArray)
	{
		ArrayList<Integer> outputArray = new ArrayList<Integer>();
		Collections.sort(inputArray);
		Double avgValue = 0.0;
		Integer counter = 0;
		if (inputArray.size() >= 4)
		{
			for (int i = 0; i < inputArray.size(); i++)
			{
				avgValue += inputArray.get(i);
				if (inputArray.get(i) == 0)
					inputArray.set(i, 1);
				counter++;
			}

			avgValue = avgValue / counter;

			if ((inputArray.get(0) / avgValue) * 100 - 100 <= -120 || (inputArray.get(0) / avgValue) * 100 - 100 >= 120)
			{
				if ((inputArray.get(0) / inputArray.get(1)) * 100 - 100 <= -70 || (inputArray.get(0) / inputArray.get(1)) * 100 - 100 >= 70)
					;
				else
					outputArray.add(inputArray.get(0));
			} else
				outputArray.add(inputArray.get(0));

			if ((inputArray.get(inputArray.size() - 1) / avgValue) * 100 - 100 <= -120 || (inputArray.get(inputArray.size() - 1) / avgValue) * 100 - 100 >= 120)
			{
				if (inputArray.get(inputArray.size() - 1) / inputArray.get(inputArray.size() - 2) * 100 - 100 <= -70 || inputArray.get(inputArray.size() - 1) / inputArray.get(inputArray.size() - 2) * 100 - 100 >= 70)
					;
				else
					outputArray.add(inputArray.get(inputArray.size() - 1));
			} else
				outputArray.add(inputArray.get(inputArray.size() - 1));

			for (int i = 1; i < inputArray.size() - 1; i++)
			{
				outputArray.add(inputArray.get(i));
			}

			return outputArray;
		} else
			return inputArray;
	}

	Integer getAvgValueFromArray(ArrayList<Integer> array)
	{
		Double avgValue = 0.0;
		for (int i = 0; i < array.size(); i++)
		{
			avgValue += array.get(i);
		}
		avgValue = avgValue / array.size();
		return avgValue.intValue();
	}

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

	//</editor-fold>

	//<editor-fold desc="Object separators">

	public ArrayList<AnalizedInfo> separateEnemyF10KNiceGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyF10KRating.equals("nice"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateEnemyF10KNormalGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyF10KRating.equals("normal"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateEnemyF10KBadGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyF10KRating.equals("bad"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateEnemyFBNiceGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyFBRating.equals("nice"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateEnemyFBNormalGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyFBRating.equals("normal"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateEnemyFBBadGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (array.get(i).enemyFBRating.equals("bad"))
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateLastThreeGames(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

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


