package ProjectDir.Analitics;

import ProjectDir.FileOperationsFactory;
import ProjectDir.MatchInfo.*;
import ProjectDir.TierWorker;

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
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	TierWorker tierWorker = new TierWorker();
	Scanner reader = new Scanner(System.in);

	public void analizeMatch(Team[] team, Player[] player, Match match) throws IOException, ParseException, InterruptedException
	{
		/**Create team file if not exists**/
		fileControlFactory.createTeamFileIfNotExists(team[0].id);
		fileControlFactory.createTeamFileIfNotExists(team[1].id);
		//createLeagueNameIfNotExists(match.leagueId,match.leagueName);
		/**Start analizing**/
		/**LeagueTier**/
		String team1Tier = tierWorker.getTeamTier(team[0].id);
		String team2Tier = tierWorker.getTeamTier(team[1].id);
		/**End analizing**/
		/*** Write info to file **/
		///Write that match was analized

		writeRadiantInfoToFile(team, player, match, team2Tier);
		writeDireInfoToFile(team, player, match, team1Tier);
		writePlayersInfoToFile(team, player, match);
		System.out.println("Match " + match.id + " was analized.");
	}

	public void writeRadiantInfoToFile(Team[] team, Player[] player, Match match, String enemyTeamTier) throws IOException, ParseException
	{
		String teamString = "";
		/**General Information [0]**/
		teamString += match.id + ";";//[0]
		teamString += match.date + ";";//[1]
		teamString += match.matchTime + ";";//[2]
		teamString += team[0].name+";";//[3]
		teamString += team[0].id + ";";//[4]
		teamString += (match.winRadiant ? "true" : "false") + ";";//[5]
		teamString += "0" + ";";//[6]
		teamString += team[1].name + ";";//[7]
		teamString += team[1].id + ";";//[8]
		teamString += match.leagueName + ";";//[9]
		teamString += match.leagueId + ";";//[10]
		teamString += enemyTeamTier;//[11]

		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[0].kills + ";";
		teamString += team[0].deaths + ";";
		teamString += team[0].assists;
		teamString += "##";
		/**Players And EPP's [2]**/
		for (int i = 0; i < 5; i++)
		{
			teamString += player[i].playerId + ";";
			teamString += player[i].hero + ";";
			teamString += player[i].totalGPM + ";";
			teamString += player[i].totalXPM + ";";
			teamString += player[i].kills + ";";
			teamString += player[i].deaths + ";";
			teamString += player[i].assists + ";";
			teamString += player[i].heroDamage + ";";
			teamString += player[i].heroHeal + ";";
			teamString += player[i].towerDamage + "||";
		}
		teamString += player[5].hero + ";";
		teamString += player[6].hero + ";";
		teamString += player[7].hero + ";";
		teamString += player[8].hero + ";";
		teamString += player[9].hero;
		teamString += "##";
		/**Rating Changes [3]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[0].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";
		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 70)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
			}
		}

		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[0].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[0].id + "/TeamMatches.txt");
		}

	}

	public void writeDireInfoToFile(Team[] team, Player[] player, Match match, String enemyTeamTier) throws IOException, ParseException
	{
		String teamString = "";
		/**General Information [0]**/
		teamString += match.id + ";";//[0]
		teamString += match.date + ";";//[1]
		teamString += match.matchTime + ";";//[2]
		teamString += team[1].name+";";
		teamString += team[1].id + ";";//[3]
		teamString += (match.winRadiant ? "false" : "true") + ";";
		teamString += "1" + ";";//[4]
		teamString += team[0].name + ";";
		teamString += team[0].id + ";";
		teamString += match.leagueName + ";";
		teamString += match.leagueId + ";";
		teamString += enemyTeamTier;
		teamString += "##";
		/**TeamInfo [1]**/
		teamString += team[1].kills + ";";
		teamString += team[1].deaths + ";";
		teamString += team[1].assists;
		teamString += "##";
		/**Players And EPP's [2]**/


		for (int i = 5; i < 10; i++)
		{
			teamString += player[i].playerId + ";";
			teamString += player[i].hero + ";";
			teamString += player[i].totalGPM + ";";
			teamString += player[i].totalXPM + ";";
			teamString += player[i].kills + ";";
			teamString += player[i].deaths + ";";
			teamString += player[i].assists + ";";
			teamString += player[i].heroDamage + ";";
			teamString += player[i].heroHeal + ";";
			teamString += player[i].towerDamage + "||";
		}
		teamString += player[0].hero + ";";
		teamString += player[1].hero + ";";
		teamString += player[2].hero + ";";
		teamString += player[3].hero + ";";
		teamString += player[4].hero;
		teamString += "##";
		/**Rating Changes [10]**/
		String oldString = fileControlFactory.readFile("files/teams/" + team[1].id + "/TeamMatches.txt");
		String[] stringInFile = oldString.split("\n");
		String ratingChanges = "";
		for (int i = 0; i < stringInFile.length; i++)
		{
			String matchId = stringInFile[i].split(";")[0];
			if (matchId.equals(match.id) && stringInFile[i].length() <= 70)
			{
				ratingChanges = stringInFile[i];
				stringInFile[i] = teamString + ratingChanges;
			}
		}
		fileControlFactory.cleanAndWriteToFile("", "files/teams/" + team[1].id + "/TeamMatches.txt");
		for (int i = 0; i < stringInFile.length; i++)
		{
			fileControlFactory.writeToFile(stringInFile[i], "files/teams/" + team[1].id + "/TeamMatches.txt");
		}
	}

	public void writePlayersInfoToFile(Team[] team, Player[] player, Match match) throws IOException, InterruptedException
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
			if (i <= 4)
				playerString += tierWorker.getTeamTier(team[1].id) + ";";
			else
				playerString += tierWorker.getTeamTier(team[0].id) + ";";
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
				playerString += ";" + team[1].id;
			else
				playerString += ";" + team[0].id;

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

