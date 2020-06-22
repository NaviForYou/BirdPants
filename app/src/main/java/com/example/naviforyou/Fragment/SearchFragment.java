package com.example.naviforyou.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.naviforyou.Activity.MainActivity;
import com.example.naviforyou.Activity.RouteMenuActivity;
import com.example.naviforyou.Activity.SearchActivity;
import com.example.naviforyou.Activity.SubwayActivity;
import com.example.naviforyou.DB.DataBase.AppDatabase_Runway;
import com.example.naviforyou.DB.Data.Runway;
import com.example.naviforyou.DB.DataBase.RunwayDao;
import com.example.naviforyou.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SearchFragment extends Fragment {

    static boolean isCategory = false;
    RelativeLayout search_layout;
    ImageView find;
    ImageView gps;
    ImageView subway;
    ImageView category;
    ViewGroup layout;

    AppDatabase_Runway db_runway;
    NaverMap naverMap;
    List<PathOverlay> pathOverlays;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        layout = (ViewGroup) inflater.inflate(R.layout.fragment_search, container, false);

        db_runway = MainActivity.db_runway;
        naverMap = MainActivity.naverMap;

        search_layout = layout.findViewById(R.id.search_layout);
        search_layout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("type","main");
            startActivity(intent);
        });

        find = layout.findViewById(R.id.find);
        find.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RouteMenuActivity.class);
            intent.putExtra("type","main");
            startActivity(intent);
        });

        NaverMap naverMap = MainActivity.naverMap;
        gps = layout.findViewById(R.id.gps);
        gps.setOnClickListener(v -> {
            if (naverMap.getLocationTrackingMode() == LocationTrackingMode.NoFollow) {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
            else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);
            }
        });

        category = layout.findViewById(R.id.category);
        category.setOnClickListener(v -> {
            ExecutorService execService = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            Log.d("TEXT","PolyLine");
            execService.execute(() -> {
                if(!isCategory) {
                    try {
                        List<Runway> runways = new RunWayGet(db_runway.runwayDao()).execute().get();
                        pathOverlays = new ArrayList<>();

                        for (int i = 0; i < runways.size(); i++) {
                            Runway runway = runways.get(i);
                            PathOverlay pathOverlay = new PathOverlay();
                            pathOverlay.setCoords(Arrays.asList(
                                    new LatLng(runway.getStartX(), runway.getStartY()),
                                    new LatLng(runway.getEndX(), runway.getEndY())
                            ));
                            pathOverlay.setColor(Color.RED);
                            pathOverlay.setWidth(20);
                            pathOverlays.add(pathOverlay);
                        }

                        handler.post(() -> {
                            for (PathOverlay pathOverlay : pathOverlays) {
                                pathOverlay.setMap(naverMap);
                            }
                        });

                        isCategory=true;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    handler.post(() -> {
                        for (PathOverlay pathOverlay : pathOverlays) {
                            pathOverlay.setMap(null);
                        }
                    });
                    isCategory=false;
                }



            });
        });

        subway = layout.findViewById(R.id.subway);
        subway.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SubwayActivity.class);
            startActivity(intent);
        });

        return layout;
    }

    private static class RunWayGet extends AsyncTask<Void, Void, List<Runway>>{
        private RunwayDao runwayDao;

        public RunWayGet(RunwayDao runwayDao) { this.runwayDao = runwayDao; }
        @Override
        protected List<Runway> doInBackground(Void... voids) {
            List<Runway> runways = runwayDao.getAll();
            return runways;
        }

        @Override
        protected void onPostExecute(List<Runway> runways) {
            super.onPostExecute(runways);
            Log.d("TEXT","Runway" + runways.toString());
        }
    }

}
