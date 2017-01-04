package ProjectDir.MatchInfo;

public class AnalizedInfo
{
	public String id;
	public String date;
	public String matchTime;
	public String teamId;
	public String enemyTeamId;
	public String teamName;
	public String enemyTeamName;
	public String leagueName;
	public Integer leagueId;
	public Boolean isWin;
	public Integer side;
	public Integer kills;
	public Integer deaths;
	public Integer assists;
	public String[] playerId = new String[5];
	public Integer[] playerGPM=new Integer[5];
	public Integer[] playerXPM=new Integer[5];
	public Integer[] playerKills=new Integer[5];
	public Integer[] playerDeaths=new Integer[5];
	public Integer[] playerAssists=new Integer[5];
	public Integer mineGlobalRating;
	public Integer enemyGlobalRating;
	public Integer mineTierRating;
	public Integer enemyTierRating;
	public Integer tier;
}
