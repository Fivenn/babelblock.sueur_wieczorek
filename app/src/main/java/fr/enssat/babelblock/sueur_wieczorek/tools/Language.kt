package fr.enssat.babelblock.sueur_wieczorek.tools

import java.util.*

class Language(val code: String) : Comparable<Language> {

    private val displayName: String
        get() = Locale(code).displayName

    override fun equals(other: Any?): Boolean {
        if (other === this) {
            return true
        }

        if (other !is Language) {
            return false
        }

        val otherLanguage = other as Language?

        return otherLanguage!!.code == code
    }

    override fun toString(): String {
        return displayName
    }

    override fun compareTo(other: Language): Int {
        return this.displayName.compareTo(other.displayName)
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }
}