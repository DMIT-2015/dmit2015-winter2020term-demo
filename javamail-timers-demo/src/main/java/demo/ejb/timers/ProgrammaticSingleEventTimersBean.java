package demo.ejb.timers;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless	// Timer service does not support Stateful session beans
public class ProgrammaticSingleEventTimersBean {

	@Resource	// This is a container created resource
	TimerService timerService;

	/**
	 * A Timeout method is executed when a programmatic timer expires.
	 *
	 * @param timer
	 */
    @Timeout
	public void timeout(Timer timer) {
		String message = (String) timer.getInfo();
		System.out.println(message);
	}

	public Timer createSingleActionTimer(long duration, String message) {
		// Create a timer that fires once in duration milliseconds
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(message);
		return timerService.createSingleActionTimer(duration, timerConfig);
	}

}