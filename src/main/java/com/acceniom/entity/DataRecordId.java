package com.acceniom.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class DataRecordId implements Serializable {
    private String idPartition;
    private String coSuperviseur;
    private Date daReporting;
    private String coPerimetre;
    private String coDoc;
    private String coOnglet;
    private String coLigne;
    private String coColonne;
}
