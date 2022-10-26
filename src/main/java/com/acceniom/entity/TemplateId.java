package com.acceniom.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class TemplateId implements Serializable {
    private Date daReporting;
    private String idDoc;
    private String idDocLogic;
    private String coSuperviseur;
}
