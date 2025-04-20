package il.test.TestWithReact.data.entity.dto;

public final class ViewLevel {
    public static class All {}
    public static class List extends All {}
    public static class Public extends List {}
    public static class Protected extends Public {}
    public static class Admin extends Protected {}
}
