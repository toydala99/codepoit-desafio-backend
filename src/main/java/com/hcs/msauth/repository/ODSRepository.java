package com.hcs.msauth.repository;

import com.hcs.msauth.entities.ODS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ODSRepository extends JpaRepository<ODS, String> {

    @Query(nativeQuery = true, value = "SELECT SUM(points) AS pontos FROM tb_ods")
    Integer totalPoints();
}
