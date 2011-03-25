package org.springframework.data.mongodb.examples.music;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Base class for test cases acting as samples for our Mongo API. They set up
 * two {@link Album}s and populate them with {@link Track}s.
 * 
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class AbstractIntegrationTest {

    static final String COLLECTION = "album";

    @Autowired
    MongoOperations operations;

    Album bigWhiskey;
    Album thePursuit;

    List<Album> albums;


    @Before
    public void setUp() {

        operations.dropCollection(COLLECTION);

        albums = new ArrayList<Album>();

        bigWhiskey =
            new Album("Big Whiskey and the Groo Grux King",
            "Dave Matthews Band");
        bigWhiskey.add(new Track(0, "Grux"));
        bigWhiskey.add(new Track(1, "Shake me lika a monkey"));
        bigWhiskey.add(new Track(2, "Funny the way it is"));
        bigWhiskey.add(new Track(3, "Lying in the hands of God"));
        bigWhiskey.add(new Track(4, "Why I am"));
        bigWhiskey.add(new Track(5, "Dive in"));
        bigWhiskey.add(new Track(6, "Spaceman"));
        bigWhiskey.add(new Track(7, "Squirm"));
        bigWhiskey.add(new Track(8, "Alligator pie"));
        bigWhiskey.add(new Track(9, "Seven"));
        bigWhiskey.add(new Track(10, "Time bomb"));
        bigWhiskey.add(new Track(11, "My baby blue"));
        bigWhiskey.add(new Track(12, "You and me"));

        albums.add(bigWhiskey);

        thePursuit = new Album("The Pursuit", "Jamie Cullum");
        thePursuit.add(new Track(0, "Just one of those things"));
        thePursuit.add(new Track(1, "I'm all over it"));
        thePursuit.add(new Track(2, "Wheels"));
        thePursuit.add(new Track(3, "If I ruled the world"));
        thePursuit.add(new Track(4, "You and me are gone"));
        thePursuit.add(new Track(5, "Don't stop the music"));
        thePursuit.add(new Track(6, "Love ain't gonna let you down"));
        thePursuit.add(new Track(7, "Mixtape"));
        thePursuit.add(new Track(8, "I think, I love"));
        thePursuit.add(new Track(9, "We run things"));
        thePursuit.add(new Track(10, "Not while I am around"));
        thePursuit.add(new Track(11, "Music is through"));
        thePursuit.add(new Track(12, "Grand Torino"));
        thePursuit.add(new Track(13, "Grace is gone"));

        albums.add(thePursuit);
    }


    /**
     * Asserts the given query returns the Groo Grux {@link Album} and only
     * that.
     * 
     * @param query
     */
    protected void assertSingleGruxAlbum(Query query) {

        List<Album> result = operations.find(COLLECTION, query, Album.class);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));

        assertSingleGruxAlbum(result.get(0));
    }


    /**
     * Asserts the given {@link Album} is the Groo Grux album.
     * 
     * @param album
     */
    protected void assertSingleGruxAlbum(Album album) {

        assertThat(album, is(notNullValue()));
        assertThat(album.getId(), is(bigWhiskey.getId()));
        assertThat(album.getTitle(), is("Big Whiskey and the Groo Grux King"));
        assertThat(album.getArtist(), is("Dave Matthews Band"));
        assertThat(album.trackCount(), is(13));
    }


    /**
     * Asserts the given query returns the Pursuit {@link Album} and only that
     * one.
     * 
     * @param query
     */
    protected void assertSinglePursuitAlbum(Query query) {

        List<Album> result = operations.find(COLLECTION, query, Album.class);

        assertThat(result, is(notNullValue()));
        assertThat(result.size(), is(1));

        assertSinglePursuitAlbum(result.get(0));
    }


    /**
     * Asserts the given {@link Album} is the Pursuit {@link Album}.
     * 
     * @param album
     */
    protected void assertSinglePursuitAlbum(Album album) {

        assertThat(album, is(notNullValue()));
        assertThat(album.getId(), is(thePursuit.getId()));
        assertThat(album.getTitle(), is("The Pursuit"));
        assertThat(album.getArtist(), is("Jamie Cullum"));
        assertThat(album.trackCount(), is(14));
    }


    /**
     * Asserts that the given {@link List} of {@link Album} contains 2
     * {@link Album}s, Groo Grux and The Pursuit.
     * 
     * @param albums
     */
    protected void assertBothAlbums(List<Album> albums) {

        assertThat(albums, is(notNullValue()));
        assertThat(albums.size(), is(2));

        for (Album album : albums) {

            if (album.getId().equals(bigWhiskey.getId())) {
                assertSingleGruxAlbum(album);
            } else if (album.getId().equals(thePursuit.getId())) {
                assertSinglePursuitAlbum(album);
            } else {
                Assert.fail("Album is neither Grux or Pursuit! ");
            }
        }
    }
}
