package ProjectDir;

import java.io.IOException;

public class TierWorker
{

	FileOperationsFactory fileOperationsFactory=new FileOperationsFactory();
	public String getLeagueTier(Integer leagueId, String leagueName) throws IOException
	{
		String file = readFile("files/LeaguesTier.txt");
		String[] eachLeagueInfo = file.split("\n");
		for (int i = 0; i < eachLeagueInfo.length; i++)
		{
			if (eachLeagueInfo[i].contains(leagueId.toString()))
			{
				return eachLeagueInfo[i].split(";")[2];
			}
		}
		System.out.println(leagueName + "(" + leagueId + ")" + " league wasn't founded. Please enter Prize Pool:");
		Integer prizePool = reader.nextInt();
		System.out.println("Enter league tier:");
		Integer tier = reader.nextInt();
		String writeString = leagueId + ";" + prizePool + ";" + tier;
		writeToFile(writeString, "files/LeaguesTier.txt");
		return tier.toString();
	}

	public String getTeamTier(String id)
	{

	}
}
