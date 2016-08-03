package com.edu.score.model;

import com.edu.score.Calculator;
import com.edu.score.CommonUtil;
import sun.reflect.annotation.ExceptionProxy;

import java.util.*;

/**
 * This Class is for recording the score rank of each subject for every student
 * Created by Administrator on 2016/6/27.
 */
public class ClassEntity {
//    public static Map<Integer,Integer> idChineseRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idMathRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idEnglishRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idChemistryPhycicsRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idBiologyRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idPoliticsRankMap = new HashMap<Integer,Integer>();
//    public static Map<Integer,Integer> idGeographyRankMap = new HashMap<Integer,Integer>();
    
    private Map<String,Map<String,Integer>> dynamicIdRankMap;
    private Map<String,Map<String,Double>> dynamicIdScoreMap;
    private Map<String,List<String>> itemSortedIdMap;
    private Map<String,String> idNameMap;

    public ClassEntity(List<Entity> studentList) throws Exception{
        initialize(studentList);
    }

    /**
     * Update the id-rank map for a specific item.
     * @param itemName Name of a specific item (subject)
     * @param idRankMap Id-rank map
     * @throws Exception
     */
    private void addIdRankMapForItem(String itemName, Map<String,Integer> idRankMap) throws Exception{
        if(CommonUtil.isNullOrEmpty(itemName))
            throw new Exception("The item name should not be null or empty string");
        dynamicIdRankMap.put(itemName,new HashMap<String, Integer>(idRankMap));
    }

    /**
     * Get an id-rank map for a specific item.
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @return
     * @throws Exception
     */
    public Map<String,Integer> getIdRankMapByItem(String itemName) throws Exception{
        if(CommonUtil.isNullOrEmpty(itemName))
            throw new Exception("The item name should not be null or empty string");
        if(dynamicIdRankMap == null)
            throw new Exception("The ClassEntity instance hasn't been initialized");
        if(dynamicIdRankMap.get(itemName) == null)
            throw new Exception("Cannot find any id-rank map matched to the given item name:"+itemName);
        return new HashMap<>(dynamicIdRankMap.get(itemName));
    }

    /**
     * Get an id-score map for a specific item.
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @return
     * @throws Exception
     */
    public Map<String,Double> getIdScoreMapByItem(String itemName) throws Exception{
        if(CommonUtil.isNullOrEmpty(itemName))
            throw new Exception("The item name should not be null or empty string");
        if(dynamicIdScoreMap == null)
            throw new Exception("The ClassEntity instance hasn't been initialized");
        if(dynamicIdScoreMap.get(itemName) == null)
            throw new Exception("Cannot find any id-score map matched to the given item name:"+itemName);
        return new HashMap<>(dynamicIdScoreMap.get(itemName));
    }

    /**
     * Get a score list for a specific item.
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @return
     * @throws Exception
     */
    public List<Double> getScoreListByItem(String itemName) throws Exception{
        Map<String,Double> idScoreMap = getIdScoreMapByItem(itemName);
        if(idScoreMap == null)
            throw new Exception("The id-score map for item ["+itemName+"] doesn't exist, cannot get the corresponding score list.");
        return new ArrayList<Double>(idScoreMap.values());
    }

    /**
     * Get a id list in the ASC order of the rank number for a specific item.
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @return
     * @throws Exception
     */
    public List<String> getIdRankListByItem(String itemName) throws Exception{
        if(CommonUtil.isNullOrEmpty(itemName))
            throw new Exception("The item name should not be null or empty string");
        if(itemSortedIdMap == null)
            throw new Exception("The ClassEntity instance hasn't been initialized");
        if(itemSortedIdMap.get(itemName) == null)
            throw new Exception("Cannot find any irankIdList matched to the given item name:"+itemName);
        return new ArrayList<>(itemSortedIdMap.get(itemName));
    }

    /**
     * Get the student name with the given student id.
     * This method must run after initialize().
     * @param studentId Student id
     * @return
     * @throws Exception
     */
    public String getStudentNameById(String studentId) throws Exception{
        if(CommonUtil.isNullOrEmpty(studentId))
            throw new Exception("The student id should not be null or empty string");
        return idNameMap.get(studentId);
    }

    /**
     * Get the average score of all students in this ClassEntity instance in a specific item(subject).
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @return
     * @throws Exception
     */
    public double getAverageScoreByItem(String itemName) throws Exception{
        return Calculator.averageForValidScores(getScoreListByItem(itemName));
    }

    /**
     * Get the score of a student in a specific item(subject).
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @param id Student id
     * @return
     * @throws Exception
     */
    public double getScore(String itemName, String id) throws Exception{
        return getIdScoreMapByItem(itemName).get(id);
    }

