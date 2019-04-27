package dev.faran.myalarm;


import android.content.Context;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by fjaved on 4/2/2018.
 */

public class UtilMethods {
    Context mContext;
    public static final String SUNDAY = "Sunday";
    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";
    public static final String SATURDAY = "Saturday";
    // Constant Intent String
    public static final String EXTRA_REMINDER_ID = "Reminder_ID";

    public UtilMethods(Context context) {
        mContext = context;
    }



    public static String convertDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stringFormat = new SimpleDateFormat("MMM dd, yyyy");

        Date convertedDate = null;
        try {
            convertedDate = sdf.parse(date);
            String date2 = stringFormat.format(convertedDate);
            return date2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Boolean boolValueOf(String value) {
        if (value.equalsIgnoreCase("True")) {
            return true;
        } else {
            return false;
        }
    }
    public static int stringValueOf(Boolean value) {
        if (value) {
            return 1;
        } else {
            return 0;
        }
    }
    public static Boolean intToBool(int i){
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }


    public static int getTodayNum(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.MONDAY:
                // Current day is Monday
                return  2;
            case Calendar.TUESDAY:
                return 3;
            case Calendar.WEDNESDAY:
                return  4;
            case Calendar.THURSDAY:
               return  5;
            case Calendar.FRIDAY:
                return 6;
            case Calendar.SATURDAY:
                return  7;
            case Calendar.SUNDAY:
                // Current day is Sunday
                return 1;
        }
        return 0;
    }

