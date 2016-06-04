package ProjectDir.Analitics;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StandinAnalitics
{
	public void lookForStandins(ArrayList<AnalizedInfo> firstTeamMonthMatches, ArrayList<AnalizedInfo> firstTeamTenDaysMatches, ArrayList<AnalizedInfo> secondTeamMonthMatches, ArrayList<AnalizedInfo> secondTeamTenDaysMatches)
	{
		ArrayList<String> firstTeamPlayersPool = getPlayersPool(firstTeamMonthMatches);
		ArrayList<String> secondTeamPlayersPool = getPlayersPool(secondTeamMonthMatches);
		System.out.println("/---First Team---/");
		System.out.println("/-Month Players Partisipation:");
		for (int i = 0; i < firstTeamPlayersPool.size(); i++)
		{
			Integer percent = getPercentOfPartisipation(firstTeamMonthMatches, firstTeamPlayersPool.get(i));
			System.out.println(firstTeamPlayersPool.get(i) + " has " + percent + "% of patisipation.");
		}
		System.out.println("/-Ten Days Players Partisipation:");
		for (int i = 0; i < firstTeamPlayersPool.size(); i++)
		{
			Integer percent = getPercentOfPartisipation(firstTeamTenDaysMatches, firstTeamPlayersPool.get(i));
			System.out.println(firstTeamPlayersPool.get(i) + " has " + percent + "% of patisipation.");
		}
		System.out.println("/---Second Team---/");
		System.out.println("/-Month Players Partisipation:");
		for (int i = 0; i < secondTeamPlayersPool.size(); i++)
		{
			Integer percent = getPercentOfPartisipation(secondTeamMonthMatches, secondTeamPlayersPool.get(i));
			System.out.println(secondTeamPlayersPool.get(i) + " has " + percent + "% of patisipation.");
		}
		System.out.println("/-Ten Days Players Partisipation:");
		for (int i = 0; i < secondTeamPlayersPool.size(); i++)
		{
			Integer percent = getPercentOfPartisipation(secondTeamTenDaysMatches, secondTeamPlayersPool.get(i));
			System.out.println(secondTeamPlayersPool.get(i) + " has " + percent + "% of patisipation.");
		}
	}

	public Integer getPercentOfPartisipation(ArrayList<AnalizedInfo> objects, String playerID)
	{
		Double allGames = 0.0;
		Double partisipateGames = 0.0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).playerId[0].equals(playerID))
				partisipateGames++;
			if (objects.get(i).playerId[1].equals(playerID))
				partisipateGames++;
			if (objects.get(i).playerId[2].equals(playerID))
				partisipateGames++;
			if (objects.get(i).playerId[3].equals(playerID))
				partisipateGames++;
			if (objects.get(i).playerId[4].equals(playerID))
				partisipateGames++;
			allGames++;
		}
		Double partisipate = partisipateGames / allGames * 100;
		return partisipate.intValue();
	}

	public ArrayList<String> getPlayersPool(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<String> playersPool = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (!playersPool.contains(objects.get(i).playerId[j]))
					playersPool.add(objects.get(i).playerId[j]);
			}
		}
		return playersPool;
	}
}
