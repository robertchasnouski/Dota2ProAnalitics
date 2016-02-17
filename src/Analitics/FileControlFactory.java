package Analitics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileControlFactory
{

	public void createTeamFileIfNotExists(String id) throws IOException
	{
		File f = new File("files/teams/"+id+".txt");
		if(!f.exists() && !f.isDirectory())
		{
			f.createNewFile();
		}
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