    /**
     * Get the rank number of a student in a specific item.
     * This method must run after initialize().
     * @param itemName Name of a specific item (subject)
     * @param id Student id
     * @return
     * @throws Exception
     */
    public int getRankNumber(String itemName, String id) throws Exception{
        return getIdRankMapByItem(itemName).get(id);
    }

    /**
     * Get an id-rank map from the given id-score map of the item.
     * @param idScoreMap Map of id and the corresponding score of a specific item
     * @return Map of id and the rank number for the corresponding score.
     */
    public static Map<String,Integer> getIdRankMapByIdScoreMap(Map<String,Double> idScoreMap){
        List<Double> scoreList = new ArrayList<Double>(idScoreMap.values());
        Collections.sort(scoreList,Collections.<Double>reverseOrder());
        Map<Double,Integer> scoreRankMap = new HashMap<Double,Integer>();

        int rankNum = 1;
        int lastRankNum = rankNum;
        double lastScore = -1;
        //Rank start with 1
        for(int i=0;i<scoreList.size();i++){
            double currentScore = scoreList.get(i);
            if(lastScore == currentScore){
                rankNum = lastRankNum;
            }
            else{
                rankNum = i + 1;
                lastRankNum = rankNum;
            }
            scoreRankMap.put(currentScore,rankNum);
            lastScore = currentScore;
        }

        Map<String,Integer> idRankMap = new HashMap<String,Integer>();
        for(Map.Entry<String,Double> entry : idScoreMap.entrySet()){
            double score = entry.getValue();//entry.getValue()==Entity.INVALID_SCORE_VALUE? 0 :entry.getValue();
            idRankMap.put(entry.getKey(),scoreRankMap.get(score));
        }

        return idRankMap;
    }

    /**
     * Get a id list with in an ASC order according to the rank number in the given id-rank map.
     * @param idRankMap Map of student id and the rank number for a specific item
     * @return
     * @throws Exception
     */
    public static List<String> getIdRankListByIdRankMap(Map<String,Integer> idRankMap) throws Exception{
        if(idRankMap==null || idRankMap.size()==0)
            return new ArrayList<>();
        String[] idArray = new String[idRankMap.size()];
        for(Map.Entry<String,Integer> entry : idRankMap.entrySet()){
            int index = entry.getValue()-1;
            for(int i=index; i<=idArray.length;i++){
                if(i == idArray.length){
                    throw new Exception("The method want to put the id into index "+i
                            +", but the idArray length is "+idArray.length+", check the array:"+Arrays.toString(idArray));
                }

                if(idArray[i]==null){
                    idArray[i] = entry.getKey();
                    break;
                }
            }
        }
        return Arrays.asList(idArray);
    }

    /**
     * Initialize the ClassEntity instance from a Entity list.
     * After initialization, id-rank maps ,id-score maps and id-rank lists for each score item will be generated, including total score.
     * @param studentList List of Entity instances
     * @return
     * @throws Exception
     */
    public void initialize(List<Entity> studentList) throws Exception{
        dynamicIdRankMap = new HashMap<String,Map<String,Integer>>();
        dynamicIdScoreMap = new HashMap<String,Map<String,Double>>();
        idNameMap = new HashMap<String,String>();
        itemSortedIdMap = new HashMap<String,List<String>>();

        //TODO: Maybe the logic can be improved by doing rank in 1 loop
        for(Entity student: studentList){
            String id = student.getParamValueAsString(Entity.STUDENT_ID_ITEM_NAME);
            idNameMap.put(id,student.getParamValueAsString(Entity.STUDENT_NAME_ITEM_NAME));
            Set<String> scoreParamSet = new HashSet(student.getScoreParamKeyAsSet());
            //Add a rank for total score
            scoreParamSet.add(Entity.TOTAL_SCORE_ITEM_NAME);
            for(String item: scoreParamSet){
                double score = 0;
                if(Entity.TOTAL_SCORE_ITEM_NAME.equals(item))
                    score = student.getTotalScoreAsDouble();
                else
                    score = student.getParamValueAsDouble(item);

//                if(score<=Entity.INVALID_SCORE_VALUE)
//                    score=0;
                Map<String,Double> idScoreMap = dynamicIdScoreMap.get(item);
                if(idScoreMap == null){
                    idScoreMap = new HashMap<String,Double>();
                }
                idScoreMap.put(id,score);
                dynamicIdScoreMap.put(item,idScoreMap);
            }
        }

        for(Map.Entry<String,Map<String,Double>> entry : dynamicIdScoreMap.entrySet()){
            Map<String,Integer> idRankMap = getIdRankMapByIdScoreMap(entry.getValue());
            this.addIdRankMapForItem(entry.getKey(),idRankMap);
            itemSortedIdMap.put(entry.getKey(),getIdRankListByIdRankMap(idRankMap));
        }
    }
}
