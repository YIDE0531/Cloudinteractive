package com.nuu.cloudinteractive.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ThumbnailInfo(
    @SerializedName("albumId") var albumId: Int,
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String?,
    @SerializedName("url") var url: String?,
    @SerializedName("thumbnailUrl") var thumbnailUrl: String?
): Serializable

