package com.antipin.stream;

/*Предположим, у нас есть список заказов, и каждый заказ представляет собой продукт и его стоимость.
Задача состоит в использовании Stream API и коллекторов для решения следующих задач:

Создайте список заказов с разными продуктами и их стоимостями.
Группируйте заказы по продуктам.
Для каждого продукта найдите общую стоимость всех заказов.
Отсортируйте продукты по убыванию общей стоимости.
Выберите три самых дорогих продукта.
Выведите результат: список трех самых дорогих продуктов и их общая стоимость.*/

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Order {
    private String product;
    private double cost;

    public Order(String product, double cost) {
        this.product = product;
        this.cost = cost;
    }

    public String getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }
}

public class StreamCollectorsExample {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("Laptop", 1200.0),
                new Order("Smartphone", 800.0),
                new Order("Laptop", 1500.0),
                new Order("Tablet", 500.0),
                new Order("Smartphone", 900.0)
        );

        /*Группируйте заказы по продуктам.*/
        Map<String, List<Order>> groupedByProduct = orders.stream()
                .collect(Collectors.groupingBy(Order::getProduct));
        System.out.println(groupedByProduct);

        /*Для каждого продукта найдите общую стоимость всех заказов*/
        Map<String, Double> costSum = orders.stream()
                .collect(Collectors.toMap(Order::getProduct, Order::getCost, Double::sum));
        System.out.println(costSum);

        /*Отсортируйте продукты по убыванию общей стоимости.*/
        List<String> reverseSortedByCostSumProducts = costSum.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
        System.out.println(reverseSortedByCostSumProducts);

        /*Выберите три самых дорогих продукта.*/
        List<String> threeMostExpensiveProducts = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .limit(3)
                .map(Order::getProduct)
                .toList();
        System.out.println(threeMostExpensiveProducts);

        /*Выведите результат: список трех самых дорогих продуктов и их общая стоимость.*/
        double costSumOfThreeMostExpensiveProducts = orders.stream()
                .sorted(Comparator.comparingDouble(Order::getCost).reversed())
                .limit(3)
                .mapToDouble(Order::getCost)
                .sum();
        System.out.print(threeMostExpensiveProducts);
        System.out.println(costSumOfThreeMostExpensiveProducts);
    }
}