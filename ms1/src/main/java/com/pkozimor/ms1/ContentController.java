package com.pkozimor.ms1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentRepository contentRepository;

    public ContentController(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @GetMapping
    public List<Content> getContent() {
        return contentRepository.findAll();
    }

    @GetMapping("/{tag}")
    public List<Content> getContentByTag(@PathVariable String tag) {
        return contentRepository.findByTagIgnoreCase(tag);
    }

    @PostMapping
    public ResponseEntity<Content> addContent(@RequestBody Content content) {
        Content savedContent = contentRepository.save(content);
        return new ResponseEntity<>(savedContent, HttpStatus.CREATED);
    }
}
