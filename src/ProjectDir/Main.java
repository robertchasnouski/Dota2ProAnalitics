package ProjectDir;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		MatchesWorker matchesWorker = new MatchesWorker();
		ReceptionWorker receptionWorker = new ReceptionWorker();
		HeroesAverageStatsWorker heroesAverageStatsWorker = new HeroesAverageStatsWorker();
		CleanerWorker cleanerWorker=new CleanerWorker();
		//EngineerWorker engineerWorker=new EngineerWorker();
		//engineerWorker.start_work();
		//Scanner in = new Scanner(System.in);
		//System.out.println("Boss, do u want to update HeroesAvgStats? Y/N");
		//String s = in.next();
		//if (s.equals("Y") || s.equals("y"))
		//	heroesAverageStatsWorker.updateStats();

		matchesWorker.start_work();
		cleanerWorker.cleanShit();
		//matchesWorker.updateTeamsTier();


	}
}

