package Sunday_CC_Week41_Simon_SpringApp;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class GameController {
    private final GameService gameService;
    private final String CSV_PATH = "leaderboard.csv";

    public GameController(GameService gs) { this.gameService = gs; }

    @PostMapping("/start")
    public List<Integer> start() {
        gameService.startNewGame();
        return gameService.getSequence();
    }

    @PostMapping("/click")
    public Map<String, Object> click(@RequestBody Map<String, Integer> input) {
        boolean correct = gameService.processClick(input.get("color"));
        Map<String, Object> response = new HashMap<>();
        response.put("correct", correct);

        if (correct && gameService.isRoundComplete()) {
            gameService.nextRound();
            response.put("nextSequence", gameService.getSequence());
            response.put("roundOver", true);
        } else if (!correct) {
            response.put("gameOver", true);
            response.put("score", gameService.getScore());
        }
        return response;
    }

    @GetMapping("/leaderboard")
    public List<String> getLeaderboard() throws IOException {
        return Files.lines(Paths.get(CSV_PATH))
                .sorted((a, b) -> Integer.compare(Integer.parseInt(b.split(",")[1]), Integer.parseInt(a.split(",")[1])))
                .limit(10).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public void save(@RequestBody Map<String, String> data) throws IOException {
        String name = (data.get("name") == null || data.get("name").isEmpty()) ? "Unknown" : data.get("name");
        Files.write(Paths.get(CSV_PATH), (name + "," + data.get("score") + "\n").getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }
}
