package com.example.githubusers.data.model


import com.google.gson.annotations.SerializedName

class ResponseFollowers : ArrayList<ResponseFollowers.ResponseFollowersItem>(){
    data class ResponseFollowersItem(
        @SerializedName("avatar_url")
        val avatarUrl: String?, // https://github.com/images/error/octocat_happy.gif
        @SerializedName("events_url")
        val eventsUrl: String?, // https://api.github.com/users/octocat/events{/privacy}
        @SerializedName("followers_url")
        val followersUrl: String?, // https://api.github.com/users/octocat/followers
        @SerializedName("following_url")
        val followingUrl: String?, // https://api.github.com/users/octocat/following{/other_user}
        @SerializedName("gists_url")
        val gistsUrl: String?, // https://api.github.com/users/octocat/gists{/gist_id}
        @SerializedName("gravatar_id")
        val gravatarId: String?,
        @SerializedName("html_url")
        val htmlUrl: String?, // https://github.com/octocat
        @SerializedName("id")
        val id: Int?, // 1
        @SerializedName("login")
        val login: String?, // octocat
        @SerializedName("node_id")
        val nodeId: String?, // MDQ6VXNlcjE=
        @SerializedName("organizations_url")
        val organizationsUrl: String?, // https://api.github.com/users/octocat/orgs
        @SerializedName("received_events_url")
        val receivedEventsUrl: String?, // https://api.github.com/users/octocat/received_events
        @SerializedName("repos_url")
        val reposUrl: String?, // https://api.github.com/users/octocat/repos
        @SerializedName("site_admin")
        val siteAdmin: Boolean?, // false
        @SerializedName("starred_url")
        val starredUrl: String?, // https://api.github.com/users/octocat/starred{/owner}{/repo}
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String?, // https://api.github.com/users/octocat/subscriptions
        @SerializedName("type")
        val type: String?, // User
        @SerializedName("url")
        val url: String? // https://api.github.com/users/octocat
    )
}