package ws_scrapper;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

public class Ws_scrapper {

	public static String api_key = "57716264bdd91bd35edc83d13f16f30c";

	private static String[] blues = { "e21ee849-a6b5-11e0-b446-00251188dd67",
			"e21ed16d-a6b5-11e0-b446-00251188dd67",
			"e21ed088-a6b5-11e0-b446-00251188dd67",
			"62766328-e08c-3180-fb0f-cb340d6b1e2d",
			"1b9b65bb-c8d3-effb-853a-47be252c43b4",
			"e576c7d8-a6b5-11e0-b446-00251188dd67",
			"d3f3164e-6e3d-e932-640e-95ea77d945ae",
			"e69bf71b-a6b5-11e0-b446-00251188dd67",
			"e59c4d0d-a6b5-11e0-b446-00251188dd67",
			"91b309ca-fbe3-344d-5eae-502f85b58864" };

	private static String[] country = { "e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"ef82ddea-a6b5-11e0-b446-00251188dd67",
			"83e62474-a62d-2b55-abe3-25f9ef802b00",
			"ee1a5c3d-a6b5-11e0-b446-00251188dd67",
			"e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"84cd6ddc-c91c-7451-7df0-944d838350a8",
			"e5bf687e-a6b5-11e0-b446-00251188dd67",
			"931539bf-8e62-c642-a116-9ee3e0758e43" };

	private static String[] vocal = { "e2ddeac8-a6b5-11e0-b446-00251188dd67",
			"e2ddea5d-a6b5-11e0-b446-00251188dd67",
			"e3a5e921-a6b5-11e0-b446-00251188dd67",
			"e6cb0fd2-a6b5-11e0-b446-00251188dd67",
			"e48a53da-a6b5-11e0-b446-00251188dd67",
			"f14f76cd-a6b5-11e0-b446-00251188dd67",
			"4913f54a-dee9-2dc0-9661-3b735e4d2894",
			"e2f68504-a6b5-11e0-b446-00251188dd67",
			"e75c61ce-a6b5-11e0-b446-00251188dd67",
			"b94000bd-2934-dfb0-0cc7-0986a885a572" };

	private static String[] electronic = {
			"9f78cb80-1b80-ab7f-dd2e-7e8098a6b246",
			"a4e6f413-4ac9-dac0-c61e-92c7325bc715",
			"e4dc5181-a6b5-11e0-b446-00251188dd67",
			"3d6d49d3-1c72-b0c6-955d-63cde8aff85c",
			"f13b441f-a6b5-11e0-b446-00251188dd67",
			"93d271a4-7a6d-b5a4-1d21-bdd633c18f5d",
			"fffb4e7e-17d8-8bc2-be44-d8516fb31ba0",
			"ee6a44bc-a6b5-11e0-b446-00251188dd67",
			"825aed91-519b-f6e8-4113-821c9a5823f2",
			"5a963b4a-66e4-763e-d88c-a4e972813099" };

	private static String[] jazz = { "e4be067b-a6b5-11e0-b446-00251188dd67",
			"9af98ec2-7774-7aa8-f7ce-11015444374c",
			"e6e62cda-a6b5-11e0-b446-00251188dd67",
			"6b9bfdad-8f75-8763-59c4-98fc6122efa5",
			"e6066461-0f1d-8412-9a50-bf7d5053aa75",
			"e24d0ca3-a6b5-11e0-b446-00251188dd67",
			"920f7c93-b58c-3b1c-c6a9-9782ebf9514d",
			"e6950a2d-a6b5-11e0-b446-00251188dd67",
			"e2e72e81-a6b5-11e0-b446-00251188dd67",
			"40ff1c67-59ca-05d2-47f3-6b134e5012fa" };

	private static String[] international = {
			"fb201948-a64a-5ada-22f3-ccf947efaa91",
			"e39e5de6-d83d-dd7b-d972-4a0ab0361e42",
			"ee25e0b0-a6b5-11e0-b446-00251188dd67",
			"f55f5af1-ad26-6b1f-0956-1269565af70d",
			"9f380701-a994-1262-2280-018208788ef9",
			"ea0b4c60-a6b5-11e0-b446-00251188dd67",
			"e5a4c8e4-a6b5-11e0-b446-00251188dd67",
			"edbbb7a9-a6b5-11e0-b446-00251188dd67",
			"a2814de7-1a09-b9dd-4bba-576950680b2b",
			"e2bc7654-cb40-b47c-6e2c-4615188a9c6b" };

	private static String[] pop_rock = {};

	private static String[] r_and_b = {};

	private static String[] rap = {};

	private static String[] reggae = {};

	public static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) throws Exception {

		// getArtists("e2ffceb5-a6b5-11e0-b446-00251188dd67", 0);

		// getAlbums("e2ffceb5-a6b5-11e0-b446-00251188dd67");

		// getTracks("0abface0-edd2-7c32-2380-5db7d8dad665");

		for (int i = 0; i < jazz.length; i++) {
			getArtists(jazz[i], 0);
			getArtists(blues[i], 0);
			getArtists(country[i], 0);
			getArtists(vocal[i], 0);
			getArtists(electronic[i], 0);
			getArtists(international[i], 0);
			
		}

		JSONObject jsonObject = new JSONObject(map);
		FileWriter file = new FileWriter("file1.txt");
		try {
			file.write(jsonObject.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + jsonObject);
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			file.flush();
			file.close();
		}

		System.out.println("END");

	}

	/**
	 * Get a list of Tracks from an Album
	 * 
	 * @param album_id
	 */
	private static void getTracks(String album_id) {

		try {
			String url = "http://api.musicgraph.com/api/v2/album/";
			url += album_id;
			url += "/tracks?api_key=" + api_key + "&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			// System.out.println("\nSending 'GET' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);
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
			// System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(album_id + ">name>"
							+ arr.getJSONObject(i).get("title"));
					String temp_track_id = (String) arr.getJSONObject(i).get(
							"id");
					System.out.println(album_id + ">id>" + temp_track_id);
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
	 * 
	 * @param artist_id
	 */
	private static void getAlbums(String artist_id) {

		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/albums?api_key=" + api_key + "&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			// System.out.println("\nSending 'GET' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);
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
			// System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					System.out.println(artist_id + ">name>"
							+ arr.getJSONObject(i).get("title"));
					String temp_album_id = (String) arr.getJSONObject(i).get(
							"id");
					System.out.println(artist_id + ">id>" + temp_album_id);
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
	 * 
	 * @param artist_id
	 * @param depth
	 */
	public static void getArtists(String artist_id, int depth) {

		if (depth > 5) {
			return;
		}

		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/similar?api_key=" + api_key + "&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			// System.out.println("\nSending 'GET' request to URL : " + url);
			// System.out.println("Response Code : " + responseCode);
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
			// System.out.println(response.toString());

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					String temp_artist_name = (String) arr.getJSONObject(i)
							.get("name");
					System.out.println(artist_id + ">name>" + temp_artist_name);
					String temp_artist_id = (String) arr.getJSONObject(i).get(
							"id");
					System.out.println(artist_id + ">id>" + temp_artist_id);
					if (!map.containsKey(temp_artist_id))
						map.put(temp_artist_id, temp_artist_name);
					getArtists(temp_artist_id, depth + 1);
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
