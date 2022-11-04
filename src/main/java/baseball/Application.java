package baseball;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static baseball.Application.StringConst.*;

public class Application {
    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        Boolean userWantToPlayGame = true;

        while (userWantToPlayGame) {
            playGame();
            userWantToPlayGame = userWantToContinueGame();
        }
    }

    public static void playGame() {
        Integer computerNumber = RandomNumberGenerator.generate();
        Map<String, Integer> score;

        do {
            System.out.print("숫자를 입력해주세요 : ");
            Integer userNumber = ConsoleReader.readInteger();
            if (!NumberValidator.valid(userNumber))
                throw new IllegalArgumentException();

            score = calculateScore(computerNumber, userNumber);
            printScore(score);
        } while (!score.get(STRIKE).equals(3));

        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
    }

    public static void printScore(Map<String, Integer> score) {
        List<String> result = new ArrayList<>();
        Integer ballCount = score.get(BALL);
        Integer strikeCount = score.get(STRIKE);

        if (!ballCount.equals(0))
            result.add(ballCount + BALL);
        if (!strikeCount.equals(0))
            result.add(strikeCount + STRIKE);
        if (ballCount.equals(0) && strikeCount.equals(0))
            result.add(NOTHING);

        System.out.print(result.stream().collect(Collectors.joining(" ")) + "\n");
    }

    public static Map<String, Integer> calculateScore(Integer computerNumber, Integer userNumber) {
        Map<String, Integer> ret = new HashMap<>();
        ret.put(STRIKE, 0);
        ret.put(BALL, 0);

        String computerNumberStringType = String.valueOf(computerNumber);
        char[] computerPlaceValues = computerNumberStringType.toCharArray();
        char[] userPlaceValues = String.valueOf(userNumber).toCharArray();

        for (int i = 0; i < 3; ++i)
            if (computerNumberStringType.contains(String.valueOf(userPlaceValues[i])))
                ret.put(BALL, ret.get(BALL) + 1);
        for (int i = 0; i < 3; ++i)
            if (userPlaceValues[i] == computerPlaceValues[i]) {
                ret.put(STRIKE, ret.get(STRIKE) + 1);
                ret.put(BALL, ret.get(BALL) - 1);
            }
        return ret;
    }

    public static Boolean userWantToContinueGame() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        Integer readInteger = ConsoleReader.readInteger();
        if (readInteger.equals(1))
            return true;
        return false;
    }

    interface StringConst {
        String BALL = "볼";
        String STRIKE = "스트라이크";
        String NOTHING = "낫싱";
    }
}
