
| <img src="/site/static/images/logo.svg"  width="100"> | <h1>JaKtA (Jason-like Kotlin Agents)</h1> |
|:-:|:-:|

JaKtA is a Kotlin internal DSL adding support for the definition of BDI agents in the spirit of the well-known Jason language.

[Alchemist](https://github.com/AlchemistSimulator/Alchemist) is a discrete-events simulator for pervasive, aggregate, and nature-inspired computing.

In this repository,
we implement an incarnation of Alchemist supporting the simulation of JaKtA agents,
with minimal changes to the MAS specification.

As a result, 
we support simulating JaKtA agents before their real-world deployment,
which remains possible, as JaKtA main goal is to support real-world execution of BDI agents.

This repository is actually a fork of the JaKtA main-line repository,
with the addition of the `alchemist-jakta-incarnation` module,
which implements the bridge between JaKtA and Alchemist.

Simulations involving JaKtA agents are available in separate repositories.
