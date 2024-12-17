import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidation {
//input validation with regular expressions

  public static int validateInt(Scanner scanner, String prompt, String regex, String errorMessage){
    while (true) { 
       System.out.println(prompt); 
       String input = scanner.nextLine();
       if(Pattern.matches(regex, input)){
        return Integer.parseInt(input);
       }
       System.out.println(errorMessage);
    }
  }  
  public static String validateString(Scanner scanner, String prompt, String regex, String errorMessage){
     while (true) { 
       System.out.println(prompt); 
       String input = scanner.nextLine().trim();
       if(Pattern.matches(regex, input)){
        return input;
       }
       System.out.println(errorMessage);
    }
  }
  public static double validateDouble(Scanner scanner, String prompt, String regex, String errorMessage){
    while (true) { 
       System.out.println(prompt); 
       String input = scanner.nextLine();
       if(Pattern.matches(regex, input)){
        return Double.parseDouble(input);
       }
       System.out.println(errorMessage);
    }
  }
    
}
