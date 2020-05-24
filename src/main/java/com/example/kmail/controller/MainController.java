package com.example.kmail.controller;

import com.example.kmail.domain.Notes;
import com.example.kmail.domain.User;
import com.example.kmail.repository.NoteRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private NoteRep noteRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String mainForm(Model model) {

        return "greeting";
    }

    @GetMapping("/greeting")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/mainForm")
    public String main(@RequestParam(required = false,defaultValue = "") String filter,
                       @AuthenticationPrincipal User user, Model model) {

        Iterable<Notes> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = noteRepo.findByAuthorAndTag(user,filter);
        } else {
            messages = noteRepo.findByAuthor(user);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter",filter);

        return "mainForm";
    }

    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text, @RequestParam String tag,
            @RequestParam MultipartFile file,
            Model model) throws IOException {

        Notes notes = new Notes(text, tag,user);
        if (file != null) {
            File fileDir = new File(uploadPath);
            if (!fileDir.exists()) fileDir.mkdir();

            String fileUUID = UUID.randomUUID().toString() ; // unique id file
            String resultPath = fileUUID.concat(".").concat(file.getOriginalFilename());

            file.transferTo(new File(uploadPath.concat("/").concat(resultPath)));

            notes.setFileName(resultPath);
        }

        noteRepo.save(notes);

        Iterable<Notes> messages = noteRepo.findAll();

        model.addAttribute("messages", messages);

        return "mainForm";
    }

    @GetMapping("/deleteNote")
    public String deleteNote (Model model, @NotNull Long notesId) {
        noteRepo.deleteById(notesId);
        return "mainForm";
    }



}


