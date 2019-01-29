package com.proptiger.scheduler;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.proptiger.service.UrlShortenerService;

@Component
public class CronScheduler {

	@Autowired
	private UrlShortenerService urlShortenerService;

	@Scheduled(cron = "0 40 12 * * ?")
	public void run() throws InterruptedException {

		LocalDate d = java.time.LocalDate.now();
		System.out.println(urlShortenerService.generateReport(d.toString()));

	}
}
