package ProjectDir.Analitics;

import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.util.ArrayList;

public class PrimaryAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();



	public void analizeMatch(String stringFromFile, Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList) throws IOException
	{
		String[] tempSeparator1 = stringFromFile.split(";");
		String matchId = tempSeparator1[0];

		/**Create team file if not exists**/
		fileControlFactory.createTeamFileIfNotExists(team[0].id);
		fileControlFactory.createTeamFileIfNotExists(team[1].id);
		/**Start analizing**/

		/**End analizing**/
		if (!fileControlFactory.checkIfIdAlreadyParsed(matchId))
			fileControlFactory.writeToFile(matchId, "files/MatchesAnalized.txt");
		else return;

	}

	public Integer analizeAggression()
	{
		return 1;
	}

	public Integer analizeDefence()
	{
		return 1;
	}
}
