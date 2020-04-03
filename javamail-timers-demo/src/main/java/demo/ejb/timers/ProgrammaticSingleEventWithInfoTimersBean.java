package demo.ejb.timers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;


@Stateless	// Timer service does not support Stateful session beans
public class ProgrammaticSingleEventWithInfoTimersBean {

	@Resource	// This is a container created resource
	TimerService timerService;

	/**
	 * A Timeout method is executed when a programmatic timer expires.
	 *
	 * @param timer
	 */
	@Timeout
	public void timeout(Timer timer) {
		@SuppressWarnings("unchecked")
		ArrayList<String> actors = (ArrayList<String>) timer.getInfo();
		System.out.println("The following actors have arrived for the audition: ");
		actors.forEach( singleActor -> System.out.println(singleActor) );
	}

	public Timer createDurationTimer(long duration) {
	    // Create a Serializable object to pass information
		ArrayList<String> actors = Arrays.asList("Larry", "Curly","Moe").stream().collect(Collectors.toCollection(ArrayList::new));
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(actors);
		// Create a timer with info that fires once in duration milliseconds
		return timerService.createSingleActionTimer(duration, timerConfig);
	}

	public Timer createTimer(ArrayList<String> info, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
		GregorianCalendar reportCalendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
	    Date auditionDate = reportCalendar.getTime();
	    return timerService.createTimer(auditionDate, info);
	}

	public Timer createCalendarTimer(ArrayList<String> info, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
	    // Create a Serializable object to pass information
	    TimerConfig timerConfig = new TimerConfig();
	    timerConfig.setInfo(info);
	    ScheduleExpression scheduleExpression = new ScheduleExpression();
	    scheduleExpression.year(year);
	    scheduleExpression.month(month);
	    scheduleExpression.dayOfMonth(dayOfMonth);
	    scheduleExpression.hour(hourOfDay);
	    scheduleExpression.minute(minute);
	    return timerService.createCalendarTimer(scheduleExpression, timerConfig);
	}


}