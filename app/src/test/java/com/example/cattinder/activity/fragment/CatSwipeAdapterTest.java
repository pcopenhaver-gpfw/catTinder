package com.example.cattinder.activity.fragment;

import com.example.cattinder.activity.view.CatCardView;
import com.example.cattinder.data.CatServiceResponse;
import com.example.test.RobolectricTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.android.api.Assertions.assertThat;

/**
 * Created by doddy on 2/19/16.
 */
public class CatSwipeAdapterTest extends RobolectricTest {

    private List<CatServiceResponse.Cat> mTestCats;
    private CatSwipeAdapter mCatSwipeAdapter;


    @Before
    public void setup() {
        this.mTestCats = getTestCats();
        mCatSwipeAdapter = prepareAdapter(this.mTestCats);
    }



    @Test
    public void testAdapterReturnsCorrectCount() {

        // Given
        int expectedCount = this.mTestCats.size();

        // When

        // Then
        assertThat(mCatSwipeAdapter).hasCount(expectedCount);
    }


    @Test
    public void testAdapterGetsCorrectItem() {

        // Given
        CatServiceResponse.Cat expectedCat = this.mTestCats.get(2);

        // When

        // Then
        assertThat(mCatSwipeAdapter).hasItem(expectedCat, 2);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testAdapterThrowsExceptionOnRequestBelowBounds() {

        // Given

        // When
        mCatSwipeAdapter.getItem(-1);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void testAdapterThrowsExceptionOnRequestAboveBounds() {

        // Given


        // When
        mCatSwipeAdapter.getItem(this.mTestCats.size());
    }


    @Test
    public void testAdapterReturnCorrectItemId() {

        // Given
        long expectedId = 2;

        // When
        long returnedId = mCatSwipeAdapter.getItemId(2);

        // Then
        Assert.assertEquals(returnedId, expectedId);
    }


    @Test
    public void testAdapterReturnsCorrectView() {

        // Given
        CatServiceResponse.Cat expectedCat = this.mTestCats.get(2);

        // When
        CatCardView returnedView = (CatCardView)mCatSwipeAdapter.getView(2, null, null);
        CatServiceResponse.Cat returnedCat = returnedView.getCat();


        // Then
        Assert.assertEquals(returnedCat, expectedCat);
    }




    private CatSwipeAdapter prepareAdapter(List<CatServiceResponse.Cat> cats) {
        CatSwipeAdapter adapter = new TestCatSwipeAdapter(getApplicationContext());
        adapter.setData(cats);
        return adapter;
    }

    private List<CatServiceResponse.Cat> getTestCats() {

        List<CatServiceResponse.Cat> cats = new ArrayList<>();

        cats.add(new CatServiceResponse.Cat("http://www.dummylink1.com", "Snippet1"));
        cats.add(new CatServiceResponse.Cat("http://www.dummylink2.com", "Snippet2"));
        cats.add(new CatServiceResponse.Cat("http://www.dummylink3.com", "Snippet3"));
        cats.add(new CatServiceResponse.Cat("http://www.dummylink4.com", "Snippet4"));
        cats.add(new CatServiceResponse.Cat("http://www.dummylink5.com", "Snippet5"));

        return cats;
    }
}
