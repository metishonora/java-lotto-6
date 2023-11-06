package lotto.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lotto.dto.LottoDto;
import lotto.model.Lotto;
import lotto.model.LottoComparator;
import lotto.model.LottoGenerator;
import lotto.model.LottoResult;
import lotto.model.LottoWithBonus;
import lotto.model.Money;
import lotto.util.Message;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    /*
    TODO
    결과계산하기();
    출력하기(outputView);
     */
    public void start() {
        Money money = requestMoney();
        List<Lotto> randomLotto = makeRandomLotto(money);
        printGeneratedLotto(randomLotto);
        Lotto winningLotto = requestWinningLotto();
        LottoWithBonus winningLottoWithBonus = requestBonusNumberOf(winningLotto);
        Map<LottoResult, Integer> allResult = compareLotto(winningLottoWithBonus, randomLotto);
    }

    Money requestMoney() {
        outputView.printMessage(Message.REQUEST_MONEY);
        while (true) {
            try {
                return new Money(inputView.requestInteger());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    List<Lotto> makeRandomLotto(Money money) {
        List<Lotto> randomLotto = new ArrayList<>();
        Integer size = money.numberOfPurchaseAvailable();
        for (int i = 0; i < size; i++) {
            Lotto newLotto = LottoGenerator.generateRandomLotto();
            randomLotto.add(newLotto);
        }
        return randomLotto;
    }

    void printGeneratedLotto(List<Lotto> lotto) {
        outputView.printMessage(Message.RESULT_MONEY, lotto.size());
        for (Lotto obj : lotto) {
            outputView.printObject(LottoDto.from(obj));
        }
    }

    Lotto requestWinningLotto() {
        outputView.printMessage(Message.REQUEST_WINNING_NUMBERS);
        while (true) {
            try {
                return LottoGenerator.generateAnswerLotto(inputView.requestIntegers());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    LottoWithBonus requestBonusNumberOf(Lotto winningLotto) {
        outputView.printMessage(Message.REQUEST_BONUS_NUMBER);
        while (true) {
            try {
                return LottoGenerator.generateLottoWithBonus(winningLotto, inputView.requestInteger());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    Map<LottoResult, Integer> compareLotto(LottoWithBonus answer, List<Lotto> randomLotto) {
        Map<LottoResult, Integer> allResult = new LinkedHashMap<>();
        for (LottoResult init : LottoResult.values()) {
            allResult.put(init, 0);
        }
        for (Lotto lotto : randomLotto) {
            LottoResult result = LottoComparator.getPlace(answer, lotto);
            allResult.put(result, allResult.get(result) + 1);
        }
        return allResult;
    }
}
