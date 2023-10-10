class Battle private constructor(
    private val first: Creature,
    private val second: Creature,
    private val battleLogEnabled: Boolean,
    private val dice: Dice
) {

    sealed interface Dice {
        val range: IntRange
        val successRange: IntRange
        private val roll: Int
            get() = range.random()
        val rollSuccess: Boolean
            get() = roll in successRange

        enum class Type {
            D6, D20
        }

        class D6 : Dice {
            override val range = 1..6
            override val successRange = 5..6
        }

        class D20 : Dice {
            override val range = 1..20
            override val successRange = 16..20
        }
    }


    class Builder(private val first: Creature, private val second: Creature) {
        private var battleLogEnabled = false
        private var dice: Dice = Dice.D6()
        private var changeOrder: Boolean = false

        fun enableBattleLog(): Builder {
            battleLogEnabled = true
            return this
        }

        fun addDice(diceType: Dice.Type): Builder {
            dice = when (diceType) {
                Dice.Type.D6 -> Dice.D6()
                Dice.Type.D20 -> Dice.D20()
            }
            return this
        }

        fun changeOrder(): Builder {
            changeOrder = true
            return this
        }

        fun build() =
            if (changeOrder)
                Battle(second, first, battleLogEnabled, dice)
            else
                Battle(first, second, battleLogEnabled, dice)
    }

    fun startBattle() {
        while (first.isAlive() && second.isAlive()) {
            val action = askForAction()
            turn(action, first, second)
            if (second.isAlive())
                turn(action, second, first)
            log("${first.name}'s health: ${first.health}\n${second.name}'s health: ${second.health}\n")
        }

        if (first.isAlive())
            println("${first.name} wins!")
        else
            println("${second.name} wins!")
    }

    private fun turn(action: Int, p1: Creature, p2: Creature) {
        if (p1 is Player && action == 2) {
            p1.heal()
            log("${p1.name} heals and restores health.")
        }
        else
            p1 attacks p2
    }

    private fun log(message: String) {
        if (battleLogEnabled)
            println(message)
    }

    private infix fun Creature.attacks(other: Creature) {
        if (this.attackOther(other, dice))
            log("${this.name} attacks ${other.name} and deals damage.")
        else
            log("${this.name}'s attack missed.")
    }

    private fun askForAction(): Int {
        var result: Int? = null
        while (result !in 1..2) {
            log("\nChoose your action:\n" + "1. Attack\n" + "2. Heal\n" + "Enter your choice: ")
            result = readLine()?.toIntOrNull()
            if (result !in 1..2)
                log("Invalid choice, please try again.")
        }
        println()
        return result!!
    }
}