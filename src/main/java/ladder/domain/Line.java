package ladder.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import ladder.domain.dto.LineResponseDto;
import ladder.domain.randomGenerator.RungGenerator;

public class Line {

    private static final boolean FIRST_POSITION_NOT_EXIST = false;
    private static final int EXCLUDE_LAST_POSITION = 1;
    private static final int FOR_BEFORE_POSITION = 1;

    private final RungGenerator rungGenerator;
    private final List<Rung> rungs;

    public Line(final RungGenerator rungGenerator, final int personCount) {
        this.rungGenerator = rungGenerator;
        this.rungs = makeRungs(personCount);
    }

    private List<Rung> makeRungs(int personCount) {
        List<Rung> rungs = new ArrayList<>();

        IntStream.range(0, personCount - EXCLUDE_LAST_POSITION)
                .forEach(currentPosition -> rungs.add(generateRung(currentPosition, rungs)));

        return rungs;
    }

    private Rung generateRung(int currentPosition, List<Rung> rungs) {
        if (!hasRungDuplicated(currentPosition, rungs) && rungGenerator.getRandomBooleanStatus()) {
            return Rung.EXIST;
        }
        return Rung.NOT_EXIST;
    }

    private boolean hasRungDuplicated(int currentPosition, List<Rung> rungs) {
        if (currentPosition > 0) {
            return rungs.get(currentPosition - FOR_BEFORE_POSITION).isBuildStatus();
        }
        return FIRST_POSITION_NOT_EXIST;
    }

    public LineResponseDto getRungs() {
        return LineResponseDto.of(rungs);
    }
}
