package MatchInfo;

public class Player
{
	public String playerId;

	public Integer role; /*1-Mider. 2-Carry. 3-Support. 4-Hardliner. 5-Jungler.*/

	public Integer level;

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
	public Integer[] minuteGPM = new Integer[150];
	public Integer[] fiveMinuteNetWorth = new Integer[30];
	//</editor-fold>
	//<editor-fold desc="LastHits: minuteLastHits, perMinuteLastHits">
	public Integer[] minuteLastHits = new Integer[150];
	public Integer[] perMinuteLastHits = new Integer[150];
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

	public Integer gemTimeCarried;
	//</editor-fold>
	//<editor-fold desc="Expirience">
	public Integer[] minuteXPM = new Integer[150];
	//</editor-fold>

	public Player()
	{
		this.playerId="000000000";
		this.hero="RandomHero";
		this.role=0;
		this.side=0;
		this.kills=0;
		this.deaths=0;
		this.assists=0;
		this.partisipation=0;
		this.heroHeal=0;
		this.heroDamage=0;
		this.totalGPM=0;
		this.totalXPM=0;
		this.totalGold=0;
		this.totalLH=0;
		this.towersDestroyed=0;
		this.towerDamage=0;
		this.towersDenied=0;
		this.roshanKills=0;
		this.goldForKills=0;
		this.goldFed=0;
		this.goldLost=0;
		this.observerWardsDestroyed=0;
		this.observerWardsPlaced=0;
		this.sentryWardsDestroyed=0;
		this.sentryWardsPlaced=0;
		this.dustHits=0;
		this.dustAccuracy=0;
		this.smokeHits=0;
		this.smokeTotalHeroes=0;
		this.gemTimeCarried=0;
		for (int i = 0; i < 6; i++)
		{
			this.item[i] = null;
		}
		for (int i = 0; i < this.minuteGPM.length; i++)
		{
			this.minuteGPM[i] = 9999;
			this.minuteXPM[i] = 9999;
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

	public void playerZeros()
	{
		this.playerId="000000000";
		this.hero="RandomHero";
		this.role=0;
		this.side=0;
		this.kills=0;
		this.deaths=0;
		this.assists=0;
		this.partisipation=0;
		this.heroHeal=0;
		this.heroDamage=0;
		this.totalGPM=0;
		this.totalXPM=0;
		this.totalGold=0;
		this.totalLH=0;
		this.towersDestroyed=0;
		this.towerDamage=0;
		this.towersDenied=0;
		this.roshanKills=0;
		this.goldForKills=0;
		this.goldFed=0;
		this.goldLost=0;
		this.observerWardsDestroyed=0;
		this.observerWardsPlaced=0;
		this.sentryWardsDestroyed=0;
		this.sentryWardsPlaced=0;
		this.dustHits=0;
		this.dustAccuracy=0;
		this.smokeHits=0;
		this.smokeTotalHeroes=0;
		this.gemTimeCarried=0;
		for (int i = 0; i < 6; i++)
		{
			this.item[i] = null;
		}
		for (int i = 0; i < this.minuteGPM.length; i++)
		{
			this.minuteGPM[i] = 9999;
			this.minuteXPM[i] = 9999;
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
		System.out.println("Player ID:" + playerId + "; Side:" + side + "; Role:" + role + ".");
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
