package ProjectDir;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CleanerWorker
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	ParserFactory parserFactory = new ParserFactory();
	DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void cleanShit() throws IOException, ParseException
	{
		deleteOldTeams();
		updateTeamNames();
	}

	private void updateTeamNames() throws IOException
	{
		String teamsFile = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String newTeamsFile = "";
		String[] eachTeamRating = teamsFile.split("\n");
		for (int i = 0; i < eachTeamRating.length; i++)
		{
			String teamFile = fileOperationsFactory.readFile("files/teams/" + eachTeamRating[i].split(";")[0] + "/TeamMatches.txt");
			if (teamFile.length() == 0)
				continue;
			String[] eachMatch = teamFile.split("\n");
			String newTeamName = eachMatch[eachMatch.length - 1].split(";")[14];
			newTeamName = newTeamName.substring(0, newTeamName.indexOf("##"));
			newTeamsFile += "\n" + eachTeamRating[i].split(";")[0] + ";" + newTeamName + ";" + eachTeamRating[i].split(";")[2] + ";" + eachTeamRating[i].split(";")[3] + ";" + eachTeamRating[i].split(";")[4] + ";" + eachTeamRating[i].split(";")[5] + ";" + eachTeamRating[i].split(";")[6] + ";" + eachTeamRating[i].split(";")[7];
		}
		newTeamsFile = newTeamsFile.replaceFirst("\n", "");
		fileOperationsFactory.cleanAndWriteToFile(newTeamsFile, "files/TeamRatings.txt");
	}

	private void deleteOldTeams() throws IOException, ParseException
	{
		String teamsFile = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String newTeamsFile = "";
		String[] eachTeamRating = teamsFile.split("\n");
		for (int i = 0; i < eachTeamRating.length; i++)
		{
			String teamFile = fileOperationsFactory.readFile("files/teams/" + eachTeamRating[i].split(";")[0] + "/TeamMatches.txt");
			if (teamFile.length() == 0)
				continue;
			String[] eachMatch = teamFile.split("\n");
			Date lastMatchDate = sourceFormat.parse(eachMatch[eachMatch.length - 1].split(";")[1]);
			long daysDiff = ((new Date()).getTime() - lastMatchDate.getTime()) / (60 * 60 * 24 * 1000);
			if (daysDiff < 90)
				newTeamsFile += "\n" + eachTeamRating[i];
		}
		newTeamsFile = newTeamsFile.replaceFirst("\n", "");
		fileOperationsFactory.cleanAndWriteToFile(newTeamsFile, "files/TeamRatings.txt");
	}
}
