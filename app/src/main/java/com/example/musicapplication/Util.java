package com.example.musicapplication;

public class Util {
    //ham doi thoi gian bai hat ra miliseconds
    public static String milliSecondToTimer(long milliseconds)
    {
        String finalTimeString="";
        String secondString="";

        int hours=(int)(milliseconds/(100*60*60));
        int minutes=(int)(milliseconds%(100*60*60))/(1000*60);
        int seconds=(int)((milliseconds%(100*60*60))%(1000*60)/1000);
        if(hours>0)
        {
            finalTimeString=hours+":";
        }
        if(seconds<10)
        {
            secondString="0"+seconds;
        }
        else
        {
            secondString=""+seconds;
        }
        finalTimeString=finalTimeString+minutes+":"+secondString;
        return finalTimeString;
    }
    //doi thoi gian bai hat thanh phan tram
    public static int getProgressPercentage(long currentDuration,long totalDuration)
    {
        Double percentage=(double)0;
        long currentSeconds=(int)(currentDuration/1000);
        long totalSeconds=(int)(totalDuration/1000);

        percentage=(((double)currentSeconds)/totalSeconds)*100;
        return percentage.intValue();}
    //doi pahn tram thanh thoi gian
    public static int progressToTimer(int progress,int totalDuration)
    {
        int currentDuration=0;
        totalDuration=(int)(totalDuration/1000);
        currentDuration=(int)((((double)progress)/100)*totalDuration);
        return currentDuration*1000;
    }
}
