package ProjectDir.Analitics;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.util.ArrayList;

public class LeagueAnalitics
{


	private Integer getLeaguePoints(ArrayList<AnalizedInfo> objects, String leagueId)
	{
		Integer points = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).leagueId.toString().equals(leagueId))
			{
				Integer r1 = objects.get(i).mineTierRating;
				Integer r2 = objects.get(i).enemyTierRating;
				String difference = calculateRatingDifference(r1, r2);
				//TODO Add MatchHardNess or not?
				Integer incrementator = calculateIncrementator(objects.get(i).isWin, difference);
				points+=incrementator;
			}
		}
		return points;
	}

	private String getLeagueNameById(ArrayList<AnalizedInfo> objects, String leagueId)
	{
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).leagueId.toString().equals(leagueId))
				return objects.get(i).leagueName;
		}
		return "LeagueNotFounded";
	}

	private Integer getLeagueMatchesPlayed(ArrayList<AnalizedInfo> objects, String leagueId)
	{
		Integer counter = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).leagueId.toString().equals(leagueId))
				counter++;
		}
		return counter;
	}

	private Integer getLeagueForm(Integer month, Integer ten)
	{
		Double form = (double) ten / month * 100;
		return form.intValue();
	}

	private Integer getLeagueWinRate(ArrayList<AnalizedInfo> objects, String leagueId)
	{
		Integer winGames = 0;
		Integer allGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).leagueId.toString().equals(leagueId))
			{
				if (objects.get(i).isWin == true)
					winGames++;
				allGames++;
			}
		}
		double temp = (double) winGames / allGames * 10000;
		temp = temp / 100;
		temp = Math.round(temp);
		return (int) temp;
	}

	private ArrayList<String> getLastTeamLeagues(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<String> leagues = new ArrayList<>();

		for (int i = 0; i < objects.size(); i++)
		{
			if (!leagues.contains(objects.get(i).leagueId.toString()))
				leagues.add(objects.get(i).leagueId.toString());
		}
		return leagues;
	}

	public String calculateRatingDifference(Integer t1R, Integer t2R)
	{
		if (t1R > t2R)
		{
			if ((double) (t1R - t2R) / t2R > 0.3)
			{
				return "T1BD";
			} else return "T1SD";
		} else
		{
			if ((double) (t2R - t1R) / Math.min(t1R, t2R) > 0.3)
			{
				return "T2BD";
			} else return "T2SD";
		}
	}

	public Integer calculateIncrementator(Boolean isWin, String difference)
	{
		Integer incrementator = 0;
		if (isWin)
		{
			if (difference.equals("T1BD"))
			{
				incrementator = 35;
			}
			if (difference.equals("T1SD"))
			{
				incrementator = 45;
			}
			if (difference.equals("T2SD"))
			{
				incrementator = 55;
			}
			if (difference.equals("T2BD"))
			{
				incrementator = 65;
			}
		} else
		{
			if (difference.equals("T1BD"))
			{
				incrementator = -65;
			}
			if (difference.equals("T1SD"))
			{
				incrementator = -55;
			}
			if (difference.equals("T2SD"))
			{
				incrementator = -45;
			}
			if (difference.equals("T2BD"))
			{
				incrementator = -35;
			}
		}
		return incrementator;
	}
}
