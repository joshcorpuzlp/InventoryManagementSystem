package model;

public class Utility {
    private static String errorMessage = "";


    public static void validIntInput(int input) {
        if (input < 0) {
            errorMessage += "You must enter a positive integer";
            try {
                int inputValidation;
                inputValidation = input;
            } catch (NumberFormatException ex) {
                errorMessage += "You must enter a whole number";
            }
        }
    }

    public static String getErrorMessage() {
        return errorMessage;
    }
}
