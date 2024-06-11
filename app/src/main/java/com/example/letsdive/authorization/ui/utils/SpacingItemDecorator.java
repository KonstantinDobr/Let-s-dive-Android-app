package com.example.letsdive.authorization.ui.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;
    private final int leftSpaceHeight;
    private final int rightSpaceHeight;

    public SpacingItemDecorator(int verticalSpaceHeight, int leftSpaceHeight, int rightSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.leftSpaceHeight = leftSpaceHeight;
        this.rightSpaceHeight = leftSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = verticalSpaceHeight;
        outRect.left = leftSpaceHeight;
        outRect.right = rightSpaceHeight;
    }
}
