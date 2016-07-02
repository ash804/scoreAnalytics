package com.edu.score;

import com.edu.score.model.Entity;
import com.edu.score.model.RankEntity;
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
//    public static String STUDENT_ID_COLUMN_NAME="学号";
//    public static String STUDENT_NAME_COLUMN_NAME="姓名";
//    public static String CHINESE_COLUMN_NAME="语文";
//    public static String MATH_COLUMN_NAME="数学";
//    public static String ENGLISH_COLUMN_NAME="英语";
//    public static String CHEMISTRY_COLUMN_NAME="化学";
//    public static String PHYCICS_COLUMN_NAME="物理";
//    public static String BIOLOGY_COLUMN_NAME="生物";
//    public static String POLITICS_COLUMN_NAME="政治";
//    public static String GEOGRAPH_COLUMN_NAME="地理";
//    public static String HISTORY_NAME_COLUMN_NAME="历史";
//
//    public static String SCIENCE_RANK_COLUMN_NAME="理排名";
//    public static String SCIENCE_TOTAL_COLUMN_NAME="理综";
//    public static String TOTAL_COLUMN_NAME="六科总分";

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
    public static Sheet getScoreSheet(String sheetName) throws Exception{
        verifyWorkBookInitialized();
        Sheet scoreSheet = null;
        int sheetNum = 0;
//        if(SCIENCES_SCORE_SHEET_NAME.equals(sheetName) || ARTS_SCORE_SHEET_NAME.equals(sheetName)){
//            System.out.println("Get the score sheet of :"+sheetName);
//            return workBook.getSheet(sheetName);
//        }
//        else
//            throw new Exception("Invalid sheet name:"+sheetName+", " +
//                    "the sheet name should be one of ["+SCIENCES_SCORE_SHEET_NAME+","+ARTS_SCORE_SHEET_NAME+"]");
        System.out.println("Get the score sheet :"+sheetName);
        return workBook.getSheet(sheetName);
    }

    public static int getIndexByContentFromCellArray(Cell[] cellArray, String content){
        if(cellArray==null)
            return -1;
        for(int i=0;i<cellArray.length;i++){
            try {
                if (cellArray[i].getContents().trim().equals(content))
                    return i;
            }
            catch (Exception e){}
        }
        return -1;
    }

    public static Cell[] getHeaderCellArrayFromSheet(Sheet sheet,int cellArrayLength) throws Exception{
        int searchRowsNumber = 3;
        Cell idHeaderCell = sheet.findCell(Entity.STUDENT_ID_ITEM_NAME,0,0,sheet.getColumns()-1,searchRowsNumber-1,false);
        if(idHeaderCell == null)
            throw new Exception("Cannot find the cell of ["+Entity.STUDENT_ID_ITEM_NAME+"] in the first "+searchRowsNumber+" rows.");
        int row = idHeaderCell.getRow();
        System.out.println("The cell of ["+Entity.STUDENT_ID_ITEM_NAME+"] is at row:"+row+"(First row index is 0)");
        if(cellArrayLength > sheet.getColumns()){
            System.out.println("The indicated cell array length is larger than the column number, reduce the length to the column number.");
            cellArrayLength = sheet.getColumns();
        }
        return Arrays.copyOf(sheet.getRow(row),cellArrayLength);
    }

    public static Cell[] getHeaderCellArrayFromSheet(Sheet sheet) throws Exception{
        return getHeaderCellArrayFromSheet(sheet,sheet.getColumns());
    }

    public static List<StudentEntity> studentEntityList = null;
    //public static List<Entity> entityList = null;
    //private RankEntity columnRanks = null;

    /**
     * Load the student data from the given score sheet and return them as a list of Entity Class.
     * The information of a student in each column will be dynamically recorded into a Entity instance.
     * @param sheetName Name of a excel sheet from which you want to load the student data
     * @param headerNames Names of column headers from which you want to load the student data
     * @return
     * @throws Exception
     */
    public static List<Entity> getStudentDataListFromScoreSheet(String sheetName,String...headerNames) throws Exception{
        if(headerNames==null || headerNames.length<1 ){
            throw new Exception("You must pass at least one header name to the method @getStudentDataListFromScoreSheet(...)");
        }
        else {
            System.out.print("Get student data under these headers:" );
            for(String name: headerNames){
                System.out.print(name+" ");
            }
            System.out.println("");
        }

        Sheet scoreSheet = getScoreSheet(sheetName);
        Cell[] headerCellArray = getHeaderCellArrayFromSheet(scoreSheet,headerNames.length);
        int headerRow = headerCellArray[0].getRow();
        Map<String,Integer> headerColumnIndexMap = new HashMap<String,Integer>();

        for(int i=0;i<headerNames.length;i++){
            int column = getIndexByContentFromCellArray(headerCellArray,headerNames[i]);
            if(headerNames[i].equals(Entity.STUDENT_ID_ITEM_NAME) && column==-1)
                throw new Exception("Invalid sheet format, the header doesn't contain student id header ["+Entity.STUDENT_ID_ITEM_NAME+"]");
            else if(column >=0)
                headerColumnIndexMap.put(headerNames[i],column);
        }

        int rowSize = scoreSheet.getRows();
        List<Entity> entityList = new ArrayList<Entity>();

        for(int i=headerRow+1;i<rowSize;i++){
            //Transfer data of each student into Entity
            Cell[] cellArray = scoreSheet.getRow(i);
            System.out.print(i+"(SIZE:"+cellArray.length+"):");//for debug
            if(cellArray.length<=0)
                break;
            Entity student = new Entity();
            for(int j=0;j<headerNames.length;j++){
                String headerName = headerNames[j];
                System.out.print("("+headerColumnIndexMap.get(headerName)+")");//for debug
                String content = cellArray[headerColumnIndexMap.get(headerName)].getContents();
                System.out.print(content+" ");//for debug
                if(content==null)
                    content="";
                if(Entity.STUDENT_ID_ITEM_NAME.equals(headerName) && CommonUtil.isNullOrEmpty(content))
                    break;
                student.addParam(headerName, content.trim());
            }
            System.out.println();//for debug
            //TODO: maybe need to filter absent students
            if(student.getParamValueAsDouble(Entity.STUDENT_ID_ITEM_NAME)!=Entity.INVALID_SCORE_VALUE)
                entityList.add(student);
            else
                System.out.println("Row No."+i+"(First index is 0) doesn't have valid student id, do not save it into student list.");
        }
        return entityList;

    }

    public static List<Entity> getStudentDataListFromChemistrySheet(String sheetName) throws Exception{
        String[] headerNames = {Entity.STUDENT_ID_ITEM_NAME,Entity.STUDENT_NAME_ITEM_NAME,
                Entity.SELECTION_QUESTION_ITEM_NAME,Entity.SUBJECTIVE_QUESTION_ITEM_NAME};
        return getStudentDataListFromScoreSheet(sheetName,headerNames);
    }

}
