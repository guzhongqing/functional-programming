package com.agufish.methodref;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String name;

    //Supplier是jdk1.8的接口，这里和lambda一起使用了
    public static Car createNoArgs(SupplierNoArgs<Car> supplier) {
        return supplier.get();
    }

    public static Car createAllArgs(String name, SupplierAllArgs<Car> supplier) {
        return supplier.get(name);
    }

    public static void collide(Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void instanceImplement(String name, String color) {
        System.out.println("instanceImplement name:" + name);
        System.out.println("instanceImplement color:" + color);
    }

    public void testInstance(TestInstanceInterface testInstanceInterface) {
        testInstanceInterface.test("instanceName", "instanceColor");
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }

    public void printName(GetNameInterface getNameInterface) {
        System.out.println(getNameInterface.getName(this));
    }


}

@FunctionalInterface
interface SupplierNoArgs<T> {
    T get();
}

@FunctionalInterface
interface SupplierAllArgs<T> {
    T get(String name);
}

@FunctionalInterface
interface TestInstanceInterface {
    void test(String name, String color);
}

@FunctionalInterface
interface GetNameInterface {
    String getName(Car car);
}