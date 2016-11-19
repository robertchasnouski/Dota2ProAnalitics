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
	Integer direRoaming = 0;
	Integer radiantRoaming = 0;
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	public int parseCounter = 0;
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
		if (parseCounter == 50)
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

	Boolean parseMatchById(Date lastMatchDate, String id, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException, InterruptedException, ParseException
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
		if (!stringMainPage.contains("Kills") || !stringMainPage.contains("Objectives"))
		{
			System.out.println("Parsing failed. There is no kills page.");
			return false;
		}
		/*********DOCUMENTS,PAGE STRINGS**********/
		//<editor-fold desc="DOCUMENTS">

		Document docKillsPage = parse_html("http://www.dotabuff.com/matches/" + id + "/kills");
		Document docFarmPage = parse_html("http://www.dotabuff.com/matches/" + id + "/farm");
		Document docLogPage = parse_html("http://www.dotabuff.com/matches/" + id + "/log");
		if (docKillsPage.toString().equals("") || docFarmPage.toString().equals("") || docLogPage.toString().equals(""))
			return false;
		//</editor-fold>

		//<editor-fold desc="STRINGS">
		String stringKillsPage = docKillsPage.toString();
		String stringFarmPage = docFarmPage.toString();
		//</editor-fold>
		/*************GLOBAL CREATION ************/
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

		//<editor-fold desc="KILLS PAGE PARSER">
		/***********Documentation*************/
		/** killsPageRadiantHeroLine - array(0-5, 6-empty one) of strings of each hero result(Radiant)**/
		/** killsPageRadiantTotalLine -  string of total results(Radiant)**/
		/** killsMap - **/
		/*************Variables***************/

		String killsPageRadiantTotalLine;
		String killsPageDireTotalLine;
		String killsMap;
		/*************Workspace***************/
		/**GLOBAL**/
		/**KillsMap**/
		currentIndex = stringKillsPage.indexOf("time-clock");
		tempIndex = stringKillsPage.indexOf("</article>", currentIndex);
		tempString = stringKillsPage.substring(currentIndex, tempIndex);
		killsMap = tempString;

		/**RADIANT**/
		tempString = substringer(stringKillsPage, "<section class=\"radiant\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] killsPageRadiantHeroLine = tempString.split("</tr>");

		tempString = substringer(stringKillsPage, "<section class=\"radiant\">", "</section>");
		killsPageRadiantTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");

		/***DIRE***/
		tempString = substringer(stringKillsPage, "<section class=\"dire\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] killsPageDireHeroLine = tempString.split("</tr>");

		tempString = substringer(stringKillsPage, "<section class=\"dire\">", "</section>");
		killsPageDireTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");

		//</editor-fold>

		//<editor-fold desc="FARM PAGE PARSER">
		/***********Documentation*************/
		/** farmPageRadiantHeroLine - array(0-5, 6-empty one) of strings of each hero result(Radiant)**/
		/** farmPageRadiantTotalLine -  string of total results(Radiant)**/

		/*************Variables***************/

		String farmPageRadiantTotalLine;
		String farmPageDireTotalLine;
		/*************Workspace***************/
		/**GLOBAL**/

		/**RADIANT**/
		tempString = substringer(stringFarmPage, "<section class=\"radiant\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] farmPageRadiantHeroLine = tempString.split("</tr>");

		tempString = substringer(stringFarmPage, "<section class=\"radiant\">", "</section>");
		farmPageRadiantTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");
		/***DIRE***/
		tempString = substringer(stringFarmPage, "<section class=\"dire\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] farmPageDireHeroLine = tempString.split("</tr>");

		tempString = substringer(stringFarmPage, "<section class=\"dire\">", "</section>");
		farmPageDireTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");

		//</editor-fold>

		//<editor-fold desc="LOG PAGE PARSER">
		/***********Documentation*************/
		/*logLine- array of log lines*/
		/*************Variables***************/

		/*************Workspace***************/

		Elements elemsLogPage = docLogPage.select("div.line");
		Object[] objsLog = elemsLogPage.toArray();
		String[] logLine = new String[objsLog.length];
		for (int i = 0; i < objsLog.length; i++)
		{
			logLine[i] = objsLog[i].toString();
		}
		//</editor-fold>

		//<editor-fold desc="HEADER PAGE PARSER">
		/***********Documentation*************/
		/*logLine- array of log lines*/
		/*************Variables***************/

		/*************Workspace***************/

		String headerString = substringer(stringMainPage, "header-content-secondary", "</div>");

		//</editor-fold>

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

		team[0].totalLevel = 0;
		team[1].totalLevel = 0;
		//</editor-fold>

		//<editor-fold desc="PLAYER ID">
		for (int i = 0; i < 5; i++)
		{
			tempString = substringer(mainPageRadiantHeroLine[i], "href=\"/players/", "\">");
			tempString = tempString.replaceAll("href=\"/players/", "");
			player[i].playerId = tempString;
		}
		for (int i = 0; i < 5; i++)
		{
			tempString = substringer(mainPageDireHeroLine[i], "href=\"/players/", "\">");
			tempString = tempString.replaceAll("href=\"/players/", "");
			player[i + 5].playerId = tempString;
		}

		//</editor-fold>

		//<editor-fold desc="GENERAL INFO:    ProjectDir.MatchInfo.Player: Hero,Side,K D A,totalGold,HH,HD,LH,DN,Item[6],Level;">
		//<editor-fold desc="RADIANT GENERAL INFO">
		/**RADIANT**/
		for (int i = 0; i < mainPageRadiantHeroLine.length - 1; i++)
		{
			player[i].level = 0;
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
			player[i].totalLH = Integer.parseInt(table[0]);
			player[i].totalXPM = Integer.parseInt(table[5]);
			player[i].totalGPM = Integer.parseInt(table[3]);
			player[i].heroDamage = Integer.parseInt(table[6]);
			player[i].heroHeal = Integer.parseInt(table[7]);
			player[i].towerDamage = Integer.parseInt(table[8]);
			/**Items**/
			/*tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-4\">", "</td>");

			tempString = tempString.replaceAll("<td class=\"r-tab r-group-4\">", "");
			String[] heroRadiantItems = tempString.split("<div class=\"match-item>");
			int howMuchItems = 0;
			if (heroRadiantItems.length > 6)
				howMuchItems = 6;
			else
				howMuchItems = heroRadiantItems.length;
			for (int j = 0; j < howMuchItems; j++)
			{
				if (heroRadiantItems[j].contains("title"))
				{
					heroRadiantItems[j] = substringer(heroRadiantItems[j], "title=\"", "data");
					heroRadiantItems[j] = heroRadiantItems[j].replaceAll("title=", "");
					heroRadiantItems[j] = heroRadiantItems[j].replaceAll("\"", "");
					heroRadiantItems[j] = heroRadiantItems[j].substring(0, heroRadiantItems[j].length() - 1);
					player[i].item[j] = heroRadiantItems[j];
				} else
					player[i].item[j] = "nottoparse";
			}*/
			for (int j = 0; j < 6; j++)
				player[i].item[j] = "nottoparse";
		}
		//</editor-fold>
		//<editor-fold desc="DIRE GENERAL INFO">
		/**RADIANT**/
		for (int i = 0; i < mainPageDireHeroLine.length - 1; i++)
		{
			player[i].level = 0;
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
			player[i + 5].totalLH = Integer.parseInt(table[0]);
			player[i + 5].totalXPM = Integer.parseInt(table[5]);
			player[i + 5].totalGPM = Integer.parseInt(table[3]);
			player[i + 5].heroDamage = Integer.parseInt(table[6]);
			player[i + 5].heroHeal = Integer.parseInt(table[7]);
			player[i + 5].towerDamage = Integer.parseInt(table[8]);
			/**Items**/
			/*tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-4\">", "</td>");

			tempString = tempString.replaceAll("<td class=\"r-tab r-group-4\">", "");
			String[] heroRadiantItems = tempString.split("<div class=\"match-item>");
			int howMuchItems = 0;
			if (heroRadiantItems.length > 6)
				howMuchItems = 6;
			else
				howMuchItems = heroRadiantItems.length;
			for (int j = 0; j < howMuchItems; j++)
			{
				if (heroRadiantItems[j].contains("title"))
				{
					heroRadiantItems[j] = substringer(heroRadiantItems[j], "title=\"", "data");
					heroRadiantItems[j] = heroRadiantItems[j].replaceAll("title=", "");
					heroRadiantItems[j] = heroRadiantItems[j].replaceAll("\"", "");
					heroRadiantItems[j] = heroRadiantItems[j].substring(0, heroRadiantItems[j].length() - 1);
					player[i].item[j] = heroRadiantItems[j];
				} else
					player[i].item[j] = "nottoparse";
			}*/
			for (int j = 0; j < 6; j++)
				player[i + 5].item[j] = "nottoparse";
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GENERAL INFO[1]: ProjectDir.MatchInfo.Player: %KPart, ProjectDir.MatchInfo.Team: KDA, %KPart">
		//<editor-fold desc="RADIANT">
		/**RADIANT**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		team[0].partisipation = 0;
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
		for (int i = 0; i < killsPageRadiantHeroLine.length - 1; i++)
		{
			player[i].partisipation = 0;
		}
		//</editor-fold>
		//<editor-fold desc="DIRE">
		/**DIRE**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		team[1].partisipation = 0;
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
		for (int i = 0; i < killsPageDireHeroLine.length - 1; i++)
		{
			player[i + 5].partisipation = 0;
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GENERAL INFO[2]:  ProjectDir.MatchInfo.Team: HH,HD,LH,DN ">
		//<editor-fold desc="Radiant GlobalInfo">

		team[0].totalLH = 0;
		team[0].totalDenies = 0;
		team[0].totalXPM = 0;
		team[0].totalGPM = 0;
		team[0].heroDamage = 0;
		team[0].heroHeal = 0;
		//</editor-fold>
		//<editor-fold desc="Dire GlobalInfo">
		team[1].totalLH = 0;
		team[1].totalDenies = 0;
		team[1].totalXPM = 0;
		team[1].totalGPM = 0;
		team[1].heroDamage = 0;
		team[1].heroHeal = 0;
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="KILLMAP">
		String[] tempKillsArray = killsMap.split("</div>");
		KillEvent[] killEvents = new KillEvent[(tempKillsArray.length - 3) / 2];
		for (
				int i = 0;
				i < (tempKillsArray.length - 3) / 2; i++)

		{
			killEvents[i] = new KillEvent();
		}

		Integer killsCounter = 0;

		for (int i = 1; i < tempKillsArray.length - 2; i++)
		{
			if (tempKillsArray[i].contains("suicide") || tempKillsArray[i].contains("Suicided"))
				continue;

			//	tempKillsArray [1..length-2]
			String killTime;
			Float xKill;
			Float yKill;
			Integer numberofasssisters;
			String assisters;
			if (i % 2 == 0)//killEvent
			{
				//<editor-fold desc="Dier and killers">
				tempString = tempKillsArray[i];
				String[] doubleString = new String[2];
				if (tempString.contains("The Dire") || tempString.contains("The Radiant"))
				{
					tempIndex = tempString.indexOf("died at");
					String whoDie = tempString.substring(tempIndex - 40, tempIndex);
					tempIndex = whoDie.indexOf(">");
					currentIndex = whoDie.indexOf("<");
					whoDie = whoDie.substring(tempIndex + 2, currentIndex);
					Integer numberWhoDie = 0;
					for (int j = 0; j < 10; j++)
					{
						if (player[j].hero.contains(whoDie))
							numberWhoDie = j + 1;
					}
					killEvents[killsCounter].dier = numberWhoDie;
					if (tempString.contains("Assisted"))
					{
						String assistersString = substringer(tempString, "Assisted by", "Respawned");
						assistersString = assistersString.replace("Assisted by", "");
						assistersString = assistersString.replace("<br />", "");
						assistersString = assistersString.replaceAll("/n", "");

						String[] assistersKills = assistersString.split("</a>");
						Integer assistsCounter = 1;
						for (int j = 0; j < assistersKills.length - 1; j++)
						{
							String whoAssists = substringer(assistersKills[j], "img alt=\"", "\" ");
							whoAssists = whoAssists.substring(9, whoAssists.length());
							Integer numberWhoAssists = 0;
							for (int k = 0; k < 10; k++)
							{
								if (player[k].hero.contains(whoAssists))
								{
									if (assistsCounter != 5)
									{
										killEvents[killsCounter].killers[assistsCounter] = k + 1;
										assistsCounter++;
										k = 10;
									}
								}
							}
						}
					}
				} else
				{
					if (tempString.contains("died at"))
					{
						String whoKill = substringer(tempString, "Killed by", "</a>");
						whoKill = removeTags(whoKill);
						whoKill = whoKill.replaceAll("\n", "");
						whoKill = whoKill.replaceAll("Killed by ", "");
						whoKill = whoKill.replaceAll("  ", "");
						whoKill = whoKill.replaceFirst(" ", "");
						Integer numberWhoKill = 0;
						for (int j = 0; j < 10; j++)
						{
							if (player[j].hero.contains(whoKill))
								numberWhoKill = j + 1;
						}
						killEvents[killsCounter].killers[0] = numberWhoKill;

						tempIndex = tempString.indexOf("died at");
						String whoDie = tempString.substring(tempIndex - 40, tempIndex);
						tempIndex = whoDie.indexOf(">");
						currentIndex = whoDie.indexOf("<");
						whoDie = whoDie.substring(tempIndex + 2, currentIndex);
						Integer numberWhoDie = 0;
						for (int j = 0; j < 10; j++)
						{
							if (player[j].hero.contains(whoDie))
								numberWhoDie = j + 1;
						}
						killEvents[killsCounter].dier = numberWhoDie;
						if (tempString.contains("Assisted"))
						{
							String assistersString = "";
							if (tempString.contains("Respawned"))
								assistersString = substringer(tempString, "Assisted by", "Respawned");
							else
								assistersString = substringer(tempString, "Assisted by", "Lost");
							assistersString = assistersString.replace("Assisted by", "");
							assistersString = assistersString.replace("<br />", "");
							assistersString = assistersString.replaceAll("/n", "");

							String[] assistersKills = assistersString.split("</a>");
							Integer assistsCounter = 1;
							for (int j = 0; j < assistersKills.length - 1; j++)
							{
								String whoAssists = substringer(assistersKills[j], "img alt=\"", "\" ");
								whoAssists = whoAssists.substring(9, whoAssists.length());

								Integer numberWhoAssists = 0;
								for (int k = 0; k < 10; k++)
								{
									if (player[k].hero.contains(whoAssists))
									{
										if (assistsCounter != 5)
										{
											killEvents[killsCounter].killers[assistsCounter] = k + 1;
											assistsCounter++;
											k = 10;
										}
									}
								}
							}
						}
					}
				}
				//</editor-fold>
				if (!tempKillsArray[i].contains("Assisted by"))
					killEvents[killsCounter].assistsNumber = 0;
				else
				{
					if (tempKillsArray[i].contains("Respawned"))
						assisters = substringer(tempKillsArray[i], "Assisted by", "Respawned");
					else
						assisters = substringer(tempKillsArray[i], "Assisted by", "Lost");
					String[] againTempArray = assisters.split("</a>");
					killEvents[killsCounter].assistsNumber = againTempArray.length - 1;
				}

				killEvents[killsCounter].whoKill = 1;
				killsCounter++;
			} else //Coords : X, Y, Time
			{
				tempString = substringer(tempKillsArray[i], "top:", "%");
				tempString = tempString.replaceAll("top:", "");
				tempString = tempString.replaceAll(" ", "");
				killEvents[killsCounter].y = Float.parseFloat(tempString);
				tempString = substringer(tempKillsArray[i], "left:", "%");
				tempString = tempString.replaceAll("left:", "");
				tempString = tempString.replaceAll(" ", "");
				killEvents[killsCounter].x = Float.parseFloat(tempString);
				//System.out.println(tempKillsArray[i]);
				if (tempKillsArray[i].contains("data-slider-max"))
				{
					tempString = substringer(tempKillsArray[i], "data-slider-min=", "data-slider-max");
					tempString = tempString.replaceAll("data-slider-min=", "");
					tempString = tempString.replaceAll("\"", "");
					tempString = tempString.replaceAll(" ", "");
					currentIndex = tempString.indexOf(".");
					tempString = tempString.substring(0, currentIndex);
					killEvents[killsCounter].second = Integer.parseInt(tempString);
				}
			}

		}

		for (int i = 0; i < killEvents.length; i++)
		{
			if (killEvents[i].second != null && killEvents[i].dier != null)
				killEventArrayList.add(killEvents[i]);
		}

		//</editor-fold>

		match.universalX = 0;

		//<editor-fold desc="TIME: Match: matchTime, FBTime, F10KTime; ProjectDir.MatchInfo.Player: timeDead">

		tempString = substringer(headerString, "Region", "</dd>");

		tempString = removeTags(tempString);

		tempString = tempString.replaceAll("Region", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		match.matchTime = timeToMinutes(tempString);
		int tempKillsDireCounter = 0;
		int tempKillsRadiantCounter = 0;
		Boolean fbbool = false;
		Boolean f10kbool = false;
		for (int j = 0; j < logLine.length; j++)
		{
			if (logLine[j].contains("killed") && !logLine[j].contains("roshan") && !logLine[j].contains("courier"))
			{
				tempString = logLine[j];
				currentIndex = tempString.indexOf("faction-dire");
				tempIndex = tempString.indexOf("faction-radiant");
				if (currentIndex < tempIndex)
				{
					if (fbbool == false)
					{
						currentIndex2 = tempString.indexOf("time\">");
						tempIndex2 = tempString.indexOf("</span", currentIndex2);

						String fbtime = tempString.substring(currentIndex2 + 6, tempIndex2);
						match.FBTime = mapTimeToSeconds(fbtime);
						match.firstBloodRadiant = true;
						fbbool = true;
					}
					tempKillsRadiantCounter++;
				}
				if (tempIndex < currentIndex)
				{
					if (fbbool == false)
					{
						currentIndex2 = tempString.indexOf("time\">");
						tempIndex2 = tempString.indexOf("</span");
						String fbtime = tempString.substring(currentIndex2 + 6, tempIndex2);
						match.FBTime = mapTimeToSeconds(fbtime);

						match.firstBloodRadiant = false;
						fbbool = true;
					}
					tempKillsDireCounter++;
				}

				if (tempKillsRadiantCounter == 10 && f10kbool == false)
				{
					currentIndex2 = tempString.indexOf("time\">");
					tempIndex2 = tempString.indexOf("</span");
					String f10killstime = tempString.substring(currentIndex2 + 6, tempIndex2);
					match.F10KTime = mapTimeToSeconds(f10killstime);
					match.first10KillsRadiant = true;
					f10kbool = true;
				}

				if (tempKillsDireCounter == 10 && f10kbool == false)
				{
					currentIndex2 = tempString.indexOf("time\">");
					tempIndex2 = tempString.indexOf("</span");
					String f10killstime = tempString.substring(currentIndex2 + 6, tempIndex2);
					match.F10KTime = mapTimeToSeconds(f10killstime);

					match.first10KillsRadiant = false;
					f10kbool = true;
				}
			}
		}

		//</editor-fold>

		//<editor-fold desc="GOLD EARNINGS:    ProjectDir.MatchInfo.Player: goldFromKills, goldLost, goldFed">
		//<editor-fold desc="Radiant GOLD EARNINGS">
		for (int i = 0; i < 5; i++)
		{
			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3");
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i].goldForKills = Integer.parseInt(tempString);

			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i].goldLost = Integer.parseInt(tempString);


			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i].goldFed = Integer.parseInt(tempString);
		}

		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3");
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[0].goldForKills = Integer.parseInt(tempString);

		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[0].goldLost = Integer.parseInt(tempString);


		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[0].goldFed = Integer.parseInt(tempString);
		//</editor-fold>
		//<editor-fold desc="Dire GOLD EARNINGS">
		for (
				int i = 0;
				i < 5; i++)

		{
			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3");
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i + 5].goldForKills = Integer.parseInt(tempString);

			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i + 5].goldLost = Integer.parseInt(tempString);


			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			currentIndex = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex + 2, tempString.length());
			tempString = tempString.replaceAll(",", "");
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			player[i + 5].goldFed = Integer.parseInt(tempString);
		}

		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3");
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[1].goldForKills = Integer.parseInt(tempString);

		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[1].goldLost = Integer.parseInt(tempString);


		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);

		currentIndex = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll("\n", "");
		tempString = tempString.replaceAll(" ", "");
		team[1].goldFed = Integer.parseInt(tempString);

		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="LAST HITS:    ProjectDir.MatchInfo.Player: minuteLastHits, perMinuteLastHits ; ProjectDir.MatchInfo.Team: minuteLastHits, perMinuteLastHits; ">
		//<editor-fold desc="Radiant LastHits">
		/*for (
				int i = 0;
				i < 5; i++)

		{
			currentIndex = farmPageRadiantHeroLine[i].lastIndexOf("r-tab r-group-2");
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			currentIndex = tempString.indexOf("<span data");
			tempIndex = tempString.indexOf("</span>", currentIndex);
			tempString = tempString.substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			String[] minuteLastHits = tempString.split(",");
			int sum = 0;
			for (int j = 0; j < minuteLastHits.length; j++)
			{
				sum += Integer.parseInt(minuteLastHits[j]);
				player[i].perMinuteLastHits[j] = Integer.parseInt(minuteLastHits[j]);
				player[i].minuteLastHits[j] = sum;
				.out.println("LAST1:" + player[i].minuteLastHits[i]);
			}
		}

		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j].minuteLastHits[i] != 9999)
				{
					team[0].minuteLastHits[i] += player[j].minuteLastHits[i];
					team[0].perMinuteLastHits[i] += player[j].perMinuteLastHits[i];
				}
			}
		}*/
		//</editor-fold>
		//<editor-fold desc="Dire LastHits">
		/*for (int i = 0; i < 5; i++)
		{
			currentIndex = farmPageDireHeroLine[i].lastIndexOf("r-tab r-group-2");
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			currentIndex = tempString.indexOf("<span data");
			tempIndex = tempString.indexOf("</span>", currentIndex);
			tempString = tempString.substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			String[] minuteLastHits = tempString.split(",");
			int sum = 0;
			for (int j = 0; j < minuteLastHits.length; j++)
			{
				sum += Integer.parseInt(minuteLastHits[j]);
				player[i + 5].perMinuteLastHits[j] =0;
				player[i + 5].minuteLastHits[j] = 0;
				System.out.println("LAST2:" + player[i + 5].perMinuteLastHits[i]);
			}
		}

		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j + 5].minuteLastHits[i] != 9999)
				{
					team[1].minuteLastHits[i] += player[j + 5].minuteLastHits[i];
					team[1].perMinuteLastHits[i] += player[j + 5].perMinuteLastHits[i];
				}
			}
		}*/
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="OBJECTIVES:     ProjectDir.MatchInfo.Player: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;  ProjectDir.MatchInfo.Team: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;">
		//<editor-fold desc="Radiant objectives">
		for (int i = 0; i < 5; i++)
		{
			//Towers destroyed and denied

			player[i].towersDestroyed = 0;
			player[i].towersDenied = 0;
			player[i].roshanKills = 0;
		}
		team[0].towersDestroyed = 0;
		team[0].towersDenied = 0;
		team[0].towerDamage = 0;
		team[0].roshanKills = 0;
		//</editor-fold>
		//<editor-fold desc="Dire objectives">

		for (int i = 0; i < 5; i++)
		{
			player[i + 5].towersDestroyed = 0;
			player[i + 5].towersDenied = 0;
			player[i + 5].roshanKills = 0;
		}

		team[1].towersDestroyed = 0;
		team[1].towersDenied = 0;
		team[1].towerDamage = 0;
		team[1].roshanKills = 0;

		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="ROLES DETECTOR: Player.role">
		fillNetworthPositions(player);
		for (int i = 0; i < mainPageRadiantHeroLine.length - 1; i++)
		{
			currentIndex = mainPageRadiantHeroLine[i].indexOf("tf-fa");
			tempIndex = mainPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = mainPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			currentIndex2 = tempString.indexOf("title=\"");
			tempIndex2 = tempString.indexOf("\"", currentIndex2 + 7);
			tempString = tempString.substring(currentIndex2, tempIndex2);
			if (tempString.contains("Support"))
				player[i].firstLine = "Support";
			else
				player[i].firstLine = "Core";

		}
		//rolesDetector(player);
		//</editor-fold>

		//<editor-fold desc="EXPIRIENCE: Player.minuteXPM, Team. minuteXPM"
		//<editor-fold desc="Radiant Expirience">
		/*for (
				int i = 0;
				i < 5; i++)

		{
			tempString = substringer(farmPageRadiantHeroLine[i], "<td class=\"r-tab r-group-2\"", "</td>");
			tempString = removeTag(tempString, "span");
			tempString = removeTag(tempString, "td");
			tempString = removeTag(tempString, "div");
			tempIndex = tempString.indexOf(" ");
			tempString = tempString.substring(tempIndex, tempString.length());
			tempString = tempString.replaceAll(" ", "");
			tempString = tempString.replaceAll("\n", "");
			String[] tempXPMArray = tempString.split(",");
			for (int j = 0; j < tempXPMArray.length; j++)
			{
				player[i].minuteXPM[j] = Integer.parseInt(tempXPMArray[j]);
			}
		}

		for (
				int i = 0;
				i < 150; i++)

		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j].minuteXPM[i] != 9999)
				{
					team[0].minuteXPM[i] += player[j].minuteXPM[i];
				}
			}
		}*/
		//</editor-fold>
		//<editor-fold desc="Dire Expirience">
		/*for (
				int i = 0;
				i < 5; i++)

		{
			tempString = substringer(farmPageDireHeroLine[i], "<td class=\"r-tab r-group-2\"", "</td>");
			tempString = removeTag(tempString, "span");
			tempString = removeTag(tempString, "td");
			tempString = removeTag(tempString, "div");
			tempIndex = tempString.indexOf(" ");
			tempString = tempString.substring(tempIndex, tempString.length());
			tempString = tempString.replaceAll(" ", "");
			tempString = tempString.replaceAll("\n", "");
			String[] tempXPMArray = tempString.split(",");
			for (int j = 0; j < tempXPMArray.length; j++)
			{
				player[i + 5].minuteXPM[j] = Integer.parseInt(tempXPMArray[j]);
			}
		}

		for (
				int i = 0;
				i < 150; i++)

		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j + 5].minuteXPM[i] != 9999)
					team[1].minuteXPM[i] += player[j + 5].minuteXPM[i];
			}
		}*/
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="FIRST OBJECTIVES">
		Boolean roshanDetected = false;
		Boolean FBDetected = false;
		Boolean F10KDetected = false;
		Integer radiantKillsCounter = 0;
		Integer direKillsCounter = 0;
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("killed") && !logLine[i].contains("Roshan") && !logLine[i].contains("The Radiant") && !logLine[i].contains("The Dire") && !logLine[i].contains("creeps"))
			{
				currentIndex = logLine[i].indexOf("faction-dire");
				tempIndex = logLine[i].indexOf("faction-radiant");
				if (tempIndex < currentIndex)
				{
					direKillsCounter++;
					if (!FBDetected)
					{
						tempString = substringer(logLine[i], "<span class=\"time", "</span>");
						tempString = removeTags(tempString);
						match.FBTime = mapTimeToSeconds(tempString);
						team[0].isFirstBlood = false;
						team[1].isFirstBlood = true;
						FBDetected = true;
					}
					if (direKillsCounter == 10 && !F10KDetected)
					{
						tempString = substringer(logLine[i], "<span class=\"time", "</span>");
						tempString = removeTags(tempString);
						match.F10KTime = mapTimeToSeconds(tempString);
						team[0].isF10K = false;
						team[1].isF10K = true;
						F10KDetected = true;
					}
				}
				if (currentIndex < tempIndex)
				{
					radiantKillsCounter++;
					if (!FBDetected)
					{
						tempString = substringer(logLine[i], "<span class=\"time", "</span>");
						tempString = removeTags(tempString);
						match.FBTime = mapTimeToSeconds(tempString);
						team[0].isFirstBlood = true;
						match.firstBloodRadiant = true;
						team[1].isFirstBlood = false;
						FBDetected = true;
					}
					if (radiantKillsCounter == 10 && !F10KDetected)
					{
						tempString = substringer(logLine[i], "<span class=\"time", "</span>");
						tempString = removeTags(tempString);
						match.F10KTime = mapTimeToSeconds(tempString);
						team[0].isF10K = true;
						match.first10KillsRadiant = true;
						team[1].isF10K = false;
						F10KDetected = true;
					}
				}
			}
			if (logLine[i].contains("Roshan") && !roshanDetected)
			{
				tempString = substringer(logLine[i], "<span class=\"time", "</span>");
				tempString = removeTags(tempString);
				match.FRoshanTime = mapTimeToSeconds(tempString);
				if (logLine[i].contains("The Dire") && !roshanDetected)
				{
					team[1].isFirstRoshan = true;
					match.firstRoshanRadiant = false;
					roshanDetected = true;
				}
				if (logLine[i].contains("The Radiant") && !roshanDetected)
				{
					team[0].isFirstRoshan = true;
					match.firstRoshanRadiant = true;
					roshanDetected = true;
				}
			}
		}
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

	private void fillNetworthPositions(Player[] player)
	{
		ArrayList<Integer> radiantNetworth = new ArrayList<>();
		ArrayList<Integer> direNetworth = new ArrayList<>();
		for (int i = 0; i < 5; i++)
		{
			radiantNetworth.add(player[i].totalGPM);
		}
		for (int i = 5; i < 10; i++)
		{
			direNetworth.add(player[i].totalGPM);
		}
		Collections.sort(radiantNetworth);
		Collections.sort(direNetworth);
		for (int i = 0; i < radiantNetworth.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j].totalGPM == radiantNetworth.get(i))
					player[j].networthPosition = 5 - i;
			}
		}
		for (int i = 0; i < direNetworth.size(); i++)
		{
			for (int j = 5; j < 10; j++)
			{
				if (player[j].totalGPM == direNetworth.get(i))
					player[j].networthPosition = 5 - i;
			}
		}
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
			if (hours >= 24 * 5)
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

	Boolean calculateIfMatchCanBeParsed(String time)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
		if (hours >= 2 && hours <= 700)
			return true;
		else
			return false;
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




