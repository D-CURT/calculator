import services.ReversePolishNotationBuilder;

public class Runner {
    public static void main(String[] args) {
        System.out.println(new ReversePolishNotationBuilder().getRPN(/*"(8^3^(5+2)*5)/(1^3+(3*2-4^6)^2)"*/"8^3^4+2"));
    }
}
