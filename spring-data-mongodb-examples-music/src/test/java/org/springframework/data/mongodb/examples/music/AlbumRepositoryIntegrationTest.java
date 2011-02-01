package org.springframework.data.mongodb.examples.music;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


/**
 * Test case to show the usage of {@link AlbumRepository} and thus the Mongo
 * repository support in general.
 * 
 * @author Oliver Gierke
 */
@ContextConfiguration
public class AlbumRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    AlbumRepository repository;


    @Test
    public void createAlbum() throws Exception {

        repository.save(albums);
        assertSingleGruxAlbum(repository.findById(bigWhiskey.getId()));
    }


    @Test
    public void findsAlbumByConcreteTrackName() throws Exception {

        repository.save(albums);
        assertSingleGruxAlbum(repository.findByTracksName("Grux"));

        List<Album> albums = repository.findByTracksName("Foo");
        assertThat(albums.isEmpty(), is(true));
    }


    @Test
    public void findsAllAlbumsByTrackNameLike() throws Exception {

        repository.save(albums);
        assertBothAlbums(repository.findByTracksNameLike("*it*"));
    }


    @Test
    public void findsAlbumsByTrackRating() throws Exception {

        bigWhiskey.getTracks().get(4).setRating(Stars.FOUR);
        repository.save(albums);

        assertSingleGruxAlbum(repository
                .findByTracksRatingGreaterThan(Stars.THREE));

        List<Album> albums =
            repository.findByTracksRatingGreaterThan(Stars.FOUR);
        assertThat(albums.isEmpty(), is(true));
    }


    private void assertSingleGruxAlbum(List<Album> albums) {

        assertThat(albums, is(notNullValue()));
        assertThat(albums.size(), is(1));
        assertThat(albums.get(0), is(notNullValue(Album.class)));
        assertSingleGruxAlbum(albums.get(0));
    }
}
