package dev.faran.myalarm;

import java.io.Serializable;

/**
 * Created by fjaved on 6/21/2018.
 */

public class Alarm implements Serializable {

    private int mID;
    private int mUserId;
    private String mTitle = "";
    private String mContent = "";
    private String mDate = "";
    private String mTime = "";
    private String mNoEndDate;
    private int mEndAfterOccurrences;
    private String mEndByDate= "";
    private int mPatternDuration;
    private int mEveryDay;
    private int mMondayToFriday;
    private int mEveryWeek;
    private int mMonDay;
    private int mTuesday;
    private int mWednesday;
    private int mThursday;
    private int mFriday;
    private int mSaturday;
    private int mSunday;
    private int mDayOfMonth;
    private int mEveryMonth;
    private int mMonthWeekName;
    private int mMonthDayName;
    private int mAlarmEveryYear;
    private int mAlarmYearMonthName;
    private int mAlarmYearMonthDay;
    private int mAlarmYearWeekName;
    private int mAlarmYearDayName;
    private int mAlarmIsActive;
    private int mAlarmIsRepeat;
    private int mAlarmTotalOccurrences;



    public Alarm(int mID, String mTitle, String mContent, String mDate, String mTime, String mNoEndDate, int mEndAfterOccurrences, String mEndByDate, int mPatternDuration, int mEveryDay, int mMondayToFriday, int mEveryWeek,
                 int mMonDay, int mTuesday, int mWednesday, int mThursday, int mFriday, int mSaturday, int mSunday,
                 int mDayOfMonth, int mEveryMonth, int mMonthWeekName, int mMonthDayName,
                 int mAlarmEveryYear, int mAlarmYearMonthName, int mAlarmYearMonthDay, int mAlarmYearWeekName, int mAlarmYearDayName, int mAlarmIsActive, int mAlarmIsRepeat, int mAlarmTotalOccurrences) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mNoEndDate = mNoEndDate;
        this.mEndAfterOccurrences = mEndAfterOccurrences;
        this.mEndByDate = mEndByDate;
        this.mPatternDuration = mPatternDuration;
        this.mEveryDay = mEveryDay;
        this.mMondayToFriday = mMondayToFriday;
        this.mEveryWeek = mEveryWeek;
        this.mMonDay = mMonDay;
        this.mTuesday = mTuesday;
        this.mWednesday = mWednesday;
        this.mThursday = mThursday;
        this.mFriday = mFriday;
        this.mSaturday = mSaturday;
        this.mSunday = mSunday;
        this.mDayOfMonth = mDayOfMonth;
        this.mEveryMonth = mEveryMonth;
        this.mMonthWeekName = mMonthWeekName;
        this.mMonthDayName = mMonthDayName;
        this.mAlarmEveryYear = mAlarmEveryYear;
        this.mAlarmYearMonthName = mAlarmYearMonthName;
        this.mAlarmYearMonthDay = mAlarmYearMonthDay;
        this.mAlarmYearWeekName = mAlarmYearWeekName;
        this.mAlarmYearDayName = mAlarmYearDayName;
        this.mAlarmIsActive = mAlarmIsActive;
        this.mAlarmIsRepeat = mAlarmIsRepeat;
        this.mAlarmTotalOccurrences = mAlarmTotalOccurrences;
    }

    public Alarm(String mTitle, String mContent, String mDate, String mTime, String mNoEndDate, int mEndAfterOccurrences, String mEndByDate, int mPatternDuration, int mEveryDay, int mMondayToFriday, int mEveryWeek,
                 int mMonDay, int mTuesday, int mWednesday, int mThursday, int mFriday, int mSaturday, int mSunday,
                 int mDayOfMonth, int mEveryMonth, int mMonthWeekName, int mMonthDayName,
                 int mAlarmEveryYear, int mAlarmYearMonthName, int mAlarmYearMonthDay, int mAlarmYearWeekName, int mAlarmYearDayName, int mAlarmIsActive, int mAlarmIsRepeat, int mAlarmTotalOccurrences) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mNoEndDate = mNoEndDate;
        this.mEndAfterOccurrences = mEndAfterOccurrences;
        this.mEndByDate = mEndByDate;
        this.mPatternDuration = mPatternDuration;
        this.mEveryDay = mEveryDay;
        this.mMondayToFriday = mMondayToFriday;
        this.mEveryWeek = mEveryWeek;
        this.mMonDay = mMonDay;
        this.mTuesday = mTuesday;
        this.mWednesday = mWednesday;
        this.mThursday = mThursday;
        this.mFriday = mFriday;
        this.mSaturday = mSaturday;
        this.mSunday = mSunday;
        this.mDayOfMonth = mDayOfMonth;
        this.mEveryMonth = mEveryMonth;
        this.mMonthWeekName = mMonthWeekName;
        this.mMonthDayName = mMonthDayName;
        this.mAlarmEveryYear = mAlarmEveryYear;
        this.mAlarmYearMonthName = mAlarmYearMonthName;
        this.mAlarmYearMonthDay = mAlarmYearMonthDay;
        this.mAlarmYearWeekName = mAlarmYearWeekName;
        this.mAlarmYearDayName = mAlarmYearDayName;
        this.mAlarmIsActive = mAlarmIsActive;
        this.mAlarmIsRepeat = mAlarmIsRepeat;
        this.mAlarmTotalOccurrences = mAlarmTotalOccurrences;
    }


    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public int getUserId() {
        return mUserId;
    }
    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public String getNoEndDate() {
        return mNoEndDate;
    }

    public void setNoEndDate(String mNoEndDate) {
        this.mNoEndDate = mNoEndDate;
    }

    public int getEndAfterOccurrences() {
        return mEndAfterOccurrences;
    }

    public void setEndAfterOccurrences(int mEndAfterOccurrences) {
        this.mEndAfterOccurrences = mEndAfterOccurrences;
    }

    public String getEndByDate() {
        return mEndByDate;
    }

    public void setEndByDate(String mEndByDate) {
        this.mEndByDate = mEndByDate;
    }

    public int getPatternDuration() {
        return mPatternDuration;
    }

    public void setPatternDuration(int mPatternDuration) {
        this.mPatternDuration = mPatternDuration;
    }

    public int getEveryDay() {
        return mEveryDay;
    }

    public void setEveryDay(int mEveryDay) {
        this.mEveryDay = mEveryDay;
    }

    public int getMondayToFriday() {
        return mMondayToFriday;
    }

    public void setMondayToFriday(int mMondayToFriday) {
        this.mMondayToFriday = mMondayToFriday;
    }

    public int getEveryWeek() {
        return mEveryWeek;
    }

    public void setEveryWeek(int mEveryWeek) {
        this.mEveryWeek = mEveryWeek;
    }

    public int getMonDay() {
        return mMonDay;
    }

    public void setMonDay(int mMonDay) {
        this.mMonDay = mMonDay;
    }

    public int getTuesday() {
        return mTuesday;
    }

    public void setTuesday(int mTuesday) {
        this.mTuesday = mTuesday;
    }

    public int getWednesday() {
        return mWednesday;
    }

    public void setWednesday(int mWednesday) {
        this.mWednesday = mWednesday;
    }

    public int getThursday() {
        return mThursday;
    }

    public void setThursday(int mThursday) {
        this.mThursday = mThursday;
    }

    public int getFriday() {
        return mFriday;
    }

    public void setFriday(int mFriday) {
        this.mFriday = mFriday;
    }

    public int getSaturday() {
        return mSaturday;
    }

    public void setSaturday(int mSaturday) {
        this.mSaturday = mSaturday;
    }

    public int getSunday() {
        return mSunday;
    }

    public void setSunday(int mSunday) {
        this.mSunday = mSunday;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int mDayOfMonth) {
        this.mDayOfMonth = mDayOfMonth;
    }

    public int getEveryMonth() {
        return mEveryMonth;
    }

    public void setEveryMonth(int mEveryMonth) {
        this.mEveryMonth = mEveryMonth;
    }

    public int getMonthWeekName() {
        return mMonthWeekName;
    }

    public void setMonthWeekName(int mMonthWeekName) {
        this.mMonthWeekName = mMonthWeekName;
    }

    public int getMonthDayName() {
        return mMonthDayName;
    }

    public void setMonthDayName(int mMonthDayName) {
        this.mMonthDayName = mMonthDayName;
    }

    public int getAlarmEveryYear() {
        return mAlarmEveryYear;
    }

    public void setAlarmEveryYear(int mAlarmEveryYear) {
        this.mAlarmEveryYear = mAlarmEveryYear;
    }

    public int getAlarmYearMonthName() {
        return mAlarmYearMonthName;
    }

    public void setAlarmYearMonthName(int mAlarmYearMonthName) {
        this.mAlarmYearMonthName = mAlarmYearMonthName;
    }

    public int getAlarmYearMonthDay() {
        return mAlarmYearMonthDay;
    }

    public void setAlarmYearMonthDay(int mAlarmYearMonthDay) {
        this.mAlarmYearMonthDay = mAlarmYearMonthDay;
    }

    public int getAlarmYearWeekName() {
        return mAlarmYearWeekName;
    }

    public void setAlarmYearWeekName(int mAlarmYearWeekName) {
        this.mAlarmYearWeekName = mAlarmYearWeekName;
    }

    public int getAlarmYearDayName() {
        return mAlarmYearDayName;
    }

    public void setAlarmYearDayName(int mAlarmYearDayName) {
        this.mAlarmYearDayName = mAlarmYearDayName;
    }

    public int getAlarmIsActive() {
        return mAlarmIsActive;
    }

    public void setAlarmIsActive(int mAlarmIsActive) {
        this.mAlarmIsActive = mAlarmIsActive;
    }

    public int getAlarmIsRepeat() {
        return mAlarmIsRepeat;
    }

    public void setAlarmIsRepeat(int mAlarmIsRepeat) {
        this.mAlarmIsRepeat = mAlarmIsRepeat;
    }

    public int getAlarmTotalOccurrences() {
        return mAlarmTotalOccurrences;
    }

    public void setAlarmTotalOccurrences(int mAlarmTotalOccurrences) {
        this.mAlarmTotalOccurrences = mAlarmTotalOccurrences;
    }
}
