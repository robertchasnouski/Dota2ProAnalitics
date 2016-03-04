import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UniqueInfoFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	Boolean checkIfIdAlreadyParsed(String id) throws IOException
	{
		String matchIdList = fileOperationsFactory.readFile("files/MatchesParsed.txt");
		String brokenMatches=fileOperationsFactory.readFile("files/BrokenMatches.txt");
		String newString=matchIdList+brokenMatches;
		String[] matchesId = newString.split("\n");
		Boolean exist = false;
		for (int i = 0; i < matchesId.length; i++)
		{
			if (matchesId[i].equals(id))
				exist = true;
		}
		return exist;
	}

	ArrayList<String> checkIfLeagueParsed(String[] getFromSite) throws IOException, ParseException
	{
		ArrayList<String> needToParse = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
		String leagueFileString = fileOperationsFactory.readFile("files/LeaguesParsed.txt");

		Date d1 = null;
		Date d2 = null;
		Date date = new Date();
		String currentDate = format.format(date);
		d1 = format.parse(currentDate);

		String[] oneLeague = leagueFileString.split("\n");


		for (int i = 0; i < getFromSite.length; i++)
		{
			Boolean exist = false;
			for (int j = 0; j < oneLeague.length; j++)
			{
				if (oneLeague[j].contains(getFromSite[i]))
				{
					exist = true;
					String[] leagueParts = oneLeague[j].split(";");
					d2 = format.parse(leagueParts[1]);
					long diff = d1.getTime() - d2.getTime();
					long diffHours = diff / (60 * 60 * 1000);
					if (diffHours > 24)
					{
						needToParse.add(leagueParts[0]);
						leagueParts[1] = currentDate;
						oneLeague[j] = "";
					} else
						continue;
				}
			}
			if (exist == false)
				needToParse.add(getFromSite[i]);
		}
		String writeLine = "";
		for (int j = 0; j < oneLeague.length; j++)
		{
			writeLine += oneLeague[j];
			if (j != oneLeague.length - 1 && oneLeague[j] != "")
				writeLine += "\n";
		}
		fileOperationsFactory.cleanAndWriteToFile(writeLine, "files/LeaguesParsed.txt");
		return needToParse;
	}

	void needToParseFile(ArrayList<String> matchesToParse) throws IOException
	{
		String alreadyInFile = fileOperationsFactory.readFile("files/NeedToParse.txt");
		for (int i = 0; i < matchesToParse.size(); i++)
		{
			if (!alreadyInFile.contains(matchesToParse.get(i)))
				fileOperationsFactory.writeToFile(matchesToParse.get(i), "files/NeedToParse.txt");
		}
	}

	void makeMatchesFileClean(String fileName) throws IOException
	{
		//Deleting dublicates
		String matchesFile = fileOperationsFactory.readFile(fileName);
		String[] matchLine = matchesFile.split("\n");
		Set<String> lines = new HashSet<String>();
		for (int i = 0; i < matchLine.length; i++)
		{
			lines.add(matchLine[i]);
		}
		String formattedString = "";
		String[] formattedMatches = lines.toArray(new String[lines.size()]);
		for (int i = 0; i < formattedMatches.length; i++)
		{
			formattedString += formattedMatches[i] + "\n";
		}
		//Organize by date
		List<ArrayList<String>> csvLines = new ArrayList<ArrayList<String>>();

		matchLine = formattedString.split("\n");
		for (String s : matchLine)
		{
			String[] dataInLine = s.split(";");
			ArrayList<String> arrayInArray = new ArrayList<String>(Arrays.asList(dataInLine));
			csvLines.add(arrayInArray);
		}
		Collections.sort(csvLines, comp);
		formattedString = "";
		for (int i = 0; i < csvLines.size(); i++)
		{
			formattedString += csvLines.get(i) + "\n";
		}
		formattedString = formattedString.replaceAll("\\[", "");
		formattedString = formattedString.replaceAll("]", "");
		formattedString = formattedString.replaceAll(",", ";");
		String[] eachLineData = formattedString.split("\n");
		String endString = "";
		fileOperationsFactory.cleanAndWriteToFile("", fileName);
		for (int i = 0; i < eachLineData.length; i++)
		{
			endString = "";
			String[] eachData = eachLineData[i].split(";");
			for (int j = 0; j < eachData.length; j++)
			{
				eachData[j] = eachData[j].replaceFirst(" ", "");
				endString += eachData[j] + ";";
			}
			endString = endString.substring(0, endString.length() - 1);
			fileOperationsFactory.writeToFile(endString, fileName);
		}
	}

	Comparator<ArrayList<String>> comp = new Comparator<ArrayList<String>>()
	{
		public int compare(ArrayList<String> csvLine1, ArrayList<String> csvLine2)
		{
			Date date1 = new Date();
			Date date2 = new Date();
			try
			{
				date1 = formatter.parse(csvLine1.get(1));
				date2 = formatter.parse(csvLine2.get(1));
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
			return date1.compareTo(date2);
		}
	};
}

