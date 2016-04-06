package ProjectDir;

import ProjectDir.Analitics.FileControlFactory;
import ProjectDir.Analitics.StringReader;

import java.io.IOException;

public class RatingFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	StringReader stringReader = new StringReader();
	FileControlFactory fileControlFactory=new FileControlFactory();
	public void organizeRating() throws IOException
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

				Integer team1Rating = getRatingById(team1Id, team1Name);
				Integer team2Rating = getRatingById(team2Id, team2Name);
				Boolean firstTeamWin = Boolean.parseBoolean(matchInfo.split(";")[7]);
				String team1Kills = team1Info.split(";")[3];
				String team2Kills = team2Info.split(";")[3];
				String team1totalGold = team1Info.split(";")[20];
				String team2totalGold = team2Info.split(";")[20];
				String matchHardness = calculateMatchHardness(team1Kills, team2Kills, team1totalGold, team2totalGold, matchTime);
				String ratingDifference = calculateRatingDifference(team1Rating, team2Rating);
				Double winMultiplier = 1.0;
				Double loseMultiplier=1.0;
				Integer incrementator = 0;
				Integer decrementator=0;
				//<editor-fold desc="Calculation">
				if (firstTeamWin)
				{
					if (ratingDifference.equals("T1BD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 0.7;
							loseMultiplier=0.7;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.05;
							loseMultiplier=1.05;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T1SD"))
					{

						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.1;
							loseMultiplier= 1.1;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T2SD"))
					{

						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.2;
							loseMultiplier=1.2;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T2BD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.3;
							loseMultiplier=1.3;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.4;
							loseMultiplier=1.4;
							incrementator=getWinTeamIncrementator(team1Rating,team2Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team2Rating, team1Rating, winMultiplier);
							updateTeamRatings(team1Id, team2Id, incrementator, decrementator);
						}
					}
				} else
				{
					if (ratingDifference.equals("T1BD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.3;
							loseMultiplier=1.3;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.4;
							loseMultiplier=1.4;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T1SD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.2;
							loseMultiplier=1.2;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T2SD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.1;
							loseMultiplier=1.1;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
					} else if (ratingDifference.equals("T2BD"))
					{
						if (matchHardness.equals("MH"))
						{
							winMultiplier = 0.7;
							loseMultiplier=0.7;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("H"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("L"))
						{
							winMultiplier = 1.0;
							loseMultiplier=1.0;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
						if (matchHardness.equals("ML"))
						{
							winMultiplier = 1.05;
							loseMultiplier=1.05;
							incrementator=getWinTeamIncrementator(team2Rating,team1Rating,winMultiplier);
							decrementator=getLoseTeamIncrementator(team1Rating, team2Rating, winMultiplier);
							updateTeamRatings(team2Id, team1Id, incrementator, decrementator);
						}
					}
				}
				//</editor-fold>

				fileControlFactory.createTeamFileIfNotExists(team1Id);
				fileControlFactory.createTeamFileIfNotExists(team2Id);
				fileOperationsFactory.writeToFile(matchId+";"+team1Rating+";"+team2Rating+";"+incrementator,"files/teams/"+team1Id+"/TeamMatches.txt");
				fileOperationsFactory.writeToFile(matchId+";"+team2Rating+";"+team1Rating+";"+incrementator,"files/teams/"+team2Id+"/TeamMatches.txt");
				fileOperationsFactory.writeToFile(matchId, "files/MatchesRated.txt");
				System.out.println("Match "+matchId+" was rated with incrementator:"+incrementator+" and decrementator:"+decrementator+". Match HardNess:"+matchHardness);
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
			fileOperationsFactory.writeToFile(teamId + ";" + teamName + ";" + "1000", "files/TeamRatings.txt");
		}
	}

	public Integer getRatingById(String id, String teamName) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] lineByLine = fileString.split("\n");
		for (int i = 0; i < lineByLine.length; i++)
		{
			String teamId = lineByLine[i].split(";")[0];
			if (teamId.equals(id))
			{
				return Integer.parseInt(lineByLine[i].split(";")[2]);
			}
		}
		addTeamToFileIfNotExists(teamName, id);
		return 1000;
	}

	public Integer getWinTeamIncrementator(Integer winTeamRating,Integer loseTeamRating,Double multiplier)
	{
		Double firstMultiplier=0.0;
		Double incrementator=0.0;

		if(winTeamRating>loseTeamRating)
		{
			firstMultiplier=(1-(double)Math.abs(winTeamRating-loseTeamRating ) / Math.min(winTeamRating, loseTeamRating));
			incrementator=50*firstMultiplier;
		}
		else
		{
			firstMultiplier=(1+(double)Math.abs(winTeamRating-loseTeamRating ) / Math.min(winTeamRating, loseTeamRating));
			incrementator=50*firstMultiplier;
		}
		if(incrementator<20)
			incrementator=20.0;
		if(incrementator>100)
			incrementator=100.0;

		incrementator=incrementator*multiplier;

		return incrementator.intValue();
	}

	public Integer getLoseTeamIncrementator(Integer loseTeamRating,Integer winTeamRating,Double multiplier)
	{
		Double firstMultiplier=0.0;
		Double incrementator=0.0;

		if(winTeamRating>loseTeamRating)
		{
			firstMultiplier=(1-(double)Math.abs(winTeamRating-loseTeamRating ) / Math.min(winTeamRating, loseTeamRating));
			incrementator=50*firstMultiplier;
		}
		else
		{
			firstMultiplier=(1+(double)Math.abs(winTeamRating-loseTeamRating ) / Math.min(winTeamRating, loseTeamRating));
			incrementator=50*firstMultiplier;
		}
		if(incrementator<20)
			incrementator=20.0;
		if(incrementator>100)
			incrementator=100.0;

		incrementator=incrementator*multiplier;

		return incrementator.intValue();
	}

	public void updateTeamRatings(String teamWinId, String teamLostId, Integer increment, Integer decrement) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] fileLines = fileString.split("\n");

		for (int i = 0; i < fileLines.length; i++)
		{
			String teamIdInLine = fileLines[i].split(";")[0];
			if (teamIdInLine.equals(teamWinId))
			{
				String[] fileSplitterIncrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterIncrement[2]) + increment;
				fileSplitterIncrement[2] = tempInt + "";
				fileLines[i] = fileSplitterIncrement[0] + ";" + fileSplitterIncrement[1] + ";" + fileSplitterIncrement[2];
			}
			if (teamIdInLine.equals(teamLostId))
			{
				String[] fileSplitterDecrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterDecrement[2]) - decrement;
				fileSplitterDecrement[2] = tempInt + "";
				fileLines[i] = fileSplitterDecrement[0] + ";" + fileSplitterDecrement[1] + ";" + fileSplitterDecrement[2];
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
