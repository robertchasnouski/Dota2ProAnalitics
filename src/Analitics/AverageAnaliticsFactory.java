package Analitics;

import java.io.IOException;

public class AverageAnaliticsFactory
{
	StringReader stringReader = new StringReader();

	public void startAverageAnalitics(String matches) throws IOException
	{
		stringReader.getTeamMatches("36");
		//getAverageFBTime(matches, "111");
	}


	public Integer getAverageFBTime(String matches, String teamId)
	{
		String[] oneMatchString = matches.split("\n");
		System.out.println(oneMatchString[0]);
		for (int i = 0; i < oneMatchString.length; i++)
		{
			String matchInfo = stringReader.getMatchInfo(oneMatchString[i]);

		}
		return 1;
	}

	public Integer getAverageFRTime()
	{
		return 1;
	}

	public Integer getAverageF10KTime()
	{
		return 1;
	}
}
