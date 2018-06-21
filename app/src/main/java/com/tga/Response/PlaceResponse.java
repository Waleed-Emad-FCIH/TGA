package com.tga.Response;

import com.tga.models.place;

/**
 * Created by Mada on 4/29/2018.
 */

public class PlaceResponse {

    private String next_page_token;

    private place[] results;

    public String getNext_page_token() {
        return next_page_token;
    }

    public place[] getResults() {
        return results;
    }
}
