package com.example.myfypproject.Base

import com.example.myfypproject.Activity.LoginActivity

object BaseUrl{
    //val url = "http://192.168.68.101:81/api/"
    val url = "http://192.168.68.101:45455/api/"
}

object PreviousScreen{
    val LoginActivity = 0
    val SwitchProfile = 1
}

object IntentKeyValue{
    val PrevScreen ="prevScreen"
}

enum class Relationship {
    Self,
    Mother,
    Father,
    GrandFather,
    GrandMother,
}

object FragmentName {
    val NotificationPreferences ="Notification Preferences"
    val SwitchProfile = "Switch Profile"
}
