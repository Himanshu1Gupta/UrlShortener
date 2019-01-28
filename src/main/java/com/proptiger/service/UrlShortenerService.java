package com.proptiger.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.proptiger.config.dto.UrlDto;
import com.proptiger.model.ReportLongToShort;
import com.proptiger.model.ReportShortToLong;
import com.proptiger.model.UrlShortener;
import com.proptiger.repo.ReportLongToShortDao;
import com.proptiger.repo.ReportShortToLongDao;
import com.proptiger.repo.UrlShortenerDao;

@Service
public class UrlShortenerService {

	@Autowired
	private UrlShortenerDao urlShortenerDao;

	@Autowired
	private ReportLongToShortDao lToS;

	@Autowired
	private ReportShortToLongDao sToL;

	private int maxTimes = 0;

	@Value("${spring.datasource.max}")
	private int max;
	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Generate shortUrl corresponding to a LongUrl and domain of ShortUrl
	 * 
	 * @param urlDto(has fields longUrl and domain)
	 * @return shortenedUrl
	 */
	public String shortenUrl(UrlDto urlDto) {

		String longUrl = urlDto.getLongUrl();
		String domain = urlDto.getDomain();
		if (!IDConverter.validateURL(longUrl)) {
			return "Invalid Url format!!";
		}
		int hashVal = IDConverter.hashUrl(longUrl);
		List<UrlShortener> url = urlShortenerDao.findByHashUrlAndLongUrl(hashVal, longUrl);

		int id;
		if (url.size() != 0) {

			UrlShortener lastUrl = url.get(url.size() - 1);
			if (!IDConverter.compare(lastUrl.getCreatedAt(), new Date()))// old is okay
			{
				id = lastUrl.getId();
			} else {
				
				UrlShortener url1 = setUrl(domain, longUrl, hashVal);

				lToS.incrementCount();
				id = url1.getId();

				if (id > max) {
					maxTimes = id / max;
					urlShortenerDao.delete(id - max);
				}
			}

		} 
		else {

			UrlShortener urlShortener = new UrlShortener();
			urlShortener.setDomain(domain);
			urlShortener.setLongUrl(longUrl);
			urlShortener.setHashUrl(hashVal);
			UrlShortener ur2 = urlShortenerDao.save(urlShortener);
			lToS.incrementCount();
			id = ur2.getId();

			if (id > max) {
				maxTimes = id / max;
				urlShortenerDao.delete(id - max);
			}
		}
		String uniqueID = IDConverter.createUniqueID(id % max);
		String shortenedURL = domain + "/" + uniqueID;
		return shortenedURL;
	}

	/**
	 * helper function to SetUrl
	 * 
	 * @param domain ,longUrl, hashVal
	 * @return instance of UrlShortener 
	 */
	
	public UrlShortener setUrl(String domain, String longUrl, int hashVal) {
		UrlShortener urlShortener = new UrlShortener();
		urlShortener.setDomain(domain);
		urlShortener.setLongUrl(longUrl);
		urlShortener.setHashUrl(hashVal);
		UrlShortener url = urlShortenerDao.save(urlShortener);
		return url;
	}

	/**
	 * helper function to GetLongUrl
	 * 
	 * @param dictionaryKey
	 * @return instance of UrlShortener corresponding to the key
	 */
	@Cacheable("helperGetLongUrl")
	public UrlShortener helperGetLongUrl(Integer dictionaryKey) {
		if (maxTimes == 0) {
			maxTimes = urlShortenerDao.lastId() / max;
		}
		return urlShortenerDao.findOne((int) dictionaryKey + maxTimes * max);
	}

	public boolean checkShortUrlFormat(String uniqueId) {
		int len = uniqueId.length();
		if (len != 7)
			return false;
		for (int i = 0; i < len; i++) {
			char c = uniqueId.charAt(i);
			if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Generate LongUrl corresponding to a ShortUrl\
	 * 
	 * @param shortUrl(has fields longUrl and domain)
	 * @return longUrl
	 */
	public String getLongUrl(String shortUrl) throws Exception {

		String arr[] = shortUrl.split("/");
		int len = arr.length;
		if (!checkShortUrlFormat(arr[len - 1])) {
			return "Invalid Url format!!";
		}
		int dictionaryKey = IDConverter.getDictionaryKeyFromUniqueID(arr[len - 1]);
		UrlShortener url = applicationContext.getBean(UrlShortenerService.class).helperGetLongUrl(dictionaryKey);
		if (url != null) {
			if (url.getDomain().equals(arr[len - 2])) {
				sToL.incrementCount();
				String longUrl = url.getLongUrl();
				return longUrl;
			} else {
				return "Invalid Domain";
			}
		} else {
			return "Invalid Url";
		}
	}

	/**
	 * Generate dailyRepot- LongToShortUrl generated and ShortURLClicksInADay
	 * 
	 * @param dateString- date in String format
	 * @return string giving counts corresponding to the date passed
	 */
	public String generateReport(String dateString) {
		try {
			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date date = simpleDateFormat.parse(dateString);
			ReportLongToShort lsc = lToS.findByDate(date);
			ReportShortToLong slc = sToL.findByDate(date);

			String s = "LongToShortCount: " + lsc.getCount() + " ****************** " + "ShortURLClicksInADay: "
					+ slc.getCount();
			return s;

		} catch (Exception e) {
			return null;
		}
	}

}
