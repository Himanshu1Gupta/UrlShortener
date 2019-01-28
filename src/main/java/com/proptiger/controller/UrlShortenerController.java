package com.proptiger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proptiger.config.dto.Response2Dto;
import com.proptiger.config.dto.ResponseDto;
import com.proptiger.config.dto.UrlDto;
import com.proptiger.service.UrlShortenerService;

@Controller
public class UrlShortenerController {

	@Autowired
	private UrlShortenerService urlShortenerService;

	@RequestMapping(value = "/shortenUrl", method = RequestMethod.POST)
	@ResponseBody
	@Cacheable(value = "shortUrl", key = "#urlDto")
	public String shortenUrl(@RequestBody UrlDto urlDto) throws Exception {
		String shortenedUrl = urlShortenerService.shortenUrl(urlDto);
		return shortenedUrl;
	}

	@RequestMapping(value = "/longUrl", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getLongUrl(@RequestParam("surl") String Id) throws Exception {
		return new ResponseDto(urlShortenerService.getLongUrl(Id));
	}

	@RequestMapping(value = "/report/{Date}", method = RequestMethod.GET)
	@ResponseBody
	public Response2Dto getReport(@PathVariable String Date) throws Exception {

		return new Response2Dto(urlShortenerService.generateReport(Date));
	}

}
