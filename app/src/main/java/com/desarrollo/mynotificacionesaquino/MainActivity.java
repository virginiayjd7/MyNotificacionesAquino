package com.desarrollo.mynotificacionesaquino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btNotificacion;
    private PendingIntent pendingIntent;
    private PendingIntent siPendingIntent;
    private PendingIntent noPendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNotificacion = findViewById(R.id.btNotificacion);
        btNotificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPendingIntent();
                setSiPendingIntent();
                setNoPendingIntent();
                createNotificationChannel();
                createNotification();
            }
        });
    }

    private void setSiPendingIntent() {
        Intent intent = new Intent(this, ReplyActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ReplyActivity.class);
        stackBuilder.addNextIntent(intent);
        siPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setNoPendingIntent() {
        Intent intent = new Intent(this, ArchiveActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ArchiveActivity.class);
        stackBuilder.addNextIntent(intent);
        noPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void setPendingIntent() {
        Intent intent = new Intent(this, NotificacionActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificacionActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setContentTitle("Notificacion Covid I Yaneth aquino");
        builder.setContentText("La historia Indeterminable o la Historia sin fin");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        //builder.setDefaults(Notification.DEFAULT_SOUND);
        Uri soundNotification= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        builder.setSound(soundNotification);
        builder.setContentIntent(pendingIntent);
        builder.addAction(R.drawable.ic_baseline_sms_24, "REPLY", siPendingIntent);
        builder.addAction(R.drawable.ic_baseline_sms_24, "ARCHIVE", noPendingIntent);
        NotificationCompat.BigPictureStyle bigPictureStyle=new NotificationCompat.BigPictureStyle();
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(),R.mipmap.lucky));
        builder.setStyle(bigPictureStyle);
        Notification notification=builder.build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
}

