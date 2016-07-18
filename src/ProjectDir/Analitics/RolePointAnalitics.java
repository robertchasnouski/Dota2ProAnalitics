package ProjectDir.Analitics;

import ProjectDir.MatchInfo.AnalizedInfo;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;

public class RolePointAnalitics
{

	public void start_work(ArrayList<AnalizedInfo> firstObjects, ArrayList<AnalizedInfo> secondObjects, ArrayList<AnalizedInfo> firstTenObjects, ArrayList<AnalizedInfo> secondTenObjects) throws IOException, InterruptedException
	{
		ArrayList<String> firstPlayers = getMainPlayers(firstObjects);
		ArrayList<String> secondPlayers = getMainPlayers(secondObjects);
		showPlayersInfo(firstObjects, firstPlayers, firstTenObjects);
		showPlayersInfo(secondObjects, secondPlayers, secondTenObjects);
	}

	private void showPlayersInfo(ArrayList<AnalizedInfo> objects, ArrayList<String> players, ArrayList<AnalizedInfo> tenObjects) throws IOException, InterruptedException
	{
		System.out.println("\t\t\t\t---" + objects.get(0).teamName + " Roles info---");
		for (int i = 0; i < players.size(); i++)
		{
			Integer role = 0;
			role = detectPlayerRole(objects, players.get(i));
			String playerName = getPlayerName(players.get(i));
			String roleString = "";
			if (role == 1)
				roleString = "Mider";
			else if (role == 2)
				roleString = "Carry";
			else if (role == 3)
				roleString = "Support";
			else roleString = "Hardliner";
			Integer points = getMedianEPP(objects, players.get(i), role);
			Integer tenPoints = getMedianEPP(tenObjects, players.get(i), role);

			double form = 0.0;
			if (tenPoints != 9999 )
			{
				form = (double) tenPoints / points * 100;
				form = Math.round(form);
			} else
				form = 0.0;
			System.out.println(playerName + ";" + roleString + ";" + points + ";" + (int) form + "%");
		}
	}

	private Integer getMedianEPP(ArrayList<AnalizedInfo> objects, String id, Integer role)
	{
		ArrayList<Integer> marks = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (objects.get(i).playerId[j].equals(id) && objects.get(i).playerRole[j] == role)
					marks.add(objects.get(i).playerEPP[j]);
			}
		}
		return getMedianFromArray(marks);
	}

	private Integer getMedianFromArray(ArrayList<Integer> array)
	{
		Collections.sort(array);
		if (array.size() <= 4)
			return 9999;
		if (array.size() % 2 == 0)
		{
			return (int) (array.get(array.size() / 2 - 1) + array.get(array.size() / 2)) / 2;
		} else
		{
			return (int) array.get(array.size() / 2);
		}
	}

	private String getPlayerName(String id) throws IOException, InterruptedException
	{
		String file = readFile("files/PlayerNames.txt");
		String name = "";
		if (!file.contains(id))
		{
			String page = parse_html("http://www.dotabuff.com/esports/players/" + id).toString();
			int currentIndex = page.indexOf("header-content-title");
			int tempIndex = page.indexOf("</div", currentIndex);
			page = page.substring(currentIndex, tempIndex);
			currentIndex = page.indexOf("h1>");
			if (page.contains("<small"))
				tempIndex = page.indexOf("<small");
			else
				tempIndex = page.indexOf("</h1");
			page = page.substring(currentIndex + 3, tempIndex);
			name = page;
			name = name.replaceAll(";", "");
			writeToFile(id + ";" + name, "files/PlayerNames.txt");
		} else
		{
			String[] line = file.split("\n");
			for (int i = 0; i < line.length; i++)
			{
				if (line[i].contains(id))
				{
					name = line[i].split(";")[1];
					break;
				}
			}
		}
		return name;
	}

	private Integer detectPlayerRole(ArrayList<AnalizedInfo> objects, String id)
	{
		int carryGames = 0;
		int midGames = 0;
		int supportGames = 0;
		int hardLinerGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (objects.get(i).playerId[j].equals(id))
				{
					if (objects.get(i).playerRole[j] == 1)
						midGames++;
					if (objects.get(i).playerRole[j] == 2)
						carryGames++;
					if (objects.get(i).playerRole[j] == 3)
						supportGames++;
					if (objects.get(i).playerRole[j] == 4)
						hardLinerGames++;
				}
			}
		}
		if (midGames > carryGames && midGames > supportGames && midGames > hardLinerGames)
			return 1;
		if (carryGames > midGames && carryGames > supportGames && carryGames > hardLinerGames)
			return 2;
		if (supportGames > midGames && supportGames > carryGames && supportGames > hardLinerGames)
			return 3;
		if (hardLinerGames > midGames && hardLinerGames > carryGames && hardLinerGames > supportGames)
			return 4;

		return 3;
	}

	private ArrayList<String> getMainPlayers(ArrayList<AnalizedInfo> objects)
	{
		ArrayList<String> playerPool = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				if (!playerPool.contains(objects.get(i).playerId[j]))
					playerPool.add(objects.get(i).playerId[j]);
			}
		}
		ArrayList<Player> players = new ArrayList<>();
		for (int i = 0; i < playerPool.size(); i++)
		{
			Integer number = getNumberOfGames(objects, playerPool.get(i));
			Player onePlayer = new Player();
			onePlayer.id = playerPool.get(i);
			onePlayer.games = number;
			players.add(onePlayer);
		}
		return getCorePlayers(players);
	}

	private Integer getNumberOfGames(ArrayList<AnalizedInfo> objects, String playerID)
	{
		Integer numOfGames = 0;
		for (int i = 0; i < objects.size(); i++)
		{
			if (objects.get(i).playerId[0].equals(playerID))
				numOfGames++;
			if (objects.get(i).playerId[1].equals(playerID))
				numOfGames++;
			if (objects.get(i).playerId[2].equals(playerID))
				numOfGames++;
			if (objects.get(i).playerId[3].equals(playerID))
				numOfGames++;
			if (objects.get(i).playerId[4].equals(playerID))
				numOfGames++;
		}
		return numOfGames;
	}

	private ArrayList<String> getCorePlayers(ArrayList<Player> players)
	{
		ArrayList<String> corePlayers = new ArrayList<>();
		while (players.size() != 0)
		{
			int maxElem = 0;
			Integer maxGames = 0;
			for (int i = 0; i < players.size(); i++)
			{
				if (players.get(i).games > maxGames)
				{
					maxGames = players.get(i).games;
					maxElem = i;
				}
			}
			corePlayers.add(players.get(maxElem).id);
			players.remove(maxElem);
		}
		return new ArrayList<>(corePlayers.subList(0, 5));
	}

	//<editor-fold desc="Operations">
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

	void writeToFile(String whatToWrite, String fileName)
	{
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true))))
		{
			out.println(whatToWrite);
		} catch (IOException e)
		{
		}

	}

	public Document parse_html(String html) throws IOException, InterruptedException
	{
		Document doc = new Document("");
		try
		{
			doc = Jsoup.connect(html)
					.userAgent("Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.46 Safari/536.5")
					.get();

		} catch (HttpStatusException e)
		{
			System.out.println("HttpStatusException." + html + ". Trying to repeat...");

		} catch (SocketTimeoutException e)
		{
			System.out.println("SocketTimeoutException. Trying to repeat...");

		}
		return doc;
	}
	//</editor-fold>

	class Player
	{
		public String id;
		public Integer games;
		public String playerNick;
		public String role;
	}
}
