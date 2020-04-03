package demo.ejb.timers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless	// Timer service does not support Stateful session beans
public class ProgrammaticIntervalEventTimerBean {

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
		System.out.println("The following actors have applied for the job posting: ");
		actors.forEach( singleActor -> System.out.println(singleActor) );
	}

	public Timer createIntervalTimer(long initialDuration, long intervalDuration) {
		TimerConfig timerConfig = new TimerConfig();
	    ArrayList<String> actors = Arrays.asList("Larry", "Curly","Moe").stream().collect(Collectors.toCollection(ArrayList::new));
	    timerConfig.setInfo(actors);
	    return timerService.createIntervalTimer(initialDuration, intervalDuration, timerConfig);
	}

	public Timer createTimer(ArrayList<String> info, long initialDuration, long intervalDuration) {
	    return timerService.createTimer(initialDuration, intervalDuration, info);
	}

}