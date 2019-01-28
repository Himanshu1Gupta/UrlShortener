package com.proptiger.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.proptiger.model.UrlShortener;

public interface UrlShortenerDao extends JpaRepository<UrlShortener, Integer> {

	public List<UrlShortener> findByHashUrlAndLongUrl(int hashUrl, String longUrl);

	@Query(value = "SELECT MAX(id) FROM url_shortener", nativeQuery = true)
	public int lastId();
}
