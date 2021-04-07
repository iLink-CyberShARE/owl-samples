package cybershare.utep.edu;

import java.io.File;
import java.io.IOException;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final String PEOPLE_IRI = "http://owl.man.ac.uk/2006/07/sssw/people.owl";
    private static final String PEOPLE_AND_PETS = "C:/Users/lagarnicachavira/Documents/Repos/owl-samples/ontologies/PeopleAndPets.owl";
    private static final String PET_TAXONOMY = "C:/Users/lagarnicachavira/Documents/Repos/owl-samples/ontologies/pet-ncbi-taxonomy.owl";
    private static final String BASE = "http://www.semanticweb.org/nvillanuevarosales/people-pets.owl";


    public static void main( String[] args )
    {
        // System.out.println( "Hello World!" );

        OWLOntologyManager manager = createManager();
        try {
            OWLOntology ontology = loadOntologyFromFile(PEOPLE_AND_PETS, manager);
            OWLDataFactory dataFactory = manager.getOWLDataFactory();
            addClassIndividual(ontology, manager, dataFactory, "Ana", "Person");
            addClassIndividual(ontology, manager, dataFactory, "Benji", "Dog");
            addObjectPropertyAssertion(ontology, manager, dataFactory, "Ana", "Benji", "hasPet");
            saveOntology("C:/Users/lagarnicachavira/Documents/Repos/owl-samples/ontologies/PeopleAndPetsNew.owl", manager, ontology);
        } catch (OWLOntologyCreationException | OWLOntologyStorageException | IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a new ontology manager
     * @return
     */
    public static OWLOntologyManager createManager(){
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager;
    }

    /**
     * Load an ontology to memory from URI source
     * @param source
     * @param manager
     */
    public static OWLOntology loadOntologyFromURI(String source, OWLOntologyManager manager) throws OWLOntologyCreationException {
        IRI iri = IRI.create(source);
        OWLOntology ontology;
        ontology = manager.loadOntologyFromOntologyDocument(iri);
        System.out.println("Loaded ontology: " + ontology);
        return ontology;
    }

    /**
     * Load ontology from local file
     * @param path
     * @param manager
     * @return
     * @throws OWLOntologyCreationException
     */
    public static OWLOntology loadOntologyFromFile(String path, OWLOntologyManager manager) throws OWLOntologyCreationException {
        File file = new File(path);
        // Load local ontology
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        System.out.println("Loaded ontology: " + ontology);
        // We can always obtain the location where an ontology was loaded from
        IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
        System.out.println("    from: " + documentIRI);
        return ontology;
    }


    /**
     * Insert individual to a class
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param subjectName
     * @param className
     * @return
     */
    public static void addClassIndividual(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory, String subjectName, String className){
        OWLIndividual subject = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + subjectName));
        OWLClass someClass = dataFactory.getOWLClass(IRI.create(BASE + "#" + className));
        OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(someClass, subject);
        manager.addAxiom(ontology, ax);
    }

    /**
     * Add object property assertion (between individuals)
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param individual_1
     * @param individual_2
     * @param property
     */
    public static void addObjectPropertyAssertion(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory, String individual_1, String individual_2, String property){
        OWLIndividual i1 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_1));
        OWLIndividual i2 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_2));
        OWLObjectProperty o = dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));

        OWLAxiom assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(o, i1, i2);
        manager.addAxiom(ontology, assertion);
    }


    /**
     * save ontology locally to specific path
     * @param path
     * @param manager
     * @param ontology
     * @throws IOException
     * @throws OWLOntologyStorageException
     */
    public static void saveOntology(String path, OWLOntologyManager manager, OWLOntology ontology) 
        throws IOException, OWLOntologyStorageException {
        File file = new File(path);

        // default format as they are loaded e.g. xml, turtle
        manager.saveOntology(ontology, IRI.create(file.toURI()));
        System.out.println("Ontology was saved");
    }

}
