package com.dwngkhoi.iohksu.ui.component

import androidx.compose.runtime.Composable
import com.dwngkhoi.iohksu.domain.model.KernelStatus

@Composable
inline fun KsuIsValid(
    status: KernelStatus,
    content: @Composable () -> Unit
) {
    if (status.isValid)
        content()
}
