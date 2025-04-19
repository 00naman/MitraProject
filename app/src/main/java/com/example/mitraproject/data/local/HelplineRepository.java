package com.example.mitraproject.data.local;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.mitraproject.data.local.entities.Helpline;
import java.util.List;

public class HelplineRepository {
    private final HelplineDao helplineDao;

    public HelplineRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        helplineDao = db.helplineDao();
    }

    public LiveData<List<Helpline>> getAllHelplines() {
        return helplineDao.getAllHelplines();
    }
}
