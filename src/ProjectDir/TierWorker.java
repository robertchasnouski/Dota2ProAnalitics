package ProjectDir;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;

public class TierWorker
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	ParserFactory parserFactory=new ParserFactory();
	/*public String getLeagueTier(Integer leagueId, String leagueName) throws IOException
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
	}*/

	public String getTeamTier(String id) throws IOException, InterruptedException
	{
		String teamsFile = fileOperationsFactory.readFile("files/TeamsTier.txt");
		String[] eachTeam = teamsFile.split("\n");
		for (int i = 0; i < eachTeam.length; i++)
		{
			if (eachTeam[i].split(";")[0].equals(id))
				return eachTeam[i].split(";")[2];
		}
		String html=parserFactory.parse_html("https://www.dotabuff.com/esports/teams/"+id).toString();
		html=html.substring(html.indexOf("<div class=\"header-content-title"),html.indexOf("Summary",html.indexOf("<div class=\"header-content-title")));
		html=parserFactory.removeTags(html);
		html=html.replaceAll(" ","");
		html=html.replaceAll("\n","");
		fileOperationsFactory.writeToFile(id+";"+html+";"+"5","files/TeamsTier.txt");
		return "5";
	}
}
