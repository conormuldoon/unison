package eu.acclimatize.unison.harvester;

import java.io.IOException;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HarvesterScheduler {

	final static private int ULS = 60;
	final static private int ULM = 40;
	final static private int LLM = 10;

	HarvesterService harvesterService;

	public HarvesterScheduler(HarvesterService harvesterService) {
		this.harvesterService = harvesterService;

	}

	// Randomising minutes and seconds for data harvesting (see
	// https://api.met.no/conditions_service.html)
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 0 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 6 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 12 * * ?")
	@Scheduled(cron = " ${random.int(" + ULS + ")} ${random.int[" + LLM + "," + ULM + "]} 18 * * ?")
	// Calling once initially constructed
	@PostConstruct
	public void harvest() throws InterruptedException, IOException {

		harvesterService.harvestData(Calendar.getInstance());

	}

}
