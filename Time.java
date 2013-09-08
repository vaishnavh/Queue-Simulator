
public class Time {
	private int hh, mm, ss;
	private Double ff;
	public  Time(){
		hh = 0;
		mm = 0;
		ss = 0;
		ff = new Double(0);
	}
	public Time(Time t){
		this.hh = t.hh;
		this.mm = t.mm;
		this.ss = t.ss;
		this.ff = t.ff;
	}

	
	public Time increment(int s){
		//Overloaded
		return increment(0, 0, new Double(s));
	}
	
	public Time increment(Double s){
		//Overloaded
		return increment(0, 0, s);
	}
	public Time increment(int h, int m, Double s){
		//Increment time by h:m:s amount
		//Log.enter("Incrementing "+this.toString()+" by "+s.toString()+" seconds");

		this.ff+= s;		
		this.ss+=ff.intValue();
		this.mm+=(this.ss/60)+m;
		this.hh+=(this.mm/60)+h;
		this.ff-=ff.intValue();
		this.ss%=60;
		this.mm%=60;
		if(this.hh>=24){
			System.err.println("Simulation went beyond a day. Time stamp have been wrapped down. Output might be erroneous.");
		}
		this.hh%=24;
		//Log.enter("Incremented to "+this.toString());
		return this;
	}
	
	public boolean greaterThan(Time t){
		//Compares whether the present time is greater than the argument.
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
		return (this.hh == t.hh && this.mm == t.mm && this.ss == t.ss && this.ff.equals(t.ff));
	}
	
	public boolean atLeast(Time t){
		return equalTo(t)||greaterThan(t);
	}
	
	public String toString(){
		return Integer.toString(hh) + ":" + Integer.toString(mm)+ ":" +Double.toString(new Double(ss)+ff);
	}
	public int toSeconds(){
		return ss + mm*60 + hh*3600;
	}
	
	public Double getSeconds(){
		return new Double(ss) + this.ff;
	}
	public static Time add(Time t1, Time t2){
		Time t3 = new Time();
		t3.increment(t1.hh+t2.hh, t1.mm+t2.mm, t1.getSeconds()+t2.getSeconds());
		return t3;
	}
}
