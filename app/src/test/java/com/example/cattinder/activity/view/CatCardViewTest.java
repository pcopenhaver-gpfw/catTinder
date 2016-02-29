package com.example.cattinder.activity.view;

import com.example.cattinder.data.CatServiceResponse;
import com.example.test.MockPicasso;
import com.example.test.RobolectricTest;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import android.view.View;
import android.widget.TextView;

import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

/**
 * Created by doddy on 2/19/16.
 */
public class CatCardViewTest extends RobolectricTest {

    private CatServiceResponse.Cat mTestCat;
    private Picasso mPicasso;
    private CatCardView mCatCardView;


    @Before
    public void setup() {
        this.mTestCat = getTestCat();
        this.mPicasso = MockPicasso.create();
        this.mCatCardView = new CatCardView(getApplicationContext(), mPicasso);
        mCatCardView.setCat(this.mTestCat);
    }



    @Test
    public void testViewReturnsProperCat() {

        // Given

        // When
        CatServiceResponse.Cat returnedCat = mCatCardView.getCat();

        // Then
        Assert.assertEquals(returnedCat, this.mTestCat);
    }


    @Test
    public void testCatNameGoesIntoTextView() {

        // Given

        // When
        TextView textView = mCatCardView.mCatName;

        // Then
        assertThat(textView).hasText(this.mTestCat.getDescription());
    }


    @Test
    public void testCatUrlGoesIntoImageView() {

        // Given

        // When


        // Then
        verify(this.mPicasso).load(this.mTestCat.getImageUri());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsIllegalArgumentExceptionOnNullCat() {

        // Given

        // When
        this.mCatCardView.setCat(null);
    }


    @Test
    public void testLikeSetsAlphaValues() {

        // Given
        View likeView = this.mCatCardView.mYesView;
        View dislikeView = this.mCatCardView.mNoView;

        // When
        this.mCatCardView.setCat(this.mTestCat);
        this.mCatCardView.likeCat(.5f);

        // Then
        assertThat(likeView).hasAlpha(.5f);
        assertThat(dislikeView).hasAlpha(0f);
    }


    @Test
    public void testDislikeSetsAlphaValues() {

        // Given
        View likeView = this.mCatCardView.mYesView;
        View dislikeView = this.mCatCardView.mNoView;

        // When
        this.mCatCardView.setCat(this.mTestCat);
        this.mCatCardView.dislikeCat(.5f);

        // Then
        assertThat(likeView).hasAlpha(0f);
        assertThat(dislikeView).hasAlpha(.5f);
    }


    @Test
    public void testSetCatResetsLikeAlpha() {

        // Given
        View likeView = this.mCatCardView.mYesView;
        View dislikeView = this.mCatCardView.mNoView;

        this.mCatCardView.setCat(this.mTestCat);
        this.mCatCardView.likeCat(.5f);

        // When
        this.mCatCardView.setCat(this.mTestCat);

        // Then
        assertThat(likeView).hasAlpha(0f);
        assertThat(dislikeView).hasAlpha(.0f);
    }


    @Test
    public void testSetCatResetsDislikeAlpha() {

        // Given
        View likeView = this.mCatCardView.mYesView;
        View dislikeView = this.mCatCardView.mNoView;

        this.mCatCardView.setCat(this.mTestCat);
        this.mCatCardView.dislikeCat(.5f);

        // When
        this.mCatCardView.setCat(this.mTestCat);

        // Then
        assertThat(likeView).hasAlpha(0f);
        assertThat(dislikeView).hasAlpha(.0f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenLikePercentageOutOfRangeLow() {

        // Given

        // When
        this.mCatCardView.likeCat(-.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenLikePercentageOutOfRangeHigh() {

        // Given

        // When
        this.mCatCardView.likeCat(1.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenDiskikePercentageOutOfRangeLow() {

        // Given

        // When
        this.mCatCardView.dislikeCat(-.1f);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionWhenDisikePercentageOutOfRangeHigh() {

        // Given

        // When
        this.mCatCardView.dislikeCat(1.1f);
    }


    private CatServiceResponse.Cat getTestCat() {
        return new CatServiceResponse.Cat("http://www.dummylink1.com", "Snippet1");
    }
}
