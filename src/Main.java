import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger countThree = new AtomicInteger(0);
    static AtomicInteger countFour = new AtomicInteger(0);
    static AtomicInteger countFive = new AtomicInteger(0);

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }


    static Set<String> isPalindrome(String[] strings) { //метод возвращает массив из слов-палиндромов
        Set<String> newString = new HashSet<>();
        for (String str : strings) {
            StringBuilder strBuilder = new StringBuilder(str);
            strBuilder.reverse(); //переворачиваем строку
            String invertedText = strBuilder.toString();//присваиваем перевернутую строку

            if (str.equals(invertedText)) {
                newString.add(str);
            }
        }
        return newString;
    }

    static Set<String> fromTheSameLetter(String[] strings) { //метод возвращает список из слов состоящих из одной и той же буквы
        Set<String> newString = new HashSet<>();
        for (String str : strings) {
            boolean isAllCharsSame = str.chars().distinct().count() == 1;
            if (isAllCharsSame) {
                newString.add(str);
            }
        }
        return newString;

    }

    static Set<String> inAscendingOrder(String[] strings) { //метод возвращает список из слов в котором буквы идут по возрастанию
        Set<String> newString = new HashSet<>();
        boolean isAscending = true;

        for (String str : strings) {
            for (int i = 0; i < str.length() - 1; ++i) {
                if (((int) str.charAt(i)) > ((int) str.charAt(i + 1))) {
                    isAscending = false;
                }
            }
            if (isAscending) {
                newString.add(str);
            }
            isAscending = true;
        }
        return newString;
    }

    public static String countWords(Set<String> set) {
        for (String str : set) {
            if (str.length() == 3) {
                countThree.addAndGet(1);
            } else if (str.length() == 4) {
                countFour.addAndGet(1);
            } else countFive.addAndGet(1);
        }
        return "Красивых слов с длиной 3: " + countThree + "\n" +
                "Красивых слов с длиной 4: " + countFour + "\n" +
                "Красивых слов с длиной 5: " + countFive + "\n";
    }

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(null, () -> System.out.println(countWords(isPalindrome(texts))), "Проверка на палиндромы");
        Thread thread2 = new Thread(null, () -> System.out.println(countWords(fromTheSameLetter(texts))), "Проверка на слова из одной и той же буквы");
        Thread thread3 = new Thread(null, () -> System.out.println(countWords(inAscendingOrder(texts))), "Проверка на буквы по возрастанию");

        //System.out.println("сгенерированное слово является палиндромом, т.е. читается одинаково как слева направо, так и справа налево");
        thread1.start();
        // System.out.println("сгенерированное слово состоит из одной и той же буквы");
        thread2.start();
        // System.out.println("буквы в слове идут по возрастанию: сперва все a (при наличии), затем все b (при наличии), затем все c");
        thread3.start();

        //System.out.println(isPalindrome(texts));
        //System.out.println(fromTheSameLetter(texts));
        //System.out.println(inAscendingOrder(texts));

    }
}

