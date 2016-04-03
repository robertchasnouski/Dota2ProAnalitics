package ProjectDir;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.io.IOException;
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

		ArrayList<AnalizedInfo> firstMonthObjects = separateMonthObjects(firstObjects);
		ArrayList<AnalizedInfo> firstSevenDaysObjects = separateSevenDaysObjects(firstObjects);
		ArrayList<AnalizedInfo> secondMonthObjects = separateMonthObjects(secondObjects);
		ArrayList<AnalizedInfo> secondSevenDaysObjects = separateSevenDaysObjects(secondObjects);

		ArrayList<AnalizedInfo> lastSevenFirstObjects = separateLastSevenGames(firstObjects);
		ArrayList<AnalizedInfo> lastThreeFirstObjects = separateLastThreeGames(firstObjects);
		ArrayList<AnalizedInfo> lastSevenSecondObjects = separateLastSevenGames(secondObjects);
		ArrayList<AnalizedInfo> lastThreeSecondObjects = separateLastThreeGames(secondObjects);

		String firstName = firstObjects.get(0).teamName;
		String secondName = secondObjects.get(0).teamName;

		/*GeneralInfo**/
		Integer firstAvgRating = getAverageEnemyRating(firstObjects);
		Integer firstAvgMatchTime = getAverageMatchTime(firstObjects);
		Integer firstMonthGamesPlayed = getMonthGamesPlayed(firstObjects);
		/**Parameters**/
		Integer firstAvgKillAbility = getAverageKillAbility(firstObjects);
		Integer firstAvgPushing = getAveragePushing(firstObjects);
		Integer firstAvgLining = getAverageLining(firstObjects);
		Integer firstAvgVision = getAverageVision(firstObjects);
		Integer firstAvgFarming = getAverageFarming(firstObjects);
		/**EPP**/
		Integer firstAvgEPP = getAverageEPPs(firstObjects);
		/**GameStages**/
		Integer firstAvgEG = getAverageEG(firstObjects);
		Integer firstAvgMG = getAverageMG(firstObjects);
		Integer firstAvgLG = getAverageLG(firstObjects);
		/**FB**/
		Integer firstFBForm = getFBForm(firstMonthObjects, firstSevenDaysObjects);
		Integer firstAvgFBPercent = getAverageFBPercent(firstMonthObjects);
		Integer firstBadGameFBPercent = getBadGameFBPercent(firstMonthObjects);
		Integer firstNormalGameFBPercent = getNormalGameFBPercent(firstMonthObjects);
		Integer firstGoodGameFBPercent = getGoodGameFBPercent(firstMonthObjects);
		Integer firstPerfectGameFBPercent = getPerfectGameFBPercent(firstMonthObjects);
		/**F10K**/
		Integer firstF10KForm = getF10KForm(firstMonthObjects, firstSevenDaysObjects);
		Integer firstAvgF10KPercent = getAverageF10KPercent(firstMonthObjects);
		Integer firstBadGameF10KPercent = getBadGameF10KPercent(firstMonthObjects);
		Integer firstNormalGameF10KPercent = getNormalGameF10KPercent(firstMonthObjects);
		Integer firstGoodGameF10KPercent = getGoodGameF10KPercent(firstMonthObjects);
		Integer firstPerfectGameF10KPercent = getPerfectGameF10KPercent(firstMonthObjects);

		/*******************************************/

		/**GeneralInfo**/
		Integer secondMonthGamesPlayed = getMonthGamesPlayed(secondObjects);
		Integer secondAvgMatchTime = getAverageMatchTime(secondObjects);
		Integer secondAvgRating = getAverageEnemyRating(secondObjects);
		/**Parameters**/
		Integer secondAvgKillAbility = getAverageKillAbility(secondObjects);
		Integer secondAvgPushing = getAveragePushing(secondObjects);
		Integer secondAvgLining = getAverageLining(secondObjects);
		Integer secondAvgVision = getAverageVision(secondObjects);
		Integer secondAvgFarming = getAverageFarming(secondObjects);
		/**EPP**/
		Integer secondAvgEPP = getAverageEPPs(secondObjects);
		/**GameStages**/
		Integer secondAvgEG = getAverageEG(secondObjects);
		Integer secondAvgMG = getAverageMG(secondObjects);
		Integer secondAvgLG = getAverageLG(secondObjects);
		/**FB**/
		Integer secondFBForm = getFBForm(secondMonthObjects, secondSevenDaysObjects);
		Integer secondAvgFBPercent = getAverageFBPercent(secondMonthObjects);
		Integer secondBadGameFBPercent = getBadGameFBPercent(secondMonthObjects);
		Integer secondNormalGameFBPercent = getNormalGameFBPercent(secondMonthObjects);
		Integer secondGoodGameFBPercent = getGoodGameFBPercent(secondMonthObjects);
		Integer secondPerfectGameFBPercent = getPerfectGameFBPercent(secondMonthObjects);
		/**F10K**/
		Integer secondF10KForm = getF10KForm(secondMonthObjects, secondSevenDaysObjects);
		Integer secondAvgF10KPercent = getAverageF10KPercent(secondMonthObjects);
		Integer secondBadGameF10KPercent = getBadGameF10KPercent(secondMonthObjects);
		Integer secondNormalGameF10KPercent = getNormalGameF10KPercent(secondMonthObjects);
		Integer secondGoodGameF10KPercent = getGoodGameF10KPercent(secondMonthObjects);
		Integer secondPerfectGameF10KPercent = getPerfectGameF10KPercent(secondMonthObjects);

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
					System.out.println("First Name:" + firstName + "\t\t Second Name:" + secondName);
					System.out.println("First GamesPlayed:" + firstMonthGamesPlayed + "\t\t Second GamesPlayed:" + secondMonthGamesPlayed);
					System.out.println("---FB---");
					System.out.println("First BadGames Percent:" + firstBadGameFBPercent + "\t\tSecond BadGames Percent:" + secondBadGameFBPercent);
					System.out.println("First NormalGames Percent:" + firstNormalGameFBPercent + "\t\tSecond NormalGames Percent:" + secondNormalGameFBPercent);
					System.out.println("First GoodGames Percent:" + firstGoodGameFBPercent + "\t\tSecond GoodGames Percent:" + secondGoodGameFBPercent);
					System.out.println("First PerfectGames Percent:" + firstPerfectGameFBPercent + "\t\tSecond PerfectGames Percent:" + secondPerfectGameFBPercent);
					System.out.println("First FB Percent:" + firstAvgFBPercent + "\t\t Second FB Percent:" + secondAvgFBPercent);
					System.out.println("First FB Form:" + firstFBForm + "\t\tSecond FB Form:" + secondFBForm);
					System.out.println("---F10K---");
					System.out.println("First BadGames Percent:" + firstBadGameF10KPercent + "\t\tSecond BadGames Percent:" + secondBadGameF10KPercent);
					System.out.println("First NormalGames Percent:" + firstNormalGameF10KPercent + "\t\tSecond NormalGames Percent:" + secondNormalGameF10KPercent);
					System.out.println("First GoodGames Percent:" + firstGoodGameF10KPercent + "\t\tSecond GoodGames Percent:" + secondGoodGameF10KPercent);
					System.out.println("First PerfectGames Percent:" + firstPerfectGameF10KPercent + "\t\tSecond PerfectGames Percent:" + secondPerfectGameF10KPercent);
					System.out.println("First F10K Percent:" + firstAvgF10KPercent + "\t\t Second F10K Percent:" + secondAvgF10KPercent);
					System.out.println("First F10K Form:" + firstF10KForm + "\t\tSecond F10K Form:" + secondF10KForm);
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
			String ratingChanges = eachMatch[i].split("##")[9];

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
			object.tenKills = Integer.parseInt(parameters.split(";")[4]);
			object.FB = Integer.parseInt(parameters.split(";")[5]);
			object.farming = Integer.parseInt(parameters.split(";")[6]);


			object.isFB = Boolean.valueOf(FBInfo.split(";")[0]);
			object.FBTime = Integer.parseInt(FBInfo.split(";")[1]);

			object.isF10K = Boolean.valueOf(F10KInfo.split(";")[0]);
			object.F10KTime = Integer.parseInt(F10KInfo.split(";")[1]);

			object.isFR = Boolean.valueOf(FRInfo.split(";")[0]);
			object.FRTime = Integer.parseInt(FRInfo.split(";")[1]);

			object.mineRating = Integer.parseInt(ratingChanges.split(";")[1]);
			object.enemyRating = Integer.parseInt(ratingChanges.split(";")[2]);

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
	//<editor-fold desc="FB">
	public Integer getFBMedian(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).FB);
		}
		array = extremumElimination(array);
		Collections.sort(array);

		Double median = 0.0;
		if (array.size() % 2 == 0)
			median = (double) (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		else
			median = (double) array.get(array.size() / 2);
		return median.intValue();
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

	public Integer getFBForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> sevenDaysObjects) throws ParseException
	{
		Integer monthMedian = 0;
		Integer sevenDaysMedian = 0;
		monthMedian = getFBMedian(monthObjects);
		if (sevenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			sevenDaysMedian = getFBMedian(sevenDaysObjects);
			Double form = (double) sevenDaysMedian / monthMedian * 100 - 100;
			return form.intValue();
		}
	}

	public Integer getBadGameFBPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).FB >= -400 && monthObjects.get(i).FB <= -100)
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
			if (monthObjects.get(i).FB > -100 && monthObjects.get(i).FB <= 100)
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
			if (monthObjects.get(i).FB > 100 && monthObjects.get(i).FB <= 300)
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
			if (monthObjects.get(i).FB > 300 && monthObjects.get(i).FB <= 500)
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
	public Integer getF10KMedian(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		ArrayList<Integer> array = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			array.add(objects.get(i).tenKills);
		}
		array = extremumElimination(array);
		Collections.sort(array);

		Double median = 0.0;
		if (array.size() % 2 == 0)
			median = (double) (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		else
			median = (double) array.get(array.size() / 2);
		return median.intValue();
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

	public Integer getF10KForm(ArrayList<AnalizedInfo> monthObjects, ArrayList<AnalizedInfo> sevenDaysObjects) throws ParseException
	{
		Integer monthMedian = 0;
		Integer sevenDaysMedian = 0;
		monthMedian = getF10KMedian(monthObjects);
		if (sevenDaysObjects.size() < 4)
		{
			return 9999;
		} else
		{
			sevenDaysMedian = getF10KMedian(sevenDaysObjects);
			Double form = (double) sevenDaysMedian / monthMedian * 100 - 100;
			return form.intValue();
		}
	}

	public Integer getBadGameF10KPercent(ArrayList<AnalizedInfo> monthObjects)
	{
		Integer counter = 0;
		Integer thisCounter = 0;

		for (int i = 0; i < monthObjects.size(); i++)
		{
			if (monthObjects.get(i).tenKills < 200)
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
			if (monthObjects.get(i).tenKills >= 200 && monthObjects.get(i).tenKills < 700)
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
			if (monthObjects.get(i).tenKills >= 700 && monthObjects.get(i).tenKills < 1300)
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
			if (monthObjects.get(i).tenKills >= 1300)
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
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 15)
			{
				points += objects.get(i).enemyRating;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
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
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
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
	public ArrayList<AnalizedInfo> separateLastThreeGames(ArrayList<AnalizedInfo> array) throws ParseException
	{
		ArrayList<AnalizedInfo> newArray = new ArrayList<>();

		for (int i = array.size() - 1; i > array.size() - 4; i--)
		{
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 30)
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
			if (getDifferenceDays(formatter.parse(array.get(i).date)) <= 30)
				newArray.add(array.get(i));
		}
		return newArray;
	}
	//</editor-fold>
}
