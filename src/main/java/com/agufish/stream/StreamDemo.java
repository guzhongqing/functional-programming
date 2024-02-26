package com.agufish.stream;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
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
                // 静态方法引用
                .map(author -> TestClass.get(author))
//                .forEach(System.out::println);
                .forEach((name) -> System.out.println(name));
    }


    public static void testDistinct() {
        // 打印所有作家的名字,名字不能重复
        genAuthors().stream()
                // 静态方法引用
                .map(Author::getName)
                .distinct()
                .forEach(System.out::println);
    }

    public static void testSorted() {
        // 对流中的元素按照年龄降序排序,不能有重复元素
        genAuthors().stream()
                .distinct()
                .sorted() // 使用sorted空参需要实体类实现Comparable接口
                .forEach(System.out::println);

        genAuthors().stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge()) // 实现Comparator降序
                .forEach(System.out::println);
    }

    public static void testLimit() {
        // 对流中的元素按照年龄降序排序,不能有重复元素,最后打印前2个作家的名字
        StreamDemo.genAuthors().stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .limit(2)
                .forEach((author) -> System.out.println(author.getName()));
    }

    public static void testSkip() {
        // 打印除了年龄最大的作家之外其他作家,要求不能重复,并且按照年龄降序排序
        StreamDemo.genAuthors().stream()
                .distinct()
                .sorted((o1, o2) -> o2.getAge() - o1.getAge())
                .skip(1)
                .forEach((author) -> System.out.println(author.getName()));
    }

    public static void testFlatMap() {
        // 案例一: 打印所有书籍的名字.注意:书籍名字不能重复
        genAuthors().stream()
                .flatMap((author) -> author.getBooks().stream())
                .map(Book::getName)
                .distinct()
                .forEach(System.out::println);

        // 案例二: 打印现有数据的所有分类,对分类进行去重,不能出现格式:哲学,爱情
        genAuthors().stream()
                .flatMap((author) -> author.getBooks().stream())
                // 对书籍对象去重
                .distinct()
                // 分割字符串,将字符串分割成数组,然后转换成流,变成flatMap的流,放入同一个流中
                .flatMap((book) -> Arrays.stream(book.getCategory().split(",")))
                // 对分类去重
                .distinct()
                .forEach(System.out::println);
    }

    public static void testForEach() {
        // 打印所有作家的名字
        genAuthors().stream()
                .map(Author::getName)
                .distinct()
                .forEach(System.out::println);
    }

    public static void testCount() {
        // 打印书籍的数量
        long count = genAuthors().stream()
                .distinct()
                .flatMap((author) -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }

    public static void testMaxMin() {
        // 分别获取作家书籍的最高分和最低分
        Optional<Integer> max = genAuthors().stream()
                .flatMap((author) -> author.getBooks().stream())
                .map(Book::getScore)
                // max寻找最后的元素,从小到大排序,最前面的就是最大的
                .max((score1, score2) -> score1 - score2);
//                .max(Comparator.comparingInt(score -> score));
        System.out.println(max.get());

        Optional<Integer> min = genAuthors().stream()
                .flatMap((author) -> author.getBooks().stream())
                .map(Book::getScore)
                // min寻找最前的元素,从小到大排序,最前面的就是最小的
                .min((score1, score2) -> score1 - score2);
//                .max(Comparator.comparingInt(score -> score));
        System.out.println(min.get());
    }


    public static void testCollect() {
        // 获取一个存放所有作家名字的list集合
        List<String> list = genAuthors().stream()
                .map(Author::getName)
                // Collectors.toList()使用的是ArrayList实现
                .collect(Collectors.toList());
        System.out.println(list);

        // 获取一个所有书名的集合
        Set<String> set = genAuthors().stream()
                .flatMap((author) -> author.getBooks().stream())
                .map(Book::getName)
                // set集合自动去重,Collectors.toSet()使用的是HashSet实现
                .collect(Collectors.toSet());
        System.out.println(set);

        // 获取一个Map集合,map的key为作家的名字,value该作家的List<Book>
        // Collectors.toMap()使用的是HashMap实现
        //                .collect(Collectors.toMap(Author::getName, Author::getBooks));
        Map<String, List<Book>> map = genAuthors().stream()
                .distinct()
                // Collectors.toMap()使用的是HashMap实现
//                .collect(Collectors.toMap(Author::getName, Author::getBooks));
                .collect(Collectors.toMap(
                        // 定义获取key的方法,Author为当前流中的元素类型,String为key的类型,注意key不能重复
                        new Function<Author, String>() {
                            @Override
                            public String apply(Author author) {
                                return author.getName();
                            }
                        },
                        // 定义获取value的方法,Author为当前流中的元素类型,List<Book>为value的类型
                        new Function<Author, List<Book>>() {
                            @Override
                            public List<Book> apply(Author author) {
                                return author.getBooks();
                            }
                        }));
        System.out.println(map);
    }

    public static void testAnyMatch() {
        // 判断是否有作家的年龄大于29
        boolean b = genAuthors().stream()
                .anyMatch(author -> author.getAge() > 29);
        System.out.println(b);
    }

    public static void testAllMatch() {
        // 判断所有作家的都是成年人
        boolean b = genAuthors().stream()
                .allMatch(author -> author.getAge() >= 18);
        System.out.println(b);
    }

    public static void testFindAny() {
        // 获取任意一个年龄大于18的,如果存在输出他的名字
        Optional<Author> any = genAuthors().stream()
                .filter(author -> author.getAge() > 18)
                .findAny();
        any.ifPresent(author -> System.out.println(author.getName()));
    }

    public static void testFindFirst() {
        // 获取年龄最小的作家,输出他的名字
        Optional<Author> first = genAuthors().stream()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .findFirst();
        first.ifPresent(author -> System.out.println(author.getName()));
    }

    public static void testReduce() {
        // 获取所有作家的年龄之和
        Integer result = genAuthors().stream()
                .distinct()
                .map(Author::getAge)
//                .reduce(0, Integer::sum);
//                .reduce(0, (age1, age2) -> Integer.sum(age1, age2));
                // 两个参数,reduce返回类型和identity的类型一致
                .reduce(0, new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer age1, Integer age2) {
                        return Integer.sum(age1, age2);
                    }
                });
        // 0 + 33 + 15 + 14
        System.out.println(result);

        // 求所有作者年龄最大的值
        Optional<Integer> max = genAuthors().stream()
                .map(Author::getAge)
