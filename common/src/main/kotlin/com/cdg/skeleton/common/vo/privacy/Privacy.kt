package com.cdg.skeleton.common.vo.privacy

abstract class Privacy {
    abstract val plain: String
    abstract val masking: String

    override fun toString(): String {
        return masking
    }

    override fun hashCode(): Int {
        return plain.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Privacy) return false
        return other.plain == plain
    }
}
