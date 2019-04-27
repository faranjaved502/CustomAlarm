package dev.faran.myalarm;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.faran.myalarm.R;

/**
 * Created by fjaved on 6/25/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    ArrayList<Alarm> mList;
    Context context;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    DatabaseHandler databaseHandler;

    public NotificationAdapter(ArrayList<Alarm> list, Context context) {
        super();
        this.mList = list;
        this.context = context;
        sortList();
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.ViewHolder holder, final int position) {
        final Alarm item = mList.get(position);
        databaseHandler = new DatabaseHandler(context);
        holder.mTextViewStartDate.setText(UtilMethods.convertDate(item.getDate()));
        holder.mTextViewStartTime.setText(item.getTime());
        holder.mTextViewTitle.setText(item.getTitle());
        holder.mTextViewContent.setText(item.getContent());
        Boolean isActive = UtilMethods.intToBool(item.getAlarmIsActive());
        Boolean isRepeat = UtilMethods.intToBool(item.getAlarmIsRepeat());
        if (isRepeat){
            holder.mImageViewRepeat.setColorFilter(ContextCompat.getColor(context, R.color.theme_blue), PorterDuff.Mode.SRC_ATOP);
        }else {
            holder.mImageViewRepeat.setColorFilter(ContextCompat.getColor(context, R.color.gray_text_color), PorterDuff.Mode.SRC_ATOP);

        }

        if (isActive){
            holder.mSwitchNotificationOffOn.setChecked(true);
        }else {
            holder.mSwitchNotificationOffOn.setChecked(false);
        }

        holder.mSwitchNotificationOffOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.mSwitchNotificationOffOn.isChecked()) {
                    item.setAlarmIsActive(UtilMethods.stringValueOf(true));
                    databaseHandler.updateIsActive(item.getID(),1);

                    if (item.getPatternDuration() == NotificationAddActivity.ONCE){
                        new NotificationAddActivity().setAlarmOnce(item.getID(),item.getDate(),item.getTime(),context);
                    }else if (item.getPatternDuration() == NotificationAddActivity.DAILY){
                        if (item.getMondayToFriday() == 1){
                            new NotificationAddActivity().monToFriAlarm(item,context);
                        }else {
                            new NotificationAddActivity().everyDayAlarm(item,context);
                        }
                    }else if (item.getPatternDuration() == NotificationAddActivity.WEEKLY){
                        new NotificationAddActivity().setWeeklyAlarm(item,context);
                    }else if (item.getPatternDuration() == NotificationAddActivity.MONTHLY){
                        if (item.getDayOfMonth() != 0 ){
                            new NotificationAddActivity().everyMonthAlarm(item,context);
                        }else {
                            new NotificationAddActivity().setMonthlyWithWeeklyAndDayAlarm(item,context);
                        }
                    }else if (item.getPatternDuration() == NotificationAddActivity.YEARLY){
                        if (item.getAlarmYearMonthDay() != 0){
                            new NotificationAddActivity().setEveryYearAlarm(item,context);
                        }else {
                            new NotificationAddActivity().setYearlyWithWeekAndDayAlarm(item,context);
                        }
                    }
                }else {
                    item.setAlarmIsActive(UtilMethods.stringValueOf(false));
                    databaseHandler.updateIsActive(item.getID(),0);
                    if (item.getPatternDuration() == NotificationAddActivity.ONCE || item.getPatternDuration() == NotificationAddActivity.DAILY ||
                            item.getPatternDuration() == NotificationAddActivity.YEARLY){
                        NotificationScheduler.cancelReminder(context, AlarmReceiver.class,item.getID());
                    }else if (item.getPatternDuration() == NotificationAddActivity.WEEKLY || item.getPatternDuration() == NotificationAddActivity.MONTHLY){
                        ArrayList<Integer> alarmIdList = databaseHandler.getSubIdAlarm(item.getID());
                        for (int i = 0; i < alarmIdList.size(); i++) {
                            NotificationScheduler.cancelReminder(context, AlarmReceiver.class, alarmIdList.get(i));
                        }
                    }
                }
            }
        });

        if (item.getPatternDuration() == NotificationAddActivity.ONCE){
            holder.mTextViewPattern.setText("Once");
        }else if (item.getPatternDuration() == NotificationAddActivity.DAILY){
            holder.mTextViewPattern.setText("Daily");
        }else if (item.getPatternDuration() == NotificationAddActivity.WEEKLY){
            holder.mTextViewPattern.setText("Weekly");
        }else if (item.getPatternDuration() == NotificationAddActivity.MONTHLY){
            holder.mTextViewPattern.setText("Monthly");
        }else {
            holder.mTextViewPattern.setText("Yearly");
        }


        binderHelper.bind(holder.swipeLayout ,String.valueOf(mList.get(position).getID()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

     public void sortList(){

        Collections.sort(mList, new Comparator<Alarm>() {
            @Override
            public int compare(Alarm alarm, Alarm t1) {
                return Integer.valueOf(t1.getID()).compareTo(alarm.getID());


            }
        });

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mTextViewStartDate;
        TextView mTextViewStartTime;
        TextView mTextViewTitle;
        TextView mTextViewContent;
        TextView mTextViewPattern;
        LinearLayout mItemViewLayout;
        private SwipeRevealLayout swipeLayout;
        ImageView mImageViewRepeat;
        Switch mSwitchNotificationOffOn;
        ImageView mDeleteImageView;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewStartDate =  itemView.findViewById(R.id.txt_view_start_date);
            mTextViewStartTime =  itemView.findViewById(R.id.text_view_stat_time);
            mTextViewTitle =  itemView.findViewById(R.id.text_view_noti_title);
            mTextViewContent =  itemView.findViewById(R.id.text_view_noti_content);
            mTextViewPattern =  itemView.findViewById(R.id.text_noti_pattern);
            swipeLayout =  itemView.findViewById(R.id.swipe_layout);
            mImageViewRepeat =  itemView.findViewById(R.id.image_repeat);
            mSwitchNotificationOffOn =  itemView.findViewById(R.id.noti_off);
            mItemViewLayout = itemView.findViewById(R.id.item_view);
            mDeleteImageView = itemView.findViewById(R.id.delete);


//            itemView.setOnClickListener(this);
            mDeleteImageView.setOnClickListener(this);
            mItemViewLayout.setOnClickListener(this);



        }


        @Override
        public void onClick(View v) {
            if (v == mDeleteImageView) {
                databaseHandler.deleteAlarm(mList.get(getAdapterPosition()).getID());
                if (mList.get(getAdapterPosition()).getPatternDuration() == NotificationAddActivity.ONCE || mList.get(getAdapterPosition()).getPatternDuration() == NotificationAddActivity.DAILY ||
                        mList.get(getAdapterPosition()).getPatternDuration() == NotificationAddActivity.YEARLY){
                    NotificationScheduler.cancelReminder(context, AlarmReceiver.class,mList.get(getAdapterPosition()).getID());
                }else if (mList.get(getAdapterPosition()).getPatternDuration() == NotificationAddActivity.WEEKLY || mList.get(getAdapterPosition()).getPatternDuration() == NotificationAddActivity.MONTHLY){
                    ArrayList<Integer> alarmIdList = databaseHandler.getSubIdAlarm(mList.get(getAdapterPosition()).getID());
                    for (int i = 0; i < alarmIdList.size(); i++) {
                        NotificationScheduler.cancelReminder(context, AlarmReceiver.class, alarmIdList.get(i));
                    }
                }
                mList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }else if (v == mItemViewLayout){
            }
        }

    }

}
