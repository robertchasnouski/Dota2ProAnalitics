import java.io.*;

public class FileOperationsFactory
{
	void writeToFile(String whatToWrite, String fileName)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true))))
		{
			out.println(whatToWrite);
		} catch (IOException e)
		{
		}

	}

	void cleanAndWriteToFile(String whatToWrite, String fileName) throws FileNotFoundException
	{
		PrintWriter writer = new PrintWriter(fileName);
		writer.print(whatToWrite);
		writer.close();
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

	public FileOperationsFactory()
	{

	}
}
