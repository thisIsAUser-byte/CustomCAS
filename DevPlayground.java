import java.util.Random;
import java.util.function.Supplier;
public class DevPlayground {
    public static void main() {
        Root R = new Root(new LargeFraction("1"), new LargeFraction("1"), new LargeFraction("13"), new LargeFraction("1/13  "));
        System.out.println(R);
        System.out.println(R.reciprocal());
    }
    private static void test(String name, Supplier<Atom> actualSupplier, Supplier<Atom> expectedSupplier) {
        try {
            Atom actual = actualSupplier.get();
            Atom expected = expectedSupplier.get();
            if (!actual.equalsTo(expected)) {
                System.out.println("FAIL: " + name + " | got=" + actual + ", expected=" + expected);
            } else {
                System.out.println("PASS: " + name + "(result: " + actual + ")");
            }
        } catch (Exception e) {
            IO.println("ERROR: " + name + " | " + e.getMessage());
        }
    }
}
