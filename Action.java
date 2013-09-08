public class Action {
	private Counter counter;
	private QueueElement element;
	public Time time;

	public static enum ActionType {
		ENTER, END_TRANSACTION, LEAVE
	}

	private ActionType type;

	public Action(Counter counter, QueueElement element) {
		this.counter = counter;
		this.element = element;
		this.time = new Time(element.getStartTime());
		time.increment(counter.available(element.getQuantity()));
		type = ActionType.END_TRANSACTION;
	}

	public Action(QueueElement element, ActionType action) { // Enter if action is
															// true
		if (action == ActionType.LEAVE) {			
			this.counter = null;
			this.element = element;
			this.type = ActionType.LEAVE;
			this.time = new Time(element.getStartTime()).increment(element.getQuantity());
		} else if(action == ActionType.ENTER){
			this.time = new Time(QueueSimulator.getTime()).increment(QueueSimulator.getPopulationInterval());
			
		}
	}
}
