package com.aphrodite.obtainstudents.http.impl;

import com.aphrodite.obtainstudents.http.model.BaseRsp;
import com.aphrodite.obtainstudents.http.model.GetStudentsRsp;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Aphrodite on 2017/5/11.
 */

public interface RetrofitAPI {
    /**
     * 获取学员信息
     *
     * @param data
     * @return
     */
    @FormUrlEncoded
    @POST("/sma-upload/getStudentInfo")
    Call<GetStudentsRsp> getStudents(@Field("data") String data);


    /**
     * 单个文件上传
     *
     * @param file
     * @param description
     * @return
     */
    @Multipart
    @POST("/sma-upload/UploadFile")
    Call<BaseRsp> uploadFile(@Part MultipartBody.Part file, @Part("description") RequestBody
            description);

    /**
     * 多文件上传
     *
     * @param file
     * @param description
     * @return
     */
    @Multipart
    @POST("/sma-upload/UploadFile")
    Call<BaseRsp> uploadFiles(@Part List<MultipartBody.Part> file, @Part("description")
            List<RequestBody>
            description);

    /**
     * 下载文件
     *
     * @return
     */
    @GET
    Call<ResponseBody> downloadFiles(@Url String url);
}
