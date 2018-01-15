package com.fuzple.headup;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DigitalClockFragment extends Fragment {

    FusedLocationProviderClient mFusedLocationClient;

    Typeface weatherFont;

    TextView currentTemperatureField;
    TextView weatherIcon;

    Handler handler;

    public DigitalClockFragment() {
        handler = new Handler();
    }

    static int todaydayNum = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    TextView wi, gi;  //위도경도 debug용
    TextView[] dateDay = new TextView[8];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_digital_clock, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();

        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf"); //날씨 아이콘 폰트
        weatherIcon = (TextView) getView().findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);

        //일단 기본 이미지로 배경 초기화
        ImageView iv = (ImageView) getView().findViewById(R.id.background_image);
        GlideApp.with(this).asGif().load(R.drawable.aoi).placeholder(R.drawable.mirei).fitCenter().into(iv);

        //요일 초기화
        new Thread() {
            public void run() {
                handler.post(new Runnable() {
                        public void run() {
                            dateDayClear();
                            dateDay[todaydayNum].setTextColor(Color.parseColor("#ff0000"));
                        }
                });
            }
        }.start();

        //권한 확인
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // 위치 정보 + 날씨 업데이트
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            wi = (TextView) getView().findViewById(R.id.textWi);
                            gi = (TextView) getView().findViewById(R.id.textGyo);
                            wi.setText("위도 : " + location.getLatitude());
                            gi.setText("경도 : " + location.getLongitude());
                            updateWeatherData(location.getLatitude(), location.getLongitude());
                        }
                    }
                });

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (todaydayNum != Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                    todaydayNum = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    new Thread() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    dateDayColor(todaydayNum);
                                    //dateDayClear();
                                    //dateDay[todaydayNum].setTextColor(Color.parseColor("#ff0000"));
                                }
                            });
                        }
                    }.start();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 0, 1000);

    }

    public void updateWeatherData(final double lat, final double lon) {
        new Thread() {
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(lat, lon);
                if (json == null) {

                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json) {
        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");

            currentTemperatureField = (TextView) getView().findViewById(R.id.current_temperature_field);
            currentTemperatureField.setText(
                    String.format("%.2f", main.getDouble("temp")) + " ℃");

            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        } catch (Exception e) {
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset) {
        weatherIcon = (TextView) getView().findViewById(R.id.weather_icon);
        ImageView iv = (ImageView) getView().findViewById(R.id.background_image);

        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            long currentTime = new Date().getTime();
            if (currentTime >= sunrise && currentTime < sunset) {    //낮이면
                icon = this.getString(R.string.weather_sunny);
                GlideApp.with(this).asGif().load(R.drawable.weather_sun).placeholder(R.drawable.mirei).centerCrop().into(iv);
            } else {
                icon = this.getString(R.string.weather_clear_night);    //밤이면
                GlideApp.with(this).asGif().load(R.drawable.rola3).placeholder(R.drawable.mirei).centerCrop().into(iv);
            }
        } else {
            switch (id) {
                case 2:
                    icon = this.getString(R.string.weather_thunder);   //뇌우 Thunderstorm
                    GlideApp.with(this).asGif().load(R.drawable.elza).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
                case 3:
                    icon = this.getString(R.string.weather_drizzle);   //이슬비 Drizzle
                    GlideApp.with(this).asGif().load(R.drawable.rei).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
                case 7:
                    icon = this.getString(R.string.weather_foggy);     //안개 Atmosphere
                    GlideApp.with(this).asGif().load(R.drawable.sumire2).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
                case 8:
                    icon = this.getString(R.string.weather_cloudy);    //구름 낌 Clouds
                    GlideApp.with(this).asGif().load(R.drawable.weather_cloudy).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
                case 6:
                    icon = this.getString(R.string.weather_snowy);     //눈 Snow
                    GlideApp.with(this).asGif().load(R.drawable.weather_snow).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
                case 5:
                    icon = this.getString(R.string.weather_rainy);     //비 Rain
                    GlideApp.with(this).asGif().load(R.drawable.weather_rain).placeholder(R.drawable.mirei).centerCrop().into(iv);
                    break;
            }
        }
        weatherIcon.setText(icon);
    }

    //해당 날짜 색상 변경
    private void dateDayColor(int dayNum) {
        for(int i=1; i<=7; i++)
        {
            dateDay[i].setTextColor(Color.parseColor("#ffffff"));
        }
        dateDay[dayNum].setTextColor(Color.parseColor("#ff0000"));
    }
    private void dateDayClear() {
        for (int i = 1; i <= 7; i++) {
            int resID = getResources().getIdentifier("textDay" + i,
                    "id", "com.fuzple.headup");
            (dateDay[i] = (TextView)getView().findViewById(resID)).setTextColor(Color.parseColor("#ffffff"));
        }
    }
}