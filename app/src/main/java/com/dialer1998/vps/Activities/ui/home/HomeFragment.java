package com.dialer1998.vps.Activities.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dialer1998.vps.Activities.Document_Activity;
import com.dialer1998.vps.Activities.FIR_Activity;
import com.dialer1998.vps.Activities.Status;
import com.dialer1998.vps.Model.GetPolicePhn;
import com.dialer1998.vps.Network.Instance;
import com.dialer1998.vps.Network.Service;
import com.dialer1998.vps.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class HomeFragment extends Fragment implements TextToSpeech.OnInitListener, LocationListener {


    FloatingActionButton emer;
    AnimationDrawable policeanim;
    ImageView imageView;
    TextToSpeech textToSpeech;
    CardView Fir, Doc, Stat;
    Service apiInterface;
    TextView talk;
    String voice = " Hello Welcome to Virtual Police Station please select any option given below ";
    private LocationManager locationManager;
    private String provider;
    String name = null;
    String Phnum = "9198911007";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate ( R.layout.fragment_home, container, false );

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated ( savedInstanceState );
        emer = getView ().findViewById ( R.id.emer );
        Fir = getView ().findViewById ( R.id.CardFir );
        Doc = getView ().findViewById ( R.id.CardDocVerify );
        Stat = getView ().findViewById ( R.id.cardStatus );
        talk = getView ().findViewById ( R.id.voice );
        imageView = getView ().findViewById ( R.id.Image );
        textToSpeech = new TextToSpeech ( getContext (), this );
        imageView.setBackgroundResource ( R.drawable.animation );
        policeanim = (AnimationDrawable) imageView.getBackground ();

        policeanim.start ();
        texttoSpeak ();
        setHasOptionsMenu ( true );

        locationManager = (LocationManager) getActivity ().getSystemService ( Context.LOCATION_SERVICE );
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria ();
        provider = locationManager.getBestProvider ( criteria, false );

        if (checkSelfPermission ( getActivity (), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission ( getContext (), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {


        }
        Location location = locationManager.getLastKnownLocation ( provider );

        if (location != null) {
            System.out.println ( "Provider " + provider + " has been selected." );
            onLocationChanged ( location );
        } else {

        }


    }

    /* Request updates at startup */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume ();
        if (checkSelfPermission ( getContext (), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && checkSelfPermission ( getContext (), Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates ( provider, 400, 1, this );

        emer.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                emer.setBackgroundColor ( getResources ().getColor ( R.color.colorPrimary ) );
                Log.d ( "number is ", "call at :-" + Phnum );
                final SweetAlertDialog pDialog = new SweetAlertDialog ( getContext (), SweetAlertDialog.WARNING_TYPE );
                pDialog.getProgressHelper ().setBarColor ( Color.parseColor ( "#ce0018" ) );
                pDialog.setTitleText ( "Attention! We are transfer your call to your nearest police Station " + name );

                pDialog.setCancelable ( true );
                pDialog.setConfirmButton ( "Sure", new SweetAlertDialog.OnSweetClickListener () {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent call = new Intent ( Intent.ACTION_CALL );

                        if (Phnum.trim ().isEmpty ()) {
                            Toast.makeText ( getContext (), "Please enter the correct mobile number", Toast.LENGTH_LONG ).show ();

                        } else {
                            if (!Phnum.contains ( "#" ))
                                call.setData ( Uri.parse ( "tel:" + Phnum ) );
                            else
                                call.setData ( Uri.parse ( "tel:" + Uri.encode ( Phnum ) ) );

                        }
                        if (ActivityCompat.checkSelfPermission ( getContext (), Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText ( getContext (), "kindly grant permission for calling", Toast.LENGTH_LONG ).show ();
                            requestpermission ();
                        }

                        if (!Phnum.trim ().isEmpty ()) {

                            startActivity ( call );
                            Toast.makeText ( getContext (), "calling " + name, Toast.LENGTH_SHORT ).show ();

                        }
                    }
                } );
                pDialog.setCancelButton ( "no", new SweetAlertDialog.OnSweetClickListener () {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.dismiss ();
                    }
                } );
                pDialog.show ();
            }
        } );

        Fir.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), FIR_Activity.class );
                startActivity ( i );

            }
        } );
        Doc.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent i = new Intent ( getActivity (), Document_Activity.class );
                startActivity ( i );

            }
        } );
        Stat.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getActivity (), Status.class );
                startActivity ( i );

            }
        } );

    }

    public void getPolice(String lon, String lat) {
        apiInterface = Instance.getRetrofit ().create ( Service.class );
        Call <GetPolicePhn> call = apiInterface.getpolice ( lon, lat );
        call.enqueue ( new Callback <GetPolicePhn> () {

            @Override
            public void onResponse(Call <GetPolicePhn> call, Response <GetPolicePhn> response) {
                if (response.isSuccessful ()) {
                    Log.d ( "repo", "success" + response.body ().getPhoneNo () );

                    Phnum = response.body ().getPhoneNo ();
                    name = response.body ().getStationName ();

//                    Toast.makeText ( getContext (), name + Phnum, Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call <GetPolicePhn> call, Throwable t) {
                Log.d ( "repo", "failed " + t.getMessage () );

            }
        } );
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//      inflater.inflate ( R.menu.home_tool, menu );
//        super.onCreateOptionsMenu(menu, inflater);
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int k = item.getItemId ();
//        if (k == R.id.logout) {
//
//            FirebaseAuth.getInstance ().signOut ();
//            long getCurrentTimeInMilSec = System.currentTimeMillis ();
//            long setEndTime = getCurrentTimeInMilSec + 1000;
//            while (setEndTime > System.currentTimeMillis ()) {
//            }
//            //   FirebaseUser user = firebaseAuth.getCurrentUser();
//            //  Log.d ("tag","current user in  "+ user);
//            Intent I = new Intent ( getContext (), Login.class );
//            //  if(user==null)
//            startActivity ( I );
//        }
//        return true;
//    }

    public void texttoSpeak() {


        String text = voice;
        if ("".equals ( text )) {
            text = "Please enter some text to speak.";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak ( text, TextToSpeech.QUEUE_FLUSH, null, null );
            talk.setText ( voice );
        } else {
            textToSpeech.speak ( text, TextToSpeech.QUEUE_FLUSH, null );
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage ( Locale.getDefault () );
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e ( "error", "This Language is not supported" );
            } else {
                texttoSpeak ();


            }
        } else {
            Log.e ( "error", "Failed to Initialize" );
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        float lat = (float) (location.getLatitude ());
        float lng = (float) (location.getLongitude ());
        //   getPolice ( (String.valueOf ( lat )), (String.valueOf ( lng )) );
//        Toast.makeText ( this, "latitude"+        (String.valueOf(lat))+"longitude"+(String.valueOf(lng))
//                , Toast.LENGTH_SHORT ).show ();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText ( getContext (), "Enabled new provider " + provider,
                Toast.LENGTH_SHORT ).show ();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText ( getContext (), "Disabled provider " + provider,
                Toast.LENGTH_SHORT ).show ();
    }

    private void requestpermission() {
        ActivityCompat.requestPermissions ( getActivity (), new String[]{Manifest.permission.CALL_PHONE}, 1 );
    }

//    @Override
//    public void onBackPressed() {
//
//        Intent intent = new Intent ( Intent.ACTION_MAIN );
//        intent.addCategory ( Intent.CATEGORY_HOME );
//        startActivity ( intent );
//        finish ();
//        System.exit ( 0 );
//
//    }

}