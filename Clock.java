import java.util.Vector;


public class Clock {
	private Time time = new Time();
	public Time getTime(){
		return time;
	}
	
	public Time nextCheckPoint(){
		//When can the next check point be? Assume all actions uptil this time have been done
		// If some counter is done transferring : would've been stored in action list
		// If some thing is done waiting in exit queue - 
		// If some thing is being added to queue - would have been added to action list (Check EntryQueue.prepareNewElement)
		return new Time();
	}
}
