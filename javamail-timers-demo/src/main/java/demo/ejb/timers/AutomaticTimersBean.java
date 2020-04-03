package demo.ejb.timers;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;

@Singleton		// Instruct the container to create a single instance of this EJB
@Startup		// Create this EJB is created when this app starts
public class AutomaticTimersBean {	// Also known as Calendar-Based Timers

	@Schedules({
		@Schedule(second = "0", minute ="40", hour = "12", dayOfWeek = "Tue, Thu", month = "Jan-Apr", year = "2020",info ="DMIT2015 Section A02 Class Notification", persistent = false),
		@Schedule(second = "0", minute ="40", hour = "14", dayOfWeek = "Wed", month = "Jan-Apr", year = "2020",info ="DMIT2015 Section A02 Class Notification", persistent = false)
	})
	public void dmit2015SectionA02ClassNotifiation() {
		System.out.println("You have a DMIT2015-A02 class in 20 minutes");
	}

	@Schedules({
		@Schedule(second = "0", minute ="40", hour = "7", dayOfWeek = "Mon", month = "Jan-Apr", year = "2020", info ="DMIT2015 Section A03 Class Notification", persistent = false),
		@Schedule(second = "0", minute ="40", hour = "11", dayOfWeek = "Wed", month = "Jan-Apr", year = "2020", info ="DMIT2015 Section A03 Class Notification", persistent = false),
		@Schedule(second = "0", minute ="40", hour = "12", dayOfWeek = "Fri", month = "Jan-Apr", year = "2020", info ="DMIT2015 Section A03 Class Notification", persistent = false)
	})
	public void dmit2015SectionA03ClassNotifiation() {
		System.out.println("You have a DMIT2015-A03 class in 20 minutes");
		
	}

	@Schedule(second = "0", minute ="30", hour = "14", dayOfWeek = "Mon, Wed, Fri", month = "Jan-Apr", year = "2020", info ="CPSC1519 Section E01 Class Notification", persistent = false)
	public void cpsc1519SectionE01ClassNotifiation(Timer timer) {
		System.out.println("You have a CPSC1519-E01 in 20 minutes");
	}
}