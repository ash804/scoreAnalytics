package com.edu.score;

import com.edu.score.model.SciencesStudentEntity;
import com.edu.score.model.StudentEntity;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Utility Class for doing all excel operation
 * Created by Administrator on 2016/6/27.
 */
public class ExcelManager {

    public static Workbook workBook = null;
    public static final String SCIENCES_SCORE_SHEET_NAME="0";
    public static final String ARTS_SCORE_SHEET_NAME="1";

    //Column names
    public static String STUDENT_ID_COLUMN_NAME="学号";
    public static String STUDENT_NAME_COLUMN_NAME="姓名";
    public static String CHINESE_COLUMN_NAME="语文";
    public static String MATH_COLUMN_NAME="数学";
    public static String ENGLISH_COLUMN_NAME="英语";
    public static String CHEMISTRY_COLUMN_NAME="化学";
    public static String PHYCICS_COLUMN_NAME="物理";
    public static String BIOLOGY_COLUMN_NAME="生物";
    public static String POLITICS_COLUMN_NAME="政治";
    public static String GEOGRAPH_COLUMN_NAME="地理";
    public static String HISTORY_NAME_COLUMN_NAME="历史";

    public static String SCIENCE_RANK_COLUMN_NAME="理排名";
    public static String SCIENCE_TOTAL_COLUMN_NAME="理综";
    public static String TOTAL_COLUMN_NAME="六科总分";

    /**
     * Load a local excel workbook to initialize this class.
     * @param path
     * @throws Exception
     */
    public static void loadWorkBook(String path) throws Exception{
        workBook = Workbook.getWorkbook(new File(path));
    }

    /**
     * Check if workBook is initizlized.
     * @return
     */
    public static void verifyWorkBookInitialized() throws Exception{
        if(workBook == null)
            throw new Exception("The variable 'workBook' hasn't been initialized, " +
                    "please use ExcelManager.loadWorkBook(...) to initialize it.");
    }

    /**
     * Get score sheet recording sciences or arts students' score with the given sheet name.
     * @param sheetName ExcelManager.SCIENCES_SCORE_SHEET_NAME or ExcelManager.ARTS_SCORE_SHEET_NAME
     * @return
     * @throws Exception
     */
    public Sheet getScoreSheet(String sheetName) throws Exception{
        verifyWorkBookInitialized();
        Sheet scoreSheet = null;
        int sheetNum = 0;
        if(SCIENCES_SCORE_SHEET_NAME.equals(sheetName) || ARTS_SCORE_SHEET_NAME.equals(sheetName)){
            System.out.println("Get the score sheet of :"+sheetName);
            return workBook.getSheet(sheetName);
        }
        else
            throw new Exception("Invalid sheet name:"+sheetName+", " +
                    "the sheet name should be one of ["+SCIENCES_SCORE_SHEET_NAME+","+ARTS_SCORE_SHEET_NAME+"]");
    }

    public static int getOrderNumByContentFromCellArray(Cell[] cellArray, String content){
        for(int i=0;i<cellArray.length;i++){
            if(cellArray[i].getContents().equals(content))
                return i;
        }
        return -1;
    }

    public static List<StudentEntity> studentEntityList = null;


    public void loadStudentDataFromScoreSheet(String sheetName) throws Exception{
        Sheet scoreSheet = getScoreSheet(sheetName);

        Cell[] headerCellArray = scoreSheet.getRow(0);
        String[] headerStringArray = {STUDENT_ID_COLUMN_NAME,STUDENT_NAME_COLUMN_NAME,
                CHINESE_COLUMN_NAME,MATH_COLUMN_NAME,ENGLISH_COLUMN_NAME,
                CHEMISTRY_COLUMN_NAME,PHYCICS_COLUMN_NAME,BIOLOGY_COLUMN_NAME,
                POLITICS_COLUMN_NAME,GEOGRAPH_COLUMN_NAME,HISTORY_NAME_COLUMN_NAME};

        Map<String,Integer> headerColumnMap = new HashMap<String,Integer>();
        for(int i=0;i<headerStringArray.length;i++){
            int column = getOrderNumByContentFromCellArray(headerCellArray,headerStringArray[i]);
            if(headerStringArray[i].equals(STUDENT_ID_COLUMN_NAME) && column==-1)
                throw new Exception("Invalid sheet format, the header doesn't contain student id header ["+STUDENT_ID_COLUMN_NAME+"]");
            else if(column >=0)
                headerColumnMap.put(headerStringArray[i],column);
        }

        int columnSize = scoreSheet.getColumns();
        int rowSize = scoreSheet.getRows();
        studentEntityList = new ArrayList<StudentEntity>();

        for(int i=1;i<rowSize;i++){
            //Transfer data of each student into StudentEntity
            Cell[] cellArray = scoreSheet.getRow(i);
            //TODO: add try-catch
            if(SCIENCES_SCORE_SHEET_NAME.equals(sheetName)){
                SciencesStudentEntity student = new SciencesStudentEntity();
                student.setId(cellArray[headerColumnMap.get(STUDENT_ID_COLUMN_NAME)].getContents());
                student.setName(cellArray[headerColumnMap.get(STUDENT_NAME_COLUMN_NAME)].getContents());
                student.setChinese(cellArray[headerColumnMap.get(CHINESE_COLUMN_NAME)].getContents());
                student.setEnglish(cellArray[headerColumnMap.get(ENGLISH_COLUMN_NAME)].getContents());
                student.setMath(cellArray[headerColumnMap.get(MATH_COLUMN_NAME)].getContents());
                student.setPhysics(cellArray[headerColumnMap.get(PHYCICS_COLUMN_NAME)].getContents());
                student.setChemistry(cellArray[headerColumnMap.get(CHEMISTRY_COLUMN_NAME)].getContents());
                student.setBiology(cellArray[headerColumnMap.get(BIOLOGY_COLUMN_NAME)].getContents());

                studentEntityList.add(student);
                //TODO: add id-score map method here;Or deal with rank here
            }


        }
    }
}
