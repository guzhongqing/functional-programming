package com.agufish.optional;

import com.agufish.stream.Author;

import java.util.Optional;
import java.util.Random;

public class OptionalDemo {
    public static void main(String[] args) {


        Author author = getAuthor();
        Optional<Author> optionalAuthor = Optional.ofNullable(author);
        optionalAuthor.ifPresent(System.out::println);

        Optional<Author> authorOptional = getAuthorOptional();
        authorOptional.ifPresent(System.out::println);

        // 这里在调用 Optional.of 时就会抛出空指针异常
        Optional<Author> authorOptionalNull = Optional.of(null);
        authorOptionalNull.ifPresent(System.out::println);


    }

    private static Optional<Author> getAuthorOptional() {
        Author author = new Author(1L, "鲁迅", 80, "鲁迅简介", null);
        return Optional.ofNullable(author);
    }

    private static Author getAuthor() {
        // 不确定返回值是否为空
        return new Random().nextInt() % 2 == 0 ? new Author(1L, "鲁迅", 80, "鲁迅简介", null) : null;
    }

}
