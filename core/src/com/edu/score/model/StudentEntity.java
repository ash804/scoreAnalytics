package com.edu.score.model;

/**
 * This Class is for recording a student's scores of basic objects, e.g. chinese, math, english.
 * Created by Administrator on 2016/6/27.
 */
public class StudentEntity {
    private int id;
    private String name;
    private float chinese;
    private float math;
    private float english;
    public final String INITIAL_NUMBER_VALUE = "-1";

    public StudentEntity(){
        setChinese(INITIAL_NUMBER_VALUE);
        setMath(INITIAL_NUMBER_VALUE);
        setEnglish(INITIAL_NUMBER_VALUE);
        setId(INITIAL_NUMBER_VALUE);
        setName("");
    }

    public StudentEntity(String id, String name,String chinese,String math, String english){
        setChinese(chinese);
        setMath(math);
        setEnglish(english);
        setId(id);
        setName(name);
    }
    public void setId(String id)
    {
        this.id = Integer.valueOf(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMath(String math) {
        this.math = Float.valueOf(math);
    }

    public void setChinese(String chinese) {
        this.chinese = Float.valueOf(chinese);
    }

    public void setEnglish(String english) {
        this.english = Float.valueOf(english);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getChinese() {
        return chinese;
    }

    public float getEnglish() {
        return english;
    }

    public float getMath() {
        return math;
    }
}
