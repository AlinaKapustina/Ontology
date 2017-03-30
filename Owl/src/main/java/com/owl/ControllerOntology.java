package com.owl;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.rules.model.Rule;
import data.MessageData;
import data.UserSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
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
import org.swrlapi.core.IRIResolver;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.exceptions.SWRLRuleException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.owl2rl.OWL2RLEngine;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.parser.SWRLParser;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

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
    String HAS_TARIF = "иметь_тариф";
    String HAS_USLUGA = "иметь_подключенную_услугу_к_тарифу";

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
            Set<Rule> rules = pelletReasoner.getKB().getRules();

            IRI prifix = ontology.getOntologyID().getOntologyIRI().get();
            prefixManager = new DefaultPrefixManager();
            prefixManager.setDefaultPrefix(prifix.toString() + "#");
            System.out.println(prifix);
//            SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
//            
////            createSWRLParser.parseSWRLRule("Услуга(?x) ^ иметь_количество_сообщений(?x, \"100\"^^xsd:integer) ^ иметь_стоимость_подключения_услуги(?x, ?y) ^ иметь_стоимость_пользования_услугой(?x, ?t) ^ Тариф(?z) ^ иметь_услугу(?z, ?x) ^ swrlb:add(?sum, ?y, ?t) -> sqwrl:select(?x, ?z, ?sum) ^ sqwrl:columnNames(\"Услуга\", \"Тариф\", \"Цена\")", true, "", "");
////            Optional<SWRLAPIRule> swrlRule = ruleEngine.getSWRLRule("select");
//            try {
//                Set<IRI> swrlBuiltInIRIs = queryEngine.getSWRLBuiltInIRIs();
//                for (IRI swrlBuiltInIRI : swrlBuiltInIRIs) {
//                    System.out.println("bbbb iri"+ swrlBuiltInIRI);
//                }
//                IRIResolver createIRIResolver = SWRLAPIFactory.createIRIResolver(prifix.getNamespace());
//                System.out.println( queryEngine.isSWRLBuiltIn(prifix));
//                queryEngine.createSWRLRule("name","diplom:Smart(?p) -> sqwrl:select(?p)");
//            } catch (SWRLParseException | SWRLBuiltInException ex) {
//                
//                ex.printStackTrace();
//            }
//
//         /*   SQWRLResult result = queryEngine.runSQWRLQuery("select");
//            System.out.println("fjdfjdh" +queryEngine.getSWRLRule("select"));
//            System.out.println(result);*/
//            
//          
//            
//            for (Rule r : rules) {
//                System.out.println(r);
//            }
//            pelletReasoner.getKB().realize();
//            System.out.println(pelletReasoner.getReasonerName());

            OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ontology);

            OWLClass createNewClass = createNewClass("Объект_1", "", "", "Подключенный_модуль");
            Set<OWLClassExpression> set = new HashSet<>();
            /*  String tarif = result.getColumn("Тариф").get(0).toString();
                String ysluga = result.getColumn("Услуга").get(0).toString();
                int index = tarif.indexOf(":");
                if (index == -1){
                    index = 0;
                }
                set.add(createOWLEquivlentClassAxiom(HAS_TARIF,tarif.substring(index)));
                index = ysluga.indexOf(":");
                if (index == -1){
                    index = 0;
                }
                set.add(createOWLEquivlentClassAxiom(HAS_USLUGA,ysluga.substring(index)));
                joinExpression(createNewClass, set);
            
            Node<OWLClass> unsatisfiableClasses = reasoner.getTopClassNode();
            for (OWLClass a : unsatisfiableClasses) {
                System.out.println(unsatisfiableClasses.iterator().next());
            }
             */
////            Dl dl = new Dl(ontology);
////            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
////            Version reasonerVersion = reasoner.getReasonerVersion();
////            System.out.println(reasonerVersion.toString());
        } catch (OWLOntologyCreationException owlcre) {
            System.out.println("The ontology could not be created: " + owlcre.getMessage());
        }

    }

    public List<String> giveAnswer(UserSet userSet) {
        convertMessageData(userSet.getMessangeData());
        OWLClass mess = createNewClassWithProperty(userSet.getMessangeData().getMinNumberMessage() + "", MIN_NUMBER, MESS, PACKAGE_MESS, HAS_NUMBER_MESS);
        OWLClass call = createNewClassWithProperty(userSet.getCallData().getMaxNumberCall() + "", MIN_NUMBER, CALL, PACKAGE_CALL, HAS_NUMBER_CALL);
        List<String> doDlQuery = doDlQuery(mess, call);
        return doDlQuery;
    }

    private void convertMessageData(MessageData messageData) {
        
        String s = "diplom:Тариф(?x)^"+
                "diplom:иметь_сообщения_абонентам_Новосибирской_области(?x,?y) ^ diplom:иметь_количество_сообщений(?y,?t)"
                + "swrlb:greaterThan(?t," + messageData.getMinNumberMessage() + ")^"
                + "swrlb:lessThan(?t," + messageData.getMaxNumberMessage() + ")->sqwrl:select(?x) ";
        SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
        try {
            queryEngine.createSWRLRule("my_rule", s);
        } catch (SWRLParseException | SWRLBuiltInException ex) {
        }
    }

    private OWLClass createNewClassWithProperty(String name, String prefix, String suffix, String subclass, String has) {

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

    private OWLClass createNewClass(String name, String prefix, String suffix, String subclass) {

        OWLClass classUser = factory.getOWLClass(prefix + name + suffix, prefixManager);
        OWLClass subclassUser = factory.getOWLClass(subclass, prefixManager);
        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(classUser, subclassUser);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        owlManager.applyChange(addAxiom);
        saveOntology();
        return classUser;

    }

    private OWLClassExpression createOWLEquivlentClassAxiom(String property, String value) {
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(property, prefixManager);
        OWLIndividual individual = factory.getOWLNamedIndividual(value, prefixManager);
        OWLClassExpression expression = factory.getOWLObjectHasValue(objectProperty, individual);
        return expression;

    }

    private void joinExpression(OWLClass classUser, Set<OWLClassExpression> set) {
        OWLClassExpression exp = factory.getOWLObjectIntersectionOf(set);
        OWLEquivalentClassesAxiom hasAxiom = factory.getOWLEquivalentClassesAxiom(classUser, exp);
        owlManager.addAxiom(ontology, hasAxiom);
        OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(classUser);
        owlManager.addAxiom(ontology, declarationAxiom);
        saveOntology();

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

    void createNewModule() {
        OWLClass owlClass = factory.getOWLClass("Тариф", prefixManager);
        try {
            Dl dl = new Dl(ontology, "Тариф");
            Set<OWLClass> classesInSignature = dl.doQuery(dl.getDlQueryPrinter());
            for (OWLClass oWLClass : classesInSignature) {
                System.out.println("my " + oWLClass);

            }
        } catch (OWLOntologyStorageException | IOException ex) {

        }

    }

}
