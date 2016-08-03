package com.edu.score;

import com.edu.score.model.Entity;

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

    public static double averageForValidScores(List<Double> scoreList){
        double total = 0;
        int invalidCount = 0;
        for(double score : scoreList){
            if(score> Entity.INVALID_SCORE_VALUE){
                total += score;
            }
            else{
                invalidCount++;
            }
        }
        if(invalidCount != 0)
            System.out.println("Number of invalid scores in the score list for calculating average score:"+invalidCount);
        //System.out.println("total:"+total+" "+(scoreList.size() - invalidCount));
        return total/(scoreList.size() - invalidCount);
    }





}
