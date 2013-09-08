
public class Log {
		public static void enter(String message){
			String timeStamp = QueueSimulator.clock.getTimeStamp();
			System.out.println(timeStamp + " : "+message);
		}
}
