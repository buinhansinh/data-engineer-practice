package com.fpt.bean.model;

/**
 * Created by zack on 9/19/2016.
 */
public class Person {
    private final int age;
    private final String workClass;
    private final double fnlwgt;
    private final String education;
    private final int educationNum;
    private final String maritialStatus;
    private final String occupation;
    private final String relationship;
    private final String race;
    private final String sex;
    private final double capitalGain;
    private final double capticalLoss;
    private final double hoursPerWeek;
    private final String nativeCountry;
    private final String salary;

    public Person(String csvLine) {
        final String[] fields = csvLine.split(", ");
        if(fields.length != 15) throw new RuntimeException("Invalid csv format");
        this.age = Integer.parseInt(fields[0]);
        this.workClass = fields[1];
        this.fnlwgt =  Double.parseDouble(fields[2]);
        this.education = fields[3];
        this.educationNum = Integer.parseInt(fields[4]);
        this.maritialStatus = fields[5];
        this.occupation = fields[6];
        this.relationship = fields[7];
        this.race = fields[8];
        this.sex = fields[9];
        this.capitalGain = Double.parseDouble(fields[10]);
        this.capticalLoss = Double.parseDouble(fields[11]);
        this.hoursPerWeek = Double.parseDouble(fields[12]);
        this.nativeCountry = fields[13];
        this.salary = fields[14];
    }

    public int getAge() {
        return age;
    }

    public String getWorkClass() {
        return workClass;
    }

    public double getFnlwgt() {
        return fnlwgt;
    }

    public String getEducation() {
        return education;
    }

    public int getEducationNum() {
        return educationNum;
    }

    public String getMaritialStatus() {
        return maritialStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getRace() {
        return race;
    }

    public String getSex() {
        return sex;
    }

    public double getCapitalGain() {
        return capitalGain;
    }

    public double getCapticalLoss() {
        return capticalLoss;
    }

    public double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public String getNativeCountry() {
        return nativeCountry;
    }

    public String getSalary() {
        return salary;
    }
}
