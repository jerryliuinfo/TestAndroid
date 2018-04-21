package com.apache.android.download;

/**
 * Created by 01370340 on 2018/4/15.
 */

public interface IDownloadListener {

    void onDownloadSuccess(String url,String localPath,Object contextObject);

    void onDownloadFaile(String url,String errorCode, String errorMsg,Object contextObject);

    void onDownloadProgress(String url,String localPath,String downloadSize,String totalSize,Object contextObject);
}
