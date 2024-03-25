package com.ayseozcan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookRepository extends JpaRepository<Book, Long> {
}
