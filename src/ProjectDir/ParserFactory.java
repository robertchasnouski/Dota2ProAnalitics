package ProjectDir;

import ProjectDir.MatchInfo.*;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ParserFactory
{
	class League
	{
		public String id;
		public String date;
	}

	Integer agentNum = 0;
	public int parseCounter = 0;
	Integer direRoaming = 0;
	Integer radiantRoaming = 0;
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	Integer leaguesParsed = 0;

	void tooManyRequestsChecker() throws InterruptedException
	{
		leaguesParsed++;
		if (leaguesParsed == 10)
		{
			System.out.println("League rest...");
			Thread.sleep(30000);
			Thread.sleep(0);
			leaguesParsed = 0;
		}
	}

	public Document parse_html(String html) throws IOException, InterruptedException
	{
		if (parseCounter == 100)
		{
			Thread.sleep(20000);
			//Thread.sleep(0);
			parseCounter = 0;
		}
		Document doc = new Document("");
		int numtries = 15;
		while (numtries-- != 0)
		{
			if (numtries == 10)
				Thread.sleep(200000);
			try
			{
				if (agentNum == 0)
				{
					doc = Jsoup.connect(html)
							.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
							.referrer("http://www.google.com")
							.timeout(10000).get();
					agentNum++;
				} else if (agentNum == 1)
				{
					doc = Jsoup.connect(html)
							.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.4.2661.78 Safari/537.36")
							.referrer("http://www.yandex.ru")
							.timeout(10000).get();
					agentNum++;
				} else
				{
					doc = Jsoup.connect(html)
							.userAgent("Opera/9.80 (X11; Linux i686; Ubuntu/14.10) Presto/2.12.388 Version/12.16")
							.referrer("http://www.yahoo.com/")
							.timeout(10000).get();
					agentNum = 0;
				}
				break;
			} catch (HttpStatusException e)
			{
				System.out.println("HttpStatusException." + html + ". Trying to repeat...");
				Thread.sleep(10000);
				continue;
			} catch (SocketTimeoutException e)
			{
				System.out.println("SocketTimeoutException. Trying to repeat...");
				Thread.sleep(10000);
				continue;
			}
		}
		parseCounter++;
		Thread.sleep(2300);
		return doc;
	}

	String[] getLeagues(Document doc)
	{
		Elements elems1 = doc.select("div#leagues-premium");
		Elements elems2 = doc.select("div#leagues-professional");
		elems2 = elems2.select("div.league-name");
		elems1 = elems1.select("div.league-name");
		Object[] objs1 = elems1.toArray();
		Object[] objs2 = elems2.toArray();
		Integer count1 = objs1.length;
		Integer count2 = objs2.length;
		String[] leagues1 = new String[count1];
		String[] leagues2 = new String[count2];
		int currentIndex = 0;
		int tempIndex = 1;
		for (int i = 0; i < leagues1.length; i++)
		{
			currentIndex = 0;
			leagues1[i] = objs1[i].toString();
			currentIndex = leagues1[i].indexOf("leagues/", currentIndex);
			tempIndex = leagues1[i].indexOf("\">", currentIndex);
			leagues1[i] = leagues1[i].substring(currentIndex + 8, tempIndex);
		}
		for (int i = 0; i < leagues2.length; i++)
		{
			currentIndex = 0;
			leagues2[i] = objs2[i].toString();
			currentIndex = leagues2[i].indexOf("leagues/", currentIndex);
			tempIndex = leagues2[i].indexOf("\">", currentIndex);
			leagues2[i] = leagues2[i].substring(currentIndex + 8, tempIndex);
		}
		String[] leagueLink;
		leagueLink = Stream.concat(Arrays.stream(leagues1), Arrays.stream(leagues2)).toArray(String[]::new);

		return leagueLink;
	}

	Boolean parseMatchById(Date lastMatchDate, String id, Team[] team, Player[] player, Match match) throws IOException, InterruptedException, ParseException
	{
		System.out.println("Parsing match with ID:" + id + ".");
		Document docMainPage = parse_html("http://www.dotabuff.com/matches/" + id);
		String stringMainPage = docMainPage.toString();
		if (stringMainPage.contains("This match is marked as insignificant"))
		{
			System.out.println("Parsing failed. Match insignificant");
			return false;
		}
		if (!stringMainPage.contains("Captains Mode") && !stringMainPage.contains("Captains Draft") && !stringMainPage.contains("All Pick"))
		{
			System.out.println("Parsing failed. Not CM or CD.");
			return false;
		}
		String someString = substringer(stringMainPage, "<section class=\"radiant\"", "</header>");
		if (!someString.contains("esports/teams/"))
		{
			System.out.println("Parsing failed. One of the teams are not esports team");
			return false;
		}
		someString = substringer(stringMainPage, "<section class=\"dire\"", "</header>");
		if (!someString.contains("esports/teams/"))
		{
			System.out.println("Parsing failed. One of the teams are not esports team");
			return false;
		}

		int currentIndex;
		int tempIndex;
		int currentIndex2;
		int tempIndex2;
		String tempString;
		Integer equaler = 0;


		//<editor-fold desc="MAIN PAGE PARSER">
		/***********Documentation*************/
		/** mainPageRadiantHeroLine - array(0-5, 6-empty one) of strings of each hero result(Radiant)**/
		/** mainPageRadiantTotalLine -string of total results(Radiant)**/

		/*************Variables***************/

		String mainPageRadiantTotalLine;
		String mainPageDireTotalLine;

		/*************Workspace***************/
		/**RADIANT**/

		tempString = substringer(stringMainPage, "<section class=\"radiant\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] mainPageRadiantHeroLine = tempString.split("</tr>");

		tempString = substringer(stringMainPage, "<section class=\"radiant\">", "</section>");
		mainPageRadiantTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");


		/***DIRE***/
		tempString = substringer(stringMainPage, "<section class=\"dire\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] mainPageDireHeroLine = tempString.split("</tr>");

		tempString = substringer(stringMainPage, "<section class=\"dire\">", "</section>");
		mainPageDireTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");
		//</editor-fold>

		String headerString = substringer(stringMainPage, "header-content-secondary", "</div>");


		/****PARAMETERS****/

		//<editor-fold desc="MATCH GENERAL INFO">
		tempString = substringer(stringMainPage, "<a class=\"esports-link\"", "</a>");
		String leagueNameName = removeTags(tempString);
		leagueNameName = replaceAllSeparators(leagueNameName);
		match.leagueName = leagueNameName;
		tempString = substringer(tempString, "/leagues/", "\">");
		match.leagueId = Integer.parseInt(tempString.substring(9, tempString.length()));
		if (match.leagueId == 4649)
		{
			System.out.println("Wrong league");
			return false;
		}
		//</editor-fold>

		//<editor-fold desc="TEAM GENERAL INFO">
		//Winteam
		String winTeam;
		currentIndex = stringMainPage.indexOf("match-result");
		tempIndex = stringMainPage.indexOf("</div>", currentIndex);
		winTeam = stringMainPage.substring(currentIndex, tempIndex);
		if (winTeam.contains("team dire"))
			match.winRadiant = false;
		else
			match.winRadiant = true;
		//Side
		team[0].side = 0;
		team[1].side = 1;
		//RadiantTeamName
		String teamName;
		String teamId;
		tempString = substringer(stringMainPage, "<section class=\"radiant\"", "</header>");

		teamId = substringer(tempString, "esports/teams/", "\">");
		teamName = substringer(tempString, "title=", " class");
		teamId = teamId.replaceAll("esports/teams/", "");
		teamName = teamName.replaceAll("title=", "");
		teamName = teamName.replaceAll("\"", "");
		teamName = teamName.replaceAll("\\;", "");
		teamName = teamName.replaceAll(";", "");
		teamName = teamName.replaceAll("&", "");
		teamName = teamName.replaceAll("\\&", "");
		teamName = replaceAllSeparators(teamName);
		team[0].id = teamId;
		team[0].name = teamName;

		//DireTeamName
		tempString = substringer(stringMainPage, "<section class=\"dire\"", "</header>");
		teamId = substringer(tempString, "esports/teams/", "\">");
		teamName = substringer(tempString, "title=", " class");
		teamId = teamId.replaceAll("esports/teams/", "");
		teamName = teamName.replaceAll("title=", "");
		teamName = teamName.replaceAll("\"", "");
		teamName = teamName.replaceAll(";", "");
		teamName = replaceAllSeparators(teamName);
		team[1].id = teamId;
		team[1].name = teamName;
		//</editor-fold>

		//<editor-fold desc="PLAYER ID">
		for (int i = 0; i < 5; i++)
		{
			if (mainPageRadiantHeroLine[i].indexOf("href=\"/esports/players/") != -1)
			{
				tempString = substringer(mainPageRadiantHeroLine[i], "href=\"/esports/players/", "\">");
				tempString = tempString.replaceAll("href=\"/esports/players/", "");
				player[i].playerId = tempString;
			} else
			{
				tempString = substringer(mainPageRadiantHeroLine[i], "href=\"/players/", "\">");
				tempString = tempString.replaceAll("href=\"/players/", "");
				player[i].playerId = tempString;
			}
		}
		for (int i = 0; i < 5; i++)
		{
			if (mainPageDireHeroLine[i].indexOf("href=\"/esports/players/") != -1)
			{
				tempString = substringer(mainPageDireHeroLine[i], "href=\"/esports/players/", "\">");
				tempString = tempString.replaceAll("href=\"/esports/players/", "");
				player[i + 5].playerId = tempString;
			} else
			{
				tempString = substringer(mainPageDireHeroLine[i], "href=\"/players/", "\">");
				tempString = tempString.replaceAll("href=\"/players/", "");
				player[i + 5].playerId = tempString;
			}
		}

		//</editor-fold>

		//<editor-fold desc="GENERAL INFO:    ProjectDir.MatchInfo.Player: Hero,Side,K D A,totalGold,HH,HD,LH,DN,Item[6],Level;">
		//<editor-fold desc="RADIANT GENERAL INFO">
		/**RADIANT**/
		for (int i = 0; i < mainPageRadiantHeroLine.length - 1; i++)
		{
			/**HeroName**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<a href=\"/heroes", "</a>");
			tempString = substringer(tempString, "title=", "data");
			tempString = tempString.replaceAll("title=", "");
			tempString = tempString.replaceAll("\"", "");
			tempString = tempString.substring(0, tempString.length() - 1);
			player[i].hero = tempString;
			player[i].side = 1;
			/**Kills, Deaths, Assists**/
			//System.out.println("LINE:"+mainPageRadiantHeroLine[i]+"\n\n\n\n");
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"tf-r r-tab r-group-1\">", "<td class=\"tf-r r-tab r-group-1 color-stat-gold\">");
			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(" ", "");
			String[] radiantHeroKDA = tempString.split("\n");

			for (int j = 0; j < 3; j++)
			{
				if (radiantHeroKDA[j].contains("-"))
				{
					radiantHeroKDA[j] = "0";
				}

			}
			player[i].kills = Integer.parseInt(radiantHeroKDA[0]);
			player[i].deaths = Integer.parseInt(radiantHeroKDA[1]);
			player[i].assists = Integer.parseInt(radiantHeroKDA[2]);

			/**HeroHeal, HeroDamage, towerDamage, totalXPM, totalGPM**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"tf-r r-tab r-group-2 cell-minor\">", "<span class=\"color-item-observer");

			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(" ", "");
			String[] table = tempString.split("\n");
			for (int j = 0; j < table.length; j++)
			{
				if (table[j].contains("k"))
				{
					table[j] = table[j].replaceAll("k", "");
					table[j] = table[j].replaceAll("\\.", "");
					table[j] = Integer.parseInt(table[j]) * 100 + "";
				}
				if (table[j].contains("-"))
					table[j] = "0";
			}
			player[i].totalXPM = Integer.parseInt(table[5]);
			player[i].totalGPM = Integer.parseInt(table[3]);
			player[i].heroDamage = Integer.parseInt(table[6]);
			player[i].heroHeal = Integer.parseInt(table[7]);
			player[i].towerDamage = Integer.parseInt(table[8]);
		}
		//</editor-fold>
		//<editor-fold desc="DIRE GENERAL INFO">
		/**RADIANT**/
		for (int i = 0; i < mainPageDireHeroLine.length - 1; i++)
		{
			/**HeroName**/
			tempString = substringer(mainPageDireHeroLine[i], "<a href=\"/heroes", "</a>");
			tempString = substringer(tempString, "title=", "data");
			tempString = tempString.replaceAll("title=", "");
			tempString = tempString.replaceAll("\"", "");
			tempString = tempString.substring(0, tempString.length() - 1);
			player[i + 5].hero = tempString;
			player[i + 5].side = 1;
			/**Kills, Deaths, Assists**/
			//System.out.println("LINE:"+mainPageRadiantHeroLine[i]+"\n\n\n\n");
			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"tf-r r-tab r-group-1\">", "<td class=\"tf-r r-tab r-group-1 color-stat-gold\">");
			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(" ", "");
			String[] direHeroKDA = tempString.split("\n");

			for (int j = 0; j < 3; j++)
			{
				if (direHeroKDA[j].contains("-"))
				{
					direHeroKDA[j] = "0";
				}

			}
			player[i + 5].kills = Integer.parseInt(direHeroKDA[0]);
			player[i + 5].deaths = Integer.parseInt(direHeroKDA[1]);
			player[i + 5].assists = Integer.parseInt(direHeroKDA[2]);

			/**HeroHeal, HeroDamage, towerDamage, totalXPM, totalGPM**/

			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"tf-r r-tab r-group-2 cell-minor\">", "<span class=\"color-item-observer");
			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(" ", "");
			String[] table = tempString.split("\n");
			for (int j = 0; j < table.length; j++)
			{
				if (table[j].contains("k"))
				{
					table[j] = table[j].replaceAll("k", "");
					table[j] = table[j].replaceAll("\\.", "");
					table[j] = Integer.parseInt(table[j]) * 100 + "";
				}
				if (table[j].contains("-"))
					table[j] = "0";
			}
			player[i + 5].totalXPM = Integer.parseInt(table[5]);
			player[i + 5].totalGPM = Integer.parseInt(table[3]);
			player[i + 5].heroDamage = Integer.parseInt(table[6]);
			player[i + 5].heroHeal = Integer.parseInt(table[7]);
			player[i + 5].towerDamage = Integer.parseInt(table[8]);
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GENERAL INFO[1]: ProjectDir.MatchInfo.Player: %KPart, ProjectDir.MatchInfo.Team: KDA, %KPart">
		//<editor-fold desc="RADIANT">
		/**RADIANT**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		tempString = substringer(mainPageRadiantTotalLine, "<td class=\"tf-r r-tab r-group-1\">", "<td class=\"tf-r r-tab r-group-1 color-stat-gold\">");
		tempString = removeTags(tempString);
		tempString = tempString.replaceAll(" ", "");
		String[] team1KDA = tempString.split("\n");
		for (int i = 0; i < team1KDA.length; i++)
		{
			if (team1KDA[i].contains("-"))
				team1KDA[i] = "0";
		}
		team[0].kills = Integer.parseInt(team1KDA[0]);
		team[0].deaths = Integer.parseInt(team1KDA[1]);
		team[0].assists = Integer.parseInt(team1KDA[2]);

		//</editor-fold>
		//<editor-fold desc="DIRE">
		/**DIRE**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		tempString = substringer(mainPageDireTotalLine, "<td class=\"tf-r r-tab r-group-1\">", "<td class=\"tf-r r-tab r-group-1 color-stat-gold\">");
		tempString = removeTags(tempString);
		tempString = tempString.replaceAll(" ", "");
		String[] team2KDA = tempString.split("\n");
		for (int i = 0; i < team2KDA.length; i++)
		{
			if (team2KDA[i].contains("-"))
				team2KDA[i] = "0";
		}
		team[1].kills = Integer.parseInt(team2KDA[0]);
		team[1].deaths = Integer.parseInt(team2KDA[1]);
		team[1].assists = Integer.parseInt(team2KDA[2]);
		//</editor-fold>
		//</editor-fold>

		match.universalX = 0;

		//<editor-fold desc="TIME: Match: matchTime, FBTime, F10KTime;">

		tempString = substringer(headerString, "Region", "</dd>");

		tempString = removeTags(tempString);

		tempString = tempString.replaceAll("Region", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		match.matchTime = timeToMinutes(tempString);

		//</editor-fold>

		//<editor-fold desc="OBJECTIVES:     ProjectDir.MatchInfo.Player: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;  ProjectDir.MatchInfo.Team: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;">


		//</editor-fold>

		//<editor-fold desc="MATCH GENERAL INFO">
		match.team1Id = team[0].id;
		match.team2Id = team[1].id;
		//MatchID
		tempString = substringer(stringMainPage, "<div class=\"header-content-title\"", "<small>");

		tempString = removeTags(tempString);

		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("Match", "");
		match.id = tempString;
		//Date
		tempString =

				substringer(stringMainPage, "datetime=\"", "title");

		tempString = tempString.substring(10, 20);
		match.date = tempString;
		Date matchDate = formatter.parse(match.date);
		if (lastMatchDate.compareTo(matchDate) == 1)

		{
			System.out.println("Parsing error. Too early date");
			return false;
		}
		//</editor-fold>

		//<editor-fold desc="TEAM RATINGS">
		team[0].rating = 1000;
		team[1].rating = 1000;
		//</editor-fold>

		return true;
	}

	ArrayList<String> parseMatches(ArrayList<String> leagueLinks) throws IOException, InterruptedException, ParseException
	{
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH");
		Document doc;
		Date date = new Date();
		String tempString;
		String html;
		String parseDate;

		/***********Here is parsing date.*********/
		String currentDate = ft.format(date);
		Document[] docs = new Document[leagueLinks.size()];
		ArrayList<String> leaguesToParse = new ArrayList<>();
		ArrayList<String> matchesToParse = new ArrayList<>();
		leaguesToParse = uniqueInfoFactory.checkIfLeaguesMustBeParsed(leagueLinks);

		for (int i = 0; i < leaguesToParse.size(); i++)
		{
			System.out.println("Read " + leaguesToParse.get(i) + " league.");
			docs[i] = parse_html("http://www.dotabuff.com/esports/leagues/" + leaguesToParse.get(i) + "/matches");
			html = docs[i].toString();
			//CHECK first match date
			String lastDate = substringer(html, "data-time-ago", "</time");
			lastDate = lastDate.substring(lastDate.indexOf(">") + 1, lastDate.length());
			Date nowDate = Calendar.getInstance().getTime();
			Date lastMatchDate = formatter.parse(lastDate);
			long diff = nowDate.getTime() - lastMatchDate.getTime();
			long hours = TimeUnit.MILLISECONDS.toHours(diff);
			if (hours >= 24 * 30)
			{
				System.out.println("There is no new matches. Continue...");
				continue;
			}

			html = substringer(html, "<tbody>", "</tbody>");
			if (docs[i].toString().contains("pagination"))
			{
				String navigation = substringer(docs[i].toString(), "<nav class=\"pagination", "</nav>");
				navigation = substringer(navigation, "\"last\"", ">Last");
				navigation = substringer(navigation, "page=", "\"");
				navigation = navigation.substring(5, navigation.length());
				String temp = "";
				Integer howMuchPages = 0;
				if (Integer.parseInt(navigation) > 5)
					howMuchPages = 5;
				else
					howMuchPages = Integer.parseInt(navigation);
				for (int j = 2; j <= howMuchPages; j++)
				{
					docs[i] = parse_html("http://www.dotabuff.com/esports/leagues/" + leaguesToParse.get(i) + "/matches?page=" + j);
					temp = docs[i].toString();
					temp = substringer(temp, "<tbody>", "</tbody");
					html = html + temp;
				}
			}
			String[] matchInLeague = html.split("</tr>");
			for (int j = 0; j < matchInLeague.length; j++)
			{
				if (matchInLeague[j].contains("<a href=\"/matches/"))
				{
					tempString = substringer(matchInLeague[j], "<a href=\"/matches/", "</a>");
					tempString = removeTags(tempString);
					String timeChecker = substringer(matchInLeague[j], "<time", "title");
					timeChecker = timeChecker.substring(16, 32);
					timeChecker = timeChecker.replaceAll("T", " ");
					if (calculateIfMatchCanBeParsed(timeChecker))
						matchesToParse.add(tempString);
				}
			}
			tooManyRequestsChecker();
		}
		return matchesToParse;
	}

	ArrayList<String> getParsingMatches() throws IOException
	{
		String needToParseString = fileOperationsFactory.readFile("files/NeedToParse.txt");
		String[] match = needToParseString.split("\n");
		ArrayList<String> matchesToParse = new ArrayList<String>();
		for (int i = 0; i < match.length; i++)
		{
			if (!match[i].equals(""))
				matchesToParse.add(match[i]);
		}
		return matchesToParse;
	}

	String substringer(String input, String begin, String end)
	{
		String temp;
		temp = input.substring(input.indexOf(begin), input.indexOf(end, input.indexOf(begin)));
		return temp;
	}

	String html2text(String html)
	{
		return Jsoup.parse(html).text();
	}

	String removeTag(String html, String redex)
	{
		html = html.replaceAll("[<](/)?" + redex + "[^>]*[>]", "");
		return html;
	}

	String removeTags(String html)
	{
		html = html.replaceAll("[<](/)?[^>]*[>]", "");
		return html;
	}

	Integer calculateTimeDifference(String date1, String date2)
	{
		int difference = 0;
		if (date1.length() != 8 && date2.length() != 8)
		{
			String[] sepDate1 = date1.split(":");
			String[] sepDate2 = date2.split(":");
			if (!date1.contains("-") && !date2.contains("-"))
			{
				difference = (Integer.parseInt(sepDate2[0]) * 60 + Integer.parseInt(sepDate2[1])) - (Integer.parseInt(sepDate1[0]) * 60 + Integer.parseInt(sepDate1[1]));
			} else
			{
				if (date1.contains("-") && date2.contains("-"))
				{
					date1 = date1.replaceAll("-", "");
					date2 = date2.replaceAll("-", "");
					String[] sepDates1 = date1.split(":");
					String[] sepDates2 = date2.split(":");
					difference = (Integer.parseInt(sepDates1[0]) * 60 + Integer.parseInt(sepDates1[1])) - (Integer.parseInt(sepDates2[0]) * 60 + Integer.parseInt(sepDates2[1]));
				} else if (!date2.contains("-") && date1.contains("-"))
				{
					date1 = date1.replaceAll("-", "");
					String[] sepDates1 = date1.split(":");
					String[] sepDates2 = date2.split(":");
					difference = (Integer.parseInt(sepDates1[0]) * 60 + Integer.parseInt(sepDates1[1])) + (Integer.parseInt(sepDates2[0]) * 60 + Integer.parseInt(sepDates2[1]));
				} else
					difference = 420;
			}
		} else
		{
			if (date1.length() == 8 && date2.length() == 8)
			{
				String[] sepDate1 = date1.split(":");
				String[] sepDate2 = date2.split(":");
				difference = (Integer.parseInt(sepDate2[1]) * 60 + Integer.parseInt(sepDate2[2])) - (Integer.parseInt(sepDate1[1]) * 60 + Integer.parseInt(sepDate1[2]));
			} else
			{
				String[] sepDate1 = date1.split(":");
				String[] sepDate2 = date2.split(":");
				difference = (3600 + Integer.parseInt(sepDate2[1]) * 60 + Integer.parseInt(sepDate2[2])) - (Integer.parseInt(sepDate1[0]) * 60 + Integer.parseInt(sepDate1[1]));
			}
		}
		return difference;
	}

	Integer mapTimeToSeconds(String time)
	{
		if (time.contains("-"))
		{
			if (time.length() == 6)
			{
				time = time.replaceAll("-", "");
				String[] tempMapTimeArray = time.split(":");
				return Integer.parseInt(tempMapTimeArray[0]) * 60 + Integer.parseInt(tempMapTimeArray[1]) * (-1);
			} else
			{
				time = time.replaceAll("-", "");
				String[] tempMapTimeArray = time.split(":");
				return Integer.parseInt(tempMapTimeArray[0]) * 3600 + Integer.parseInt(tempMapTimeArray[1]) * 60 + Integer.parseInt(tempMapTimeArray[2]) * (-1);
			}
		} else
		{
			if (time.length() == 5)
			{
				String[] tempMapTimeArray = time.split(":");
				return Integer.parseInt(tempMapTimeArray[0]) * 60 + Integer.parseInt(tempMapTimeArray[1]);
			} else
			{
				String[] tempMapTimeArray = time.split(":");
				return Integer.parseInt(tempMapTimeArray[0]) * 3600 + Integer.parseInt(tempMapTimeArray[1]) * 60 + Integer.parseInt(tempMapTimeArray[2]);
			}
		}
	}

	Integer timeToMinutes(String time)
	{
		if (time.length() == 5)
		{
			String[] tempMapTimeArray = time.split(":");
			return Integer.parseInt(tempMapTimeArray[0]);

		} else
		{
			String[] tempMapTimeArray = time.split(":");
			return Integer.parseInt(tempMapTimeArray[0]) * 60 + Integer.parseInt(tempMapTimeArray[1]);

		}

	}

	Boolean calculateIfMatchCanBeParsed(String time) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date patchUpBorder = format.parse("2016-12-15 00:00");
		Date d1 = null;
		Date d2 = null;
		try
		{
			d1 = Calendar.getInstance().getTime();
			d2 = format.parse(time);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		long diff = d1.getTime() - d2.getTime();
		long hours = TimeUnit.MILLISECONDS.toHours(diff);
		if (d2.after(patchUpBorder))
		{
			if (hours >= 2 && hours <= 700)
				return true;
			else
				return false;
		} else return false;
	}

	public String replaceAllSeparators(String string)
	{
		string = string.replaceAll("#", "");
		string = string.replaceAll("\"", "");
		string = string.replaceAll("\\*", "");
		string = string.replaceAll("\\|", "");
		string = string.replaceAll(";", "");
		string = string.replaceAll("\\;", "");
		return string;
	}
}




