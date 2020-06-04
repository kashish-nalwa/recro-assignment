package com.android.recroassignment.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.recroassignment.R
import com.android.recroassignment.model.newslist.Article
import com.bumptech.glide.Glide

class ArticlesRecyclerAdapter(val listener: ArticleSelectionListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var articlesResource: ArticlesResource<List<Article>>? = null

    fun updateList(articlesResource: ArticlesResource<List<Article>>) {
        this.articlesResource = articlesResource
        notifyDataSetChanged()
    }

    fun onError(articlesResource: ArticlesResource<List<Article>>) {
        if (!(this.articlesResource is ArticlesResource.Success)) {
            updateList(articlesResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View

        when (viewType) {
            VIEW_TYPE_LOADING -> {
                view = inflater.inflate(R.layout.item_view_loading, parent, false)
                return LoadingViewHolder(
                    view
                )
            }
            VIEW_TYPE_RESULT -> {
                view = inflater.inflate(R.layout.item_view_articles_recycler_adapter, parent, false)
                return ResultViewHolder(
                    view
                )
            }
            VIEW_TYPE_ERROR -> {
                view = inflater.inflate(R.layout.item_view_error, parent, false)
                return ErrorViewHolder(
                    view
                )
            }
            else -> {
                view = inflater.inflate(R.layout.item_view_loading, parent, false)
                return LoadingViewHolder(
                    view
                )
            }
        }
    }

    override fun getItemCount(): Int {
        articlesResource?.let {
            return it.data.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingViewHolder -> {
                onBindViewHolderLoader(holder, articlesResource!!.data.get(position))
            }

            is ResultViewHolder -> {
                onBindViewHolderResult(holder, articlesResource!!.data.get(position))
            }
            is ErrorViewHolder -> {
                onBindViewHolderError(holder, articlesResource!!.data.get(position))
            }
        }
    }

    private fun onBindViewHolderLoader(
        holder: LoadingViewHolder,
        article: Article
    ) {
    }

    private fun onBindViewHolderError(
        holder: ErrorViewHolder,
        article: Article
    ) {
        holder.tvError.text = holder.tvError.context.getString(R.string.text_no_result_found)
    }

    private fun onBindViewHolderResult(
        holder: ResultViewHolder,
        article: Article
    ) {

        if (article.title.isNullOrEmpty()) {
            holder.tvTitle.text = holder.tvTitle.context.getString(R.string.text_samplet_title)
        } else {
            holder.tvTitle.text = article.title
        }


        if (article.author.isNullOrEmpty()) {
            holder.tvAuthor.text = holder.tvAuthor.context.getString(R.string.text_anonymous)
        } else {
            holder.tvAuthor.text = article.author
        }

        Glide.with(holder.ivArticle)
            .load(article.urlToImage)
            .into(holder.ivArticle)

        holder.itemView.setOnClickListener {
            listener.onArticleSelected(article)
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (articlesResource != null) {
            when (articlesResource) {
                is ArticlesResource.Loading -> {
                    return VIEW_TYPE_LOADING
                }
                is ArticlesResource.Success -> {
                    return VIEW_TYPE_RESULT
                }
                is ArticlesResource.Error -> {
                    return VIEW_TYPE_ERROR
                }
            }
        }
        return VIEW_TYPE_ERROR
    }

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticle: ImageView
        val tvTitle: TextView
        val tvAuthor: TextView

        init {
            tvAuthor = itemView.findViewById(R.id.tvAuthor)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            ivArticle = itemView.findViewById(R.id.ivArticle)
        }
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvError: TextView

        init {
            tvError = itemView.findViewById(R.id.tvError)
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }

    companion object {
        val TAG = "ArticlesRecyclerAdapter"
        val VIEW_TYPE_LOADING = 0
        val VIEW_TYPE_RESULT = 1
        val VIEW_TYPE_ERROR = 2
    }

}