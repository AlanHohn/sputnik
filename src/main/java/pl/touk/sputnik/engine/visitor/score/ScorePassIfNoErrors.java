package pl.touk.sputnik.engine.visitor.score;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import pl.touk.sputnik.engine.visitor.AfterReviewVisitor;
import pl.touk.sputnik.review.Review;
import pl.touk.sputnik.review.Severity;

import java.util.Map;

@Slf4j
@Getter
@AllArgsConstructor
public class ScorePassIfNoErrors implements AfterReviewVisitor {
    private final Map<String, Integer> passingScore;
    private final Map<String, Integer> failingScore;

    @Override
    public void afterReview(@NotNull Review review) {
        Integer errorCount = review.getViolationCount().get(Severity.ERROR);
        if (errorCount == null || errorCount == 0) {
            log.info("Adding passing score {} for no errors found", passingScore);
            review.setScores(passingScore);
            return;
        }

        log.info("Adding failing score {} for {} errors found", failingScore, errorCount);
        review.setScores(failingScore);
    }
}
