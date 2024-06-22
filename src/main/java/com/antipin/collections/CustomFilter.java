package com.antipin.collections;

import java.util.Arrays;

/*Напишите метод filter, который принимает на вход массив любого типа, вторым арументом
метод должен принимать клас, реализующий интерфейс Filter, в котором один метод -
Object apply(Object o).

Метод должен быть реализован так чтобы возращать новый масив, к каждому элементу
которого была применена функция apply

*/

public class CustomFilter {

    @FunctionalInterface
    public interface Filter {
        Object apply(Object o);
    }

    public Object[] filter(Object[] array, Filter filter) {
        return Arrays.stream(array)
                .map(filter::apply)
                .toArray();
    }
}
