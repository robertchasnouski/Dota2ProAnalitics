package ProjectDir.Analitics;

import ProjectDir.MatchInfo.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.util.ArrayList;

public class StringReader
{
	FileControlFactory fileControlFactory = new FileControlFactory();

	//Give me String and we will return smthg
	public String getMatchInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[0];
	}

	public String getTeamsInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[1];
	}

	public String getPlayersInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[2];
	}

	public String getTeamMatches(String id) throws IOException
	{
		String allMatches = fileControlFactory.readFile("files/Matches.txt");
		String[] oneMatch = allMatches.split("\n");
		String teamMatches = "";
		Integer counter = 0;
		for (int i = 0; i < oneMatch.length; i++)
		{
			String teamInfo = getTeamsInfo(oneMatch[i]);
			String[] oneTeamInfo = teamInfo.split("\\|\\|");
			String[] firstTeamSplitter = oneTeamInfo[0].split(";");
			String[] secondTeamSplitter = oneTeamInfo[1].split(";");
			if (firstTeamSplitter[0].equals(id) || secondTeamSplitter[0].equals(id))
			{
				if (counter != 0)
					teamMatches += "\n";
				teamMatches += oneMatch[i];
				counter++;
			}
		}
		return teamMatches;
	}

	public String getTeamRadiantMatches(String id) throws IOException
	{
		String allMatches = fileControlFactory.readFile("files/Matches.txt");
		String[] oneMatch = allMatches.split("\n");
		String teamMatches = "";
		Integer counter = 0;
		for (int i = 0; i < oneMatch.length; i++)
		{
			String teamInfo = getTeamsInfo(oneMatch[i]);
			String[] oneTeamInfo = teamInfo.split("\\|\\|");
			String[] firstTeamSplitter = oneTeamInfo[0].split(";");
			String[] secondTeamSplitter = oneTeamInfo[1].split(";");
			if (firstTeamSplitter[0].equals(id))
			{
				if (counter != 0)
					teamMatches += "\n";
				teamMatches += oneMatch[i];
				counter++;
			}
		}
		return teamMatches;
	}

	public String getTeamDireMatches(String id) throws IOException
	{
		String allMatches = fileControlFactory.readFile("files/Matches.txt");
		String[] oneMatch = allMatches.split("\n");
		String teamMatches = "";
		Integer counter = 0;
		for (int i = 0; i < oneMatch.length; i++)
		{
			String teamInfo = getTeamsInfo(oneMatch[i]);
			String[] oneTeamInfo = teamInfo.split("\\|\\|");
			String[] firstTeamSplitter = oneTeamInfo[0].split(";");
			String[] secondTeamSplitter = oneTeamInfo[1].split(";");
			if (secondTeamSplitter[0].equals(id))
			{
				if (counter != 0)
					teamMatches += "\n";
				teamMatches += oneMatch[i];
				counter++;
			}
		}
		return teamMatches;
	}

	public void fillArraysFromFile(String matchString, Team[] team, Player[] player, Match match)
	{
		String matchInfo = getMatchInfo(matchString);
		String teamInfo = getTeamsInfo(matchString);
		String playerInfo = getPlayersInfo(matchString);

		//<editor-fold desc="MATCH">
		//Match
		String[] matchInfoArray = matchInfo.split(";");
		match.id = matchInfoArray[0];
		match.date = matchInfoArray[1];
		match.matchTime = Integer.parseInt(matchInfoArray[2]);
		match.team1Id = matchInfoArray[3];
		match.team2Id = matchInfoArray[4];
		match.leagueId = Integer.parseInt(matchInfoArray[5]);
		match.leagueName = matchInfoArray[6];
		match.winRadiant = Boolean.parseBoolean(matchInfoArray[7]);
		//</editor-fold>

		//<editor-fold desc="TEAMS">
		//Teams
		String[] teamInfoArray = teamInfo.split("\\|\\|");
		String[] teamInfo1Array = teamInfoArray[0].split(";");
		String[] teamInfo2Array = teamInfoArray[1].split(";");

		team[0].id = teamInfo1Array[0];
		team[0].name = teamInfo1Array[1];
		team[0].kills = Integer.parseInt(teamInfo1Array[2]);
		team[0].deaths = Integer.parseInt(teamInfo1Array[3]);
		team[0].assists = Integer.parseInt(teamInfo1Array[4]);

		team[1].id = teamInfo2Array[0];
		team[1].name = teamInfo2Array[1];
		team[1].kills = Integer.parseInt(teamInfo2Array[2]);
		team[1].deaths = Integer.parseInt(teamInfo2Array[3]);
		team[1].assists = Integer.parseInt(teamInfo2Array[4]);
		//</editor-fold>

		//<editor-fold desc="PLAYERS">
		//Players
		String[] onePlayerInfo = playerInfo.split("\\|\\|");
		for (int i = 0; i < 10; i++)
		{
			String[] mainInfoOnePlayer = onePlayerInfo[i].split(";");
			player[i].playerId = mainInfoOnePlayer[0];
			player[i].hero = mainInfoOnePlayer[1];
			player[i].kills = Integer.parseInt(mainInfoOnePlayer[2]);
			player[i].deaths = Integer.parseInt(mainInfoOnePlayer[3]);
			player[i].assists = Integer.parseInt(mainInfoOnePlayer[4]);
			player[i].heroHeal = Integer.parseInt(mainInfoOnePlayer[5]);
			player[i].heroDamage = Integer.parseInt(mainInfoOnePlayer[6]);
			player[i].towerDamage = Integer.parseInt(mainInfoOnePlayer[7]);
			player[i].totalGPM = Integer.parseInt(mainInfoOnePlayer[8]);
			player[i].totalXPM = Integer.parseInt(mainInfoOnePlayer[9]);
		}
		//</editor-fold>

	}
}
