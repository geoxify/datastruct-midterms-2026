package final_program.quizzer.util;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class InputValidator<T> {
    private final Scanner sc = new Scanner(System.in);

    public T getValidInput(String prompt, Function<String, T> parser, Predicate<T> isValid){
        while(true){
            try {
                System.out.print(prompt + ": ");
                String rawInput = sc.nextLine();
                T parsedValue = parser.apply(rawInput);

                if (isValid.test(parsedValue)) {
                    return parsedValue;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e){
                System.out.println("Invalid format. Please enter the correct data type.");
            }
        }
    }
}