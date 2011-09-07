package org.springframework.data.mongodb.examples.music;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * An {@link Album} represents a collection of {@link Track}s.
 * 
 * @author Oliver Gierke
 */
@Document
public class Album {

	@Id
	private ObjectId id;
	private String title;
	private String artist;
	private List<Track> tracks = new ArrayList<Track>();

	/**
	 * Creates a new {@link Album} with the given title and artist.
	 * 
	 * @param string
	 * @param artist
	 */
	public Album(String title, String artist) {

		Assert.isTrue(StringUtils.hasText(artist), "Artist name must be given!");
		Assert.isTrue(StringUtils.hasText(title), "Album title must be given!");

		this.id = new ObjectId();
		this.title = title;
		this.artist = artist;
	}


	public ObjectId getId() {

		return id;
	}


	public String getTitle() {

		return title;
	}


	public String getArtist() {

		return artist;
	}


	public List<Track> getTracks() {

		return tracks;
	}


	/**
	 * Adds the given {@link Track} to the {@link Album}.
	 * 
	 * @param track
	 */
	public void add(Track track) {

		this.tracks.add(track);
	}


	/**
	 * Returns the number of {@link Track}s contained in the {@link Album}.
	 * 
	 * @return
	 */
	public int trackCount() {

		return this.tracks.size();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || !getClass().equals(obj.getClass())) {
			return false;
		}

		Album that = (Album) obj;

		return id == null ? false : this.id.equals(that.id);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return 17 + ( id == null ? 0 : id.hashCode());
	}
}
