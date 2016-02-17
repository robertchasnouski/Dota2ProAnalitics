package Analitics;

import java.io.IOException;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory=new FileControlFactory();
	public void analizeMatches() throws IOException
	{
		String matchesFile = fileControlFactory.readFile("files/Matches.txt");
		String[] matches = matchesFile.split("\n");
		//TODO: Here need to parse team1ID and team2ID and createFilesIfNotExists
		for (int i = 0; i < matches.length; i++)
		{
			String []tempSeparator1=matches[i].split(";");
			String matchId=tempSeparator1[0];

		}
	}
}
