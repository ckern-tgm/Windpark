package com.ckern.zentralrechner;

import com.ckern.zentralrechner.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Rest {

    @Autowired
    private VersionRepository versionRepository;

    @Autowired
    private WindparkRepository windparkVersions;

    @Autowired
    private WindradRepository windrads;

    @GetMapping("/latestwindparks")
    public List<Windpark> latestWindparks() {
        List<Windpark> latestWindparks = new ArrayList<>();
        for (Version version : versionRepository.findAll()) {
            latestWindparks.add(version.getLatestWindpark());
        }
        return latestWindparks;
    }

    @GetMapping("/windparks")
    public List<Version> windparks() {
        return versionRepository.findAll();
    }

    @GetMapping("/latestwindpark")
    public Windpark latestWindpark(@RequestParam(value = "id") String id) {
        return versionRepository.findByid(id).getLatestWindpark();
    }

    @GetMapping("/windpark")
    public Version windpark(@RequestParam(value = "id") String id) {
        return versionRepository.findByid(id);
    }

}
