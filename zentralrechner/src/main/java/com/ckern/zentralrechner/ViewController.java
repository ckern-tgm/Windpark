package com.ckern.zentralrechner;

import com.ckern.zentralrechner.model.Version;
import com.ckern.zentralrechner.model.VersionRepository;
import com.ckern.zentralrechner.model.Windpark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private VersionRepository versionRepository;

    @GetMapping("/graphic/windparks")
    public String windparks(Model model) {
        List<Windpark> latestWindparks = new ArrayList<>();

        for (Version windpark : versionRepository.findAll()) {
            latestWindparks.add(windpark.getLatestWindpark());
        }

        model.addAttribute("name", latestWindparks);

        return "windparks";
    }

    @GetMapping("/graphic/windpark")
    public String windpark(Model model, @RequestParam(value = "id") String id) {
        model.addAttribute("windpark", versionRepository.findByid(id));

        return "windpark";
    }
}
