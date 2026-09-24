package com.skd.astralcore.ritual;

/**
 * Configuration for what happens when a ritual fails the risk roll.
 *
 * @param failureChance probability of failure (0.0 = never, 1.0 = always)
 * @param penalty       what happens on failure
 */
public record RitualRisk(double failureChance, RiskPenalty penalty) {

    /**
     * What happens when a ritual fails.
     */
    public enum RiskPenalty {
        /** Nothing extra — ritual just fails without consuming inputs. */
        NONE,
        /** Consumes the input items without producing outputs. */
        CONSUME_INPUTS
    }

    /**
     * Safe default: never fails.
     */
    public static RitualRisk none() {
        return new RitualRisk(0.0, RiskPenalty.NONE);
    }
}
