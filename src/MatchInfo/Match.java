package MatchInfo;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Match
{
	public Boolean winRadiant;

	public String date;
	public String team1Id;
	public String team2Id;
	public String id;
	public String leagueName;
	public Integer leagueId;

	public Boolean first10KillsRadiant;
	public Boolean firstRoshanRadiant;
	public Boolean firstBloodRadiant;

	public Integer universalX;

	//<editor-fold desc="Event Times">
	public Integer F10KTime;
	public Integer FBTime;
	public Integer FRoshanTime;
	public Integer matchTime;
	//</editor-fold>


	public Match()
	{
		this.firstBloodRadiant = false;
		this.firstRoshanRadiant = false;
		this.first10KillsRadiant = false;
		this.winRadiant = false;
	}

}
