package me.guendouz.livedata_recyclerview.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by guendouz on 15/02/2018.
 */

@Database(entities = {Post.class}, version = 2 ,exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {

    private static PostsDatabase INSTANCE;

    private final static List<Post> POSTS = Arrays.asList(
            new Post("Winter Olympics: Aksel Lund Svindal wins downhill gold at 35 in Pyeongchang", "Norway's Aksel Lund Svindal became the oldest Olympic alpine skiing champion as he held off Kjetil Jansrud to win downhill gold at the age of 35.\n" +
                    "\n" +
                    "He finished 0.12 seconds ahead of compatriot Jansrud, with Swiss world champion Beat Feuz taking bronze in Pyeongchang, South Korea."),
            new Post("Winter Olympics: Britain's Elise Christie 'fearless' despite fall in 500m final", "Great Britain's Elise Christie says she will \"still be racing fearless\" at the Winter Olympics, despite falling in the 500m short-track speed skating final.\n" +
                    "\n" +
                    "The 27-year-old, disqualified in each of her three events at Sochi 2014, was clipped by Yara van Kerkhof and crashed before finishing fourth on Tuesday.\n" +
                    "\n" +
                    "Scot Christie will race again in the 1500m on Saturday and the 1,000m on Tuesday in Pyeongchang."),
            new Post("Chinese woman joins handbag in X-ray machine", "Worried about your bags being stolen at security? One Chinese woman joined her handbag through an X-ray machine to prevent just that.\n" +
                    "\n" +
                    "Staff at Dongguan Railway Station in southern China were shocked to find the silhouette of the train commuter on their X-ray monitors.\n" +
                    "\n" +
                    "An online video showed the bizarre incident took place on Sunday during the Lunar New Year travel rush.")
    );

    public abstract PostDao postDao();

    private static final Object sLock = new Object();

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE posts_db "
                    +"ADD COLUMN  TEXT");

        }
    };*/
    public static PostsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PostsDatabase.class, "Posts.db")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(
                                        () -> getInstance(context).postDao().saveAll(POSTS));
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }
}
