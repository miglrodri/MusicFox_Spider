package ws_scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.*;

public class Ws_scrapper {
	
	public static String api_key = "57716264bdd91bd35edc83d13f16f30c";

	private String[] blues = {
			"e21ee849-a6b5-11e0-b446-00251188dd67",
			"e21ed16d-a6b5-11e0-b446-00251188dd67",
			"e21ed088-a6b5-11e0-b446-00251188dd67",
			"62766328-e08c-3180-fb0f-cb340d6b1e2d",
			"1b9b65bb-c8d3-effb-853a-47be252c43b4",
			"e576c7d8-a6b5-11e0-b446-00251188dd67",
			"d3f3164e-6e3d-e932-640e-95ea77d945ae",
			"e69bf71b-a6b5-11e0-b446-00251188dd67",
			"e59c4d0d-a6b5-11e0-b446-00251188dd67",
			"91b309ca-fbe3-344d-5eae-502f85b58864"
			};
	
	private String[] country = {
			"e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"ef82ddea-a6b5-11e0-b446-00251188dd67",
			"83e62474-a62d-2b55-abe3-25f9ef802b00",
			"ee1a5c3d-a6b5-11e0-b446-00251188dd67",
			"e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"84cd6ddc-c91c-7451-7df0-944d838350a8",
			"e5bf687e-a6b5-11e0-b446-00251188dd67",
			"931539bf-8e62-c642-a116-9ee3e0758e43"
			};
	
	private String[] vocal = {};
	
	private String[] electronic = {};
	
	private String[] jazz = {
			"e4be067b-a6b5-11e0-b446-00251188dd67",
			"9af98ec2-7774-7aa8-f7ce-11015444374c",
			"e6e62cda-a6b5-11e0-b446-00251188dd67",
			"6b9bfdad-8f75-8763-59c4-98fc6122efa5",
			"e6066461-0f1d-8412-9a50-bf7d5053aa75",
			"e24d0ca3-a6b5-11e0-b446-00251188dd67",
			"920f7c93-b58c-3b1c-c6a9-9782ebf9514d",
			"e6950a2d-a6b5-11e0-b446-00251188dd67",
			"e2e72e81-a6b5-11e0-b446-00251188dd67",
			"40ff1c67-59ca-05d2-47f3-6b134e5012fa"
			};
	
	private String[] pop_rock = {};
	
	private String[] r_and_b = {};
	
	private String[] rap = {};
	
	private String[] reggae = {};
	
	public static void main(String[] args) throws Exception {
		
		//getArtists("e2ffceb5-a6b5-11e0-b446-00251188dd67", 0);
		
		//getAlbums("e2ffceb5-a6b5-11e0-b446-00251188dd67");
		
		//getTracks("0abface0-edd2-7c32-2380-5db7d8dad665");
		
		System.out.println("END");
		

	}

	/**
	 * Get a list of Tracks from an Album
	 * @param album_id
	 */
	private static void getTracks(String album_id) {

		try {
			String url = "http://api.musicgraph.com/api/v2/album/";
			url += album_id;
			url += "/tracks?api_key="+api_key+"&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
			if (responseCode != 200)
				throw new Exception("Some error");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(album_id+">name>"+arr.getJSONObject(i).get("title"));
					String temp_track_id = (String) arr.getJSONObject(i).get("id");
					System.out.println(album_id+">id>"+temp_track_id);
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getAlbums < DUDE ERROR!");
		}
		
	}

	/**
	 * Get a list of Albums for a given artist
	 * @param artist_id
	 */
	private static void getAlbums(String artist_id) {
		
		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/albums?api_key="+api_key+"&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
			if (responseCode != 200)
				throw new Exception("Some error");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(artist_id+">name>"+arr.getJSONObject(i).get("title"));
					String temp_album_id = (String) arr.getJSONObject(i).get("id");
					System.out.println(artist_id+">id>"+temp_album_id);
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getAlbums < DUDE ERROR!");
		}
	}

	/**
	 * Get a list of artists similar to given artist
	 * @param artist_id
	 * @param depth
	 */
	public static void getArtists(String artist_id, int depth) {
		
		if (depth > 3) {
			return;
		}
		
		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/similar?api_key="+api_key+"&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);
			if (responseCode != 200)
				throw new Exception("Some error");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			//System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(artist_id+">name>"+arr.getJSONObject(i).get("name"));
					String temp_artist_id = (String) arr.getJSONObject(i).get("id");
					System.out.println(artist_id+">id>"+temp_artist_id);
					getArtists(temp_artist_id, depth+1);
				}
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getArtists < DUDE ERROR!");
		}

	}

}
