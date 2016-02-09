package MatchInfo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Match
{
	//<editor-fold desc="Event Times">
	public Integer F10KTime;
	public Integer FBTime;
	public Integer matchTime;
	//</editor-fold>

	public Boolean winRadiant;

	public String date;
	public String team1;
	public String team2;
	public String score;
	public String id;
	public String leagueName;
	public Integer leagueId;

	public Boolean first10KillsRadiant;
	public Boolean firstRoshanRadiant; //TODO
	public Boolean firstBloodRadiant;

	public Match()
	{
	}

	public void setMatch(String id, String date, String team1, String team2, String score)
	{
		this.id = id;
		this.date = date;
		this.team1 = team1;
		this.team2 = team2;
		this.score = score;
	}


}
