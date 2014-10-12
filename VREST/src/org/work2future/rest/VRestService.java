package org.work2future.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;



@Path("v1/service")
public class VRestService {

	@GET
	@Path("candidate/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getStudentInfo(@PathParam("id") String id){
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			DBCollection coll = db.getCollection("candidate");
			DBCursor cursor = coll.find();
			BasicDBObject query = new BasicDBObject("_id", id);
			cursor = coll.find(query);
			
			try {
				while(cursor.hasNext()) {
				   obj = (JSONObject)parser.parse(cursor.next().toString());
				}
			} finally {
				   cursor.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		return obj.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("allcandidates")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllStudentInfo(){
		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			DBCollection coll = db.getCollection("candidate");
			DBCursor cursor = coll.find();
			
			try {
				JSONArray arr = new JSONArray();
				while(cursor.hasNext()) {
				   arr.add((JSONObject)parser.parse(cursor.next().toString()));
				}
				obj.put("candidates", arr);
				
			} finally {
				   cursor.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		return obj.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("alljobs")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllJobs(){
		JSONParser parser = new JSONParser();
		JSONObject obj = new JSONObject();
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			DBCollection coll = db.getCollection("job");
			DBCursor cursor = coll.find();
			
			try {
				JSONArray arr = new JSONArray();
				while(cursor.hasNext()) {
				   arr.add((JSONObject)parser.parse(cursor.next().toString()));
				}
				obj.put("jobs", arr);
				
			} finally {
				   cursor.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		return obj.toJSONString();

	}

	
	@GET
	@Path("job/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJob(@PathParam("id") String id){
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			DBCollection coll = db.getCollection("job");
			DBCursor cursor = coll.find();
			BasicDBObject query = new BasicDBObject("_id", id);
			cursor = coll.find(query);
			
			try {
				while(cursor.hasNext()) {
				   obj = (JSONObject)parser.parse(cursor.next().toString());
				}
			} finally {
				   cursor.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
		return obj.toJSONString();

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("candidate")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadStudent(String str){
		JSONObject response = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(str.toLowerCase());
			Object candidatesObj = obj.get("candidates");
			if(candidatesObj instanceof JSONArray){
				JSONArray arr = (JSONArray)candidatesObj;
				obj = (JSONObject)arr.get(0);
			}
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			
			DBObject dbObject = new BasicDBObject(obj);
			db.getCollection("candidate").save(dbObject);
			response.put("ack", "SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.put("ack", "FAILURE");
			response.put("value", e.getMessage());
		}
		
		return response.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("job")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String uploadJob(String str){
		JSONObject responseObj = new JSONObject();
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(str);
			
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			
			DBObject dbObject = new BasicDBObject(obj);
			db.getCollection("job").save(dbObject);
			responseObj.put("ack", "SUCCESS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseObj.put("ack", "FAILURE");
			responseObj.put("value", e.getMessage());
		}
		return responseObj.toJSONString();
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("bestjobs/{id}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJobs(@PathParam("id") String str){
		JSONObject responseObj = new JSONObject();
		JSONObject finalObj = new JSONObject();
		
		try {
			JSONParser parser = new JSONParser();
			//JSONObject obj = (JSONObject)parser.parse(str);
			String candidateId = str;//(String)obj.get("id");
			
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "work2future" );
			DBCollection coll = db.getCollection("candidate");
			DBCursor cursor = coll.find();
			BasicDBObject query = new BasicDBObject("_id", candidateId);
			cursor = coll.find(query);
			
			try {
				while(cursor.hasNext()) {
				   finalObj.put("candidate", cursor.next());
				}
			} finally {
				   cursor.close();
			}
			coll = db.getCollection("job");
			DBCursor cursor2 = coll.find();
			try {
				JSONArray arr = new JSONArray();
				while(cursor2.hasNext()) {
				   //TODO: Date comparison
					arr.add(cursor2.next());
				}
				finalObj.put("jobs", arr);
				responseObj.put("ack", "SUCCESS");
			} finally {
				   cursor.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			responseObj.put("ack", "FAILURE");
			responseObj.put("value", e.getMessage());
			return responseObj.toJSONString();
		}
		return finalObj.toJSONString();
	}
	
	public static void main(String[] args) {
		JSONObject obj = new JSONObject();
		/*obj.put("_id", "4");
		obj.put("jobname", "grocery clerk at target");
		obj.put("jobdescription", "This is a grocery clerk job. Requires you to bill items, pack them. This is a day job");
		obj.put("salary", "15");
		obj.put("jobaddress", "533 Coleman Ave San Jose, CA");
		obj.put("phoneno", "1234567890");
		obj.put("email", "random1@gmail.com");
		obj.put("Company", "TARGET");
		JSONObject dateObj = new JSONObject();
		dateObj.put("from", "2015-03-15");
		dateObj.put("to", "2015-06-15");
		obj.put("jobdate", dateObj);
		JSONArray skills = new JSONArray();
		skills.add("quick");
		skills.add("computer");
		skills.add("communication");
		obj.put("skills", skills);*/
		VRestService v = new VRestService();
		//v.uploadJob(obj.toJSONString());
		
		obj.put("_id","2");
		obj.put("address","1031 clyde avenue santa clara ca 95054");
		obj.put("dob", "1998-01-10");
		obj.put("firstname", "aaron");
		obj.put("lastname", "rooney");
		obj.put("phoneno", "7766672909");
		JSONArray skills = new JSONArray();
		skills.add("quick");
		skills.add("computer");
		skills.add("communication");
		obj.put("skills", skills);
		JSONObject dateObj = new JSONObject();
		dateObj.put("from", "2015-03-03");
		dateObj.put("to", "2015-06-02");
		obj.put("availability", dateObj);
		JSONArray interests = new JSONArray();
		interests.add("baseball");
		interests.add("outdoor");
		obj.put("interests", interests);
		v.uploadStudent(obj.toJSONString());
		/*obj.put("id", "1");
		v.getJobs(obj.toJSONString());*/
	}
}
