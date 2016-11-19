package ProjectDir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class EngineerWorker
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();
	DataWorker dataWorker = new DataWorker();
	ParserFactory parser = new ParserFactory();

	public ArrayList<String> listFilesForFolder(final File folder)
	{
		ArrayList<String> fileNames = new ArrayList<>();
		for (final File fileEntry : folder.listFiles())
		{
			fileNames.add(fileEntry.getName());
		}
		return fileNames;
	}


	public void start_work() throws IOException, InterruptedException
	{
		final File folder = new File("files/players");
		ArrayList<String> fileNames = listFilesForFolder(folder);
		String writeString="";
		for (int i = 0; i < fileNames.size(); i++)
		{
			String file = fileOperationsFactory.readFile("files/players/" + fileNames.get(i));
			String[] lines = file.split("\n");
			if (lines.length < 40)
				continue;
			String name = getPlayerName(fileNames.get(i).replaceAll(".txt", ""));
			name = name.replaceAll("\n", "");
			name = name.replaceAll(" ", "");
			String careerStartDate = lines[0].split(";")[2];
			String role = "";
			String region = "";
			Integer roleRandom = ThreadLocalRandom.current().nextInt(1, 4 + 1);
			Integer regionRadnom = ThreadLocalRandom.current().nextInt(1, 3 + 1);
			;
			if (roleRandom == 1)
				role = "Mider";
			if (roleRandom == 2)
				role = "Carry";
			if (roleRandom == 3)
				role = "Suport";
			if (roleRandom == 4)
				role = "Hardliner";
			if (regionRadnom == 1)
				region = "EUROPE";
			if (regionRadnom == 2)
				region = "CHINA";
			if (regionRadnom == 3)
				region = "AMERICA";
			ArrayList<Integer> medianEPP = new ArrayList<>();
			for (int j = 0; j < lines.length; j++)
			{
				medianEPP.add(Integer.parseInt(lines[j].split(";")[4]));
			}
			Integer epp = dataWorker.getMedianFromArray(medianEPP);
			Integer form = ThreadLocalRandom.current().nextInt(-25, 25 + 1);
			Integer fighting = 0;
			Integer positioning = 0;
			Integer teamplay = 0;
			Integer stability = 0;
			if (epp < 950)
			{
				fighting = ThreadLocalRandom.current().nextInt(0, 4 + 1);
				positioning = ThreadLocalRandom.current().nextInt(0, 4 + 1);
				teamplay = ThreadLocalRandom.current().nextInt(0, 4 + 1);
				stability = ThreadLocalRandom.current().nextInt(0, 4 + 1);
			}
			if (epp >= 950 && epp < 1000)
			{
				fighting = ThreadLocalRandom.current().nextInt(3, 6 + 1);
				positioning = ThreadLocalRandom.current().nextInt(3, 6 + 1);
				teamplay = ThreadLocalRandom.current().nextInt(3, 6 + 1);
				stability = ThreadLocalRandom.current().nextInt(3, 6 + 1);
			}
			if (epp >= 1000 && epp < 1050)
			{

				fighting = ThreadLocalRandom.current().nextInt(4, 8 + 1);
				positioning = ThreadLocalRandom.current().nextInt(4, 8 + 1);
				teamplay = ThreadLocalRandom.current().nextInt(4, 8 + 1);
				stability = ThreadLocalRandom.current().nextInt(4, 8 + 1);
			}
			if (epp >= 1050 && epp < 1150)
			{
				fighting = ThreadLocalRandom.current().nextInt(5, 9 + 1);
				positioning = ThreadLocalRandom.current().nextInt(5, 9 + 1);
				teamplay = ThreadLocalRandom.current().nextInt(5, 9 + 1);
				stability = ThreadLocalRandom.current().nextInt(5, 9 + 1);
			}
			if (epp >= 1150)
			{
				fighting = ThreadLocalRandom.current().nextInt(7, 10 + 1);
				positioning = ThreadLocalRandom.current().nextInt(7, 10 + 1);
				teamplay = ThreadLocalRandom.current().nextInt(7, 10 + 1);
				stability = ThreadLocalRandom.current().nextInt(7, 10 + 1);
			}
			Integer cost = 240000 * (fighting + positioning + teamplay + stability) / 45;
			Boolean free=true;
			if(name.contains("."))
				free=false;
			System.out.println(name + ";" + epp + ";" + form + ";" + role + ";" + region + ";" + fighting + ";" + positioning + ";" + teamplay + ";" + stability + ";" + cost);
			writeString+="\n"+name + ";" + epp + ";" + form + ";" + role + ";" + region + ";" + fighting + ";" + positioning + ";" + teamplay + ";" + stability + ";" + cost+";"+free;
		}
		writeString=writeString.replaceFirst("\n","");
		fileOperationsFactory.writeToFile(writeString,"files/PlayersPrice.txt");
	}

	private String getPlayerName(String id) throws IOException, InterruptedException
	{
		String html = parser.parse_html("http://www.dotabuff.com/esports/players/" + id).toString();
		html = parser.substringer(html, "<div class=\"header-content-title\">", "</div>");
		if (html.indexOf("small") != -1)
			html = html.substring(0, html.indexOf("<small>"));
		html = parser.removeTags(html);
		return html;
	}
}
