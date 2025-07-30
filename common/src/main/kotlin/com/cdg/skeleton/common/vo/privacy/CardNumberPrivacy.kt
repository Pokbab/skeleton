package com.cdg.skeleton.common.vo.privacy

class CardNumberPrivacy(
    override val plain: String,
) : Privacy() {
    override val masking: String =
        if (plain.length < 16) {
            maskingMiddleRange(this.plain, 6, 11)
        } else {
            maskingEdge(maskingMiddleRange(this.plain, 8, 11), 0, 1)
        }
}
