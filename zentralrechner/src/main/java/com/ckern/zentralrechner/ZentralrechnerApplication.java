package com.ckern.zentralrechner;

import com.ckern.zentralrechner.model.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
public class ZentralrechnerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ZentralrechnerApplication.class, args);
    }

    @Autowired
    VersionRepository versionRepository;

    @Autowired
    WindparkRepository windparkRepository;

    @Autowired
    WindradRepository windradRepository;


    @Override
    public void run(String... args) throws Exception {

        try {
            // Get all windpark URLs from file
            ArrayList<String[]> locations = getWindparkLocations(
                    xmlFileToString(
                            new ClassPathResource("windparkConfig.xml").getFile()
                    )
            );

            /*// Get all windpark XMLs from URLs
            ArrayList<String[]> locations = new ArrayList(Arrays.asList(new String[][]{
                    new String[]{"127.0.0.1","8081"}//,
                    //new String[]{"127.0.0.1","8082"},
                    //new String[]{"127.0.0.1","8083"}
            }));*/

            ArrayList<Windpark> windparkArrayList = new ArrayList();

            for (String[] location : locations) {
                windparkArrayList.add(
                        getWindparkFromXML(
                                getXmlFromIp(location[0] + ":" + location[1])
                        )
                );
            }

            //Persist Windparks

            ArrayList<Version> savedParks = new ArrayList(versionRepository.findAll());

            outerLoop:
            for (Windpark w : windparkArrayList) {

                // Persisting Windrad
                for (Windrad windrad : w.getWindraeder()) {
                    windradRepository.save(windrad);
                }

                // Persisting Version
                windparkRepository.save(w);

                // Storing version in Windpark
                windparkRepository.save(w);
                for (Version savedPark : savedParks) {
                    if (w.getId().equals(savedPark.getId())) {
                        savedPark.addVersion(w);
                        versionRepository.save(savedPark);
                        continue outerLoop;
                    }
                }

                // Creating new
                Version wp = new Version(w.getId());
                wp.addVersion(w);
                versionRepository.save(wp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getXmlFromIp(String ip) throws URISyntaxException {
        RestTemplate t = new RestTemplate();
        return t.exchange(new URI("http://" + ip + "/xml"), HttpMethod.GET, null, String.class).getBody();
    }

    public static Windpark getWindparkFromXML(String xml) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document xmlFile = sb.build(new StringReader(xml));

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date timestamp = new Date();

        String version = formatter.format(timestamp);

        Windpark windpark = new Windpark(xmlFile.getRootElement().getAttribute("id").getValue(), version);

        ArrayList<Element> windrads = new ArrayList<>(xmlFile.getRootElement().getChildren());

        for (Element windrad : windrads) {
            String id = windrad.getAttribute("id").getValue();
            double power = Double.parseDouble(windrad.getChild("power").getText());
            double blindpower = Double.parseDouble(windrad.getChild("blindpower").getText());
            double windspeed = Double.parseDouble(windrad.getChild("windspeed").getText());
            double rotationspeed = Double.parseDouble(windrad.getChild("rotationspeed").getText());
            double temperature = Double.parseDouble(windrad.getChild("temperature").getText());
            double bladeposition = Double.parseDouble(windrad.getChild("bladeposition").getText());
            double transfertime = Double.parseDouble(windrad.getChild("transfertime").getText());

            windpark.addWindrad(new Windrad(id, version, power, blindpower, windspeed, rotationspeed, temperature, bladeposition, transfertime));
        }

        return windpark;
    }

    public static String xmlFileToString(File file) {
        String output = "";
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                output += scanner.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return output;
    }

    public static ArrayList<String[]> getWindparkLocations(String xml) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document xmlFile = sb.build(new StringReader(xml));

        ArrayList<Element> windparks = new ArrayList<>(xmlFile.getRootElement().getChildren("windpark"));

        ArrayList<String[]> windparkLocations = new ArrayList<>();

        for (Element windpark : windparks) {
            String[] location = new String[2];

            location[0] = windpark.getAttribute("ip").getValue();
            location[1] = windpark.getAttribute("port").getValue();

            windparkLocations.add(location);
        }
        return windparkLocations;

    }
}
