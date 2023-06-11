package com.dat.csmis.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.AnnounceEntity;

public interface AnnounceRepository extends JpaRepository<AnnounceEntity, Long> {

	
	@Query("select a from AnnounceEntity a where a.dateTime > ?1")
	@Modifying
	@Transactional
	public List<AnnounceEntity> findByDate(LocalDateTime datetime);
}
