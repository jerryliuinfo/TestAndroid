package com.apache.android.bean;

/**
 * Created by 01370340 on 2018/4/29.
 */

public class FatMan extends Person {

    private RichManDelegate richMan;

    public FatMan(RichMan richMan) {
        this.richMan = new RichManDelegate();
    }

    public void rich(){
        if (richMan != null){
            richMan.rich();
        }
    }

    private static class RichManDelegate extends RichMan{
    }
}
