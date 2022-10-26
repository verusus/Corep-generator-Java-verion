package com.acceniom.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@IdClass(DataRecordId.class)
@Table(name = "V_COREP_AGREGAT_EXCEL")
public class DataRecord {
    @Id
    private String idPartition;
    @Id
    @Column(name = "C_SUPERVISEUR")
    private String coSuperviseur;
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "D_REPORTING")
    private Date   daReporting;
    @Id
    @Column(name = "C_PERIMETRE")
    private String coPerimetre;
    @Id
    @Column(name = "C_DOC")
    private String coDoc;
    @Id
    @Column(name = "C_ONGLET")
    private String coOnglet;
    @Id
    @Column(name = "C_Ligne")
    private String coLigne;
    @Id
    @Column(name = "C_COLONNE")
    private String coColonne;

    @Column(name = "L_ONGLET")
    private String laOnglet;

    @Column(name = "L_LIGNE")
    private String laLigne;

    private Integer noExcel;
    private BigDecimal montant;

}
