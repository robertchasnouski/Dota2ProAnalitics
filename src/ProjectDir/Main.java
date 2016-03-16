package ProjectDir;

public class Main
{

	public static void main(String[] args) throws Exception
	{
		Worker worker = new Worker();
		ReceptionWorker receptionWorker= new ReceptionWorker();
		worker.start_work();
		receptionWorker.start_work();
	}

}

