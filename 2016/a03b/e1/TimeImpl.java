package a03b.e1;

import java.time.*;

/**
 * An interface modelling a time in a day, in between 0:0:0 and 23:59:59
 */
public class TimeImpl implements Time {
  
	private final int hour;
	private final int minutes;
	private final int seconds;
	
	public TimeImpl(final int hour, final int minutes, final int seconds) {
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	
	}
	
    /**
     * @return hours, in range (0-23)
     */
    int getHours() {
		return hour;
	}
    
    /**
     * @return minutes, in range (0-59)
     */
    int getMinutes() {
		return minutes;
	}
    
    /**
     * @return seconds, in range (0-59)
     */
    int getSeconds() {
		return this.seconds;
	}
    
    /**
     * @return a 8-characters string representing the time, e.g.: "00:00:00" or "23:59:59"
     */
    String getLabel24() {
		return hour + ":" + minutes + ":" + seconds;
	}
    
    /**
     * @return how many seconds elapsed from midnight; note maximum value is 60*60*24-1
     */
    int getSecondsFromMidnight(){
		return this.hour * this.minutes * this.seconds;
}
