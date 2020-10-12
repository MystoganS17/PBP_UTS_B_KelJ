package com.tubes.rentalmotor;

import java.util.ArrayList;

public class CreditList{
    public ArrayList<Credit> CREDIT;
    public CreditList(){
        CREDIT = new ArrayList();
        CREDIT.add(Data);
    }
    public static final Credit Data = new Credit("180709977, 180709975, 170709525", "Juan Miguel, Yohanes Ryan Budhi Dharmawan, Ariel Topan Simanjuntak",
            "FTI", "Informatika");
}
