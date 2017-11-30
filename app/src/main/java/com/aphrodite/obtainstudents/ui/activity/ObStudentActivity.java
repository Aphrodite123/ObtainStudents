package com.aphrodite.obtainstudents.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphrodite.obtainstudents.R;
import com.aphrodite.obtainstudents.config.BaseConfig;
import com.aphrodite.obtainstudents.config.IntentAction;
import com.aphrodite.obtainstudents.config.MessageType;
import com.aphrodite.obtainstudents.http.impl.RequestManager;
import com.aphrodite.obtainstudents.http.impl.RetrofitAPIManager;
import com.aphrodite.obtainstudents.http.model.BaseRsp;
import com.aphrodite.obtainstudents.http.model.GetStudentsRsp;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ObStudentActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = ObStudentActivity.class.getSimpleName();

    @BindView(R.id.obtain_students)
    Button mObtainStudent;
    @BindView(R.id.request_get)
    Button mGetReqBtn;
    @BindView(R.id.request_post)
    Button mPostReqBtn;
    @BindView(R.id.upload_file)
    Button mUploadFile;
    @BindView(R.id.download_file)
    Button mDownloadFile;
    @BindView(R.id.download_file_break_point)
    Button mDownloadBreakpoint;
    @BindView(R.id.result_info)
    TextView mResult;
    @BindView(R.id.download_image)
    ImageView mDownloadImg;

    private OkHttpClient mClient;
    private Request mRequest;

    private Map<String, String> map = new HashMap<String, String>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessageType.DownloadFileMessage.DOWNLOAD_SUCCESS:
                    byte[] bytes = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    mDownloadImg.setImageBitmap(bitmap);
                    break;
                case MessageType.DownloadFileMessage.DOWNLOAD_FAILED:
                    Log.d(TAG, "Enter handleMessage method,info: Download image failed!");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    private void initView() {
//        mObtainStudent = (Button) findViewById(R.id.obtain_students);
//        mGetReqBtn = (Button) findViewById(R.id.request_get);
//        mPostReqBtn = (Button) findViewById(R.id.request_post);
//        mUploadFile = (Button) findViewById(R.id.upload_file);
//        mDownloadFile = (Button) findViewById(R.id.download_file);
//        mResult = (TextView) findViewById(R.id.result_info);
//        mDownloadImg = (ImageView) findViewById(R.id.download_image);

        mObtainStudent.setOnClickListener(this);
        mGetReqBtn.setOnClickListener(this);
        mPostReqBtn.setOnClickListener(this);
        mUploadFile.setOnClickListener(this);
        mDownloadFile.setOnClickListener(this);
        mDownloadBreakpoint.setOnClickListener(this);
    }

    private void initData() {
        mClient = new OkHttpClient();
        mRequest = new Request.Builder().get().tag(this).url(BaseConfig.sQueryStudentUrl).build();
    }

    private void getStudents() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = mClient.newCall(mRequest).execute();
                    if (response.isSuccessful()) {
                        Log.d(TAG, "Enter getStudents method,response: "
                                + response.body().string());
                        mResult.setText(response.body().string());
                    }
                } catch (IOException e) {
                    Log.d(TAG, "Enter getStudents method,IOException: " + e);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.obtain_students:
                getStudents();
                mDownloadImg.setVisibility(View.GONE);
                mResult.setVisibility(View.VISIBLE);
                break;
            case R.id.request_get:
                sendGetRequest();
                mDownloadImg.setVisibility(View.GONE);
                mResult.setVisibility(View.VISIBLE);
                break;
            case R.id.request_post:
                sendPostRequest(new RequestManager.OnResponseListener<GetStudentsRsp>() {
                    @Override
                    public void onSuccess(GetStudentsRsp respone) {
                        List<GetStudentsRsp.Data> datas = new ArrayList<GetStudentsRsp.Data>();
                        datas = respone.data;
                        StringBuffer buffer = new StringBuffer();
                        if (null != datas && datas.size() > 0) {
                            for (GetStudentsRsp.Data data : datas) {
                                buffer.append(data.toString() + "\n");
                            }
                        }
                        Log.d(TAG, "Enter sendPostRequest method and into onSuccess,Students: " +
                                buffer.toString());
                        mResult.setText(buffer.toString());
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "Enter sendPostRequest method and into onFailure,Throwable: " +
                                t);
                    }
                });
                mDownloadImg.setVisibility(View.GONE);
                mResult.setVisibility(View.VISIBLE);
                break;
            case R.id.upload_file:
                sendPostFile(new RequestManager.OnResponseListener<BaseRsp>() {
                    @Override
                    public void onSuccess(BaseRsp respone) {
                        Log.d(TAG, "Enter sendPostFile method and into onSuccess,respone: " +
                                respone);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, "Enter sendPostFile method and into onFailure,Throwable: " +
                                t);
                    }
                });
                mDownloadImg.setVisibility(View.GONE);
                mResult.setVisibility(View.VISIBLE);
                break;
            case R.id.download_file:
                downloadImage(new RequestManager.OnResponseListener<ResponseBody>() {
                    @Override
                    public void onSuccess(ResponseBody respone) {
                        Message message = mHandler.obtainMessage();
                        message.what = MessageType.DownloadFileMessage.DOWNLOAD_SUCCESS;
                        try {
                            message.obj = respone.bytes();
                            mHandler.sendMessage(message);
                            Log.d(TAG, "Enter downloadImage method and into onSuccess,response: " +
                                    respone
                                            .bytes().toString());
                        } catch (IOException e) {
                            Log.d(TAG, "Enter downloadImage method and into onSuccess," +
                                    "IOException: " + e);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mHandler.sendEmptyMessage(MessageType.DownloadFileMessage.DOWNLOAD_FAILED);
                        Log.d(TAG, "Enter downloadImage method and into onFailure,Throwable: " + t);
                    }
                });
                mDownloadImg.setVisibility(View.VISIBLE);
                mResult.setVisibility(View.GONE);
                break;
            case R.id.download_file_break_point:
                Intent intent = new Intent(IntentAction.BreakPointDownAction.ACTION);
                startActivity(intent);
                break;
        }
    }

    private void sendPostRequest(RequestManager.OnResponseListener<GetStudentsRsp>
                                         responseListener) {
        /**
         * 通过OkHttpUtils发送请求
         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpUtils.post().url(BaseConfig.sQueryStudentUrl).addParams("age", "26").build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int i) {
//                                Log.d(TAG,
//                                        "Enter sendPostRequest method,Exception: "
//                                                + e);
//                            }
//
//                            @Override
//                            public void onResponse(String s, int i) {
//                                Log.d(TAG,
//                                        "Enter sendPostRequest method,response: "
//                                                + s);
//                                mResult.setText(s);
//                            }
//                        });
//            }
//        }).start();

        /**
         * 通过retrofit实现Post请求
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "1");
        map.put("age", "26");
        map.put("name", "谢蓉蓉");
        map.put("sex", "女");
        //将Map类型数据转换成Json
        String data = new Gson().toJson(map);
        Log.d(TAG, "Enter sendPostRequest method,data: " + data);

        //Json数据解析
//        Student stu = new Gson().fromJson(data, Student.class);
//        Log.d(TAG, "Enter sendPostRequest method,Student: " + stu.toString());

        RequestManager requestManager = new RequestManager(GetStudentsRsp.class);
        requestManager.sendRequest(RetrofitAPIManager.provideClientApi().getStudents(data),
                responseListener);

    }

    private void sendGetRequest() {
        /**
         * 通过OkHttpUtils发送请求
         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OkHttpUtils.get().url(BaseConfig.sQueryStudentUrl).addParams("age", "26").build()
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int i) {
//                                Log.d(TAG,
//                                        "Enter sendGetRequest method,Exception: "
//                                                + e);
//                            }
//
//                            @Override
//                            public void onResponse(String s, int i) {
//                                Log.d(TAG,
//                                        "Enter sendGetRequest method,response: "
//                                                + s);
//                                mResult.setText(s);
//                            }
//                        });
//            }
//        }).start();
    }

    private void sendPostFile(RequestManager.OnResponseListener<BaseRsp>
                                      responseListener) {
        File file = new File(BaseConfig.IMAGE_PATH);
        MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");

        /**
         * OkHttp发送请求
         */
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody
//                        .FORM);
//                if (null != file) {
//                    builder.addFormDataPart("img", file.getName(), RequestBody.create
//                            (MEDIA_TYPE_JPG, file));
//                }
//
//                MultipartBody requestBody = builder.build();
//
//                Request request = new Request.Builder()
//                        .url(BaseConfig.sUpFileUrl)
//                        .post(requestBody)
//                        .build();
//
//                mClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "Enter sendPostFile method,IOException: " + e);
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        Log.d(TAG, "Enter sendPostFile method,response: " + response.body()
//                                .string());
//                    }
//                });
//            }
//        }).start();

        /**
         * 通过retrofit实现单个文件上传
         */
//        RequestBody reqFile = RequestBody.create(MEDIA_TYPE_JPG, file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
//                reqFile);
//        RequestBody reqDescription = RequestBody.create(null, file.getName());
//        RequestManager requestManager = new RequestManager(BaseRsp.class);
//        requestManager.sendRequest(RetrofitAPIManager.provideClientApi().uploadFile(body,
//                reqDescription),
//                responseListener);


        /**
         * 通过retrofit实现多个文件上传
         */
        List<MultipartBody.Part> parts = new ArrayList<MultipartBody.Part>();
        parts.clear();
        RequestBody reqFile = RequestBody.create(MEDIA_TYPE_JPG, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(),
                reqFile);

        File file2 = new File(BaseConfig.IMAGE_PATH_TWO);
        RequestBody reqFile2 = RequestBody.create(MEDIA_TYPE_JPG, file2);
        MultipartBody.Part body2 = MultipartBody.Part.createFormData("file", file2.getName(),
                reqFile2);

        File file3 = new File(BaseConfig.IMAGE_PATH_THREE);
        RequestBody reqFile3 = RequestBody.create(MEDIA_TYPE_JPG, file3);
        MultipartBody.Part body3 = MultipartBody.Part.createFormData("file", file3.getName(),
                reqFile3);

        parts.add(body);
        parts.add(body2);
        parts.add(body3);


        List<RequestBody> bodies = new ArrayList<RequestBody>();
        RequestBody reqDescription = RequestBody.create(null, file.getName());
        RequestBody reqDescription2 = RequestBody.create(null, file2.getName());
        RequestBody reqDescription3 = RequestBody.create(null, file3.getName());

        bodies.add(reqDescription);
        bodies.add(reqDescription2);
        bodies.add(reqDescription3);


        RequestManager requestManager = new RequestManager(BaseRsp.class);
        requestManager.sendRequest(RetrofitAPIManager.provideClientApi().uploadFiles(parts,
                bodies),
                responseListener);

    }

    /**
     * 文件下载
     *
     * @param responseListener
     */
    private void downloadImage(RequestManager.OnResponseListener<ResponseBody>
                                       responseListener) {
//        Request request = new Request.Builder()
//                .url(BaseConfig.sDownFileUrl)
//                .build();
//
//        mClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "Enter downloadImage method,IOException: " + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Message message = mHandler.obtainMessage();
//                if (response.isSuccessful()) {
//                    message.what = MessageType.DownloadFileMessage.DOWNLOAD_SUCCESS;
//                    message.obj = response.body().bytes();
//                    mHandler.sendMessage(message);
//                } else {
//                    mHandler.sendEmptyMessage(MessageType.DownloadFileMessage.DOWNLOAD_FAILED);
//                }
//                Log.d(TAG, "Enter downloadImage method,response: " + response.body()
//                        .string());
//            }
//        });


        /**
         * 通过retrofit发送请求
         */
        RequestManager requestManager = new RequestManager(ResponseBody.class);
        requestManager.sendRequest(RetrofitAPIManager.provideClientApi().downloadFiles(BaseConfig
                        .sDownFileUrl),
                responseListener);
    }


}
