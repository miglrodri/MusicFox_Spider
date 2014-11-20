package ws_scrapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ws_scrapper {

	public static void main(String[] args) throws Exception {

		new Scraper().start();

	}
}

/**
 * Class for scraping
 * 
 */
class Scraper extends Thread {

	private String api_key = "1048b6a5acdb76adc78d351f04b96242";
	
	//private String api_key = "57716264bdd91bd35edc83d13f16f30c";

	String[] blues = { "e21ee849-a6b5-11e0-b446-00251188dd67",
			"e21ed16d-a6b5-11e0-b446-00251188dd67",
			"e21ed088-a6b5-11e0-b446-00251188dd67",
			"62766328-e08c-3180-fb0f-cb340d6b1e2d",
			"1b9b65bb-c8d3-effb-853a-47be252c43b4",
			"e576c7d8-a6b5-11e0-b446-00251188dd67",
			"d3f3164e-6e3d-e932-640e-95ea77d945ae",
			"e69bf71b-a6b5-11e0-b446-00251188dd67",
			"e59c4d0d-a6b5-11e0-b446-00251188dd67",
			"91b309ca-fbe3-344d-5eae-502f85b58864" };

	String[] country = { "e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"ef82ddea-a6b5-11e0-b446-00251188dd67",
			"83e62474-a62d-2b55-abe3-25f9ef802b00",
			"ee1a5c3d-a6b5-11e0-b446-00251188dd67",
			"e45ba6f6-a6b5-11e0-b446-00251188dd67",
			"ea73553f-a6b5-11e0-b446-00251188dd67",
			"84cd6ddc-c91c-7451-7df0-944d838350a8",
			"e5bf687e-a6b5-11e0-b446-00251188dd67",
			"931539bf-8e62-c642-a116-9ee3e0758e43" };

	String[] vocal = { "e2ddeac8-a6b5-11e0-b446-00251188dd67",
			"e2ddea5d-a6b5-11e0-b446-00251188dd67",
			"e3a5e921-a6b5-11e0-b446-00251188dd67",
			"e6cb0fd2-a6b5-11e0-b446-00251188dd67",
			"e48a53da-a6b5-11e0-b446-00251188dd67",
			"f14f76cd-a6b5-11e0-b446-00251188dd67",
			"4913f54a-dee9-2dc0-9661-3b735e4d2894",
			"e2f68504-a6b5-11e0-b446-00251188dd67",
			"e75c61ce-a6b5-11e0-b446-00251188dd67",
			"b94000bd-2934-dfb0-0cc7-0986a885a572" };

	String[] electronic = { "9f78cb80-1b80-ab7f-dd2e-7e8098a6b246",
			"a4e6f413-4ac9-dac0-c61e-92c7325bc715",
			"e4dc5181-a6b5-11e0-b446-00251188dd67",
			"3d6d49d3-1c72-b0c6-955d-63cde8aff85c",
			"f13b441f-a6b5-11e0-b446-00251188dd67",
			"93d271a4-7a6d-b5a4-1d21-bdd633c18f5d",
			"fffb4e7e-17d8-8bc2-be44-d8516fb31ba0",
			"ee6a44bc-a6b5-11e0-b446-00251188dd67",
			"825aed91-519b-f6e8-4113-821c9a5823f2",
			"5a963b4a-66e4-763e-d88c-a4e972813099" };

	String[] jazz = { "e4be067b-a6b5-11e0-b446-00251188dd67",
			"9af98ec2-7774-7aa8-f7ce-11015444374c",
			"e6e62cda-a6b5-11e0-b446-00251188dd67",
			"6b9bfdad-8f75-8763-59c4-98fc6122efa5",
			"e6066461-0f1d-8412-9a50-bf7d5053aa75",
			"e24d0ca3-a6b5-11e0-b446-00251188dd67",
			"920f7c93-b58c-3b1c-c6a9-9782ebf9514d",
			"e6950a2d-a6b5-11e0-b446-00251188dd67",
			"e2e72e81-a6b5-11e0-b446-00251188dd67",
			"40ff1c67-59ca-05d2-47f3-6b134e5012fa" };

