open class Creature(
    val name: String,
    private val attack: Int,
    private val defense: Int,
    val maxHealth: Int,
    private val damageRange: IntRange
) {
    var health = maxHealth
        protected set

    fun isAlive(): Boolean {
        return health > 0
    }

    private fun takeDamage(damage: Int) {
        require(damage >= 0) { "Damage cannot be negative" }
        require(isAlive()) { "$name is already dead" }
        health = (health - damage).coerceAtLeast(0)
    }

    fun attackOther(otherCreature: Creature, dice: Battle.Dice): Boolean {
        require(isAlive()) { "$name is already dead, cannot attack" }
        val attackModifier = attack - otherCreature.defense + 1
        val numDice = attackModifier.coerceAtLeast(1)

        repeat(numDice) {
            if (dice.rollSuccess) {
                otherCreature.takeDamage(damageRange.random())
                return true
            }
        }
        return false
    }

}

