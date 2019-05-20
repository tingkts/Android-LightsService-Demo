package ting.notificationdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.InvocationTargetException;

import static android.app.Notification.VISIBILITY_SECRET;
import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String LOGTAG = MainActivity.class.getName();

    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //goToSleep
        try {
            powerManager.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(powerManager, SystemClock.uptimeMillis());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(LOGTAG, "thread start");
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d(LOGTAG, "thread send notification");
                    sendNotification();
                    Log.d(LOGTAG, "thread end");
                }
            }).start();


    }

    private void sendNotification(){
        Log.i(LOGTAG, "sendNotification");
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //26及以上
            NotificationChannel notificationChannel=new NotificationChannel("id","name", IMPORTANCE_DEFAULT);
            notificationChannel.canBypassDnd();//可否绕过请勿打扰模式
            notificationChannel.canShowBadge();//桌面lanchener显示角标
            notificationChannel.enableLights(true);//闪光
            notificationChannel.shouldShowLights();//闪光
            notificationChannel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
            notificationChannel.enableVibration(true);//是否允许震动
            notificationChannel.setVibrationPattern(new long[]{100,100,200});//设置震动方式（事件长短）
            notificationChannel.getAudioAttributes();//获取系统响铃配置
            notificationChannel.getGroup();//获取消息渠道组
            notificationChannel.setBypassDnd(true);
            notificationChannel.setDescription("description");
            notificationChannel.setLightColor(Color.GREEN);//制定闪灯是灯光颜色
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);

            Notification.Builder builder=new Notification.Builder(getApplicationContext(),"id");
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);
            builder.setChannelId("id");
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("题目");
            builder.setContentText("内容");
            builder.setNumber(3);
//            Intent intent=new Intent(this,SecondActivity.class);
//            PendingIntent pendingIntent=PendingIntent.getActivity(this,PENDING_INTENT_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(pendingIntent);

            notificationManager.notify(2,builder.build());
        }else {
            NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            builder.setContentTitle("题目");
            builder.setContentText("内容");
//            Intent intent=new Intent(this,SecondActivity.class);
//            PendingIntent pendingIntent=PendingIntent.getActivity(this,PENDING_INTENT_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//            builder.setContentIntent(pendingIntent);
            notificationManager.notify(2,builder.build());
        }
    }


    @Override
    public void onClick(View v) {
        Log.i(LOGTAG, "button onClick");
        sendNotification();
    }
}
