package com.edu.score.model;

/**
 * This Class is for recording the overall scores of all students in a sciences class
 * Created by Administrator on 2016/6/27.
 */
public class SciencesClassEntity extends ClassEntity {
    private float phycics;
    private float chemistry;
    private float biology;
    private int phycicsRank;
    private int chemistryRank;
    private int biologyRank;

    public SciencesClassEntity(){
        super();
        super.setIsSciences(true);
        setBiology(INITIAL_NUMBER_VALUE);
        setChemistry(INITIAL_NUMBER_VALUE);
        setPhycics(INITIAL_NUMBER_VALUE);
        setBiologyRank(INITIAL_NUMBER_VALUE);
        setChemistryRank(INITIAL_NUMBER_VALUE);
        setPhycicsRank(INITIAL_NUMBER_VALUE);
    }
    //Setter
    public void setPhycics(float phycics) {
        this.phycics = phycics;
    }

    public void setChemistry(float chemistry) {
        this.chemistry = chemistry;
    }

    public void setBiology(float biology) {
        this.biology = biology;
    }

    public void setPhycicsRank(int phycicsRank) {
        this.phycicsRank = phycicsRank;
    }

    public void setChemistryRank(int chemistryRank) {
        this.chemistryRank = chemistryRank;
    }

    public void setBiologyRank(int biologyRank) {
        this.biologyRank = biologyRank;
    }

    //Getter

    public float getPhycics() {
        return phycics;
    }

    public float getBiology() {
        return biology;
    }

    public float getChemistry() {
        return chemistry;
    }

    public int getBiologyRank() {
        return biologyRank;
    }

    public int getChemistryRank() {
        return chemistryRank;
    }

    public int getPhycicsRank() {
        return phycicsRank;
    }
}

