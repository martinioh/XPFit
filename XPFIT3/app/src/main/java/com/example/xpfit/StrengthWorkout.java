package com.example.xpfit;

public class StrengthWorkout {

    public String workoutname, workouttype, typeoflift, uid;
    public int  weight, reps, xpscore;

    public StrengthWorkout() {
    }

    public String getWorkoutname() {
        return workoutname;
    }

    public void setWorkoutname(String workoutname) {
        this.workoutname = workoutname;
    }

    public String getWorkouttype() {
        return workouttype;
    }

    public void setWorkouttype(String workouttype) {
        this.workouttype = workouttype;
    }

    public String getTypeoflift() {
        return typeoflift;
    }

    public void setTypeoflift(String typeoflift) {
        this.typeoflift = typeoflift;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getXpscore() {
        return xpscore;
    }

    public void setXpscore(int xpscore) {
        this.xpscore = xpscore;
    }

    public StrengthWorkout(String uid, String workoutname, String workouttype, String typeoflift, int reps, int weight, int xpscore) {
        this.uid = uid;
        this.workoutname = workoutname;
        this.workouttype = workouttype;
        this.typeoflift = typeoflift;
        this.reps = reps;
        this.weight = weight;
        this.xpscore = xpscore;
    }

}
