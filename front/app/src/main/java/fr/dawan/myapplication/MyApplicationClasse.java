package fr.dawan.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplicationClasse extends Application {

    //Application classe - sert à déckarer des variables globales - ou exécuter des traitement au démarrage de l'appli.
    //Ne pas oublier de déclarer cette classe dans le Manifest.xml

    //Ex: déclaration de variables globales - Channel pour les Notifications

    public static final String CHANNEL_1 = "channel1";
    public static final String CHANNEL_2 = "channel2";

    public static String token = "";


    //Cette méthode s'exécutera avant la méthode onCreate définie ds toutes les Activity
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        //Channel obligation depuis la version de 26 de l'API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2, "Channel 2", NotificationManager.IMPORTANCE_LOW);
            channel2.setDescription("This is channel 2");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }

    }
}
