package me.chriskyle.ikan.data.repository.datastore.params.provider

import me.chriskyle.ikan.data.repository.datastore.params.Constants
import me.chriskyle.library.net.request.param.BaseParamsProvider

/**
 * Description :
 *
 * Created by Chris Kyle on 2017/9/19.
 */
class FeedParamProvider : BaseParamsProvider() {

    fun feedId(feedId: Long): FeedParamProvider {
        append(FEED_ID, feedId)
        return this
    }

    fun type(feedType: Int?): FeedParamProvider {
        append(FEED_TYPE, feedType)
        return this
    }

    fun segment(segment: String): FeedParamProvider {
        append(FEED_SEGMENT, segment)
        return this
    }

    fun searchContent(searchContent: String): FeedParamProvider {
        append(FEED_SEARCH_CONTENT, searchContent)
        return this
    }

    fun category(category: String): FeedParamProvider {
        append(FEED_CATEGORY, category)
        return this
    }

    fun commentContent(content: String) : FeedParamProvider {
        append(FEED_COMMENT_CONTENT, content)
        return this
    }

    fun getFeedId() = optionalParam[FEED_ID] as String

    companion object {

        const val FEED_ID = Constants.API.FEED_ID
        const val FEED_SEGMENT = Constants.API.FEED_SEGMENT
        const val FEED_TYPE = Constants.API.FEED_TYPE
        const val FEED_CATEGORY = Constants.API.FEED_CATEGORY
        const val FEED_SEARCH_CONTENT = Constants.API.FEED_SEARCH_CONTENT
        const val FEED_COMMENT_CONTENT = Constants.API.FEED_COMMENT_CONTENT
    }
}