package com.edu.score;

import com.edu.score.model.Entity;
import com.edu.score.model.RankEntity;

import java.util.*;

/**
 * Utility Class to do calculation for all data
 * Created by Administrator on 2016/6/27.
 */
public class Calculator {

    public static double sum(List<Double> scoreList){
        double total = 0;
        for(double score: scoreList){
            total+=score;
        }
        return total;
    }

    public static double average(List<Double> scoreList){
        int num = scoreList.size();
        return sum(scoreList)/num;
    }

    /**
     * Get an id-rank map for a specific item from the given id-score map of the item
     * @param idScoreMap Map of id and the corresponding score of a specific item
     * @return Map of id and the rank number for the corresponding score.
     */
    public static Map<String,Integer> getIdRankMapByIdScoreMap(Map<String,Double> idScoreMap){
        List<Double> scoreList = new ArrayList<Double>(new HashSet<Double>(idScoreMap.values()));
        Collections.sort(scoreList,Collections.<Double>reverseOrder());
        Map<Double,Integer> scoreRankMap = new HashMap<Double,Integer>();

        for(int i=0;i<scoreList.size();i++){
            scoreRankMap.put(scoreList.get(i),i);
        }

        Map<String,Integer> idRankMap = new HashMap<String,Integer>();
        for(Map.Entry<String,Double> entry : idScoreMap.entrySet()){
            double score = entry.getValue()==Entity.INVALID_SCORE_VALUE? 0 :entry.getValue();
            idRankMap.put(entry.getKey(),scoreRankMap.get(score));
        }

        return idRankMap;
    }

    public static RankEntity getRankEntityFromEntityList(List<Entity> studentList) throws Exception{
        RankEntity ranks = new RankEntity();
        Map<String,Map<String,Double>> header_idScore_map = new HashMap<String,Map<String,Double>>();

        //TODO: Maybe the logic can be improved by doing rank in 1 loop
        for(Entity student: studentList){
            String id = student.getParamValueAsString(Entity.STUDENT_ID_ITEM_NAME);
            for(String item: student.getScoreParamKeyAsSet()){
                double score = student.getParamValueAsDouble(item);
                if(score==Entity.INVALID_SCORE_VALUE)
                    score=0;
                Map<String,Double> idScoreMap = header_idScore_map.get(item);
                if(idScoreMap == null){
                    idScoreMap = new HashMap<String,Double>();
                }
                idScoreMap.put(id,score);
                header_idScore_map.put(item,idScoreMap);
            }
            //TODO: add a rank for total score
        }

        for(Map.Entry<String,Map<String,Double>> entry : header_idScore_map.entrySet()){
            ranks.addIdRankMapForItem(entry.getKey(),getIdRankMapByIdScoreMap(entry.getValue()));
        }

        return ranks;
    }

}
