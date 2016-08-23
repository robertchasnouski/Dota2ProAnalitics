package ProjectDir;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		MatchesWorker matchesWorker = new MatchesWorker();
		ReceptionWorker receptionWorker = new ReceptionWorker();
		HeroesPointsWorker heroesPointsWorker=new HeroesPointsWorker();
		//heroesPointsWorker.start_work();
		matchesWorker.start_work();
		receptionWorker.start_work();
	}
}

