import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final String[] HANGMAN_STATES = {
            """
             +---+
             |   |
                 |
                 |
                 |
                 |
            =======    
            """,
            """
            +---+
             |   |
             O   |
                 |
                 |
                 |
            =======    
            """,
            """
            +---+
             |   |
             O   |
             |   |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|   |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            /    |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            / \\  |
                 |
            =======    
            """
    };

    private static final String[] WORDS_LIST = {"собака", "кошка", "пудель", "творог", "гречка"};

    private static final int MAX_ERRORS_COUNT = HANGMAN_STATES.length - 1;

    private static final String WRONG_ERRORS_COUNT = "Неверное количество ошибок";

    private static final String LOSE_STATE = "Проигрыш";
    private static final String WIN_STATE = "Выигрыш";
    private static final String GAME_NOT_OVER_STATE = "Игра продолжается";


    private static final Random random = new Random();

    private static final Scanner scanner = new Scanner(System.in);
    private static final String CONTINUE_GAME_CHOICE = "1";


    public static void main(String[] args) {


        startGameLoop();
        int a =1;
        // startGameLoop
    }

    public static void startGameLoop() {

        System.out.println("Для начала игры введите 1. Для выхода введите что угодно, кроме 1");
        String playerChoice = scanner.nextLine();

        while (playerChoice.equals(CONTINUE_GAME_CHOICE))
        {
            String randomWord = getRandomWord();
            startRoundLoop(randomWord);

            System.out.println("Для продолжения введите 1. Для выхода введите что угодно, кроме 1");
            playerChoice = scanner.nextLine();
        }


    }

    public static String getRandomWord() {
        int randomInt = random.nextInt(WORDS_LIST.length);
        return WORDS_LIST[randomInt];
    }

    public static void startRoundLoop(String hiddenWord) {

        char[] userWord = generateUserWord(hiddenWord);
        int errorsCount = 0;
        char[] errors = new char[MAX_ERRORS_COUNT];

        do {
            char character = inputCharacter();

            if (isCharacterInWord(character, new String(userWord))) {
                System.out.println("Вы ввели ранее отгаданную букву!");

            }
            else if (isCharacterInWord(character, new String(errors))) {
                System.out.println("Вы повторно ввели букву, которой нет в загаданном слове!");
            }

            else if (isCharacterInWord(character, hiddenWord)) {
                uncoverCharacters(character, userWord, hiddenWord);

            }
            else {
                errors[errorsCount] = character;
                errorsCount += 1;
            }

            String gameState = getGameState(userWord, hiddenWord, errorsCount);

            if (gameState.equals(WIN_STATE)) {
                System.out.println("Вы победили!");
            }
            else if (gameState.equals(LOSE_STATE)) {
                System.out.println("Вы проиграли");
            };

            showGameState(errorsCount, userWord, errors);

        } while (getGameState(userWord,hiddenWord, errorsCount).equals(GAME_NOT_OVER_STATE));


    }

    private static void uncoverCharacters(char character, char[] userWord, String hiddenWord) {

        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == character) {
                userWord[i] = character;
            }
        }
    }

    private static char[] generateUserWord(String hiddenWord) {
        char[] userWord = new char[hiddenWord.length()];

        Arrays.fill(userWord, '_');

        return userWord;
    }

    public static char inputCharacter() {
        System.out.println("Введите букву:");

        do {
            String inputString = scanner.nextLine();

            if (inputString.length() == 1) {
                return inputString.toCharArray()[0];
            }

            System.out.println("Неверное значение! Введите один символ");
        } while (true);

    }

    public static boolean isCharacterInWord(char character, String word) {

        for (int i = 0; i < word.length(); i++) {
            if (character == word.charAt(i)) {
                return true;
            }
        }

        return false;
    }

    // на вход функции подаём корректные userWord и hiddenWord
    // одновременно наступить и выигрыш, и проигрыш не может
    public static String getGameState (char[] userWord, String hiddenWord, int errorsCount) {

        String userWordCopy = new String(userWord);

        if (errorsCount >= MAX_ERRORS_COUNT) {
            return LOSE_STATE;
        }
        else if (userWordCopy.equals(hiddenWord)) {
            return WIN_STATE;
        }
        else {
            return GAME_NOT_OVER_STATE;
        }

    }

    public static String getHangManState(int errorsCount) {
        if (errorsCount < 0 || errorsCount > MAX_ERRORS_COUNT) {
            return WRONG_ERRORS_COUNT;
        }
        else {
            return HANGMAN_STATES[errorsCount];
        }
    }

    public static void showGameState(int errorsCount, char[] userWord, char[] errors) {
        System.out.println("Количество ошибок: " + errorsCount);
        System.out.println("Ваши ошибки: " + getErrors(errorsCount, errors));
        System.out.println("Состояние виселицы:");
        System.out.println(getHangManState(errorsCount));
        System.out.println("Ваш прогресс:");
        System.out.println(userWord);
    }

    private static String getErrors(int errorsCount, char[] errors) {
        StringBuilder result = new StringBuilder("[ ");
        for (int i = 0; i < errorsCount; i++) {
            result.append(errors[i]);
            if (i != errorsCount - 1) {
                result.append(", ");
            }
        }
        result.append(" ]");
        return result.toString();
    }
}