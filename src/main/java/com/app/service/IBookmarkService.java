package com.app.service;

import com.app.dto.BookmarkRequest;
import com.app.dto.BookmarkResponse;

import java.util.List;

public interface IBookmarkService {

    BookmarkResponse createBookmark(BookmarkRequest bookmarkRequest);

    List<BookmarkResponse> findAllBookmarks();

    BookmarkResponse findBookmarkById(String id);

    BookmarkResponse updateBookmark(String id, BookmarkRequest bookmarkRequest);

    void deleteBookmarkById(String id);
}
