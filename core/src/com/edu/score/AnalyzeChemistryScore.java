package com.edu.score;

import com.edu.score.model.ClassEntity;
import com.edu.score.model.Entity;
import jxl.Cell;
import jxl.Sheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ClassEntity class7 = new ClassEntity(studentList);
//        Map<String,Integer> idTotalScoreRankMap = class7.getIdRankMapByItem(Entity.TOTAL_SCORE_ITEM_NAME);
//        Map<String,Integer> idSelectionRankMap = class7.getIdRankMapByItem(Entity.SELECTION_QUESTION_ITEM_NAME);
//        Map<String,Integer> idSubjectiveRankMap = class7.getIdRankMapByItem(Entity.SUBJECTIVE_QUESTION_ITEM_NAME);

        List<String> totalScoreRankIdList = class7.getIdRankListByItem(Entity.TOTAL_SCORE_ITEM_NAME);
        for(String id: totalScoreRankIdList){
            System.out.print(id+" ");
            System.out.print(class7.getStudentNameById(id)+" ");
            System.out.print(class7.getScore(Entity.SELECTION_QUESTION_ITEM_NAME,id)+" ");
            System.out.print(class7.getScore(Entity.SUBJECTIVE_QUESTION_ITEM_NAME,id)+" ");
            System.out.print(class7.getScore(Entity.TOTAL_SCORE_ITEM_NAME,id)+" ");
            System.out.println(class7.getRankNumber(Entity.TOTAL_SCORE_ITEM_NAME,id));
        }

//        for(Entity student: studentList){
//            System.out.print(student.getParamValueAsString(Entity.STUDENT_ID_ITEM_NAME)+" ");
//            System.out.print(student.getParamValueAsString(Entity.STUDENT_NAME_ITEM_NAME)+" ");
//            System.out.print(student.getParamValueAsString(Entity.SELECTION_QUESTION_ITEM_NAME)+" ");
//            System.out.print(student.getParamValueAsString(Entity.SUBJECTIVE_QUESTION_ITEM_NAME)+" ");
//            System.out.print(student.getTotalScoreAsString()+"");
//            System.out.println("排名："+idTotalScoreRankMap.get(student.getParamValueAsString(Entity.STUDENT_ID_ITEM_NAME)));
//        }
        System.out.println("平均分： "+class7.getAverageScoreByItem(Entity.SELECTION_QUESTION_ITEM_NAME)+" "
            +class7.getAverageScoreByItem(Entity.SUBJECTIVE_QUESTION_ITEM_NAME)+" "
            +class7.getAverageScoreByItem(Entity.TOTAL_SCORE_ITEM_NAME));


    }
}
