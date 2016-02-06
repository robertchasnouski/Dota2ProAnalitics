import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static java.lang.StrictMath.round;


public class Statistics
{
	private String team_name;
	private int rating = 1000;
	private int position;

	public int getPosition()
	{
		return position;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}


	private String[] parts;

	public int getRating()
	{
		return rating;
	}

	public void setRating(int rating)
	{
		this.rating = rating;
	}

	public void setTeam(String team)
	{
		this.team_name = team;
	}

	public String getTeam()
	{
		return team_name;
	}

  /*  public void getTeamStatistic(MatchInfo.Match[] matches, boolean temp,Statistics []stat)
	{
        //all time
        count_of_games = 0;
        win_games = 0;
        float lohi=0;
        float lohi_all=0;
        for (int i = 0; i < matches.length; i++)
        {
            if (matches[i].getTeam1().equals(team)) {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[0]);

            }
            if (matches[i].getTeam2().equals(team)) {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[1]);
            }
            if(matches[i].getTeam1().equals(team))
            {
                for (int j = 0; j < stat.length; j++)
                {
                    if(matches[i].getTeam2().equals(stat[j].getTeam()))
                    {
                        for (int k = 0; k < stat.length; k++)
                        {
                            if(matches[i].getTeam1().equals(stat[k].getTeam()))
                            {
                                lohi_all++;
                                String []score=matches[i].getScore().split(":");
                                if((stat[k].getRating()-100)>stat[j].getRating() && Integer.parseInt(score[0])<Integer.parseInt(score[1]))
                                {
                                    lohi++;
                                }
                            }
                        }
                    }
                }
            }
            if(matches[i].getTeam2().equals(team))
            {
                for (int j = 0; j < stat.length; j++)
                {
                    if(matches[i].getTeam1().equals(stat[j].getTeam()))
                    {
                        for (int k = 0; k < stat.length; k++)
                        {
                            if(matches[i].getTeam2().equals(stat[k].getTeam()))
                            {
                                lohi_all++;
                                String []score=matches[i].getScore().split(":");
                                if((stat[k].getRating()-100)>stat[j].getRating() && Integer.parseInt(score[1])<Integer.parseInt(score[0]))
                                {
                                    lohi++;
                                }
                            }
                        }
                    }
                }
            }


        }

        percent = round(win_games / count_of_games * 100 * 100);
        percent = percent / 100;
        if (temp == true)
        {

            System.out.println("All time MatchInfo.Team " + team + " statistics:");
            System.out.println("%:" + percent + "%. Won:" + win_games + ". All:" + count_of_games);

        }
        //current month
        count_of_games = 0;
        win_games = 0;
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        String []parts_current=ft.format(date).split("\\.");
        for (int i = 0; i < matches.length; i++)
        {
            String [] parts_match=matches[i].getDate().split("\\.");
            if (matches[i].getTeam1().equals(team) && parts_current[1].equals(parts_match[1]))
            {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[0]);

            }
            if (matches[i].getTeam2().equals(team) && parts_current[1].equals(parts_match[1]))
            {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[1]);
            }



        }
        percent = round(win_games / count_of_games * 100 * 100);
        percent = percent / 100;
        if (temp == true)
        {

            System.out.println("Current month MatchInfo.Team " + team + " statistics:");
            System.out.println("%:" + percent + "%. Won:" + win_games + ". All:" + count_of_games);

        }
        //previous month
        count_of_games = 0;
        win_games = 0;
        String []parts_current2=ft.format(date).split("\\.");
        for (int i = 0; i < matches.length; i++)
        {
            String [] parts_match2=matches[i].getDate().split("\\.");
            if (matches[i].getTeam1().equals(team) && (Integer.parseInt(parts_current2[1])-1)==Integer.parseInt(parts_match2[1]))
            {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[0]);

            }
            if (matches[i].getTeam2().equals(team) && (Integer.parseInt(parts_current2[1])-1)==Integer.parseInt(parts_match2[1]))
            {
                parts = matches[i].getScore().split(":");
                count_of_games = count_of_games + Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                win_games = win_games + Integer.parseInt(parts[1]);
            }



        }
        percent = round(win_games / count_of_games * 100 * 100);
        percent = percent / 100;
        if (temp == true)
        {

            System.out.println("Previous month MatchInfo.Team " + team + " statistics:");
            System.out.println("%:" + percent + "%. Won:" + win_games + ". All:" + count_of_games);

        }
        //
        float perc=lohi/lohi_all;
        perc=Math.round(perc*100);
        perc=perc/100;
        System.out.println("Lost to losers:"+lohi+". Percent:"+perc+".");
        System.out.println("MatchInfo.Team rating:" + rating);
    }
*/
}
