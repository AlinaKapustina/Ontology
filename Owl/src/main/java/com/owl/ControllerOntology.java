package com.owl;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.clarkparsia.pellet.rules.model.Rule;
import data.CallData;
import data.InternetData;
import data.MessageData;
import data.PlugInData;
import data.SummaryData;
import data.UserSet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLResultValue;

public class ControllerOntology {

    public final String MIN_NUMBER = "меньше_или_равно_";
    public final String PACKAGE_MESS = "Пакеты_сообщений";
    public final String PACKAGE_CALL = "Пакеты_звонков";
    public final String HAS_NUMBER_CALL = "иметь_время_звонков_в_минутах";
    public final String HAS_NUMBER_MESS = "иметь_количество_сообщений";
    public final String HAS_MESS = "иметь_сообщения";
    public final String HAS_CALL = "иметь_звонки";
    public final String MESS = "_СМС";
    public final String CALL = "_минут";
    public final String SOME = " some ";
    public final String HAS_TARIF = "иметь_тариф";
    public final String HAS_USLUGA = "иметь_подключенную_услугу_к_тарифу";
    public final String MODULE = "Подключенный_модуль";
    public final String HAS_EXPENDITURE = "иметь_плату_за_использования_ресурсов";

    private PrefixManager prefixManager;
    private OWLDataFactory factory;
    private OWLOntology ontology;
    private OWLOntologyManager owlManager;
    private File ontologyOwlFile;
    private SQWRLQueryEngine queryEngine;
    private List<PlugInData> dataMessage = new ArrayList<>();
    private List<PlugInData> dataCall = new ArrayList<>();
    private List<PlugInData> dataInternet = new ArrayList<>();

    public ControllerOntology() throws OWLOntologyStorageException, IOException {

        try {
            owlManager = OWLManager.createOWLOntologyManager();
            ontologyOwlFile = new File("C:\\Users\\Admin\\Desktop\\exa.owl");
            ontology = owlManager.loadOntologyFromOntologyDocument(ontologyOwlFile);
            factory = owlManager.getOWLDataFactory();
            PelletReasonerFactory pelletReasonerFactory = PelletReasonerFactory.getInstance();
            PelletReasoner pelletReasoner = pelletReasonerFactory.createNonBufferingReasoner(ontology);
            Set<Rule> rules = pelletReasoner.getKB().getRules();
            queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
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

            OWLClass createNewClass = createNewClass("Объект_1", "Подключенный_модуль");
            Set<OWLClassExpression> set = new HashSet<>();

////            Dl dl = new Dl(ontology);
////            reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
////            Version reasonerVersion = reasoner.getReasonerVersion();
////            System.out.println(reasonerVersion.toString());
        } catch (OWLOntologyCreationException owlcre) {
            System.out.println("The ontology could not be created: " + owlcre.getMessage());
        }

    }

    public List<String> giveAnswer(UserSet userSet) {
        int sizeMessage = generateQueryMessage(userSet.getMessangeData());
        int sizeCall = generateQueryCall(userSet.getCallData());
        int sizeInternet = generateInternetData(userSet.getInternetData());
        List<SQWRLResult> messageOutput = getMessageOutput(sizeMessage);
        List<SQWRLResult> callOutput = getCallOutput(sizeCall);
        List<SQWRLResult> internetOutput = getInternetOutput(sizeInternet);
        processResult(messageOutput, dataMessage);
        System.out.println("Result");
        processResult(callOutput, dataCall);
        processResult(internetOutput, dataInternet);
        deleteQueryCall(sizeCall);
        deleteQueryMessage(sizeMessage);
        deleteQueryIntenet(sizeInternet);
        //  createObject();
        return null;
    }

    private int generateQueryMessage(MessageData messageData) {
        SqwrlGenerator generator = new SqwrlGenerator();
        List<String> listMessageQuery = generator.generateMessageQuery(messageData.getMinNumberMessage(), messageData.getMaxNumberMessage());
        for (int i = 0; i < listMessageQuery.size(); i++) {
            String name = "message_" + String.valueOf(i + 1);
            try {
                queryEngine.createSQWRLQuery(name, listMessageQuery.get(i));
            } catch (SWRLParseException | SQWRLException ex) {
            }
        }
        saveOntology();
        return listMessageQuery.size();
    }

    private int generateQueryCall(CallData callData) {
        SqwrlGenerator generator = new SqwrlGenerator();
        List<String> listCallQuery = generator.generateCallQuery(callData.getMinNumberCall(), callData.getMaxNumberCall());
        for (int i = 0; i < listCallQuery.size(); i++) {
            String name = "call_" + String.valueOf(i + 1);
            try {
                queryEngine.createSQWRLQuery(name, listCallQuery.get(i));
            } catch (SWRLParseException | SQWRLException ex) {
            }
        }
        saveOntology();
        return listCallQuery.size();
    }

