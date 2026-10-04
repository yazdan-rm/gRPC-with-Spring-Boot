package com.app.controller;

import com.app.dto.BookmarkRequest;
import com.app.dto.BookmarkResponse;
import com.app.service.IBookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final IBookmarkService bookmarkService;


    @PostMapping
    public ResponseEntity<BookmarkResponse> createBookmark(
            @Valid @RequestBody BookmarkRequest bookmarkRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookmarkService.createBookmark(bookmarkRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookmarkResponse> updateBookmark(
            @PathVariable String id,
            @Valid @RequestBody BookmarkRequest bookmarkRequest) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookmarkService.updateBookmark(id, bookmarkRequest));
    }

    @GetMapping
    public ResponseEntity<List<BookmarkResponse>> getAllBookmarks() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookmarkService.findAllBookmarks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookmarkResponse> getBookmarkById(@PathVariable String id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookmarkService.findBookmarkById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmarkById(@PathVariable String id) {

        bookmarkService.deleteBookmarkById(id);

        return ResponseEntity.noContent().build();
    }
}
