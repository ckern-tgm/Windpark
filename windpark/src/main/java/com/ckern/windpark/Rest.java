package com.ckern.windpark;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest {

    @GetMapping("/xml")
    @ResponseBody
    public String getXml() {
        return "<windpark id=\"NOE0005\">\n" +
                "    <windrad id=\"0001\">\n" +
                "        <power unit=\"kWh\">891.51</power>\n" +
                "        <blindpower unit=\"kWh\">178.54</blindpower>\n" +
                "        <windspeed unit=\"km/h\">41.54</windspeed>\n" +
                "        <rotationspeed unit=\"m/s\">0.20</rotationspeed>\n" +
                "        <temperature unit=\"C\">84.0</temperature>\n" +
                "        <bladeposition unit=\"deg\">112.8</bladeposition>\n" +
                "        <transfertime unit=\"ms\">991</transfertime>\n" +
                "    </windrad>\n" +
                "    <windrad id=\"0002\">\n" +
                "        <power unit=\"kWh\">514.35</power>\n" +
                "        <blindpower unit=\"kWh\">111.54</blindpower>\n" +
                "        <windspeed unit=\"km/h\">33.54</windspeed>\n" +
                "        <rotationspeed unit=\"m/s\">0.85</rotationspeed>\n" +
                "        <temperature unit=\"C\">32.1</temperature>\n" +
                "        <bladeposition unit=\"deg\">21.1</bladeposition>\n" +
                "        <transfertime unit=\"ms\">758</transfertime>\n" +
                "    </windrad>\n" +
                "    <windrad id=\"0003\">\n" +
                "        <power unit=\"kWh\">484.2</power>\n" +
                "        <blindpower unit=\"kWh\">172.25</blindpower>\n" +
                "        <windspeed unit=\"km/h\">91.10</windspeed>\n" +
                "        <rotationspeed unit=\"m/s\">0.76</rotationspeed>\n" +
                "        <temperature unit=\"C\">82.9</temperature>\n" +
                "        <bladeposition unit=\"deg\">26.0</bladeposition>\n" +
                "        <transfertime unit=\"ms\">864</transfertime>\n" +
                "    </windrad>\n" +
                "</windpark>";
    }
}
