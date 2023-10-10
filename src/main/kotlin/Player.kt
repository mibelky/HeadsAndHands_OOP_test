class Player(
    name: String,
    attack: Int,
    defense: Int,
    maxHealth: Int,
    damageRange: IntRange
) : Creature(name, attack, defense, maxHealth, damageRange) {

    var healsLeft = 4
        private set

    fun heal() {
        require(isAlive()) { "$name is already dead, cannot heal" }
        if (healsLeft-- > 0)
            health = (health + (maxHealth * 3) / 10).coerceAtMost(maxHealth)
    }
}