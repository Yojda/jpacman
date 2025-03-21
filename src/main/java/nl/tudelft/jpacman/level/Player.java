package nl.tudelft.jpacman.level;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.sprite.AnimatedSprite;
import nl.tudelft.jpacman.sprite.Sprite;

/**
 * A player operated unit in our game.
 *
 * @author Jeroen Roosen
 */
public class Player extends Unit {

    private static final int STARTING_HP = 3;

    /**
     * The amount of heal points this player has.
     */
    private int hp;

    private boolean alive;

    /**
     * The amount of points accumulated by this player.
     */
    private int score;

    /**
     * The animations for every direction.
     */
    private final Map<Direction, Sprite> sprites;

    /**
     * The animation that is to be played when Pac-Man dies.
     */
    private final AnimatedSprite deathSprite;


    /**
     * {@link Unit} iff this player died by collision, <code>null</code> otherwise.
     */
    private Unit killer;


    /**
     * Creates a new player with a score of 0 points.
     *
     * @param spriteMap      A map containing a sprite for this player for every direction.
     * @param deathAnimation The sprite to be shown when this player dies.
     */
    protected Player(Map<Direction, Sprite> spriteMap, AnimatedSprite deathAnimation) {
        this.hp = STARTING_HP;
        this.alive = true;
        this.score = 0;
        this.sprites = spriteMap;
        this.deathSprite = deathAnimation;
        deathSprite.setAnimating(false);
    }

    /**
     * Returns the amount of heal points this player has.
     *
     * @return The amount of heal points this player has.
     */
    public int getHP() {
        return hp;
    }

    /**
     * Returns whether this player is alive or not.
     *
     * @return <code>true</code> iff the player is alive.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Change isAlive status if a player still have lives.
     */
    public void revive() {
        if (this.getHP() > 0) {
            setAlive(true);
        }
    }

    /**
     * Handle the death of a player
     */
    public void death() {
        this.alive = false;
        deathSprite.restart();
    }

    /**
     * Sets whether this player is alive or not.
     * <p>
     * If the player comes back alive, the {@link this.killer} will be reset.
     *
     * @param isAlive <code>true</code> iff this player is alive.
     */
    public void setAlive(boolean isAlive) {
        this.alive = isAlive;
    }

    /**
     * Returns the unit that caused the death of Pac-Man.
     *
     * @return <code>Unit</code> iff the player died by collision, otherwise <code>null</code>.
     */
    public Unit getKiller() {
        return killer;
    }

    /**
     * Sets the cause of death.
     *
     * @param killer is set if collision with ghost happens.
     */
    public void setKiller(Unit killer) {
        this.killer = killer;
    }

    /**
     * Returns the amount of points accumulated by this player.
     *
     * @return The amount of points accumulated by this player.
     */
    public int getScore() {
        return score;
    }

    @Override
    public Sprite getSprite() {
        if (isAlive()) {
            return sprites.get(getDirection());
        }
        return deathSprite;
    }

    /**
     *
     * @return the locker of the animation.
     */
    public CompletableFuture<Void> getAnimationFuture() {
        return deathSprite.getAnimationFuture();
    }

    /**
     * Adds points to the score of this player.
     *
     * @param points The amount of points to add to the points this player already
     *               has.
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Removes points from the score of this player.
     *
     * @param hp The amount of points to remove from the points this player already has.
     */
    public void removeHP(int hp) {
        this.hp -= hp;
    }
}
