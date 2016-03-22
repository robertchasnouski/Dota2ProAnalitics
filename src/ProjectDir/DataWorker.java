package ProjectDir;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DataWorker
{

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void analizeFutureMatch(String team1Id, String team2Id) throws IOException
	{
		String team1Matches = fileOperationsFactory.readFile("files/teams/" + team1Id + "/TeamMatches.txt");
		analizeTeamInfo(team1Matches);
	}

	public void analizeTeamInfo(String teamString)
	{
		ArrayList<AnalizedInfo> objects = new ArrayList<AnalizedInfo>();
		fillObject(objects, teamString);
		showMatchInfo(objects.get(0));
		//Integer avgAggression=getAverageAggression(objects);
	}

	public void fillObject(ArrayList<AnalizedInfo> objects, String allMatches)
	{
		String[] eachMatch = allMatches.split("\n");
		for (int i = 0; i < eachMatch.length; i++)
		{
			AnalizedInfo object = new AnalizedInfo();

			String matchGeneralInfo = eachMatch[i].split("##")[0];
			String KDA = eachMatch[i].split("##")[1];
			String playerEPPs = eachMatch[i].split("##")[2];
			String parameters = eachMatch[i].split("##")[3];
			String FBInfo = eachMatch[i].split("##")[4];
			String F10KInfo = eachMatch[i].split("##")[5];
			String FRInfo = eachMatch[i].split("##")[6];
			String killEventsInfo = eachMatch[i].split("##")[7];
			String ratingChanges = eachMatch[i].split("##")[8];

			object.id = matchGeneralInfo.split(";")[0];
			object.date = matchGeneralInfo.split(";")[1];
			object.matchTime = matchGeneralInfo.split(";")[2];
			object.teamId = matchGeneralInfo.split(";")[3];
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
		System.out.println("IsF10K:" + object.isF10K + " IsFR:" + object.isFR + " IsWin:" + object.isWin + " IsFB:" + object.isFB);
	}

	public Integer getAverageKillAbility(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).killAbility;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageTenKills(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).tenKills;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageFB(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).FB;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageEG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).EG;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageMG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).MG;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageLG(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).LG;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAveragePushing(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).pushing;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageVision(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).vision;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageLining(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).lining;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageFarming(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).farming;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageEnemyRating(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				points += objects.get(i).enemyRating;
				counter++;
			}
		}
		Double points2 = points / counter;
		return points2.intValue();
	}

	public Integer getAverageFBPercent(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				//points+=Integer.parseInt(objects.get(i).matchTime);
				//counter++;
			}
		}
		//Double points2=points/counter;
		//return points2.intValue();
		return 1;
	}

	public Integer getAverageF10KPercent(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
			{
				//points+=Integer.parseInt(objects.get(i).matchTime);
				//counter++;
			}
		}
		//Double points2=points/counter;
		//return points2.intValue();
		return 1;
	}

	public Integer getAverageMatchTime(ArrayList<AnalizedInfo> objects) throws ParseException
	{
		Integer counter = 0;
		Double points = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
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
			if (getDifferenceDays(formatter.parse(objects.get(i).date)) <= 30)
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

	ArrayList<Integer> extremumElimination(ArrayList<Integer> inputArray)
	{
		ArrayList<Integer> outputArray = new ArrayList<Integer>();
		Collections.sort(inputArray);
		Double avgValue = 0.0;
		Integer counter = 0;
		if (inputArray.size() >= 6)
		{
			for (int i = 0; i < inputArray.size(); i++)
			{
				avgValue += inputArray.get(i);
				counter++;
			}

			avgValue = avgValue / counter;

			if ((inputArray.get(0) - avgValue) / inputArray.get(0) * 100 >= 300 || (inputArray.get(0) - avgValue) / inputArray.get(0) * 100 <= -300)
			{
			} else
				outputArray.add(inputArray.get(0));

			for (int i = 1; i < inputArray.size(); i++)
			{
				outputArray.add(inputArray.get(i));
			}

			return outputArray;
		} else
			return inputArray;
	}

	public static long getDifferenceDays(Date d1)
	{
		Date d2 = new Date();
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}
