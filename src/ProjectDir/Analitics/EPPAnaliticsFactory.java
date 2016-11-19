package ProjectDir.Analitics;

import ProjectDir.FileOperationsFactory;
import ProjectDir.MatchInfo.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class EPPAnaliticsFactory
{
	FileControlFactory fileControlFactory = new FileControlFactory();
	StringReader stringReader = new StringReader();
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void calculatePlayersEPP(Team[] team, Player[] player, Match match, ArrayList<KillEvent> killEventArrayList, ArrayList<BuyBackEvent> buyBackEventArrayList, ArrayList<GlyphEvent> glyphEventArrayList, ArrayList<TowerEvent> towerEventArrayList, ArrayList<WardEvent> wardEventArrayList, ArrayList<RoshanEvent> roshanEventArrayList) throws IOException
	{
		for (int i = 0; i < 10; i++)
		{
			Integer epp = 0;
			String avgStatFile = new String(Files.readAllBytes(Paths.get("files/heroes/" + parseHeroName(player[i].hero) + ".txt")));
			Integer avgGPM = Integer.parseInt(avgStatFile.split(";")[0]);
			Integer avgXPM = Integer.parseInt(avgStatFile.split(";")[1]);
			Integer avgHeroDamage = Integer.parseInt(avgStatFile.split(";")[2]);
			Integer avgHeroHeal = Integer.parseInt(avgStatFile.split(";")[3]);
			Integer avgTowerDamage = Integer.parseInt(avgStatFile.split(";")[4]);

			if (player[i].networthPosition <= 3)
			{
				//Without Heal and Tower
				if (avgHeroHeal < 75)
				{
					////GPM-40 XPM-25 Damage-35
					Double GPMMark = 0.4 * 1000 * player[i].totalGPM / avgGPM;
					Double XPMMark = 0.25 * 1000 * player[i].totalXPM / avgXPM;
					Double DamageMark = 0.35 * 1000 * (player[i].heroDamage / match.matchTime) / avgHeroDamage;
					epp = (int) (GPMMark + XPMMark + DamageMark);
				}
				//Heal
				else if (avgHeroHeal >= 75)
				{
					////GPM-30 XPM-25 Damage-25 Heal-20
					Double GPMMark = 0.35 * 1000 * player[i].totalGPM / avgGPM;
					Double XPMMark = 0.3 * 1000 * player[i].totalXPM / avgXPM;
					Double DamageMark = 0.25 * 1000 * (player[i].heroDamage / match.matchTime) / avgHeroDamage;
					Double HealMark = 0.1 * 1000 * (player[i].heroHeal / match.matchTime) / avgHeroHeal;
					epp = (int) (GPMMark + XPMMark + DamageMark + HealMark);
				}
			} else
			{
				if (avgTowerDamage < 1000 && avgHeroHeal < 75)
				{
					////GPM-30 XPM-35 Damage-35
					Double GPMMark = 0.3 * 1000 * player[i].totalGPM / avgGPM;
					Double XPMMark = 0.35 * 1000 * player[i].totalXPM / avgXPM;
					Double DamageMark = 0.35 * 1000 * (player[i].heroDamage / match.matchTime) / avgHeroDamage;
					epp = (int) (GPMMark + XPMMark + DamageMark);
				}
				//Heal
				else if (avgHeroHeal >= 75)
				{
					////GPM-20 XPM-25 Damage-30 Heal-25
					Double GPMMark = 0.2 * 1000 * player[i].totalGPM / avgGPM;
					Double XPMMark = 0.25 * 1000 * player[i].totalXPM / avgXPM;
					Double DamageMark = 0.3 * 1000 * (player[i].heroDamage / match.matchTime) / avgHeroDamage;
					Double HealMark = 0.25 * 1000 * (player[i].heroHeal / match.matchTime) / avgHeroHeal;
					epp = (int) (GPMMark + XPMMark + DamageMark + HealMark);
				}
			}
			player[i].EPP = epp;
			//System.out.println("Player:" + player[i].hero + " " + player[i].firstLine + " EPP:" + player[i].EPP);
		}
	}

	public String parseHeroName(String heroName)
	{
		heroName = heroName.replaceAll(" ", "");
		heroName = heroName.replaceAll("-", "");
		heroName = heroName.replaceAll("'", "");
		return heroName;
	}

}
