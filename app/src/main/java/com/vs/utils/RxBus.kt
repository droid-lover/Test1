package com.vs.utils

import com.vs.models.User
import io.reactivex.subjects.PublishSubject

/**
 * Created by Sachin
 */
object RxBus {
    val showActionDailog = PublishSubject.create<User>()
    val changedLocale = PublishSubject.create<Boolean>()
}