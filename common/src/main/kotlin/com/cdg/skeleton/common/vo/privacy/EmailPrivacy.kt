package com.cdg.skeleton.common.vo.privacy

class EmailPrivacy(
    override val plain: String,
) : Privacy() {
    override val masking: String = mask()

    private fun mask(): String {
        if (!this.plain.contains("@")) {
            return this.plain
        }
        val split = this.plain.split("@").toTypedArray()
        val localPart = split[0]
        val domainPart = split[1]
        val maskedLocalPart =
            if (localPart.length <= 2) {
                maskingEdge(localPart, maskingTailCount = 1)
            } else {
                maskingEdge(localPart, maskingTailCount = localPart.length - 2)
            }
        return "$maskedLocalPart@$domainPart"
    }
}
