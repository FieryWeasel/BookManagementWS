package com.lp.bookmanager.Tools;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by iem on 05/12/14.
 */
public class JSONArrayRequest extends JsonArrayRequest {


    public JSONArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            // solution 1:
            String jsonString = new String(response.data, "UTF-8");
            // solution 2:
//            response.headers.put(HTTP.CONTENT_TYPE,
//                    response.headers.get("content-type"));
//            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }



}
