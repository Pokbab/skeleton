package com.cdg.skeleton.common.vo.privacy

class PhonePrivacy(
    override val plain: String,
) : Privacy() {
    override val masking: String = maskingMiddle(this.plain, 3, 4)
}
