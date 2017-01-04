package ProjectDir.MatchInfo;

public class Team
{
	public String id;
	public String name;
	public Integer rating;
	public Integer side;
	public Integer kills;
	public Integer deaths;
	public Integer assists;

	public Team()
	{
		this.id = "000000000";
		this.name = "RandomTeam";
		this.rating = 1000;
		this.side = 0;
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
	}

	public void teamZeros()
	{
		this.id = "000000000";
		this.name = "RandomTeam";
		this.rating = 1000;
		this.side = 0;
		this.kills = 0;
		this.deaths = 0;
		this.assists = 0;
	}

}
