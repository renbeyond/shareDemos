package com.example.demo.eric.unittest.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.demo.eric.unittest.activity.UnitTestMainActivity;
import com.robotium.solo.Solo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Eric on 16/3/22.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<UnitTestMainActivity> {

    private Solo solo;
    public MainActivityTest() {
        super(UnitTestMainActivity.class);
    }

    @Before
    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void testShowText() {
        Assert.assertTrue(solo.searchText("robotium test"));
    }
}
