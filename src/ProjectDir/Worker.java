package ProjectDir;

import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//TODO: Phase 1: Make full updatable and workable parse system
////TODO: Check error matches and check if they are in parsed matches
////TODO: Test Writing and reading the same information
////TODO: Why dublicates appear?
////
//TODO: Phase 2: Prototype analizing
////TODO: GameMode can be also AP because its reprick and regame
//TODO: Phase 3: Advanced analizing

public class Worker
{
	ParserFactory parserHelper = new ParserFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	MainAnaliticsFactory mainAnaliticsFactory = new MainAnaliticsFactory();
	RatingFactory ratingFactory = new RatingFactory();

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

		//checkIfTemporaryFileIsClean();
		readNewMatches(true, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

		//ratingFactory.organizeRating();
		//mainAnaliticsFactory.startWork();
	}


	void tooManyRequestsChecker() throws InterruptedException
	{
		alreadyParsedMatches++;
		if (alreadyParsedMatches == 20)
		{
			System.out.println("Okay. Let's have a rest for a while.");
			Thread.sleep(500000);
			alreadyParsedMatches = 0;
		}
	}

	void checkIfTemporaryFileIsClean() throws IOException
	{
		String tempFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
		String oldFile = fileOperationsFactory.readFile("files/Matches.txt");
		if (!tempFile.equals(""))
		{
			uniqueInfoFactory.makeMatchesFileClean("files/TemporaryMatches.txt");
			tempFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
			fileOperationsFactory.cleanAndWriteToFile(oldFile + tempFile, "files/Matches.txt");
		}
		fileOperationsFactory.cleanAndWriteToFile("", "files/TemporaryMatches.txt");
	}

	void readNewMatches(boolean parse, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException, ParseException, InterruptedException
	{
		String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
		ArrayList<String> matchesFromLeagues = parserHelper.parseMatches(leagueLinks);
		uniqueInfoFactory.needToParseFile(matchesFromLeagues);
		ArrayList<String> matchesToParse = parserHelper.getParsingMatches();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String[] tempArray = fileOperationsFactory.readFile("files/Matches.txt").split("\n");
//		String lastMatchDateString=tempArray[tempArray.length-1].split(";")[1];
		Date lastMatchDate = formatter.parse("2013-05-12"/**lastMatchDateString**/);

		/*
			if (parserHelper.parseMatchById(lastMatchDate, "2173255322", team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList))
				writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
			writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
			writerReaderFactory.makeZeros(team, player, match);
		*/
		if (parse)
			for (int i = 0; i < matchesToParse.size(); i++)
			{
				if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
				{
					if (parserHelper.parseMatchById(lastMatchDate, matchesToParse.get(i), team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList))
					{
						if (writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList))
							fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/MatchesParsed.txt");
						for (int j = 0; j <10 ; j++)
						{
							System.out.println("ROLE:"+player[j].role);
						}
						writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
						writerReaderFactory.makeZeros(team, player, match);
					} else
					{
						fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/BrokenMatches.txt");
						writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList);
						writerReaderFactory.makeZeros(team, player, match);
					}
					tooManyRequestsChecker();
				}
			}
		//uniqueInfoFactory.makeMatchesFileClean("files/Matches.txt");
		String newMatchesFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");
		String oldMatchesFile = fileOperationsFactory.readFile("files/Matches.txt");
		String allMatchesfile = oldMatchesFile + newMatchesFile;
		fileOperationsFactory.cleanAndWriteToFile("", "files/TemporaryMatches.txt");
		fileOperationsFactory.cleanAndWriteToFile(allMatchesfile, "files/Matches.txt");
	}
}
