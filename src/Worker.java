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

	void start_work() throws IOException, InterruptedException, ParseException
	{
		String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
	//	ArrayList<String> matchesToParse = parserHelper.parseMatches(leagueLinks);

		Match match = new Match();
		Player[] player = new Player[10];
		Team[] team = new Team[2];
		//TODO: Add BuyBackEvent, WardEvent, TowerEvent,GlyphEvent,KillEvent
		for (int i = 0; i < 10; i++)
		{
			player[i] = new Player();
		}
		for (int i = 0; i < 2; i++)
		{
			team[i] = new Team();
		}
		parserHelper.parseMatchById("2125768709", team, player, match);
		/*for (int i = 0; i < matchesToParse.size() ; i++)
		{
			if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
			{
				System.out.println("Parsing match with ID:" + matchesToParse.get(i) + ".");
				parserHelper.parseMatchById(matchesToParse.get(i), team, player, match);
				parserHelper.writeToFile("\n" + matchesToParse.get(i), "files/MatchesParsed.txt");
			}
		}*/

	}
}

