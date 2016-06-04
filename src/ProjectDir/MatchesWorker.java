package ProjectDir;

import ProjectDir.Analitics.ABCAnalitics;
import ProjectDir.Analitics.GameStageAnalitics;
import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class MatchesWorker
{
	ParserFactory parserHelper = new ParserFactory();
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	WriterReaderFactory writerReaderFactory = new WriterReaderFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	MainAnaliticsFactory mainAnaliticsFactory = new MainAnaliticsFactory();
	RatingFactory ratingFactory = new RatingFactory();
	GameStageAnalitics gameStageAnalitics = new GameStageAnalitics();
	BackupFactory backupFactory=new BackupFactory();
	Integer alreadyParsedMatches = 0;

	void start_work() throws IOException, InterruptedException, ParseException
	{
		System.out.println("/----------------Patrick--------------------/");
		System.out.println("/-What's up, Boss?");
		ArrayList<KillEvent> killEventArrayList = new ArrayList<KillEvent>();
		ArrayList<BuyBackEvent> buyBackEventArrayList = new ArrayList<BuyBackEvent>();
		ArrayList<GlyphEvent> glyphEventArrayList = new ArrayList<GlyphEvent>();
		ArrayList<TowerEvent> towerEventArrayList = new ArrayList<TowerEvent>();
		ArrayList<WardEvent> wardEventArrayList = new ArrayList<WardEvent>();
		ArrayList<RoshanEvent> roshanEventArrayList = new ArrayList<RoshanEvent>();
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
		backupFactory.checkForBackUp();
		//readNewMatches(true, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList);
		ratingFactory.organizeRating();
		mainAnaliticsFactory.startWork();
		System.out.println("/-Boss, all work is done.");
	}

	void tooManyRequestsChecker(Boolean parsed) throws InterruptedException
	{
		if (parsed)
			alreadyParsedMatches++;
		if (alreadyParsedMatches == 20)
		{
			System.out.println("Okay. Let's have a rest for a while.");
			Thread.sleep(240000);
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

	void readNewMatches(boolean parse, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException, ParseException, InterruptedException
	{
		String[] leagueLinks = parserHelper.getLeagues(parserHelper.parse_html("http://www.dotabuff.com/esports/leagues"));
		ArrayList<String> leagueLinksArray = new ArrayList<String>(Arrays.asList(leagueLinks));
		leagueLinksArray.remove("4649");
		ArrayList<String> matchesFromLeagues = parserHelper.parseMatches(leagueLinksArray);
		uniqueInfoFactory.needToParseFile(matchesFromLeagues);
		ArrayList<String> matchesToParse = parserHelper.getParsingMatches();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String[] tempArray = fileOperationsFactory.readFile("files/Matches.txt").split("\n");
		String lastMatchDateString = tempArray[tempArray.length - 1].split(";")[1];
		Date lastMatchDate = formatter.parse(lastMatchDateString);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		date.setTime(lastMatchDate.getTime() - 24 * 60 * 60 * 1000);

//		parserHelper.parseMatchById(lastMatchDate, "2095305164", team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList,roshanEventArrayList);

		//if (writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList,roshanEventArrayList))
		//	fileOperationsFactory.writeToFile("1932536887", "files/MatchesParsed.txt");

		if (parse)
			for (int i = 0; i < matchesToParse.size(); i++)
			{
				if (!uniqueInfoFactory.checkIfIdAlreadyParsed(matchesToParse.get(i)))
				{
					if (parserHelper.parseMatchById(date, matchesToParse.get(i), team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList, roshanEventArrayList))
					{
						if (writerReaderFactory.writeMatchInfoToFile(player, team, match, wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList, roshanEventArrayList))
							fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/MatchesParsed.txt");
						writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList, roshanEventArrayList);
						writerReaderFactory.makeZeros(team, player, match);
						tooManyRequestsChecker(true);

					} else
					{
						tooManyRequestsChecker(false);
						fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/BrokenMatches.txt");
						writerReaderFactory.cleanArrayLists(wardEventArrayList, towerEventArrayList, killEventArrayList, glyphEventArrayList, buyBackEventArrayList, roshanEventArrayList);
						writerReaderFactory.makeZeros(team, player, match);
					}

				}
			}
		uniqueInfoFactory.removeFirstEnter("files/TemporaryMatches.txt");

		uniqueInfoFactory.makeMatchesFileClean("files/TemporaryMatches.txt");

		String newMatchesFile = fileOperationsFactory.readFile("files/TemporaryMatches.txt");

		if (!newMatchesFile.equals("\n"))
		{
			String oldMatchesFile = fileOperationsFactory.readFile("files/Matches.txt");
			String allMatchesfile = oldMatchesFile + newMatchesFile;
			fileOperationsFactory.cleanAndWriteToFile("", "files/TemporaryMatches.txt");
			fileOperationsFactory.cleanAndWriteToFile(allMatchesfile, "files/Matches.txt");
		}
		//uniqueInfoFactory.makeMatchesFileClean("files/Matches.txt");

	}
}

