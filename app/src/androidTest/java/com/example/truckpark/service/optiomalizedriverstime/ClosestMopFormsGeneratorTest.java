package com.example.truckpark.service.optiomalizedriverstime;

import android.content.Context;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.truckpark.domain.entity.MopForm;
import com.example.truckpark.domain.json.mopapi.ExtendedMopData;
import com.example.truckpark.domain.json.mopapi.Mop;
import com.example.truckpark.domain.json.positionapi.Coordinate;
import com.example.truckpark.repository.CurrentPosition;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ClosestMopFormsGeneratorTest {

    private static ClosestMopFormsGenerator closestMopFormsGenerator;

    @Before
    public static void prepareDataForTests() {
        Context context = InstrumentationRegistry.getTargetContext();
        closestMopFormsGenerator = new ClosestMopFormsGenerator(context);
    }

    @Test
    public void generateClosestMopFormsGenerator_inputCorrectValues_assertQuantityIsProper() {
        //given
        prepareRepositoryDataToSetCurrentPositionInGdansk();

        Mop mop1 = new Mop(
                1L,
                "MOP I Jeżewo",
                "MOP I",
                "Jeżewo",
                new Coordinate(1L, 53.135688, 22.750355),
                "S8",
                10,
                0,
                "1",
                new ExtendedMopData()
        );

        Mop mop2 = new Mop(
                2L,
                "MOP II Radule",
                "MOP II",
                "Radule",
                new Coordinate(2L, 53.144893, 22.793501),
                "S8",
                250,
                0,
                "2",
                new ExtendedMopData()
        );

        List<Mop> mops = new ArrayList<>(Arrays.asList(mop2, mop1));
        //when
        List<MopForm> generatedClosestMopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(mops);
        //then
        assertThat(generatedClosestMopForms.size(), is(equalTo(2)));
    }

    @Test
    public void generateClosestMopFormsGenerator_inputCorrectValues_assertOrderIsProper() {
        //given
        prepareRepositoryDataToSetCurrentPositionInGdansk();

        Mop mop1 = new Mop(
                1L,
                "MOP I Jeżewo",
                "MOP I",
                "Jeżewo",
                new Coordinate(1L, 53.135688, 22.750355),
                "S8",
                10,
                0,
                "1",
                new ExtendedMopData()
        );

        Mop mop2 = new Mop(
                2L,
                "MOP II Radule",
                "MOP II",
                "Radule",
                new Coordinate(2L, 53.144893, 22.793501),
                "S8",
                250,
                0,
                "2",
                new ExtendedMopData()
        );

        List<Mop> mops = new ArrayList<>(Arrays.asList(mop2, mop1));
        //when
        List<MopForm> generatedClosestMopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(mops);
        //then
        assertThat(generatedClosestMopForms.get(0), is(equalTo(mop1)));
    }

    @Test
    public void generateClosestMopFormsGenerator_inputCorrectValues_assertIsNotNull() {
        //given
        prepareRepositoryDataToSetCurrentPositionInGdansk();

        Mop mop1 = new Mop(
                1L,
                "MOP I Jeżewo",
                "MOP I",
                "Jeżewo",
                new Coordinate(1L, 53.135688, 22.750355),
                "S8",
                10,
                0,
                "1",
                new ExtendedMopData()
        );

        Mop mop2 = new Mop(
                2L,
                "MOP II Radule",
                "MOP II",
                "Radule",
                new Coordinate(2L, 53.144893, 22.793501),
                "S8",
                250,
                0,
                "2",
                new ExtendedMopData()
        );

        List<Mop> mops = new ArrayList<>(Arrays.asList(mop1, mop2));
        //when
        List<MopForm> generatedClosestMopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(mops);
        //then
        assertThat(generatedClosestMopForms, is(notNullValue()));
    }

    @Test
    public void generateClosestMopFormsGenerator_inputIncorrectValues_assertIsNull() {
        //given
        List<Mop> mops = null;
        //when
        List<MopForm> generatedClosestMopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(mops);
        //then
        assertThat(generatedClosestMopForms, is(nullValue()));
    }

    @Test
    public void generateClosestMopFormsGenerator_inputEmptyList_assertIsNull() {
        //given
        List<Mop> mops = new ArrayList<>();
        //when
        List<MopForm> generatedClosestMopForms = closestMopFormsGenerator.generateClosestMopFormsGenerator(mops);
        //then
        assertThat(generatedClosestMopForms, is(nullValue()));
    }

    @After
    public void correctRepoData() {
        CurrentPosition.getCurrentPositionInstance().setCurrentX(0.0);
        CurrentPosition.getCurrentPositionInstance().setCurrentY(0.0);
    }

    private void prepareRepositoryDataToSetCurrentPositionInGdansk() {
        CurrentPosition.getCurrentPositionInstance().setCurrentX(54.402997);
        CurrentPosition.getCurrentPositionInstance().setCurrentY(18.565098);
    }
}
