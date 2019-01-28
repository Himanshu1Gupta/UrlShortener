package com.proptiger.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.proptiger.model.ReportShortToLong;

public interface ReportShortToLongDao extends JpaRepository<ReportShortToLong, Date> {

	public ReportShortToLong findByDate(Date date);

	@Transactional
	@Modifying
	@Query(value = "Insert into urlShortener.short_to_long(date,count) values (date(now()),1) on duplicate key Update count=count+1;", nativeQuery = true)
	public void incrementCount();
}
