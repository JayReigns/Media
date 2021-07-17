package com.jr.media.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import com.jr.media.adapters.LibraryAdapter;
import android.view.View.OnClickListener;
import android.content.Context;
import android.widget.Toast;

public class LibraryAdapter extends BaseRecyclerAdapter<LibraryAdapter.ItemViewHolder>
{
	private int [] mTitles;
	private int [] mIcons;
	private Uri [] mUris;
	
	public LibraryAdapter(int[] titles, int[] icons, Uri[] uris){
		this.mTitles = titles;
		this.mIcons = icons;
		this.mUris = uris;
	}
	
	@Override
	public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View itemView = inflater.inflate(R.layout.library_item, parent, false);
		ItemViewHolder viewHolder = new ItemViewHolder(itemView);
		
		itemView.setOnClickListener(this);
		
		return viewHolder; 
	}

	@Override
	public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
		String subtext =  viewHolder.itemView.getContext().getString(R.string.library_subtext, MusicUtils.getCountForUri(mUris[position]));
		viewHolder.title.setText(mTitles[position]);
		viewHolder.subtitle.setText(subtext);
		viewHolder.icon.setImageResource(mIcons[position]);
	}

	@Override
	public int getItemCount() {
		return mTitles.length;
	}
	
	public class ItemViewHolder extends BaseRecyclerAdapter.ViewHolder{ 
		ImageView icon; 
		TextView title, subtitle;

		ItemViewHolder(View itemView) { 
			super(itemView);
			icon = itemView.findViewById(R.id.item_icon);
			title = itemView.findViewById(R.id.item_title);
			subtitle = itemView.findViewById(R.id.item_sub_title);
		}
	}

}
