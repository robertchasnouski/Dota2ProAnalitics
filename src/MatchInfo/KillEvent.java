package MatchInfo;

public class KillEvent
{
	public float x;
	public float y;


	public Integer second;
	public Integer whoKill;
	public Integer assistsNumber;

	public Integer[] killers = new Integer[5];
	public Integer dier;

	public KillEvent()
	{
		for (int i = 0; i < 5; i++)
		{
			this.killers[i] = 0;
		}
	}
}
