package com.example.githubusers.data.model


import com.google.gson.annotations.SerializedName

data class ResponseDetail(
    @SerializedName("avatar_url")
    val avatarUrl: String?, // https://avatars.githubusercontent.com/u/103646893?v=4
    @SerializedName("bio")
    val bio: String?, // Android Developer
    @SerializedName("blog")
    val blog: String?,
    @SerializedName("company")
    val company: String?, // Freelancer
    @SerializedName("created_at")
    val createdAt: String?, // 2022-04-14T13:06:48Z
    @SerializedName("email")
    val email: Any?, // null
    @SerializedName("events_url")
    val eventsUrl: String?, // https://api.github.com/users/hossein69ameri/events{/privacy}
    @SerializedName("followers")
    val followers: Int?, // 1
    @SerializedName("followers_url")
    val followersUrl: String?, // https://api.github.com/users/hossein69ameri/followers
    @SerializedName("following")
    val following: Int?, // 1
    @SerializedName("following_url")
    val followingUrl: String?, // https://api.github.com/users/hossein69ameri/following{/other_user}
    @SerializedName("gists_url")
    val gistsUrl: String?, // https://api.github.com/users/hossein69ameri/gists{/gist_id}
    @SerializedName("gravatar_id")
    val gravatarId: String?,
    @SerializedName("hireable")
    val hireable: Any?, // null
    @SerializedName("html_url")
    val htmlUrl: String?, // https://github.com/hossein69ameri
    @SerializedName("id")
    val id: Int?, // 103646893
    @SerializedName("location")
    val location: String?, // Tehran,Iran
    @SerializedName("login")
    val login: String?, // hossein69ameri
    @SerializedName("name")
    val name: String?, // Hossein
    @SerializedName("node_id")
    val nodeId: String?, // U_kgDOBi2GrQ
    @SerializedName("organizations_url")
    val organizationsUrl: String?, // https://api.github.com/users/hossein69ameri/orgs
    @SerializedName("public_gists")
    val publicGists: Int?, // 0
    @SerializedName("public_repos")
    val publicRepos: Int?, // 5
    @SerializedName("received_events_url")
    val receivedEventsUrl: String?, // https://api.github.com/users/hossein69ameri/received_events
    @SerializedName("repos_url")
    val reposUrl: String?, // https://api.github.com/users/hossein69ameri/repos
    @SerializedName("site_admin")
    val siteAdmin: Boolean?, // false
    @SerializedName("starred_url")
    val starredUrl: String?, // https://api.github.com/users/hossein69ameri/starred{/owner}{/repo}
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String?, // https://api.github.com/users/hossein69ameri/subscriptions
    @SerializedName("twitter_username")
    val twitterUsername: Any?, // null
    @SerializedName("type")
    val type: String?, // User
    @SerializedName("updated_at")
    val updatedAt: String?, // 2023-01-26T12:36:30Z
    @SerializedName("url")
    val url: String? // https://api.github.com/users/hossein69ameri
)