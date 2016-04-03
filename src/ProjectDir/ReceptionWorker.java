package ProjectDir;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReceptionWorker
{
	Scanner s = new Scanner(System.in);
	DataWorker dataWorker = new DataWorker();
	FileOperationsFactory fileOperationsFactory=new FileOperationsFactory();
	public void start_work() throws IOException, ParseException
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
					ArrayList<Integer> arrayList=new ArrayList<>();
					arrayList.add(2);
					arrayList.add(1);
					arrayList.add(3);
					arrayList.add(2);
					arrayList.add(3);
					arrayList.add(1);
					System.out.println(dataWorker.stabilityParameterAnalizer(arrayList));*/
					System.out.println("Now enter team ID's.");
					s.nextLine();
					Integer team1Id=findTeamIdByName();
					s.nextLine();
					Integer team2Id=findTeamIdByName();
					System.out.println("/----Jack will help you, Robert.");
					dataWorker.analizeFutureMatch(team1Id.toString(), team2Id.toString());
					break;
				}
				case 9:
				{
					break;
				}
			}
		}
	}



	public Integer findTeamIdByName() throws IOException
	{
		System.out.println("/----Please, enter KeyWord:");
		String keyWord=s.nextLine();
		String teamsFile=fileOperationsFactory.readFile("files/TeamRatings.txt");
		String [] eachLine=teamsFile.split("\n");
		ArrayList<String> matchesArray=new ArrayList<>();
		Integer teamId;
		for (int i = 0; i < eachLine.length; i++)
		{
			if(eachLine[i].toLowerCase().contains(keyWord.toLowerCase()))
			{
				matchesArray.add(eachLine[i]);
			}
		}
		if(matchesArray.size()==0)
			return 0;
		else
		{
			for (int i = 0; i < matchesArray.size(); i++)
			{
				System.out.println((i+1)+" Match:"+matchesArray.get(i));
			}
			System.out.println("0-Choose manualy");
			Integer choice=s.nextInt();
			if(choice!=0)
			{
				teamId=Integer.parseInt(matchesArray.get(choice-1).split(";")[0]);
				return teamId;
			}
			else
			{
				System.out.println("Enter team1Id:");
				teamId=s.nextInt();
				return  teamId;
			}
		}
	}


}
