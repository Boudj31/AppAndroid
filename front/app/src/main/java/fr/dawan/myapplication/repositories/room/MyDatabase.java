package fr.dawan.myapplication.repositories.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import fr.dawan.myapplication.entities.room.Category;
import fr.dawan.myapplication.entities.room.CodeBarre;
import fr.dawan.myapplication.entities.room.Product;
import fr.dawan.myapplication.entities.room.ProductSupplierCrossRef;
import fr.dawan.myapplication.entities.room.Supplier;

@Database(entities = {Product.class, Category.class, Supplier.class, CodeBarre.class, ProductSupplierCrossRef.class}, exportSchema = false, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static final String DB_NAME = "my_database";
    private volatile static MyDatabase instance; //volatile: accéssible par plusieurs Threads


    //synchronized vérrouille l'accès à la ressource partagée
    public static synchronized MyDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, DB_NAME)
                    //.addMigrations(MIGRATION_1_2)
                    //.addMigrations(MIGRATION_2_3)
                    .fallbackToDestructiveMigration() // au changement de la version, si aucune migration n'ai appliquée, en sera en mode create-drop
                    .build();
        }

        return instance;
    }

    //Les DAOs
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract CodeBarreDao codeBarreDao();
    public abstract SupplierDao supplierDao();
    public abstract ProductSupplierDao productSupplierDao();

    /*
    Modification de la BD
    1- Modifier la version
    2- Créer des migrations pour lister les modif à appliquer
     */

    //Modification de la version: version 2
    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN last_update INTEGER");
        }
    };

    //Modifier la version : version3
    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE users_new(userId INTEGER, username TEXT, last_update INTEGER, primary key (userId))");
            //copie des données
            database.execSQL("INSERT INTO users_new(userId, username, last_update) select user_id, username, last_update from users");
            //Supprimer de la table users
            database.execSQL("DROP table users");
            //Renommer la nouvelle table
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };


}
