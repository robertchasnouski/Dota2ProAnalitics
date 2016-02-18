package Analitics;

import java.io.*;

public class FileControlFactory
{

	public void createTeamFileIfNotExists(String id) throws IOException
	{
		File f = new File("files/teams/" + id + ".txt");
		if (!f.exists() && !f.isDirectory())
		{
			f.createNewFile();
		}
	}

	public String readFile(String fileName) throws IOException
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

	Boolean checkIfIdAlreadyParsed(String id) throws IOException
	{
		String matchIdList = readFile("files/MatchesAnalized.txt");
		String[] matchesId = matchIdList.split("\n");
		Boolean exist = false;
		for (int i = 0; i < matchesId.length; i++)
		{
			if (matchesId[i].equals(id))
				exist = true;
		}
		return exist;
	}

	void writeToFile(String whatToWrite, String fileName)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true))))
		{
			out.println(whatToWrite);
		} catch (IOException e)
		{
		}

	}
}
