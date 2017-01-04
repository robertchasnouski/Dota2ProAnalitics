package ProjectDir.MatchInfo;

public class Player
{
	public String playerId;
	public String hero;
	public Integer kills;
	public Integer deaths;
	public Integer assists;
	public Integer heroHeal;
	public Integer heroDamage;
	public Integer towerDamage;
	public Integer totalGPM;
	public Integer totalXPM;
	public Integer side;
	public Integer totalGold;

	public Player()
	{
		this.playerId = "000000000";
		this.hero = "RandomHero";
		this.side = 0;
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
		this.heroHeal = 0;
		this.heroDamage = 0;
		this.totalGPM = 0;
		this.totalXPM = 0;
		this.totalGold = 0;
		this.towerDamage = 0;
	}

	public void playerZeros()
	{
		this.playerId = "000000000";
		this.hero = "RandomHero";
		this.side = 0;
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
		this.heroHeal = 0;
		this.heroDamage = 0;
		this.totalGPM = 0;
		this.totalXPM = 0;
		this.totalGold = 0;
		this.towerDamage = 0;
	}
}
