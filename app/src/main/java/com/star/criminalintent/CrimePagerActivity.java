package com.star.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.star.criminalintent.model.Crime;

import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class CrimePagerActivity extends AppCompatActivity
        implements CrimeFragment.Callbacks {

    public static final String EXTRA_CRIME_ID = "com.star.criminalintent.crime_id";

    private ViewPager mViewPager;
    private Button mJumpToFirstButton;
    private Button mJumpToLastButton;

    private List<Crime> mCrimes;

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = findViewById(R.id.activity_crime_pager_view_pager);

        mCrimes = CrimeLab.getInstance(this).getCrimes();

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mJumpToFirstButton = findViewById(R.id.jump_to_first);
        mJumpToFirstButton.setOnClickListener(v -> mViewPager.setCurrentItem(0));

        mJumpToLastButton = findViewById(R.id.jump_to_last);
        mJumpToLastButton.setOnClickListener(v -> mViewPager.setCurrentItem(mCrimes.size() - 1));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateUI();
                setResult();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setResult();
    }

    @Override
    public void onResume() {
        super.onResume();

        updateUI();
    }

    private void updateUI() {
        mJumpToFirstButton.setEnabled(mViewPager.getCurrentItem() != 0);
        mJumpToLastButton.setEnabled(mViewPager.getCurrentItem() != (mCrimes.size() - 1));
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CRIME_ID, mCrimes.get(mViewPager.getCurrentItem()).getId());
        setResult(RESULT_OK, intent);
    }

    @Override
    public void onCrimeUpdated() {

    }

    @Override
    public void onCrimeDeleted() {
        finish();
    }
}
