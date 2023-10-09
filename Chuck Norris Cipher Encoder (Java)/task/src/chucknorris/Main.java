package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String choice = scanner.nextLine();
            switch (choice) {
                case "encode":
                    System.out.println("Input string:");
                    String inputText = scanner.nextLine();
                    System.out.println("Encoded string:");
                    System.out.println(encodeInputMessage(inputText));
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String inputMessage = scanner.nextLine();
                    System.out.println (isValidEncodedMessage(inputMessage));
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("There is no '" + choice + "' operation");

            }
        }
    }
    public static String isValidEncodedMessage (String inputMessage){
        if (!(isOnlyZeroOrSpaceInCode(inputMessage))) {
            return "Encoded string is not valid.";
        }
        if (!(isNumberOfBlocksEven(inputMessage))) {
            return "Encoded string is not valid.";
        }
        if (!(isLengthOfDecodeBinaryStringMultipleOf7(inputMessage))) {
            return "Encoded string is not valid.";
        }
        if (!(is0Or00InEvenBlocks(inputMessage))) {
            return "Encoded string is not valid.";
        } else {
            return "Decoded string: " + "\n" + decodeInputMessage(inputMessage);
        }
    }
    public static String encodeInputMessage(String text) {
        StringBuilder binaryMessage = new StringBuilder();
        for (char c : text.toCharArray()) {
            String binaryCode = String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryMessage.append(binaryCode);
        }
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < binaryMessage.length()) {
            char currentElementOfBinary = binaryMessage.charAt(i);
            int count = 1;

            while (i + 1 < binaryMessage.length() && binaryMessage.charAt(i + 1) == currentElementOfBinary) {
                count++;
                i++;
            }

            result.append((currentElementOfBinary == '1' ? "0" : "00") + " " + "0".repeat(count) + " ");
            i++;
        }
        return result.toString().trim();
    }

    public static String decodeInputMessage(String inputMessage) {
        String decodeBinary = decodingBinaryString(inputMessage);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < decodeBinary.length(); i += 7) {
            String binaryChar = decodeBinary.substring(i, Math.min(i + 7, decodeBinary.length()));
            int decimalChar = Integer.parseInt(binaryChar, 2);
            result.append((char) decimalChar);
        }
        return result.toString();
    }

    public static String decodingBinaryString(String inputMessage) {
        StringBuilder decoded = new StringBuilder();

        String[] blocks = inputMessage.split(" ");
        for (int i = 0; i < blocks.length; i += 2) {
            String binaryDigit = blocks[i].equals("00") ? "0" : "1";
            int count = blocks[i + 1].length();
            decoded.append(binaryDigit.repeat(count));
        }
        return decoded.toString();
    }

    public static boolean isOnlyZeroOrSpaceInCode(String inputMessage) {
        return inputMessage.matches("^[0\\s]+$");
    }

    public static boolean isNumberOfBlocksEven(String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        int length = blocks.length;
        return (length % 2 == 0);
    }

    public static boolean is0Or00InEvenBlocks(String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        for (int i = 0; i < blocks.length; i += 2) {
            if (!("0".equals(blocks[i]) || "00".equals(blocks[i]))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLengthOfDecodeBinaryStringMultipleOf7(String inputMessage) {
        int length = decodingBinaryString(inputMessage).length();
        return length % 7 == 0;
    }
}