package com.owl;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.rules.model.Rule;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.Version;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class ControllerOntology {

    public ControllerOntology() throws OWLOntologyStorageException, IOException {

        try {
            OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager();
            File ontologyOwlFile = new File("C:\\Users\\Admin\\Desktop\\exa.owl");
            OWLOntology ontology = owlManager.loadOntologyFromOntologyDocument(ontologyOwlFile);
            OWLDataFactory factory = owlManager.getOWLDataFactory();
            PelletReasonerFactory pelletReasonerFactory = PelletReasonerFactory.getInstance();
            PelletReasoner pelletReasoner = pelletReasonerFactory.createNonBufferingReasoner(ontology);
            Set<Rule> rules = pelletReasoner.getKB().getRules();
            for (Rule r : rules) {
                System.out.println(r);
            }
            pelletReasoner.getKB().realize();
            System.out.println(pelletReasoner.getReasonerName());

            IRI prifix = ontology.getOntologyID().getOntologyIRI().get();
            PrefixManager prefixManager = new DefaultPrefixManager();
            prefixManager.setDefaultPrefix(prifix.toString() + "#");
            OWLClass classUser = factory.getOWLClass("меньше_или_равно_450_СМC", prefixManager);
            OWLClass clsB = factory.getOWLClass("Пакеты_сообщений", prefixManager);
            OWLAxiom axiom = factory.getOWLSubClassOfAxiom(classUser, clsB);
            AddAxiom addAxiom = new AddAxiom(ontology, axiom);
            owlManager.applyChange(addAxiom);
            OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();

            OWLDataProperty hasA = factory.getOWLDataProperty("иметь_количество_сообщений", prefixManager);
            OWLFacetRestriction geq450 = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, factory.getOWLLiteral(450));
            OWLDataRange dataRng = factory.getOWLDatatypeRestriction(integerDatatype, geq450);
            OWLDataSomeValuesFrom teenagerAgeRestriction = factory.getOWLDataSomeValuesFrom(hasA, dataRng);
            OWLClassExpression teenagePerson = factory.getOWLObjectIntersectionOf(clsB, teenagerAgeRestriction);

            OWLEquivalentClassesAxiom teenagerDefinition = factory.getOWLEquivalentClassesAxiom(classUser, teenagePerson);
            owlManager.addAxiom(ontology, teenagerDefinition);
            OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(classUser);

            owlManager.addAxiom(ontology, declarationAxiom);
            owlManager.saveOntology(ontology, IRI.create(ontologyOwlFile.toURI()));
            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
            Node<OWLClass> unsatisfiableClasses = reasoner.getTopClassNode();
            for (OWLClass a:unsatisfiableClasses) {
                System.out.println(unsatisfiableClasses.iterator().next());
            }
            Dl dl = new Dl(ontology);
            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
            Version reasonerVersion = reasoner.getReasonerVersion();
            System.out.println(reasonerVersion.toString());
        } catch (OWLOntologyCreationException owlcre) {
            System.out.println("The ontology could not be created: " + owlcre.getMessage());
        }

    }

}
