package com.nuu.cloudinteractive.config

class AppConfig {

    companion object{
        val target = Target.PROD

        //base url
//        const val DEV_BASE_API = "https://jsonplaceholder.typicode.com"
        const val PROD_BASE_API = "https://jsonplaceholder.typicode.com"

        //end point
        const val PROD_ENDPOINT = "/photos"

        val URL_CALL_PHOTOS = serviceBaseApi + serviceEndpoint

        private val serviceBaseApi: String
            private get() {
                return when (target) {
//                    Target.DEV -> DEV_BASE_API
                    Target.PROD -> PROD_BASE_API
                    else -> PROD_BASE_API
                }
            }

        private val serviceEndpoint: String
            private get() {
                return when (target) {
//                    Target.DEV -> DEV_BASE_API
                    Target.PROD -> PROD_ENDPOINT
                    else -> PROD_ENDPOINT
                }
            }

        enum class Target {
            DEV, TEST, PROD
        }
    }
}