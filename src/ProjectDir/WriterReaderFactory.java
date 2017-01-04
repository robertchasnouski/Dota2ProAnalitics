package ProjectDir;

import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class WriterReaderFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	Boolean writeMatchInfoToFile(Player[] players, Team[] teams, Match match) throws IOException
	{
		System.out.print("Don't terminate program. Wait...");
		boolean error = false;
		//Match
		String writeString = "";
		writeString += match.id + ";";
		if (match.id.length() != 10)
		{
			System.out.println("MatchID Error");
			error = true;
		}
		writeString += match.date + ";";
		if (match.date.length() != 10)
		{
			error = true;
			System.out.println("MatchDate Error");
		}
		writeString += Integer.toString(match.matchTime) + ";";
		if (match.matchTime < 10 || match.matchTime > 150)
		{
			error = true;
			System.out.println("MatchTime Error");
		}
		writeString += match.team1Id + ";";
		writeString += match.team2Id + ";";
		writeString += Integer.toString(match.leagueId) + ";";
		writeString += match.leagueName + ";";
		writeString += Boolean.toString(match.winRadiant);

		writeString += "##";
		//writeToFile("##", false);
		//Team
		for (int i = 0; i < 2; i++)
		{
			writeString += teams[i].id + ";";
			//writeToFile(teams[i].id, true);
			teams[i].name = teams[i].name.replaceAll(";", "");
			writeString += teams[i].name + ";";
			writeString += Integer.toString(teams[i].kills) + ";";
			if (teams[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(teams[i].deaths) + ";";
			if (teams[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(teams[i].assists);
			if (teams[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			if(i==0)
				writeString+="||";
		}
		writeString += "##";
		//Player
		for (int i = 0; i < 10; i++)
		{
			writeString += players[i].playerId + ";";
			writeString += players[i].hero + ";";
			writeString += Integer.toString(players[i].kills) + ";";
			if (players[i].kills < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(players[i].deaths) + ";";
			if (players[i].deaths < 0)
			{
				error = true;
				System.out.println("KDA error");
			}
			writeString += Integer.toString(players[i].assists) + ";";
			if (players[i].assists < 0)
			{
				error = true;
				System.out.println("KDA error");
			}

			writeString += Integer.toString(players[i].heroHeal) + ";";
			if (players[i].heroHeal < 0 || players[i].heroHeal > 100000)
			{
				error = true;
				System.out.println("HeroHeal error");
			}
			writeString += Integer.toString(players[i].heroDamage) + ";";
			//writeToFile(Integer.toString(players[i].heroDamage), true);
			if (players[i].heroDamage < 0 || players[i].heroDamage > 250000)
			{
				error = true;
				System.out.println("HeroDamage error");
			}
			writeString += Integer.toString(players[i].towerDamage) + ";";
			//writeToFile(Integer.toString(players[i].towerDamage), true);
			if (players[i].towerDamage < 0 || players[i].towerDamage > 100000)
			{
				error = true;
				System.out.println("TowerDamage error");
			}
			writeString += Integer.toString(players[i].totalGPM) + ";";
			//writeToFile(Integer.toString(players[i].totalGPM), true);
			if (players[i].totalGPM < 50 || players[i].totalGPM > 10000)
			{
				error = true;
				System.out.println("TotalGPM player error");
			}
			writeString += Integer.toString(players[i].totalXPM);
			//writeToFile(Integer.toString(players[i].totalXPM), true);
			if (players[i].totalXPM < 0 || players[i].totalXPM > 10000)
			{
				error = true;
				System.out.println("TotalXPM error");
			}
			if (i != 9)
			{
				writeString += "||";
			}
		}

		if (error)
		{
			fileOperationsFactory.writeToFile("Match " + match.id + " error.", "files/Log.txt");
			/*String newString = "";
			String originalFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
			String[] lineByLine = originalFile.split("\n");
			for (int i = 0; i < lineByLine.length - 1; i++)
			{
				newString += lineByLine[i] + "\n";
			}
			fileOperationsFactory.cleanAndWriteToFile(newString, "files/TemporaryMatches.txt");*/
		} else
		{
			fileOperationsFactory.writeToFile(writeString, "files/TemporaryMatches.txt");
			//writeString += "\n";
			//writeToFile("\n", false);
		}

		if (error)
		{
			return false;
		} else
		{
			System.out.print("Successfully ended.\n");
			return true;
		}

	}

	void makeZeros(Team[] teams, Player[] players, Match match)
	{
		for (int i = 0; i < teams.length; i++)
		{
			teams[i].teamZeros();
		}
		for (int i = 0; i < players.length; i++)
		{
			players[i].playerZeros();
		}
		match.matchZeros();
	}

	public WriterReaderFactory()
	{

	}

}
