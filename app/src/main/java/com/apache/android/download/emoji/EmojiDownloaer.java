package com.apache.android.download.emoji;

import com.apache.android.download.IDownload;
import com.apache.android.download.IDownloadListener;
import com.apache.android.download.NormalDownloader;
import com.apache.android.util.NLog;

import java.util.List;

/**
 * Created by 01370340 on 2018/4/16.
 */

public class EmojiDownloaer implements IEmojiDownload<EmojiBean>,IDownloadListener {
    public static final String TAG = EmojiDownloaer.class.getSimpleName();
    private IDownload downloader;
    private EmojiDownloadContext mEmojiDownloadContext;
    private EmojiDownloadListener mListener;

    public EmojiDownloaer() {
        this.downloader = new NormalDownloader();
        downloader.setListener(this);
    }

    @Override
    public void startDownload(EmojiBean bean) {
        NLog.d(TAG, "startDownload");
        if (mEmojiDownloadContext == null){
            mEmojiDownloadContext = new EmojiDownloadContext();
            mEmojiDownloadContext.setEmojiBean(bean);
            downloader.startDownload(bean.getEmojiUrls().get(0), getLocalPathForEmoji(bean,0),mEmojiDownloadContext);
        }
    }

    @Override
    public void setListener(EmojiDownloadListener listener) {
        this.mListener = listener;
    }


    public String getLocalPathForEmoji(EmojiBean bean, int index){
        return bean.getEmojiUrls().get(index);
    }

    @Override
    public void onDownloadSuccess(String url, String localPath, Object contextObject) {
        NLog.d(TAG, "onDownloadSuccess url = %s",url);

        mEmojiDownloadContext.getLocalPathList().add(url);
        mEmojiDownloadContext.setDownloadedEmoji((mEmojiDownloadContext.getDownloadedEmoji() + 1));
        EmojiBean bean = mEmojiDownloadContext.getEmojiBean();
        //还没下载完
        if (mEmojiDownloadContext.getDownloadedEmoji() < bean.getEmojiUrls().size()){
            //产生一次结果回调
            try {
                if (mListener != null){
                    mListener.onDownloadProgress(bean,url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            String nextUrl = bean.getEmojiUrls().get(mEmojiDownloadContext.getDownloadedEmoji());
            downloader.startDownload(nextUrl,getLocalPathForEmoji(bean,mEmojiDownloadContext.getDownloadedEmoji()),mEmojiDownloadContext);
        }else {
            installEmojiPackageLocally(bean, mEmojiDownloadContext.getLocalPathList());
            mEmojiDownloadContext = null;

            if (mListener != null){
                mListener.onDownloadProgress(bean,url);
                mListener.onDownloadSuccess(bean);
            }
        }
    }

    @Override
    public void onDownloadFaile(String url, String errorCode, String errorMsg, Object contextObject) {
        NLog.d(TAG, "onDownloadFaile");

    }

    @Override
    public void onDownloadProgress(String url, String localPath, String downloadSize, String totalSize, Object contextObject) {
        NLog.d(TAG, "onDownloadProgress url = %s",url);

    }


    /**
     * 把表情包安装到本地
     * @param emojiPackage
     * @param localPathList
     */
    private void installEmojiPackageLocally(EmojiBean emojiPackage, List<String> localPathList) {
        //TODO:
        return;
    }
}
