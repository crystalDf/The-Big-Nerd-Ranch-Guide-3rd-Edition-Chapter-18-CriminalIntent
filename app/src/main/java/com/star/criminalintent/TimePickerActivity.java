package com.star.criminalintent;


import android.content.Context;
import android.content.Intent;

import java.util.Date;

import androidx.fragment.app.Fragment;

public class TimePickerActivity extends SingleFragmentActivity {

    private static final String EXTRA_DATE = "com.star.criminalintent.date";

    public static Intent newIntent(Context packageContext, Date date) {

        Intent intent = new Intent(packageContext, TimePickerActivity.class);
        intent.putExtra(EXTRA_DATE, date);

        return intent;
    }

    @Override
    protected Fragment createFragment() {

        Date date = (Date) getIntent().getSerializableExtra(EXTRA_DATE);

        return TimePickerFragment.newInstance(date);
    }
}
