package com.scoutzknifez.weatherappv2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scoutzknifez.weatherappv2.R;
import com.scoutzknifez.weatherappv2.datafetcher.DataConnector;
import com.scoutzknifez.weatherappv2.utility.AppUtils;
import com.scoutzknifez.weatherappv2.utility.Constants;
import com.scoutzknifez.weatherappv2.utility.Globals;
import com.scoutzknifez.weatherappv2.utility.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LocationWaiter extends Fragment {
    @BindView(R.id.text_waiter_message)
    TextView textMessage;

    int resourceID;
    int timesResumed = 0;

    public LocationWaiter(int resourceID) {
        super();
        setResourceID(resourceID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_waiter, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getTextMessage().setText(getResourceID());

        new Thread(() -> {
            while (DataConnector.lastKnownLocation == null) {}

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
            AppUtils.getMainActivity(this).initializeMainWeatherDisplay();
        }).start();
    }
}
