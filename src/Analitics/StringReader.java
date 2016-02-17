package Analitics;

public class StringReader
{
	//Give me String and we will return smthg
	public String getMatchInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[0];
	}

	public String getTeamsInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[1];
	}

	public String getPlayersInfo(String match)
	{
		String[] tempString = match.split("##");
		return tempString[2];
	}

	public String getKillEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[3];
	}

	public String getWardEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[4];
	}

	public String getGlyphEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[5];
	}

	public String getBuyBackEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[6];
	}

	public String getTowerEvents(String match)
	{
		String[] tempString = match.split("##");
		return tempString[7];
	}
}
