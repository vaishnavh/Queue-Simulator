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

	public Action(QueueElement element, ActionType action) { // Enter if action
																// is
																// true
		if (action == ActionType.LEAVE) {
			this.counter = null;
			this.element = element;
			this.type = ActionType.LEAVE;
			this.time = new Time(element.getStartTime()).increment(element
					.getQuantity());
		} else if (action == ActionType.ENTER) {
			this.time = new Time(QueueSimulator.getTime()).increment(QueueSimulator.getPopulationInterval());
			this.element = element;
			this.type = ActionType.ENTER;
			this.counter = null;
		}
	}
	
	
	public void perform(){
		Log.enter("Performing "+this.toString());
		if(this.isEnter()){
			element.enter();
		}else if(this.isEndTransaction()){
			element.finishTransaction();
		}else if(this.isLeave()){
			QueueSimulator.sendOff();
		}
	}
	
	public boolean isEndTransaction(){
		return (this.type == ActionType.END_TRANSACTION);
	}
	public boolean isEnter(){
		return (this.type == ActionType.ENTER);
	}
	public boolean isLeave(){
		return (this.type == ActionType.LEAVE);
	}
	
	public String toString(){
		if(this.isEnter()){
			return "ENTER "+element.toString()+" @ "+this.time.toString();
		}else if(this.isEndTransaction()){
			return "END_TRANSACTION "+element.toString()+" with "+counter.toString()+" @ "+this.time.toString();
		}else if(this.isLeave()){
			return "LEAVE "+element.toString()+" @ "+this.time.toString();
		}
		return "";
	}
}
