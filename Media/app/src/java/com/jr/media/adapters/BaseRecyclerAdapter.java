package com.jr.media.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
implements View.OnClickListener, View.OnLongClickListener
{
	protected BaseRecyclerAdapter.OnItemClickListener mClickListener;
	protected BaseRecyclerAdapter.OnItemLongClickListener mLongClickListener;
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mClickListener = listener;
	}
	
	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.mLongClickListener = listener;
	}
	
	@Override
	abstract public VH onCreateViewHolder(ViewGroup p1, int p2);

	@Override
	abstract public void onBindViewHolder(VH p1, int p2)

	@Override
	abstract public int getItemCount();

	@Override
	public void onClick(View v)
	{
		if (this.mClickListener != null) {
			ViewHolder holder = (ViewHolder) v.getTag();
			int position = holder.getPosition();

			this.mClickListener.onItemClick(v, position, v.getId());
		}
	}

	@Override
	public boolean onLongClick(View v)
	{
		if (this.mLongClickListener != null) {
			ViewHolder holder = (ViewHolder) v.getTag();
			int position = holder.getPosition();

			return this.mLongClickListener.onItemLongClick(v, position, v.getId());
		}
		return false;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public ViewHolder(View itemView) { 
			super(itemView);
			itemView.setTag(this);
		}
	}
	
	public static interface OnItemClickListener
	{
		public void onItemClick (View v, int pos, int viewid);
	}
	
	public static interface OnItemLongClickListener
	{
		public boolean onItemLongClick (View v, int pos, int viewid);
	}

}
