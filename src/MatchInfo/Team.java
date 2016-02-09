package MatchInfo;

public class Team
{
	public String id;
	public String name;
	public Integer rating;
	public Integer side;
	public Integer partisipation;

	//<editor-fold desc="General Info">
	public Integer kills;
	public Integer deaths;
	public Integer assists;
	public Integer heroHeal;
	public Integer heroDamage;
	public Integer totalGPM;
	public Integer totalXPM;
	public Integer totalLH;
	public Integer totalDenies;
	//</editor-fold>
	//<editor-fold desc="Objectives">
	public Integer towersDestroyed;
	public Integer roshanKills;
	public Integer towerDamage;
	public Integer towersDenied;
	//</editor-fold>
	//<editor-fold desc="Gold Earnings">
	public Integer goldForKills;
	public Integer goldFed;
	public Integer goldLost;
	//</editor-fold>
	//<editor-fold desc="NetWorth: minuteGPM, fiveMinuteNetWorth">
	public Integer totalGold;
	public Integer [] minuteGPM = new Integer [150];
	public Integer [] fiveMinuteNetWorth= new Integer[30];
	//</editor-fold>
	//<editor-fold desc="LastHits: perMinuteLastHits, minuteLastHits">
	public Integer [] perMinuteLastHits= new Integer [150];
	public Integer [] minuteLastHits = new Integer [150];
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

	public Integer gemsDropped;
	public Integer gemTimeCarried;
	//</editor-fold>
	//<editor-fold desc="FirstObjectives">
	public Boolean isFirstBlood;
	public Boolean isF10K;
	public Boolean isFirstRoshan;
	//</editor-fold>
	//<editor-fold desc="Expirience">
	public Integer[] minuteXPM = new Integer[150];//TODO: TEST
	//</editor-fold>

	public Team()
	{
		this.gemsDropped=0;
		for (int i = 0; i < this.minuteGPM.length; i++)
		{
			this.minuteGPM[i]=0;
			this.minuteXPM[i]=0;
		}
		for (int i = 0; i < this.minuteLastHits.length; i++)
		{
			this.minuteLastHits[i]=0;
		}
		for (int i = 0; i < this.perMinuteLastHits.length; i++)
		{
			this.perMinuteLastHits[i]=0;
		}
		for (int i = 0; i < this.fiveMinuteNetWorth.length; i++)
		{
			this.fiveMinuteNetWorth[i]=0;
		}
		this.totalGold=0;
	}

	public void showTeamStatistics()
	{
		System.out.println("MatchInfo.Team ID:"+id+"; Side:"+side);
		System.out.println("TeamName:"+name+"; TeamRating:"+rating +"; Partisipation:"+partisipation+".");
		System.out.println("Kills:"+kills+"; Deaths:"+deaths+"; Assists:"+assists+".");
		System.out.println("GoldForKills:"+goldForKills+"; GoldFed:"+goldFed+"; GoldLost:"+goldLost+".");
		System.out.println("TotalGold:"+totalGold+"; TotalXPM:"+totalXPM+"; TotalGPM:"+totalGPM+".");
		//System.out.println("HeroHeal:"+heroHeal+"; HeroDamage:"+heroDamage+"; TotalLH:"+totalLH+".");
		System.out.println("TowersDestroyed:"+towersDestroyed+"; TowersDamage:"+towerDamage+"; TowersDenied:"+towersDenied+".");
		System.out.println("RoshanKills:"+roshanKills+"; GemTimeCarried"+gemTimeCarried+"; GemsDropped:"+gemsDropped);
		System.out.println("OserverWardsPlaced:"+observerWardsPlaced+"; ObserverWardsDestroyed:"+observerWardsDestroyed+"; SentryWardsPlaced:"+sentryWardsPlaced+"; SentryWardsDestroyed:"+sentryWardsDestroyed);
		System.out.println("Dust Hits:"+dustHits+"; DustAccuracy:"+dustAccuracy+"; SmokeHits:"+smokeHits+"; SmokeHeroes"+smokeTotalHeroes);
	}
}
