package chrono;


public class Clock {
	// Handles a Time object and handles sleeping of
	// process to synchronize with real time
	private boolean stop; // Has execution of queue simulator stopped?
	private Time time = new Time(); // current time

	public Time getTime() {
		return time;
	}

	public Clock() {
		stop = false;
	}

	public void nextStep(Time next) {
		// Sleeps from current time till next time
		// If next time is null, it is a signal to stop
		if (next != null) {
		
			time = new Time(next);
		} else {
			stop = true;
		}

	}

	// Return a pretty-printing format of the current time
	public String getTimeStamp() {
		return time.toString();
	}

	public boolean isStopped() {
		return this.stop;
	}
}
