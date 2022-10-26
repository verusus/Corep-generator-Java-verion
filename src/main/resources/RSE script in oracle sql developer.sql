USE RSE;
SELECT * FROM RSE.COREP_DOC;

@C:\Users\Administrator\Desktop\corep_doc.sql;
ALTER TABLE V_COREP_AGREGAT_EXCEL MODIFY MONTANT varchar2(255);
@C:\Users\Administrator\Desktop\v_corep_agregat_excel.sql;

select * from V_COREP_AGREGAT_EXCEL;
select * from personne;
select nom as n from personne;
commit;


SELECT * FROM V_COREP_AGREGAT_EXCEL WHERE D_REPORTING=to_date('2022-03-31', 'yyyy/MM/dd')and ('GD_CE16_AR42,GD_CE16_CC04'='*' or C_PERIMETRE in ('GD_CE16_AR42','GD_CE16_CC04')) and ('*'='*' or C_DOC in ('*')) order by C_PERIMETRE, C_DOC, C_ONGLET, C_LIGNE;




CREATE OR REPLACE PROCEDURE RSE.CHARGER_MODELE_COREP
(
in_d_reporting varchar2,
c_etat varchar2,
name_type_etat varchar2
)
AS
  v_src_loc BFILE := BFILENAME('PATH_DIR_COREP', name_type_etat);
  v_amount  INTEGER;
  v_b       BLOB;
BEGIN
  DBMS_LOB.OPEN(v_src_loc, DBMS_LOB.LOB_READONLY);
  v_amount := DBMS_LOB.GETLENGTH(v_src_loc);
  SELECT r.template INTO v_b FROM rse.corep_doc r WHERE r.id_doc = c_etat and d_reporting= TO_DATE(in_d_reporting, 'dd/MM/yyyy') for update;
  DBMS_LOB.LOADFROMFILE(v_b, v_src_loc, v_amount);
  DBMS_LOB.CLOSE(v_src_loc);
END;
/



execute RSE.CHARGER_MODELE_COREP('31/03/2022', 'CR IRB 1', 'CR IRB 1.xlsx');
