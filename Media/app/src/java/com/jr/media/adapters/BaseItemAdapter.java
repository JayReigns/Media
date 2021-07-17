package com.jr.media.adapters;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import android.widget.Toast;
import android.util.Log;

public abstract class BaseItemAdapter extends BaseRecyclerAdapter<BaseItemAdapter.ItemViewHolder>
{
	protected Cursor cursor;

	public BaseItemAdapter(Cursor cursor) {
		this.setCursor(cursor);
	}

	public void setCursor(Cursor c) {
		if(c != null){
			this.cursor = c;
			notifyDataSetChanged();
		}
	}

	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Log.w("adapter", "create holder");
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.song_item, parent, false);
		ItemViewHolder viewHolder = new ItemViewHolder(itemView);
		return viewHolder; 
	}

	@Override
	public void onClick(View v)
	{Toast.makeText(v.getContext(), "gjkkl   " + (v.getId() != R.id.item_options), 0).show();
		if (this.mClickListener != null) {
			ViewHolder holder = (ViewHolder) v.getTag();
			int position = holder.getPosition();
			if (this.mLongClickListener != null && v.getId() != R.id.item_options) {
				this.mLongClickListener.onItemLongClick(v, position, v.getId());
			} else {
				this.mClickListener.onItemClick(v, position, v.getId());
			}
		}
	}
	
	public class ItemViewHolder extends BaseRecyclerAdapter.ViewHolder{ 
		ImageView icon; 
		TextView title, subtitle;

		ItemViewHolder(View itemView) { 
			super(itemView);
			icon = itemView.findViewById(R.id.item_icon);
			title = itemView.findViewById(R.id.item_title);
			subtitle = itemView.findViewById(R.id.item_sub_title);
			
			itemView.setOnClickListener(BaseItemAdapter.this);
			itemView.findViewById(R.id.item_options).setOnClickListener(BaseItemAdapter.this);
		}
	}

	@Override
	abstract public void onBindViewHolder(ItemViewHolder p1, int p2)
	
	@Override
	abstract public int getItemCount()

	protected class AlbumArtListener implements MusicUtils.AlbumArtAsyncListener
	{
		private ImageView imageView;
		public AlbumArtListener (ImageView view) {
			this.imageView = view;
		}

		@Override
		public void onGenerated(Bitmap bmp){
			this.imageView.setImageBitmap(bmp);
		}
	}
}