	String[] international = { "fb201948-a64a-5ada-22f3-ccf947efaa91",
			"e39e5de6-d83d-dd7b-d972-4a0ab0361e42",
			"ee25e0b0-a6b5-11e0-b446-00251188dd67",
			"f55f5af1-ad26-6b1f-0956-1269565af70d",
			"9f380701-a994-1262-2280-018208788ef9",
			"ea0b4c60-a6b5-11e0-b446-00251188dd67",
			"e5a4c8e4-a6b5-11e0-b446-00251188dd67",
			"edbbb7a9-a6b5-11e0-b446-00251188dd67",
			"a2814de7-1a09-b9dd-4bba-576950680b2b",
			"e2bc7654-cb40-b47c-6e2c-4615188a9c6b" };

	String[] pop_rock = { "e2ffceb5-a6b5-11e0-b446-00251188dd67",
			"e5cae432-a6b5-11e0-b446-00251188dd67",
			"789aaf1e-d05e-2e77-7148-2cad9b96d558",
			"acfc1a73-fd8b-4a3f-dc48-c56c3e57fc4e",
			"e22e4bfb-a6b5-11e0-b446-00251188dd67",
			"e7a06e67-a6b5-11e0-b446-00251188dd67",
			"169f779e-e7fe-9006-2426-13b7dbcdda6c",
			"87696308-1518-38ca-29e1-bab8caf120ba",
			"0bd17ae1-cb85-2d3f-28d2-35182f9a0f25",
			"333caa00-4211-ce3d-e60c-d07dd0d954c8" };

	String[] r_and_b = { "b59a61ed-5be7-5694-bbc4-160523e4abe8",
			"6cb4bc22-71a6-9f57-193a-e88aae92b975",
			"dc78ddd1-f2f4-1b29-f7db-7627018688e7",
			"eb63fd6f-a6b5-11e0-b446-00251188dd67",
			"f50e6ec0-b477-5521-82ce-a35527d50244",
			"e6b1441e-a6b5-11e0-b446-00251188dd67",
			"62e4e2d5-b34e-4712-01f0-ec879c6251ab",
			"e2ffcd91-a6b5-11e0-b446-00251188dd67",
			"815bed6b-d7bb-eccc-f8fe-aaae44086f43",
			"e211bf6b-a6b5-11e0-b446-00251188dd67" };

	String[] rap = { "65951b5d-f6f6-898a-0e85-9f2dc8f7e888",
			"3d8ace70-6493-f175-b6a8-1a2a4fc1a37b",
			"4fedd4c4-fbf2-16f1-dd3d-2cf446d0e986",
			"d64a0494-3777-4e5f-f3d2-d5310e30a88c",
			"e4484087-a6b5-11e0-b446-00251188dd67",
			"e310a0db-a6b5-11e0-b446-00251188dd67",
			"3dbbeb4c-0d81-39d2-84d6-cecc21c5cedd",
			"82cea060-ac73-45d8-75a4-9a037a16baac",
			"c42e44a0-b945-1e4f-2e71-09cfb932e1e5",
			"e6b149bb-a6b5-11e0-b446-00251188dd67" };

	String[] reggae = { "e2059f5a-a6b5-11e0-b446-00251188dd67",
			"236c28d0-0b32-cb78-e995-6a467ff4c8ec",
			"e2150a9b-a6b5-11e0-b446-00251188dd67",
			"e5bf3ed5-a6b5-11e0-b446-00251188dd67",
			"22291377-193a-aab8-ad55-6611e3994774",
			"6b2cdc25-6b22-06ca-d7bf-03eb40156026",
			"e5d199c1-a6b5-11e0-b446-00251188dd67",
			"e5383fdd-a6b5-11e0-b446-00251188dd67",
			"ef0f4e6a-a6b5-11e0-b446-00251188dd67",
			"63fff093-cad6-847f-f054-3e08d9fc5190" };

