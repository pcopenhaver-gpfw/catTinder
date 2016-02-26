package com.example.cattinder.activity.view;

import com.example.cattinder.data.CatServiceResponse;
import com.example.test.MockPicasso;
import com.example.test.RobolectricTest;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import rx.exceptions.OnErrorNotImplementedException;

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
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());
        catCardView.setCat(this.mTestCat);

        // When
        CatServiceResponse.Cat returnedCat = catCardView.getCat();

        // Then
        Assert.assertEquals(returnedCat, this.mTestCat);
    }


    @Test
    public void testCatNameGoesIntoTextView() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);
        TextView textView = catCardView.mCatName;

        // Then
        assertThat(textView).hasText(this.mTestCat.getDescription());
    }


    @Test
    public void testCatUrlGoesIntoImageView() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);


        // Then
        verify(catCardView.mPicasso).load(this.mTestCat.getImageUri());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentExceptionOnNullCat() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(null);
    }


    @Test
    public void testLikeSetsAlphaValues() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());
        View likeView = catCardView.mYesView;
        View dislikeView = catCardView.mNoView;

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.likeCat(.5f);

        // Then
        assertThat(likeView).hasAlpha(.5f);
        assertThat(dislikeView).hasAlpha(0f);
    }


    @Test
    public void testDislikeSetsAlphaValues() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());
        View likeView = catCardView.mYesView;
        View dislikeView = catCardView.mNoView;

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.dislikeCat(.5f);

        // Then
        assertThat(likeView).hasAlpha(0f);
        assertThat(dislikeView).hasAlpha(.5f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenLikePercentageOutOfRangeLow() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.likeCat(-.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenLikePercentageOutOfRangeHigh() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.likeCat(1.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenDiskikePercentageOutOfRangeLow() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.dislikeCat(-.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenDisikePercentageOutOfRangeHigh() {

        // Given
        TestCatCardView catCardView = new TestCatCardView(getApplicationContext());

        // When
        catCardView.setCat(this.mTestCat);
        catCardView.dislikeCat(1.1f);
    }


    private CatServiceResponse.Cat getTestCat() {
        return new CatServiceResponse.Cat("http://www.dummylink1.com", "Snippet1");
    }




}
