package dev.faran.myalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by fjaved on 6/21/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "mdvision";

    public static final String TABLE_ALARM = "alarm";
    public static final String ALARM_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String ALARM_DESCRIPTION = "alarm_description";
    public static final String ALARM_TITLE = "alarm_title";
    public static final String ALARM_DATE = "alarm_date";
    public static final String ALARM_TIME = "alarm_time";
    public static final String ALARM_PATTERN_DURATION = "alarm_pattern_duration";
    public static final String ALARM_EVERY_DAY = "alarm_every_day";
    public static final String ALARM_MON_FRI = "alarm_mon_fri";
    public static final String ALARM_EVERY_WEEK = "alarm_every_week";
    public static final String ALARM_SUN = "alarm_sun";
    public static final String ALARM_MON = "alarm_mon";
    public static final String ALARM_TUE = "alarm_tue";
    public static final String ALARM_WED = "alarm_wed";
    public static final String ALARM_THUR = "alarm_thur";
    public static final String ALARM_FRI = "alarm_fri";
    public static final String ALARM_SAT = "alarm_sat";
    public static final String ALARM_DAY_OF_MONTH = "alarm_day_of_month";
    public static final String ALARM_EVERY_MONTH = "alarm_every_month";
    public static final String ALARM_MONTH_WEEK_NUMBER = "alarm_month_week_num";
    public static final String ALARM_MONTH_DAY_NAME = "alarm_month_day_name";
    public static final String ALARM_EVERY_YEAR = "alarm_every_year";
    public static final String ALARM_YEAR_MONTH_NAME = "alarm_year_month_name";
    public static final String ALARM_YEAR_MONTH_DATE = "alarm_year_month_date";
    public static final String ALARM_YEAR_WEEK_NUM = "alarm_year_week_num";
    public static final String ALARM_YEAR_DAY_NAME = "alarm_year_day_name";
    public static final String ALARM_OCCURRENCE_NO_END_DATE = "alarm_occurrence_no_end_date";
    public static final String ALARM_OCCURRENCE_END_AFTER = "alarm_occurrence_end_after";
    public static final String ALARM_OCCURRENCE_END_BY = "alarm_occurrence_end_by";
    public static final String ALARM_IS_ACTIVE = "alarm_is_active";
    public static final String ALARM_IS_REPEAT = "alarm_is_repeat";
    public static final String ALARM_TOTAL_OCCURRENCES = "alarm_total_occurrences";


    public static final String TABLE_ALARM_SUB_ID = "alarm_sub_id_table";
    public static final String SUB_ID = "sub_id";
    public static final String DATABASE_ID = "database_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ALARM_TABLE = "CREATE TABLE " + TABLE_ALARM + "("
                + ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ALARM_DESCRIPTION + " TEXT,"
                + ALARM_TITLE + " TEXT,"
                + ALARM_DATE + " TEXT,"
                + ALARM_TIME + " TEXT,"
                + ALARM_PATTERN_DURATION + " INTEGER,"
                + ALARM_EVERY_DAY + " INTEGER,"
                + ALARM_MON_FRI + " INTEGER DEFAULT 0,"
                + ALARM_EVERY_WEEK + " INTEGER,"
                + ALARM_SUN + " INTEGER,"
                + ALARM_MON + " INTEGER,"
                + ALARM_TUE + " INTEGER,"
                + ALARM_WED + " INTEGER,"
                + ALARM_THUR + " INTEGER,"
                + ALARM_FRI + " INTEGER,"
                + ALARM_SAT + " INTEGER,"
                + ALARM_DAY_OF_MONTH + " INTEGER,"
                + ALARM_EVERY_MONTH + " INTEGER,"
                + ALARM_MONTH_WEEK_NUMBER + " INTEGER,"
                + ALARM_MONTH_DAY_NAME + " INTEGER,"
                + ALARM_EVERY_YEAR + " INTEGER,"
                + ALARM_YEAR_MONTH_NAME + " INTEGER,"
                + ALARM_YEAR_MONTH_DATE + " INTEGER,"
                + ALARM_YEAR_WEEK_NUM + " INTEGER,"
                + ALARM_YEAR_DAY_NAME + " INTEGER,"
                + ALARM_OCCURRENCE_NO_END_DATE + " TEXT,"
                + ALARM_OCCURRENCE_END_AFTER + " INTEGER,"
                + ALARM_OCCURRENCE_END_BY + " TEXT,"
                + ALARM_IS_ACTIVE + " INTEGER DEFAULT 0,"
                + ALARM_IS_REPEAT + " INTEGER DEFAULT 0,"
                + ALARM_TOTAL_OCCURRENCES + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_ALARM_TABLE);


        String CREATE_ALARM_SUB_IDS_TABLE = "CREATE TABLE " + TABLE_ALARM_SUB_ID + "("
                + SUB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ALARM_ID + " INTEGER,"
                + DATABASE_ID + " INTEGER" + ")";
        sqLiteDatabase.execSQL(CREATE_ALARM_SUB_IDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int addAlarm(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ALARM_TITLE , alarm.getTitle());
        values.put(ALARM_DESCRIPTION , alarm.getContent());
        values.put(ALARM_DATE , alarm.getDate());
        values.put(ALARM_TIME , alarm.getTime());
        values.put(ALARM_OCCURRENCE_NO_END_DATE , alarm.getNoEndDate());
        values.put(ALARM_OCCURRENCE_END_AFTER, alarm.getEndAfterOccurrences());
        values.put(ALARM_OCCURRENCE_END_BY, alarm.getEndByDate());
        values.put(ALARM_PATTERN_DURATION, alarm.getPatternDuration());
        values.put(ALARM_EVERY_DAY, alarm.getEveryDay());
        values.put(ALARM_MON_FRI, alarm.getMondayToFriday());
        values.put(ALARM_EVERY_WEEK,alarm.getEveryWeek());
        values.put(ALARM_MON,alarm.getMonDay());
        values.put(ALARM_TUE,alarm.getTuesday());
        values.put(ALARM_WED,alarm.getWednesday());
        values.put(ALARM_THUR,alarm.getThursday());
        values.put(ALARM_FRI,alarm.getFriday());
        values.put(ALARM_SAT,alarm.getSaturday());
        values.put(ALARM_SUN,alarm.getSunday());
        values.put(ALARM_DAY_OF_MONTH, alarm.getDayOfMonth());
        values.put(ALARM_EVERY_MONTH,alarm.getEveryMonth());
        values.put(ALARM_MONTH_WEEK_NUMBER,alarm.getMonthWeekName());
        values.put(ALARM_MONTH_DAY_NAME,alarm.getMonthDayName());
        values.put(ALARM_EVERY_YEAR , alarm.getAlarmEveryYear());
        values.put(ALARM_YEAR_MONTH_NAME, alarm.getAlarmYearMonthName());
        values.put(ALARM_YEAR_MONTH_DATE, alarm.getAlarmYearMonthDay());
        values.put(ALARM_YEAR_WEEK_NUM, alarm.getAlarmYearWeekName());
        values.put(ALARM_YEAR_DAY_NAME, alarm.getAlarmYearDayName());
        values.put(ALARM_IS_ACTIVE, alarm.getAlarmIsActive());
        values.put(ALARM_IS_REPEAT, alarm.getAlarmIsRepeat());
        values.put(ALARM_TOTAL_OCCURRENCES,alarm.getAlarmTotalOccurrences());


        // Inserting Row
        long ID = db.insert(TABLE_ALARM, null, values);
        db.close();
        return (int) ID;

    }

    public Alarm getAlarm(int id) {
        Alarm alarm = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM + " WHERE " + ALARM_ID + " = '" + id + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(ALARM_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(ALARM_DESCRIPTION));
                String mDate = cursor.getString(cursor.getColumnIndex(ALARM_DATE));
                String mTime = cursor.getString(cursor.getColumnIndex(ALARM_TIME));
                String mNoEndDate = cursor.getString(cursor.getColumnIndex(ALARM_OCCURRENCE_NO_END_DATE));
                int mEndAfterOccurrences = cursor.getInt(cursor.getColumnIndex(ALARM_OCCURRENCE_END_AFTER));
                String mEndBy = cursor.getString(cursor.getColumnIndex(ALARM_OCCURRENCE_END_BY));
                int mPatternDelay = cursor.getInt(cursor.getColumnIndex(ALARM_PATTERN_DURATION));
                int mEveryDay = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_DAY));
                int monToFri = cursor.getInt(cursor.getColumnIndex(ALARM_MON_FRI));
                int everyWeek = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_WEEK));
                int mMonday = cursor.getInt(cursor.getColumnIndex(ALARM_MON));
                int mTuesday = cursor.getInt(cursor.getColumnIndex(ALARM_TUE));
                int mWednesday = cursor.getInt(cursor.getColumnIndex(ALARM_WED));
                int mThursday = cursor.getInt(cursor.getColumnIndex(ALARM_THUR));
                int mFriday = cursor.getInt(cursor.getColumnIndex(ALARM_FRI));
                int mSaturday = cursor.getInt(cursor.getColumnIndex(ALARM_SAT));
                int mSunday = cursor.getInt(cursor.getColumnIndex(ALARM_SUN));
                int mDayOfMonth = cursor.getInt(cursor.getColumnIndex(ALARM_DAY_OF_MONTH));
                int mEveryMonth = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_MONTH));
                int mMonthWeekName = cursor.getInt(cursor.getColumnIndex(ALARM_MONTH_WEEK_NUMBER));
                int mMonthDayName = cursor.getInt(cursor.getColumnIndex(ALARM_MONTH_DAY_NAME));
                int mAlarmEveryYear = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_YEAR));
                int mAlarmYearMonthName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_MONTH_NAME));
                int mAlarmYearMonthDay = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_MONTH_DATE));
                int mAlarmYearWeekName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_WEEK_NUM));
                int mAlarmYearDayName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_DAY_NAME));
                int mAlarmIsActive = cursor.getInt(cursor.getColumnIndex(ALARM_IS_ACTIVE));
                int mAlarmIsRepeat = cursor.getInt(cursor.getColumnIndex(ALARM_IS_REPEAT));
                int mAlarmTotalOccurrences = cursor.getInt(cursor.getColumnIndex(ALARM_TOTAL_OCCURRENCES));
                alarm = new Alarm(title, content, mDate, mTime, mNoEndDate, mEndAfterOccurrences, mEndBy, mPatternDelay,
                        mEveryDay, monToFri, everyWeek, mMonday, mTuesday, mWednesday, mThursday, mFriday, mSaturday, mSunday,
                        mDayOfMonth, mEveryMonth, mMonthWeekName, mMonthDayName,
                        mAlarmEveryYear, mAlarmYearMonthName, mAlarmYearMonthDay, mAlarmYearWeekName, mAlarmYearDayName,mAlarmIsActive,mAlarmIsRepeat,mAlarmTotalOccurrences);

            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return alarm;
    }

    public ArrayList<Alarm> getAllAlarm(){
        ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
        Alarm alarm = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ALARM_ID));
                String title = cursor.getString(cursor.getColumnIndex(ALARM_TITLE));
                String content = cursor.getString(cursor.getColumnIndex(ALARM_DESCRIPTION));
                String mDate = cursor.getString(cursor.getColumnIndex(ALARM_DATE));
                String mTime = cursor.getString(cursor.getColumnIndex(ALARM_TIME));
                String mNoEndDate = cursor.getString(cursor.getColumnIndex(ALARM_OCCURRENCE_NO_END_DATE));
                int mEndAfterOccurrences = cursor.getInt(cursor.getColumnIndex(ALARM_OCCURRENCE_END_AFTER));
                String mEndBy = cursor.getString(cursor.getColumnIndex(ALARM_OCCURRENCE_END_BY));
                int mPatternDelay = cursor.getInt(cursor.getColumnIndex(ALARM_PATTERN_DURATION));
                int mEveryDay = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_DAY));
                int monToFri = cursor.getInt(cursor.getColumnIndex(ALARM_MON_FRI));
                int everyWeek = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_WEEK));
                int mMonday = cursor.getInt(cursor.getColumnIndex(ALARM_MON));
                int mTuesday = cursor.getInt(cursor.getColumnIndex(ALARM_TUE));
                int mWednesday = cursor.getInt(cursor.getColumnIndex(ALARM_WED));
                int mThursday = cursor.getInt(cursor.getColumnIndex(ALARM_THUR));
                int mFriday = cursor.getInt(cursor.getColumnIndex(ALARM_FRI));
                int mSaturday = cursor.getInt(cursor.getColumnIndex(ALARM_SAT));
                int mSunday = cursor.getInt(cursor.getColumnIndex(ALARM_SUN));
                int mDayOfMonth = cursor.getInt(cursor.getColumnIndex(ALARM_DAY_OF_MONTH));
                int mEveryMonth = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_MONTH));
                int mMonthWeekName = cursor.getInt(cursor.getColumnIndex(ALARM_MONTH_WEEK_NUMBER));
                int mMonthDayName = cursor.getInt(cursor.getColumnIndex(ALARM_MONTH_DAY_NAME));
                int mAlarmEveryYear = cursor.getInt(cursor.getColumnIndex(ALARM_EVERY_YEAR));
                int mAlarmYearMonthName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_MONTH_NAME));
                int mAlarmYearMonthDay = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_MONTH_DATE));
                int mAlarmYearWeekName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_WEEK_NUM));
                int mAlarmYearDayName = cursor.getInt(cursor.getColumnIndex(ALARM_YEAR_DAY_NAME));
                int mAlarmIsActive = cursor.getInt(cursor.getColumnIndex(ALARM_IS_ACTIVE));
                int mAlarmIsRepeat = cursor.getInt(cursor.getColumnIndex(ALARM_IS_REPEAT));
                int mAlarmTotalOccurrences = cursor.getInt(cursor.getColumnIndex(ALARM_TOTAL_OCCURRENCES));

                alarm = new Alarm(id,title,content,mDate,mTime,mNoEndDate,mEndAfterOccurrences,mEndBy,mPatternDelay,
                         mEveryDay,monToFri,everyWeek,mMonday,mTuesday,mWednesday,mThursday,mFriday,mSaturday,mSunday,
                         mDayOfMonth,mEveryMonth,mMonthWeekName,mMonthDayName,
                         mAlarmEveryYear,mAlarmYearMonthName,mAlarmYearMonthDay,mAlarmYearWeekName,mAlarmYearDayName,mAlarmIsActive,mAlarmIsRepeat,mAlarmTotalOccurrences);
                    alarmList.add(alarm);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return alarmList;
    }

    public void updateOccurrence (int alarmId, int occurrences){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALARM_OCCURRENCE_END_AFTER, occurrences);
        db.update(TABLE_ALARM, values, ALARM_ID + " = " + alarmId , null);
    }

    public void updateIsActive (int alarmId, int isActive){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ALARM_IS_ACTIVE, isActive);
        db.update(TABLE_ALARM, values, ALARM_ID + " = " + alarmId , null);
    }



    public int addSubIdAlarm(int alarm_id, int database_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ALARM_ID , alarm_id);
        values.put(DATABASE_ID , database_id);

        // Inserting Row
        long ID = db.insert(TABLE_ALARM_SUB_ID, null, values);
        db.close();
        return (int) ID;

    }

    public ArrayList<Integer> getSubIdAlarm(int database_id){
        ArrayList<Integer> alarmIdList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ALARM_SUB_ID + " WHERE "+ DATABASE_ID + " = '"+ database_id +"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int mAlarmId = cursor.getInt(cursor.getColumnIndex(ALARM_ID));
                alarmIdList.add(mAlarmId);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return alarmIdList;

    }


    public void deleteAlarm(int alarmId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALARM, ALARM_ID + " = " + alarmId , null);
        db.close();
    }

}
