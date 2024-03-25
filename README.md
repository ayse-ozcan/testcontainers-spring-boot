##  Testcontainers in Java Spring Boot

👉 [For more information, you can check out my blog post.](https://ayseozcan.com/2024/03/25/testcontainers-in-java-spring-boot/)

### Requirements
- IDE
- JDK 17+
- Gradle 7.5+
- Docker

### Dependencies

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    runtimeOnly 'org.postgresql:postgresql'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.boot:spring-boot-testcontainers'
    testImplementation 'org.testcontainers:junit-jupiter'
    testImplementation 'org.testcontainers:postgresql'
}
```

### schema.sql 

Create file under the `src/main/resources` directory.

```sql
CREATE TABLE IF NOT EXISTS books
(
    id serial primary key,
    name varchar(255) not null,
    author varchar(255) not null
);
```
### application.yaml

```yaml
spring:
  sql:
    init:
      mode: always
#  datasource:
#    url: jdbc:tc:postgresql:16:///booksdb
```
### BookController

```java
@RestController
@RequestMapping("api/v1/books")
class BookController {

    private final BookRepository bookRepository;


    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/find-all")
    ResponseEntity<List<Book>> findAll() {
        return ResponseEntity.ok(bookRepository.findAll());
    }
}
```
### BookControllerTest

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class BookControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        List<Book> books = List.of(
                new Book(1L, " The Hobbit", "J. R. R. Tolkien"),
                new Book(2L, "The Lord of the Rings", "J. R. R. Tolkien")
        );
        bookRepository.saveAll(books);
    }

    @Test
    @DisplayName("find all books")
    void shouldFindAllBooks() {

        ResponseEntity<List<Book>> response = testRestTemplate.exchange(
                "/api/v1/books/find-all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
```
### Getting started

Clone this project and run.

```
https://github.com/ayse-ozcan/testcontainers-spring-boot.git
```