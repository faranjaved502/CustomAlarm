package dev.faran.myalarm;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import dev.faran.myalarm.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView notificationListingView;
    NotificationAdapter notificationAdapter;
    ArrayList<Alarm> alarmArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler;
    private TextView mTextViewNoData;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        floatingActionButton.setOnClickListener(this);
    }
    void init(){
        databaseHandler = new DatabaseHandler(this);
        notificationListingView = findViewById(R.id.recycler_view_notification_listing);
        mTextViewNoData =  findViewById(R.id.no_data);
        floatingActionButton = findViewById(R.id.fab);
    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton){
            Intent intent = new Intent(this, NotificationAddActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("a","Resume");
        alarmArrayList = databaseHandler.getAllAlarm();
        Log.d("list", ""+alarmArrayList.size());
        if (alarmArrayList.size() == 0){
            notificationListingView.setVisibility(View.GONE);
            mTextViewNoData.setVisibility(View.VISIBLE);
        }
        notificationListingView.setLayoutManager(new LinearLayoutManager(this));
        notificationAdapter = new NotificationAdapter(alarmArrayList,this);
        notificationListingView.setAdapter(notificationAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("a","Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("a","Stop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("a","Pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("a","Destroy");
    }
}
