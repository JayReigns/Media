package com.jr.media;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.view.ViewGroup;
import android.content.Context;
import android.view.LayoutInflater;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;
import com.jr.media.adapters.LibraryAdapter;
import android.view.MotionEvent;
import com.jr.media.adapters.BaseRecyclerAdapter;
import com.jr.media.adapters.BaseRecyclerAdapter.OnItemClickListener;
import com.jr.media.adapters.BaseRecyclerAdapter.OnItemLongClickListener;
import com.jr.media.adapters.SongsAdapter;
import android.net.Uri;
import com.jr.media.adapters.ArtistsAdapter;
import com.jr.media.adapters.BaseItemAdapter;
import android.widget.Toolbar;
import com.jr.media.adapters.AlbumsAdapter;
import com.jr.media.adapters.PlaylistsAdapter;

public class MainActivity extends Activity 
{
	private Toolbar mToolbar;
	private RecyclerView mRecyclerView;
	
	private LibraryAdapter mLibraryAdapter;
	private SongsAdapter mSongsAdapter;
	private ArtistsAdapter mArtistsAdapter;
	private AlbumsAdapter mAlbumsAdapter;
	private PlaylistsAdapter mPlaylistsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		MusicUtils.create(this);
		
		mToolbar = findViewById(R.id.toolbar);
		/*mToolbar.setNavigationIcon(R.drawable.ic_launcher);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
				@Override public void onClick(View v){
					onBackPressed();
				}
			});*/
		//setActionBar(mToolbar);
		
		mRecyclerView = findViewById(R.id.recyclerView);
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager( new LinearLayoutManager(this));
		
		mLibraryAdapter = new LibraryAdapter(LibraryItems.titles, LibraryItems.icons, LibraryItems.uris);
		mLibraryAdapter.setOnItemClickListener(mLibraryItemClickListener);
		
		mSongsAdapter = new SongsAdapter(MusicUtils.getCursorForUri(MusicUtils.AUDIO_URI, MusicUtils.SORT_TITLE_ASC));
		
		mArtistsAdapter = new ArtistsAdapter(MusicUtils.getCursorForUri(MusicUtils.ARTIST_URI));
		
		mAlbumsAdapter = new AlbumsAdapter(MusicUtils.getCursorForUri(MusicUtils.ALBUM_URI));
		
		mPlaylistsAdapter = new PlaylistsAdapter(MusicUtils.getCursorForUri(MusicUtils.PLAYLIST_URI));
		
		setLibraryView();
    }
	
	private void setLibraryView() {
		mToolbar.setTitle("Library");
		mRecyclerView.setAdapter(mLibraryAdapter);
	}
	
	private void setSongsView() {
		mToolbar.setTitle("Songs");
		mRecyclerView.setAdapter(mSongsAdapter);
	}
	
	private void setArtistsView() {
		mToolbar.setTitle("Artists");
		mRecyclerView.setAdapter(mArtistsAdapter);
	}
	
	private void setAlbumsView() {
		mToolbar.setTitle("Albums");
		mRecyclerView.setAdapter(mAlbumsAdapter);
	}
	
	private void setPlaylistsView() {
		mToolbar.setTitle("Playlists");
		mRecyclerView.setAdapter(mPlaylistsAdapter);
	}
	
	private OnItemClickListener mLibraryItemClickListener = new OnItemClickListener(){
		@Override public void onItemClick(View v, int pos, int viewid){
			onLibraryItemClicked(v, pos);
		}
	};
	
	private OnItemLongClickListener mSongLongClickListener = new OnItemLongClickListener(){
		@Override
		public boolean onItemLongClick(View v, int pos, int viewid)
		{
			Toast.makeText(v.getContext(), "gjkkl", 0).show();
			return true;
		}
	};
	
	private void onLibraryItemClicked(View v, int pos)
	{
		switch (pos) {
			case 0 :
				setPlaylistsView();
				break;
			case 1 :
				setAlbumsView();
				break;
			case 2 :
				setArtistsView();
				break;
			case 3 :
				setSongsView();
				break;
			case 4 :

				break;
		}
	}

	@Override
	public void onBackPressed()
	{
		if(mRecyclerView.getAdapter() != mLibraryAdapter) {
			setLibraryView();
		} else {
			super.onBackPressed();
		}
	}
	
	private class LibraryItems
	{
		public static final int [] titles = new int[] {
			R.string.library_playlists,
			R.string.library_albums,
			R.string.library_artists,
			R.string.library_audios,
			//R.string.library_videos
		};

		public static final int [] icons = new int[] {
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher,
			R.drawable.ic_launcher
		};

		public static final Uri [] uris = new Uri[] {
			MusicUtils.PLAYLIST_URI,
			MusicUtils.ALBUM_URI,
			MusicUtils.ARTIST_URI,
			MusicUtils.AUDIO_URI,
			MusicUtils.VIDEO_URI,
		};
	}
	
	
	/*public int getIndexforId(int id, Cursor cursor){
		if(cursor == null || cursor.getCount() <1)
			return -1;
		
		int start = 0, count = cursor.getCount();
		
		while(count > 1){
			int idx = count/2;
			cursor.moveToPosition(start+idx);
			int curid = cursor.getInt(0);
			Log.e("check", idx+", "+start +", "+ count+", "+curid+", "+id);
			if(id == curid){
				return start + idx;
			}else if(id<curid){
				count = idx;
			}else if(id>curid){
				if(count%2 == 0){
					start = idx+1;
					count = idx-1;
				}else{
					start = idx+1;
					count = idx;
				}
			}else{
				return -1;
			}
		}
		cursor.moveToPosition(start);
		int curid = cursor.getInt(0);
		if(curid == id)
			return start;
		return -1;
	}*/
	
}
