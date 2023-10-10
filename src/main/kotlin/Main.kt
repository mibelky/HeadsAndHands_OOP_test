fun main() {
    val creatureFactory = ByTypeCreatureFactory()
    val player = creatureFactory.create(CreatureFactory.CreatureType.Player,"Archer")
    val monster = creatureFactory.create(CreatureFactory.CreatureType.Monster, "Orc")

    println("Welcome to the Battle Game!")
    println("Player: ${player.name} vs. Monster: ${monster.name}")

    val battle = Battle.Builder(player, monster)
        .enableBattleLog()
        .addDice(Battle.Dice.Type.D6)
        .changeOrder()
        .build()

    battle.startBattle()
}
