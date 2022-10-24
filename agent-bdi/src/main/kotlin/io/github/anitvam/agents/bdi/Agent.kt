package io.github.anitvam.agents.bdi

import io.github.anitvam.agents.bdi.beliefs.BeliefBase
import io.github.anitvam.agents.bdi.beliefs.Belief
import io.github.anitvam.agents.bdi.beliefs.RetrieveResult
import io.github.anitvam.agents.bdi.events.Event
import io.github.anitvam.agents.bdi.events.EventQueue
import io.github.anitvam.agents.bdi.intentions.Intention
import io.github.anitvam.agents.bdi.intentions.IntentionPool
import io.github.anitvam.agents.bdi.plans.Plan
import io.github.anitvam.agents.bdi.plans.PlanLibrary

/** BDI Agent definition*/
interface Agent {
    /** Snapshot of Agent's Actual State */
    val context: AgentContext

    /**
     * STEP 1 of reasoning cycle: Belief Update Function.
     * This function defines how to merge new [perceptions] into the current [beliefBase]
     * @param perceptions: [BeliefBase] that collects all agent's perceptions of the environment
     * @param beliefBase: [BeliefBase] the current agent's [BeliefBase]
     * @return a [RetrieveResult] that contains the updated [BeliefBase] and the added [Belief]s
     */
    fun updateBelief(perceptions: BeliefBase, beliefBase: BeliefBase): RetrieveResult

    /**
     * STEP 5 of reasoning cycle: Selecting an Event.
     * This function select an event to be handled in a particular reasoning cycle.
     * The default implementation follows a FIFO policy for the [EventQueue].
     * @param events: [EventQueue] on which select the event
     * @return the selected [Event]
     */
    fun selectEvent(events: EventQueue): Event

    /**
     * STEP 6 of reasoning cycle: Retrieving all Relevant Plans.
     * This function returns all plans from [PlanLibrary] that have a triggering event that can be unified
     * with the selected event.
     * @param event: the selected [Event]
     * @param planLibrary: the [PlanLibrary] of the Agent
     * @return the relevant [Plan]s
     */
    fun selectRelevantPlans(event: Event, planLibrary: PlanLibrary): PlanLibrary

    /**
     * STEP 7 of reasoning cycle: Determining the Applicable Plans.
     * This function defines if a plan is applicable based on the agent's Belief Base.
     * @param event: the selected [Event] that triggered the [Plan]
     * @param plan: the triggered [Plan]
     * @param beliefBase: the agent's [BeliefBase]
     * @return yes if it's applicable, false otherwise.
     */
    fun isPlanApplicable(event: Event, plan: Plan, beliefBase: BeliefBase): Boolean

    /**
     * Step 8 of reasoning cycle: Selecting one Applicable Plan.
     * Given all the applicable plans, this Selection Function returns the plan that the agent will commit to execute.
     * By default,
     * @param plans: applicable [Plan]s
     * @return the selected [Plan] to be executed
     */
    fun selectApplicablePlan(plans: Iterable<Plan>): Plan

    /**
     * Step 8 of reasoning cycle: Assign selected plan to an Intention.
     * If the event is external, then a new Intention is created. Otherwise, the selected plan is pushed on top of the
     * firing Intention.
     * @param event: the [Event] that triggered the [Plan]
     * @param plan: the selected [Plan]
     * @param intentions: the [IntentionPool] of the agent
     * @return the updated [Intention]
     */
    fun assignPlanToIntention(event: Event, plan: Plan, intentions: IntentionPool): Intention

    /**
     * Step 9 of reasoning cycle: Selecting an Intention for Further Execution.
     * Given all agent's intentions, this Selection Function selects the intention to be scheduled to execution
     * by the agent.
     */
    fun scheduleIntention(intentions: IntentionPool): Intention
    fun runIntention(intention: Intention): Intention

    fun reason()
}

