package com.apache.android.download.emoji;

/**
 * Created by 01370340 on 2018/4/16.
 */

public interface IEmojiDownload<T> {

    void startDownload(T t);

    void setListener(EmojiDownloadListener listener);
}