	@SuppressWarnings("rawtypes")
	private Map<String, Map> artistMap = new HashMap<String, Map>();
	@SuppressWarnings("rawtypes")
	private Map<String, Map> albumMap = new HashMap<String, Map>();
	@SuppressWarnings("rawtypes")
	private Map<String, Map> trackMap = new HashMap<String, Map>();

	/**
	 * Thread start point
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void run() {
		try {
			System.out.println("SCRAPPING ARTISTS");

			for (int i = 0; i < jazz.length; i++) {
				getArtists(jazz[i], 0);
				getArtists(blues[i], 0);
				getArtists(country[i], 0);
				getArtists(vocal[i], 0);
				getArtists(electronic[i], 0);
				getArtists(international[i], 0);
				getArtists(pop_rock[i], 0);
				getArtists(r_and_b[i], 0);
				getArtists(rap[i], 0);
				getArtists(reggae[i], 0);
			}

			System.out.println("END SCRAPPING: got " + artistMap.size()
					+ " entries");

			/*JSONObject jsonArtists = new JSONObject(artistMap);
			FileWriter fileArtists = new FileWriter("fileArtists.json");
			fileArtists.write(jsonArtists.toString());
			fileArtists.close();*/
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("artists.ser"));
			oos.writeObject(artistMap);
			oos.flush();
			oos.close();

			System.out.println("SCRAPPING ALBUMS");

			for (String key : artistMap.keySet()) {
				Map temp = getAlbums(key);
				albumMap.put(key, temp);
			}

			System.out.println("END SCRAPPING: got " + albumMap.size()
					+ " entries");

			/*JSONObject jsonAlbums = new JSONObject(albumMap);
			FileWriter fileAlbums = new FileWriter("fileAlbums.json");
			fileAlbums.write(jsonAlbums.toString());
			fileAlbums.close();*/
			oos = new ObjectOutputStream(new FileOutputStream("albums.ser"));
			oos.writeObject(albumMap);
			oos.flush();
			oos.close();

			System.out.println("SCRAPPING TRACKS");

			for (String key : albumMap.keySet()) {
				for (Object o : albumMap.get(key).keySet()) {
					Map temp = getTracks((String) o);
					trackMap.put((String) o, temp);
				}
			}

			System.out.println("END SCRAPPING: got " + trackMap.size()
					+ " entries");

			/*JSONObject jsonTracks = new JSONObject(trackMap);
			FileWriter fileTracks = new FileWriter("fileTracks.json");
			fileTracks.write(jsonTracks.toString());
			fileTracks.close();*/
			oos = new ObjectOutputStream(new FileOutputStream("tracks.ser"));
			oos.writeObject(trackMap);
			oos.flush();
			oos.close();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Builds a Map from a JSONObject
	 * 
	 * @param object
	 * @return Map<String, ?>
	 * @throws JSONException
	 */
	public Map<String, ?> toMap(JSONObject object) throws JSONException {

		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> keysItr = object.keys();

		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	/**
	 * Get a list from a JSONArray
	 * 
	 * @param array
	 * @return List<?>
	 * @throws JSONException
	 */
	public List<?> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	/**
	 * Get a list of Tracks from an Album
	 * 
	 * @param album_id
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Map> getTracks(String album_id)
			throws InterruptedException {

		Map<String, Map> temp = new HashMap<String, Map>();

		try {
			String url = "http://api.musicgraph.com/api/v2/album/";
			url += album_id;
			url += "/tracks?api_key=" + api_key + "&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();

			if (responseCode != 200)
				throw new Exception("Error " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					if(arr.getJSONObject(i).has("track_index") && arr.getJSONObject(i).has("duration")){
						Map<String, String> aux = new HashMap<String, String>();
	
						String temp_title = "" + arr.getJSONObject(i).get("title");
						// System.out.println(album_id + ">name>"+ temp_title);
						String temp_track_id = (String) arr.getJSONObject(i).get("id");
						// System.out.println(album_id + ">id>" + temp_track_id);
	
						String temp_trackindex = ""
								+ arr.getJSONObject(i).get("track_index");
						String temp_duration = ""
								+ arr.getJSONObject(i).get("duration");
	
						aux.put("title", temp_title);
						aux.put("track_index", temp_trackindex);
						aux.put("duration", temp_duration);
	
						temp.put(temp_track_id, aux);
					}
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getAlbums < DUDE ERROR!" + e.getMessage());
			sleep(2000);
		}

		return temp;
	}

	/**
	 * Get a list of Albums for a given artist
	 * 
	 * @param artist_id
	 * @throws InterruptedException
	 */
	@SuppressWarnings("rawtypes")
	private Map<String, Map> getAlbums(String artist_id)
			throws InterruptedException {

		Map<String, Map> temp = new HashMap<String, Map>();

		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/albums?api_key=" + api_key + "&limit=10";

			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();

			if (responseCode != 200)
				throw new Exception("Error " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					if(arr.getJSONObject(i).has("release_date") && arr.getJSONObject(i).has("number_of_tracks") && arr.getJSONObject(i).has("decade")){
						Map<String, String> aux = new HashMap<String, String>();
	
						String temp_title = "" + arr.getJSONObject(i).get("title");
						// System.out.println(artist_id + ">name>"+ temp_title);
						String temp_album_id = (String) arr.getJSONObject(i).get("id");
						// System.out.println(artist_id + ">id>" + temp_album_id);
						String temp_releasedate = ""
								+ arr.getJSONObject(i).get("release_date");
						String temp_numberoftracks = ""
								+ arr.getJSONObject(i).get("number_of_tracks");
						String temp_decade = ""
								+ arr.getJSONObject(i).get("decade");
	
						aux.put("title", temp_title);
						aux.put("release_date", temp_releasedate);
						aux.put("number_of_tracks", temp_numberoftracks);
						aux.put("decade", temp_decade);
	
						if (!temp_numberoftracks.equals("0"))
							temp.put(temp_album_id, aux);
					}
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getAlbums < DUDE ERROR!" + e.getMessage());
			sleep(500);
		}

		return temp;
	}

	/**
	 * Get a list of artists similar to given artist
	 * 
	 * @param artist_id
	 * @param depth
	 */
	@SuppressWarnings("rawtypes")
	public void getArtists(String artist_id, int depth) {

		if (depth > 1) {
			return;
		}

		Map<String, String> tempMap = new HashMap<String, String>();

		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/similar?api_key=" + api_key + "&limit=1";

			System.out.println(url);
			
			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();

			if (responseCode != 200)
				throw new Exception("Error " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			//System.out.println(response.toString());

			if (message.equals("Success")) {
				JSONArray arr = obj.getJSONArray("data");
				for (int i = 0; i < arr.length(); i++) {
					if(arr.getJSONObject(i).has("main_genre") && arr.getJSONObject(i).has("decade") && arr.getJSONObject(i).has("gender")){
						String temp_artist_name = ""
								+ arr.getJSONObject(i).get("name");
						// System.out.println(artist_id + ">name>" +
						// temp_artist_name);
						String temp_artist_id = (String) arr.getJSONObject(i).get(
								"id");
						// System.out.println(artist_id + ">id>" + temp_artist_id);
						String temp_maingenre = ""
						 		+ arr.getJSONObject(i).get("main_genre");
						String temp_decade = ""
								+ arr.getJSONObject(i).get("decade");
	
						String temp_gender = ""
								+ arr.getJSONObject(i).get("gender");
	
						Map<String, Map> temp_stats = getStats(temp_artist_id);
	
						tempMap.put("name", temp_artist_name);
						tempMap.put("main_genre", temp_maingenre);
						tempMap.put("decade", temp_decade);
						tempMap.put("gender", temp_gender);
	
						if (!artistMap.containsKey(temp_artist_id)) {
							artistMap.put(temp_artist_id, tempMap);
							artistMap.put(temp_artist_id, temp_stats);
						}
						// getArtists(temp_artist_id, depth + 1);
					}
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getArtists < DUDE ERROR!" + e.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	private Map<String, Map> getStats(String artist_id)
			throws InterruptedException {

		Map<String, Map> temp = new HashMap<String, Map>();

		try {
			String url = "http://api.musicgraph.com/api/v2/artist/";
			url += artist_id;
			url += "/metrics?api_key=" + api_key;

			//System.out.println(url);
			
			URL obj_url = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj_url
					.openConnection();

			con.setRequestMethod("GET");

			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();

			if (responseCode != 200)
				throw new Exception("Error " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONObject obj = new JSONObject(response.toString());
			String message = obj.getJSONObject("status").getString("message");

			if (message.equals("Success")) {

				Map<String, String> aux = new HashMap<String, String>();

				JSONObject temp_data = obj.getJSONObject("data");

				if (temp_data.get("vevo") != JSONObject.NULL) {
					JSONObject vevo = temp_data.getJSONObject("vevo");

					if (vevo.has("url") && vevo.has("viewsLastMonth")
							&& vevo.has("viewsTotal")) {
						String temp_url = "" + vevo.get("url");
						String temp_viewslastmonth = ""
								+ vevo.getJSONObject("viewsLastMonth")
										.getJSONObject("data").get("value");
						String temp_viewstotal = ""
								+ vevo.getJSONObject("viewsTotal")
										.getJSONObject("data").get("value");
						aux.put("url", temp_url);
						aux.put("viewsLastMonth", temp_viewslastmonth);
						aux.put("viewsTotal", temp_viewstotal);
						temp.put("vevo", aux);
					}
				}

				aux = new HashMap<String, String>();

				if (temp_data.get("twitter") != JSONObject.NULL) {
					JSONObject twitter = temp_data.getJSONObject("twitter");

					if (twitter.has("url") && twitter.has("followers")) {

						String temp_url = "" + twitter.get("url");
						String temp_followers = ""
								+ twitter.getJSONObject("followers")
										.getJSONObject("data").get("value");
						aux.put("url", temp_url);
						aux.put("followers", temp_followers);
						temp.put("twitter", aux);
					}
				}

				aux = new HashMap<String, String>();

				if (temp_data.get("facebook") != JSONObject.NULL) {
					JSONObject facebook = temp_data.getJSONObject("facebook");

					if (facebook.has("url")
							&& facebook.has("people_talking_about")
							&& facebook.has("likes")) {

						String temp_url = "" + facebook.get("url");
						String temp_people_talking_about = ""
								+ facebook
										.getJSONObject("people_talking_about")
										.getJSONObject("data").get("value");
						String temp_likes = ""
								+ facebook.getJSONObject("likes")
										.getJSONObject("data").get("value");
						aux.put("url", temp_url);
						aux.put("people_talking_about",
								temp_people_talking_about);
						aux.put("likes", temp_likes);
						temp.put("facebook", aux);
					}
				}

				aux = new HashMap<String, String>();

				if (temp_data.get("lastfm") != JSONObject.NULL) {
					JSONObject lastfm = temp_data.getJSONObject("lastfm");

					if (lastfm.has("url") && lastfm.has("listeners")
							&& lastfm.has("playcount")) {

						String temp_url = "" + lastfm.get("url");
						String temp_listeners = ""
								+ lastfm.getJSONObject("listeners")
										.getJSONObject("data").get("value");
						String temp_playcount = ""
								+ lastfm.getJSONObject("playcount")
										.getJSONObject("data").get("value");
						aux.put("url", temp_url);
						aux.put("listeners", temp_listeners);
						aux.put("playcount", temp_playcount);
						temp.put("lastfm", aux);
					}
				}

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getStats < DUDE ERROR!" + e.getMessage());
			e.printStackTrace();
			sleep(500);
		}

		return temp;

	}
}
