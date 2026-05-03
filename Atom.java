import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
/*
RULES TO REMEMBER
Hello, visitor. It appears you come here to investigate this class. Classic old thing
that's been around since the dawn of computing. But that's not the point. This comment,
despite its tone, serves only to list possible outputs that can come out of certain inputs.
If you refuse to follow any of these then enjoy your "perfect" all-nighter.

SCALARS & SCALARS
LargeInteger + LargeInteger = LargeInteger;
LargeInteger - LargeInteger = LargeInteger;
LargeInteger * LargeInteger = LargeInteger;
LargeInteger / LargeInteger = LargeFraction; (unless you use the integer division method, then
you get LargeInteger. But who even does that?)
LargeInteger ^ LargeInteger = LargeInteger or LargeFraction; (LargeFraction if exponent is negative.
That's properties of exponents for you.)
LargeFraction + LargeFraction = LargeFraction;
LargeFraction - LargeFraction = LargeFraction;
LargeFraction * LargeFraction = LargeFraction;
LargeFraction / LargeFraction = LargeFraction;
LargeFraction ^ LargeFraction = LargeExponent or LargeFraction; (Roots are a thing.
Oh, you forgot they were? Your middle school math teacher must be extremely proud of you.)

SCALARS AND TENSORS
Scalar × Vector → Vector; (commutative property of multiplication.)
Scalar × Matrix → Matrix; (commutative property of multiplication AGAIN.)

TENSORS AND TENSORS
Vector + Vector -> Vector; (look at that. Quite the satisfaction.)
Matrix + Matrix -> Matrix; (it happened again. The math gods are playing nice.)
Matrix × Vector → Vector; (it feels wrong, I know. But it comes from dot products. Don't worry
you'll get used to it.)
Matrix × Matrix → Matrix (this...I don't have to explain.)

LAYERS/MODELS
Layer applied to Vector → Vector(it's a small step in gradient descent. Learn neural networking.)
Layer × Layer → maybe composition, maybe forbidden; (do not question these. Not yet.)

WE DON'T TALK ABOUT THESE
Vector + Scalar → probably forbidden; (Do not question these guys back here. It's for your own good.)
Vector + Matrix -> forbiden... (You sure you wanna go with this?)
Exponentiation on Tensors → probably forbidden unless special cases; (YOU ASKED TOO MANY QUESTIONS.
YOU MUST BE CONDEMNED FOR YOUR ACTIONS.)

THE SUMMARY
When performing an operation, if the result leaves a certain family, promote to the next layer that
can still represent the result. At least, if that is possible.
 */

/*
This is the head of the system; it is responsible for things like printing a number in string form and doing equality checks.
Operations are resolved via dynamic dispatch:
 - Each subclass overrides arithmetic methods
 - Type-specific logic is handled via instanceof checks where needed
 This replaces the previously done centralized algorithm approach.
 */
