package ProjectDir;

import ProjectDir.Analitics.FileControlFactory;
import ProjectDir.Analitics.StringReader;

import java.io.IOException;
import java.util.Scanner;

public class RatingFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	StringReader stringReader = new StringReader();
	FileControlFactory fileControlFactory = new FileControlFactory();
	TierWorker tierWorker = new TierWorker();
	Scanner reader = new Scanner(System.in);

	public void organizeRating() throws IOException, InterruptedException
	{
		String matchFile = fileOperationsFactory.readFile("files/Matches.txt");
		String[] matches = matchFile.split("\n");

		for (int i = 0; i < matches.length; i++)
		{
			String matchId = matches[i].split(";")[0];
			if (!fileOperationsFactory.readFile("files/MatchesRated.txt").contains(matchId))
			{
				//Here I must analize match hardness
				String matchInfo = stringReader.getMatchInfo(matches[i]);
				String team1Info = stringReader.getTeamsInfo(matches[i]).split("\\|\\|")[0];
				String team2Info = stringReader.getTeamsInfo(matches[i]).split("\\|\\|")[1];

				String matchTime = matchInfo.split(";")[2];
				String team1Id = team1Info.split(";")[0];
				String team1Name = team1Info.split(";")[1];
				String team2Id = team2Info.split(";")[0];
				String team2Name = team2Info.split(";")[1];

				String team1Tier = tierWorker.getTeamTier(team1Id);
				String team2Tier = tierWorker.getTeamTier(team2Id);
				Integer team1Rating = getRatingById(team1Id, team1Name, Integer.parseInt(team2Tier));
				Integer team2Rating = getRatingById(team2Id, team2Name, Integer.parseInt(team1Tier));
				Integer team1MainRating = getRatingById(team1Id, team1Name, 0);
				Integer team2MainRating = getRatingById(team2Id, team2Name, 0);

				Boolean firstTeamWin = Boolean.parseBoolean(matchInfo.split(";")[7]);
				String ratingDifference = calculateRatingDifference(team1Rating, team2Rating);
				Double winMultiplier = 1.0;
				Double loseMultiplier = 1.0;
				Integer incrementator = 0;
				Integer decrementator = 0;

				//<editor-fold desc="Calculation">
				if (firstTeamWin)
				{
					if (ratingDifference.equals("T1BD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						updateTeamRatings(team1Id, team2Id, incrementator, decrementator, Integer.parseInt(team1Tier), Integer.parseInt(team2Tier));
					} else if (ratingDifference.equals("T1SD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						updateTeamRatings(team1Id, team2Id, incrementator, decrementator, Integer.parseInt(team1Tier), Integer.parseInt(team2Tier));
					} else if (ratingDifference.equals("T2SD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						updateTeamRatings(team1Id, team2Id, incrementator, decrementator, Integer.parseInt(team1Tier), Integer.parseInt(team2Tier));
					} else if (ratingDifference.equals("T2BD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						updateTeamRatings(team1Id, team2Id, incrementator, decrementator, Integer.parseInt(team1Tier), Integer.parseInt(team2Tier));
					}
				} else
				{
					if (ratingDifference.equals("T1BD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						updateTeamRatings(team2Id, team1Id, incrementator, decrementator, Integer.parseInt(team2Tier), Integer.parseInt(team1Tier));
					} else if (ratingDifference.equals("T1SD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						updateTeamRatings(team2Id, team1Id, incrementator, decrementator, Integer.parseInt(team2Tier), Integer.parseInt(team1Tier));
					} else if (ratingDifference.equals("T2SD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						updateTeamRatings(team2Id, team1Id, incrementator, decrementator, Integer.parseInt(team2Tier), Integer.parseInt(team1Tier));
					} else if (ratingDifference.equals("T2BD"))
					{
						winMultiplier = 1.0;
						loseMultiplier = 1.0;
						incrementator = getWinTeamIncrementator(team2Rating, team1Rating, winMultiplier);
						decrementator = getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
						updateTeamRatings(team2Id, team1Id, incrementator, decrementator, Integer.parseInt(team2Tier), Integer.parseInt(team1Tier));
					}
				}
				//</editor-fold>

				if (firstTeamWin)
					updateTeamRatings(team1Id, team2Id, 50, 50, 0, 0);
				else
					updateTeamRatings(team2Id, team1Id, 50, 50, 0, 0);

				fileControlFactory.createTeamFileIfNotExists(team1Id);
				fileControlFactory.createTeamFileIfNotExists(team2Id);
				fileOperationsFactory.writeToFile(matchId + ";" + team1MainRating + ";" + team2MainRating + ";" + 50 + ";" + team1Rating + ";" + team2Rating, "files/teams/" + team1Id + "/TeamMatches.txt");
				fileOperationsFactory.writeToFile(matchId + ";" + team2MainRating + ";" + team1MainRating + ";" + 50 + ";" + team2Rating + ";" + team1Rating, "files/teams/" + team2Id + "/TeamMatches.txt");
				fileOperationsFactory.writeToFile(matchId, "files/MatchesRated.txt");
				System.out.println("Match " + matchId + " was rated");
			}
		}
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

	public void addTeamToFileIfNotExists(String teamName, String teamId) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] eachLine = fileString.split("\n");
		boolean contains = false;
		for (int i = 0; i < eachLine.length; i++)
		{
			String id = eachLine[i].split(";")[0];
			if (id.equals(teamId))
				contains = true;
		}
		if (contains == false)
		{
			fileOperationsFactory.writeToFile(teamId + ";" + teamName + ";" + "1000" + ";" + "1000" + ";" + "1000" + ";" + "1000" + ";" + "1000" + ";" + "1000", "files/TeamRatings.txt");
		}
	}

	public Integer getRatingById(String id, String teamName, Integer tier) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] lineByLine = fileString.split("\n");
		for (int i = 0; i < lineByLine.length; i++)
		{
			String teamId = lineByLine[i].split(";")[0];
			if (teamId.equals(id))
				return Integer.parseInt(lineByLine[i].split(";")[2 + tier]);
		}
		addTeamToFileIfNotExists(teamName, id);
		return 1000;
	}

	public Integer getWinTeamIncrementator(Integer winTeamRating, Integer loseTeamRating, Double multiplier)
	{
		Double firstMultiplier = 0.0;
		Double incrementator = 0.0;

		if (winTeamRating > loseTeamRating)
		{
			firstMultiplier = (1 - (double) Math.abs(winTeamRating - loseTeamRating) / Math.min(winTeamRating, loseTeamRating));
			incrementator = 50 * firstMultiplier;
		} else
		{
			firstMultiplier = (1 + (double) Math.abs(winTeamRating - loseTeamRating) / Math.min(winTeamRating, loseTeamRating));
			incrementator = 50 * firstMultiplier;
		}
		if (incrementator < 20)
			incrementator = 20.0;
		if (incrementator > 100)
			incrementator = 100.0;

		incrementator = incrementator * multiplier;

		return incrementator.intValue();
	}

	public Integer getLoseTeamIncrementator(Integer loseTeamRating, Integer winTeamRating, Double multiplier)
	{
		Double firstMultiplier = 0.0;
		Double incrementator = 0.0;

		if (winTeamRating > loseTeamRating)
		{
			firstMultiplier = (1 - (double) Math.abs(winTeamRating - loseTeamRating) / Math.min(winTeamRating, loseTeamRating));
			incrementator = 50 * firstMultiplier;
		} else
		{
			firstMultiplier = (1 + (double) Math.abs(winTeamRating - loseTeamRating) / Math.min(winTeamRating, loseTeamRating));
			incrementator = 50 * firstMultiplier;
		}
		if (incrementator < 20)
			incrementator = 20.0;
		if (incrementator > 100)
			incrementator = 100.0;

		incrementator = incrementator * multiplier;

		return incrementator.intValue();
	}

	public void updateTeamRatings(String teamWinId, String teamLostId, Integer increment, Integer decrement, Integer tierWonTeam, Integer tierLostTeam) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] fileLines = fileString.split("\n");

		for (int i = 0; i < fileLines.length; i++)
		{
			String teamIdInLine = fileLines[i].split(";")[0];
			if (teamIdInLine.equals(teamWinId))
			{
				String[] fileSplitterIncrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterIncrement[2 + tierLostTeam]) + increment;
				fileSplitterIncrement[2 + tierLostTeam] = tempInt + "";
				fileLines[i] = fileSplitterIncrement[0] + ";" + fileSplitterIncrement[1] + ";" + fileSplitterIncrement[2] + ";" + fileSplitterIncrement[3] + ";" + fileSplitterIncrement[4] + ";" + fileSplitterIncrement[5] + ";" + fileSplitterIncrement[6] + ";" + fileSplitterIncrement[7];
			}
			if (teamIdInLine.equals(teamLostId))
			{
				String[] fileSplitterDecrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterDecrement[2 + tierWonTeam]) - decrement;
				fileSplitterDecrement[2 + tierWonTeam] = tempInt + "";
				fileLines[i] = fileSplitterDecrement[0] + ";" + fileSplitterDecrement[1] + ";" + fileSplitterDecrement[2] + ";" + fileSplitterDecrement[3] + ";" + fileSplitterDecrement[4] + ";" + fileSplitterDecrement[5] + ";" + fileSplitterDecrement[6] + ";" + fileSplitterDecrement[7];
			}
		}
		String outputString = "";
		for (int i = 0; i < fileLines.length; i++)
		{
			outputString += fileLines[i];
			outputString += "\n";
		}
		fileOperationsFactory.cleanAndWriteToFile(outputString, "files/TeamRatings.txt");
	}

}
