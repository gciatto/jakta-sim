package io.github.anitvam.agents.bdi.impl

import io.github.anitvam.agents.bdi.Agent
import io.github.anitvam.agents.bdi.ExecutionStrategy
import io.github.anitvam.agents.bdi.Mas
import io.github.anitvam.agents.bdi.actions.effects.BroadcastMessage
import io.github.anitvam.agents.bdi.actions.effects.EnvironmentChange
import io.github.anitvam.agents.bdi.actions.effects.RemoveAgent
import io.github.anitvam.agents.bdi.actions.effects.SendMessage
import io.github.anitvam.agents.bdi.actions.effects.SpawnAgent
import io.github.anitvam.agents.bdi.environment.Environment

internal class MasImpl(
    override val executionStrategy: ExecutionStrategy,
    override var environment: Environment,
    override var agents: Iterable<Agent>,
) : Mas {
    init {
        agents.forEach { environment.addAgent(it.agentID) }
    }

    override fun start() = agents.forEach { executionStrategy.dispatch(it, this).run() }

    override fun applyEnvironmentEffects(effects: Iterable<EnvironmentChange>) = effects.forEach {
        when (it) {
            is BroadcastMessage -> environment = environment.broadcastMessage(it.message)
            is RemoveAgent -> environment = environment.removeAgent(it.agentID)
            is SendMessage -> environment = environment.submitMessage(it.recipient, it.message)
            is SpawnAgent -> {
                agents += it.agent
                environment = environment.addAgent(it.agent.agentID)
            }
        }
    }
}
