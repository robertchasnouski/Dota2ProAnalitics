package ProjectDir;

import ProjectDir.MatchInfo.AnalizedInfo;

import java.util.ArrayList;

public class DataWorker
{
	public void analizeFutureMatch(String team1Id, String team2Id)
	{

	}

	public void analizeTeamInfo(String teamString)
	{
		ArrayList<AnalizedInfo> objects = new ArrayList<AnalizedInfo>();
		fillObject(objects, teamString);

	}

	public void fillObject(ArrayList<AnalizedInfo> objects, String allMatches)
	{
		AnalizedInfo object = new AnalizedInfo();

		String matchGeneralInfo = allMatches.split("##")[0];
		String KDA = allMatches.split("##")[1];
		String playerEPPs = allMatches.split("##")[2];
		String parameters = allMatches.split("##")[3];
		String FBInfo = allMatches.split("##")[4];
		String F10KInfo = allMatches.split("##")[5];
		String FRInfo = allMatches.split("##")[6];
		String killEventsInfo = allMatches.split("##")[7];
		String ratingChanges = allMatches.split("##")[8];

		object.id = matchGeneralInfo.split(";")[0];
		object.date = matchGeneralInfo.split(";")[1];
		object.matchTime = matchGeneralInfo.split(";")[2];
		object.teamId = matchGeneralInfo.split(";")[3];
		object.isWin = Boolean.getBoolean(matchGeneralInfo.split(";")[4]);
		object.side = Integer.parseInt(matchGeneralInfo.split(";")[5]);

		object.kills = Integer.parseInt(KDA.split(";")[0]);
		object.deaths = Integer.parseInt(KDA.split(";")[1]);
		object.assists = Integer.parseInt(KDA.split(";")[2]);

		String[] eachEPPs = playerEPPs.split("\\|\\|");
		object.playerId[0] = eachEPPs[0].split(";")[0];
		object.playerEPP[0] = Integer.parseInt(eachEPPs[0].split(";")[1]);
		object.playerId[1] = eachEPPs[1].split(";")[0];
		object.playerEPP[1] = Integer.parseInt(eachEPPs[1].split(";")[1]);
		object.playerId[2] = eachEPPs[2].split(";")[0];
		object.playerEPP[2] = Integer.parseInt(eachEPPs[2].split(";")[1]);
		object.playerId[3] = eachEPPs[3].split(";")[0];
		object.playerEPP[3] = Integer.parseInt(eachEPPs[3].split(";")[1]);
		object.playerId[4] = eachEPPs[4].split(";")[0];
		object.playerEPP[4] = Integer.parseInt(eachEPPs[4].split(";")[1]);

		object.aggression = Integer.parseInt(parameters.split(";")[0]);
		object.pushing = Integer.parseInt(parameters.split(";")[1]);
		object.vision = Integer.parseInt(parameters.split(";")[2]);
		object.defence = Integer.parseInt(parameters.split(";")[3]);
		object.lining = Integer.parseInt(parameters.split(";")[4]);
		object.killAbility = Integer.parseInt(parameters.split(";")[5]);
		object.farming = Integer.parseInt(parameters.split(";")[6]);

		object.isFB = Boolean.getBoolean(FBInfo.split(";")[0]);
		object.FBTime = Integer.parseInt(FBInfo.split(";")[1]);

		object.isF10K = Boolean.getBoolean(F10KInfo.split(";")[0]);
		object.F10KTime = Integer.parseInt(F10KInfo.split(";")[1]);

		object.isFR = Boolean.getBoolean(FRInfo.split(";")[0]);
		object.FRTime = Integer.parseInt(FRInfo.split(";")[1]);


		object.mineRating = Integer.parseInt(ratingChanges.split(";")[1]);
		object.enemyRating = Integer.parseInt(ratingChanges.split(";")[2]);


		objects.add(object);
	}

	/*public Integer getAggression(String teamString)
	{

	}

	public Integer getPushing(String teamString)
	{

	}

	public Integer getVision(String teamString)
	{

	}

	public Integer getDefence(String teamString)
	{

	}

	public Integer getLining(String teamString)
	{

	}

	public Integer getKillAbility(String teamString)
	{

	}*/

}
