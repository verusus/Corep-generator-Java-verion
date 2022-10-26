package com.acceniom.repository;

import com.acceniom.entity.Template;
import com.acceniom.entity.TemplateId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface TemplateRepository extends CrudRepository<Template, TemplateId> {
    Template findByDaReportingAndCoSuperviseurAndIdDoc(Date dReporting, String cSuperviseur, String idDoc);
}
