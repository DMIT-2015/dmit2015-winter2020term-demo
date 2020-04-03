package demo.ejb.timers;

import java.net.URI;

import javax.ejb.Timer;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * RESTful Web services for managing EJB timers.
 *
 *  URI																		Http Method		Description
 * 	------------------------------------------------------------------		-----------		------------------------------------------
 *	/timers/single-event-timer/{seconds}									POST			Create a single event timer
 * 	/timers/single-event-info-timer/{seconds}								POST			Create a single event timer with info
 * 	/timers/interval-event-timer/{initialDuration}/{intervalDuration}		POST			Create an interval timer
 * 	/timers/cancel-all														PUT				Cancel all active timers associated with the beans in the same module in which the caller bean is packaged
 *
 * Create a single event timer to fire in 10 seconds
curl -i -X POST http://localhost:8080/webapi/timers/single-event-timer/10 \
	-d 'The truce is over, you can attack the enemy now' \
	-H 'Content-Type:application/json'
 * Create a single event timer to fire in 30 seconds
curl -i -X POST http://localhost:8080/webapi/timers/single-event-timer/30 \
	-d 'The 30 second timer has elapsed.' \
	-H 'Content-Type:application/json'

 * Create a single event timer to fire in 15 seconds
curl -i -X POST http://localhost:8080/webapi/timers/single-event-info-timer/15

 * Create a interval event timer start in 30 seconds and repeat every 2 minutes (120 seconds)
curl -i -X POST http://localhost:8080/webapi/timers/interval-event-timer/30/120

 * Cancel all active timers associated with this bean
curl -i -X PUT http://localhost:8080/webapi/timers/cancel

 * Cancel all active timers associated with the beans in the same module in which the caller bean is packaged
curl -i -X PUT http://localhost:8080/webapi/timers/cancel-all

 *
 * @author samcw
 *
 */
@Path("timers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProgrammaticTimersResource {

	@Inject
	private ProgrammaticSingleEventTimersBean singleEventTimerBean;

	@Inject
	private ProgrammaticSingleEventWithInfoTimersBean singleEventWithInfoTimerBean;

	@Inject
	private ProgrammaticIntervalEventTimerBean intervalEventTimerBean;

	@Inject
	private ProgrammaticTimersManagerBean timerManagerBean;

	@PUT
	@Path("cancel-all")
	public Response cancelAllTimers() {
		timerManagerBean.cancelAllTimers();
		return Response.noContent().build();
	}

	@POST					// This method only accepts HTTP POST requests.
	@Path("single-event-timer/{seconds}")
	public Response createSingleEventTimer(@PathParam("seconds") long durationSeconds, String message, @Context UriInfo uriInfo) {
		long durationMilliseconds = durationSeconds * 1000;
		Timer timer = singleEventTimerBean.createSingleActionTimer(durationMilliseconds, message);
		URI location = uriInfo.getAbsolutePathBuilder()
				.path(timer.getNextTimeout().toString())
				.build();
		return Response
				.created(location)
				.build();
	}

	@POST					// This method only accepts HTTP POST requests.
	@Path("single-event-info-timer/{seconds}")
	public Response createSingleEventWithInfoTimer(@PathParam("seconds") long durationSeconds,@Context UriInfo uriInfo) {
		long durationMilliseconds = durationSeconds * 1000;
		Timer timer = singleEventWithInfoTimerBean.createDurationTimer(durationMilliseconds);
		URI location = uriInfo.getAbsolutePathBuilder()
				.path(timer.getNextTimeout().toString())
				.build();
		return Response
				.created(location)
				.build();
	}

	@POST					// This method only accepts HTTP POST requests.
	@Path("interval-event-timer/{initialDuration}/{intervalDuration}")
	public Response createIntervalTimer(
		@PathParam("initialDuration") long initialDurationSeconds,
		@PathParam("intervalDuration") long intervalDurationSeconds,
		@Context UriInfo uriInfo) {

		long initialDurationMilliseconds = initialDurationSeconds * 1000;
		long intervalDurationMilliseconds = intervalDurationSeconds * 1000;
		Timer timer =  intervalEventTimerBean.createIntervalTimer(initialDurationMilliseconds, intervalDurationMilliseconds);
		URI location = uriInfo.getAbsolutePathBuilder()
				.path(timer.getNextTimeout().toString())
				.build();
		return Response
				.created(location)
				.build();
	}
}