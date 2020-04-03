package demo.ejb.timers;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Stateless	// Timer service does not support Stateful session beans
public class ProgrammaticTimersManagerBean {

	@Resource	// This is a container created resource
	TimerService timerService;

	/**
	 * Cancel all active timers associated with the beans in the same module in which the caller bean is packaged
	 */
	public void cancelAllTimers() {
		for(Timer singleTimer : timerService.getAllTimers()) {
			singleTimer.cancel();
		}
	}


}