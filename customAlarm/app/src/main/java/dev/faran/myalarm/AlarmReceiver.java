package dev.faran.myalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fjaved on 5/11/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    String TAG = "AlarmReceiver";
    DatabaseHandler databaseHandler;
    int monToFri;
    int everyYear;
    int mYearWeekNum;
    int mYearDayNum;
    String time;
    String noEndDate;
    String mTitle,mContent,endDate;
    int patternDuration,occurrence;
    ArrayList<Alarm> alarmArrayList;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        int mReceivedID = -2;
        if (intent.getStringExtra(UtilMethods.EXTRA_REMINDER_ID) !=  null) {
            mReceivedID = Integer.parseInt(intent.getStringExtra(UtilMethods.EXTRA_REMINDER_ID));
            Log.d("test Id", "" + mReceivedID);
            String splitId = String.valueOf(mReceivedID);
            if (splitId.contains("909")) {
                Log.d("splitID", splitId);
                String[] parts = splitId.split("909");
                String part1 = parts[0];
                String part2 = parts[1];
                mReceivedID = Integer.valueOf(part1);
            }
            DatabaseHandler db = new DatabaseHandler(context);
            Alarm alarm = db.getAlarm(mReceivedID);
            mTitle = alarm.getTitle();
            mContent = alarm.getContent();
            occurrence = alarm.getEndAfterOccurrences();
            endDate = alarm.getEndByDate();
            noEndDate = alarm.getNoEndDate();
            everyYear = alarm.getAlarmEveryYear();
            mYearWeekNum = alarm.getAlarmYearWeekName();
            mYearDayNum = alarm.getAlarmYearDayName();
            time = alarm.getTime();
            monToFri = alarm.getMondayToFriday();
            patternDuration = alarm.getPatternDuration();
        }
        databaseHandler = new DatabaseHandler(context);

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                alarmArrayList = databaseHandler.getAllAlarm();
                for (int i = 0; i<alarmArrayList.size();i++){
                    if (alarmArrayList.get(i).getPatternDuration() == NotificationAddActivity.ONCE){
                        new NotificationAddActivity().setAlarmOnce(alarmArrayList.get(i).getID(),alarmArrayList.get(i).getDate(),alarmArrayList.get(i).getTime(),context);
                    }else if (alarmArrayList.get(i).getPatternDuration() == NotificationAddActivity.DAILY){
                        if (alarmArrayList.get(i).getMondayToFriday() == 1){
                            new NotificationAddActivity().monToFriAlarm(alarmArrayList.get(i),context);
                        }else {
                            new NotificationAddActivity().everyDayAlarm(alarmArrayList.get(i),context);
                        }
                    }else if (alarmArrayList.get(i).getPatternDuration() == NotificationAddActivity.WEEKLY){
                        new NotificationAddActivity().setWeeklyAlarm(alarmArrayList.get(i),context);
                    }else if (alarmArrayList.get(i).getPatternDuration() == NotificationAddActivity.MONTHLY){
                        if (alarmArrayList.get(i).getDayOfMonth() != 0 ){
                            new NotificationAddActivity().everyMonthAlarm(alarmArrayList.get(i),context);
                        }else {
                            new NotificationAddActivity().setMonthlyWithWeeklyAndDayAlarm(alarmArrayList.get(i),context);
                        }
                    }else if (alarmArrayList.get(i).getPatternDuration() == NotificationAddActivity.YEARLY){
                        if (alarmArrayList.get(i).getAlarmYearMonthDay() != 0){
                            new NotificationAddActivity().setEveryYearAlarm(alarmArrayList.get(i),context);
                        }else {
                            new NotificationAddActivity().setYearlyWithWeekAndDayAlarm(alarmArrayList.get(i),context);
                        }
                    }
                }

                return;
            }
        }

        Log.d(TAG, "onReceive: ");

        if (UtilMethods.intToBool(monToFri)){
            if (!isWeekend()){
                //Trigger the notification
                NotificationScheduler.showNotification(context, MainActivity.class,
                        mTitle, mContent,mReceivedID);
            }
        }else {
            //Trigger the notification
            NotificationScheduler.showNotification(context, MainActivity.class,
                    mTitle, mContent,mReceivedID);

        }


        checkOccurrences(context, mReceivedID, occurrence, endDate);

    }

    private void checkOccurrences(Context context, int mReceivedID, int occurrence, String endDate) {
        if (patternDuration != NotificationAddActivity.MONTHLY) {
            if (occurrence != -1) {
                /*if (UtilMethods.intToBool(monToFri)) {
                    if (isSunday()) {
                        occurrence--;
                    }
                } else {
                    occurrence--;
                }*/
                occurrence--;
                if (occurrence > 0) {
                    databaseHandler.updateOccurrence(mReceivedID, occurrence);
                } else {
                    if (patternDuration == NotificationAddActivity.WEEKLY) {
                        ArrayList<Integer> alarmIdList = databaseHandler.getSubIdAlarm(mReceivedID);
                        databaseHandler.deleteAlarm(mReceivedID);
                        for (int i = 0; i < alarmIdList.size(); i++) {
                            NotificationScheduler.cancelReminder(context, AlarmReceiver.class, alarmIdList.get(i));
                        }
                    } else {
                        NotificationScheduler.cancelReminder(context, AlarmReceiver.class, mReceivedID);
                        databaseHandler.deleteAlarm(mReceivedID);
                    }
                }
            }


            if (!endDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                try {
                    Date convertedDate = sdf.parse(endDate);
                    if (!convertedDate.after(new Date())) {
                        if (patternDuration == NotificationAddActivity.WEEKLY) {
                            ArrayList<Integer> alarmIdList = databaseHandler.getSubIdAlarm(mReceivedID);
                            databaseHandler.deleteAlarm(mReceivedID);
                            for (int i = 0; i < alarmIdList.size(); i++) {
                                NotificationScheduler.cancelReminder(context, AlarmReceiver.class, alarmIdList.get(i));
                            }
                        } else {
                            NotificationScheduler.cancelReminder(context, AlarmReceiver.class, mReceivedID);
                            databaseHandler.deleteAlarm(mReceivedID);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            /*
            * This is used to set year alarm again after year time interval
            * */

            if (patternDuration == NotificationAddActivity.YEARLY) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                if (occurrence > 0) {
                    int year = calendar.get(Calendar.YEAR);
                    calendar.set(Calendar.YEAR, year + everyYear);
                    if (mYearDayNum !=0 && mYearWeekNum !=0){
                        int maximumWeekOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                        if (mYearWeekNum <= maximumWeekOfMonth) {
                            calendar.set(Calendar.WEEK_OF_MONTH, mYearWeekNum);
                            calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                        } else {
                            calendar.set(Calendar.WEEK_OF_MONTH, maximumWeekOfMonth);
                            calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                        }
                       /* calendar.set(Calendar.WEEK_OF_MONTH,mYearWeekNum);
                        calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);*/
                    }
                    Log.d("nextYear", "" + calendar.getTimeInMillis());
                    new NotificationScheduler().setYearlyAlarm(context, calendar, mReceivedID);
                }
                else if (!endDate.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                    try {
                        Date convertedDate = sdf.parse(endDate);
                        if (convertedDate.after(new Date())) {
                            int year = calendar.get(Calendar.YEAR);
                            calendar.set(Calendar.YEAR, year + everyYear);
                            if (mYearDayNum !=0 && mYearWeekNum !=0){
                                int maximumWeekOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                                if (mYearWeekNum <= maximumWeekOfMonth) {
                                    calendar.set(Calendar.WEEK_OF_MONTH, mYearWeekNum);
                                    calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                                } else {
                                    calendar.set(Calendar.WEEK_OF_MONTH, maximumWeekOfMonth);
                                    calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                                }
                                /*calendar.set(Calendar.WEEK_OF_MONTH,mYearWeekNum);
                                calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);*/
                            }
                            Log.d("nextYear", "" + calendar.getTimeInMillis());
                            new NotificationScheduler().setYearlyAlarm(context, calendar, mReceivedID);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    if (!noEndDate.isEmpty()) {
                        if (noEndDate.equalsIgnoreCase("Yes")) {
                            int year = calendar.get(Calendar.YEAR);
                            calendar.set(Calendar.YEAR, year + everyYear);
                            if (mYearDayNum !=0 && mYearWeekNum !=0){
                                int maximumWeekOfMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                                if (mYearWeekNum <= maximumWeekOfMonth) {
                                    calendar.set(Calendar.WEEK_OF_MONTH, mYearWeekNum);
                                    calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                                } else {
                                    calendar.set(Calendar.WEEK_OF_MONTH, maximumWeekOfMonth);
                                    calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);
                                }
                                /*calendar.set(Calendar.WEEK_OF_MONTH,mYearWeekNum);
                                calendar.set(Calendar.DAY_OF_WEEK,mYearDayNum);*/
                            }
                            Log.d("nextYear", "" + calendar.getTimeInMillis());
                            new NotificationScheduler().setYearlyAlarm(context, calendar, mReceivedID);
                        }
                    }
                }
            }

            if (patternDuration == NotificationAddActivity.ONCE){
                databaseHandler.deleteAlarm(mReceivedID);
            }
        }
    }

    private boolean isWeekend() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }
    private Boolean isSunday(){
        Calendar calendar = Calendar.getInstance();
        int sunday = calendar.get(Calendar.DAY_OF_WEEK);
        return sunday == Calendar.SUNDAY;
    }
    private Boolean isSaturday(){
        Calendar calendar = Calendar.getInstance();
        int sunday = calendar.get(Calendar.DAY_OF_WEEK);
        return sunday == Calendar.SATURDAY;
    }

}