    private void deleteQueryCall(int size) {
        for (int i = 0; i < size; i++) {
            String name = "call_" + String.valueOf(i + 1);
            queryEngine.deleteSWRLRule(name);
        }
        saveOntology();
    }

    private void deleteQueryMessage(int size) {
        for (int i = 0; i < size; i++) {
            String name = "message_" + String.valueOf(i + 1);
            queryEngine.deleteSWRLRule(name);
        }
        saveOntology();
    }

    private void deleteQueryIntenet(int size) {
        for (int i = 0; i < size; i++) {
            String name = "internet_" + String.valueOf(i + 1);
            queryEngine.deleteSWRLRule(name);
        }
        saveOntology();
    }

    private int generateInternetData(InternetData internetData) {
        SqwrlGenerator generator = new SqwrlGenerator();
        List<String> listInternetQuery = generator.generateInternetQuery(internetData.getMinNumberInternet(), internetData.getMaxNumberInternet());
        for (int i = 0; i < listInternetQuery.size(); i++) {
            
            String name = "internet_" + String.valueOf(i + 1);
            System.out.println("интернет"+name);
            try {
                queryEngine.createSQWRLQuery(name, listInternetQuery.get(i));
            } catch (SWRLParseException | SQWRLException ex) {
            }
        }
        saveOntology();
        return listInternetQuery.size();
    }

