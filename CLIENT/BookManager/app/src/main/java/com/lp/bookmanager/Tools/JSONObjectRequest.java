package com.lp.bookmanager.Tools;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by iem on 05/12/14.
 */
public class JSONObjectRequest extends JsonRequest<JSONObject> {

    public JSONObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            // solution 1:
            String jsonString = new String(response.data, "UTF-8");
            // solution 2:
//            response.headers.put(HTTP.CONTENT_TYPE,
//                    response.headers.get("content-type"));
//            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            //
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
