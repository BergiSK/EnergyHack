package com.example.ideapad.testapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends Activity {

    BroadcastReceiver updateUIReciver;
    NotificationCompat.Builder notification;
    private static final int ID = 565465;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.ideapad.testapp");

        updateUIReciver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                buildNotification(intent.getStringExtra("time"));
            }
        };
        registerReceiver(updateUIReciver,filter);
        createSimpleChart();
        //chart.invalidate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(updateUIReciver);
        super.onDestroy();
    }

    private void createSimpleChart() {
        final ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        BarChart chart = new BarChart(this);
        setContentView(chart);

        BarData data = new BarData(labels, createSimpleDataset());

        chart.setData(data);

        chart.setDescription("# of times Alice called Bob");
        chart.animateY(3000);
    }

    private BarDataSet createSimpleDataset() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(8f, 1));
        entries.add(new BarEntry(8f, 2));
        entries.add(new BarEntry(12f, 3));
        entries.add(new BarEntry(18f, 4));
        entries.add(new BarEntry(9f, 5));

        BarDataSet dataSet = new BarDataSet(entries, "# of Calls");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //dataSet.set
        return dataSet;
    }

    private void buildNotification(String data) {
        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setTicker("Ticker");
        notification.setWhen(System.currentTimeMillis());

        // toto sa nezobrazi
        notification.setContentTitle("Notification title");
        notification.setContentText(data);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Event tracker details:");
        String [] lines = data.split(";");
        for (String line : lines) {
            inboxStyle.addLine(line);
        }
        notification.setStyle(inboxStyle);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(ID, notification.build());
    }
}
