package eu.acclimatize.unison.harvester;

import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 
 * 
 * A component that schedules data to be harvested at start up and subsequently 4 times a day.
 *
 */
@Component
public class HarvesterScheduler {

	final static private int ULS = 60;
	final static private int ULM = 40;
	final static private int LLM = 10;

	private HarvesterService harvesterService;

	/**
	 * Creates and instance of HarvesterScheduler.
	 * 
	 * @param harvesterService The service that will be used to harvest the data.
	 */
	public HarvesterScheduler(HarvesterService harvesterService) {
		this.harvesterService = harvesterService;

	}

	
	/**
	 * Invokes the {@link HarvesterService#harvestData} method using a Calendar object for the current date and time.
	 * The method is invoked every 6 hours and initially when the application begins. The minutes and seconds
	 * are randomised to load balance connections to the Harmonie API (see https://api.met.no/conditions_service.html). 
	 * 
	 * @throws InterruptedException The harvester service will sleep for a second if it is having problem obtaining data from the API. 
	 * The exception will be thrown if the service is interrupted during this time.
	 */
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 0 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 6 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 12 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 18 * * ?")
	// Calling once initially constructed
	@PostConstruct
	public void harvest() throws InterruptedException {

		harvesterService.harvestData(Calendar.getInstance());

	}

}
