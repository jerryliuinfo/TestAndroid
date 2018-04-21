package com.apache.android.download.emoji;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2018/4/16.
 */

public class EmojiDownloadContext {

    private EmojiBean emojiBean;

    private int downloadedEmoji;

    private List<String> localPathList = new ArrayList<>();

    public void setEmojiBean(EmojiBean emojiBean) {
        this.emojiBean = emojiBean;
    }

    public void setDownloadedEmoji(int downloadedEmoji) {
        this.downloadedEmoji = downloadedEmoji;
    }

    public void setLocalPathList(List<String> localPathList) {
        this.localPathList = localPathList;
    }

    public EmojiBean getEmojiBean() {
        return emojiBean;
    }

    public int getDownloadedEmoji() {
        return downloadedEmoji;
    }

    public List<String> getLocalPathList() {
        return localPathList;
    }
}
