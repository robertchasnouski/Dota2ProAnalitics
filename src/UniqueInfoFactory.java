import java.io.IOException;

public class UniqueInfoFactory
{
	ParserFactory parserFactory=new ParserFactory();
	Boolean checkIfIdAlreadyParsed(String id) throws IOException
	{
		String matchIdList=parserFactory.readFile("files/MatchesParsed.txt");
		String [] matchesId=matchIdList.split("\n");
		Boolean exist=false;
		for (int i = 0; i < matchesId.length; i++)
		{
			if(matchesId[i].equals(id))
				exist=true;
		}
		return exist;
	}
}

