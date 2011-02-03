package org.springframework.data.mongodb.examples.music;

import org.junit.Test;
import org.springframework.data.document.mongodb.MongoOperations;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.util.JSON;


/**
 * Simple showcase to see how {@link Album} instances can be persisted and
 * queried by using the {@link MongoTemplate} API (or {@link MongoOperations}).
 * 
 * @author Oliver Gierke
 */
@ContextConfiguration
public class AlbumsIntegrationTest extends AbstractIntegrationTest {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.data.mongodb.examples.music.AbstractIntegrationTests
     * #setUp()
     */
    @Override
    public void setUp() {

        super.setUp();

        // Stores both albums
        operations.insertList(albums);
    }


    @Test
    public void createCollection() {

    }


    @Test
    public void lookupAlbumByIdWithQueryBuilder() throws Exception {

        DBObject query = QueryBuilder.start("_id").is(bigWhiskey.getId()).get();
        assertSingleGruxAlbum(query);
    }


    @Test
    public void lookupAlbumByIdUsingJson() throws Exception {

        DBObject query =
            parseQuery("{'_id' : { '$oid' : '%s' }}", bigWhiskey.getId());
        assertSingleGruxAlbum(query);
    }


    @Test
    public void lookupAlbumsByTrackNameUsingJson() throws Exception {

        DBObject query = parseQuery("{'tracks.name' : 'Wheels'}");
        assertSinglePursuitAlbum(query);
    }


    @Test
    public void lookupAlbumByTrackNameUsingQueryBuilder() {

        DBObject query = QueryBuilder.start("tracks.name").is("Grux").get();
        assertSingleGruxAlbum(query);
    }


    @Test
    public void lookupAlbumByTrackNamePattern() throws Exception {

        DBObject query =
            parseQuery("{ 'tracks.name' : { '$regex' : '.*it.*' , '$options' : '' }}");
        assertBothAlbums(operations.find(query, Album.class));
    }


    private DBObject parseQuery(String query, Object... arguments) {

        return (DBObject) JSON.parse(String.format(query, arguments));
    }

}
