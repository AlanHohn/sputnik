package pl.touk.sputnik.engine.visitor;

import org.junit.Test;
import pl.touk.sputnik.ReviewBuilder;
import pl.touk.sputnik.review.Review;

import static org.assertj.core.api.Assertions.assertThat;

public class LimitCommentVisitorTest {

    @Test
    public void shouldNotLimitCommentsIfCountIsBelowMaximumCount() {
        Review review = ReviewBuilder.buildReview();

        new LimitCommentVisitor(10).afterReview(review);

        assertThat(review.getFiles()).hasSize(4);
        assertThat(review.getMessages()).containsExactly("Total 8 violations found");
        assertThat(review.getFiles().get(0).getComments()).hasSize(2);
        assertThat(review.getFiles().get(1).getComments()).hasSize(2);
        assertThat(review.getFiles().get(2).getComments()).hasSize(2);
        assertThat(review.getFiles().get(3).getComments()).hasSize(2);
    }

    @Test
    public void shouldLimitCommentsIfCountIsHigherMaximumCount() {
        Review review = ReviewBuilder.buildReview();

        new LimitCommentVisitor(3).afterReview(review);

        assertThat(review.getFiles()).hasSize(4);
        assertThat(review.getMessages()).containsExactly("Total 8 violations found", "Showing only first 3 comments. 5 comments are filtered out");
        assertThat(review.getFiles().get(0).getComments()).hasSize(2);
        assertThat(review.getFiles().get(1).getComments()).hasSize(1);
        assertThat(review.getFiles().get(2).getComments()).isEmpty();
        assertThat(review.getFiles().get(3).getComments()).isEmpty();
    }
}