package com.example.cattinder.activity.view;

import com.example.cattinder.data.CatServiceResponse;
import com.example.test.MockPicasso;
import com.example.test.RobolectricTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.widget.TextView;

import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by doddy on 2/19/16.
 */
public class CatCardViewTest extends RobolectricTest {

    private CatServiceResponse.Cat mTestCat;


    @Before
    public void setup() {
        this.mTestCat = getTestCat();
    }



    @Test
    public void testViewReturnsProperCat() {

        // Given
        CatCardView catCardView = new CatCardView(getApplicationContext());
        catCardView.setCat(this.mTestCat);

        // When
        CatServiceResponse.Cat returnedCat = catCardView.getCat();

        // Then
        Assert.assertEquals(returnedCat, this.mTestCat);
    }


    @Test
    public void testCatNameGoesIntoTextView() {

        // Given
        CatCardView catCardView = new CatCardView(getApplicationContext());
        catCardView.setCat(this.mTestCat);

        // When
        TextView textView = catCardView.mCatName;

        // Then
        assertThat(textView).hasText(this.mTestCat.getDescription());
    }


    @Test
    public void testCatUrlGoesIntoImageView() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());
        catCardView.setCat(this.mTestCat);

        // When


        // Then
        verify()
    }


    private CatServiceResponse.Cat getTestCat() {
        return new CatServiceResponse.Cat("http://www.dummylink1.com", "Snippet1");
    }




    private static class TestCatCardView extends CatCardView {

        public TestCatCardView(Context context) {
            super(context);
        }


        @Override
        void inject() {
            this.mPicasso = MockPicasso.create();
        }
    }
}
