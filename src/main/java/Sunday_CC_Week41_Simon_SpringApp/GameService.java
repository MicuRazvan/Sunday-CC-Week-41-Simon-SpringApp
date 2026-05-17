package Sunday_CC_Week41_Simon_SpringApp;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GameService {
    private List<Integer> sequence = new ArrayList<>();
    private int playerPointer = 0;
    private Random random = new Random();

    public void startNewGame() {
        sequence = new ArrayList<>();
        sequence.add(random.nextInt(4));
        playerPointer = 0;
    }

    public void nextRound() {
        int newLength = sequence.size() + 1;
        sequence = new ArrayList<>();
        for(int i = 0; i < newLength; i++) {
            sequence.add(random.nextInt(4));
        }
        playerPointer = 0;
    }

    public boolean processClick(int color) {
        if (sequence.get(playerPointer) == color) {
            playerPointer++;
            return true;
        }
        return false;
    }

    public boolean isRoundComplete() { return playerPointer == sequence.size(); }
    public List<Integer> getSequence() { return sequence; }
    public int getScore() { return sequence.size() - 1; }
}