package ProjectDir;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceptionWorker
{
	Scanner s = new Scanner(System.in);
	DataWorker dataWorker = new DataWorker();

	public void start_work() throws IOException
	{
		System.out.println("/----------------Alice--------------------/");
		System.out.println("/-Welcome back, Robert.");
		Integer choice = 0;
		while (choice != 9)
		{
			System.out.println("/-How can i help you?");
			System.out.println("/--1: Analize future match");
			System.out.println("/--2: Poceluj moju zalupu");
			System.out.println("/--9: Exit");
			choice = s.nextInt();
			switch (choice)
			{
				case 1:
				{

					/*
					System.out.println("/----Please, enter Team1ID:");
					Integer team1Id=s.nextInt();
					System.out.println("/----Please, enter Team2ID:");
					Integer team2Id=s.nextInt();*/
					dataWorker.analizeFutureMatch("36", "1231243");
					break;
				}
				case 9:
				{
					break;
				}
			}

		}
	}


}
