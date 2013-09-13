package chrono;

public class Time {
	//Time object dealing with hour minutes and seconds
	private int hh, mm, ss;
	private Double ff; //Second also contains a fractional part
	public  Time(){ //Initialize to zero time.
		hh = 0;
		mm = 0;
		ss = 0;
		ff = new Double(0);
	}
	
	public Time(Time t){ //Initialize to specific time
		this.hh = t.hh;
		this.mm = t.mm;
		this.ss = t.ss;
		this.ff = t.ff;
	}

	
	public Time increment(int s){
		//Overloaded : increment by s seconds
		return increment(0, 0, new Double(s));
	}
	
	public Time increment(Double s){
		//Overloaded
		return increment(0, 0, s);
	}
	
	
	private Time increment(int h, int m, Double s){
		//Increment time by h:m:s amount
		//Log.enter("Incrementing "+this.toString()+" by "+s.toString()+" seconds");

		this.ff+= s;		
		this.ss+=ff.intValue();
		this.mm+=(this.ss/60)+m;
		this.hh+=(this.mm/60)+h;
		this.ff-=ff.intValue();
		this.ss%=60;
		this.mm%=60;
		//When simulation goes beyond a day, er, we have a problem
		if(this.hh>=24){
			System.err.println("Simulation went beyond a day. Time stamp have been wrapped down. Output might be erroneous.");
		}
		this.hh%=24;
		//Log.enter("Incremented to "+this.toString());
		return this;
	}
	
	public boolean greaterThan(Time t){
		//Compares whether this time is greater than the argument.
		if(this.hh!=t.hh){
			return (this.hh>t.hh);
		}else if(this.mm!=t.mm){
			return (this.mm>t.mm);
		}else if(this.ss!=t.ss){
			return (this.ss>t.ss);
		}else if(this.ff!=t.ff){
			return (this.ff>t.ff);
		}
		return false;
	}
	
	public boolean equalTo(Time t){
		//Is this time equal to t?
		return (this.hh == t.hh && this.mm == t.mm && this.ss == t.ss && this.ff.equals(t.ff));
	}
	
	public boolean atLeast(Time t){
		//this >= t ?
		return equalTo(t)||greaterThan(t);
	}
	
	public String toString(){
		return Integer.toString(hh) + ":" + Integer.toString(mm)+ ":" +Double.toString(new Double(ss)+Math.round(ss*100)/100);
	}
	public Double toSeconds(){
		//How many seconds since zero have passed?
		return ff + new Double(ss+ mm*60 + hh*3600);
	}
	
	private Double getSeconds(){
		//What is the second place value?
		return new Double(ss) + this.ff;
	}
	public static Time add(Time t1, Time t2){
		//Adding two times to produce a new time
		Time t3 = new Time();
		t3.increment(t1.hh+t2.hh, t1.mm+t2.mm, t1.getSeconds()+t2.getSeconds());
		return t3;
	}
}
