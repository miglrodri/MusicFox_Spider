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
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

public class jenaImporter {

	private String inputFileName = "MusicOntology.owl";
	String SOURCE = "http://www.semanticweb.org/MusicOntology";
	private String NS = SOURCE + "#";
	private String outFileName = "MusicOntologyWithIndividuals.owl";

	void main() throws FileNotFoundException {

		final InputStream inputStream = FileManager.get().open(inputFileName);
		OntModel model = ModelFactory.createOntologyModel(
				OntModelSpec.OWL_DL_MEM, null);
		model.read(inputStream, SOURCE);

		try {
			FileInputStream fis = new FileInputStream("artists.ser");
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
			//TODO como vai ser isto??
			
			
			statements.add(model.createStatement(artistIndividual,model.getProperty("hasName"), name));
			statements.add(model.createStatement(artistIndividual,model.getProperty("hasMainGenre"), mainGenre));
			statements.add(model.createStatement(artistIndividual,model.getProperty("hasDecade"), decade));
			statements.add(model.createStatement(artistIndividual,model.getProperty("hasGender"), gender));
			model.add(statements);
		}
		
		return 0;
	}
	
	int populateAlbums(OntModel model, Map<?, ?> albumsMap) {

		OntClass album = model.getOntClass(NS + "Album");
		Property property = model.getProperty("isAssembledBy");
		
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
				
				statements.add(model.createStatement(albumIndividual, property, NS+id));
				statements.add(model.createStatement(albumIndividual,model.getProperty("hasDecade"), decade));
				statements.add(model.createStatement(albumIndividual, model.getProperty("hasReleaseDate"),releasedate));
				statements.add(model.createStatement(albumIndividual,model.getProperty("hasNumberOfTracks"), numberOfTracks));
				statements.add(model.createStatement(albumIndividual,model.getProperty("hasTitle"), title));
				model.add(statements);
			}
		}
		
		return 0;
	}
	
	int populateTracks(OntModel model, Map<?, ?> tracksMap) {

		OntClass track = model.getOntClass(NS + "Track");
		Property property = model.getProperty("isAssembledBy");

		for (Object _id : tracksMap.keySet()) {
			String id = (String) _id;
			Map<?, ?> values = (Map<?, ?>) tracksMap.get(_id);
			for(Object trackID : values.keySet())
			{
				Map<?, ?> trackValues = (Map<?, ?>) values.get(trackID);
				List<Statement> statements = new ArrayList<Statement>();
				
				Individual trackIndividual = track.createIndividual(NS + trackID);
				
				trackIndividual.addProperty(property, NS + id);
				Literal duration = model.createTypedLiteral(trackValues.get("duration"),XSDDatatype.XSDinteger);
				Literal trackindex = model.createTypedLiteral(trackValues.get("track_index"), XSDDatatype.XSDinteger);
				Literal title = model.createTypedLiteral(trackValues.get("title"),XSDDatatype.XSDstring);
				
				statements.add(model.createStatement(trackIndividual,model.getProperty("hasDuration"), duration));
				statements.add(model.createStatement(trackIndividual, model.getProperty("hasTrackIndex"),trackindex));
				statements.add(model.createStatement(trackIndividual,model.getProperty("hasTitle"), title));
				model.add(statements);
			}
		}

		return 0;
	}

}
