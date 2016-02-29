import MatchInfo.*;

import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: Phase 1: Make full updatable and workable parse system
////TODO: Going through one parameter tests
////TODO: Check error matches
//TODO: Phase 2: Prototype analizing
////TODO: Rating !Priority!
/////TODO: Rating system +/-
////TODO: GameMode can be also AP becouse its reprick and regame
//TODO: Phase 3: Advanced analizing

public class Worker
{
	ParserFactory parserHelper = new ParserFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	StatisticsFactory statisticsFactory = new StatisticsFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	MainAnaliticsFactory mainAnaliticsFactory = new MainAnaliticsFactory();
	Integer alreadyParsedMatches = 0;

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
		/*String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
		ArrayList<String> matchesFromLeagues = parserHelper.parseMatches(leagueLinks);
		uniqueInfoFactory.needToParseFile(matchesFromLeagues);
		ArrayList<String> matchesToParse = parserHelper.getParsingMatches();*/
		//parserHelper.parseMatchById("2147302916", team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);
		//writerReaderFactory.writeMatchTestInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);

		/*for (int i = 0; i < matchesToParse.size(); i++)
		{
			if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
			{
				if (parserHelper.parseMatchById(matchesToParse.get(i), team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList))
				{
					writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
					fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/MatchesParsed.txt");
					writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
					writerReaderFactory.makeZeros(team, player, match);
				} else
				{
					writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
					writerReaderFactory.makeZeros(team, player, match);
				}
				tooManyRequestsChecker();
			}
		}*/
		uniqueInfoFactory.makeMatchesFileClean();
		mainAnaliticsFactory.startWork();
	}


	void tooManyRequestsChecker() throws InterruptedException
	{
		alreadyParsedMatches++;
		if (alreadyParsedMatches == 30)
		{
			System.out.println("Okay. Let's have a rest for a while.");
			Thread.sleep(300000);
			alreadyParsedMatches = 0;
		}
	}
}