public abstract class Atom{
    public abstract String toString();
    public abstract boolean equalsTo(Atom other);
    public abstract boolean notEqualsTo(Atom other);
}
abstract class ScalarAtom extends Atom{
    public abstract LargeInteger floor();
    public abstract LargeInteger ceil();
    public abstract long sign();
    public abstract ScalarAtom magnitude();
    public abstract long compareMags(ScalarAtom other);
    public abstract long compareTo(ScalarAtom other);
    public boolean greaterThan(ScalarAtom other){return this.compareTo(other) > 0;}
    public boolean lessThan(ScalarAtom other){return this.compareTo(other) < 0;}
    public boolean greaterThanOrEqualTo(ScalarAtom other){return this.compareTo(other) >= 0;}
    public boolean lesserThanOrEqualTo(ScalarAtom other){return this.compareTo(other) <= 0;}
    public abstract ScalarAtom negate();
    public abstract ScalarAtom add(ScalarAtom other);
    public abstract ScalarAtom subtract(ScalarAtom other);
    public abstract ScalarAtom multiply(ScalarAtom other);
    public abstract ScalarAtom divide(ScalarAtom other);
    public abstract ScalarAtom reciprocal();
    public abstract ScalarAtom mod(ScalarAtom other);
    public abstract ScalarAtom square();
    public abstract ScalarAtom cube();
    public abstract ScalarAtom percent();
    public abstract ScalarAtom permille();
}
abstract class TensorAtom extends Atom{
    public abstract int[] shape();
    public abstract TensorAtom add(TensorAtom other);
    public abstract TensorAtom multiply(TensorAtom other);
    public abstract TensorAtom scale(ScalarAtom scalar);
}
abstract class ModelAtom extends Atom{}
abstract class PhysicalAtom extends Atom {}
class LargeInteger extends ScalarAtom {
    //instance variables
    private final ArrayList<Long> number;
    static final LargeInteger negativeOne = new LargeInteger(-1L);
    static final LargeInteger zero = new LargeInteger(0L);
    static final LargeInteger one = new LargeInteger(1L);
    static final LargeInteger two = new LargeInteger(2L);
    static final LargeInteger three = new LargeInteger(3L);
    static final LargeInteger four = new LargeInteger(4L);
    static final LargeInteger five = new LargeInteger(5L);
    static final LargeInteger six = new LargeInteger(6L);
    static final LargeInteger seven  = new LargeInteger(7L);
    static final LargeInteger eight = new LargeInteger(8L);
    static final LargeInteger nine = new LargeInteger(9L);
    static final LargeInteger ten = new LargeInteger(10L);
    static final LargeInteger eleven = new LargeInteger(11L);
    static final LargeInteger thirteen = new LargeInteger(13L);
    static final LargeInteger seventeen = new LargeInteger(17L);
    static final LargeInteger nineteen = new LargeInteger(19L);
    static final LargeInteger twentyThree = new LargeInteger(23L);
    static final LargeInteger twentyNine = new LargeInteger(29L);
    static final LargeInteger thirtyOne = new LargeInteger(31L);
    static final LargeInteger thirtySeven = new LargeInteger(37L);
    static final LargeInteger fortyOne = new LargeInteger(41L);
    static final LargeInteger fortyThree = new LargeInteger(43L);
    static final LargeInteger fortySeven = new LargeInteger(47L);
    static final LargeInteger fiftyThree = new LargeInteger(53L);
    static final LargeInteger fiftyNine = new LargeInteger(59L);
    static final LargeInteger sixtyOne = new LargeInteger(61L);
    static final LargeInteger sixtySeven = new LargeInteger(67L);
    static final LargeInteger seventyOne = new LargeInteger(71L);
    static final LargeInteger seventyThree = new LargeInteger(73L);
    static final LargeInteger seventyNine = new LargeInteger(79L);
    static final LargeInteger eightyThree = new LargeInteger(83L);
    static final LargeInteger eightyNine = new LargeInteger(89L);
    static final LargeInteger ninetySeven = new LargeInteger(97L);
    static final LargeInteger hundred = new LargeInteger(100L);
    static final LargeInteger hundredOne = new LargeInteger(101L);
    static final LargeInteger threeHundredThree = new LargeInteger(103L);
    static final LargeInteger thousand = new LargeInteger(1000L);
    static final LargeInteger thousandThree = new LargeInteger(1003L);
    static final LargeInteger threeThousandThree = new LargeInteger(3003L);
    static final LargeInteger million = new LargeInteger(1000000L);
    static final LargeInteger billion = new LargeInteger(1000000000L);
    static final LargeInteger trillion = new LargeInteger(1000000000000L);
    static final LargeInteger quadrillion = new LargeInteger(1000000000000000L);
    private static final SecureRandom RNG = new SecureRandom();
    private final boolean sign;
    //these constructors serve as various ways to build the number from structure of the inputs.
    public LargeInteger(){number = new ArrayList<>(); sign = false;}
    public LargeInteger(long num){
        number = new ArrayList<>();
        do{
            number.addFirst(num % 1000000000L);
            num /= 1000000000L;
        }while(num != 0L);
        sign = number.getFirst() < 0;
        number.replaceAll(Math::abs);
    }
    public LargeInteger(ArrayList<Long> digits){
        number = new ArrayList<>();
        number.addAll(digits);
        if(number.isEmpty()){
            number.add(0L);
            sign = false;
            return;
        }
        for(int i = number.size()-1; i > 0; i--){
            number.set(i-1, number.get(i-1) + number.get(i)/1000000000L);
            number.set(i, number.get(i)%1000000000L);
        }
        while(Math.abs(number.get(0)) >= 1000000000L){
            number.addFirst(number.get(0)/1000000000L);
            number.set(1, number.get(1)%1000000000L);
        }
        while(number.size() > 1 && number.get(0) == 0L) number.removeFirst();
        sign = number.get(0) < 0;
        number.replaceAll(Math::abs);
    }
    public LargeInteger(String str){
        ArrayList<Long> ar = new ArrayList<>();
        if(str.isEmpty()){
            ar.add(0L);
            sign = false;
            number = ar;
            return;
        }
        if (!str.matches("-?\\d+")) throw new IllegalArgumentException("Invalid input: " + str);
        //eliminate leading zeros to avoid ambiguity
        if(str.charAt(0) == '-') while(str.length() > 2 && str.charAt(1) == '0') str = "-" + str.substring(2);
        else while(str.length() > 1 && str.charAt(0) == '0') str = str.substring(1);
        if(str.equals("-0"))
            str = str.substring(1);
        for(int j = str.length(); j > 0; j-=9){
            if(str.charAt(0) == '-' && j <= 10){
                ar.addFirst(Long.parseLong(str.substring(1, j)));
                break;
            }else if(str.charAt(0) != '-' && j <= 9){
                ar.addFirst(Long.parseLong(str.substring(0, j)));
                break;
            }else
                ar.addFirst(Long.parseLong(str.substring(j-9, j)));
        }
        sign = str.charAt(0) == '-';
        number = ar;
    }
    //returns a String version that can be printed.
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        if(sign) str.append("-");
        for(int i=0; i < number.size(); i++){
            if(i > 0){
                if(number.get(i) < (long) 100000000)
                    str.append(0);
                if(number.get(i) < (long) 10000000)
                    str.append(0);
                if(number.get(i) < (long) 1000000)
                    str.append(0);
                if(number.get(i) < (long) 100000)
                    str.append(0);
                if(number.get(i) < (long) 10000)
                    str.append(0);
                if(number.get(i) < (long) 1000)
                    str.append(0);
                if(number.get(i) < (long) 100)
                    str.append(0);
                if(number.get(i) < (long) 10)
                    str.append(0);
            }
            str.append(number.get(i));
        }return str.toString();
    }
    //evaluates mathematical equality of two numbers.
    @Override
    public boolean equalsTo(Atom other){
        if(!(other instanceof ScalarAtom)) return false;
        return this.compareTo((ScalarAtom) other) == 0L;
    }
    //evaluates mathematical inequality of two numbers.
    @Override
    public boolean notEqualsTo(Atom other){
        if(!(other instanceof ScalarAtom)) return true;
        return this.compareTo((ScalarAtom) other) != 0L;
    }
    //returns the sign of an integer.
    @Override
    public long sign(){
        if(this.toString().charAt(0) != '-'){
            for (Long aLong : number) if (aLong != 0L) return 1L;
            return 0L;
        }
        return -1L;
    }
    //returns a new integer with the same list as the old one.
    @Override
    public LargeInteger magnitude(){
        ArrayList<Long> result = new ArrayList<>();
        for(Long l: number) result.add(Math.abs(l));
        return new LargeInteger(result);
    }
    @Override
    public LargeInteger floor(){return this;}
    @Override
    public LargeInteger ceil(){return this;}
    //this serves as a way to optimize modulo for large integer powers.
    public LargeFraction powMod(LargeInteger exp, LargeInteger rem){
        LargeFraction result = new LargeFraction(1);
        LargeInteger base = new LargeInteger(this.number);
        LargeInteger expTemp = new LargeInteger(exp.number);
        while(expTemp.sign() > 0L){
            if(expTemp.mod2().equalsTo(one)){
                if(exp.sign() == 1L) result = (LargeFraction) result.multiply(base).mod(rem);
                else result = (LargeFraction) result.divide(base).mod(rem);
            }
            base = (LargeInteger) base.square();
            if(exp.sign() == 1L) base = (LargeInteger) base.mod(rem);
            expTemp = expTemp.LongDivision(two);
        }
        if(rem.sign() == -1) result = (LargeFraction) result.add(rem);
        return result;
    }
    //generates a random integer within a given range.
    public static LargeInteger random(LargeInteger bottom, LargeInteger top){
        //generates a random number within a certain range, including both bounds in the range
        if (bottom.compareTo(top) == 1L) throw new IllegalArgumentException("Error: Invalid range");
        LargeInteger range = (LargeInteger) top.subtract(bottom);
        LargeInteger result = new LargeInteger("0");
        result.number.clear();
        boolean alreadySmaller = false;
        for (int i = 0; i < range.number.size(); i++) {
            long boundChunk = alreadySmaller ? 1000000000L - 1 : range.number.get(i);
            long generated = RNG.nextLong(boundChunk + 1);
            result.number.add(generated);
            if (!alreadySmaller && generated < range.number.get(i)) alreadySmaller = true;
        }
        return (LargeInteger) result.add(bottom);
    }
    //uses Miller-Rabin to determine if an integer is prime.
    public boolean isPrime(){
        if(this.lessThan(LargeInteger.two))
            return false;
        LargeInteger[] smallPrimes = {two, three, five, seven, eleven, thirteen, seventeen, nineteen, twentyThree, twentyNine, thirtyOne, thirtySeven, fortyOne, fortyThree, fortySeven, fiftyThree, fiftyNine, sixtyOne, sixtySeven, seventyOne, seventyThree, seventyNine, eightyThree, eightyNine, ninetySeven, hundredOne};
        for(LargeInteger n: smallPrimes)
            if(this.mod(n).equalsTo(LargeInteger.zero)){
                if(this.notEqualsTo(n)) return false;
                return true;
            }
        int rounds = 60;
        for(int i = 0; i < rounds; i++){
            LargeInteger witness = random(two, (LargeInteger) this.subtract(two));
            if(!isWitness(witness)) return false;
        }
        return true;
    }
    //private helper for isPrime
    private boolean isWitness(LargeInteger witness){
        LargeInteger nMinusOne = (LargeInteger) this.subtract(one);
        LargeInteger multiplier = new LargeInteger(nMinusOne.number);
        LargeInteger twoPower = zero;
        while(multiplier.mod(two).equalsTo(zero)){
            twoPower = (LargeInteger) twoPower.add(one);
            multiplier = multiplier.LongDivision(two);
        }
        LargeInteger x = witness.powMod(multiplier, this).numerator;
        if(x.equalsTo(one) || x.equalsTo(nMinusOne)) return true;
        for(LargeInteger r = one; r.lessThan(twoPower); r= (LargeInteger) r.add(one)){
            x = (LargeInteger) x.multiply(x).mod(this);
            if(x.equalsTo(nMinusOne)) return true;
        }
        return false;
    }
    //helper that finds a nontrivial factor of the input.
    public LargeInteger pollardsRho(){
        while(true){
            LargeInteger x = random(two, (LargeInteger) this.subtract(one));
            LargeInteger y = new LargeInteger(x.number);
            LargeInteger c = random(one, (LargeInteger) this.subtract(two));
            //gcd step
            LargeInteger G = one;
            while(G.equalsTo(one)){
                //tortoise step
                x = (LargeInteger) x.multiply(x).add(c).mod(this);
                //hare step
                y = (LargeInteger) y.multiply(y).add(c).mod(this);
                y = (LargeInteger) y.multiply(y).add(c).mod(this);
                G = ((LargeInteger) x.subtract(y)).magnitude().gcd(this);
            }
            if(!G.equalsTo(this))
                return G;
        }
    }
    //factors a number using trial division for small primes and pollard's rho for the rest. despite the implications, it is not strict prime factorization. why? well, 0 and 1 do not have prime factorizations. however, a total function is basically required in programming.
    public ArrayList<PrimeExponent> factor(){
        ArrayList<PrimeExponent> p = new ArrayList<>();
        if(this.equalsTo(zero)){
            p.add(new PrimeExponent(zero, (LargeFraction) one.divide(one)));
            return p;
        }else if(this.equalsTo(one)){
            p.add(new PrimeExponent(one, (LargeFraction) one.divide(one)));
            return p;
        }else if(this.isPrime()){
            p.add(new PrimeExponent(this, (LargeFraction) one.divide(one)));
            return p;
        }
        if(this.sign() == -1L) p.add(new PrimeExponent(negativeOne, (LargeFraction) one.divide(one)));
        LargeInteger copy = this.magnitude();
        LargeInteger[] smallPrimes = {two, three, five, seven, eleven, thirteen, seventeen, nineteen, twentyThree, twentyNine, thirtyOne, thirtySeven, fortyOne, fortyThree, fortySeven, fiftyThree, fiftyNine, sixtyOne, sixtySeven, seventyOne, seventyThree, seventyNine, eightyThree, eightyNine, ninetySeven, hundredOne};
        for(LargeInteger s: smallPrimes){
            LargeInteger exp = zero;
            while(copy.mod(s).equalsTo(zero)){
                exp = (LargeInteger) exp.add(one);
                copy = copy.LongDivision(s);
            }
            if(exp.notEqualsTo(zero)) p.add(new PrimeExponent(s, (LargeFraction) exp.divide(one)));
        }
        if(copy.equalsTo(one)) return p;
        //pollard's rho
        while(!copy.isPrime() && copy.greaterThan(one)){
            LargeInteger P = copy.pollardsRho();
            if(!P.isPrime()){
                ArrayList<PrimeExponent> list = P.factor();
                for(PrimeExponent pr: list) p.add(new PrimeExponent(pr.base, pr.exponent));
            }else p.add(new PrimeExponent(P, new LargeFraction(1)));
            copy = copy.LongDivision(P);
        }
        if(copy.greaterThan(one)) p.add(new PrimeExponent(copy, new LargeFraction(1)));
        PrimeExponent.sortBases(p);
        for(int i = 1; i < p.size(); i++){
            if(p.get(i).base.equalsTo(p.get(i-1).base)){
                p.get(i-1).exponent = (LargeFraction) p.get(i-1).exponent.add(p.get(i).exponent);
                p.remove(i);
                i--;
            }
        }
        return p;
    }
    //the inverse of the factor method.
    public static LargeInteger unfactor(ArrayList<PrimeExponent> pr){
        LargeInteger result = one;
        for(PrimeExponent p: pr){
            if(p.exponent.numerator.lessThan(zero) || p.exponent.denominator.notEqualsTo(one)) throw new ArithmeticException("Error: Result not an integer");
            LargeInteger expTemp = new LargeInteger(p.exponent.numerator.number);
            LargeInteger basTemp = new LargeInteger(p.base.number).multiply(p.base.sign());
            while(expTemp.greaterThan(zero)){
                if(expTemp.mod2().equalsTo(one)) result = (LargeInteger) result.multiply(basTemp);
                basTemp = (LargeInteger) basTemp.multiply(basTemp);
                expTemp = expTemp.LongDivision(two);
            }
        }
        return result;
    }
    //approximates a number to scientific notation with a specified number of digits.
    public String approximateToScientific(long digits){
        StringBuilder str = new StringBuilder();
        if(digits <= 0) throw new IllegalArgumentException("Error: Digit count must be positive");
        else if(this.equalsTo(LargeInteger.zero)) return "0. * 10^0";
        String thi = this.toString();
        if(sign){
            str.append("-");
            digits++;
        }
        for(int i = thi.indexOf("-")+1; i < digits; i++){
            if(i < thi.length()){
                str.append(thi.charAt(i));
                if(i == thi.indexOf("-")+1) str.append(".");
            }else str.append("0");
        }
        long power = 9L *(number.size()-1) + (long)Math.log10(number.getFirst());
        str.append(" * 10^").append(power);
        return str.toString();
    }
    //compares the magnitudes of two numbers.
    @Override
    public long compareMags(ScalarAtom other) {
        if(other instanceof LargeInteger){
            //do actual COMPARISON. avoid recursion
            if(this.number.size() > ((LargeInteger) other).number.size()) return 1L;
            else if(this.number.size() < ((LargeInteger) other).number.size()) return -1L;
            for(int i = 0; i < this.number.size(); i++){
                if(this.number.get(i) > ((LargeInteger) other).number.get(i)) return 1L;
                else if(this.number.get(i) < ((LargeInteger) other).number.get(i)) return -1L;
            }
            return 0L;
        }
        return this.magnitude().subtract(other.magnitude()).sign();
    }
    //compares two numbers. for integers and exponents, it uses special techniques.
    //integer comparison uses compareMags, which does most of the hard work.
    @Override
    public long compareTo(ScalarAtom l) {
        if(l instanceof LargeInteger){
            if(this.compareMags(l) > 0L){
                if(this.sign() == -1L) return -1L;
                return 1L;
            }else if(this.compareMags(l) < 0L){
                if(l.sign() == -1L) return 1L;
                return -1L;
            }
            if(this.sign() == -1L && l.sign() == 1L) return -1L;
            else if(this.sign() == 1L && l.sign() == -1L) return 1L;
            return 0L;
        }else if(l instanceof LargeExponent){
            ArrayList<Root> rt = new ArrayList<>();
            rt.add(new Root((LargeFraction) this.divide(one), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            rt.add(new Root(new LargeFraction(0), new LargeFraction(0), (LargeExponent) l));
            return new RootSum(rt).sign();
        }
        return this.subtract(l).sign();
    }
    //these four are fixed methods that directly branch off of compareTo.
    public boolean greaterThan(ScalarAtom other){return this.compareTo(other) > 0L;}
    public boolean lessThan(ScalarAtom other) {return this.compareTo(other) < 0L;}
    public boolean greaterThanOrEqualTo(ScalarAtom other){return this.compareTo(other) >= 0L;}
    public boolean lesserThanOrEqualTo(ScalarAtom other){return this.compareTo(other) <= 0L;}
    public ScalarAtom negate() {
        ArrayList<Long> l = new ArrayList<>(this.number);
        l.set(0, l.getFirst()*-1);
        if(this.sign)
            l.set(0, l.getFirst()*-1);
        return new LargeInteger(l);
    }
    public LargeInteger xor(LargeInteger l){
        Base copy = new Base(this, LargeInteger.two);
        Base copy2 = new Base(l, LargeInteger.two);
        while(copy.list.size() < copy2.list.size())
            copy.list.addFirst(LargeInteger.zero);
        while(copy2.list.size() < copy.list.size()) copy2.list.addFirst(LargeInteger.zero);
        ArrayList<Long> result = new ArrayList<>();
        for(int i = 0; i < copy.list.size(); i++){
            int str = Integer.parseInt(copy.list.get(i).toString());
            int str2 = Integer.parseInt(copy2.list.get(i).toString());
            long thing = str ^ str2;
            result.add(thing);
        }
        LargeInteger x = LargeInteger.zero;
        for(int i = 1; i < result.size(); i++) x = (LargeInteger) x.multiply(2).add(new LargeInteger(result.get(i)));
        return x;
    }
    public LargeInteger gcd(LargeInteger other){
        LargeInteger copy = this.magnitude();
        LargeInteger copy2 = other.magnitude();
        LargeInteger commonTwos = zero;
        while(copy2.isEven() && copy.isEven() && copy.sign() != 0L && copy2.sign() != 0L){
            copy2 = copy2.LongDivision(two);
            copy = copy.LongDivision(two);
            commonTwos = (LargeInteger) commonTwos.add(one);
        }
        if(copy.sign() == 0L) return copy2;
        else if(copy2.sign() == 0L) return copy;
        while (copy2.sign() != 0L) {
            if (copy.greaterThan(copy2)) {
                LargeInteger temp = copy.magnitude();
                copy = copy2.magnitude();
                copy2 = temp.magnitude();
            }
            copy2 = (LargeInteger) copy2.subtract(copy);
            while(copy2.isEven() && copy2.sign() != 0L) copy2 = copy2.LongDivision(two);
        }
        for(LargeInteger i = zero; i.lessThan(commonTwos); i = (LargeInteger) i.add(one)) copy = (LargeInteger) copy.multiply(two);
        return copy;
    }
    public LargeInteger gcd(ArrayList<LargeInteger> other){
        ArrayList<LargeInteger> copy = new ArrayList<>();
        for(LargeInteger n: other) copy.add(new LargeInteger(n.number));
        while(copy.size() > 1){
            copy.set(0, copy.get(0).gcd(copy.get(1)));
            copy.remove(1);
        }
        return this.gcd(copy.get(0));
    }
    public LargeInteger lcm(LargeInteger other){return ((LargeInteger)this.multiply(other)).LongDivision(this.gcd(other));}
    public LargeInteger lcm(ArrayList<LargeInteger> other){
        LargeInteger copy = this.lcm(other.getFirst());
        for(int i = 1; i < other.size(); i++) copy = copy.lcm(other.get(i));
        return copy;
    }
    public static LargeInteger triangular(LargeInteger l){return ((LargeInteger) l.add(one)).nCr(two);}
    public static LargeInteger tetrahedral(LargeInteger l){return ((LargeInteger)l.add(two)).nCr(three);}
    public static LargeInteger figurate(LargeInteger l, LargeInteger k){return ((LargeInteger)k.add(l).subtract(one)).nCr(k);}
    //This method serves to add two numbers. More details in the method
    @Override
    public ScalarAtom add(ScalarAtom other) {
        //This one adds two integers via base-10^9 chunking. Like every other method, it assumes both are normalized to the best they can be. Runs in time complexity O(n), n being max number of chunks
        if(other instanceof LargeInteger){
            LargeInteger copy = new LargeInteger(this.number);
            LargeInteger copy2 = new LargeInteger(((LargeInteger)other).number);
            ArrayList<Long> sum = new ArrayList<>();
            //determine finalSign
            long finalSign = 1L;
            if(this.compareMags(other) > 0L){
                if(this.sign() == -1L) finalSign = -1L;
            }else if(this.compareMags(other) < 0L){
                if(other.sign() == -1L) finalSign = -1L;
            }else
                if (this.sign) finalSign = -1L;
            //pad zeros
            while(copy.number.size() < copy2.number.size()) copy.number.addFirst(0L);
            while(copy2.number.size() < copy.number.size()) copy2.number.addFirst(0L);
            //addition case
            long part = 0;
            if(this.sign() == other.sign()){
                for(int i = copy.number.size()-1; i >= 0; i--){
                    part += copy.number.get(i) + copy2.number.get(i);
                    sum.addFirst(part % 1000000000L);
                    part /= 1000000000L;
                }
                while(part > 0){
                    sum.addFirst(part % 1000000000L);
                    part /= 1000000000L;
                }
                //eliminate leading zeros
                while(sum.size() > 1 && sum.getFirst() == 0L) sum.removeFirst();
                LargeInteger result = new LargeInteger(sum);
                if(this.sign() == -1L && result.sign() != 0) result = (LargeInteger) result.negate();
                return result;
            }//subtraction case
            else{
                for(int i = copy.number.size() - 1; i >= 0; i--){
                    part += copy.number.get(i)*this.sign() + copy2.number.get(i)*other.sign();
                    sum.addFirst(part % 1000000000L);
                    if(i != 0){
                        if(finalSign == 1 && sum.getFirst() < 0){
                            sum.set(0, Math.floorMod(sum.getFirst(), 1000000000L));
                            part /= 1000000000L;
                            part -= 1;
                        }else if(finalSign == -1 && sum.getFirst() > 0){
                            sum.set(0, Math.floorMod(-sum.getFirst(), 1000000000L));
                            part /= 1000000000L;
                            part += 1;
                        }else part /= 1000000000L;
                    }else part /= 1000000000L;
                }
                while(part > 0){
                    sum.addFirst(part % 1000000000L);
                    part /= 1000000000L;
                }
                //eliminate leading zeros
                while(sum.size() > 1 && sum.getFirst() == 0L) sum.removeFirst();
                sum.replaceAll(Math::abs);
                sum.set(0, sum.getFirst()*finalSign);
                return new LargeInteger(sum);
            }
        }//this one adds two fractions. despite the looks, due to LargeFraction.add eventually, it will reach the above instance case.
        else if(other instanceof LargeFraction) return this.divide(one).add(other);
        else if(other instanceof LargeExponent) return new Root((LargeFraction) this.divide(LargeInteger.one), new LargeFraction(1), ((LargeExponent) other).base, ((LargeExponent) other).exponent);
        else if(other instanceof Root) return new Root((LargeFraction) this.add(((Root) other).rationalPart), ((Root) other).coefficient, ((Root) other).radical, ((Root) other).power);
        else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            for(Root r: ((RootSum) other).sum) rt.add(new Root(r.rationalPart, r.coefficient, r.radical, r.power));
            rt.add(new Root((LargeFraction) this.divide(LargeInteger.one), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operations with these types together not supported yet");
    }
    public LargeInteger add(ArrayList<LargeInteger> f){
        LargeInteger result = zero;
        for(LargeInteger w: f) result = (LargeInteger) result.add(w);
        return (LargeInteger) this.add(result);
    }
    public LargeInteger add(long other){return (LargeInteger) this.add(new LargeInteger(other));}
    @Override
    public ScalarAtom subtract(ScalarAtom other){return this.add(other.negate());}
    public LargeInteger subtract(ArrayList<LargeInteger> l){
        LargeInteger result = zero;
        for(LargeInteger w: l) result = (LargeInteger) result.subtract(w);
        return (LargeInteger) this.add(result);
    }
    public LargeInteger subtract(long other){return (LargeInteger) this.add(new LargeInteger(other).multiply(negativeOne));}
    private LargeInteger shift(int n){
        if(this.sign() == 0L) return this;
        LargeInteger copy = this.magnitude();
        for(int i = 1; i <= n; i++) copy.number.add(0L);
        return copy;
    }
    @Override
    public ScalarAtom multiply(ScalarAtom other) {
        if(other instanceof LargeInteger){
            //handle null case
            if(((LargeInteger) other).number.isEmpty())
                throw new IllegalArgumentException("Error: Empty list");
            else if(this.number.size() == 1 && ((LargeInteger) other).number.size() == 1){
                long re = this.number.getFirst() * ((LargeInteger) other).number.getFirst() * this.sign() * other.sign();
                return new LargeInteger(re);
            }
            LargeInteger copy = new LargeInteger(this.number);
            LargeInteger copy2 = new LargeInteger(((LargeInteger) other).number);
            int n = Math.max(copy.number.size(), copy2.number.size())/2;
            LargeInteger breakIt = new LargeInteger(0);
            while(copy.number.size() > n)
                breakIt = (LargeInteger) breakIt.shift(1).add(new LargeInteger(copy.number.removeFirst()));
            LargeInteger breakIt2 = new LargeInteger(0);
            while(copy2.number.size() > n)
                breakIt2 = (LargeInteger) breakIt2.shift(1).add(new LargeInteger(copy2.number.removeFirst()));
            while(breakIt.number.size() > 1 && breakIt.number.getFirst() == 0L)
                breakIt.number.removeFirst();
            while(breakIt2.number.size() > 1 && breakIt2.number.getFirst() == 0L)
                breakIt2.number.removeFirst();
            while(copy.number.size() > 1 && copy.number.getFirst() == 0L)
                copy.number.removeFirst();
            while(copy2.number.size() > 1 && copy2.number.getFirst() == 0L)
                copy2.number.removeFirst();
            //the work
            LargeInteger z0 = (LargeInteger) copy.multiply(copy2);
            LargeInteger z2 = (LargeInteger) breakIt.multiply(breakIt2);
            LargeInteger z1 = (LargeInteger) breakIt.add(copy).multiply(breakIt2.add(copy2));
            LargeInteger middle = (LargeInteger) z1.subtract(z2).subtract(z0);
            LargeInteger result = (LargeInteger) z2.shift(2*n).add(middle.shift(n)).add(z0);
            if(result.sign() != 0)
                result.number.set(0, result.number.getFirst()*this.sign()*other.sign());
            return result;
        }else if(other instanceof LargeFraction) return new LargeFraction((LargeInteger) this.multiply(((LargeFraction) other).numerator), ((LargeFraction) other).denominator);
        else if(other instanceof LargeExponent) return new LargeExponent((LargeFraction) this.divide(one), new LargeFraction(1)).multiply(other);
        else if(other instanceof Root) return new Root((LargeFraction) this.multiply(((Root) other).rationalPart), (LargeFraction) this.multiply(((Root) other).coefficient), ((Root) other).radical, ((Root) other).power);
        else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            for(Root r: ((RootSum) other).sum) rt.add(new Root((LargeFraction) r.rationalPart.multiply(this), (LargeFraction) r.coefficient.multiply(this), r.radical, r.power));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    public LargeFraction multiply(ArrayList<LargeFraction> l){
        LargeFraction result = new LargeFraction(1);
        for(LargeFraction w: l)
            result = (LargeFraction) result.multiply(w);
        return (LargeFraction) this.multiply(result);
    }
    public LargeInteger multiply(long greg){return (LargeInteger) this.multiply(new LargeInteger(greg));}
    @Override
    public ScalarAtom square(){return this.multiply(this);}
    @Override
    public ScalarAtom cube(){return this.multiply(this).multiply(this);}
    private static LargeInteger productRange(LargeInteger left, LargeInteger right) {
        if (left.equalsTo(right)) return left;
        if (right.subtract(left).equalsTo(one)) return (LargeInteger) left.multiply(right);
        LargeInteger mid = ((LargeInteger)left.add(right)).LongDivision(two);
        return (LargeInteger) productRange(left, mid).multiply(productRange((LargeInteger) mid.add(one), right));
    }
    public static LargeInteger factorial(LargeInteger l){
        if(l.sign() < 0L) throw new ArithmeticException("Error: Factorial undefined for negative integers");
        if(l.equalsTo(zero) || l.equalsTo(one)) return one;
        return productRange(one, l);
    }
    public LargeInteger LongDivision(LargeInteger other){
        if(other.sign() == 0) throw new ArithmeticException("Error: Cannot divide by 0");
        else if(this.compareMags(other) < 0L) return LargeInteger.zero;
        else if(this.compareMags(other) == 0L) return new LargeInteger(this.sign()*other.sign());
        //knuth-style division
        LargeInteger copy = this.magnitude();
        LargeInteger copy2 = other.magnitude();
        if(copy2.number.size() == 1){
            long carry = 0;
            ArrayList<Long> quotient = new ArrayList<>();
            for (long chunk : copy.number) {
                long current = carry * 1000000000L + chunk;
                long qChunk = current / copy2.number.getFirst();
                carry = current % copy2.number.getFirst();
                quotient.add(qChunk);
            }
            while(quotient.getFirst() == 0L && quotient.size() > 1) quotient.removeFirst();
            quotient.set(0, quotient.getFirst()*this.sign()*other.sign());
            return new LargeInteger(quotient);
        }
        long factor = 1000000000L/(copy2.number.getFirst()+1);
        copy = copy.multiply(factor);
        copy2 = copy2.multiply(factor);
        LargeInteger remainder = new LargeInteger(0);
        for(int i = 0; i < copy2.number.size(); i++) remainder = remainder.shift(1).add(copy.number.get(i));
        ArrayList<Long> quotient = new ArrayList<>();
        for(int i = 0; i <= copy.number.size() - copy2.number.size(); i++){
            //estimate
            long qHat = remainder.number.get(0)*1000000000L + remainder.number.get(1);
            qHat = qHat/copy2.number.getFirst();
            qHat = Math.min(qHat, 999999999L);
            remainder = (LargeInteger) remainder.subtract(copy2.multiply(qHat));
            while(remainder.sign() < 0L){
                qHat--;
                remainder = (LargeInteger) remainder.add(copy2);
            }
            quotient.add(qHat);
            int nextIndex = i + copy2.number.size();
            if (nextIndex < copy.number.size()) remainder = remainder.shift(1).add(copy.number.get(nextIndex));
        }
        while(quotient.getFirst() == 0L && quotient.size() > 1) quotient.removeFirst();
        quotient.set(0, quotient.getFirst()*this.sign()*other.sign());
        return new LargeInteger(quotient);
    }
    public LargeInteger floorDivision(LargeInteger other){
        LargeInteger result = this.LongDivision(other);
        boolean shouldBeNegative = this.sign() != other.sign();
        if(other.multiply(result).notEqualsTo(this) && shouldBeNegative) result = (LargeInteger) result.subtract(LargeInteger.one);
        return result;
    }
    @Override
    public ScalarAtom divide(ScalarAtom other) {
        if(other instanceof LargeInteger) return new LargeFraction(this, (LargeInteger) other);
        else if(other instanceof LargeFraction) return new LargeFraction((LargeInteger) ((LargeFraction) other).denominator.multiply(this), ((LargeFraction) other).numerator);
        else if(other instanceof LargeExponent) return new Root(new LargeFraction(0), (LargeFraction) this.divide(LargeInteger.one), ((LargeExponent) other).base, (LargeFraction)((LargeExponent) other).exponent.multiply(new LargeFraction(-1)));
        return other.reciprocal().multiply(this);
    }
    @Override
    public LargeFraction reciprocal(){return (LargeFraction) one.divide(this);}
    @Override
    public ScalarAtom mod(ScalarAtom other){
        if(other instanceof LargeInteger){
            if(other.sign() == 0L) throw new ArithmeticException("Error: Cannot divide by 0");
            return this.subtract(this.floorDivision((LargeInteger) other).multiply(other));
        }else if(other instanceof LargeFraction) return this.subtract(((LargeFraction) this.divide(other)).floor().multiply(other.magnitude()));
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    public LargeInteger mod2(){
        if(this.isOdd()) return LargeInteger.one;
        return LargeInteger.zero;
    }
    public boolean isOdd(){return this.number.getLast() % 2L == 1L;}
    public boolean isEven(){return this.number.getLast() % 2L == 0L;}
    public static void sort(ArrayList<LargeInteger> l){
        for(int i = 0; i < l.size()-1; i++){
            for(int j = i+1; j < l.size(); j++){
                if(l.get(j).lessThan(l.get(i))){
                    LargeInteger temp = new LargeInteger(l.get(j).toString());
                    l.set(j, l.get(i));
                    l.set(i, temp);
                }
            }
        }
    }
    public static LargeFraction mean(ArrayList<LargeInteger> arr){return (LargeFraction) zero.add(arr).divide(new LargeInteger(arr.size()));}
    public static LargeFraction median(ArrayList<LargeInteger> arr){
        sort(arr);
        if(arr.size() % 2 == 0) return (LargeFraction) arr.get(arr.size()/2-1).add(arr.get(arr.size()/2)).divide(two);
        return (LargeFraction) arr.get(arr.size()/2).divide(one);
    }
    public static LargeInteger min(ArrayList<LargeInteger> arr){
        LargeInteger.sort(arr);
        return arr.getFirst();
    }
    public static LargeInteger max(ArrayList<LargeInteger> arr){
        LargeInteger.sort(arr);
        return arr.getLast();
    }
    public static LargeFraction mad(ArrayList<LargeInteger> arr){
        ArrayList<LargeFraction> copy = new ArrayList<>();
        LargeFraction m = median(arr);
        for(LargeInteger l: arr)
            copy.add((LargeFraction) l.divide(one).subtract(m).magnitude());
        return LargeFraction.median(copy);
    }
    public static LargeInteger mode(ArrayList<LargeInteger> l){
        ArrayList<LargeInteger> copy = new ArrayList<>(l);
        sort(copy);
        int frequency = 1;
        ArrayList<Integer> frequencies = new ArrayList<>();
        for(int i = 1; i < copy.size(); i++){
            if(copy.get(i).equalsTo(copy.get(i-1))){
                frequency++;
                copy.remove(i);
                i--;
            }else{
                frequencies.add(frequency);
                frequency = 1;
            }
        }
        //get max index
        int index = 0;
        for(int i = 0; i < frequencies.size(); i++)
            if(frequencies.get(i) >= frequencies.get(index))
                index = i;
        return copy.get(index);
    }
    public static LargeExponent stdev(ArrayList<LargeInteger> l){
        if(l.size() < 2)
            throw new IllegalArgumentException("Error: Need at least 2 values in list");
        LargeFraction m = mean(l);
        LargeFraction result = new LargeFraction(0);
        for(LargeInteger f: l)
            result = (LargeFraction) result.add(f.subtract(m).square());
        result = result.divide(l.size() - 1);
        return new LargeExponent(result, new LargeFraction("1/2"));
    }
    public static LargeExponent stdevp(ArrayList<LargeInteger> l){
        if(l.isEmpty())
            throw new IllegalArgumentException("Error: Empty list");
        LargeFraction m = mean(l);
        LargeFraction result = new LargeFraction(0);
        for(LargeInteger f: l)
            result = (LargeFraction) result.add(f.subtract(m).square());
        result = result.divide(l.size());
        return new LargeExponent(result, new LargeFraction("1/2"));
    }
    public static LargeFraction IQR(ArrayList<LargeInteger> l){
        if(l.size() < 2)
            throw new IllegalArgumentException("Error: Need at least 2 values in list");
        sort(l);
        ArrayList<LargeInteger> copy = new ArrayList<>();
        for(int i = 0; i < l.size()/2; i++)
            copy.add(l.get(i));
        ArrayList<LargeInteger> copy2 = new ArrayList<>();
        if(l.size() % 2 == 0)
            for(int i = l.size()/2; i < l.size(); i++)
                copy2.add(l.get(i));
        else
            for(int i = l.size()/2+1; i < l.size(); i++)
                copy2.add(l.get(i));
        return (LargeFraction) median(copy2).subtract(median(copy));
    }
    @Override
    public LargeFraction percent(){return new LargeFraction(this, hundred);}
    @Override
    public LargeFraction permille(){return new LargeFraction(this, thousand);}
    public LargeInteger nCr(LargeInteger l) {
        if (this.lessThan(l))
            return zero;
        LargeInteger result = new LargeInteger(one.number);
        for(LargeInteger i = (LargeInteger) this.subtract(l).add(one); i.lesserThanOrEqualTo(this); i = (LargeInteger) i.add(one))
            result = (LargeInteger) result.multiply(i);
        return result.LongDivision(factorial(l));
    }
    public LargeFraction nPr(LargeInteger l){
        LargeInteger temp = (LargeInteger) this.subtract(l);
        if(temp.sign() == -1L) return new LargeFraction(zero, one);
        return (LargeFraction) factorial(this).divide(factorial(temp));
    }
    public LargeFraction power(LargeInteger l){return new LargeExponent((LargeFraction) this.divide(LargeInteger.one), (LargeFraction) l.divide(LargeInteger.one)).base;}
    public static LargeInteger pow10(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative exponent");
        ArrayList<Long> chunks = new ArrayList<>();
        int fullChunks = n / 9;
        int rem = n % 9;
        long first = 1;
        for (int i = 0; i < rem; i++) first *= 10;
        chunks.add(first);
        for (int i = 0; i < fullChunks; i++) chunks.add(0L);
        return new LargeInteger(chunks);
    }
}
class Base{
    ArrayList<LargeInteger> list;
    boolean sign;
    LargeInteger base;
    public Base(LargeInteger N, LargeInteger b) {
        if (b.lesserThanOrEqualTo(LargeInteger.one)) throw new IllegalArgumentException("Error: Invalid base");
        list = new ArrayList<>();
        LargeInteger copy = N.magnitude();
        do {
            list.addFirst((LargeInteger) copy.mod(b));
            copy = copy.LongDivision(b);
        } while (copy.greaterThan(LargeInteger.zero));
        sign = N.sign() == -1L;
        base = b;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        if (sign) sb.append('-');
        if(list.getFirst().sign() == 0L){
            sb.append("0");
            return sb.toString();
        }
        for (int i = 0; i < list.size(); i++){
            LargeInteger bit = list.get(i);
            if(base.lessThan(LargeInteger.ten)) sb.append(bit);
            else if(base.greaterThanOrEqualTo(LargeInteger.ten) && base.lesserThanOrEqualTo(new LargeInteger(chars.length()+10)))
                if(bit.greaterThanOrEqualTo(LargeInteger.ten)) sb.append(chars.charAt(Integer.parseInt(bit.toString())-10));
                else sb.append(bit);
            else{
                sb.append(bit);
                if(i < list.size()-1) sb.append(",");
            }
        }
        if(base.notEqualsTo(LargeInteger.ten)) sb.append(":").append(base);
        return sb.toString();
    }
}
class LargeFraction extends ScalarAtom{
    public final LargeInteger numerator;
    public final LargeInteger denominator;
    public LargeFraction(String str){
        LargeInteger num;
        LargeInteger dem = new LargeInteger("1");
        if(str.contains("/")){
            LargeFraction temp = getLargeFraction(str);
            numerator = temp.numerator;
            denominator = temp.denominator;
            return;
        }else if(str.contains(".")){
            if(str.contains("..")) throw new IllegalArgumentException("Error: Invalid input");
            if(str.contains("(") || str.contains(")")){
                if(!str.contains(")")) str += ")";
                if(!str.contains("(")) str = str.replace(".", ".(");
                if(str.indexOf("(") != str.lastIndexOf("(") || str.indexOf(")") != str.lastIndexOf(")")) throw new IllegalArgumentException("Error: Invalid input");
                String nonRepeat = str.substring(str.indexOf(".")+1, str.indexOf("("));
                String repeat = str.substring(str.indexOf("(")+1, str.indexOf(")"));
                String whole = str.substring(0, str.indexOf("."));
                dem = (LargeInteger) LargeInteger.pow10(repeat.length()).subtract(LargeInteger.one);
                dem = (LargeInteger) dem.multiply(LargeInteger.pow10(nonRepeat.length()));
                if(nonRepeat.isEmpty()) nonRepeat += 0;
                if(whole.isEmpty() || whole.equals("-")) whole += 0;
                if(whole.startsWith("-")) nonRepeat = "-" + nonRepeat;
                num = (LargeInteger) new LargeInteger(nonRepeat+repeat).subtract(new LargeInteger(nonRepeat));
                num = (LargeInteger) num.add(new LargeInteger(whole).multiply(dem));
            }else if(str.contains(")") && !str.contains("(")) throw new IllegalArgumentException("Error: Invalid Input");
            else{
                String nonRepeat = str.substring(str.indexOf(".")+1);
                String whole = str.substring(0, str.indexOf("."));
                for(int i = 0; i < nonRepeat.length(); i++) dem = dem.multiply(10L);
                if(nonRepeat.isEmpty()) nonRepeat += 0;
                if(whole.isEmpty() || whole.equals("-")) whole += 0;
                if(whole.startsWith("-")) num = new LargeInteger("-" + nonRepeat);
                else num = new LargeInteger(nonRepeat);
                num = (LargeInteger) num.add(new LargeInteger(whole).multiply(dem));
            }
        }else{
            num = new LargeInteger(str);
            numerator = num;
            denominator = dem;
            return;
        }
        if(dem.sign() == -1L){
            num = (LargeInteger) num.negate();
            dem = (LargeInteger) dem.negate();
        }
        LargeInteger g = num.gcd(dem);
        num = num.LongDivision(g);
        dem = dem.LongDivision(g);
        if(dem.sign() == 0) throw new ArithmeticException("Error: Cannot divide by 0");
        numerator = num;
        denominator = dem;
    }
    private static LargeFraction getLargeFraction(String str) {
        if(str.contains("//"))
            throw new IllegalArgumentException("Error: Invalid input");
        String string1 = str.substring(0, str.lastIndexOf("/"));
        String string2 = str.substring(str.lastIndexOf("/")+1);
        if(string1.isEmpty() || string2.isEmpty()) throw new IllegalArgumentException("Error: Invalid input");
        LargeFraction fixIt = new LargeFraction(string1);
        LargeFraction fixIt2 = new LargeFraction(string2);
        return (LargeFraction) fixIt.divide(fixIt2);
    }
    public LargeFraction(LargeInteger num, LargeInteger dem){
        if(dem.sign() == -1L){
            num = (LargeInteger) num.negate();
            dem = (LargeInteger) dem.negate();
        }
        LargeInteger g = num.gcd(dem);
        numerator = num.LongDivision(g);
        denominator = dem.LongDivision(g);
        if(denominator.sign() == 0L) throw new ArithmeticException("Error: Cannot divide by 0");
    }
    public LargeFraction(ArrayList<Long> digits){
        numerator = new LargeInteger(digits);
        denominator = LargeInteger.one;
    }
    public LargeFraction(long num){
        numerator = new LargeInteger(num);
        denominator = LargeInteger.one;
    }
    @Override
    public String toString(){
        if(denominator.equalsTo(LargeInteger.one)) return numerator.toString();
        return numerator + "/" + denominator;
    }

    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof ScalarAtom)) return false;
        return this.compareTo((ScalarAtom) other) == 0L;
    }

    @Override
    public boolean notEqualsTo(Atom other) {return !this.equalsTo(other);}
    @Override
    public LargeFraction magnitude(){return new LargeFraction(numerator.magnitude(), denominator);}
    @Override
    public long compareMags(ScalarAtom other) {
        if(other instanceof LargeInteger) return this.magnitude().subtract(other.magnitude()).sign();
        else if(other instanceof LargeFraction){
            LargeInteger n = this.numerator;
            LargeInteger n2 = ((LargeFraction) other).numerator;
            LargeInteger multiplier = this.denominator.lcm(((LargeFraction) other).denominator);
            n = (LargeInteger) n.multiply(multiplier.LongDivision(this.denominator));
            n2 = (LargeInteger) n2.multiply(multiplier.LongDivision(((LargeFraction) other).denominator));
            return n.compareMags(n2);
        }
        return this.magnitude().subtract(other.magnitude()).sign();
    }
    public long compareTo(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction) return this.subtract(other).sign();
        else if(other instanceof LargeExponent) return -1 * other.compareTo(this);
        else if(other instanceof Root) return new Root((LargeFraction) this.subtract(((Root) other).rationalPart), (LargeFraction) ((Root) other).coefficient.negate(), ((Root) other).radical, ((Root) other).power).sign();
        return 0;
    }
    public boolean greaterThan(ScalarAtom other) {return this.compareTo(other) > 0L;}
    public boolean lessThan(ScalarAtom other) {return this.compareTo(other) < 0L;}
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return this.compareTo(other) >= 0L;}
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return this.compareTo(other) <= 0L;}
    @Override
    public ScalarAtom negate() {return new LargeFraction((LargeInteger) numerator.negate(), denominator);}
    @Override
    public long sign(){
        if(numerator.sign() >= 0L){
            if(numerator.sign() == 0L) return 0L;
            return 1L;
        }
        return -1L;
    }
    @Override
    public LargeInteger floor(){
        LargeInteger result = numerator.LongDivision(denominator);
        if(this.sign() < 0L && numerator.mod(denominator).sign() != 0L) result = (LargeInteger) result.subtract(LargeInteger.one);
        return result;
    }
    public LargeInteger intPart(){return numerator.LongDivision(denominator);}
    public LargeFraction fracPart(){return new LargeFraction((LargeInteger)numerator.mod(denominator), denominator);}
    @Override
    public LargeInteger ceil(){
        LargeInteger result = numerator.LongDivision(denominator);
        if(this.sign() > 0L && numerator.mod(denominator).sign() != 0L) result = (LargeInteger) result.add(LargeInteger.one);
        return result;
    }
    public static LargeFraction random(){
        Random RNG = new Random();
        StringBuilder str = new StringBuilder();
        //whole part
        long range = RNG.nextLong(50L)+1;
        boolean sign = RNG.nextBoolean();
        if(sign) str.append("-");
        for(long i = 0; i < range; i++) str.append(RNG.nextLong(10L));
        boolean point = RNG.nextBoolean();
        if(point){
            str.append(".");
            //nonrepeating part
            long range2 = RNG.nextLong(50L)+1;
            for(long i = 0; i < range2; i++) str.append(RNG.nextLong(10L));
            boolean repeat = RNG.nextBoolean();
            if(repeat){
                //repeating part
                str.append("(");
                long range3 = RNG.nextLong(20L)+1;
                for(long i = 0; i < range3; i++)str.append(RNG.nextLong(10L));
                str.append(")");
            }
        }
        return new LargeFraction(str.toString());
    }
    public static LargeFraction smallRandom() {
        int num = (int)(Math.random() * 11) - 5;
        int den = (int)(Math.random() * 9) + 1;
        return new LargeFraction(num + "/" + den);
    }
    public ArrayList<PrimeExponent> factor(){
        ArrayList<PrimeExponent> n = numerator.factor();
        ArrayList<PrimeExponent> d = denominator.factor();
        for(PrimeExponent p: d) n.add(new PrimeExponent(p.base, (LargeFraction) p.exponent.multiply(new LargeFraction(-1))));
        while(n.size() > 1 && n.getFirst().base.equalsTo(LargeInteger.one)) n.removeFirst();
        while(n.size() > 1 && n.getLast().base.equalsTo(LargeInteger.one)) n.removeLast();
        //sort the result
        for(int i = 0; i < n.size()-1; i++){
            for(int j = i+1; j < n.size(); j++){
                if(n.get(i).base.greaterThan(n.get(j).base)){
                    PrimeExponent temp = n.get(i);
                    n.set(i, n.get(j));
                    n.set(j, temp);}}}
        if(n.getFirst().base.equalsTo(LargeInteger.one)) n.getFirst().exponent = new LargeFraction(1);
        return n;
    }
    public static LargeFraction unfactor(ArrayList<PrimeExponent> pr){
        LargeFraction result = new LargeFraction(1);
        for(PrimeExponent p: pr){
            if(p.exponent.denominator.notEqualsTo(LargeInteger.one)) throw new IllegalArgumentException("Error: Not a rational number");
            LargeInteger expTemp = p.exponent.numerator.magnitude();
            LargeInteger basTemp = p.base.magnitude().multiply(p.base.sign());
            while(expTemp.sign() != 0){
                if(expTemp.mod2().equalsTo(LargeInteger.one)){
                    if(p.exponent.sign() == -1L) result = (LargeFraction) result.divide(basTemp);
                    else result = (LargeFraction) result.multiply(basTemp);
                }
                basTemp = (LargeInteger) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
        }
        return result;
    }
    @Override
    public ScalarAtom add(ScalarAtom other) {
        if(other instanceof LargeInteger) return this.add(new LargeFraction((LargeInteger) other, LargeInteger.one));
        else if(other instanceof LargeFraction){
            LargeInteger den = this.denominator.lcm(((LargeFraction) other).denominator);
            LargeInteger num = (LargeInteger) den.LongDivision(this.denominator).multiply(this.numerator).add(den.LongDivision(((LargeFraction) other).denominator).multiply(((LargeFraction) other).numerator));
            return new LargeFraction(num, den);
        }else if(other instanceof LargeExponent) return new Root(this, new LargeFraction(1), (LargeExponent) other);
        else if(other instanceof Root) return new Root((LargeFraction) this.add(((Root) other).rationalPart), ((Root) other).coefficient, ((Root) other).radical, ((Root) other).power);
        else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            for(Root r: ((RootSum) other).sum) rt.add(new Root(r.rationalPart, r.coefficient, r.radical, r.power));
            rt.add(new Root(this, new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom subtract(ScalarAtom other) {
        if(other instanceof LargeInteger) return this.subtract(new LargeFraction((LargeInteger) other, LargeInteger.one));
        else if(other instanceof LargeFraction){
            //a/b - c/d = (ad-bc)/(bd)
            LargeInteger den = (LargeInteger) this.denominator.multiply(((LargeFraction) other).denominator);
            LargeInteger num = (LargeInteger) this.numerator.multiply(((LargeFraction) other).denominator).subtract(this.denominator.multiply(((LargeFraction) other).numerator));
            return new LargeFraction(num, den);
        }else if(other instanceof LargeExponent) return new Root(this, new LargeFraction(-1), (LargeExponent) other);
        else if(other instanceof Root) return new Root((LargeFraction) this.subtract(((Root) other).rationalPart), (LargeFraction) ((Root) other).coefficient.negate(), ((Root) other).radical, ((Root) other).power);
        else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            for(Root r: ((RootSum) other).sum) rt.add(new Root((LargeFraction) r.rationalPart.negate(), (LargeFraction) r.coefficient.negate(), r.radical, r.power));
            rt.add(new Root(this, new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    public LargeFraction subtract(ArrayList<LargeFraction> other){
        LargeFraction result = new LargeFraction(this.numerator, this.denominator);
        for (LargeFraction fr : other) result = (LargeFraction) result.subtract(fr);
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom multiply(ScalarAtom other) {
        if(other instanceof LargeInteger) return new LargeFraction((LargeInteger) numerator.multiply(other), denominator);
        else if(other instanceof LargeFraction){
            LargeInteger g1 = this.numerator.gcd(((LargeFraction) other).denominator);
            LargeInteger g2 = this.denominator.gcd(((LargeFraction) other).numerator);
            LargeInteger a2 = this.numerator.LongDivision(g1);
            LargeInteger d2 = ((LargeFraction) other).denominator.LongDivision(g1);
            LargeInteger c2 = ((LargeFraction) other).numerator.LongDivision(g2);
            LargeInteger b2 = this.denominator.LongDivision(g2);
            return a2.multiply(c2).divide(b2.multiply(d2));
        }else if(other instanceof Root) return new Root((LargeFraction) ((Root) other).rationalPart.multiply(this), (LargeFraction) ((Root) other).coefficient.multiply(this), ((Root) other).radical, ((Root) other).power);
        else if(other instanceof RootSum) return new RootSum(((RootSum)other).sum).multiply(this);
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    public LargeFraction multiply(long other){return (LargeFraction) this.multiply(new LargeFraction(other));}
    public LargeFraction multiply(ArrayList<LargeFraction> other){
        LargeFraction result = new LargeFraction(this.numerator, this.denominator);
        for (LargeFraction fr : other) result = (LargeFraction) result.multiply(fr);
        return result;
    }
    @Override
    public ScalarAtom square(){return this.multiply(this);}
    @Override
    public ScalarAtom cube(){return this.multiply(this).multiply(this);}
    @Override
    public ScalarAtom divide(ScalarAtom other) {
        if(other instanceof LargeInteger) return this.divide(new LargeFraction((LargeInteger) other, LargeInteger.one));
        else if(other instanceof LargeFraction) return this.multiply(other.reciprocal());
        else if(other instanceof LargeExponent) return new LargeExponent(this, new LargeFraction(1)).divide(other);
        else if(other instanceof Root || other instanceof RootSum) return other.reciprocal().multiply(this);
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    public LargeFraction divide(long other){return (LargeFraction) this.divide(new LargeFraction(other));}
    @Override
    public LargeFraction reciprocal() {return new LargeFraction(this.denominator, this.numerator);}
    @Override
    public ScalarAtom mod(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger){
            if(other.sign() == 0L) throw new ArithmeticException("Error: Cannot divide by 0");
            LargeFraction result = (LargeFraction) this.subtract(this.divide(other).floor().multiply(other));
            if(result.sign() != other.sign() && result.sign() != 0L) result = (LargeFraction) result.add(other.magnitude());
            return result;
        }
        return this.subtract(this.divide(other).floor().multiply(other));
    }
    public LargeFraction gcd(LargeFraction other){
        LargeFraction copy = new LargeFraction(this.numerator, this.denominator);
        LargeFraction copy2 = new LargeFraction(other.numerator, other.denominator);
        while(copy2.sign() != 0){
            LargeFraction temp = new LargeFraction(copy.numerator, copy.denominator);
            copy = new LargeFraction(copy2.numerator, copy2.denominator);
            copy2 = (LargeFraction) temp.mod(copy2);
        }
        return copy;
    }
    public static LargeFraction mean(ArrayList<LargeFraction> l){
        if(l.isEmpty()) throw new IllegalArgumentException("Error: Cannot calculate mean of empty list");
        LargeFraction result = new LargeFraction(0);
        for(LargeFraction f: l) result = (LargeFraction) result.add(f);
        return (LargeFraction) result.divide(new LargeFraction(l.size()));
    }
    public static void sort(ArrayList<LargeFraction> l){
        for(int i = 0; i < l.size()-1; i++){
            for(int j = i+1; j < l.size(); j++){
                if(l.get(i).greaterThan(l.get(j))){
                    LargeFraction temp = l.get(i);
                    l.set(i, l.get(j));
                    l.set(j, temp);
                }
            }
        }
    }
    public static LargeFraction median(ArrayList<LargeFraction> l){
        sort(l);
        if(l.size() % 2 == 1) return l.get(l.size()/2);
        else return (LargeFraction) l.get(l.size()/2-1).add(l.get(l.size()/2)).divide(LargeInteger.two);}
    public static LargeFraction mad(ArrayList<LargeFraction> l){
        ArrayList<LargeFraction> copy = new ArrayList<>();
        LargeFraction m = median(l);
        for(LargeFraction f: l) copy.add((LargeFraction) f.subtract(m).magnitude());
        return median(copy);}
    public static LargeFraction min(ArrayList<LargeFraction> l){
        if(l.isEmpty()) throw new IllegalArgumentException("Error: Empty list");
        sort(l);
        return l.getFirst();
    }
    public static LargeFraction max(ArrayList<LargeFraction> l){
        if(l.isEmpty()) throw new IllegalArgumentException("Error: Empty list");
        sort(l);
        return l.getLast();
    }
    public static LargeFraction mode(ArrayList<LargeFraction> l){
        ArrayList<LargeFraction> copy = new ArrayList<>(l);
        sort(copy);
        int frequency = 1;
        ArrayList<Integer> frequencies = new ArrayList<>();
        for(int i = 1; i < copy.size(); i++){
            if(copy.get(i).equalsTo(copy.get(i-1))){
                frequency++;
                copy.remove(i);
                i--;
            }else{
                frequencies.add(frequency);
                frequency = 1;
            }
        }
        //get max index
        int index = 0;
        for(int i = 0; i < frequencies.size(); i++) if(frequencies.get(i) >= frequencies.get(index)) index = i;
        return copy.get(index);
    }
    public static LargeFraction range(ArrayList<LargeFraction> l){return (LargeFraction) max(l).subtract(min(l));}
    public static LargeExponent stdev(ArrayList<LargeFraction> l){
        if(l.size() < 2) throw new IllegalArgumentException("Error: Need at least 2 values in list");
        LargeFraction m = mean(l);
        LargeFraction result = new LargeFraction(0);
        for(LargeFraction f: l) result = (LargeFraction) result.add(((LargeFraction)f.subtract(m)).square());
        result = result.divide(l.size() - 1);
        return new LargeExponent(result, new LargeFraction("1/2"));
    }
    public static LargeExponent stdevp(ArrayList<LargeFraction> l){
        if(l.isEmpty()) throw new IllegalArgumentException("Error: Empty list");
        LargeFraction m = mean(l);
        LargeFraction result = new LargeFraction(0);
        for(LargeFraction f: l) result = (LargeFraction) result.add(f.subtract(m).square());
        result = result.divide(l.size());
        return new LargeExponent(result, new LargeFraction("1/2"));
    }
    public static LargeFraction IQR(ArrayList<LargeFraction> l){
        if(l.size() < 2) throw new IllegalArgumentException("Error: Need at least 2 values in list");
        sort(l);
        ArrayList<LargeFraction> copy = new ArrayList<>();
        for(int i = 0; i < l.size()/2; i++) copy.add(l.get(i));
        ArrayList<LargeFraction> copy2 = new ArrayList<>();
        if(l.size() % 2 == 0) for(int i = l.size()/2; i < l.size(); i++) copy2.add(l.get(i));
        else for(int i = l.size()/2+1; i < l.size(); i++) copy2.add(l.get(i));
        return (LargeFraction) median(copy2).subtract(median(copy));
    }
    public static boolean allEqual(ArrayList<LargeFraction> arr){
        long count = 0L;
        for(LargeFraction f: arr) if(f.equalsTo(arr.getFirst())) count++;
        return count == arr.size();
    }
    @Override
    public ScalarAtom percent(){return this.divide(new LargeFraction(100));}
    @Override
    public ScalarAtom permille(){return this.divide(new LargeFraction(1000));}
    public LargeExponent power(LargeFraction exp) {return new LargeExponent(this, exp);}
    public LargeFraction power(LargeInteger exp){
        LargeFraction n = numerator.power(exp);
        LargeFraction d = denominator.power(exp);
        return (LargeFraction) n.divide(d);
    }
}
class PrimeExponent extends ScalarAtom{
    LargeInteger base;
    LargeFraction exponent;
    public PrimeExponent(LargeInteger b, LargeFraction e){
        base = b.magnitude().multiply(b.sign());
        if(base.notEqualsTo(LargeInteger.one)) exponent = e;
        else exponent = (LargeFraction) LargeInteger.one.divide(LargeInteger.one);
    }
    public String toString(){
        if(exponent.equalsTo(LargeInteger.one)) return base.toString();
        return base + "^(" + exponent + ")";}

    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof ScalarAtom)) return false;
        if(other instanceof PrimeExponent) return this.base.equalsTo(((PrimeExponent) other).base) && this.exponent.equalsTo(((PrimeExponent) other).exponent);
        return this.compareTo((ScalarAtom) other) == 0L;
    }
    @Override
    public boolean notEqualsTo(Atom other) {return !this.equalsTo(other);}
    public static void sortBases(ArrayList<PrimeExponent> l) {
        for (int i = 0; i < l.size() - 1; i++) {
            for (int j = i + 1; j < l.size(); j++) {
                if (l.get(i).base.greaterThan(l.get(j).base)) {
                    PrimeExponent temp = l.get(i);
                    l.set(i, l.get(j));
                    l.set(j, temp);
                }
            }
        }
    }
    @Override
    public PrimeExponent reciprocal(){return new PrimeExponent(base, (LargeFraction) exponent.negate());}
    @Override
    public ScalarAtom mod(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom square(){return new PrimeExponent(base, (LargeFraction) exponent.multiply(LargeInteger.two));}
    @Override
    public ScalarAtom cube() {return new PrimeExponent(base, (LargeFraction) exponent.multiply(LargeInteger.three));}
    @Override
    public ScalarAtom percent() {return null;}
    @Override
    public ScalarAtom permille() {return null;}
    @Override
    public LargeInteger floor() {return null;}
    @Override
    public LargeInteger ceil() {return null;}
    @Override
    public long sign() {return new LargeExponent((LargeFraction) base.divide(LargeInteger.one), exponent).sign();}
    @Override
    public ScalarAtom magnitude() {return new PrimeExponent(base.magnitude(), exponent);}
    @Override
    public long compareMags(ScalarAtom other) {return 0;}
    @Override
    public long compareTo(ScalarAtom other) {return 0;}
    @Override
    public boolean greaterThan(ScalarAtom other) {return false;}
    @Override
    public boolean lessThan(ScalarAtom other) {return false;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public ScalarAtom negate() {return null;}
    @Override
    public ScalarAtom add(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom subtract(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom multiply(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom divide(ScalarAtom other) {return null;}
}class LargeExponent extends ScalarAtom{
    LargeFraction base;
    LargeFraction exponent;
    LargeExponent(LargeFraction num, LargeFraction dem){
        if(num.sign()!=0 && dem.sign()==0){
            base = new LargeFraction(1);
            exponent = new LargeFraction(1);
            return;
        }else if(num.equalsTo(new LargeFraction(0)) || num.equalsTo(new LargeFraction(1))){
            if(num.equalsTo(new LargeFraction(0)) && dem.lessThan(new LargeFraction(0))) throw new ArithmeticException("Error: Cannot divide by 0");
            else if(num.equalsTo(new LargeFraction(0)) && dem.equalsTo(new LargeFraction(0))) throw new ArithmeticException("Indeterminate form: 0^0");
            base = new LargeFraction(num.numerator, num.denominator);
            exponent = new LargeFraction(1);
            return;
        }else if(num.magnitude().equalsTo(LargeInteger.one)){
            base = new LargeFraction(num.numerator, num.denominator);
            if(num.equalsTo(LargeInteger.one)){
                exponent = new LargeFraction(1);
                return;
            }
            exponent = new LargeFraction(dem.numerator, dem.denominator);
            if(exponent.numerator.isEven() && exponent.denominator.equalsTo(LargeInteger.one)) base = new LargeFraction(1);
            if(exponent.denominator.equalsTo(LargeInteger.one)) exponent = new LargeFraction(1);
            return;
        }else if(dem.magnitude().equalsTo(LargeInteger.one)){
            if(dem.sign() == -1) base = num.reciprocal();
            else base = new LargeFraction(num.numerator, num.denominator);
            exponent = new LargeFraction(1);
            return;
        }
        base = new LargeFraction(num.numerator, num.denominator);
        exponent = new LargeFraction(dem.numerator, dem.denominator);
        ArrayList<PrimeExponent> pr = base.factor();
        if(pr.getFirst().base.equalsTo(new LargeInteger(-1)) && exponent.denominator.mod2().equalsTo(LargeInteger.zero)){
            if(exponent.sign() == -1L){
                base = base.reciprocal();
                exponent = (LargeFraction) exponent.multiply(new LargeFraction(-1));
            }
            return;
        }
        for(int i = 0; i < pr.size(); i++){
            PrimeExponent p = pr.get(i);
            p.exponent = (LargeFraction) p.exponent.multiply(exponent);
            if(p.base.equalsTo(LargeInteger.negativeOne)){
                if(p.exponent.denominator.isOdd()){
                    if(p.exponent.numerator.isOdd()) p.exponent = new LargeFraction(1);
                    else{
                        pr.remove(i);
                        i--;
                    }
                }else
                    if(p.exponent.numerator.isEven())
                        p.exponent = new LargeFraction(1);
            }
        }
        ArrayList<LargeInteger> list = new ArrayList<>();
        for(PrimeExponent p: pr) list.add(p.exponent.denominator);
        LargeInteger lc = list.getFirst().lcm(list);
        if(lc.equalsTo(LargeInteger.one)){
            base = LargeFraction.unfactor(pr);
            exponent = new LargeFraction(1);
            return;
        }else if(pr.size() == 1){
            if(pr.getFirst().exponent.sign() == -1L){
                base = (LargeFraction) LargeInteger.one.divide(pr.getFirst().base);
                exponent = pr.getFirst().exponent.magnitude();
            }else{
                base = (LargeFraction) pr.getFirst().base.divide(LargeInteger.one);
                exponent = pr.getFirst().exponent;
            }
            return;
        }
        //fix the damn thing
        for(PrimeExponent p: pr){
            //make the exponents all have common denominator
            LargeInteger multiplier = lc.LongDivision(p.exponent.denominator);
            p.exponent = (LargeFraction) p.exponent.divide(multiplier);
            LargeInteger basTemp = p.base.magnitude().multiply(p.base.sign());
            p.base = LargeInteger.one;
            while(multiplier.greaterThan(LargeInteger.zero)){
                if(multiplier.mod2().equalsTo(LargeInteger.one)) p.base = (LargeInteger) p.base.multiply(basTemp);
                basTemp = (LargeInteger) basTemp.square();
                multiplier = multiplier.LongDivision(LargeInteger.two);
            }
        }
        //extract gcd of numerators
        ArrayList<LargeInteger> list2 = new ArrayList<>();
        for(PrimeExponent p: pr) list2.add(p.exponent.numerator);
        LargeInteger gc = list2.getFirst().gcd(list2);
        base = new LargeFraction(1);
        for(PrimeExponent p: pr){
            LargeInteger expTemp = p.exponent.numerator.LongDivision(gc).magnitude();
            LargeInteger basTemp = p.base.magnitude().multiply(p.base.sign());
            while(expTemp.greaterThan(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one)){
                    if(p.exponent.sign() == -1L) base = (LargeFraction) base.divide(basTemp);
                    else base = (LargeFraction) base.multiply(basTemp);
                }
                basTemp = (LargeInteger) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
        }
        exponent = new LargeFraction(gc, lc);
    }
    LargeExponent(String str){
        if(str.contains("^")){
            String s1 = str.substring(0, str.indexOf("^"));
            String s2 = str.substring(str.indexOf("^") + 1);
            LargeExponent obj = new LargeExponent(new LargeFraction(s1), new LargeFraction(s2));
            base = obj.base;
            exponent = obj.exponent;
        }else{
            LargeExponent obj = new LargeExponent(new LargeFraction(str), new LargeFraction(1));
            base = obj.base;
            exponent = obj.exponent;
        }
    }
    LargeExponent(Long l){
        base = new LargeFraction(l);
        exponent = new LargeFraction(1);}
    @Override
    public String toString(){
        String str = "";
        if(!exponent.equalsTo(LargeInteger.one)){
            str += "(";
            str += base.toString();
            str += ")";
        }else str += base.toString();
        if(!exponent.toString().equals("1")){
            str += "^";
            if(exponent.denominator.notEqualsTo(LargeInteger.one)){
                str += "(";
                str += exponent.toString();
                str += ")";
            }else str += exponent.numerator;
        }
        return str;
    }
    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof ScalarAtom)) return false;
        return this.compareTo((ScalarAtom) other) == 0L;
    }
    @Override
    public boolean notEqualsTo(Atom other) {return !equalsTo(other);}
    public long sign(){
        if(base.sign() == -1){
            if(!this.isReal()) throw new IllegalArgumentException("Error: Sign is not real");
            if(exponent.numerator.isOdd()) return -1L;
            return 1L;
        }else if(base.sign() == 1L) return 1L;
        return 0L;
    }
    @Override
    public LargeExponent magnitude(){return new LargeExponent(base.magnitude(), exponent);}
    @Override
    public long compareMags(ScalarAtom other) {return this.magnitude().compareTo(other.magnitude());}
    @Override
    public long compareTo(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger){
            if((this.base.sign() == -1L && this.exponent.denominator.mod2().equalsTo(LargeInteger.zero)))
                throw new IllegalArgumentException("Error: Complex numbers cannot be compared");
            LargeExponent copy = new LargeExponent(base, exponent);
            LargeExponent copy2 = new LargeExponent((LargeFraction) other.divide(LargeInteger.one), new LargeFraction(1));
            LargeFraction pow = copy.exponent.denominator.power(LargeInteger.one);
            copy = copy.power(pow);
            copy2 = copy2.power(pow);
            return copy.base.compareTo(copy2.base);
        }
        if(other instanceof LargeExponent){
            if((this.base.sign() == -1L && this.exponent.denominator.mod2().equalsTo(LargeInteger.zero)) || (((LargeExponent) other).base.sign() == -1L && ((LargeExponent) other).exponent.denominator.mod2().equalsTo(LargeInteger.zero))) throw new IllegalArgumentException("Error: Complex numbers cannot be compared");
            LargeExponent copy = new LargeExponent(this.base, this.exponent);
            LargeExponent copy2 = new LargeExponent(((LargeExponent) other).base, ((LargeExponent) other).exponent);
            LargeInteger lc = this.exponent.denominator.lcm(((LargeExponent) other).exponent.denominator);
            copy.exponent = (LargeFraction) copy.exponent.multiply(lc);
            copy2.exponent = (LargeFraction) copy2.exponent.multiply(lc);
            return new LargeExponent(copy.base, copy.exponent).base.compareTo(new LargeExponent(copy2.base, copy2.exponent).base);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public boolean greaterThan(ScalarAtom other){return this.compareTo(other) == 1L;}
    @Override
    public boolean lessThan(ScalarAtom l){return this.compareTo(l) == -1L;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom l){return this.compareTo(l) >= 0L;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return this.compareTo(other) <= 0L;}
    @Override
    public ScalarAtom negate() {return this.multiply(new LargeExponent(new LargeFraction(-1), new LargeFraction(1)));}
    public boolean isRational(){return exponent.equalsTo(new LargeFraction(1));}
    public boolean isReal(){return base.sign() != -1 || !exponent.denominator.isEven();}
    @Override
    public LargeInteger floor(){
        if(!this.isReal())
            throw new IllegalArgumentException("Error: Complex numbers do not have floor or ceil");
        else if(this.isRational())
            return base.floor();
        boolean negativeResult = base.sign() == -1L
                && exponent.numerator.mod2().equalsTo(LargeInteger.one);
        LargeFraction begun = base.numerator.magnitude().power(exponent.numerator);
        LargeFraction begon = base.denominator.magnitude().power(exponent.numerator);
        LargeInteger d = exponent.denominator;
        LargeInteger low;
        LargeInteger high;
        if(LargeInteger.one.power(d).multiply(begon).lesserThanOrEqualTo(begun)){
            low = LargeInteger.one;
            high = LargeInteger.two;
            while(high.power(d).multiply(begon).lesserThanOrEqualTo(begun)){
                low = high;
                high = (LargeInteger) high.multiply(LargeInteger.two);
            }
            while(high.subtract(low).greaterThan(LargeInteger.one)){
                LargeInteger mid = ((LargeInteger) low.add(high)).LongDivision(LargeInteger.two);
                if(mid.power(d).multiply(begon).lesserThanOrEqualTo(begun))
                    low = mid;
                else
                    high = mid;
            }
        }
        else low = LargeInteger.zero;
        if(!negativeResult) return low;
        return (LargeInteger) low.add(LargeInteger.one).negate();
    }
    @Override
    public LargeInteger ceil(){
        if(this.isRational()) return base.ceil();
        return (LargeInteger) this.floor().add(LargeInteger.one);
    }
    @Override
    public ScalarAtom add(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction) return new Root((LargeFraction) other.divide(LargeInteger.one), new LargeFraction(1), this);
        else if(other instanceof LargeExponent){
            RootSum r = new RootSum();
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), (LargeExponent) other));
            return new RootSum(r.sum);
        }else if(other instanceof Root){
            RootSum r = new RootSum();
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            r.sum.add((Root) other);
            return new RootSum(r.sum);
        }else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            rt.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            for(Root r: ((RootSum) other).sum) rt.sum.add(new Root(r.rationalPart, r.coefficient, r.radical, r.power));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom subtract(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction) return new Root((LargeFraction) other.multiply(new LargeFraction(-1)), new LargeFraction(1), this);
        else if(other instanceof LargeExponent){
            RootSum r = new RootSum();
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(-1), (LargeExponent) other));
            return new RootSum(r.sum);
        }else if(other instanceof Root){
            RootSum r = new RootSum();
            r.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            r.sum.add((Root) other.multiply(new LargeFraction(-1)));
            return new RootSum(r.sum);
        }else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            rt.sum.add(new Root(new LargeFraction(0), new LargeFraction(1), this));
            for(Root r: ((RootSum) other).sum)
                rt.sum.add(new Root((LargeFraction) r.rationalPart.negate(), (LargeFraction) r.coefficient.negate(), r.radical, r.power));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }

    @Override
    public ScalarAtom multiply(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger){
            return this.multiply(new LargeExponent((LargeFraction) other.divide(LargeInteger.one), new LargeFraction(1)));
        }else if(other instanceof LargeExponent){
            LargeExponent copy = new LargeExponent(this.base, this.exponent);
            LargeExponent copy2 = new LargeExponent(((LargeExponent) other).base, ((LargeExponent) other).exponent);
            //make exponents integers
            LargeInteger lc = copy.exponent.denominator.lcm(copy2.exponent.denominator);
            copy.exponent = (LargeFraction) copy.exponent.multiply(lc);
            copy2.exponent = (LargeFraction) copy2.exponent.multiply(lc);
            //multiply
            LargeFraction result = new LargeFraction(1);
            LargeInteger expTemp = copy.exponent.numerator.magnitude();
            LargeFraction basTemp = copy.base.magnitude().multiply(copy.base.sign());
            while(expTemp.greaterThan(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one)) result = (LargeFraction) result.multiply(basTemp);
                basTemp = (LargeFraction) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
            expTemp = copy2.exponent.numerator.magnitude();
            basTemp = copy2.base.magnitude().multiply(copy2.base.sign());
            while(expTemp.greaterThan(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one)) result = (LargeFraction) result.multiply(basTemp);
                basTemp = (LargeFraction) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
            return new LargeExponent(result, (LargeFraction) LargeInteger.one.divide(lc));
        }else if(other instanceof Root){
            RootSum rt = new RootSum();
            rt.add(new Root(new LargeFraction(0), ((Root) other).rationalPart, this));
            rt.add(new Root(new LargeFraction(0), ((Root) other).coefficient, (LargeExponent) new LargeExponent(((Root) other).radical, ((Root) other).power).multiply(this)));
            return new RootSum(rt.sum);
        }else if(other instanceof RootSum){
            RootSum rt = new RootSum();
            for(Root r: ((RootSum) other).sum) rt.add(this.multiply(new Root(r.rationalPart, r.coefficient, r.radical, r.power)));
            return new RootSum(rt.sum);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom square(){return new LargeExponent(this.base, (LargeFraction) this.exponent.multiply(LargeInteger.two));}
    @Override
    public ScalarAtom cube(){return new LargeExponent(this.base, (LargeFraction) this.exponent.multiply(LargeInteger.three));}
    @Override
    public ScalarAtom percent() {return this.multiply(new LargeFraction("0.01"));}
    @Override
    public ScalarAtom permille() {return this.multiply(new LargeFraction("0.001"));}
    public LargeExponent power(LargeFraction f){return new LargeExponent(base, (LargeFraction) exponent.multiply(f));}
    public ArrayList<PrimeExponent> factor(){
        ArrayList<PrimeExponent> pr = base.factor();
        for(PrimeExponent p: pr) if(p.base.notEqualsTo(LargeInteger.zero) && p.base.notEqualsTo(LargeInteger.one)) p.exponent = (LargeFraction) p.exponent.multiply(exponent);
        return pr;
    }
    public static LargeExponent unfactor(ArrayList<PrimeExponent> pr){
        LargeExponent l = new LargeExponent(new LargeFraction(1), new LargeFraction(1));
        for(PrimeExponent p: pr) l = (LargeExponent) l.multiply(new LargeExponent((LargeFraction) p.base.divide(LargeInteger.one), p.exponent));
        return l;
    }
    @Override
    public ScalarAtom divide(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger) return new Root(new LargeFraction(0), (LargeFraction) other.reciprocal(), this);
        else if(other instanceof LargeExponent){
            LargeExponent copy = new LargeExponent(this.base, this.exponent);
            LargeExponent copy2 = new LargeExponent(((LargeExponent) other).base, ((LargeExponent) other).exponent);
            //make exponents integers
            LargeInteger lc = copy.exponent.denominator.lcm(copy2.exponent.denominator);
            copy.exponent = (LargeFraction) copy.exponent.multiply(lc);
            copy2.exponent = (LargeFraction) copy2.exponent.multiply(lc);
            //multiply
            LargeFraction result = new LargeFraction(1);
            LargeInteger expTemp = copy.exponent.numerator.magnitude();
            LargeFraction basTemp = copy.base.magnitude().multiply(copy.base.sign());
            while(expTemp.greaterThan(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one)) result = (LargeFraction) result.multiply(basTemp);
                basTemp = (LargeFraction) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
            expTemp = copy2.exponent.numerator.magnitude();
            basTemp = copy2.base.magnitude().multiply(copy2.base.sign());
            while(expTemp.greaterThan(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one)) result = (LargeFraction) result.divide(basTemp);
                basTemp = (LargeFraction) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
            return new LargeExponent(result, (LargeFraction) LargeInteger.one.divide(lc));
        }
        return other.reciprocal().multiply(this);
    }
    @Override
    public LargeExponent reciprocal(){return new LargeExponent(base.reciprocal(), exponent);}
    @Override
    public ScalarAtom mod(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction || other instanceof LargeExponent) return this.subtract(other.multiply(this.divide(other).floor()));
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
}
class Root extends ScalarAtom{
    public LargeFraction rationalPart;
    public LargeFraction coefficient;
    public LargeFraction radical;
    public LargeFraction power;
    public Root(LargeFraction f, LargeFraction c, LargeFraction r, LargeFraction pow){
        rationalPart = new LargeFraction(f.numerator, f.denominator);
        coefficient = new LargeFraction(c.numerator, c.denominator);
        radical = new LargeFraction(1);
        power = new LargeFraction(pow.numerator, pow.denominator);
        if(power.denominator.isEven() && radical.lessThan(LargeInteger.zero)) return;
        else if(radical.sign() == 0 || coefficient.sign() == 0){
            radical = new LargeFraction(0);
            power = new LargeFraction(1);
            coefficient = new LargeFraction(0);
            return;
        }
        LargeExponent E = new LargeExponent(r, pow);
        ArrayList<PrimeExponent> pr = E.factor();
        for(int i = 0; i < pr.size(); i++){
            PrimeExponent p = pr.get(i);
            LargeInteger expTemp = p.exponent.intPart();
            p.exponent = (LargeFraction) p.exponent.subtract(expTemp);
            LargeInteger basTemp = p.base.magnitude().multiply(p.base.sign());
            while(expTemp.notEqualsTo(LargeInteger.zero)){
                if(expTemp.mod2().equalsTo(LargeInteger.one))
                    if(expTemp.sign() == -1L) coefficient = (LargeFraction) coefficient.divide(basTemp);
                    else coefficient = (LargeFraction) coefficient.multiply(basTemp);
                basTemp = (LargeInteger) basTemp.square();
                expTemp = expTemp.LongDivision(LargeInteger.two);
            }
            if(p.exponent.equalsTo(LargeInteger.zero)){
                pr.remove(i);
                i--;
            }
        }
        E = LargeExponent.unfactor(pr);
        radical = E.base;
        power = E.exponent;
        if(radical.equalsTo(LargeInteger.one)){
            rationalPart = (LargeFraction) rationalPart.add(coefficient);
            coefficient = new LargeFraction(0);
            radical = new LargeFraction(0);
            power = new LargeFraction(1);
        }else if(radical.sign() == -1 && coefficient.sign() == -1 && power.denominator.isOdd()){
            coefficient = coefficient.multiply(-1);
            radical = radical.multiply(-1);
        }
    }
    public Root(LargeFraction r, LargeFraction c, LargeExponent E){
        Root x = new Root(r, c, E.base, E.exponent);
        rationalPart = x.rationalPart;
        coefficient = x.coefficient;
        radical = x.radical;
        power = x.power;
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        if(rationalPart.notEqualsTo(LargeInteger.zero))
            str.append(rationalPart.toString());
        if(coefficient.sign() == 0){
            if(str.isEmpty()) str.append(rationalPart.toString());
            return str.toString();
        }else if(coefficient.sign() == -1){
            if(rationalPart.sign() != 0) str.append(" - ");
            else str.append("-");
            if(coefficient.notEqualsTo(LargeInteger.negativeOne)) str.append(coefficient.magnitude()).append("*");
        }else{
            if(rationalPart.sign() != 0) str.append(" + ");
            if(coefficient.notEqualsTo(LargeInteger.one)) str.append(coefficient.magnitude()).append("*");
        }
        str.append("(").append(radical).append(")^(").append(power).append(")");
        return str.toString();
    }
    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof ScalarAtom)) return false;
        else if(other instanceof LargeInteger || other instanceof LargeFraction) return this.rationalPart.equalsTo(other) && this.coefficient.equalsTo(new LargeFraction(0));
        else if(other instanceof LargeExponent){
            Root newOther = new Root(new LargeFraction(0), new LargeFraction(1), (LargeExponent) other);
            return this.rationalPart.equalsTo(newOther.rationalPart) && this.coefficient.equalsTo(newOther.coefficient) && this.radical.equalsTo(newOther.radical) && this.power.equalsTo(newOther.power);
        }else if(other instanceof Root) return this.rationalPart.equalsTo(((Root) other).rationalPart) && this.coefficient.equalsTo(((Root) other).coefficient) && this.radical.equalsTo(((Root)other).radical) && this.power.equalsTo(((Root)other).power);
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public boolean notEqualsTo(Atom other) {return !this.equalsTo(other);}
    @Override
    public LargeInteger floor() {
        if(coefficient.sign() == 0L) return rationalPart.floor();
        LargeInteger f1 = new LargeExponent(radical, power).multiply(coefficient).floor();
        LargeInteger f2 = rationalPart.floor();
        //do exponent comparison
        LargeFraction threshold = (LargeFraction) new LargeFraction(1).subtract(rationalPart.fracPart());
        long s = new LargeExponent(radical, power).multiply(coefficient).compareTo(threshold.add(f1));
        if(s == -1) s=0;
        return (LargeInteger) f1.add(f2).add(new LargeInteger(s));
    }
    @Override
    public LargeInteger ceil() {
        if(radical.sign() == 0) return rationalPart.ceil();
        return (LargeInteger) this.floor().add(LargeInteger.one);
    }
    public long sign(){
        //coefficient is zero
        if(coefficient.sign() == 0L) return rationalPart.sign();
        LargeExponent E = new LargeExponent(radical, power);
        long rSign = rationalPart.sign();
        long coeffSign = coefficient.sign();
        long termSign = E.sign()*coeffSign;
        if(termSign == 0L) return rSign;
        if(termSign == rSign) return rSign;
        LargeExponent left = new LargeExponent((LargeFraction) rationalPart.magnitude().divide(coefficient.magnitude()), new LargeFraction(1));
        LargeExponent right = new LargeExponent(radical, power).magnitude();
        long cmp = left.compareTo(right);
        if(cmp > 0L) return rSign;
        return termSign;
    }
    public boolean isInteger(){return coefficient.sign() == 0 && rationalPart.denominator.equalsTo(LargeInteger.one);}
    @Override
    public Root magnitude(){
        if(new LargeExponent(radical, power).isReal()){
            if(this.sign() == -1L) return new Root((LargeFraction) rationalPart.negate(), (LargeFraction) coefficient.negate(), radical, power);
            return this;
        }
        throw new IllegalArgumentException("Error: Still haven't implemented modulus for complex numbers");
    }
    @Override
    public long compareMags(ScalarAtom other) {return this.magnitude().compareTo(other.magnitude());}
    @Override
    public long compareTo(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction) return new Root((LargeFraction) rationalPart.subtract(other), coefficient, radical, power).sign();
        else if(other instanceof LargeExponent){
            LargeFraction a1 = (LargeFraction) this.floor().divide(LargeInteger.one);
            LargeFraction b1 = (LargeFraction) this.ceil().divide(LargeInteger.one);
            LargeFraction a2 = (LargeFraction) other.floor().divide(LargeInteger.one);
            LargeFraction b2 = (LargeFraction) other.ceil().divide(LargeInteger.one);
            if(this.equalsTo(other)) return 0;
            while(true) {
                if (b1.lesserThanOrEqualTo(a2)) return -1;
                else if (b2.lesserThanOrEqualTo(a1)) return 1;
                LargeFraction mediant = new LargeFraction((LargeInteger) a1.numerator.add(b1.numerator), (LargeInteger) a1.denominator.add(b1.denominator));
                if (mediant.lessThan(this)) a1 = mediant;
                else b1 = mediant;
                LargeFraction mediant2 = new LargeFraction((LargeInteger) a2.numerator.add(b2.numerator), (LargeInteger) a2.denominator.add(b2.denominator));
                if (mediant2.lessThan(other)) a2 = mediant2;
                else b2 = mediant2;
            }
        }else if(other instanceof Root) return new Root((LargeFraction) this.add(((Root) other).rationalPart), coefficient, radical, power).compareTo(new LargeExponent(((Root) other).radical, ((Root) other).power).multiply(((Root) other).coefficient));
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public boolean greaterThan(ScalarAtom other) {return this.compareTo(other) > 0;}
    @Override
    public boolean lessThan(ScalarAtom other) {return this.compareTo(other) < 0;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return this.compareTo(other) >= 0;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other){return this.compareTo(other) <= 0;}
    @Override
    public ScalarAtom negate(){return new Root((LargeFraction) rationalPart.negate(), (LargeFraction) coefficient.negate(), radical, power);}
    @Override
    public ScalarAtom add(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger){
            Root r = new Root(this.rationalPart, this.coefficient, this.radical, this.power);
            r.rationalPart = (LargeFraction) r.rationalPart.add(other);
            return r;
        }else if(other instanceof LargeExponent){
            ArrayList<Root> r = new ArrayList<>();
            r.add(this);
            r.add(new Root(new LargeFraction(0), new LargeFraction(1), ((LargeExponent) other).base, ((LargeExponent) other).exponent));
            return new RootSum(r);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }

    @Override
    public ScalarAtom subtract(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger){
            Root r = new Root(this.rationalPart, this.coefficient, this.radical, this.power);
            r.rationalPart = (LargeFraction) r.rationalPart.subtract(other);
            return r;
        }else if(other instanceof LargeExponent || other instanceof Root){
            ArrayList<Root> r = new ArrayList<>();
            r.add(this);
            r.add((Root) other.multiply(new LargeFraction(-1)));
            return new RootSum(r);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom multiply(ScalarAtom f) {
        if(f instanceof LargeFraction || f instanceof LargeInteger) return new Root((LargeFraction) this.rationalPart.multiply(f), (LargeFraction) this.coefficient.multiply(f), this.radical, this.power);
        else if(f instanceof LargeExponent) {
            ArrayList<Root> rt = new ArrayList<>();
            rt.add(new Root(new LargeFraction(0), this.rationalPart, ((LargeExponent) f).base, ((LargeExponent) f).exponent));
            rt.add(new Root(new LargeFraction(0), this.coefficient, (LargeExponent) new LargeExponent(this.radical, power).multiply(f)));
            return new RootSum(rt);
        }else if(f instanceof Root){
            ArrayList<Root> r = new ArrayList<>();
            r.add(new Root((LargeFraction) this.rationalPart.multiply(((Root) f).rationalPart), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            if(this.coefficient.equalsTo(LargeInteger.zero) && ((Root) f).coefficient.equalsTo(LargeInteger.zero)) return new RootSum(r);
            r.add(new Root(this.rationalPart, (LargeFraction) ((Root) f).coefficient.multiply(this.rationalPart), ((Root)f).radical, ((Root) f).power));
            r.add(new Root(this.rationalPart, (LargeFraction) ((Root) f).rationalPart.multiply(this.coefficient), this.radical, this.power));
            LargeExponent l = (LargeExponent) new LargeExponent(this.radical, this.power).multiply(new LargeExponent(((Root)f).radical, ((Root) f).power));
            r.add(new Root(new LargeFraction(0), (LargeFraction) this.coefficient.multiply(((Root) f).coefficient), l.base, l.exponent));
            return new RootSum(r);
        }else if(f instanceof RootSum){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root r: ((RootSum) f).sum)
                for(Root k: ((RootSum)r.multiply(this)).sum)
                    rt.add(new Root(k.rationalPart, k.coefficient, k.radical, k.power));
            return new RootSum(rt);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom square(){return this.multiply(this);}
    @Override
    public ScalarAtom cube() {return this.multiply(this).multiply(this);}
    @Override
    public ScalarAtom percent() {return this.multiply(new LargeFraction("1/100"));}
    @Override
    public ScalarAtom permille() {return this.multiply(new LargeFraction("1/1000"));}
    @Override
    public RootSum reciprocal(){
        if(radical.equalsTo(LargeInteger.zero)){
            RootSum end = new RootSum();
            end.sum.add(new Root(rationalPart.reciprocal(), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(end.sum);
        }else if(rationalPart.equalsTo(LargeInteger.zero)){
            RootSum end = new RootSum();
            end.sum.add(new Root(new LargeFraction(0), coefficient.reciprocal(), radical.reciprocal(), power));
            return new RootSum(end.sum);
        }
        //generate basis
        ArrayList<LargeExponent> basis = new ArrayList<>();
        for(LargeInteger i = LargeInteger.zero; i.lessThan(power.denominator); i= (LargeInteger) i.add(LargeInteger.one)) basis.add(new LargeExponent(radical, (LargeFraction) i.divide(power.denominator)));
        //determine equations
        ArrayList<RootSum> whyMe = new ArrayList<>();
        for(LargeExponent E: basis) whyMe.add((RootSum) this.multiply(E));
        //create matrix
        LargeMatrix M = new LargeMatrix();
        for(RootSum rt: whyMe){
            LargeVector v = new LargeVector(0);
            for(int i = 0; i < basis.size(); i++){
                if(i >= rt.sum.size()) rt.sum.add(new Root(new LargeFraction(0), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
                if(rt.sum.get(i).radical.equalsTo(basis.get(i).base) && rt.sum.get(i).power.equalsTo(basis.get(i).exponent)) v.append(new LargeFraction(rt.sum.get(i).coefficient.numerator, rt.sum.get(i).coefficient.denominator));
                else if(rt.sum.get(i).rationalPart.notEqualsTo(new LargeFraction(0))) v.append(new LargeFraction(rt.sum.get(i).rationalPart.numerator, rt.sum.get(i).rationalPart.denominator));
                else{
                    rt.sum.add(i, new Root(new LargeFraction(0), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
                    v.append(new LargeFraction(0));
                }
            }
            M.addRow(v);
        }
        LargeVector v = new LargeVector(0);
        v.append(new LargeFraction(1));
        while(v.dimension() < M.cols()) v.append(new LargeFraction(0));
        M.addRow(v);
        M = M.transpose().rref();
        ArrayList<Root> result = new ArrayList<>();
        for(int i = 0; i < basis.size(); i++) result.add(new Root(new LargeFraction(0), M.get(i, (int) (M.cols()-1)), basis.get(i)));
        return new RootSum(result);
    }
    @Override
    public ScalarAtom divide(ScalarAtom other) {
        if(other instanceof LargeFraction || other instanceof LargeInteger) return new Root((LargeFraction)this.rationalPart.divide(other), (LargeFraction)coefficient.divide(other), this.radical, this.power);
        else if(other instanceof LargeExponent){
            ArrayList<Root> rt = new ArrayList<>();
            rt.add(new Root(new LargeFraction(0), this.rationalPart, ((LargeExponent) other).base.reciprocal(), ((LargeExponent) other).exponent));
            rt.add(new Root(new LargeFraction(0), this.coefficient, (LargeExponent) new LargeExponent(this.radical, power).divide(other)));
            return new RootSum(rt);
        }
        else if(other instanceof Root || other instanceof RootSum) return other.reciprocal().multiply(this);
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom mod(ScalarAtom other) {return this.subtract(this.divide(other).floor().multiply(other));}
}
class RootSum extends ScalarAtom{
    ArrayList<Root> sum;
    public RootSum(){sum = new ArrayList<>();}
    public RootSum(ArrayList<Root> r){
        sum = new ArrayList<>();
        if(r.isEmpty()) return;
        for(Root rt: r) sum.add(new Root(rt.rationalPart, rt.coefficient, rt.radical, rt.power));
        //combine like radicals
        for(int i = 0; i < sum.size()-1; i++){
            for(int j = i+1; j < sum.size(); j++){
                if(sum.get(i).radical.equalsTo(sum.get(j).radical) && sum.get(i).power.equalsTo(sum.get(j).power) && sum.get(i).coefficient.notEqualsTo(new LargeFraction(0))){
                    sum.get(i).coefficient = (LargeFraction) sum.get(i).coefficient.add(sum.get(j).coefficient);
                    sum.remove(j);
                    j--;
                }
            }
        }
        //sort radicands
        for(int i = 0; i < sum.size()-1; i++){
            for(int j = i+1; j < sum.size(); j++){
                if(sum.get(j).power.lessThan(sum.get(i).power)){
                    Root temp = new Root(sum.get(i).rationalPart, sum.get(i).coefficient, sum.get(i).radical, sum.get(i).power);
                    Root temped = new Root(sum.get(j).rationalPart, sum.get(j).coefficient, sum.get(j).radical, sum.get(j).power);
                    sum.set(i, temped);
                    sum.set(j, temp);
                }else if(sum.get(j).power.equalsTo(sum.get(i).power)){
                    if(sum.get(j).radical.lessThan(sum.get(i).radical)){
                        Root temp = new Root(sum.get(i).rationalPart, sum.get(i).coefficient, sum.get(i).radical, sum.get(i).power);
                        Root temped = new Root(sum.get(j).rationalPart, sum.get(j).coefficient, sum.get(j).radical, sum.get(j).power);
                        sum.set(i, temped);
                        sum.set(j, temp);
                    }
                }
            }
        }
        //set rational part to front
        sum.addFirst(new Root(new LargeFraction(0), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
        for(int i = 1; i < sum.size(); i++){
            sum.getFirst().rationalPart = (LargeFraction) sum.getFirst().rationalPart.add(sum.get(i).rationalPart);
            sum.get(i).rationalPart = new LargeFraction(0);
        }
        //eliminate zeros
        for(int i = 0; i < sum.size(); i++){
            if(sum.get(i).sign() == 0L){
                sum.remove(i);
                i--;
            }
        }
    }
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        if(sum.isEmpty() || sum.getFirst().sign() == 0L){
            str.append("0");
            return str.toString();
        }
        for(int i = 0; i < sum.size(); i++){
            if(i != 0){
                if(sum.get(i).sign() == -1L) str.append(" - ");
                else str.append(" + ");
                str.append(sum.get(i).magnitude());
            }else str.append(sum.getFirst());
        }
        return str.toString();
    }

    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof ScalarAtom)) return false;
        else if(other instanceof LargeFraction || other instanceof LargeInteger) return sum.size() == 1 && sum.getFirst().equalsTo(other);
        else if(other instanceof LargeExponent) return sum.size() == 1 && sum.getFirst().power.equalsTo(((LargeExponent) other).exponent) && sum.getFirst().radical.equalsTo(((LargeExponent) other).base);
        else if(other instanceof Root) return sum.size() == 1 && sum.getFirst().equalsTo(other);
        else if(other instanceof RootSum){
            if(((RootSum) other).sum.size() != this.sum.size()) return false;
            for(int i = 0; i < this.sum.size(); i++)
                if(((RootSum) other).sum.get(i).notEqualsTo(this.sum.get(i))) return false;
            return true;
        }
        return false;
    }
    @Override
    public boolean notEqualsTo(Atom other) {return !equalsTo(other);}
    public boolean isInteger(){return sum.size() == 1 && sum.getFirst().isInteger();}
    @Override
    public LargeInteger floor() {
        ArrayList<LargeFraction> floors = new ArrayList<>();
        ArrayList<LargeFraction> ceils = new ArrayList<>();
        for(Root r: this.sum){
            LargeFraction F = (LargeFraction) r.floor().divide(LargeInteger.one);
            floors.add(F);
            if(r.isInteger())
                F = (LargeFraction) F.add(LargeInteger.one);
            ceils.add(F);
        }
        LargeInteger result;
        while(true){
            LargeFraction floorSum = new LargeFraction(0);
            LargeFraction ceilSum = new LargeFraction(0);
            //store a list
            ArrayList<LargeFraction> list = new ArrayList<>();
            for(int i = 0; i < floors.size(); i++)
                list.add((LargeFraction) ceils.get(i).subtract(floors.get(i)));
            LargeFraction diff = LargeFraction.max(list);
            for(int i = 0; i < floors.size(); i++){
                LargeFraction mediant = new LargeFraction((LargeInteger) floors.get(i).numerator.add(ceils.get(i).numerator), (LargeInteger) floors.get(i).denominator.add(ceils.get(i).denominator));
                if(mediant.compareTo(sum.get(i)) == -1) floors.set(i, mediant);
                else ceils.set(i, mediant);
                floorSum = (LargeFraction) floorSum.add(floors.get(i));
                ceilSum = (LargeFraction) ceilSum.add(ceils.get(i));
            }
            if(ceilSum.ceil().subtract(LargeInteger.one).equalsTo(floorSum.floor()) || ceilSum.equalsTo(floorSum)){
                result = floorSum.floor();
                break;
            }
        }
        return result;
    }
    @Override
    public LargeInteger ceil() {
        LargeInteger I = this.floor();
        if(this.isInteger())
            I = (LargeInteger) I.add(LargeInteger.one);
        return I;
    }
    @Override
    public long sign() {return 0;}
    @Override
    public ScalarAtom magnitude() {return null;}
    @Override
    public long compareMags(ScalarAtom other) {return 0;}
    @Override
    public long compareTo(ScalarAtom other) {return 0;}
    @Override
    public boolean greaterThan(ScalarAtom other) {return false;}
    @Override
    public boolean lessThan(ScalarAtom other) {return false;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public ScalarAtom negate() {return null;}
    @Override
    public ScalarAtom add(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction) {
            ArrayList<Root> rt = new ArrayList<>();
            for (Root l : sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add(new Root((LargeFraction) other.divide(LargeInteger.one), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(rt);
        }else if(other instanceof LargeExponent){
            ArrayList<Root> rt = new ArrayList<>();
            for (Root l : sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add(new Root(new LargeFraction(0), new LargeFraction(1), (LargeExponent) other));
            return new RootSum(rt);
        }else if(other instanceof Root){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root l: sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add((Root) other);
            return new RootSum(rt);
        }else if(other instanceof RootSum){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root l: sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            for(Root l: ((RootSum) other).sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            return new RootSum(rt);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom subtract(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root l: sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add(new Root((LargeFraction) other.divide(LargeInteger.negativeOne), new LargeFraction(0), new LargeFraction(0), new LargeFraction(1)));
            return new RootSum(rt);
        }else if(other instanceof LargeExponent){
            ArrayList<Root> rt = new ArrayList<>();
            for (Root l : sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add(new Root(new LargeFraction(0), new LargeFraction(-1), (LargeExponent) other));
            return new RootSum(rt);
        }else if(other instanceof Root){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root l: sum) rt.add(new Root(l.rationalPart, l.coefficient, l.radical, l.power));
            rt.add((Root) other.multiply(new LargeFraction(-1)));
            return new RootSum(rt);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom multiply(ScalarAtom other) {
        if(other instanceof LargeInteger || other instanceof LargeFraction){
            RootSum q = new RootSum(this.sum);
            q.sum.replaceAll(root -> (Root) root.multiply(other));
            return q;
        }else if(other instanceof Root){
            ArrayList<Root> rt = new ArrayList<>();
            for(Root r: this.sum){
                RootSum rf = (RootSum) r.multiply(other);
                for(Root s: rf.sum) rt.add(new Root(s.rationalPart, s.coefficient, s.radical, s.power));
            }
            return new RootSum(rt);
        }
        throw new IllegalArgumentException("Error: Operation with these types together not supported yet");
    }
    @Override
    public ScalarAtom square() {return null;}
    @Override
    public ScalarAtom cube() {return null;}
    @Override
    public RootSum reciprocal(){return null;}
    @Override
    public ScalarAtom divide(ScalarAtom other) {return other.reciprocal().multiply(this);}
    @Override
    public ScalarAtom mod(ScalarAtom other) {return this.subtract(this.divide(other).floor().multiply(other));}
    @Override
    public ScalarAtom percent() {return this.multiply(new LargeFraction("0.01"));}
    @Override
    public ScalarAtom permille() {return this.multiply(new LargeFraction("0.001"));}
}
class Logarithm extends ScalarAtom {
    public LargeFraction arg;
    Logarithm(LargeFraction a) {arg = a;}
    @Override
    public LargeInteger floor() {return null;}
    @Override
    public LargeInteger ceil() {return null;}
    @Override
    public long sign() {return 0;}
    @Override
    public ScalarAtom magnitude() {return null;}
    @Override
    public long compareMags(ScalarAtom other) {return 0;}
    @Override
    public long compareTo(ScalarAtom other) {return 0;}
    @Override
    public boolean greaterThan(ScalarAtom other) {return false;}
    @Override
    public boolean lessThan(ScalarAtom other) {return false;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public ScalarAtom negate() {return new Logarithm(arg.reciprocal());}
    @Override
    public ScalarAtom add(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom subtract(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom multiply(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom divide(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom reciprocal() {return null;}
    @Override
    public ScalarAtom mod(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom square() {return null;}
    @Override
    public ScalarAtom cube() {return null;}
    @Override
    public ScalarAtom percent() {return null;}
    @Override
    public ScalarAtom permille() {return null;}
    @Override
    public String toString() {return "";}
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
}
class LogB extends ScalarAtom{
    Logarithm numerator;
    Logarithm denominator;
    LogB(LargeFraction b, LargeFraction a) {
        numerator = new Logarithm(a);
        denominator = new Logarithm(b);
    }
    @Override
    public LargeInteger floor() {return null;}
    @Override
    public LargeInteger ceil(){return null;}
    @Override
    public long sign() {return 0;}
    @Override
    public ScalarAtom magnitude() {return null;}
    @Override
    public long compareMags(ScalarAtom other) {return 0;}
    @Override
    public long compareTo(ScalarAtom other) {return 0;}
    @Override
    public boolean greaterThan(ScalarAtom other) {return false;}
    @Override
    public boolean lessThan(ScalarAtom other) {return false;}
    @Override
    public boolean greaterThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public boolean lesserThanOrEqualTo(ScalarAtom other) {return false;}
    @Override
    public ScalarAtom negate() {return null;}
    @Override
    public ScalarAtom add(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom subtract(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom multiply(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom divide(ScalarAtom other) {return null;}
    @Override
    public LogB reciprocal() {return null;}
    @Override
    public ScalarAtom mod(ScalarAtom other) {return null;}
    @Override
    public ScalarAtom square() {return null;}
    @Override
    public ScalarAtom cube() {return null;}
    @Override
    public ScalarAtom percent() {return null;}
    @Override
    public ScalarAtom permille() {return null;}
    @Override
    public String toString() {return "";}
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
}
class LargeVector extends TensorAtom {
    private final ArrayList<LargeFraction> list;
    public LargeVector(int dimension) {
        if (dimension < 0) throw new IllegalArgumentException("Error: Dimension cannot be negative");
        list = new ArrayList<>();
        for (int i = 0; i < dimension; i++) list.add(new LargeFraction(0));
    }
    public LargeVector(LargeFraction f) {
        list = new ArrayList<>();
        list.add(f);
    }
    public LargeVector(ArrayList<LargeFraction> l) {
        list = new ArrayList<>();
        for (LargeFraction f : l) list.add(new LargeFraction(f.numerator, f.denominator));
    }
    public ArrayList<LargeFraction> daList() {
        ArrayList<LargeFraction> r = new ArrayList<>();
        for(LargeFraction f: list) r.add(new LargeFraction(f.numerator, f.denominator));
        return r;
    }
    public int dimension() {return list.size();}
    public LargeFraction get(int i) {return list.get(i);}
    public void set(int i, LargeFraction f) {this.list.set(i, f);}
    public void append(LargeFraction f) {list.add(f);}
    public void remove(int i){list.remove(i);}
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (this.list.isEmpty()) return "()";
        str.append("(").append(list.getFirst());
        for (int i = 1; i < list.size(); i++) {
            str.append(", ");
            str.append(list.get(i));
        }
        str.append(")");
        return str.toString();
    }
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
    public LargeVector add(LargeVector v) {
        if (v.list.size() != this.list.size()) throw new IllegalArgumentException("Error: Dimension mismatch");
        LargeVector result = new LargeVector(this.list);
        for (int i = 0; i < v.list.size(); i++) result.list.set(i, (LargeFraction) result.list.get(i).add(v.list.get(i)));
        return result;
    }
    public LargeVector subtract(LargeVector v) {
        if (v.list.size() != this.list.size()) throw new IllegalArgumentException("Error: Dimension mismatch");
        LargeVector result = new LargeVector(this.list);
        for (int i = 0; i < v.list.size(); i++) result.list.set(i, (LargeFraction) result.list.get(i).subtract(v.list.get(i)));
        return result;
    }
    public LargeVector divide(LargeFraction f){return (LargeVector) this.scale(new LargeFraction(f.denominator, f.numerator));}
    public LargeFraction dotProduct(LargeVector v) {
        if (v.list.size() != this.list.size()) throw new IllegalArgumentException("Error: Dimension mismatch");
        LargeFraction result = new LargeFraction(0);
        for (int i = 0; i < v.list.size(); i++) result = (LargeFraction) result.add(this.list.get(i).multiply(v.list.get(i)));
        return result;
    }
    public boolean isPerpendicularTo(LargeVector v) {return this.dotProduct(v).equalsTo(new LargeFraction(0));}
    public LargeExponent magnitude() {
        LargeFraction result = new LargeFraction(0);
        for (LargeFraction f : list) result = (LargeFraction) result.add(f.square());
        return new LargeExponent(result, new LargeFraction("1/2"));
    }
    public LargeVector map(Function<LargeFraction, LargeFraction> f) {
        ArrayList<LargeFraction> result = new ArrayList<>();
        for (LargeFraction x : list) result.add(f.apply(x));
        return new LargeVector(result);
    }
    @Override
    public int[] shape() {
        return new int[0];
    }
    @Override
    public TensorAtom add(TensorAtom other) {return null;}
    @Override
    public TensorAtom multiply(TensorAtom other) {
        if(other instanceof LargeVector){
            if (((LargeVector)other).list.size() != this.list.size()) throw new IllegalArgumentException("Error: Dimension mismatch");
            LargeVector result = new LargeVector(this.list);
            for (int i = 0; i < ((LargeVector)other).list.size(); i++) result.list.set(i, (LargeFraction) result.list.get(i).multiply(((LargeVector)other).list.get(i)));
            return result;
        }
        return null;
    }

    @Override
    public TensorAtom scale(ScalarAtom scalar) {
        if(scalar instanceof LargeFraction || scalar instanceof LargeInteger){
            LargeVector result = new LargeVector(this.list);
            result.list.replaceAll(LargeFraction -> (LargeFraction) LargeFraction.multiply(scalar));
            return result;
        }
        return null;
    }
}

class LargeMatrix extends TensorAtom {
    private final ArrayList<LargeVector> matrix;
    public LargeMatrix() {matrix = new ArrayList<>();}
    public LargeMatrix(ArrayList<LargeVector> l) {
        matrix = new ArrayList<>();
        //check if all vectors have equal size
        int count = 0;
        for (int i = 0; i < l.size(); i++){
            if (l.get(i).daList().size() == l.getFirst().daList().size()) count++;
            matrix.add(new LargeVector(l.get(i).daList()));
        }
        if (count != l.size()) throw new IllegalArgumentException("Error: Rows must have same sizes");
    }
    public LargeMatrix(int rows, int cols) {
        matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) matrix.add(new LargeVector(cols));
    }
    public void addRow(LargeVector row) {
        if (!matrix.isEmpty() && row.dimension() != matrix.getFirst().dimension()) throw new IllegalArgumentException("Error: Rows must have same sizes");
        matrix.add(row);
    }
    public void removeCol(int i){
        if(i >= this.cols() || i < 0) throw new IndexOutOfBoundsException("Error: index " + i + " out of bounds for range 0," + this.cols());
        for(LargeVector v: matrix) v.remove(v.dimension()-1);
    }
    public long rows() {return matrix.size();}
    public long cols() {return matrix.getFirst().dimension();}
    private String padRight(String s, int width) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < width) sb.append(' ');
        return sb.toString();
    }
    public LargeFraction get(int i, int j) {return matrix.get(i).daList().get(j);}
    public void set(int i, int j, LargeFraction f){matrix.get(i).set(j, f);}
    @Override
    public String toString() {
        if (matrix.isEmpty()) return "[]";
        StringBuilder str = new StringBuilder();
        int rows = matrix.size();
        int cols = matrix.getFirst().dimension();
        int[] colWidths = new int[cols];
        for (int j = 0; j < cols; j++) {
            int max = 0;
            for (int i = 0; i < rows; i++) {
                String s = matrix.get(i).get(j).toString();
                if (s.length() > max) max = s.length();
            }
            colWidths[j] = max;
        }
        for (int i = 0; i < matrix.size(); i++) {
            if (i == 0 && i != matrix.size() - 1) str.append("⌈");
            else if (i > 0 && i < matrix.size() - 1) str.append("|");
            else if (matrix.size() - 1 == 0) str.append("[");
            else str.append("⌊");
            for (int j = 0; j < matrix.get(i).dimension(); j++) {
                String s = matrix.get(i).get(j).toString();
                s = padRight(s, colWidths[j] + 1);
                str.append(s);
            }
            if (i == 0 && i != matrix.size() - 1) str.append("⌉\n");
            else if (i > 0 && i < matrix.size() - 1) str.append("|\n");
            else if (matrix.size() - 1 == 0) str.append("]");
            else str.append("⌋");
        }
        return str.toString();
    }
    public static LargeMatrix identity(int count){
        LargeMatrix M = new LargeMatrix();
        for(int i = 0; i < count; i++){
            LargeVector v = new LargeVector(0);
            for(int j = 0; j < count; j++){
                if(i == j)
                    v.append(new LargeFraction(1));
                else v.append(new LargeFraction(0));
            }
            M.addRow(v);
        }
        return M;
    }
    @Override
    public boolean equalsTo(Atom other) {
        if(!(other instanceof TensorAtom))
            return false;
        else if(other instanceof LargeMatrix){
            if(this.rows() != ((LargeMatrix) other).rows() || this.cols() != ((LargeMatrix) other).cols())
                return false;
            for(int i = 0; i < this.rows(); i++){
                for(int j = 0; j < this.cols(); j++)
                    if(this.get(i, j).notEqualsTo(((LargeMatrix) other).get(i, j)))
                        return false;
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean notEqualsTo(Atom other) {return !this.equalsTo(other);}
    public LargeMatrix transpose() {
        LargeMatrix result = new LargeMatrix();
        for (int i = 0; i < this.cols(); i++) {
            ArrayList<LargeFraction> ver = new ArrayList<>();
            for (int j = 0; j < this.rows(); j++) ver.add(this.matrix.get(j).get(i));
            result.addRow(new LargeVector(ver));
        }
        return result;
    }
    public LargeMatrix add(LargeMatrix m) {
        if (this.rows() != m.rows() || this.cols() != m.cols()) throw new IllegalArgumentException("Error: Dimension mismatch");
        ArrayList<LargeVector> result = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) result.add(this.matrix.get(i).add(m.matrix.get(i)));
        return new LargeMatrix(result);
    }
    public LargeMatrix subtract(LargeMatrix m) {
        if (this.rows() != m.rows() || this.cols() != m.cols()) throw new IllegalArgumentException("Error: Dimension mismatch");
        ArrayList<LargeVector> result = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) result.add(this.matrix.get(i).subtract(m.matrix.get(i)));
        return new LargeMatrix(result);
    }
    public LargeMatrix multiply(LargeFraction f) {
        LargeMatrix m = new LargeMatrix(this.matrix);
        for (int i = 0; i < this.rows(); i++) for (int j = 0; j < this.cols(); j++) m.matrix.get(i).set(j, (LargeFraction) m.matrix.get(i).get(j).multiply(f));
        return m;
    }

    public LargeVector multiply(LargeVector v) {
        if (v.dimension() != this.cols()) throw new IllegalArgumentException("Error: Dimension mismatch");
        LargeVector result = new LargeVector(0);
        for (int i = 0; i < this.rows(); i++) result.append(this.matrix.get(i).dotProduct(v));
        return result;
    }
    public LargeMatrix multiply(LargeMatrix m) {
        if (this.cols() != m.rows()) throw new IllegalArgumentException("Error: Dimension mismatch");
        LargeMatrix mT = m.transpose();
        ArrayList<LargeVector> result = new ArrayList<>();
        for (LargeVector rowA : this.matrix) {
            ArrayList<LargeFraction> newRow = new ArrayList<>();
            for (LargeVector colB : mT.matrix) newRow.add(rowA.dotProduct(colB));
            result.add(new LargeVector(newRow));
        }
        return new LargeMatrix(result);
    }
    public LargeFraction determinant(){
        if(this.rows() != this.cols()) throw new IllegalArgumentException("Error: Matrix must be a square");
        LargeMatrix M = new LargeMatrix(this.matrix);
        ArrayList<LargeInteger> scaleList = new ArrayList<>();
        for(LargeVector v: M.matrix) for(LargeFraction f: v.daList()) scaleList.add(f.denominator);
        LargeInteger scale = scaleList.getFirst().lcm(scaleList);
        M = M.multiply((LargeFraction) scale.divide(LargeInteger.one));
        //Bareiss elimination
        LargeFraction prev = new LargeFraction(1);
        for(int k = 0; k < M.rows()-1; k++){
            LargeFraction pivot = M.get(k, k);
            for(int i = 0; i < M.rows(); i++) for(int j = 0; j < M.rows(); j++) M.set(i, j, (LargeFraction) M.get(i, j).multiply(pivot).subtract(M.get(i, k).multiply(M.get(k, j))).divide(prev));
            prev = pivot;
        }
        return M.get((int) (M.rows()-1), (int) (M.cols()-1)).magnitude();
    }
    public LargeMatrix inverse(){
        if(this.rows() != this.cols()) throw new IllegalArgumentException("Error: Matrix must be a square");
        LargeMatrix M = new LargeMatrix(this.matrix);
        long n = M.rows();
        for(int i = 0; i < n; i++){
            LargeVector v = M.matrix.get(i);
            for(int j = 0; j < n; j++){
                if(i == j) v.append(new LargeFraction(1));
                else v.append(new LargeFraction(0));
            }
        }
        M = M.rref();
        LargeMatrix left = new LargeMatrix();
        LargeMatrix right = new LargeMatrix();
        for (LargeVector row : M.matrix) {
            LargeVector leftRow = new LargeVector(0);
            LargeVector rightRow = new LargeVector(0);
            for (int i = 0; i < n; i++) leftRow.append(row.get(i));
            for (long i = n; i < 2 * n; i++) rightRow.append(row.get((int) i));
            left.addRow(leftRow);
            right.addRow(rightRow);
        }
        if (left.notEqualsTo(identity((int) n))) throw new IllegalArgumentException("Error: Singular matrix");
        return right;
    }
    public int rank(){
        LargeMatrix M = this.rref();
        int count = 0;
        for(LargeVector i: M.matrix){
            boolean check = true;
            for(long j = 0; j < i.dimension() && check; j++)
                if(i.get((int) j).sign() != 0)
                    check = false;
            if(!check)
                count++;
        }
        return count;
    }
    public LargeMatrix map(Function<LargeVector, LargeVector> f) {
        ArrayList<LargeVector> result = new ArrayList<>();
        for (LargeVector x : matrix) result.add(f.apply(x));
        return new LargeMatrix(result);
    }
    public LargeMatrix ref(){
        LargeMatrix M = new LargeMatrix(this.matrix);
        long rows = M.rows();
        long cols = M.cols();
        int pivotRow = 0;
        for (int pivotCol = 0; pivotCol < cols && pivotRow < rows; pivotCol++) {
            int best = -1;
            for (int r = pivotRow; r < rows; r++) {
                if (!M.get(r, pivotCol).equalsTo(new LargeFraction(0))) {
                    best = r;
                    break;
                }
            }
            if (best == -1) continue;
            LargeVector temp = M.matrix.get(pivotRow);
            M.matrix.set(pivotRow, M.matrix.get(best));
            M.matrix.set(best, temp);
            LargeFraction pivot = M.get(pivotRow, pivotCol);
            // normalize pivot row
            for (int c = 0; c < cols; c++) M.set(pivotRow, c, (LargeFraction) M.get(pivotRow, c).divide(pivot));
            // clear rows below only
            for (int r = pivotRow + 1; r < rows; r++) {
                LargeFraction factor = M.get(r, pivotCol);
                if (factor.equalsTo(new LargeFraction(0))) continue;
                for (int c = 0; c < cols; c++) M.set(r, c, (LargeFraction) M.get(r, c).subtract(factor.multiply(M.get(pivotRow, c))));
            }
            pivotRow++;
        }
        return M;
    }
    public LargeMatrix rref(){
        LargeMatrix M = new LargeMatrix(this.matrix);
        long rows = M.rows();
        long cols = M.cols();
        int pivotRow = 0;
        for (int pivotCol = 0; pivotCol < cols && pivotRow < rows; pivotCol++) {
            int best = -1;
            for (int r = pivotRow; r < rows; r++) {
                if (!M.get(r, pivotCol).equalsTo(new LargeFraction(0))) {
                    best = r;
                    break;
                }
            }
            if (best == -1) continue;
            LargeVector temp = M.matrix.get(pivotRow);
            M.matrix.set(pivotRow, M.matrix.get(best));
            M.matrix.set(best, temp);
            LargeFraction pivot = M.get(pivotRow, pivotCol);
            // normalize pivot row
            for (int c = 0; c < cols; c++) M.set(pivotRow, c, (LargeFraction) M.get(pivotRow, c).divide(pivot));
            // clear all other rows
            for (int r = 0; r < rows; r++) {
                if (r == pivotRow) continue;
                LargeFraction factor = M.get(r, pivotCol);
                for (int c = 0; c < cols; c++)
                    M.set(r, c, (LargeFraction) M.get(r, c).subtract(factor.multiply(M.get(pivotRow, c))));
            }
            pivotRow++;
        }
        return M;
    }
    @Override
    public int[] shape() {return new int[0];}
    @Override
    public TensorAtom add(TensorAtom other) {return null;}
    @Override
    public TensorAtom multiply(TensorAtom other) {return null;}
    @Override
    public TensorAtom scale(ScalarAtom scalar) {return null;}
}
//behold...the NEURAL NETWORK CLASS
class LinearLayer extends ModelAtom {
    LargeMatrix W;
    LargeVector b;
    public LargeVector forward(LargeVector x) {
        return W.multiply(x).add(b);
    }
    @Override
    public String toString() {
        return "";
    }
    @Override
    public boolean equalsTo(Atom other) {
        return false;
    }
    @Override
    public boolean notEqualsTo(Atom other) {
        return false;
    }
}

class SI extends PhysicalAtom{
    public static final LargeFraction quecto = new LargeFraction("1/1000000000000000000000000000000");
    public static final LargeFraction ronto = new LargeFraction("1/1000000000000000000000000000");
    public static final LargeFraction yocto = new LargeFraction("1/1000000000000000000000000");
    public static final LargeFraction zepto = new LargeFraction("1/1000000000000000000000");
    public static final LargeFraction atto = new LargeFraction("1/1000000000000000000");
    public static final LargeFraction femto = new LargeFraction("1/1000000000000000");
    public static final LargeFraction pico = new LargeFraction("1/1000000000000");
    public static final LargeFraction nano = new LargeFraction("1/1000000000");
    public static final LargeFraction micro = new LargeFraction("1/1000000");
    static final LargeFraction milli = new LargeFraction("1/1000");
    static final LargeFraction centi = new LargeFraction("1/100");
    static final LargeFraction deci = new LargeFraction("1/10");
    static final LargeFraction unit = new LargeFraction("1");
    static final LargeFraction deka = new LargeFraction("10");
    static final LargeFraction hecto = new LargeFraction("100");
    static final LargeFraction kilo = new LargeFraction("1000");
    static final LargeFraction mega = new LargeFraction("1000000");
    static final LargeFraction giga = new LargeFraction("1000000000");
    static final LargeFraction tera = new LargeFraction("1000000000000");
    static final LargeFraction peta = new LargeFraction("1000000000000000");
    static final LargeFraction exa = new LargeFraction("1000000000000000000");
    static final LargeFraction zetta = new LargeFraction("1000000000000000000000");
    static final LargeFraction yotta = new LargeFraction("1000000000000000000000000");
    static final LargeFraction ronna = new LargeFraction("1000000000000000000000000000");
    static final LargeFraction quetta = new LargeFraction("1000000000000000000000000000000");
    @Override
    public String toString() {return "";}
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
}
class Unit extends PhysicalAtom {
    @Override
    public String toString() {return "";}
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
}
class Quantity extends PhysicalAtom {
    @Override
    public String toString() {return "";}
    @Override
    public boolean equalsTo(Atom other) {return false;}
    @Override
    public boolean notEqualsTo(Atom other) {return false;}
}