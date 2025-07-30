package com.cdg.skeleton.common.vo.privacy

class CiPrivacy(
    override val plain: String,
) : Privacy() {
    override val masking: String =
        maskingMiddle(
            target = this.plain,
            plainHeadCount = 7,
            plainTailCount = 0,
        )
}
