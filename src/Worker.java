import MatchInfo.Match;
import MatchInfo.Player;
import MatchInfo.Team;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Worker
{
	ParserFactory parserHelper = new ParserFactory();
	MatchFinderFactory matchFinder = new MatchFinderFactory();
	AnalizingFactory analizingFactory = new AnalizingFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();

	SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
	Scanner scanner = new Scanner(System.in);

	// TODO: 1. Get match ID's to parse 													DONE
	// TODO: 2. Try to write to file and create new files with match ID and team ID's
	// TODO: 3.

	void start_work() throws IOException, InterruptedException, ParseException
	{
		String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
		ArrayList<String> matchesToParse = parserHelper.parseMatches(leagueLinks);

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
		for (int i = 0; i < matchesToParse.size() ; i++)
		{
			if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
			{
				System.out.println("Parsing match with ID:" + matchesToParse.get(i) + ".");
				parserHelper.parseMatchById(matchesToParse.get(i), team, player, match);
				parserHelper.writeToFile("\n" + matchesToParse.get(i), "files/MatchesParsed.txt");
				Thread.sleep(20000);
			}
		}

	}
}

