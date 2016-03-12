package ProjectDir.Analitics;

import ProjectDir.FileOperationsFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AverageAnaliticsFactory
{
	StringReader stringReader = new StringReader();

	void startAverageAnalitics(String teamId) throws IOException
	{
		String teamMatches = readFile("files/teams/" + teamId + "/TeamMatches.txt");
		//TODO: Add date limit
		Integer avgFBTime = getAverageFBTime(teamMatches);
		Integer avgFRTime = getAverageFRTime(teamMatches);
		Integer avgF10KTime = getAverageF10KTime(teamMatches);
		Integer getFB = getPercentGetFB(teamMatches);
		Integer getFR = getPercentGetFR(teamMatches);
		Integer getF10K = getPercentGetF10K(teamMatches);
	}


	public Integer getAverageFBTime(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allFB = 0;
		Integer FBTime = 0;
		Integer getFB = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String FBInfo = oneMatchString[i].split("##")[4];
			String isRadiantFB = FBInfo.split(";")[0];
			String second = FBInfo.split(";")[1];
			if(isRadiantFB.equals("true") && side.equals("0"))
			{
				allFB++;
				getFB++;
				FBTime+=Integer.parseInt(second);
			}
			else if(isRadiantFB.equals("false") && side.equals("1"))
			{
				allFB++;
				getFB++;
				FBTime+=Integer.parseInt(second);
			}
			else
				allFB++;
		}

		return (int) FBTime/getFB;
	}

	public Integer getPercentGetFB(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allFB = 0;
		Integer getFB = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String FBInfo = oneMatchString[i].split("##")[4];
			String isRadiantFB = FBInfo.split(";")[0];
			String second = FBInfo.split(";")[1];
			if(isRadiantFB.equals("true") && side.equals("0"))
			{
				allFB++;
				getFB++;
			}
			else if(isRadiantFB.equals("false") && side.equals("1"))
			{
				allFB++;
				getFB++;
			}
			else
				allFB++;
		}
		return (int)((double)(getFB/allFB)*100);
	}

	public Integer getAverageFRTime(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allFR = 0;
		Integer FRTime = 0;
		Integer getFR = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String FRInfo = oneMatchString[i].split("##")[6];
			String isRadiantFR = FRInfo.split(";")[0];
			String second = FRInfo.split(";")[1];
			if(isRadiantFR.equals("true") && side.equals("0"))
			{
				allFR++;
				getFR++;
				FRTime+=Integer.parseInt(second);
			}
			else if(isRadiantFR.equals("false") && side.equals("1"))
			{
				allFR++;
				getFR++;
				FRTime+=Integer.parseInt(second);
			}
			else
				allFR++;
		}

		return (int) FRTime/getFR;
	}

	public Integer getPercentGetFR(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allFR = 0;
		Integer FRTime = 0;
		Integer getFR = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String FRInfo = oneMatchString[i].split("##")[6];
			String isRadiantFR = FRInfo.split(";")[0];
			String second = FRInfo.split(";")[1];
			if(isRadiantFR.equals("true") && side.equals("0"))
			{
				allFR++;
				getFR++;
				FRTime+=Integer.parseInt(second);
			}
			else if(isRadiantFR.equals("false") && side.equals("1"))
			{
				allFR++;
				getFR++;
				FRTime+=Integer.parseInt(second);
			}
			else
				allFR++;
		}

		return (int) ((double) getFR/allFR)*100;
	}

	public Integer getPercentGetF10K(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allF10K = 0;
		Integer F10KTime = 0;
		Integer getF10K = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String F10KInfo = oneMatchString[i].split("##")[5];
			String isRadiantF10K = F10KInfo.split(";")[0];
			String second = F10KInfo.split(";")[1];
			if(isRadiantF10K.equals("true") && side.equals("0"))
			{
				allF10K++;
				getF10K++;
				F10KTime+=Integer.parseInt(second);
			}
			else if(isRadiantF10K.equals("false") && side.equals("1"))
			{
				allF10K++;
				getF10K++;
				F10KTime+=Integer.parseInt(second);
			}
			else
				allF10K++;
		}

		return (int) F10KTime/getF10K;
	}

	public Integer getAverageF10KTime(String matches)
	{
		String[] oneMatchString = matches.split("\n");
		Integer allF10K = 0;
		Integer F10KTime = 0;
		Integer getF10K = 0;
		String side;
		for (int i = 0; i < oneMatchString.length; i++)
		{
			side = oneMatchString[i].split("##")[0].split(";")[5];
			String F10KInfo = oneMatchString[i].split("##")[5];
			String isRadiantF10K = F10KInfo.split(";")[0];
			String second = F10KInfo.split(";")[1];
			if(isRadiantF10K.equals("true") && side.equals("0"))
			{
				allF10K++;
				getF10K++;
				F10KTime+=Integer.parseInt(second);
			}
			else if(isRadiantF10K.equals("false") && side.equals("1"))
			{
				allF10K++;
				getF10K++;
				F10KTime+=Integer.parseInt(second);
			}
			else
				allF10K++;
		}

		return (int) ((double)getF10K/allF10K)*100;
	}

	String readFile(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally
		{
			br.close();
		}
	}
}
