package ProjectDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BackupFactory
{
	FileOperationsFactory fileOperationsFactory = new FileOperationsFactory();

	public void checkForBackUp() throws IOException, ParseException
	{
		String lastBackup = fileOperationsFactory.readFile("files/BackUpInfo.txt");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date lastBackupDate = formatter.parse(lastBackup);
		String todayDate = formatter.format(new Date());
		String dirName = "BackUp/" + todayDate;
		if (getDifferenceDays(lastBackupDate) >= 7)
		{
			File theDir = new File(dirName);

			if (!theDir.exists())
			{
				try
				{
					theDir.mkdir();
				} catch (SecurityException se)
				{
				}
			}
			System.out.println("Backuping files...");
			Path source1 = Paths.get("files/BrokenMatches.txt");
			Path source2 = Paths.get("files/Matches.txt");
			Path source3 = Paths.get("files/MatchesAnalized.txt");
			Path source4 = Paths.get("files/MatchesParsed.txt");
			Path source5 = Paths.get("files/MatchesRated.txt");
			Path source6 = Paths.get("files/NeedToParse.txt");
			Path source7 = Paths.get("files/TeamRatings.txt");
			Path target1 = Paths.get(dirName + "/BrokenMatches.txt");
			Path target2 = Paths.get(dirName + "/Matches.txt");
			Path target3 = Paths.get(dirName + "/MatchesAnalized.txt");
			Path target4 = Paths.get(dirName + "/MatchesParsed.txt");
			Path target5 = Paths.get(dirName + "/MatchesRated.txt");
			Path target6 = Paths.get(dirName + "/NeedToParse.txt");
			Path target7 = Paths.get(dirName + "/TeamRatings.txt");
			try
			{
				Files.copy(source1, target1);
				Files.copy(source2, target2);
				Files.copy(source3, target3);
				Files.copy(source4, target4);
				Files.copy(source5, target5);
				Files.copy(source6, target6);
				Files.copy(source7, target7);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			fileOperationsFactory.cleanAndWriteToFile(todayDate, "files/BackUpInfo.txt");
		}
	}

	public static long getDifferenceDays(Date d1)
	{
		Date d2 = new Date();
		long diff = d2.getTime() - d1.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}
