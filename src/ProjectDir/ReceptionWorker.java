package ProjectDir;

import java.util.Scanner;

public class ReceptionWorker
{
	Scanner s = new Scanner(System.in);

	public void start_work()
	{
		System.out.println("/----------------Alice--------------------/");
		System.out.println("/-Welcome back, Robert.");
		Integer choice=0;
		while(choice!=9)
		{
			System.out.println("/-How can i help you?");
			System.out.println("/--1: Analize future match");
			System.out.println("/--2:");
			choice=s.nextInt();
			switch(choice)
			{
				case 1:
				{
					System.out.println("/---Please, enter Team1ID:");
					Integer team1Id=s.nextInt();
					System.out.println("/---Please, enter Team2ID:");
					Integer team2Id=s.nextInt();

					break;
				}
			}

		}
	}


}
