package org.wxx.restService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.sql.SQLException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wxx.lucene.BasicSearch;
import org.wxx.lucene.SpatialSearch;

@Path("/")
public class Rest {
	@GET
	@Path("/basic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LuceneQueryAuthor(@QueryParam("keywords") String keywords,
			@QueryParam("numResultsToSkip") int numResultsToSkip,
			@QueryParam("numResultsToReturn") int numResultsToReturn) 
					throws ParseException, IOException, SQLException {
		BasicSearch q = new BasicSearch();
		String results = q.LuceneQueryAuthor((String)keywords,(int)numResultsToSkip, (int)numResultsToReturn).toString();
		results = results.replace("\\\"", "\"");
		results = results.replace("\"{", "{");
		results = results.replace("}\"", "}");
		System.out.println(results);
		return Response.status(200).entity(results).build();
	}
	
	@GET
	@Path("/spatial")
	@Produces(MediaType.APPLICATION_JSON)
	public Response LuceneQueryTitle(@QueryParam("keywords") String keywords,
			@QueryParam("yearfrom") String yearfrom,
			@QueryParam("yearto") String yearto,
			@QueryParam("numResultsToSkip") int numResultsToSkip,
			@QueryParam("numResultsToReturn") int numResultsToReturn) 
					throws ParseException, IOException, SQLException {
		SpatialSearch s = new SpatialSearch();
		String results =s.LuceneQueryTitle((String)keywords,(String)yearfrom,(String)yearto,(int)numResultsToSkip, (int)numResultsToReturn).toString();
		results = results.replace("\\\"", "\"");
		results = results.replace("\"{", "{");
		results = results.replace("}\"", "}");
		System.out.println(results);
		return Response.status(200).entity(results).build();
	}

}