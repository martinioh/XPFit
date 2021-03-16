package com.example.xpfit;

public class CardioWorkout {

    public String workoutname, workouttype, uid;
    public int  time, distance, xpscore;


    public CardioWorkout() {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getXpscore() {
        return xpscore;
    }

    public void setXpscore(int xpscore) {
        this.xpscore = xpscore;
    }

    public CardioWorkout(String uid, String workoutname, String workouttype, int time, int distance, int xpscore) {
        this.uid = uid;
        this.workoutname = workoutname;
        this.workouttype = workouttype;
        this.time = time;
        this.distance = distance;
        this.xpscore = xpscore;
    }

}
