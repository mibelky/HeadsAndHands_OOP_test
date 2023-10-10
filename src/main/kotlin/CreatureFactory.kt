class ByTypeCreatureFactory : CreatureFactory {
    private fun createPlayer(name: String): Player {
        return Player(name, 20, 10, 80, 20..25)
    }

    private fun createMonster(name: String): Monster {
        return Monster(name, 13, 18, 150, 15..17)
    }

    override fun create(type: CreatureFactory.CreatureType, name: String): Creature =
        when (type) {
            CreatureFactory.CreatureType.Player -> createPlayer(name)
            CreatureFactory.CreatureType.Monster -> createMonster(name)
        }

}

interface CreatureFactory {
    enum class CreatureType {  Player, Monster }
    fun create(type: CreatureType, name: String) : Creature
}