package com.github.samunohito.libs.io

import kotlin.math.abs
import kotlin.math.pow

/**
 * ﾌﾞﾘﾌﾞﾘﾌﾞﾘﾌﾞﾘｭﾘｭﾘｭﾘｭﾘｭﾘｭ！！！！！！
 * ﾌﾞﾂﾁﾁﾌﾞﾌﾞﾌﾞﾁﾁﾁﾁﾌﾞﾘﾘｲﾘﾌﾞﾌﾞﾌﾞﾌﾞｩｩｩｩｯｯｯ
 */
internal enum class UnchiStateType(val word: String) {
    /**
     * ﾌﾞ
     */
    BU("ﾌﾞ"),

    /**
     * ﾘ
     */
    RI("ﾘ"),

    /**
     * ﾘｭ
     */
    RYU("ﾘｭ"),

    /**
     * ﾁ
     */
    CHI("ﾁ"),

    /**
     * ﾂ
     */
    TSU("ﾂ"),

    /**
     * ｯ
     */
    LTU("ｯ"),

    /**
     * ｩ
     */
    LU("ｩ"),

    /**
     * !
     */
    EX("!")
    ;

    companion object {
        fun fromOrdinal(idx: Int): UnchiStateType {
            val values = values()
            if (idx >= values.size) {
                throw IllegalArgumentException("idx out of bounds.")
            }

            return values[idx]
        }
    }
}

/**
 * うんちの振る舞いを決める状態
 */
internal interface UnchiState {
    /**
     * つぎのうんちの仕方を決める
     */
    fun next(seed: Int): UnchiState

    /**
     * うんちの仕方とパラメータを使ってうんちする
     */
    fun generate(seed: Int): String

    /**
     * いまのうんちの仕方を取り出す
     */
    val current: UnchiStateType
}

internal object UnchiStateFactory {
    /**
     * うんちステートの種別からうんちステートのインスタンスを取得する
     */
    fun get(state: UnchiStateType): UnchiState {
        return when (state) {
            UnchiStateType.BU -> UnchiBuState
            UnchiStateType.RI -> UnchiRiState
            UnchiStateType.RYU -> UnchiRyuState
            UnchiStateType.CHI -> UnchiChiState
            UnchiStateType.TSU -> UnchiTsuState
            UnchiStateType.LTU -> UnchiLtuState
            UnchiStateType.LU -> UnchiLuState
            UnchiStateType.EX -> UnchiExState
        }
    }

    private object UnchiBuState : UnchiState {
        private val candidates = listOf(
            UnchiStateType.BU,
            UnchiStateType.BU,
            UnchiStateType.BU,
            UnchiStateType.BU,
            UnchiStateType.RI,
            UnchiStateType.RYU,
            UnchiStateType.RYU,
            UnchiStateType.CHI,
            UnchiStateType.CHI,
            UnchiStateType.TSU,
            UnchiStateType.LTU,
        )

        override fun next(seed: Int): UnchiState {
            val next = candidates[abs(seed) % candidates.size]
            return get(next)
        }

        override fun generate(seed: Int): String {
            return current.word
        }

        override val current: UnchiStateType = UnchiStateType.BU
    }

    private object UnchiRiState : UnchiState {
        private val candidates = listOf(
            UnchiStateType.BU,
            UnchiStateType.RI,
            UnchiStateType.RYU,
        )

        override fun next(seed: Int): UnchiState {
            val next = candidates[abs(seed) % candidates.size]
            return get(next)
        }

        override fun generate(seed: Int): String {
            if (seed.odd()) {
                return current.word.repeat(abs(seed.digitOf(1)) / 2)
            }

            return current.word
        }

        override val current: UnchiStateType = UnchiStateType.RI
    }

    private object UnchiRyuState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.EX)
        }

        override fun generate(seed: Int): String {
            return UnchiRiState.current.word.repeat(abs(seed.digitOf(1)))
        }

        override val current: UnchiStateType = UnchiStateType.RYU
    }

    private object UnchiChiState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.BU)
        }

        override fun generate(seed: Int): String {
            val cnt = abs(seed.digitOf(1)) / 2 + 1
            return UnchiLtuState.current.word.repeat(cnt)
        }

        override val current: UnchiStateType = UnchiStateType.CHI
    }

    private object UnchiTsuState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.CHI)
        }

        override fun generate(seed: Int): String {
            return current.word
        }

        override val current: UnchiStateType = UnchiStateType.TSU
    }

    private object UnchiLtuState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.EX)
        }

        override fun generate(seed: Int): String {
            val cnt = abs(seed.digitOf(1)) / 2 + 1
            return current.word.repeat(cnt)
        }

        override val current: UnchiStateType = UnchiStateType.LTU
    }

    private object UnchiLuState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.LTU)
        }

        override fun generate(seed: Int): String {
            return current.word
        }

        override val current: UnchiStateType = UnchiStateType.LU
    }

    private object UnchiExState : UnchiState {
        override fun next(seed: Int): UnchiState {
            return get(UnchiStateType.BU)
        }

        override fun generate(seed: Int): String {
            val words = current.word.repeat(abs(seed.digitOf(1)) / 2)

            if (seed.digitOf(2).even()) {
                return "ｯ$words"
            }

            return words
        }

        override val current: UnchiStateType = UnchiStateType.EX
    }

    private fun Int.odd(): Boolean {
        return this % 2 == 0
    }

    private fun Int.even(): Boolean {
        return this % 2 == 1
    }

    private fun Int.digitOf(digit: Int): Int {
        if (digit == 0) {
            throw IllegalArgumentException("incorrect ranges.")
        }

        val power1 = 10.0.pow(digit).toInt()
        val power2 = 10.0.pow(digit - 1).toInt()

        // 4321 / 1000 -> 4 * 1000 -> 4000
        val step1 = (this / power1) * power1
        // 4321 - 4000 -> 321
        val step2 = this - step1
        // 321 / 100 -> 3
        return step2 / power2
    }

    private fun String.repeat(count: Int): String {
        val sb = StringBuilder(this.length * count)
        for (i in 0..count) {
            sb.append(this)
        }
        return sb.toString()
    }
}