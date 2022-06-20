package fr.dawan.myapplication;

import static fr.dawan.myapplication.MyApplicationClasse.CHANNEL_1;
import static fr.dawan.myapplication.MyApplicationClasse.CHANNEL_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NotifChannelActivity extends AppCompatActivity {

    EditText edTitle, edDescription;
    NotificationManagerCompat notifManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_channel);

        edTitle = findViewById(R.id.edTitle);
        edDescription = findViewById(R.id.edDescription);

        notifManager = NotificationManagerCompat.from(this);
    }

    public void btnChannel1Click(View view) {
        String title =edTitle.getText().toString();
        String description = edDescription.getText().toString();

        //Si la version est < 26, channel sera ignoré
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH) //la notif emet un son
                .build();

        notifManager.notify(1, notification);
    }

    public void btnChannel2Click(View view) {
        String title =edTitle.getText().toString();
        String description = edDescription.getText().toString();

        //Si la version est < 26, channel sera ignoré
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2)
                .setSmallIcon(R.drawable.ic_email)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_LOW) //la notif est silencieuse
                .build();

        notifManager.notify(2, notification);

    }
}