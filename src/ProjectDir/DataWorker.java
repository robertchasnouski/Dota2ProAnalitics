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
		//ArrayList<AnalizedInfo> objects = new ArrayList<AnalizedInfo>();
		//fillObject(objects, teamString);
		//Integer avgAggression=getAverageAggression(objects);
	}

	public void fillObject(ArrayList<AnalizedInfo> objects, String allMatches)
	{
		String [] eachMatch=allMatches.split("\n");
		for (int i = 0; i < eachMatch.length; i++)
		{
			AnalizedInfo object = new AnalizedInfo();

			String matchGeneralInfo = eachMatch[i].split("##")[0];
			String KDA = eachMatch[i].split("##")[1];
			String playerEPPs = eachMatch[i].split("##")[2];
			String parameters = eachMatch[i].split("##")[3];
			String FBInfo = eachMatch[i].split("##")[4];
			String F10KInfo = eachMatch[i].split("##")[5];
			String FRInfo = eachMatch[i].split("##")[6];
			String killEventsInfo = eachMatch[i].split("##")[7];
			String ratingChanges = eachMatch[i].split("##")[8];

			object.id = matchGeneralInfo.split(";")[0];
			object.date = matchGeneralInfo.split(";")[1];
			object.matchTime = matchGeneralInfo.split(";")[2];
			object.teamId = matchGeneralInfo.split(";")[3];
			object.isWin = Boolean.getBoolean(matchGeneralInfo.split(";")[4]);
			object.side = Integer.parseInt(matchGeneralInfo.split(";")[5]);
			object.EG=Integer.parseInt(matchGeneralInfo.split(";")[6]);
			object.MG=Integer.parseInt(matchGeneralInfo.split(";")[7]);
			object.LG=Integer.parseInt(matchGeneralInfo.split(";")[8]);

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

			object.killAbility = Integer.parseInt(parameters.split(";")[0]);
			object.pushing = Integer.parseInt(parameters.split(";")[1]);
			object.vision = Integer.parseInt(parameters.split(";")[2]);
			object.defence = Integer.parseInt(parameters.split(";")[3]);
			object.lining = Integer.parseInt(parameters.split(";")[4]);
			object.farming = Integer.parseInt(parameters.split(";")[5]);

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
	}

	public Integer getAverageAggression(ArrayList<AnalizedInfo> objects)
	{
		Integer allAggression=0;
		Integer aggressionCounter=0;
		for (int i = 0; i < objects.size(); i++)
		{
			allAggression+=objects.get(i).aggression;
			aggressionCounter++;
		}
		Double aggression=(double)allAggression/aggressionCounter;
		return aggression.intValue();
	}

	public Integer getAveragePushing(ArrayList<AnalizedInfo> objects)
	{
		return 1;
	}
	public Integer getAverageVision(ArrayList<AnalizedInfo> objects)
	{
		return 1;
	}
	public Integer getAverageLining(ArrayList<AnalizedInfo> objects)
	{
		return 1;
	}

	public Integer getAverageKillAbility(ArrayList<AnalizedInfo> objects)
	{
		return 1;
	}


}
