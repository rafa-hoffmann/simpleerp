package com.sonder.simpleerp.common.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val serpDispatcher: SerpDispatchers)

enum class SerpDispatchers {
    IO
}
