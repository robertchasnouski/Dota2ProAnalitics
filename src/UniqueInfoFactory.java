import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UniqueInfoFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();


	Boolean checkIfIdAlreadyParsed(String id) throws IOException
	{
		String matchIdList = fileOperationsFactory.readFile("files/MatchesParsed.txt");
		String[] matchesId = matchIdList.split("\n");
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
}

