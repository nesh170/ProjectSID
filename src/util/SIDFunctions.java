package util;

import java.util.List;
import java.util.stream.Collectors;

public class SIDFunctions {

    public static List<Object> addNoNull (List<Object> collect) {
        return collect.stream().filter(obj -> obj!=null).collect(Collectors.toList());
    }
    
}