//                .reduce(Integer::max);
//                .reduce((age1, age2) -> Integer.max(age1, age2));
                // 一个参数,reduce返回Optional类型
                .reduce(new BinaryOperator<Integer>() {
                    @Override
                    public Integer apply(Integer age1, Integer age2) {
                        return Integer.max(age1, age2);
                    }
                });
        max.ifPresent(System.out::println);
    }

    public static void test() {
        List<Author> authorList = genAuthors();
        System.out.println(authorList);
        authorList.stream()
                // peek方法可以对流中的元素进行中间操作,但是不会改变流中的元素
                .peek(author -> author.setAge(100))
                .map(Author::getAge)
                .forEach(System.out::println);
        System.out.println(authorList);
    }

    public static void testParallel() {
        // 使用集合的parallelStream方法,创建并行流
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        list.parallelStream()
                .peek(integer -> System.out.println(Thread.currentThread().getName() + " peek: " + integer))
                .reduce(Integer::sum)
                .ifPresent(System.out::println);

        //
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
//        integerStream
//                .peek(integer -> System.out.println(Thread.currentThread().getName() + " peek: " + integer))
//                .reduce(Integer::sum)
//                .ifPresent(System.out::println);

        // 使用流的parallel方法,创建并行流,并行流会使用多个线程,并行流的中间操作是无序的
        integerStream.parallel()
                .peek(integer -> System.out.println(Thread.currentThread().getName() + " peek: " + integer))
                .reduce(Integer::sum)
                .ifPresent(System.out::println);
    }


    public static void main(String[] args) {
        testParallel();
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