package ProjectDir;

import ProjectDir.Analitics.*;
import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class MainAnaliticsFactory
{
	PrimaryAnaliticsFactory primaryAnaliticsFactory = new PrimaryAnaliticsFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	FileControlFactory fileControlFactory = new FileControlFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	StringReader stringReader = new StringReader();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void startWork() throws IOException, ParseException, InterruptedException
	{
		Match match = new Match();
		Player[] player = new Player[10];
		Team[] team = new Team[2];

		for (int i = 0; i < 10; i++)
		{
			player[i] = new Player();
		}
		for (int i = 0; i < 2; i++)
		{
			team[i] = new Team();
		}
		/**PrimaryAnaliticsFactory and EPPFactory**/
		String matchesFile = fileControlFactory.readFile("files/Matches.txt");
		String[] matches = matchesFile.split("\n");
		for (int i = 0; i < matches.length; i++)
		{
			String matchId = matches[i].split(";")[0];
			if (uniqueInfoFactory.checkIfIdAlreadyAnalized(matchId))
				continue;
			stringReader.fillArraysFromFile(matches[i], team, player, match);
			primaryAnaliticsFactory.analizeMatch(team, player, match);
			writerReaderFactory.makeZeros(team, player, match);
			fileOperationsFactory.writeToFile(matchId, "files/MatchesAnalized.txt");
		}
	}
}
