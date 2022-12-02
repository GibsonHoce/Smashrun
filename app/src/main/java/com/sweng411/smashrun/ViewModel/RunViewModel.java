package com.sweng411.smashrun.ViewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sweng411.smashrun.Callback;
import com.sweng411.smashrun.Model.Run;
import com.sweng411.smashrun.Repo.UserRunRepository;
import com.sweng411.smashrun.State.RunsUiState;
import com.sweng411.smashrun.State.UserRunUiState;

import java.util.ArrayList;
import java.util.List;

public class RunViewModel extends ViewModel {
    private UserRunRepository repository = UserRunRepository.GetInstance();
    private final MutableLiveData<List<UserRunUiState>> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<UserRunUiState>> GetUserRuns() {

        //Grabs data from repo using a callback
        repository.GetRuns(runs -> {
            ArrayList<UserRunUiState> states = new ArrayList<>();
            Log.d("RunViewModel", String.valueOf(runs.size()));

            //Takes data from Repo and transforms it into what the UI consumes
            for (Run run :
                    runs) {
                UserRunUiState state = new UserRunUiState();
                state.calories = String.valueOf(run.Calories);
                state.distance = String.valueOf(run.Distance);
                state.duration = String.valueOf(run.Duration);
                state.pace = String.valueOf(run.Duration / run.Distance);
                state.date = String.valueOf(run.Date);
                states.add(state);
            }

            //Live data is a way to update UI after it received the initial variable
            userLiveData.postValue(states);
        });

        //Returns initial live data variable for UI to consume
        return userLiveData;
    }

    public LiveData<Boolean> GetIsLoading() {
        return isLoading;
    }


}