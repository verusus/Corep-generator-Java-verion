package com.acceniom.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;   // possible to use the sql package

@Entity
@Data
@IdClass(TemplateId.class)
@Table(name = "COREP_DOC")
public class Template {
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "D_REPORTING")
    private Date daReporting;
    @Id
    private String idDoc;
    @Id
    private String idDocLogic;
    @Id
    @Column(name = "C_SUPERVISEUR")
    private String coSuperviseur;

    private Integer idCorepVersion;
    @Column(name = "F_ACTIF")
    private String flActif;
    @Column(name = "C_WHERE_INIT")
    private String coWhereInit;
    @Column(name = "C_WHERE_FINAL")
    private String coWhereFinal;
    @Column(name = "L_TABLE")
    private String laTable;
    private Blob template;
}
