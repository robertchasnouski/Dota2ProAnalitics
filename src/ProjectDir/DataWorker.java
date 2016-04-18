package ProjectDir;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DataWorker
{

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	Scanner s = new Scanner(System.in);

	public void analizeFutureMatch(String team1Id, String team2Id) throws IOException, ParseException
	{
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
		ArrayList<AnalizedInfo> firstSevenDaysObjects = separateSevenDaysObjects(firstObjects);
		ArrayList<AnalizedInfo> firstLastFiveGames=separateLastFiveGames(firstObjects);
		ArrayList<AnalizedInfo> secondMonthObjects = separateMonthObjects(secondObjects);
		ArrayList<AnalizedInfo> secondSevenDaysObjects = separateSevenDaysObjects(secondObjects);
		ArrayList<AnalizedInfo> secondLastFiveGames=separateLastFiveGames(secondObjects);

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

		ArrayList<AnalizedInfo> lastSevenFirstObjects = separateLastSevenGames(firstObjects);
		ArrayList<AnalizedInfo> lastThreeFirstObjects = separateLastThreeGames(firstObjects);
		ArrayList<AnalizedInfo> lastSevenSecondObjects = separateLastSevenGames(secondObjects);
		ArrayList<AnalizedInfo> lastThreeSecondObjects = separateLastThreeGames(secondObjects);
		//</editor-fold>

		String firstName = firstObjects.get(0).teamName;
		String secondName = secondObjects.get(0).teamName;

		/**GeneralInfo**/
		Integer firstAvgMonthEnemyRating = getAverageEnemyRating(firstMonthObjects);
		Integer firstAvgSevenDaysEnemyRating = getAverageEnemyRating(firstSevenDaysObjects);
		Integer firstAvgMatchTime = getAverageMatchTime(firstObjects);
		Integer firstMonthGamesPlayed = getMonthGamesPlayed(firstObjects);

		//<editor-fold desc="FB">
		/**FB**/
		/****First Team****/
		Integer firstFBMedianTime=getMedianFBTime(firstMonthObjects);
		Integer secondFBMedianTime=getMedianFBTime(secondMonthObjects);
		Double firstEnemyFBRatingMonth = getAverageEnemyFBRating(firstMonthObjects);
		Double secondEnemyFBRatingMonth = getAverageEnemyFBRating(secondMonthObjects);
		Double firstEnemyFBRatingSeven = getAverageEnemyFBRating(firstSevenDaysObjects);
		Double secondEnemyFBRatingSeven = getAverageEnemyFBRating(secondSevenDaysObjects);

		Integer firstFBForm = getFBForm(firstMonthObjects, firstSevenDaysObjects);
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
		Integer secondFBForm = getFBForm(secondMonthObjects, secondSevenDaysObjects);
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
		Integer firstF10KMedianTime=getMedianF10KTime(firstMonthObjects);
		Integer secondF10KMedianTime=getMedianF10KTime(secondMonthObjects);
		Double firstEnemyF10KRatingMonth = getAverageEnemyF10KRating(firstMonthObjects);
		Double secondEnemyF10KRatingMonth = getAverageEnemyF10KRating(secondMonthObjects);
		Double firstEnemyF10KRatingSeven = getAverageEnemyF10KRating(firstSevenDaysObjects);
		Double secondEnemyF10KRatingSeven = getAverageEnemyF10KRating(secondSevenDaysObjects);

		Integer firstF10KForm = getF10KForm(firstMonthObjects, firstSevenDaysObjects);
		Integer firstAvgF10KPercent = getAverageF10KPercent(firstMonthObjects);
		Integer secondF10KForm = getF10KForm(secondMonthObjects, secondSevenDaysObjects);
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

		Integer firstPercentWhenFBWasGet=getPercentWhenFBWasGet(firstMonthObjects);
		Integer secondPercentWhenFBWasGet=getPercentWhenFBWasGet(secondMonthObjects);
		/**************************************************/
		//</editor-fold>

		/**GeneralInfo**/
		Integer secondMonthGamesPlayed = getMonthGamesPlayed(secondMonthObjects);
		Integer secondAvgMatchTime = getAverageMatchTime(secondMonthObjects);
		Integer secondAvgMonthEnemyRating = getAverageEnemyRating(secondMonthObjects);
		Integer secondAvgSevenDaysEnemyRating = getAverageEnemyRating(secondSevenDaysObjects);


		Integer choice = 0;

		while (choice != 9)
		{
			System.out.println("/-What do u want to know about future?");
			System.out.println("/--1: FB,F10K Statistics");
			System.out.println("/--2: Parameters Statistics");
			System.out.println("/--9: Exit");
			choice = s.nextInt();
			switch (choice)
			{
				case 1:
				{
					Integer secondChoice = 0;
					while (secondChoice != 9)
					{
						System.out.println("/--1: Usual Statisctics.");
						System.out.println("/--2: NiceEnemy Statistics.");
						System.out.println("/--3: NormalEnemy Statistics.");
						System.out.println("/--4: BadEnemy Statistics.");
						System.out.println("/--5: Last 5 Matches.");
						System.out.println("/--9: Back.");
						secondChoice = s.nextInt();
						switch (secondChoice)
						{
							case 1:
							{
								System.out.println("First Name:" + firstName + "\t\t Second Name:" + secondName);
								System.out.println("First GamesPlayed:" + firstMonthGamesPlayed + "\t\t Second GamesPlayed:" + secondMonthGamesPlayed);
								System.out.println("First Month EnemyAverageRating:" + firstAvgMonthEnemyRating + "\t\t Second  Month EnemyAverageRating:" + secondAvgMonthEnemyRating);
								System.out.println("First Seven Days EnemyAverageRating:" + firstAvgSevenDaysEnemyRating + "\t\t Second Seven Days EnemyAverageRating:" + secondAvgSevenDaysEnemyRating);
								System.out.println("---FB---");
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercent + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercent);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercent + "\t\tSecond BadGames Percent:" + secondBadGameFBPercent);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercent + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercent);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercent + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercent);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercent + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercent);
								System.out.println("First FB Percent:" + firstAvgFBPercent + "\t\t Second FB Percent:" + secondAvgFBPercent);
								System.out.println("First FB For2m:" + firstFBForm + "\t\tSecond FB Form:" + secondFBForm);
								System.out.println("First Month FBEnemyRating:" + firstEnemyFBRatingMonth + "\t\tSecond Month FBEnemyRating:" + secondEnemyFBRatingMonth);
								System.out.println("First Seven Days FBEnemyRating:" + firstEnemyFBRatingSeven + "\t\tSecond Seven Days FBEnemyRating:" + secondEnemyFBRatingSeven);
								System.out.println("First FB Time:"+firstFBMedianTime+"\t\tSecond FB Time:"+secondFBMedianTime);
								System.out.println("---F10K---");
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercent + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercent);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercent + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercent);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercent + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercent);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercent + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercent);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercent + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercent);
								System.out.println("First F10K Percent:" + firstAvgF10KPercent + "\t\t Second F10K Percent:" + secondAvgF10KPercent);
								System.out.println("First F10K Form:" + firstF10KForm + "\t\tSecond F10K Form:" + secondF10KForm);
								System.out.println("First Percent with FB:"+firstPercentWhenFBWasGet+"\t\tSecond Percent with FB:"+secondPercentWhenFBWasGet);
								System.out.println("First Month F10KEnemyRating:" + firstEnemyF10KRatingMonth + "\t\tSecond Month F10KEnemyRating:" + secondEnemyF10KRatingMonth);
								System.out.println("First Seven Days F10KEnemyRating:" + firstEnemyF10KRatingSeven + "\t\tSecond Seven Days F10KEnemyRating:" + secondEnemyF10KRatingSeven);
								System.out.println("First F10K Time:"+firstF10KMedianTime+"\t\tSecond F10K Time:"+secondF10KMedianTime);
								break;
							}
							case 2:
							{
								System.out.println("---FB Nice Enemy Statistics---");
								System.out.println("Games Played:" + firstFBNiceGames.size() + "\tSecond Games Played:" + secondFBNiceGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentNiceEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentNiceEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentNiceEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentNiceEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentNiceEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentNiceEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentNiceEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentNiceEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentNiceEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentNiceEnemy);
								System.out.println("---F10K Nice Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KNiceGames.size() + "\tSecond Games Played:" + secondF10KNiceGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentNiceEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentNiceEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentNiceEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentNiceEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentNiceEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentNiceEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentNiceEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentNiceEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentNiceEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentNiceEnemy);
								break;
							}
							case 3:
							{
								System.out.println("---FB Normal Enemy Statistics---");
								System.out.println("Games Played:" + firstFBNormalGames.size() + "\tSecond Games Played:" + secondFBNormalGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentNormalEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentNormalEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentNormalEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentNormalEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentNormalEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentNormalEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentNormalEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentNormalEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentNormalEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentNormalEnemy);
								System.out.println("---F10K Normal Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KNormalGames.size() + "\tSecond Games Played:" + secondF10KNormalGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentNormalEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentNormalEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentNormalEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentNormalEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentNormalEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentNormalEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentNormalEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentNormalEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentNormalEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentNormalEnemy);
								break;
							}
							case 4:
							{
								System.out.println("---FB Bad Enemy Statistics---");
								System.out.println("First Games Played:" + firstFBBadGames.size() + "\tSecond Games Played:" + secondFBBadGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameFBPercentBadEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameFBPercentBadEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameFBPercentBadEnemy + "\t\tSecond BadGames Percent:" + secondBadGameFBPercentBadEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameFBPercentBadEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercentBadEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameFBPercentBadEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercentBadEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercentBadEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercentBadEnemy);
								System.out.println("---F10K Bad Enemy Statistics---");
								System.out.println("Games Played:" + firstF10KBadGames.size() + "\tSecond Games Played:" + secondF10KBadGames.size());
								System.out.println("First HorribleGames Percent:" + firstHorribleGameF10KPercentBadEnemy + "\t\tSecond HorribleGames Percent:" + secondHorribleGameF10KPercentBadEnemy);
								System.out.println("First BadGames Percent:" + firstBadGameF10KPercentBadEnemy + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercentBadEnemy);
								System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercentBadEnemy + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercentBadEnemy);
								System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercentBadEnemy + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercentBadEnemy);
								System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercentBadEnemy + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercentBadEnemy);
								break;
							}
							case 5:
							{
								System.out.println("---"+firstName+" Team Last 5 Games---");
								for (int i = 0; i < firstLastFiveGames.size(); i++)
								{
									System.out.println(firstLastFiveGames.get(i).date+";"+firstLastFiveGames.get(i).enemyTeamName+";"+firstLastFiveGames.get(i).enemyFBRating+";"+firstLastFiveGames.get(i).enemyF10KRating+";"+firstLastFiveGames.get(i).isFB+";"+firstLastFiveGames.get(i).isF10K+";"+firstLastFiveGames.get(i).FB+";"+firstLastFiveGames.get(i).tenKills);
								}
								System.out.println("---"+secondName+" Team Last 5 Games---");
								for (int i = 0; i < secondLastFiveGames.size(); i++)
								{
									System.out.println(secondLastFiveGames.get(i).date+";"+secondLastFiveGames.get(i).enemyTeamName+";"+secondLastFiveGames.get(i).enemyFBRating+";"+secondLastFiveGames.get(i).enemyF10KRating+";"+secondLastFiveGames.get(i).isFB+";"+secondLastFiveGames.get(i).isF10K+";"+secondLastFiveGames.get(i).FB+";"+secondLastFiveGames.get(i).tenKills);
								}
								break;
							}
						}
					}
					break;
				}
				case 2:
				{
					System.out.println("No parameters.");
					break;
				}
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
			String ratingChanges = eachMatch[i].split("##")[11];

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
			object.enemyTeamName=matchGeneralInfo.split(";")[9];

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
		if(array.size()<=4)
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
		ArrayList<Integer> FBTimeArray=new ArrayList<>();
		for (int i = 0; i < objects.size() ; i++)
		{
			if(objects.get(i).isFB==true)
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

	public Integer getFBForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> SevenDaysObjects) throws ParseException
	{
		if (SevenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			Integer horribleGamesMonth = getHorribleGameFBPercent(monthObjects);
			Integer badGamesMonth = getBadGameFBPercent(monthObjects);
			Integer normalGamesMonth = getNormalGameFBPercent(monthObjects);
			Integer goodGamesMonth = getGoodGameFBPercent(monthObjects);
			Integer perfectGamesMonth = getPerfectGameFBPercent(monthObjects);
			Integer horribleGamesSevenDays = getHorribleGameFBPercent(SevenDaysObjects);
			Integer badGamesSevenDays = getBadGameFBPercent(SevenDaysObjects);
			Integer normalGamesSevenDays = getNormalGameFBPercent(SevenDaysObjects);
			Integer goodGamesSevenDays = getGoodGameFBPercent(SevenDaysObjects);
			Integer perfectGamesSevenDays = getPerfectGameFBPercent(SevenDaysObjects);
			Integer form = perfectGamesSevenDays - perfectGamesMonth + goodGamesSevenDays - goodGamesMonth;
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
		Integer fbGets=0;
		Integer f10kGets=0;
		for (int i = 0; i < objects.size(); i++)
		{
			if(objects.get(i).isFB==true)
			{
				fbGets++;
				if(objects.get(i).isF10K==true)
				{
					f10kGets++;
				}
			}
		}
		Double temp=(double)f10kGets/fbGets*100;
		return temp.intValue();
	}
	public Integer getMedianF10KTime(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<Integer> F10KTimeArray=new ArrayList<>();
		for (int i = 0; i < objects.size() ; i++)
		{
			if(objects.get(i).isF10K==true)
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
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 20)
			{
				if (objects.get(i).isF10K == true)
					points++;
				counter++;
			}
		}
		points = points / counter * 100;
		return points.intValue();
	}

	public Integer getF10KForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> SevenDaysObjects) throws ParseException
	{
		if (SevenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			Integer horribleGamesMonth = getHorribleGameF10KPercent(monthObjects);
			Integer badGamesMonth = getBadGameF10KPercent(monthObjects);
			Integer normalGamesMonth = getNormalGameF10KPercent(monthObjects);
			Integer goodGamesMonth = getGoodGameF10KPercent(monthObjects);
			Integer perfectGamesMonth = getPerfectGameF10KPercent(monthObjects);
			Integer horribleGamesSevenDays = getHorribleGameF10KPercent(monthObjects);
			Integer badGamesSevenDays = getBadGameF10KPercent(SevenDaysObjects);
			Integer normalGamesSevenDays = getNormalGameF10KPercent(SevenDaysObjects);
			Integer goodGamesSevenDays = getGoodGameF10KPercent(SevenDaysObjects);
			Integer perfectGamesSevenDays = getPerfectGameF10KPercent(SevenDaysObjects);
			Integer form = perfectGamesSevenDays - perfectGamesMonth + goodGamesSevenDays - goodGamesMonth;
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

	//<editor-fold desc="Parameters">
	public Integer getAverageKillAbility(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).killAbility);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAveragePushing(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).pushing);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAverageVision(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).vision);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAverageLining(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).lining);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAverageFarming(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).farming);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}
	//</editor-fold>

	//<editor-fold desc="GameStages">
	public Integer getAverageEG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).EG);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAverageMG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).MG);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
	}

	public Integer getAverageLG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				array.add(objects.get(i).LG);
			}
		}
		array = extremumElimination(array);
		return getAvgValueFromArray(array);
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

	public Integer getHalfMonthGamesPlayed(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				counter++;
			}
		}
		return counter;
	}

	public Integer getMonthGamesPlayed(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 20)
			{
				counter++;
			}
		}
		return counter;
	}

	public Integer getAverageMatchTime(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				points += Integer.parseInt(objects.get(i).matchTime);
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageEPPs(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				points += objects.get(i).playerEPP[0];
				points += objects.get(i).playerEPP[1];
				points += objects.get(i).playerEPP[2];
				points += objects.get(i).playerEPP[3];
				points += objects.get(i).playerEPP[4];
				points = points / 5;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}
	//</editor-fold>

	//<editor-fold desc="WorkerFunctions">
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

	public ArrayList<AnalizedInfo> separateLastSevenGames(ArrayList<AnalizedInfo> array)
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();
		if (array.size() >= 7)
		{
			for (int i = array.size() - 1; i > array.size() - 8; i--)
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

	public ArrayList<AnalizedInfo> separateSevenDaysObjects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 7)
				newArray.add(array.get(i));
		}
		return newArray;
	}

	public ArrayList<AnalizedInfo> separateMonthObjects(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = 0; i < array.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 20)
				newArray.add(array.get(i));
		}
		return newArray;
	}
	//</editor-fold>
}
