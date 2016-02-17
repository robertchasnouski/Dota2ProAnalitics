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
		this.winRadiant = false;
		this.firstBloodRadiant = false;
		this.firstRoshanRadiant = false;
		this.first10KillsRadiant = false;
		this.date = "00/00/00";
		this.team1Id = "000000";
		this.team2Id = "000000";
		this.id = "0000000";
		this.leagueName = "RandomLeagueName";
		this.leagueId = 0;
		this.universalX = 0;
		this.FBTime = 9999;
		this.FRoshanTime = 9999;
		this.F10KTime = 9999;
		this.matchTime = 9999;
	}

	public void matchZeros()
	{
		this.winRadiant = false;
		this.firstBloodRadiant = false;
		this.firstRoshanRadiant = false;
		this.first10KillsRadiant = false;
		this.date = "00/00/00";
		this.team1Id = "000000";
		this.team2Id = "000000";
		this.id = "0000000";
		this.leagueName = "RandomLeagueName";
		this.leagueId = 0;
		this.universalX = 0;
		this.FBTime = 9999;
		this.FRoshanTime = 9999;
		this.F10KTime = 9999;
		this.matchTime = 9999;
	}

}
