package com.jr.media.adapters;

import android.database.Cursor;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import com.jr.media.adapters.BaseItemAdapter;

public class SongsAdapter extends BaseItemAdapter
{
	private int titleIdx;
	private int artistIdx;
	private int durationIdx;

	private int albumIdIdx;

	public SongsAdapter(Cursor cursor) {
		super(cursor);
	}

	@Override
	public void setCursor(Cursor c)
	{
		super.setCursor(c);
		
		if (this.cursor != null) {
			this.titleIdx = cursor.getColumnIndexOrThrow("title");
			this.artistIdx = cursor.getColumnIndexOrThrow("artist");
			this.durationIdx = cursor.getColumnIndexOrThrow("duration");
			this.albumIdIdx = cursor.getColumnIndexOrThrow("album_id");
		}
	}

	@Override
	public void onBindViewHolder(BaseItemAdapter.ItemViewHolder viewHolder, int position)
	{
		cursor.moveToPosition(position);
		viewHolder.title.setText(cursor.getString(titleIdx));
		String subtext = cursor.getString(artistIdx) + " • " + MusicUtils.getFormattedStringForMiliseconds(cursor.getInt(durationIdx));
		viewHolder.subtitle.setText(subtext);

		MusicUtils.getAlbumArtAsync(cursor.getInt(albumIdIdx), R.drawable.unknown_album, new BaseItemAdapter.AlbumArtListener(viewHolder.icon));
	}

	@Override
	public int getItemCount() { 
		return cursor.getCount(); 
	} 
}
