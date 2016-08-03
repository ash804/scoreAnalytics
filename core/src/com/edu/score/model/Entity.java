package com.edu.score.model;

import com.edu.score.Calculator;

import java.util.*;

/**
 * Created by Administrator on 2016/6/28.
 */
public class Entity {
    private int id;
    private String name;
    public final String INITIAL_NUMBER_VALUE = "-1";
    private Map<String,Object> paramMap = new HashMap<String,Object>();

    public static final String STUDENT_ID_ITEM_NAME="学号";
    public static final String STUDENT_NAME_ITEM_NAME="姓名";
    public static final String CHINESE_ITEM_NAME="语文";
    public static final String MATH_ITEM_NAME="数学";
    public static final String ENGLISH_ITEM_NAME="英语";
    public static final String CHEMISTRY_ITEM_NAME="化学";
    public static final String PHYCICS_ITEM_NAME="物理";
    public static final String BIOLOGY_ITEM_NAME="生物";
    public static final String POLITICS_ITEM_NAME="政治";
    public static final String GEOGRAPH_ITEM_NAME="地理";
    public static final String HISTORY_ITEM_NAME="历史";

    public static final String SELECTION_QUESTION_ITEM_NAME="选择题";
    public static final String SUBJECTIVE_QUESTION_ITEM_NAME="主观题";
    public static final String TOTAL_SCORE_ITEM_NAME="总分";
    public static final String AVG_SCORE_ITEM_NAME="平均分";
    public static final String RANK_ITEM_NAME="排名";
    public static final String ABSENCE="缺考";

    public static final double INVALID_SCORE_VALUE = -1;

//    public Entity(){
//        setId(INITIAL_NUMBER_VALUE);
//        setName("");
//    }
    
    public void addParam(String paramKey, Object paramValue){
        paramMap.put(paramKey,paramValue);
    }
    
    public Object getParamValue(String paramKey){
        return paramMap.get(paramKey);
    }

    public String getParamValueAsString(String paramKey){
        return String.valueOf(getParamValue(paramKey));
    }

    public Double getParamValueAsDouble(String paramKey){
        double value = INVALID_SCORE_VALUE;
        String content = String.valueOf(getParamValue(paramKey));
        try {
            value = Double.valueOf(content);
        }
        catch(NumberFormatException e){
            System.out.println("The content of paramKey ["+paramKey+"] is ["+content+"], " +
                    "cannot parse it into double type, return "+INVALID_SCORE_VALUE+".");
        }
        return value;
    }

    public int getParamsSize(){
        return paramMap.size();
    }

    public Set<String> getScoreParamKeyAsSet(){
        Set<String> set = new HashSet(paramMap.keySet());
        set.remove(STUDENT_ID_ITEM_NAME);
        set.remove(STUDENT_NAME_ITEM_NAME);
        return set;
    }

    public List<Double> getScoreParamValueAsList(){
        List<Double> scoreList = new ArrayList<Double>();
        for(String key:getScoreParamKeyAsSet() ){
            scoreList.add(getParamValueAsDouble(key));
        }
        return scoreList;
    }

    public boolean isAbsent(){
        if(ABSENCE.equals(getTotalScoreAsString()))
            return true;
        else
            return false;
    }

    public double getTotalScoreAsDouble(){
        return Calculator.sum(getScoreParamValueAsList());
    }

    public String getTotalScoreAsString(){
        double total = getTotalScoreAsDouble();
        if(total <= INVALID_SCORE_VALUE)
            return ABSENCE;
        else
            return String.valueOf(total);
    }

    public String getAverageScoreAsString(){
        String total = getTotalScoreAsString();
        if(ABSENCE.equals(total))
            return ABSENCE;
        else{
            return String.valueOf(Double.valueOf(total)/getScoreParamKeyAsSet().size());
        }
    }

//    //Setter
//    public void setId(String id)
//    {
//        this.id = Integer.valueOf(id);
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//    //Getter
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
}
