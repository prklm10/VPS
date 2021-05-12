package com.dialer1998.vps.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dialer1998.vps.Model.GetPolicePhn;
import com.dialer1998.vps.Network.Instance;
import com.dialer1998.vps.Network.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public Repository() {
    }

    private static Repository instance = null;
    public Service service = Instance.getRetrofit ().create ( Service.class );
    public static MutableLiveData <GetPolicePhn> numberApiAns = new MutableLiveData <> ();

//    public void getpolice(String lat, String longi) {
//        Call <GetPolicePhn> call = service.getpolicePhn ( lat, longi );
//        call.enqueue ( new Callback <GetPolicePhn> () {
//            @Override
//            public void onResponse(Call <GetPolicePhn> call, Response <GetPolicePhn> response) {
//                if (response.isSuccessful ()) {
//                    Log.d ( "Tag", "Response is success" + response );
//
//                    numberApiAns.setValue ( response.body () );
//                } else {
//                    Log.d ( "Tag", "Response is unsuccess" + response.code () );
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call <GetPolicePhn> call, Throwable t) {
//                Log.d ( "Tag", "Response is unsuccessfull" );
//            }
//        } );
//    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository ();
        }
        return instance;

    }
}