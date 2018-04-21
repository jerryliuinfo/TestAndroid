package com.apache.android.download;

/**
 * Created by 01370340 on 2018/4/15.
 */

public interface IDownload {

    void setListener(IDownloadListener listener);

    void startDownload(String url,String localPath,Object contextObject);
}
