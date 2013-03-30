package org.mcxiaoke.fancooker.adapter;

import java.util.List;

import org.mcxiaoke.fancooker.App;
import org.mcxiaoke.fancooker.R;
import org.mcxiaoke.fancooker.dao.model.StatusModel;
import org.mcxiaoke.fancooker.util.OptionHelper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author mcxiaoke
 * @version 1.0 2011.06.25
 * @version 1.1 2011.10.26
 * @version 2.0 2011.12.06
 * @version 3.0 2012.02.22
 * 
 */
public class StatusArrayAdapter extends BaseStatusArrayAdapter {

	private static final String TAG = StatusArrayAdapter.class.getSimpleName();

	private static final int NONE = 0;
	private static final int MENTION = 1;
	private static final int SELF = 2;
	private static final int[] TYPES = new int[] { NONE, MENTION, SELF, };

	private boolean colored;

	private int mMentionedBgColor;// = 0x332266aa;
	private int mSelfBgColor;// = 0x33999999;

	void log(String message) {
		Log.e(TAG, message);
	}

	public StatusArrayAdapter(Context context, List<StatusModel> ss) {
		super(context, ss);
		init(context, false);
	}

	public StatusArrayAdapter(Context context, List<StatusModel> ss, boolean colored) {
		super(context, ss);
		init(context, colored);
	}

	private void init(Context context, boolean colored) {
		this.colored = colored;
		if (colored) {
			mMentionedBgColor = OptionHelper.readInt(mContext,
					R.string.option_color_highlight_mention, context
							.getResources().getColor(R.color.mentioned_color));
			mSelfBgColor = OptionHelper.readInt(mContext,
					R.string.option_color_highlight_self, context
							.getResources().getColor(R.color.self_color));
			if (App.DEBUG) {
				log("init mMentionedBgColor="
						+ Integer.toHexString(mMentionedBgColor));
				log("init mSelfBgColor=" + Integer.toHexString(mSelfBgColor));
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		final StatusModel s = getItem(position);
		if (s == null) {
			return NONE;
		}
		if (s.getSimpleText().contains("@" + App.getScreenName())) {
			return MENTION;
		} else {
			return s.isSelf() ? SELF : NONE;
		}
	}

	@Override
	public int getViewTypeCount() {
		return TYPES.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view= super.getView(position, convertView, parent);
		setColor(position, convertView);
		return view;
	}

	private void setColor(int position, View convertView) {
		if (colored) {
			int itemType = getItemViewType(position);
			switch (itemType) {
			case MENTION:
				convertView.setBackgroundColor(mMentionedBgColor);
				break;
			case SELF:
				convertView.setBackgroundColor(mSelfBgColor);
				break;
			case NONE:
				break;
			default:
				break;
			}
		}
	}

	public void changeData(List<StatusModel> data) {
		setData(data);
		notifyDataSetChanged();
	}

}