package ws_scrapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

public class jenaImporter {

	private String inputFileName = "MusicOntology.owl";
	private String SOURCE = "http://www.semanticweb.org/MusicOntology";
	private String NS = SOURCE + "#";
	private String outFileName = "MusicOntologyWithIndividuals.owl";
	private final InputStream inputStream = FileManager.get().open(inputFileName);
	private final OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM, null);
	private ObjectProperty isAssembledBy, producesAlbum, isPartOf, hasTrack, isCreatedBy, writesTrack;
	private Property hasVevoUrl;
	private Property hasVevoViewsLastMonth;
	private Property hasVevoViewsTotal;
	private Property hasTwitterUrl;
	private Property hasTwitterFollowers;
	private Property hasFacebookUrl;
	private Property hasFacebookPeopleTalkingAbout;
	private Property hasFacebookLikes;
	private Property hasLastFMUrl;
	private Property hasLastFMListeners;
	private Property hasLastFMPlayCount;
	
	void main() throws FileNotFoundException {

		try {
			model.read(inputStream, SOURCE);
			
			isAssembledBy = model.getObjectProperty(NS+"isAssembledBy");
			producesAlbum = model.getObjectProperty(NS+"producesAlbum");
			isPartOf = model.getObjectProperty(NS+"isPartOf");
			hasTrack = model.getObjectProperty(NS+"hasTrack");
			isCreatedBy = model.getObjectProperty(NS+"isCreatedBy");
			writesTrack = model.getObjectProperty(NS+"writesTrack");
			
			hasVevoUrl = model.getProperty(NS+"hasVevoUrl");
			hasVevoViewsLastMonth = model.getProperty(NS+"hasVevoViewsLastMonth");
			hasVevoViewsTotal = model.getProperty(NS+"hasVevoViewsTotal");
			
			hasTwitterUrl = model.getProperty(NS+"hasTwitterUrl");
			hasTwitterFollowers = model.getProperty(NS+"hasTwitterFollowers");
			
			hasFacebookUrl = model.getProperty(NS+"hasFacebookUrl");
			hasFacebookPeopleTalkingAbout = model.getProperty(NS+"hasFacebookPeopleTalkingAbout");
			hasFacebookLikes = model.getProperty(NS+"hasFacebookLikes");
			
			hasLastFMUrl = model.getProperty(NS+"hasLastFMUrl");
			hasLastFMListeners = model.getProperty(NS+"hasLastFMListeners");
			hasLastFMPlayCount = model.getProperty(NS+"hasLastFMPlayCount");
			
			
			FileInputStream fis = new FileInputStream("_artists.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Map<?, ?> artistMap = (Map<?, ?>) ois.readObject();
			ois.close();
			fis.close();

			fis = new FileInputStream("stats.ser");
			ois = new ObjectInputStream(fis);
			Map<?, ?> statsMap = (Map<?, ?>) ois.readObject();
			ois.close();
			fis.close();
			
			populateArtists(model, artistMap, statsMap);
			
			fis = new FileInputStream("albums.ser");
			ois = new ObjectInputStream(fis);
			Map<?, ?> albumsMap = (Map<?, ?>) ois.readObject();
			ois.close();
			fis.close();
			
			populateAlbums(model, albumsMap);
			
			fis = new FileInputStream("tracks.ser");
			ois = new ObjectInputStream(fis);
			Map<?, ?> tracksMap = (Map<?, ?>) ois.readObject();
			ois.close();
			fis.close();
			
			populateTracks(model, tracksMap);

			FileWriter out = new FileWriter(outFileName);
			model.write( out, "Turtle" );
			
		
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}

	}

	@SuppressWarnings("unchecked")
	int populateArtists(OntModel model, Map<?, ?> artistMap, Map<?, ?> statsMap) {

		OntClass artist = model.getOntClass(NS + "Artist");

		for (Object _id : artistMap.keySet()) {
			Map<?, ?> values = (Map<?, ?>) artistMap.get(_id);
			List<Statement> statements = new ArrayList<Statement>();
			String id = (String) _id;
			
			Individual artistIndividual = artist.createIndividual(NS + id);
			
			Literal name = model.createTypedLiteral(values.get("name"),XSDDatatype.XSDstring);
			Literal mainGenre = model.createTypedLiteral(values.get("main_genre"),XSDDatatype.XSDstring);
			Literal decade = model.createTypedLiteral(values.get("decade"),XSDDatatype.XSDstring);
			Literal gender = model.createTypedLiteral(values.get("gender"),XSDDatatype.XSDstring);
			
			values = (Map<?, ?>) statsMap.get(_id);
			for(Object e : statsMap.keySet()){
				switch((String)e) {
				case "vevo":
					Map<String, String> vevoValues = (Map<String, String>) statsMap.get("vevo");
					Literal url = model.createTypedLiteral(vevoValues.get("url"),XSDDatatype.XSDstring);
					Literal viewsLastMonth = model.createTypedLiteral(vevoValues.get("viewsLastMonth"),XSDDatatype.XSDinteger);
					Literal viewsTotal = model.createTypedLiteral(vevoValues.get("viewsTotal"),XSDDatatype.XSDinteger);
					
					Resource vevoStats = model.createResource("vevoStats").addProperty(hasVevoUrl, url).addProperty(hasVevoViewsLastMonth, viewsLastMonth).addProperty(hasVevoViewsTotal, viewsTotal);
					statements.add(model.createStatement(artistIndividual,model.getProperty(NS+"hasVevoStats"), vevoStats));
					
					break;
				case "twitter":
					Map<String, String> twitterValues = (Map<String, String>) statsMap.get("twitter");
					url = model.createTypedLiteral(twitterValues.get("url"),XSDDatatype.XSDstring);
					Literal followers = model.createTypedLiteral(twitterValues.get("followers"),XSDDatatype.XSDinteger);
					Resource twitterStats = model.createResource("twitterStats").addProperty(hasTwitterUrl, url).addProperty(hasTwitterFollowers, followers);
							
					statements.add(model.createStatement(artistIndividual, model.getProperty(NS+"hasTwitterStats"), twitterStats));
					break;
				case "facebook":
					Map<String, String> fbValues = (Map<String, String>) statsMap.get("facebook");
					url = model.createTypedLiteral(fbValues.get("url"),XSDDatatype.XSDstring);
					Literal fblikes = model.createTypedLiteral(fbValues.get("likes"),XSDDatatype.XSDinteger);
					Literal people_talking = model.createTypedLiteral(fbValues.get("people_talking_about"),XSDDatatype.XSDinteger);
					Resource facebookStats = model.createResource("facebookStats").addProperty(hasFacebookUrl, url).addProperty(hasFacebookPeopleTalkingAbout, people_talking).addProperty(hasFacebookLikes, fblikes);
					statements.add(model.createStatement(artistIndividual, model.getProperty(NS+"hasFacebookStats"), facebookStats));
					
					break;
				case "lastfm":
					Map<String, String> lfValues = (Map<String, String>) statsMap.get("lastfm");
					url = model.createTypedLiteral(lfValues.get("url"),XSDDatatype.XSDstring);
					Literal listeners = model.createTypedLiteral(lfValues.get("listeners"),XSDDatatype.XSDinteger);
					Literal playcount = model.createTypedLiteral(lfValues.get("playcount"),XSDDatatype.XSDinteger);
					Resource lastfmStats = model.createResource("lastfmStats").addProperty(hasLastFMUrl, url).addProperty(hasLastFMListeners, listeners).addProperty(hasLastFMPlayCount, playcount);
					statements.add(model.createStatement(artistIndividual, model.getProperty(NS+"hasLastFMStats"), lastfmStats));
					
					break;
				}
			}
			statements.add(model.createStatement(artistIndividual,model.getProperty(NS+"hasName"), name));
			statements.add(model.createStatement(artistIndividual,model.getProperty(NS+"hasMainGenre"), mainGenre));
			statements.add(model.createStatement(artistIndividual,model.getProperty(NS+"hasDecade"), decade));
			statements.add(model.createStatement(artistIndividual,model.getProperty(NS+"hasGender"), gender));
			model.add(statements);
		}
		
		return 0;
	}
	
	int populateAlbums(OntModel model, Map<?, ?> albumsMap) {

		OntClass album = model.getOntClass(NS + "Album");
		
		for (Object _id : albumsMap.keySet()) {	
			String id = (String) _id;
			Map<?, ?> values = (Map<?, ?>) albumsMap.get(_id);
			for(Object albumID : values.keySet())
			{
				Map<?, ?> albumValues = (Map<?, ?>) values.get(albumID);
				List<Statement> statements = new ArrayList<Statement>();
				
				Individual albumIndividual = album.createIndividual(NS + albumID);
				
				Literal decade = model.createTypedLiteral(albumValues.get("decade"),XSDDatatype.XSDstring);
				Literal numberOfTracks = model.createTypedLiteral(albumValues.get("number_of_tracks"), XSDDatatype.XSDinteger);
				Literal releasedate = model.createTypedLiteral(albumValues.get("release_date"), XSDDatatype.XSDdateTime);
				Literal title = model.createTypedLiteral(albumValues.get("title"),XSDDatatype.XSDstring);
				
				statements.add(model.createStatement(albumIndividual, isAssembledBy, model.getIndividual(NS+id)));
				
				statements.add(model.createStatement(model.getIndividual(NS+id), producesAlbum, albumIndividual));
				statements.add(model.createStatement(albumIndividual,model.getDatatypeProperty(NS+"hasDecade"), decade));
				statements.add(model.createStatement(albumIndividual, model.getDatatypeProperty(NS+"hasReleaseDate"),releasedate));
				statements.add(model.createStatement(albumIndividual,model.getDatatypeProperty(NS+"hasNumberOfTracks"), numberOfTracks));
				statements.add(model.createStatement(albumIndividual,model.getDatatypeProperty(NS+"hasTitle"), title));
				model.add(statements);
			}
		}
		
		return 0;
	}
	
	int populateTracks(OntModel model, Map<?, ?> tracksMap) {

		OntClass track = model.getOntClass(NS + "Track");
	
		
		for (Object _id : tracksMap.keySet()) {
			String id = (String) _id;
			Map<?, ?> values = (Map<?, ?>) tracksMap.get(_id);
			for(Object trackID : values.keySet())
			{
				Map<?, ?> trackValues = (Map<?, ?>) values.get(trackID);
				List<Statement> statements = new ArrayList<Statement>();
				
				Individual trackIndividual = track.createIndividual(NS + trackID);
				// TODO get artist individual to set object properties
				Individual artistIndividual = model.getIndividual(NS+trackValues.get("artist_id"));
				
				Literal duration = model.createTypedLiteral(trackValues.get("duration"),XSDDatatype.XSDinteger);
				Literal trackindex = model.createTypedLiteral(trackValues.get("track_index"), XSDDatatype.XSDinteger);
				Literal title = model.createTypedLiteral(trackValues.get("title"),XSDDatatype.XSDstring);
				
				statements.add(model.createStatement(trackIndividual, isPartOf, model.getIndividual(NS+id)));
				statements.add(model.createStatement(model.getIndividual(NS+id), hasTrack, trackIndividual));
				statements.add(model.createStatement(artistIndividual, writesTrack, trackIndividual));
				statements.add(model.createStatement(trackIndividual, isCreatedBy, artistIndividual));
				
				statements.add(model.createStatement(trackIndividual,model.getDatatypeProperty(NS+"hasDuration"), duration));
				statements.add(model.createStatement(trackIndividual, model.getDatatypeProperty(NS+"hasTrackIndex"),trackindex));
				statements.add(model.createStatement(trackIndividual,model.getDatatypeProperty(NS+"hasTitle"), title));
				model.add(statements);
			}
		}

		return 0;
	}

}
