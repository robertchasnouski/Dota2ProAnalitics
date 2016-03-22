package ProjectDir;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		MatchesWorker worker = new MatchesWorker();
		ReceptionWorker receptionWorker= new ReceptionWorker();
		worker.start_work();
		receptionWorker.start_work();
	}

}

