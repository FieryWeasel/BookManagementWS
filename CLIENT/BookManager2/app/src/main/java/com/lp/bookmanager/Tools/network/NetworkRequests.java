package com.lp.bookmanager.tools.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lp.bookmanager.model.Author;
import com.lp.bookmanager.model.Book;
import com.lp.bookmanager.model.Review;
import com.lp.bookmanager.model.User;
import com.lp.bookmanager.tools.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iem on 03/03/15.
 */
public class NetworkRequests {

    /*****************************************************************************/
    /**                                                                         **/
    /**                               CONNECTION                                **/
    /**                                                                         **/
    /*****************************************************************************/

    public static void connect(Context context, String key, final ConnectionListener listener){

        Map<String, String> params = new HashMap<String, String>();
        params.put("crypted_key", key);

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_AUTH + "user/canConnect", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onConnectionSucceeded(response.getString("id"));
                            }else {
                                listener.onConnectionFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onConnectionFailed();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
        });
        addRequestToQueue(context, request);
    }
    /*****************************************************************************/
    /**                                                                         **/
    /**                                CREATION                                 **/
    /**                                                                         **/
    /*****************************************************************************/


    public static void createReview(Context context, Review review, final ReviewCreatedListener listener){
        Map<String, String> params = new HashMap<String, String>();

        params.put("user_id", Integer.toString(review.getUser_id()));
        params.put("book_id", review.getBook_id());
        params.put("title", review.getTitle());
        params.put("comment", review.getComment());

        params.put("mark", Integer.toString(review.getMark()));

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "review/add", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onReviewCreatedSuccessful();
                            }else {
                                listener.onReviewCreatedFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onReviewCreatedFailed();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void createUser(Context context, User user, final UserCreatedListener listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("first_name", user.getFirst_name());
        params.put("last_name", user.getLast_name());
        params.put("nickname", user.getNickname());
        params.put("birth_date", user.getBirth_date());
        params.put("email", user.getMail());
        params.put("crypted_key", user.getCrypted_key());

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "user/add", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onUserCreatedSuccessful();
                            }else {
                                listener.onUserCreatedFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onUserCreatedFailed();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void createAuthor(Context context, Author author, final AuthorCreatedListener listener){
        Map<String, String> params = new HashMap<>();
        params.put("first_name", author.getFirst_name());
        params.put("last_name", author.getLast_name());

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "author/add", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onAuthorCreatedSuccessful(response.getString("id"));
                            }else {
                                listener.onAuthorCreatedFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onAuthorCreatedFailed();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void createBook(Context context, Book book, final BookrCreatedListener listener){
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", book.getTitle());
        params.put("isbn", book.getIsbn());
        params.put("type_id", Integer.toString(book.getType_id()));
        params.put("author_id", Integer.toString(book.getAuthor_id()));
        params.put("summary", book.getSummary());

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "book/add", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onBookCreatedSuccessful();
                            }else {
                                listener.onBookCreatedFailed();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onBookCreatedFailed();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    /*****************************************************************************/
    /**                                                                         **/
    /**                                   GET                                   **/
    /**                                                                         **/
    /*****************************************************************************/


    public static void getUSerInfo(Context context, String userId, final UserInfoListener listener){

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", userId);

        ObjectRequest request = new ObjectRequest(Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "user/get", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onUserInfoCorrectlyRetrieved(response.getString("users"));
                            }else {
                                listener.onFailToRetrieveUserInfo();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveUserInfo();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });

        addRequestToQueue(context, request);
    }

    public static void getBookList(Context context, final BookListListener listener) {
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "book/get", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onBookListRetrieved(response.getString("books"));
                            }else {
                                listener.onFailToRetrieveBookList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveBookList();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void getAuthorList(Context context, final AuthorListListener listener) {
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "author/get", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onAuthorListCorrectlyRetrieved(response.getString("authors"));
                            }else {
                                listener.onFailToRetrieveAuthorList();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveAuthorList();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void getReviewList(Context context, final ReviewListListener listener) {
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "review/get", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onReviewListCorrectlyRetrieved(response.getString("reviews"));
                            }else {
                                listener.onFailToRetrieveReviews();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveReviews();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void getReviewListById(Context context, String id, final ReviewListListener listener) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("book_id", id);
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "review/get", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onReviewListCorrectlyRetrieved(response.getString("reviews"));
                            }else {
                                listener.onFailToRetrieveReviews();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveReviews();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    public static void getUSerList(Context context, final UserListListener listener) {
        ObjectRequest request = new ObjectRequest( Constants.METHOD,
                Constants.URL_BASE + Constants.URL_MNG + "user/get", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("response").equalsIgnoreCase("YES")){
                                listener.onUserListCorrectlyRetrieved(response.getString("users"));
                            }else {
                                listener.onFailToRetrieveUsers();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailToRetrieveUsers();
                        error.printStackTrace();
                        Log.d("ERROR_RESPONSE", error.toString());
                    }
                });
        addRequestToQueue(context, request);
    }

    private static void addRequestToQueue(Context context,  Request request){
        NetworkData.getInstance().addToRequestQueue(context, request);
    }

    /*****************************************************************************/
    /**                                                                         **/
    /**                            GET LISTENERS                                **/
    /**                                                                         **/
    /*****************************************************************************/

    public interface ConnectionListener{
        public void onConnectionSucceeded(String userId);
        public void onConnectionFailed();
    }

    public interface UserInfoListener{
        public void onUserInfoCorrectlyRetrieved(String jsonUser);
        public void onFailToRetrieveUserInfo();
    }

    public interface BookListListener{
        public void onBookListRetrieved(String jsonBooks);
        public void onFailToRetrieveBookList();
    }

    public interface AuthorListListener{
        public void onAuthorListCorrectlyRetrieved(String jsonAuthor);
        public void onFailToRetrieveAuthorList();
    }

    public interface ReviewListListener{
        public void onReviewListCorrectlyRetrieved(String jsonReview);
        public void onFailToRetrieveReviews();
    }

    public interface UserListListener{
        public void onUserListCorrectlyRetrieved(String jsonReview);
        public void onFailToRetrieveUsers();
    }

    /*****************************************************************************/
    /**                                                                         **/
    /**                       CREATION LISTENERS                                **/
    /**                                                                         **/
    /*****************************************************************************/


    public interface UserCreatedListener{
        public void onUserCreatedSuccessful();
        public void onUserCreatedFailed();
    }

    public interface ReviewCreatedListener{
        public void onReviewCreatedSuccessful();
        public void onReviewCreatedFailed();
    }

    public interface AuthorCreatedListener{
        public void onAuthorCreatedSuccessful(String id);
        public void onAuthorCreatedFailed();
    }

    public interface BookrCreatedListener{
        public void onBookCreatedSuccessful();
        public void onBookCreatedFailed();
    }
}
