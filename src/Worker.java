import MatchInfo.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Worker
{
	ParserFactory parserHelper = new ParserFactory();
	AnalizingFactory analizingFactory = new AnalizingFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	StatisticsFactory statisticsFactory = new StatisticsFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
	Scanner scanner = new Scanner(System.in);

	void start_work() throws IOException, InterruptedException, ParseException
	{
		ArrayList<KillEvent> killEventArrayList = new ArrayList<KillEvent>();
		ArrayList<BuyBackEvent> buyBackEventArrayList = new ArrayList<BuyBackEvent>();
		ArrayList<GlyphEvent> glyphEventArrayList = new ArrayList<GlyphEvent>();
		ArrayList<TowerEvent> towerEventArrayList = new ArrayList<TowerEvent>();
		ArrayList<WardEvent> wardEventArrayList = new ArrayList<WardEvent>();
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

		String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
		ArrayList<String> matchesToParse = parserHelper.parseMatches(leagueLinks);

		//TODO: Phase 1: Make full updatable and workable parse system
		//TODO: End of 1 phase

		//TODO: Phase 2: Prototype analizing
		//TODO: Phase 3: Advanced analizing

		/*
			1. Items not null.
			2. players=10
			3.
		 */

		for (int i = 0; i < matchesToParse.size(); i++)
		{
			if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
			{
				parserHelper.parseMatchById(matchesToParse.get(i), team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
				writerReaderFactory.writeMatchTestInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
				fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/MatchesParsed.txt");
				writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
			}
		}

	}
}

