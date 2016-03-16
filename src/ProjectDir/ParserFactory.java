package ProjectDir;

import ProjectDir.MatchInfo.*;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


//TODO: Update RolesDetector.

public class ParserFactory
{
	class League
	{
		public String id;
		public String date;
	}

	Integer direRoaming = 0;
	Integer radiantRoaming = 0;
	UniqueInfoFactory uniqueInfoFactory = new UniqueInfoFactory();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	RatingFactory ratingFactory = new RatingFactory();
	public int parseCounter = 0;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public Document parse_html(String html) throws IOException, InterruptedException
	{
		if (parseCounter == 50)
		{
			Thread.sleep(30000);
			parseCounter = 0;
		}
		Document doc = new Document("");
		int numtries = 20;
		while (numtries-- != 0)
		{
			if (numtries == 10)
				Thread.sleep(500000);
			try
			{
				doc = Jsoup.connect(html)
						.userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
						.get();
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
		Thread.sleep(500);
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
		if (!stringMainPage.contains("Captains Mode") && !stringMainPage.contains("Captains Draft"))
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
		Document docObjectivesPage = parse_html("http://www.dotabuff.com/matches/" + id + "/objectives");
		Document docRunesPage = parse_html("http://www.dotabuff.com/matches/" + id + "/runes");
		Document docVisionPage = parse_html("http://www.dotabuff.com/matches/" + id + "/vision");
		Document docLogPage = parse_html("http://www.dotabuff.com/matches/" + id + "/log");
		//</editor-fold>

		//<editor-fold desc="STRINGS">
		String stringKillsPage = docKillsPage.toString();
		String stringFarmPage = docFarmPage.toString();
		String stringObjectivesPage = docObjectivesPage.toString();
		String stringRunesPage = docRunesPage.toString();
		String stringVisionPage = docVisionPage.toString();
		String stringLogPage = docLogPage.toString();
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

		//<editor-fold desc="BUILDS PAGE PARSER">
		/***********Documentation*************/

		/*************Variables***************/

		/*************Workspace***************/
		/**RADIANT**/


		/***DIRE***/
		//</editor-fold>

		//<editor-fold desc="OBJECTIVES PAGE PARSER">
		/***********Documentation*************/
		/** objectivesPageRadiantHeroLine - array(0-5, 6-empty one) of strings of each hero result(Radiant)**/
		/** objectivesPageRadiantTotalLine -string of total results(Radiant)**/

		/*************Variables***************/

		String objectivesPageRadiantTotalLine;
		String objectivesPageDireTotalLine;

		/*************Workspace***************/
		/**RADIANT**/

		tempString = substringer(stringObjectivesPage, "<section class=\"radiant\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] objectivesPageRadiantHeroLine = tempString.split("</tr>");

		tempString = substringer(stringObjectivesPage, "<section class=\"radiant\">", "</section>");
		objectivesPageRadiantTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");

		/***DIRE***/
		tempString = substringer(stringObjectivesPage, "<section class=\"dire\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] objectivesPageDireHeroLine = tempString.split("</tr>");

		tempString = substringer(stringObjectivesPage, "<section class=\"dire\">", "</section>");
		objectivesPageDireTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");
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

		//<editor-fold desc="RUNES PAGE PARSER">
		/***********Documentation*************/

		/*************Variables***************/

		/*************Workspace***************/
		/**RADIANT**/


		/***DIRE***/
		//</editor-fold>

		//<editor-fold desc="VISION PAGE PARSER">
		/***********Documentation*************/
		/** visionPageRadiantHeroLine - array(0-5, 6-empty one) of strings of each hero result(Radiant)**/
		/** visionPageRadiantTotalLine -  string of total results(Radiant)**/

		/*************Variables***************/

		String visionPageRadiantTotalLine;
		String visionPageDireTotalLine;
		/*************Workspace***************/
		/**GLOBAL**/

		/**RADIANT**/
		tempString = substringer(stringVisionPage, "<section class=\"radiant\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] visionPageRadiantHeroLine = tempString.split("</tr>");

		tempString = substringer(stringVisionPage, "<section class=\"radiant\">", "</section>");
		visionPageRadiantTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");
		/***DIRE***/
		tempString = substringer(stringVisionPage, "<section class=\"dire\">", "</section>");
		tempString = substringer(tempString, "<article class=\"r-tabbed-table\"", "</article>");
		tempString = substringer(tempString, "<tbody>", "</tbody>");
		String[] visionPageDireHeroLine = tempString.split("</tr>");

		tempString = substringer(stringVisionPage, "<section class=\"dire\">", "</section>");
		visionPageDireTotalLine = substringer(tempString, "<tfoot>", "</tfoot>");

		//</editor-fold>

		//<editor-fold desc="LOG PAGE PARSER">
		/***********Documentation*************/
		/*logLine- array of log lines*/
		/*************Variables***************/

		/*************Workspace***************/

		Elements elemsLogPage = docLogPage.select("div.event");
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

		Elements elemsHeaderPage = docMainPage.select("div.header-content-secondary");
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
		//</editor-fold>

		//<editor-fold desc="TEAM GENERAL INFO">
		//Winteam
		String winTeam;
		currentIndex = stringMainPage.indexOf("match-result");
		tempIndex = stringMainPage.indexOf("</div>", currentIndex);
		winTeam = stringMainPage.substring(currentIndex, tempIndex);
		winTeam = winTeam.replaceAll("\n", "");
		winTeam = winTeam.replaceAll(" ", "");
		currentIndex = winTeam.indexOf("\">");
		tempIndex = winTeam.indexOf("Victory");
		winTeam = winTeam.substring(currentIndex + 2, tempIndex);
		winTeam = winTeam.toLowerCase();
		if (winTeam.equals("dire"))
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
		teamName = replaceAllSeparators(teamName);
		team[1].id = teamId;
		team[1].name = teamName;

		tempString = substringer(mainPageRadiantTotalLine, "r-group-1 cell-centered\">", "</td>");
		tempString = tempString.substring(25, tempString.length());
		team[0].totalLevel = Integer.parseInt(tempString);
		tempString = substringer(mainPageDireTotalLine, "r-group-1 cell-centered\">", "</td>");
		tempString = tempString.substring(25, tempString.length());
		team[1].totalLevel = Integer.parseInt(tempString);
		if (team[0].totalLevel < 30 || team[1].totalLevel < 30)
		{
			System.out.println("Parsing failed. Level block");
			return false;
		}
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
		tempString = substringer(mainPageRadiantTotalLine, "<td class=\"r-tab r-group-2 cell-centered\">", "</td>");
		tempString = tempString.replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
		tempString = tempString.replaceAll("k", "");
		tempString = tempString.replaceAll("\\.", "");


		for (int i = 0; i < mainPageRadiantHeroLine.length - 1; i++)
		{
			/**Level**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered", "</td>");
			tempString = removeTags(tempString);
			player[i].level = Integer.parseInt(tempString);
			/**HeroName**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<a href=\"/heroes", "</a>");
			tempString = substringer(tempString, "title=", "data");
			tempString = tempString.replaceAll("title=", "");
			tempString = tempString.replaceAll("\"", "");
			tempString = tempString.substring(0, tempString.length() - 1);
			player[i].hero = tempString;
			player[i].side = 1;
			/**Kills, Deaths, Assists**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\">", "<td class=\"r-tab r-group-2 cell-centered\">");
			tempString = tempString.replaceAll("<td class=\"r-tab r-group-1 cell-centered\">", "");
			tempString = tempString.replaceAll("\n", "");
			String[] radiantHeroKDA = tempString.split("</td>");
			for (int j = 1; j <= 3; j++)
			{
				radiantHeroKDA[j] = radiantHeroKDA[j].replaceAll(" ", "");
				if (radiantHeroKDA[j].contains("-"))
				{
					radiantHeroKDA[j] = "0";
				}
			}
			player[i].kills = Integer.parseInt(radiantHeroKDA[1]);
			player[i].deaths = Integer.parseInt(radiantHeroKDA[2]);
			player[i].assists = Integer.parseInt(radiantHeroKDA[3]);
			/**HeroHeal, HeroDamage, towerDamage, totalXPM, totalGPM**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-2 cell-centered\">", "<td class=\"r-tab r-group-4\">");
			String[] radiantHeroMainGeneralParameters = tempString.split("</td>");
			//1-LH,2-DN,3-XPM,4-GPM,5-HD,6-HH
			for (int j = 0; j < radiantHeroMainGeneralParameters.length; j++)
			{
				radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("\n", "");
				radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
				radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-3 cell-centered\">", "");
				radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll(" ", "");
				if (radiantHeroMainGeneralParameters[j].contains("-"))
					radiantHeroMainGeneralParameters[j] = "0";
				if (radiantHeroMainGeneralParameters[j].contains("k"))
				{
					radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("k", "");
					if (radiantHeroMainGeneralParameters[j].contains("."))
					{
						radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("\\.", "");
						radiantHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(radiantHeroMainGeneralParameters[j]) * 100);
					} else
					{
						radiantHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(radiantHeroMainGeneralParameters[j]) * 1000);
					}
				}
			}
			player[i].totalLH = Integer.parseInt(radiantHeroMainGeneralParameters[1]);
			player[i].totalXPM = Integer.parseInt(radiantHeroMainGeneralParameters[3]);
			player[i].totalGPM = Integer.parseInt(radiantHeroMainGeneralParameters[4]);
			player[i].heroDamage = Integer.parseInt(radiantHeroMainGeneralParameters[5]);
			player[i].heroHeal = Integer.parseInt(radiantHeroMainGeneralParameters[6]);
			/**Items**/
			tempString = substringer(mainPageRadiantHeroLine[i], "<td class=\"r-tab r-group-4\">", "</td>");

			tempString = tempString.replaceAll("<td class=\"r-tab r-group-4\">", "");
			String[] heroRadiantItems = tempString.split("</div>");
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

			}
		}
		//</editor-fold>
		//<editor-fold desc="DIRE GENERAL INFO">
		/**DIRE**/
		tempString = substringer(mainPageDireTotalLine, "<td class=\"r-tab r-group-2 cell-centered\">", "</td>");
		tempString = tempString.replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
		tempString = tempString.replaceAll("k", "");
		tempString = tempString.replaceAll("\\.", "");
		for (int i = 0; i < mainPageDireHeroLine.length - 1; i++)
		{
			//Level
			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered", "</td>");
			tempString = removeTags(tempString);
			player[i + 5].level = Integer.parseInt(tempString);
			//HeroName
			tempString = substringer(mainPageDireHeroLine[i], "<a href=\"/heroes", "</a>");
			tempString = substringer(tempString, "title=", "data");
			tempString = tempString.replaceAll("title=", "");
			tempString = tempString.replaceAll("\"", "");
			tempString = tempString.substring(0, tempString.length() - 1);
			player[i + 5].hero = tempString;
			player[i + 5].side = 2;
			//Kills, Deaths, Assists
			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\">", "<td class=\"r-tab r-group-2 cell-centered\">");
			tempString = tempString.replaceAll("<td class=\"r-tab r-group-1 cell-centered\">", "");
			tempString = tempString.replaceAll("\n", "");
			String[] direHeroKDA = tempString.split("</td>");
			for (int j = 1; j <= 3; j++)
			{
				direHeroKDA[j] = direHeroKDA[j].replaceAll(" ", "");
				if (direHeroKDA[j].contains("-"))
				{
					direHeroKDA[j] = "0";
				}
			}
			player[i + 5].kills = Integer.parseInt(direHeroKDA[1]);
			player[i + 5].deaths = Integer.parseInt(direHeroKDA[2]);
			player[i + 5].assists = Integer.parseInt(direHeroKDA[3]);
			//HeroHeal, HeroDamage, towerDamage, totalXPM, totalGPM
			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"r-tab r-group-2 cell-centered\">", "<td class=\"r-tab r-group-4\">");
			String[] direHeroMainGeneralParameters = tempString.split("</td>");
			//1-LH,2-DN,3-XPM,4-GPM,5-HD,6-HH
			for (int j = 0; j < direHeroMainGeneralParameters.length; j++)
			{
				direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("\n", "");
				direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
				direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-3 cell-centered\">", "");
				direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll(" ", "");
				if (direHeroMainGeneralParameters[j].contains("-"))
					direHeroMainGeneralParameters[j] = "0";
				if (direHeroMainGeneralParameters[j].contains("k"))
				{
					direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("k", "").replaceAll("\\.", "");
					direHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(direHeroMainGeneralParameters[j]) * 100);
				}
			}
			player[i + 5].totalLH = Integer.parseInt(direHeroMainGeneralParameters[1]);
			player[i + 5].totalXPM = Integer.parseInt(direHeroMainGeneralParameters[3]);
			player[i + 5].totalGPM = Integer.parseInt(direHeroMainGeneralParameters[4]);
			player[i + 5].heroDamage = Integer.parseInt(direHeroMainGeneralParameters[5]);
			player[i + 5].heroHeal = Integer.parseInt(direHeroMainGeneralParameters[6]);
			/**Items**/
			tempString = substringer(mainPageDireHeroLine[i], "<td class=\"r-tab r-group-4\">", "</td>");

			tempString = tempString.replaceAll("<td class=\"r-tab r-group-4\">", "");
			String[] heroDireItems = tempString.split("</div>");
			int howMuchItems = 0;
			if (heroDireItems.length > 6)
				howMuchItems = 6;
			else
				howMuchItems = heroDireItems.length;
			for (int j = 0; j < howMuchItems; j++)
			{
				if (j >= 6)
					break;
				if (heroDireItems[j].contains("title="))
				{
					heroDireItems[j] = substringer(heroDireItems[j], "title=\"", "data");
					heroDireItems[j] = heroDireItems[j].replaceAll("title=", "");
					heroDireItems[j] = heroDireItems[j].replaceAll("\"", "");
					heroDireItems[j] = heroDireItems[j].substring(0, heroDireItems[j].length() - 1);
					player[i + 5].item[j] = heroDireItems[j];
				} else player[i + 5].item[j] = "null";
			}
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GENERAL INFO[1]: ProjectDir.MatchInfo.Player: %KPart, ProjectDir.MatchInfo.Team: KDA, %KPart">
		//<editor-fold desc="RADIANT">
		/**RADIANT**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		currentIndex = killsPageRadiantTotalLine.indexOf("%");
		tempIndex = killsPageRadiantTotalLine.indexOf("\">", currentIndex - 7);
		currentIndex = killsPageRadiantTotalLine.indexOf("</td>", tempIndex);
		tempString = killsPageRadiantTotalLine.substring(tempIndex, currentIndex - 1);
		tempString = tempString.replaceAll("\">", "");
		team[0].partisipation = Integer.parseInt(tempString);

		currentIndex = killsPageRadiantTotalLine.indexOf("cell-centered r-tab r-group-1\">");
		tempIndex = killsPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageRadiantTotalLine.substring(currentIndex + 31, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].kills = Integer.parseInt(tempString);

		currentIndex = killsPageRadiantTotalLine.indexOf("cell-centered r-tab r-group-1\">", tempIndex);
		tempIndex = killsPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageRadiantTotalLine.substring(currentIndex + 31, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].assists = Integer.parseInt(tempString);

		currentIndex = killsPageRadiantTotalLine.indexOf("cell-centered r-tab r-group-3 cell-divider\">");
		tempIndex = killsPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageRadiantTotalLine.substring(currentIndex + 44, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].deaths = Integer.parseInt(tempString);
		for (int i = 0; i < killsPageRadiantHeroLine.length - 1; i++)
		{
			/**Kills % partisipation**/
			currentIndex = killsPageRadiantHeroLine[i].indexOf("%");
			tempIndex = killsPageRadiantHeroLine[i].indexOf("\">", currentIndex - 7);
			currentIndex = killsPageRadiantHeroLine[i].indexOf("</td>", tempIndex);
			tempString = killsPageRadiantHeroLine[i].substring(tempIndex, currentIndex - 1);
			tempString = tempString.replaceAll("\">", "");
			player[i].partisipation = Integer.parseInt(tempString);
		}
		//</editor-fold>
		//<editor-fold desc="DIRE">
		/**DIRE**/
		/**ProjectDir.MatchInfo.Team Total Kills % partisipation**/
		currentIndex = killsPageDireTotalLine.indexOf("%");
		tempIndex = killsPageDireTotalLine.indexOf("\">", currentIndex - 7);
		currentIndex = killsPageDireTotalLine.indexOf("</td>", tempIndex);
		tempString = killsPageDireTotalLine.substring(tempIndex, currentIndex - 1);
		tempString = tempString.replaceAll("\">", "");
		team[1].partisipation = Integer.parseInt(tempString);
		currentIndex = killsPageDireTotalLine.indexOf("cell-centered r-tab r-group-1\">");
		tempIndex = killsPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageDireTotalLine.substring(currentIndex + 31, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].kills = Integer.parseInt(tempString);

		currentIndex = killsPageDireTotalLine.indexOf("cell-centered r-tab r-group-1\">", tempIndex);
		tempIndex = killsPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageDireTotalLine.substring(currentIndex + 31, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].assists = Integer.parseInt(tempString);

		currentIndex = killsPageDireTotalLine.indexOf("cell-centered r-tab r-group-3 cell-divider\">");
		tempIndex = killsPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = killsPageDireTotalLine.substring(currentIndex + 44, tempIndex);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].deaths = Integer.parseInt(tempString);
		for (int i = 0; i < killsPageDireHeroLine.length - 1; i++)
		{
			/**Kills % partisipation**/
			currentIndex = killsPageDireHeroLine[i].indexOf("%");
			tempIndex = killsPageDireHeroLine[i].indexOf("\">", currentIndex - 7);
			currentIndex = killsPageDireHeroLine[i].indexOf("</td>", tempIndex);
			tempString = killsPageDireHeroLine[i].substring(tempIndex, currentIndex - 1);
			tempString = tempString.replaceAll("\">", "");
			player[i + 5].partisipation = Integer.parseInt(tempString);
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GENERAL INFO[2]:  ProjectDir.MatchInfo.Team: HH,HD,LH,DN ">
		//<editor-fold desc="Radiant GlobalInfo">
		tempString = substringer(mainPageRadiantTotalLine, "<td class=\"r-tab r-group-2 cell-centered\">", "<td class=\"r-tab r-group-4\">");
		String[] radiantHeroMainGeneralParameters = tempString.split("</td>");
		//1-LH,2-DN,3-XPM,4-GPM,5-HD,6-HH
		for (int j = 0; j < radiantHeroMainGeneralParameters.length; j++)
		{
			radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("\n", "");
			radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
			radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-3 cell-centered\">", "");
			radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll(" ", "");
			if (radiantHeroMainGeneralParameters[j].contains("-"))
				radiantHeroMainGeneralParameters[j] = "0";
			if (radiantHeroMainGeneralParameters[j].contains("k"))
			{
				radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("k", "");
				if (radiantHeroMainGeneralParameters[j].contains("."))
				{
					radiantHeroMainGeneralParameters[j] = radiantHeroMainGeneralParameters[j].replaceAll("\\.", "");
					radiantHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(radiantHeroMainGeneralParameters[j]) * 100);
				} else
				{
					radiantHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(radiantHeroMainGeneralParameters[j]) * 1000);
				}
			}
			if (radiantHeroMainGeneralParameters[j].contains("-"))
				radiantHeroMainGeneralParameters[j] = "0";
		}
		team[0].totalLH = Integer.parseInt(radiantHeroMainGeneralParameters[1]);
		team[0].totalDenies = Integer.parseInt(radiantHeroMainGeneralParameters[2]);
		team[0].totalXPM = Integer.parseInt(radiantHeroMainGeneralParameters[3]);
		team[0].totalGPM = Integer.parseInt(radiantHeroMainGeneralParameters[4]);
		team[0].heroDamage = Integer.parseInt(radiantHeroMainGeneralParameters[5]);
		team[0].heroHeal = Integer.parseInt(radiantHeroMainGeneralParameters[6]);
		//</editor-fold>
		//<editor-fold desc="Dire GlobalInfo">
		tempString = substringer(mainPageDireTotalLine, "<td class=\"r-tab r-group-2 cell-centered\">", "<td class=\"r-tab r-group-4\">");
		String[] direHeroMainGeneralParameters = tempString.split("</td>");
		//1-LH,2-DN,3-XPM,4-GPM,5-HD,6-HH
		for (int j = 0; j < direHeroMainGeneralParameters.length; j++)
		{
			direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("\n", "");
			direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-2 cell-centered\">", "");
			direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("<td class=\"r-tab r-group-3 cell-centered\">", "");
			direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll(" ", "");
			if (direHeroMainGeneralParameters[j].contains("-"))
				direHeroMainGeneralParameters[j] = "0";
			if (direHeroMainGeneralParameters[j].contains("k"))
			{
				direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("k", "");
				if (direHeroMainGeneralParameters[j].contains("."))
				{
					direHeroMainGeneralParameters[j] = direHeroMainGeneralParameters[j].replaceAll("\\.", "");
					direHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(direHeroMainGeneralParameters[j]) * 100);
				} else
				{
					direHeroMainGeneralParameters[j] = Integer.toString(Integer.parseInt(direHeroMainGeneralParameters[j]) * 1000);
				}
			}
			if (direHeroMainGeneralParameters[j].contains("-"))
				direHeroMainGeneralParameters[j] = "0";
		}
		team[1].totalLH = Integer.parseInt(direHeroMainGeneralParameters[1]);
		team[1].totalDenies = Integer.parseInt(direHeroMainGeneralParameters[2]);
		team[1].totalXPM = Integer.parseInt(direHeroMainGeneralParameters[3]);
		team[1].totalGPM = Integer.parseInt(direHeroMainGeneralParameters[4]);
		team[1].heroDamage = Integer.parseInt(direHeroMainGeneralParameters[5]);
		team[1].heroHeal = Integer.parseInt(direHeroMainGeneralParameters[6]);
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="KILLMAP">
		String[] tempKillsArray = killsMap.split("</div>");
		KillEvent[] killEvents = new KillEvent[(tempKillsArray.length - 3) / 2];
		for (int i = 0; i < (tempKillsArray.length - 3) / 2; i++)
		{
			killEvents[i] = new KillEvent();
		}
		Integer killsCounter = 0;

		for (int i = 1; i < tempKillsArray.length - 2; i++)
		{
			if (tempKillsArray[i].contains("suicide"))
				continue;

			//	tempKillsArray [1..length-2]
			String killTime;
			Float xKill;
			Float yKill;
			Integer numberofasssisters;
			String assisters;
			tempKillsArray[i] = removeTag(tempKillsArray[i], "img");
			if (i % 2 == 0)//killEvent
			{
				//<editor-fold desc="Dier and killers">
				tempString = tempKillsArray[i];
				tempString = tempString.replaceAll("auto attack ", "");
				String[] doubleString = tempString.split("killed");
				if (doubleString[0].contains("The Dire") || doubleString[0].contains("The Radiant"))
				{
					String whoDie = substringer(doubleString[1], "title=\"", "\" data");
					whoDie = whoDie.substring(7, whoDie.length());
					Integer numberWhoDie = 0;
					for (int j = 0; j < 10; j++)
					{
						if (player[j].hero.contains(whoDie))
							numberWhoDie = j + 1;
					}
					killEvents[killsCounter].dier = numberWhoDie;
					if (doubleString[1].contains("Assisted"))
					{
						doubleString[1] = doubleString[1].substring(doubleString[1].indexOf("Assisted by"), doubleString[1].length());
						String[] assistersKills = doubleString[1].split("</a>");
						Integer assistsCounter = 0;
						for (int j = 0; j < assistersKills.length - 1; j++)
						{
							String whoAssists = substringer(assistersKills[j], "title=\"", "\" data");
							whoAssists = whoAssists.substring(7, whoAssists.length());
							for (int k = 0; k < 10; k++)
							{
								if (player[k].hero.equals(whoAssists))
								{
									killEvents[killsCounter].killers[assistsCounter] = k + 1;
									assistsCounter++;
									k = 10;
								}
							}
						}
					}
				} else
				{
					String whoKill = substringer(doubleString[0], "title=\"", "\" data");
					whoKill = whoKill.substring(7, whoKill.length());
					Integer numberWhoKill = 0;
					for (int j = 0; j < 10; j++)
					{
						if (player[j].hero.contains(whoKill))
							numberWhoKill = j + 1;
					}
					killEvents[killsCounter].killers[0] = numberWhoKill;

					String whoDie = substringer(doubleString[1], "title=\"", "\" data");
					whoDie = whoDie.substring(7, whoDie.length());
					Integer numberWhoDie = 0;
					for (int j = 0; j < 10; j++)
					{
						if (player[j].hero.contains(whoDie))
							numberWhoDie = j + 1;
					}
					killEvents[killsCounter].dier = numberWhoDie;
					if (doubleString[1].contains("Assisted"))
					{
						doubleString[1] = doubleString[1].substring(doubleString[1].indexOf("Assisted by"), doubleString[1].length());
						String[] assistersKills = doubleString[1].split("</a>");
						Integer assistsCounter = 1;
						for (int j = 0; j < assistersKills.length - 1; j++)
						{
							String whoAssists = substringer(assistersKills[j], "title=\"", "\" data");
							whoAssists = whoAssists.substring(7, whoAssists.length());
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
				//</editor-fold>
				if (!tempKillsArray[i].contains("Assisted by"))
					killEvents[killsCounter].assistsNumber = 0;
				else
				{
					assisters = substringer(tempKillsArray[i], "Assisted by", ";");
					String[] againTempArray = assisters.split("</a>");
					killEvents[killsCounter].assistsNumber = againTempArray.length - 1;
				}

				tempString = html2text(tempKillsArray[i]);

				if (tempString.contains("The Dire"))
				{
					killEvents[killsCounter].whoKill = 2;
				} else if (tempString.contains("The Radiant"))
				{
					killEvents[killsCounter].whoKill = 1;
				} else if (tempString.contains("'s"))
				{
					tempString = substringer(tempString, "", "'s");
					for (int j = 0; j < 10; j++)
					{

						if (player[j].hero.equals(tempString))
						{
							equaler = j;
						}
					}
					if (equaler <= 4)
						killEvents[killsCounter].whoKill = 1;
					else if (equaler >= 5 && equaler < 10)
					{
						killEvents[killsCounter].whoKill = 2;
					} else System.out.println("KillsMap Error");
				} else
				{
					System.out.println("KillMap Error");
				}
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
				tempString = substringer(tempKillsArray[i], "data-slider-min=", "data-slider-max");
				tempString = tempString.replaceAll("data-slider-min=", "");
				tempString = tempString.replaceAll("\"", "");
				tempString = tempString.replaceAll(" ", "");
				killEvents[killsCounter].second = Integer.parseInt(tempString);
			}

		}
		for (int i = 0; i < killEvents.length; i++)
		{
			if (killEvents[i].second != null && killEvents[i].dier != null)
				killEventArrayList.add(killEvents[i]);
		}
		//</editor-fold>

		//<editor-fold desc="WARDMAP">
		tempString = substringer(stringVisionPage, "Vision Map", "Vision Log");
		String[] wardOnMap = tempString.split("<div class=\"vision-icon");
		ArrayList<WardEvent> wardEvents = new ArrayList<WardEvent>();
		for (int i = 1; i < wardOnMap.length; i++)
		{
			if (wardOnMap[i].contains("sentry"))
				continue;

			WardEvent wardEvent = new WardEvent();
			//X Y
			currentIndex = wardOnMap[i].indexOf("top: ");
			tempIndex = wardOnMap[i].indexOf("%", currentIndex);
			tempString = wardOnMap[i].substring(currentIndex + 5, tempIndex);
			tempString = tempString.substring(0, 4);
			wardEvent.y = Float.parseFloat(tempString);

			currentIndex = wardOnMap[i].indexOf("left: ");
			tempIndex = wardOnMap[i].indexOf("%", currentIndex);
			tempString = wardOnMap[i].substring(currentIndex + 6, tempIndex);
			tempString = tempString.substring(0, 4);
			wardEvent.x = Float.parseFloat(tempString);
			//Ward second
			currentIndex = wardOnMap[i].indexOf("> at");
			tempIndex = wardOnMap[i].indexOf("</", currentIndex);
			tempString = wardOnMap[i].substring(currentIndex + 5, tempIndex);
			tempString = tempString.replaceAll("\n", "");
			tempString = tempString.replaceAll(" ", "");
			String dateWard1 = tempString;
			if (tempString.contains("-"))
				wardEvent.second = 0;
			else
			{
				String[] tempWardMinute = tempString.split(":");
				wardEvent.second = Integer.parseInt(tempWardMinute[0]) * 60 + Integer.parseInt(tempWardMinute[1]);
			}
			//LifeTime
			if (wardOnMap[i].contains("Destroyed"))
			{
				currentIndex = wardOnMap[i].indexOf("> at", tempIndex);
				tempIndex = wardOnMap[i].indexOf("</", currentIndex);
				String dateWard2 = wardOnMap[i].substring(currentIndex + 5, tempIndex);
				dateWard2 = dateWard2.replaceAll(" ", "");
				dateWard2 = dateWard2.replaceAll("\n", "");
				wardEvent.lifeTime = calculateTimeDifference(dateWard1, dateWard2);
			} else
			{
				wardEvent.lifeTime = 420;
			}
			//Side
			if (wardOnMap[i].contains("Destroyed"))
			{
				currentIndex = wardOnMap[i].indexOf("faction-radiant");
				tempIndex = wardOnMap[i].indexOf("faction-dire");
				if (currentIndex < tempIndex)
					wardEvent.side = 1;
				else
					wardEvent.side = 2;
			} else
			{
				if (wardOnMap[i].contains("faction-radiant"))
					wardEvent.side = 1;
				else
					wardEvent.side = 2;
			}
			wardEvents.add(wardEvent);
		}
		wardEventArrayList.addAll(wardEvents);
		//</editor-fold>

		//<editor-fold desc="NET WORTH:    ProjectDir.MatchInfo.Player: TotalGold, MinuteGPM, fiveMinuteNetWorth">
		//<editor-fold desc="Radiant NET WORTH">
		for (int i = 0; i < 5; i++)
		{
			tempString = substringer(farmPageRadiantHeroLine[i], "<td class=\"r-tab r-group-2 cell-divider\"", "</td>");
			tempString = removeTag(tempString, "span");
			tempString = removeTag(tempString, "td");
			tempString = removeTag(tempString, "div");
			tempIndex = tempString.indexOf(" ");
			tempString = tempString.substring(tempIndex, tempString.length());
			tempString = tempString.replaceAll(" ", "");
			tempString = tempString.replaceAll("\n", "");
			String[] tempGPMArray = tempString.split(",");
			for (int j = 0; j < tempGPMArray.length; j++)
			{
				player[i].minuteGPM[j] = Integer.parseInt(tempGPMArray[j]);
			}
			match.universalX = tempGPMArray.length;
		}
		for (int j = 0; j < logLine.length; j++)
		{
			if (logLine[j].contains("killed") && !logLine[j].contains("Roshan") && !logLine[j].contains("The Radiant") && !logLine[j].contains("The Dire"))
			{
				tempIndex = logLine[j].indexOf("<a ");
				currentIndex = logLine[j].indexOf("</a>", tempIndex);
				tempString = logLine[j].substring(tempIndex, currentIndex);
				if (tempString.contains("the-dire object"))
				{
					String minute = substringer(logLine[j], "time\">", "</span>");
					minute = minute.substring(6, minute.length());
					minute = minute.substring(0, 2);
					currentIndex = logLine[j].indexOf("the-radiant object");
					tempString = logLine[j].substring(currentIndex, logLine[j].length());

					currentIndex = tempString.indexOf("</a>");
					tempString = tempString.substring(0, currentIndex);
					tempIndex = tempString.lastIndexOf("/>");
					tempString = tempString.substring(tempIndex + 3, tempString.length());
					String whoDied = tempString;

					String lostGold;
					if (logLine[j].contains("lost"))
					{
						tempIndex = logLine[j].indexOf("lost");
						currentIndex = logLine[j].indexOf("</span>", tempIndex);
						tempString = logLine[j].substring(tempIndex, currentIndex);
						lostGold = tempString;
						currentIndex = lostGold.lastIndexOf("\">");
						lostGold = lostGold.substring(currentIndex + 2, lostGold.length());
					} else lostGold = "300";

					for (int i = 0; i < 5; i++)
					{
						if (player[i].hero.equals(whoDied))
						{
							if (player[i].minuteGPM[Integer.parseInt(minute)] != 9999)
								player[i].minuteGPM[Integer.parseInt(minute)] = player[i].minuteGPM[Integer.parseInt(minute)] - Integer.parseInt(lostGold);
							else
								player[i].minuteGPM[Integer.parseInt(minute) - 1] = player[i].minuteGPM[Integer.parseInt(minute) - 1] - Integer.parseInt(lostGold);
						}
					}
				}
			}
		}

		for (int i = 0; i < 5; i++)
		{
			int sum = 0;
			for (int j = 0; j < 150; j++)
			{
				if (player[i].minuteGPM[j] == 9999)
					break;
				else
					sum += player[i].minuteGPM[j];
			}
			player[i].totalGold = sum;
		}

		for (int i = 0; i < 5; i++)
		{
			int GPM_sum = 0;
			int counter = 0;
			for (int j = 0; j < player[i].minuteGPM.length; j++)
			{
				if (player[i].minuteGPM[j] != 9999)
					GPM_sum += player[i].minuteGPM[j];
				else break;

				if ((j + 1) % 5 == 0 && j != 0)
				{
					player[i].fiveMinuteNetWorth[counter] = GPM_sum;
					counter++;
				}
			}
		}

		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j].minuteGPM[i] != 9999)
					team[0].minuteGPM[i] += player[j].minuteGPM[i];
			}
		}
		for (int i = 0; i < 150; i++)
		{
			if (team[0].minuteGPM[i] != 9999)
				team[0].totalGold += team[0].minuteGPM[i];
		}

		int totalGoldTeamRadiantTemp = 0;
		int tempRadiantCounter = 0;
		for (int i = 0; i < 150; i++)
		{
			if (team[0].minuteGPM[i] != 0)
			{
				totalGoldTeamRadiantTemp += team[0].minuteGPM[i];
			}
			if ((i + 1) % 5 == 0 && i != 0)
			{
				team[0].fiveMinuteNetWorth[tempRadiantCounter] = totalGoldTeamRadiantTemp;
				tempRadiantCounter++;
			}
		}
		//</editor-fold>
		//<editor-fold desc="Dire NET WORTH">
		for (int i = 0; i < 5; i++)
		{
			tempString = substringer(farmPageDireHeroLine[i], "<td class=\"r-tab r-group-2 cell-divider\"", "</td>");
			tempString = removeTag(tempString, "span");
			tempString = removeTag(tempString, "td");
			tempString = removeTag(tempString, "div");
			tempIndex = tempString.indexOf(" ");
			tempString = tempString.substring(tempIndex, tempString.length());
			tempString = tempString.replaceAll(" ", "");
			tempString = tempString.replaceAll("\n", "");
			String[] tempGPMArray = tempString.split(",");
			for (int j = 0; j < tempGPMArray.length; j++)
			{
				player[i + 5].minuteGPM[j] = Integer.parseInt(tempGPMArray[j]);
			}
		}
		for (int j = 0; j < logLine.length; j++)
		{
			if (logLine[j].contains("killed") && !logLine[j].contains("Roshan") && !logLine[j].contains("The Radiant") && !logLine[j].contains("The Dire"))
			{
				tempIndex = logLine[j].indexOf("<a ");
				currentIndex = logLine[j].indexOf("</a>", tempIndex);
				tempString = logLine[j].substring(tempIndex, currentIndex);
				if (tempString.contains("the-radiant object"))
				{
					String minute = substringer(logLine[j], "time\">", "</span>");
					minute = minute.substring(6, minute.length());
					minute = minute.substring(0, 2);
					currentIndex = logLine[j].indexOf("the-dire object");
					tempString = logLine[j].substring(currentIndex, logLine[j].length());

					currentIndex = tempString.indexOf("</a>");
					tempString = tempString.substring(0, currentIndex);
					tempIndex = tempString.lastIndexOf("/>");
					tempString = tempString.substring(tempIndex + 3, tempString.length());
					String whoDied = tempString;

					String lostGold;
					if (logLine[j].contains("lost"))
					{
						tempIndex = logLine[j].indexOf("lost");
						currentIndex = logLine[j].indexOf("</span>", tempIndex);
						tempString = logLine[j].substring(tempIndex, currentIndex);
						lostGold = tempString;
						currentIndex = lostGold.lastIndexOf("\">");
						lostGold = lostGold.substring(currentIndex + 2, lostGold.length());
					} else lostGold = "300";

					for (int i = 0; i < 5; i++)
					{
						if (player[i + 5].hero.equals(whoDied))
						{
							if (player[i + 5].minuteGPM[Integer.parseInt(minute)] != 9999)
								player[i + 5].minuteGPM[Integer.parseInt(minute)] = player[i + 5].minuteGPM[Integer.parseInt(minute)] - Integer.parseInt(lostGold);
							else
								player[i + 5].minuteGPM[Integer.parseInt(minute) - 1] = player[i + 5].minuteGPM[Integer.parseInt(minute) - 1] - Integer.parseInt(lostGold);

						}
					}
				}
			}
		}
		for (int i = 0; i < 5; i++)
		{
			int sum = 0;
			for (int j = 0; j < 150; j++)
			{
				if (player[i + 5].minuteGPM[j] == 9999)
					break;
				else
					sum += player[i + 5].minuteGPM[j];
			}
			player[i + 5].totalGold = sum;
		}
		for (int i = 0; i < 5; i++)
		{
			int GPM_sum = 0;
			int counter = 0;
			for (int j = 0; j < player[i + 5].minuteGPM.length; j++)
			{
				if (player[i + 5].minuteGPM[j] != 9999)
					GPM_sum += player[i + 5].minuteGPM[j];
				else break;

				if ((j + 1) % 5 == 0 && j != 0)
				{
					player[i + 5].fiveMinuteNetWorth[counter] = GPM_sum;
					counter++;
				}
			}
		}

		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j + 5].minuteGPM[i] != 9999)
					team[1].minuteGPM[i] += player[j + 5].minuteGPM[i];
			}
		}

		for (int i = 0; i < 150; i++)
		{
			if (team[1].minuteGPM[i] != 9999)
				team[1].totalGold += team[1].minuteGPM[i];
		}
		int totalGoldTeamDireTemp = 0;
		int tempDireCounter = 0;
		for (int i = 0; i < 150; i++)
		{
			if (team[1].minuteGPM[i] != 0)
			{
				totalGoldTeamDireTemp += team[1].minuteGPM[i];
			}
			if ((i + 1) % 5 == 0 && i != 0)
			{
				team[1].fiveMinuteNetWorth[tempDireCounter] = totalGoldTeamDireTemp;
				tempDireCounter++;
			}
		}
		//</editor-fold>
		//</editor-fold>

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
				tempString = removeTag(logLine[j], "div");
				tempString = removeTag(tempString, "span");
				currentIndex = tempString.indexOf("the-radiant object");
				tempIndex = tempString.indexOf("the-dire object");
				if (currentIndex < tempIndex)
				{
					if (fbbool == false)
					{
						currentIndex2 = tempString.indexOf("<p>");
						tempIndex2 = tempString.indexOf("<i");
						String fbtime = tempString.substring(currentIndex2 + 3, tempIndex2 - 3);
						match.FBTime = Integer.parseInt(fbtime);

						match.firstBloodRadiant = true;
						fbbool = true;
					}
					tempKillsRadiantCounter++;
				}
				if (currentIndex > tempIndex)
				{
					if (fbbool == false)
					{
						currentIndex2 = tempString.indexOf("<p>");
						tempIndex2 = tempString.indexOf("<i");
						String fbtime = tempString.substring(currentIndex2 + 3, tempIndex2 - 3);
						match.FBTime = Integer.parseInt(fbtime);

						match.firstBloodRadiant = false;
						fbbool = true;
					}
					tempKillsDireCounter++;
				}

				if (tempKillsRadiantCounter == 10 && f10kbool == false)
				{
					currentIndex2 = tempString.indexOf("<p>");
					tempIndex2 = tempString.indexOf("<i");
					String f10killstime = tempString.substring(currentIndex2 + 3, tempIndex2 - 3);
					match.F10KTime = Integer.parseInt(f10killstime);

					match.first10KillsRadiant = true;
					f10kbool = true;
				}

				if (tempKillsDireCounter == 10 && f10kbool == false)
				{
					currentIndex2 = tempString.indexOf("<p>");
					tempIndex2 = tempString.indexOf("<i");
					String f10killstime = tempString.substring(currentIndex2 + 3, tempIndex2 - 3);
					match.F10KTime = Integer.parseInt(f10killstime);

					match.first10KillsRadiant = false;
					f10kbool = true;
				}
			}
		}

		//TODO: TIMEDEAD

		//</editor-fold>

		//<editor-fold desc="GOLD EARNINGS:    ProjectDir.MatchInfo.Player: goldFromKills, goldLost, goldFed">
		//<editor-fold desc="Radiant GOLD EARNINGS">
		for (int i = 0; i < 5; i++)
		{
			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3");
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i].goldForKills = Integer.parseInt(tempString);

			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i].goldLost = Integer.parseInt(tempString);


			currentIndex = farmPageRadiantHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageRadiantHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i].goldFed = Integer.parseInt(tempString);
		}
		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3");
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[0].goldForKills = Integer.parseInt(tempString);

		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[0].goldLost = Integer.parseInt(tempString);


		currentIndex = farmPageRadiantTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageRadiantTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[0].goldFed = Integer.parseInt(tempString);
		//</editor-fold>
		//<editor-fold desc="Dire GOLD EARNINGS">
		for (int i = 0; i < 5; i++)
		{
			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3");
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i + 5].goldForKills = Integer.parseInt(tempString);

			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i + 5].goldLost = Integer.parseInt(tempString);


			currentIndex = farmPageDireHeroLine[i].indexOf("r-tab r-group-3", tempIndex + 15);
			tempIndex = farmPageDireHeroLine[i].indexOf("</td>", currentIndex + 15);
			tempString = farmPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTag(tempString, "div");
			currentIndex2 = tempString.indexOf("=\"");
			tempIndex2 = tempString.indexOf("\">");
			tempString = tempString.substring(currentIndex2 + 2, tempIndex2);
			player[i + 5].goldFed = Integer.parseInt(tempString);
		}
		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3");
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[1].goldForKills = Integer.parseInt(tempString);

		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[1].goldLost = Integer.parseInt(tempString);


		currentIndex = farmPageDireTotalLine.indexOf("r-tab r-group-3", tempIndex + 15);
		tempIndex = farmPageDireTotalLine.indexOf("</td>", currentIndex + 15);
		tempString = farmPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTag(tempString, "div");
		currentIndex2 = tempString.indexOf("\">");
		tempString = tempString.substring(currentIndex2 + 2, tempString.length());
		tempString = tempString.replaceAll(",", "");
		tempString = tempString.replaceAll(" ", "");
		tempString = tempString.replaceAll("\n", "");
		team[1].goldFed = Integer.parseInt(tempString);

		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="LAST HITS:    ProjectDir.MatchInfo.Player: minuteLastHits, perMinuteLastHits ; ProjectDir.MatchInfo.Team: minuteLastHits, perMinuteLastHits; ">
		//<editor-fold desc="Radiant LastHits">
		for (int i = 0; i < 5; i++)
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
		}
		//</editor-fold>
		//<editor-fold desc="Dire LastHits">
		for (int i = 0; i < 5; i++)
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
				player[i + 5].perMinuteLastHits[j] = Integer.parseInt(minuteLastHits[j]);
				player[i + 5].minuteLastHits[j] = sum;
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
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="OBJECTIVES:     ProjectDir.MatchInfo.Player: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;  ProjectDir.MatchInfo.Team: TowersDestroyed, TowersDenied, RoshanKills, TowerDamage;">
		//<editor-fold desc="Radiant objectives">
		for (int i = 0; i < 5; i++)
		{
			//Towers destroyed and denied
			tempString = substringer(objectivesPageRadiantHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\">", "</td>");
			tempString = removeTags(tempString);
			String[] tempTowersSeparator = tempString.split("/");
			if (tempTowersSeparator.length != 2)
			{
				System.out.println("Towers destroyed/denied error.");
			}
			for (int j = 0; j < tempTowersSeparator.length; j++)
			{
				if (tempTowersSeparator[j].contains("-"))
					tempTowersSeparator[j] = "0";
			}
			player[i].towersDestroyed = Integer.parseInt(tempTowersSeparator[0]);
			player[i].towersDenied = Integer.parseInt(tempTowersSeparator[1]);
			//TowersDamage
			tempString = substringer(objectivesPageRadiantHeroLine[i], "<td class=\"r-tab r-group-2 cell-centered", "</td>");
			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(",", "");
			if (tempString.contains("-"))
				tempString = "0";
			player[i].towerDamage = Integer.parseInt(tempString);
			//RoshanKills
			currentIndex = objectivesPageRadiantHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-1 cell-centered");
			tempIndex = objectivesPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = objectivesPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i].roshanKills = Integer.parseInt(tempString);
		}
		//Towers destroyed and denied
		tempString = substringer(objectivesPageRadiantTotalLine, "<td class=\"r-tab r-group-1 cell-centered\">", "</td>");
		tempString = removeTags(tempString);
		String[] tempTowersSeparator = tempString.split("/");
		if (tempTowersSeparator.length != 2)
		{
			System.out.println("Towers destroyed/denied error.");
		}
		for (int j = 0; j < tempTowersSeparator.length; j++)
		{
			if (tempTowersSeparator[j].contains("-"))
				tempTowersSeparator[j] = "0";
		}
		team[0].towersDestroyed = Integer.parseInt(tempTowersSeparator[0]);
		team[0].towersDenied = Integer.parseInt(tempTowersSeparator[1]);
		//TowersDamage
		tempString = substringer(objectivesPageRadiantTotalLine, "<td class=\"r-tab r-group-2 cell-centered", "</td>");
		tempString = removeTags(tempString);
		tempString = tempString.replaceAll(",", "");
		if (tempString.contains("-"))
			tempString = "0";
		team[0].towerDamage = Integer.parseInt(tempString);
		//RoshanKills
		currentIndex = objectivesPageRadiantTotalLine.lastIndexOf("<td class=\"r-tab r-group-1 cell-centered");
		tempIndex = objectivesPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = objectivesPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].roshanKills = Integer.parseInt(tempString);

		//</editor-fold>
		//<editor-fold desc="Dire objectives">

		for (int i = 0; i < 5; i++)
		{
			//Towers destroyed and denied
			tempString = substringer(objectivesPageDireHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\">", "</td>");
			tempString = removeTags(tempString);
			String[] tempTowersSeparatorDire = tempString.split("/");
			if (tempTowersSeparatorDire.length != 2)
			{
				System.out.println("Towers destroyed/denied error.");
			}
			for (int j = 0; j < tempTowersSeparatorDire.length; j++)
			{
				if (tempTowersSeparatorDire[j].contains("-"))
					tempTowersSeparatorDire[j] = "0";
			}
			player[i + 5].towersDestroyed = Integer.parseInt(tempTowersSeparatorDire[0]);
			player[i + 5].towersDenied = Integer.parseInt(tempTowersSeparatorDire[1]);
			//TowersDamage
			tempString = substringer(objectivesPageDireHeroLine[i], "<td class=\"r-tab r-group-2 cell-centered", "</td>");
			tempString = removeTags(tempString);
			tempString = tempString.replaceAll(",", "");
			if (tempString.contains("-"))
				tempString = "0";
			player[i + 5].towerDamage = Integer.parseInt(tempString);
			//RoshanKills
			currentIndex = objectivesPageDireHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-1 cell-centered");
			tempIndex = objectivesPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = objectivesPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i + 5].roshanKills = Integer.parseInt(tempString);
		}
		//Towers destroyed and denied
		tempString = substringer(objectivesPageDireTotalLine, "<td class=\"r-tab r-group-1 cell-centered\">", "</td>");
		tempString = removeTags(tempString);
		String[] tempTowersSeparatorDire = tempString.split("/");
		if (tempTowersSeparatorDire.length != 2)
		{
			System.out.println("Towers destroyed/denied error.");
		}
		for (int j = 0; j < tempTowersSeparatorDire.length; j++)
		{
			if (tempTowersSeparatorDire[j].contains("-"))
				tempTowersSeparatorDire[j] = "0";
		}
		team[1].towersDestroyed = Integer.parseInt(tempTowersSeparatorDire[0]);
		team[1].towersDenied = Integer.parseInt(tempTowersSeparatorDire[1]);
		//TowersDamage
		tempString = substringer(objectivesPageDireTotalLine, "<td class=\"r-tab r-group-2 cell-centered", "</td>");
		tempString = removeTags(tempString);
		tempString = tempString.replaceAll(",", "");
		if (tempString.contains("-"))
			tempString = "0";
		team[1].towerDamage = Integer.parseInt(tempString);
		//RoshanKills
		currentIndex = objectivesPageDireTotalLine.lastIndexOf("<td class=\"r-tab r-group-1 cell-centered");
		tempIndex = objectivesPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = objectivesPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].roshanKills = Integer.parseInt(tempString);

		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="VISION: Wards, Smokes, Dusts, Gems">
		//<editor-fold desc="Radiant Vision">
		for (int i = 0; i < 5; i++)
		{
			//Wards
			tempString = substringer(visionPageRadiantHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\"", "</td>");
			tempString = removeTags(tempString);
			String[] visionSeparator = tempString.split("/");
			for (int j = 0; j < 2; j++)
			{
				if (visionSeparator[j].contains("-"))
					visionSeparator[j] = "0";
			}
			player[i].observerWardsPlaced = Integer.parseInt(visionSeparator[0]);
			player[i].sentryWardsPlaced = Integer.parseInt(visionSeparator[1]);

			currentIndex = visionPageRadiantHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-1");
			tempIndex = visionPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			visionSeparator = tempString.split("/");
			for (int j = 0; j < 2; j++)
			{
				if (visionSeparator[j].contains("-"))
					visionSeparator[j] = "0";
			}
			player[i].observerWardsDestroyed = Integer.parseInt(visionSeparator[0]);
			player[i].sentryWardsDestroyed = Integer.parseInt(visionSeparator[1]);
			//Dusts
			currentIndex = visionPageRadiantHeroLine[i].indexOf("<td class=\"r-tab r-group-2");
			currentIndex = visionPageRadiantHeroLine[i].indexOf("<td class=\"r-tab r-group-2", currentIndex + 1);
			tempIndex = visionPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i].dustHits = Integer.parseInt(tempString);

			currentIndex = visionPageRadiantHeroLine[i].indexOf("<td class=\"r-tab r-group-2", tempIndex);
			tempIndex = visionPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			tempString = tempString.replaceAll("%", "");
			player[i].dustAccuracy = Integer.parseInt(tempString);
			//Smokes
			tempString = substringer(visionPageRadiantHeroLine[i], "<td class=\"r-tab r-group-3 cell-centered", "</td>");
			tempString = removeTags(tempString);
			visionSeparator = tempString.split("/");
			if (visionSeparator[1].contains("-"))
				visionSeparator[1] = "0";
			player[i].smokeHits = Integer.parseInt(visionSeparator[1]);

			currentIndex = visionPageRadiantHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-3");
			tempIndex = visionPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i].smokeTotalHeroes = Integer.parseInt(tempString);
			//Gems
			currentIndex = visionPageRadiantHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-4");
			tempIndex = visionPageRadiantHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageRadiantHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
			{
				tempString = "0";
				player[i].gemTimeCarried = Integer.parseInt(tempString);
			} else
			{
				String[] timeSeparator = tempString.split(":");
				player[i].gemTimeCarried = 60 * Integer.parseInt(timeSeparator[0]) + Integer.parseInt(timeSeparator[1]);
			}
		}

		//Wards
		tempString = substringer(visionPageRadiantTotalLine, "<td class=\"r-tab r-group-1 cell-centered\"", "</td>");
		tempString = removeTags(tempString);
		String[] visionSeparator = tempString.split("/");
		for (int j = 0; j < 2; j++)
		{
			if (visionSeparator[j].contains("-"))
				visionSeparator[j] = "0";
		}
		team[0].observerWardsPlaced = Integer.parseInt(visionSeparator[0]);
		team[0].sentryWardsPlaced = Integer.parseInt(visionSeparator[1]);

		currentIndex = visionPageRadiantTotalLine.lastIndexOf("<td class=\"r-tab r-group-1");
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		visionSeparator = tempString.split("/");
		for (int j = 0; j < 2; j++)
		{
			if (visionSeparator[j].contains("-"))
				visionSeparator[j] = "0";
		}
		team[0].observerWardsDestroyed = Integer.parseInt(visionSeparator[0]);
		team[0].sentryWardsDestroyed = Integer.parseInt(visionSeparator[1]);
		//Dusts
		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-2");
		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-2", currentIndex + 1);
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].dustHits = Integer.parseInt(tempString);

		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-2", tempIndex);
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		tempString = tempString.replaceAll("%", "");
		team[0].dustAccuracy = Integer.parseInt(tempString);
		//Smokes
		tempString = substringer(visionPageRadiantTotalLine, "<td class=\"r-tab r-group-3 cell-centered", "</td>");
		tempString = removeTags(tempString);
		visionSeparator = tempString.split("/");
		if (visionSeparator[1].contains("-"))
			visionSeparator[1] = "0";
		team[0].smokeHits = Integer.parseInt(visionSeparator[1]);

		currentIndex = visionPageRadiantTotalLine.lastIndexOf("<td class=\"r-tab r-group-3");
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].smokeTotalHeroes = Integer.parseInt(tempString);
		//Gems
		currentIndex = visionPageRadiantTotalLine.lastIndexOf("<td class=\"r-tab r-group-4");
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
		{
			tempString = "0";
			team[0].gemTimeCarried = Integer.parseInt(tempString);
		} else
		{
			String[] timeSeparator = tempString.split(":");
			team[0].gemTimeCarried = 60 * Integer.parseInt(timeSeparator[0]) + Integer.parseInt(timeSeparator[1]);
		}
		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-4");
		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-4", currentIndex + 1);
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].gemsDropped = Integer.parseInt(tempString);

		currentIndex = visionPageRadiantTotalLine.indexOf("<td class=\"r-tab r-group-4");
		tempIndex = visionPageRadiantTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageRadiantTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[0].gemsBought = Integer.parseInt(tempString);
		//</editor-fold>
		//<editor-fold desc="Dire Vision">
		for (int i = 0; i < 5; i++)
		{
			//Wards
			tempString = substringer(visionPageDireHeroLine[i], "<td class=\"r-tab r-group-1 cell-centered\"", "</td>");
			tempString = removeTags(tempString);
			visionSeparator = tempString.split("/");
			for (int j = 0; j < 2; j++)
			{
				if (visionSeparator[j].contains("-"))
					visionSeparator[j] = "0";
			}
			player[i + 5].observerWardsPlaced = Integer.parseInt(visionSeparator[0]);
			player[i + 5].sentryWardsPlaced = Integer.parseInt(visionSeparator[1]);

			currentIndex = visionPageDireHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-1");
			tempIndex = visionPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			visionSeparator = tempString.split("/");
			for (int j = 0; j < 2; j++)
			{
				if (visionSeparator[j].contains("-"))
					visionSeparator[j] = "0";
			}
			player[i + 5].observerWardsDestroyed = Integer.parseInt(visionSeparator[0]);
			player[i + 5].sentryWardsDestroyed = Integer.parseInt(visionSeparator[1]);
			//Dusts
			currentIndex = visionPageDireHeroLine[i].indexOf("<td class=\"r-tab r-group-2");
			currentIndex = visionPageDireHeroLine[i].indexOf("<td class=\"r-tab r-group-2", currentIndex + 1);
			tempIndex = visionPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i + 5].dustHits = Integer.parseInt(tempString);

			currentIndex = visionPageDireHeroLine[i].indexOf("<td class=\"r-tab r-group-2", tempIndex);
			tempIndex = visionPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			tempString = tempString.replaceAll("%", "");
			player[i + 5].dustAccuracy = Integer.parseInt(tempString);
			//Smokes
			tempString = substringer(visionPageDireHeroLine[i], "<td class=\"r-tab r-group-3 cell-centered", "</td>");
			tempString = removeTags(tempString);
			visionSeparator = tempString.split("/");
			if (visionSeparator[1].contains("-"))
				visionSeparator[1] = "0";
			player[i + 5].smokeHits = Integer.parseInt(visionSeparator[1]);

			currentIndex = visionPageDireHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-3");
			tempIndex = visionPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
				tempString = "0";
			player[i + 5].smokeTotalHeroes = Integer.parseInt(tempString);
			//Gems
			currentIndex = visionPageDireHeroLine[i].lastIndexOf("<td class=\"r-tab r-group-4");
			tempIndex = visionPageDireHeroLine[i].indexOf("</td>", currentIndex);
			tempString = visionPageDireHeroLine[i].substring(currentIndex, tempIndex);
			tempString = removeTags(tempString);
			if (tempString.contains("-"))
			{
				tempString = "0";
				player[i + 5].gemTimeCarried = Integer.parseInt(tempString);
			} else
			{
				String[] timeSeparator = tempString.split(":");
				player[i + 5].gemTimeCarried = 60 * Integer.parseInt(timeSeparator[0]) + Integer.parseInt(timeSeparator[1]);
			}
		}
		//Wards
		tempString = substringer(visionPageDireTotalLine, "<td class=\"r-tab r-group-1 cell-centered\"", "</td>");
		tempString = removeTags(tempString);
		visionSeparator = tempString.split("/");
		for (int j = 0; j < 2; j++)
		{
			if (visionSeparator[j].contains("-"))
				visionSeparator[j] = "0";
		}
		team[1].observerWardsPlaced = Integer.parseInt(visionSeparator[0]);
		team[1].sentryWardsPlaced = Integer.parseInt(visionSeparator[1]);

		currentIndex = visionPageDireTotalLine.lastIndexOf("<td class=\"r-tab r-group-1");
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		visionSeparator = tempString.split("/");
		for (int j = 0; j < 2; j++)
		{
			if (visionSeparator[j].contains("-"))
				visionSeparator[j] = "0";
		}
		team[1].observerWardsDestroyed = Integer.parseInt(visionSeparator[0]);
		team[1].sentryWardsDestroyed = Integer.parseInt(visionSeparator[1]);
		//Dusts
		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-2");
		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-2", currentIndex + 1);
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].dustHits = Integer.parseInt(tempString);

		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-2", tempIndex);
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		tempString = tempString.replaceAll("%", "");
		team[1].dustAccuracy = Integer.parseInt(tempString);
		//Smokes
		tempString = substringer(visionPageDireTotalLine, "<td class=\"r-tab r-group-3 cell-centered", "</td>");
		tempString = removeTags(tempString);
		visionSeparator = tempString.split("/");
		if (visionSeparator[1].contains("-"))
			visionSeparator[1] = "0";
		team[1].smokeHits = Integer.parseInt(visionSeparator[1]);

		currentIndex = visionPageDireTotalLine.lastIndexOf("<td class=\"r-tab r-group-3");
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].smokeTotalHeroes = Integer.parseInt(tempString);
		//Gems
		currentIndex = visionPageDireTotalLine.lastIndexOf("<td class=\"r-tab r-group-4");
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
		{
			tempString = "0";
			team[1].gemTimeCarried = Integer.parseInt(tempString);
		} else
		{
			String[] timeSeparator = tempString.split(":");
			team[1].gemTimeCarried = 60 * Integer.parseInt(timeSeparator[0]) + Integer.parseInt(timeSeparator[1]);
		}
		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-4");
		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-4", currentIndex + 1);
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].gemsDropped = Integer.parseInt(tempString);

		currentIndex = visionPageDireTotalLine.indexOf("<td class=\"r-tab r-group-4");
		tempIndex = visionPageDireTotalLine.indexOf("</td>", currentIndex);
		tempString = visionPageDireTotalLine.substring(currentIndex, tempIndex);
		tempString = removeTags(tempString);
		if (tempString.contains("-"))
			tempString = "0";
		team[1].gemsBought = Integer.parseInt(tempString);
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="ROLES DETECTOR: Player.role">
		/**Radiant**/
		for (int i = 0; i < mainPageRadiantHeroLine.length - 1; i++)
		{
			tempString = substringer(mainPageRadiantHeroLine[i], "<div class=\"lanes\">", "</td>");
			tempString = tempString.substring(110, tempString.length());
			fillRolesDetectorInfo(player, tempString, i);
		}
		/**Dire**/
		for (int i = 0; i < mainPageDireHeroLine.length - 1; i++)
		{
			tempString = substringer(mainPageDireHeroLine[i], "<div class=\"lanes\">", "</td>");
			tempString = tempString.substring(110, tempString.length());
			fillRolesDetectorInfo(player, tempString, i + 5);
		}
		rolesDetector(player);
		checkIfMiderExist(player);
		checkIfCarryExist(player);
		checkIfHardlinerExist(player);
		//</editor-fold>

		//<editor-fold desc="EXPIRIENCE: Player.minuteXPM, Team. minuteXPM"
		//<editor-fold desc="Radiant Expirience">
		for (int i = 0; i < 5; i++)
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
		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j].minuteXPM[i] != 9999)
				{
					team[0].minuteXPM[i] += player[j].minuteXPM[i];
				}
			}
		}
		//</editor-fold>
		//<editor-fold desc="Dire Expirience">
		for (int i = 0; i < 5; i++)
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
		for (int i = 0; i < 150; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (player[j + 5].minuteXPM[i] != 9999)
					team[1].minuteXPM[i] += player[j + 5].minuteXPM[i];
			}
		}
		//</editor-fold>
		//</editor-fold>

		//<editor-fold desc="GLYPHS AND TOWERS">
		//<editor-fold desc="Glyphs">
		//	ArrayList<GlyphEvent> glyphArrayList = new ArrayList<GlyphEvent>();
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("Glyph of Fortification"))
			{
				GlyphEvent glyphik = new GlyphEvent();
				tempString = substringer(logLine[i], "<span class=\"time", "</span>");
				tempString = removeTags(tempString);
				glyphik.second = mapTimeToSeconds(tempString);
				if (logLine[i].contains("The Radiant"))
					glyphik.side = 1;
				else if (logLine[i].contains("The Dire"))
					glyphik.side = 2;
				else System.out.println("Glyph Identification Error.");


				glyphEventArrayList.add(glyphik);
			}
		}
		//	glyphEventArrayList=glyphArrayList;
		//</editor-fold>
		//<editor-fold desc="Towers">
		//ArrayList<TowerEvent> towerArrayList = new ArrayList<TowerEvent>();
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("Tier"))
			{
				TowerEvent towerchik = new TowerEvent();
				tempString = substringer(logLine[i], "<span class=\"time", "</span>");
				tempString = removeTags(tempString);
				towerchik.second = mapTimeToSeconds(tempString);


				if (!logLine[i].contains("a href"))
				{
					if (tempString.contains("The Radiant"))
					{
						towerchik.whoDestroy = "Radiant";
					} else
						towerchik.whoDestroy = "Dire";
				} else
				{
					tempString = substringer(logLine[i], "<a href", "</a>");
					if (tempString.contains("the-dire object"))
					{
						if (tempString.contains("denies"))
							towerchik.whoDestroy = "RadiantButDenied";
						else
							towerchik.whoDestroy = "Dire";
					} else
					{
						if (tempString.contains("denies"))
							towerchik.whoDestroy = "DireButDenied";
						else
							towerchik.whoDestroy = "Radiant";
					}
				}
				if (logLine[i].contains("Top"))
					towerchik.line = "top";
				else if (logLine[i].contains("Bottom"))
					towerchik.line = "bottom";
				else if (logLine[i].contains("Middle"))
					towerchik.line = "middle";
				else
					towerchik.line = "middle";
				tempString = substringer(logLine[i], "Tier", "Tower");
				if (tempString.contains("1"))
					towerchik.tierLevel = 1;
				if (tempString.contains("2"))
					towerchik.tierLevel = 2;
				if (tempString.contains("3"))
					towerchik.tierLevel = 3;
				if (tempString.contains("4"))
					towerchik.tierLevel = 4;
				towerEventArrayList.add(towerchik);
			}
		}
		//towerEventArrayList=towerArrayList;
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
			if (logLine[i].contains("killed") && !logLine[i].contains("Roshan") && !logLine[i].contains("The Radiant") && !logLine[i].contains("The Dire"))
			{
				currentIndex = logLine[i].indexOf("the-dire object");
				tempIndex = logLine[i].indexOf("the-radiant object");
				if (currentIndex < tempIndex)
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
				if (tempIndex < currentIndex)
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

		//<editor-fold desc="BUYBACKS">
		//	ArrayList<BuyBackEvent> buyBackArrayList = new ArrayList<BuyBackEvent>();
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("bought back"))
			{
				BuyBackEvent buybacker = new BuyBackEvent();
				tempString = substringer(logLine[i], "<span class=\"time", "</span>");
				tempString = removeTags(tempString);
				buybacker.second = mapTimeToSeconds(tempString);
				tempString = substringer(logLine[i], "<span class=\"gold", "</span>");
				tempString = removeTags(tempString);
				buybacker.goldCost = Integer.parseInt(tempString);
				tempString = substringer(logLine[i], "<a href", "</a>");
				tempString = removeTags(tempString);
				tempString = tempString.replaceFirst(" ", "");
				buybacker.whoBoughtBack = tempString;
				buyBackEventArrayList.add(buybacker);
			}
		}
		//buyBackEventArrayList=buyBackArrayList;
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
		tempString = substringer(stringMainPage, "datetime=\"", "title");
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
		team[0].rating = ratingFactory.getRatingById(team[0].id, team[0].name);
		team[1].rating = ratingFactory.getRatingById(team[1].id, team[1].name);
		//</editor-fold>

		//<editor-fold desc="RAXES COUNT">
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("Barracks"))
			{
				if (logLine[i].contains("the-dire object"))
					team[0].raxesRemain--;
				else
					team[1].raxesRemain--;
			}
		}
		//</editor-fold>

		//<editor-fold desc="RoshanEvents">
		for (int i = 0; i < logLine.length; i++)
		{
			if (logLine[i].contains("Roshan"))
			{
				RoshanEvent roshik = new RoshanEvent();
				if (logLine[i].contains("Radiant"))
					roshik.whoKill = "Radiant";
				else
					roshik.whoKill = "Dire";
				tempString = substringer(logLine[i], "<span class=\"time", "</span>");
				tempString = removeTags(tempString);
				roshik.second = mapTimeToSeconds(tempString);
				roshanEventArrayList.add(roshik);
			}
		}
		//</editor-fold>

		return true;
	}

	void doTests(Player[] players, Match match, Team[] teams)
	{
		System.out.println("Player statisticses:");
		for (int i = 0; i < players.length; i++)
		{
			players[i].showPlayerStatistics();
			System.out.println("--------------------------------------------------------------");
		}
		System.out.println("Team statisticses:");
		teams[0].showTeamStatistics();
		System.out.println("--------------------------------------------------------------");
		teams[1].showTeamStatistics();
	}

	ArrayList<String> parseMatches(String[] leagueLinks) throws IOException, InterruptedException, ParseException
	{
		//TODO: function gets only first page of matches in given league.
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH");
		Document doc;
		Date date = new Date();
		String tempString;
		String html;
		String parseDate;

		/***********Here is parsing date.*********/
		String currentDate = ft.format(date);
		Document[] docs = new Document[leagueLinks.length];
		ArrayList<String> leaguesToParse = new ArrayList<>();
		ArrayList<String> matchesToParse = new ArrayList<>();
		leaguesToParse = uniqueInfoFactory.checkIfLeagueParsed(leagueLinks);

		for (int i = 0; i < leaguesToParse.size(); i++)
		{
			System.out.println("Read " + leaguesToParse.get(i) + " league.");
			docs[i] = parse_html("http://www.dotabuff.com/esports/leagues/" + leaguesToParse.get(i) + "/matches");
			if (i == 0)
				fileOperationsFactory.writeToFile("\n" + leaguesToParse.get(i) + ";" + currentDate, "files/LeaguesParsed.txt");
			else
				fileOperationsFactory.writeToFile(leaguesToParse.get(i) + ";" + currentDate, "files/LeaguesParsed.txt");
			html = docs[i].toString();
			html = substringer(html, "<tbody>", "</tbody>");
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

	void fillRolesDetectorInfo(Player[] players, String input, Integer number)
	{
		//<editor-fold desc="Count lines">
		String subinput;
		Integer botSafe = 0;
		Integer topOff = 0;
		Integer middle = 0;
		Integer roaming = 0;
		Integer jungle = 0;
		Integer topSafe = 0;
		Integer botOff = 0;
		subinput = "Bottom (Safe)";
		int lastIndex = 0;
		int count = 0;

		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		botSafe = count;

		lastIndex = 0;
		count = 0;
		subinput = "Bottom (Off)";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		botOff = count;

		lastIndex = 0;
		count = 0;
		subinput = "Top (Off)";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		topOff = count;

		lastIndex = 0;
		count = 0;
		subinput = "Top (Safe)";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		topSafe = count;

		lastIndex = 0;
		count = 0;
		subinput = "Middle";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		middle = count;

		lastIndex = 0;
		count = 0;
		subinput = "Roaming";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		roaming = count;
		if (number < 5)
			radiantRoaming += count;
		else
			direRoaming += count;

		lastIndex = 0;
		count = 0;
		subinput = "Dire Jungle";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}

		lastIndex = 0;
		subinput = "Radiant Jungle";
		while (lastIndex != -1)
		{

			lastIndex = input.indexOf(subinput, lastIndex);

			if (lastIndex != -1)
			{
				count++;
				lastIndex += subinput.length();
			}
		}
		jungle += count;
		//</editor-fold>
		players[number].jungle = jungle;
		players[number].safeLine = topSafe + botSafe;
		players[number].offLine = topOff + botOff;
		players[number].middle = middle;
		players[number].roaming = roaming;
		Integer[] networthPositionArray = new Integer[5];
		//<editor-fold desc="NetWorthPosition">
		if (number < 5)
		{
			networthPositionArray[0] = players[0].totalGold;
			networthPositionArray[1] = players[1].totalGold;
			networthPositionArray[2] = players[2].totalGold;
			networthPositionArray[3] = players[3].totalGold;
			networthPositionArray[4] = players[4].totalGold;
			Arrays.sort(networthPositionArray);
			for (int i = 0; i < 5; i++)
			{
				if (players[i].totalGold == networthPositionArray[0])
				{
					players[i].networthPosition = 5;
				} else if (players[i].totalGold == networthPositionArray[1])
				{
					players[i].networthPosition = 4;
				} else if (players[i].totalGold == networthPositionArray[2])
				{
					players[i].networthPosition = 3;
				} else if (players[i].totalGold == networthPositionArray[3])
				{
					players[i].networthPosition = 2;
				} else if (players[i].totalGold == networthPositionArray[4])
				{
					players[i].networthPosition = 1;
				}
			}
		} else
		{
			networthPositionArray[0] = players[5].totalGold;
			networthPositionArray[1] = players[6].totalGold;
			networthPositionArray[2] = players[7].totalGold;
			networthPositionArray[3] = players[8].totalGold;
			networthPositionArray[4] = players[9].totalGold;
			Arrays.sort(networthPositionArray);
			for (int i = 0; i < 5; i++)
			{
				if (players[i + 5].totalGold == networthPositionArray[0])
				{
					players[i + 5].networthPosition = 5;
				} else if (players[i + 5].totalGold == networthPositionArray[1])
				{
					players[i + 5].networthPosition = 4;
				} else if (players[i + 5].totalGold == networthPositionArray[2])
				{
					players[i + 5].networthPosition = 3;
				} else if (players[i + 5].totalGold == networthPositionArray[3])
				{
					players[i + 5].networthPosition = 2;
				} else if (players[i + 5].totalGold == networthPositionArray[4])
				{
					players[i + 5].networthPosition = 1;
				}
			}
		}
		//</editor-fold>
	}

	void rolesDetector(Player[] player)
	{
		for (int i = 0; i < 10; i++)
		{
			//Jungler
			if (player[i].jungle > player[i].middle && player[i].jungle > player[i].safeLine && player[i].jungle > player[i].offLine && player[i].jungle > player[i].roaming)
			{
				player[i].role = 5;
				continue;
			}
			//Hardliner
			if ((player[i].offLine > player[i].middle && player[i].offLine > player[i].jungle && player[i].offLine > player[i].safeLine && player[i].offLine >= player[i].roaming) || (player[i].safeLine > player[i].middle && player[i].safeLine > player[i].jungle && player[i].safeLine > player[i].offLine && player[i].safeLine >= player[i].roaming))
			{
				Boolean offExist = false;
				Boolean safeExist = false;
				if (i < 5)
				{
					for (int j = 0; j < 5; j++)
					{
						if (i == j)
							continue;
						if (player[j].safeLine > 1)
							safeExist = true;
						if (player[j].offLine > 1)
							offExist = true;
					}
				} else
				{
					for (int j = 5; j < 10; j++)
					{
						if (i == j)
							continue;
						if (player[j].safeLine > 1)
							safeExist = true;
						if (player[j].offLine > 1)
							offExist = true;
					}
				}
				if (player[i].offLine > player[i].middle && player[i].offLine > player[i].jungle && player[i].offLine > player[i].safeLine && player[i].offLine >= player[i].roaming)
					if (!offExist && player[i].networthPosition >= 3)
						player[i].role = 4;
				if (player[i].safeLine > player[i].middle && player[i].safeLine > player[i].jungle && player[i].safeLine > player[i].offLine && player[i].safeLine >= player[i].roaming)
					if (!safeExist && player[i].networthPosition >= 3)
						player[i].role = 4;

			}
			//Mider
			if (player[i].middle > player[i].safeLine && player[i].middle > player[i].offLine && player[i].middle > player[i].jungle && player[i].middle >= player[i].roaming)
			{
				if (player[i].networthPosition <= 2)
				{
					player[i].role = 1;
					continue;
				}
			}
			//Carry
			if (((player[i].offLine > player[i].middle && player[i].offLine > player[i].jungle && player[i].offLine > player[i].safeLine && player[i].offLine >= player[i].roaming) || (player[i].safeLine > player[i].middle && player[i].safeLine > player[i].jungle && player[i].safeLine > player[i].offLine && player[i].safeLine >= player[i].roaming)) && player[i].role == 0)
			{
				if (player[i].networthPosition <= 2)
				{
					player[i].role = 2;
					continue;
				}

			}
			if (player[i].networthPosition == 1 && player[i].middle <= 1)
			{
				player[i].role = 2;
				continue;
			}
			if (player[i].networthPosition >= 3 && player[i].role == 0 && (player[i].heroHeal >= 1000 || player[i].smokeHits != 0 || player[i].observerWardsPlaced != 0))
				player[i].role = 3;
			if (player[i].role == 0)
				player[i].role = 6;

		}
	}

	void checkIfCarryExist(Player[] player)
	{
		for (int i = 0; i < 10; i++)
		{
			if (player[i].role == 6 && player[i].networthPosition <= 3)
				player[i].role = 2;
		}
		//If no carry and hardliner has good <=3
	}

	void checkIfMiderExist(Player[] player)
	{
		for (int i = 0; i < 10; i++)
		{
			if (player[i].role == 6 && player[i].middle >= 2 && player[i].networthPosition <= 3)
			{
				player[i].role = 1;
			}
		}
	}

	void checkIfHardlinerExist(Player[] player)
	{
		for (int i = 0; i < 10; i++)
		{
			if (player[i].role == 6 && player[i].networthPosition <= 3)
				player[i].role = 4;
			if (player[i].role == 6 && player[i].networthPosition >= 4)
				player[i].role = 3;

		}
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
		if (hours >= 6)
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
		return string;
	}
}




