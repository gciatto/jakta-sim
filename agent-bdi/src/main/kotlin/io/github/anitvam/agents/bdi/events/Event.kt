package io.github.anitvam.agents.bdi.events

import io.github.anitvam.agents.bdi.beliefs.Belief
import io.github.anitvam.agents.bdi.intentions.IntentionID
import io.github.anitvam.agents.bdi.events.impl.EventImpl
import io.github.anitvam.agents.bdi.goals.Achieve
import io.github.anitvam.agents.bdi.goals.Test
import io.github.anitvam.agents.bdi.intentions.Intention
import it.unibo.tuprolog.core.Struct

/**
 * A BDI Agent can react to two types of Events: External and Internal.
 * An Event is a pair where the [Trigger] represents the change that took place and the [IntentionID] is the
 * associated intention.
 *
 * As External Events are not generated by intentions, they are represented by having an empty [IntentionID]
 */
interface Event {
    /** Denotes the change that took place for the Event generation */
    val trigger: Trigger

    /** The Event's associated Intention. Its value is null if the Event is an External one. */
    val intention: Intention?

    /** @return true if this is an Internal Event, otherwise false. */
    fun isInternal(): Boolean = intention != null

    /** @return true if this is an External Event, otherwise false. */
    fun isExternal(): Boolean = intention == null

    companion object {
        /**
         * Generates a new [Event]
         * @param trigger: the [Trigger] of the [Event]
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun of(trigger: Trigger, intention: Intention? = null): Event = EventImpl(trigger, intention)

        /**
         * Generates an [Event] with a [BeliefBaseAddition] trigger.
         * @param belief: the belief that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofBeliefBaseAddition(belief: Belief, intention: Intention? = null): Event =
            of(BeliefBaseAddition(belief), intention)

        /**
         * Generates an [Event] with a [BeliefBaseRemoval] trigger.
         * @param belief: the belief that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofBeliefBaseRemoval(belief: Belief, intention: Intention? = null): Event =
            of(BeliefBaseRemoval(belief), intention)

        fun ofBeliefBaseUpdate(belief: Belief, intention: Intention? = null): Event =
            of(BeliefBaseUpdate(belief), intention)

        /**
         * Generates an [Event] with a [TestGoalInvocation] trigger.
         * @param testGoal: the [Test] Goal that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofTestGoalInvocation(testGoal: Test, intention: Intention? = null): Event =
            of(TestGoalInvocation(testGoal.value), intention)

        /**
         * Generates an [Event] with a [TestGoalFailure] trigger.
         * @param testGoal: the Goal that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofTestGoalFailure(testGoal: Struct, intention: Intention? = null): Event =
            of(TestGoalFailure(testGoal), intention)

        /**
         * Generates an [Event] with a [AchievementGoalInvocation] trigger.
         * @param achievementGoal: the [Achieve] Goal that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofAchievementGoalInvocation(achievementGoal: Achieve, intention: Intention? = null): Event =
            of(AchievementGoalInvocation(achievementGoal.value), intention)

        /**
         * Generates an [Event] with a [AchievementGoalFailure] trigger.
         * @param achievementGoal: the Goal that triggered this Event
         * @param intention: if the event is internal, this parameter specifies the intention id where the event belongs.
         * If the event is external, this value is set to null. It's default value is null.
         * @return a new instance of [Event]
         */
        fun ofAchievementGoalFailure(achievementGoal: Struct, intention: Intention? = null): Event =
            of(AchievementGoalFailure(achievementGoal), intention)
    }
}
