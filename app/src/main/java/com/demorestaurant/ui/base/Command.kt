package com.demorestaurant.ui.base

sealed class Command {
    class HideLoadingDialog : Command()
    class ShowLoadingDialog : Command()
    class OfflineDialog : Command()
}