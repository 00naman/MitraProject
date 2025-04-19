package com.example.mitraproject.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.mitraproject.data.local.entities.Helpline;

import java.util.List;

@Dao
public interface HelplineDao {

    @Insert
    void insertAll(List<Helpline> helplines);

    @Query("SELECT * FROM helpline")
    LiveData<List<Helpline>> getAllHelplines();

    @Query("DELETE FROM helpline")
    void deleteAll();
}