    public static ArrayList<Integer> getDaysArray(Alarm alarm, String startDate){
        ArrayList<Integer> arrayListDays  = new ArrayList<>();
        ArrayList<Integer> daySize = getDaySize(alarm);
        Log.d("dayarray", ""+daySize.size());
        int b =0;
        String dtStart = startDate;
        Calendar calendar = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = format.parse(dtStart);
            calendar = Calendar.getInstance();
            calendar.setTime(date);

            while (true){
                if (daySize.size() != arrayListDays.size()) {
                    int dayNumber = calendar.get(Calendar.DAY_OF_WEEK);
                    if (alarm.getSunday() != 0) {
                        if (dayNumber == alarm.getSunday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getMonDay() != 0) {
                        if (dayNumber == alarm.getMonDay()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getTuesday() != 0) {
                        if (dayNumber == alarm.getTuesday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getWednesday() != 0) {
                        if (dayNumber == alarm.getWednesday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getThursday() != 0) {
                        if (dayNumber == alarm.getThursday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getFriday() != 0) {
                        if (dayNumber == alarm.getFriday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    if (alarm.getSaturday() != 0) {
                        if (dayNumber == alarm.getSaturday()) {
                            arrayListDays.add(dayNumber);
                        }
                    }
                    calendar.add(Calendar.DATE,1);
                }else {
                    break;
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return arrayListDays;

    }

    public static ArrayList<Integer> getDaySize(Alarm alarm){
        ArrayList<Integer> arrayListDays  = new ArrayList<>();

        if (alarm.getMonDay() != 0){
            arrayListDays.add(alarm.getMonDay());
        }if (alarm.getTuesday() != 0){
            arrayListDays.add(alarm.getTuesday());
        }if (alarm.getWednesday() != 0){
            arrayListDays.add(alarm.getWednesday());
        }if (alarm.getThursday() != 0){
            arrayListDays.add(alarm.getThursday());
        }if (alarm.getFriday() != 0){
            arrayListDays.add(alarm.getFriday());
        }if (alarm.getSaturday() != 0){
            arrayListDays.add(alarm.getSaturday());
        }if (alarm.getSunday() != 0){
            arrayListDays.add(alarm.getSunday());
        }
        return arrayListDays;
    }

    public static String getDayName(String strDate) {

        String dtStart = strDate;
        String day ="";
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = format.parse(dtStart);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String[] days = new String[] { "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY" };
            day = days[calendar.get(Calendar.DAY_OF_WEEK)];

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static int dayNumber(String dayName){

        if (dayName.equalsIgnoreCase(SUNDAY)){
            return 1;
        }else if (dayName.equalsIgnoreCase(MONDAY)){
            return 2;
        }else if (dayName.equalsIgnoreCase(TUESDAY)){
            return 3;
        }else if (dayName.equalsIgnoreCase( WEDNESDAY)){
            return 4;
        }else if (dayName.equalsIgnoreCase( THURSDAY)){
            return 5;
        }else if (dayName.equalsIgnoreCase(FRIDAY)){
            return 6;
        }else if (dayName.equalsIgnoreCase(SATURDAY)){
            return 7;
        }
        return 0;
    }

    public static String dayName(int dayNumber){

        if (dayNumber == 1){
            return SUNDAY;
        }else if (dayNumber == 2){
            return MONDAY;
        }else if (dayNumber == 3){
            return TUESDAY;
        }else if (dayNumber == 4){
            return WEDNESDAY;
        }else if (dayNumber == 5){
            return THURSDAY;
        }else if (dayNumber == 6){
            return FRIDAY;
        }else if (dayNumber == 7){
            return SATURDAY;
        }
        return "";
    }


    public static  int weekNumber(String weekName){
        if (weekName.equalsIgnoreCase("First")){
            return 1;
        }else if (weekName.equalsIgnoreCase("Second")){
            return 2;
        }else if (weekName.equalsIgnoreCase("Third")){
            return 3;
        }else if (weekName.equalsIgnoreCase("Fourth")){
            return 4;
        }else if (weekName.equalsIgnoreCase("Last")){
            return 5;
        }
        return 0;
    }
    public static String weekName(int weekNumber){
        if (weekNumber == 1){
            return "First";
        }else if (weekNumber == 2){
            return "Second";
        }else if (weekNumber == 3){
            return "Third";
        }else if (weekNumber ==  4){
            return "Fourth";
        }else if (weekNumber == 5){
            return "Last";
        }
        return "";
    }

    public static int monthNumber(String monthName){

        if (monthName.equalsIgnoreCase("January")){
            return 0;
        }else if (monthName.equalsIgnoreCase("February")){
            return 1;
        }else if (monthName.equalsIgnoreCase("March")){
            return 2;
        }else if (monthName.equalsIgnoreCase( "April")){
            return 3;
        }else if (monthName.equalsIgnoreCase( "May")){
            return 4;
        }else if (monthName.equalsIgnoreCase("June")){
            return 5;
        }else if (monthName.equalsIgnoreCase("July")){
            return 6;
        } else if (monthName.equalsIgnoreCase("August")){
            return 7;
        } else if (monthName.equalsIgnoreCase("September")){
            return 8;
        } else if (monthName.equalsIgnoreCase("October")){
            return 9;
        } else if (monthName.equalsIgnoreCase("November")){
            return 10;
        } else if (monthName.equalsIgnoreCase("December")){
            return 11;
        }
        return -1;
    }

    public static String monthName(int monthNumber){

        if (monthNumber == 0){
            return "January";
        }else if (monthNumber == 1){
            return "February";
        }else if (monthNumber == 2){
            return "March";
        }else if (monthNumber == 3){
            return "April";
        }else if (monthNumber == 4){
            return  "May";
        }else if (monthNumber == 5){
            return "June";
        }else if (monthNumber == 6){
            return "July";
        } else if (monthNumber == 7){
            return "August";
        } else if (monthNumber ==  8){
            return "September";
        } else if (monthNumber == 9){
            return "October";
        } else if (monthNumber == 10){
            return "November";
        } else if (monthNumber == 11){
            return "December";
        }
        return "";
    }

    public static int getDifferencePositive(int num1, int num2){

        return  Math.abs(num1 - num2);
    }

    public static Calendar getTriggerDate(Calendar startDate, ArrayList<Integer> daysNumberArray) {
//        String dtStart = startDate;
//        Calendar calendar = null;
        int c = 0;
        /*SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = format.parse(dtStart);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
*/

        Boolean found = false;
        while (true) {
            c++;
            Log.d("gettrigger Date", "" + c);
            for (int i = 0; i < daysNumberArray.size(); i++) {
                if (startDate.get(Calendar.DAY_OF_WEEK) == daysNumberArray.get(i)) {
                    found = true;
                    Log.d("found", "" + daysNumberArray.get(i));
                    break;
                }
            }
            if (found)
                break;

            startDate.add(Calendar.DATE, 1);
        }

        /*} catch (ParseException e) {
            e.printStackTrace();
        }*/
        return startDate;
    }

    public static int[] getSplitDate(String startDate){
        String[] date = startDate.split("/");
        int month = Integer.parseInt(date[0]);
        int dayOfMonth = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        return new int[]{month,dayOfMonth,year};
    }

    public static int[] getSplitTime(String time){
        String[] sTime = time.split(":");
        int hour = Integer.parseInt(sTime[0]);
        int minute = Integer.parseInt(sTime[1]);
        return new int[]{hour,minute};
    }

}
