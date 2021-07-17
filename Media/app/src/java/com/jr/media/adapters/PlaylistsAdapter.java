package com.jr.media.adapters;

import android.database.Cursor;
import android.provider.MediaStore;
import com.jr.media.MusicUtils;
import com.jr.media.R;
import com.jr.media.adapters.BaseItemAdapter;

public class PlaylistsAdapter extends BaseItemAdapter
{
	private int playlistIdx;

	public PlaylistsAdapter(Cursor cursor) {
		super(cursor);
	}

	@Override
	public void setCursor(Cursor c)
	{
		super.setCursor(c);

		if (this.cursor != null) {
			this.playlistIdx = cursor.getColumnIndexOrThrow(MediaStore.Audio.Playlists.NAME);
		}
	}

	@Override
	public void onBindViewHolder(BaseItemAdapter.ItemViewHolder viewHolder, int position)
	{
		cursor.moveToPosition(position);
		viewHolder.title.setText(cursor.getString(playlistIdx));
		String subtext = MusicUtils.getPlaylistMemberCount(cursor.getInt(0)) + " songs";
		viewHolder.subtitle.setText(subtext);

		//MusicUtils.getAlbumArtAsync(cursor.getInt(albumIdIdx), R.drawable.ic_launcher, new BaseItemAdapter.AlbumArtListener(viewHolder.icon));
	}

	@Override
	public int getItemCount() { 
		return cursor.getCount(); 
	}
}
