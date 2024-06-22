package com.antipin.collections;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CountOfElements {

    public <K> Map<K, Long> countElements(K[] elements) {
        return Arrays.stream(elements)
                .collect(Collectors.groupingBy(k -> k, Collectors.counting()));
    }
}
