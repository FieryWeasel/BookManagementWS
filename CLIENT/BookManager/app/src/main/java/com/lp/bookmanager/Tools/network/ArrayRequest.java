package com.lp.bookmanager.tools.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by iem on 11/03/15.
 */
public class ArrayRequest extends Request<JSONArray> {
    private Response.Listener<JSONArray> listener;
    private Map<String, String> params;

    public ArrayRequest(String url, Response.ErrorListener listener, Response.Listener<JSONArray> responseListener, Map<String, String> params) {
        super(url, listener);
        this.listener = responseListener;
        this.params = params;
    }

    public ArrayRequest(int method, String url, Map<String, String> params, Response.Listener<JSONArray> responseListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.listener = responseListener;
        this.params = params;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONArray(jsonString), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONArray jsonArray) {
        listener.onResponse(jsonArray);
    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    };

}
