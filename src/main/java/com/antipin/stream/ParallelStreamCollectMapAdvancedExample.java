package com.antipin.stream;

/*Создайте коллекцию студентов, где каждый студент содержит информацию о предметах, которые он изучает,
 и его оценках по этим предметам.
Используйте Parallel Stream для обработки данных и создания Map, где ключ - предмет,
а значение - средняя оценка по всем студентам.
Выведите результат: общую Map с средними оценками по всем предметам.*/

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }
}

public class ParallelStreamCollectMapAdvancedExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        Map<String, Double> averageGrades = students.stream()
                .parallel()
                .flatMap(s -> s.getGrades().entrySet().parallelStream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.averagingInt(Map.Entry::getValue)));

        System.out.println(averageGrades);
    }
}
