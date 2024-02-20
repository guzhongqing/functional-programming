package com.agufish.methodref;

import java.util.Collections;
import java.util.List;

public class TestDemo {

    public static void main(String[] args) {
        testClass();
    }

    // 构造方法引用
    public static void testNew() {
        Car car1 = Car.createNoArgs(Car::new);
//        Car car1 = Car.create(() -> new Car());
//        Car car1 = Car.create(new Supplier<Car>() {
//            @Override
//            public Car get() {
//                return new Car();
//            }
//        });
        System.out.println(car1);

        Car car2 = Car.createAllArgs("myCar", Car::new);
//        Car car2 = Car.createAllArgs("myCar", name -> new Car(name));
//        Car car2 = Car.createAllArgs("myCar", new SupplierAllArgs<Car>() {
//            @Override
//            public Car get(String name) {
//                return new Car(name);
//            }
//        });
        System.out.println(car2);
    }

    // 静态方法引用
    public static void testStatic() {
        List<Car> cars = Collections.singletonList(new Car());
        cars.forEach(System.out::println);
//        cars.forEach(car -> System.out.println(car));
//        cars.forEach(new Consumer<Car>() {
//            @Override
//            public void accept(Car car) {
//                System.out.println(car);
//            }
//        });
        cars.forEach(Car::collide);
//        cars.forEach(car -> Car.collide(car));
//        cars.forEach(new Consumer<Car>() {
//            @Override
//            public void accept(Car car) {
//                Car.collide(car);
//            }
//
//        });
    }


    // 对象的实例方法引用
    public static void testInstance() {
        Car car = new Car();
        car.testInstance(car::instanceImplement);
//        car.testInstance((name, color) -> car.instanceImplement(name, color));
//        car.testInstance(new TestInstanceInterface() {
//            @Override
//            public void test(String name, String color) {
//                car.instanceImplement(name, color);
//            }
//        });

        List<Car> cars = Collections.singletonList(car);
        StringBuilder sb = new StringBuilder();
        cars.forEach(sb::append);
//        cars.forEach(car -> sb.append(car));
//        cars.forEach(new Consumer<Car>() {
//            @Override
//            public void accept(Car car) {
//                sb.append(car);
//            }
//        });
//        System.out.println(sb.toString());

        Runnable runnable = car::getName;
//        Runnable runnable = () -> car.getName();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                car.getName();
//            }
//        };

    }


    // 类的的实例方法引用
    public static void testClass() {
        List<Car> cars = Collections.singletonList(new Car());

        cars.forEach(Car::repair);
//        cars.forEach(car -> car.repair());
//        cars.forEach(new Consumer<Car>() {
//            @Override
//            public void accept(Car car) {
//                car.repair();
//            }
//        });


        Car car = new Car("myCarName");
        car.printName(Car::getName);
//        car.printName(car1 -> car1.getName());
//        car.printName(new GetNameInterface() {
//            @Override
//            public String getName(Car car) {
//                return car.getName();
//            }
//        }

        String result = subString("agufish", 1, 3, String::substring);
//        String result = subString("agufish", 1, 3, (s, start, end) -> s.substring(start, end));
//        String result = subString("agufish", 1, 3, new UseString() {
//            @Override
//            public String use(String s, int start, int end) {
//                return s.substring(start, end);
//            }
//        });

        System.out.println(result);
    }

    @FunctionalInterface
    interface UseString {
        String use(String s, int start, int end);
    }

    public static String subString(String s, int start, int end, UseString useString) {
        return useString.use(s, start, end);
    }
}


