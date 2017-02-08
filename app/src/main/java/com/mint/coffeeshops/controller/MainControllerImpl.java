package com.mint.coffeeshops.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.mint.coffeeshops.R;
import com.mint.coffeeshops.model.CoffeeShop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.VolleyLog.TAG;

public class MainControllerImpl implements MainController {

    private ProgressDialog loadingDialog;

    public void getVenuesData(String area, final List venuesList, final RecyclerView.Adapter venuesRecyclerAdapter, Context mainContext) {

    }

    @Override
    public void getVenuesData(double lat, double lon, final List venuesList, final RecyclerView.Adapter venuesRecyclerAdapter, Context mainContext) {
        final Context context = mainContext;

        loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage(context.getResources().getString(R.string.venue_search_loading));
        loadingDialog.setIndeterminate(false);
        loadingDialog.setCancelable(false);
        loadingDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String foursquareRequestUrl = null;
        //TODO:put url , secret key and client id in variable.
        foursquareRequestUrl = "https://api.foursquare.com/v2/venues/explore?client_id=HJXGIXFJMOSULTRIMGNIB2NB4LBWZMG2XJYN5ZSDGKNHPNOZ&client_secret=2ZVLMDEPRJNW4FYLC5RTLZRFEKVVRX4DNDZMUTS01K0XAQZG&v=20130815%20&ll=" + lat + "," + lon + "&categoryId=4bf58dd8d48988d1e0931735&radius=114500";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, foursquareRequestUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        loadingDialog.dismiss();
                        try {
                            venuesList.clear();
                            JSONArray items = response.getJSONObject("response").getJSONArray("groups").getJSONObject(0).getJSONArray("items");
                            Log.d("response", "response : " + response);
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject venuesJson = items.getJSONObject(i).getJSONObject("venue");
                                JSONObject locationJsonObject = venuesJson.getJSONObject("location");
                                String url = "";
                                if (venuesJson.has("url")) {

                                    url = Uri.encode(venuesJson.getString("url"), "@#&=*+-_.,:!?()/~'%");
                                }
                                venuesList.add(new CoffeeShop(
                                        venuesJson.getString("name"),
                                        venuesJson.getString("rating"),
                                        venuesJson.getJSONArray("categories"),
                                        locationJsonObject.getString("address"),
                                        locationJsonObject.getString("distance"),
                                        url
                                ));
                            }
                        } catch (JSONException e) {
                            venuesList.clear();
                            Toast.makeText(context, context.getString(R.string.venue_search_invalid_error), Toast.LENGTH_SHORT).show();
                        }
                        venuesRecyclerAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                            loadingDialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.no_inernet_connection), Toast.LENGTH_SHORT).show();
                        } else {
                            loadingDialog.dismiss();
                            Toast.makeText(context, context.getString(R.string.venue_search_loading_error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsObjRequest);
    }

}
