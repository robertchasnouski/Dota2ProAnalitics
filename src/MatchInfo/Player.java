package MatchInfo;

public class Player
{
	public String playerId;

	public Integer role; /*1-Mider. 2-Carry. 3-Support. 4-Hardliner. 5-Jungler.*/

	public Integer level;
	public Integer OSI;

	public Integer partisipation;
	public Integer runesUsed;

	//<editor-fold desc="Gold Earnings">
	public Integer goldForKills;
	public Integer goldFed;
	public Integer goldLost;
	//</editor-fold>
	//<editor-fold desc="General Info: HeroName,Side,K D A,HH,HD,GPM,XPM,LH,Items">
	public String hero;
	public Integer kills;
	public Integer deaths;
	public Integer assists;
	public Integer heroHeal;
	public Integer heroDamage;
	public Integer totalGPM;
	public Integer totalXPM;
	public Integer totalLH;
	public String[] item = new String[6];
	public Integer side; /*1-Radiant. 2-Dire.*/
	//</editor-fold>
	//<editor-fold desc="Net Worth: totalGold, minuteGPM, fiveMinuteNetWorth">
	public Integer totalGold;
	public Integer[] minuteGPM = new Integer[150];//TODO: TEST
	public Integer[] fiveMinuteNetWorth = new Integer[30];//TODO: TEST
	//</editor-fold>
	//<editor-fold desc="LastHits: minuteLastHits, perMinuteLastHits">
	public Integer[] minuteLastHits = new Integer[150];//TODO TEST
	public Integer[] perMinuteLastHits = new Integer[150];//TODO TEST
	//</editor-fold>
	//<editor-fold desc="Objectives">
	public Integer towersDestroyed;
	public Integer roshanKills;
	public Integer towerDamage;
	public Integer towersDenied;
	//</editor-fold>
	//<editor-fold desc="Vision">
	public Integer observerWardsPlaced;
	public Integer observerWardsDestroyed;
	public Integer sentryWardsPlaced;
	public Integer sentryWardsDestroyed;

	public Integer dustHits;
	public Integer dustAccuracy;

	public Integer smokeHits;
	public Integer smokeTotalHeroes;

	//TODO gemsRaised

	public Integer gemTimeCarried;
	//</editor-fold>

	public Player()
	{
		this.OSI = 0;
		for (int i = 0; i < 6; i++)
		{
			this.item[i] = null;
		}
		for (int i = 0; i < this.minuteGPM.length; i++)
		{
			this.minuteGPM[i] = 9999;
		}
		for (int i = 0; i < this.fiveMinuteNetWorth.length; i++)
		{
			this.fiveMinuteNetWorth[i] = 9999;
		}
		for (int i = 0; i < this.minuteLastHits.length; i++)
		{
			this.minuteLastHits[i] = 9999;
		}
		for (int i = 0; i < this.perMinuteLastHits.length; i++)
		{
			this.perMinuteLastHits[i] = 9999;
		}
	}

	public void showPlayerStatistics()
	{
		System.out.println("MatchInfo.Player ID:" + playerId + "; Side:" + side + "; Role:" + role + ".");
		System.out.println("HeroName:" + hero + "; Level:" + level + "; Partisipation:" + partisipation + ".");
		System.out.println("Kills:" + kills + "; Deaths:" + deaths + "; Assists:" + assists + ".");
		System.out.println("GoldForKills:" + goldForKills + "; GoldFed:" + goldFed + "; GoldLost:" + goldLost + ".");
		System.out.println("TotalGold:" + totalGold + "; TotalXPM:" + totalXPM + "; TotalGPM:" + totalGPM + ".");
		System.out.println("HeroHeal:" + heroHeal + "; HeroDamage:" + heroDamage + "; TotalLH:" + totalLH + ".");
		System.out.println("TowersDestroyed:" + towersDestroyed + "; TowersDamage:" + towerDamage + "; TowersDenied:" + towersDenied + ".");
		System.out.println("RoshanKills:" + roshanKills + "GemTimeCarried" + gemTimeCarried);
		System.out.println("OserverWardsPlaced:" + observerWardsPlaced + "; ObserverWardsDestroyed:" + observerWardsDestroyed + "; SentryWardsPlaced:" + sentryWardsPlaced + "; SentryWardsDestroyed:" + sentryWardsDestroyed);
		System.out.println("Dust Hits:" + dustHits + "; DustAccuracy:" + dustAccuracy + "; SmokeHits:" + smokeHits + "SmokeHeroes" + smokeTotalHeroes);
		for (int i = 0; i < 6; i++)
		{
			System.out.println("Item:" + item[i]);
		}
	}

}
