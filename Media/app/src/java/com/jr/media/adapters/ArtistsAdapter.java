package com.jr.media.adapters;

import android.database.Cursor;
import android.provider.MediaStore;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import com.jr.media.adapters.BaseItemAdapter;

public class ArtistsAdapter extends BaseItemAdapter
{
	private int artistIdx;
	private int numTrackIdx;

	private int albumIdIdx;

	public ArtistsAdapter(Cursor cursor) {
		super(cursor);
	}

	@Override
	public void setCursor(Cursor c)
	{
		super.setCursor(c);
		
		if (this.cursor != null) {
			this.artistIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST);
			this.numTrackIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.NUMBER_OF_TRACKS);
			this.albumIdIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.ArtistColumns.ARTIST_KEY);
		}
	}

	@Override
	public void onBindViewHolder(BaseItemAdapter.ItemViewHolder viewHolder, int position)
	{
		cursor.moveToPosition(position);
		viewHolder.title.setText(cursor.getString(artistIdx));
		String subtext = cursor.getString(numTrackIdx) + " songs";
		viewHolder.subtitle.setText(subtext);

		MusicUtils.getAlbumArtAsync(cursor.getInt(albumIdIdx), R.drawable.ic_launcher, new BaseItemAdapter.AlbumArtListener(viewHolder.icon));
	}

	@Override
	public int getItemCount() { 
		return cursor.getCount(); 
	}
}
