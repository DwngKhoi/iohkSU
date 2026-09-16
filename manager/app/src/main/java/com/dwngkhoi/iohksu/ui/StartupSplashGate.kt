package com.dwngkhoi.iohksu.ui

import com.dwngkhoi.iohksu.domain.model.StartupState

internal fun shouldKeepStartupSplash(
    startupState: StartupState,
    homeInitialDataLoaded: Boolean,
): Boolean = when (startupState) {
    StartupState.Loading -> true
    StartupState.Ready -> !homeInitialDataLoaded
    is StartupState.Failed -> false
}
