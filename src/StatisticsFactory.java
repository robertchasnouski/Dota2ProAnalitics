import java.io.IOException;

public class StatisticsFactory
{

	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void addTeamToFileIfNotExists(String teamName, String teamId) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		if (!fileString.contains(teamId))
		{
			fileOperationsFactory.writeToFile(teamId + ";" + teamName + ";" + "1000", "files/TeamRatings.txt");
		}
	}

	public void updateTeamRatings(String teamWinId, String teamLostId, Integer increment, Integer decrement) throws IOException
	{
		String fileString = fileOperationsFactory.readFile("files/TeamRatings.txt");
		String[] fileLines = fileString.split("\n");
		for (int i = 0; i < fileLines.length; i++)
		{
			if (fileLines[i].contains(teamWinId))
			{
				String[] fileSplitterIncrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterIncrement[2]) + increment;
				fileSplitterIncrement[2] = tempInt + "";
				fileLines[i] = fileSplitterIncrement[0] + ";" + fileSplitterIncrement[1] + ";" + fileSplitterIncrement[2];
			}
			if (fileLines[i].contains(teamLostId))
			{
				String[] fileSplitterDecrement = fileLines[i].split(";");
				Integer tempInt = Integer.parseInt(fileSplitterDecrement[2]) - decrement;
				fileSplitterDecrement[2] = tempInt + "";
				fileLines[i] = fileSplitterDecrement[0] + ";" + fileSplitterDecrement[1] + ";" + fileSplitterDecrement[2];
			}
		}
		String outputString = "";
		for (int i = 0; i < fileLines.length; i++)
		{
			outputString += fileLines[i];
			if (i != fileLines.length - 1)
				outputString += "\n";
		}
		fileOperationsFactory.cleanAndWriteToFile(outputString,"files/TeamRatings.txt");
	}

	StatisticsFactory()
	{

	}

}
