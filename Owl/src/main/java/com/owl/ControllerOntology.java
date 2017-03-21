package com.owl;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.rules.model.Rule;
import data.UserSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindswap.pellet.ABox;
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
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class ControllerOntology {

    String MIN_NUMBER = "меньше_или_равно_";
    String PACKAGE_MESS = "Пакеты_сообщений";
    String PACKAGE_CALL = "Пакеты_звонков";
    String HAS_NUMBER_CALL = "иметь_время_звонков_в_минутах";
    String HAS_NUMBER_MESS = "иметь_количество_сообщений";
    String HAS_MESS = "иметь_сообщения";
    String HAS_CALL = "иметь_звонки";
    String MESS = "_СМС";
    String CALL = "_минут";
    String SOME = " some ";

    PrefixManager prefixManager;
    OWLDataFactory factory;
    OWLOntology ontology;
    OWLOntologyManager owlManager;
    File ontologyOwlFile;

    public ControllerOntology() throws OWLOntologyStorageException, IOException {

        try {
            owlManager = OWLManager.createOWLOntologyManager();
            ontologyOwlFile = new File("C:\\Users\\Admin\\Desktop\\exa.owl");
            ontology = owlManager.loadOntologyFromOntologyDocument(ontologyOwlFile);
            factory = owlManager.getOWLDataFactory();
            PelletReasonerFactory pelletReasonerFactory = PelletReasonerFactory.getInstance();
            PelletReasoner pelletReasoner = pelletReasonerFactory.createNonBufferingReasoner(ontology);
//            Set<Rule> rules = pelletReasoner.getKB().getRules();
//            SWRLRuleEngine ruleEngine = SWRLAPIFactory.createSWRLRuleEngine(ontology);
//            Optional<SWRLAPIRule> swrlRule = ruleEngine.getSWRLRule("new");
//
//            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
//            SQWRLResult result = queryEngine.runSQWRLQuery("new");
//            System.out.println(result);
//            for (Rule r : rules) {
//                System.out.println(r);
//            }
            pelletReasoner.getKB().realize();
            System.out.println(pelletReasoner.getReasonerName());
            IRI prifix = ontology.getOntologyID().getOntologyIRI().get();
            prefixManager = new DefaultPrefixManager();
            prefixManager.setDefaultPrefix(prifix.toString() + "#");
            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
            Node<OWLClass> unsatisfiableClasses = reasoner.getTopClassNode();
            for (OWLClass a : unsatisfiableClasses) {
                System.out.println(unsatisfiableClasses.iterator().next());
            }
////            Dl dl = new Dl(ontology);
////            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
////            Version reasonerVersion = reasoner.getReasonerVersion();
////            System.out.println(reasonerVersion.toString());
        } catch (OWLOntologyCreationException owlcre) {
            System.out.println("The ontology could not be created: " + owlcre.getMessage());
        } 
//        catch (SWRLRuleException ex) {
//            ex.printStackTrace();
//        }
//        } catch (SQWRLException ex) {
//            Logger.getLogger(ControllerOntology.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public List<String> giveAnswer(UserSet userSet) {

        OWLClass mess = createNewClass(userSet.getNumberMessage() + "", MIN_NUMBER, MESS, PACKAGE_MESS, HAS_NUMBER_MESS);
        OWLClass call = createNewClass(userSet.getTimeCall() + "", MIN_NUMBER, CALL, PACKAGE_CALL, HAS_NUMBER_CALL);
        List<String> doDlQuery = doDlQuery(mess, call);
        return doDlQuery;

    }

    private OWLClass createNewClass(String name, String prefix, String suffix, String subclass, String has) {

        OWLClass classUser = factory.getOWLClass(prefix + name + suffix, prefixManager);
        OWLClass subclassUser = factory.getOWLClass(subclass, prefixManager);
        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(classUser, subclassUser);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();

        OWLDataProperty hasNumber = factory.getOWLDataProperty(has, prefixManager);
        OWLFacetRestriction restr = factory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, factory.getOWLLiteral(Integer.parseInt(name)));
        OWLDataRange dataRng = factory.getOWLDatatypeRestriction(integerDatatype, restr);
        OWLDataSomeValuesFrom hasRestr = factory.getOWLDataSomeValuesFrom(hasNumber, dataRng);
        OWLClassExpression expression = factory.getOWLObjectIntersectionOf(subclassUser, hasRestr);

        OWLEquivalentClassesAxiom hasAxiom = factory.getOWLEquivalentClassesAxiom(classUser, expression);
        owlManager.addAxiom(ontology, hasAxiom);
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(classUser);
        owlManager.addAxiom(ontology, declarationAxiom);
        System.out.println(addAxiom);
        owlManager.applyChange(addAxiom);
        saveOntology();
        return classUser;
    }

    private List<String> doDlQuery(OWLClass mess, OWLClass call) {
        String name = "(" + HAS_MESS + SOME + mess.getIRI().getShortForm() + ")" + " and " + "(" + HAS_CALL + SOME + call.getIRI().getShortForm() + " )";

        System.out.println(name);
        Set<OWLNamedIndividual> doQueryAnswer = null;
        try {
            Dl dl = new Dl(ontology, name);
            doQueryAnswer = dl.doQueryLoop(dl.getDlQueryPrinter());
        } catch (OWLOntologyStorageException | IOException ex) {
        }
        if (!doQueryAnswer.isEmpty()) {
            List<String> answer = new ArrayList<>();
            for (OWLNamedIndividual nameAnswer : doQueryAnswer) {
                int index = nameAnswer.getIRI().getShortForm().indexOf("#");
                if (index == -1) {
                    index = 0;
                }
                answer.add(nameAnswer.getIRI().getShortForm().substring(index));
            }
            return answer;
        }
        return null;
    }

    void saveOntology() {
        try {
            owlManager.saveOntology(ontology, IRI.create(ontologyOwlFile.toURI()));
        } catch (OWLOntologyStorageException ex) {

        }
    }

}
