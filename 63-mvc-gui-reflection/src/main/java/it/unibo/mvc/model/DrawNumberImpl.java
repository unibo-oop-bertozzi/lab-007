package it.unibo.mvc.model;

import it.unibo.mvc.api.DrawNumber;
import it.unibo.mvc.api.DrawResult;

import java.util.Random;

/**
 * Implementationn of the application model.
 */
public final class DrawNumberImpl implements DrawNumber {

    private int choice;
    private final int min;
    private final int max;
    private final int attempts;
    private int remainingAttempts;
    private final Random random = new Random();

    /**
     * Builds a new game.
     *
     * @throws IllegalStateException if the configuration is not consistent
     */
    public DrawNumberImpl() {
        this(new Configuration.Builder().build());
    }

    /**
     * Builds a new game.
     *
     * @param configuration the game configuration
     * @throws IllegalStateException if the configuration is not consistent
     */
    public DrawNumberImpl(final Configuration configuration) {
        if (!configuration.isConsistent()) {
            throw new IllegalArgumentException("The game requires a valid configuration");
        }
        this.min = configuration.getMin();
        this.max = configuration.getMax();
        this.attempts = configuration.getAttempts();
        this.reset();
    }

    @Override
    public void reset() {
        this.remainingAttempts = this.attempts;
        this.choice = this.min + random.nextInt(this.max - this.min + 1);
    }

    @Override
    public DrawResult attempt(final int n) {
        if (this.remainingAttempts <= 0) {
            return DrawResult.YOU_LOST;
        }
        if (n < this.min || n > this.max) {
            throw new IllegalArgumentException("The number is outside boundaries");
        }
        remainingAttempts--;
        if (n > this.choice) {
            return DrawResult.YOURS_HIGH;
        }
        if (n < this.choice) {
            return DrawResult.YOURS_LOW;
        }
        return DrawResult.YOU_WON;
    }
}
