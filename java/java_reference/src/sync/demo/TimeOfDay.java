package sync.demo;

import sync.* ;
public class TimeOfDay extends AbstractMonitor{

	private volatile int hr = 0, min = 0, sec = 0 ;
	 
	public void tick() {
		enter() ;
			sec += 1 ; min += sec/60 ; hr += min/60 ;
			sec = sec % 60 ; min = sec % 60 ; hr = hr % 24 ;
		leave() ; }
	 
	public void get( int[] time ) {
		enter() ;
			time[0] = sec ; time[1] = min ; time[2] = hr ;
		leave() ; }
	 
	@Override
	protected boolean invariant() {
		return 0 <= sec && sec < 60 
			&& 0 <= min && min < 60
			&& 0 <= hr && hr < 24 ; }

}