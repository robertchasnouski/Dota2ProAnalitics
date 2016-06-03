package ProjectDir.Analitics;

import ProjectDir.DataWorker;
import ProjectDir.MatchInfo.AnalizedInfo;

import java.util.ArrayList;

public class ABCAnalitics
{
	public ArrayList<String> getMatchesPool(ArrayList<AnalizedInfo> firstTeamMatches, ArrayList<AnalizedInfo> secondTeamMatches)
	{
		ArrayList<String> idPool = new ArrayList<>();
		for (int i = 0; i < firstTeamMatches.size(); i++)
		{
			for (int j = 0; j < secondTeamMatches.size(); j++)
			{
				if (firstTeamMatches.get(i).enemyTeamId.equals(secondTeamMatches.get(j).enemyTeamId))
				{
					if (!idPool.contains(firstTeamMatches.get(i).enemyTeamId))
						idPool.add(firstTeamMatches.get(i).enemyTeamId);
				}
			}
		}
		return idPool;
	}

	public ArrayList<Meeting> groupMeetingsHistory(ArrayList<AnalizedInfo> teamMatches, ArrayList<String> idPool)
	{
		ArrayList<Meeting> teamMeetings = new ArrayList<>();

		for (int i = 0; i < idPool.size(); i++)
		{
			Meeting meeting = new Meeting();
			meeting.enemyTeamId = idPool.get(i);
			meeting.FBWins = 0;
			meeting.FBLoses = 0;
			meeting.F10KLoses = 0;
			meeting.F10KWins = 0;
			for (int j = 0; j < teamMatches.size(); j++)
			{

				meeting.myName=teamMatches.get(j).teamName;
				if (teamMatches.get(j).enemyTeamId.equals(idPool.get(i)))
				{
					meeting.enemyTeamName = teamMatches.get(j).enemyTeamName;
					if (teamMatches.get(j).isFB)
						meeting.FBWins++;
					else
						meeting.FBLoses++;
					if (teamMatches.get(j).isF10K)
						meeting.F10KWins++;
					else
						meeting.F10KLoses++;
				}
			}
			teamMeetings.add(meeting);
		}
		return teamMeetings;
	}

	public void showMeetings(ArrayList<Meeting> firstTeamMeetings, ArrayList<Meeting> secondTeamMeetings)
	{
		for (int i = 0; i < firstTeamMeetings.size(); i++)
		{
			for (int j = 0; j < secondTeamMeetings.size(); j++)
			{
				if (firstTeamMeetings.get(i).enemyTeamId.equals(secondTeamMeetings.get(j).enemyTeamId))
				{
					System.out.println(firstTeamMeetings.get(i).myName+" vs "+firstTeamMeetings.get(i).enemyTeamName+" Stats: FB-"+firstTeamMeetings.get(i).FBWins+":"+firstTeamMeetings.get(i).FBLoses+" F10K-"+firstTeamMeetings.get(i).F10KWins+":"+firstTeamMeetings.get(i).F10KLoses);
					System.out.println(secondTeamMeetings.get(j).myName+" vs "+secondTeamMeetings.get(j).enemyTeamName+" Stats: FB-"+secondTeamMeetings.get(j).FBWins+":"+secondTeamMeetings.get(j).FBLoses+" F10K-"+secondTeamMeetings.get(j).F10KWins+":"+secondTeamMeetings.get(j).F10KLoses);
				}
			}
		}
	}

	public void checkForABCMatches(ArrayList<AnalizedInfo> firstTeamMatches, ArrayList<AnalizedInfo> secondTeamMatches)
	{
		ArrayList<String> idPool = getMatchesPool(firstTeamMatches, secondTeamMatches);
		ArrayList<Meeting> firstTeamMeetings = new ArrayList<>();
		ArrayList<Meeting> secondTeamMeetings = new ArrayList<>();
		firstTeamMeetings = groupMeetingsHistory(firstTeamMatches, idPool);
		secondTeamMeetings = groupMeetingsHistory(secondTeamMatches, idPool);
		showMeetings(firstTeamMeetings, secondTeamMeetings);
	}

	public class Meeting
	{
		public String enemyTeamId;
		public String enemyTeamName;
		public String myName;
		public Integer FBWins;
		public Integer FBLoses;
		public Integer F10KWins;
		public Integer F10KLoses;
	}

}
