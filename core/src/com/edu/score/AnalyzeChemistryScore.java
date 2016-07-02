package com.edu.score;

import com.edu.score.model.Entity;
import jxl.Cell;
import jxl.Sheet;

import java.util.List;

/**
 * Created by Administrator on 2016/7/1.
 */
public class AnalyzeChemistryScore {
    public static void main(String[] args) throws Exception{
        String excelFilePath = "chemistry_scores.xls";
        ExcelManager.loadWorkBook(excelFilePath);
//        Sheet scoreSheet = ExcelManager.getScoreSheet("7班");
//        System.out.println(scoreSheet.getCell(0,0).getContents());
//        Cell[] headerCellArray =  ExcelManager.getHeaderCellArrayFromSheet(scoreSheet,4);
//        System.out.println("Size:"+headerCellArray.length);
//        for(int i=0;i<headerCellArray.length;i++){
//            System.out.println(i+":"+headerCellArray[i].getContents());
//        }
//        System.out.println("Columns:"+scoreSheet.getColumns());
//        System.out.println("Rows:"+scoreSheet.getRows());

        List<Entity> studentList = ExcelManager.getStudentDataListFromChemistrySheet("7班");
        for(Entity student: studentList){
            System.out.print(student.getParamValueAsString(Entity.STUDENT_ID_ITEM_NAME)+" ");
            System.out.print(student.getParamValueAsString(Entity.STUDENT_NAME_ITEM_NAME)+" ");
            System.out.print(student.getParamValueAsString(Entity.SELECTION_QUESTION_ITEM_NAME)+" ");
            System.out.print(student.getParamValueAsString(Entity.SUBJECTIVE_QUESTION_ITEM_NAME)+" ");
            System.out.println(student.getTotalScoreAsString());
        }
    }
}
