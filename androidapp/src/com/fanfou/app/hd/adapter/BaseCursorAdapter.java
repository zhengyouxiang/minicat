package com.fanfou.app.hd.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.fanfou.app.hd.App;
import com.fanfou.app.hd.R;
import com.fanfou.app.hd.cache.IImageLoader;
import com.fanfou.app.hd.util.OptionHelper;

/**
 * @author mcxiaoke
 * @version 1.0 2011.06.01
 * @version 1.5 2011.10.24
 * @version 1.6 2011.10.30
 * @version 1.7 2011.11.10
 * @version 1.8 2011.12.06
 * @version 2.0 2012.02.20
 * @version 2.1 2012.02.27
 * 
 */
public abstract class BaseCursorAdapter extends CursorAdapter implements
		OnScrollListener {

	protected Context mContext;
	protected LayoutInflater mInflater;
	protected IImageLoader mLoader;
	protected boolean busy;
	private int fontSize;

	public BaseCursorAdapter(Context context) {
		super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
		initialize(context);
	}

	public BaseCursorAdapter(Context context, Cursor c) {
		super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
		initialize(context);
	}

	private void initialize(Context context) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mLoader = App.getImageLoader();
		this.fontSize = OptionHelper.readInt(mContext,
				R.string.option_fontsize,
				context.getResources().getInteger(R.integer.defaultFontSize));
	}

	abstract int getLayoutId();

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int size) {
		fontSize = size;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			busy = false;
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			busy = true;
			break;
		case OnScrollListener.SCROLL_STATE_FLING:
			busy = true;
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
	}

}
