package com.proptiger.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.proptiger.model.ReportLongToShort;

public interface ReportLongToShortDao extends JpaRepository<ReportLongToShort, Date> {

	public ReportLongToShort findByDate(Date date);

	@Transactional
	@Modifying
	@Query(value = "Insert into urlShortener.long_to_short (date,count) values (date(now()),1) on duplicate key Update count=count+1;", nativeQuery = true)
	public void incrementCount();
}
