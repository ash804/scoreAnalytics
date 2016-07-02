package com.edu.score.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This Class is for recording the score rank of each subject for every student
 * Created by Administrator on 2016/6/27.
 */
public class RankEntity {
//    public static Map<Integer,Integer> idChineseRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idMathRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idEnglishRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idChemistryPhycicsRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idBiologyRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idPoliticsRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idGeographyRankMap = new HashMap<Integer,Integer>();

    //TODO: Add dynamic rank map here for different items
    private Map<String,Map<String,Integer>> dynamicIdRankMap = new HashMap<String,Map<String,Integer>>();

    public void addIdRankMapForItem(String itemName, Map<String,Integer> idRankMap) throws Exception{
        if(itemName==null || itemName.trim().isEmpty())
            throw new Exception("The item name should not be null or empty string");
        dynamicIdRankMap.put(itemName,idRankMap);
    }

    public Map<String,Integer> getIdRankMapByItem(String itemName) throws Exception{
        if(itemName==null || itemName.trim().isEmpty())
            throw new Exception("The item name should not be null or empty string");
        return dynamicIdRankMap.get(itemName);
    }

}
