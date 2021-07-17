package com.jr.media;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.util.Log;

public class RL extends RelativeLayout
{
	public RL(Context c){
		super(c);
	}
	
	public RL(Context c, AttributeSet a) {
		super(c, a);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		toast("measure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		toast("layout");
		super.onLayout(changed, l, t, r, b);
	}
	
	void toast(String text){
		//Toast.makeText(getContext(), text, 0).show();
		//Log.d("tag", text);
	}
}
