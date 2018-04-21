package com.apache.android.download.emoji;

import java.util.List;

/**
 * Created by 01370340 on 2018/4/16.
 */

public class EmojiBean {

    private String emojiId;

    private List<String> emojiUrls;


    public String getEmojiId() {
        return emojiId;
    }

    public void setEmojiId(String emojiId) {
        this.emojiId = emojiId;
    }

    public List<String> getEmojiUrls() {
        return emojiUrls;
    }

    public void setEmojiUrls(List<String> emojiUrls) {
        this.emojiUrls = emojiUrls;
    }
}
