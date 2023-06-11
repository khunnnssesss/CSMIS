package com.dat.csmis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dat.csmis.entity.AvoidMeatEntity;
import com.dat.csmis.entity.MeatList;

public interface AvoidMeatRepository extends JpaRepository<AvoidMeatEntity,Integer> {
	@Query("SELECT a.avoid, COUNT(*) AS count FROM AvoidMeatEntity a WHERE a.avoid IS NOT NULL GROUP BY a.avoid")
    List<Object[]> getCountByAvoid();

    Optional<MeatList> findById(int id);
}
