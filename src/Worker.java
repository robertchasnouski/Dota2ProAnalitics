import MatchInfo.*;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Worker
{
	ParserFactory parserHelper = new ParserFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	StatisticsFactory statisticsFactory = new StatisticsFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	MainAnaliticsFactory mainAnaliticsFactory = new MainAnaliticsFactory();

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
		//TODO: Phase 2: Prototype analizing
		//TODO: Make Match ID in Matches.txt file unique
		//TODO: Going through one parameter tests
		//TODO: Phase 3: Advanced analizing

		//parserHelper.parseMatchById("2147302916", team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//writerReaderFactory.writeMatchTestInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);

		for (int i = 0; i < matchesToParse.size(); i++)
		{
			if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
			{
				if (parserHelper.parseMatchById(matchesToParse.get(i), team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList))
				{
					writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
					fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/MatchesParsed.txt");
					writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
					writerReaderFactory.makeZeros(team, player, match);
				}
			}
		}
		mainAnaliticsFactory.startWork();
	}
}

