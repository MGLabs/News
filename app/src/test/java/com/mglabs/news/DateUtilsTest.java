package com.mglabs.news;

import com.mglabs.news.utils.DateUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mglabs on 30/01/18.
 */

public class DateUtilsTest {
     String correctInputDate = "2016-07-25T09:56:27Z";
     String correctOutputDate = "Mon, 25 Jul 2016 09:56";

     @Test
     public void formatNewsApiDate_correctDate_outputsCorrectDate() {
         String outputDate = DateUtils.formatNewsApiDate(correctInputDate);
         assertEquals(outputDate, correctOutputDate);
     }

     @Test
     public void formatNewsApiDate_nullInput_outputsNull() {
         String outputDate = DateUtils.formatNewsApiDate(null);
         assertEquals(outputDate, null);
     }
}
