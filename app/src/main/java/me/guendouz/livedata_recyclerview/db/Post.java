package me.guendouz.livedata_recyclerview.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by guendouz on 15/02/2018.
 */

@Entity
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;

    public Post() {
    }

    @Ignore
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
