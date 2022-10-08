public class StringOp {
    public static void main(String[] args) {
        String s = "[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]";
        System.out.println(s.substring(0, 5));
        System.out.println(s.substring(6));
        System.out.println(s.substring(6, 11));
        System.out.println(s.substring(6, s.length()));
        System.out.println(s.substring(6, s.length() - 1));
        System.out.println(s.substring(6, s.length() - 1).length());
    }
}
