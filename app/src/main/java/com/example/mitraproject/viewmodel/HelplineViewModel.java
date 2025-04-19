package com.example.mitraproject.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.mitraproject.data.local.HelplineRepository;
import com.example.mitraproject.data.local.entities.Helpline;
import java.util.List;

public class HelplineViewModel extends AndroidViewModel {
    private final HelplineRepository repository;
    public LiveData<List<Helpline>> allHelplines;

    public HelplineViewModel(@NonNull Application application) {
        super(application);
        repository = new HelplineRepository(application);
        allHelplines = repository.getAllHelplines();
    }
}