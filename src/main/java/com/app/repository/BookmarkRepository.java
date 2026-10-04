package com.app.repository;

import com.app.domain.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, String> {
}