    private List<SQWRLResult> getMessageOutput(int size) {
        List<SQWRLResult> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                String name = "message_" + String.valueOf(i + 1);
                SQWRLResult query = queryEngine.runSQWRLQuery(name);
                result.add(query);
            } catch (SQWRLException ex) {
            }
        }
        return result;
    }

    private List<SQWRLResult> getCallOutput(int size) {
        List<SQWRLResult> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                String name = "call_" + String.valueOf(i + 1);
                SQWRLResult runSQWRLQuery = queryEngine.runSQWRLQuery(name);
                result.add(runSQWRLQuery);
            } catch (SQWRLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private List<SQWRLResult> getInternetOutput(int size) {
        List<SQWRLResult> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                String name = "internet_" + String.valueOf(i + 1);
                System.out.println("name   " + name);
                SQWRLResult runSQWRLQuery = queryEngine.runSQWRLQuery(name);
                System.out.println("name   " + name);
                result.add(runSQWRLQuery);
            } catch (SQWRLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private List<PlugInData> findTarif(String t, List<PlugInData> data) {
        List<PlugInData> findListData = new ArrayList<>();
        for (PlugInData plugInData : data) {
            if (plugInData.getNameTarif().equals(t)) {
                findListData.add(plugInData);
            }
        }
        return findListData;
    }

    private PlugInData findYsluga(List<PlugInData> tarifData, String ysluga) {
        PlugInData d = null;
        for (PlugInData plugInData : tarifData) {
            if (plugInData.getNameYsluga().equals(ysluga)) {
                d = plugInData;
            }
        }
        return d;
    }
    
    private void createSummaryData(){
        SummaryData summaryData = new SummaryData();
        
    }

    private void processResult(List<SQWRLResult> result, List<PlugInData> data) {

        result.forEach((sQWRLResult) -> {
            try {
                List<String> columnNames = sQWRLResult.getColumnNames();
                while (sQWRLResult.next()) {
                    List<SQWRLResultValue> rowData = sQWRLResult.getRow();
                    System.out.println(rowData);
                    PlugInData plugInData = new PlugInData();
                    int i = 0;
                    if (columnNames.get(i).equals("Тариф")) {
                        String name = rowData.get(i).toString();
                        int index = name.indexOf(":");
                        name = name.substring(index + 1);
                        List<PlugInData> findTarif = findTarif(name, data);
                        if (findTarif.isEmpty()) {
                            plugInData.setNameTarif(name);
                            if (columnNames.size() > 1) {
                                if (columnNames.get(i + 1).equals("Услуга")) {
                                    String nameYsluga = rowData.get(i + 1).toString();
                                    index = nameYsluga.indexOf(":");
                                    nameYsluga = nameYsluga.substring(index + 1);
                                    plugInData.setNameYsluga(nameYsluga);
                                    if (columnNames.size() > 2) {
                                        if (columnNames.get(i + 2).equals("Цена")) {
                                            plugInData.setCost(rowData.get(i + 2).asLiteralResult().getDouble());
                                        }
                                    }
                                } else {
                                    if (columnNames.get(i + 1).equals("Цена")) {
                                        plugInData.setCost(rowData.get(i + 1).asLiteralResult().getDouble());
                                    }
                                }
                            }
                            data.add(plugInData);
                        } else {
                            if (columnNames.size() > 1) {
                                if (columnNames.get(i + 1).equals("Услуга")) {
                                    String nameYsluga = rowData.get(i + 1).toString();
                                    index = nameYsluga.indexOf(":");
                                    nameYsluga = nameYsluga.substring(index + 1);
                                    PlugInData findYsluga = findYsluga(findTarif, nameYsluga);
                                    if (findYsluga == null) {
                                        plugInData.setNameTarif(findTarif.get(0).getNameTarif());
                                        plugInData.setNameYsluga(nameYsluga);
                                        if (columnNames.size() > 2) {
                                            if (columnNames.get(i + 2).equals("Цена")) {
                                                plugInData.setCost(rowData.get(i + 2).asLiteralResult().getDouble());
                                            }
                                        }

                                        data.add(plugInData);
                                    } else {
                                        if (columnNames.size() > 2) {
                                            if (columnNames.get(i + 2).equals("Цена")) {
                                                double cost = rowData.get(i + 2).asLiteralResult().getDouble();
                                                if (findYsluga.getCost() > cost) {
                                                    findYsluga.setCost(cost);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (columnNames.get(i + 1).equals("Цена")) {
                                        double cost = rowData.get(i + 1).asLiteralResult().getDouble();
                                        for (PlugInData dataTarif : findTarif) {
                                            if (dataTarif.getNameYsluga().equals("")) {
                                                if (dataTarif.getCost() > cost) {
                                                    dataTarif.setCost(cost);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (SQWRLException ex) {
            }
        });
        for (PlugInData plugInData : data) {
            System.out.println("Тариф " + plugInData.getNameTarif() + "   Услуга  " + plugInData.getNameYsluga() + "  цена " + plugInData.getCost());
        }
    }

    private OWLClass createNewClass(String name, String subclass) {
        OWLClass classUser = factory.getOWLClass(name, prefixManager);
        OWLClass subclassUser = factory.getOWLClass(subclass, prefixManager);
        OWLAxiom axiom = factory.getOWLSubClassOfAxiom(classUser, subclassUser);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        owlManager.applyChange(addAxiom);
        saveOntology();
        return classUser;
    }

    private OWLIndividual createNewIndividuals(String name, OWLClass nameClass) {
        OWLNamedIndividual owlNamedIndividual = factory.getOWLNamedIndividual(name, prefixManager);
        OWLClassAssertionAxiom owlClassAssertionAxiom = factory.getOWLClassAssertionAxiom(nameClass, owlNamedIndividual);
        AddAxiom addAxiom = new AddAxiom(ontology, owlClassAssertionAxiom);
        owlManager.applyChange(addAxiom);
        saveOntology();
        return owlNamedIndividual;
    }

    private OWLClassExpression createOWLEquivalentObjectClassAxiom(String property, String value) {
        OWLObjectProperty objectProperty = factory.getOWLObjectProperty(property, prefixManager);
        OWLIndividual individual = factory.getOWLNamedIndividual(value, prefixManager);
        OWLClassExpression expression = factory.getOWLObjectHasValue(objectProperty, individual);
        return expression;
    }

    private OWLClassExpression createOWLEquivalentDataClassAxiom(String property, double value) {
        OWLDataProperty owlDataProperty = factory.getOWLDataProperty(property, prefixManager);
        OWLLiteral owlLiteral = factory.getOWLLiteral(value);
        OWLClassExpression expression = factory.getOWLDataHasValue(owlDataProperty, owlLiteral);
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
    
    

    private void createObject() {
        String object = "Объект_";
        Set<OWLIndividual> individuals = new HashSet<>();
        int i = 1;
        for (PlugInData plugInData : dataMessage) {
            OWLClass createNewClass = createNewClass(object + i, MODULE);
            Set<OWLClassExpression> set = new HashSet<>();
            set.add(createOWLEquivalentObjectClassAxiom(HAS_TARIF, plugInData.getNameTarif()));
            if (!plugInData.getNameYsluga().equals("")) {
                set.add(createOWLEquivalentObjectClassAxiom(HAS_USLUGA, plugInData.getNameYsluga()));
            }
            set.add(createOWLEquivalentDataClassAxiom(HAS_EXPENDITURE, plugInData.getCost()));
            joinExpression(createNewClass, set);
            OWLIndividual createNewIndividuals = createNewIndividuals(object + i, createNewClass);
            individuals.add(createNewIndividuals);
            i++;
        }
        setDifferentIndividuals(individuals);
    }

    private void setDifferentIndividuals(Set<OWLIndividual> individuals) {
        OWLDifferentIndividualsAxiom owlDifferentIndividualsAxiom = factory.getOWLDifferentIndividualsAxiom(individuals);
        AddAxiom addAxiom = new AddAxiom(ontology, owlDifferentIndividualsAxiom);
        owlManager.applyChange(addAxiom);
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
}
