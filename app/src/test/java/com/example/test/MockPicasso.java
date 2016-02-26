package com.example.test;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.mockito.Mockito;

import android.net.Uri;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
/**
 * Created by doddy on 2/26/16.
 */
public class MockPicasso {

    public static Picasso create() {
        Picasso picasso = Mockito.mock(Picasso.class);
        mockLoadMethod(picasso);
        return picasso;
    }

    static void mockLoadMethod(Picasso picasso) {
        RequestCreator requestCreator = Mockito.mock(RequestCreator.class);
        doReturn(requestCreator).when(picasso).load(any(File.class));
        doReturn(requestCreator).when(picasso).load(any(Uri.class));
        doReturn(requestCreator).when(picasso).load(any(String.class));
        doReturn(requestCreator).when(picasso).load(any(Integer.class));
    }
}