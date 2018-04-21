package com.apache.android.download.emoji;

/**
 * Created by 01370340 on 2018/4/16.
 */

public interface EmojiDownloadListener {

    void onDownloadSuccess(EmojiBean bean);

    void onDownloadFailed(EmojiBean bean, int errorCode, String errorMsg);

    void onDownloadProgress(EmojiBean bean, String donwloadImgUrl);
}
