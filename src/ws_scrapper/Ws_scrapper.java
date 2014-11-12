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
