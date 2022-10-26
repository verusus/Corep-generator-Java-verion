package com.acceniom.controller;

import com.acceniom.entity.DataRecord;
import com.acceniom.entity.Template;
import com.acceniom.repository.DataRecordRepository;
import com.acceniom.repository.TemplateRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;

@Component
@NoArgsConstructor
@Data
public class DataManager {
    @Value("${config.D_REPORTING}")
    private String dReporting;
    @Value("${config.C_PERIMETRE}")
    private String cPerimetre;
    @Value("${config.C_DOC}")
    private String cDoc;
    @Value("${config.C_SUPERVISEUR}")
    private String cSuperviseur;
    @Value("${config.OUT_DIRECTORY}")
    private String outDirectory;

    @Autowired
    private DataRecordRepository dataRecordRepository;

    @Autowired
    private TemplateRepository templateRepository;


    public void generateWorkbooks() throws IOException, ParseException, SQLException {

        Workbook workbook = null;
        String currentPerimetre = null;
        String currentDoc = null;
        String currentSheet = null;
        Sheet sheetCopy=null;
        int numColonne;
        Cell cell;

        int sizePage = 1000;
        // One page after recalcul the true number of page
        int maxPages = 1;
        Page<DataRecord> dataPage;
        for (int i = 0; i < maxPages; i++) {
            dataPage = dataRecordRepository.findByDaReportingAndCoSuperviseurAndCoDocAndCoPerimetre(
                    PageRequest.of(i, sizePage,
                            Sort.by(Sort.Direction.ASC, "coPerimetre").and(Sort.by(Sort.Direction.ASC, "coDoc")).
                                    and(Sort.by(Sort.Direction.ASC, "coOnglet"))),
                    new SimpleDateFormat("yyyy-MM-dd").parse(dReporting), cDoc,
                    Arrays.asList(cDoc.split(",")),
                    cPerimetre,
                    Arrays.asList(cPerimetre.split(",")));

            // initalize max pages
            if(i == 0){
                maxPages = dataPage.getTotalPages();
            }
            for(DataRecord dataRecord : dataPage){
                // si le perimetre ou c_doc a été changé: sauvegarder le fichier actuel
                // et créer le nouveau fichier
                if(!Objects.equals(currentPerimetre, dataRecord.getCoPerimetre()) || !Objects.equals(currentDoc, dataRecord.getCoDoc())){
                    if(currentPerimetre != null && currentDoc != null){
                        // save the doc
                        saveDoc(workbook, currentDoc, currentPerimetre);
                    }

                    // load template
                    Template template = templateRepository.findByDaReportingAndCoSuperviseurAndIdDoc(
                            new SimpleDateFormat("yyyy-MM-dd").parse(dReporting), cSuperviseur, dataRecord.getCoDoc());

                    workbook = new XSSFWorkbook(template.getTemplate().getBinaryStream()); // # we retrieve the workbook we constructed in the dictionary
                    // initialize new doc params
                    currentPerimetre = dataRecord.getCoPerimetre();
                    currentDoc = dataRecord.getCoDoc();
                    // initializing current_sheet everytime we create new doc
                    currentSheet = null;
                }

                // if current sheet changed or new doc has created: make another copy of the reference sheet first,
                // else continue filling its cells.
                if(!Objects.equals(currentSheet, dataRecord.getCoOnglet())){
                    currentSheet = dataRecord.getCoOnglet();

                    sheetCopy = workbook.cloneSheet(0);
                    // give title L_ONGLET
//                System.out.println(dataRecord.getCoOnglet());
                    workbook.setSheetName(workbook.getNumberOfSheets()-1, dataRecord.getLaOnglet());
                    // set cell amount of this iteration
                    numColonne = CellReference.convertColStringToIndex(dataRecord.getCoColonne());
                    cell = sheetCopy.getRow(dataRecord.getNoExcel()-1).getCell(numColonne);
                    // test for cell style before setting the value
                    CellStyle cellStyle = cell.getCellStyle();
                    Color color = cellStyle.getFillBackgroundColorColor();
                    if (color == null)
                        cell.setCellValue(dataRecord.getMontant().doubleValue());
                }else{
                    // set cell amount of current iteration
                    numColonne = CellReference.convertColStringToIndex(dataRecord.getCoColonne());
                    cell = sheetCopy.getRow(dataRecord.getNoExcel()-1).getCell(numColonne);
                    // test for cell style before setting the value
                    CellStyle cellStyle = cell.getCellStyle();
                    Color color = cellStyle.getFillBackgroundColorColor();
                    if (color == null)
                        cell.setCellValue(dataRecord.getMontant().doubleValue());
                }
            }
        }
        if(currentPerimetre != null && currentDoc != null){
            // save the doc
            saveDoc(workbook, currentDoc, currentPerimetre);
        }
    }

    public void saveDoc(Workbook workbook, String currentDoc, String currentPerimetre) throws IOException {
        // delete first sheet
        workbook.removeSheetAt(0);
        // give doc title and save
        String path = String.format(outDirectory+"\\%s-%s.xlsx", currentDoc, currentPerimetre);
        File myFile = new File(path);
        FileOutputStream fos = new FileOutputStream(myFile);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
