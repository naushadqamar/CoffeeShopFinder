package com.mint.coffeeshops.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public interface MainController {

  void getVenuesData(String area, final List venuesList, final RecyclerView.Adapter venuesRecyclerAdapter, Context mainContext);

  void getVenuesData(double lat,double lon, final List venuesList, final RecyclerView.Adapter venuesRecyclerAdapter, Context mainContext);
}
