package org.springframework.data.mongodb.examples.music;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;


/**
 * Simple showcase to see how {@link Album} instances can be persisted and
 * queried by using the {@link MongoTemplate} API (or {@link MongoOperations}).
 * 
 * @author Oliver Gierke
 */
public class AlbumsIntegrationTest extends AbstractIntegrationTest {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.mongodb.core.core.examples.music.AbstractIntegrationTests
	 * #setUp()
	 */
	@Override
	public void setUp() {

		super.setUp();

		// Stores both albums
		operations.insertAll(albums);
	}


	@Test
	public void lookupAlbumByIdWithQueryBuilder() throws Exception {

		Query build =
				new Query(where("_id").is(bigWhiskey.getId()));

		assertSingleGruxAlbum(build);
	}


	@Test
	public void lookupAlbumByIdUsingJson() throws Exception {

		Query query =
				parseQuery("{'_id' : { '$oid' : '%s' }}", bigWhiskey.getId());
		assertSingleGruxAlbum(query);
	}


	@Test
	public void lookupAlbumsByTrackNameUsingJson() throws Exception {

		Query query = parseQuery("{'tracks.name' : 'Wheels'}");
		assertSinglePursuitAlbum(query);
	}


	@Test
	public void lookupAlbumByTrackNameUsingQueryBuilder() {

		Query spec =
				new Query(where("tracks.name").is("Grux"));
		assertSingleGruxAlbum(spec);
	}


	@Test
	public void lookupAlbumByTrackNamePattern() throws Exception {

		Query query =
				parseQuery("{ 'tracks.name' : { '$regex' : '.*it.*' , '$options' : '' }}");
		assertBothAlbums(operations.find(query, Album.class, COLLECTION));
	}


	private Query parseQuery(String query, Object... arguments) {

		return new BasicQuery(String.format(query, arguments));
	}

}
