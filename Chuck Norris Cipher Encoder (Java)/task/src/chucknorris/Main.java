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
                    String encodedMessage = encodeInputMessage(inputText);
                    System.out.println(encodedMessage);
                    System.out.println();
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    String inputMessage = scanner.nextLine();
                    if (isOnlyZeroOrSpaceInCode(inputMessage)
                            && isNumberOfBlockEven(inputMessage)
                            && isLengthOfDecodeBinaryStringMultipleOf7(inputMessage)
                            && is0Or00InEvenBlocks(inputMessage)) {
                        System.out.println("Decoded string:");
                        System.out.println(decodeInputMessage(inputMessage));
                        System.out.println();
                    } else {
                        System.out.println("Encoded string is not valid.");
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("There is no '" + choice + "' operation");
            }
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

            result.append((currentElementOfBinary == '1' ? "0" : "00") + " " + "0".repeat(count)+ " ");
            i++;
        }
        return result.toString().trim();
    }

    public static String decodeInputMessage(String encodedMessage) {
        StringBuilder decoded = new StringBuilder();

        String[] blocks = encodedMessage.split(" ");
        for (int i = 0; i < blocks.length; i += 2) {
            int binaryDigit = blocks[i].equals("00") ? 0 : 1;
            for (int j = 0; j < blocks[i + 1].length(); j++) {
                decoded.append(binaryDigit == 0 ? '0' : '1');
            }

        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < decoded.length(); i += 7) {
            String binaryChar = decoded.substring(i, Math.min(i + 7, decoded.length()));
            int decimalChar = Integer.parseInt(binaryChar, 2);
            result.append((char) decimalChar);
        }
        return result.toString();
    }

    public static boolean isOnlyZeroOrSpaceInCode(String inputMessage) {
        return inputMessage.matches("^[0\\s]+$");
    }

    public static boolean isNumberOfBlockEven (String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        int length = blocks.length;
        return (length % 2 == 0);
    }

    public static boolean is0Or00InEvenBlocks(String inputMessage) {
        String[] blocks = inputMessage.split(" ");
        boolean validBlock = true;
        for (int i = 0; i < blocks.length; i += 2) {
            if ("0".equals(blocks[i]) || "00".equals(blocks[i])) {
                validBlock = true;
            } else {
                validBlock = false;
            }
        }
        return validBlock;
    }

    public static int determineLengthOfDecodeBinaryString(String inputMessage) {
        StringBuilder decoded = new StringBuilder();
        String[] blocks = inputMessage.split(" ");

        for (int i = 0; i < blocks.length; i += 2) {
            int count = blocks[i].equals("00") ? 0 : 1;
            for (int j = 0; j < blocks[i + 1].length(); j++) {
                decoded.append(count == 0 ? '0' : '1');
            }
        }
        return decoded.length();
    }

    public static boolean isLengthOfDecodeBinaryStringMultipleOf7(String inputMessage) {
        int length = determineLengthOfDecodeBinaryString(inputMessage);
        return length % 7 == 0;
    }
}