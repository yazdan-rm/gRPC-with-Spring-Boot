package com.app.service.impl;

import com.app.domain.BookmarkEntity;
import com.app.dto.BookmarkRequest;
import com.app.dto.BookmarkResponse;
import com.app.repository.BookmarkRepository;
import com.app.service.IBookmarkService;
import com.bookmark.BookmarkServiceGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class BookmarkServiceImpl
        extends BookmarkServiceGrpc.BookmarkServiceImplBase
        implements IBookmarkService {

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

    @Override
    public void findBookmarkById(com.bookmark.BookmarkRequest request, StreamObserver<com.bookmark.BookmarkResponse> responseObserver) {
        BookmarkResponse bookmarkById = findBookmarkById(request.getId());

        com.bookmark.BookmarkResponse bookmarkGrpc = com.bookmark.BookmarkResponse.newBuilder()
                .setId(bookmarkById.id())
                .setUrl(bookmarkById.url())
                .setTitle(bookmarkById.title())
                .setDescription(bookmarkById.description())
                .setCreatedAt(toTimestamp(bookmarkById.createdAt()))
                .setUpdatedAt(toTimestamp(bookmarkById.updatedAt()))
                .build();

        responseObserver.onNext(bookmarkGrpc);
        responseObserver.onCompleted();
    }

    private static Timestamp toTimestamp(LocalDateTime value) {
        return Timestamp.newBuilder()
                .setSeconds(
                        value.toEpochSecond(ZoneOffset.UTC)
                )
                .setNanos(
                        value.getNano()
                )
                .build();
    }

}


