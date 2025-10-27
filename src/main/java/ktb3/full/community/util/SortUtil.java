package ktb3.full.community.util;

import ktb3.full.community.common.exception.InvalidSortPropertyException;
import ktb3.full.community.dto.page.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;

public class SortUtil {

    @SuppressWarnings("unchecked")
    public static <T, R extends Comparable<R>> Comparator<T> getComparator(Sort sort) {
        Comparator<T> comparator = Comparator.comparing(t -> {
            try {
                Field field = getFieldRecursively(t.getClass(), sort.getProperty());
                field.setAccessible(true);
                return (R) field.get(t);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(String.format("필드 %s에 접근할 수 없습니다.", sort.getProperty()));
            }
        });

        if (sort.isDescending()) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private static Field getFieldRecursively(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new InvalidSortPropertyException();
    }
}
