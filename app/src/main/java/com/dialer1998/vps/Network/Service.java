package com.dialer1998.vps.Network;


import com.dialer1998.vps.Model.FirPostResponseObject;
import com.dialer1998.vps.Model.GetPolicePhn;
import com.dialer1998.vps.Model.StatusResponse;
import com.dialer1998.vps.Model.TeacherInstitutesResponse;
import com.dialer1998.vps.Model.getQuestionResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {
    @GET("api/v1/institute/TeacherInstitute/")
    Call <TeacherInstitutesResponse> getAllintitute(@Query("teacherId") String teacherId);

    @GET("police")
    Call <GetPolicePhn> getpolice(@Query("lat") String lat, @Query("long") String lon);

    @POST("FIR/")
    @FormUrlEncoded
    Call <FirPostResponseObject> savePost(@Field("name_of_victim") String title,
                                                  @Field("type_of_crime") String typeofCrime,
                                                  @Field("datetime_of_crime") String date,
                                                  @Field ( "description_of_incidence" ) String des

    );
    @PUT("FIR/{id}/")
    @FormUrlEncoded
    Call <FirPostResponseObject> saveFirPost(
                                            @Path ( "id" )Integer id,
                                            @Field("description_of_incidence") String desc,
                                             @Field("location_of_crime") String location_of_crime,
                                              @Field("witness_name") String witness_name,
                                             @Field ( "witness_aadhar" ) String witness_aadhar,
                                              @Field ( "identified_accused" ) String identified_accused
    );


    @GET("question/")

    Call <getQuestionResponse> saveFirDesc(
            @Query ( "id" ) Integer id
    );

    @GET("FIR/{id}/")
    Call <StatusResponse> savestatusPost(
            @Path ( "id" )Integer id
    );
}
