package com.jr.media;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;

public class MusicUtils
{
	public static final Uri AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	public static final Uri VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	public static final Uri ARTIST_URI = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
	public static final Uri ALBUM_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	public static final Uri PLAYLIST_URI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
	public static final Uri FILE_URI = MediaStore.Files.getContentUri("external");
	
	public static final Uri ALBUM_ART_URI = Uri.parse("content://media/external/audio/albumart");
	
	private static final String ASC = " ASC";
	private static final String DESC = " DESC";
	
	public static final String SORT_ID_ASC = MediaStore.Files.FileColumns._ID + ASC;
	public static final String SORT_ID_DESC = MediaStore.Files.FileColumns._ID +DESC;
	
	public static final String SORT_DATE_ADDED_ASC = MediaStore.Files.FileColumns.DATE_ADDED + ASC;
	public static final String SORT_DATE_ADDED_DESC = MediaStore.Files.FileColumns.DATE_ADDED +DESC;
	
	public static final String SORT_DATE_MODIFIED_ASC = MediaStore.Files.FileColumns.DATE_MODIFIED + ASC;
	public static final String SORT_DATE_MODIFIED_DESC = MediaStore.Files.FileColumns.DATE_MODIFIED +DESC;
	
	public static final String SORT_TITLE_ASC = "LOWER(" + MediaStore.Files.FileColumns.TITLE + ")" + ASC;
	public static final String SORT_TITLE_DESC = "LOWER(" + MediaStore.Files.FileColumns.TITLE + ")" +DESC;
	
	public static final String SORT_SIZE_ASC = MediaStore.Files.FileColumns.SIZE + ASC;
	public static final String SORT_SIZE_DESC = MediaStore.Files.FileColumns.SIZE +DESC;
	
	public static final String SORT_DURATION_ASC = MediaStore.Audio.AudioColumns.DURATION + ASC;
	public static final String SORT_DURATION_DESC = MediaStore.Audio.AudioColumns.DURATION +DESC;
	
	public static final String SORT_ARTIST_KEY = MediaStore.Audio.AudioColumns.ARTIST_KEY;
	public static final String SORT_ALBUM_KEY = MediaStore.Audio.AudioColumns.ALBUM_KEY;
	public static final String SORT_TRACK = MediaStore.Audio.AudioColumns.TRACK;
	public static final String SORT_PLAYORDER_KEY = MediaStore.Audio.Playlists.Members.PLAY_ORDER;
	
	
	private static Context sContext;
	
	//static methods for creating and closing instance
	
	public static void create(Context ctx){
		sContext = ctx;
	}
	
	public static void close(){
		sContext = null;
	}
	
	public static ContentResolver getContentResolver() {
		return sContext.getContentResolver();
	}
	
