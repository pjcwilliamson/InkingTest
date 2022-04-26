package org.williamsonministry.inkingtest;

import android.graphics.Path;

public class PathWithParams extends Path {
    public static final int ERASER = -1;
    public static final int MAGENTA = 1;
    private int Type;

    public PathWithParams(int type) {
        Type = type;
    }

    public int getType() {
        return Type;
    }
}
