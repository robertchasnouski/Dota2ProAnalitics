package Analitics;

import MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	StringReader stringReader = new StringReader();


	public void analizeMatch(String stringFromFile) throws IOException
	{
		String[] tempSeparator1 = stringFromFile.split(";");
		String matchId = tempSeparator1[0];
		fileControlFactory.createTeamFileIfNotExists(matchId);
		//TODO: Writing match that it analized before succesful analysis(Must be after)
		if (!fileControlFactory.checkIfIdAlreadyParsed(matchId))
			fileControlFactory.writeToFile(matchId, "files/MatchesAnalized.txt");

		String matchString = stringReader.getMatchInfo(stringFromFile);
		String teamsString = stringReader.getTeamsInfo(stringFromFile);
		String playersString = stringReader.getPlayersInfo(stringFromFile);
		String killEventString = stringReader.getKillEvents(stringFromFile);
		String wardEventString = stringReader.getWardEvents(stringFromFile);
		String glyphEventString = stringReader.getGlyphEvents(stringFromFile);
		String buyBackEventString = stringReader.getBuyBackEvents(stringFromFile);
		String towerEventString = stringReader.getTowerEvents(stringFromFile);
		/**TeamID's**/
		String[] teamString = teamsString.split("||");
		String[] tempSeparator2 = teamString[0].split(";");
		String[] tempSeparator3 = teamString[1].split(";");
		String team1Id = tempSeparator2[0];
		String team2Id = tempSeparator3[0];
		fileControlFactory.createTeamFileIfNotExists(team1Id);
		fileControlFactory.createTeamFileIfNotExists(team2Id);
		/****/
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
		stringReader.fillArraysFromFile(stringFromFile, team, player, match, killEventArrayList, buyBackEventArrayList, glyphEventArrayList, towerEventArrayList, wardEventArrayList);

	}
}
