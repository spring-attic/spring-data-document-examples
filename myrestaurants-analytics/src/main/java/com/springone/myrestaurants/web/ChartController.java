package com.springone.myrestaurants.web;

import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.datastore.document.mongodb.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.springone.myrestaurants.dao.RestaurantDao;
import com.springone.myrestaurants.domain.Restaurant;

@RequestMapping("/charts")
@Controller
public class ChartController {

	@Autowired
	private RestaurantDao restaurantDao;
	

	@RequestMapping("/demo-chart.png")
	public void renderChart(String variation, OutputStream stream)
			throws Exception {
		boolean rotate = "rotate".equals(variation); // add ?variation=rotate to
														// the URL to rotate the
														// chart
		JFreeChart chart = generateChart(rotate);
		ChartUtilities.writeChartAsPNG(stream, chart, 750, 400);
	}

	private JFreeChart generateChart(boolean rotate) {
		DefaultCategoryDataset dataset = getData();

		return ChartFactory.createBarChart("Favorited Restaurants", // title
				"Restaurants", // x-axis label
				"Number of times recommended", // y-axis label
				dataset, rotate ? PlotOrientation.HORIZONTAL
						: PlotOrientation.VERTICAL, true, // legend displayed
				true, // tooltips displayed
				false); // no URLs*/
	}

	private DefaultCategoryDataset getData()  {
		MongoTemplate mongoTemplate;
		DefaultCategoryDataset ds = null;
		try {
			Mongo m = new Mongo();
			DB db = m.getDB("mvc");
			mongoTemplate = new MongoTemplate(db, "mvc");
			mongoTemplate.afterPropertiesSet();
			
			DBObject result = getTopRecommendedRestaurants(mongoTemplate);
			/*
			 * [ { "parameters.p1" : "1" , "count" : 5.0} , 
			 *   { "parameters.p1" : "2" , "count" : 6.0} , 
			 *   { "parameters.p1" : "3" , "count" : 3.0} , 
			 *   { "parameters.p1" : "4" , "count" : 8.0}]
			 */
			//resultObject.
			ds =  new DefaultCategoryDataset();
			if (result instanceof BasicDBList) {
				BasicDBList dbList = (BasicDBList) result;
				for (Iterator iterator = dbList.iterator(); iterator.hasNext();) {
					DBObject dbo = (DBObject) iterator.next();
					System.out.println(dbo);
					Restaurant r = restaurantDao.findRestaurant(Long.parseLong(dbo.get("parameters.p1").toString()));
					ds.addValue(Double.parseDouble(dbo.get("count").toString()), "recommended", r.getName());
				}
			}
			/*
			ds.addValue(1, "fav-rest", "rest1");
			ds.addValue(3, "fav-rest", "rest2");
			ds.addValue(2, "fav-rest", "rest3");*/
			return ds;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ds;
		

	}
	
	
	
	public DBObject getTopRecommendedRestaurants(MongoTemplate mongoTemplate) {
		//This circumvents exception translation
		DBCollection collection = mongoTemplate.getConnection().getCollection("mvc");
		
		Date startDate = createDate(1, 5, 2010); 
		Date endDate = createDate(1,12,2010);
	    	    
	    DBObject cond = QueryBuilder.start("date").greaterThanEquals(startDate).lessThan(endDate).and("action").is("addFavoriteRestaurant").get();	    
		DBObject key = new BasicDBObject("parameters.p1", true);
		
		DBObject intitial = new BasicDBObject("count", 0);
		DBObject result = collection.group(key, cond, intitial, "function(doc, out){ out.count++; }");
		
		
		//List<ParameterRanking> parameterRanking = mongoTemplate.queryForListGroupBy(groupQuery, ParameterRanking.class);
		
		return result;
	}

	private Date createDate(int day, int month, int year) {
		Calendar d = Calendar.getInstance();
	    d.clear();
	    d.set(Calendar.YEAR, year);
	    d.set(Calendar.MONTH, month);
	    d.set(Calendar.DATE, day);
	    return d.getTime();
	}

}
