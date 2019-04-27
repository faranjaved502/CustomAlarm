package dev.faran.myalarm;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dev.faran.myalarm.R;

public class NotificationAddActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, RadioButton.OnCheckedChangeListener, View.OnClickListener,
    DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener,AdapterView.OnItemSelectedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner mSpinnerMonthWeekName,mSpinnerMonthDayName,mSpinnerMonth,mSpinnerMonth2,mSpinnerYearWeek,mSpinnerYearDay;
    private List<String> mWeekList;
    private List<String> mDayList,mMonthList;
    GeneralAdapter generalAdapter;
    LinearLayout mLayoutDaily,mLayoutWeekly,mLayoutMonthly,mLayoutYearly,mLayoutPatternAndRange;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioBtnDaily;
    private RadioButton mRadioBtnWeekly;
    private RadioButton mRadioBtnMonthly;
    private RadioButton mRadioBtnYearly;
    private RadioButton mRadioBtnEveryDay,mRadioBtnEveryWeekDay,mRadioBtnNoEndDate,mRadioBtnEndAfter,mRadioBtnEndBy;
    private RadioButton mRadioBtnMonthDay,mRadioBtnMonthOptions,mRadioBtnYearOn,mRadioBtnYearOptions;
    private CheckBox mCheckBoxRepeat;
    ResizeAnimation ra;
    private TextView mTextViewDate,mTextViewTime,mTextViewEndBy;
    private Button mSaveBtn;
    DatabaseHandler databaseHandler;
    String time = "";
    private String mDate,mStartDate = "",mEndDate = "";
    String hour = "";
    String min = "";
    private int mYear, mMonth, mHour, mMinute, mDay;
    private EditText mEditTextEveryDay,mEditTextOccurrences,mEditTextTitle,mEditTextContent,mEditTextRecurWeek,mEditTextMonthOfDay,mEditTextEveryMonths,mEditTextEveryMonth2,mEditTextRecurYear;
    private EditText mEditTextYearMonthDay;
    private static final long milDay = 86400000L;
    private static final long milMinute = 60000L;
    private static final long milMonth = 2592000000L;
    public static final int DAILY = 1;
    public static final int WEEKLY = 2;
    public static final int MONTHLY = 3;
    public static final int YEARLY = 4;
    public static final int ONCE = 5;
    private int EVERYDAY = 2 ,MONtoFRI = 1;
    public int MON = 2;
    public int TUES = 3;
    public int WED = 4;
    public int THURS = 5;
    public int FRI = 6;
    public int SAT = 7;
    public int SUN = 1;
    CheckBox mCheckBoxMon,mCheckBoxTue,mCheckBoxWed,mCheckBoxThurs,mCheckBoxFri,mCheckBoxSat,mCheckBoxSun;
    private Calendar mCalendar;
    private long mRepeatTime;
    private  Calendar mWeeklyCalender;
    private int mWeekNumber;
    private int mDayNumber;
    private int mMonthNumber;
    private int mYearWeekNumber;
    private int mYearDayNumber;
    private int mMonthNumber2;
    private Boolean mIsRepeat = false;
    private int mIsActive = 1;
    int isRepeat = 0;
    Alarm alarm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_add);
        init();
        generalAdapter = new GeneralAdapter(this, android.R.layout.simple_spinner_dropdown_item, mWeekList, "First", -1, -1, 11);
       /* mSpinnerWeek.setAdapter(generalAdapter);*/

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, mWeekList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMonthWeekName.setAdapter(dataAdapter);
        mSpinnerYearWeek.setAdapter(dataAdapter);

        ArrayAdapter<String> dataAdapterDay = new ArrayAdapter<String>(this,
                R.layout.spinner_item, mDayList);
        dataAdapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMonthDayName.setAdapter(dataAdapterDay);
        mSpinnerYearDay.setAdapter(dataAdapterDay);



        ArrayAdapter<String> dataAdapterMonth = new ArrayAdapter<String>(this,
                R.layout.spinner_item, mMonthList);
        dataAdapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMonth.setAdapter(dataAdapterMonth);
        mSpinnerMonth2.setAdapter(dataAdapterMonth);

        mCheckBoxRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxRepeat.isChecked()){
                    mIsRepeat = true;
                    onClickSearch(true);
                }else {
                    mIsRepeat = false;
                    onClickSearch(false);
                }
            }
        });


        mCheckBoxMon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxMon.isChecked()){
                    MON = 2;
                }else {
                    MON = 0;
                }
            }
        });
        mCheckBoxTue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxTue.isChecked()){
                    TUES = 3;
                }else {
                    TUES = 0;
                }
            }
        });
        mCheckBoxWed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxWed.isChecked()){
                    WED = 4;
                }else {
                    WED = 0;
                }
            }
        });
        mCheckBoxThurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxThurs.isChecked()){
                    THURS = 5;
                }else {
                    THURS = 0;
                }
            }
        });
        mCheckBoxFri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxFri.isChecked()){
                    FRI = 6;
                }else {
                    FRI = 0;
                }
            }
        });
        mCheckBoxSat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxSat.isChecked()){
                    SAT = 7;
                }else {
                    SAT = 0;
                }
            }
        });
        mCheckBoxSun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckBoxSun.isChecked()){
                    SUN = 1;
                }else {
                    SUN = 0;
                }
            }
        });
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioBtnEveryWeekDay.setOnCheckedChangeListener(this);
        mRadioBtnEveryDay.setOnCheckedChangeListener(this);
        mRadioBtnNoEndDate.setOnCheckedChangeListener(this);
        mRadioBtnEndAfter.setOnCheckedChangeListener(this);
        mRadioBtnEndBy.setOnCheckedChangeListener(this);
        mRadioBtnMonthDay.setOnCheckedChangeListener(this);
        mRadioBtnMonthOptions.setOnCheckedChangeListener(this);
        mRadioBtnYearOn.setOnCheckedChangeListener(this);
        mRadioBtnYearOptions.setOnCheckedChangeListener(this);
        mTextViewDate.setOnClickListener(this);
        mTextViewTime.setOnClickListener(this);
        mTextViewEndBy.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mSpinnerMonthDayName.setOnItemSelectedListener(this);
        mSpinnerMonthWeekName.setOnItemSelectedListener(this);
        mSpinnerMonth.setOnItemSelectedListener(this);
        mSpinnerYearDay.setOnItemSelectedListener(this);
        mSpinnerYearWeek.setOnItemSelectedListener(this);
        mSpinnerMonth2.setOnItemSelectedListener(this);

    }

    public void init(){
        mWeeklyCalender = Calendar.getInstance();
        mCalendar = Calendar.getInstance();
        databaseHandler = new DatabaseHandler(this);
        mWeekList = Arrays.asList(this.getResources().getStringArray(R.array.week));
        mDayList = Arrays.asList(this.getResources().getStringArray(R.array.day));
        mMonthList = Arrays.asList(this.getResources().getStringArray(R.array.month));
        mSpinnerMonthWeekName = findViewById(R.id.spinner_week_name);
        mSpinnerMonthDayName = findViewById(R.id.spinner_day_name);
        mSpinnerMonth = findViewById(R.id.spinner_month);
        mSpinnerMonth2 = findViewById(R.id.spinner_month2);
        mSpinnerYearWeek = findViewById(R.id.spinner_year_week);
        mSpinnerYearDay = findViewById(R.id.spinner_year_day);
        mRadioGroup = findViewById(R.id.radio_group_duration);
        mRadioBtnDaily = findViewById(R.id.radio_btn_daily);
        mRadioBtnWeekly = findViewById(R.id.radio_btn_weekly);
        mRadioBtnMonthly = findViewById(R.id.radio_btn_monthly);
        mRadioBtnYearly = findViewById(R.id.radio_btn_yealy);
        mLayoutDaily = findViewById(R.id.layout_daily_view);
        mLayoutWeekly = findViewById(R.id.layout_weekly_view);
        mLayoutMonthly = findViewById(R.id.layout_monthly_view);
        mLayoutYearly = findViewById(R.id.layout_yearly_view);
        mRadioBtnEveryDay = findViewById(R.id.radio_btn_every_day);
        mRadioBtnEveryWeekDay = findViewById(R.id.radio_btn_every_weekday);
        mRadioBtnNoEndDate = findViewById(R.id.radio_btn_no_end_date);
        mRadioBtnEndAfter = findViewById(R.id.radio_btn_end_after);
        mRadioBtnEndBy = findViewById(R.id.radio_btn_end_by);
        mRadioBtnMonthDay = findViewById(R.id.radio_btn_day);
        mRadioBtnMonthOptions = findViewById(R.id.radio_btn_the);
        mRadioBtnYearOn = findViewById(R.id.radio_btn_on);
        mRadioBtnYearOptions = findViewById(R.id.radio_btn_year_the);
        mLayoutPatternAndRange = findViewById(R.id.layout_pattern_and_range);
        mCheckBoxRepeat= findViewById(R.id.checkbox_repeat);
        mTextViewDate = findViewById(R.id.text_view_date);
        mTextViewTime = findViewById(R.id.text_view_time);
        mSaveBtn = findViewById(R.id.save_btn);
        mEditTextEveryDay = findViewById(R.id.edit_txt_recur_daily);
        mEditTextOccurrences = findViewById(R.id.edit_txt_occurrences);
        mEditTextTitle= findViewById(R.id.edit_txt_noti_title);
        mEditTextContent = findViewById(R.id.edit_txt_noti_content);
        mTextViewEndBy = findViewById(R.id.txt_date);
        mEditTextRecurWeek = findViewById(R.id.edit_txt_recur_week);
        mCheckBoxMon = findViewById(R.id.checkbox_mon);
        mCheckBoxTue = findViewById(R.id.checkbox_tue);
        mCheckBoxWed = findViewById(R.id.checkbox_wed);
        mCheckBoxThurs = findViewById(R.id.checkbox_thurs);
        mCheckBoxFri = findViewById(R.id.checkbox_fri);
        mCheckBoxSat = findViewById(R.id.checkbox_sat);
        mCheckBoxSun = findViewById(R.id.checkbox_sun);
        mEditTextMonthOfDay = findViewById(R.id.edit_txt_day);
        mEditTextEveryMonths = findViewById(R.id.edit_txt_month);
        mEditTextEveryMonth2 = findViewById(R.id.edit_txt_month2);
        mEditTextRecurYear = findViewById(R.id.edit_txt_recur_year);
        mEditTextYearMonthDay = findViewById(R.id.edit_txt_year_day);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (i == mRadioBtnDaily.getId()){
            mLayoutDaily.setVisibility(View.VISIBLE);
            mLayoutWeekly.setVisibility(View.GONE);
            mLayoutMonthly.setVisibility(View.GONE);
            mLayoutYearly.setVisibility(View.GONE);
        }else if (i == mRadioBtnWeekly.getId()){
            mLayoutDaily.setVisibility(View.GONE);
            mLayoutWeekly.setVisibility(View.VISIBLE);
            mLayoutMonthly.setVisibility(View.GONE);
            mLayoutYearly.setVisibility(View.GONE);
        }else if (i == mRadioBtnMonthly.getId()){
            mLayoutDaily.setVisibility(View.GONE);
            mLayoutWeekly.setVisibility(View.GONE);
            mLayoutMonthly.setVisibility(View.VISIBLE);
            mLayoutYearly.setVisibility(View.GONE);
        }else if (i == mRadioBtnYearly.getId()){
            mLayoutDaily.setVisibility(View.GONE);
            mLayoutWeekly.setVisibility(View.GONE);
            mLayoutMonthly.setVisibility(View.GONE);
            mLayoutYearly.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

        if (isChecked){
            if (compoundButton.getId() == R.id.radio_btn_every_day){
                mRadioBtnEveryWeekDay.setChecked(false);
            }if (compoundButton.getId() == R.id.radio_btn_every_weekday){
                mRadioBtnEveryDay.setChecked(false);
            }
            if (compoundButton.getId() == R.id.radio_btn_no_end_date){
                mRadioBtnEndBy.setChecked(false);
                mRadioBtnEndAfter.setChecked(false);
            }if (compoundButton.getId() == R.id.radio_btn_end_after){
                mRadioBtnEndBy.setChecked(false);
                mRadioBtnNoEndDate.setChecked(false);
            }if (compoundButton.getId() == R.id.radio_btn_end_by){
                mRadioBtnNoEndDate.setChecked(false);
                mRadioBtnEndAfter.setChecked(false);
            }
            if (compoundButton.getId() == R.id.radio_btn_day){
                mRadioBtnMonthOptions.setChecked(false);
            }if (compoundButton.getId() == R.id.radio_btn_the){
                mRadioBtnMonthDay.setChecked(false);
            }
            if (compoundButton.getId() == R.id.radio_btn_on){
                mRadioBtnYearOptions.setChecked(false);
            }if (compoundButton.getId() == R.id.radio_btn_year_the){
                mRadioBtnYearOn.setChecked(false);
            }
        }

    }


    public void onClickSearch(Boolean mIsSearchBarVisible){
        if (mIsSearchBarVisible) {
            ra = new ResizeAnimation(mLayoutPatternAndRange, getResources().getDimensionPixelSize(R.dimen.noti_repeat_400), 0);
        } else {
            ra = new ResizeAnimation(mLayoutPatternAndRange, 0, getResources().getDimensionPixelSize(R.dimen.noti_repeat_400));
        }
        ra.setDuration(400);
        mLayoutPatternAndRange.startAnimation(ra);
        mLayoutPatternAndRange.setVisibility(View.VISIBLE);
        mLayoutPatternAndRange.requestLayout();
    }

    @Override
    public void onClick(View view) {
        if (view == mTextViewDate){
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(getResources().getColor(R.color.theme_blue));
            dpd.show(this.getFragmentManager(),"startDate");
        }else if (view == mTextViewTime){
            Calendar now = Calendar.getInstance();
            TimePickerDialog dpd = TimePickerDialog.newInstance(
                    this,
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true);
            dpd.setAccentColor(getResources().getColor(R.color.theme_blue));
            dpd.show(this.getFragmentManager(), "Datepickerdialog");
        }else if (view == mTextViewEndBy){
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(getResources().getColor(R.color.theme_blue));
            dpd.show(this.getFragmentManager(),"endDate");
        }else if (view == mSaveBtn){
            if (!mEndDate.isEmpty() && !mStartDate.isEmpty()) {
                if (getDate(mEndDate).before(getDate(mStartDate))) {
                    Toast.makeText(this, "End date cannot be less then start date", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (mEditTextTitle.getText().toString().replace(" ","").isEmpty()){
                Toast.makeText(this, "Please enter the notification title", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mEditTextContent.getText().toString().replace(" ","").isEmpty()){
                Toast.makeText(this, "Please enter the notification content", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mTextViewDate.getText().toString().isEmpty()){
                Toast.makeText(this, "Please select start date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mTextViewTime.getText().toString().isEmpty()){
                Toast.makeText(this, "Please select notification trigger time", Toast.LENGTH_SHORT).show();
                return;
            }
            submitAlarm();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        mDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
        if (view.getTag().equalsIgnoreCase("startDate" )){
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                mDay = dayOfMonth;
                mMonth = monthOfYear;
                mYear = year;
                Date date = format.parse(mDate);
                if (DateUtils.isToday(date.getTime())) {
                    mTextViewDate.setText(mDate);
                    mWeeklyCalender.setTime(date);
                    mStartDate = mDate;
                }else if (date.after(new Date())){
                    mTextViewDate.setText(mDate);
                    mWeeklyCalender.setTime(date);
                    mStartDate = mDate;
                } else {
                    Toast.makeText(this, "Please enter valid start date", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (view.getTag().equalsIgnoreCase("endDate")){

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = format.parse(mDate);
                if (DateUtils.isToday(date.getTime())) {
                    mTextViewEndBy.setText(mDate);
                    mEndDate = mDate;

                }else if (date.after(new Date())){
                    mTextViewEndBy.setText(mDate);
                    mEndDate = mDate;

                } else {
                    Toast.makeText(this, "Please enter valid end date", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        time = getTime(hourOfDay, minute);
        mTextViewTime.setText(time);
        mHour = hourOfDay;
        mMinute = minute;
        hour = "" + hourOfDay;
        min = "" + minute;
    }

    private String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(tme);
    }

    public void submitAlarm() {

        if (mIsRepeat) {
            isRepeat = 1;
            if (mRadioBtnDaily.isChecked()) {
                setDailyAlarm();
            } else if (mRadioBtnWeekly.isChecked()) {
                setWeeklyAlarm(alarm,this);
            } else if (mRadioBtnMonthly.isChecked()) {
                setMonthlyAlarm();
            } else if (mRadioBtnYearly.isChecked()) {
                setYearlyAlarm();
            }
        }else {
            isRepeat = 0;
            setAlarmOnce(-1,mStartDate, time,this);
            Toast.makeText(this, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
            finish();

        }
    }


    public void setAlarmOnce(int alarmId , String startDate, String time,Context context){
        int id;
        if (alarmId == -1){
            id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), startDate, time, "",
                    -1, "", ONCE, 0, 0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,-1,0,0,0,mIsActive,isRepeat,-1));
            Log.d("Insert Id", "" + id);
            mCalendar = getCalender();
        }else {
            mCalendar = Calendar.getInstance();
            id = alarmId;
            int date[] = UtilMethods.getSplitDate(startDate);
            int month = date[0];
            int sTime[] = UtilMethods.getSplitTime(time);
            mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
        }

        Calendar c = Calendar.getInstance();
        Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
        Log.d("Current Time ", "" + c.getTimeInMillis());
        new NotificationScheduler().setMonthlyAlarm(context, mCalendar, id);
    }
    public void setDailyAlarm() {
        if (mRadioBtnEveryDay.isChecked()) {
            everyDayAlarm(alarm,this);
        } else if (mRadioBtnEveryWeekDay.isChecked()) {
            monToFriAlarm(alarm,this);

        }
    }
    public void setWeeklyAlarm(Alarm weeklyAlarm,Context context) {
        if (weeklyAlarm == null) {
            if (Integer.valueOf(mEditTextRecurWeek.getText().toString()) > 0) {
                if (MON !=0 ||TUES!=0 ||WED !=0 || THURS !=0 || FRI !=0 || SAT !=0 ||SUN != 0) {
                    if (mRadioBtnNoEndDate.isChecked()) {
                        Alarm alarm = new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                                -1, "", WEEKLY, 0, 0, Integer.valueOf(mEditTextRecurWeek.getText().toString()), MON, TUES, WED, THURS, FRI, SAT, SUN, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1);
                        //This is used to get checked days sorted  array.
                        ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(alarm, mStartDate);
                        int id = databaseHandler.addAlarm(alarm);
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        //This is used to calculate first alarm trigger time using sorted array list
                        mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                        mWeeklyCalender.set(Calendar.HOUR, mHour);
                        mWeeklyCalender.set(Calendar.MINUTE, mMinute);
                        mWeeklyCalender.set(Calendar.SECOND, 0);
                        mRepeatTime = Integer.parseInt(mEditTextRecurWeek.getText().toString()) * milDay * 7;
                        for (int i = 0; i < getDaysArray.size(); i++) {
                            if (i == 0) {
                                mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                Log.d("Day of Week", "" + getDaysArray.get(i));
                                Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                Log.d("Current Time ", "" + c.getTimeInMillis());
                                //calculate alarm sub id's
                                int alarmId = weeklyAlarmId(id, getDaysArray.get(i));
                                new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, alarmId, mRepeatTime, mWeeklyCalender);
                                //add alarm sub id into database
                                databaseHandler.addSubIdAlarm(alarmId, id);
                            } else {
                                int days = Math.abs(getDaysArray.get(i) - getDaysArray.get(i - 1));
//                            mCalendar = getCalenderForWeek(days);
                                mWeeklyCalender.add(Calendar.DATE, 1);
                                //This is used to calculate others alarm trigger time except first using sorted array list
                                mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                                mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                Log.d("Day of Week", "" + getDaysArray.get(i));
                                Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                Log.d("Current Time ", "" + c.getTimeInMillis());
                                new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, weeklyAlarmId(id, getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);

                                //add alarm sub id into database
                                databaseHandler.addSubIdAlarm(weeklyAlarmId(id, getDaysArray.get(i)), id);
                            }

                        }
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (mRadioBtnEndAfter.isChecked()) {
                        if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0) {
                            Alarm alarm = (new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    Integer.valueOf(mEditTextOccurrences.getText().toString()), "", WEEKLY, 0, 0, Integer.valueOf(mEditTextRecurWeek.getText().toString()), MON, TUES, WED, THURS, FRI, SAT, SUN, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,Integer.valueOf(mEditTextOccurrences.getText().toString())));
                            //This is used to get checked days sorted  array.
                            ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(alarm, mStartDate);

                          /*  int totalOccurrences = Integer.valueOf(mEditTextOccurrences.getText().toString()) * getDaysArray.size();
                            alarm.setEndAfterOccurrences(totalOccurrences);*/
                            int id = databaseHandler.addAlarm(alarm);
                            Log.d("Insert Id", "" + id);
                            // Set up calender for creating the notification

                            Calendar c = Calendar.getInstance();

                            //This is used to calculate first alarm trigger time using sorted array list
                            mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                            mWeeklyCalender.set(Calendar.HOUR, mHour);
                            mWeeklyCalender.set(Calendar.MINUTE, mMinute);
                            mWeeklyCalender.set(Calendar.SECOND, 0);
                            mRepeatTime = Integer.parseInt(mEditTextRecurWeek.getText().toString()) * milDay * 7;
                            for (int i = 0; i < getDaysArray.size(); i++) {
                                if (i == 0) {
                                    mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                    Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                    Log.d("Day of Week", "" + getDaysArray.get(i));
                                    Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                    Log.d("Current Time ", "" + c.getTimeInMillis());
                                    //calculate alarm sub id's
                                    int alarmId = weeklyAlarmId(id, getDaysArray.get(i));
                                    new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, alarmId, mRepeatTime, mWeeklyCalender);
                                    //add alarm sub id into database
                                    databaseHandler.addSubIdAlarm(alarmId, id);
                                } else {
                                    int days = Math.abs(getDaysArray.get(i) - getDaysArray.get(i - 1));
//                                   mCalendar = getCalenderForWeek(days);
                                    mWeeklyCalender.add(Calendar.DATE, 1);
                                    //This is used to calculate others alarm trigger time except first using sorted array list
                                    mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                                    mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                    Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                    Log.d("Day of Week", "" + getDaysArray.get(i));
                                    Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                    Log.d("Current Time ", "" + c.getTimeInMillis());
                                    new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, weeklyAlarmId(id, getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);

                                    //add alarm sub id into database
                                    databaseHandler.addSubIdAlarm(weeklyAlarmId(id, getDaysArray.get(i)), id);
                                }
                            }
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mRadioBtnEndBy.isChecked()) {
                        if (!mEndDate.isEmpty()) {

                            String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                            Alarm alarm = (new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    -1, finalEndDate, WEEKLY, 0, 0, Integer.valueOf(mEditTextRecurWeek.getText().toString()), MON, TUES, WED, THURS, FRI, SAT, SUN, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));
                            ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(alarm, mStartDate);
                            int id = databaseHandler.addAlarm(alarm);
                            Log.d("Insert Id", "" + id);
                            Calendar c = Calendar.getInstance();
                            mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);
                            mWeeklyCalender.set(Calendar.HOUR, mHour);
                            mWeeklyCalender.set(Calendar.MINUTE, mMinute);
                            mWeeklyCalender.set(Calendar.SECOND, 0);

                            mRepeatTime = Integer.parseInt(mEditTextRecurWeek.getText().toString()) * milDay * 7;
                            for (int i = 0; i < getDaysArray.size(); i++) {
                                if (i == 0) {
                                    mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                    Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                    Log.d("Day of Week", "" + getDaysArray.get(i));
                                    Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                    Log.d("Current Time ", "" + c.getTimeInMillis());
                                    int alarmId = weeklyAlarmId(id, getDaysArray.get(i));


                            /* Check first trigger date is after endDate
                            * if yes then delete alarm from database
                            * break loop
                            */
                                    if (mWeeklyCalender.after(getDate(mEndDate))) {
                                        databaseHandler.deleteAlarm(id);
                                        break;
                                    }
                                    new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, alarmId, mRepeatTime, mWeeklyCalender);
                                    databaseHandler.addSubIdAlarm(alarmId, id);
                                } else {
                                    int days = Math.abs(getDaysArray.get(i) - getDaysArray.get(i - 1));
//                            mCalendar = getCalenderForWeek(days);
                                    mWeeklyCalender.add(Calendar.DATE, 1);
                                    mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);
                                    mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                                    Log.d("weekly Id", "" + weeklyAlarmId(id, getDaysArray.get(i)));
                                    Log.d("Day of Week", "" + getDaysArray.get(i));
                                    Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                                    Log.d("Current Time ", "" + c.getTimeInMillis());
                                    new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), this, weeklyAlarmId(id, getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);
                                    databaseHandler.addSubIdAlarm(weeklyAlarmId(id, getDaysArray.get(i)), id);
                                }
                            }
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();

                        }
                    }
                }else {
                    Toast.makeText(context, "Please select one of day to proceed", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "Please enter a valid recurrence value", Toast.LENGTH_SHORT).show();
            }
        }else {
             /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mWeeklyCalender = Calendar.getInstance();
            if (weeklyAlarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(weeklyAlarm.getDate());
                int sTime[] = UtilMethods.getSplitTime(weeklyAlarm.getTime());
                mWeeklyCalender.setTime(getDate(weeklyAlarm.getDate()));
                ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(weeklyAlarm, weeklyAlarm.getDate());

                Calendar c = Calendar.getInstance();
                //This is used to calculate first alarm trigger time using sorted array list
                mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                mWeeklyCalender.set(Calendar.HOUR, sTime[0]);
                mWeeklyCalender.set(Calendar.MINUTE, sTime[1]);
                mWeeklyCalender.set(Calendar.SECOND, 0);
                mRepeatTime = weeklyAlarm.getEveryWeek() * milDay * 7;
                for (int i = 0; i < getDaysArray.size(); i++) {
                    if (i == 0) {
                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        //calculate alarm sub id's
                        int alarmId = weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i));
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), context, alarmId, mRepeatTime, mWeeklyCalender);
                    } else {

                        mWeeklyCalender.add(Calendar.DATE, 1);
                        //This is used to calculate others alarm trigger time except first using sorted array list
                        mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);
                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), context, weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);
                    }
                }
            }else if (weeklyAlarm.getEndAfterOccurrences()>0){
                int date[] = UtilMethods.getSplitDate(weeklyAlarm.getDate());
                int sTime[] = UtilMethods.getSplitTime(weeklyAlarm.getTime());
                mWeeklyCalender.setTime(getDate(weeklyAlarm.getDate()));
                //This is used to get checked days sorted  array.
                ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(weeklyAlarm, weeklyAlarm.getDate());
                Calendar c = Calendar.getInstance();

                //This is used to calculate first alarm trigger time using sorted array list
                mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                mWeeklyCalender.set(Calendar.HOUR, sTime[0]);
                mWeeklyCalender.set(Calendar.MINUTE, sTime[1]);
                mWeeklyCalender.set(Calendar.SECOND, 0);
                mRepeatTime = weeklyAlarm.getEveryWeek() * milDay * 7;
                for (int i = 0; i < getDaysArray.size(); i++) {
                    if (i == 0) {
                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        //calculate alarm sub id's
                        int alarmId = weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i));
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i),context, alarmId, mRepeatTime, mWeeklyCalender);
                    } else {
                        int days = Math.abs(getDaysArray.get(i) - getDaysArray.get(i - 1));
//                            mCalendar = getCalenderForWeek(days);
                        mWeeklyCalender.add(Calendar.DATE, 1);
                        //This is used to calculate others alarm trigger time except first using sorted array list
                        mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);

                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), context, weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);
                    }
                }

            }else if (getDate(weeklyAlarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(weeklyAlarm.getDate());
                int sTime[] = UtilMethods.getSplitTime(weeklyAlarm.getTime());
                mWeeklyCalender.setTime(getDate(weeklyAlarm.getDate()));
                //This is used to get checked days sorted  array.
                ArrayList<Integer> getDaysArray = UtilMethods.getDaysArray(weeklyAlarm, weeklyAlarm.getDate());
                Calendar c = Calendar.getInstance();
                mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);
                mWeeklyCalender.set(Calendar.HOUR, sTime[0]);
                mWeeklyCalender.set(Calendar.MINUTE, sTime[1]);
                mWeeklyCalender.set(Calendar.SECOND, 0);

                mRepeatTime = weeklyAlarm.getEveryWeek() * milDay * 7;
                for (int i = 0; i < getDaysArray.size(); i++) {
                    if (i == 0) {
                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        int alarmId = weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i));


                            /* Check first trigger date is after endDate
                            * if yes then delete alarm from database
                            * break loop
                            */
                        if (mWeeklyCalender.after(getDate(mEndDate))) {
                            databaseHandler.deleteAlarm(weeklyAlarm.getID());
                            break;
                        }
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), context, alarmId, mRepeatTime, mWeeklyCalender);

                    } else {
                        int days = Math.abs(getDaysArray.get(i) - getDaysArray.get(i - 1));
//                            mCalendar = getCalenderForWeek(days);
                        mWeeklyCalender.add(Calendar.DATE, 1);
                        mWeeklyCalender = UtilMethods.getTriggerDate(mWeeklyCalender, getDaysArray);
                        mWeeklyCalender.set(Calendar.DAY_OF_WEEK, getDaysArray.get(i));
                        Log.d("weekly Id", "" + weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)));
                        Log.d("Day of Week", "" + getDaysArray.get(i));
                        Log.d("calenderTime", "" + mWeeklyCalender.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                        new NotificationScheduler().scheduleAlarm(getDaysArray.get(i), context, weeklyAlarmId(weeklyAlarm.getID(), getDaysArray.get(i)), mRepeatTime, mWeeklyCalender);
                    }
                }
            }
        }
    }
    public void setMonthlyAlarm(){
        if (mRadioBtnMonthDay.isChecked()){
            everyMonthAlarm(alarm,this);
        }else if (mRadioBtnMonthOptions.isChecked()){
            setMonthlyWithWeeklyAndDayAlarm(alarm,this);

        }
    }
    public void setYearlyAlarm(){
        if (mRadioBtnYearOn.isChecked()){
            setEveryYearAlarm(alarm,this);
        }else if (mRadioBtnYearOptions.isChecked()){
            setYearlyWithWeekAndDayAlarm(alarm,this);
        }
    }

    /*
    * Start Daily Alarm Code
    * */
    public void monToFriAlarm(Alarm alarm,Context context) {
        if (alarm == null) {
            if (mRadioBtnNoEndDate.isChecked()) {
                int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                        -1, "", DAILY, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));
                Log.d("Insert Id", "" + id);
                // Set up calender for creating the notification
                mCalendar = getCalender();

                mRepeatTime = milDay;
                new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);
                Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else if (mRadioBtnEndAfter.isChecked()) {
                if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0 ) {
                    int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                            Integer.valueOf(mEditTextOccurrences.getText().toString()), "", DAILY, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,Integer.valueOf(mEditTextOccurrences.getText().toString())));
                    Log.d("Insert Id", "" + id);
                    // Set up calender for creating the notification
                    mCalendar = getCalender();
                    mRepeatTime = milDay;
                    new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);
                    Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();

                }
            } else if (mRadioBtnEndBy.isChecked()) {
                if (!mEndDate.isEmpty()) {
                    String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                    int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                            -1, finalEndDate, DAILY, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));

                    // Set up calender for creating the notification
                    mCalendar = getCalender();
                    mRepeatTime = milDay;
                    new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);

                    Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                   finish();
                }else {
                    Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();

                }
            }
        } else {
            /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (alarm.getNoEndDate().equalsIgnoreCase("Yes")) {
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--, date[1], date[2], sTime[0], sTime[1]);
                mRepeatTime = milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            } else if (alarm.getEndAfterOccurrences() > 0) {
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--, date[1], date[2], sTime[0], sTime[1]);
                mRepeatTime = milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            } else if (getDate(alarm.getEndByDate()).after(new Date())) {
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--, date[1], date[2], sTime[0], sTime[1]);
                mRepeatTime = milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            }
        }
    }
    public void everyDayAlarm(Alarm alarm, Context context) {
        if (alarm == null){
            if (Integer.valueOf(mEditTextEveryDay.getText().toString()) > 0) {
                if (mRadioBtnNoEndDate.isChecked()) {
                    int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                            -1, "", DAILY, Integer.valueOf(mEditTextEveryDay.getText().toString()), 0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,-1,0,0,0,mIsActive,isRepeat,-1));
                    Log.d("Insert Id", "" + id);
                    // Set up calender for creating the notification
                    mCalendar = getCalender();

                    mRepeatTime = Integer.parseInt(mEditTextEveryDay.getText().toString()) * milDay;
                    new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);
                    Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (mRadioBtnEndAfter.isChecked()) {
                    if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0 ) {
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                Integer.valueOf(mEditTextOccurrences.getText().toString()), "", DAILY, Integer.valueOf(mEditTextEveryDay.getText().toString()), 0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,-1,0,0,0,mIsActive,isRepeat,Integer.valueOf(mEditTextOccurrences.getText().toString())));
                        Log.d("Insert Id", "" + id);
                        // Set up calender for creating the notification
                        mCalendar = getCalender();

                        mRepeatTime = Integer.parseInt(mEditTextEveryDay.getText().toString()) * milDay;
                        new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();
                    }
                } else if (mRadioBtnEndBy.isChecked()) {

                    if (!mEndDate.isEmpty()) {
                        String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                -1, finalEndDate, DAILY, Integer.valueOf(mEditTextEveryDay.getText().toString()), 0,0,0,0,0,0,0,0,0,-1,-1,0,0,0,-1,0,0,0,mIsActive,isRepeat,-1));

                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification
                        mCalendar = getCalender();
                        Log.d("calender", ""+mCalendar.getTimeInMillis());
                        Log.d("current", ""+c.getTimeInMillis());
                        mRepeatTime = Integer.parseInt(mEditTextEveryDay.getText().toString()) * milDay;
                        new NotificationScheduler().setRepeatAlarm(this, mCalendar, id, mRepeatTime);
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                       finish();
                    }else {
                        Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                Toast.makeText(context, "Please enter a valid everyday value", Toast.LENGTH_SHORT).show();
            }
        }else {

            /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (alarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                mRepeatTime = alarm.getEveryDay() * milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            }else if (alarm.getEndAfterOccurrences()>0){
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                mRepeatTime = alarm.getEveryDay() * milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            }else if (getDate(alarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(alarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(alarm.getTime());
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                mRepeatTime = alarm.getEveryDay() * milDay;
                new NotificationScheduler().setRepeatAlarm(context, mCalendar, alarm.getID(), mRepeatTime);
            }

        }

    }
    // End Daily alarm code

    /*
    * Start Monthly Alarm Code
    * */
    public void everyMonthAlarm(Alarm monthlyAlarm,Context context) {
        if (monthlyAlarm == null) {
            int mDayOfMonth = Integer.valueOf(mEditTextMonthOfDay.getText().toString());
            int mEveryMonth = Integer.valueOf(mEditTextEveryMonths.getText().toString());
            int occurrences = Integer.valueOf(mEditTextOccurrences.getText().toString());
            if (mDayOfMonth > 0 && mDayOfMonth <= 31) {
                if (mEveryMonth > 0 && mEveryMonth <= 12) {
                    if (mRadioBtnNoEndDate.isChecked()) {
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                                -1, "", MONTHLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mDayOfMonth, mEveryMonth, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();

                        // Set up calender for creating the notification
                        mCalendar = getCalender();

                    /*
                     * Calculate first alarm trigger time comparing to start date
                     * */
                        calculateMonthlyFirstTriggerTime(mDayOfMonth, mEveryMonth, c);

                    /*
                     * set 1st alarm that triggered first
                     * */
                        new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                        databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);

                    /*
                     * This loop is used to create 8 different alarm according to month interval
                     * */
                        for (int i = 1; i < 8; i++) {
                            mCalendar.add(Calendar.MONTH, mEveryMonth);
                            int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                            calculateAndSetMonthlyAlarm(mDayOfMonth, id, i, maximumDaysOfMonth);

                        }
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();

                    } else if (mRadioBtnEndAfter.isChecked()) {
                        if (occurrences > 0) {
                            int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    occurrences, "", MONTHLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mDayOfMonth, mEveryMonth, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,occurrences));
                            Log.d("Insert Id", "" + id);
                            Calendar c = Calendar.getInstance();
                            // Set up calender for creating the notification
                            mCalendar = getCalender();

                       /*
                       * Calculate first alarm trigger time comparing to start date
                       * */
                            calculateMonthlyFirstTriggerTime(mDayOfMonth, mEveryMonth, c);

                        /*
                        * set 1st alarm that triggered first
                        * */
                            new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                            databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);
                            if (occurrences > 0) {
                                occurrences--;
                                databaseHandler.updateOccurrence(id, occurrences);
                            }

                        /*
                        * This loop is used to create 8 different alarm according to month interval or occurrences
                        * */
                            for (int i = 1; i < 8; i++) {
                                mCalendar.add(Calendar.MONTH, mEveryMonth);
                                int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                                Log.d("occurrence", "" + occurrences);
                                if (occurrences > 0) {
                                    calculateAndSetMonthlyAlarm(mDayOfMonth, id, i, maximumDaysOfMonth);
                                    databaseHandler.updateOccurrence(id, occurrences--);
                                } else {
                                    break;
                                }
                            }
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mRadioBtnEndBy.isChecked()) {

                        if (!mEndDate.isEmpty()) {
                            String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                            int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    -1, finalEndDate, MONTHLY, 0, 0, 0,
                                    0, 0, 0, 0, 0, 0, 0,
                                    mDayOfMonth, mEveryMonth, 0, 0, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));

                            Log.d("Insert Id", "" + id);
                            Calendar c = Calendar.getInstance();
                            // Set up calender for creating the notification
                            mCalendar = getCalender();

                       /*
                       * Calculate first alarm trigger time comparing to start date
                       * */
                            calculateMonthlyFirstTriggerTime(mDayOfMonth, mEveryMonth, c);
                        /*
                        * set 1st alarm that triggered first
                        * */
                            new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                            databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);

                        /*
                        * This loop is used to create 8 different alarm according to month interval or end date
                        * */
                            for (int i = 1; i < 8; i++) {
                                mCalendar.add(Calendar.MONTH, mEveryMonth);
                                int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                                if (getDate(mEndDate).after(mCalendar.getTime())) {
                                    calculateAndSetMonthlyAlarm(mDayOfMonth, id, i, maximumDaysOfMonth);
                                } else {
                                    break;
                                }
                            }
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                           finish();
                        }else {
                            Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(context, "Please enter a valid month value", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "Please enter a valid day value", Toast.LENGTH_SHORT).show();

            }
        } else {
           /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (monthlyAlarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                // Set up calender for creating the notification
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                Calendar c = Calendar.getInstance();
                /*
                 * Calculate first alarm trigger time comparing to start date
                 * */
                calculateMonthlyFirstTriggerTime(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getEveryMonth(), c);

                /*
                 * set 1st alarm that triggered first
                 * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));

                /*
                 * This loop is used to create 8 different alarm according to month interval
                 * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    calculateAndSetMonthlyAlarm(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getID(), i, maximumDaysOfMonth);

                }

            }else if (monthlyAlarm.getEndAfterOccurrences()>0){
                int occur = -1;
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                // Set up calender for creating the notification
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                Calendar c = Calendar.getInstance();
                /*
                 * Calculate first alarm trigger time comparing to start date
                 * */
                calculateMonthlyFirstTriggerTime(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getEveryMonth(), c);
                /*
                 * set 1st alarm that triggered first
                 * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));
                if (monthlyAlarm.getEndAfterOccurrences()>0) {
                    occur = monthlyAlarm.getEndAfterOccurrences();
                    occur--;
                    databaseHandler.updateOccurrence(monthlyAlarm.getID(), occur);
                }

                /*
                 * This loop is used to create 8 different alarm according to month interval or occurrences
                 * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    Log.d("occurrence", "" + occur);
                    if (occur > 0) {
                        calculateAndSetMonthlyAlarm(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getID(), i, maximumDaysOfMonth);
                        databaseHandler.updateOccurrence(monthlyAlarm.getID(), occur--);
                    } else {
                        break;
                    }
                }

            }else if (getDate(monthlyAlarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                // Set up calender for creating the notification
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                Calendar c = Calendar.getInstance();

                /*
                 * Calculate first alarm trigger time comparing to start date
                 * */
                calculateMonthlyFirstTriggerTime(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getEveryMonth(), c);
                /*
                 * set 1st alarm that triggered first
                 * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));
                /*
                 * This loop is used to create 8 different alarm according to month interval or end date
                 * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumDaysOfMonth = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (getDate(mEndDate).after(mCalendar.getTime())) {
                        calculateAndSetMonthlyAlarm(monthlyAlarm.getDayOfMonth(), monthlyAlarm.getID(), i, maximumDaysOfMonth);
                    } else {
                        break;
                    }
                }
            }
        }
    }
    public void setMonthlyWithWeeklyAndDayAlarm(Alarm monthlyAlarm,Context context) {
        if (monthlyAlarm == null) {
            int mEveryMonth = Integer.valueOf(mEditTextEveryMonth2.getText().toString());
            int occurrences = Integer.valueOf(mEditTextOccurrences.getText().toString());
            if (mEveryMonth > 0 && mEveryMonth <= 12) {
                if (mRadioBtnNoEndDate.isChecked()) {
                    int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                            -1, "", MONTHLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mEveryMonth, mWeekNumber, mDayNumber, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));

                    Log.d("Insert Id", "" + id);
                    Calendar c = Calendar.getInstance();
                    // Set up calender for creating the notification
                    mCalendar = getMonthlyCalender();

                    if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                        mCalendar.set(Calendar.MONTH, mMonth);
                        mCalendar.add(Calendar.MONTH, mEveryMonth);
                        Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                        Log.d("Current Time ", "" + c.getTimeInMillis());
                    } else {
                        mCalendar.set(Calendar.MONTH, mMonth);
                        Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                        Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                    }

                     /*
                     * set 1st alarm that triggered first
                     * */
                    new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                    databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);

                    /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                    for (int i = 1; i < 8; i++) {
                        mCalendar.add(Calendar.MONTH, mEveryMonth);
                        int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                        calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, id, i, maximumWeekOfMonth);
                    }
                    Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (mRadioBtnEndAfter.isChecked()) {
                    if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0 ) {
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                occurrences, "", MONTHLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mEveryMonth, mWeekNumber, mDayNumber, 0, -1, 0, 0, 0, mIsActive, isRepeat,occurrences));
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification
                        mCalendar = getMonthlyCalender();

                        if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                            mCalendar.set(Calendar.MONTH, mMonth);
                            mCalendar.add(Calendar.MONTH, mEveryMonth);
                            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                            Log.d("Current Time ", "" + c.getTimeInMillis());
                        } else {
                            mCalendar.set(Calendar.MONTH, mMonth);
                            Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                            Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                        }

                    /*
                     * set 1st alarm that triggered first
                     * */
                        new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                        databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);
                        if (occurrences > 0) {
                            occurrences--;
                            databaseHandler.updateOccurrence(id, occurrences);
                        }

                     /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                        for (int i = 1; i < 8; i++) {
                            mCalendar.add(Calendar.MONTH, mEveryMonth);
                            int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

                            Log.d("occurrence", "" + occurrences);
                            if (occurrences > 0) {
                                calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, id, i, maximumWeekOfMonth);
                                databaseHandler.updateOccurrence(id, occurrences--);
                            } else {
                                break;
                            }
                        }
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();
                    }

                } else if (mRadioBtnEndBy.isChecked()) {
                    if (!mEndDate.isEmpty()) {
                        String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                -1, finalEndDate, MONTHLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, mEveryMonth, mWeekNumber, mDayNumber, 0, -1, 0, 0, 0, mIsActive, isRepeat,-1));

                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification
                        mCalendar = getMonthlyCalender();

                        if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                            mCalendar.set(Calendar.MONTH, mMonth);
                            mCalendar.add(Calendar.MONTH, mEveryMonth);
                            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                            Log.d("Current Time ", "" + c.getTimeInMillis());
                        } else {
                            mCalendar.set(Calendar.MONTH, mMonth);
                            Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                            Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                        }

                     /*
                     * set 1st alarm that triggered first
                     * */
                        new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, 0));
                        databaseHandler.addSubIdAlarm(monthlyAlarmId(id, 0), id);

                    /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                        for (int i = 1; i < 8; i++) {
                            mCalendar.add(Calendar.MONTH, mEveryMonth);
                            int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                            if (getDate(mEndDate).after(mCalendar.getTime())) {
                                calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, id, i, maximumWeekOfMonth);
                            } else {
                                break;
                            }
                        }
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                       finish();
                    }else {
                        Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                Toast.makeText(context, "Please enter a valid month value", Toast.LENGTH_SHORT).show();
            }
        }else {
            /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (monthlyAlarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getMonthlyCalender(month--, date[2],monthlyAlarm.getMonthWeekName(),monthlyAlarm.getMonthDayName(),sTime[0],sTime[1]);
                if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("Current Time ", "" + c.getTimeInMillis());
                } else {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                }

                     /*
                     * set 1st alarm that triggered first
                     * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));

                    /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                    calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, monthlyAlarm.getID(), i, maximumWeekOfMonth);
                }
            }else if (monthlyAlarm.getEndAfterOccurrences()>0){
                int occur =-1;
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getMonthlyCalender(month--, date[2],monthlyAlarm.getMonthWeekName(),monthlyAlarm.getMonthDayName(),sTime[0],sTime[1]);
                if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("Current Time ", "" + c.getTimeInMillis());
                } else {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                }

                    /*
                     * set 1st alarm that triggered first
                     * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));
                if (monthlyAlarm.getEndAfterOccurrences() > 0) {
                    occur = monthlyAlarm.getEndAfterOccurrences();
                    occur--;
                    databaseHandler.updateOccurrence(monthlyAlarm.getID(), occur);
                }

                     /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);

                    Log.d("occurrence", "" + occur);
                    if (occur > 0) {
                        calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, monthlyAlarm.getID(), i, maximumWeekOfMonth);
                        databaseHandler.updateOccurrence(monthlyAlarm.getID(), occur--);
                    } else {
                        break;
                    }
                }
            }else if (getDate(monthlyAlarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(monthlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(monthlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getMonthlyCalender(month--, date[2],monthlyAlarm.getMonthWeekName(),monthlyAlarm.getMonthDayName(),sTime[0],sTime[1]);

                if (mCalendar.get(Calendar.DAY_OF_MONTH) < mDay) {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("Current Time ", "" + c.getTimeInMillis());
                } else {
                    mCalendar.set(Calendar.MONTH, mMonth);
                    Log.d("elseCalenderTime", "" + mCalendar.getTimeInMillis());
                    Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
                }

                     /*
                     * set 1st alarm that triggered first
                     * */
                new NotificationScheduler().setMonthlyAlarm(context, mCalendar, monthlyAlarmId(monthlyAlarm.getID(), 0));

                    /*
                     * This loop is used to create 8 different alarm according to month interval or occurrences
                     * */
                for (int i = 1; i < 8; i++) {
                    mCalendar.add(Calendar.MONTH, monthlyAlarm.getEveryMonth());
                    int maximumWeekOfMonth = mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
                    if (getDate(mEndDate).after(mCalendar.getTime())) {
                        calculateAndSetMonthlyWeekWiseAlarm(mWeekNumber, monthlyAlarm.getID(), i, maximumWeekOfMonth);
                    } else {
                        break;
                    }
                }

            }
        }
    }
    //End Monthly Alarm

    /*
    * Start Yearly Alarm Code
    * */
    public void setEveryYearAlarm(Alarm yearlyAlarm,Context context) {
        if (yearlyAlarm == null) {
            int mDayOfMonth = Integer.valueOf(mEditTextYearMonthDay.getText().toString());
            int occurrences = Integer.valueOf(mEditTextOccurrences.getText().toString());

            if (Integer.valueOf(mEditTextRecurYear.getText().toString()) > 0 ) {
                int mEveryYear = Integer.valueOf(mEditTextRecurYear.getText().toString());
                if (mDayOfMonth > 0 && mDayOfMonth <= 31) {
                    if (mRadioBtnNoEndDate.isChecked()) {
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "Yes",
                                -1, "", YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber, mDayOfMonth, 0, 0, mIsActive, isRepeat,-1));
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification
                        mCalendar = getCalender();
                        calculateYearFirstTriggerTime(mDayOfMonth, mEveryYear, c);

                    /*
                     * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                     * */
                        new NotificationScheduler().setYearlyAlarm(this, mCalendar, id);
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();

                    } else if (mRadioBtnEndAfter.isChecked()) {
                        if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0 ) {
                            int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    occurrences, "", YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber, mDayOfMonth, 0, 0, mIsActive, isRepeat,occurrences));
                            Log.d("Insert Id", "" + id);
                            Calendar c = Calendar.getInstance();
                            // Set up calender for creating the notification
                            mCalendar = getCalender();
                            calculateYearFirstTriggerTime(mDayOfMonth, mEveryYear, c);

                        /*
                        * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                        * */
                            new NotificationScheduler().setYearlyAlarm(this, mCalendar, id);
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mRadioBtnEndBy.isChecked()) {
                        if (!mEndDate.isEmpty()) {
                            String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                            int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "No",
                                    -1, finalEndDate, YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber, mDayOfMonth, 0, 0, mIsActive, isRepeat,-1));
                            Log.d("Insert Id", "" + id);
                            Calendar c = Calendar.getInstance();
                            // Set up calender for creating the notification
                            mCalendar = getCalender();
                            calculateYearFirstTriggerTime(mDayOfMonth, mEveryYear, c);

                        /*
                        * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                        * */
                            new NotificationScheduler().setYearlyAlarm(this, mCalendar, id);
                            Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(context, "Please enter a valid day value", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(context, "Please enter a valid recurrence value", Toast.LENGTH_SHORT).show();
            }
        }else {
             /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (yearlyAlarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);

                calculateYearFirstTriggerTime(yearlyAlarm.getAlarmYearMonthDay(), yearlyAlarm.getAlarmEveryYear(), c);
                /*
                 * this will repeat alarm when previous one is triggered then we calculate next year alarm on AlarmReceiver.class
                 * */
                new NotificationScheduler().setYearlyAlarm(context, mCalendar, yearlyAlarm.getID());
            }else if (yearlyAlarm.getEndAfterOccurrences()>0){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                calculateYearFirstTriggerTime(yearlyAlarm.getAlarmYearMonthDay(), yearlyAlarm.getAlarmEveryYear(), c);
                /*
                 * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                 * */
                new NotificationScheduler().setYearlyAlarm(context, mCalendar, yearlyAlarm.getID());
            }else if (getDate(yearlyAlarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                mCalendar = getCalender(month--,date[1],date[2],sTime[0],sTime[1]);
                calculateYearFirstTriggerTime(yearlyAlarm.getAlarmYearMonthDay(), yearlyAlarm.getAlarmEveryYear(), c);
                /*
                 * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                 * */
                new NotificationScheduler().setYearlyAlarm(context, mCalendar, yearlyAlarm.getID());
            }

        }
    }
    public void setYearlyWithWeekAndDayAlarm(Alarm yearlyAlarm, Context context) {
        if (yearlyAlarm == null) {
            int occurrences = Integer.valueOf(mEditTextOccurrences.getText().toString());
            if (Integer.valueOf(mEditTextRecurYear.getText().toString()) > 0) {
                int mEveryYear = Integer.valueOf(mEditTextRecurYear.getText().toString());

                if (mRadioBtnNoEndDate.isChecked()) {
                    int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "yes",
                            -1, "", YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber2, 0, mYearWeekNumber, mYearDayNumber, mIsActive, isRepeat,-1));
                    Log.d("Insert Id", "" + id);
                    Calendar c = Calendar.getInstance();
                    // Set up calender for creating the notification
                    mCalendar = getYearlyCalender();
                    calculateYearlyAlarmWeekAndDay(mEveryYear, id, c,mCalendar);
                    Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (mRadioBtnEndAfter.isChecked()) {
                    if (Integer.valueOf(mEditTextOccurrences.getText().toString()) > 0 ) {
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "",
                                occurrences, "", YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber2, 0, mYearWeekNumber, mYearDayNumber, mIsActive, isRepeat,occurrences));
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification
                        mCalendar = getYearlyCalender();
                        calculateYearlyAlarmWeekAndDay(mEveryYear, id, c,mCalendar);
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(context, "Please enter a valid occurrences value", Toast.LENGTH_SHORT).show();

                    }
                } else if (mRadioBtnEndBy.isChecked()) {
                    if (!mEndDate.isEmpty()) {
                        String finalEndDate = mEndDate + "  " + mHour + ":" + mMinute;
                        int id = databaseHandler.addAlarm(new Alarm(mEditTextTitle.getText().toString(), mEditTextContent.getText().toString(), mStartDate, time, "",
                                -1, finalEndDate, YEARLY, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Integer.valueOf(mEditTextRecurYear.getText().toString()), mMonthNumber2, 0, mYearWeekNumber, mYearDayNumber, mIsActive, isRepeat,-1));
                        Log.d("Insert Id", "" + id);
                        Calendar c = Calendar.getInstance();
                        // Set up calender for creating the notification

                        mCalendar = getYearlyCalender();
                        calculateYearlyAlarmWeekAndDay(mEveryYear, id, c,mCalendar);
                        Toast.makeText(context, "Notification saved successfully!", Toast.LENGTH_SHORT).show();
                        finish();

                    }else {
                        Toast.makeText(context, "End date cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }else {
                Toast.makeText(context, "Please enter a valid recurrence value", Toast.LENGTH_SHORT).show();
            }
        }else {

            /*
            * This case is used to set alarm only if mobile is rebooted or if user on/off alarm from listing
            * then this case is set alarm again on these conditions
            * */
            mCalendar = Calendar.getInstance();
            if (yearlyAlarm.getNoEndDate().equalsIgnoreCase("Yes")){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getYearlyCalender(month--,date[2],sTime[0],sTime[1]);
                calculateYearlyAlarmWeekAndDayFromDB(yearlyAlarm, c, context);

            }else if (yearlyAlarm.getEndAfterOccurrences()>0){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getYearlyCalender(month--,date[2],sTime[0],sTime[1]);
                calculateYearlyAlarmWeekAndDayFromDB(yearlyAlarm, c,context);
            }else if (getDate(yearlyAlarm.getEndByDate()).after(new Date())){
                int date[] = UtilMethods.getSplitDate(yearlyAlarm.getDate());
                int month = date[0];
                int sTime[] = UtilMethods.getSplitTime(yearlyAlarm.getTime());
                Calendar c = Calendar.getInstance();
                // Set up calender for creating the notification
                mCalendar = getYearlyCalender(month--,date[2],sTime[0],sTime[1]);
                calculateYearlyAlarmWeekAndDayFromDB(yearlyAlarm, c,context);
            }
        }

    }

    private void calculateYearlyAlarmWeekAndDayFromDB(Alarm yearlyAlarm, Calendar c,Context context) {
        mCalendar.set(Calendar.MONTH, yearlyAlarm.getAlarmYearMonthName());
        mCalendar.set(Calendar.WEEK_OF_MONTH, yearlyAlarm.getAlarmYearWeekName());
        mCalendar.set(Calendar.DAY_OF_WEEK, yearlyAlarm.getAlarmYearDayName());

        if (mCalendar.getTime().before(getDate(yearlyAlarm.getDate()))) {
            mCalendar.add(Calendar.YEAR, yearlyAlarm.getAlarmEveryYear());
            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("Current Time ", "" + c.getTimeInMillis());

        } else {
            Log.d("ElsecalenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
        }

                /*
                 * this will repeat alarm when previous one is trggered then we calculate next yea alarm on AlarmReceiver.class
                 * */
        new NotificationScheduler().setYearlyAlarm(context, mCalendar, yearlyAlarm.getID());
    }

    private void calculateYearlyAlarmWeekAndDay(int mEveryYear, int id, Calendar c,Calendar mCalendar) {
        mCalendar.set(Calendar.MONTH, mMonthNumber2);
        mCalendar.set(Calendar.WEEK_OF_MONTH, mYearWeekNumber);
        mCalendar.set(Calendar.DAY_OF_WEEK, mYearDayNumber);

        if (mCalendar.getTime().before(getDate(mStartDate))) {
            mCalendar.add(Calendar.YEAR, mEveryYear);
            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("Current Time ", "" + c.getTimeInMillis());

        } else {
            Log.d("ElsecalenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("elseCurrent Time ", "" + c.getTimeInMillis());
        }

                /*
                 * this will repeat alarm when previous one is triggered then we calculate next yea alarm on AlarmReceiver.class
                 * */
        new NotificationScheduler().setYearlyAlarm(this, mCalendar, id);
    }
    //End Yearly Alarm


    private void calculateYearFirstTriggerTime(int mDayOfMonth, int mEveryYear, Calendar c) {
        /*
        * mMonthNumber is a month of year
        * mDay of month is day of month
        * check if start date month & day is greater then selected month & day then alarm trigger on next year
        * else trigger on current year
        * */
        if (mCalendar.get(Calendar.MONTH) <= mMonthNumber){
            if (mCalendar.get(Calendar.DAY_OF_MONTH) <= mDayOfMonth){
                mCalendar.set(Calendar.MONTH,mMonthNumber);
                mCalendar.set(Calendar.DAY_OF_MONTH,mDayOfMonth);
                Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                Log.d("Current Time ", "" + c.getTimeInMillis());

            }else {
                mCalendar.add(Calendar.YEAR,mEveryYear);
                mCalendar.set(Calendar.MONTH,mMonthNumber);
                mCalendar.set(Calendar.DAY_OF_MONTH,mDayOfMonth);
                Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
                Log.d("Current Time ", "" + c.getTimeInMillis());
            }
        }else {

            mCalendar.add(Calendar.YEAR,mEveryYear);
            mCalendar.set(Calendar.MONTH,mMonthNumber);
            mCalendar.set(Calendar.DAY_OF_MONTH,mDayOfMonth);
            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("Current Time ", "" + c.getTimeInMillis());
        }
    }

    private void calculateMonthlyFirstTriggerTime(int mDayOfMonth, int mEveryMonth, Calendar c) {

         /*
          * mCalendar.get(Calendar.DAY_OF_MONTH) is a start day of month
          * mDayOfMonth is a trigger day of month E.g 31
          * if start day of month is less then mDay of month then 1st alarm trigger in current month
          * if start day of month is greater then mDay of month then 1st alarm trigger after interval of month
          * */
        if (mCalendar.get(Calendar.DAY_OF_MONTH)  <= mDayOfMonth ){

            mCalendar.set(Calendar.MONTH,mMonth);
            mCalendar.set(Calendar.DAY_OF_MONTH ,mDayOfMonth);
            Log.d("Month",""+mMonth);
            Log.d("dayofmonth",""+mDayOfMonth);
            Log.d("Hour",""+mHour);
            Log.d("min",""+mMinute);
            Log.d("Year",""+mYear);
            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("Current Time ", "" + c.getTimeInMillis());

        }else {
            mCalendar.set(Calendar.MONTH,mMonth);
            mCalendar.add(Calendar.MONTH, mEveryMonth);
            mCalendar.set(Calendar.DAY_OF_MONTH,mDayOfMonth);
            Log.d("calenderTime", "" + mCalendar.getTimeInMillis());
            Log.d("Current Time ", "" + c.getTimeInMillis());

        }
    }

    /*
    * calculateAndSetMonthlyAlarm() is used to calculate the actual day of month
    * In which the alarm is being triggered
    * */
    private void calculateAndSetMonthlyAlarm(int mDayOfMonth, int id, int i, int maximumDaysOfMonth) {
        if (mDayOfMonth <= maximumDaysOfMonth) {
            mCalendar.set(Calendar.DAY_OF_MONTH, mDayOfMonth);
        } else {
            mCalendar.set(Calendar.DAY_OF_MONTH, maximumDaysOfMonth);
        }

        Log.d("looping Calender",""+mCalendar.getTimeInMillis());
        Log.d("looping Current", ""+System.currentTimeMillis());
        new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, i));
        databaseHandler.addSubIdAlarm(monthlyAlarmId(id, i), id);
    }

    /*
    * calculateAndSetMonthlyWeekWiseAlarm() is used to calculate the actual Week of month
    * In which the alarm is being triggered
    * */
    private void calculateAndSetMonthlyWeekWiseAlarm(int mWeekOfMonth, int id, int i, int maximumWeekOfMonth) {
        if (mWeekOfMonth <= maximumWeekOfMonth) {
            mCalendar.set(Calendar.WEEK_OF_MONTH, mWeekOfMonth);
            mCalendar.set(Calendar.DAY_OF_WEEK,mDayNumber);
//            mCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH,mWeekNumber);
        } else {
            mCalendar.set(Calendar.WEEK_OF_MONTH, maximumWeekOfMonth);
            mCalendar.set(Calendar.DAY_OF_WEEK,mDayNumber);
//            mCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH,mWeekNumber);
        }

        Log.d("looping Calender",""+mCalendar.getTimeInMillis());
        Log.d("looping Current", ""+System.currentTimeMillis());
        new NotificationScheduler().setMonthlyAlarm(this, mCalendar, monthlyAlarmId(id, i));
        databaseHandler.addSubIdAlarm(monthlyAlarmId(id, i), id);
    }


    public Calendar getCalender(){
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }
    public Calendar getCalender(int month,int day,int year,int hour, int minute){
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }

    public Calendar getMonthlyCalender(){
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.WEEK_OF_MONTH, mWeekNumber);
        mCalendar.set(Calendar.DAY_OF_WEEK,mDayNumber);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }
    public Calendar getMonthlyCalender(int month,int year,int weekNumber, int dayNumber,int hour, int minute){
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.WEEK_OF_MONTH, weekNumber);
        mCalendar.set(Calendar.DAY_OF_WEEK,dayNumber);
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }

    public Calendar getYearlyCalender(){
        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
        mCalendar.set(Calendar.MINUTE, mMinute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }
    public Calendar getYearlyCalender(int month,int year,int hour, int minute){
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, 0);
        return mCalendar;
    }
    public int weeklyAlarmId(int id, int dayId){
        return Integer.valueOf(id+""+909+""+dayId);
    }
    public int monthlyAlarmId(int id, int dayId){
        return Integer.valueOf(id+""+909+""+dayId);
    }

    public Date getDate(String srtDate){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(srtDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if (spinner.getId() == R.id.spinner_day_name) {
            String name  = (String) mSpinnerMonthDayName.getSelectedItem();
            mDayNumber = UtilMethods.dayNumber(name);
            Log.d("dayNumber",""+mDayNumber);
        }else if (spinner.getId() == R.id.spinner_week_name){
            String name  = (String) mSpinnerMonthWeekName.getSelectedItem();
            mWeekNumber = UtilMethods.weekNumber(name);
            Log.d("WeekNumber",""+mWeekNumber);
        }else if (spinner.getId() == R.id.spinner_month){
            String name = (String) mSpinnerMonth.getSelectedItem();
             mMonthNumber = UtilMethods.monthNumber(name);
            Log.d("monthNumber",""+mMonthNumber);
        }else if (spinner.getId() == R.id.spinner_year_day){
            String name = (String) mSpinnerYearDay.getSelectedItem();
            mYearDayNumber = UtilMethods.dayNumber(name);
            Log.d("dayNumberYear",""+mDayNumber);
        }else if (spinner.getId() == R.id.spinner_year_week){
            String name = (String) mSpinnerYearWeek.getSelectedItem();
            mYearWeekNumber = UtilMethods.weekNumber(name);
            Log.d("WeekNumberYear",""+mWeekNumber);
        }else if (spinner.getId() == R.id.spinner_month2){
            String name = (String) mSpinnerMonth2.getSelectedItem();
            mMonthNumber2 = UtilMethods.monthNumber(name);
            Log.d("monthNumber2",""+mMonthNumber);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
