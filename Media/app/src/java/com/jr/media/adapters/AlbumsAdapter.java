package com.jr.media.adapters;

import android.database.Cursor;
import android.provider.MediaStore;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import com.jr.media.adapters.BaseItemAdapter;

public class AlbumsAdapter extends BaseItemAdapter
{
	private int albumIdx;
	private int numTrackIdx;

	private int albumIdIdx;

	public AlbumsAdapter(Cursor cursor) {
		super(cursor);
	}

	@Override
	public void setCursor(Cursor c)
	{
		super.setCursor(c);

		if (this.cursor != null) {
			this.albumIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM);
			this.numTrackIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS);
			this.albumIdIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.AlbumColumns.ALBUM_KEY);
		}
	}

	@Override
	public void onBindViewHolder(BaseItemAdapter.ItemViewHolder viewHolder, int position)
	{
		cursor.moveToPosition(position);
		viewHolder.title.setText(cursor.getString(albumIdx));
		String subtext = cursor.getString(numTrackIdx) + " songs";
		viewHolder.subtitle.setText(subtext);

		MusicUtils.getAlbumArtAsync(cursor.getInt(albumIdIdx), R.drawable.ic_launcher, new BaseItemAdapter.AlbumArtListener(viewHolder.icon));
	}

	@Override
	public int getItemCount() { 
		return cursor.getCount(); 
	}
}