	public static Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return MusicUtils.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
    }
	
	public static Uri insert(Uri uri, ContentValues values) {
		return MusicUtils.getContentResolver().insert(uri, values);
	}
	
	public static int update(Uri uri, ContentValues values, String where, String[] args) {
		return MusicUtils.getContentResolver().update(uri, values, where, args);
	}
	
	public static int delete(Uri uri, String where, String[] args) {
		return MusicUtils.getContentResolver().delete(uri, where, args);
	}

	public static void registerObserver(Uri uri, boolean notifyForDescendents, ContentObserver observer) {
		MusicUtils.getContentResolver().registerContentObserver(uri, notifyForDescendents, observer);
	}
	
	public static void unRegisterObserver(ContentObserver observer) {
		MusicUtils.getContentResolver().unregisterContentObserver(observer);
	}
	
	// to get the list of columns for a cursor
	
	private final static int [] sEmptyList = new int[0];
	
	public static int [] getIdsForCursor(Cursor cursor){
		if (cursor == null || cursor.getCount() == 0) {
			return sEmptyList;
		}
		int colidx = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
		int len = cursor.getCount();
		int [] list = new int[len];
		cursor.moveToFirst();

		for (int i = 0; i < len; i++) {
			list[i] = cursor.getInt(colidx);
			cursor.moveToNext();
		}

		return list;
    }
	
	
	public static int getCountForUri(Uri uri) {
		String[] cols = new String[] {
			"count(*)"
		};
		Cursor cursor = MusicUtils.query(uri, cols, null, null, null);
		cursor.moveToFirst();
		int count = cursor.getInt(0);
		cursor.close();

        return count;
    }
	
	public static Cursor getCursorForUri(Uri uri) {
        return MusicUtils.getCursorForUri(uri, null);
    }
	
	public static Cursor getCursorForUri(Uri uri, String sort) {
        return MusicUtils.query(uri, null, null, null, sort);
    }
	
	
	public static Cursor getAudioVideoCursor(String volume) {
        return MusicUtils.getAudioVideoCursor(volume, null, MusicUtils.SORT_ID_ASC);
    }
	
	public static Cursor getAudioVideoCursor(String volume, String[] projection, String sort) {
		String where = MediaStore.Files.FileColumns.MEDIA_TYPE + " IN (" + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + "," + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO + ")";
        return MusicUtils.query(MediaStore.Files.getContentUri(volume), projection, where, null, sort);
    }
	
	
	public static Cursor getCursorForArtist(int id, String[] projection, String sort) {
		if(sort == null) sort = MediaStore.Audio.Media.ARTIST_KEY + ","  + MediaStore.Audio.Media.TRACK; 
        String where = MediaStore.Audio.Media.ARTIST_ID + "=" + id + " AND " + MediaStore.Audio.Media.IS_MUSIC + "=1";
        return MusicUtils.query(MusicUtils.AUDIO_URI, projection, where, null, sort);
    }

    public static Cursor getCursorForAlbum(int id, String[] projection, String sort) {
		if(sort == null) sort = MediaStore.Audio.Media.ALBUM_KEY + ","  + MediaStore.Audio.Media.TRACK; 
        String where = MediaStore.Audio.Media.ALBUM_ID + "=" + id + " AND " + MediaStore.Audio.Media.IS_MUSIC + "=1";
        return query(MusicUtils.AUDIO_URI, projection, where, null, sort);
    }

	public static Cursor getCursorForPlaylist(int id, String[] projection, String sort) {
		if(sort == null) sort = MediaStore.Audio.Playlists.Members.PLAY_ORDER; 
        return MusicUtils.query(MediaStore.Audio.Playlists.Members.getContentUri("external", id), projection, null, null, sort);
    }
	
    
	//	static methods for playlist management
	
	public static int getPlaylistMemberCount(int id) {
        return MusicUtils.getCountForUri(MediaStore.Audio.Playlists.Members.getContentUri("external", id));
    }
	
	public static Uri createPlaylist(String playlistName) {
        String name = playlistName.trim();

        // Add the playlist to the MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.Audio.Playlists.NAME, name);
        values.put(MediaStore.Audio.Playlists.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Audio.Playlists.DATE_MODIFIED, System.currentTimeMillis());

        Uri playlistUri = MusicUtils.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI, values);

        if (playlistUri == null) {
            throw new RuntimeException("Can't create Playlist: Content resolver insert() returned null");
        }
		
		return playlistUri;
    }

    public static void deletePlaylist(int id) {
        // Remove the playlist from the MediaStore
        MusicUtils.delete(
			MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
			MediaStore.Audio.Playlists._ID + "=" + id,
			null);
    }
	
	public static void clearPlaylist(int id) {
        // Clear the playlist members
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id);
        MusicUtils.clearPlaylist(uri);
    }
	
	public static void clearPlaylist(Uri uri) {
        // Clear the playlist members
        MusicUtils.delete(uri, null, null);
    }

    public static void editPlaylist(int id, int [] ids) {
		Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id);
		clearPlaylist(uri);

        if (ids != null) {
			int size = ids.length;
			
			int base = 1;
			
            ContentValues values [] = new ContentValues[size];
			for (int i = 0; i < size; i++) {
				values[i] = new ContentValues();
				values[i].put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf(base + i));
				values[i].put(MediaStore.Audio.Playlists.Members.AUDIO_ID, ids[i]);
			}
			
            ContentResolver resolver = MusicUtils.getContentResolver();
            resolver.bulkInsert(MediaStore.Audio.Playlists.Members.getContentUri("external", id),
								values);
            resolver.notifyChange(Uri.parse("content://media"), null);
        }
    }

    public static void appendToPlaylist(int id, int [] ids) {
        Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", id);
        
		if (ids != null) {
			int size = ids.length;

			int base = MusicUtils.getCountForUri(uri);

            ContentValues values [] = new ContentValues[size];
			for (int i = 0; i < size; i++) {
				values[i] = new ContentValues();
				values[i].put(MediaStore.Audio.Playlists.Members.PLAY_ORDER, Integer.valueOf(base + i));
				values[i].put(MediaStore.Audio.Playlists.Members.AUDIO_ID, ids[i]);
			}

            ContentResolver resolver = MusicUtils.getContentResolver();
            resolver.bulkInsert(MediaStore.Audio.Playlists.Members.getContentUri("external", id),
								values);
            resolver.notifyChange(Uri.parse("content://media"), null);
        }
    }
	
	
	public static Bitmap getBitmapForId(int id) {
        return ((BitmapDrawable) sContext.getResources().getDrawable(id)).getBitmap();
    }
	
	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
    
	public static Bitmap getAlbumArt(int album_id, int def)
	{
		if (album_id < 0)
			return getBitmapForId(def);

        ContentResolver res = MusicUtils.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
        if (uri != null) {
            InputStream in = null;
            try {
                in = res.openInputStream(uri);
                return BitmapFactory.decodeStream(in, null, sBitmapOptions);
            } catch (FileNotFoundException ex) {
				
			} finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
        }

        return getBitmapForId(def);
	}
	
	public static interface AlbumArtAsyncListener
	{
		void onGenerated (Bitmap bmp);
	}
	
	public static void getAlbumArtAsync(int album_id, int def, AlbumArtAsyncListener listener)
	{
		if (listener == null)
			return;
		if(album_id < 0){
			listener.onGenerated(MusicUtils.getBitmapForId(def));
		} else {
			new AlbumArtAsyncTask(album_id, def, listener).execute();
		}
	}
	
	private static class AlbumArtAsyncTask extends AsyncTask<Void, Void, Bitmap>
	{
		private AlbumArtAsyncListener listener;
		private int album_id, def;
		
		public AlbumArtAsyncTask (int album_id, int def, AlbumArtAsyncListener listener){
			this.album_id = album_id;
			this.def = def;
			this.listener = listener;
			
			this.listener.onGenerated(MusicUtils.getBitmapForId(this.def));
		}

		@Override
		protected Bitmap doInBackground(Void[] p1){
			return MusicUtils.getAlbumArt(album_id, def);
		}
		
		@Override
		protected void onPostExecute(Bitmap result){
			super.onPostExecute(result);
			listener.onGenerated(result);
		}
	}
	
	public static String getFormattedStringForMiliseconds(int miliseconds)
	{
		StringBuilder builder = new StringBuilder();
		
		int seconds = miliseconds/1000;
		
		int value = seconds / 3600;
		if(value > 0) {
			builder.append(value + ":");
			seconds = seconds % 3600;
		}
		
		value = seconds / 60;

		builder.append(value + ":");
		seconds = seconds % 60;
		
		builder.append((seconds>9)?""+seconds:"0"+seconds);
		
		return builder.toString();
	}
	
	/*public static Bitmap getAlbumArt(String path, int def)
	{
		try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            byte[] embeddedPicture = mmr.getEmbeddedPicture();
            return BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.length);
        } catch (Exception e) {
            return getBitmapForId(def);
        }
	}*/
}
