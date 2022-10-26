package com.acceniom.repository;

import com.acceniom.entity.DataRecord;
import com.acceniom.entity.DataRecordId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface DataRecordRepository extends JpaRepository<DataRecord, DataRecordId> {
    @Query(value = "SELECT d FROM DataRecord d WHERE d.daReporting= :dReporting " +
            "and (:cPerimetre='*' or d.coPerimetre in (:cPerimetreList)) " +
            "and (:cDoc='*' or d.coDoc in (:cDocList))")
    Page<DataRecord> findByDaReportingAndCoSuperviseurAndCoDocAndCoPerimetre(
            Pageable pageable, @Param("dReporting") Date dReporting,@Param("cDoc") String cDoc,
            @Param("cDocList") List<String> cDocList,
            @Param("cPerimetre") String cPerimetre,
            @Param("cPerimetreList") List<String> cPerimetreList);
}
