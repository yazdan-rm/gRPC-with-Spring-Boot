package com.app.service.impl;

import com.app.domain.BookmarkEntity;
import com.app.dto.BookmarkRequest;
import com.app.dto.BookmarkResponse;
import com.app.repository.BookmarkRepository;
import com.app.service.IBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements IBookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public BookmarkResponse createBookmark(BookmarkRequest bookmarkRequest) {

        BookmarkEntity bookmarkEntity = new BookmarkEntity();

        bookmarkEntity.setUrl(bookmarkRequest.url());
        bookmarkEntity.setTitle(bookmarkRequest.title());
        bookmarkEntity.setDescription(bookmarkRequest.description());

        BookmarkEntity savedBookmark = bookmarkRepository.save(bookmarkEntity);

        return toResponse(savedBookmark);
    }

    @Override
    public List<BookmarkResponse> findAllBookmarks() {
        return bookmarkRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BookmarkResponse findBookmarkById(String id) {
        BookmarkEntity bookmarkEntity = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark Not Found"));

        return toResponse(bookmarkEntity);
    }

    @Override
    public BookmarkResponse updateBookmark(String id, BookmarkRequest bookmarkRequest) {
        BookmarkEntity bookmarkEntity = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark Not Found"));

        bookmarkEntity.setTitle(bookmarkRequest.title());
        bookmarkEntity.setDescription(bookmarkRequest.description());
        bookmarkEntity.setUrl(bookmarkRequest.url());

        BookmarkEntity updatedBookmarkEntity = bookmarkRepository.save(bookmarkEntity);

        return toResponse(updatedBookmarkEntity);
    }

    @Override
    public void deleteBookmarkById(String id) {
        BookmarkEntity bookmarkEntity = bookmarkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bookmark Not Found"));

        bookmarkRepository.delete(bookmarkEntity);
    }

    private BookmarkResponse toResponse(BookmarkEntity bookmark) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getTitle(),
                bookmark.getUrl(),
                bookmark.getDescription(),
                bookmark.getCreatedAt(),
                bookmark.getUpdatedAt()
        );
    }
}
