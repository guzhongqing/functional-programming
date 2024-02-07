package com.agufish.stream;

import java.util.*;
import java.util.stream.Stream;

public class StreamDemo {

    public static void test1() {
        List<Author> authorList = genAuthors();
        // 单列集合: 现在需要打印所有年龄小于18的作家的名字，并且要注意去重。
        authorList.stream()
                .filter(author -> author.getAge() < 18)
                .distinct()
                // 终结操作
                .forEach(author -> System.out.println(author.getName()));
    }

    public static void test2() {
        // 数组:转换成流,Arrays.stream(数组)或者Stream.of(数组)
        Integer[] arr = new Integer[]{1, 2, 3, 4, 5};
        Arrays.stream(arr).filter(n -> n > 2).forEach(System.out::println);
        Stream.of(arr).filter(n -> n > 2).forEach(System.out::println);
    }

    public static void test3() {
        // 双列集合:转换成流,先转为单列集合再转为流
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);

        // 获取map实体的流
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        entrySet.stream()
                .filter(entry -> entry.getValue() > 1)
                .forEach(System.out::println);

        System.out.println(map);

    }

    public static void test4() {
        // 打印所有姓名长度大于2的作者的名字
        genAuthors().stream()
                .filter(author -> author.getName().length() > 1)
                .forEach(author -> System.out.println(author.getName()));
    }

    public static void test5() {
        // 打印所有作家的名字
        genAuthors().stream()
                // 方法引用
                .map(Author::getName)
//                .map(author -> author.getName())
                .forEach(System.out::println);
    }

    public static void test6() {

        // 打印所有作家的名字
        genAuthors().stream()
                // 方法引用
                .map(author -> TestClass.get(author))
//                .forEach(System.out::println);
                .forEach((name) -> System.out.println(name));
    }


    public static void main(String[] args) {
        test6();

    }

    private static List<Author> genAuthors() {
        // 数据初始化
        Author author1 = new Author(1L, "蒙多", 33, "一个从菜刀中明悟哲理的祖安人", null);
        Author author2 = new Author(2L, "亚拉索", 15, "狂风也追逐不上他的思考速度", null);
        Author author3 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);
        Author author4 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L, "刀的两侧是光明与黑暗", "哲学,爱情", 88, "用一把刀划分了爱恨"));
        books1.add(new Book(2L, "一个人不能死在同一把刀下", "个人成长,爱情", 99, "讲述如何从失败中明悟真理"));

        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(4L, "吹或不吹", "爱情,个人传记", 56, "一个哲学家的恋爱观注定很难把他所在的时代理解"));
        books3.add(new Book(5L, "你的剑就是我的剑", "爱情", 56, "无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢?"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢?"));

        author1.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        return Arrays.asList(author1, author2, author3, author4);
    }
}

class TestClass {
    public static String get(Author author) {

        int n = 10;
        int age = author.getAge();
        System.out.println(n + age);

        return author.getName() + age;
    }
}